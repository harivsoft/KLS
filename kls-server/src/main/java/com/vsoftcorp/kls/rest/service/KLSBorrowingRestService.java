package com.vsoftcorp.kls.rest.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.kls.data.BorrowingProductData;
import com.vsoftcorp.kls.data.BorrowingRecoveryEntryData;
import com.vsoftcorp.kls.data.BorrowingsAccountPropertyData;
import com.vsoftcorp.kls.data.BorrowingsLineOfCreditData;
import com.vsoftcorp.kls.data.LoanRecoveryData;
import com.vsoftcorp.kls.service.IBorrowingProductService;
import com.vsoftcorp.kls.service.account.IBorrowingAccountPropertyService;
import com.vsoftcorp.kls.service.account.IBorrowingRecoveryEntryService;
import com.vsoftcorp.kls.service.account.IBorrowingsLineOfCreditService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author a1691
 * 
 */
@Path("/borrowing")
public class KLSBorrowingRestService {
	private static final Logger logger = Logger.getLogger(KLSBorrowingRestService.class);

	public KLSBorrowingRestService() {

	}

	private static final KLSBorrowingRestService INSTANCE = new KLSBorrowingRestService();

	public static KLSBorrowingRestService getInstance() {
		return INSTANCE;
	}

	@POST
	@Path("/account/create")
	@Consumes("application/json")
	@Produces("application/text")
	public String createAccount(String accountDetails) {

		logger.info("Start : Calling Pacs Loan Account Service in createAccount() method.--" + accountDetails);
		BorrowingsAccountPropertyData data = null;
		String returnMessage = "";
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(accountDetails, BorrowingsAccountPropertyData.class);
			IBorrowingAccountPropertyService service = KLSServiceFactory.getBorrowingAccountPropertyService();

			returnMessage = service.saveBorrowingAccountProperty(data);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage = e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Pacs Loan Account Service in createAccount() method.");
		return returnMessage;
	}

	@GET
	@Path("/account/check")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean checkIfAccountExists(@QueryParam("productId") Integer productId) {

		logger.info("Start : Checking if account exists for customer id in checkIfAccountExists() method.");
		boolean flag = false;
		logger.info(" productId : " + productId);
		try {
			IBorrowingAccountPropertyService service = KLSServiceFactory.getBorrowingAccountPropertyService();
			int pacId = 1;
			flag = service.checkIfAccountExists(pacId, productId);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info(" flag : " + flag);
		logger.info("End : Checking if account exists for customer id in checkIfAccountExists() method.");
		return flag;
	}

	@GET
	@Path("/getallaccounts")
	@Produces("application/json")
	public String getAllAccounts() {

		logger.info("Start : Calling BorrowingsAccountProperty master service in getAllAccounts() method.");
		List<BorrowingsAccountPropertyData> accountsDataList = new ArrayList<BorrowingsAccountPropertyData>();
		String jsonAllProductsString = "";
		try {
			IBorrowingAccountPropertyService service = KLSServiceFactory.getBorrowingAccountPropertyService();
			accountsDataList = service.getAllAccounts();
			Gson gson = new GsonBuilder().create();
			jsonAllProductsString = gson.toJson(accountsDataList);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling BorrowingsAccountProperty master service in getAllAccounts() method.");
		return jsonAllProductsString;
	}

	@POST
	@Path("/product")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveBorrowingProduct(String str) {
		logger.info("Start : Calling BrrowingProductService in saveBorrowingProduct() method." + str);
		BorrowingProductData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, BorrowingProductData.class);
			IBorrowingProductService service = KLSServiceFactory.getBorrowingProductService();
			service.saveBorrowingProduct(data);
		} catch (RollbackException re) {

			re.getMessage();
			logger.error("Borrowing Product Already Exits With Same Name Or Code .");

			return "Borrowing Product Code Or Name Already Exits ";
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			logger.error("Error While Saving");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}

		logger.info("End : Calling BrrowingProductService  in saveBorrowingProduct() method.");

		return "Borrowing Product Saved Successfully";
	}

	@GET
	@Path("/product")
	@Produces("application/json")
	public String getAllBorrowingProducts() {

		logger.info("Start : Calling BorrowingsAccountProperty master service in getAllAccounts() method.");
		List<BorrowingProductData> dataList = new ArrayList<BorrowingProductData>();
		String jsonAllProductsString = "";
		try {
			IBorrowingProductService service = KLSServiceFactory.getBorrowingProductService();
			dataList = service.getAllBorrowingProducts();
			Gson gson = new GsonBuilder().create();
			jsonAllProductsString = gson.toJson(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling BorrowingsProduct data in getAllBorrowingProducts() method." + dataList.get(0).getProductType());
		return jsonAllProductsString;
	}

	@DELETE
	@Path("/account/delete")
	@Consumes("*/*")
	@Produces("application/text")
	public String deleteAccount(@QueryParam("borrowingAccountId") Long borrowingAccountId) {

		logger.info("Start : Calling BorrowingsAccountProperty service in deleteAccount() method. : " + borrowingAccountId);
		String returnMessage = null;
		try {
			IBorrowingAccountPropertyService service = KLSServiceFactory.getBorrowingAccountPropertyService();
			returnMessage = service.deleteBorrowingAccountProperty(borrowingAccountId);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling BorrowingsAccountProperty service in deleteAccount() method.");
		return returnMessage;
	}

	@GET
	@Path("/account/byborrowingproductid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAccountPropertyByBorrowingProductId(@QueryParam("borrowingProductId") Integer borrowingProductId) {

		logger.info("Start : Calling BorrowingsAccountProperty service in getAccountPropertyByBorrowingProductId() method.==" + borrowingProductId);
		BorrowingsAccountPropertyData data = null;
		String jsonBorrowingAccPropertyString = "";
		try {
			IBorrowingAccountPropertyService service = KLSServiceFactory.getBorrowingAccountPropertyService();
			if (borrowingProductId != null) {
				data = service.getAccountPropertyByBorrowingProductId(borrowingProductId);
				Gson gson = new GsonBuilder().create();
				jsonBorrowingAccPropertyString = gson.toJson(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling BorrowingsAccountProperty service in getAccountPropertyByBorrowingProductId() method.");
		return jsonBorrowingAccPropertyString;
	}

	@GET
	@Path("/product/getproductbyid")
	@Produces("application/json")
	public String getProductById(@QueryParam("borrowing_product_id") Integer borrowingProductId) {

		logger.info("Start : Calling BorrowingsAccountProperty master service in getAllAccounts() method.");
		BorrowingProductData data = new BorrowingProductData();
		String jsonAllProductsString = "";
		try {
			IBorrowingProductService service = KLSServiceFactory.getBorrowingProductService();
			data = service.getProductById(borrowingProductId);
			Gson gson = new GsonBuilder().create();
			jsonAllProductsString = gson.toJson(data);
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling BorrowingsAccountProperty master service in getAllAccounts() method.");
		return jsonAllProductsString;
	}

	@PUT
	@Path("/product")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateBorrowingProduct(String str) {

		logger.info("Start : Calling BorrowingProductService in updateBorrowingProduct() method." + str);
		BorrowingProductData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, BorrowingProductData.class);
			IBorrowingProductService service = KLSServiceFactory.getBorrowingProductService();
			service.updateBorrowingProduct(data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while updating BorrowingProduct");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "BorrowingProduct  Updated Successfully!";

	}

	@GET
	@Path("/product/checkproductcode")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean checkForUniqueProductCode(@QueryParam("productCode") String productCode) {

		logger.info("Start : Checking for unique borrowing product code in checkForUniqueProductCode() method.");
		boolean flag = false;
		logger.info(" productCode : " + productCode);
		try {
			IBorrowingProductService service = KLSServiceFactory.getBorrowingProductService();
			flag = service.checkForUniqueProductCode(productCode);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info(" flag : " + flag);
		logger.info("End : Checking for unique borrowing product code in checkForUniqueProductCode() method.");
		return flag;
	}

	@GET
	@Path("/product/checkproductname")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean checkForUniqueProductName(@QueryParam("productName") String productName) {

		logger.info("Start : Checking for unique borrowing product Name in checkForUniqueProductName() method.");
		boolean flag = false;
		logger.info(" productCode : " + productName);
		try {
			IBorrowingProductService service = KLSServiceFactory.getBorrowingProductService();
			flag = service.checkForUniqueProductName(productName);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info(" flag : " + flag);
		logger.info("End : Checking for unique borrowing product Name in checkForUniqueProductName() method.");
		return flag;
	}

	@GET
	@Path("/account/bypacsidproductid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAccountPropertyByProductIdPacsId(@QueryParam("pacsId") Integer pacsId, @QueryParam("productId") Integer productId) {

		logger.info("Start : Calling BorrowingsAccountProperty service in getAccountPropertyByProductIdPacsId() method.==" + productId);
		BorrowingsAccountPropertyData data = null;
		String jsonBorrowingAccPropertyString = "";
		try {
			IBorrowingAccountPropertyService service = KLSServiceFactory.getBorrowingAccountPropertyService();
			if (productId != null) {
				data = service.getAccountPropertyByProductIdPacsId(pacsId, productId);
				Gson gson = new GsonBuilder().create();
				jsonBorrowingAccPropertyString = gson.toJson(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling BorrowingsAccountProperty service in getAccountPropertyByProductIdPacsId() method.");
		return jsonBorrowingAccPropertyString;
	}

	@GET
	@Path("/account/byborrowingproductidpacsid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAccountPropertyByBorrowingProductIdPacsId(@QueryParam("pacsId") Integer pacsId,
			@QueryParam("borrowingProductId") Integer borrowingProductId) {

		logger.info("Start : Calling BorrowingsAccountProperty service in getAccountPropertyByBorrowingProductIdPacsId() method.=="
				+ borrowingProductId + ":pacsId:" + pacsId);
		BorrowingsAccountPropertyData data = null;
		String jsonBorrowingAccPropertyString = "";
		try {
			IBorrowingAccountPropertyService service = KLSServiceFactory.getBorrowingAccountPropertyService();
			if (borrowingProductId != null && pacsId != null) {
				data = service.getAccountPropertyByBorrowingProductIdPacsId(pacsId, borrowingProductId);
				Gson gson = new GsonBuilder().create();
				jsonBorrowingAccPropertyString = gson.toJson(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling BorrowingsAccountProperty service in getAccountPropertyByBorrowingProductIdPacsId() method.");
		return jsonBorrowingAccPropertyString;
	}
	
	@POST
	@Path("/saveborrwoingrecoveryentry")
	@Consumes("application/json")
	@Produces("application/json")
	public String saveBorrowingRecoveryEntry(String jsonPassingData) {
		String message = null;
		BorrowingRecoveryEntryData borrowingData = null;
		try {

			IBorrowingRecoveryEntryService service = KLSServiceFactory.getBorrowingRecoveryEntryService();
			Gson gson = new Gson();
			borrowingData = gson.fromJson(jsonPassingData, BorrowingRecoveryEntryData.class);
			message = service.saveBorrowingRecoveryEntry(borrowingData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	@GET
	@Path("/borrowingRecoveryEntryByPacsId")
	@Consumes("*/*")
	@Produces("application/json")
	
	public String getBorrowingRecoveryEntryByPacsId(@QueryParam("pacsId") Integer pacsId , @QueryParam("status") Integer status){
		logger.info("Start : Calling Loan Disbursement Entry Service in getLoanDisbursementEntry() method. ");
		List<BorrowingRecoveryEntryData> dataList = new ArrayList<>();
		String jsonAllDisbursementEntryString = "";
		try {
			IBorrowingRecoveryEntryService service = KLSServiceFactory.getBorrowingRecoveryEntryService();
			if (pacsId != null) {
				dataList = service.getBorrowingRecoveryEntriesByPacsId(pacsId, status);
				Gson gson = new GsonBuilder().create();
				jsonAllDisbursementEntryString = gson.toJson(dataList);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Loan Disbursement Entry Service service in getLoanDisbursementEntry() method.");
		return jsonAllDisbursementEntryString;
	}
	
	@GET
	@Path("/borrwoingrecoveryentry/account")
	@Consumes("*/*")
	@Produces("application/json")
	public String getBorrowingRecoveryEntryByAccount(@QueryParam("accountId") Long accountId) {

		logger.info("Start : Calling Loan Disbursement Entry Service in getLoanDisbursementEntry() method. accountId=" + accountId);
		Map map = new HashMap<>();
		String jsonAllDisbursementEntryString = "";
		try {
			IBorrowingRecoveryEntryService service = KLSServiceFactory.getBorrowingRecoveryEntryService();
			if (accountId != null) {
				map = service.getBorrowingRecoveryAmounts(accountId);
				Gson gson = new GsonBuilder().create();
				jsonAllDisbursementEntryString = gson.toJson(map);
			}
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Loan Disbursement Entry Service service in getLoanDisbursementEntry() method.");
		return jsonAllDisbursementEntryString;
	}
	
	@GET
	@Path("/borrwoingrecoveryentries/borrwingprodid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getBorrowingRecoveryEntryByProdId(@QueryParam("borrowingProdId") Integer borrowingProdId, @QueryParam("status") Integer status) {

		logger.info("Start : Calling Loan Disbursement Entry Service in getLoanDisbursementEntry() method. ");
		List<BorrowingRecoveryEntryData> dataList = new ArrayList<>();
		String jsonAllDisbursementEntryString = "";
		try {
			IBorrowingRecoveryEntryService service = KLSServiceFactory.getBorrowingRecoveryEntryService();
			if (borrowingProdId != null) {
				dataList = service.getBorrowingRecoveryEntries(borrowingProdId, status);
				Gson gson = new GsonBuilder().create();
				jsonAllDisbursementEntryString = gson.toJson(dataList);
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
	@Path("/updateborrwoingrecoveryentry")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateBorrowingRecoveryEntry(String str) {

		logger.info("Start : Calling BorrowingProductService in updateBorrowingRecoveryEntry() method." + str);
		BorrowingRecoveryEntryData data = null;
		String message = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, BorrowingRecoveryEntryData.class);
			IBorrowingRecoveryEntryService service = KLSServiceFactory.getBorrowingRecoveryEntryService();
			message = service.updateBorrowingRecoveryEntry(data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while updating BorrowingRecoveryEntry");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return message;

	}
	
	@DELETE
	@Path("/borrwoingrecoveryentry/delete")
	@Consumes("*/*")
	@Produces("application/text")
	public String deleteBorrowingRecoveryEntry(@QueryParam("borrowingRecoveryId") Long borrowingRecoveryId) {

		logger.info("Start : Calling BorrowingsAccountProperty service in deleteAccount() method. : " + borrowingRecoveryId);
		String returnMessage = null;
		try {
			IBorrowingRecoveryEntryService service = KLSServiceFactory.getBorrowingRecoveryEntryService();
			returnMessage = service.deleteBorrowingRecoveryEntry(borrowingRecoveryId);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling BorrowingsAccountProperty service in deleteAccount() method.");
		return returnMessage;
	}
	
	@POST
	@Path("/saveborrwoingrecoverypassing")
	@Consumes("application/json")
	@Produces("application/json")
	public String saveBorrowingRecoveryEntryPassing(String jsonPassingData) {
		String message = null;
		BorrowingRecoveryEntryData borrowingData = null;
		try {

			IBorrowingRecoveryEntryService service = KLSServiceFactory.getBorrowingRecoveryEntryService();
			Gson gson = new Gson();
			borrowingData = gson.fromJson(jsonPassingData, BorrowingRecoveryEntryData.class);
			message = service.doBorrowingRecovery(borrowingData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	@GET
	@Path("/borrowingDirectRecoveryEntryPassing")
	@Consumes("*/*")
	@Produces("application/json")
	public String borrowingDirectRecoveryEntryPassing(@QueryParam("borrowingRecoveryId") Long borrowingRecoveryId , @QueryParam("businessDate") String businessDate) {
		String message = "Recovery Entry Passed Successfully.";
		KLSServiceFactory.getBorrowingTransactionService().borrowingDirectCollectionPassing(borrowingRecoveryId ,businessDate);
		return message;
	}
	
	@GET
	@Path("/borrowingDirectRecoveryEntryRejection")
	@Consumes("*/*")
	@Produces("application/json")
	public String borrowingDirectRecoveryEntryRejection(@QueryParam("borrowingRecoveryId") Long borrowingRecoveryId ,  @QueryParam("businessDate") String businessDate) {
		String message = "Recovery Entry Rejected Successfully.";
		try {
			IBorrowingRecoveryEntryService service = KLSServiceFactory.getBorrowingRecoveryEntryService();
			BorrowingRecoveryEntryData data = service.getBorrowingRecoveryEntryById(borrowingRecoveryId);
			data.setStatus(3);
			service.updateBorrowingRecoveryEntry(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	
	
	@GET
    @Path("/borrowingsLineOfCredit")
    @Consumes("*/*")
    @Produces("application/json")
    public String getBorrowingsLineOfCredit(@QueryParam("locId") Long locId){
		IBorrowingsLineOfCreditService service = KLSServiceFactory.getBorrowingLineOfCreditService();
		BorrowingsLineOfCreditData borrowingLineOfCredit = service.getBorrowingsLineOfCreditById(locId);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(borrowingLineOfCredit);
    }
    
    @GET
    @Path("/borrowingsLineOfCreditByAccountId")
    @Consumes("*/*")
    @Produces("application/json")
    public String getOrderedBorrowingLocListByAccountId(@QueryParam("accountId") Long accountId){
    	IBorrowingsLineOfCreditService service = KLSServiceFactory.getBorrowingLineOfCreditService();
        List<BorrowingsLineOfCreditData> borrowingLineOfCreditList = service.getOrderedBorrowingLocListByAccountId(accountId);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(borrowingLineOfCreditList);
    }
    
    @GET
    @Path("/borrowingRocoveryInfoBased")
    @Consumes("*/*")
    @Produces("application/json")
    public String getRocoveryInfoBasedOnAmountPaid(@QueryParam("amountPaid") BigDecimal amountPaid , @QueryParam("recoverySequenceId") Integer recoverySequenceId, @QueryParam("loanLocId") Long loanLocId){
    	Gson gson = new GsonBuilder().create();
    	LoanRecoveryData data= null;
    	try {
    	IBorrowingRecoveryEntryService service = KLSServiceFactory.getBorrowingRecoveryEntryService();
    	data = service.getRocoveryInfoBasedOnAmountPaid(amountPaid, recoverySequenceId, loanLocId);
      }catch(Exception ex){
      ex.printStackTrace();
      }
		return gson.toJson(data , LoanRecoveryData.class);
     	
    }
    
	
}
