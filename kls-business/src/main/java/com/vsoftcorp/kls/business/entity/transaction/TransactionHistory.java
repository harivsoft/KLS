package com.vsoftcorp.kls.business.entity.transaction;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

/**
 * @author sponnam
 *
 */
@TypeDefs({
		@TypeDef(name = "transactionCode", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.transaction.TransactionCode"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "channelType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.transaction.ChannelType"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "transactionType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.transaction.TransactionType"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "accountingMoney", typeClass = com.vsoftcorp.accounting.hibernate.types.AccountingMoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "transaction_history")
public class TransactionHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "txnHistoryIdSeq", sequenceName = "txn_history_id_seq", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "txnHistoryIdSeq")
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "voucher_number")
	private String voucherNumber;

	@Basic
	@Column(name = "trans_type", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "transactionType")
	private TransactionType transactionType;

	// 1-principal,2-interest,3-penal
	@Basic
	@Column(name = "tran_code")
	@Enumerated(EnumType.ORDINAL)
	@Type(type = "transactionCode")
	private TransactionCode transactionCode;

	@Basic
	@Column(name = "opening_balance", precision = 22, scale = 6)
	@Type(type = "accountingMoney")
	private AccountingMoney openingBalance;

	@Basic
	@Column(name = "trans_amt", precision = 22, scale = 6)
	@Type(type = "accountingMoney")
	private AccountingMoney transactionAmount;

	// --1->cr and -1 -->debit
	@Basic
	@Column(name = "crdr")
	private Integer crDr;

	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;

	@Basic
	@Column(name = "account_number", length = 20)
	private String accountNumber;

	@ManyToOne(optional = true)
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;

	@Basic
	@Column(name = "business_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date businessDate;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "line_of_credit_id", referencedColumnName = "id")
	private LineOfCredit lineOfCredit;

	@Basic
	@Column(name = "channel_type", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "channelType")
	private ChannelType channelType;
	
	// 0-not posted, 1- posted
	@Basic
	@Column(name = "status")
	private Integer postedStatus;
	
	@Basic
	@Column(name="remarks",length=255)
	private String remarks;

	@Basic
	@Column(name="terminal_id",length=20)
	private String terminalId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public TransactionCode getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(TransactionCode transactionCode) {
		this.transactionCode = transactionCode;
	}

	public AccountingMoney getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(AccountingMoney openingBalance) {
		this.openingBalance = openingBalance;
	}

	public AccountingMoney getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(AccountingMoney transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Integer getCrDr() {
		return crDr;
	}

	public void setCrDr(Integer crDr) {
		this.crDr = crDr;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Pacs getPacs() {
		return pacs;
	}

	public void setPacs(Pacs pacs) {
		this.pacs = pacs;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public LineOfCredit getLineOfCredit() {
		return lineOfCredit;
	}

	public void setLineOfCredit(LineOfCredit lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	public Integer getPostedStatus() {
		return postedStatus;
	}

	public void setPostedStatus(Integer postedStatus) {
		this.postedStatus = postedStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result
				+ ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result
				+ ((businessDate == null) ? 0 : businessDate.hashCode());
		result = prime * result
				+ ((channelType == null) ? 0 : channelType.hashCode());
		result = prime * result + ((crDr == null) ? 0 : crDr.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lineOfCredit == null) ? 0 : lineOfCredit.hashCode());
		result = prime * result
				+ ((openingBalance == null) ? 0 : openingBalance.hashCode());
		result = prime * result + ((pacs == null) ? 0 : pacs.hashCode());
		result = prime * result
				+ ((postedStatus == null) ? 0 : postedStatus.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime
				* result
				+ ((transactionAmount == null) ? 0 : transactionAmount
						.hashCode());
		result = prime * result
				+ ((transactionCode == null) ? 0 : transactionCode.hashCode());
		result = prime * result
				+ ((transactionType == null) ? 0 : transactionType.hashCode());
		result = prime * result
				+ ((voucherNumber == null) ? 0 : voucherNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionHistory other = (TransactionHistory) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (businessDate == null) {
			if (other.businessDate != null)
				return false;
		} else if (!businessDate.equals(other.businessDate))
			return false;
		if (channelType != other.channelType)
			return false;
		if (crDr == null) {
			if (other.crDr != null)
				return false;
		} else if (!crDr.equals(other.crDr))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lineOfCredit == null) {
			if (other.lineOfCredit != null)
				return false;
		} else if (!lineOfCredit.equals(other.lineOfCredit))
			return false;
		if (openingBalance == null) {
			if (other.openingBalance != null)
				return false;
		} else if (!openingBalance.equals(other.openingBalance))
			return false;
		if (pacs == null) {
			if (other.pacs != null)
				return false;
		} else if (!pacs.equals(other.pacs))
			return false;
		if (postedStatus == null) {
			if (other.postedStatus != null)
				return false;
		} else if (!postedStatus.equals(other.postedStatus))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (transactionAmount == null) {
			if (other.transactionAmount != null)
				return false;
		} else if (!transactionAmount.equals(other.transactionAmount))
			return false;
		if (transactionCode != other.transactionCode)
			return false;
		if (transactionType != other.transactionType)
			return false;
		if (voucherNumber == null) {
			if (other.voucherNumber != null)
				return false;
		} else if (!voucherNumber.equals(other.voucherNumber))
			return false;
		return true;
	}

}
