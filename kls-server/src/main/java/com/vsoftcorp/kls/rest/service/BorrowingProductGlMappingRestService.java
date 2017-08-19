package com.vsoftcorp.kls.rest.service;

import javax.persistence.NoResultException;
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
import com.vsoftcorp.kls.data.BorrowingProductGlMappingData;
import com.vsoftcorp.kls.service.IBorrowingProductGlMapping;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.BorrowingProductGlMappingHelper;
import com.vsoftcorp.kls.util.EntityManagerUtil;

@Path("/borrowinggl")
public class BorrowingProductGlMappingRestService {
	private static final Logger logger = Logger.getLogger(BorrowingProductGlMappingRestService.class);

	public BorrowingProductGlMappingRestService() {

	}

	private static final BorrowingProductGlMappingRestService INSTANCE = new BorrowingProductGlMappingRestService();

	public static BorrowingProductGlMappingRestService getInstance() {
		return INSTANCE;
	}

	@POST
	@Path("/saveborrowingproductglmapping")
	@Consumes("application/json")
	@Produces("application/json")
	public String saveBorrowingProductGlMapping(String borrowingProductGlMappingData) {

		logger.info("Start : Entered into saveBorrowingProductGlMapping() method in RestService Class.");
		BorrowingProductGlMappingData data = null;
		String returnMessage = "";
		try {
			IBorrowingProductGlMapping service = KLSServiceFactory.getBorrowingProductGlMappingService();
			Gson gson = new Gson();
			data = gson.fromJson(borrowingProductGlMappingData, BorrowingProductGlMappingData.class);
			returnMessage = service.saveBorrowingProductGlMapping(BorrowingProductGlMappingHelper.getBorrowingProductGlMapping(data));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error : Error in saveBorrowingProductGlMapping() method in RestService Class.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}

		logger.info("End : saveBorrowingProductGlMapping() method in RestService Class.");
		return returnMessage;
	}
	@PUT
	@Path("/updateborrowingproductglmapping")
	@Consumes("application/json")
	@Produces("application/json")
	public String updateBorrowingProductGlMapping(String borrowingProductGlMappingData) {

		logger.info("Start : Entered into updateBorrowingProductGlMapping() method in RestService Class.");
		BorrowingProductGlMappingData data = null;
		String returnMessage = "";
		try {
			IBorrowingProductGlMapping service = KLSServiceFactory.getBorrowingProductGlMappingService();
			Gson gson = new Gson();
			data = gson.fromJson(borrowingProductGlMappingData, BorrowingProductGlMappingData.class);
			returnMessage = service.updateBorrowingProductGlMapping(BorrowingProductGlMappingHelper.getBorrowingProductGlMapping(data));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error : Error in updateBorrowingProductGlMapping() method in RestService Class.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}

		logger.info("End : updateBorrowingProductGlMapping() method in RestService Class.");
		return returnMessage;
	}


	@GET
	@Path("/getborrowingproductglmapping")
	@Consumes("*/*")
	@Produces("application/json")
	public String getBorrowingProductGlMapping(@QueryParam("productId") Integer productId,@QueryParam("pacsId") Integer pacsId) {

		logger.info("getBorrowingProductGlMapping entered");
		String jsonString = "";
		BorrowingProductGlMappingData borrowingProductGlMappingData=null;
		try {
			IBorrowingProductGlMapping service = KLSServiceFactory.getBorrowingProductGlMappingService();
			
			borrowingProductGlMappingData = service.getBorrowingProductGlMappingById(productId,pacsId);
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(borrowingProductGlMappingData);

		}catch(NoResultException ex){
			logger.info("No Entity found with this id");
			return null;
		}
		catch (Exception e) {
			logger.info("Error: Erroe in getBorrowingProductGlMapping ");
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: getBorrowingProductGlMapping ");
		return jsonString;
	}
}
