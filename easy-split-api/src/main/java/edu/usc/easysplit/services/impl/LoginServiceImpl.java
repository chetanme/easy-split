package edu.usc.easysplit.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.usc.easysplit.Constants;
import edu.usc.easysplit.LoginRequest;
import edu.usc.easysplit.LoginResponse;
import edu.usc.easysplit.SplitGroup;
import edu.usc.easysplit.SplitUser;
import edu.usc.easysplit.services.ICacheManager;
import edu.usc.easysplit.services.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private ICacheManager cacheService;
	
	@Override
	public LoginResponse loginUser (LoginRequest request) {
		LoginResponse response = new LoginResponse();
		
		if (!this.cacheService.hasMapKey(Constants.ALL_USERS)) {
			Map<String, SplitUser> userMap = new HashMap<String, SplitUser>();
			SplitUser newUser = new SplitUser();
			newUser.setName(request.getUserName());
			newUser.setBalance(0);
			
			userMap.put(request.getUserName(), newUser);
			
			this.cacheService.storeMap(Constants.ALL_USERS, userMap);
			response.setSuccess(true);
		} else if (!this.cacheService.hasValueForKeys(Constants.ALL_USERS, request.getUserName())) {
			SplitUser newUser = new SplitUser();
			newUser.setName(request.getUserName());
			newUser.setBalance(0);
			
			this.cacheService.storeValue(Constants.ALL_USERS, request.getUserName(), newUser);
			response.setSuccess(true);
		} else {
			SplitUser thisUser = (SplitUser) this.cacheService.getValue(Constants.ALL_USERS, request.getUserName());
			
			List<SplitGroup> listOfGroups = new ArrayList<SplitGroup>();
			for (Integer eachGroupId : thisUser.getPartOf()) {
				listOfGroups.add((SplitGroup) this.cacheService.getValue(Constants.ALL_GROUPS, Integer.toString(eachGroupId.intValue())));
			}
			response.setMemberOf(listOfGroups);
			response.setSuccess(true);
		}
		return response;
	}
}