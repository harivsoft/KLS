package com.vosftcorp.kls.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.ICollectionReportService;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.DrawalReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.CollectionReportHelper;
import com.vsoftcorp.time.Date;

public class CollectionReportService implements ICollectionReportService {
	private static final Logger logger = Logger
			.getLogger(CollectionReportService.class);

	@Override
	public List<DrawalReportData> getCollectionReport(Integer pacId,
			Integer schemeId, Long seasonId, Date fromDate, Date toDate) {
		logger.info("Start: calling CollectionReportDAO  inside getCollectionReport ()");
		List<DrawalReportData> collectionReportDataList = new ArrayList<DrawalReportData>();
		List<DrawalReportData> list = new ArrayList<DrawalReportData>();

		try {
			List<Object[]> collectionList = KLSReportDataAccessFactory
					.getCollectionReportDAO().getCollectionReport(pacId,
							schemeId, seasonId, fromDate, toDate);
			collectionReportDataList = CollectionReportHelper
					.getCollectionReportDataList(collectionList);
			ISeasonDAO sDao = KLSDataAccessFactory.getSeasonDAO();

			for (DrawalReportData collectionReportData : collectionReportDataList) {
				Season season = sDao.getSeason(seasonId);
				collectionReportData.setSeasonName(season.getName());
				BigDecimal accountBalance = KLSReportDataAccessFactory
						.getDrawalReportDAO()
						.getAccountBalance(
								Long.parseLong(collectionReportData.getLocNo()),
								toDate);
				collectionReportData.setAccountBalance((accountBalance.setScale(2)).abs());
				list.add(collectionReportData);
			}
		} catch (Exception exception) {
			logger.error("Error:Inside getCollectionReport method");
			throw new KlsReportRuntimeException(
					"Can not print Collection Report:", exception.getCause());
		}
		logger.info("End: calling CollectionReportDAO  inside getCollectionReport ()");
		return collectionReportDataList;
	}

}
