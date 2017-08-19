package com.vsoftcorp.kls.data;

import java.util.List;

public class LoanProductDocumentMappingData {
	private String loanType;
	private Integer productId;
	private List<LoanDocumentData> loanDocumentsList;
	private List<Long> deleteDocumentsList;

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<LoanDocumentData> getLoanDocumentsList() {
		return loanDocumentsList;
	}

	public void setLoanDocumentsList(List<LoanDocumentData> loanDocumentsList) {
		this.loanDocumentsList = loanDocumentsList;
	}

	public List<Long> getDeleteDocumentsList() {
		return deleteDocumentsList;
	}

	public void setDeleteDocumentsList(List<Long> deleteDocumentsList) {
		this.deleteDocumentsList = deleteDocumentsList;
	}
}
