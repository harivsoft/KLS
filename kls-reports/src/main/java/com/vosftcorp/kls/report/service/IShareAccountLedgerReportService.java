package com.vosftcorp.kls.report.service;


import java.util.List;

import com.vsoftcorp.kls.report.service.data.ShareAccountLedgerReportData;
import com.vsoftcorp.time.Date;

public interface IShareAccountLedgerReportService {
	public List<ShareAccountLedgerReportData> getShareAccountLedgerReport(Integer pacId, Integer productId,
			String memberNo, Date fromDate, Date toDate);
}
