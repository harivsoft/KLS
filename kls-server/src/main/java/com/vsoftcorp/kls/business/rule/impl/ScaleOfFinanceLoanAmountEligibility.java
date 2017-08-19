package com.vsoftcorp.kls.business.rule.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.vsoftcorp.kls.business.entities.ScaleOfFinance;
import com.vsoftcorp.kls.business.rule.IScaleOfFinanceLoanAmountEligibility;

public class ScaleOfFinanceLoanAmountEligibility implements IScaleOfFinanceLoanAmountEligibility{

	@Override
	public BigDecimal getScaleOfFinanceAmount(ScaleOfFinance theScaleOfFinance,
			Double landAreainAcres) throws RuntimeException{
		return theScaleOfFinance.getLoanAmtPerAcre().multiply(BigDecimal.valueOf(landAreainAcres)).setScale(0, RoundingMode.HALF_UP);
	}
}
