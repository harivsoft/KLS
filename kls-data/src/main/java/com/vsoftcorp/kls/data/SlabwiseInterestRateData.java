package com.vsoftcorp.kls.data;

import java.util.List;

public class SlabwiseInterestRateData {

	private Integer interestCategoryId;
	
	private String effectiveDate;

	private List<InterestSlab> interestSlabs;

	private List<DeleteSlab> deleteSlab;

	/**
	 * @return the interestCategoryId
	 */
	public Integer getInterestCategoryId() {
		return interestCategoryId;
	}

	/**
	 * @param interestCategoryId
	 *            the interestCategoryId to set
	 */
	public void setInterestCategoryId(Integer interestCategoryId) {
		this.interestCategoryId = interestCategoryId;
	}

	/**
	 * @return the interestSlabs
	 */
	public List<InterestSlab> getInterestSlabs() {
		return interestSlabs;
	}

	/**
	 * @param interestSlabs
	 *            the interestSlabs to set
	 */
	public void setInterestSlabs(List<InterestSlab> interestSlabs) {
		this.interestSlabs = interestSlabs;
	}

	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate
	 *            the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the deleteInterestSlabs
	 */
	public List<DeleteSlab> getDeleteInterestSlabs() {
		return deleteSlab;
	}

	/**
	 * @param deleteInterestSlabs
	 *            the deleteInterestSlabs to set
	 */
	public void setDeleteInterestSlabs(List<DeleteSlab> deleteInterestSlabs) {
		this.deleteSlab = deleteInterestSlabs;
	}

}
