/**
 * 
 */
package com.vsoftcorp.kls.business.entity.loan;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
@TypeDefs({
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "loan_repayment_schedule")
public class LoanRepaymentSchedule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3216343247633726199L;

	@EmbeddedId
	private LoanRepaymentScheduleId loanRepaymentScheduleId;

	@Basic
	@Column(name = "installment_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date installmentDate;

	@Basic
	@Type(type = "money")
	@Column(name = "installment_amount", precision = 22, scale = 6)
	private Money installmentAmount;

	@Basic
	@Type(type = "money")
	@Column(name = "principal_amount", precision = 22, scale = 6)
	private Money principalAmount;

	@Basic
	@Type(type = "money")
	@Column(name = "interest_amount", precision = 22, scale = 6)
	private Money interestAmount;

	@Basic
	@Type(type = "money")
	@Column(name = "loan_outstanding_amount", precision = 22, scale = 6)
	private Money loanOutstandingAmount;

	/**
	 * @return the loanRepaymentScheduleId
	 */
	public LoanRepaymentScheduleId getLoanRepaymentScheduleId() {
		return loanRepaymentScheduleId;
	}

	/**
	 * @param loanRepaymentScheduleId
	 *            the loanRepaymentScheduleId to set
	 */
	public void setLoanRepaymentScheduleId(LoanRepaymentScheduleId loanRepaymentScheduleId) {
		this.loanRepaymentScheduleId = loanRepaymentScheduleId;
	}

	/**
	 * @return the installmentDate
	 */
	public Date getInstallmentDate() {
		return installmentDate;
	}

	/**
	 * @param installmentDate
	 *            the installmentDate to set
	 */
	public void setInstallmentDate(Date installmentDate) {
		this.installmentDate = installmentDate;
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
	 * @return the principalAmount
	 */
	public Money getPrincipalAmount() {
		return principalAmount;
	}

	/**
	 * @param principalAmount
	 *            the principalAmount to set
	 */
	public void setPrincipalAmount(Money principalAmount) {
		this.principalAmount = principalAmount;
	}

	/**
	 * @return the interestAmount
	 */
	public Money getInterestAmount() {
		return interestAmount;
	}

	/**
	 * @param interestAmount
	 *            the interestAmount to set
	 */
	public void setInterestAmount(Money interestAmount) {
		this.interestAmount = interestAmount;
	}

	/**
	 * @return the loanOutstandingAmount
	 */
	public Money getLoanOutstandingAmount() {
		return loanOutstandingAmount;
	}

	/**
	 * @param loanOutstandingAmount
	 *            the loanOutstandingAmount to set
	 */
	public void setLoanOutstandingAmount(Money loanOutstandingAmount) {
		this.loanOutstandingAmount = loanOutstandingAmount;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
