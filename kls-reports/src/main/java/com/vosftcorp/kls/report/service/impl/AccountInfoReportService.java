package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IAccountInfoReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.AccountInfoData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.AccountInfoReportHelper;
import com.vsoftcorp.time.Date;

public class AccountInfoReportService implements IAccountInfoReportService {

	private static final Logger logger = Logger.getLogger(AccountInfoReportService.class);

	@Override
	public List<AccountInfoData> getAccountInfoReport(String businessType, Integer productId, String accountStatus,
			Date fromDate, Date toDate, Integer pacsId) {
		logger.info("Start: Calling AccountInfoReportDAO inside getAccountInfoReport()");
		List<AccountInfoData> accountInfoReportDataList = new ArrayList<AccountInfoData>();
		List<AccountInfoData> list = new ArrayList<AccountInfoData>();

		try {
			List<Object[]> accList = KLSReportDataAccessFactory.getAccountInfoReportDAO().getAccountInfoReport(
					businessType, productId, accountStatus, fromDate, toDate, pacsId);
			accountInfoReportDataList = AccountInfoReportHelper.getAccountInfoReportDataList(accList,businessType);

			for (AccountInfoData accReportData : accountInfoReportDataList) {

				list.add(accReportData);
			}
		} catch (Exception exception) {
			logger.info("Error:Inside getAccountInfoReport method");
			throw new KlsReportRuntimeException("Can not print AccountInfo Report:", exception.getCause());
		}
		logger.info("End: Calling AccountInfoReportDAO inside getAccountInfoReport()");
		return accountInfoReportDataList;

	}

}
