package com.vsoftcorp.kls.business.entity.account;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.valuetypes.charges.ChargeType;

/**
 * This Entity is used to do CRUD operations with charges table
 * 
 * @author a1605
 * 
 */

@TypeDefs({ @TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
		@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
		@Parameter(name = "isDebitNegative", value = "true") }),@TypeDef(name = "chargeType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
			@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.charges.ChargeType"),
			@Parameter(name = "identifierMethod", value = "getValue"),
			@Parameter(name = "convertIdentifierMethod", value = "getType"),
			@Parameter(name = "valueOfMethod", value = "getType") }) })
@Entity
@Table(name = "charges")
public class Charges {

	@Id
	@GeneratedValue(generator = "changesIdSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "changesIdSeq", sequenceName = "charges_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = true)
	@JoinColumn(name = "pacs_loan_application_dtl_id", referencedColumnName = "id")
	private PacsLoanApplicationDetail pacsLoanApplicationDtl;

	@ManyToOne(optional = true)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;

	@ManyToOne(optional = true)
	@JoinColumn(name = "season_id", referencedColumnName = "id")
	private Season season;

	@Column(name = "charge_type", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "chargeType")
	private ChargeType chargeType;

	@Basic
	@Column(name = "charge_amount", precision = 22, scale = 6)
	@Type(type = "money")
	private Money chargeAmount;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "line_of_credit_id", referencedColumnName = "id")
	private LineOfCredit lineOfCredit;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "pacs_loan_application_id", referencedColumnName = "id")
	private PacsLoanApplication loanApplication;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PacsLoanApplicationDetail getPacsLoanApplicationDtl() {
		return pacsLoanApplicationDtl;
	}

	public Account getAccount() {
		return account;
	}

	public Season getSeason() {
		return season;
	}

	public void setPacsLoanApplicationDtl(PacsLoanApplicationDetail pacsLoanApplicationDtl) {
		this.pacsLoanApplicationDtl = pacsLoanApplicationDtl;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Money getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Money chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public LineOfCredit getLineOfCredit() {
		return lineOfCredit;
	}

	public void setLineOfCredit(LineOfCredit lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	/**
	 * @return the loanApplication
	 */
	public PacsLoanApplication getLoanApplication() {
		return loanApplication;
	}

	/**
	 * @param loanApplication
	 *            the loanApplication to set
	 */
	public void setLoanApplication(PacsLoanApplication loanApplication) {
		this.loanApplication = loanApplication;
	}

	/**
	 * @return the chargeType
	 */
	public ChargeType getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType
	 *            the chargeType to set
	 */
	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}

}
