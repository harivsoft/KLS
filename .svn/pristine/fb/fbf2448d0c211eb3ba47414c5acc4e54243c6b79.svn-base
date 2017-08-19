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
import com.vsoftcorp.time.Date;

@TypeDefs({
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "charges_debit")
public class ChargesDebit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2656503278068479150L;

	@Id
	@GeneratedValue(generator = "chargesDebitIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "chargesDebitIdSequence", sequenceName = "charges_debit_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = true)
	@JoinColumn(name = "line_of_credit_id", referencedColumnName = "id")
	private LineOfCredit lineOfCredit;

	@ManyToOne(optional = true)
	@JoinColumn(name = "charges_master_id", referencedColumnName = "id")
	private ChargesMaster chargesMaster;

	@Basic
	@Type(type = "money")
	@Column(name = "amount", precision = 22, scale = 6)
	private Money amount;

	@Basic
	@Column(name = "remarks")
	private String remarks;

	@Basic
	@Column(name = "voucher_number")
	private String voucherNumber;

	@Basic
	@Column(name = "business_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date businessDate;
	
	
	@Basic
	@Type(type = "money")
	@Column(name = "balance_amount", precision = 22, scale = 6)
	private Money balanceAmount;
	
	
	

	public Money getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Money balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

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
	 * @return the chargesMaster
	 */
	public ChargesMaster getChargesMaster() {
		return chargesMaster;
	}

	/**
	 * @param chargesMaster
	 *            the chargesMaster to set
	 */
	public void setChargesMaster(ChargesMaster chargesMaster) {
		this.chargesMaster = chargesMaster;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * @return the businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 *            the businessDate to set
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

}
