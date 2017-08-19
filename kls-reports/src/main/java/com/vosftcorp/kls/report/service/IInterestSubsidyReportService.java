package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.InterestSubsidyReportData;

public interface IInterestSubsidyReportService {

	List<InterestSubsidyReportData> getInterestSubsidyReport(Integer pacId,
			Long subsidySchemeId, String typeOfScheme);

}
