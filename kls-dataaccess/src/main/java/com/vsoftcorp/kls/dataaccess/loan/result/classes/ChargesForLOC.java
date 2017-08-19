package com.vsoftcorp.kls.dataaccess.loan.result.classes;

import java.math.BigDecimal;


public class ChargesForLOC {
	
	private BigDecimal chargeAmount;

	private Long lineOfCreditId;

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public Long getLineOfCreditId() {
		return lineOfCreditId;
	}

	public void setLineOfCreditId(Long lineOfCreditId) {
		this.lineOfCreditId = lineOfCreditId;
	}
	
	
}
