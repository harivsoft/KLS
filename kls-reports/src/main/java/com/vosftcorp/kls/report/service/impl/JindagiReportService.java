package com.vosftcorp.kls.report.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IJindagiReportService;
import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.CustomerLandDetailsData;
import com.vsoftcorp.kls.report.service.data.JindagiReportData;
import com.vsoftcorp.kls.report.service.data.LandChargesData;
import com.vsoftcorp.kls.report.service.data.RentLandData;
import com.vsoftcorp.kls.report.service.data.TenantLandData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.JindagiReportHelper;

public class JindagiReportService implements IJindagiReportService {
	
	public static final Logger logger = Logger.getLogger(JindagiReportService.class);

	@Override
	public JindagiReportData getLandDetailsByCustomerId(Long customerId) {
		
		JindagiReportData jindagiReportData = new JindagiReportData();
		try {
			
			List<LandDetail> landDetailsList = KLSReportDataAccessFactory.getJindagiReportDAO().getLandDetailsListByCustomerId(customerId);
			
			List<CustomerLandDetailsData> customerLandDetailsDataList = JindagiReportHelper.getCustomerLandtDataList(landDetailsList);
			jindagiReportData.setCustomerLandDetailsDataList(customerLandDetailsDataList);
			
			List<LandChargesData> landChargesDataList = JindagiReportHelper.getLandChargesDataList(landDetailsList);
			jindagiReportData.setLandChargesDataList(landChargesDataList);
			
			List<TenantLandData> tenantLandDataList = JindagiReportHelper.getTenantLandDataList(KLSReportDataAccessFactory.getJindagiReportDAO().getTenantLandDetailsByCustomerId(customerId));
			jindagiReportData.setTenantLandDataList(tenantLandDataList);
			
			List<RentLandData> rentLandDataList = JindagiReportHelper.getRentedLandDataList(KLSReportDataAccessFactory.getJindagiReportDAO().getRentedDetailsByCustomerId(customerId));
			jindagiReportData.setRentLandDataList(rentLandDataList);
			
			Object[] cusObj = KLSReportDataAccessFactory.getJindagiReportDAO().getCustomerDetails(customerId.intValue());
			
			jindagiReportData.setMemberNumber(cusObj[0].toString() == null ? "N/A" : cusObj[0].toString());
			jindagiReportData.setMemberName(cusObj[1].toString() == null ? "N/A" : cusObj[1].toString());
			
			
			
			
		} catch (Exception exception) {
			logger.info("Error:Inside getLandDetailsByCustomerId method");
			throw new KlsReportRuntimeException(
					"Can not print getLandDetailsByCustomerId Report:", exception.getCause());
		}
		logger.info("End: Calling JindagiReportService inside getLandDetailsByCustomerId()");
		
		return jindagiReportData;
	}

}
