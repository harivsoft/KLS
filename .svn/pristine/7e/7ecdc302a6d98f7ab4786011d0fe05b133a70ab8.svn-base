package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;
import java.math.BigDecimal;

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
import com.vsoftcorp.kls.valuetypes.loanproduct.DisbursementType;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestCalculationMethod;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestPostingFrequency;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestType;
import com.vsoftcorp.kls.valuetypes.loanproduct.PenalInterestApplicable;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentSchedule;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentType;
import com.vsoftcorp.time.Date;

@TypeDefs({
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }),
		@TypeDef(name = "disbursementType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loanproduct.DisbursementType"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "repaymentType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentType"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "repaymentSchedule", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentSchedule"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "interestCalcMethod", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loanproduct.InterestCalculationMethod"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "interestPostFreq", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loanproduct.InterestPostingFrequency"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
				@Parameter(name = "defaultISOCurrencyCode", value = "INR"), @Parameter(name = "isDebitNegative", value = "true") }),
		@TypeDef(name = "penalInterestApplicable", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loanproduct.PenalInterestApplicable"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "interestType", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.loanproduct.InterestType"),
				@Parameter(name = "identifierMethod", value = "getValue"), @Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }) })
@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

	private static final long serialVersionUID = 6067921645190702550L;

	public Product() {
	}

	@Id
	@SequenceGenerator(name = "productIdSeq", sequenceName = "product_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productIdSeq")
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "NAME", length = 45)
	private String name;

	@Basic
	@Column(name = "SHORT_NAME", length = 45)
	private String shortName;

	@Basic
	@Column(name = "RELEASED", length = 1)
	private String releasedFlag;

	@Basic
	@Column(name = "RELEASED_DATE")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date releasedDate;

	@Basic
	@Column(name = "LAST_INT_POST_DATE")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date lastIntPostDate;

	@Basic
	@Column(name = "ATM_APPLICABLE", length = 1)
	private String atmApplicable;

	@ManyToOne(optional = false)
	@JoinColumn(name = "interest_category_id", referencedColumnName = "ID")
	private InterestCategory intrCategory;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_type_id", referencedColumnName = "ID")
	private ProductType productType;

	@Basic
	@Column(name = "share_percentage", precision = 5, scale = 2)
	private BigDecimal sharePercentage;

	@Basic
	@Column(name = "is_penal_interest_allowed", length = 1)
	private String isPenalInterestAllowed;

	@Basic
	@Column(name = "penal_interest_on", length = 2)
	private String penalInterestOn;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "recovery_sequence_id", referencedColumnName = "id")
	private EventTypeDefinition recoverySequence;

	@Column(name = "disbursement_type", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "disbursementType")
	private DisbursementType disbursementType;

	@Basic
	@Column(name = "min_period")
	private Integer minPeriod;

	@Basic
	@Column(name = "max_period")
	private Integer maxPeriod;

	@Basic
	@Column(name = "moratorium_principle_period")
	private Integer moratoriumPrinciplePeriod;

	@Basic
	@Column(name = "moratorium_interest_period")
	private Integer moratoriumInterestPeriod;

	@Basic
	@Column(name = "moratorium_period_incl_in_loan", length = 1)
	private String moratoriumPeriodIncludedInLoan;

	@Basic
	@Column(name = "loan_product_code", length = 6)
	private String loanProductCode;

	@Column(name = "repayment_type", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "repaymentType")
	private RepaymentType repaymentType;

	@Column(name = "repayment_schedule", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "repaymentSchedule")
	private RepaymentSchedule repaymentSchedule;

	@Column(name = "interest_calc_method", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "interestCalcMethod")
	private InterestCalculationMethod interestCalcMethod;

	@Column(name = "interest_post_frequency", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "interestPostFreq")
	private InterestPostingFrequency interestPostFreq;

	@Column(name = "penal_interest_applicable", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "penalInterestApplicable")
	private PenalInterestApplicable penalInterestApplicable;

	@Column(name = "interest_type", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "interestType")
	private InterestType interestType;

	@Basic
	@Column(name = "guarantors_required", length = 1)
	private String guarantorsReqd;

	@Basic
	@Column(name = "doc_required", length = 1)
	private String docRequired;

	@Basic
	@Column(name = "insurance_required", length = 1)
	private String insuranceRequired;

	@Basic
	@Column(name = "security_required", length = 1)
	private String securityRequired;

	@Basic
	@Column(name = "subsidy", length = 1)
	private String subsidy;

	@Basic
	@Column(name = "processing_fee", precision = 22, scale = 6)
	private BigDecimal processingFee;

	@Basic
	@Column(name = "pre_closure_min_period")
	private Integer preClosureMinPeriod;

	@Basic
	@Column(name = "pre_closure_min__charged_period")
	private Integer preClosureMinChargedPeriod;

	@Basic
	@Column(name = "pre_closure_int_recoverable_period")
	private Integer preClosureIntRecoverablePeriod;

	@Basic
	@Column(name = "after_closure_min_period")
	private Integer afterClosureMinPeriod;

	@Basic
	@Column(name = "max_share_amount", precision = 22, scale = 6)
	@Type(type = "money")
	private Money maxShareAmount;

	@Basic
	@Column(name = "as_and_when_implemented")
	private Boolean asAndWhenImplemented;

	@Basic
	@Column(name = "scheme_id")
	//@JoinColumn(name = "scheme_id", referencedColumnName = "ID")
	private Integer schemeId;
	
	@Basic
	@Column(name = "borrowing_product_id")
	private Integer borrowingProductId;
	
	@Basic
	@Column(name = "borrowing_required")
	private String borrowingRequired;
	
	public String getBorrowingRequired() {
		return borrowingRequired;
	}

	public void setBorrowingRequired(String borrowingRequired) {
		this.borrowingRequired = borrowingRequired;
	}

	public Integer getBorrowingProductId() {
		return borrowingProductId;
	}

	public void setBorrowingProductId(Integer borrowingProductId) {
		this.borrowingProductId = borrowingProductId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getReleasedFlag() {
		return releasedFlag;
	}

	public void setReleasedFlag(String releasedFlag) {
		this.releasedFlag = releasedFlag;
	}

	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}

	public Date getLastIntPostDate() {
		return lastIntPostDate;
	}

	public void setLastIntPostDate(Date lastIntPostDate) {
		this.lastIntPostDate = lastIntPostDate;
	}

	public String getAtmApplicable() {
		return atmApplicable;
	}

	public void setAtmApplicable(String atmApplicable) {
		this.atmApplicable = atmApplicable;
	}

	public InterestCategory getIntrCategory() {
		return intrCategory;
	}

	public void setIntrCategory(InterestCategory intrCategory) {
		this.intrCategory = intrCategory;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public BigDecimal getSharePercentage() {
		return sharePercentage;
	}

	public void setSharePercentage(BigDecimal sharePercentage) {
		this.sharePercentage = sharePercentage;
	}

	public String getIsPenalInterestAllowed() {
		return isPenalInterestAllowed;
	}

	public void setIsPenalInterestAllowed(String isPenalInterestAllowed) {
		this.isPenalInterestAllowed = isPenalInterestAllowed;
	}

	public String getPenalInterestOn() {
		return penalInterestOn;
	}

	public void setPenalInterestOn(String penalInterestOn) {
		this.penalInterestOn = penalInterestOn;
	}

	public EventTypeDefinition getRecoverySequence() {
		return recoverySequence;
	}

	public void setRecoverySequence(EventTypeDefinition recoverySequence) {
		this.recoverySequence = recoverySequence;
	}

	/**
	 * @return the disbursementType
	 */
	public DisbursementType getDisbursementType() {
		return disbursementType;
	}

	/**
	 * @param disbursementType
	 *            the disbursementType to set
	 */
	public void setDisbursementType(DisbursementType disbursementType) {
		this.disbursementType = disbursementType;
	}

	/**
	 * @return the minPeriodpublic String getPenelIntReceivedGL() { return
	 *         penelIntReceivedGL; }
	 */
	public Integer getMinPeriod() {
		return minPeriod;
	}

	/**
	 * @param minPeriod
	 *            the minPeriod to set
	 */
	public void setMinPeriod(Integer minPeriod) {
		this.minPeriod = minPeriod;
	}

	/**
	 * @return the maxPeriod
	 */
	public Integer getMaxPeriod() {
		return maxPeriod;
	}

	/**
	 * @param maxPeriod
	 *            the maxPeriod to set
	 */
	public void setMaxPeriod(Integer maxPeriod) {
		this.maxPeriod = maxPeriod;
	}

	/**
	 * @return the moratoriumPrinciplePeriod
	 */
	public Integer getMoratoriumPrinciplePeriod() {
		return moratoriumPrinciplePeriod;
	}

	/**
	 * @param moratoriumPrinciplePeriod
	 *            the moratoriumPrinciplePeriod to set
	 */
	public void setMoratoriumPrinciplePeriod(Integer moratoriumPrinciplePeriod) {
		this.moratoriumPrinciplePeriod = moratoriumPrinciplePeriod;
	}

	/**
	 * @return the moratoriumInterestPeriod
	 */
	public Integer getMoratoriumInterestPeriod() {
		return moratoriumInterestPeriod;
	}

	/**
	 * @param moratoriumInterestPeriod
	 *            the moratoriumInterestPeriod to set
	 */
	public void setMoratoriumInterestPeriod(Integer moratoriumInterestPeriod) {
		this.moratoriumInterestPeriod = moratoriumInterestPeriod;
	}

	/**
	 * @return the interestCalcMethod
	 */
	public InterestCalculationMethod getInterestCalcMethod() {
		return interestCalcMethod;
	}

	/**
	 * @param interestCalcMethod
	 *            the interestCalcMethod to set
	 */
	public void setInterestCalcMethod(InterestCalculationMethod interestCalcMethod) {
		this.interestCalcMethod = interestCalcMethod;
	}

	/**
	 * @return the interestPostFreq
	 */
	public InterestPostingFrequency getInterestPostFreq() {
		return interestPostFreq;
	}

	/**
	 * @param interestPostFreq
	 *            the interestPostFreq to set
	 */
	public void setInterestPostFreq(InterestPostingFrequency interestPostFreq) {
		this.interestPostFreq = interestPostFreq;
	}

	/**
	 * @return the guarantorsReqd
	 */
	public String getGuarantorsReqd() {
		return guarantorsReqd;
	}

	/**
	 * @param guarantorsReqd
	 *            the guarantorsReqd to set
	 */
	public void setGuarantorsReqd(String guarantorsReqd) {
		this.guarantorsReqd = guarantorsReqd;
	}

	/**
	 * @return the docRequired
	 */
	public String getDocRequired() {
		return docRequired;
	}

	/**
	 * @param docRequired
	 *            the docRequired to set
	 */
	public void setDocRequired(String docRequired) {
		this.docRequired = docRequired;
	}

	/**
	 * @return the insuranceRequired
	 */
	public String getInsuranceRequired() {
		return insuranceRequired;
	}

	/**
	 * @param insuranceRequired
	 *            the insuranceRequired to set
	 */
	public void setInsuranceRequired(String insuranceRequired) {
		this.insuranceRequired = insuranceRequired;
	}

	/**
	 * @return the securityRequired
	 */
	public String getSecurityRequired() {
		return securityRequired;
	}

	/**
	 * @param securityRequired
	 *            the securityRequired to set
	 */
	public void setSecurityRequired(String securityRequired) {
		this.securityRequired = securityRequired;
	}

	/**
	 * @return the subsidy
	 */
	public String getSubsidy() {
		return subsidy;
	}

	/**
	 * @param subsidy
	 *            the subsidy to set
	 */
	public void setSubsidy(String subsidy) {
		this.subsidy = subsidy;
	}

	/**
	 * @return the processingFee
	 */
	public BigDecimal getProcessingFee() {
		return processingFee;
	}

	/**
	 * @param processingFee
	 *            the processingFee to set
	 */
	public void setProcessingFee(BigDecimal processingFee) {
		this.processingFee = processingFee;
	}

	/**
	 * @return the preClosureMinPeriod
	 */
	public Integer getPreClosureMinPeriod() {
		return preClosureMinPeriod;
	}

	/**
	 * @param preClosureMinPeriod
	 *            the preClosureMinPeriod to set
	 */
	public void setPreClosureMinPeriod(Integer preClosureMinPeriod) {
		this.preClosureMinPeriod = preClosureMinPeriod;
	}

	/**
	 * @return the preClosureMinChargedPeriod
	 */
	public Integer getPreClosureMinChargedPeriod() {
		return preClosureMinChargedPeriod;
	}

	/**
	 * @param preClosureMinChargedPeriod
	 *            the preClosureMinChargedPeriod to set
	 */
	public void setPreClosureMinChargedPeriod(Integer preClosureMinChargedPeriod) {
		this.preClosureMinChargedPeriod = preClosureMinChargedPeriod;
	}

	/**
	 * @return the preClosureIntRecoverablePeriod
	 */
	public Integer getPreClosureIntRecoverablePeriod() {
		return preClosureIntRecoverablePeriod;
	}

	/**
	 * @param preClosureIntRecoverablePeriod
	 *            the preClosureIntRecoverablePeriod to set
	 */
	public void setPreClosureIntRecoverablePeriod(Integer preClosureIntRecoverablePeriod) {
		this.preClosureIntRecoverablePeriod = preClosureIntRecoverablePeriod;
	}

	/**
	 * @return the afterClosureMinPeriod
	 */
	public Integer getAfterClosureMinPeriod() {
		return afterClosureMinPeriod;
	}

	/**
	 * @param afterClosureMinPeriod
	 *            the afterClosureMinPeriod to set
	 */
	public void setAfterClosureMinPeriod(Integer afterClosureMinPeriod) {
		this.afterClosureMinPeriod = afterClosureMinPeriod;
	}

	/**
	 * @return the moratoriumPeriodIncludedInLoan
	 */
	public String getMoratoriumPeriodIncludedInLoan() {
		return moratoriumPeriodIncludedInLoan;
	}

	/**
	 * @param moratoriumPeriodIncludedInLoan
	 *            the moratoriumPeriodIncludedInLoan to set
	 */
	public void setMoratoriumPeriodIncludedInLoan(String moratoriumPeriodIncludedInLoan) {
		this.moratoriumPeriodIncludedInLoan = moratoriumPeriodIncludedInLoan;
	}

	/**
	 * @return the repaymentType
	 */
	public RepaymentType getRepaymentType() {
		return repaymentType;
	}

	/**
	 * @param repaymentType
	 *            the repaymentType to set
	 */
	public void setRepaymentType(RepaymentType repaymentType) {
		this.repaymentType = repaymentType;
	}

	/**
	 * @return the repaymentSchedule
	 */
	public RepaymentSchedule getRepaymentSchedule() {
		return repaymentSchedule;
	}

	/**
	 * @param repaymentSchedule
	 *            the repaymentSchedule to set
	 */
	public void setRepaymentSchedule(RepaymentSchedule repaymentSchedule) {
		this.repaymentSchedule = repaymentSchedule;
	}

	/**
	 * @return the penalInterestApplicable
	 */
	public PenalInterestApplicable getPenalInterestApplicable() {
		return penalInterestApplicable;
	}

	/**
	 * @param penalInterestApplicable
	 *            the penalInterestApplicable to set
	 */
	public void setPenalInterestApplicable(PenalInterestApplicable penalInterestApplicable) {
		this.penalInterestApplicable = penalInterestApplicable;
	}

	/**
	 * @return the interestType
	 */
	public InterestType getInterestType() {
		return interestType;
	}

	/**
	 * @param interestType
	 *            the interestType to set
	 */
	public void setInterestType(InterestType interestType) {
		this.interestType = interestType;
	}

	/**
	 * @return the loanProductCode
	 */
	public String getLoanProductCode() {
		return loanProductCode;
	}

	/**
	 * @param loanProductCode
	 *            the loanProductCode to set
	 */
	public void setLoanProductCode(String loanProductCode) {
		this.loanProductCode = loanProductCode;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Money getMaxShareAmount() {
		return maxShareAmount;
	}

	public void setMaxShareAmount(Money maxShareAmount) {
		this.maxShareAmount = maxShareAmount;
	}
	
	             
	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atmApplicable == null) ? 0 : atmApplicable.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((intrCategory == null) ? 0 : intrCategory.hashCode());
		result = prime * result + ((isPenalInterestAllowed == null) ? 0 : isPenalInterestAllowed.hashCode());
		result = prime * result + ((lastIntPostDate == null) ? 0 : lastIntPostDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((penalInterestOn == null) ? 0 : penalInterestOn.hashCode());
		result = prime * result + ((productType == null) ? 0 : productType.hashCode());
		result = prime * result + ((recoverySequence == null) ? 0 : recoverySequence.hashCode());
		result = prime * result + ((releasedDate == null) ? 0 : releasedDate.hashCode());
		result = prime * result + ((releasedFlag == null) ? 0 : releasedFlag.hashCode());
		result = prime * result + ((sharePercentage == null) ? 0 : sharePercentage.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (atmApplicable == null) {
			if (other.atmApplicable != null)
				return false;
		} else if (!atmApplicable.equals(other.atmApplicable))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (intrCategory == null) {
			if (other.intrCategory != null)
				return false;
		} else if (!intrCategory.equals(other.intrCategory))
			return false;
		if (isPenalInterestAllowed == null) {
			if (other.isPenalInterestAllowed != null)
				return false;
		} else if (!isPenalInterestAllowed.equals(other.isPenalInterestAllowed))
			return false;
		if (lastIntPostDate == null) {
			if (other.lastIntPostDate != null)
				return false;
		} else if (!lastIntPostDate.equals(other.lastIntPostDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (penalInterestOn == null) {
			if (other.penalInterestOn != null)
				return false;
		} else if (!penalInterestOn.equals(other.penalInterestOn))
			return false;
		if (productType == null) {
			if (other.productType != null)
				return false;
		} else if (!productType.equals(other.productType))
			return false;
		if (recoverySequence == null) {
			if (other.recoverySequence != null)
				return false;
		} else if (!recoverySequence.equals(other.recoverySequence))
			return false;
		if (releasedDate == null) {
			if (other.releasedDate != null)
				return false;
		} else if (!releasedDate.equals(other.releasedDate))
			return false;
		if (releasedFlag == null) {
			if (other.releasedFlag != null)
				return false;
		} else if (!releasedFlag.equals(other.releasedFlag))
			return false;
		if (sharePercentage == null) {
			if (other.sharePercentage != null)
				return false;
		} else if (!sharePercentage.equals(other.sharePercentage))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		return true;
	}

	/**
	 * @return the asAndWhenImplemented
	 */
	public Boolean getAsAndWhenImplemented() {
		return asAndWhenImplemented;
	}

	/**
	 * @param asAndWhenImplemented the asAndWhenImplemented to set
	 */
	public void setAsAndWhenImplemented(Boolean setAsAndWhenImplemented) {
		this.asAndWhenImplemented = setAsAndWhenImplemented;
	}
}
