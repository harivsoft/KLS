/**
 * 
 */
package com.vsoftcorp.kls.rest.service;

import java.util.ArrayList;
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
import com.vsoftcorp.kls.data.LoanProductEnumsData;
import com.vsoftcorp.kls.data.ProductData;
import com.vsoftcorp.kls.service.IProductService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author a9152
 * 
 */
@Path("/pacs")
public class PacsLoanProductRestService {

	private static final Logger logger = Logger.getLogger(PacsLoanProductRestService.class);

	public PacsLoanProductRestService() {
	}

	private static final PacsLoanProductRestService INSTANCE = new PacsLoanProductRestService();

	public static PacsLoanProductRestService getInstance() {
		return INSTANCE;
	}

	@POST
	@Path("/loanproduct")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveProduct(String productJsonString) {

		logger.info("Start : Calling branch master service in saveBranch() method.");
		ProductData data = null;
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(productJsonString, ProductData.class);
			productMasterService.saveProduct(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling branch master service in saveProduct() method.");
		return "Product Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/loanproduct")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateProduct(String productJsonString) {

		logger.info("Start : Calling Product master service in updateProduct() method.");
		ProductData data = null;
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(productJsonString, ProductData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			productMasterService.updateProduct(data);
		} catch (Exception e) {
			logger.error("Error while updating Product Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Calling Product master service in updateProduct() method.");
		return "Product Data Updated Successfully!";
	}

	@GET
	@Path("/loanproduct")
	@Produces("application/json")
	public String getAllProducts() {

		logger.info("Start : Calling Product master service in getAllProducts() method.");
		List<ProductData> productMasterDataList = new ArrayList<ProductData>();
		String jsonAllProductsString = "";
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			productMasterDataList = productMasterService.getAllProducts(null);
			Gson gson = new GsonBuilder().create();
			jsonAllProductsString = gson.toJson(productMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling product master service in getAllProducts() method.");
		return jsonAllProductsString;
	}

	@GET
	@Path("/loanproductenums")
	@Produces("application/json")
	public String getAllLoanProductEnums() {

		logger.info("Start : Fetching all loan product enums in getAllLoanProductEnums() method.");
		String loanProductEnumsString = "";
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			LoanProductEnumsData loanProductEnumsData = productMasterService.getAllLoanProductEnums();
			Gson gson = new GsonBuilder().create();
			loanProductEnumsString = gson.toJson(loanProductEnumsData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Fetching all loan product enums in getAllLoanProductEnums() method.");
		return loanProductEnumsString;
	}

	@GET
	@Path("/loanproductcode")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean checkForUniqueLoanProductCode(@QueryParam("loanProductCode") String loanProductCode) {

		logger.info("Start : Checking for unique loan product code in checkForUniqueLoanProductCode() method.");
		boolean flag = false;
		logger.info(" loanProductCode : " + loanProductCode);
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			flag = productMasterService.checkForUniqueLoanProductCode(loanProductCode);
		} catch (Exception e) {
			flag = false;
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info(" flag : " + flag);
		logger.info("End : Checking for unique loan product code in checkForUniqueLoanProductCode() method.");
		return flag;
	}

}
