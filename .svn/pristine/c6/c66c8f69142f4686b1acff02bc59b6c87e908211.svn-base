package com.vsoftcorp.kls.loan.util;

import java.math.BigDecimal;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.time.Date;

/**
 * @author sponnam
 * 
 */
public class InterestCalculationUtil {

	/**
	 * @param principleAmount
	 * @param rateOfInterest
	 * @param noOfDays
	 * @return interest
	 */
	public static double perDaySimpleInterest(double principleAmount,
			double rateOfInterest, int noOfDays) {

		return (principleAmount * rateOfInterest * noOfDays) / 36500;
	}

	public static Money perDaySimpleInterest(Money principleAmount,
			float rateOfInterest, int noOfDays) {

		return (principleAmount.multiply(rateOfInterest).multiply(noOfDays))
				.divide(36500);
	}

	// TODO: interest with scale of 6 digits. not to be used as of now. To
	// change LineOfCredit interestDue Money with BigDecimal since Money
	// supports 2 digit after decimal???
	public static BigDecimal perDaySimpleInterest(BigDecimal principleAmount,
			BigDecimal rateOfInterest, int noOfDays) {

		return (principleAmount.multiply(rateOfInterest)
				.multiply(new BigDecimal(noOfDays))).divide(new BigDecimal(
				36500.00), 6, BigDecimal.ROUND_CEILING);
	}

	public static int getNoOfDays(Date currentDate, Date openDate,
			Date lastIntPostedDate) {
		int interestToBeCalculatedDays = (lastIntPostedDate == null) ? currentDate
				.subtract(openDate) : currentDate.subtract(lastIntPostedDate);
		return interestToBeCalculatedDays;
	}

	public BigDecimal getPercentageOntheAmount(Money amount, BigDecimal roi) {
		BigDecimal percentageAmount = BigDecimal.ZERO;
		try {
			percentageAmount = (amount.getAmount().abs().multiply(roi))
					.divide(BigDecimal.valueOf(100.00));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return percentageAmount;
	}
}
