/**
 * 
 */
package com.vsoftcorp.kls.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.kls.data.LandDetailData;
import com.vsoftcorp.kls.data.MemberSummaryData;
import com.vsoftcorp.kls.service.IKLSOmniIntegrationService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author skeleti
 *
 */
@Path("/omni")
public class KLSOmniIntegrationRestService {
	
	private static final Logger logger = Logger.getLogger(KLSOmniIntegrationRestService.class);

	public KLSOmniIntegrationRestService() {

	}

	private static final KLSOmniIntegrationRestService INSTANCE = new KLSOmniIntegrationRestService();

	public static KLSOmniIntegrationRestService getInstance() {
		return INSTANCE;
	}
	
	@GET
	@Path("/members/{membernumber}/summary")
	@Produces("application/json")
	public String  getMemberSummary(@PathParam("membernumber") String membernumber){
		
		logger.info("Start: getMemberSummary() in KLSOmniIntegrationRestService"); 
		MemberSummaryData memberSummary;
		String responce=null;
		try{
			IKLSOmniIntegrationService service = KLSServiceFactory.getKLSOmniIntegrationService();
			memberSummary = service.getMemberSummaryService(membernumber);
			Gson gson = new GsonBuilder().create();
			responce = gson.toJson(memberSummary);
		}catch(Exception e){
			logger.error("Error while getting MemberSummary in KLSOmniIntegrationRestService" );
		}
		logger.info("END: getMemberSummary() in KLSOmniIntegrationRestService"); 
		return responce;
	}
	
	@GET
	@Path("/landDetail/{membernumber}")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLandDetailsByCustomerNumber(@PathParam("membernumber") String membernumber) {

		logger.info("Start : Calling LandDetails  service in getLandDetailsByCustomerNumber() method.");
		LandDetailData data = null;
		String jsonAllLandDetailString = "";
		try {
			IKLSOmniIntegrationService service = KLSServiceFactory.getKLSOmniIntegrationService();
			data = service.getLandDetailsByMemberNumber(membernumber);
			Gson gson = new GsonBuilder().create();
			jsonAllLandDetailString = gson.toJson(data);
		} catch (Exception e) {
			logger.error("Error: Error while retrieving LandDetails data  in getLandDetailsByCustomerNumber() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling  LandDetails service in getLandDetailsByCustomerNumber() method.");
		return jsonAllLandDetailString;
	}
	
	@GET
	@Path("/getdata")
	@Produces("application/json")
	public String getDataForOMNI(@QueryParam("inputParam") String inputParam) {

		logger.info("Start : Calling getData()  service in getDataForOMNI() method.");
		String dataString = "";
		try {
			IKLSOmniIntegrationService service = KLSServiceFactory.getKLSOmniIntegrationService();
			dataString = service.getData(inputParam);
		} catch (Exception e) {
			logger.error("Error: Error while retrieving data  in getDataForOMNI() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling getData()  service in getDataForOMNI() method.");
		return dataString;
	}
	
}