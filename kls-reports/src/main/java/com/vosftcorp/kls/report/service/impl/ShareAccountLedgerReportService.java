package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IShareAccountLedgerReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.ShareAccountLedgerReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.ShareAccountLedgerReportHelper;
import com.vsoftcorp.time.Date;


public class ShareAccountLedgerReportService implements IShareAccountLedgerReportService {
	private static final Logger logger = Logger
			.getLogger(ShareAccountLedgerReportService.class);

	@Override
	public List<ShareAccountLedgerReportData> getShareAccountLedgerReport(Integer pacId, Integer productId,
			String memberNo, Date fromDate, Date toDate) {
		
		

		logger.info("Start: Calling ShareAccountLedgerReportDAO inside getShareAccountLedgerReport()");
		List<ShareAccountLedgerReportData> shareaccountReportDataList = new ArrayList<ShareAccountLedgerReportData>();
		List<ShareAccountLedgerReportData> list = new ArrayList<ShareAccountLedgerReportData>();

		try {
			List<Object[]> shareAccList = KLSReportDataAccessFactory.getShareAccountLedgerReportDAO().getShareAccountLedgerReport(pacId, productId, memberNo, fromDate, toDate);
			shareaccountReportDataList = ShareAccountLedgerReportHelper.getShareAccountLedgerReportDataList(shareAccList,fromDate);

			for (ShareAccountLedgerReportData shareAccReportData : shareaccountReportDataList) {
				
				list.add(shareAccReportData);
			}
		} catch (Exception exception) {
			logger.info("Error:Inside getShareAccountLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print ShareAccountLedger Report:", exception.getCause());
		}
		logger.info("End: Calling ShareAccountLedgerReportDAO inside getShareAccountLedgerReport()");
		return shareaccountReportDataList;
		
	}
	
}
