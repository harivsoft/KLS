package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IMasterReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.MasterReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.MasterReportHelper;

public class MasterReportService implements IMasterReportService{
	private static final Logger logger = Logger
			.getLogger(MasterReportService.class);
	@Override
	public List<MasterReportData> getMasterReport(String master) {
		logger.info("Start: Calling MasterReportDAO inside getMasterReport()");
		List<MasterReportData> masterReportDataList = new ArrayList<MasterReportData>();
		try {
			List<Map> masterReportList = KLSReportDataAccessFactory
					.getMasterReportDAO().getMasterReport(master);

			masterReportDataList.addAll(MasterReportHelper
					.getMasterReportDataList(masterReportList));
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getSanctionReport method");
			throw new KlsReportRuntimeException(
					"Can not print Sanction Report:", exception.getCause());
		}
		logger.info("End: Calling SanctionReportDAO inside getSanctionReport()");
		return masterReportDataList;
	}

}
