package com.vsoftcorp.kls.rest.service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.BankParameterData;
import com.vsoftcorp.kls.data.LandApplicationDetail;
import com.vsoftcorp.kls.data.LoanApplicationRenewalData;
import com.vsoftcorp.kls.data.LoanSanctionData;
import com.vsoftcorp.kls.data.LoanSanctionSummaryData;
import com.vsoftcorp.kls.data.PacsLoanApplicationDetailData;
import com.vsoftcorp.kls.data.PacsLoanApplicationHeaderData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IBankParameterService;
import com.vsoftcorp.kls.service.ILocClosureService;
import com.vsoftcorp.kls.service.IPacsLoanApplicationDetailService;
import com.vsoftcorp.kls.service.IPacsLoanApplicationHeaderService;
import com.vsoftcorp.kls.service.IScaleOfFinanceService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.MasterHelper;
import com.vsoftcorp.kls.service.loan.ILoanApplicationRenewalService;
import com.vsoftcorp.kls.service.loan.ILoanInterestService;
import com.vsoftcorp.kls.service.loan.IPacsLoanSanctionProcessService;
import com.vsoftcorp.kls.service.loan.impl.LoanInterestService;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

@Path("/loan")
public class KLSLoanRestService {
	private static final Logger logger = Logger.getLogger(KLSLoanRestService.class);

	public KLSLoanRestService() {

	}

	private static final KLSLoanRestService INSTANCE = new KLSLoanRestService();

	public static KLSLoanRestService getInstance() {
		return INSTANCE;
	}

	@POST
	@Path("/pLoanApplnHdr")
	@Consumes("application/json")
	@Produces("application/text")
	public String savePacsLoanApplicationHeader(String pLoanApplnHdrJsonString) {

		logger.info("Start : Calling PacsLoanApplicationHeader service in savePacsLoanApplicationHeader() method.");
		PacsLoanApplicationHeaderData data = null;
		Long headerId = null;
		try {
			IPacsLoanApplicationHeaderService pLoanApplnHdrService = KLSServiceFactory
					.getPacsLoanApplicationHeaderService();
			Gson gson = new Gson();
			data = gson.fromJson(pLoanApplnHdrJsonString, PacsLoanApplicationHeaderData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			// data.setPacsId("4");
			data.setProcessStatus("A");
			headerId = pLoanApplnHdrService.savePacsLoanApplicationHeader(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling savePacsLoanApplicationHeader service in savePacsLoanApplicationHeader() method.");
		return "Master loan application created successfully with Master Application No.: "
				+ headerId;
	}

	@GET
	@Path("/pLoanApplnHdr")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsLoanApplicationHeaders(@QueryParam("processStatus") String processStatus,
			@QueryParam("pacsId") String pacsIdString,@QueryParam("branchId") Integer branchId) {

		logger.info("Start : Calling PacsLoanApplicationHeader master service in getPacsLoanApplicationHeaders() method.");
		List<PacsLoanApplicationHeaderData> pLoanApplnHdrDataList = new ArrayList<PacsLoanApplicationHeaderData>();
		String jsonAllPLoanApplnHdrString = "";
		try {
			IPacsLoanApplicationHeaderService pLoanApplnHdrService = KLSServiceFactory
					.getPacsLoanApplicationHeaderService();
			Integer pacsId=null;
			if(pacsIdString!=null)
				pacsId=Integer.parseInt(pacsIdString);
			logger.info("pacsId  :---  "+pacsId);
			if(processStatus.equals("C"))
				pacsId=null;
			pLoanApplnHdrDataList = pLoanApplnHdrService
					.getPacsLoanApplicationHeaders(processStatus,pacsId,branchId);
			Gson gson = new GsonBuilder().create();
			jsonAllPLoanApplnHdrString = gson.toJson(pLoanApplnHdrDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling PacsLoanApplicationHeader master service in getPacsLoanApplicationHeaders() method.");
		return jsonAllPLoanApplnHdrString;
	}

	@PUT
	@Path("/pLoanApplnHdr")
	@Consumes("application/json")
	@Produces("application/text")
	public String closePacsLoanApplicationHeader(String pLoanApplnHdrJsonString) {

		logger.info("Start : Calling PacsLoanApplicationHeader master service in updatePacsLoanApplicationHeader() method.");
		PacsLoanApplicationHeaderData data = null;
		try {
			IPacsLoanApplicationHeaderService pLoanApplnHdrService = KLSServiceFactory
					.getPacsLoanApplicationHeaderService();

			Gson gson = new Gson();
			data = gson.fromJson(pLoanApplnHdrJsonString, PacsLoanApplicationHeaderData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			pLoanApplnHdrService.closePacsLoanApplicationHeader(data);
		} catch (Exception e) {
			logger.error("Error while updating PacsLoanApplicationHeader Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Calling PacsLoanApplicationHeader master service in updatePacsLoanApplicationHeader() method.");
		return "PacsLoanApplication Closed Successfully!";
	}

	@POST
	@Path("/pacsLoanApplication")
	@Consumes("application/json")
	@Produces("application/text")
	public String savePacsLoanApplicationDetail(String pacsLoanApplDetailJsonString) {

		logger.info("Start : Calling pacs loan application detail service in savePacsLoanApplicationDetail() method.");
		PacsLoanApplicationDetailData data = null;
		System.out.println("pacsLoanApplDetailJsonString is : " + pacsLoanApplDetailJsonString);
		try {
			IPacsLoanApplicationDetailService pacsLoanApplDetailService = KLSServiceFactory
					.getPacsLoanApplicationDetailService();
			Gson gson = new Gson();
			data = gson.fromJson(pacsLoanApplDetailJsonString, PacsLoanApplicationDetailData.class);
			pacsLoanApplDetailService.savePacsLoanApplicationDetail(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling pacs loan application detail service in savePacsLoanApplicationDetail() method.");
		return "PacsLoanApplicationDetail Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/pacsLoanApplication")
	@Consumes("application/json")
	@Produces("application/text")
	public String updatePacsLoanApplicationDetail(String pacsLoanApplDetailJsonString) {

		logger.info("Start : Calling pacs loan application detail service in updatePacsLoanApplicationDetail() method.");
		PacsLoanApplicationDetailData data = null;
		System.out.println("pacsLoanApplDetailJsonString is : " + pacsLoanApplDetailJsonString);
		try {
			IPacsLoanApplicationDetailService pacsLoanApplDetailService = KLSServiceFactory
					.getPacsLoanApplicationDetailService();
			Gson gson = new Gson();
			data = gson.fromJson(pacsLoanApplDetailJsonString, PacsLoanApplicationDetailData.class);
			pacsLoanApplDetailService.updatePacsLoanApplicationDetail(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling pacs loan application detail service in updatePacsLoanApplicationDetail() method.");
		return "PacsLoanApplicationDetail Data Updated Successfully!";
	}

	@GET
	@Path("/pacsLoanApplication")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsLoanApplicationDetailList(@QueryParam("headerId") String headerIdString) {

		logger.info("Start : Calling pacs loan application detail service in getPacsLoanApplicationDetails() method.");
		List<PacsLoanApplicationDetailData> pacsLoanApplDetailDataList = new ArrayList<PacsLoanApplicationDetailData>();
		String jsonAllPacsLoanApplDetailString = "";
		try {
			IPacsLoanApplicationDetailService pacsLoanApplDetailService = KLSServiceFactory
					.getPacsLoanApplicationDetailService();
			if (headerIdString != null) {
				Long headerId = new Long(headerIdString);
				pacsLoanApplDetailDataList = pacsLoanApplDetailService
						.getPacsLoanApplicationDetailsByHeaderId(headerId);
				Gson gson = new GsonBuilder().create();
				jsonAllPacsLoanApplDetailString = gson.toJson(pacsLoanApplDetailDataList);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling pacs loan application detail service in getPacsLoanApplicationDetails() method.");
		return jsonAllPacsLoanApplDetailString;
	}

	@GET
	@Path("/pacsLoanApplicationByStatus")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsLoanApplicationDetailListByStatus(@QueryParam("headerId") String headerIdString) {
		//added for Inspection Authorization.
		logger.info("Start : Calling pacs loan application detail service in getPacsLoanApplicationDetailListByStatus() method.");
		List<PacsLoanApplicationDetailData> pacsLoanApplDetailDataList = new ArrayList<PacsLoanApplicationDetailData>();
		String jsonAllPacsLoanApplDetailString = "";
		try {
			IPacsLoanApplicationDetailService pacsLoanApplDetailService = KLSServiceFactory
					.getPacsLoanApplicationDetailService();
			if (headerIdString != null) {
				Long headerId = new Long(headerIdString);
				pacsLoanApplDetailDataList = pacsLoanApplDetailService
						.getPacsLoanApplicationDetailsByHeaderIdWithStatus(headerId);
				Gson gson = new GsonBuilder().create();
				jsonAllPacsLoanApplDetailString = gson.toJson(pacsLoanApplDetailDataList);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling pacs loan application detail service in getPacsLoanApplicationDetailListByStatus() method.");
		return jsonAllPacsLoanApplDetailString;
	}

	@GET
	@Path("/pacsScaleOfFinance")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsScaleOfFinanceAmount(@QueryParam("seasonId") String seasonIdString,
			@QueryParam("cropId") String cropIdString,
			@QueryParam("landTypeId") String landTypeIdString,
			@QueryParam("landArea") String landAreaString, @QueryParam("pacsId") String pacsIdString){

		logger.info("Start : Calling scale of finance service in getPacsScaleOfFinanceAmount() method.");

		String jsonScaleOfFinanceAmount = "";
		try {
			IScaleOfFinanceService scaleOfFinanceService = KLSServiceFactory
					.getScaleOfFinanceService();
			if (seasonIdString != null && cropIdString != null && landTypeIdString != null
					&& landAreaString != null && pacsIdString != null) {
				Long seasonId = new Long(seasonIdString);
				Integer cropId = new Integer(cropIdString);
				Integer landTypeId = new Integer(landTypeIdString);
				BigDecimal landArea = MasterHelper.roundTo2DecimalPlaces(landAreaString);
				Integer pacsId = new Integer(pacsIdString);
				jsonScaleOfFinanceAmount = scaleOfFinanceService.getScaleOfFinanceAmount(seasonId,
						cropId, landTypeId, landArea, pacsId);
				if (jsonScaleOfFinanceAmount != null)
					jsonScaleOfFinanceAmount = MoneyUtil.getAmountRoundedValue(
							new BigDecimal(jsonScaleOfFinanceAmount)).toString();
				else
					jsonScaleOfFinanceAmount = "Scale of Finance not defined";
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling scale of finance service in getPacsScaleOfFinanceAmount() method.");
		return jsonScaleOfFinanceAmount;
	}

	@GET
	@Path("/pacsLoanSanctionApplication")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsLoanApplDetailListToSanction(@QueryParam("pacId") String pacId,
			@QueryParam("financialYear") String financialYear,
			@QueryParam("pageIndex") Integer pageIndex,
			@QueryParam("path") String pathString, @QueryParam("customerId") String customerId,
			@QueryParam("sanctionedAmount") String sanctionedAmount, @QueryParam("id") String id , @QueryParam("businessDate") String businessDate ) {
		logger.info("start: Calling pacsLoanSanctionProcessService in getPacsLoanApplDetailListToSanction() method."+businessDate);
		String jsonLoanSanctionData = "";
		Gson gson = new GsonBuilder().create();
		IPacsLoanSanctionProcessService pacsLoanSanctionProcessService = KLSServiceFactory
				.getPacsLoanSanctionProcessService();
		try {
			if (pathString.equalsIgnoreCase("pacSearch")) {
				List<String> financialYearList = null;
				if (pacId != null) {
					String currentFinancialYear = DateUtil.getFinancialYear(DateUtil.getVSoftDateByString(businessDate));
					String startYear = currentFinancialYear.substring(0,currentFinancialYear.indexOf('-'));
					String endYear = currentFinancialYear.substring(currentFinancialYear.indexOf('-')+1);
					financialYearList = KLSServiceFactory.getPacsLoanApplicationHeaderService()
							.getAllFinancialYearsByPacId(pacId);
					
					for (Iterator iterator = financialYearList.iterator(); iterator
							.hasNext();) {
						String eachList = (String) iterator.next();
					
						String startFinancialYear = eachList.substring(0,eachList.indexOf('-'));
						String endFinancialYear = eachList.substring(eachList.indexOf('-')+1);
						if(Integer.parseInt(startYear) > Integer.parseInt(startFinancialYear) && Integer.parseInt(endYear) > Integer.parseInt(endFinancialYear)){
							iterator.remove();
						}
					}
					jsonLoanSanctionData = "{\"pacsFinancialyears\" :"
							+ gson.toJson(financialYearList) + "}";
				}
				if (financialYearList.isEmpty())
					jsonLoanSanctionData = "Financial years not found";
			} else if (pathString.equalsIgnoreCase("customerApplications")) {
				PacsLoanApplicationDetailData loanApplicationDetailData = null;
				if (pacId != null && customerId != null && sanctionedAmount != null
						&& financialYear != null) {
					loanApplicationDetailData = pacsLoanSanctionProcessService
							.getPacsLoanApplicationByCustomer(customerId, pacId, financialYear,
									Money.valueOf(Currency.getInstance("INR"), sanctionedAmount));
					jsonLoanSanctionData = gson.toJson(loanApplicationDetailData);
				}
				if (loanApplicationDetailData == null)
					jsonLoanSanctionData = "Customer loan applications not found";

			} else if (pathString.equalsIgnoreCase("applicationSearch")) {
				List<LoanSanctionData> loanSanctionDataList = null;
				if (pacId != null && financialYear != null) {
					loanSanctionDataList = pacsLoanSanctionProcessService.getLoanSanctionDetails(
							pacId, financialYear,pageIndex);
					jsonLoanSanctionData = "{\"loanSanctionDataList\" :"
							+ gson.toJson(loanSanctionDataList) + "}";
				}
				if (loanSanctionDataList == null)
					jsonLoanSanctionData = "Pending applications are not found";
			} else if (pathString.equalsIgnoreCase("loanSummary")) {
				LoanSanctionSummaryData loanSanctionSummaryData = null;
				if (pacId != null && financialYear != null) {
					loanSanctionSummaryData = pacsLoanSanctionProcessService
							.getLoanSanctionSummary(pacId, financialYear);
					jsonLoanSanctionData = gson.toJson(loanSanctionSummaryData);
				}
				if (loanSanctionSummaryData == null)
					jsonLoanSanctionData = "Summary not found";
			} else if (pathString.equalsIgnoreCase("updateSanctionAmount")) {
				if (id != null && sanctionedAmount != null)
					jsonLoanSanctionData = pacsLoanSanctionProcessService
							.getShareAndInsuranceAmount(Long.parseLong(id), sanctionedAmount);
			}
		} catch (Exception e) {
			logger.error("Error occured while retrieving Pacs loan application details listt to be sanctioned");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling pacsLoanSanctionProcessService in getPacsLoanApplDetailListToSanction() method.");
		return jsonLoanSanctionData;
	}

	@POST
	@Path("/pacsLoanSanctionApplication")
	@Consumes("*/*")
	@Produces("application/text")
	public String updateLoanSanctionProcess(String loanSanctionString) {
		logger.info("start: Calling pacsLoanSanctionProcessService in updateLoanSanctionProcess() method.");
		String jsonLoanSanctionData = null;
		LoanSanctionData data = null;
		try {
			Gson gson = new Gson();
			data = gson.fromJson(loanSanctionString, LoanSanctionData.class);
			KLSServiceFactory.getPacsLoanSanctionProcessService().updateLoanSanction(data);
			if (data.getStatus().equals("1"))
				jsonLoanSanctionData = "Loan Amount Rs."
						+ MoneyUtil.getAmountRoundedValue(Money.valueOf(
								Currency.getInstance("INR"), data.getSanctionedAmount())
								.getAmount()) + " is sanctioned for customer: "
						+ data.getCustomerName();
			else
				jsonLoanSanctionData = "Loan rejected for customer: " + data.getCustomerName();
		} catch (Exception exception) {
			logger.error("Error: while updating loan sanction in updateLoanSanctionProcess() method.");
			return exception.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Calling pacsLoanSanctionProcessService in updateLoanSanctionProcess() method.");

		return jsonLoanSanctionData;
	}

	@PUT
	@Path("/pacsLoanSanctionApplication")
	@Consumes("*/*")
	@Produces("application/text")
	public String updateSanctionAmount(String loanSanctionString) {
		logger.info("start: Calling pacsLoanSanctionProcessService in updateSanctionAmount() method.");
		List<LoanSanctionData> loanSanctionDataList = null;
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<List<LoanSanctionData>>() {
			}.getType();
			loanSanctionDataList = gson.fromJson(loanSanctionString, type);
			KLSServiceFactory.getPacsLoanSanctionProcessService().updateSanctionAmount(
					loanSanctionDataList);
		} catch (Exception exception) {
			logger.error("Error: while updating loan sanction in updateSanctionAmount() method.");
			return exception.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Calling pacsLoanSanctionProcessService in updateSanctionAmount() method.");

		return "Sanction amount updated successfully";
	}

	@GET
	@Path("/tempInterestCalculation")
	@Consumes("*/*")
	@Produces("application/text")
	public String calculateInterest(@QueryParam("date") String dateString) {
		logger.info("start: calculating interesrt dateString:" + dateString);
		//Commented as this method no longer use
		/*try {
			ILoanInterestService service = new LoanInterestService();
			if (dateString != null)
				service.calculateInterest(DateUtil.getVSoftDateByString(dateString));
		} catch (Exception exception) {
			logger.error("Error: while calculating interest updateSanctionAmount() method.");
			return exception.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Calling pacsLoanSanctionProcessService in updateSanctionAmount() method.");*/

		return "Interest calculated successfully";
	}

	//@GET
	//@Path("/tempInterestPosting")
	//@Consumes("*/*")
	//@Produces("application/text")
	/*public String postingInterest(@QueryParam("date") String dateString) {
		logger.info("start: posting interest dateString:" + dateString);
		try {
			ILoanInterestService service = new LoanInterestService();
			if (dateString != null)
				service.postInterest(DateUtil.getVSoftDateByString(dateString),"MonthEnd");
		} catch (Exception exception) {
			logger.error("Error: while posting interest.");
			return exception.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("posting interest");

		return "Interest posted successfully";
	}*/

	@PUT
	@Path("/locClosure")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateLocCloser(String locCloserJsonString) {

		logger.info("Start : Calling LocClosure service in updateLocCloser() method.");
		BankParameterData data = null;
		int numOfColumnsClosed = 0;
		try {
			ILocClosureService locClosureService = KLSServiceFactory.getLocClosureService();
			Gson gson = new Gson();
			data = gson.fromJson(locCloserJsonString, BankParameterData.class);
			numOfColumnsClosed = locClosureService.updateLocClosure(data.getBusinessDate());
		} catch (Exception e) {
			// e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LocClosure service in updateLocCloser() method.");
		if (numOfColumnsClosed > 0) {
			return "LOC Closure is done successfully for " + numOfColumnsClosed
					+ " Line Of Credits";
		} else {
			return "There is no records for LOC Closure";
		}
	}

	@POST
	@Path("/loanApplicationRenewal")
	@Consumes("*/*")
	@Produces("application/text")
	public String updateLoanApplicationRenewal(String FinancialYearData) {
		logger.info("start: loan application renewal" + FinancialYearData);
		String response = "";
		LoanApplicationRenewalData data = null;
		try {
			Gson gson = new Gson();
			data = gson.fromJson(FinancialYearData, LoanApplicationRenewalData.class);
			ILoanApplicationRenewalService service = KLSServiceFactory
					.getLoanApplicationRenewalService();
			response = service.updateLoanApplicationRenewal(data, data.getLoggedInUserDetails()
					.getPacsId());
		} catch (Exception exception) {
			logger.error("Error: while doing application renewal");
			return exception.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Loan application renewal, Response :" + response);
		return response;
	}
	
	@GET
	@Path("/totalNoOfInspectedLoans")
	@Consumes("*/*")
	@Produces("application/json")
	public Integer getTotalNoOfInspectedLoans(@QueryParam("pacId") String pacId,
			@QueryParam("financialYear") String financialYear){
		logger.info("START: Getting total no of inspectecd loans in PacsLoanSanctionProcess Rest Service class");
		Integer totalInspectedLoans=0;
		try {
			IPacsLoanSanctionProcessService sanctionService = KLSServiceFactory.getPacsLoanSanctionProcessService();
			totalInspectedLoans = sanctionService.getTotalNoOfLoanInspectedApplications(pacId, financialYear);
		} catch (Exception e) {
			logger.info("ERROR: Error Getting total no of inspectecd loans in PacsLoanSanctionProcess Rest Service class");
			e.printStackTrace();
		}
		
		logger.info("END: Getting total no of inspectecd loans in PacsLoanSanctionProcess Rest Service class");
		return totalInspectedLoans;
		
	}
	
	@GET
	@Path("/pLoanApplnHdrByPacsId")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsLoanApplicationHeadersByPacsId(@QueryParam("pacsId") Integer pacsId) {

		logger.info("Start : Calling PacsLoanApplicationHeader master service in getPacsLoanApplicationHeaders() method.");
		List<PacsLoanApplicationHeaderData> pLoanApplnHdrDataList = new ArrayList<PacsLoanApplicationHeaderData>();
		String jsonAllPLoanApplnHdrString = "";
		try {
			IPacsLoanApplicationHeaderService pLoanApplnHdrService = KLSServiceFactory
					.getPacsLoanApplicationHeaderService();
			pLoanApplnHdrDataList = pLoanApplnHdrService.getPacsLoanApplicationHeadersByPacsId(pacsId);
			Gson gson = new GsonBuilder().create();
			jsonAllPLoanApplnHdrString = gson.toJson(pLoanApplnHdrDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling PacsLoanApplicationHeader master service in getPacsLoanApplicationHeaders() method.");
		return jsonAllPLoanApplnHdrString;
	}
	
	@GET
	@Path("/pacsLoanApplicationEntryModify")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsLoanApplicationDetailForModification(@QueryParam("headerId") String headerIdString) {

		logger.info("Start : Calling pacs loan application detail service in getPacsLoanApplicationDetails() method.");
		List<PacsLoanApplicationDetailData> pacsLoanApplDetailDataList = new ArrayList<PacsLoanApplicationDetailData>();
		String jsonAllPacsLoanApplDetailString = "";
		try {
			IPacsLoanApplicationDetailService pacsLoanApplDetailService = KLSServiceFactory
					.getPacsLoanApplicationDetailService();
			if (headerIdString != null) {
				Long headerId = new Long(headerIdString);
				pacsLoanApplDetailDataList = pacsLoanApplDetailService.getPacsLoanApplicationDetailsEntryModify(headerId);
				Gson gson = new GsonBuilder().create();
				jsonAllPacsLoanApplDetailString = gson.toJson(pacsLoanApplDetailDataList);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling pacs loan application detail service in getPacsLoanApplicationDetails() method.");
		return jsonAllPacsLoanApplDetailString;
	}
}
