/**
 * 
 */
package com.vsoftcorp.kls.data;

import java.math.BigDecimal;

/**
 * @author a9152
 * 
 */
public class LoanLineOfCreditData {

	private Long id;// LocId

	private String accountNumber;

	private String sanctionDate;

	private String sanctionAmount;

	private String firstDueDate;

	private String loanExpiryDate;

	private String shareAmountToBeDeducted;

	private String insuranceAmount;

	private Long loanApplicationId;

	private LoanAccountPropertyData loanAccountPropertyData;

	private String processingFee;

	private String interestCategoryDesc;

	private Integer interestCategoryId;

	private String rateOfInterest;

	private String penalInterest;

	private String installmentAmount;

	private LoanAccountClosureData closureData;

	private BigDecimal interestReceivable;

	private BigDecimal outstandingBalance;

	private BigDecimal overdueAmount;

	private Boolean isDisbursed;
	
	//Produt type MT/LT/ST for OMNI 
	private String productType;

	/**
	 * @return the shareAmountToBeDeducted
	 */
	public String getShareAmountToBeDeducted() {
		return shareAmountToBeDeducted;
	}

	/**
	 * @param shareAmountToBeDeducted
	 *            the shareAmountToBeDeducted to set
	 */
	public void setShareAmountToBeDeducted(String shareAmountToBeDeducted) {
		this.shareAmountToBeDeducted = shareAmountToBeDeducted;
	}

	/**
	 * @return the closureData
	 */
	public LoanAccountClosureData getClosureData() {
		return closureData;
	}

	/**
	 * @param closureData
	 *            the closureData to set
	 */
	public void setClosureData(LoanAccountClosureData closureData) {
		this.closureData = closureData;
	}

	/**
	 * @return the sanctionDate
	 */
	public String getSanctionDate() {
		return sanctionDate;
	}

	/**
	 * @param sanctionDate
	 *            the sanctionDate to set
	 */
	public void setSanctionDate(String sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	/**
	 * @return the sanctionAmount
	 */
	public String getSanctionAmount() {
		return sanctionAmount;
	}

	/**
	 * @param sanctionAmount
	 *            the sanctionAmount to set
	 */
	public void setSanctionAmount(String sanctionAmount) {
		this.sanctionAmount = sanctionAmount;
	}

	/**
	 * @return the firstDueDate
	 */
	public String getFirstDueDate() {
		return firstDueDate;
	}

	/**
	 * @param firstDueDate
	 *            the firstDueDate to set
	 */
	public void setFirstDueDate(String firstDueDate) {
		this.firstDueDate = firstDueDate;
	}

	/**
	 * @return the loanExpiryDate
	 */
	public String getLoanExpiryDate() {
		return loanExpiryDate;
	}

	/**
	 * @param loanExpiryDate
	 *            the loanExpiryDate to set
	 */
	public void setLoanExpiryDate(String loanExpiryDate) {
		this.loanExpiryDate = loanExpiryDate;
	}

	/**
	 * @return the shareAmount
	 */
	public String getShareAmount() {
		return shareAmountToBeDeducted;
	}

	/**
	 * @param shareAmount
	 *            the shareAmount to set
	 */
	public void setShareAmount(String shareAmount) {
		this.shareAmountToBeDeducted = shareAmount;
	}

	/**
	 * @return the insuranceAmount
	 */
	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * @param insuranceAmount
	 *            the insuranceAmount to set
	 */
	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the loanAccountPropertyData
	 */
	public LoanAccountPropertyData getLoanAccountPropertyData() {
		return loanAccountPropertyData;
	}

	/**
	 * @param loanAccountPropertyData
	 *            the loanAccountPropertyData to set
	 */
	public void setLoanAccountPropertyData(LoanAccountPropertyData loanAccountPropertyData) {
		this.loanAccountPropertyData = loanAccountPropertyData;
	}

	/**
	 * @return the loanApplicationId
	 */
	public Long getLoanApplicationId() {
		return loanApplicationId;
	}

	/**
	 * @param loanApplicationId
	 *            the loanApplicationId to set
	 */
	public void setLoanApplicationId(Long loanApplicationId) {
		this.loanApplicationId = loanApplicationId;
	}

	/**
	 * @return the processingFee
	 */
	public String getProcessingFee() {
		return processingFee;
	}

	/**
	 * @param processingFee
	 *            the processingFee to set
	 */
	public void setProcessingFee(String processingFee) {
		this.processingFee = processingFee;
	}

	/**
	 * @return the interestCategoryDesc
	 */
	public String getInterestCategoryDesc() {
		return interestCategoryDesc;
	}

	/**
	 * @param interestCategoryDesc
	 *            the interestCategoryDesc to set
	 */
	public void setInterestCategoryDesc(String interestCategoryDesc) {
		this.interestCategoryDesc = interestCategoryDesc;
	}

	/**
	 * @return the interestCategoryId
	 */
	public Integer getInterestCategoryId() {
		return interestCategoryId;
	}

	/**
	 * @param interestCategoryId
	 *            the interestCategoryId to set
	 */
	public void setInterestCategoryId(Integer interestCategoryId) {
		this.interestCategoryId = interestCategoryId;
	}

	/**
	 * @return the rateOfInterest
	 */
	public String getRateOfInterest() {
		return rateOfInterest;
	}

	/**
	 * @param rateOfInterest
	 *            the rateOfInterest to set
	 */
	public void setRateOfInterest(String rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	/**
	 * @return the penalInterest
	 */
	public String getPenalInterest() {
		return penalInterest;
	}

	/**
	 * @param penalInterest
	 *            the penalInterest to set
	 */
	public void setPenalInterest(String penalInterest) {
		this.penalInterest = penalInterest;
	}

	/**
	 * @return the installmentAmount
	 */
	public String getInstallmentAmount() {
		return installmentAmount;
	}

	/**
	 * @param installmentAmount
	 *            the installmentAmount to set
	 */
	public void setInstallmentAmount(String installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	/**
	 * @return the interestReceivable
	 */
	public BigDecimal getInterestReceivable() {
		return interestReceivable;
	}

	/**
	 * @param interestReceivable
	 *            the interestReceivable to set
	 */
	public void setInterestReceivable(BigDecimal interestReceivable) {
		this.interestReceivable = interestReceivable;
	}

	/**
	 * @return the outstandingBalance
	 */
	public BigDecimal getOutstandingBalance() {
		return outstandingBalance;
	}

	/**
	 * @param outstandingBalance
	 *            the outstandingBalance to set
	 */
	public void setOutstandingBalance(BigDecimal outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	/**
	 * @return the overdueAmount
	 */
	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	/**
	 * @param overdueAmount
	 *            the overdueAmount to set
	 */
	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	/**
	 * @return the isDisbursed
	 */
	public Boolean getIsDisbursed() {
		return isDisbursed;
	}

	/**
	 * @param isDisbursed
	 *            the isDisbursed to set
	 */
	public void setIsDisbursed(Boolean isDisbursed) {
		this.isDisbursed = isDisbursed;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}
