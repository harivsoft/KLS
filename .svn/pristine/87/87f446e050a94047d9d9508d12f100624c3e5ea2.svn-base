package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IDeceasedReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.DeceasedReportData;
import com.vsoftcorp.kls.report.service.data.LandRegisterReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.DeceasedReportHelper;
import com.vsoftcorp.kls.report.service.helper.LandRegisterReportHelper;

public class DeceasedReportService implements IDeceasedReportService {

	private static final Logger logger = Logger
			.getLogger(DeceasedReportService.class);

	@Override
	public List<DeceasedReportData> getDeceasedDetails(Integer pacsId,String date) {
		
		logger.info("Start: Calling getDeceasedDetailsDAO inside getDeceasedDetails()");
		List<DeceasedReportData> deceasedDataList = new ArrayList<DeceasedReportData>();
		try {
			List<Object[]> deceasedList = KLSReportDataAccessFactory.getDeceasedReportDAO().getDeceasedDetails(pacsId,date);
			deceasedDataList=DeceasedReportHelper.getDeceasedReportData(deceasedList);
			
		} catch (Exception exception) {
			logger.info("Error:Inside getDeceasedDetails method");
			throw new KlsReportRuntimeException(
					"Can not print DeceasedDetails Report:", exception.getCause());
		}
		logger.info("End: Calling getDeceasedDetailsDAO inside getDeceasedDetails()");
		return deceasedDataList;
	}

}
