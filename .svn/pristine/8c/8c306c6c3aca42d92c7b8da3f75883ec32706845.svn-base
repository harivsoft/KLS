package com.vsoftcorp.kls.business.entity.loan;

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
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1623
 * 
 */
@TypeDefs({ @TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
		@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
		@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "charges_recovery")
public class ChargesRecovery {
	@Id
	@GeneratedValue(generator = "chargeRecoveryIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "chargeRecoveryIdSequence", sequenceName = "charge_recovery_id_sequence", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = true)
	@JoinColumn(name = "charge_debit_id", referencedColumnName = "id")
	private ChargesDebit chargeDebitId;

	@Basic
	@Column(name = "voucher_number")
	private String voucherNumber;

	@Basic
	@Type(type = "money")
	@Column(name = "amount", precision = 22, scale = 6)
	private Money amount;
	
	@Basic
	@Type(type = "money")
	@Column(name = "bank_amount", precision = 22, scale = 6)
	private Money bankAmount;
	
	@Basic
	@Column(name = "business_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date businessDate;

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
	 * @return the chargeDebitId
	 */
	public ChargesDebit getChargeDebitId() {
		return chargeDebitId;
	}

	/**
	 * @param chargeDebitId the chargeDebitId to set
	 */
	public void setChargeDebitId(ChargesDebit chargeDebitId) {
		this.chargeDebitId = chargeDebitId;
	}

	/**
	 * @return the voucherNumber
	 */
	public String getVoucherNumber() {
		return voucherNumber;
	}

	/**
	 * @param voucherNumber
	 *            the voucherNumber to set
	 */
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	/**
	 * @return the amount
	 */
	public Money getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Money amount) {
		this.amount = amount;
	}

	/**
	 * @return the businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the bankAmount
	 */
	public Money getBankAmount() {
		return bankAmount;
	}

	/**
	 * @param bankAmount the bankAmount to set
	 */
	public void setBankAmount(Money bankAmount) {
		this.bankAmount = bankAmount;
	}

	

}
