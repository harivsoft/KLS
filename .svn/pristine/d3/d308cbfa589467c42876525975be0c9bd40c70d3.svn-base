package com.vosftcorp.kls.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IScheduleVsGLBalanceReportService;
import com.vsoftcorp.kls.report.service.data.GLHistoryData;
import com.vsoftcorp.kls.report.dao.impl.AccountStatementReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.AccountStatementReportData;
import com.vsoftcorp.kls.report.service.data.ScheduleReportData;
import com.vsoftcorp.kls.report.service.data.ScheduleVsGLBalanceReportData;
import com.vsoftcorp.kls.report.service.helper.AccountStatementReportHelper;
import com.vsoftcorp.kls.report.service.helper.ScheduleReportHelper;
import com.vsoftcorp.kls.report.service.helper.ScheduleVsGLBalanceReportHelper;
import com.vsoftcorp.time.Date;


public class ScheduleVsGLBalanceReportService  implements IScheduleVsGLBalanceReportService{
	private static final Logger logger = Logger
			.getLogger(ScheduleVsGLBalanceReportService.class);
	@Override
	
	public List<ScheduleVsGLBalanceReportData> getScheduleVsGLBalance(String pacsId,Date toDate,String status){
		
		List<ScheduleVsGLBalanceReportData> scheduleData=new ArrayList<ScheduleVsGLBalanceReportData>();
		logger.info("Start: inside method getScheduleVsGLBalance()");
		List<ScheduleVsGLBalanceReportData> accountStatementReportDataList = new ArrayList<ScheduleVsGLBalanceReportData>();
		BigDecimal totStGLBal=new BigDecimal(0);
		BigDecimal totMtGLBal=new BigDecimal(0);
		BigDecimal totLtGLBal=new BigDecimal(0);
		BigDecimal totStSchBal=new BigDecimal(0);
		BigDecimal totStDiffBal=new BigDecimal(0);
		BigDecimal totMtSchBal=new BigDecimal(0);
		BigDecimal totMtDiffBal=new BigDecimal(0);
		BigDecimal totLtSchBal=new BigDecimal(0);
		BigDecimal totLtDiffBal=new BigDecimal(0);
		Integer temp=0;
		BigDecimal diff=new BigDecimal(0);
		try {

			List<Object[]> stBalnceList = KLSReportDataAccessFactory
					.getScheduleVsGLBalanceReportDAO().getScheduleVsGLBalance(
							pacsId, toDate,status);
			List<String> glcode=new ArrayList<String>();
			String branchId=KLSReportDataAccessFactory
					.getScheduleVsGLBalanceReportDAO().getBranchId(pacsId);
			for(Object[] o:stBalnceList){
				ScheduleVsGLBalanceReportData s=new ScheduleVsGLBalanceReportData();
				s=ScheduleVsGLBalanceReportHelper.getScheduleVsBalanceReport(o);
			
				//glcode.add(s.getGlcode());
				String gldata=getGLBalance(s.getGlcode(),toDate,branchId,pacsId);
				logger.info("gl balance::"+gldata);
                if(scheduleData.size()>0 && scheduleData.get(temp-1).getCropId().equals(s.getCropId()) && scheduleData.get(temp-1).getGlcode().equals(s.getGlcode())){
                	gldata="0.00";
    				diff=new BigDecimal(scheduleData.get(temp-1).getGlBalance()).abs().subtract(new BigDecimal(s.getSumOfOpeningBal()).add(new BigDecimal(scheduleData.get(temp-1).getSumOfOpeningBal())).abs());
    				logger.info("difference in if part::"+diff);
				}
                else
				diff=new BigDecimal(gldata).abs().subtract(new BigDecimal(s.getSumOfOpeningBal()).abs());
				logger.info("difference in blances::"+diff);
				if(s.getCropId().equals("ST")){
					totStGLBal=totStGLBal.add(new BigDecimal(gldata));
					s.setTotGLBal(totStGLBal.toString());
					logger.info("tota st "+totStSchBal);
					logger.info("balance in ST::"+s.getSumOfOpeningBal());
					totStSchBal=totStSchBal.add(new BigDecimal(s.getSumOfOpeningBal()));
					logger.info("total st "+totStSchBal);
					s.setTotSchBal(totStSchBal.setScale(2).toString());
					totStDiffBal=totStDiffBal.add(diff);
					s.setTotDiff(totStDiffBal.setScale(2).toString());
				}
				else if(s.getCropId().equals("MT"))
				{
					logger.info("in Mt total::"+totMtGLBal);
					totMtGLBal=totMtGLBal.add(new BigDecimal(gldata));
					logger.info("2 ndin Mt total::"+totMtGLBal);
					s.setTotGLBal(totMtGLBal.toString());
					totMtSchBal=totMtSchBal.add(new BigDecimal(s.getSumOfOpeningBal()));
					s.setTotSchBal(totMtSchBal.setScale(2).toString());
					totMtDiffBal=totMtDiffBal.add(diff);
					s.setTotDiff(totMtDiffBal.setScale(2).toString());
				}
				else{
					totLtGLBal=totLtGLBal.add(new BigDecimal(gldata));
					s.setTotGLBal(totLtGLBal.toString());
					totLtSchBal=totLtSchBal.add(new BigDecimal(s.getSumOfOpeningBal()));
					s.setTotSchBal(totLtSchBal.setScale(2).toString());
					totLtDiffBal=totLtDiffBal.add(diff);
					s.setTotDiff(totLtDiffBal.setScale(2).toString());
				}
				 if(scheduleData.size()>0 && scheduleData.get(temp-1).getCropId().equals(s.getCropId()) && scheduleData.get(temp-1).getGlcode().equals(s.getGlcode())){
					 s.setGlBalance("");
						s.setGlcode("");
						s.setDiffInBalances("");
						scheduleData.get(temp-1).setDiffInBalances(diff.setScale(2).toString());
				 }
				 else{
				s.setGlBalance(new BigDecimal(gldata).toString());
				s.setDiffInBalances(diff.setScale(2).toString());
				 }
				temp++;
				
				if(status.equals("B"))
					s.setStatus("Balances");
					else
					s.setStatus("Int Receivable");	
			
				scheduleData.add(s);
			}

		} catch (Exception exception) {
			 exception.printStackTrace();
			logger.info("Error while generating Schedule Vs Gl Balnces report");
		}
		return scheduleData;
	}
	private String getGLBalance(String glcode,Date toDate,String branchId,String pacsId){
		String glbalance="";
		try {

			glbalance = KLSReportDataAccessFactory
					.getScheduleVsGLBalanceReportDAO().getGLBalance(
							glcode, toDate,branchId,pacsId);
			
		} catch (Exception exception) {
			 exception.printStackTrace();
			logger.info("Error while generating Schedule Vs Gl Balnces report");
		}	
	
return glbalance;
}
	
@Override
	
	public List<ScheduleReportData> getScheduleBalance(String pacsId,Date toDate,String productId){
		
		List<ScheduleReportData> scheduleData=new ArrayList<ScheduleReportData>();
		logger.info("Start: inside method getScheduleBalance()"+productId);
		List<ScheduleReportData> accountStatementReportDataList = new ArrayList<ScheduleReportData>();
   		BigDecimal totBal=new BigDecimal(0);
		try {

			List<Object[]> stBalnceList = KLSReportDataAccessFactory
					.getScheduleVsGLBalanceReportDAO().getScheduleBalance(pacsId,toDate,productId);
			logger.info("inside service method stBalnceList::"+stBalnceList);
			for(Object[] o:stBalnceList){
			 ScheduleReportData scheduleReportData=new ScheduleReportData();
			 scheduleReportData=ScheduleReportHelper.getScheduleReport(o);
			 totBal=totBal.add(new BigDecimal(scheduleReportData.getClosingBalance()));
			 scheduleReportData.setTotalBalance(totBal.setScale(2).toString());
			 scheduleData.add(scheduleReportData);
			}
			}

		 catch (Exception exception) {
			 exception.printStackTrace();
			logger.info("Error while generating Schedule Vs Gl Balnces report");
		}
		return scheduleData;
	}
}
