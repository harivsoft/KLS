package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.InterestChargedReportData;

public interface IInterestChargedReportService {
	
	public List<InterestChargedReportData> getInterestChargedDataReport(Integer pacsId, Integer productId, Integer purposeId, Integer branchId,  String productType,
			String fromDateString, String toDateString);

}
