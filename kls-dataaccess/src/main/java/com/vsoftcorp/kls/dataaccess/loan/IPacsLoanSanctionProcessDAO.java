package com.vsoftcorp.kls.dataaccess.loan;



import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entity.account.Account;

public interface IPacsLoanSanctionProcessDAO {

	// This will also create the LineOfCredit internally season and crop wise
	public void createOrUpdateAccount(Product theProduct,Account theAccount); 
	
	public List<Map> getLoanSanctionDetails(String pacId,String financialYear);

	public List<PacsLoanApplicationDetail> getPacsLoanApplicationByCustomer(String customerId,String pacId,String financialYear);

	public List<PacsLoanApplicationDetail> getPacsLoanInspectedApplicationList(
			String pacId, String financialYear,Integer pageIndex);
	
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationList(
			String pacId, String financialYear);

	public void updateSanctionAmount(
			PacsLoanApplicationDetail pacsLoanApplicationDetail);
	
	public Integer getTotalNoOfLoanInspectedApplications(String pacId, String financialYear);
}
