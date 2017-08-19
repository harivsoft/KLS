package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.BorrowingAccountLedgerReportData;

public interface IBorrowingAccountLedgerReportService {
	
	public List<BorrowingAccountLedgerReportData> getBorrowingAccountLedgerReportReport(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,Integer branchId);
	
	public List<BorrowingAccountLedgerReportData> getLendingAccountLedgerReportReport(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,String memberId);
	
	public List<BorrowingAccountLedgerReportData> getLendingLedgerData(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,String memberId,Integer noOfRecords) ;
 
	public List<BorrowingAccountLedgerReportData> getLendingLedgerDatabyLocId(String borrowingType, 
			String fromDateString, String toDateString,Integer noOfRecords,String locId,String transType);
	
}
