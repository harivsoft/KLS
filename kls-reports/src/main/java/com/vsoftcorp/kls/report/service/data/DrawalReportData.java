package com.vsoftcorp.kls.report.service.data;

import java.math.BigDecimal;

public class DrawalReportData {


	private String customerId;

	private String customerName;

	private String accountNumber;

	private String locNo;

	private String schemeId;

	private String schemeName;

	private String seasonName;

	private String cropName;

	private String sanctionedDate;

	private BigDecimal sanctionedAmount;

	private BigDecimal amountDrawan;
	
	private BigDecimal amountCollected;

	private BigDecimal accountBalance;

	public String getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getLocNo() {
		return locNo;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public String getCropName() {
		return cropName;
	}

	public String getSanctionedDate() {
		return sanctionedDate;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setLocNo(String locNo) {
		this.locNo = locNo;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String shemeName) {
		this.schemeName = shemeName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public void setSanctionedDate(String sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}

	public BigDecimal getSanctionedAmount() {
		return sanctionedAmount;
	}

	public BigDecimal getAmountDrawan() {
		return amountDrawan;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setSanctionedAmount(BigDecimal sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public void setAmountDrawan(BigDecimal amountDrawan) {
		this.amountDrawan = amountDrawan;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public BigDecimal getAmountCollected() {
		return amountCollected;
	}

	public void setAmountCollected(BigDecimal amountCollected) {
		this.amountCollected = amountCollected;
	}

}
