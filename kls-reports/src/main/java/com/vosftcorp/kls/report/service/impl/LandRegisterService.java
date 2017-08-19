package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.ILandRegisterReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.LandRegisterReportData;
import com.vsoftcorp.kls.report.service.data.ShareAccountLedgerReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.LandRegisterReportHelper;
import com.vsoftcorp.kls.report.service.helper.ShareAccountLedgerReportHelper;

public class LandRegisterService implements ILandRegisterReportService {
	private static final Logger logger = Logger
			.getLogger(LandRegisterService.class);


	@Override
	public List<LandRegisterReportData> getLandRegisterDetails(Integer customerId,Integer pacsId) {
		logger.info("Start: Calling getLandRegisterDetails inside getLandRegisterDetails()");
		List<LandRegisterReportData> landRegisterReportDataList = new ArrayList<LandRegisterReportData>();
		try {
			List<Object[]> landList = KLSReportDataAccessFactory.getLandRegisterReportDAO().getLandRegisterDetails(customerId,pacsId);
			landRegisterReportDataList=LandRegisterReportHelper.getLandRegisterReportData(landList);
			
		} catch (Exception exception) {
			logger.info("Error:Inside getLandRegisterDetails method");
			throw new KlsReportRuntimeException(
					"Can not print getLandRegisterDetails Report:", exception.getCause());
		}
		logger.info("End: Calling getLandRegisterDetails inside getLandRegisterDetails()");
		return landRegisterReportDataList;
	}

}
