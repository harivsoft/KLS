/**
 * 
 */
package com.vsoftcorp.kls.business.entity.account;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.valuetypes.account.AccountType;
import com.vsoftcorp.kls.valuetypes.account.OperatingInstructionsType;
import com.vsoftcorp.kls.valuetypes.account.OperatorType;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
@TypeDefs({
		@TypeDef(name = "accountType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.account.AccountType"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "operatorType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.account.OperatorType"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "operatingInstructionsType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.account.OperatingInstructionsType"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }), })
@Entity
@Inheritance
@DiscriminatorColumn(name = "ACCOUNT_PROPERTY_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
@DiscriminatorValue("L")
// L for loans accounts.
@Table(name = "loan_account_property")
@NamedQueries({
		@NamedQuery(name = "LoanAccountProperty.findAll", query = "SELECT l FROM LoanAccountProperty l order by id"),
		@NamedQuery(name = "LoanAccountProperty.getTotalCount", query = "SELECT COUNT(l) FROM LoanAccountProperty l"), })
public class LoanAccountProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3896152683469736805L;

	@Id
	@GeneratedValue(generator = "loanAccountPropertyIdSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "loanAccountPropertyIdSeq", sequenceName = "loan_account_property_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "account_type", length = 2, nullable = true)
	@Enumerated(EnumType.STRING)
	@Type(type = "accountType")
	private AccountType accountType;

	@Column(name = "operator_type", length = 2, nullable = true)
	@Enumerated(EnumType.STRING)
	@Type(type = "operatorType")
	private OperatorType operatorType;

	@Column(name = "operating_instructions_type", length = 2, nullable = true)
	@Enumerated(EnumType.STRING)
	@Type(type = "operatingInstructionsType")
	private OperatingInstructionsType operatingInstructionsType;

	@Basic
	@Column(name = "resolution_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date resolutionDate;

	@Column(name = "resolution_number")
	private String resolutionNumber;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;

	@Basic
	@Column(name = "remarks", length = 60)
	private String remarks;

	@Basic
	@Column(name = "bank_code", length = 5, nullable = true)
	private String bankCode;

	@Basic
	@Column(name = "ACCOUNT_PROPERTY_TYPE", insertable = false, updatable = false)
	private String accountPropertyType;

	// today this is of no use, but going forward it will be used
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getAccountPropertyType() {
		return accountPropertyType;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setAccountPropertyType(String accountPropertyType) {
		this.accountPropertyType = accountPropertyType;
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
	 * @return the accountType
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the operatorType
	 */
	public OperatorType getOperatorType() {
		return operatorType;
	}

	/**
	 * @param operatorType
	 *            the operatorType to set
	 */
	public void setOperatorType(OperatorType operatorType) {
		this.operatorType = operatorType;
	}

	/**
	 * @return the operatingInstructionsType
	 */
	public OperatingInstructionsType getOperatingInstructionsType() {
		return operatingInstructionsType;
	}

	/**
	 * @param operatingInstructionsType
	 *            the operatingInstructionsType to set
	 */
	public void setOperatingInstructionsType(OperatingInstructionsType operatingInstructionsType) {
		this.operatingInstructionsType = operatingInstructionsType;
	}

	/**
	 * @return the resolutionDate
	 */
	public Date getResolutionDate() {
		return resolutionDate;
	}

	/**
	 * @param resolutionDate
	 *            the resolutionDate to set
	 */
	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	/**
	 * @return the resolutionNumber
	 */
	public String getResolutionNumber() {
		return resolutionNumber;
	}

	/**
	 * @param resolutionNumber
	 *            the resolutionNumber to set
	 */
	public void setResolutionNumber(String resolutionNumber) {
		this.resolutionNumber = resolutionNumber;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
