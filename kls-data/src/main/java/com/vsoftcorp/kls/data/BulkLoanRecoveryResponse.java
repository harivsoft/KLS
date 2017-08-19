package com.vsoftcorp.kls.data;

import java.util.List;

public class BulkLoanRecoveryResponse {

	private boolean status;

	private List<BulkCustomerData> rejectedList;

	private String response;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<BulkCustomerData> getRejectedList() {
		return rejectedList;
	}

	public void setRejectedList(List<BulkCustomerData> rejectedList) {
		this.rejectedList = rejectedList;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
