/**
 * 
 */
package com.vsoftcorp.kls.rest.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Document;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.data.AccountData;
import com.vsoftcorp.kls.data.AddressData;
import com.vsoftcorp.kls.data.LoanDisbursementEntryDataList;
import com.vsoftcorp.kls.data.BulkRecoveryData;
import com.vsoftcorp.kls.data.BulkSTRecoveryResponse;
import com.vsoftcorp.kls.data.CustXLData;
import com.vsoftcorp.kls.data.LoanRepaymentScheduleData;
import com.vsoftcorp.kls.data.MTAccountSummary;
import com.vsoftcorp.kls.data.MTMultipleDisburseData;
import com.vsoftcorp.kls.data.MTMultiplePassingDataList;
import com.vsoftcorp.kls.data.MemberAccountsSummaryData;
import com.vsoftcorp.kls.data.STAccountsSummary;
import com.vsoftcorp.kls.data.PacsLoanApplicationData;
import com.vsoftcorp.kls.data.ChargesDebitData;
import com.vsoftcorp.kls.data.ChargesEnumData;
import com.vsoftcorp.kls.data.ChargesMasterData;
import com.vsoftcorp.kls.data.DisbursementData;
import com.vsoftcorp.kls.data.LoanAccountEnumsData;
import com.vsoftcorp.kls.data.LoanAccountPropertyData;
import com.vsoftcorp.kls.data.LoanDisbursementEntryData;
import com.vsoftcorp.kls.data.LoanDisbursementEntryList;
import com.vsoftcorp.kls.data.LoanDisbursementScheduleData;
import com.vsoftcorp.kls.data.LoanLineOfCreditData;
import com.vsoftcorp.kls.data.LoanRecoveryData;
import com.vsoftcorp.kls.data.LoanRepaymentScheduleDataList;
import com.vsoftcorp.kls.data.PacsLoanDisbursementData;
import com.vsoftcorp.kls.data.ProductChargesMappingData;
import com.vsoftcorp.kls.data.StLoanRecoveryData;
import com.vsoftcorp.kls.data.TransactionModeEnumsData;
import com.vsoftcorp.kls.data.gateway.datahelpers.KLSResponse;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.data.gateway.datahelpers.RegistrationIdData;
import com.vsoftcorp.kls.dataaccess.IDocumentDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanDisbursementDAO;
import com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLOC;
import com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLineOfCredit;
import com.vsoftcorp.kls.service.account.IAccountPropertyService;
import com.vsoftcorp.kls.service.account.IChargesDebitService;
import com.vsoftcorp.kls.service.account.IChargesMasterService;
import com.vsoftcorp.kls.service.account.ILoanAccountPropertyService;
import com.vsoftcorp.kls.service.account.ILoanLineOfCreditService;
import com.vsoftcorp.kls.service.account.IProductChargesMappingService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementEntryService;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementScheduleService;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementService;
import com.vsoftcorp.kls.service.loan.ILoanRecoveryService;
import com.vsoftcorp.kls.service.loan.ILoanRepaymentScheduleService;
import com.vsoftcorp.kls.service.loan.ITempLoanRepaymentScheduleService;
import com.vsoftcorp.kls.service.util.FileUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.charges.ChargeType;
/**
 * @author a9152
 * 
 */
@Path("/loan/account")
public class LoanAccountRestService {

	private static final Logger logger = Logger.getLogger(LoanAccountRestService.class);

	public LoanAccountRestService() {
	}

	private static final LoanAccountRestService INSTANCE = new LoanAccountRestService();

	public static LoanAccountRestService getInstance() {
		return INSTANCE;
	}

	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/text")
	public String createAccount(String accountDetails) {

		logger.info("Start : Calling Pacs Loan Account Service in createAccount() method.");
		LoanAccountPropertyData data = null;
		String returnMessage = "";
		try {
			ILoanAccountPropertyService service = KLSServiceFactory.getLoanAccountPropertyService();
			Gson gson = new Gson();
			data = gson.fromJson(accountDetails, LoanAccountPropertyData.class);
			returnMessage = service.saveLoanAccountProperty(data);
		} catch (Exception e) {
			returnMessage = e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Account Service in createAccount() method.");
		return returnMessage;
	}

	@GET
	@Path("/check")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean checkIfAccountExists(@QueryParam("cust_id") String custId) {

		logger.info("Start : Checking if account exists for customer id in checkIfAccountExists() method.");
		boolean flag = false;
		logger.info(" custId : " + custId);
		try {
			ILoanAccountPropertyService accountPropertyService = KLSServiceFactory.getLoanAccountPropertyService();
			// flag = accountPropertyService.checkIfAccountExists(custId);
		} catch (Exception e) {
			flag = false;
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info(" flag : " + flag);
		logger.info("End : Checking if account exists for customer id in checkIfAccountExists() method.");
		return flag;
	}

	@GET
	@Path("/getloc")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean getLineOfCredit(@QueryParam("cust_id") String custId) {

		logger.info("Start : Checking if account exists for customer id in checkIfAccountExists() method.");
		boolean flag = false;
		logger.info(" custId : " + custId);
		try {
			IAccountPropertyService accountPropertyService = KLSServiceFactory.getAccountPropertyService();
			flag = accountPropertyService.checkIfAccountExists(custId);
		} catch (Exception e) {
			flag = false;
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info(" flag : " + flag);
		logger.info("End : Checking if account exists for customer id in checkIfAccountExists() method.");
		return flag;
	}

	@GET
	@Path("/checkloanstatus")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean checkLoanStatus(@QueryParam("cust_id") Long custId) {

		logger.info("Start : Checking if loan exists for customer id in checkLoanStatus() method.");
		boolean flag = false;
		logger.info(" custId : " + custId);
		try {
			ILineOfCreditDAO lineOfCreditDAO = KLSDataAccessFactory.getLineOfCreditDAO();
			flag = lineOfCreditDAO.checkLoanStatus(custId);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info(" flag : " + flag);
		logger.info("End : Checking if account exists for customer id in checkIfAccountExists() method.");
		return flag;
	}

	// Get LineOfCredit Id List By Customer Id
	@GET
	@Path("/getlocbycustid")
	@Produces("application/json")
	public String getLOCListByCustomerId(@QueryParam("customerId") Long customerId) {

		logger.info("Start : Calling LoanAccountRestService in getLOCListByCustomerId() method.");
		List<LoanLineOfCreditData> list = new ArrayList<LoanLineOfCreditData>();
		String jsonString = "";
		try {
			ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
			list = service.getLineOfCreditList(customerId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getLOCListByCustomerId() method");
		return jsonString;
	}

	@GET
	@Path("/getdisbursedlocbycustid")
	@Produces("application/json")
	public String getDisbursedLOCListByCustomerId(@QueryParam("customerId") Long customerId) {

		logger.info("Start : Calling LoanAccountRestService in getLOCListByCustomerId() method.");
		List<LoanLineOfCreditData> list = new ArrayList<LoanLineOfCreditData>();
		String jsonString = "";
		try {
			ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
			list = service.getDisbursedLineOfCreditList(customerId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getLOCListByCustomerId() method");
		return jsonString;
	}

	@GET
	@Path("/getdisbursedlocIdsbycustid")
	@Produces("application/json")
	public String getDisbursedLOCIdsListByCustomerId(@QueryParam("customerId") Long customerId) {

		logger.info("Start : Calling LoanAccountRestService in getLOCListByCustomerId() method.");
		List<Map> list = new ArrayList<>();
		String jsonString = "";
		try {
			ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
			list = service.getDisbursedLineOfCreditIdsList(customerId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getLOCListByCustomerId() method");
		return jsonString;
	}

	@GET
	@Path("/getdisbursedlocIdsbycustidForClosure")
	@Produces("application/json")
	public String getDisbursedLOCIdByCustomerIdForClosure(@QueryParam("customerId") Long customerId) {

		logger.info("Start : Calling LoanAccountRestService in getLOCListByCustomerId() method.");
		List<LoanLineOfCreditData> list = new ArrayList<>();
		List<Map> locList = new ArrayList<>();
		Map<String, Long> map = null;
		String jsonString = "";
		try {
			ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
			list = service.getLineOfCreditList(customerId);

			for (LoanLineOfCreditData l : list) {
				Long locId = l.getId();
				ILoanDisbursementDAO dDao = KLSDataAccessFactory.getLoanDisbursementDAO();
				Money disbursedAmount = dDao.getDisbursedAmountByLocId(locId);
				logger.info("disbursedAmount---" + disbursedAmount);
				if (disbursedAmount != null) {
					map = new HashMap<String, Long>();
					map.put("locId", locId);
					locList.add(map);
				}
			}

			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(locList);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getLOCListByCustomerId() method");
		return jsonString;
	}

	// Get LineOfCredit Data List By LineOfCredit Id

	@GET
	@Path("/bylocid")
	@Produces("application/json")
	public String getLoanLineOfCreditById(@QueryParam("loanLocId") Long loanLocId) {

		logger.info("Start : Calling LoanAccountRestService in getLoanLineOfCreditById() method.");
		LoanLineOfCreditData loanLocData = null;
		String jsonString = "";
		logger.info(" loanLocId : " + loanLocId);
		try {
			ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
			loanLocData = service.getLineOfCreditDataById(loanLocId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(loanLocData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getLoanLineOfCreditById() method");
		return jsonString;
	}

	@GET
	@Path("/locforchargedebit")
	@Produces("application/json")
	public String getLoanLineOfCreditForChargeDebit(@QueryParam("loanLocId") Long loanLocId) {

		logger.info("Start : Calling LoanAccountRestService in getLoanLineOfCreditById() method.");
		LoanLineOfCreditData loanLocData = null;
		String jsonString = "";
		logger.info(" loanLocId : " + loanLocId);
		try {
			ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
			loanLocData = service.getLineOfCreditForChargeDebit(loanLocId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(loanLocData);
		} catch (Exception e) {
			return e.getMessage();
		}
		logger.info("End : Calling LoanAccountRestService in getLoanLineOfCreditById() method");
		return jsonString;
	}

	// Get LineOfCredit Data ,Application Data and Disbursed Data By LineOf
	// Credit Id
	@GET
	@Path("/disbursedlistbylocid")
	@Produces("application/json")
	public String getDisbursementSchedule(@QueryParam("loanLocId") Long loanLocId) {

		logger.info("Start : Calling LoanAccountRestService in getDisbursementSchedule() method.");
		List<DisbursementData> data = new ArrayList<DisbursementData>();
		String jsonString = "";
		logger.info(" loanLocId : " + loanLocId);
		try {
			ILoanDisbursementScheduleService scheduleService = KLSServiceFactory.getLoanDisbursementScheduleService();
			data = scheduleService.getDisbursementScheduleData(loanLocId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getDisbursementSchedule() method" + jsonString);
		return jsonString;
	}

	@POST
	@Path("/disbursementschedule")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveLoanDisbursementSchedule(String str) {
		logger.info("Start : Calling LoanDisbursementScheduleService in saveLoanDisbursementSchedule() method." + str);
		LoanDisbursementScheduleData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, LoanDisbursementScheduleData.class);
			ILoanDisbursementScheduleService service = KLSServiceFactory.getLoanDisbursementScheduleService();
			service.saveLoanDisbursementSchedule(data);
		} catch (Exception e) {
			logger.info("End : Calling LoanDisbursementSchedule service in update() method.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving");
		return "LoanDisbursementSchedule Saved Successfully";
	}

	@PUT
	@Path("/disbursementschedule")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateLoanDisbursementSchedule(String str) {

		logger.info("Start : Calling LoanDisbursementScheduleService in updateLoanDisbursementSchedule() method." + str);
		LoanDisbursementScheduleData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, LoanDisbursementScheduleData.class);
			ILoanDisbursementScheduleService service = KLSServiceFactory.getLoanDisbursementScheduleService();
			// service.saveLoanDisbursementSchedule(data);
		} catch (Exception e) {
			logger.error("Error while updating LoanDisbursementSchedule");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "LoanDisbursementSchedule  Updated Successfully!";

	}

	@GET
	@Path("/enums")
	@Produces("application/json")
	public String getAllLoanAccountEnums() {

		logger.info("Start : Fetching all loan account enums in getAllLoanAccountEnums() method.");
		String loanProductEnumsString = "";
		try {
			ILoanAccountPropertyService loanAcctPropertyService = KLSServiceFactory.getLoanAccountPropertyService();
			LoanAccountEnumsData loanAccountEnumsData = loanAcctPropertyService.getAllLoanAccountEnums();
			Gson gson = new GsonBuilder().create();
			loanProductEnumsString = gson.toJson(loanAccountEnumsData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Fetching all loan account enums in getAllLoanAccountEnums() method.");
		return loanProductEnumsString;
	}

	@POST
	@Path("/loandisbursement")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveLoanDisbursement(String str) {
		logger.info("Start : Calling LoanDisbursementService in saveLoanDisbursement() method." + str);
		PacsLoanDisbursementData data = null;
		Gson gson = new GsonBuilder().create();
		String voucherNumber = null;
		KLSResponse klsResponse=new KLSResponse();

		try {
			data = gson.fromJson(str, PacsLoanDisbursementData.class);
			ILoanDisbursementService disbursementService = KLSServiceFactory.getLoanDisbursementService();
			voucherNumber = disbursementService.saveLoanDisbursement(data);
			klsResponse.setVoucherNumber(voucherNumber); //MT Disbursement always use Transfer voucher.
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("End : Calling LoanDisbursement service in update() method.");
			logger.info("Error:" + e.getMessage());
			klsResponse.setErrorMessage("Error While Processing Request");
			klsResponse.setResponseStatus("1");//failure
			return  gson.toJson(klsResponse);
//			return "Loan Cannot disbursed!"; //Dont change this message. This message used in UI for validation
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving");
		klsResponse.setErrorMessage("LoanDisbursement Save Successful with voucher " + voucherNumber);
		klsResponse.setResponseStatus("0");//success
		return gson.toJson(klsResponse);
	}

	@GET
	@Path("/disbursementscheduleamount")
	@Produces("application/json")
	public String getDisbursementscheduleAmount(@QueryParam("lineOfCreditId") Long lineOfCreditId,
			@QueryParam("dateOfDisbursement") String dateOfDisbursement) {
		logger.info("Start : Calling LoanDisbursementService in getDisbursementscheduleAmount() method." + lineOfCreditId + "  " + dateOfDisbursement);
		PacsLoanDisbursementData data = new PacsLoanDisbursementData();
		String disbursementscheduleAmount = null;
		String jsonString = "";
		try {
			ILoanDisbursementService service = KLSServiceFactory.getLoanDisbursementService();
			disbursementscheduleAmount = service.getDisburshmentScheduleAmout(lineOfCreditId, dateOfDisbursement);
			data.setDisbursementscheduleAmount(String.format("%.2f", BigDecimal.valueOf(Double.parseDouble(disbursementscheduleAmount))));
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanDisbursementScheduleService in getLoanDisbursementScheduleList() method" + jsonString);
		return jsonString;
	}

	@POST
	@Path("/charges")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveChargesMaster(String charges) {

		logger.info("Start : Calling Charges Master Service in saveChargesMaster() method.==" + charges);
		ChargesMasterData data = null;
		try {
			IChargesMasterService service = KLSServiceFactory.getChargesMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(charges, ChargesMasterData.class);
			service.saveChargesMaster(data);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Charges Master Service in saveChargesMaster() method.");
		return "Charges Master data saved successfully";
	}

	@PUT
	@Path("/charges")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateChargesMaster(String str) {

		logger.info("Start : Calling Charges Master Service in updateChargesMaster() method.==" + str);
		ChargesMasterData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, ChargesMasterData.class);
			IChargesMasterService service = KLSServiceFactory.getChargesMasterService();
			service.updateChargesMaster(data);
		} catch (Exception e) {
			logger.error("Error while updating LoanDisbursementSchedule");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Charges Master Service in updateChargesMaster() method.");
		return "Charges Master data updated successfully";

	}

	@GET
	@Path("/charges")
	@Produces("application/json")
	public String getAllChargesMasters() {
		logger.info("Start: Calling getAllChargesMasters()");
		String chargesJson = "";
		List<ChargesMasterData> chargesMasterDataList = new ArrayList<ChargesMasterData>();
		try {
			Gson gson = new GsonBuilder().create();

			IChargesMasterService service = KLSServiceFactory.getChargesMasterService();

			chargesMasterDataList = service.getAllCharges();

			chargesJson = gson.toJson(chargesMasterDataList);

		} catch (Exception e) {
			logger.error("Error while retriving all charges");
			return "exception in get all charges";
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Calling getAllChargesMasters()");
		return chargesJson;
	}

	@GET
	@Path("/chargestypeenum")
	@Produces("application/json")
	public String getChargesTypeEnum() {

		logger.info("Start : Fetching all charges type enum in getChargesTypeEnum() method.");
		String loanChargesEnumString = "";
		try {
			IChargesMasterService service = KLSServiceFactory.getChargesMasterService();
			ChargesEnumData chargesEnumData = service.getAllChargesTypeEnums();
			Gson gson = new GsonBuilder().create();
			loanChargesEnumString = gson.toJson(chargesEnumData);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Fetching all charges type enum in getChargesTypeEnum() method.");
		return loanChargesEnumString;
	}

	@POST
	@Path("/productchargesmapping")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveProductChargesMapping(String str) {

		logger.info("Start : Calling Charges Master mapping Service in saveChargesMaster() method.==" + str);
		ProductChargesMappingData data = null;
		try {
			IProductChargesMappingService service = KLSServiceFactory.getProductChargesMappingService();
			Gson gson = new Gson();
			data = gson.fromJson(str, ProductChargesMappingData.class);
			service.saveProductChargesMapping(data);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Charges Master mapping Service in saveChargesMaster() method.");
		return "Product Charges Mapping saved successfully";
	}

	@GET
	@Path("/productchargesmapping")
	@Consumes("*/*")
	@Produces("application/json")
	public String getProductChargesMapping(@QueryParam("productId") Integer productId) {

		logger.info("Start : Calling Charges Master mapping Service in getProductChargesMapping() method." + productId);
		List<ProductChargesMappingData> dataLst = new ArrayList<ProductChargesMappingData>();
		String jsonAllProductChargesMappingString = "";
		try {
			IProductChargesMappingService service = KLSServiceFactory.getProductChargesMappingService();
			if (productId != null) {
				dataLst = service.getProductChargesMapping(productId);
				Gson gson = new GsonBuilder().create();
				jsonAllProductChargesMappingString = gson.toJson(dataLst);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Charges Master mapping Service service in getProductChargesMapping() method.");
		return jsonAllProductChargesMappingString;
	}

	@POST
	@Path("/chargesdebit")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveChargesDebit(String chargesDebit) {

		logger.info("Start : Calling Charges Debit Service in saveChargesDebit() method.==" + chargesDebit);
		ChargesDebitData data = null;
		try {
			IChargesDebitService service = KLSServiceFactory.getChargesDebitService();
			Gson gson = new Gson();
			data = gson.fromJson(chargesDebit, ChargesDebitData.class);
			service.saveChargesDebit(data);
		} catch (Exception e) {
			e.getMessage();
			return "Charges Master debit data cannot be saved";
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Charges Master Service in saveChargesDebit() method.");
		return "Charges Master debit data saved successfully";
	}

	@GET
	@Path("/chargesdebit")
	@Produces("application/json")
	public String getAllChargesDebit(@QueryParam("lineOfCreditId") Long lineOfCreditId) {
		logger.info("Start: Calling getAllChargesDebit()");
		String chargesDebitJson = "";
		ChargesDebitData chargesDebitData = null;
		try {
			Gson gson = new GsonBuilder().create();

			IChargesDebitService service = KLSServiceFactory.getChargesDebitService();

			chargesDebitData = service.getAllChargesDebit(lineOfCreditId);

			chargesDebitJson = gson.toJson(chargesDebitData);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all charges debit");
			return "exception in get all charges";
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Calling getAllChargesDebit()");
		return chargesDebitJson;
	}

	@GET
	@Path("/repaymentschedulelist")
	@Consumes("*/*")
	@Produces("application/json")
	public String getRepaymentScheduleList(@QueryParam("loanLocId") Long loanLocId, @QueryParam("editType") Integer editType) {

		logger.info("Start : Calling Loan Repayment Schedule Service in getRepaymentScheduleList() method.");
		LoanRepaymentScheduleDataList scheduleData = null;
		String jsonString = "";
		try {
			ILoanRepaymentScheduleService service = KLSServiceFactory.getLoanRepaymentScheduleService();
			if (loanLocId != null) {
				scheduleData = service.getRepaymentScheduleDataList(loanLocId, editType);
				Gson gson = new GsonBuilder().create();
				jsonString = gson.toJson(scheduleData);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Loan Repayment Schedule Service in getRepaymentScheduleList() method.");
		return jsonString;
	}

	@PUT
	@Path("/repaymentschedule")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateLoanRepaymentSchedule(String repaymentSchedule) {

		logger.info("Start : Updating loan repayment schedule list in updateLoanRepaymentSchedule() method.");
		LoanRepaymentScheduleDataList dataList = null;
		try {
			Gson gson = new GsonBuilder().create();
			dataList = gson.fromJson(repaymentSchedule, LoanRepaymentScheduleDataList.class);
			ILoanRepaymentScheduleService service = KLSServiceFactory.getLoanRepaymentScheduleService();
			service.updateRepaymentScheduleDataList(dataList);
		} catch (Exception e) {
			logger.error("Error while updating Loan Repayment Schedule List");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Updating loan repayment schedule list in updateLoanRepaymentSchedule() method.");
		return "LoanRepaymentSchedule  Updated Successfully!";
	}

	@GET
	@Path("/modifiedrepaymentschedulelist")
	@Consumes("*/*")
	@Produces("application/json")
	public String getModifiedRepaymentScheduleList(@QueryParam("loanLocId") Long loanLocId, @QueryParam("installmentNum") Integer installmentNum,
			@QueryParam("principalAmt") String principalAmt, @QueryParam("editType") Integer editType) {

		logger.info("Start : Calling Loan Repayment Schedule Service in getRepaymentScheduleList() method.");
		LoanRepaymentScheduleDataList scheduleData = null;
		String jsonString = "";
		try {
			ITempLoanRepaymentScheduleService service = KLSServiceFactory.getTempLoanRepaymentScheduleService();
			if (loanLocId != null && installmentNum != null && principalAmt != null) {
				scheduleData = service.getModifiedRepaymentScheduleDataList(loanLocId, installmentNum, principalAmt, editType);
				Gson gson = new GsonBuilder().create();
				jsonString = gson.toJson(scheduleData);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Loan Repayment Schedule Service in getRepaymentScheduleList() method.");
		return jsonString;
	}

	@GET
	@Path("/locforclosure")
	@Produces("application/json")
	public String getLoanLocByIdForClosure(@QueryParam("loanLocId") Long loanLocId) {

		logger.info("Start : Calling LoanAccountRestService in getLoanLineOfCreditById() method.");
		LoanLineOfCreditData loanLocData = null;
		String jsonString = "";
		logger.info(" loanLocId : " + loanLocId);
		try {
			ILoanLineOfCreditService service = KLSServiceFactory.getLoanLineOfCreditService();
			loanLocData = service.getLineOfCreditDataForClosure(loanLocId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(loanLocData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getLoanLineOfCreditById() method");
		return jsonString;
	}

	@GET
	@Path("/transactionmodeenums")
	@Produces("application/json")
	public String getTransactionModeEnums() {

		logger.info("Start : Fetching all transaction mode enums in getTransactionModeEnums() method.");
		String loanProductEnumsString = "";
		try {
			ILoanAccountPropertyService loanAcctPropertyService = KLSServiceFactory.getLoanAccountPropertyService();
			TransactionModeEnumsData tranModeEnumsData = loanAcctPropertyService.getTransactionModeEnums();
			Gson gson = new GsonBuilder().create();
			loanProductEnumsString = gson.toJson(tranModeEnumsData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Fetching all transaction mode enums in getTransactionModeEnums() method.");
		return loanProductEnumsString;
	}

	@POST
	@Path("/closure")
	@Consumes("application/json")
	@Produces("application/text")
	public String closeAccount(String loanLocClosureDetails) {

		logger.info("Start : Calling Pacs Loan Account Property Service in closeAccount() method.");
		LoanLineOfCreditData data = null;
		String returnMessage = "";
		try {
			ILoanAccountPropertyService service = KLSServiceFactory.getLoanAccountPropertyService();
			Gson gson = new Gson();
			data = gson.fromJson(loanLocClosureDetails, LoanLineOfCreditData.class);
			returnMessage = service.closeAccount(data);
		} catch (Exception e) {
			returnMessage = e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Account Property Service in closeAccount() method.");
		return returnMessage;
	}

	@GET
	@Path("/getrecoveryinfobylocid")
	@Produces("application/json")
	public String getRocoveryInfoBasedOnLocId(@QueryParam("loanLocId") Long loanLocId) {

		logger.info("Start : Calling LoanAccountRestService in getLoanLineOfCreditById() method.");
		LoanRecoveryData loanRecoveryData = null;
		String jsonString = "";
		logger.info(" loanLocId : " + loanLocId);
		try {
			ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
			loanRecoveryData = service.getLoanRecoveryDataByLocId(loanLocId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(loanRecoveryData);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getLoanLineOfCreditById() method");
		return jsonString;
	}

	@GET
	@Path("/getrecoveryinfobyamountpaid")
	@Produces("application/json")
	public String getRocoveryInfoBasedOnAmountPaid(@QueryParam("amountPaid") BigDecimal amountPaid,
			@QueryParam("recoverySequenceId") Integer recoverySequenceId, @QueryParam("loanLocId") Long loanLocId) {

		logger.info("Start : Calling LoanAccountRestService in getLoanLineOfCreditById() method.");
		LoanRecoveryData loanRecoveryData = null;
		String jsonString = "";
		logger.info(" amountPaid : " + amountPaid);
		logger.info("recoverySequenceId : " + recoverySequenceId);
		try {
			ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
			loanRecoveryData = service.getRocoveryInfoBasedOnAmountPaid(amountPaid, recoverySequenceId, loanLocId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(loanRecoveryData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getLoanLineOfCreditById() method");
		return jsonString;
	}

	@POST
	@Path("/saveloanrecovery")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveLoanRecovery(String loanRecoveryDetails) {

		logger.info("Start : Calling Pacs Loan Account Service in saveLoanRecovery() method.==" + loanRecoveryDetails);
		LoanRecoveryData data = null;
		String message = null;
		try {
			ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
			Gson gson = new Gson();
			data = gson.fromJson(loanRecoveryDetails, LoanRecoveryData.class);
			message = service.saveLoanRecovery(data);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Account Service in saveLoanRecovery() method.");
		return message;
	}
	
	@POST
	@Path("/savebulkloanrecovery")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveBulkLoanRecovery(String loanRecoveryDetails) {

		logger.info("Start : Calling Pacs Loan Account Service in saveBulkLoanRecovery() method.==" + loanRecoveryDetails);
		List<LoanRecoveryData> loanRecoveryDataList = new ArrayList<LoanRecoveryData>();
		
		LoanRecoveryData data = null;
		String message = null;
		try {
			ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
			Gson gson = new Gson();
			data = gson.fromJson(loanRecoveryDetails, LoanRecoveryData.class);
			message = service.saveLoanRecovery(data);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Account Service in saveBulkLoanRecovery() method.");
		return message;
	}


	@GET
	@Path("/getaccountbymodeofpayment")
	@Produces("application/json")
	public String getAccountByModeOfPayment(@QueryParam("modeOfPayment") String modeOfPayment, @QueryParam("loanLocId") Long loanLocId) {

		logger.info("Start : Calling Pacs Loan Account Service in getAccountByModeOfPayment() method.");
		List<Map> list = new ArrayList<>();
		String jsonString = "";
		try {
			ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
			list = service.getAccountByModeOfPayment(modeOfPayment, loanLocId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Account Service in getAccountByModeOfPayment() method.");
		return jsonString;
	}

	@GET
	@Path("/getaccountbypacsid")
	@Produces("application/json")
	public String getAccountByPacsId(@QueryParam("pacsId") Integer pacsId) {

		logger.info("Start : Calling Pacs Loan Account Service in getAccountByPacsId() method.");
		List<String> list = new ArrayList<>();
		String jsonString = "";
		try {
			IAccountPropertyService service = KLSServiceFactory.getAccountPropertyService();
			list = service.getAccountNumberListByPacsId(pacsId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);

		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Account Service in getAccountByPacsId() method.");
		return jsonString;
	}

	@POST
	@Path("/saveloandisbursemententry")
	@Consumes("application/json")
	@Produces("application/json")
	public String saveLoanDisbursementEntry(String jsonPassingData) {
		String message = null;
		LoanDisbursementEntryData passingData = null;
		try {

			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			Gson gson = new Gson();
			passingData = gson.fromJson(jsonPassingData, LoanDisbursementEntryData.class);
			message = service.saveLoanDisbursementEntry(passingData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	
	@POST
	@Path("/savebulkloandisbursemententry")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveBULKLoanDisbursementEntry(String jsonPassingData) {
		logger.info("Start : Calling bulk disbursement entry  Service in saveBULKLoanDisbursementEntry() method."+jsonPassingData);
		String message = null;
		LoanDisbursementEntryList passingData = null;
		try {

			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			Gson gson = new Gson();
			passingData = gson.fromJson(jsonPassingData, LoanDisbursementEntryList.class);
			message = service.saveBulkLoanDisbursementEntry(passingData);
			
			System.out.println("Message"+message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("End : Calling bulk disbursement entry Service in saveBULKLoanDisbursementEntry method.");
		return message;
	}

	@GET
	@Path("/loandisbursemententry")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLoanDisbursementEntry(@QueryParam("customerNumber") Long customerNumber, @QueryParam("status") Integer status,
			@QueryParam("loanType") String loanType) {

		logger.info("Start : Calling Loan Disbursement Entry Service in getLoanDisbursementEntry() method. customerNumber=" + customerNumber
				+ " ,status =" + status + " loanType= " + loanType);
		List<LoanDisbursementEntryData> dataLst = new ArrayList<>();
		String jsonAllDisbursementEntryString = "";
		try {
			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			if (customerNumber != null && status != null) {
				dataLst = service.getDisbursementEntriesList(customerNumber, status, loanType);
				Gson gson = new GsonBuilder().create();
				jsonAllDisbursementEntryString = gson.toJson(dataLst);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Loan Disbursement Entry Service service in getLoanDisbursementEntry() method.");
		return jsonAllDisbursementEntryString;
	}
	
	
	
	@PUT
	@Path("/updateloandisbursemententry")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateLoanDisbursementEntry(String str) {

		logger.info("Start : Calling LoanDisbursementScheduleService in updateLoanDisbursementEntry() method." + str);
		LoanDisbursementEntryData data = null;
		String message = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, LoanDisbursementEntryData.class);
			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			service.updateLoanDisbursementEntry(data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while updating LoanDisbursementEntry");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		if (data.getStatus() == 4){
			message = "Loan Disbursement Passed Successfully !";
			ILineOfCreditDAO locDao = KLSDataAccessFactory.getLineOfCreditDAO();
			LineOfCredit loc = locDao.getLineOfCreditById(data.getLocId(),false);
			Money wBal = loc.getSanctionedAmount().subtract(loc.getCurrentBalance().getMoney());
			try{
			//SmsData smsData= SmsDataService.getLocSmsData( loc, AccountingMoney.valueOf(Money.valueOf(data.getAmountDisbursed()),DebitOrCredit.DEBIT), "W", "D", "DI", wBal, "Amount Disbursed Successfully");
			//RestClientUtil.postSMSAlertData(smsData);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
			
		else if (data.getStatus() == 3)
			message = "Loan Disbursement Rejected !";
		else
			message = "Loan Disbursement Entry Updated Successfully !";
		return message;
	}

	@GET
	@Path("/loandisbursemententrybylocid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLoanDisbursementEntrybasedOnLocId(@QueryParam("customerNumber") Long customerNumber, @QueryParam("status") Integer status,
			@QueryParam("locId") long locId, @QueryParam("loanType") String loanType) {

		logger.info("Start : Calling Loan Disbursement Entry Service in getLoanDisbursementEntry() method. customerNumber=" + customerNumber
				+ " status =" + status + " locId= " + locId + " loanType= " + loanType);
		List<LoanDisbursementEntryData> dataLst = new ArrayList<>();
		String jsonAllDisbursementEntryString = "";
		try {
			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			dataLst = service.getDisbursementEntriesListbasedOnLocId( customerNumber, status, locId, loanType);
			Gson gson = new GsonBuilder().create();
			jsonAllDisbursementEntryString = gson.toJson(dataLst);

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		logger.info("End : Calling Loan Disbursement Entry Service service in getLoanDisbursementEntry() method.");
		return jsonAllDisbursementEntryString;
	}

	@GET
	@Path("/getdisbursedlocIdsforpassing")
	@Produces("application/json")
	public String getDisbursedLOCIdsForPassing(@QueryParam("customerNumber") Long customerNumber, @QueryParam("loanType") String loanType) {

		logger.info("Start : Calling LoanAccountRestService in getDisbursedLOCIdsForPassing() method.");
		List<Map> list = new ArrayList<>();
		String jsonString = "";
		try {
			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			list = service.getDisbursedLOCIdsForPassing(customerNumber, loanType);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} /*
			* finally { EntityManagerUtil.closeSession(); }
			*/
		logger.info("End : Calling LoanAccountRestService in getDisbursedLOCIdsForPassing() method");
		return jsonString;
	}

	@GET
	@Path("/getloaninfo")
	@Produces("application/json")
	public String getLoanInfo(@QueryParam("accountId") Long accountId, @QueryParam("loanType") String loanType,
			@QueryParam("transType") String transType) {

		logger.info("Start : Calling LoanAccountRestService in getDisbursedLOCIdsForPassing() method.");
		Map<String, Object> infoMap = new HashMap<String, Object>();
		String jsonString = "";
		try {
			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			infoMap = service.getLoanInfo(accountId, loanType, transType);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(infoMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getDisbursedLOCIdsForPassing() method");
		return jsonString;
	}
	
	//To retrieve ST and MT/LT accounts summary for omni	
	@GET
	@Path("customerAccountsSummary/{memberId}")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAccountsSummary(@PathParam("memberId") String memberId) {

		logger.info("Start : Calling LoanAccountRestService in getDisbursedLOCIdsForPassing() method.");
		Map<String, Object> infoMap = new HashMap<String, Object>();
		String jsonString = "";
		STAccountsSummary stSummary=new STAccountsSummary();
		MemberAccountsSummaryData memberAccountSummaryData=new MemberAccountsSummaryData();
		AccountData accData= new AccountData();
		String loanType="C";
		String transType="C";
		List<Object> accountSummaryList = new ArrayList<Object>();
		try {
			
			IAccountPropertyService accPropService = KLSServiceFactory.getAccountPropertyService();
			PersonData personData=RestClientUtil.getCustomerByMemberNumber(memberId);
			if(personData  != null) {
			accData = accPropService.getAccountInfoByCustId(personData.getCustomerId());
				if (accData != null) {
					String accNo = accData.getAccountNo();
					ILoanDisbursementEntryService service = KLSServiceFactory
							.getLoanDisbursementEntryService();
					infoMap = service.getLoanInfo(accData.getAccountId(),
							loanType, transType);
					for (Map.Entry<String, Object> entry : infoMap.entrySet()) {
						stSummary.setCurrentBalance(infoMap.get(
								"currentBalance").toString());
						stSummary.setSanctionLimit(infoMap.get(
								"sanctionedAmount").toString());
						stSummary.setInterestReceivableAmount(infoMap.get(
								"interestReceivable").toString());
						stSummary.setPenalInterestReceivableAmount(infoMap.get(
								"penalInterestReceivable").toString());
						stSummary.setKindLimit(infoMap.get("kindLimit")
								.toString());
						stSummary.setAccountNumber(accNo);
						stSummary.setAvailableCashLimit(infoMap.get(
								"drawableAmount").toString());
						stSummary.setAvailableKindLimit(infoMap.get(
								"kindDrawableAmount").toString());
						stSummary.setAccountType("ST");
						if (infoMap.get("seasonDueDates") != null) {
							List dueDate = (List) infoMap.get("seasonDueDates");
							if(dueDate.size()>0){
							int minIndex = dueDate.indexOf(Collections
									.min(dueDate));
							stSummary.setDueDate(dueDate.get(minIndex)
									.toString());
							}
							else
								stSummary.setDueDate("");
						}
						Double totReceivableAmt = Double.parseDouble(infoMap
								.get("currentBalance").toString())
								+ Double.parseDouble(infoMap.get(
										"interestReceivable").toString())
								+ Double.parseDouble(infoMap.get(
										"penalInterestReceivable").toString());
						BigDecimal totReceivable = new BigDecimal(
								totReceivableAmt);
						stSummary.setDueAmount(totReceivable.setScale(2)
								.toString());
					}
					ILoanLineOfCreditService loanservice = KLSServiceFactory
							.getLoanLineOfCreditService();
					List<Map> list = loanservice
							.getDisbursedLineOfCreditIdsList(personData
									.getCustomerId());
					for (Map l : list) {
						ILoanLineOfCreditService loancreditservice = KLSServiceFactory
								.getLoanLineOfCreditService();
						LoanLineOfCreditData loanLocData = loancreditservice
								.getLineOfCreditDataById(Long.parseLong(l.get(
										"locId").toString()));
						String businessDate = KLSServiceFactory.getBankParameterService().getAllBankParameters().get(0).getBusinessDate();
						LoanRepaymentScheduleData data = KLSServiceFactory.getLoanRepaymentScheduleService().getRepaymentScheduleData(Long.parseLong(l.get("locId").toString()), com.vsoftcorp.kls.business.util.DateUtil.getVSoftDateByString(businessDate));
						MTAccountSummary mtSummary = new MTAccountSummary();
						if(data != null) {
							mtSummary.setInstallmentAmount(data.getContributionAmount());
							mtSummary.setNextInstallmentDate(data.getInstallmentDate());
						} else {
							mtSummary.setInstallmentAmount("");
							mtSummary.setNextInstallmentDate("");
						}
						mtSummary.setLocNumber(loanLocData.getId().toString());
						mtSummary.setOutstandingBalance(loanLocData
								.getOutstandingBalance().toString());
						mtSummary.setOverdueAmount(loanLocData
								.getOverdueAmount().toString());
						mtSummary.setPenalROI(loanLocData.getPenalInterest());
						mtSummary.setRateOfInterest(loanLocData
								.getRateOfInterest());
						mtSummary.setSanctionAmount(loanLocData
								.getSanctionAmount());
						mtSummary
								.setSanctionDate(loanLocData.getSanctionDate());
						mtSummary.setAccountNumber(loanLocData
								.getAccountNumber());
						mtSummary.setAccountType(loanLocData.getProductType());

						PacsLoanApplicationData loanApplicationData = loanApplicationData = KLSServiceFactory
								.getPacsLoanApplicationService()
								.getLoanApplicationById(
										loanLocData.getLoanApplicationId());
						mtSummary.setPurpose(loanApplicationData
								.getPurposeName());
						logger.info("subpurpose******************"
								+ loanApplicationData.getSubPurposeName());
						mtSummary.setSubpurpose(loanApplicationData
								.getSubPurposeName());
						mtSummary.setLoanPeriod(loanApplicationData
								.getLoanPeriod().toString());
						// loanData.add(mtSummary);
						accountSummaryList.add(mtSummary);
					}
					accountSummaryList.add(stSummary);
					memberAccountSummaryData.setAccountSummary(accountSummaryList);
				}else{
					
					logger.info("ST,MT/LT loans are not there for this customer");
					memberAccountSummaryData.setAccountSummary(accountSummaryList);
				}
			memberAccountSummaryData.setCustomerId(personData.getCustomerId().toString());
			memberAccountSummaryData.setMemberNumber(personData.getMemberNumber());
			memberAccountSummaryData.setCustomerName(personData.getDisplayName());
			memberAccountSummaryData.setGenderName(personData.getGenderName());
			memberAccountSummaryData.setCustomerType(personData.getCustomerTypeName());
			memberAccountSummaryData.setPacsId(personData.getPacsId().toString());
			if(personData.getPacsId() != null) {
				memberAccountSummaryData.setPacsName(KLSServiceFactory.getPacsService().getPacsByPacId(personData.getPacsId()).getName());
			}
			
			if(personData.getContactDetailList().get(0).getVillageId() != null) {
				memberAccountSummaryData.setVillageName(KLSServiceFactory.getVillageMasterService().getVillageDetailsById(personData.getContactDetailList().get(0).getVillageId()).getVillageName());
				memberAccountSummaryData.setAddressType(personData.getContactDetailList().get(0).getAddressTypeName());
			}
			if(personData.getDigitalProofDetailList() != null && !personData.getDigitalProofDetailList().isEmpty()){
				RegistrationIdData proof=personData.getDigitalProofDetailList().get(0);
				IDocumentDAO dao = KLSDataAccessFactory.getDocumentDAO();
				Document document = dao.getDocumentById(proof.getTypeId().intValue());
				if(document != null && "AADHAAR CARD".equalsIgnoreCase(document.getName())){
					memberAccountSummaryData.setAadhaarNo(proof.getDocumentNumber());
				}else{
					memberAccountSummaryData.setAadhaarNo("");
				}
			}else{
				memberAccountSummaryData.setAadhaarNo("");
			}
			AddressData addressData = new AddressData();
			if(personData.getContactDetailList() != null) {
				addressData.setAddressType(personData.getContactDetailList().get(0).getAddressTypeName());
				addressData.setZip(personData.getContactDetailList().get(0).getZipCode()==null?"":personData.getContactDetailList().get(0).getZipCode());
				addressData.setAddressLine1(personData.getContactDetailList().get(0).getLine1()==null?"":personData.getContactDetailList().get(0).getLine1());
				addressData.setAddressLine2(personData.getContactDetailList().get(0).getLine2()==null?"":personData.getContactDetailList().get(0).getLine2());
				if(personData.getContactDetailList().get(0).getVillageId() != null) {
					addressData.setCity(KLSServiceFactory.getVillageMasterService().getVillageDetailsById(personData.getContactDetailList().get(0).getVillageId()).getVillageName());
				}
				memberAccountSummaryData.setPhoneNumber(personData.getContactDetailList().get(0).getMobileNo()==null?"":personData.getContactDetailList().get(0).getMobileNo());
				memberAccountSummaryData.setEmailAddress("");
			}
			//accountSummaryList.add(stSummary);
			memberAccountSummaryData.setAddressData(addressData);
			//memberAccountSummaryData.setAccountSummary(accountSummaryList);
			List<MTAccountSummary> ltLoanData=new ArrayList<MTAccountSummary>();
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(memberAccountSummaryData);
			} else {
				jsonString = "Member does not exist: "+memberId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanAccountRestService in getDisbursedLOCIdsForPassing() method");
		return jsonString;
	}

	
	@POST
	@Path("/mtltbulkrecovery")
	@Consumes("application/json")
	@Produces("application/json")
	public String doMtLtBulkRecovery(String bulkRecoveryData) {
		
		logger.info("Start: Reading data from Excel File in doBulkRecovery() method");
		List<CustXLData> rejectedList = new ArrayList<CustXLData>();
		List<CustXLData> rejected = new ArrayList<CustXLData>();
		ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
		Gson gson = new GsonBuilder().create();
		String jsonString="";
		try {
			BulkRecoveryData bulkData = gson.fromJson(bulkRecoveryData,BulkRecoveryData.class);
			File custdata = FileUtil.getEncodedFile(bulkData.getCustFile());
			
			Workbook w = Workbook.getWorkbook(custdata);

			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			List<CustXLData> custXLDataList = new ArrayList<CustXLData>();
			CustXLData excelData = null;
			if(sheet.getRows()!=0 && sheet.getCell(0,0).getContents()!=null){
				for (int i = 0; i < sheet.getRows(); i++) {
					try {
						excelData = new CustXLData();
						for (int j = 0; j < sheet.getColumns(); j++) {
							Cell cell = sheet.getCell(j, i);
							if (cell.getContents() != null && cell.getContents().trim() != "" && !cell.getContents().trim().equalsIgnoreCase("null")) {
								if (j == 0) {
									excelData.setMemberNumber(cell.getContents());
								} else if (j == 1) {
									excelData.setMemberName(cell.getContents());
								} else if (j == 2) {
									if(cell.getContents() != null) {
										excelData.setLocId(Long.parseLong(cell.getContents()));
									}
								} else if (j == 3) {
									excelData.setRecoverdAmount(new BigDecimal(cell.getContents()));
								}
							}
						}
						custXLDataList.add(excelData);
					}catch(NumberFormatException nfe){
						excelData.setRemarks("Invalid amount format");
						rejected.add(excelData);	
					}
				}
				if(bulkData.getPacsId() != null) {
					rejectedList =   service.validateAndProcessMtLtRecovery(custXLDataList, Long.parseLong(bulkData.getPacsId().toString()));
					if(rejected != null && rejected.size() > 0){
						rejectedList.addAll(rejected);
					}
				} else {
					BulkSTRecoveryResponse response = new BulkSTRecoveryResponse();
					response.setRejectedList(rejectedList);
					response.setStatus(false);
					response.setResponse("Pacs Id does not exist");
					jsonString = gson.toJson(response);
				}
				if(rejectedList != null && rejectedList.size()>0){
					BulkSTRecoveryResponse response = new BulkSTRecoveryResponse();
					response.setRejectedList(rejectedList);
					response.setStatus(false);
					response.setResponse("Transaction Failed.");
					jsonString = gson.toJson(response);
				}else{
					BulkSTRecoveryResponse response = new BulkSTRecoveryResponse();
					response.setRejectedList(rejectedList);
					response.setStatus(true);
					response.setResponse("Transaction Successful.");
					jsonString = gson.toJson(response);
				}
			} else {
				BulkSTRecoveryResponse response = new BulkSTRecoveryResponse();
				response.setStatus(true);
				response.setResponse("Can not proceed. Empty Excel File Uploaded.");
				jsonString = gson.toJson(response);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(BiffException b){
			BulkSTRecoveryResponse response = new BulkSTRecoveryResponse();
			response.setStatus(true);
			response.setResponse("Invalid File Format");
			jsonString = gson.toJson(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		logger.info("End: Reading data from Excel File in doBulkRecovery() method");
		return jsonString;

	}

/*@POST
@Path("/savemultipleloandisbursemententry")
@Consumes("application/json")
@Produces("application/json")
public String saveMultipleLoanDisbursementEntry(String jsonPassingData) {
	String message = null;
	LoanDisbursementEntryDataList passingDataList = null;
	MTMultipleDisburseData disbursepassingData=new MTMultipleDisburseData();
	//MTMultiplePassingDataList mtpassingList=new MTMultiplePassingDataList();
	//List<MTMultipleDisburseData> listData=new ArrayList<MTMultipleDisburseData>();
	try {
			ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
			ILoanDisbursementService disburseservice=KLSServiceFactory.getLoanDisbursementService();
			Gson gson = new Gson();
			passingDataList = gson.fromJson(jsonPassingData, LoanDisbursementEntryDataList.class);
			for(LoanDisbursementEntryData passingData:passingDataList.getDisbursementEntryData()){
			message = service.saveLoanDisbursementEntry(passingData);
			disbursepassingData=LoanDisbursementHelper.getMTPassingData(passingData);
		//mtpassingList.setPassingDataList(listData);
			disburseservice.updateMultipleDisbursement(disbursepassingData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}*/
@GET
@Path("/retrieveloandisbursemententrylist")
@Produces("application/json")
public String getloanDisburseEntryList(@QueryParam("pacsId") String pacsId,@QueryParam("loanType") String loanType){
	logger.info("Start : Calling LoanAccountRestService in getloanDisburseEntryList() method.");
	String jsonString="";
	ILoanDisbursementService service=KLSServiceFactory.getLoanDisbursementService();
	try{
	LoanDisbursementEntryDataList passingDataList=new LoanDisbursementEntryDataList();
	passingDataList=service.getloanDisburseEntryList(pacsId,loanType);
	Gson gson = new GsonBuilder().create();
	jsonString = gson.toJson(passingDataList);
	logger.info("json String::"+jsonString);
	}catch(Exception e){
		e.printStackTrace();
	}
	return jsonString;
	
	
}
@POST
@Path("/updatemultipledisbursement")
@Consumes("application/json")
@Produces("application/json")
public String updateMultipleDisbursement(String passDataString){
	logger.info("Start : Calling LoanAccountRestService in updateMultipleDisbursement() method."+passDataString);
	String jsonString="";
	MTMultiplePassingDataList mtpassingList=new MTMultiplePassingDataList();
	Gson gson = new Gson();
	mtpassingList = gson.fromJson(passDataString, MTMultiplePassingDataList.class);
	ILoanDisbursementService service=KLSServiceFactory.getLoanDisbursementService();
	logger.info("checking rest service"+mtpassingList.getPassingDataList());
	for(MTMultipleDisburseData passingData:mtpassingList.getPassingDataList()){
		jsonString=service.updateMultipleDisbursement(passingData);
	}
	//Gson gson = new GsonBuilder().create();
	//jsonString = gson.toJson(passingList);
	logger.info("json String::"+jsonString);
	return jsonString;
}

@GET
@Path("/getnextinstallmentdate")
@Produces("application/json")
public String getNextInstallmentDate(@QueryParam("locId") long locId,@QueryParam("businessDate") String businessDate){
	logger.info("Start : Calling LoanAccountRestService in getloanDisburseEntryList() method.");
	String jsonString="";
	String nextInstallmentDate = KLSServiceFactory.getLoanRepaymentScheduleService().getNextInstallmentAmount(locId, com.vsoftcorp.kls.business.util.DateUtil.getVSoftDateByString(businessDate));
	Gson gson = new GsonBuilder().create();
	jsonString = gson.toJson(nextInstallmentDate);
	logger.info("json String::"+jsonString);
	return jsonString;
}
	
@GET
@Path("/bulkdisbursementdetails")
@Produces("application/json")
public String bulkDisbursementDetails(@QueryParam("loanType") String loanType,@QueryParam("disbursementDate") String disbursementDate,@QueryParam("instrumentNo") String instrumentNo,@QueryParam("pacsId") String pacsId){
	String jsonString="";
	List<Map> disbursementMap=new ArrayList<Map>();
	ILoanDisbursementService service=KLSServiceFactory.getLoanDisbursementService();
	try{
		disbursementMap=service.getBulkDisbursementDetails(loanType,disbursementDate,instrumentNo,pacsId);
		Gson gson = new GsonBuilder().create();
		jsonString=gson.toJson(disbursementMap);
		
	}catch(Exception ex){
		ex.printStackTrace();
	}
	
	return jsonString;
}

@POST
@Path("/bulkdisbursementpassing")
@Consumes("application/json")
@Produces("application/text")
public String bulkDisbursementPassing(String jsonPassingData){
	logger.info("Start : Calling bulk disbursement Passing  Service in saveBULKLoanDisbursementEntry() method."+jsonPassingData);
	String message = null;
	LoanDisbursementEntryList passingData = null;
	try {

		ILoanDisbursementEntryService service = KLSServiceFactory.getLoanDisbursementEntryService();
		Gson gson = new Gson();
		passingData = gson.fromJson(jsonPassingData, LoanDisbursementEntryList.class);
		message = service.disbursementPassing(passingData);
		
		System.out.println("Message"+message);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	logger.info("End : Calling bulk disbursement entry Service in saveBULKLoanDisbursementEntry method.");
	return message;
}
@PUT
@Path("/removeBulkDisbursementEntry")
@Consumes("*/*")
@Produces("application/text")
public String removeBulkDisbursementEntry(@QueryParam("id") String id){
	String returnmsg="";
	try{
		ILoanDisbursementEntryService service=KLSServiceFactory.getLoanDisbursementEntryService();
		returnmsg=service.removeDisbursementEntry(id);
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return returnmsg;
}
@POST
@Path("/stLoanRecoveryEntry")
@Consumes("*/*")
@Produces("application/text")
public String saveStLoanRecoveryEntry(String jsonString){
	String strMsg="";
	StLoanRecoveryData data=new StLoanRecoveryData();
	try{
		ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
		Gson gson = new Gson();
		data = gson.fromJson(jsonString, StLoanRecoveryData.class);
		strMsg = service.saveStLoanRecoveryEntry(data);
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return strMsg;
}
@GET
@Path("/stLoanRecoveryEntry")
@Produces("application/text")
public String getStLoanRecoveryEntry(@QueryParam("pacsId") String pacsId){
	String jsonString="";
	List<StLoanRecoveryData> recoveryDataList=new ArrayList<StLoanRecoveryData>();
	try{
		ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
		recoveryDataList=service.getStLoanRecoveryEntry(pacsId);
		Gson gson = new Gson();
		jsonString=gson.toJson(recoveryDataList);
	}catch(Exception e){
		e.printStackTrace();
		}
	return jsonString;
}
@PUT
@Path("/stLoanRecoveryEntry")
@Consumes("application/json")
@Produces("*/*")
public String updateStLoanRecoveryEntry(String jsonStr){
	String returnMsg="";
	StLoanRecoveryData stRecoveryData=new StLoanRecoveryData();
	try{
	 ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
	 Gson gson=new Gson();
	 stRecoveryData=gson.fromJson(jsonStr, StLoanRecoveryData.class);
	 returnMsg=service.updateStLoanRecoveryEntry(stRecoveryData);
		
	}catch(Exception e){
		e.printStackTrace();
		}
	return returnMsg;
}
@POST
@Path("/MtLoanRecoveryEntry")
@Consumes("application/json")
@Produces("*/*")
public String saveMtLoanRecoveryEntry(String jsonStr)
{
	String returnMsg="";
	LoanRecoveryData recoveryData=new LoanRecoveryData();
	ILoanRecoveryService service=KLSServiceFactory.getLoanRecoveryService();
	try{
		Gson gson=new Gson();
		recoveryData=gson.fromJson(jsonStr,LoanRecoveryData.class);
		returnMsg=service.saveMtLoanRecoveryEntry(recoveryData);
	}catch(Exception e){
		e.printStackTrace();
	}
	logger.info("End : saveMtLoanRecoveryEntry method");
	return returnMsg;
}
@GET
@Path("/MtLoanRecoveryEntry")
@Produces("application/text")
public String getMtLoanRecoveryEntry(@QueryParam("pacsId") String pacsId){
	logger.info("Start : getMtLoanRecoveryEntry");
	String jsonStr="";
	ILoanRecoveryService service=KLSServiceFactory.getLoanRecoveryService();
	List<LoanRecoveryData> recoveryDataList=new ArrayList<LoanRecoveryData>();
	Integer pacId=Integer.parseInt(pacsId);
	try{
		recoveryDataList=service.getMtLoanRecovery(pacId);
		Gson gson=new Gson();
		jsonStr=gson.toJson(recoveryDataList);
	}catch(Exception e){
		e.printStackTrace();
	}
	return jsonStr;
}
@PUT
@Path("/MtLoanRecoveryEntry")
@Consumes("application/json")
@Produces("*/*")
public String updateMtLoanRecoveryEntryStatus(String jsonStr){
	logger.info("Start : updateMtLoanRecoveryEntryStatus method");
	String successMsg="";
	ILoanRecoveryService service=KLSServiceFactory.getLoanRecoveryService();
	LoanRecoveryData recoveryData=new LoanRecoveryData();
	try{
		Gson gson=new Gson();
		recoveryData=gson.fromJson(jsonStr, LoanRecoveryData.class);
		successMsg=service.updateMtRecoveryStatus(recoveryData);		
	}catch(Exception e){
		e.printStackTrace();
	}
	logger.info("End : updateMtLoanRecoveryEntryStatus method");
	return successMsg;
}

@GET
@Path("/getShareAmount")
@Consumes("*/*")
@Produces("application/json")
public String getAllActiveLoansShareAmount(@QueryParam("cust_id") Long custId) {
	logger.info("Start : getting share balance if loan exists for customer id in getAllActiveLoansShareAmount() method.");
	logger.info(" custId : " + custId);
    String returnMessage = "";
    try {
      ILineOfCreditDAO lineOfCreditDAO = KLSDataAccessFactory.getLineOfCreditDAO();
      IChargesDAO chargesDAO = KLSDataAccessFactory.getChargesDAO();
      List<ChargesForLineOfCredit> loanChargesList = new ArrayList<ChargesForLineOfCredit>();
      List<ChargesForLOC> chargesList = new ArrayList<ChargesForLOC>();
      List<LineOfCredit> loc = lineOfCreditDAO.getActiveLocListByCustomerId(custId);
      for (LineOfCredit lineOfCredit : loc) {
    	  Long locId = lineOfCredit.getId();
          loanChargesList = chargesDAO.getCharges(locId, ChargeType.SHARE);
          for (ChargesForLineOfCredit chargesforloc : loanChargesList) {
        	  ChargesForLOC charges = new ChargesForLOC();
        	  charges.setChargeAmount(chargesforloc.getChargeAmount().getAmount());
        	  charges.setLineOfCreditId(chargesforloc.getLineOfCreditId());
        	  chargesList.add(charges);
		}
	}
      
      Gson gson = new GsonBuilder().create();
      returnMessage = gson.toJson(chargesList);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      EntityManagerUtil.closeSession();
    }
    logger.info("End : getting share balance if loan exists for customer id in getAllActiveLoansShareAmount() method.");
    return returnMessage;
}
}

