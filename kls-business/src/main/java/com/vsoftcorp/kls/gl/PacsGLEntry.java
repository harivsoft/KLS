package com.vsoftcorp.kls.gl;

import com.vsoftcorp.accounting.types.AccountingMoney;

public class PacsGLEntry {
	
	private String accountNo;
	
	private AccountingMoney transactionAmount;
	
	private Integer crDr;
	
	private String transType;
	
	private String remarks;

	public String getAccountNo() {
		return accountNo;
	}

	public AccountingMoney getTransactionAmount() {
		return transactionAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNo == null) ? 0 : accountNo.hashCode());
		result = prime * result + ((crDr == null) ? 0 : crDr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PacsGLEntry other = (PacsGLEntry) obj;
		if (accountNo == null) {
			if (other.accountNo != null)
				return false;
		} else if (!accountNo.equals(other.accountNo))
			return false;
		if (crDr == null) {
			if (other.crDr != null)
				return false;
		} else if (!crDr.equals(other.crDr))
			return false;
		return true;
	}

	


	public Integer getCrDr() {
		return crDr;
	}

	public void setCrDr(Integer crDr) {
		this.crDr = crDr;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public void setTransactionAmount(AccountingMoney transactionAmount) {
		this.transactionAmount = transactionAmount;
	}


	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
