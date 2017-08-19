package com.vsoftcorp.kls.data;

public class LoanSanctionData {

	private String customerName;

	private String customerId;
	
	private String memberNumber;

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	private String sanctionedAmount;

	private String sanctionRemarks;
	
	private String status;
	
	private String pacId;
	
	private String financialYear;
	
	private String id;
	
	private String insuranceAmount;
	
	private String tempSanctionAmount;
	
	private UserLoginDetailsData loggedInUserDetails;

	
	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(String sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public String getSanctionRemarks() {
		return sanctionRemarks;
	}

	public void setSanctionRemarks(String sanctionRemarks) {
		this.sanctionRemarks = sanctionRemarks;
	}

	public String getStatus() {
		return status;
	}

	public String getPacId() {
		return pacId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPacId(String pacId) {
		this.pacId = pacId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getTempSanctionAmount() {
		return tempSanctionAmount;
	}

	public void setTempSanctionAmount(String tempSanctionAmount) {
		this.tempSanctionAmount = tempSanctionAmount;
	}

	public UserLoginDetailsData getLoggedInUserDetails() {
		return loggedInUserDetails;
	}

	public void setLoggedInUserDetails(UserLoginDetailsData loggedInUserDetails) {
		this.loggedInUserDetails = loggedInUserDetails;
	}

}
