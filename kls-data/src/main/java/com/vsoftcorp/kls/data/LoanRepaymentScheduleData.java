/**
 * 
 */
package com.vsoftcorp.kls.data;

/**
 * @author a9152
 * 
 */
public class LoanRepaymentScheduleData {

	private String installmentDate;

	private String contributionAmount;

	private String principalAmount;

	private String interestAmount;

	private String loanOutstandingAmount;

	private Long lineOfCreditId;

	private Integer installmentNumber;

	/**
	 * @return the installmentDate
	 */
	public String getInstallmentDate() {
		return installmentDate;
	}

	/**
	 * @param installmentDate
	 *            the installmentDate to set
	 */
	public void setInstallmentDate(String installmentDate) {
		this.installmentDate = installmentDate;
	}

	/**
	 * @return the contributionAmount
	 */
	public String getContributionAmount() {
		return contributionAmount;
	}

	/**
	 * @param contributionAmount
	 *            the contributionAmount to set
	 */
	public void setContributionAmount(String contributionAmount) {
		this.contributionAmount = contributionAmount;
	}

	/**
	 * @return the principalAmount
	 */
	public String getPrincipalAmount() {
		return principalAmount;
	}

	/**
	 * @param principalAmount
	 *            the principalAmount to set
	 */
	public void setPrincipalAmount(String principalAmount) {
		this.principalAmount = principalAmount;
	}

	/**
	 * @return the interestAmount
	 */
	public String getInterestAmount() {
		return interestAmount;
	}

	/**
	 * @param interestAmount
	 *            the interestAmount to set
	 */
	public void setInterestAmount(String interestAmount) {
		this.interestAmount = interestAmount;
	}

	/**
	 * @return the loanOutstandingAmount
	 */
	public String getLoanOutstandingAmount() {
		return loanOutstandingAmount;
	}

	/**
	 * @param loanOutstandingAmount
	 *            the loanOutstandingAmount to set
	 */
	public void setLoanOutstandingAmount(String loanOutstandingAmount) {
		this.loanOutstandingAmount = loanOutstandingAmount;
	}

	/**
	 * @return the lineOfCreditId
	 */
	public Long getLineOfCreditId() {
		return lineOfCreditId;
	}

	/**
	 * @param lineOfCreditId
	 *            the lineOfCreditId to set
	 */
	public void setLineOfCreditId(Long lineOfCreditId) {
		this.lineOfCreditId = lineOfCreditId;
	}

	/**
	 * @return the installmentNumber
	 */
	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	/**
	 * @param installmentNumber
	 *            the installmentNumber to set
	 */
	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}
}
