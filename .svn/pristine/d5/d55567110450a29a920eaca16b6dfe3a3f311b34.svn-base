package com.vsoftcorp.kls.business.entity.transaction;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.time.Date;

/***
@a1565
**/

@TypeDefs({
	@TypeDef(name = "accountingMoney", typeClass = com.vsoftcorp.accounting.hibernate.types.AccountingMoneyUserType.class, parameters = {
			@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
			@Parameter(name = "isDebitNegative", value = "true") }),
	@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
	@Entity
	@Table(name = "inconsistency_audit_trail")
public class AccountWiseConsistency implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "inconsistencyAuditTrailIdSeq", sequenceName = "inconsistency_audit_trail_id_seq", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inconsistencyAuditTrailIdSeq")
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "line_of_credit_id", referencedColumnName = "id")
	private LineOfCredit lineOfCredit;
	
	@Basic
	@Column(name = "business_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date businessDate;

	@Basic
	@Column(name = "transaction_balance", precision = 22, scale = 6)
	@Type(type = "accountingMoney")
	private AccountingMoney transactionBalance;
	
	@Basic
	@Column(name = "loc_balance", precision = 22, scale = 6)
	@Type(type = "accountingMoney")
	private AccountingMoney lOCBalance;
	
	@Basic
	@Column(name = "status", length = 1)
	private String consistencyStatus;
	
	public String getConsistencyStatus() {
		return consistencyStatus;
	}

	public void setConsistencyStatus(String consistencyStatus) {
		this.consistencyStatus = consistencyStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public LineOfCredit getLineOfCredit() {
		return lineOfCredit;
	}

	public void setLineOfCredit(LineOfCredit lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public AccountingMoney getTransactionBalance() {
		return transactionBalance;
	}

	public void setTransactionBalance(AccountingMoney transactionBalance) {
		this.transactionBalance = transactionBalance;
	}

	public AccountingMoney getlOCBalance() {
		return lOCBalance;
	}

	public void setlOCBalance(AccountingMoney lOCBalance) {
		this.lOCBalance = lOCBalance;
	}
	
}
