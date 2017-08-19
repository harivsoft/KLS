package com.vsoftcorp.kls.data;

public class ChargesDebitItem {
	private Long id;
	private ChargesMasterData chargesMasterData;
	private String amount;
	private String remarks;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the chargesMasterData
	 */
	public ChargesMasterData getChargesMasterData() {
		return chargesMasterData;
	}
	/**
	 * @param chargesMasterData the chargesMasterData to set
	 */
	public void setChargesMasterData(ChargesMasterData chargesMasterData) {
		this.chargesMasterData = chargesMasterData;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
