package com.vosftcorp.kls.report.service;

import java.io.PrintWriter;
import java.util.List;

import com.vsoftcorp.kls.report.service.data.BatchWiseVoucherPrintData;

public interface IBatchWiseVoucherPrintReportService {
	
	public List<BatchWiseVoucherPrintData> getBatchWiseVoucherPrintDataList(String voucherType, String transactionType, 
			String fromDateString, String toDateString, PrintWriter printWriter, Integer pacsId);

}
