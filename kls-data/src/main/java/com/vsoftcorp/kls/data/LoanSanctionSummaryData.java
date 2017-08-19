package com.vsoftcorp.kls.data;

public class LoanSanctionSummaryData {

	private LoanSummaryData sanctionSummary;
	private LoanSummaryData pendingSummary;

	public LoanSummaryData getSanctionSummary() {
		return sanctionSummary;
	}

	public void setSanctionSummary(LoanSummaryData sanctionSummary) {
		this.sanctionSummary = sanctionSummary;
	}

	public LoanSummaryData getPendingSummary() {
		return pendingSummary;
	}

	public void setPendingSummary(LoanSummaryData pendingSummary) {
		this.pendingSummary = pendingSummary;
	}

}
