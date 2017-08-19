package com.vsoftcorp.kls.data;

import java.io.Serializable;


public class BorrowingProductGlMappingData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer productId;
	private Integer pacsId;
	private String glName;
	private String glCode;
	private String interestpayableGl;
	private String interestpaidGl;
	private String interestSubsidyGl;
	private String principleSubsidyGl;
	private String penalInterestPayableGlCode;
	private String penalInterestPaidGlCode;
	private String bankInterestReceivedGL;
	private String bankInterestReceivableGL;
	private String bankPenalInterestReceivedGL;
	private String bankPenalInterestReceivableGL;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getPacsId() {
		return pacsId;
	}
	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}
	public String getGlName() {
		return glName;
	}
	public void setGlName(String glName) {
		this.glName = glName;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public String getInterestpayableGl() {
		return interestpayableGl;
	}
	public void setInterestpayableGl(String interestpayableGl) {
		this.interestpayableGl = interestpayableGl;
	}
	public String getInterestpaidGl() {
		return interestpaidGl;
	}
	public void setInterestpaidGl(String interestpaidGl) {
		this.interestpaidGl = interestpaidGl;
	}
	public String getInterestSubsidyGl() {
		return interestSubsidyGl;
	}
	public void setInterestSubsidyGl(String interestSubsidyGl) {
		this.interestSubsidyGl = interestSubsidyGl;
	}
	public String getPrincipleSubsidyGl() {
		return principleSubsidyGl;
	}
	public void setPrincipleSubsidyGl(String principleSubsidyGl) {
		this.principleSubsidyGl = principleSubsidyGl;
	}
	public String getPenalInterestPayableGlCode() {
		return penalInterestPayableGlCode;
	}
	public void setPenalInterestPayableGlCode(String penalInterestPayableGlCode) {
		this.penalInterestPayableGlCode = penalInterestPayableGlCode;
	}
	public String getPenalInterestPaidGlCode() {
		return penalInterestPaidGlCode;
	}
	public void setPenalInterestPaidGlCode(String penalInterestPaidGlCode) {
		this.penalInterestPaidGlCode = penalInterestPaidGlCode;
	}
	public String getBankInterestReceivedGL() {
		return bankInterestReceivedGL;
	}
	public void setBankInterestReceivedGL(String bankInterestReceivedGL) {
		this.bankInterestReceivedGL = bankInterestReceivedGL;
	}
	public String getBankInterestReceivableGL() {
		return bankInterestReceivableGL;
	}
	public void setBankInterestReceivableGL(String bankInterestReceivableGL) {
		this.bankInterestReceivableGL = bankInterestReceivableGL;
	}
	public String getBankPenalInterestReceivedGL() {
		return bankPenalInterestReceivedGL;
	}
	public void setBankPenalInterestReceivedGL(String bankPenalInterestReceivedGL) {
		this.bankPenalInterestReceivedGL = bankPenalInterestReceivedGL;
	}
	public String getBankPenalInterestReceivableGL() {
		return bankPenalInterestReceivableGL;
	}
	public void setBankPenalInterestReceivableGL(
			String bankPenalInterestReceivableGL) {
		this.bankPenalInterestReceivableGL = bankPenalInterestReceivableGL;
	}
	
	
	
	
	
}
