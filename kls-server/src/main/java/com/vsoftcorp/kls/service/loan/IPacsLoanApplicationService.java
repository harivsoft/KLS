package com.vsoftcorp.kls.service.loan;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.data.LoanApplicationEnumsData;
import com.vsoftcorp.kls.data.PacsLoanApplicationData;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

public interface IPacsLoanApplicationService {

	public void saveLoanApplication(PacsLoanApplicationData data);

	public void saveLoanApplicationDocument(PacsLoanApplicationData data, Long appId);

	public List<Map> getAllApplicationsBasedOnCustId(Long custId, LoanApplicationState applicationStatus);

	public PacsLoanApplicationData getLoanApplicationById(Long applicationId);

	public LoanApplicationEnumsData getAllLoanApplicationEnums();

	public List<Map> getAllRateOfInterest(Integer interestCategoryId, Money amount, Integer months);

	public List<Map> getNumOfInstallmentsAndInstallmentAmount(Integer loanPeriod, Integer installmentFrequency, Long applicationId);

	public String getdocument(String file);
	
	public List<Map> getAllApplicationsBasedOnCustId(Long custId, LoanApplicationState applicationStatus,String applicationType);

	public List<Map> getAllApplicationsBasedOnCustIdAndLoanType(Long customerId,LoanApplicationState applicationStatus, String applicationType,
			String loanType);
}
