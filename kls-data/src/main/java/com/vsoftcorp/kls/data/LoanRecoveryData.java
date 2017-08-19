package com.vsoftcorp.kls.data;

import java.math.BigDecimal;

/**
 * 
 * @author a1623
 * 
 */
public class LoanRecoveryData {
	private Long id;
	private Long locId;
	private String customerNumber;
	private String customerName;
	private String recoverySequence;
	private BigDecimal outstandingBalance;
	private BigDecimal installmentAmount;
	private BigDecimal totalPrincipalReceivable;
	private BigDecimal totalInterestReceivable;
	private BigDecimal totalPenalInterestReceivable;
	private BigDecimal totalChargesReceivable;
	private BigDecimal totalReceivableAmount;
	private BigDecimal amountPaid;
	private BigDecimal principalPaid;
	private BigDecimal interestPaid;
	private BigDecimal penalInterestPaid;
	private BigDecimal chargesPaid;
	private BigDecimal outstandingBalanceAfterPayment;
	private String modeOfPayment;
	private String savingsAccountNumber;
	private String remarks;
	private boolean standAloneStatus;
	private String channelType;
    private Integer status;
    private String memberNumber;
    private Long recoveryEntryId;
    private String rejectionRemarks;
    private String recoveryEntryDate;
    private UserLoginDetailsData loggedInUserDetails;
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
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the recoverySequence
	 */
	public String getRecoverySequence() {
		return recoverySequence;
	}

	/**
	 * @param recoverySequence
	 *            the recoverySequence to set
	 */
	public void setRecoverySequence(String recoverySequence) {
		this.recoverySequence = recoverySequence;
	}

	/**
	 * @return the installmentAmount
	 */
	public BigDecimal getInstallmentAmount() {
		return installmentAmount;
	}

	/**
	 * @param installmentAmount
	 *            the installmentAmount to set
	 */
	public void setInstallmentAmount(BigDecimal installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	/**
	 * @return the totalPrincipalReceivable
	 */
	public BigDecimal getTotalPrincipalReceivable() {
		return totalPrincipalReceivable;
	}

	/**
	 * @param totalPrincipalReceivable
	 *            the totalPrincipalReceivable to set
	 */
	public void setTotalPrincipalReceivable(BigDecimal totalPrincipalReceivable) {
		this.totalPrincipalReceivable = totalPrincipalReceivable;
	}

	/**
	 * @return the totalInterestReceivable
	 */
	public BigDecimal getTotalInterestReceivable() {
		return totalInterestReceivable;
	}

	/**
	 * @param totalInterestReceivable
	 *            the totalInterestReceivable to set
	 */
	public void setTotalInterestReceivable(BigDecimal totalInterestReceivable) {
		this.totalInterestReceivable = totalInterestReceivable;
	}

	/**
	 * @return the totalPenalInterestReceivable
	 */
	public BigDecimal getTotalPenalInterestReceivable() {
		return totalPenalInterestReceivable;
	}

	/**
	 * @param totalPenalInterestReceivable
	 *            the totalPenalInterestReceivable to set
	 */
	public void setTotalPenalInterestReceivable(BigDecimal totalPenalInterestReceivable) {
		this.totalPenalInterestReceivable = totalPenalInterestReceivable;
	}

	/**
	 * @return the totalChargesReceivable
	 */
	public BigDecimal getTotalChargesReceivable() {
		return totalChargesReceivable;
	}

	/**
	 * @param totalChargesReceivable
	 *            the totalChargesReceivable to set
	 */
	public void setTotalChargesReceivable(BigDecimal totalChargesReceivable) {
		this.totalChargesReceivable = totalChargesReceivable;
	}

	/**
	 * @return the totalReceivableAmount
	 */
	public BigDecimal getTotalReceivableAmount() {
		return totalReceivableAmount;
	}

	/**
	 * @param totalReceivableAmount
	 *            the totalReceivableAmount to set
	 */
	public void setTotalReceivableAmount(BigDecimal totalReceivableAmount) {
		this.totalReceivableAmount = totalReceivableAmount;
	}

	/**
	 * @return the amountPaid
	 */
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	/**
	 * @param amountPaid
	 *            the amountPaid to set
	 */
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	/**
	 * @return the principalPaid
	 */
	public BigDecimal getPrincipalPaid() {
		return principalPaid;
	}

	/**
	 * @param principalPaid
	 *            the principalPaid to set
	 */
	public void setPrincipalPaid(BigDecimal principalPaid) {
		this.principalPaid = principalPaid;
	}

	/**
	 * @return the interestPaid
	 */
	public BigDecimal getInterestPaid() {
		return interestPaid;
	}

	/**
	 * @param interestPaid
	 *            the interestPaid to set
	 */
	public void setInterestPaid(BigDecimal interestPaid) {
		this.interestPaid = interestPaid;
	}

	/**
	 * @return the penalInterestPaid
	 */
	public BigDecimal getPenalInterestPaid() {
		return penalInterestPaid;
	}

	/**
	 * @param penalInterestPaid
	 *            the penalInterestPaid to set
	 */
	public void setPenalInterestPaid(BigDecimal penalInterestPaid) {
		this.penalInterestPaid = penalInterestPaid;
	}

	/**
	 * @return the chargesPaid
	 */
	public BigDecimal getChargesPaid() {
		return chargesPaid;
	}

	/**
	 * @param chargesPaid
	 *            the chargesPaid to set
	 */
	public void setChargesPaid(BigDecimal chargesPaid) {
		this.chargesPaid = chargesPaid;
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
	 * @return the outstandingBalanceAfterPayment
	 */
	public BigDecimal getOutstandingBalanceAfterPayment() {
		return outstandingBalanceAfterPayment;
	}

	/**
	 * @param outstandingBalanceAfterPayment
	 *            the outstandingBalanceAfterPayment to set
	 */
	public void setOutstandingBalanceAfterPayment(BigDecimal outstandingBalanceAfterPayment) {
		this.outstandingBalanceAfterPayment = outstandingBalanceAfterPayment;
	}

	/**
	 * @return the modeOfPayment
	 */
	public String getModeOfPayment() {
		return modeOfPayment;
	}

	/**
	 * @param modeOfPayment
	 *            the modeOfPayment to set
	 */
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	/**
	 * @return the locId
	 */
	public Long getLocId() {
		return locId;
	}

	/**
	 * @param locId
	 *            the locId to set
	 */
	public void setLocId(Long locId) {
		this.locId = locId;
	}

	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}

	/**
	 * @param customerNumber
	 *            the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	/**
	 * @return the savingsAccountNumber
	 */
	public String getSavingsAccountNumber() {
		return savingsAccountNumber;
	}

	/**
	 * @param savingsAccountNumber
	 *            the savingsAccountNumber to set
	 */
	public void setSavingsAccountNumber(String savingsAccountNumber) {
		this.savingsAccountNumber = savingsAccountNumber;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the standAloneStatus
	 */
	public boolean getStandAloneStatus() {
		return standAloneStatus;
	}

	/**
	 * @param standAloneStatus the standAloneStatus to set
	 */
	public void setStandAloneStatus(boolean standAloneStatus) {
		this.standAloneStatus = standAloneStatus;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public Long getRecoveryEntryId() {
		return recoveryEntryId;
	}

	public void setRecoveryEntryId(Long recoveryEntryId) {
		this.recoveryEntryId = recoveryEntryId;
	}

	public String getRejectionRemarks() {
		return rejectionRemarks;
	}

	public void setRejectionRemarks(String rejectionRemarks) {
		this.rejectionRemarks = rejectionRemarks;
	}

	public String getRecoveryEntryDate() {
		return recoveryEntryDate;
	}

	public void setRecoveryEntryDate(String recoveryEntryDate) {
		this.recoveryEntryDate = recoveryEntryDate;
	}

	public UserLoginDetailsData getLoggedInUserDetails() {
		return loggedInUserDetails;
	}

	public void setLoggedInUserDetails(UserLoginDetailsData loggedInUserDetails) {
		this.loggedInUserDetails = loggedInUserDetails;
	}
	
}
