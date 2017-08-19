package com.vsoftcorp.kls.GepRep.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.GepRep.business.ReportParameters;
import com.vsoftcorp.kls.GepRep.business.ReportStructure;
import com.vsoftcorp.kls.GepRep.dao.IGenerateReportDAO;
import com.vsoftcorp.kls.GepRep.dao.factory.GenrepDataaccessFactory;
import com.vsoftcorp.kls.GepRep.data.ReportParametersData;
import com.vsoftcorp.kls.GepRep.data.ReportStructureData;
import com.vsoftcorp.kls.GepRep.service.IGenerateReportService;
import com.vsoftcorp.kls.GepRep.service.helper.GenerateReportServiceHelper;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class GenerateReportService implements IGenerateReportService {

	private static final Logger logger = Logger
			.getLogger(GenerateReportService.class);
	
	@Override
	public List<ReportStructureData> getAllReportStructure() {
		logger.info("Start: Inside method getAllReportStructure()");
		IGenerateReportDAO dao = GenrepDataaccessFactory.getGenerateReportDao();
		List<ReportStructureData> reportStructureDataList = new ArrayList<ReportStructureData>();
		try {
			List<ReportStructure> reportStructureList = dao.getAllReportStructure();
			for (ReportStructure reportStructure : reportStructureList) {
				reportStructureDataList.add(GenerateReportServiceHelper.getReportGenerateData(reportStructure));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all getAllReportStructure from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all getAllReportStructure from the database",e.getCause());
		}		
		logger.info("End : Inside method getAllReportStructure()");
		return reportStructureDataList;
	}

	@Override
	public List<ReportParametersData> getAllReportId(Integer reportId) {
		logger.info("Start: Inside method getAllReportId ()");
		IGenerateReportDAO dao = GenrepDataaccessFactory.getGenerateReportDao();
		List<ReportParametersData> dataList = new ArrayList<ReportParametersData>();
		try {
			List<ReportParameters> reportParametersList = dao.getAllReportParameterId(reportId);
			for (ReportParameters reportParameters : reportParametersList) {
				dataList.add(GenerateReportServiceHelper.getReportParameterId(reportParameters));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all report parameter  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all report parameter ", e.getCause());
		}
		logger.info("End : Inside method getAllReportId ()");
		return dataList;
	}

	@Override
	public ReportStructureData getReportStructureData(Integer reportId) {
		logger.info("Start: Inside method getReportStructureData ()");
		IGenerateReportDAO dao = KLSReportDataAccessFactory.getGenerateReportDAO();
		ReportStructureData reportStructureData = null;
		try {
			reportStructureData = GenerateReportServiceHelper.getReportGenerateData(dao.getReportStructureByReportId(reportId));
		}catch(Exception e) {
			e.printStackTrace();
			throw new DataAccessException("Error while retriving  report structure ", e.getCause());
		}
		logger.info("End : Inside method getReportStructureData ()");
		return reportStructureData;
	}

}
