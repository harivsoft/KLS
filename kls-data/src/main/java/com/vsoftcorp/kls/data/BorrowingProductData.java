package com.vsoftcorp.kls.data;

public class BorrowingProductData {

/**
 * @author danaiah
 * 
 *    
 */
	
	private Integer id;
	
	
	private String productName;
	
	private Integer productTypeId;
	
	private Integer schemeId;
	
	private String productCode;
		
	private String shortName;
	
	private String productType;
	
	private String productTypeDesc;

	private String releaseDate;
	
	private String nabardScheme;
	
	private  String borrowingType;
	
	private Integer minPeriod;
	
	private Integer maxPeriod;
	
	private String interestCategoryName;
	
	private Integer interestCategoryId;
	
	private String interestFrequency;
	
	private String glName;
	
	private String glCode;
	
	private String interestpayableGlCode;
	
	private String interestpaidGlCode;
	
	private String principleSubsidy;
	
	private String interstSubsidy;
	
	private  String bankInterestReceivableGL;
	private String  bankInterestReceivedGL;
	private String bankPenalInterestReceivableGL;
	private String bankPenalInterestReceivedGL;

	public Integer getId() {
		return id;
	}

	public String getProductTypeDesc() {
		return productTypeDesc;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProductTypeDesc(String productTypeDesc) {
		this.productTypeDesc = productTypeDesc;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getNabardScheme() {
		return nabardScheme;
	}

	public void setNabardScheme(String nabardScheme) {
		this.nabardScheme = nabardScheme;
	}

	public String getBorrowingType() {
		return borrowingType;
	}

	public void setBorrowingType(String borrowingType) {
		this.borrowingType = borrowingType;
	}

	

	public String getInterestFrequency() {
		return interestFrequency;
	}

	public Integer getMinPeriod() {
		return minPeriod;
	}

	public Integer getMaxPeriod() {
		return maxPeriod;
	}

	public void setMinPeriod(Integer minPeriod) {
		this.minPeriod = minPeriod;
	}

	public void setMaxPeriod(Integer maxPeriod) {
		this.maxPeriod = maxPeriod;
	}

	public void setInterestFrequency(String interestFrequency) {
		this.interestFrequency = interestFrequency;
	}

	public String getGlName() {
		return glName;
	}

	public void setGlName(String glName) {
		this.glName = glName;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public String getInterestpayableGlCode() {
		return interestpayableGlCode;
	}

	public void setInterestpayableGlCode(String interestpayableGlCode) {
		this.interestpayableGlCode = interestpayableGlCode;
	}

	public String getInterestpaidGlCode() {
		return interestpaidGlCode;
	}

	public void setInterestpaidGlCode(String interestpaidGlCode) {
		this.interestpaidGlCode = interestpaidGlCode;
	}

	public String getPrincipleSubsidy() {
		return principleSubsidy;
	}

	public void setPrincipleSubsidy(String principleSubsidy) {
		this.principleSubsidy = principleSubsidy;
	}

	public String getInterstSubsidy() {
		return interstSubsidy;
	}

	public void setInterstSubsidy(String interstSubsidy) {
		this.interstSubsidy = interstSubsidy;
	}

	public String getProductName() {
		return productName;
	}

	

	public String getInterestCategoryName() {
		return interestCategoryName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}

	

	public void setInterestCategoryName(String interestCategoryName) {
		this.interestCategoryName = interestCategoryName;
	}

	
	public Integer getInterestCategoryId() {
		return interestCategoryId;
	}

	public void setInterestCategoryId(Integer interestCategoryId) {
		this.interestCategoryId = interestCategoryId;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	public String getBankInterestReceivableGL() {
		return bankInterestReceivableGL;
	}

	public void setBankInterestReceivableGL(String bankInterestReceivableGL) {
		this.bankInterestReceivableGL = bankInterestReceivableGL;
	}

	public String getBankInterestReceivedGL() {
		return bankInterestReceivedGL;
	}

	public void setBankInterestReceivedGL(String bankInterestReceivedGL) {
		this.bankInterestReceivedGL = bankInterestReceivedGL;
	}

	public String getBankPenalInterestReceivableGL() {
		return bankPenalInterestReceivableGL;
	}

	public void setBankPenalInterestReceivableGL(
			String bankPenalInterestReceivableGL) {
		this.bankPenalInterestReceivableGL = bankPenalInterestReceivableGL;
	}

	public String getBankPenalInterestReceivedGL() {
		return bankPenalInterestReceivedGL;
	}

	public void setBankPenalInterestReceivedGL(
			String bankPenalInterestReceivedGL) {
		this.bankPenalInterestReceivedGL = bankPenalInterestReceivedGL;
	}

	

}
