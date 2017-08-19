package com.vsoftcorp.kls.business.entity.account;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.BorrowingProduct;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.valuetypes.account.SanctionAuthority;
import com.vsoftcorp.kls.valuetypes.loan.LoanType;
import com.vsoftcorp.time.Date;

@TypeDefs({
		@TypeDef(name = "loanType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loan.LoanType"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
				@Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "accountType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.account.AccountType"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "sanctionAuthority", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.account.SanctionAuthority"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@DiscriminatorValue("B")
// B for borrowings account.
public class BorrowingsAccountProperty extends LoanAccountProperty {
	private static final long serialVersionUID = 3175283650129788972L;
	@Basic
	@Column(name = "name")
	private String name;
	@Basic
	@Column(name = "lender_name")
	private String lenderName;
	@Basic
	@Column(name = "borrowing_type")
	@Enumerated(EnumType.ORDINAL)
	@Type(type = "loanType")
	private LoanType borrowingType;
	@Basic
	@Column(name = "ccb_account_number")
	private String ccbAccountNumber;
	@ManyToOne
	@JoinColumn(name = "purpose_id", referencedColumnName = "id")
	private Purpose purpose;
	@ManyToOne(optional = true)
	@JoinColumn(name = "borrowing_product_id", referencedColumnName = "id")
	private BorrowingProduct borrowingProduct;
	@ManyToOne(optional = true)
	@JoinColumn(name = "interest_category_id", referencedColumnName = "id")
	private InterestCategory interestCategory;

	@Basic
	@Column(name = "sanctioned_limit", precision = 22, scale = 6)
	@Type(type = "money")
	private Money sanctionedAmount;

	@Basic
	@Column(name = "sanctioned_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date sanctionedDate;

	@Basic
	@Column(name = "sanction_authority")
	@Enumerated(EnumType.ORDINAL)
	@Type(type = "sanctionAuthority")
	private SanctionAuthority sanctionAuthority;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the lenderName
	 */
	public String getLenderName() {
		return lenderName;
	}

	/**
	 * @param lenderName
	 *            the lenderName to set
	 */
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}

	/**
	 * @return the borrowingType
	 */
	public LoanType getBorrowingType() {
		return borrowingType;
	}

	/**
	 * @param borrowingType
	 *            the borrowingType to set
	 */
	public void setBorrowingType(LoanType borrowingType) {
		this.borrowingType = borrowingType;
	}

	/**
	 * @return the ccbAccountNumber
	 */
	public String getCcbAccountNumber() {
		return ccbAccountNumber;
	}

	/**
	 * @param ccbAccountNumber
	 *            the ccbAccountNumber to set
	 */
	public void setCcbAccountNumber(String ccbAccountNumber) {
		this.ccbAccountNumber = ccbAccountNumber;
	}

	/**
	 * @return the purpose
	 */
	public Purpose getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose
	 *            the purpose to set
	 */
	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}

	/**
	 * @return the borrowingProduct
	 */
	public BorrowingProduct getBorrowingProduct() {
		return borrowingProduct;
	}

	/**
	 * @param borrowingProduct
	 *            the borrowingProduct to set
	 */
	public void setBorrowingProduct(BorrowingProduct borrowingProduct) {
		this.borrowingProduct = borrowingProduct;
	}

	/**
	 * @return the interestCategory
	 */
	public InterestCategory getInterestCategory() {
		return interestCategory;
	}

	/**
	 * @param interestCategory
	 *            the interestCategory to set
	 */
	public void setInterestCategory(InterestCategory interestCategory) {
		this.interestCategory = interestCategory;
	}

	/**
	 * @return the sanctionedAmount
	 */
	public Money getSanctionedAmount() {
		return sanctionedAmount;
	}

	/**
	 * @param sanctionedAmount
	 *            the sanctionedAmount to set
	 */
	public void setSanctionedAmount(Money sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	/**
	 * @return the sanctionedDate
	 */
	public Date getSanctionedDate() {
		return sanctionedDate;
	}

	/**
	 * @param sanctionedDate
	 *            the sanctionedDate to set
	 */
	public void setSanctionedDate(Date sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}

	/**
	 * @return the sanctionAuthority
	 */
	public SanctionAuthority getSanctionAuthority() {
		return sanctionAuthority;
	}

	/**
	 * @param sanctionAuthority
	 *            the sanctionAuthority to set
	 */
	public void setSanctionAuthority(SanctionAuthority sanctionAuthority) {
		this.sanctionAuthority = sanctionAuthority;
	}

}
