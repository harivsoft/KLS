package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IAccountStatementReportService;
import com.vsoftcorp.kls.report.dao.impl.AccountStatementReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.AccountStatementReportData;
import com.vsoftcorp.kls.report.service.helper.AccountStatementReportHelper;
import com.vsoftcorp.time.Date;

public class AccountStatementReportSerive implements
		IAccountStatementReportService {

	private static final Logger logger = Logger
			.getLogger(AccountStatementReportDAO.class);

	@Override
	public List<AccountStatementReportData> getAccountStatementReport(
			String accNo, Date fromDate, Date toDate) {

		logger.info("Start: inside method getAccountStatementReport()");
		List<AccountStatementReportData> accountStatementReportDataList = new ArrayList<AccountStatementReportData>();
		try {

			List<Map> accStmtCurrentTransactionList = KLSReportDataAccessFactory
					.getAccountStatementReportDAO().getAccountStatementReport(
							accNo, fromDate, toDate);
			List<Map> accStmtTransactionHistoryList = KLSReportDataAccessFactory
					.getAccountStatementReportDAO()
					.getAccountStatementReportFromTransactionHistory(accNo,
							fromDate, toDate);
			accountStatementReportDataList
					.addAll(AccountStatementReportHelper
							.getAccountStatementReportDataList(accStmtTransactionHistoryList));
			accountStatementReportDataList
					.addAll(AccountStatementReportHelper
							.getAccountStatementReportDataList(accStmtCurrentTransactionList));

		} catch (Exception exception) {
			 exception.printStackTrace();
			logger.info("Error while generating Account Statement report");
		}

		logger.info("End: inside method getAccountStatementReport()");
		return accountStatementReportDataList;
	}

}
