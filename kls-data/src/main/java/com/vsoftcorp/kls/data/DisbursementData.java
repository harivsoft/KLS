package com.vsoftcorp.kls.data;

import java.math.BigDecimal;
/**
 * 
 * @author a1565
 *
 */
public class DisbursementData {

	private Long lineOfCreditId;

	private Integer noOfDisbursement;
	
	private String disbursementDate;
	
	private BigDecimal disbursementAmount;
	
	private BigDecimal disbursedAmount;
	
	private BigDecimal remainingBalance;
	
	private String remarks;
	

	public Integer getNoOfDisbursement() {
		return noOfDisbursement;
	}

	public void setNoOfDisbursement(Integer noOfDisbursement) {
		this.noOfDisbursement = noOfDisbursement;
	}

	public String getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public BigDecimal getDisbursementAmount() {
		return disbursementAmount;
	}

	public void setDisbursementAmount(BigDecimal disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}

	public BigDecimal getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(BigDecimal remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getDisbursedAmount() {
		return disbursedAmount;
	}

	public void setDisbursedAmount(BigDecimal disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}

	public Long getLineOfCreditId() {
		return lineOfCreditId;
	}

	public void setLineOfCreditId(Long lineOfCreditId) {
		this.lineOfCreditId = lineOfCreditId;
	}

	

}
