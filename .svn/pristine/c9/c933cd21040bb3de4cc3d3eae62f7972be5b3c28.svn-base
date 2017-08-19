package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IInterestSubsidyReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.InterestSubsidyReportData;
import com.vsoftcorp.kls.report.service.helper.InterestSubsidyReportHelper;

public class InterestSubsidyReportService implements IInterestSubsidyReportService{
	private static final Logger logger = Logger
			.getLogger(InterestSubsidyReportService.class);
	@Override
	public List<InterestSubsidyReportData> getInterestSubsidyReport(
			Integer pacId, Long subsidySchemeId, String typeOfScheme) {

		logger.info("Start: inside method getAccountStatementReport()");
		List<InterestSubsidyReportData> inList = new ArrayList<InterestSubsidyReportData>();
		try {

			List<Map> interestSubsidyReportTransHistoryList = KLSReportDataAccessFactory
					.getInterestSubsidyReportDAO().getTransHistoryRecoveredLoansBySeasonDate(pacId,subsidySchemeId,typeOfScheme);
			
			inList.addAll(InterestSubsidyReportHelper
					.getInterestSubsidyReportDataList(interestSubsidyReportTransHistoryList,subsidySchemeId));
			

		} catch (Exception exception) {
			 exception.printStackTrace();
			logger.info("Error while generating Account Statement report");
		}

		logger.info("End: inside method getAccountStatementReport()");
		return inList;
	}


}
