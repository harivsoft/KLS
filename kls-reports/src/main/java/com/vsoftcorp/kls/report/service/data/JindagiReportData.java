package com.vsoftcorp.kls.report.service.data;

import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JindagiReportData {
	
	private String memberNumber;
	private String memberName;
	
	List<CustomerLandDetailsData> customerLandDetailsDataList;
	List<LandChargesData> landChargesDataList;
	List<TenantLandData> tenantLandDataList;
	List<RentLandData> rentLandDataList;
	
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
	public List<CustomerLandDetailsData> getCustomerLandDetailsDataList() {
		return customerLandDetailsDataList;
	}
	public void setCustomerLandDetailsDataList(
			List<CustomerLandDetailsData> customerLandDetailsDataList) {
		this.customerLandDetailsDataList = customerLandDetailsDataList;
	}
	public List<LandChargesData> getLandChargesDataList() {
		return landChargesDataList;
	}
	public void setLandChargesDataList(List<LandChargesData> landChargesDataList) {
		this.landChargesDataList = landChargesDataList;
	}
	public List<TenantLandData> getTenantLandDataList() {
		return tenantLandDataList;
	}
	public void setTenantLandDataList(List<TenantLandData> tenantLandDataList) {
		this.tenantLandDataList = tenantLandDataList;
	}
	public List<RentLandData> getRentLandDataList() {
		return rentLandDataList;
	}
	public void setRentLandDataList(List<RentLandData> rentLandDataList) {
		this.rentLandDataList = rentLandDataList;
	}
}
