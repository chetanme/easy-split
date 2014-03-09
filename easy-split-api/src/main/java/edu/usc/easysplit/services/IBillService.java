package edu.usc.easysplit.services;

import edu.usc.easysplit.AddBillRequest;
import edu.usc.easysplit.AddBillResponse;

public interface IBillService {
	
	public AddBillResponse addBill (AddBillRequest request);
}