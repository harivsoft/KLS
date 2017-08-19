package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.service.data.LandRegisterReportData;

public class LandRegisterReportHelper {

	private static final Logger logger = Logger.getLogger(LandRegisterReportHelper.class);

	public static List<LandRegisterReportData> getLandRegisterReportData(List<Object[]> landRegisterdata) {
		logger.info("Start:of LandRegisterReportHelper");
		List<LandRegisterReportData> landRegisterReportDataList = new ArrayList<LandRegisterReportData>();
		LandRegisterReportData landRegisterReportData = null;
		try {
			for (Object[] object : landRegisterdata) {

				landRegisterReportData = new LandRegisterReportData();
				Double cultivatedLand = (Double) object[1];
				Double totalLand = (Double) object[2];

				landRegisterReportData.setCultivatedLand(new BigDecimal(cultivatedLand).setScale(2, RoundingMode.HALF_UP));
				landRegisterReportData.setTotalLand(new BigDecimal(totalLand).setScale(2, RoundingMode.HALF_UP));
				landRegisterReportData.setBSNo((String) object[3]);
				landRegisterReportData.setChargeDeclared((String) object[5]);
				landRegisterReportData.setMortgaged((String) object[6]);
				landRegisterReportData.setRSNo((String) object[8]);
				landRegisterReportData.setSubVillage((String) object[9]);
				landRegisterReportData.setSurveyNumber((String) object[10]);
				landRegisterReportData.setMemberId((BigInteger) object[13]);
				landRegisterReportData.setVillage((String) object[14]);
				landRegisterReportData.setMemberName((String) object[15]);
				landRegisterReportData.setMobileNo((String) object[16]);
				landRegisterReportData.setLandType((String) object[17]);

				landRegisterReportDataList.add(landRegisterReportData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("End:of LandRegisterReportHelper");
		return landRegisterReportDataList;
	}

}
