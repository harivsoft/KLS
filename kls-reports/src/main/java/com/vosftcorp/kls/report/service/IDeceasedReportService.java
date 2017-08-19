package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.DeceasedReportData;

public interface IDeceasedReportService {
	public List<DeceasedReportData> getDeceasedDetails(Integer pacsId,String date);

}
