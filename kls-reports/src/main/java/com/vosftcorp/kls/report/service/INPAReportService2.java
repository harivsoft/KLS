package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.NPAReportData2;

public interface INPAReportService2 {
	
	public List<NPAReportData2> getNPAReportData(String asOnDate,String loanType,String reportType,String excludeStndLoan,Integer productId,Integer pacsId);
	
	public List<NPAReportData2> getBorrowingsNPAReportData(String asOnDate,String loanType,String reportType,String excludeStndLoan);

}
