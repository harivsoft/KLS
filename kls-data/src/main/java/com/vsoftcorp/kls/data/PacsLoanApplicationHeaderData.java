package com.vsoftcorp.kls.data;



/**
 * @author a9153 
 * 
 * This Class is pojo for the product entiy.
 * 
 */

public class PacsLoanApplicationHeaderData {

	public PacsLoanApplicationHeaderData() {
	}

	private String id;
	
	private String  processStatus;
	
	private String  applicationType;
	
	private String applicationDate;
	
	private  String financialYear;
	
	private  String pacsId;
	
	private  String pacsName;
	
	private  String cropId;
	
	private  String schemeId;

	private String cropName;
	
	private String schemeName;
	
	private Integer productId;
	
	private String productName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}


	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getPacsId() {
		return pacsId;
	}

	public void setPacsId(String pacsId) {
		this.pacsId = pacsId;
	}

	public String getCropId() {
		return cropId;
	}

	public void setCropId(String cropId) {
		this.cropId = cropId;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getPacsName() {
		return pacsName;
	}

	public void setPacsName(String pacsName) {
		this.pacsName = pacsName;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
