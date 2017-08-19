/**
 * 
 */
package com.vsoftcorp.kls.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
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
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.data.LoanApplicationEnumsData;
import com.vsoftcorp.kls.data.PacsLoanApplicationData;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.loan.IPacsLoanApplicationService;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

/**
 * @author a9152
 * 
 */
@Path("/pacs/loanapplication")
public class PacsLoanApplicationRestService {

	private static final Logger logger = Logger.getLogger(PacsLoanApplicationRestService.class);

	private static final PacsLoanApplicationRestService INSTANCE = new PacsLoanApplicationRestService();

	public PacsLoanApplicationRestService() {
	}

	public static PacsLoanApplicationRestService getInstance() {
		return INSTANCE;
	}

	@POST
	@Path("/entry")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveLoanApplicationEntry(String loanApplicationEntry) {

		logger.info("Start : Calling Pacs Loan Application Service in saveLoanApplicationEntry() method."+loanApplicationEntry);
		PacsLoanApplicationData data = null;
		String returnMessage = "";
		try {
			IPacsLoanApplicationService service = KLSServiceFactory.getPacsLoanApplicationService();
			Gson gson = new Gson();
			data = gson.fromJson(loanApplicationEntry, PacsLoanApplicationData.class);
			service.saveLoanApplication(data);
		} catch (Exception e) {
			returnMessage = e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Application Service in saveLoanApplicationEntry() method.");
		Integer applicationStatusValue = data.getApplicationStatus();
		if (applicationStatusValue == null || LoanApplicationState.ENTERED.getValue().equals(applicationStatusValue) || LoanApplicationState.ENTRY_PENDING.getValue().equals(applicationStatusValue)) {
			returnMessage = "LoanApplication - " + data.getId() + " Data Saved Successfully!";
		} else if (LoanApplicationState.INSPECTED.getValue().equals(
				applicationStatusValue)
				|| LoanApplicationState.AUTHORIZATION_PENDING.getValue().equals(
						applicationStatusValue) || LoanApplicationState.INSPECTION_PENDING.getValue().equals(
								applicationStatusValue) || LoanApplicationState.INSPECTION_RETURN.getValue().equals(
										applicationStatusValue)) {
			returnMessage = "LoanApplication - " + data.getId() + " Inspection Data Saved Successfully!";
		} else if (LoanApplicationState.SANCTIONED.getValue().equals(applicationStatusValue) || LoanApplicationState.SANCTION_PENDING.getValue().equals(applicationStatusValue) || LoanApplicationState.SANCTION_RETURN.getValue().equals(applicationStatusValue)) {
			returnMessage = "LoanApplication - " + data.getId() + " Sanction Data Saved Successfully!";
		} else if (LoanApplicationState.REJECTED.getValue().equals(applicationStatusValue)) {
			returnMessage = "LoanApplication - " + data.getId() + " Rejected Successfully!";
		}
		return returnMessage;
	}

/*	@PUT
	@Path("/entry")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateLoanApplicationEntry(String loanApplicationEntry) {

		logger.info("Start : Calling Pacs Loan Application Service in updateLoanApplicationEntry() method.");
		PacsLoanApplicationData data = null;
		try {
			IPacsLoanApplicationService service = KLSServiceFactory.getPacsLoanApplicationService();
			Gson gson = new Gson();
			data = gson.fromJson(loanApplicationEntry, PacsLoanApplicationData.class);
			service.updateLoanApplication(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Application Service in updateLoanApplicationEntry() method.");
		return "LoanApplication Data Updated Successfully!";
	}*/

	@GET
	@Path("/entry")
	@Produces("application/json")
	public String getAllDocument() {

		logger.info("Start : Calling Pacs Loan Application Service in updatePacsMTLTLoanApplication() method.");
		List<PacsLoanApplicationData> list = new ArrayList<PacsLoanApplicationData>();
		String jsonString = "";
		try {
			IPacsLoanApplicationService service = KLSServiceFactory.getPacsLoanApplicationService();
			// list = service.get();
			// Gson gson = new GsonBuilder().create();
			jsonString = "Get";
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Application Service in updatePacsMTLTLoanApplication() method.");

		return jsonString;
	}

	@GET
	@Path("/byid")
	@Produces("application/json")
	public String getLoanApplicationById(@QueryParam("applicationId") Long applicationId) {
		logger.info("Start : Calling Pacs Loan Application Service in updatePacsMTLTLoanApplication() method.");
		PacsLoanApplicationData loanApplicationData = null;
		String pacsLoanApplicationDataString = "";
		try {
			loanApplicationData = KLSServiceFactory.getPacsLoanApplicationService().getLoanApplicationById(
					applicationId);
			Gson gson = new GsonBuilder().create();
			pacsLoanApplicationDataString = gson.toJson(loanApplicationData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Application Service in getLoanApplicationById() method.");

		return pacsLoanApplicationDataString;
	}

	@GET
	@Path("/bycustomerid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllApplicationsBasedOnCustId(@QueryParam("customerId") Long customerId,
			@QueryParam("applicationStatus") Integer applicationStatus) {

		logger.info("Start : Calling Pacs Loan Application Service in getAllApplicationsBasedOnCustId() method.");
		List<Map> list = new ArrayList<Map>();
		String jsonString = "";
		LoanApplicationState loanApplcationState = null;
		try {
			IPacsLoanApplicationService service = KLSServiceFactory.getPacsLoanApplicationService();
			if (customerId != null && applicationStatus != null) {
				loanApplcationState = LoanApplicationState.getType(applicationStatus);

				list = service.getAllApplicationsBasedOnCustId(customerId, loanApplcationState);

				Gson gson = new GsonBuilder().create();
				jsonString = gson.toJson(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Application Service in getAllApplicationsBasedOnCustId() method.");

		return jsonString;
	}

	@GET
	@Path("/enums")
	@Produces("application/json")
	public String getAllLoanApplicationEnums() {

		logger.info("Start : Fetching all loan application enums in getAllLoanApplicationEnums() method.");
		String loanApplicationEnumsString = "";
		try {
			IPacsLoanApplicationService pacsLoanApplicationService = KLSServiceFactory.getPacsLoanApplicationService();
			LoanApplicationEnumsData loanApplicationEnumsData = pacsLoanApplicationService.getAllLoanApplicationEnums();
			Gson gson = new GsonBuilder().create();
			loanApplicationEnumsString = gson.toJson(loanApplicationEnumsData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Fetching all loan application enums in getAllLoanApplicationEnums() method.");
		return loanApplicationEnumsString;
	}

	public String deleteActivityByApplicationId(@QueryParam("applicationId") Long applicationId,
			@QueryParam("activityId") Long activityId) {
		logger.info("Start : Calling Pacs Loan Application Service in deleteActivityByApplicationId() method.");
		String jsonString = "";
		try {

		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Delete the Activities in deleteActivityByApplicationId method.");

		return null;
	}

	@GET
	@Path("/getrateofinterest")
	@Consumes("*/*")
	@Produces("application/json")
	public String getRateOfInterest(@QueryParam("interestCategoryId") Integer interestCategoryId,
			@QueryParam("amount") String amount, @QueryParam("months") Integer months) {

		logger.info("Start : Calling Pacs Loan Application Service in getRateOfInterest() method.==="
				+ Money.valueOf(Currency.getInstance("INR"), amount));
		List<Map> list = new ArrayList<Map>();
		String jsonString = "";
		try {
			IPacsLoanApplicationService service = KLSServiceFactory.getPacsLoanApplicationService();
			if (interestCategoryId != null && amount != null) {

				list = service.getAllRateOfInterest(interestCategoryId,
						(Money.valueOf(Currency.getInstance("INR"), amount)), months);

				Gson gson = new GsonBuilder().create();
				jsonString = gson.toJson(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Application Service in getAllApplicationsBasedOnCustId() method.");

		return jsonString;
	}

	@GET
	@Path("/getnoofinstallmentsandinstallmentamount")
	@Consumes("*/*")
	@Produces("application/json")
	public String getNumOfInstallmentsAndInstallmentAmount(@QueryParam("loanPeriod") Integer loanPeriod,
			@QueryParam("installmentFrequency") Integer installmentFrequency,
			@QueryParam("applicationId") Long applicationId) {

		logger.info("Start : Calculating number of installments and installment amount in getNumOfInstallmentsAndInstallmentAmount() method.");
		List<Map> list = new ArrayList<Map>();
		String jsonString = "";

		try {
			IPacsLoanApplicationService service = KLSServiceFactory.getPacsLoanApplicationService();
			if (loanPeriod != null && installmentFrequency != null) {
				list = service.getNumOfInstallmentsAndInstallmentAmount(loanPeriod, installmentFrequency, applicationId);
				Gson gson = new GsonBuilder().create();
				jsonString = gson.toJson(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calculating number of installments and installment amount in getNumOfInstallmentsAndInstallmentAmount() method.");
		return jsonString;
	}
	
	@GET
	@Path("/getDocument")
	@Consumes("*/*")
	@Produces("application/json")
	public String getDocument(@QueryParam("file") String file) {
		logger.info("Get Document Id method entered");
		String documentData = null;
		try {
			documentData = KLSServiceFactory.getPacsLoanApplicationService().getdocument(file); 
		}catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Get Document Id method Returning");
		return "{\"fileData\":\""+documentData+"\"}";
	}
	@GET
	@Path("/bymemberId")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllApplicationsBasedOnMemberId(@QueryParam("customerId") Long customerId,
			@QueryParam("applicationStatus") Integer applicationStatus,@QueryParam("applicationType") String applicationType) {

		logger.info("Start : Calling Pacs Loan Application Service in getAllApplicationsBasedOnCustId() method.");
		List<Map> list = new ArrayList<Map>();
		String jsonString = "";
		LoanApplicationState loanApplcationState = null;
		try {
			IPacsLoanApplicationService service = KLSServiceFactory.getPacsLoanApplicationService();
			if (customerId != null && applicationStatus != null) {
				loanApplcationState = LoanApplicationState.getType(applicationStatus);

				list = service.getAllApplicationsBasedOnCustId(customerId, loanApplcationState,applicationType);

				Gson gson = new GsonBuilder().create();
				jsonString = gson.toJson(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Application Service in getAllApplicationsBasedOnCustId() method.");

		return jsonString;
	}
	
	
	@GET
	@Path("/bymemberIdAndLoanType")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllApplicationsBasedOnMemberIdAndLoanType(@QueryParam("customerId") Long customerId,
			@QueryParam("applicationStatus") Integer applicationStatus,@QueryParam("applicationType") String applicationType, 
			@QueryParam("loanType") String loanType) {

		logger.info("Start : Calling Pacs Loan Application Service in getAllApplicationsBasedOnMemberIdAndLoanType() method.");
		List<Map> list = new ArrayList<Map>();
		String jsonString = "";
		LoanApplicationState loanApplcationState = null;
		try {
			IPacsLoanApplicationService service = KLSServiceFactory.getPacsLoanApplicationService();
			if (customerId != null && applicationStatus != null) {
				loanApplcationState = LoanApplicationState.getType(applicationStatus);

				list = service.getAllApplicationsBasedOnCustIdAndLoanType(customerId, loanApplcationState,applicationType,loanType);

				Gson gson = new GsonBuilder().create();
				jsonString = gson.toJson(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Application Service in getAllApplicationsBasedOnMemberIdAndLoanType() method.");

		return jsonString;
	}
}
