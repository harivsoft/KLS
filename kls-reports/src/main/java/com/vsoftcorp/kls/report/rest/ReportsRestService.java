package com.vsoftcorp.kls.report.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.kls.data.TalukaData;
import com.vsoftcorp.kls.report.factory.KLSReportSeriveFactory;
import com.vsoftcorp.kls.report.service.data.BorrowingAccountLedgerReportData;
import com.vsoftcorp.kls.rest.service.KLSMasterRestService;
import com.vsoftcorp.kls.util.EntityManagerUtil;

@Path("/report")
public class ReportsRestService {

	private static final Logger logger = Logger.getLogger(KLSMasterRestService.class);

	public ReportsRestService() {

	}

	private static final ReportsRestService INSTANCE = new ReportsRestService();

	public static ReportsRestService getInstance() {
		return INSTANCE;
	}
	
	@GET
	@Path("lendingLedger")
	@Produces("application/json")
	public String getLendingLedger(@QueryParam("pacsId") Integer pacsId, @QueryParam("productId") Integer productId, @QueryParam("purposeId") Integer purposeId,  
			@QueryParam("borrowingType") String borrowingType,@QueryParam("fromDateString") String fromDateString,
			@QueryParam("toDateString") String toDateString, @QueryParam("memberId") String memberId, @QueryParam("noOfRecords") Integer noOfRecords) {
		logger.info("Start: Calling lending ledger report");
		String talukasJson = "";
		List<TalukaData> talukas = new ArrayList<TalukaData>();
		try {
			Gson gson = new GsonBuilder().create();

			List<BorrowingAccountLedgerReportData> list= KLSReportSeriveFactory
					.getBorrowingAccountLedgerReportService().getLendingLedgerData(pacsId, productId, purposeId, borrowingType,
							fromDateString, toDateString,memberId,noOfRecords);
			talukasJson = gson.toJson(list);

		} catch (Exception e) {
			logger.error("Error while retriving all talukas");
			return "exception in get all talukas";
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Calling lending ledger report");
		return talukasJson;
	}
	@GET
	@Path("lendingLedgerbyLocId")
	@Produces("application/json")
	public String getLendingLedgerDatabyLocId(@QueryParam("pacsId") Integer pacsId, @QueryParam("productId") Integer productId, @QueryParam("purposeId") Integer purposeId,  
			@QueryParam("borrowingType") String borrowingType,@QueryParam("fromDateString") String fromDateString,
			@QueryParam("toDateString") String toDateString, @QueryParam("memberId") String memberId, @QueryParam("noOfRecords") Integer noOfRecords, @QueryParam("locId") String locId,@QueryParam("type") String type) {
		logger.info("Start: Calling lending ledger report");
		String talukasJson = "";
		List<TalukaData> talukas = new ArrayList<TalukaData>();
		try {
			Gson gson = new GsonBuilder().create();

			List<BorrowingAccountLedgerReportData> list= KLSReportSeriveFactory
					.getBorrowingAccountLedgerReportService().getLendingLedgerDatabyLocId(borrowingType,
							fromDateString, toDateString,noOfRecords,locId,type);
			talukasJson = gson.toJson(list);

		} catch (Exception e) {
			logger.error("Error while retriving all talukas");
			return "exception in get all talukas";
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Calling lending ledger report");
		return talukasJson;
	}
}
