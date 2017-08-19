package com.vsoftcorp.kls.business.entity.loan;

import java.io.Serializable;

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
import com.vsoftcorp.kls.valuetypes.charges.ChargesType;

@TypeDefs({
		@TypeDef(name = "chargesType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.charges.ChargesType"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }), })
@Entity
@Table(name = "charges_master")
public class ChargesMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3743044875391341744L;

	@Id
	@GeneratedValue(generator = "chargesMasterIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "chargesMasterIdSequence", sequenceName = "charges_master_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "charges_description")
	private String chargesDescription;

	@Basic
	@Column(name = "charges_code")
	private String chargesCode;

	@Basic
	@Type(type = "money")
	@Column(name = "min_amount", precision = 22, scale = 6)
	private Money minAmount;

	@Basic
	@Type(type = "money")
	@Column(name = "max_amount", precision = 22, scale = 6)
	private Money maxAmount;

	@Basic
	@Column(name = "charges_type")
	@Enumerated(EnumType.STRING)
	@Type(type = "chargesType")
	private ChargesType chargesType;

	@Basic
	@Column(name = "charges_received_gl", length = 18)
	private String chargesReceivedGL;

	@Basic
	@Column(name = "bank_charges_receivable_gl", length = 18)
	private String bankChargesReceivableGL;
	@Basic
	@Column(name = "bank_charges_received_gl", length = 18)
	private String bankChargesReceivedGL;

	@Basic
	@Column(name = "charges_receivable_gl", length = 18)
	private String chargesReceivableGL;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;
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
	 * @return the chargesDescription
	 */
	public String getChargesDescription() {
		return chargesDescription;
	}

	/**
	 * @param chargesDescription
	 *            the chargesDescription to set
	 */
	public void setChargesDescription(String chargesDescription) {
		this.chargesDescription = chargesDescription;
	}

	/**
	 * @return the chargesCode
	 */
	public String getChargesCode() {
		return chargesCode;
	}

	/**
	 * @param chargesCode
	 *            the chargesCode to set
	 */
	public void setChargesCode(String chargesCode) {
		this.chargesCode = chargesCode;
	}

	/**
	 * @return the minAmount
	 */
	public Money getMinAmount() {
		return minAmount;
	}

	/**
	 * @param minAmount
	 *            the minAmount to set
	 */
	public void setMinAmount(Money minAmount) {
		this.minAmount = minAmount;
	}

	/**
	 * @return the maxAmount
	 */
	public Money getMaxAmount() {
		return maxAmount;
	}

	/**
	 * @param maxAmount
	 *            the maxAmount to set
	 */
	public void setMaxAmount(Money maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * @return the chargesType
	 */
	public ChargesType getChargesType() {
		return chargesType;
	}

	/**
	 * @param chargesType
	 *            the chargesType to set
	 */
	public void setChargesType(ChargesType chargesType) {
		this.chargesType = chargesType;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the chargesReceivedGL
	 */
	public String getChargesReceivedGL() {
		return chargesReceivedGL;
	}

	/**
	 * @param chargesReceivedGL the chargesReceivedGL to set
	 */
	public void setChargesReceivedGL(String chargesReceivedGL) {
		this.chargesReceivedGL = chargesReceivedGL;
	}

	/**
	 * @return the chargesReceivableGL
	 */
	public String getChargesReceivableGL() {
		return chargesReceivableGL;
	}

	/**
	 * @param chargesReceivableGL the chargesReceivableGL to set
	 */
	public void setChargesReceivableGL(String chargesReceivableGL) {
		this.chargesReceivableGL = chargesReceivableGL;
	}

	/**
	 * @return the pacs
	 */
	public Pacs getPacs() {
		return pacs;
	}

	/**
	 * @param pacs the pacs to set
	 */
	public void setPacs(Pacs pacs) {
		this.pacs = pacs;
	}

	public String getBankChargesReceivableGL() {
		return bankChargesReceivableGL;
	}

	public void setBankChargesReceivableGL(String bankChargesReceivableGL) {
		this.bankChargesReceivableGL = bankChargesReceivableGL;
	}

	public String getBankChargesReceivedGL() {
		return bankChargesReceivedGL;
	}

	public void setBankChargesReceivedGL(String bankChargesReceivedGL) {
		this.bankChargesReceivedGL = bankChargesReceivedGL;
	}

}
