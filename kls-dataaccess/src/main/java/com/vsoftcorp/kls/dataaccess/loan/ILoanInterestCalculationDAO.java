package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.subsidy.InterestSubsidy;
import com.vsoftcorp.kls.business.entity.subsidy.SlabwisesubsidyInterestRate;
import com.vsoftcorp.kls.valuetypes.interest.InterestCalculationBasis;
import com.vsoftcorp.time.Date;

/**
 * @author sponnam
 * 
 */
public interface ILoanInterestCalculationDAO {

	public void calculateInterest(Date theBusinessDate,
			InterestCalculationBasis theCalculationBasis,List<Integer> pacsList);

	public SlabwiseInterestRate getRateOfInterest(Integer interestCategoryId,
			Date theBusinessDate, Money theBalance);

	public void updateInterest(LineOfCredit theLoc, Money theInterestDue);

	public void currentDayInterestCalculation(Date theBusinessDate,List<Integer> pacsList);
	
	public List<LineOfCredit> getLineOfCreditList(List<Integer> pacsList);

	public Map getPrincipleAndInterestAmtByLocId(Long locId, Date businessDate);

	public SlabwisesubsidyInterestRate getSubsidyRateOfInterest(Long subsidyId,
			Money theBalance);

	public List<InterestSubsidy> getSubsidyBySchemeIdDAO(Integer schemeId, Long seasonId);
	
}
