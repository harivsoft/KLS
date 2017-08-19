package com.vsoftcorp.kls.data;


public class SBAccountMappingData {
	
	private Long partyId;
	private Long pacsId;
	private String businessDate;
	private Integer status;
	private String savingsAccountNo;
	private String remarks;
	
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	public Long getPacsId() {
		return pacsId;
	}
	public void setPacsId(Long pacsId) {
		this.pacsId = pacsId;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSavingsAccountNo() {
		return savingsAccountNo;
	}
	public void setSavingsAccountNo(String savingsAccountNo) {
		this.savingsAccountNo = savingsAccountNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}
