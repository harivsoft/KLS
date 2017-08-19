package com.vsoftcorp.kls.business.entity.loan;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.finance.Money;


@TypeDefs({
	@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
			@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
			@Parameter(name = "isDebitNegative", value = "true") }) ,
@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name="pacs_loan_app_activity")
public class LoanApplicationForActivity {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private LoanApplicationActivityId loanApplicationActivityId;
	
	@Column(name="no_of_units")
    private Integer noOfUnit;
	
	@Type(type = "money")
	@Column(name="requested_amount",  precision = 22, scale = 6)
    private Money requestedAmount;
	
	@Type(type = "money")
	@Column(name="amt_as_per_unit_cost",  precision = 22, scale = 6)
    private Money amountAsPerUnitCost;
    
    
	public LoanApplicationActivityId getLoanApplicationActivityId() {
		return loanApplicationActivityId;
	}
	public void setLoanApplicationActivityId(
			LoanApplicationActivityId loanApplicationActivityId) {
		this.loanApplicationActivityId = loanApplicationActivityId;
	}
	public Integer getNoOfUnit() {
		return noOfUnit;
	}
	public void setNoOfUnit(Integer noOfUnit) {
		this.noOfUnit = noOfUnit;
	}
	public Money getRequestedAmount() {
		return requestedAmount;
	}
	public void setRequestedAmount(Money requestedAmount) {
		this.requestedAmount = requestedAmount;
	}
	public Money getAmountAsPerUnitCost() {
		return amountAsPerUnitCost;
	}
	public void setAmountAsPerUnitCost(Money amountAsPerUnitCost) {
		this.amountAsPerUnitCost = amountAsPerUnitCost;
	}
	
}
