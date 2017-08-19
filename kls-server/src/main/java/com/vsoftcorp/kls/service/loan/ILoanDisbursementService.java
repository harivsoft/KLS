package com.vsoftcorp.kls.service.loan;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.data.LoanDisbursementEntryDataList;
import com.vsoftcorp.kls.data.LoanDisbursementEntryList;
import com.vsoftcorp.kls.data.LoanDisbursementPassingData;
import com.vsoftcorp.kls.data.MTMultipleDisburseData;
import com.vsoftcorp.kls.data.MTMultiplePassingDataList;
import com.vsoftcorp.kls.data.PacsLoanDisbursementData;
import com.vsoftcorp.kls.data.DisbursementPassingData;

public interface ILoanDisbursementService {
	
	public String saveLoanDisbursement(PacsLoanDisbursementData loanDisbursementData);
	
	public String getDisburshmentScheduleAmout(long id, String date);
	
	public LoanDisbursementEntryDataList getloanDisburseEntryList(String pacsId,String transactionDate);

	public String updateMultipleDisbursement(MTMultipleDisburseData passingData);
	
	public List<Map> getBulkDisbursementDetails(String loanType,String disbursementDate,String instrumentNo,String pacsId);
	
}
