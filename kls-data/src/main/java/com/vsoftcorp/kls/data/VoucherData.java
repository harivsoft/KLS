package com.vsoftcorp.kls.data;

public class VoucherData {
	
	private String voucherNumber;
	private String businessDate;
	private Integer crdr;
	private String accountNumber;
	private String description;
	private String amount;
	public String getVoucherNumber() {
		return voucherNumber;
	}
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public Integer getCrdr() {
		return crdr;
	}
	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
