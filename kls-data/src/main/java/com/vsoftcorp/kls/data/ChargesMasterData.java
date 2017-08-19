package com.vsoftcorp.kls.data;

public class ChargesMasterData {

	private Long id;
	private String chargesCode;
	private String chargesDescription;
	private String minAmount;
	private String maxAmount;
	private Integer chargesType;
	private String chargesReceivedGL;
	private String chargesReceivableGL;
	private Integer pacsId;
	private String bankChargesReceivedGL;
	private String bankChargesReceivableGL;
	private String bankPercentage;
	
	
	// private String amount;
	// private String remarks;

	public String getBankPercentage() {
		return bankPercentage;
	}

	public void setBankPercentage(String bankPercentage) {
		this.bankPercentage = bankPercentage;
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
	 * @return the chargesCode
	 */
	public String getChargesCode() {
		return chargesCode;
	}

	/**
	 * @param chargesCode
	 *            the chargesCode to set
	 */
	public void setChargesCode(String chargesCode) {
		this.chargesCode = chargesCode;
	}

	/**
	 * @return the chargesDescription
	 */
	public String getChargesDescription() {
		return chargesDescription;
	}

	/**
	 * @param chargesDescription
	 *            the chargesDescription to set
	 */
	public void setChargesDescription(String chargesDescription) {
		this.chargesDescription = chargesDescription;
	}

	/**
	 * @return the minAmount
	 */
	public String getMinAmount() {
		return minAmount;
	}

	/**
	 * @param minAmount
	 *            the minAmount to set
	 */
	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	/**
	 * @return the maxAmount
	 */
	public String getMaxAmount() {
		return maxAmount;
	}

	/**
	 * @param maxAmount
	 *            the maxAmount to set
	 */
	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * @return the chargesType
	 */
	public Integer getChargesType() {
		return chargesType;
	}

	/**
	 * @param chargesType
	 *            the chargesType to set
	 */
	public void setChargesType(Integer chargesType) {
		this.chargesType = chargesType;
	}

	/**
	 * @return the chargesReceivedGL
	 */
	public String getChargesReceivedGL() {
		return chargesReceivedGL;
	}

	/**
	 * @param chargesReceivedGL
	 *            the chargesReceivedGL to set
	 */
	public void setChargesReceivedGL(String chargesReceivedGL) {
		this.chargesReceivedGL = chargesReceivedGL;
	}

	/**
	 * @return the chargesReceivableGL
	 */
	public String getChargesReceivableGL() {
		return chargesReceivableGL;
	}

	/**
	 * @param chargesReceivableGL
	 *            the chargesReceivableGL to set
	 */
	public void setChargesReceivableGL(String chargesReceivableGL) {
		this.chargesReceivableGL = chargesReceivableGL;
	}

	/**
	 * @return the pacsId
	 */
	public Integer getPacsId() {
		return pacsId;
	}

	/**
	 * @param pacsId the pacsId to set
	 */
	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	public String getBankChargesReceivedGL() {
		return bankChargesReceivedGL;
	}

	public void setBankChargesReceivedGL(String bankChargesReceivedGL) {
		this.bankChargesReceivedGL = bankChargesReceivedGL;
	}

	public String getBankChargesReceivableGL() {
		return bankChargesReceivableGL;
	}

	public void setBankChargesReceivableGL(String bankChargesReceivableGL) {
		this.bankChargesReceivableGL = bankChargesReceivableGL;
	}
	
	/**
	 * @return the amount
	 * 
	 *         public String getAmount() { return amount; }
	 * 
	 *         /**
	 * @param amount
	 *            the amount to set
	 * 
	 *            public void setAmount(String amount) { this.amount = amount; }
	 * 
	 *            /**
	 * @return the remarks
	 * 
	 *         public String getRemarks() { return remarks; }
	 * 
	 *         /**
	 * @param remarks
	 *            the remarks to set
	 * 
	 *            public void setRemarks(String remarks) { this.remarks =
	 *            remarks; }
	 */
}
