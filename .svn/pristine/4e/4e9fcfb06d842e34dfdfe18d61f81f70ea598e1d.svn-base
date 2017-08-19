package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationForActivity;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationDocument;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationForGuarantorDetails;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

public interface IPacsLoanApplicationDAO {

	public Long saveLoanApplication(PacsLoanApplication master);

	public void updatePacsLoanApplication(PacsLoanApplication master);

	public void saveLoanDocuments(LoanApplicationDocument document);

	public void updateLoanDocuments(LoanApplicationDocument document);

	public void saveLoanActivity(LoanApplicationForActivity activity);

	public void saveLoanGuarantor(
			LoanApplicationForGuarantorDetails guarantorDetails);

	public PacsLoanApplication getLoanApplicationById(Long applicationId);

	public List<LoanApplicationForActivity> getActivityListByApplicationId(
			Long applicationId);

	public List<LoanApplicationForGuarantorDetails> getGuarantorListByApplicationId(
			Long applicationId);

	public List<LoanApplicationDocument> getDocumentListByApplicationId(
			Long docApplicationId);

	public List<PacsLoanApplication> getLoanApplicationsByCustomerId(
			Long customerId, LoanApplicationState loanApplicationState);

	public List<Map> getRateOfInterest(Integer interestCategoryId,
			Money amount, Integer months);
	
	public void deleteActivityByApplicationId(Long applicationId, Long activityId); 
	
	public void deleteGuarantorByApplicationId(Long applicationId, Long guarantorId);
	
	public void deleteDocumentsById(Long documentId);
	
	public List<PacsLoanApplication> getLoanApplicationsByCustomerId(
			Long customerId, LoanApplicationState loanApplicationState,String applicationType);

	public List<PacsLoanApplication> getLoanApplicationsByCustomerIdAndLoanType(
			Long customerId, LoanApplicationState loanApplicationState,
			String applicationType, String loanType);
}
