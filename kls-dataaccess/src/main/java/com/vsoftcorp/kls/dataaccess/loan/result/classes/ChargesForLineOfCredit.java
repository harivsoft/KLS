/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan.result.classes;

import com.vsoftcorp.finance.Money;

/**
 * @author a9152
 * 
 */
public class ChargesForLineOfCredit {

	private Money chargeAmount;

	private Long lineOfCreditId;

	public ChargesForLineOfCredit() {
	}

	public ChargesForLineOfCredit(Money chargeAmount, Long lineOfCreditId) {
		this.chargeAmount = chargeAmount;
		this.lineOfCreditId = lineOfCreditId;
	}

	/**
	 * @return the chargeAmount
	 */
	public Money getChargeAmount() {
		return chargeAmount;
	}

	/**
	 * @param chargeAmount
	 *            the chargeAmount to set
	 */
	public void setChargeAmount(Money chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	/**
	 * @return the lineOfCreditId
	 */
	public Long getLineOfCreditId() {
		return lineOfCreditId;
	}

	/**
	 * @param lineOfCreditId
	 *            the lineOfCreditId to set
	 */
	public void setLineOfCreditId(Long lineOfCreditId) {
		this.lineOfCreditId = lineOfCreditId;
	}
}
