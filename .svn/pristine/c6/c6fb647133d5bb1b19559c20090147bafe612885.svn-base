package com.vsoftcorp.kls.service.transaction;

import java.util.List;

import com.vsoftcorp.kls.data.BulkCustomerData;
import com.vsoftcorp.kls.data.CustXLData;
import com.vsoftcorp.kls.data.gateway.datahelpers.STLoanRecoveryData;
/**
 * 
 * @author a1605
 *
 */
public interface ISTLoanRecoveryTransactionService {

	public STLoanRecoveryData getBifurcationAmountsToBePaid(STLoanRecoveryData stLoanRecoveryData);
	
	public List<CustXLData> validateAndProcessRecovery(List<CustXLData> uploadedExcelData,Integer pacsId);
	
	public List<BulkCustomerData> validateAndProcessBulkLoanRecovery(List<BulkCustomerData> bulkCustData);

}
