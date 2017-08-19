package com.vosftcorp.kls.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IDrawalReportService;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.DrawalReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.DrawalReportHelper;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 */
public class DrawalReportSerive implements IDrawalReportService {
	private static final Logger logger = Logger.getLogger(DrawalReportSerive.class);

	@Override
	public List<DrawalReportData> getDrawalReport(Integer pacId, Integer schemeId, Long seasonId, Date fromDate, Date toDate) {     

		logger.info("Start: Calling DrawalReportDAO inside getDrawalReport()");
		List<DrawalReportData> drawalReportDataList = new ArrayList<DrawalReportData>();
		List<DrawalReportData> list = new ArrayList<DrawalReportData>();
		try {
			List<Object[]> drawalTransactionList = KLSReportDataAccessFactory.getDrawalReportDAO().getDrawalReport(pacId, schemeId, seasonId, fromDate, toDate);
			drawalReportDataList = DrawalReportHelper.getDrawalReportDataList(drawalTransactionList);
			ISeasonDAO sDao = KLSDataAccessFactory.getSeasonDAO();
			for (DrawalReportData drawalReportData : drawalReportDataList) {
				Season season = sDao.getSeason(seasonId);
				drawalReportData.setSeasonName(season.getName());

				BigDecimal accountBalance = KLSReportDataAccessFactory.getDrawalReportDAO().getAccountBalance(Long.parseLong(drawalReportData.getLocNo()), toDate);
				drawalReportData.setAccountBalance(accountBalance != null ? accountBalance.setScale(2).abs() : BigDecimal.ZERO.setScale(2));
				

				list.add(drawalReportData);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getDrawalReport method");
			throw new KlsReportRuntimeException("Can not print Drawal Report:", exception.getCause());
		}
		logger.info("End: Calling DrawalReportDAO inside getDrawalReport()");
		return list;
	}
}
