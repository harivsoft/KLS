package com.vsoftcorp.kls.business.entity.loan;

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
import com.vsoftcorp.kls.business.entities.BorrowingProduct;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.Account;
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
	@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "borrowing_recovery_entry")
public class BorrowingRecoveryEntry {
	@Id
	@GeneratedValue(generator = "borrowingRecoveryEntrySequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "borrowingRecoveryEntrySequence", sequenceName = "borrowing_recovery_entry_id_sequence", allocationSize = 1)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;
	
	@Basic
	@Column(name = "recovery_entry_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date recoveryEntryDate;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "borrowing_product_id", referencedColumnName = "id")
	private BorrowingProduct borrowingProduct;
	
	@Basic
	@Type(type = "money")
	@Column(name = "recovery_amount", precision = 22, scale = 6)
	private Money recoveryAmount;
	
	@Basic
	@Column(name = "mode_of_collection")
	@Enumerated(EnumType.STRING)
	@Type(type = "modeOfPayment")
	private TransactionMode modeOfCollection;
	
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
	@Column(name = "passing_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date passingDate;
	
	@Basic
	@Column(name = "line_of_credit_id")
	private Long lineOfCredit;
	
	@Basic
	@Column(name = "purpose")
	private String purpose;
	
	@Basic
	@Column(name = "ccb_account_number")
	private String ccbLoanAccountNumber;
	

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getCcbLoanAccountNumber() {
		return ccbLoanAccountNumber;
	}

	public void setCcbLoanAccountNumber(String ccbLoanAccountNumber) {
		this.ccbLoanAccountNumber = ccbLoanAccountNumber;
	}

	public Long getLineOfCredit() {
		return lineOfCredit;
	}

	public void setLineOfCredit(Long lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	@Basic
	@Type(type = "money")
	@Column(name = "principle_balance", precision = 22, scale = 6)
	private Money principlaBalance;
	
	@Basic
	@Type(type = "money")
	@Column(name = "interest_payable", precision = 22, scale = 6)
	private Money interestPayable;
	
	@Basic
	@Type(type = "money")
	@Column(name = "penal_interest_payable", precision = 22, scale = 6)
	private Money penalInterestPayable;
	

	public Money getPrinciplaBalance() {
		return principlaBalance;
	}

	public void setPrinciplaBalance(Money principlaBalance) {
		this.principlaBalance = principlaBalance;
	}

	public Money getInterestPayable() {
		return interestPayable;
	}

	public void setInterestPayable(Money interestPayable) {
		this.interestPayable = interestPayable;
	}

	public Money getPenalInterestPayable() {
		return penalInterestPayable;
	}

	public void setPenalInterestPayable(Money penalInterestPayable) {
		this.penalInterestPayable = penalInterestPayable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pacs getPacs() {
		return pacs;
	}

	public void setPacs(Pacs pacs) {
		this.pacs = pacs;
	}

	public Date getRecoveryEntryDate() {
		return recoveryEntryDate;
	}

	public void setRecoveryEntryDate(Date recoveryEntryDate) {
		this.recoveryEntryDate = recoveryEntryDate;
	}


	public Money getRecoveryAmount() {
		return recoveryAmount;
	}

	public void setRecoveryAmount(Money recoveryAmount) {
		this.recoveryAmount = recoveryAmount;
	}

	public TransactionMode getModeOfCollection() {
		return modeOfCollection;
	}

	public void setModeOfCollection(TransactionMode modeOfCollection) {
		this.modeOfCollection = modeOfCollection;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public DisbursementStatus getStatus() {
		return status;
	}

	public void setStatus(DisbursementStatus status) {
		this.status = status;
	}

	public Date getPassingDate() {
		return passingDate;
	}

	public void setPassingDate(Date passingDate) {
		this.passingDate = passingDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BorrowingProduct getBorrowingProduct() {
		return borrowingProduct;
	}

	public void setBorrowingProduct(BorrowingProduct borrowingProduct) {
		this.borrowingProduct = borrowingProduct;
	}
	
	
}
