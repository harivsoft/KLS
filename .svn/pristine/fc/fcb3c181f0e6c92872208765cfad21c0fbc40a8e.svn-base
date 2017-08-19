package com.vsoftcorp.kls.business.entity.loan;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.time.Date;

@TypeDefs({
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"), @Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "transactionMode", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.transaction.TransactionMode"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }), })
@Entity
@Table(name = "pacs_loan_disbursement")
public class PacsLoanDisbursement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "loanDisbursementIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "loanDisbursementIdSequence", sequenceName = "pacs_loan_disbursement_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "disbursement_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date disbursementDate;

	@ManyToOne
	@JoinColumn(name = "line_of_credit_id", referencedColumnName = "id")
	private LineOfCredit lineOfCredit;

	@Basic
	@Type(type = "money")
	@Column(name = "disbursed_amount", precision = 22, scale = 6)
	private Money disbursedAmount;

	@Basic
	@Type(type = "transactionMode")
	@Column(name = "mode_of_disbursement")
	private TransactionMode modeOfDisbursement;

	@Column(name = "to_account_number")
	private String toAccountNumber;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "amt_deducted_from")
	private String amountDeductedFrom;

	@Column(name = "pacs_suvikas_voucher_num")
	private String pacsSuvikasVoucherNumber;

	@Column(name = "pacs_suvikas_voucher_dt")
	private Date pacsSuvikasVoucherDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(Date disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public LineOfCredit getLineOfCredit() {
		return lineOfCredit;
	}

	public void setLineOfCredit(LineOfCredit lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	public Money getDisbursedAmount() {
		return disbursedAmount;
	}

	public void setDisbursedAmount(Money disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}

	public TransactionMode getModeOfDisbursement() {
		return modeOfDisbursement;
	}

	public void setModeOfDisbursement(TransactionMode modeOfDisbursement) {
		this.modeOfDisbursement = modeOfDisbursement;
	}

	public String getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAmountDeductedFrom() {
		return amountDeductedFrom;
	}

	public void setAmountDeductedFrom(String amountDeductedFrom) {
		this.amountDeductedFrom = amountDeductedFrom;
	}

	public String getPacsSuvikasVoucherNumber() {
		return pacsSuvikasVoucherNumber;
	}

	public void setPacsSuvikasVoucherNumber(String pacsSuvikasVoucherNumber) {
		this.pacsSuvikasVoucherNumber = pacsSuvikasVoucherNumber;
	}

	public Date getPacsSuvikasVoucherDate() {
		return pacsSuvikasVoucherDate;
	}

	public void setPacsSuvikasVoucherDate(Date pacsSuvikasVoucherDate) {
		this.pacsSuvikasVoucherDate = pacsSuvikasVoucherDate;
	}

}
