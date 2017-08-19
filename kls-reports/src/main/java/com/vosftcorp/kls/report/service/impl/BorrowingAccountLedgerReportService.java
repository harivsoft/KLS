package com.vosftcorp.kls.report.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IBorrowingAccountLedgerReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.BorrowingAccountLedgerReportData;
import com.vsoftcorp.kls.report.service.helper.BorrowingAccountLedgerReportHelper;

public class BorrowingAccountLedgerReportService implements IBorrowingAccountLedgerReportService {
	private static final Logger logger = Logger
			.getLogger(BorrowingAccountLedgerReportService.class);
	
	public List<BorrowingAccountLedgerReportData> getBorrowingAccountLedgerReportReport(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,Integer branchId) {
		
		logger.info("Start: Calling BorrowingAccountLedgerReportService inside getBorrowingAccountLedgerDataList()*********************");
		
		List<Object[]> getBorrowingAccountList = KLSReportDataAccessFactory.getBorrowingAccountLedgerReportDAO().getBorrowingAccountLedgerReportReport(pacsId, productId, purposeId, borrowingType, fromDateString, toDateString,branchId);
		
		List<BorrowingAccountLedgerReportData> getBorrowingAccountLedgerList = BorrowingAccountLedgerReportHelper.getBorrowingAccountLedgerReportDataList(getBorrowingAccountList);
		for(BorrowingAccountLedgerReportData borrowingData:getBorrowingAccountLedgerList){
			List<Object[]> listOfObj = KLSReportDataAccessFactory.getBorrowingAccountLedgerReportDAO().getCustomerName(borrowingData.getLocId());
			if(listOfObj != null && !listOfObj.isEmpty()){
				for (Object[] objects : listOfObj) {
					borrowingData.setMemberName(objects[0].toString());
				}
			}
		}
		
		return getBorrowingAccountLedgerList;
		
	}
	
	public List<BorrowingAccountLedgerReportData> getLendingAccountLedgerReportReport(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,String memberId) {
		
		logger.info("Start: Calling BorrowingAccountLedgerReportService inside getLendingAccountLedgerReportReport()");
		
		List<Object[]> getBorrowingAccountList = KLSReportDataAccessFactory.getBorrowingAccountLedgerReportDAO().getLendingAccountLedgerReportReport(pacsId, productId, purposeId, borrowingType, fromDateString, toDateString);
		
		List<BorrowingAccountLedgerReportData> getBorrowingAccountLedgerList = BorrowingAccountLedgerReportHelper.getLendingAccountLedgerReportDataList(getBorrowingAccountList);
		List<BorrowingAccountLedgerReportData> borrowingAccountLedgerList = new ArrayList<BorrowingAccountLedgerReportData>();
		for(BorrowingAccountLedgerReportData borrowingData:getBorrowingAccountLedgerList){
			List<Object[]> listOfObj = KLSReportDataAccessFactory.getBorrowingAccountLedgerReportDAO().getMemberName(borrowingData.getLocId(),memberId);
			 
			if(listOfObj != null && !listOfObj.isEmpty()){
				for (Object[] objects : listOfObj) {
					 
					if(objects[0]!=null)
						borrowingData.setMemberName(objects[0].toString());
					if(objects[1]!=null){
						Integer memId=Integer.parseInt(objects[1].toString());
						borrowingData.setMemberId(memId);
					}
				}
			}
			logger.info("customer id==="+memberId+",,,,"+borrowingData.getMemberId());
			Integer custId=Integer.parseInt(memberId);
			if(borrowingType.equalsIgnoreCase("MT")){
				if(custId!=0){
				 if(custId==borrowingData.getMemberId())
				   borrowingAccountLedgerList.add(borrowingData);
				}
				else
					borrowingAccountLedgerList.add(borrowingData);
			}
			else
				borrowingAccountLedgerList.add(borrowingData);
		}
		
		return borrowingAccountLedgerList;
		
	}

	public List<BorrowingAccountLedgerReportData> getLendingLedgerData(Integer pacsId, Integer productId, Integer purposeId,  String borrowingType, 
			String fromDateString, String toDateString,String memberId,Integer noOfRecords) {
		logger.info("Start: Calling BorrowingAccountLedgerReportService inside getLendingAccountLedgerReportReport()");
		//BigDecimal debitValue = BigDecimal.ZERO;
		List<Object[]> getBorrowingAccountList = KLSReportDataAccessFactory.getBorrowingAccountLedgerReportDAO().getLendingLedgerData(pacsId, productId, purposeId, borrowingType, fromDateString, toDateString,noOfRecords);
		Collection<BorrowingAccountLedgerReportData> listAllRecords=null;
		List<BorrowingAccountLedgerReportData> list=null;
		List<BorrowingAccountLedgerReportData> getBorrowingAccountLedgerList = BorrowingAccountLedgerReportHelper.getLendingAccountLedgerReportDataList(getBorrowingAccountList);
		List<BorrowingAccountLedgerReportData> borrowingAccountLedgerList = new ArrayList<BorrowingAccountLedgerReportData>();
		Map<String, BorrowingAccountLedgerReportData> infoMap = new HashMap<String, BorrowingAccountLedgerReportData>();
		try{
		for(BorrowingAccountLedgerReportData borrowingData:getBorrowingAccountLedgerList){
			
			List<Object[]> listOfObj = KLSReportDataAccessFactory.getBorrowingAccountLedgerReportDAO().getMemberName(borrowingData.getLocId(),memberId);
			
			 		 if(infoMap.containsKey(borrowingData.getLocId())){
			 			 
			 			 			 			 
					 BorrowingAccountLedgerReportData bdata = infoMap.get(borrowingData.getLocId());
					 bdata.setCredit(bdata.getCredit().add(borrowingData.getCredit()));
					 bdata.setDebit(bdata.getDebit().add(borrowingData.getDebit()));
					 		 
					 infoMap.put(borrowingData.getLocId(), bdata);
					 }else{
						infoMap.put(borrowingData.getLocId(), borrowingData);
					 	 
					 if(listOfObj != null && !listOfObj.isEmpty()){
							for (Object[] objects : listOfObj) {
								 
								if(objects[0]!=null)
									//borrowingData.setMemberName(objects[0].toString());
								if(objects[1]!=null){
									Integer memId=Integer.parseInt(objects[1].toString());
									//borrowingData.setMemberId(memId);
								}
							}
						}
						logger.info("customer id==="+memberId+",,,,"+borrowingData.getMemberId());
						Integer custId=Integer.parseInt(memberId);
						if(borrowingType.equalsIgnoreCase("MT")){
							if(custId!=0){
							 if(custId==borrowingData.getMemberId()){
							   borrowingAccountLedgerList.add(borrowingData);
							}
							else
								borrowingAccountLedgerList.add(borrowingData);
						}
						else
							borrowingAccountLedgerList.add(borrowingData);
					
						}
					 
				    }
			 	listAllRecords =   infoMap.values();
			
			    list = new ArrayList<BorrowingAccountLedgerReportData>(listAllRecords);
			    
			    if(list.size() >  noOfRecords ){
			    	list=	list.subList(0, noOfRecords);
			    }
			
			
		}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return list;
		
		
	}
	public List<BorrowingAccountLedgerReportData> getLendingLedgerDatabyLocId( String borrowingType, 
			String fromDateString, String toDateString,Integer noOfRecords,String locId , String transType) {
		
		logger.info("Start: Calling BorrowingAccountLedgerReportService inside getLendingAccountLedgerReportReport()");
		
		List<Object[]> getBorrowingAccountList = KLSReportDataAccessFactory.getBorrowingAccountLedgerReportDAO().getLendingLedgerDatabyLocId(borrowingType, fromDateString, toDateString,noOfRecords,locId,transType);
		List<BorrowingAccountLedgerReportData> list=null;
		List<BorrowingAccountLedgerReportData> getBorrowingAccountLedgerList =  new ArrayList<BorrowingAccountLedgerReportData>();
		
		BorrowingAccountLedgerReportData data=null;
		for (Object[] object : getBorrowingAccountList) {
			data = new BorrowingAccountLedgerReportData();
			Date date = (Date) object[0];
			data.setDate(DateFormatUtils.format(date, "dd/MM/yyyy"));
			BigDecimal transAmount = (BigDecimal) object[1];
			data.setDebit(transAmount.setScale(2));
			getBorrowingAccountLedgerList.add(data);
		}
		//List<BorrowingAccountLedgerReportData> borrowingAccountLedgerList = new ArrayList<BorrowingAccountLedgerReportData>();
		
		/*for(BorrowingAccountLedgerReportData borrowingData:getBorrowingAccountLedgerList){
			List<Object[]> listOfObj = KLSReportDataAccessFactory.getBorrowingAccountLedgerReportDAO().getMemberName(borrowingData.getLocId(),memberId);
			 
			if(listOfObj != null && !listOfObj.isEmpty()){
				for (Object[] objects : listOfObj) {
					 
					if(objects[0]!=null)
						borrowingData.setMemberName(objects[0].toString());
					if(objects[1]!=null){
						Integer memId=Integer.parseInt(objects[1].toString());
						borrowingData.setMemberId(memId);
					}
				}
			}
			logger.info("customer id==="+memberId+",,,,"+borrowingData.getMemberId());
			Integer custId=Integer.parseInt(memberId);
			if(borrowingType.equalsIgnoreCase("MT")){
				if(custId!=0){
				 if(custId==borrowingData.getMemberId())
				   borrowingAccountLedgerList.add(borrowingData);
				}
				else
					borrowingAccountLedgerList.add(borrowingData);
			}
			else
				borrowingAccountLedgerList.add(borrowingData);
		}
		
		list = new ArrayList<BorrowingAccountLedgerReportData>(borrowingAccountLedgerList);
	    
	    if(list.size() >  noOfRecords ){
	    	list=	list.subList(0, noOfRecords);
	    }*/
	
		return getBorrowingAccountLedgerList;
		
	}

}
