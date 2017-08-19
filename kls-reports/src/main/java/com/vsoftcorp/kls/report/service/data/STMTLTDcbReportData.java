package com.vsoftcorp.kls.report.service.data;

import java.math.BigDecimal;

/**
 * This class is used for ST/MT/LT DCB report
 * @author a1605
 *
 */

public class STMTLTDcbReportData {
	
	private String pacsName;
	
	private Integer pacsId;
	
	private String branchName;
	
	private Integer branchId;
	
	
	// *** For STAgri *** //
	
	private BigDecimal stAgriPricipleDmdArrears;
	
	private BigDecimal stAgriPrincipleDmdCurrent;
	
	private BigDecimal stAgriPrincipleCollectionTotal;
	
	private BigDecimal stAgriPrincipleCollectionInAdvance;
	
	private BigDecimal stAgriIntDmdArrears;
	
	private BigDecimal stAgriIntDmdCurrent;
	
	private BigDecimal stAgriIntCollectionTotal;
	
	private BigDecimal stAgriIntCollectionInAdvance;
	
	private BigDecimal stAgriPercentage;
	
	// *** For MTLTAgri *** //
	
	private BigDecimal mtltAgriPricipleDmdArrears;
	
	private BigDecimal mtltAgriPrincipleDmdCurrent;
	
	private BigDecimal mtltAgriPrincipleCollectionTotal;
	
	private BigDecimal mtltAgriPrincipleCollectionInAdvance;
	
	private BigDecimal mtltAgriIntDmdArrears;
	
	private BigDecimal mtltAgriIntDmdCurrent;
	
	private BigDecimal mtltAgriIntCollectionTotal;
	
	private BigDecimal mtltAgriIntCollectionInAdvance;
	
	private BigDecimal mtltAgriPercentage;


	/**
	 * @return the pacsName
	 */
	public String getPacsName() {
		return pacsName;
	}

	/**
	 * @param pacsName the pacsName to set
	 */
	public void setPacsName(String pacsName) {
		this.pacsName = pacsName;
	}

	/**
	 * @return the pacsId
	 */
	public Integer getPacsId() {
		return pacsId;
	}

	/**
	 * @param pacsId the pacsId to set
	 */
	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the stAgriPricipleDmdArrears
	 */
	public BigDecimal getStAgriPricipleDmdArrears() {
		return stAgriPricipleDmdArrears;
	}

	/**
	 * @param stAgriPricipleDmdArrears the stAgriPricipleDmdArrears to set
	 */
	public void setStAgriPricipleDmdArrears(BigDecimal stAgriPricipleDmdArrears) {
		this.stAgriPricipleDmdArrears = stAgriPricipleDmdArrears;
	}

	/**
	 * @return the stAgriPrincipleDmdCurrent
	 */
	public BigDecimal getStAgriPrincipleDmdCurrent() {
		return stAgriPrincipleDmdCurrent;
	}

	/**
	 * @param stAgriPrincipleDmdCurrent the stAgriPrincipleDmdCurrent to set
	 */
	public void setStAgriPrincipleDmdCurrent(BigDecimal stAgriPrincipleDmdCurrent) {
		this.stAgriPrincipleDmdCurrent = stAgriPrincipleDmdCurrent;
	}

	/**
	 * @return the stAgriPrincipleCollectionTotal
	 */
	public BigDecimal getStAgriPrincipleCollectionTotal() {
		return stAgriPrincipleCollectionTotal;
	}

	/**
	 * @param stAgriPrincipleCollectionTotal the stAgriPrincipleCollectionTotal to set
	 */
	public void setStAgriPrincipleCollectionTotal(BigDecimal stAgriPrincipleCollectionTotal) {
		this.stAgriPrincipleCollectionTotal = stAgriPrincipleCollectionTotal;
	}

	/**
	 * @return the stAgriPrincipleCollectionInAdvance
	 */
	public BigDecimal getStAgriPrincipleCollectionInAdvance() {
		return stAgriPrincipleCollectionInAdvance;
	}

	/**
	 * @param stAgriPrincipleCollectionInAdvance the stAgriPrincipleCollectionInAdvance to set
	 */
	public void setStAgriPrincipleCollectionInAdvance(BigDecimal stAgriPrincipleCollectionInAdvance) {
		this.stAgriPrincipleCollectionInAdvance = stAgriPrincipleCollectionInAdvance;
	}

	/**
	 * @return the stAgriIntDmdArrears
	 */
	public BigDecimal getStAgriIntDmdArrears() {
		return stAgriIntDmdArrears;
	}

	/**
	 * @param stAgriIntDmdArrears the stAgriIntDmdArrears to set
	 */
	public void setStAgriIntDmdArrears(BigDecimal stAgriIntDmdArrears) {
		this.stAgriIntDmdArrears = stAgriIntDmdArrears;
	}

	/**
	 * @return the stAgriIntDmdCurrent
	 */
	public BigDecimal getStAgriIntDmdCurrent() {
		return stAgriIntDmdCurrent;
	}

	/**
	 * @param stAgriIntDmdCurrent the stAgriIntDmdCurrent to set
	 */
	public void setStAgriIntDmdCurrent(BigDecimal stAgriIntDmdCurrent) {
		this.stAgriIntDmdCurrent = stAgriIntDmdCurrent;
	}

	/**
	 * @return the stAgriIntCollectionTotal
	 */
	public BigDecimal getStAgriIntCollectionTotal() {
		return stAgriIntCollectionTotal;
	}

	/**
	 * @param stAgriIntCollectionTotal the stAgriIntCollectionTotal to set
	 */
	public void setStAgriIntCollectionTotal(BigDecimal stAgriIntCollectionTotal) {
		this.stAgriIntCollectionTotal = stAgriIntCollectionTotal;
	}

	/**
	 * @return the stAgriIntCollectionInAdvance
	 */
	public BigDecimal getStAgriIntCollectionInAdvance() {
		return stAgriIntCollectionInAdvance;
	}

	/**
	 * @param stAgriIntCollectionInAdvance the stAgriIntCollectionInAdvance to set
	 */
	public void setStAgriIntCollectionInAdvance(BigDecimal stAgriIntCollectionInAdvance) {
		this.stAgriIntCollectionInAdvance = stAgriIntCollectionInAdvance;
	}

	/**
	 * @return the mtltAgriPricipleDmdArrears
	 */
	public BigDecimal getMtltAgriPricipleDmdArrears() {
		return mtltAgriPricipleDmdArrears;
	}

	/**
	 * @param mtltAgriPricipleDmdArrears the mtltAgriPricipleDmdArrears to set
	 */
	public void setMtltAgriPricipleDmdArrears(BigDecimal mtltAgriPricipleDmdArrears) {
		this.mtltAgriPricipleDmdArrears = mtltAgriPricipleDmdArrears;
	}

	/**
	 * @return the mtltAgriPrincipleDmdCurrent
	 */
	public BigDecimal getMtltAgriPrincipleDmdCurrent() {
		return mtltAgriPrincipleDmdCurrent;
	}

	/**
	 * @param mtltAgriPrincipleDmdCurrent the mtltAgriPrincipleDmdCurrent to set
	 */
	public void setMtltAgriPrincipleDmdCurrent(BigDecimal mtltAgriPrincipleDmdCurrent) {
		this.mtltAgriPrincipleDmdCurrent = mtltAgriPrincipleDmdCurrent;
	}

	/**
	 * @return the mtltAgriPrincipleCollectionTotal
	 */
	public BigDecimal getMtltAgriPrincipleCollectionTotal() {
		return mtltAgriPrincipleCollectionTotal;
	}

	/**
	 * @param mtltAgriPrincipleCollectionTotal the mtltAgriPrincipleCollectionTotal to set
	 */
	public void setMtltAgriPrincipleCollectionTotal(BigDecimal mtltAgriPrincipleCollectionTotal) {
		this.mtltAgriPrincipleCollectionTotal = mtltAgriPrincipleCollectionTotal;
	}

	/**
	 * @return the mtltAgriPrincipleCollectionInAdvance
	 */
	public BigDecimal getMtltAgriPrincipleCollectionInAdvance() {
		return mtltAgriPrincipleCollectionInAdvance;
	}

	/**
	 * @param mtltAgriPrincipleCollectionInAdvance the mtltAgriPrincipleCollectionInAdvance to set
	 */
	public void setMtltAgriPrincipleCollectionInAdvance(BigDecimal mtltAgriPrincipleCollectionInAdvance) {
		this.mtltAgriPrincipleCollectionInAdvance = mtltAgriPrincipleCollectionInAdvance;
	}

	/**
	 * @return the mtltAgriIntDmdArrears
	 */
	public BigDecimal getMtltAgriIntDmdArrears() {
		return mtltAgriIntDmdArrears;
	}

	/**
	 * @param mtltAgriIntDmdArrears the mtltAgriIntDmdArrears to set
	 */
	public void setMtltAgriIntDmdArrears(BigDecimal mtltAgriIntDmdArrears) {
		this.mtltAgriIntDmdArrears = mtltAgriIntDmdArrears;
	}

	/**
	 * @return the mtltAgriIntDmdCurrent
	 */
	public BigDecimal getMtltAgriIntDmdCurrent() {
		return mtltAgriIntDmdCurrent;
	}

	/**
	 * @param mtltAgriIntDmdCurrent the mtltAgriIntDmdCurrent to set
	 */
	public void setMtltAgriIntDmdCurrent(BigDecimal mtltAgriIntDmdCurrent) {
		this.mtltAgriIntDmdCurrent = mtltAgriIntDmdCurrent;
	}

	/**
	 * @return the mtltAgriIntCollectionTotal
	 */
	public BigDecimal getMtltAgriIntCollectionTotal() {
		return mtltAgriIntCollectionTotal;
	}

	/**
	 * @param mtltAgriIntCollectionTotal the mtltAgriIntCollectionTotal to set
	 */
	public void setMtltAgriIntCollectionTotal(BigDecimal mtltAgriIntCollectionTotal) {
		this.mtltAgriIntCollectionTotal = mtltAgriIntCollectionTotal;
	}

	/**
	 * @return the mtltAgriIntCollectionInAdvance
	 */
	public BigDecimal getMtltAgriIntCollectionInAdvance() {
		return mtltAgriIntCollectionInAdvance;
	}

	/**
	 * @param mtltAgriIntCollectionInAdvance the mtltAgriIntCollectionInAdvance to set
	 */
	public void setMtltAgriIntCollectionInAdvance(BigDecimal mtltAgriIntCollectionInAdvance) {
		this.mtltAgriIntCollectionInAdvance = mtltAgriIntCollectionInAdvance;
	}

	/**
	 * @return the stAgriPercentage
	 */
	public BigDecimal getStAgriPercentage() {
		return stAgriPercentage;
	}

	/**
	 * @param stAgriPercentage the stAgriPercentage to set
	 */
	public void setStAgriPercentage(BigDecimal stAgriPercentage) {
		this.stAgriPercentage = stAgriPercentage;
	}

	/**
	 * @return the mtltAgriPercentage
	 */
	public BigDecimal getMtltAgriPercentage() {
		return mtltAgriPercentage;
	}

	/**
	 * @param mtltAgriPercentage the mtltAgriPercentage to set
	 */
	public void setMtltAgriPercentage(BigDecimal mtltAgriPercentage) {
		this.mtltAgriPercentage = mtltAgriPercentage;
	}
	

}
