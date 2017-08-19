package com.vsoftcorp.kls.business.entities;

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


@TypeDefs({
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "st_loan_recovery")

public class StLoanRecovery {
	
		@Id
		@GeneratedValue(generator = "stLoanRecoveryIdSequence", strategy = GenerationType.SEQUENCE)
		@SequenceGenerator(name = "stLoanRecoveryIdSequence", sequenceName = "st_loan_recovery_id_sequence", allocationSize = 1)
		@Column(name = "id")
		private Long id;

		@Column(name = "customer_id")
		private String customerNumber;

		@Basic
		@Column(name = "available_balance", precision = 22, scale = 6)
		@Type(type = "money")
		private Money availableBalance;
		
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
		@Column(name = "total_subsidy_receivable", precision = 22, scale = 6)
		@Type(type = "money")
		private Money totalSubsidyReceivable;
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
		@Column(name = "recovery_entry_date")
		@Type(type = "com.vsoftcorp.time.Date")
		private Date recoveryEntryDate;

		@Column(name = "recovered_by")
		private String recoveredBy;

		@Basic
		@Column(name = "remarks")
		private String remarks;
		
		@Basic
		@Column(name="status")
		private Integer status;
		
		@Basic
		@Column(name="pacs_id")
		private Integer pacsId;
		
		@Basic
		@Column(name="passing_date")
		@Type(type="com.vsoftcorp.time.Date")
		private Date passingDate;
		
		@Basic
		@Column(name="charges_receivalble", precision = 22, scale = 6)
		@Type(type = "money")
		private Money chargesReceivable;
		
		@Basic
		@Column(name="charges_paid", precision = 22, scale = 6)
		@Type(type = "money")
		private Money chargesPaid;
		
		@Basic
		@Column(name="sanction_limit")
		@Type(type="money")
		private Money sanctionedLimit;
		
		@Basic
		@Column(name="member_number")
		private String memberNum;
		
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
		 * @return the recoveredDate
		 */
		public Date getRecoveryEntryDate() {
			return recoveryEntryDate;
		}

		/**
		 * @param recoveredDate
		 *            the recoveredDate to set
		 */
		public void setRecoveryEntryDate(Date recoveryEntryDate) {
			this.recoveryEntryDate = recoveryEntryDate;
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
		public Money getTotalSubsidyReceivable() {
			return totalSubsidyReceivable;
		}

		/**
		 * @param totalChargesReceivable
		 *            the totalChargesReceivable to set
		 */
		public void setTotalChargesReceivable(Money totalChargesReceivable) {
			this.totalSubsidyReceivable = totalSubsidyReceivable;
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

		public Integer getPacsId() {
			return pacsId;
		}

		public void setPacsId(Integer pacsId) {
			this.pacsId = pacsId;
		}

		public Date getPassingDate() {
			return passingDate;
		}

		public void setPassingDate(Date passingDate) {
			this.passingDate = passingDate;
		}
		public Money getAvailableBalance() {
			return availableBalance;
		}

		public void setAvailableBalance(Money availableBalance) {
			this.availableBalance = availableBalance;
		}

		public void setTotalSubsidyReceivable(Money totalSubsidyReceivable) {
			this.totalSubsidyReceivable = totalSubsidyReceivable;
		}

		public Money getChargesReceivable() {
			return chargesReceivable;
		}

		public void setChargesReceivable(Money chargesReceivable) {
			this.chargesReceivable = chargesReceivable;
		}

		public Money getChargesPaid() {
			return chargesPaid;
		}

		public void setChargesPaid(Money chargesPaid) {
			this.chargesPaid = chargesPaid;
		}

		public Money getSanctionedLimit() {
			return sanctionedLimit;
		}

		public void setSanctionedLimit(Money sanctionedLimit) {
			this.sanctionedLimit = sanctionedLimit;
		}

		public String getMemberNum() {
			return memberNum;
		}

		public void setMemberNum(String memberNum) {
			this.memberNum = memberNum;
		}

		public String getRejectionRemarks() {
			return rejectionRemarks;
		}

		public void setRejectionRemarks(String rejectionRemarks) {
			this.rejectionRemarks = rejectionRemarks;
		}
		
	}



