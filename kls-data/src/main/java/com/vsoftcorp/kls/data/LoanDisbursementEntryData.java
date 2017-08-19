package com.vsoftcorp.kls.data;

import java.math.BigDecimal;


/**
 * 
 * @author a1623
 * 
 */

public class LoanDisbursementEntryData {
	private Long id;
	private Long locId;
	private Integer pacsId;
	private String customerNumber;
	private String customerName;
	private String amountDisbursed;
	private String accountNumber;
	private String modeOfDisbursement;
	private String modeOfDisbursed;
	private String toAccountNumber;
	private String pacsSuvikasVoucherNumber;
	private String pacsSuvikasVoucherDate;
	private String pacsSuvikasVoucherAmount;
	private String deductFrom;
	private String chequeDate;
	private String chequeNumber;
	private Integer status;
	private String remarks;
	private BigDecimal disbursementAmount;
	private String loanType;
	private String number;
	private String date;
	private String productName;
	private BigDecimal transferedAmount;
	private BigDecimal insuranceAmount;
	private BigDecimal shareAmount;
	private String businessDate;
	private String voucherNumber;
	private String dateOfDisbursement;
	private String cardStatus;
	// adding new field
	
	private String instrumentNumber;
	private String additionalInformation;
	private BigDecimal totalDisburseAmt;
	
	//Added by Silpa for IR-71
	private String installmentDate;
	private String loanExpiryDate;
	private String installmentFrequency;
	private String noOfInstallments;

	public String getInstallmentFrequency() {
		return installmentFrequency;
	}

	public void setInstallmentFrequency(String installmentFrequency) {
		this.installmentFrequency = installmentFrequency;
	}

	public String getNoOfInstallments() {
		return noOfInstallments;
	}

	public void setNoOfInstallments(String noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	public String getLoanExpiryDate() {
		return loanExpiryDate;
	}

	public void setLoanExpiryDate(String loanExpiryDate) {
		this.loanExpiryDate = loanExpiryDate;
	}

	public String getInstallmentDate() {
		return installmentDate;
	}

	public void setInstallmentDate(String installmentDate) {
		this.installmentDate = installmentDate;
	}
	

	public String getInstrumentNumber() {
		return instrumentNumber;
	}

	public void setInstrumentNumber(String instrumentNumber) {
		this.instrumentNumber = instrumentNumber;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public String getModeOfDisbursed() {
		return modeOfDisbursed;
	}

	public void setModeOfDisbursed(String modeOfDisbursed) {
		this.modeOfDisbursed = modeOfDisbursed;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(BigDecimal shareAmount) {
		this.shareAmount = shareAmount;
	}

	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
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
	 * @param customerNumber
	 *            the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the amountDisbursed
	 */
	public String getAmountDisbursed() {
		return amountDisbursed;
	}

	/**
	 * @param amountDisbursed
	 *            the amountDisbursed to set
	 */
	public void setAmountDisbursed(String amountDisbursed) {
		this.amountDisbursed = amountDisbursed;
	}

	/**
	 * @return the toAccountNumber
	 */
	public String getToAccountNumber() {
		return toAccountNumber;
	}

	/**
	 * @param toAccountNumber
	 *            the toAccountNumber to set
	 */
	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	/**
	 * @return the pacsSuvikasVoucherNumber
	 */
	public String getPacsSuvikasVoucherNumber() {
		return pacsSuvikasVoucherNumber;
	}

	/**
	 * @param pacsSuvikasVoucherNumber
	 *            the pacsSuvikasVoucherNumber to set
	 */
	public void setPacsSuvikasVoucherNumber(String pacsSuvikasVoucherNumber) {
		this.pacsSuvikasVoucherNumber = pacsSuvikasVoucherNumber;
	}

	/**
	 * @return the pacsSuvikasVoucherDate
	 */
	public String getPacsSuvikasVoucherDate() {
		return pacsSuvikasVoucherDate;
	}

	/**
	 * @param pacsSuvikasVoucherDate
	 *            the pacsSuvikasVoucherDate to set
	 */
	public void setPacsSuvikasVoucherDate(String pacsSuvikasVoucherDate) {
		this.pacsSuvikasVoucherDate = pacsSuvikasVoucherDate;
	}

	/**
	 * @return the pacsSuvikasVoucherAmount
	 */
	public String getPacsSuvikasVoucherAmount() {
		return pacsSuvikasVoucherAmount;
	}

	/**
	 * @param pacsSuvikasVoucherAmount
	 *            the pacsSuvikasVoucherAmount to set
	 */
	public void setPacsSuvikasVoucherAmount(String pacsSuvikasVoucherAmount) {
		this.pacsSuvikasVoucherAmount = pacsSuvikasVoucherAmount;
	}

	/**
	 * @return the deductFrom
	 */
	public String getDeductFrom() {
		return deductFrom;
	}

	/**
	 * @param deductFrom
	 *            the deductFrom to set
	 */
	public void setDeductFrom(String deductFrom) {
		this.deductFrom = deductFrom;
	}

	/**
	 * @return the chequeDate
	 */
	public String getChequeDate() {
		return chequeDate;
	}

	/**
	 * @param chequeDate
	 *            the chequeDate to set
	 */
	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	/**
	 * @return the chequeNumber
	 */
	public String getChequeNumber() {
		return chequeNumber;
	}

	/**
	 * @param chequeNumber
	 *            the chequeNumber to set
	 */
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @return the pacsId
	 */
	public Integer getPacsId() {
		return pacsId;
	}

	/**
	 * @param pacsId
	 *            the pacsId to set
	 */
	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
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
	 * @return the disbursementAmount
	 */
	public BigDecimal getDisbursementAmount() {
		return disbursementAmount;
	}

	/**
	 * @param disbursementAmount
	 *            the disbursementAmount to set
	 */
	public void setDisbursementAmount(BigDecimal disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}

	/**
	 * @return the modeOfDisbursement
	 */
	public String getModeOfDisbursement() {
		return modeOfDisbursement;
	}

	/**
	 * @param modeOfDisbursement
	 *            the modeOfDisbursement to set
	 */
	public void setModeOfDisbursement(String modeOfDisbursement) {
		this.modeOfDisbursement = modeOfDisbursement;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the loanType
	 */
	public String getLoanType() {
		return loanType;
	}

	/**
	 * @param loanType
	 *            the loanType to set
	 */
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the transferedAmount
	 */
	public BigDecimal getTransferedAmount() {
		return transferedAmount;
	}

	/**
	 * @param transferedAmount
	 *            the transferedAmount to set
	 */
	public void setTransferedAmount(BigDecimal transferedAmount) {
		this.transferedAmount = transferedAmount;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}


	public String getDateOfDisbursement() {
		return dateOfDisbursement;
	}

	public void setDateOfDisbursement(String dateOfDisbursement) {
		this.dateOfDisbursement = dateOfDisbursement;
	}

	public BigDecimal getTotalDisburseAmt() {
		return totalDisburseAmt;
	}

	public void setTotalDisburseAmt(BigDecimal totalDisburseAmt) {
		this.totalDisburseAmt = totalDisburseAmt;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}


	

}
