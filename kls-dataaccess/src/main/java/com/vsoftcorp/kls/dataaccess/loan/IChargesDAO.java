package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.Charges;
import com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLineOfCredit;
import com.vsoftcorp.kls.valuetypes.charges.ChargeType;
/**
 * @author a1605
 */
public interface IChargesDAO {
	
	public void saveCharges(Charges charges);
	
	public List<ChargesForLineOfCredit> getShareCharges(Long accountId,List<Long> seasonIdList);
	
	public List<ChargesForLineOfCredit> getInsuranceCharges(Long accountId,List<Long> seasonIdList);

	public void saveChargesList(List<Charges> chargesList);
	
	public List<ChargesForLineOfCredit> getCharges(Long loanLocId, ChargeType chargeType);
	
	public List<Charges> getAnyCharges(Long loanLocId, ChargeType chargeType);
	
	public void updateCharges(Charges charges);
	
	public List<Charges> getChargesByAccountIdAndLoc(Long loanLocId, Long accountId,Long seasonId);

}
