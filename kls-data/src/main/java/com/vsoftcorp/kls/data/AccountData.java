package com.vsoftcorp.kls.data;

public class AccountData {
	
	String accountNo;
	
	Long accountId;
	
	String savingBankAccNo;
	
	String errorMssg = "";



	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getSavingBankAccNo() {
		return savingBankAccNo;
	}

	public void setSavingBankAccNo(String savingBankAccNo) {
		this.savingBankAccNo = savingBankAccNo;
	}

	public String getErrorMssg() {
		return errorMssg;
	}

	public void setErrorMssg(String errorMssg) {
		this.errorMssg = errorMssg;
	}
	
}
