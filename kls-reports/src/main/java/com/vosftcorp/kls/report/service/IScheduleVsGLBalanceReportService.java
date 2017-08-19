package com.vosftcorp.kls.report.service;


import java.util.List;

import com.vsoftcorp.kls.report.service.data.ScheduleReportData;
import com.vsoftcorp.kls.report.service.data.ScheduleVsGLBalanceReportData;
import com.vsoftcorp.time.Date;

public interface IScheduleVsGLBalanceReportService {
	
	public List<ScheduleVsGLBalanceReportData> getScheduleVsGLBalance(String pacsId,Date toDate,String status);
	
	public List<ScheduleReportData> getScheduleBalance(String pacsId,Date toDate,String productId);

}
