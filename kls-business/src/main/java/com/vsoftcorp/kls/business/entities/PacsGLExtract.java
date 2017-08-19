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

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.time.Date;


/**
 * 
 * @author a1605
 * 
 * This Entity class maps to pacs_gl_entry table of pacs_suvikas db
 *
 */
@TypeDefs({
		@TypeDef(name = "accountingMoney", typeClass = com.vsoftcorp.accounting.hibernate.types.AccountingMoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"), @Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "pacs_gl_extract")
public class PacsGLExtract implements Serializable {

	private static final long serialVersionUID = -7561752101720603519L;

	public PacsGLExtract() {
	}

	@Id
	@GeneratedValue(generator = "pacsGLExtarctIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "pacsGLExtarctIdSequence", sequenceName = "pacsGLExtarct_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "gl_acc_no", length = 18)
	private String accountNo;

	@Basic
	@Column(name = "trans_amt", precision = 22, scale = 6)
	@Type(type = "accountingMoney")
	private AccountingMoney transactionAmount;

	@Basic
	@Column(name = "crdr")
	private Integer crDr;

	@Basic
	@Column(name = "trans_type", length = 1)
	private String transType;

	@Basic
	@Column(name = "pacs_id")
	private Integer pacs_id;

	@Basic
	@Column(name = "branch_id")
	private Integer branch_id;

	@Basic
	@Column(name = "business_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date businessDate;

	@Basic
	@Column(name = "process_status")
	private String processStatus="N";

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the transactionAmount
	 */
	public AccountingMoney getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(AccountingMoney transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return the crDr
	 */
	public Integer getCrDr() {
		return crDr;
	}

	/**
	 * @param crDr the crDr to set
	 */
	public void setCrDr(Integer crDr) {
		this.crDr = crDr;
	}

	/**
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * @return the pacs_id
	 */
	public Integer getPacs_id() {
		return pacs_id;
	}

	/**
	 * @param pacs_id the pacs_id to set
	 */
	public void setPacs_id(Integer pacs_id) {
		this.pacs_id = pacs_id;
	}

	/**
	 * @return the branch_id
	 */
	public Integer getBranch_id() {
		return branch_id;
	}

	/**
	 * @param branch_id the branch_id to set
	 */
	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}

	/**
	 * @return the businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the processStatus
	 */
	public String isProcessStatus() {
		return processStatus;
	}

	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	
	
}