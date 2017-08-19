package com.vsoftcorp.kls.data;

/**
 * @author a1565
 */

public class ActivityData {

	private Integer id;
	private String name;
	private String unitId;
	private String unitName;
	private String remarks;
	private String unitCost;
	private String MaxLimit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}

	public String getMaxLimit() {
		return MaxLimit;
	}

	public void setMaxLimit(String maxLimit) {
		MaxLimit = maxLimit;
	}

	public String getUnitId() {
		return unitId;
	}

	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * @param unitName
	 *            the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
