package com.vsoftcorp.kls.data;

public class CurrentTransactionData {

	private String id;

	private String voucherNumber;

	private String transactionType;

	private String transactionTowards;

	private String openingBalance;

	private String transactionAmount;

	private String crDr;

	private String accountId;

	private String accountNumber;

	private String pacsId;

	private String businessDate;

	private String channelType;

	private String lineOfCreditId;

	private String savingsAccountNumber;

	private String remarks;
	
	private String terminalId;
	
	private boolean standAloneStatus;
	
	private String bulkRecovery;
	
	private String modeOfPayment;
	
	private boolean bulkRecoveryFromEntry;
	
	private String partyId;
	
    private String bulkDisbursement;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionTowards() {
		return transactionTowards;
	}

	public void setTransactionTowards(String transactionTowards) {
		this.transactionTowards = transactionTowards;
	}

	public String getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getCrDr() {
		return crDr;
	}

	public void setCrDr(String crDr) {
		this.crDr = crDr;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPacsId() {
		return pacsId;
	}

	public void setPacsId(String pacsId) {
		this.pacsId = pacsId;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}

	/**
	 * @param channelType
	 *            the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * @return the lineOfCreditId
	 */
	public String getLineOfCreditId() {
		return lineOfCreditId;
	}

	/**
	 * @param lineOfCreditId
	 *            the lineOfCreditId to set
	 */
	public void setLineOfCreditId(String lineOfCreditId) {
		this.lineOfCreditId = lineOfCreditId;
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

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the standAloneStatus
	 */
	public boolean isStandAloneStatus() {
		return standAloneStatus;
	}

	/**
	 * @param standAloneStatus the standAloneStatus to set
	 */
	public void setStandAloneStatus(boolean standAloneStatus) {
		this.standAloneStatus = standAloneStatus;
	}

	public String getBulkRecovery() {
		return bulkRecovery;
	}

	public void setBulkRecovery(String bulkRecovery) {
		this.bulkRecovery = bulkRecovery;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public boolean isBulkRecoveryFromEntry() {
		return bulkRecoveryFromEntry;
	}

	public void setBulkRecoveryFromEntry(boolean bulkRecoveryFromEntry) {
		this.bulkRecoveryFromEntry = bulkRecoveryFromEntry;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getBulkDisbursement() {
		return bulkDisbursement;
	}

	public void setBulkDisbursement(String bulkDisbursement) {
		this.bulkDisbursement = bulkDisbursement;
	}
	
	
}
