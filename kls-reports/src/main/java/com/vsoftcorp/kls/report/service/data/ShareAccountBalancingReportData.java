package com.vsoftcorp.kls.report.service.data;

import java.math.BigDecimal;

public class ShareAccountBalancingReportData {
	private Integer shareProductId;

	private String shareProductName;

	private String memberNo;

	private String memberName;

	private String memberCode;

	private String shareAccountNumber;

	private String asOnDate;

	private Integer noOfShares;

	private BigDecimal valueOfShares;

	private Integer pacsId;
	
	private String pacName;

	/**
	 * @return the shareProductId
	 */
	public Integer getShareProductId() {
		return shareProductId;
	}

	/**
	 * @param shareProductId
	 *            the shareProductId to set
	 */
	public void setShareProductId(Integer shareProductId) {
		this.shareProductId = shareProductId;
	}

	/**
	 * @return the shareProductName
	 */
	public String getShareProductName() {
		return shareProductName;
	}

	/**
	 * @param shareProductName
	 *            the shareProductName to set
	 */
	public void setShareProductName(String shareProductName) {
		this.shareProductName = shareProductName;
	}

	/**
	 * @return the memberNo
	 */
	public String getMemberNo() {
		return memberNo;
	}

	/**
	 * @param memberNo
	 *            the memberNo to set
	 */
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName
	 *            the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the shareAccountNumber
	 */
	public String getShareAccountNumber() {
		return shareAccountNumber;
	}

	/**
	 * @param shareAccountNumber
	 *            the shareAccountNumber to set
	 */
	public void setShareAccountNumber(String shareAccountNumber) {
		this.shareAccountNumber = shareAccountNumber;
	}


	/**
	 * @return the asOnDate
	 */
	public String getAsOnDate() {
		return asOnDate;
	}

	/**
	 * @param asOnDate the asOnDate to set
	 */
	public void setAsOnDate(String asOnDate) {
		this.asOnDate = asOnDate;
	}

	/**
	 * @return the noOfShares
	 */
	public Integer getNoOfShares() {
		return noOfShares;
	}

	/**
	 * @param noOfShares
	 *            the noOfShares to set
	 */
	public void setNoOfShares(Integer noOfShares) {
		this.noOfShares = noOfShares;
	}

	/**
	 * @return the valueOfShares
	 */
	public BigDecimal getValueOfShares() {
		return valueOfShares;
	}

	/**
	 * @param valueOfShares
	 *            the valueOfShares to set
	 */
	public void setValueOfShares(BigDecimal valueOfShares) {
		this.valueOfShares = valueOfShares;
	}

	/**
	 * @return the pacsId
	 */
	public Integer getPacsId() {
		return pacsId;
	}

	/**
	 * @param pacsId
	 *            the pacsId to set
	 */
	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	/**
	 * @return the pacName
	 */
	public String getPacName() {
		return pacName;
	}

	/**
	 * @param pacName the pacName to set
	 */
	public void setPacName(String pacName) {
		this.pacName = pacName;
	}

}
