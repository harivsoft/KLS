package com.vosftcorp.kls.report.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IInterestChargedReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.InterestChargedReportData;
import com.vsoftcorp.kls.report.service.helper.InterestChargedReportHelper;

public class InterestChargedReportService implements IInterestChargedReportService {
	private static final Logger logger = Logger
			.getLogger(InterestChargedReportService.class);

	@Override
	public List<InterestChargedReportData> getInterestChargedDataReport(
			Integer pacsId, Integer productId, Integer purposeId,
			Integer branchId, String productType, String fromDateString,
			String toDateString) {
		
		logger.info("Enter: Insidet the InterestChargedReportService in getInterestChargedDataReport() method.");
		
		List<Object[]> getInterestCharged = KLSReportDataAccessFactory.getInterestChargedReportDAO().getInterestChargedDataReport(pacsId, productId, purposeId, branchId, productType, fromDateString, toDateString);
		
		System.out.println("size in service==="+getInterestCharged.size());
		
		List<InterestChargedReportData> getInterestChargedReportDataList = InterestChargedReportHelper.getInterestChargedList(getInterestCharged, fromDateString, toDateString);
		
		logger.info("End: Insidet the InterestChargedReportService in getInterestChargedDataReport() method.");
		
		return getInterestChargedReportDataList;
	}

}
