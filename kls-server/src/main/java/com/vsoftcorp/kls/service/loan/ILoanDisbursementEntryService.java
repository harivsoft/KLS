package com.vsoftcorp.kls.service.loan;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.data.LoanDisbursementEntryData;
import com.vsoftcorp.kls.data.LoanDisbursementEntryList;
/**
 * 
 * @author a1623
 *
 */
public interface ILoanDisbursementEntryService {

	String saveLoanDisbursementEntry(LoanDisbursementEntryData passingData);
	
	public String saveBulkLoanDisbursementEntry(LoanDisbursementEntryList passingData);

	List<LoanDisbursementEntryData> getDisbursementEntriesList(long customerNumber, Integer status, String loanType);

	void updateLoanDisbursementEntry(LoanDisbursementEntryData data);

	List<LoanDisbursementEntryData> getDisbursementEntriesListbasedOnLocId(long customerNumber,
			Integer status, long locId, String loanType);

	List<Map> getDisbursedLOCIdsForPassing(Long customerId, String loanType);

	Map<String,Object> getLoanInfo(Long customerNumber, String loanType, String transType);
	
	LoanDisbursementEntryData getDisbursementEntriesbasedOnLocId(long customerNumber, Integer status, long locId, String loanType,String transactionDate);
	
	public String disbursementPassing(LoanDisbursementEntryList passingData);
	
	public void updateBulkDisbursementEntry(LoanDisbursementEntryData data);
	
	public String removeDisbursementEntry(String id);

}
