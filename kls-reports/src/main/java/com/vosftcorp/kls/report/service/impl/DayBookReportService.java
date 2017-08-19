package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.vosftcorp.kls.report.service.IDayBookReportService;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.dao.IDayBookReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.DayBookReportData;
import com.vsoftcorp.kls.report.service.helper.DayBookReportHelper;
import com.vsoftcorp.time.Date;

public class DayBookReportService implements IDayBookReportService {

	@Override
	public List<DayBookReportData> getDayBookReportService(Date asOnDate,
			Integer pacsId) {
		 List<DayBookReportData> dayBookDataList = new ArrayList<DayBookReportData>();
		 IDayBookReportDAO dao = KLSReportDataAccessFactory.getDayBookReportDAO();
		 String cashgl =KLSDataAccessFactory.getPacsDAO().getPacs(pacsId).getCashGl();
		 String branchId=KLSDataAccessFactory.getPacsDAO().getPacs(pacsId).getBranch().getId().toString();
		 String pacId=pacsId.toString();
		 dayBookDataList=DayBookReportHelper.getDayBookReportData(dao.getDayBookReportDAO(asOnDate, pacsId),cashgl,asOnDate,pacId,branchId);
		return dayBookDataList;
	}

}
