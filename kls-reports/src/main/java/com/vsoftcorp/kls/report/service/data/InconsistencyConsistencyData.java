package com.vsoftcorp.kls.report.service.data;

import java.math.BigDecimal;


public class InconsistencyConsistencyData {

	private String accountNumber;
	
	private Long locId;

	private String businessDate;

	private BigDecimal transactionBalance;
	
	private BigDecimal locBalance;
	
	private BigDecimal difference;
	
	private String memberName;
	
	public BigDecimal getDifference() {
		return difference;
	}
	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		// TODO Auto-generated method stub
		this.accountNumber=accountNumber;
	}
	/*private Long accountId;
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}*/

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public BigDecimal getTransactionBalance() {
		return transactionBalance;
	}

	public void setTransactionBalance(BigDecimal transactionBalance) {
		this.transactionBalance = transactionBalance;
	}

	public BigDecimal getLocBalance() {
		return locBalance;
	}

	public void setLocBalance(BigDecimal locBalance) {
		this.locBalance = locBalance;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/*private Long inconsistencyId;
	public Long getInconsistencyId() {
		return inconsistencyId;
	}

	public void setInconsistencyId(Long inconsistencyId) {
		this.inconsistencyId = inconsistencyId;
	}
*/
	
	
}
