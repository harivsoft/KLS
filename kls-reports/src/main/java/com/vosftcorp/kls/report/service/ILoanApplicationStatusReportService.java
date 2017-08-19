package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.LoanApplicationStatusReportData;

public interface ILoanApplicationStatusReportService {
	
	public List<LoanApplicationStatusReportData> getLoanStatusDetails(Integer pacsId, String loanType,
			String fromDate, String toDate,Integer status);

}
