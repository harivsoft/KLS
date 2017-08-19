package com.vsoftcorp.kls.subsidy.data;

import java.math.BigDecimal;

/**
 * 
 * @author a1623
 *
 */
public class SlabwisesubsidyInterestRateData {
	private Long id;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private BigDecimal subsidyRoiPerAnnum;
	private Long interestSubsidyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public BigDecimal getFromAmount() {
		return fromAmount;
	}
	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}
	public BigDecimal getToAmount() {
		return toAmount;
	}
	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}
	public BigDecimal getSubsidyRoiPerAnnum() {
		return subsidyRoiPerAnnum;
	}
	public void setSubsidyRoiPerAnnum(BigDecimal subsidyRoiPerAnnum) {
		this.subsidyRoiPerAnnum = subsidyRoiPerAnnum;
	}
	public Long getInterestSubsidyId() {
		return interestSubsidyId;
	}
	public void setInterestSubsidyId(Long interestSubsidyId) {
		this.interestSubsidyId = interestSubsidyId;
	}
	
	
	
}
