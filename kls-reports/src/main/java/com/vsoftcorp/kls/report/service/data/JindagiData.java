package com.vsoftcorp.kls.report.service.data;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JindagiData extends JindagiReportData {

	private JRBeanCollectionDataSource customerLandDetailsData;

	private JRBeanCollectionDataSource landChargesData;

	private JRBeanCollectionDataSource tenantLandData;

	private JRBeanCollectionDataSource rentLandData;

	public JRBeanCollectionDataSource getCustomerLandDetailsData() {
		return customerLandDetailsData;
	}

	public void setCustomerLandDetailsData(
			JRBeanCollectionDataSource customerLandDetailsData) {
		this.customerLandDetailsData = customerLandDetailsData;
	}

	public JRBeanCollectionDataSource getLandChargesData() {
		return landChargesData;
	}

	public void setLandChargesData(JRBeanCollectionDataSource landChargesData) {
		this.landChargesData = landChargesData;
	}

	public JRBeanCollectionDataSource getTenantLandData() {
		return tenantLandData;
	}

	public void setTenantLandData(JRBeanCollectionDataSource tenantLandData) {
		this.tenantLandData = tenantLandData;
	}

	public JRBeanCollectionDataSource getRentLandData() {
		return rentLandData;
	}

	public void setRentLandData(JRBeanCollectionDataSource rentLandData) {
		this.rentLandData = rentLandData;
	}

}
