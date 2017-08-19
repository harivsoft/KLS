package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.MasterReportData;

public interface IMasterReportService {

	List<MasterReportData> getMasterReport(String master);

}
