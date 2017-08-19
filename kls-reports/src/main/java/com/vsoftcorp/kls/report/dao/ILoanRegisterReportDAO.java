package com.vsoftcorp.kls.report.dao;

import java.util.List;

import com.vsoftcorp.time.Date;

public interface ILoanRegisterReportDAO {
	
	List<Object[]> getLoanRegisterReport(Integer pacsId, Integer productId, Date businessDate,  String loanType);
	
	List<Object[]> getLoanRegisterSummeryReport(Integer pacsId,	Integer productId, Date businessDate, String loanType);

}
