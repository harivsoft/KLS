package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "borrowing_product_gl_mapping")
public class BorrowingProductGlMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "borrowingProductGlIdSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "borrowingProductGlIdSeq", sequenceName = "borrowing_product_gl_mapping_seq_id", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name="pacs_id")
	private Integer pacsId;
	
	@Basic
	@Column(name = "gl_name", length = 45)
	private String glName;
	
	@Basic
	@Column(name = "gl_code", length = 18)
	private String glCode;
	
	@Basic
	@Column(name = "interest_payable_gl_code" ,length=18)
	private String interestpayableGl;
	
	@Basic
	@Column(name = "interest_paid_gl_code", length=18)
	private String interestpaidGl;

	@Basic
	@Column(name ="interst_subsidy", length=18)
	private String interestSubsidyGl;

	@Basic
	@Column(name = "principle_subsidy",length=18)
	private String principleSubsidyGl;
	
	@Basic
	@Column(name = "penal_interest_payable_gl_code",length=18)
	private String penalInterestPayableGlCode;
	
	@Basic
	@Column(name = "penal_interest_paid_gl_code",length=18)
	private String penalInterestPaidGlCode;

	@Column(name = "bank_interest_received_gl",length=18)
	private String bankInterestReceivedGL;
	
	@Basic
	@Column(name = "bank_interest_receivable_gl",length=18)
	private String bankInterestReceivableGL;
	
	@Column(name = "bank_penal_interest_received_gl",length=18)
	private String bankPenalInterestReceivedGL;
	
	@Basic
	@Column(name = "bank_penal_interest_receivable_gl",length=18)
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
