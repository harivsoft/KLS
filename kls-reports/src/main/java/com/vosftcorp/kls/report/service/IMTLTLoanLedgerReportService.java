package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.MTLTLoanLedgerReportData;
import com.vsoftcorp.time.Date;

public interface IMTLTLoanLedgerReportService {

	public List<MTLTLoanLedgerReportData> getMTLTLoanLedgerReport(Long locNo,
			Date fromDate, Date toDate, Long customerId, Integer pacsId);
	
	/*public List<MTLTLoanLedgerReportData> getMTLTLoanLedgerReport(String reportType, String fromDateString,
			String toDateString, PrintWriter printWriter, Integer productId, Integer customerId, File filename,
			Integer pacsId, Integer accountNumber, String productType);
*/
}
