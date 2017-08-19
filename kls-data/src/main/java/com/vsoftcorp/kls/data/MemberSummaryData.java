package com.vsoftcorp.kls.data;


public class MemberSummaryData {

	private String memberName;
    private String familyHeadName;
    private Integer familyMembersCount;
    private Double totalLandInAcres;
    private String PDSCardType;
    private Integer sharesCount;
    private Double sharesValue;
    private String aadhaarCard;
    
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getFamilyHeadName() {
		return familyHeadName;
	}
	public void setFamilyHeadName(String familyHeadName) {
		this.familyHeadName = familyHeadName;
	}
	
	public Integer getFamilyMembersCount() {
		return familyMembersCount;
	}
	public void setFamilyMembersCount(Integer familyMembersCount) {
		this.familyMembersCount = familyMembersCount;
	}
	
	public Double getTotalLandInAcres() {
		return totalLandInAcres;
	}
	public void setTotalLandInAcres(Double totalLandInAcres) {
		this.totalLandInAcres = totalLandInAcres;
	}
	public String getPDSCardType() {
		return PDSCardType;
	}
	public void setPDSCardType(String pDSCardType) {
		PDSCardType = pDSCardType;
	}
	public Integer getSharesCount() {
		return sharesCount;
	}
	public void setSharesCount(Integer sharesCount) {
		this.sharesCount = sharesCount;
	}
	public Double getSharesValue() {
		return sharesValue;
	}
	public void setSharesValue(Double sharesValue) {
		this.sharesValue = sharesValue;
	}
	public String getAadhaarCard() {
		return aadhaarCard;
	}
	public void setAadhaarCard(String aadhaarCard) {
		this.aadhaarCard = aadhaarCard;
	}
	
    
	
}
