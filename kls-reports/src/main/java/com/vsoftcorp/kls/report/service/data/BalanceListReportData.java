package com.vsoftcorp.kls.report.service.data;

import java.math.BigDecimal;

/**
 * 
 * @author a1605
 * 
 */
public class BalanceListReportData {

	private Long memberId;

	private String memberName;

	private Integer productId;

	private String productName;

	private String accNo;

	private Long locNo;

	private String cropOrPurposeName;

	private BigDecimal principleBal;

	private BigDecimal intBal;

	private BigDecimal chargesBal;

	private String villageName;

	private String shareBal;

	/**
	 * @return the memberId
	 */
	public Long getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the accNo
	 */
	public String getAccNo() {
		return accNo;
	}

	/**
	 * @param accNo the accNo to set
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	/**
	 * @return the locNo
	 */
	public Long getLocNo() {
		return locNo;
	}

	/**
	 * @param locNo the locNo to set
	 */
	public void setLocNo(Long locNo) {
		this.locNo = locNo;
	}

	/**
	 * @return the cropOrPurposeName
	 */
	public String getCropOrPurposeName() {
		return cropOrPurposeName;
	}

	/**
	 * @param cropOrPurposeName the cropOrPurposeName to set
	 */
	public void setCropOrPurposeName(String cropOrPurposeName) {
		this.cropOrPurposeName = cropOrPurposeName;
	}

	/**
	 * @return the principleBal
	 */
	public BigDecimal getPrincipleBal() {
		return principleBal;
	}

	/**
	 * @param principleBal the principleBal to set
	 */
	public void setPrincipleBal(BigDecimal principleBal) {
		this.principleBal = principleBal;
	}

	/**
	 * @return the intBal
	 */
	public BigDecimal getIntBal() {
		return intBal;
	}

	/**
	 * @param intBal the intBal to set
	 */
	public void setIntBal(BigDecimal intBal) {
		this.intBal = intBal;
	}

	/**
	 * @return the chargesBal
	 */
	public BigDecimal getChargesBal() {
		return chargesBal;
	}

	/**
	 * @param chargesBal the chargesBal to set
	 */
	public void setChargesBal(BigDecimal chargesBal) {
		this.chargesBal = chargesBal;
	}

	/**
	 * @return the villageName
	 */
	public String getVillageName() {
		return villageName;
	}

	/**
	 * @param villageName the villageName to set
	 */
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	/**
	 * @return the shareBal
	 */
	public String getShareBal() {
		return shareBal;
	}

	/**
	 * @param shareBal the shareBal to set
	 */
	public void setShareBal(String shareBal) {
		this.shareBal = shareBal;
	}
}
