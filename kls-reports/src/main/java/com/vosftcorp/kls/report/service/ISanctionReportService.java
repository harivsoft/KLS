package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.SanctionReportData;

public interface ISanctionReportService {

	public List<SanctionReportData> getSanctionReport(Integer memNumber,
			Long seasonId, String seasonYear, String pacsId);

}
