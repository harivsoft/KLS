package com.vsoftcorp.kls.data;
/**
 * 
 * @author a1565
 *
 */
public class MappingData {
	
	private Integer purposeId;
	public Integer getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(Integer purposeId) {
		this.purposeId = purposeId;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getSubPurposeId() {
		return subPurposeId;
	}
	public void setSubPurposeId(Integer subPurposeId) {
		this.subPurposeId = subPurposeId;
	}
	public Integer getloanProductId() {
		return loanProductId;
	}
	public void setloanProductId(Integer loanProductId) {
		this.loanProductId = loanProductId;
	}
	public Integer getMappingId() {
		return mappingId;
	}
	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}
	private Integer activityId;
	private Integer subPurposeId;
	private Integer loanProductId;
	private Integer mappingId;
	private String remarks;
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
