package com.vsoftcorp.kls.report.dao;

import java.util.List;

public interface IBorrowingAccountLedgerReportDAO {
	
	public List<Object[]> getBorrowingAccountLedgerReportReport(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,Integer branchId);
	
	public List<Object[]> getLendingAccountLedgerReportReport(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString);
	
	public  List<Object[]> getCustomerName(String locId);

	public  List<Object[]> getMemberName(String locId,String memberId);
	
	public List<Object[]> getLendingLedgerData(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,Integer noOfRecords);

	public List<Object[]> getLendingLedgerDatabyLocId(String borrowingType, 
			String fromDateString, String toDateString,Integer noOfRecords,String locId , String transType);
}
