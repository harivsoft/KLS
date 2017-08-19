package com.vsoftcorp.kls.GepRep.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.kls.GepRep.data.InputParametersData;
import com.vsoftcorp.kls.GepRep.data.ReportInputData;
import com.vsoftcorp.kls.GepRep.data.ReportParametersData;
import com.vsoftcorp.kls.GepRep.data.ReportStructureData;
import com.vsoftcorp.kls.GepRep.service.IGenerateReportService;
import com.vsoftcorp.kls.GepRep.service.factory.GenrepServiceFactory;
import com.vsoftcorp.kls.GepRep.util.GenRepUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;

@Path("/generate/report")
public class GenerateReportRestService {

	private static final Logger logger = Logger
			.getLogger(GenerateReportRestService.class);

	public GenerateReportRestService() {
	}

	private static final GenerateReportRestService INSTANCE = new GenerateReportRestService();

	public static GenerateReportRestService getInstance() {
		return INSTANCE;
	}

	@GET
	@Path("/reportstructure")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllReporsStructure() {
		logger.info("Start : Calling SuvikasReportStructureService . getAllReporsStructure() ");
		List<ReportStructureData> reportStructureDataList = new ArrayList<ReportStructureData>();
		String jsonAllReportStructure = "";
		try {
			IGenerateReportService generateReportService = GenrepServiceFactory
					.getGenerateReportService();
			reportStructureDataList = generateReportService
					.getAllReportStructure();
			Gson gson = new GsonBuilder().create();
			jsonAllReportStructure = gson.toJson(reportStructureDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End :Calling SuvikasReportStructureService . getAllReporsStructure() ");
		return jsonAllReportStructure;

	}

	@GET
	@Path("/reportparameter")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllReportId(@QueryParam("reportId") Integer reportId) {

		logger.info("Start : Calling reportparameter  service in getAllReportId() method.");
		List<ReportParametersData> reportDataList = new ArrayList<ReportParametersData>();
		String jsonAllReportIdString = "";
		try {
			IGenerateReportService generateReportService = GenrepServiceFactory
					.getGenerateReportService();
			reportDataList = generateReportService.getAllReportId(reportId);
			Gson gson = new GsonBuilder().create();
			jsonAllReportIdString = gson.toJson(reportDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling reportparameter  service in getAllReportId() method.");
		return jsonAllReportIdString;
	}

	@POST
	@Path("/generatereport")
	@Consumes("application/json")
	@Produces("application/pdf")
	public Response getGeneratePDFReport(String jsonReportParameters) {
		logger.info("Start : Calling GenerateReportRest  service in getGeneratePDFReport() method.");
		ResponseBuilder responseBuilder = null;
		try {
			Gson gson = new GsonBuilder().create();
			InputParametersData inputParametersData = gson.fromJson(jsonReportParameters, InputParametersData.class);
			IGenerateReportService generateReportService = GenrepServiceFactory.getGenerateReportService();
			ReportStructureData data = generateReportService.getReportStructureData(inputParametersData.getReportId());
			ReportInputData reportInputData = new ReportInputData();
			String query = GenRepUtil.getGeneratedQuery(new StringBuffer(data.getReportQuery()), inputParametersData.getUserInputs());
			if (data != null) {
				if (data.getReportQuery() != null) {
					reportInputData.setStrQry(query);
				}
				if (data.getReportColAlignment() != null) {
					reportInputData
							.setStrColAlign(data.getReportColAlignment());
				}
				if (data.getReportColumn() != null) {
					reportInputData.setStrColTitles(data.getReportColumn());
				}
				if (data.getReportOrderby() != null) {
					reportInputData.setOrderFalg(data.getReportOrderby());
				}
				if (data.getReportGroupby() != null) {
					reportInputData.setGroupParam(data.getReportGroupby());
				}
				if (data.getReportTitle() != null) {
					reportInputData.setStrPgHdrFtr(data.getReportTitle());
				}
				if (data.getReportColTotal() != null) {
					reportInputData.setStrTotal(data.getReportColTotal());
				}
				reportInputData.setStrBankname(inputParametersData.getBankName());
				reportInputData.setStrBranchname(inputParametersData.getBranchName());
				reportInputData.setColSpace(data.getReportColumnSize());
				reportInputData.setStrSlNoFlg("1");
//				reportInputData.setStrSkipCol("0");
				reportInputData.setStrErrMsg("DATA NOT AVAILABLE");
				File file = GenrepServiceFactory.getGenRepService().gerateReportInPDF(reportInputData);
				responseBuilder = Response.ok((Object) file);
			    responseBuilder.header("Content-Disposition","attachment; filename=report.pdf");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("End : Calling GenerateReportRest  service in getGeneratePDFReport() method.");
		
		return responseBuilder.build();
	}

}
