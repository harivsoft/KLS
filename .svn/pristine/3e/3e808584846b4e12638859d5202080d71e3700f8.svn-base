package com.vsoftcorp.kls.loan.util;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.junit.Test;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.time.Date;

public class InterestCalculationUtilTest extends TestCase {

	@Test
	public void testPerDaySimpleInterest_return_interest() {
		Money principleAmount = Money.valueOf(Currency.getInstance("INR"),
				"100");
		BigDecimal roi = new BigDecimal(4);
		BigDecimal interest = InterestCalculationUtil.perDaySimpleInterest(
				principleAmount.getAmount(), roi, 1);
		assertEquals("0.010959", interest.toString());
	}

	@Test
	public void testPerDaySimpleInterest_return_money() {
		Money principleAmount = Money.valueOf(Currency.getInstance("INR"),
				"100");
		BigDecimal roi = new BigDecimal(4);
		Money interest = InterestCalculationUtil.perDaySimpleInterest(
				principleAmount, roi.floatValue(), 1);
		assertEquals("0.01", interest.getAmount().toString());
	}

	@Test
	public void testGetNoOfDays_given_dates() {
		Date accountOpenDate = DateUtil.getVSoftDateByString("01 Jan 2014");
		Date lastIntPosedDate = DateUtil.getVSoftDateByString("01 Jan 2014");
		Date theBusinessDate = DateUtil.getVSoftDateByString("01 Mar 2014");
		int noOfDays = InterestCalculationUtil.getNoOfDays(theBusinessDate,
				accountOpenDate, lastIntPosedDate);
		assertEquals(59, noOfDays);
	}

}
