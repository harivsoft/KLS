package com.vsoftcorp.kls.report.service.helper;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.service.data.DeceasedReportData;

public class DeceasedReportHelper {
	
	private static final Logger logger = Logger
			.getLogger(DeceasedReportHelper.class);

	public static List<DeceasedReportData> getDeceasedReportData(List<Object[]> deceasedList) {
		
		logger.info("Start: of DeceasedReportHelper");
		List<DeceasedReportData> deceasedReportDataList = new ArrayList<DeceasedReportData>();
		DeceasedReportData deceasedReportData=null;
		try {
			for (Object[] object : deceasedList) {
				deceasedReportData=new DeceasedReportData();
				
				deceasedReportData.setMemberName((String)object[0]);
				deceasedReportData.setMemberCode((String)object[1]);
				deceasedReportData.setFather((String)object[2]);
				Date tranDate = (Date) object[3];
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String text = df.format(tranDate);
				deceasedReportData.setDateOfDeceased(text);
				deceasedReportData.setSubVillage((String)object[5]);
				deceasedReportData.setVillage((String)object[6]);
				deceasedReportData.setTaluka((String)object[7]);
				deceasedReportData.setDistrict((String)object[8]);
				deceasedReportData.setRemarks((String)object[9]);
				
				deceasedReportDataList.add(deceasedReportData);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("End: of DeceasedReportHelper");	
		return deceasedReportDataList;
		}
			
	}
	
	


