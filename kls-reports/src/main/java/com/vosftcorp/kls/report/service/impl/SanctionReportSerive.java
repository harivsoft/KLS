package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.ISanctionReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.SanctionReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.SanctionReportHelper;

public class SanctionReportSerive implements ISanctionReportService {
	private static final Logger logger = Logger
			.getLogger(SanctionReportSerive.class);

	@Override
	public List<SanctionReportData> getSanctionReport(Integer memNumber,
			Long seasonId, String seasonYear, String pacsId) {
		logger.info("Start: Calling SanctionReportDAO inside getDrawalReport()");
		List<SanctionReportData> sanctionReportDataList = new ArrayList<SanctionReportData>();
		try {
			List<Map> sanctionReportList = KLSReportDataAccessFactory
					.getSanctionReportDAO().getSanctionReport(memNumber,
							seasonId, seasonYear, pacsId);

			sanctionReportDataList.addAll(SanctionReportHelper
					.getSanctionReportDataList(sanctionReportList));
		} catch (Exception exception) {
			logger.info("Error:Inside getSanctionReport method");
			throw new KlsReportRuntimeException(
					"Can not print Sanction Report:", exception.getCause());
		}
		logger.info("End: Calling SanctionReportDAO inside getSanctionReport()");
		return sanctionReportDataList;
	}

}
