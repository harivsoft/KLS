package com.vsoftcorp.kls.report.service.data;

import java.math.BigDecimal;

public class DCBReportData {

	private String memberNumber;

	private String memberName;

	private String branchName;

	private String frequency;

	private String pacName;

	private String schemeName;

	private String schemeCode;
	
	private String pacsId;
	
	private String branchId;
	
	private String accountNumber;

	private BigDecimal overduePrincipleAsOnFromDate;

	private BigDecimal overdueInterestAsOnFromDate;

	private BigDecimal previousPrincipalOverdueDemand;

	private BigDecimal previousInterestOverdueDemand;

	private BigDecimal toDatePrincipalDemand;

	private BigDecimal toDateInterestDemand;

	private BigDecimal totalPrincipalDemand;

	private BigDecimal totalInterestDemand;

	// this two columns are directly from loc - balance and interest receivable
	private BigDecimal currentPrincipalDemand;

	private BigDecimal currentInterestDemand;

	private BigDecimal overduePrincipalCollection;

	private BigDecimal overdueInterestCollection;

	private BigDecimal currentDemandPrincipalCollection;

	private BigDecimal currentDemandInterestCollection;

	private BigDecimal totalPrincipalCollection;

	private BigDecimal totalInterestCollection;

	private BigDecimal advancePrincipalCollection;

	private BigDecimal advanceInterestCollection;

	private BigDecimal principalBalanceOutstanding;

	private BigDecimal interestBalanceOutstanding;

	private BigDecimal principalOverdueFromArrearsDemand;

	private BigDecimal interestOverdueFromArrearsDemand;

	private BigDecimal principalOverdueFromCurrentDemand;

	private BigDecimal interestOverdueFromCurrentDemand;

	private BigDecimal totalPrincipalOverdue;

	private BigDecimal totalInterestOverdue;
	
	private BigDecimal interestAccrued;
	
	private boolean asOnWhen;

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getPacName() {
		return pacName;
	}

	public void setPacName(String pacName) {
		this.pacName = pacName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public BigDecimal getPreviousPrincipalOverdueDemand() {
		return previousPrincipalOverdueDemand;
	}

	public void setPreviousPrincipalOverdueDemand(
			BigDecimal previousPrincipalOverdueDemand) {
		this.previousPrincipalOverdueDemand = previousPrincipalOverdueDemand;
	}

	public BigDecimal getPreviousInterestOverdueDemand() {
		return previousInterestOverdueDemand;
	}

	public void setPreviousInterestOverdueDemand(
			BigDecimal previousInterestOverdueDemand) {
		this.previousInterestOverdueDemand = previousInterestOverdueDemand;
	}

	public BigDecimal getToDatePrincipalDemand() {
		return toDatePrincipalDemand;
	}

	public void setToDatePrincipalDemand(BigDecimal toDatePrincipalDemand) {
		this.toDatePrincipalDemand = toDatePrincipalDemand;
	}

	public BigDecimal getToDateInterestDemand() {
		return toDateInterestDemand;
	}

	public void setToDateInterestDemand(BigDecimal toDateInterestDemand) {
		this.toDateInterestDemand = toDateInterestDemand;
	}

	public BigDecimal getTotalPrincipalDemand() {
		return totalPrincipalDemand;
	}

	public void setTotalPrincipalDemand(BigDecimal totalPrincipalDemand) {
		this.totalPrincipalDemand = totalPrincipalDemand;
	}

	public BigDecimal getTotalInterestDemand() {
		return totalInterestDemand;
	}

	public void setTotalInterestDemand(BigDecimal totalInterestDemand) {
		this.totalInterestDemand = totalInterestDemand;
	}

	public BigDecimal getCurrentPrincipalDemand() {
		return currentPrincipalDemand;
	}

	public void setCurrentPrincipalDemand(BigDecimal currentPrincipalDemand) {
		this.currentPrincipalDemand = currentPrincipalDemand;
	}

	public BigDecimal getCurrentInterestDemand() {
		return currentInterestDemand;
	}

	public void setCurrentInterestDemand(BigDecimal currentInterestDemand) {
		this.currentInterestDemand = currentInterestDemand;
	}

	public BigDecimal getOverduePrincipalCollection() {
		return overduePrincipalCollection;
	}

	public void setOverduePrincipalCollection(
			BigDecimal overduePrincipalCollection) {
		this.overduePrincipalCollection = overduePrincipalCollection;
	}

	public BigDecimal getOverdueInterestCollection() {
		return overdueInterestCollection;
	}

	public void setOverdueInterestCollection(
			BigDecimal overdueInterestCollection) {
		this.overdueInterestCollection = overdueInterestCollection;
	}

	public BigDecimal getCurrentDemandPrincipalCollection() {
		return currentDemandPrincipalCollection;
	}

	public void setCurrentDemandPrincipalCollection(
			BigDecimal currentDemandPrincipalCollection) {
		this.currentDemandPrincipalCollection = currentDemandPrincipalCollection;
	}

	public BigDecimal getCurrentDemandInterestCollection() {
		return currentDemandInterestCollection;
	}

	public void setCurrentDemandInterestCollection(
			BigDecimal currentDemandInterestCollection) {
		this.currentDemandInterestCollection = currentDemandInterestCollection;
	}

	public BigDecimal getTotalPrincipalCollection() {
		return totalPrincipalCollection;
	}

	public void setTotalPrincipalCollection(BigDecimal totalPrincipalCollection) {
		this.totalPrincipalCollection = totalPrincipalCollection;
	}

	public BigDecimal getTotalInterestCollection() {
		return totalInterestCollection;
	}

	public void setTotalInterestCollection(BigDecimal totalInterestCollection) {
		this.totalInterestCollection = totalInterestCollection;
	}

	public BigDecimal getAdvancePrincipalCollection() {
		return advancePrincipalCollection;
	}

	public void setAdvancePrincipalCollection(
			BigDecimal advancePrincipalCollection) {
		this.advancePrincipalCollection = advancePrincipalCollection;
	}

	public BigDecimal getAdvanceInterestCollection() {
		return advanceInterestCollection;
	}

	public void setAdvanceInterestCollection(
			BigDecimal advanceInterestCollection) {
		this.advanceInterestCollection = advanceInterestCollection;
	}

	public BigDecimal getPrincipalBalanceOutstanding() {
		return principalBalanceOutstanding;
	}

	public void setPrincipalBalanceOutstanding(
			BigDecimal principalBalanceOutstanding) {
		this.principalBalanceOutstanding = principalBalanceOutstanding;
	}

	public BigDecimal getInterestBalanceOutstanding() {
		return interestBalanceOutstanding;
	}

	public void setInterestBalanceOutstanding(
			BigDecimal interestBalanceOutstanding) {
		this.interestBalanceOutstanding = interestBalanceOutstanding;
	}

	public BigDecimal getPrincipalOverdueFromArrearsDemand() {
		return principalOverdueFromArrearsDemand;
	}

	public void setPrincipalOverdueFromArrearsDemand(
			BigDecimal principalOverdueFromArrearsDemand) {
		this.principalOverdueFromArrearsDemand = principalOverdueFromArrearsDemand;
	}

	public BigDecimal getInterestOverdueFromArrearsDemand() {
		return interestOverdueFromArrearsDemand;
	}

	public void setInterestOverdueFromArrearsDemand(
			BigDecimal interestOverdueFromArrearsDemand) {
		this.interestOverdueFromArrearsDemand = interestOverdueFromArrearsDemand;
	}

	public BigDecimal getPrincipalOverdueFromCurrentDemand() {
		return principalOverdueFromCurrentDemand;
	}

	public void setPrincipalOverdueFromCurrentDemand(
			BigDecimal principalOverdueFromCurrentDemand) {
		this.principalOverdueFromCurrentDemand = principalOverdueFromCurrentDemand;
	}

	public BigDecimal getInterestOverdueFromCurrentDemand() {
		return interestOverdueFromCurrentDemand;
	}

	public void setInterestOverdueFromCurrentDemand(
			BigDecimal interestOverdueFromCurrentDemand) {
		this.interestOverdueFromCurrentDemand = interestOverdueFromCurrentDemand;
	}

	public BigDecimal getTotalPrincipalOverdue() {
		return totalPrincipalOverdue;
	}

	public void setTotalPrincipalOverdue(BigDecimal totalPrincipalOverdue) {
		this.totalPrincipalOverdue = totalPrincipalOverdue;
	}

	public BigDecimal getTotalInterestOverdue() {
		return totalInterestOverdue;
	}

	public void setTotalInterestOverdue(BigDecimal totalInterestOverdue) {
		this.totalInterestOverdue = totalInterestOverdue;
	}

	public BigDecimal getOverduePrincipleAsOnFromDate() {
		return overduePrincipleAsOnFromDate;
	}

	public void setOverduePrincipleAsOnFromDate(
			BigDecimal overduePrincipleAsOnFromDate) {
		this.overduePrincipleAsOnFromDate = overduePrincipleAsOnFromDate;
	}

	public BigDecimal getOverdueInterestAsOnFromDate() {
		return overdueInterestAsOnFromDate;
	}

	public void setOverdueInterestAsOnFromDate(
			BigDecimal overdueInterestAsOnFromDate) {
		this.overdueInterestAsOnFromDate = overdueInterestAsOnFromDate;
	}

	public String getPacsId() {
		return pacsId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setPacsId(String pacsId) {
		this.pacsId = pacsId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	
	
	public BigDecimal getInterestAccrued() {
		return interestAccrued;
	}

	public void setInterestAccrued(BigDecimal interestAccrued) {
		this.interestAccrued = interestAccrued;
	}

	public boolean isAsOnWhen() {
		return asOnWhen;
	}

	public void setAsOnWhen(boolean asOnWhen) {
		this.asOnWhen = asOnWhen;
	}

	@Override
	public String toString() {
		return "DCBReportData [memberNumber=" + memberNumber + ", schemeCode=" + schemeCode + ", pacsId=" + pacsId + ", branchId=" + branchId + ", accountNumber=" + accountNumber + "]";
	}
	

}
