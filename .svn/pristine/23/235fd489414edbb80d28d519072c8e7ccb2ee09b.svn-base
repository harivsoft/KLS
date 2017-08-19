package com.vsoftcorp.kls.report.dao;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.report.service.data.GLHistoryData;
import com.vsoftcorp.time.Date;

public interface IScheduleVsGLBalanceReportDAO {
	
	List<Object[]> getScheduleVsGLBalance(String pacsId, Date toDate,String status);
	
	public String getGLBalance(String glcode, Date toDate,String branchId,String pacsId);
	
	public List<Object[]> getScheduleBalance(String pacsId, Date toDate,String productId);

	public String getBranchId(String pacsId);
}
