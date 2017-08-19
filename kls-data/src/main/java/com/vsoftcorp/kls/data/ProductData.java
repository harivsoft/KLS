package com.vsoftcorp.kls.data;

import java.math.BigDecimal;

/**
 * @author a9153
 * 
 *         This Class is pojo for the product entiy.
 * 
 */

public class ProductData {

	public ProductData() {
	}

	private String id;

	private String productTypeId;

	private String productType;

	private String productTypeDesc;

	private String name;

	private String shortName;

	private String releasedDate;

	private String releasedFlag;

	private String atmApplicable;

	private String lastIntPostDate;

	private String intrCategoryId;

	private String intrCategoryDesc;

	private String sharePercentage;

	private String isPenalInterestAllowed;

	private String penalInterestOn;

	private String recoverySequence;

	private String recoverySequenceId;

	private String disbursementType;

	private Integer minPeriod;

	private Integer maxPeriod;

	private Integer moratoriumPrinciplePeriod;

	private Integer moratoriumInterestPeriod;

	private String moratoriumPeriodIncludedInLoan;

	private String loanProductCode;

	private String repaymentType;

	private String repaymentSchedule;

	private String interestCalcMethod;

	private String interestPostFreq;

	private String penalInterestApplicable;

	private String interestType;

	private String guarantorsReqd;

	private String docRequired;

	private String insuranceRequired;

	private String securityRequired;

	private String subsidy;

	private BigDecimal processingFee;

	private Integer preClosureMinPeriod;

	private Integer preClosureMinChargedPeriod;

	private Integer preClosureIntRecoverablePeriod;

	private Integer afterClosureMinPeriod;
	
	private Boolean asAndWhenImplemented;
	
	private BigDecimal maxShareAmount;
	
	private Integer schemeId;
	
	private String schemeName;
	
	private Integer borrowingProductId;
	
	private String borrowingRequired;
	
	private BorrowingProductData borrowingProductData;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductTypeDesc() {
		return productTypeDesc;
	}

	public void setProductTypeDesc(String productTypeDesc) {
		this.productTypeDesc = productTypeDesc;
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

	public String getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}

	public String getReleasedFlag() {
		return releasedFlag;
	}

	public void setReleasedFlag(String releasedFlag) {
		this.releasedFlag = releasedFlag;
	}

	public String getAtmApplicable() {
		return atmApplicable;
	}

	public void setAtmApplicable(String atmApplicable) {
		this.atmApplicable = atmApplicable;
	}

	public String getLastIntPostDate() {
		return lastIntPostDate;
	}

	public void setLastIntPostDate(String lastIntPostDate) {
		this.lastIntPostDate = lastIntPostDate;
	}

	public String getIntrCategoryId() {
		return intrCategoryId;
	}

	public void setIntrCategoryId(String intrCategoryId) {
		this.intrCategoryId = intrCategoryId;
	}

	public String getIntrCategoryDesc() {
		return intrCategoryDesc;
	}

	public void setIntrCategoryDesc(String intrCategoryDesc) {
		this.intrCategoryDesc = intrCategoryDesc;
	}

	public String getSharePercentage() {
		return sharePercentage;
	}

	public void setSharePercentage(String sharePercentage) {
		this.sharePercentage = sharePercentage;
	}

	public String getIsPenalInterestAllowed() {
		return isPenalInterestAllowed;
	}

	public String getPenalInterestOn() {
		return penalInterestOn;
	}

	public void setIsPenalInterestAllowed(String isPenalInterestAllowed) {
		this.isPenalInterestAllowed = isPenalInterestAllowed;
	}

	public void setPenalInterestOn(String penalInterestOn) {
		this.penalInterestOn = penalInterestOn;
	}

	public String getRecoverySequence() {
		return recoverySequence;
	}

	public void setRecoverySequence(String recoverySequence) {
		this.recoverySequence = recoverySequence;
	}

	public String getRecoverySequenceId() {
		return recoverySequenceId;
	}

	public void setRecoverySequenceId(String recoverySequenceId) {
		this.recoverySequenceId = recoverySequenceId;
	}

	/**
	 * @return the disbursementType
	 */
	public String getDisbursementType() {
		return disbursementType;
	}

	/**
	 * @param disbursementType
	 *            the disbursementType to set
	 */
	public void setDisbursementType(String disbursementType) {
		this.disbursementType = disbursementType;
	}

	/**
	 * @return the minPeriod
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
	public String getInterestCalcMethod() {
		return interestCalcMethod;
	}

	/**
	 * @param interestCalcMethod
	 *            the interestCalcMethod to set
	 */
	public void setInterestCalcMethod(String interestCalcMethod) {
		this.interestCalcMethod = interestCalcMethod;
	}

	/**
	 * @return the interestPostFreq
	 */
	public String getInterestPostFreq() {
		return interestPostFreq;
	}

	/**
	 * @param interestPostFreq
	 *            the interestPostFreq to set
	 */
	public void setInterestPostFreq(String interestPostFreq) {
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
	public void setPreClosureIntRecoverablePeriod(
			Integer preClosureIntRecoverablePeriod) {
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
	public void setMoratoriumPeriodIncludedInLoan(
			String moratoriumPeriodIncludedInLoan) {
		this.moratoriumPeriodIncludedInLoan = moratoriumPeriodIncludedInLoan;
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
	 * @return the repaymentType
	 */
	public String getRepaymentType() {
		return repaymentType;
	}

	/**
	 * @param repaymentType
	 *            the repaymentType to set
	 */
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	/**
	 * @return the repaymentSchedule
	 */
	public String getRepaymentSchedule() {
		return repaymentSchedule;
	}

	/**
	 * @param repaymentSchedule
	 *            the repaymentSchedule to set
	 */
	public void setRepaymentSchedule(String repaymentSchedule) {
		this.repaymentSchedule = repaymentSchedule;
	}

	/**
	 * @return the penalInterestApplicable
	 */
	public String getPenalInterestApplicable() {
		return penalInterestApplicable;
	}

	/**
	 * @param penalInterestApplicable
	 *            the penalInterestApplicable to set
	 */
	public void setPenalInterestApplicable(String penalInterestApplicable) {
		this.penalInterestApplicable = penalInterestApplicable;
	}

	/**
	 * @return the interestType
	 */
	public String getInterestType() {
		return interestType;
	}

	/**
	 * @param interestType
	 *            the interestType to set
	 */
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	public Boolean getAsAndWhenImplemented() {
		return asAndWhenImplemented;
	}

	public void setAsAndWhenImplemented(Boolean asAndWhenImplemented) {
		this.asAndWhenImplemented = asAndWhenImplemented;
	}

	public BigDecimal getMaxShareAmount() {
		return maxShareAmount;
	}

	public void setMaxShareAmount(BigDecimal maxShareAmount) {
		this.maxShareAmount = maxShareAmount;
	}

	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public Integer getBorrowingProductId() {
		return borrowingProductId;
	}

	public void setBorrowingProductId(Integer borrowingProductId) {
		this.borrowingProductId = borrowingProductId;
	}

	public BorrowingProductData getBorrowingProductData() {
		return borrowingProductData;
	}

	public void setBorrowingProductData(BorrowingProductData borrowingProductData) {
		this.borrowingProductData = borrowingProductData;
	}

	public String getBorrowingRequired() {
		return borrowingRequired;
	}

	public void setBorrowingRequired(String borrowingRequired) {
		this.borrowingRequired = borrowingRequired;
	}

}
