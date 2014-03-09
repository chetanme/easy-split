package edu.usc.easysplit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.usc.easysplit.AddBillRequest;
import edu.usc.easysplit.AddBillResponse;
import edu.usc.easysplit.Constants;
import edu.usc.easysplit.SplitGroup;
import edu.usc.easysplit.services.IBillService;
import edu.usc.easysplit.services.ICacheManager;

@Service
public class BillServiceImpl implements IBillService {

	@Autowired
	private ICacheManager cacheService;
	
	@Override
	public AddBillResponse addBill (AddBillRequest request) {
		AddBillResponse response = new AddBillResponse();
		if (request.getGroupId() != 0) {
			if (!this.cacheService.hasValueForKeys(Constants.ALL_GROUPS, Integer.toString(request.getGroupId()))) {
				response.setSuccess(false);
				response.setFailMsg("No group with this id");
			} else {
				SplitGroup group = (SplitGroup) this.cacheService.getValue(Constants.ALL_GROUPS, Integer.toString(request.getGroupId()));
				
				int groupSize = group.getStats().size();
				double eachMemContri = request.getAmount() / groupSize;
				
				for (String eachMem : group.getStats().keySet()) {
					double currentBal = group.getStats().get(eachMem);
					if (request.getUserName().equalsIgnoreCase(eachMem)) {
						group.getStats().put(eachMem, (currentBal + request.getAmount() - eachMemContri));
					} else {
						group.getStats().put(eachMem, (currentBal - eachMemContri));
					}
				}
				
				this.cacheService.storeValue(Constants.ALL_GROUPS, Integer.toString(request.getGroupId()), group);
				
				response.setUpdatedGroup(group);
				response.setSuccess(true);
			}
		} else {
			response.setSuccess(false);
			response.setFailMsg("No group id in request");
		}
		return response;
	}
}