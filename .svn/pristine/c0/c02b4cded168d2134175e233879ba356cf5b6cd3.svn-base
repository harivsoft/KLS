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
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1623
 * 
 */
@TypeDefs({
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
@Table(name = "loan_recovery")
public class LoanRecovery {
	@Id
	@GeneratedValue(generator = "loanRecoveryIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "loanRecoveryIdSequence", sequenceName = "loan_recovery_id_sequence", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "customer_number")
	private String customerNumber;

	@ManyToOne(optional = false)
	@JoinColumn(name = "line_of_credit_id", referencedColumnName = "id")
	private LoanLineOfCredit loanLineOfCredit;

	@Basic
	@Column(name = "outstanding_balance", precision = 22, scale = 6)
	@Type(type = "money")
	private Money outStandingBalance;
	@Basic
	@Column(name = "installment_amount", precision = 22, scale = 6)
	@Type(type = "money")
	private Money installmentAmount;
	@Basic
	@Column(name = "total_principal_receivable", precision = 22, scale = 6)
	@Type(type = "money")
	private Money totalPrincipalReceivable;
	@Basic
	@Column(name = "total_interest_receivable", precision = 22, scale = 6)
	@Type(type = "money")
	private Money totalInterestReceivable;
	@Basic
	@Column(name = "total_penal_interest_receivable", precision = 22, scale = 6)
	@Type(type = "money")
	private Money totalPenalInterestReceivable;
	@Basic
	@Column(name = "total_charges_receivable", precision = 22, scale = 6)
	@Type(type = "money")
	private Money totalChargesReceivable;
	@Basic
	@Column(name = "total_receivable_amount", precision = 22, scale = 6)
	@Type(type = "money")
	private Money totalReceivableAmount;
	@Basic
	@Column(name = "amount_paid", precision = 22, scale = 6)
	@Type(type = "money")
	private Money amountPaid;
	@Basic
	@Column(name = "principal_paid", precision = 22, scale = 6)
	@Type(type = "money")
	private Money principalPaid;
	@Basic
	@Column(name = "interest_paid", precision = 22, scale = 6)
	@Type(type = "money")
	private Money interestPaid;
	@Basic
	@Column(name = "penal_interest_paid", precision = 22, scale = 6)
	@Type(type = "money")
	private Money penalInterestPaid;
	@Basic
	@Column(name = "charges_paid", precision = 22, scale = 6)
	@Type(type = "money")
	private Money chargesPaid;

	@Basic
	@Column(name = "outstanding_balance_after_payment", precision = 22, scale = 6)
	@Type(type = "money")
	private Money outStandingBalanceAfterPayment;

	@Basic
	@Column(name = "mode_of_payment")
	@Enumerated(EnumType.STRING)
	@Type(type = "modeOfPayment")
	private TransactionMode modeOfPayment;
	@Basic
	@Column(name = "savings_account_number")
	private String savingsAccountNumber;

	@Basic
	@Column(name = "recovered_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date recoveredDate;

	@Column(name = "recovered_by")
	private String recoveredBy;

	@Basic
	@Column(name = "remarks")
	private String remarks;
	
	@Basic
	@Column(name="status")
	private Integer status;
	
	@Basic
	@Column(name="rejection_remarks")
	private String rejectionRemarks;

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
	 * @return the loanLineOfCredit
	 */
	public LoanLineOfCredit getLoanLineOfCredit() {
		return loanLineOfCredit;
	}

	/**
	 * @param loanLineOfCredit
	 *            the loanLineOfCredit to set
	 */
	public void setLoanLineOfCredit(LoanLineOfCredit loanLineOfCredit) {
		this.loanLineOfCredit = loanLineOfCredit;
	}

	/**
	 * @return the recoveredDate
	 */
	public Date getRecoveredDate() {
		return recoveredDate;
	}

	/**
	 * @param recoveredDate
	 *            the recoveredDate to set
	 */
	public void setRecoveredDate(Date recoveredDate) {
		this.recoveredDate = recoveredDate;
	}

	/**
	 * @return the recoveredBy
	 */
	public String getRecoveredBy() {
		return recoveredBy;
	}

	/**
	 * @param recoveredBy
	 *            the recoveredBy to set
	 */
	public void setRecoveredBy(String recoveredBy) {
		this.recoveredBy = recoveredBy;
	}

	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}

	/**
	 * @param customerNumber
	 *            the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	/**
	 * @return the outStandingBalance
	 */
	public Money getOutStandingBalance() {
		return outStandingBalance;
	}

	/**
	 * @param outStandingBalance
	 *            the outStandingBalance to set
	 */
	public void setOutStandingBalance(Money outStandingBalance) {
		this.outStandingBalance = outStandingBalance;
	}

	/**
	 * @return the installmentAmount
	 */
	public Money getInstallmentAmount() {
		return installmentAmount;
	}

	/**
	 * @param installmentAmount
	 *            the installmentAmount to set
	 */
	public void setInstallmentAmount(Money installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	/**
	 * @return the totalPrincipalReceivable
	 */
	public Money getTotalPrincipalReceivable() {
		return totalPrincipalReceivable;
	}

	/**
	 * @param totalPrincipalReceivable
	 *            the totalPrincipalReceivable to set
	 */
	public void setTotalPrincipalReceivable(Money totalPrincipalReceivable) {
		this.totalPrincipalReceivable = totalPrincipalReceivable;
	}

	/**
	 * @return the outStandingBalanceAfterPayment
	 */
	public Money getOutStandingBalanceAfterPayment() {
		return outStandingBalanceAfterPayment;
	}

	/**
	 * @param outStandingBalanceAfterPayment
	 *            the outStandingBalanceAfterPayment to set
	 */
	public void setOutStandingBalanceAfterPayment(Money outStandingBalanceAfterPayment) {
		this.outStandingBalanceAfterPayment = outStandingBalanceAfterPayment;
	}

	/**
	 * @return the totalInterestReceivable
	 */
	public Money getTotalInterestReceivable() {
		return totalInterestReceivable;
	}

	/**
	 * @param totalInterestReceivable
	 *            the totalInterestReceivable to set
	 */
	public void setTotalInterestReceivable(Money totalInterestReceivable) {
		this.totalInterestReceivable = totalInterestReceivable;
	}

	/**
	 * @return the totalPenalInterestReceivable
	 */
	public Money getTotalPenalInterestReceivable() {
		return totalPenalInterestReceivable;
	}

	/**
	 * @param totalPenalInterestReceivable
	 *            the totalPenalInterestReceivable to set
	 */
	public void setTotalPenalInterestReceivable(Money totalPenalInterestReceivable) {
		this.totalPenalInterestReceivable = totalPenalInterestReceivable;
	}

	/**
	 * @return the totalChargesReceivable
	 */
	public Money getTotalChargesReceivable() {
		return totalChargesReceivable;
	}

	/**
	 * @param totalChargesReceivable
	 *            the totalChargesReceivable to set
	 */
	public void setTotalChargesReceivable(Money totalChargesReceivable) {
		this.totalChargesReceivable = totalChargesReceivable;
	}

	/**
	 * @return the totalReceivableAmount
	 */
	public Money getTotalReceivableAmount() {
		return totalReceivableAmount;
	}

	/**
	 * @param totalReceivableAmount
	 *            the totalReceivableAmount to set
	 */
	public void setTotalReceivableAmount(Money totalReceivableAmount) {
		this.totalReceivableAmount = totalReceivableAmount;
	}

	/**
	 * @return the amountPaid
	 */
	public Money getAmountPaid() {
		return amountPaid;
	}

	/**
	 * @param amountPaid
	 *            the amountPaid to set
	 */
	public void setAmountPaid(Money amountPaid) {
		this.amountPaid = amountPaid;
	}

	/**
	 * @return the principalPaid
	 */
	public Money getPrincipalPaid() {
		return principalPaid;
	}

	/**
	 * @param principalPaid
	 *            the principalPaid to set
	 */
	public void setPrincipalPaid(Money principalPaid) {
		this.principalPaid = principalPaid;
	}

	/**
	 * @return the interestPaid
	 */
	public Money getInterestPaid() {
		return interestPaid;
	}

	/**
	 * @param interestPaid
	 *            the interestPaid to set
	 */
	public void setInterestPaid(Money interestPaid) {
		this.interestPaid = interestPaid;
	}

	/**
	 * @return the penalInterestPaid
	 */
	public Money getPenalInterestPaid() {
		return penalInterestPaid;
	}

	/**
	 * @param penalInterestPaid
	 *            the penalInterestPaid to set
	 */
	public void setPenalInterestPaid(Money penalInterestPaid) {
		this.penalInterestPaid = penalInterestPaid;
	}

	/**
	 * @return the chargesPaid
	 */
	public Money getChargesPaid() {
		return chargesPaid;
	}

	/**
	 * @param chargesPaid
	 *            the chargesPaid to set
	 */
	public void setChargesPaid(Money chargesPaid) {
		this.chargesPaid = chargesPaid;
	}

	/**
	 * @return the outStandingBalanceAfterPayment
	 */
	/*
	 * public Money getOutStandingBalanceAfterPayment() { return
	 * outStandingBalanceAfterPayment; }
	 *//**
	 * @param outStandingBalanceAfterPayment
	 *            the outStandingBalanceAfterPayment to set
	 */
	/*
	 * public void setOutStandingBalanceAfterPayment(Money
	 * outStandingBalanceAfterPayment) { this.outStandingBalanceAfterPayment =
	 * outStandingBalanceAfterPayment; }
	 */

	/**
	 * @return the savingsAccountNumber
	 */
	public String getSavingsAccountNumber() {
		return savingsAccountNumber;
	}

	/**
	 * @return the modeOfPayment
	 */
	public TransactionMode getModeOfPayment() {
		return modeOfPayment;
	}

	/**
	 * @param modeOfPayment
	 *            the modeOfPayment to set
	 */
	public void setModeOfPayment(TransactionMode modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	/**
	 * @param savingsAccountNumber
	 *            the savingsAccountNumber to set
	 */
	public void setSavingsAccountNumber(String savingsAccountNumber) {
		this.savingsAccountNumber = savingsAccountNumber;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRejectionRemarks() {
		return rejectionRemarks;
	}

	public void setRejectionRemarks(String rejectionRemarks) {
		this.rejectionRemarks = rejectionRemarks;
	}

}
