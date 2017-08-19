package com.vosftcorp.kls.report.service.dcb;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.DCBReportData;
import com.vsoftcorp.time.Date;

public interface IDCBReportService {
	

	public List<DCBReportData> getDCBReport(Integer branchId, Integer pacId, Integer schemeId, Long customerId, Date fromDate, Date toDate, Integer demandFreequency);	


}
