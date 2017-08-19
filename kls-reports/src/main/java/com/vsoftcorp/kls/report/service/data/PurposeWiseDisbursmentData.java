package com.vsoftcorp.kls.report.service.data;

import java.math.BigDecimal;

/**
 * 
 * @author a1605
 *
 */
public class PurposeWiseDisbursmentData {

	String branchName;

	String pacName;
	
	String address;

	String purposeName;

	String productName;

	Integer productId;

	Integer branchId;

	Integer pacsId;

	Integer purposeId;
	
	String activityName;

	BigDecimal sfmfAmount;

	BigDecimal othersAmount;

	BigDecimal womanAmount;

	BigDecimal scstAmount;

	Integer sfmfCount;

	Integer scstCount;

	Integer othersCount;

	Integer womanCount;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPacName() {
		return pacName;
	}

	public void setPacName(String pacName) {
		this.pacName = pacName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPurposeName() {
		return purposeName;
	}

	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getPacsId() {
		return pacsId;
	}

	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	public Integer getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(Integer purposeId) {
		this.purposeId = purposeId;
	}

	public BigDecimal getSfmfAmount() {
		return sfmfAmount;
	}

	public void setSfmfAmount(BigDecimal sfmfAmount) {
		this.sfmfAmount = sfmfAmount;
	}

	public BigDecimal getOthersAmount() {
		return othersAmount;
	}

	public void setOthersAmount(BigDecimal othersAmount) {
		this.othersAmount = othersAmount;
	}

	public BigDecimal getWomanAmount() {
		return womanAmount;
	}

	public void setWomanAmount(BigDecimal womanAmount) {
		this.womanAmount = womanAmount;
	}

	public BigDecimal getScstAmount() {
		return scstAmount;
	}

	public void setScstAmount(BigDecimal scstAmount) {
		this.scstAmount = scstAmount;
	}

	public Integer getSfmfCount() {
		return sfmfCount;
	}

	public void setSfmfCount(Integer sfmfCount) {
		this.sfmfCount = sfmfCount;
	}

	public Integer getScstCount() {
		return scstCount;
	}

	public void setScstCount(Integer scstCount) {
		this.scstCount = scstCount;
	}

	public Integer getOthersCount() {
		return othersCount;
	}

	public void setOthersCount(Integer othersCount) {
		this.othersCount = othersCount;
	}

	public Integer getWomanCount() {
		return womanCount;
	}

	public void setWomanCount(Integer womanCount) {
		this.womanCount = womanCount;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

}
