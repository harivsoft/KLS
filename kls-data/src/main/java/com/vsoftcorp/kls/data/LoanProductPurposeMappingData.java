package com.vsoftcorp.kls.data;

import java.util.List;

public class LoanProductPurposeMappingData {

	private Integer productId;
	private Integer purposeId;
	private List<SubPurposeActivityData> subPurposeActivityList;
	private List<Long> deleteList;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(Integer purposeId) {
		this.purposeId = purposeId;
	}

	public List<SubPurposeActivityData> getSubPurposeActivityList() {
		return subPurposeActivityList;
	}

	public void setSubPurposeActivityList(
			List<SubPurposeActivityData> subPurposeActivityList) {
		this.subPurposeActivityList = subPurposeActivityList;
	}

	public List<Long> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<Long> deleteList) {
		this.deleteList = deleteList;
	}
}
