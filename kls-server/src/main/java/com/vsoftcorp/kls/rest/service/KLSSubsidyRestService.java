package com.vsoftcorp.kls.rest.service;
/*
 * @skeleti
 */
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

import com.bea.xml.stream.samples.Parse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.kls.data.SeasonData;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.subsidy.IInstituteMasterService;
import com.vsoftcorp.kls.service.subsidy.IInterestSubsidyService;
import com.vsoftcorp.kls.service.subsidy.ISubsidySchemeGlService;
import com.vsoftcorp.kls.subsidy.data.InstituteMasterData;
import com.vsoftcorp.kls.subsidy.data.InterestSubsidyData;
import com.vsoftcorp.kls.subsidy.data.SubsidySchemeGlMappingData;
import com.vsoftcorp.kls.util.EntityManagerUtil;

@Path("/subsidy")
public class KLSSubsidyRestService {
	
	private static final Logger logger = Logger.getLogger(KLSSubsidyRestService.class);

	public KLSSubsidyRestService() {

	}

	private static final KLSSubsidyRestService INSTANCE = new KLSSubsidyRestService();

	public static KLSSubsidyRestService getInstance() {
		return INSTANCE;
	}
	
	
	@POST
	@Path("/saveinstitutemaster")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveInstituteMaster(String instituteJsonString) {

		logger.info("Start : Calling Institute master service in saveInstituteMaster() method.");
		try {
			Gson gson = new GsonBuilder().create();
			InstituteMasterData Data = gson.fromJson(instituteJsonString, InstituteMasterData.class);
			IInstituteMasterService instService = KLSServiceFactory.getInstituteMasterService();
			instService.savesInstituteMasterservice(Data);
		} catch (Exception excp) {
			logger.error("Error while saving Institute Master Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Institute master service in saveInstituteMaster() method.");
		return "Institute Master Saved Successfully!";
	}
	
	@PUT
	@Path("/modifyinstitutemaster")
	@Consumes("application/json")
	@Produces("application/text")
	public String modifyInstituteMaster(String instituteJsonString) {

		logger.info("Start : Calling Institute master service in modifyInstituteMaster() method.");
		try {
			Gson gson = new GsonBuilder().create();
			InstituteMasterData Data = gson.fromJson(instituteJsonString, InstituteMasterData.class);
			IInstituteMasterService instService = KLSServiceFactory.getInstituteMasterService();
			instService.modifiesInstituteMasterservice(Data);
		} catch (Exception excp) {
			logger.error("Error while modified Institute Master Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Institute master service in modifyInstituteMaster() method.");
		return "Institute Master Modified Successfully!";
	}
	
	@GET
	@Path("/getinstituttemasterbyid")
	@Consumes("application/json")
	@Produces("application/json")
	public String getInstituteMaster(@QueryParam("instituteMasterId") Long instituteMasterId) {

		logger.info("Start : Calling Institute Master service in getInstituteMaster() method.");
		InstituteMasterData data = null;
		String instituteData = "";
		try {
			Gson gson = new GsonBuilder().create();
			IInstituteMasterService instService = KLSServiceFactory.getInstituteMasterService();
			data = instService.getInstituteMasterservice(instituteMasterId);
			instituteData = gson.toJson(data);

		} catch (Exception excp) {
			logger.error("Error while fetching Institute Master Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Institute Master service in getInstituteMaster() method.");
		return instituteData;
	}
	
	@GET
	@Path("/getallinstitutemaster")
	@Consumes("application/json")
	@Produces("application/json")
	public String getAllInstituteMaster() {

		logger.info("Start : Calling Institute Master service in getAllInstituteMaster() method.");
		List<InstituteMasterData> data = null;
		String instituteData = "";
		try {
			Gson gson = new GsonBuilder().create();
			IInstituteMasterService instService = KLSServiceFactory.getInstituteMasterService();
			data = instService.getAllInstituteMasterservice();
			instituteData = gson.toJson(data);

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while fetching Institute Master Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Institute Master service in getAllInstituteMaster() method.");
		return instituteData;
	}
	
	@POST
	@Path("/saveinterestsubsidy")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveInterestSubsidy(String subsidyJsonString) {

		logger.info("Start : Calling Interest subsidy service in saveInterestSubsidy() method.");
		try {
			Gson gson = new GsonBuilder().create();
			InterestSubsidyData subsidyData = gson.fromJson(subsidyJsonString, InterestSubsidyData.class);
			IInterestSubsidyService subsidyService = KLSServiceFactory.getInterestSubsidyService();
			subsidyService.saveInterestSubsidyService(subsidyData);
		} catch (Exception excp) {
			logger.error("Error while saving Interest Subvention/Subsidy Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Interest subsidy service in saveInterestSubsidy() method.");
		return "Interest Subvention/Subsidy Saved Successfully!";
	}
	
	
	@PUT
	@Path("/modifyinterestsubsidy")
	@Consumes("application/json")
	@Produces("application/text")
	public String modifyInterestSubsidy(String subsidyJsonString) {

		logger.info("Start : Calling Interest subsidy service in modifyInterestSubsidy() method.");
		try {
			Gson gson = new GsonBuilder().create();
			InterestSubsidyData subsidyData = gson.fromJson(subsidyJsonString, InterestSubsidyData.class);
			IInterestSubsidyService subsidyService = KLSServiceFactory.getInterestSubsidyService();
			subsidyService.modifyInterestSubsidyService(subsidyData);
		} catch (Exception excp) {
			logger.error("Error while saving Interest Subvention/Subsidy Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Interest subsidy service in modifyInterestSubsidy() method.");
		return "Interest Subvention/Subsidy Modified Successfully!";
	}
	
	@GET
	@Path("/getinterestsubsidybyid")
	@Consumes("application/json")
	@Produces("application/json")
	public String getInterestSubsidy(@QueryParam("subsidyId") Long subsidyId) {

		logger.info("Start : Calling Interest subsidy service in getInterestSubsidy() method.");
		InterestSubsidyData data = null;
		String subsidyData = "";
		try {
			Gson gson = new GsonBuilder().create();
			IInterestSubsidyService subsidyService = KLSServiceFactory.getInterestSubsidyService();
			data = subsidyService.getInterestSubsidyService(subsidyId);
			subsidyData = gson.toJson(data);

		} catch (Exception excp) {
			logger.error("Error while fetching Interest Subvention/Subsidy Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Interest subsidy service in getInterestSubsidy() method.");
		return subsidyData;
	}
	
	@GET
	@Path("/getallinterestsubsidy")
	@Consumes("application/json")
	@Produces("application/json")
	public String getAllInterestSubsidy() {

		logger.info("Start : Calling Interest subsidy service in getAllInterestSubsidy() method.");
		List<InterestSubsidyData> data = null;
		String subsidyData = "";
		try {
			Gson gson = new GsonBuilder().create();
			IInterestSubsidyService subsidyService = KLSServiceFactory.getInterestSubsidyService();
			data = subsidyService.getAllInterestSubsidyService();
			subsidyData = gson.toJson(data);

		} catch (Exception excp) {
			logger.error("Error while fetching Interest Subvention/Subsidy Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Interest subsidy service in getAllInterestSubsidy() method.");
		return subsidyData;
	}
	
	@GET
	@Path("/getinterestsubsidybytype")
	@Consumes("application/json")
	@Produces("application/json")
	public String getInterestSubsidy(@QueryParam("subsidySchemeType") String subsidySchemeType) {

		logger.info("Start : Calling Interest subsidy service in getInterestSubsidy() method.");
		List<InterestSubsidyData> data = null;
		String subsidyData = "";
		try {
			Gson gson = new GsonBuilder().create();
			IInterestSubsidyService subsidyService = KLSServiceFactory.getInterestSubsidyService();
			data = subsidyService.getInterestSubsidySchemeByType(subsidySchemeType);
			subsidyData = gson.toJson(data);

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while fetching Interest Subvention/Subsidy Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Interest subsidy service in getInterestSubsidy() method.");
		return subsidyData;
	}
	
	@POST
	@Path("/subsidyschemeglmapping")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveSubsidySchemeGlMapping(String subsidyGlJsonString) {

		logger.info("Start : Calling Interest subsidy service in saveInterestSubsidy() method.");
		try {
			Gson gson = new GsonBuilder().create();
			SubsidySchemeGlMappingData subsidyGlData = gson.fromJson(subsidyGlJsonString, SubsidySchemeGlMappingData.class);
			ISubsidySchemeGlService subsidyService = KLSServiceFactory.getSubsidySchemeGlService();
			subsidyService.saveSubsidySchemeGlMapping(subsidyGlData);
		} catch (Exception excp) {
			logger.error("Error while saving Interest Subvention/Subsidy Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Interest subsidy service in saveInterestSubsidy() method.");
		return "Subsidy Scheme Gl Mapping Saved Successfully!";
	}
	
	@PUT
	@Path("/subsidyschemeglmapping")
	@Consumes("application/json")
	@Produces("application/text")
	public String modifySubsidySchemeGlMapping(String subsidyJsonString) {

		logger.info("Start : Calling SubsidySchemeGlMapping service in modifySubsidySchemeGlMapping() method.");
		try {
			Gson gson = new GsonBuilder().create();
			SubsidySchemeGlMappingData subsidyData = gson.fromJson(subsidyJsonString, SubsidySchemeGlMappingData.class);
			ISubsidySchemeGlService subsidyService = KLSServiceFactory.getSubsidySchemeGlService();
			subsidyService.modifySubsidySchemeGlMapping(subsidyData);
		} catch (Exception excp) {
			logger.error("Error while saving SubsidySchemeGlMapping Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling SubsidySchemeGlMapping service in modifySubsidySchemeGlMapping() method.");
		return "Subsidy Scheme Gl Mapping Modified Successfully!";
	}
	
	@GET
	@Path("/subsidyschemeglmapping")
	@Consumes("application/json")
	@Produces("application/json")
	public String getAllSubsidySchemeGlMapping(@QueryParam("pacsId") Integer pacsId) {

		logger.info("Start : Calling SubsidySchemeGlMapping service in getAllSubsidySchemeGlMapping() method.");
		List<SubsidySchemeGlMappingData> data = null;
		String subsidyData = "";
		try {
			Gson gson = new GsonBuilder().create();
			ISubsidySchemeGlService subsidyService = KLSServiceFactory.getSubsidySchemeGlService();
			data = subsidyService.getAllSubsidySchemeGlMapping(pacsId);
			subsidyData = gson.toJson(data);

		} catch (Exception excp) {
			logger.error("Error while fetching SubsidySchemeGlMapping Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling SubsidySchemeGlMapping service in getAllSubsidySchemeGlMapping() method.");
		return subsidyData;
	}
	
	@GET
	@Path("/subsidyschemeglmappingbypacandscheme")
	@Consumes("application/json")
	@Produces("application/json")
	public String getSubsidySchemeGlMapping(@QueryParam("subsidySchemeId") Long subsidySchemeId,@QueryParam("pacsId") Integer pacsId) {

		logger.info("Start : Calling Interest subsidy service in getSubsidySchemeGlMapping() method.");
		SubsidySchemeGlMappingData data = null;
		String subsidyData = "";
		try {
			Gson gson = new GsonBuilder().create();
			ISubsidySchemeGlService subsidyService = KLSServiceFactory.getSubsidySchemeGlService();
			data = subsidyService.getSubsidySchemeGlMapping(subsidySchemeId,pacsId);
			subsidyData = gson.toJson(data);

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while fetching SubsidySchemeGlMapping Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Interest subsidy service in getSubsidySchemeGlMapping() method.");
		return subsidyData;
	}
	
	@GET
	@Path("/subsidyscheheModifiable")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean isLOCExist(@QueryParam("schemeId") Integer schemeId,@QueryParam("seasonId") Long seasonId) {

		logger.info("Start : Calling isLOCExistService() method in isLOCExist()");
		boolean flag=false;
		try {
			IInterestSubsidyService subsidyService = KLSServiceFactory.getInterestSubsidyService();
			flag=subsidyService.isLOCExistService(schemeId, seasonId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling isLOCExistService() method in isLOCExist()");
		return flag;
	}
	
	@GET
	@Path("/subsidyscheheglmodifiable")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean isTransactionExist(@QueryParam("subsidyReceievableGl") String subsidyReceievableGl, @QueryParam("pacsId") Integer pacsId) {

		logger.info("Start : Calling isLOCExistService() method in isLOCExist()");
		boolean flag=false;
		try {
			ISubsidySchemeGlService subsidyService = KLSServiceFactory.getSubsidySchemeGlService();
			flag=subsidyService.isTransactionExist(subsidyReceievableGl, pacsId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling isLOCExistService() method in isLOCExist()");
		return flag;
	}
	
	
	@GET
	@Path("/getAllSubsidys")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllSubsidys(){
		IInterestSubsidyService subsidyService = KLSServiceFactory.getInterestSubsidyService();
		List<InterestSubsidyData>  data = new ArrayList<InterestSubsidyData>();
		data.addAll(subsidyService.getInterestSubsidySchemeByType("SB"));
		data.addAll(subsidyService.getInterestSubsidySchemeByType("SS"));
		Gson gson = new GsonBuilder().create();
		for (InterestSubsidyData interestSubsidyData : data) {
			interestSubsidyData.setInstituteMasterName(KLSServiceFactory.getInstituteMasterService().getInstituteMasterservice(interestSubsidyData.getInstituteMasterId()).getInstituteName());
			interestSubsidyData.setSeasonName(KLSServiceFactory.getSeasonService().getSeason(new Long(interestSubsidyData.getSeasonId())).getName());
			interestSubsidyData.setSchemeName(KLSServiceFactory.getSchemeMasterService().getScheme(interestSubsidyData.getSchemeId()).getName());
			if("SS".equalsIgnoreCase(interestSubsidyData.getTypeOfSchemeName())){
				interestSubsidyData.setTypeOfSchemeName("Subvention");
			}else{
				interestSubsidyData.setTypeOfSchemeName("Subsidy");
			}
		}
		String subsidyData = gson.toJson(data);
		
		return subsidyData;
	}
	

}
