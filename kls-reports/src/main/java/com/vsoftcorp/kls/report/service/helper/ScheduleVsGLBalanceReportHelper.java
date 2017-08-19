package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.impl.ScheduleVsGLBalanceReportService;
import com.vsoftcorp.kls.report.action.ScheduleVsGLBalanceActionServlet;
import com.vsoftcorp.kls.report.service.data.ScheduleVsGLBalanceReportData;

public class ScheduleVsGLBalanceReportHelper {
	
	 private static final Logger logger = Logger
				.getLogger(ScheduleVsGLBalanceReportHelper.class);
	
 public static ScheduleVsGLBalanceReportData getScheduleVsBalanceReport(Object data[]) {
	 
	 ScheduleVsGLBalanceReportData scheduleData = new ScheduleVsGLBalanceReportData();
			try {
				
					
					String glCode = data[2].toString();

					scheduleData.setGlcode(glCode);
					logger.info("glCode Id::"+scheduleData.getGlcode());
					
					if(data[0]!=null){
					String cropId = data[0].toString();

					scheduleData.setCropId(cropId);
					}
					logger.info("cropId Id::"+scheduleData.getCropId());
					
					String totBal = data[1].toString();
                    BigDecimal opbal=new BigDecimal(totBal);
					scheduleData.setSumOfOpeningBal(opbal.setScale(2).toString());

					String productId = data[3].toString();
					
					scheduleData.setProductId(productId);
					logger.info("product Id::"+scheduleData.getProductId());
					String productName=(String) data[4];
					
					scheduleData.setProductName(productName);
					
					String acc=data[5].toString();
								
					scheduleData.setNoOfAccounts(acc);
					if(scheduleData.getCropId().equals("ST"))
						scheduleData.setLoanType("Short Term");
					else if(scheduleData.getCropId().equals("MT"))
						scheduleData.setLoanType("Mid Term");
					else
						scheduleData.setLoanType("Long Term");
			
			} catch (Exception e) {
				e.printStackTrace();
			}

			return scheduleData;

		}

	}



