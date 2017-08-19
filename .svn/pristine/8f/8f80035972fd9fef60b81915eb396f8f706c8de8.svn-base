package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.impl.ScheduleVsGLBalanceReportService;
import com.vsoftcorp.kls.report.action.ScheduleVsGLBalanceActionServlet;
import com.vsoftcorp.kls.report.service.data.ScheduleReportData;
import com.vsoftcorp.kls.report.service.data.ScheduleVsGLBalanceReportData;

public class ScheduleReportHelper {

		 private static final Logger logger = Logger
					.getLogger(ScheduleReportHelper.class);
		
	 public static ScheduleReportData getScheduleReport(Object data[]) {
		 
		 ScheduleReportData scheduleData = new ScheduleReportData();
				try {
					BigDecimal bal=new BigDecimal(0.00);
					if(data[0]!=null)
						scheduleData.setMemberName(data[0].toString());
					if(data[1]!=null)	
						scheduleData.setMemberNumber(data[1].toString());
					if(data[2]!=null)	
				        scheduleData.setLocNumber(data[2].toString());
					if(data[3]!=null){
						bal=new BigDecimal(data[3].toString());
						scheduleData.setClosingBalance(bal.setScale(2).toString());
					}
					else
						scheduleData.setClosingBalance(bal.setScale(2).toString());	
				} catch (Exception e) {
					e.printStackTrace();
				}

				return scheduleData;

			}

		}






