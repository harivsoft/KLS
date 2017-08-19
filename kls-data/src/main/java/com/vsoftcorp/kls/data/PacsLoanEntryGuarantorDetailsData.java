package com.vsoftcorp.kls.data;

import java.math.BigInteger;
/***
 * 
 * @author a1565
 *
 */
public class PacsLoanEntryGuarantorDetailsData {
	 private Long customerId;
	 private String gurantorName;
	 private String memberNumber;
	 private String occupation;
	 private Double annualIncome;
	 private String phoneNumber;
	 private String village;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getGurantorName() {
		return gurantorName;
	}

	public void setGurantorName(String gurantorName) {
		this.gurantorName = gurantorName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Double getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	/**
	 * @return the memberNumber
	 */
	public String getMemberNumber() {
		return memberNumber;
	}

	/**
	 * @param memberNumber the memberNumber to set
	 */
	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}
	
}
