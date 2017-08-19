package com.vsoftcorp.kls.report.dao;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.time.Date;

public interface ISanctionReportDAO {
	List<Map> getSanctionReport(Integer memNumber,Long seasonId,String seasonYear, String pacsId);
}
