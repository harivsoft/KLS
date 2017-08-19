package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;
import com.vsoftcorp.kls.business.entities.Season;
/**
 * 
 * @author a1605
 *
 */
 public interface ILoanApplicationRenewalDAO {

	
	public List<PacsLoanApplicationHeader> getLoanApplicationHeaderForRenewal(String financialYear,Integer pacId);
	
	public List<PacsLoanApplicationDetail> getLoanApplicationDetailForRenewal(Long headerId);
	
	public Season getSeasonByName(String seasonName);
	
}
