package com.vsoftcorp.kls.data;

import java.math.BigDecimal;

public class CustXLData {

	private Long locId;
	private String memberNumber;
	private String memberName;
	private BigDecimal recoverdAmount;
	private String remarks;
	
	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public BigDecimal getRecoverdAmount() {
		return recoverdAmount;
	}

	public void setRecoverdAmount(BigDecimal recoverdAmount) {
		this.recoverdAmount = recoverdAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
