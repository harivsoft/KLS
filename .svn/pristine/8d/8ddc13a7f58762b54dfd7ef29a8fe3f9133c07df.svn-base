package com.vosftcorp.kls.report.service.impl;

import java.util.List;

import com.vosftcorp.kls.report.service.IBalanceListReportService;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.report.dao.IBalanceListReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.BalanceListReportData;
import com.vsoftcorp.kls.report.service.helper.BalanceListReportHelper;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class BalanceListReportService implements IBalanceListReportService {

	private IBalanceListReportDAO dao = KLSReportDataAccessFactory.getBalanceListReportDAO();

	@Override
	public List<BalanceListReportData> getBalanceListReportData(Integer pacsId, Integer productId, Long customerId, Date asOnDate) {

		List<LineOfCredit> locList = dao.getLineOfCreditByPacsAndCustomerId(pacsId, productId, customerId);

		List<BalanceListReportData> result = BalanceListReportHelper.getBalanceListReportData(locList, asOnDate);
		return result;
	}

}
