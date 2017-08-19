package com.vsoftcorp.kls.data;

import java.io.Serializable;

/**
 * 
 * @author a1565
 *
 */
@SuppressWarnings("serial")
public class ChequeTransactionData implements Serializable {
	
	private String transactionAmount;

	private String transactionMode;

	private String transactionType; 

	private String debitGL;
	
	private String creditGL;

	private String debitAccountNumber;

	private String creditAccountNumber;

	private String batchCode;
	
	private String channelType;
	
	private String chequeNo;
	
	private String chequeDate;
	
	private String branchId;
	
	private String remarks;
	
	private String currentDate;
	
	private String pacsLoanAccount;
	
	private String pacsSavingsBankAccNo;
	
	private String farmerSavingsBankAccNo;
	
	private String ddNumber;

	private String ddProductCode;

	private String ddDate;
	
	private String instrumentType;
	
	private String dedctionChargesAccountNumber;
	
	private String dedctionCharges;
	
	private String deductFrom;
	
	private String userName;

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

	
	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}

	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}

	public String getCreditAccountNumber() {
		return creditAccountNumber;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setCreditAccountNumber(String creditAccountNumber) {
		this.creditAccountNumber = creditAccountNumber;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getDebitGL() {
		return debitGL;
	}

	public void setDebitGL(String debitGL) {
		this.debitGL = debitGL;
	}

	public String getCreditGL() {
		return creditGL;
	}

	public void setCreditGL(String creditGL) {
		this.creditGL = creditGL;
	}

	public String getPacsLoanAccount() {
		return pacsLoanAccount;
	}

	public void setPacsLoanAccount(String pacsLoanAccount) {
		this.pacsLoanAccount = pacsLoanAccount;
	}

	public String getPacsSavingsBankAccNo() {
		return pacsSavingsBankAccNo;
	}

	public void setPacsSavingsBankAccNo(String pacsSavingsBankAccNo) {
		this.pacsSavingsBankAccNo = pacsSavingsBankAccNo;
	}

	public String getFarmerSavingsBankAccNo() {
		return farmerSavingsBankAccNo;
	}

	public void setFarmerSavingsBankAccNo(String farmerSavingsBankAccNo) {
		this.farmerSavingsBankAccNo = farmerSavingsBankAccNo;
	}

	public String getDdNumber() {
		return ddNumber;
	}

	public void setDdNumber(String ddNumber) {
		this.ddNumber = ddNumber;
	}

	public String getDdProductCode() {
		return ddProductCode;
	}

	public void setDdProductCode(String ddProductCode) {
		this.ddProductCode = ddProductCode;
	}

	public String getDdDate() {
		return ddDate;
	}

	public void setDdDate(String ddDate) {
		this.ddDate = ddDate;
	}

	public String getInstrumentType() {
		return instrumentType;
	}

	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}

	public String getDedctionChargesAccountNumber() {
		return dedctionChargesAccountNumber;
	}

	public void setDedctionChargesAccountNumber(String dedctionChargesAccountNumber) {
		this.dedctionChargesAccountNumber = dedctionChargesAccountNumber;
	}

	public String getDedctionCharges() {
		return dedctionCharges;
	}

	public void setDedctionCharges(String dedctionCharges) {
		this.dedctionCharges = dedctionCharges;
	}

	public String getDeductFrom() {
		return deductFrom;
	}

	public void setDeductFrom(String deductFrom) {
		this.deductFrom = deductFrom;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
	
}
