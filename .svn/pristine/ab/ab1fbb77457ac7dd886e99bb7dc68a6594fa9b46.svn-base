package com.vosftcorp.kls.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IOverdueReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.OverdueListReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.OverdueReportHelper;
import com.vsoftcorp.time.Date;

public class OverdueReportService implements IOverdueReportService {
	private static final Logger logger = Logger.getLogger(OverdueReportService.class);

	@Override
	public List<OverdueListReportData> getOverdueReport(Integer pacsId, Integer productId, String memberNo,
			Date instalmentDate, BigDecimal villageId, String loanType, String reportMode) {

		logger.info("Start: Calling  OverduelistReportDAO inside getOverdueReport()");
		List<OverdueListReportData> overdueReportDataList = new ArrayList<OverdueListReportData>();
		List<OverdueListReportData> list = new ArrayList<OverdueListReportData>();

		try {
			List<Object[]> overdueList = KLSReportDataAccessFactory.getOverdueReportDAO().getOverdueReport(pacsId,
					productId, memberNo, instalmentDate, villageId, loanType, reportMode);
			overdueReportDataList = OverdueReportHelper.getOverdueListReport(overdueList,instalmentDate);

			for (OverdueListReportData overdueReportData : overdueReportDataList) {

				list.add(overdueReportData);
			}
		} catch (Exception exception) {
			logger.info("Error:Inside getOverduelistReport method");
			throw new KlsReportRuntimeException("Can not print Overdue list Report:", exception.getCause());
		}
		logger.info("End: Calling ShareAccountLedgerReportDAO inside getOverdueReport()");
		return overdueReportDataList;

	}

}
