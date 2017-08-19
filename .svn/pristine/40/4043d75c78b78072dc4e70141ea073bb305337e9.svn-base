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
 * 
 * @author a1565
 *
 */

@TypeDefs({
	@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
			@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
			@Parameter(name = "isDebitNegative", value = "true") }),
	@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") })
})

@Entity
@Table(name = "loan_disbursement_schedule")
public class LoanDisbursementSchedule implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private LoanDisbursementCompositeId loanDisbursmentCompositeId;

	@Basic
	@Column(name = "disbursement_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date disbursementDate;
	
	@Basic
	@Type(type = "money")
	@Column(name = "disbursement_amount", precision = 22, scale = 6)
	private Money disbursementAmount;
	
	@Basic
	@Type(type = "money")
	@Column(name = "remaining_loc_balance", precision = 22, scale = 6)
	private Money remainingBalance;
	
	@Basic
	@Type(type = "money")
	@Column(name = "disbursed_amount", precision = 22, scale = 6)
	private Money disbursedAmount;
	
	@Basic
	@Column(name = "remarks" ,length=60)
	private String remarks;
	

	public Date getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(Date disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public Money getDisbursementAmount() {
		return disbursementAmount;
	}

	public void setDisbursementAmount(Money disbursementAmount) {
		this.disbursementAmount = disbursementAmount;
	}

	public Money getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(Money remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public String getRemarks() {
		return remarks;
	}

	public Money getDisbursedAmount() {
		return disbursedAmount;
	}

	public void setDisbursedAmount(Money disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LoanDisbursementCompositeId getLoanDisbursmentCompositeId() {
		return loanDisbursmentCompositeId;
	}

	public void setLoanDisbursmentCompositeId(
			LoanDisbursementCompositeId loanDisbursmentCompositId) {
		this.loanDisbursmentCompositeId = loanDisbursmentCompositId;
	}


	
}
