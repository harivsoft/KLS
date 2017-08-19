package com.vsoftcorp.kls.report.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.time.Date;

public interface IMTLTLoanLedgerReportDAO  {
	
/*	public List<Object[]> getMTLTLoanLedgerReport(String reportType, String fromDateString,
			String toDateString, PrintWriter printWriter, Integer productId, Integer customerId, File filename,
			Integer pacsId, Integer accountNumber, String productType);
*/
	
	public List<Map> getAccountStatementReport(Long locNo, Date fromDate,
			Date toDate, Long customerId, Integer pacsId);
	
	public List<Map> getAccountStatementReportFromTransactionHistory(
			Long locNo, Date fromDate, Date toDate, Long customerId, Integer pacsId);

	public List<BigInteger> getLineOfCreditListByCustId(Long customerId);
}
