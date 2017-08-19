package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.vosftcorp.kls.report.service.ILoanRegisterReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.LoanRegisterData;
import com.vsoftcorp.time.Date;

public class LoanRegisterReportService implements ILoanRegisterReportService {

	@Override
	public List<LoanRegisterData> getLoanRegisterReport(Integer pacsId, Integer productId, Date businessDate, String loanType) {
		
		List<LoanRegisterData> loanRegisterDataList  = new ArrayList<LoanRegisterData>();
		
		List<Object[]> loanAccList = KLSReportDataAccessFactory.getLoanRegisterReportDAO().getLoanRegisterReport(pacsId, productId, businessDate, loanType);
		
		loanRegisterDataList = LoanRegisterReportHelper.getLoanRegisterDataList(loanAccList, businessDate.toString(), loanType);
		
		return loanRegisterDataList;
	}
	
	@Override
	public List<LoanRegisterData> getLoanRegisterSummeryReport(Integer pacsId,
			Integer productId, Date businessDate, String loanType) {
	 
			List<LoanRegisterData> loanRegisterSummeryDataList  = new ArrayList<LoanRegisterData>();
			
			List<Object[]> loanAccList = KLSReportDataAccessFactory.getLoanRegisterReportDAO().getLoanRegisterSummeryReport(pacsId, productId, businessDate, loanType);
			
			loanRegisterSummeryDataList = LoanRegisterReportHelper.getLoanRegisterSummeryDataList(loanAccList, businessDate.toString(), loanType);
			
			return loanRegisterSummeryDataList;
		}
	 

}
