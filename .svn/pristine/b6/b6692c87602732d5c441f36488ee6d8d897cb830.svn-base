package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.report.dao.IDayBookReportDAO;
import com.vsoftcorp.kls.report.dao.IScheduleVsGLBalanceReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.DayBookReportData;
import com.vsoftcorp.time.Date;

public class DayBookReportHelper {
	
	public static List<DayBookReportData> getDayBookReportData(List<Object[]> data,String cashgl,Date asOnDate,String pacId,String branchId){
		 List<DayBookReportData> dayBookDataList = new ArrayList<DayBookReportData>();
		 IDayBookReportDAO dao = KLSReportDataAccessFactory.getDayBookReportDAO();
		 IScheduleVsGLBalanceReportDAO schDao=KLSReportDataAccessFactory.getScheduleVsGLBalanceReportDAO();
		 String cashOpBal=schDao.getGLBalance(cashgl, asOnDate,branchId,pacId);
		 BigDecimal cashOpeningBal=new BigDecimal(cashOpBal);
		 for(Object[] obj : data){
			 DayBookReportData dayBookData = new DayBookReportData();
			 dayBookData.setCashOpBal(cashOpeningBal);
			 dayBookData.setAccountNum((String)obj[0]);
			 dayBookData.setCashPayment(((BigDecimal)obj[9]).setScale(2));
			 dayBookData.setCashReceipt(((BigDecimal)obj[8]).setScale(2));
			 
			 if("null".equalsIgnoreCase((String)obj[5]))
				 dayBookData.setName(dao.getGLNameByCode((String)obj[0]));
			 else
				 dayBookData.setName((String)obj[5]);
			 
			 dayBookData.setTransferPayment(((BigDecimal)obj[11]).setScale(2));
			 dayBookData.setTransferReceipt(((BigDecimal)obj[10]).setScale(2));
			 dayBookData.setVoucherNum((String)obj[3]);
			 
			 if("null".equalsIgnoreCase((String)obj[7]))
				 dayBookData.setProductName(dao.getGLNameByCode((String)obj[6]));
			 else{
				 if((BigDecimal)obj[6]==BigDecimal.ZERO){
					 //dayBookData.setProductName(dao.getGLNameByCode((String)obj[7]));
					 dayBookData.setProductName((String)obj[7]);
				 }
				 else
				 dayBookData.setProductName((String)obj[7]);
			 }
			
			 dayBookDataList.add(dayBookData);
		 }
		 return dayBookDataList;
	}

}
