package com.vosftcorp.kls.report.service.impl;

import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IBatchWiseVoucherPrintReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.BatchWiseVoucherPrintData;
import com.vsoftcorp.kls.report.service.helper.BatchWiseVoucherPrintReportHelper;

public class BatchWiseVoucherPrintReportService implements IBatchWiseVoucherPrintReportService {
	
	private static final Logger logger = Logger
			.getLogger(BatchWiseVoucherPrintReportService.class);
	
	
	public List<BatchWiseVoucherPrintData> getBatchWiseVoucherPrintDataList(String voucherType, String transactionType, 
			String fromDateString, String toDateString, PrintWriter printWriter, Integer pacsId) {
		
		logger.info("Start: Calling BatchWiseVoucherPrintReportService inside getBatchWiseVoucherPrintDataList()");
		
		List<Object[]> getBatchWiseVouchers = KLSReportDataAccessFactory.getBatchWiseVoucherPrintReportDAO().getBatchWiseVoucherPrintReport(voucherType, transactionType, fromDateString, toDateString, printWriter, pacsId);
		
		List<BatchWiseVoucherPrintData> batchWiseVoucherPrintDataList = BatchWiseVoucherPrintReportHelper.getBatchWiseVoucherPrintDataList(getBatchWiseVouchers, toDateString); 
		
		
		return batchWiseVoucherPrintDataList;
		
		
	}

}
