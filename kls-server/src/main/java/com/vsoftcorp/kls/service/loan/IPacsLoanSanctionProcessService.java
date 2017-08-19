package com.vsoftcorp.kls.service.loan;

import java.util.List;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.data.LoanSanctionData;
import com.vsoftcorp.kls.data.LoanSanctionSummaryData;
import com.vsoftcorp.kls.data.PacsLoanApplicationDetailData;

public interface IPacsLoanSanctionProcessService {

	public List<LoanSanctionData> getLoanSanctionDetails(String pacId,String financialYear,Integer pageIndex);
	
	public PacsLoanApplicationDetailData getPacsLoanApplicationByCustomer(String customerId,String pacId,String financialYear,Money sanctionedAmount);

	public void updateLoanSanction(LoanSanctionData loanSanctionData);

	public LoanSanctionSummaryData getLoanSanctionSummary(String pacId,
			String financialYear);

	public void updateSanctionAmount(List<LoanSanctionData> loanSanctionDataList);

	public String getShareAndInsuranceAmount(long id, String sanctionedAmount);
	
	public void createAccount(String customerId, String pacId);
	
	public Integer getTotalNoOfLoanInspectedApplications(String pacId, String financialYear);
}
