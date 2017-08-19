package com.vsoftcorp.kls.business.entity.loan;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.valuetypes.transaction.DisbursementStatus;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1623
 * 
 */
@TypeDefs({
		@TypeDef(name = "disbursementStatus", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.transaction.DisbursementStatus"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "modeOfPayment", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.transaction.TransactionMode"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "loan_disbursement_entry")
public class LoanDisbursementEntry {
	@Id
	@GeneratedValue(generator = "loanDisbursementIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "loanDisbursementIdSequence", sequenceName = "loan_disbursement_entry_id_sequence", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "customer_id")
	private Long customerNumber;

	@ManyToOne(optional = false)
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;

	@Basic
	@Column(name = "disbursement_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date disbursementDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "line_of_credit_id", referencedColumnName = "id")
	private LineOfCredit lineOfCredit;

	@Basic
	@Type(type = "money")
	@Column(name = "disbursement_amount", precision = 22, scale = 6)
	private Money disbursementAmount;

	@Basic
	@Column(name = "mode_of_disbursement")
	@Enumerated(EnumType.STRING)
	@Type(type = "modeOfPayment")
	private TransactionMode modeOfDisbursement;

	@Column(name = "to_account_number")
	private String toAccountNumber;

	@Column(name = "amt_deducted_from")
	private String amountDeductedFrom;

	@Column(name = "pacs_suvikas_voucher_no")
	private String pacsSuvikasVoucherNumber;

	@Column(name = "pacs_suvikas_voucher_date")
	private Date pacsSuvikasVoucherDate;

	@Basic
	@Column(name = "cheque_no")
	private String chequeNumber;

	@Basic
	@Column(name = "cheque_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date chequeDate;

	@Basic
	@Column(name = "remarks")
	private String remarks;

	@Basic
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	@Type(type = "disbursementStatus")
	private DisbursementStatus status;
	
	@Basic
	@Column(name = "loan_type")
	private String loanType;
	
	@Basic
	@Column(name = "insurance_amount")
	private BigDecimal insuranceAmount;
	
	@Basic
	@Column(name = "share_amount")
	private BigDecimal shareAmount;
	
	@Basic
	@Column(name = "passing_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date passingDate;
	
	@Basic
	@Column(name = "voucher_number")
	private String voucherNumber;
	
	   // For bulk disbursement
	
		@Basic
		@Column(name="instrument_no")
		private String instrumentNum;
		
		@Basic
		@Column(name="additional_info")
		private String additionalInfo;
		
		@Basic
		@Column(name="total_disburse_amt")
		private BigDecimal totalDisburseAmt;
		
		// for installment date
		
				@Basic
				@Column(name = "installment_date")
				@Type(type = "com.vsoftcorp.time.Date")
				private Date installmentDate;
				
		// for loan expiry date		
				@Basic
				@Column(name = "loan_expiry_date")
				@Type(type = "com.vsoftcorp.time.Date")
				private Date loanExpiryDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(BigDecimal shareAmount) {
		this.shareAmount = shareAmount;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the disbursementDate
	 */
	public Date getDisbursementDate() {
		return disbursementDate;
	}

	/**
	 * @param disbursementDate
	 *            the disbursementDate to set
	 */
	public void setDisbursementDate(Date disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	/**
	 * @return the modeOfDisbursement
	 */
	public TransactionMode getModeOfDisbursement() {
		return modeOfDisbursement;
	}

	/**
	 * @param modeOfDisbursement
	 *            the modeOfDisbursement to set
	 */
	public void setModeOfDisbursement(TransactionMode modeOfDisbursement) {
		this.modeOfDisbursement = modeOfDisbursement;
	}

	/**
	 * @return the toAccountNumber
	 */
	public String getToAccountNumber() {
		return toAccountNumber;
	}

	/**
	 * @param toAccountNumber
	 *            the toAccountNumber to set
	 */
	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	/**
	 * @return the amountDeductedFrom
	 */
	public String getAmountDeductedFrom() {
		return amountDeductedFrom;
	}

	/**
	 * @param amountDeductedFrom
	 *            the amountDeductedFrom to set
	 */
	public void setAmountDeductedFrom(String amountDeductedFrom) {
		this.amountDeductedFrom = amountDeductedFrom;
	}

	/**
	 * @return the pacsSuvikasVoucherNumber
	 */
	public String getPacsSuvikasVoucherNumber() {
		return pacsSuvikasVoucherNumber;
	}

	/**
	 * @param pacsSuvikasVoucherNumber
	 *            the pacsSuvikasVoucherNumber to set
	 */
	public void setPacsSuvikasVoucherNumber(String pacsSuvikasVoucherNumber) {
		this.pacsSuvikasVoucherNumber = pacsSuvikasVoucherNumber;
	}

	/**
	 * @return the pacsSuvikasVoucherDate
	 */
	public Date getPacsSuvikasVoucherDate() {
		return pacsSuvikasVoucherDate;
	}

	/**
	 * @param pacsSuvikasVoucherDate
	 *            the pacsSuvikasVoucherDate to set
	 */
	public void setPacsSuvikasVoucherDate(Date pacsSuvikasVoucherDate) {
		this.pacsSuvikasVoucherDate = pacsSuvikasVoucherDate;
	}

	/**
	 * @return the chequeNumber
	 */
	public String getChequeNumber() {
		return chequeNumber;
	}

	/**
	 * @param chequeNumber
	 *            the chequeNumber to set
	 */
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @return the chequeDate
	 */
	public Date getChequeDate() {
		return chequeDate;
	}

	/**
	 * @param chequeDate
	 *            the chequeDate to set
	 */
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
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

	/**
	 * @return the pacs
	 */
	public Pacs getPacs() {
		return pacs;
	}

	/**
	 * @param pacs
	 *            the pacs to set
	 */
	public void setPacs(Pacs pacs) {
		this.pacs = pacs;
	}

	/**
	 * @return the lineOfCredit
	 */
	public LineOfCredit getLineOfCredit() {
		return lineOfCredit;
	}

	/**
	 * @param lineOfCredit
	 *            the lineOfCredit to set
	 */
	public void setLineOfCredit(LineOfCredit lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	/**
	 * @return the disbursementAmount
	 */
	public Money getDisbursementAmount() {
		return disbursementAmount;
	}

	/**
	 * @param disbursementAmount
	 *            the disbursementAmount to set
	 */
	public void setDisbursementAmount(Money disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}

	/**
	 * @return the customerNumber
	 */
	public Long getCustomerNumber() {
		return customerNumber;
	}

	/**
	 * @param customerNumber
	 *            the customerNumber to set
	 */
	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	/**
	 * @return the status
	 */
	public DisbursementStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(DisbursementStatus status) {
		this.status = status;
	}

	/**
	 * @return the loanType
	 */
	public String getLoanType() {
		return loanType;
	}

	/**
	 * @param loanType the loanType to set
	 */
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}


	public Date getPassingDate() {
		return passingDate;
	}

	public void setPassingDate(Date passingDate) {
		this.passingDate = passingDate;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getInstrumentNum() {
		return instrumentNum;
	}

	public void setInstrumentNum(String instrumentNum) {
		this.instrumentNum = instrumentNum;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public BigDecimal getTotalDisburseAmt() {
		return totalDisburseAmt;
	}

	public void setTotalDisburseAmt(BigDecimal totalDisburseAmt) {
		this.totalDisburseAmt = totalDisburseAmt;
	}
	
	public Date getInstallmentDate() {
		return installmentDate;
	}

	public void setInstallmentDate(Date installmentDate) {
		this.installmentDate = installmentDate;
	}
	public Date getLoanExpiryDate() {
		return loanExpiryDate;
	}

	public void setLoanExpiryDate(Date loanExpiryDate) {
		this.loanExpiryDate = loanExpiryDate;
	}

}
