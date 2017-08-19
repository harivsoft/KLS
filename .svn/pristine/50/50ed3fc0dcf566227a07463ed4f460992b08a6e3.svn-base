package com.vsoftcorp.kls.business.rule.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.vsoftcorp.kls.business.entities.ScaleOfFinance;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;

public class ScaleOfFinanceLoanAmountEligibilityTest {
	private static final Logger logger = Logger
			.getLogger(ScaleOfFinanceLoanAmountEligibilityTest.class);

	@Test
	public void testGetScaleOfFinanceAmount() {
		logger.info("Start: testing testGetScaleOfFinanceAmount()");
		try {
			ScaleOfFinanceLoanAmountEligibility sof = new ScaleOfFinanceLoanAmountEligibility();
			ScaleOfFinance scaleOfFinance = new ScaleOfFinance();
			scaleOfFinance.setLoanAmtPerAcre(new BigDecimal(10));
			BigDecimal loanAmount = sof.getScaleOfFinanceAmount(scaleOfFinance,
					new Double(10));
			Assert.assertEquals(new BigDecimal(100), loanAmount);
		} catch (Exception exception) {
			logger.error("Error occured while calculating scale of financeeligibility amount in testGetScaleOfFinanceAmount() method ");
			throw new KlsRuntimeException(
					"Error while calculating scale of finance eligibility amount ",
					exception.getCause());
		}
		logger.info("End: testing testGetScaleOfFinanceAmount()");

	}
}
