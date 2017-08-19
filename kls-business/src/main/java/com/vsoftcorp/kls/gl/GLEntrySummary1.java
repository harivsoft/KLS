package com.vsoftcorp.kls.gl;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.time.Date;

public class GLEntrySummary1 {

	private String productId;

	private String institutionId;

	private String branchId;

	private String accountType;

	private String productTypeId;

	private String accountNumber;

	private String trancode;

	private AccountingMoney transactionAmount;

	private Date effectiveDate;

	private Date postingDate;

	private String postStatus;

	private String sourceType;

	private String description;

	private String monetaryComponentId;

	private String loanStatus;

	private Long transactionId;

	private String fileName;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

	public AccountingMoney getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(AccountingMoney transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Date getEffectivateDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public String getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMonetaryComponentId() {
		return monetaryComponentId;
	}

	public void setMonetaryComponentId(String monetaryComponentId) {
		this.monetaryComponentId = monetaryComponentId;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
