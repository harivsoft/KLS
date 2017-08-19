package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.ILoanApplicationStatusReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.LoanApplicationStatusReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.LoanApplicationStatusReportHelper;

public class LoanApplicationStatusReportService implements
		ILoanApplicationStatusReportService {
	
	private static final Logger logger = Logger
			.getLogger(LoanApplicationStatusReportService.class);

	@Override
	public List<LoanApplicationStatusReportData> getLoanStatusDetails(Integer pacsId, String loanType,
			String fromDate, String toDate,Integer status) {
		logger.info("Start: Calling getLoanAccountStatusDAO inside getLoanStatusDetails()");
		List<LoanApplicationStatusReportData> loanAccountStatusDataList = new ArrayList<LoanApplicationStatusReportData>();
		try {
			List<Object[]> loanAccountList = KLSReportDataAccessFactory.getLoanAccountStatusReportDAO().getLoanStatusDetails(pacsId, loanType, fromDate, toDate, status);
					
			loanAccountStatusDataList=LoanApplicationStatusReportHelper.getLoanAccountStatusData(loanAccountList);
			
		} catch (Exception exception) {
			logger.info("Error:Inside getLoanStatusDetails method in service");
			throw new KlsReportRuntimeException(
					"Can not print DeceasedDetails Report:", exception.getCause());
		}
		logger.info("End: Calling getLoanAccountStatusDAO inside getLoanStatusDetails()");
		return loanAccountStatusDataList;
	}

}
