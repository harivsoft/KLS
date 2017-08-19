package com.vsoftcorp.kls.business.entity.account;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
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

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;
import com.vsoftcorp.time.Date;

@TypeDefs({
		@TypeDef(name = "accountingMoney", typeClass = com.vsoftcorp.accounting.hibernate.types.AccountingMoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"), @Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "lineOfCreditStatus", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.LineOfCreditStatus"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"), @Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Inheritance
@DiscriminatorColumn(name = "LOAN_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
@DiscriminatorValue("C")
// C for crop loans. Always short term.
@Table(name = "line_of_credit")
@NamedQueries({
		@NamedQuery(name = "LineOfCredit.findAllActiveLOCs", query = "SELECT l FROM LineOfCredit l where l.lineOfCreditStatus=1 and l.account.accountProperty.pacs.id in (:pacs) order by l.id"),
		@NamedQuery(name = "LineOfCredit.getTotalCountOfActiveLOCs", query = "SELECT COUNT(l) FROM LineOfCredit l where l.lineOfCreditStatus=1 and l.account.accountProperty.pacs.id in (:pacs)") })
/*
 * @NamedQuery(name = "LineOfCredit.getTotalCountIntPosting", query =
 * "SELECT COUNT(l) FROM LineOfCredit l where l.interestAccrued!=0 or l.overdueInterest!=0 "
 * ), @NamedQuery(name = "LineOfCredit.getLocForIntPosting", query =
 * "SELECT l FROM LineOfCredit l where l.interestAccrued!=0 or l.overdueInterest!=0 "
 * ), })
 */
public class LineOfCredit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "locIdSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "locIdSeq", sequenceName = "loc_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = false  )
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;

	@ManyToOne(optional = true )
	@JoinColumn(name = "season_id", referencedColumnName = "id")
	private Season season;

	@ManyToOne(optional = true)
	@JoinColumn(name = "crop_id", referencedColumnName = "id")
	private Crop crop;

	@ManyToOne(optional = true )
	@JoinColumn(name = "scheme_id", referencedColumnName = "id")
	private Scheme scheme;

	@ManyToOne(optional = false )
	@JoinColumn(name = "interest_category_id", referencedColumnName = "id")
	private InterestCategory interestCategory;

	@Basic
	@Column(name = "drawal_priority")
	private Integer drawalPriority;

	@Basic
	@Column(name = "current_balance", precision = 22, scale = 6)
	@Type(type = "accountingMoney")
	private AccountingMoney currentBalance = AccountingMoney.ZERO;

	@Basic
	@Column(name = "sanctioned_limit", precision = 22, scale = 6)
	@Type(type = "money")
	private Money sanctionedAmount;

	@Basic
	@Column(name = "interest_accrued", precision = 22, scale = 6)
	private BigDecimal interestAccrued = BigDecimal.ZERO;

	@Basic
	@Column(name = "overdue_interest", precision = 22, scale = 6)
	private BigDecimal overdueInterest = BigDecimal.ZERO;

	@Basic
	@Column(name = "interest_receivable", precision = 22, scale = 6)
	private BigDecimal interestReceivable = BigDecimal.ZERO;

	@Basic
	@Column(name = "penal_interest_receivable", precision = 22, scale = 6)
	private BigDecimal penalInterestReceivable = BigDecimal.ZERO;
	@Basic
	@Column(name = "charges_receivable", precision = 22, scale = 6)
	private BigDecimal chargesReceivable = BigDecimal.ZERO;
	@Basic
	@Column(name = "last_int_cal_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date lastInterestCalculatedDate;

	@Basic
	@Column(name = "sanctioned_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date sanctionedDate;

	@Basic
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	@Type(type = "lineOfCreditStatus")
	private LineOfCreditStatus lineOfCreditStatus = LineOfCreditStatus.Active;

	@Basic
	@Column(name = "is_first_drawal", length = 1)
	private Integer isFirstDrawal = 0;

	@Basic
	@Column(name = "operating_cash_limit", precision = 22, scale = 6)
	@Type(type = "money")
	private Money operatingCashLimit;

	@Basic
	@Column(name = "kind_limit", precision = 22, scale = 6)
	@Type(type = "money")
	private Money kindLimit;

	@Basic
	@Column(name = "kind_balance", precision = 22, scale = 6)
	@Type(type = "accountingMoney")
	private AccountingMoney kindBalance = AccountingMoney.ZERO;

	@Basic
	@Column(name = "borrowings_loc_id")
	private Long borrowingsLocId;

	@ManyToOne(optional = true)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@Basic
	@Column(name = "loan_type", insertable = false, updatable = false)
	private String loanType;

	@Basic
	@Column(name = "customer_id")
	private Long customerId;
	
	@Basic
	@Column(name = "loan_exp_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date loanExpiryDate;

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public InterestCategory getInterestCategory() {
		return interestCategory;
	}

	public void setInterestCategory(InterestCategory interestCategory) {
		this.interestCategory = interestCategory;
	}

	public Integer getDrawalPriority() {
		return drawalPriority;
	}

	public void setDrawalPriority(Integer drawalPriority) {
		this.drawalPriority = drawalPriority;
	}

	public AccountingMoney getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(AccountingMoney currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Money getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(Money sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public BigDecimal getInterestAccrued() {
		return interestAccrued;
	}

	public void setInterestAccrued(BigDecimal interestAccrued) {
		this.interestAccrued = interestAccrued;
	}

	public BigDecimal getOverdueInterest() {
		return overdueInterest;
	}

	public void setOverdueInterest(BigDecimal overdueInterest) {
		this.overdueInterest = overdueInterest;
	}

	public BigDecimal getInterestReceivable() {
		return interestReceivable;
	}

	public void setInterestReceivable(BigDecimal interestReceivable) {
		this.interestReceivable = interestReceivable;
	}

	public BigDecimal getPenalInterestReceivable() {
		return penalInterestReceivable;
	}

	public void setPenalInterestReceivable(BigDecimal penalInterestReceivable) {
		this.penalInterestReceivable = penalInterestReceivable;
	}

	public Date getLastInterestCalculatedDate() {
		return lastInterestCalculatedDate;
	}

	public void setLastInterestCalculatedDate(Date lastInterestCalculatedDate) {
		this.lastInterestCalculatedDate = lastInterestCalculatedDate;
	}

	public Date getSanctionedDate() {
		return sanctionedDate;
	}

	public void setSanctionedDate(Date sanctionedDate) {
		this.sanctionedDate = sanctionedDate;
	}

	public LineOfCreditStatus getLineOfCreditStatus() {
		return lineOfCreditStatus;
	}

	public void setLineOfCreditStatus(LineOfCreditStatus lineOfCreditStatus) {
		this.lineOfCreditStatus = lineOfCreditStatus;
	}

	public Integer getIsFirstDrawal() {
		return isFirstDrawal;
	}

	public void setIsFirstDrawal(Integer isFirstDrawal) {
		this.isFirstDrawal = isFirstDrawal;
	}

	public Money getKindLimit() {
		return kindLimit;
	}

	public AccountingMoney getKindBalance() {
		return kindBalance;
	}

	public void setKindLimit(Money kindLimit) {
		this.kindLimit = kindLimit;
	}

	public void setKindBalance(AccountingMoney kindBalance) {
		this.kindBalance = kindBalance;
	}

	/**
	 * @return the chargesReceivable
	 */
	public BigDecimal getChargesReceivable() {
		return chargesReceivable;
	}

	/**
	 * @param chargesReceivable
	 *            the chargesReceivable to set
	 */
	public void setChargesReceivable(BigDecimal chargesReceivable) {
		this.chargesReceivable = chargesReceivable;
	}

	public Money getOperatingCashLimit() {
		return operatingCashLimit;
	}

	public void setOperatingCashLimit(Money operatingCashLimit) {
		this.operatingCashLimit = operatingCashLimit;
	}

	public Long getBorrowingsLocId() {
		return borrowingsLocId;
	}

	public void setBorrowingsLocId(Long borrowingsLocId) {
		this.borrowingsLocId = borrowingsLocId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	 
	public Date getLoanExpiryDate() {
		return loanExpiryDate;
	}

	public void setLoanExpiryDate(Date loanExpiryDate) {
		this.loanExpiryDate = loanExpiryDate;
	}
}