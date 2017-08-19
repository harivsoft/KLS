package com.vsoftcorp.kls.report.service.data;

import java.math.BigDecimal;

public class InterestChargedReportData {
	
	private Integer sno;
	private String memberName;
	private String memberCode;
	private Integer loanAccountNumber;
	private BigDecimal outStandingAmount;
	private BigDecimal interestCharged;
	private BigDecimal balance;
	private String productName;
	private String purposeName;
	private Integer productId;
	private Integer purposeId;
	
	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public Integer getLoanAccountNumber() {
		return loanAccountNumber;
	}
	public void setLoanAccountNumber(Integer loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
	}
	public BigDecimal getOutStandingAmount() {
		return outStandingAmount;
	}
	public void setOutStandingAmount(BigDecimal outStandingAmount) {
		this.outStandingAmount = outStandingAmount;
	}
	public BigDecimal getInterestCharged() {
		return interestCharged;
	}
	public void setInterestCharged(BigDecimal interestCharged) {
		this.interestCharged = interestCharged;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPurposeName() {
		return purposeName;
	}
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(Integer purposeId) {
		this.purposeId = purposeId;
	}
	
}
