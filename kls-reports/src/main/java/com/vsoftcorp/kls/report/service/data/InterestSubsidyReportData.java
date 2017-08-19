package com.vsoftcorp.kls.report.service.data;

import java.math.BigDecimal;

public class InterestSubsidyReportData {
	
	private Integer pacsId;
	
	private String typeOfScheme;
	
	private String locNo;

	private String subsidySchemeId;

	private String subsidySchemeName;

	private String period;
	
	private Integer numOfAccountsInSlab;
	
	private BigDecimal amountInSlab;
	
	private BigDecimal subsidyAmtInSlab;
	
	private BigDecimal subventionAmtInSlab;
	
	private String fromSlab;
	
	private String toSlab;

	public Integer getPacsId() {
		return pacsId;
	}

	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	public String getTypeOfScheme() {
		return typeOfScheme;
	}

	public void setTypeOfScheme(String typeOfScheme) {
		this.typeOfScheme = typeOfScheme;
	}

	public String getLocNo() {
		return locNo;
	}

	public void setLocNo(String locNo) {
		this.locNo = locNo;
	}

	public String getSubsidySchemeId() {
		return subsidySchemeId;
	}

	public void setSubsidySchemeId(String subsidySchemeId) {
		this.subsidySchemeId = subsidySchemeId;
	}

	public String getSubsidySchemeName() {
		return subsidySchemeName;
	}

	public void setSubsidySchemeName(String subsidySchemeName) {
		this.subsidySchemeName = subsidySchemeName;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Integer getNumOfAccountsInSlab() {
		return numOfAccountsInSlab;
	}

	public void setNumOfAccountsInSlab(Integer numOfAccountsInSlab) {
		this.numOfAccountsInSlab = numOfAccountsInSlab;
	}

	public BigDecimal getAmountInSlab() {
		return amountInSlab;
	}

	public void setAmountInSlab(BigDecimal amountInSlab) {
		this.amountInSlab = amountInSlab;
	}

	public BigDecimal getSubsidyAmtInSlab() {
		return subsidyAmtInSlab;
	}

	public void setSubsidyAmtInSlab(BigDecimal subsidyAmtInSlab) {
		this.subsidyAmtInSlab = subsidyAmtInSlab;
	}

	public BigDecimal getSubventionAmtInSlab() {
		return subventionAmtInSlab;
	}

	public void setSubventionAmtInSlab(BigDecimal subventionAmtInSlab) {
		this.subventionAmtInSlab = subventionAmtInSlab;
	}

	public String getFromSlab() {
		return fromSlab;
	}

	public void setFromSlab(String fromSlab) {
		this.fromSlab = fromSlab;
	}

	public String getToSlab() {
		return toSlab;
	}

	public void setToSlab(String toSlab) {
		this.toSlab = toSlab;
	}
	

}
