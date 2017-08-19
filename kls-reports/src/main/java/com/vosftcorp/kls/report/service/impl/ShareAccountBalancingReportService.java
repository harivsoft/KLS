
package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IShareAccountBalancingReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.ShareAccountBalancingReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.ShareAccountBalancingReportHelper;
import com.vsoftcorp.time.Date;

public class ShareAccountBalancingReportService implements IShareAccountBalancingReportService{
	private static final Logger logger = Logger
			.getLogger(ShareAccountBalancingReportService.class);
	@Override
	public List<ShareAccountBalancingReportData> getShareAccountBalancingReport(Integer pacsId, Integer productId,
			String memberNo, Date asOnDate) {

		logger.info("Start: Calling ShareAccountBalancingReportDAO inside getShareAccountBalancingReport()");
		List<ShareAccountBalancingReportData> shareaccountReportDataList = new ArrayList<ShareAccountBalancingReportData>();
		List<ShareAccountBalancingReportData> list = new ArrayList<ShareAccountBalancingReportData>();

		try {
			List<Object[]> shareAccBalancingList = KLSReportDataAccessFactory.getShareAccountBalancingReportDAO().getShareAccountBalancingReport(pacsId, productId, memberNo, asOnDate);
			shareaccountReportDataList = ShareAccountBalancingReportHelper.getShareAccountBalancingReportDataList(shareAccBalancingList,asOnDate,pacsId, productId, memberNo);

			for (ShareAccountBalancingReportData shareAccReportData : shareaccountReportDataList) {
				
				list.add(shareAccReportData);
			}
		} catch (Exception exception) {
			logger.info("Error:Inside getShareAccountBalancingReport method");
			throw new KlsReportRuntimeException(
					"Can not print ShareAccountBalancing Report:", exception.getCause());
		}
		logger.info("End: Calling ShareAccountBalancingReportDAO inside getShareAccountBalancingReport()");
		return shareaccountReportDataList;
		
	}

}
