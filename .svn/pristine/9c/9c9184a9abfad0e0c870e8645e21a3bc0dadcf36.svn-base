package com.vsoftcorp.kls.rest.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.bcel.generic.LLOAD;
import org.apache.log4j.Logger;

import com.ctc.wstx.io.InputBootstrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vsoftcorp.kls.business.entities.BorrowingProductGlMapping;
import com.vsoftcorp.kls.business.entities.SeasonDefinition;
import com.vsoftcorp.kls.data.AccountData;
import com.vsoftcorp.kls.data.ActivityData;
import com.vsoftcorp.kls.data.BankPacsGlData;
import com.vsoftcorp.kls.data.BankParameterData;
import com.vsoftcorp.kls.data.BorrowingProductGlMappingData;
import com.vsoftcorp.kls.data.BranchData;
import com.vsoftcorp.kls.data.CalendarData;
import com.vsoftcorp.kls.data.CalendarSetupData;
import com.vsoftcorp.kls.data.CropMasterData;
import com.vsoftcorp.kls.data.CropTypeMasterData;
import com.vsoftcorp.kls.data.CustomerData;
import com.vsoftcorp.kls.data.DistrictData;
import com.vsoftcorp.kls.data.DocumentData;
import com.vsoftcorp.kls.data.EventTypeData;
import com.vsoftcorp.kls.data.InterestCategoryData;
import com.vsoftcorp.kls.data.KccCardMappingData;
import com.vsoftcorp.kls.data.LandDetailData;
import com.vsoftcorp.kls.data.LandTypeData;
import com.vsoftcorp.kls.data.LoanProductDocumentMappingData;
import com.vsoftcorp.kls.data.LoanProductPurposeMappingData;
import com.vsoftcorp.kls.data.LoggedInUserDetailsData;
import com.vsoftcorp.kls.data.NewCalendarData;
import com.vsoftcorp.kls.data.PacsData;
import com.vsoftcorp.kls.data.PacsGlData;
import com.vsoftcorp.kls.data.PacsGlMappingData;
import com.vsoftcorp.kls.data.PacsLoanApplicationDetailData;
import com.vsoftcorp.kls.data.ProductData;
import com.vsoftcorp.kls.data.ProductDefinitionData;
import com.vsoftcorp.kls.data.ProductTypeData;
import com.vsoftcorp.kls.data.PurposeData;
import com.vsoftcorp.kls.data.RecoverySequenceData;
import com.vsoftcorp.kls.data.SBAccountMappingData;
import com.vsoftcorp.kls.data.SanctionedComponentData;
import com.vsoftcorp.kls.data.ScaleOfFinanceData;
import com.vsoftcorp.kls.data.SchemeData;
import com.vsoftcorp.kls.data.SeasonData;
import com.vsoftcorp.kls.data.SeasonMasterData;
import com.vsoftcorp.kls.data.SeasonParameterData;
import com.vsoftcorp.kls.data.SlabwiseInterestRateData;
import com.vsoftcorp.kls.data.SubPurposeData;
import com.vsoftcorp.kls.data.TalukaData;
import com.vsoftcorp.kls.data.TenantLandDetailsData;
import com.vsoftcorp.kls.data.UnitData;
import com.vsoftcorp.kls.data.VillageData;
import com.vsoftcorp.kls.data.VillageDetailsData;
import com.vsoftcorp.kls.loans.gl.service.ILoansGLEntriesService;
import com.vsoftcorp.kls.service.IActivityService;
import com.vsoftcorp.kls.service.IBankPacsGlService;
import com.vsoftcorp.kls.service.IBankParameterService;
import com.vsoftcorp.kls.service.IBorrowingProductGlMapping;
import com.vsoftcorp.kls.service.IBorrowingProductService;
import com.vsoftcorp.kls.service.IBranchService;
import com.vsoftcorp.kls.service.ICalendarService;
import com.vsoftcorp.kls.service.ICalendarSetupService;
import com.vsoftcorp.kls.service.ICropMasterService;
import com.vsoftcorp.kls.service.ICropTypeMasterService;
import com.vsoftcorp.kls.service.ICustomerService;
import com.vsoftcorp.kls.service.IDistrictService;
import com.vsoftcorp.kls.service.IDocumentService;
import com.vsoftcorp.kls.service.IInterestCategoryService;
import com.vsoftcorp.kls.service.IKccCardMappingService;
import com.vsoftcorp.kls.service.ILandDetailService;
import com.vsoftcorp.kls.service.ILandTypeService;
import com.vsoftcorp.kls.service.ILoanProductDocumentMappingService;
import com.vsoftcorp.kls.service.ILoanProductPurposeMappingService;
import com.vsoftcorp.kls.service.IPacsGlMappingService;
import com.vsoftcorp.kls.service.IPacsGlService;
import com.vsoftcorp.kls.service.IPacsLoanApplicationDetailService;
import com.vsoftcorp.kls.service.IPacsService;
import com.vsoftcorp.kls.service.IProductService;
import com.vsoftcorp.kls.service.IProductTypeService;
import com.vsoftcorp.kls.service.IPurposeService;
import com.vsoftcorp.kls.service.IRecoveryOrderService;
import com.vsoftcorp.kls.service.ISanctionedComponentService;
import com.vsoftcorp.kls.service.IScaleOfFinanceService;
import com.vsoftcorp.kls.service.ISchemeService;
import com.vsoftcorp.kls.service.ISeasonMasterService;
import com.vsoftcorp.kls.service.ISeasonService;
import com.vsoftcorp.kls.service.ISlabwiseInterestRateService;
import com.vsoftcorp.kls.service.ISubPurposeService;
import com.vsoftcorp.kls.service.ITalukaService;
import com.vsoftcorp.kls.service.IUnitService;
import com.vsoftcorp.kls.service.IVillageService;
import com.vsoftcorp.kls.service.account.IAccountPropertyService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.BorrowingProductGlMappingHelper;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.SeasonStatus;
import com.vsoftcorp.kls.valuetypes.transaction.RecoveryOrder;


@Path("/master")
public class KLSMasterRestService {

	private static final Logger logger = Logger.getLogger(KLSMasterRestService.class);

	public KLSMasterRestService() {

	}

	private static final KLSMasterRestService INSTANCE = new KLSMasterRestService();

	public static KLSMasterRestService getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 */
	@POST
	@Path("/district")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveDistrict(String districtJsonString) {

		logger.info("Start : Calling district master service in saveDistrict() method.");
		try {
			Gson gson = new GsonBuilder().create();
			DistrictData distData = gson.fromJson(districtJsonString, DistrictData.class);

			IDistrictService distService = KLSServiceFactory.getDistrictService();

			distService.saveDistrict(distData);
		} catch (Exception excp) {
			logger.error("Error while saving District Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling district master service in saveDistrict() method.");
		return "District Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/district")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateDistrict(String districtJsonString) {

		logger.info("Start : Calling district master service in updateDistrict() method.");
		try {
			Gson gson = new GsonBuilder().create();

			DistrictData distData = gson.fromJson(districtJsonString, DistrictData.class);

			IDistrictService distService = KLSServiceFactory.getDistrictService();

			distService.updateDistrict(distData);
		} catch (Exception excp) {
			logger.error("Error while updating District Data");
			return excp.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling district master service in updateDistrict() method.");
		return "District Data Updated Successfully!";
	}

	@POST
	@Path("/taluka")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveTaluka(String talukaFormData) {
		logger.info("Start : Calling KlsApplicationRestfulService.saveTaluka()");
		try {
			Gson gson = new GsonBuilder().create();
			TalukaData tMasterData = gson.fromJson(talukaFormData, TalukaData.class);

			ITalukaService talukaService = KLSServiceFactory.getTalukaService();
			talukaService.saveTaluka(tMasterData);
		} catch (Exception e) {
			logger.error("Error while saving  taluka in KlsApplicationRestfulService");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.saveTaluka()");
		return "Taluka saved successfully";
	}

	@PUT
	@Path("/taluka")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateTaluka(String talukaFormData) {

		logger.info("Start : Calling KlsApplicationRestfulService.saveTaluka()");
		try {
			Gson gson = new GsonBuilder().create();
			TalukaData tMasterData = gson.fromJson(talukaFormData, TalukaData.class);

			ITalukaService talukaService = KLSServiceFactory.getTalukaService();

			talukaService.updateTaluka(tMasterData);
		} catch (Exception e) {
			logger.error("Error while updating  taluka in KlsApplicationRestfulService");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.saveTaluka()");
		return "Taluka updated successfully";
	}

	@POST
	@Path("/cropType")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveCropType(String cropTypeData) {

		logger.info("Start : Calling KlsApplicationRestfulService.saveCropType()");
		CropTypeMasterData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(cropTypeData, CropTypeMasterData.class);
			logger.info("getting data in save" + data.getCropTypeName());
			ICropTypeMasterService service = KLSServiceFactory.getCropTypeMasterService();
			service.saveCropType(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End for Saving");
		return "Crop Type Saved Successfully!";
	}

	@PUT
	@Path("/cropType")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateCropType(String cropTypeData) {
		logger.info("In JSON Service for update");
		CropTypeMasterData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(cropTypeData, CropTypeMasterData.class);

			ICropTypeMasterService service = KLSServiceFactory.getCropTypeMasterService();
			service.updateCropType(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Updated Crop Type end");
		return "Crop Type Updated Successfully!";
	}

	@POST
	@Path("/village")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveVillage(String villageJsonString) {

		logger.info("Start : Calling village master service in saveVillage() method.");
		VillageData data = null;
		try {
			IVillageService villageMasterService = KLSServiceFactory.getVillageMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(villageJsonString, VillageData.class);
			villageMasterService.saveVillage(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling village master service in saveVillage() method.");
		return "Village Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/village")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateVillage(String villageJsonString) {

		logger.info("Start : Calling village master service in updateVillage() method.");
		VillageData data = null;
		try {
			IVillageService villageMasterService = KLSServiceFactory.getVillageMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(villageJsonString, VillageData.class);
			villageMasterService.updateVillage(data);
		} catch (Exception e) {
			logger.error("Error while updating Village Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling village master service in updateVillage() method.");
		return "Village Data Updated Successfully!";
	}

	@POST
	@Path("/crop")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveCrop(String cropData) {
		CropMasterData data = null;
		logger.info("Start : Calling KlsApplicationRestfulService.saveCrop");
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(cropData, CropMasterData.class);
			logger.info("getting data in save" + data.getName());
			ICropMasterService service = KLSServiceFactory.getCropMasterService();
			service.saveCrop(data);
		} catch (Exception e) {
			logger.error("Error while saving  Crop ");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End for Saving");
		return "Crop Saved Successfully!";
	}

	@PUT
	@Path("/crop")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateCrop(String cropData) {
		logger.info("In JSON Service for update");
		CropMasterData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(cropData, CropMasterData.class);

			ICropMasterService service = KLSServiceFactory.getCropMasterService();
			service.updateCrop(data);
		} catch (Exception e) {
			logger.error("Error while updating Crop");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Updated Crop Type end");
		return "Crop Updated Successfully!";
	}

	@GET
	@Path("/taluka")
	@Produces("application/json")
	public String getAllTalukas() {
		logger.info("Start: Calling KlsApplicationRestfulService.getAllTalukas()");
		String talukasJson = "";
		List<TalukaData> talukas = new ArrayList<TalukaData>();
		try {
			Gson gson = new GsonBuilder().create();

			ITalukaService tService = KLSServiceFactory.getTalukaService();

			talukas = tService.getAllTalukas();

			talukasJson = gson.toJson(talukas);

		} catch (Exception e) {
			logger.error("Error while retriving all talukas");
			return "exception in get all talukas";
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Calling KlsApplicationRestfulService.getAllTalukas()");
		return talukasJson;
	}

	@GET
	@Path("/district")
	@Produces("application/json")
	public String getAllDistricts() {

		logger.info("Start : Calling district master service in getAllDistricts() method.");
		List<DistrictData> districtDataList = new ArrayList<DistrictData>();
		String jsonAllDistrictsString = "";
		try {
			IDistrictService districtService = KLSServiceFactory.getDistrictService();

			districtDataList = districtService.getAllDistricts();
			Gson gson = new GsonBuilder().create();
			jsonAllDistrictsString = gson.toJson(districtDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling district master service in getAllDistricts() method.");
		return jsonAllDistrictsString;
	}

	@GET
	@Path("/village")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllVillages(@QueryParam("talukaId") String talukaId) {

		logger.info("Start : Calling village master service in getAllVillages() method.");
		List<VillageData> villageMasterDataList = new ArrayList<VillageData>();
		String jsonAllVillagesString = "";
		try {
			IVillageService villageMasterService = KLSServiceFactory.getVillageMasterService();
			if (talukaId != null && talukaId != "")
				villageMasterDataList = villageMasterService.getAllVillagesByTaluka(Integer.parseInt(talukaId));
			else
				villageMasterDataList = villageMasterService.getAllVillages();
			Gson gson = new GsonBuilder().create();
			jsonAllVillagesString = gson.toJson(villageMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling village master service in getAllVillages() method.");
		return jsonAllVillagesString;
	}

	@GET
	@Path("/village/details")
	@Consumes("*/*")
	@Produces("application/json")
	public String getVillageDetails(@QueryParam("villageId") String villageId) {

		logger.info("Start : Calling village details service in getVillageDetails() method.");
		VillageDetailsData villageDetailsData = new VillageDetailsData();
		String jsonVillageDetailString = "";
		try {
			IVillageService villageMasterService = KLSServiceFactory.getVillageMasterService();
			villageDetailsData = villageMasterService.getVillageDetailsById(Integer.parseInt(villageId));
			Gson gson = new GsonBuilder().create();
			jsonVillageDetailString = gson.toJson(villageDetailsData);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling village details service in getVillageDetails() method.");
		return jsonVillageDetailString;
	}

	@GET
	@Path("/crop")
	@Produces("application/json")
	public String getAllCrops() {

		logger.info("Start : Calling KlsApplicationRestfulService.getAllCrops() ");
		List<CropMasterData> cropMasterDataList = new ArrayList<CropMasterData>();
		String jsonAllCrops = "";
		try {
			ICropMasterService cropMasterService = KLSServiceFactory.getCropMasterService();
			cropMasterDataList = cropMasterService.getAllCrops();

			Gson gson = new GsonBuilder().create();
			jsonAllCrops = gson.toJson(cropMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.getAllCrops() ");
		logger.info("jsonAllCrops : " + jsonAllCrops);
		return jsonAllCrops;
	}

	@GET
	@Path("/cropType")
	@Produces("application/json")
	public String getAllCropTypes() {

		logger.info("Start : Calling KlsApplicationRestfulService.getAllCropTypes() ");
		List<CropTypeMasterData> cropTypeMasterDataList = new ArrayList<CropTypeMasterData>();
		String jsonAllCropTypes = "";
		try {
			ICropTypeMasterService cropTypeMasterService = KLSServiceFactory.getCropTypeMasterService();
			cropTypeMasterDataList = cropTypeMasterService.getAllCropTypes();

			Gson gson = new GsonBuilder().create();
			jsonAllCropTypes = gson.toJson(cropTypeMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.getAllCropTypes() ");
		return jsonAllCropTypes;
	}

	@POST
	@Path("/pacs")
	@Consumes("application/json")
	@Produces("application/text")
	public String savePacs(String pacsFormData) {
		logger.info("Start : Calling KlsApplicationRestfulService.savePacs()");
		try {
			Gson gson = new GsonBuilder().create();
			PacsData pMasterData = gson.fromJson(pacsFormData, PacsData.class);
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
			// TODO remove hard coded values after implementing bank and branch
			pacsMasterService.savePacs(pMasterData);
		} catch (Exception e) {
			logger.error("Error while saving  pacs in KlsApplicationRestfulService");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.savePacs()");
		return "Pacs saved successfully";
	}

	@PUT
	@Path("/pacs")
	@Consumes("application/json")
	@Produces("application/text")
	public String updatePacs(String pacsFormData) {

		logger.info("Start : Calling KlsApplicationRestfulService.updatePacs()");
		try {
			Gson gson = new GsonBuilder().create();
			PacsData pMasterData = gson.fromJson(pacsFormData, PacsData.class);
			// pMasterData.setBankCode("001");
			// pMasterData.setBranchCode("0011");
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
			pacsMasterService.updatePacs(pMasterData);
		} catch (Exception e) {
			logger.error("Error while updating  pacs in KlsApplicationRestfulService");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.updatePacs()");
		return "PACS updated successfully";
	}

	@GET
	@Path("/pacs")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllPacs(@QueryParam("branchId") String branchId) {

		logger.info("Start : Calling KlsApplicationRestfulService.getAllPacs() ");
		List<PacsData> pacsMasterDataList = new ArrayList<PacsData>();
		String jsonAllPacs = "";
		try {
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
			if (branchId != null && branchId != "")
				pacsMasterDataList = pacsMasterService.getAllPacsByBranch(Integer.parseInt(branchId));
			else
				pacsMasterDataList = pacsMasterService.getAllPacs();

			Gson gson = new GsonBuilder().create();
			jsonAllPacs = gson.toJson(pacsMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.getAllPacs() ");
		return jsonAllPacs;
	}
	
	@GET
	@Path("/pacs/suvikas")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllPacsForPacsSuvikas() {

		logger.info("Start : Calling KlsApplicationRestfulService.getAllPacsForPacsSuvikas() ");
		List<PacsData> pacsMasterDataList = new ArrayList<PacsData>();
		String jsonAllPacs = "";
		try {
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
				pacsMasterDataList = pacsMasterService.getAllPacs();
			Gson gson = new GsonBuilder().create();
			jsonAllPacs = gson.toJson(pacsMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.getAllPacsForPacsSuvikas() ");
		return jsonAllPacs;
	}

	@GET
	@Path("/pacs/byid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsById(@QueryParam("pacsId") String pacsId) {

		logger.info("Start : Calling KlsApplicationRestfulService.getPacsById() ");
		PacsData pacsMasterData = new PacsData();
		String jsonAllPacs = "";
		try {
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
			pacsMasterData = pacsMasterService.getPacsByPacId(Integer.parseInt(pacsId));
			Gson gson = new GsonBuilder().create();
			jsonAllPacs = gson.toJson(pacsMasterData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.getPacsById() ");
		return jsonAllPacs;
	}

	@POST
	@Path("/branch")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveBranch(String branchJsonString) {

		logger.info("Start : Calling branch master service in saveBranch() method.");
		BranchData data = null;
		try {
			IBranchService branchMasterService = KLSServiceFactory.getBranchMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(branchJsonString, BranchData.class);
			branchMasterService.saveBranch(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling branch master service in saveBranch() method.");
		return "Branch Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/branch")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateBranch(String branchJsonString) {

		logger.info("Start : Calling branch master service in updateBranch() method.");
		BranchData data = null;
		try {
			IBranchService branchMasterService = KLSServiceFactory.getBranchMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(branchJsonString, BranchData.class);
			branchMasterService.updateBranch(data);
		} catch (Exception e) {
			logger.error("Error while updating Branch Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling branch master service in updateBranch() method.");
		return "Branch Data Updated Successfully!";
	}

	@GET
	@Path("/branch")
	@Produces("application/json")
	public String getAllBranches() {

		logger.info("Start : Calling branch master service in getAllBranches() method.");
		List<BranchData> branchMasterDataList = new ArrayList<BranchData>();
		String jsonAllBranchesString = "";
		try {
			IBranchService branchMasterService = KLSServiceFactory.getBranchMasterService();
			branchMasterDataList = branchMasterService.getAllBranches();
			Gson gson = new GsonBuilder().create();
			jsonAllBranchesString = gson.toJson(branchMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling branch master service in getAllBranches() method.");
		return jsonAllBranchesString;
	}

	@POST
	@Path("/season")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveSeason(String seasonJsonString) {

		logger.info("Start : Calling branch master service in saveBranch() method.");
		SeasonMasterData data = null;
		try {
			ISeasonMasterService seasonMasterService = KLSServiceFactory.getSeasonMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(seasonJsonString, SeasonMasterData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			/*
			 * data.setSeasonCode(MasterHelper.generateUniqueMasterCode(
			 * seasonMasterService.getAllSeasons(), data.getSeasonName()));
			 */
			seasonMasterService.saveSeason(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling branch master service in saveSeason() method.");
		return "Season Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/season")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateSeason(String seasonJsonString) {

		logger.info("Start : Calling Season master service in updateSeason() method.");
		SeasonMasterData data = null;
		try {
			ISeasonMasterService seasonMasterService = KLSServiceFactory.getSeasonMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(seasonJsonString, SeasonMasterData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			seasonMasterService.updateSeason(data);
		} catch (Exception e) {
			logger.error("Error while updating Season Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Calling Season master service in updateSeason() method.");
		return "Season Data Updated Successfully!";
	}

	@POST
	@Path("/seasonYr")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveSeasonYear(String seasonJsonString) {

		logger.info("Start : Calling branch master service in saveSeasonYear() method.");
		SeasonData data = null;
		try {
			ISeasonService seasonService = KLSServiceFactory.getSeasonService();
			Gson gson = new Gson();
			data = gson.fromJson(seasonJsonString, SeasonData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			/*
			 * data.setSeasonCode(MasterHelper.generateUniqueMasterCode(
			 * seasonMasterService.getAllSeasons(), data.getSeasonName()));
			 */
			data.setProcessStatus(SeasonStatus.NEW.getValue());
			seasonService.saveSeason(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling branch master service in saveSeasonYear() method.");
		return "Season Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/seasonYr")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateSeasonYear(String seasonJsonString) {

		logger.info("Start : Calling Season master service in updateSeasonYear() method.");
		SeasonData data = null;
		try {
			ISeasonService seasonService = KLSServiceFactory.getSeasonService();
			Gson gson = new Gson();
			data = gson.fromJson(seasonJsonString, SeasonData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			seasonService.updateSeason(data);
		} catch (Exception e) {
			logger.error("Error while updating Season Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Calling Season master service in updateSeasonYear() method.");
		return "Season Data Updated Successfully!";
	}

	@GET
	@Path("/season")
	@Produces("application/json")
	public String getAllSeasons() {

		logger.info("Start : Calling Season master service in getAllSeasons() method.");
		List<SeasonMasterData> seasonMasterDataList = new ArrayList<SeasonMasterData>();
		String jsonAllSeasonsString = "";
		try {
			ISeasonMasterService seasonMasterService = KLSServiceFactory.getSeasonMasterService();
			seasonMasterDataList = seasonMasterService.getAllSeasons();
			Gson gson = new GsonBuilder().create();
			jsonAllSeasonsString = gson.toJson(seasonMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling season master service in getAllSeasons() method.");
		return jsonAllSeasonsString;
	}

	@GET
	@Path("/seasonYr")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllSeasonYears(@QueryParam("seasonYear") String seasonYear) {

		logger.info("Start : Calling Season master service in getAllSeasonYrs() method.");
		List<SeasonData> seasonDataList = new ArrayList<SeasonData>();
		String jsonAllSeasonsString = "";
		try {
			ISeasonService seasonService = KLSServiceFactory.getSeasonService();
			if (seasonYear != null && seasonYear != "")
				seasonDataList = seasonService.getAllSeasonsBySeasonYr(seasonYear);
			else
				seasonDataList = seasonService.getAllSeasons();
			Gson gson = new GsonBuilder().create();
			jsonAllSeasonsString = gson.toJson(seasonDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling season master service in getAllSeasonYrs() method.");
		return jsonAllSeasonsString;
	}
	
	@GET
	@Path("/activeseasons")
	@Consumes("*/*")
	@Produces("application/json")
	public String getActiveSeasons(@QueryParam("businessDate") String businessDate) {

		logger.info("Start : Calling Season master service in getAllSeasonYrs() method.");
		List<SeasonData> seasonDataList = new ArrayList<SeasonData>();
		String jsonAllSeasonsString = "";
		try {
			ISeasonService seasonService = KLSServiceFactory.getSeasonService();
			if (businessDate != null && businessDate != "")
				seasonDataList = seasonService.getActiveSeasons(businessDate);
			
			Gson gson = new GsonBuilder().create();
			jsonAllSeasonsString = gson.toJson(seasonDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling season master service in getAllSeasonYrs() method.");
		return jsonAllSeasonsString;
	}


	@POST
	@Path("/landType")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveLandType(String landTypeJsonString) {

		logger.info("Start : Calling land type master service in saveLandType() method.");
		LandTypeData data = null;
		try {
			ILandTypeService landTypeService = KLSServiceFactory.getLandTypeService();
			Gson gson = new Gson();
			data = gson.fromJson(landTypeJsonString, LandTypeData.class);
			/*
			 * data.setLandTypeCode(MasterHelper.generateUniqueMasterCode(
			 * landTypeMasterService.getAllLandTypes(),
			 * data.getLandTypeName()));
			 */
			landTypeService.saveLandType(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling land type master service in saveLandType() method.");
		return "Land Type Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/landType")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateLandType(String landTypeJsonString) {

		logger.info("Start : Calling land type master service in updateLandType() method.");
		LandTypeData data = null;
		try {
			ILandTypeService landTypeService = KLSServiceFactory.getLandTypeService();
			Gson gson = new Gson();
			data = gson.fromJson(landTypeJsonString, LandTypeData.class);
			landTypeService.updateLandType(data);
		} catch (Exception e) {
			logger.error("Error while updating land type Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling land type master service in updateLandType() method.");
		return "Land Type Data Updated Successfully!";
	}

	@GET
	@Path("/landType")
	@Produces("application/json")
	public String getAllLandTypes() {

		logger.info("Start : Calling land type service in getAllLandTypes() method.");
		List<LandTypeData> landTypeDataList = new ArrayList<LandTypeData>();
		String jsonAllLandTypesString = "";
		try {
			ILandTypeService landTypeService = KLSServiceFactory.getLandTypeService();
			landTypeDataList = landTypeService.getAllLandTypes();
			Gson gson = new GsonBuilder().create();
			jsonAllLandTypesString = gson.toJson(landTypeDataList);
		} catch (Exception e) {
			logger.error("Error while fetching land types");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling land type service in getAllLandTypes() method.");
		return jsonAllLandTypesString;
	}

	@POST
	@Path("/scaleOfFinance")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveScaleOfFinance(String scaleOfFinJsonString) {

		logger.info("Start : Calling scale of finance master service in saveScaleOfFinance() method.");
		ScaleOfFinanceData data = null;
		try {
			IScaleOfFinanceService scaleOfFinMasterService = KLSServiceFactory.getScaleOfFinanceService();
			Gson gson = new Gson();
			data = gson.fromJson(scaleOfFinJsonString, ScaleOfFinanceData.class);
			// data.setBankCode("001");
			scaleOfFinMasterService.saveScaleOfFinance(data);
		} catch (Exception e) {
			logger.error("Error while saving scale of finance data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling scale of finance master service in saveScaleOfFinance() method.");
		return "Scale Of Finance Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/scaleOfFinance")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateScaleOfFinance(String scaleOfFinJsonString) {

		logger.info("Start : Calling scale of finance master service in updateScaleOfFinance() method.");
		ScaleOfFinanceData data = null;
		try {
			IScaleOfFinanceService scaleOfFinMasterService = KLSServiceFactory.getScaleOfFinanceService();
			Gson gson = new Gson();
			data = gson.fromJson(scaleOfFinJsonString, ScaleOfFinanceData.class);
			// data.setBankCode("001");
			scaleOfFinMasterService.updateScaleOfFinance(data);
		} catch (Exception e) {
			logger.error("Error while updating scale of finance data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling scale of finance master service in updateScaleOfFinance() method.");
		return "Scale Of Finance Data Updated Successfully!";
	}

	@GET
	@Path("/scaleOfFinance")
	@Consumes("*/*")
	@Produces("application/json")
	public String getScaleOfFinance(@QueryParam("areaType") String areaType, @QueryParam("areaTypeCode") String areaTypeCode,
			@QueryParam("seasonId") String seasonId, @QueryParam("landTypeId") String landTypeId, @QueryParam("cropId") String cropId) {

		logger.info("Start : Calling scale of finance master service in getAllLandTypes() method.");
		ScaleOfFinanceData financeMasterData = new ScaleOfFinanceData();
		String jsonAllScaleOfFinancesString = "";
		try {
			IScaleOfFinanceService scaleOfFinMasterService = KLSServiceFactory.getScaleOfFinanceService();
			if (areaType != null && areaType != "" && areaTypeCode != null && areaTypeCode != "" && seasonId != null && seasonId != ""
					&& landTypeId != null && landTypeId != "" && cropId != null && cropId != "") {
				financeMasterData = scaleOfFinMasterService.getScaleOfFinance(areaType, Integer.parseInt(areaTypeCode), Long.parseLong(seasonId),
						Integer.parseInt(landTypeId), Integer.parseInt(cropId));
				Gson gson = new GsonBuilder().create();
				if (financeMasterData != null)
					jsonAllScaleOfFinancesString = gson.toJson(financeMasterData);
			}
		} catch (Exception e) {
			logger.error("Error while fetching all scale of finance data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling scale of finance master service in getAllLandTypes() method.");
		return jsonAllScaleOfFinancesString;
	}

	@POST
	@Path("/pacsGl")
	@Consumes("application/json")
	@Produces("application/text")
	public String savePacsGl(String gLJsonString) {

		logger.info("Start : Calling GL master service in savePacsGl() method.");
		PacsGlData data = null;
		try {
			IPacsGlService pacsGlService = KLSServiceFactory.getPacsGlService();
			Gson gson = new Gson();
			data = gson.fromJson(gLJsonString, PacsGlData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			// data.setPacsId("1");
			// data.setBranchCode("0001");
			// data.setEnteredBy("kls");
			// data.setEnteredDate(new Date(System.currentTimeMillis()));
			pacsGlService.savePacsGl(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling GL master service in savePacsGl() method.");
		return "GL Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/pacsGl")
	@Consumes("application/json")
	@Produces("application/text")
	public String updatePacsGl(String gLJsonString) {

		logger.info("Start : Calling GL master service in updatePacsGl() method.");
		PacsGlData data = null;
		try {
			IPacsGlService pacsGlService = KLSServiceFactory.getPacsGlService();
			Gson gson = new Gson();
			data = gson.fromJson(gLJsonString, PacsGlData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			// data.setPacsCode("PAC01");
			// data.setBranchCode("0001");
			// data.setEnteredBy("kls");
			// data.setEnteredDate(new Date(System.currentTimeMillis()));
			pacsGlService.updatePacsGl(data);
		} catch (Exception e) {
			logger.error("Error while updating GL Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Calling GL master service in updatePacsGl() method.");
		return "GL Data Updated Successfully!";
	}

	@GET
	@Path("/pacsGl")
	@Produces("application/json")
	public String getAllPacsGls() {

		logger.info("Start : Calling GL  master service in getAllPacsGls() method.");
		List<PacsGlData> gLMasterDataList = new ArrayList<PacsGlData>();
		String jsonAllGLString = "";
		try {
			IPacsGlService pacsGlService = KLSServiceFactory.getPacsGlService();

			gLMasterDataList = pacsGlService.getAllPacsGls();
			Gson gson = new GsonBuilder().create();
			jsonAllGLString = gson.toJson(gLMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling GL master service in getAllPacsGls() method.");
		return jsonAllGLString;
	}

	@GET
	@Path("/intrCategory")
	@Produces("application/json")
	public String getAllInterestCategories() {

		logger.info("Start : Calling Interest Category master service in getAllInterestCategories() method.");
		List<InterestCategoryData> interestCategoryMasterDataList = new ArrayList<InterestCategoryData>();
		String jsonAllIntrCategoryString = "";
		try {
			IInterestCategoryService interestCategoryMasterService = KLSServiceFactory.getInterestCategoryMasterService();
			interestCategoryMasterDataList = interestCategoryMasterService.getAllInterestCategories();
			Gson gson = new GsonBuilder().create();
			jsonAllIntrCategoryString = gson.toJson(interestCategoryMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Interest Category master service in getAllInterestCategories() method.");
		return jsonAllIntrCategoryString;
	}

	@POST
	@Path("/productType")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveProductType(String productTypeJsonString) {

		logger.info("Start : Calling product type master service in saveProductType() method.");
		ProductTypeData data = null;
		try {
			IProductTypeService prodTypeService = KLSServiceFactory.getProductTypeService();
			Gson gson = new Gson();
			data = gson.fromJson(productTypeJsonString, ProductTypeData.class);

			prodTypeService.saveProductType(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling product type master service in saveProductType() method.");
		return "Product Type Data Saved Successfully!";
	}

	/**
	 * 
	 */
	@PUT
	@Path("/productType")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateProductType(String productTypeJsonString) {

		logger.info("Start : Calling product type master service in updateProductType() method.");
		ProductTypeData data = null;
		try {
			IProductTypeService prodTypeService = KLSServiceFactory.getProductTypeService();
			Gson gson = new Gson();
			data = gson.fromJson(productTypeJsonString, ProductTypeData.class);
			prodTypeService.updateProductType(data);
		} catch (Exception e) {
			logger.error("Error while updating product type Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling product type master service in updateProductType() method.");
		return "Product Type Data Updated Successfully!";
	}

	@GET
	@Path("/productType")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllProductTypes(@QueryParam("loanType") String loanType) {

		logger.info("Start : Calling ProductType  service in getAllProductTypes() method.");
		List<ProductTypeData> productTypeDataList = new ArrayList<ProductTypeData>();
		String jsonAllProductTypeString = "";
		try {
			IProductTypeService productTypeService = KLSServiceFactory.getProductTypeService();
			productTypeDataList = productTypeService.getAllProductTypes(loanType);
			Gson gson = new GsonBuilder().create();
			jsonAllProductTypeString = gson.toJson(productTypeDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling ProductType  service in getAllProductTypes() method.");
		return jsonAllProductTypeString;
	}

	@POST
	@Path("/slabwiseInterest")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveSlabwiseInterest(String slabwiseInterestJsonString) {
		System.out.println("slabwise:" + slabwiseInterestJsonString);
		logger.info("Start : Calling slab wise interest master service in saveSlabwiseInterest() method.");
		SlabwiseInterestRateData data = null;
		try {
			ISlabwiseInterestRateService slabwiseIntMasterService = KLSServiceFactory.getSlabwiseInterestRateService();
			Gson gson = new Gson();
			data = gson.fromJson(slabwiseInterestJsonString, SlabwiseInterestRateData.class);
			slabwiseIntMasterService.saveSlabwiseInterestRate(data);
		} catch (Exception e) {
			logger.error("Error while saving slab wise interest data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling slab wise interest master service in saveSlabwiseInterest() method.");
		return "Slabwise Interest Data Saved Successfully!";
	}

	@GET
	@Path("/slabwiseInterest")
	@Produces("application/json")
	public String getAllSlabwiseInterestRecords() {

		logger.info("Start : Calling slab wise interest master service in getAllSlabwiseInterestRecords() method.");
		List<SlabwiseInterestRateData> slabwiseIntMasterDataList = new ArrayList<SlabwiseInterestRateData>();
		String jsonAllSlabwiseInterestString = "";
		try {
			ISlabwiseInterestRateService slabwiseIntMasterService = KLSServiceFactory.getSlabwiseInterestRateService();
			slabwiseIntMasterDataList = slabwiseIntMasterService.getAllSlabwiseInterestRateRecords();
			Gson gson = new GsonBuilder().create();
			jsonAllSlabwiseInterestString = gson.toJson(slabwiseIntMasterDataList);
		} catch (Exception e) {
			logger.error("Error while fetching all slab wise interest data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling slab wise interest master service in getAllSlabwiseInterestRecords() method.");
		return jsonAllSlabwiseInterestString;
	}

	@POST
	@Path("/product")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveProduct(String productJsonString) {

		logger.info("Start : Calling branch master service in saveBranch() method.");
		ProductData data = null;
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			Gson gson = new Gson();
			data = gson.fromJson(productJsonString, ProductData.class);
			// TODO remove hard coded values after implementing bank and branch
			// data.setBankCode("001");
			// data.setPacsCode("PAC01");
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
	@Path("/product")
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
	@Path("/product")
	@Produces("application/json")
	public String getAllProducts(@QueryParam("productTypeId") Integer productTypeId) {

		logger.info("Start : Calling Product master service in getAllProducts() method.");
		logger.info("productTypeId : " + productTypeId);

		List<ProductData> productMasterDataList = new ArrayList<ProductData>();
		String jsonAllProductsString = "";
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			if (productTypeId != null)
				productMasterDataList = productMasterService.getAllProducts(productTypeId);
			else
				productMasterDataList = productMasterService.getAllProducts();
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
	@Path("/productsbytype")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllProductsBasedOnProductType(@QueryParam("productType") String productType) {

		logger.info("Start : Calling Product master service in getAllProducts() method.");
		List<ProductData> productMasterDataList = new ArrayList<ProductData>();
		String jsonAllProductsString = "";
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			productMasterDataList = productMasterService.getAllProductsBasedOnLoanType(productType);
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
	@Path("/product/getProductById")
	@Consumes("*/*")
	@Produces("application/json")
	public String getProductBasedOnId(@QueryParam("productId") Integer productId) {

		logger.info("Start : Calling Product master service in getProductBasedOnId() method.");
		ProductData productMasterData = new ProductData();
		String jsonAllProductsString = "";
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			productMasterData = productMasterService.getProductBasedOnId(productId);
			Gson gson = new GsonBuilder().create();
			jsonAllProductsString = gson.toJson(productMasterData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling product master service in getProductBasedOnId() method.");
		return jsonAllProductsString;
	}

	@GET
	@Path("/product/getProductByLoanType")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllProductsBasedOnLoanType(@QueryParam("loanType") String loanType) {

		logger.info("Start : Calling Product master service in getAllProductsBasedOnLoanType() method.");
		List<ProductData> productMasterDataList = new ArrayList<ProductData>();
		String jsonAllProductsString = "";
		try {
			IProductService productMasterService = KLSServiceFactory.getProductMasterService();
			productMasterDataList = productMasterService.getAllProductsBasedOnLoanType(loanType);
			Gson gson = new GsonBuilder().create();
			jsonAllProductsString = gson.toJson(productMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling product master service in getAllProductsBasedOnLoanType() method.");
		return jsonAllProductsString;
	}

	@GET
	@Path("/customer")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAllCustomersByName(@QueryParam("name") String name) {
		logger.info("Start : Calling Customer service in getAllCustomersByName() method.");
		List<CustomerData> customerDataList = new ArrayList<CustomerData>();
		String jsonAllProductsString = "";
		try {
			ICustomerService customerService = KLSServiceFactory.getCustomerService();
			customerDataList = customerService.getAllCustomersByName(name);
			jsonAllProductsString = new GsonBuilder().create().toJson(customerDataList);
		} catch (Exception e) {
			logger.error("Error: Error while retrieving customer data  in getAllCustomersByName() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Customer service in getAllCustomersByName() method.");
		return jsonAllProductsString;
	}

	@POST
	@Path("/landDetail")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveLandDetail(String landDetailJsonString) {
		logger.info("Start : Calling LandDetailService in saveLandDetail() method." + landDetailJsonString);
		LandDetailData landDetailData = null;
		try {
			ILandDetailService landDetailService = KLSServiceFactory.getLandDetailService();

			Gson gson = new Gson();
			landDetailData = gson.fromJson(landDetailJsonString, LandDetailData.class);
			landDetailService.saveLandDetails(landDetailData);
		} catch (Exception e) {
			logger.error("Error while saving land details saveLandDetails() method ");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LandDetailService  in saveLandDetails() method.");
		return "Land Details Data Saved Successfully!";
	}

	@GET
	@Path("/landDetail")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLandDetailsByCustomerId(@QueryParam("customerId") String customerId) {

		logger.info("Start : Calling LandDetails  service in getLandDetailsByCustomerId() method.");
		LandDetailData data = null;
		String jsonAllLandDetailString = "";
		try {
			ILandDetailService landDetailService = KLSServiceFactory.getLandDetailService();
			if (customerId != null)
				data = landDetailService.getLandDetailsByCustomerId(Long.parseLong(customerId));
			Gson gson = new GsonBuilder().create();
			jsonAllLandDetailString = gson.toJson(data);
		} catch (Exception e) {
			logger.error("Error: Error while retrieving LandDetails data  in getLandDetailsByCustomerId() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling  LandDetails service in getLandDetailsByCustomerId() method.");
		return jsonAllLandDetailString;
	}

	@GET
	@Path("/getTotallandDetailbycustid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLandDetailsByCustIdAndLandType(@QueryParam("customerId") Long customerId,@QueryParam("landTypeId") Integer landTypeId) {

		logger.info("Start : Calling LandDetails  service in getLandDetailsByCustomerId() method.");
		Double totalLand = null;
		String jsonAllLandDetailString = "";
		try {
			ILandDetailService landDetailService = KLSServiceFactory.getLandDetailService();
			if (customerId != null)
				totalLand = landDetailService.getTotalLandDetailsByCustomerId(customerId, landTypeId);
			Gson gson = new GsonBuilder().create();
			jsonAllLandDetailString = gson.toJson(totalLand);
		} catch (Exception e) {
			logger.error("Error: Error while retrieving LandDetails data  in getLandDetailsByCustomerId() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling  LandDetails service in getLandDetailsByCustomerId() method.");
		return jsonAllLandDetailString;
	}
	
	@GET
	@Path("/getTotalavailablelandbycustid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getTotalAvailableLand(@QueryParam("customerId") Long customerId) {

		logger.info("Start : Calling LandDetails  service in getLandDetailsByCustomerId() method.");
		Double totalLand = null;
		String jsonAllLandDetailString = "";
		try {
			ILandDetailService landDetailService = KLSServiceFactory.getLandDetailService();
			if (customerId != null)
				totalLand = landDetailService.getTotalAvailableLandByCustId(customerId);
			Gson gson = new GsonBuilder().create();
			jsonAllLandDetailString = gson.toJson(totalLand);
		} catch (Exception e) {
			logger.error("Error: Error while retrieving LandDetails data  in getLandDetailsByCustomerId() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling  LandDetails service in getLandDetailsByCustomerId() method.");
		return jsonAllLandDetailString;
	}
	
	
	@GET
	@Path("/getcultivatedlandDetailbycustid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getCultivatedLandDetailsByCustId(@QueryParam("customerId") Long customerId) {

		logger.info("Start : Calling LandDetails  service in getLandDetailsByCustomerId() method.");
		Double totalLand = null;
		String jsonAllLandDetailString = "";
		try {
			ILandDetailService landDetailService = KLSServiceFactory.getLandDetailService();
			if (customerId != null)
				totalLand = landDetailService.getTotalCultivatedLandDetailsByCustomerId(customerId);
			Gson gson = new GsonBuilder().create();
			jsonAllLandDetailString = gson.toJson(totalLand);
		} catch (Exception e) {
			logger.error("Error: Error while retrieving LandDetails data  in getLandDetailsByCustomerId() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling  LandDetails service in getLandDetailsByCustomerId() method.");
		return jsonAllLandDetailString;
	}
	
	@GET
	@Path("/scheme")
	@Produces("application/json")
	public String getAllSchemes() {

		logger.info("Start : Calling Scheme master service in getAllProducts() method.");
		List<SchemeData> schemeMasterDataList = new ArrayList<SchemeData>();
		String jsonAllSchemesString = "";
		try {
			ISchemeService schemeMasterService = KLSServiceFactory.getSchemeMasterService();
			schemeMasterDataList = schemeMasterService.getAllSchemes();
			Gson gson = new GsonBuilder().create();
			jsonAllSchemesString = gson.toJson(schemeMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling scheme master service in getAllSchemes() method.");
		return jsonAllSchemesString;
	}

	// Added Post and Put for Scheme ..... added @a1565
	@POST
	@Path("/scheme")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveScheme(String schemeJsonString) {
		logger.info("Start : Calling  in  saveScheme() method ");
		SchemeData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(schemeJsonString, SchemeData.class);
			ISchemeService service = KLSServiceFactory.getSchemeMasterService();
			service.saveScheme(data);
		} catch (Exception e) {
			logger.error("Error while Saving Scheme");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving");
		return "Scheme Saved Successfully";
	}

	@PUT
	@Path("/scheme")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateScheme(String schemeJsonString) {
		logger.info("Start : Calling updateScheme() Method.");
		SchemeData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(schemeJsonString, SchemeData.class);
			ISchemeService service = KLSServiceFactory.getSchemeMasterService();
			service.updateScheme(data);
		} catch (Exception e) {
			logger.error("Error while updating Scheme ");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Updating");
		return "Scheme Updated Successfully ";

	}

	@POST
	@Path("/bankParameter")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveBankParameter(String bankParameterJsonString) {

		logger.info("Start : Calling bank parameter service in saveBankParameter() method.");
		BankParameterData data = null;
		try {
			IBankParameterService bankParameterService = KLSServiceFactory.getBankParameterService();
			Gson gson = new Gson();
			data = gson.fromJson(bankParameterJsonString, BankParameterData.class);
			bankParameterService.saveBankParameter(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling bank parameter service in saveBankParameter() method.");
		return "Bank Parameter Data Saved Successfully!";
	}

	@PUT
	@Path("/bankParameter")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateBankParameter(String bankParameterJsonString) {

		logger.info("Start : Calling bank parameter service in updateBankParameter() method.");
		BankParameterData data = null;
		try {
			IBankParameterService bankParameterService = KLSServiceFactory.getBankParameterService();
			Gson gson = new Gson();
			data = gson.fromJson(bankParameterJsonString, BankParameterData.class);
			bankParameterService.updateBankParameter(data);
		} catch (Exception e) {
			logger.error("Error while updating Bank Parameter Data");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling bank parameter service in updateBankParameter() method.");
		return "Bank Parameter Data Updated Successfully!";
	}

	@GET
	@Path("/bankParameter")
	@Produces("application/json")
	public String getAllBankParameters() {

		logger.info("Start : Calling bank parameter service in getAllBankParameters() method.");
		List<BankParameterData> bankParameterDataList = new ArrayList<BankParameterData>();
		String jsonAllBankParamsString = "";
		try {
			IBankParameterService bankParameterService = KLSServiceFactory.getBankParameterService();
			bankParameterDataList = bankParameterService.getAllBankParameters();
			Gson gson = new GsonBuilder().create();
			jsonAllBankParamsString = gson.toJson(bankParameterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling bank parameter service in getAllBankParameters() method.");
		return jsonAllBankParamsString;
	}

	// Added Post and Put for Interest Category.....added by @a1565
	@POST
	@Path("/intrCategory")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveInterestCategory(String interestCategory) {
		logger.info("Start : Calling  in  saveInterestCategory() method");

		InterestCategoryData data = null;

		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(interestCategory, InterestCategoryData.class);
			IInterestCategoryService service = KLSServiceFactory.getInterestCategoryMasterService();
			service.saveInterestCategory(data);
		} catch (Exception e) {
			logger.error("Error while saving InterestCategory");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Saving");
		return "InterestCategory  Saved Successfully!";

	}

	@PUT
	@Path("/intrCategory")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateInterestCategory(String interestCategory) {

		logger.info("Start : Calling  updateInterestCategory() method");
		InterestCategoryData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(interestCategory, InterestCategoryData.class);
			IInterestCategoryService service = KLSServiceFactory.getInterestCategoryMasterService();
			service.updateInterestCategory(data);
		} catch (Exception e) {
			logger.error("Error while updating InterestCategory");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "InterestCategory  Updated Successfully!";

	}

	@POST
	@Path("/cropLang")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveCropLang(String cropData) {
		CropMasterData data = null;
		logger.info("Start : Calling KlsApplicationRestfulService.saveCropLang");
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(cropData, CropMasterData.class);
			logger.info("getting data in save" + data.getName());
			ICropMasterService service = KLSServiceFactory.getCropMasterService();
			data.setName(data.getName().getBytes("UTF-8").toString());
			service.saveCrop(data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while saving  saveCropLang ");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End for Saving");
		return "Crop Saved Successfully!";
	}

	@GET
	@Path("/recoveryOrder")
	@Consumes("*/*")
	@Produces("application/json")
	public String getRecoveryOrder(@QueryParam("checkValue") String checkValue) {

		logger.info("Start : getting RecoveryOrder from getRecoveryOrder() method.");
		List<EventTypeData> recSequenceDataLst = new ArrayList<>();
		List<RecoverySequenceData> recoverySequenceDataLst = new ArrayList<>();
		RecoverySequenceData recData = new RecoverySequenceData();
		RecoverySequenceData recData1 = new RecoverySequenceData();
		RecoverySequenceData recData2 = new RecoverySequenceData();
		RecoverySequenceData recData3 = new RecoverySequenceData();

		String jsonRecoveryOrderString = "";
		try {
			Gson gson = new GsonBuilder().create();
			if (checkValue.equals("type1")) {

				recData.setId(RecoveryOrder.PRINCIPAL.getValue());
				recData.setValue(RecoveryOrder.PRINCIPAL.toString());
				recData1.setId(RecoveryOrder.INTEREST.getValue());
				recData1.setValue(RecoveryOrder.INTEREST.toString());
				recData2.setId(RecoveryOrder.PENAL_INTEREST.getValue());
				recData2.setValue(RecoveryOrder.PENAL_INTEREST.toString());
				recData3.setId(RecoveryOrder.CHARGES.getValue());
				recData3.setValue(RecoveryOrder.CHARGES.toString());
				recoverySequenceDataLst.add(recData);
				recoverySequenceDataLst.add(recData1);
				recoverySequenceDataLst.add(recData2);
				recoverySequenceDataLst.add(recData3);
				jsonRecoveryOrderString = gson.toJson(recoverySequenceDataLst);
			} else {
				IRecoveryOrderService recoveryOrderService = KLSServiceFactory.getRecoveryOrderService();
				recSequenceDataLst = recoveryOrderService.getAllRecoverySequnces();
				jsonRecoveryOrderString = gson.toJson(recSequenceDataLst);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Successfully got RecoveryOrder using getRecoveryOrder() method.");
		return jsonRecoveryOrderString;
	}

	@POST
	@Path("/recoveryOrder")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveRecoverySequence(String recoverySeqJsonString) {

		logger.info("Start : Calling recovery sequence service in saveRecoverySequence() method.");
		EventTypeData data = null;
		try {
			IRecoveryOrderService recoveryOrderService = KLSServiceFactory.getRecoveryOrderService();
			Gson gson = new Gson();
			data = gson.fromJson(recoverySeqJsonString, EventTypeData.class);
			recoveryOrderService.saveRecoveryOrder(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling recovery sequence service in saveRecoverySequence() method.");
		return "Recovery Sequence Data Saved Successfully!";
	}

	@PUT
	@Path("/recoveryOrder")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateRecoverySequence(String recoverySequence) {

		logger.info("Start : Calling  updateRecoverySequence() method");
		EventTypeData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(recoverySequence, EventTypeData.class);
			IRecoveryOrderService service = KLSServiceFactory.getRecoveryOrderService();
			service.updateRecoverySequence(data);
		} catch (Exception e) {
			logger.error("Error while updating RecoverySequence");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "RecoverySequence  Updated Successfully!";

	}

	@GET
	@Path("/glSystem")
	@Consumes("*/*")
	@Produces("application/json")
	public String extractGLEntries(@QueryParam("effectiveDate") String effectiveDate) {

		logger.info("Start : generating gl text file in extractGLEntries() method.");
		String jsonRecoveryOrderString = "gl system file generated successfully.";
		logger.info(" effective date : " + effectiveDate);
		try {
			ILoansGLEntriesService service = KLSServiceFactory.getLoansGLEntriesService();
			service.extractGLEntries(effectiveDate);
		} catch (Exception e) {
			logger.error(" exception thrown ");
			jsonRecoveryOrderString = "Cannot generate gl system excel file.";
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : generating gl text file in extractGLEntries() method.");
		return jsonRecoveryOrderString;
	}

	@POST
	@Path("/sanctionedComponent")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveSanctionedComponent(String sanctionedComponentJsonString) {

		logger.info("Start : Calling Sanctioned Component service in saveSanctionedComponent() method.");
		SanctionedComponentData data = null;
		try {
			ISanctionedComponentService sanctionedComponentService = KLSServiceFactory.getSanctionedComponentService();
			Gson gson = new Gson();
			data = gson.fromJson(sanctionedComponentJsonString, SanctionedComponentData.class);
			sanctionedComponentService.saveSanctionedComponent(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Sanctioned Component service in saveSanctionedComponent() method.");
		return "Sanctioned Component Data Saved Successfully!";
	}

	@GET
	@Path("/sanctionedComponent")
	@Consumes("*/*")
	@Produces("application/json")
	public String getSanctionedComponentDetailsBySeasonId(@QueryParam("seasonId") String seasonId) {

		logger.info("Start : Calling Sanctioned Component Details  service in getSanctionedComponentDetailsBySeasonId() method.");
		SanctionedComponentData data = null;
		String jsonSanctionedComponentsDetailString = "";
		try {
			ISanctionedComponentService sanctionedComponentService = KLSServiceFactory.getSanctionedComponentService();
			if (seasonId != null)
				data = sanctionedComponentService.getSanctionedComponentsBySeasonId(Long.parseLong(seasonId));
			Gson gson = new GsonBuilder().create();
			jsonSanctionedComponentsDetailString = gson.toJson(data);
		} catch (Exception e) {
			logger.error("Error: Error while retrieving Sanctioned Component  Details data  in getSanctionedComponentDetailsBySeasonId() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling Sanctioned Component Details service in getSanctionedComponentDetailsBySeasonId() method.");
		return jsonSanctionedComponentsDetailString;
	}

	@POST
	@Path("/seasonParameter")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveSeasonParameter(String theSeasonParameterData) {

		logger.info("Start : Calling KlsApplicationRestfulService.saveSeasonParameter()");
		SeasonParameterData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(theSeasonParameterData, SeasonParameterData.class);
			logger.info("getting data in save" + theSeasonParameterData);
				KLSServiceFactory.getSeasonParameterService().saveSeasonParameter(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End for Saving");
		return "Crop Insurance Parameter Saved Successfully!";
	}

	@PUT
	@Path("/seasonParameter")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateSeasonParameter(String theSeasonParameterData) {
		logger.info("In JSON Service for update");
		SeasonParameterData data = null;
		String returnMsg="";
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(theSeasonParameterData, SeasonParameterData.class);
			returnMsg=KLSServiceFactory.getSeasonParameterService().updateSeasonParameter(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Updated Season Parameter  end");
		return returnMsg;
	}

	@GET
	@Path("/seasonParameter")
	@Consumes("*/*")
	@Produces("application/json")
	public String getSeasonParameter(@QueryParam("cropId") String cropID, @QueryParam("seasonId") String seasonId, @QueryParam("pacsId") String pacsId) {
		logger.info("Start : get Season Parameter in getSeasonParameter() method.");
		String seasonParameterJsonString = "";
		try {
			Gson gson = new GsonBuilder().create();
			if (cropID != null && seasonId != null && pacsId != null) {
				SeasonParameterData data = KLSServiceFactory.getSeasonParameterService().getSeasonParameter(Long.parseLong(seasonId),
						Integer.parseInt(cropID), Integer.parseInt(pacsId));
				seasonParameterJsonString = gson.toJson(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" Error : exception thrown ");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End :  get Season Parameter in getSeasonParameter() method.");
		return seasonParameterJsonString;
	}

	@PUT
	@Path("/purpose")
	@Consumes("application/json")
	@Produces("application/text")
	public String updatePurpose(String str) {

		logger.info("Start : Calling PurposeService in updatePurpose() method." + str);
		PurposeData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, PurposeData.class);
			IPurposeService service = KLSServiceFactory.getPurposeService();
			service.updatePurpose(data);
		} catch (Exception e) {
			logger.error("Error while updating Purpose");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "Purpose  Updated Successfully!";

	}

	@GET
	@Path("/purpose")
	@Produces("application/json")
	public String getAllPurpose() {

		logger.info("Start : Calling PurposeService in getPurposeList() method.");
		List<PurposeData> list = new ArrayList<PurposeData>();
		String jsonString = "";
		try {
			IPurposeService service = KLSServiceFactory.getPurposeService();
			list = service.getAllPurpose();
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling PurposeService in getPurposeList() method..gg.???" + jsonString);
		return jsonString;
	}

	@POST
	@Path("/purpose")
	@Consumes("application/json")
	@Produces("application/text")
	public String savePurpose(String str) {
		logger.info("Start : Calling PurposeService in savePurpose() method." + str);
		PurposeData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, PurposeData.class);
			IPurposeService service = KLSServiceFactory.getPurposeService();
			service.savePurpose(data);
		} catch (Exception e) {
			logger.info("End : Calling Purpose service in update() method.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving");
		return "Purpose Saved Successfully";
	}

	@POST
	@Path("/subpurpose")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveSubPurpose(String str) {
		logger.info("Start : Calling SubPurposeService in saveSubPurpose() method." + str);
		SubPurposeData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, SubPurposeData.class);
			ISubPurposeService service = KLSServiceFactory.getSubPurposeService();
			service.saveSubPurpose(data);
		} catch (Exception e) {
			logger.info("End : Calling SubPurpose service in update() method.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving");
		return "SubPurpose Saved Successfully";
	}

	@PUT
	@Path("/subpurpose")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateSubPurpose(String str) {

		logger.info("Start : Calling SubPurposeService in updateSubPurpose() method." + str);
		SubPurposeData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, SubPurposeData.class);
			ISubPurposeService service = KLSServiceFactory.getSubPurposeService();
			service.updateSubPurpose(data);
		} catch (Exception e) {
			logger.error("Error while updating SubPurpose");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "SubPurpose  Updated Successfully!";

	}

	@GET
	@Path("/subpurpose")
	@Produces("application/json")
	public String getAllSubPurpose() {

		logger.info("Start : Calling SubPurposeService in getSubPurposeList() method.");
		List<SubPurposeData> list = new ArrayList<SubPurposeData>();
		String jsonString = "";
		try {
			ISubPurposeService service = KLSServiceFactory.getSubPurposeService();
			list = service.getAllSubPurpose();
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling SubPurposeService in getSubPurposeList() method..gg.???" + jsonString);
		return jsonString;
	}

	@POST
	@Path("/activity")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveActivity(String str) {
		logger.info("Start : Calling ActivityService in saveActivity() method." + str);
		ActivityData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, ActivityData.class);
			IActivityService service = KLSServiceFactory.getActivityService();
			service.saveActivity(data);
		} catch (Exception e) {
			logger.info("End : Calling Activity service in update() method.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving");
		return "Activity Saved Successfully";
	}

	@PUT
	@Path("/activity")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateActivity(String str) {

		logger.info("Start : Calling ActivityService in updateActivity() method." + str);
		ActivityData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, ActivityData.class);
			IActivityService service = KLSServiceFactory.getActivityService();
			service.updateActivity(data);
		} catch (Exception e) {
			logger.error("Error while updating Activity");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "Activity  Updated Successfully!";

	}

	@GET
	@Path("/activity")
	@Produces("application/json")
	public String getAllActivity() {

		logger.info("Start : Calling ActivityService in getActivityList() method.");
		List<ActivityData> list = new ArrayList<ActivityData>();
		String jsonString = "";
		try {
			IActivityService service = KLSServiceFactory.getActivityService();
			list = service.getActivityList();
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling ActivityService in getActivityList() method..gg.???" + jsonString);
		return jsonString;
	}

	@POST
	@Path("/unit")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveUnit(String str) {
		logger.info("Start : Calling UnitService in saveUnit() method." + str);
		UnitData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, UnitData.class);
			IUnitService service = KLSServiceFactory.getUnitService();
			service.saveUnit(data);
		} catch (Exception e) {
			logger.info("End : Calling Unit service in update() method.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving");
		return "Unit Saved Successfully";
	}

	@PUT
	@Path("/unit")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateUnit(String str) {

		logger.info("Start : Calling UnitService in updateUnit() method." + str);
		UnitData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, UnitData.class);
			IUnitService service = KLSServiceFactory.getUnitService();
			service.updateUnit(data);
		} catch (Exception e) {
			logger.error("Error while updating Unit");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "Unit  Updated Successfully!";

	}

	@GET
	@Path("/unit")
	@Produces("application/json")
	public String getAllUnit() {

		logger.info("Start : Calling UnitService in getUnitList() method.");
		List<UnitData> list = new ArrayList<UnitData>();
		String jsonString = "";
		try {
			IUnitService service = KLSServiceFactory.getUnitService();
			list = service.getAllUnit();
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling UnitService in getUnitList() method..gg.???" + jsonString);
		return jsonString;
	}

	@PUT
	@Path("/document")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateDocument(String str) {

		logger.info("Start : Calling DocumentService in updateDocument() method." + str);
		DocumentData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, DocumentData.class);
			IDocumentService service = KLSServiceFactory.getDocumentService();
			service.updateDocument(data);
		} catch (Exception e) {
			logger.error("Error while updating Document");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "Document  Updated Successfully!";

	}

	@GET
	@Path("/document")
	@Produces("application/json")
	public String getAllDocument() {

		logger.info("Start : Calling DocumentService in getDocumentList() method.");
		List<DocumentData> list = new ArrayList<DocumentData>();
		String jsonString = "";
		try {
			IDocumentService service = KLSServiceFactory.getDocumentService();
			list = service.getAllDocumentList();
			Gson gson = new GsonBuilder().create();
			jsonString = gson.toJson(list);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling DocumentService in getDocumentList() method..gg.???" + jsonString);
		return jsonString;
	}

	@POST
	@Path("/document")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveDocument(String str) {
		logger.info("Start : Calling DocumentService in saveDocument() method." + str);
		DocumentData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, DocumentData.class);
			IDocumentService service = KLSServiceFactory.getDocumentService();
			service.saveDocument(data);
		} catch (Exception e) {
			logger.info("End : Calling Document service in update() method.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving");
		return "Document Saved Successfully";
	}

	@POST
	@Path("/loanProductDocumentMapping")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveLoanProductDocumentMapping(String str) {
		logger.info("Start : Calling LoanProductDocumentMappingService in saveLoanProductDocumentMapping() method." + str);
		LoanProductDocumentMappingData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, LoanProductDocumentMappingData.class);
			ILoanProductDocumentMappingService docMappingservice = KLSServiceFactory.getLoanProductDocumentMappingService();
			docMappingservice.saveLoanProductDocumentMapping(data);
		} catch (Exception e) {
			logger.info("End : Calling LoanProductDocumentMapping service in saveLoanProductDocumentMapping() method.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		return "Loan Product Document Mapping Saved Successfully";
	}

	@GET
	@Path("/loanProductDocumentMapping")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLoanProductDocumentMapping(@QueryParam("productId") String productId) {

		logger.info("Start : Calling LoanProductDocumentMappingService in getLoanProductDocumentMapping() method." + productId);
		List<LoanProductDocumentMappingData> dataLst = new ArrayList<>();
		String jsonAllLoanMappingString = "";
		try {
			ILoanProductDocumentMappingService docMappingservice = KLSServiceFactory.getLoanProductDocumentMappingService();
			if (productId != null) {
				dataLst = docMappingservice.getLoanProductDocumentMapping(productId);
				Gson gson = new GsonBuilder().create();
				jsonAllLoanMappingString = gson.toJson(dataLst);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanProductDocumentMapping service in getLoanProductDocumentMapping() method.");
		return jsonAllLoanMappingString;
	}

	@POST
	@Path("/loanProductPurposeMapping")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveLoanProductPurposeMapping(String str) {
		logger.info("Start : Calling LoanProductPurposeMappingService in saveLoanProductPurposeMapping() method.");
		LoanProductPurposeMappingData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, LoanProductPurposeMappingData.class);
			ILoanProductPurposeMappingService prodMappingservice = KLSServiceFactory.getLoanProductPurposeMappingService();
			prodMappingservice.saveLoanProductPurposeMapping(data);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanProductPurposeMapping service in saveLoanProductPurposeMapping() method.");
		return "Loan Product Purpose Mapping Saved Successfully";
	}

	@GET
	@Path("/loanProductPurposeMapping")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLoanProductPurposeMapping(@QueryParam("productId") String productId, @QueryParam("purposeId") String purposeId) {

		logger.info("Start : Calling LoanProductPurposeMappingService in getLoanProductPurposeMapping() method." + productId + "purposeId=="
				+ purposeId);
		List<LoanProductPurposeMappingData> data = new ArrayList<>();
		String jsonAllLoanMappingString = "";
		try {
			ILoanProductPurposeMappingService loanMappingservice = KLSServiceFactory.getLoanProductPurposeMappingService();
			if (productId != null && purposeId != "") {
				data = loanMappingservice.getLoanProductPurposeMapping(productId, purposeId);
				Gson gson = new GsonBuilder().create();
				jsonAllLoanMappingString = gson.toJson(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanProductPurposeMapping service in getLoanProductPurposeMapping() method.");
		return jsonAllLoanMappingString;
	}

	@GET
	@Path("/loanProductPurposeMapping/getPurposeBasedOnProduct")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPurposeBasedOnProduct(@QueryParam("productId") Integer productId) {

		logger.info("Start : Calling LoanProductPurposeMappingService in getPurposeBasedOnProduct() method." + productId);
		List<PurposeData> data = new ArrayList<>();
		String jsonAllPurposeString = "";
		try {
			ILoanProductPurposeMappingService loanMappingservice = KLSServiceFactory.getLoanProductPurposeMappingService();
			if (productId != null) {
				data = loanMappingservice.getAllPurposesBasedOnProduct(productId);
				Gson gson = new GsonBuilder().create();
				jsonAllPurposeString = gson.toJson(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanProductPurposeMapping service in getPurposeBasedOnProduct() method.");
		return jsonAllPurposeString;
	}

	@GET
	@Path("/loanProductPurposeMapping/getSubPurposeBasedOnProductAndPurpose")
	@Consumes("*/*")
	@Produces("application/json")
	public String getSubPurposeBasedOnProductAndPurpose(@QueryParam("productId") Integer productId, @QueryParam("purposeId") Integer purposeId) {

		logger.info("Start : Calling LoanProductPurposeMappingService in getSubPurposeBasedOnProductAndPurpose() method." + productId);
		List<SubPurposeData> dataList = new ArrayList<>();
		String jsonAllSubPurposeString = "";
		try {
			ILoanProductPurposeMappingService loanMappingservice = KLSServiceFactory.getLoanProductPurposeMappingService();
			if (productId != null && purposeId != null) {
				dataList = loanMappingservice.getAllSubPurposeBasedOnProductAndPurpose(productId, purposeId);
				Gson gson = new GsonBuilder().create();
				jsonAllSubPurposeString = gson.toJson(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanProductPurposeMapping service in getSubPurposeBasedOnProductAndPurpose() method.");
		return jsonAllSubPurposeString;
	}

	@GET
	@Path("/loanProductPurposeMapping/getActivityBasedOnProductPurposeAndSubPurpose")
	@Consumes("*/*")
	@Produces("application/json")
	public String getActivityBasedOnProductPurposeAndSubPurpose(@QueryParam("productId") Integer productId,
			@QueryParam("purposeId") Integer purposeId, @QueryParam("subPurposeId") Integer subPurposeId) {

		logger.info("Start : Calling LoanProductPurposeMappingService in getActivityBasedOnProductPurposeAndSubPurpose() method." + productId);
		List<ActivityData> dataList = new ArrayList<>();
		String jsonAllActivitiesString = "";
		try {
			ILoanProductPurposeMappingService loanMappingservice = KLSServiceFactory.getLoanProductPurposeMappingService();
			if (productId != null && purposeId != null || subPurposeId != null) {
				dataList = loanMappingservice.getAllActivityBasedOnProductPurposeAndSubPurpose(productId, purposeId, subPurposeId);
				Gson gson = new GsonBuilder().create();
				jsonAllActivitiesString = gson.toJson(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling LoanProductPurposeMapping service in getActivityBasedOnProductPurposeAndSubPurpose() method.");
		return jsonAllActivitiesString;
	}

	@POST
	@Path("/pacsglmapping")
	@Consumes("application/json")
	@Produces("application/text")
	public String savePacsGlMapping(String str) {
		logger.info("Start : Calling PacsGlMappingService in savePacsGlMapping() method." + str);
		PacsGlMappingData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, PacsGlMappingData.class);
			IPacsGlMappingService service = KLSServiceFactory.getPacsGlMappingService();
			service.savePacsGlMappingData(data);
		} catch (Exception e) {
			logger.info("End : Calling PacsGlMapping  savePacsGlMapping() method.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving PacsGlMapping savePacsGlMapping() method");
		return "PacsGlMapping Saved Successfully!";
	}

	@PUT
	@Path("/pacsglmapping")
	@Consumes("application/json")
	@Produces("application/text")
	public String updatePacsGlMapping(String str) {

		logger.info("Start : Calling PacsGlMappingService in updatePacsGlMapping() method." + str);
		PacsGlMappingData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, PacsGlMappingData.class);
			IPacsGlMappingService service = KLSServiceFactory.getPacsGlMappingService();
			service.updatePacsGlMappingData(data);
		} catch (Exception e) {
			logger.error("Error while updating PacsGlMapping");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End while Updating");
		return "PacsglMapping  Updated Successfully!";

	}

	@GET
	@Path("/pacsGlMapping")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsGlMapping(@QueryParam("productId") Integer productId, @QueryParam("pacsId") Integer pacsId) {
		logger.info("Start : get PacsGlMapping in getPacsGlMapping() method.");
		String pacsGlMappingJsonString = "";
		try {
			Gson gson = new GsonBuilder().create();
			PacsGlMappingData data = KLSServiceFactory.getPacsGlMappingService().getPacsGlMapping(productId, pacsId);
			if (data != null)
				pacsGlMappingJsonString = gson.toJson(data);
			else
				return "PACS GL Mapping data not found";
		} catch (Exception e) {
			logger.error(" Error : exception thrown ");
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End :  get PacsGlMapping in getPacsGlMapping() method.");
		return pacsGlMappingJsonString;
	}

	@GET
	@Path("/calendarsetup")
	@Consumes("*/*")
	@Produces("application/json")
	public String getCalendarSetup(@QueryParam("loggedInUserDetails") String loggedInUserDetails) {
		logger.info("Start : getCalendarSetup() method.");

		String calendarSetupJsonString = "";
		try {
			Gson gson = new GsonBuilder().create();
			LoggedInUserDetailsData detailsData = gson.fromJson(loggedInUserDetails, LoggedInUserDetailsData.class);
			if (detailsData.getIsBankUser()) {
				detailsData.setPacsId("0");
			}
			CalendarSetupData data = KLSServiceFactory.getCalendarSetupService().getCalendarSetup(Integer.parseInt(detailsData.getPacsId()));
			calendarSetupJsonString = gson.toJson(data);
		} catch (Exception e) {
			logger.error(" Error : exception thrown ");
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : getCalendarSetup() method.");
		return calendarSetupJsonString;
	}

	@POST
	@Path("/calendarsetup")
	@Consumes("application/json")
	@Produces("application/text")
	public String calendarSetup(String str) {
		logger.info("Start : Calling calendarSetup() method.");
		CalendarSetupData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, CalendarSetupData.class);
			ICalendarSetupService service = KLSServiceFactory.getCalendarSetupService();
			service.setupCalendar(data);
		} catch (Exception e) {
			logger.info("Error While Calling calendarSetup() method.");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End calendarSetup() method");
		return "Calendar Setup Done!";
	}

	@GET
	@Path("/calendar")
	@Consumes("*/*")
	@Produces("application/json")
	public String getCalendar(@QueryParam("loggedInUserDetailsData") String loggedInUserDetailsData) {
		logger.info("Start : getCalendarSetup() method.");
		LoggedInUserDetailsData userDetailsData = null;
		String calendarJsonString = "";
		try {
			Gson gson = new GsonBuilder().create();
			userDetailsData = gson.fromJson(loggedInUserDetailsData, LoggedInUserDetailsData.class);
			if (userDetailsData.getIsBankUser()) {
				userDetailsData.setPacsId("0");
			}
			List<CalendarData> data = KLSServiceFactory.getCalendarService().getCalendar(userDetailsData);
			calendarJsonString = gson.toJson(data);
		} catch (Exception e) {
			logger.error(" Error : exception thrown ");
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : getCalendarSetup() method.");
		return calendarJsonString;
	}

	@POST
	@Path("/calendar")
	@Consumes("application/json")
	@Produces("application/text")
	public String newCalendar(String str) {
		logger.info("Start : Calling newCalendar() method.");
		NewCalendarData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, NewCalendarData.class);
			ICalendarService service = KLSServiceFactory.getCalendarService();
			service.newCalendar(data);
		} catch (Exception e) {
			logger.info("Error While Calling newCalendar() method.");
			return "{\"result\":false}";
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End newCalendar() method");
		return "{\"result\":true}";
	}

	@GET
	// TODO: For Testing, Remove After Day Activity Integration
	@Path("/getnextbusinessdate")
	@Consumes("*/*")
	@Produces("application/json")
	public String getNextBusinessDate(@QueryParam("date") String date, @QueryParam("loggedInUserDetailsData") String loggedInUserDetailsData) {
		logger.info("Start : getCalendarSetup() method.");
		String calendarJsonString = "";
		LoggedInUserDetailsData userDetailsData = null;
		try {
			Gson gson = new GsonBuilder().create();
			userDetailsData = gson.fromJson(loggedInUserDetailsData, LoggedInUserDetailsData.class);
			if (userDetailsData.getIsBankUser()) {
				userDetailsData.setPacsId("0");
			}
			Map data = KLSServiceFactory.getCalendarService().getNextBusinessDate(date, Integer.parseInt(userDetailsData.getPacsId()));
			calendarJsonString = gson.toJson(data);
		} catch (Exception e) {
			logger.error(" Error : exception thrown ");
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : getCalendarSetup() method.");
		return calendarJsonString;
	}

	/*
	 * @POST
	 * 
	 * @Path("/borrowingsaccount")
	 * 
	 * @Consumes("application/json")
	 * 
	 * @Produces("application/text") public String saveBorrowingAccount(String
	 * str) { logger.info(
	 * "Start : Calling BorrowingAccountService in saveBorrowingAccount() method."
	 * + str); BorrowingsAccountData data = null; try { Gson gson = new
	 * GsonBuilder().create(); data = gson.fromJson(str,
	 * BorrowingsAccountData.class); IBorrowingsAccountService service =
	 * KLSServiceFactory.getBorrowingsAccountService();
	 * service.saveBorrowingsAccount(data); } catch (Exception e) {
	 * logger.info("End : Calling BorrowingsAccount  saveBorrowingAccount() method."
	 * ); return e.getMessage(); }
	 * logger.info("End While Saving  saveBorrowingAccount() method"); return
	 * " Saved Successfully!"; }
	 * 
	 * @PUT
	 * 
	 * @Path("/borrowingsaccount")
	 * 
	 * @Consumes("application/json")
	 * 
	 * @Produces("application/text") public String updateBorrowingAccount(String
	 * str) {
	 * 
	 * logger.info(
	 * "Start : Calling BorrowingAccountService in updateBorrowingAccount() method."
	 * + str); BorrowingsAccountData data = null; try { Gson gson = new
	 * GsonBuilder().create(); data = gson.fromJson(str,
	 * BorrowingsAccountData.class); IBorrowingsAccountService service =
	 * KLSServiceFactory.getBorrowingsAccountService();
	 * service.updateBorrowingsAccount(data); } catch (Exception e) {
	 * logger.error("Error while updating "); return e.getMessage(); }
	 * logger.info("End while Updating"); return "  Updated Successfully!";
	 * 
	 * }
	 */

	// @GET
	// @Path("/getaccnoandglcode")
	// @Consumes("*/*")
	/*
	 * @Produces("application/json") public String
	 * getAccnoAndGlCode(@QueryParam("productId") int
	 * productId,@QueryParam("pacsId") int pacsId) {
	 * 
	 * logger.info("getAcconoAndGlCode entered"); String jsonString = "";
	 * BorrowingsAccountData borrowingsAccountData=null; try {
	 * IBorrowingsAccountService service =
	 * KLSServiceFactory.getBorrowingsAccountService();
	 * 
	 * borrowingsAccountData = service.getAccnoAndGlCode(productId,pacsId); Gson
	 * gson = new GsonBuilder().create(); jsonString =
	 * gson.toJson(borrowingsAccountData);
	 * 
	 * } catch (Exception e) {
	 * logger.info("Error: Error in getAccnoAndGlCode "); e.printStackTrace();
	 * return e.getMessage(); } logger.info("End: getAcconoAndGlCode "); return
	 * jsonString; }
	 */

	// @GET
	// @Path("/checkproductaccountno")
	// @Consumes("*/*")
	/*
	 * @Produces("application/json") public boolean
	 * checkForUniqueAccountNo(@QueryParam("accountNo") String accountNo) {
	 * 
	 * logger.info(
	 * "Start : Checking for unique AccountNo in checkForUniqueAccountNo() method."
	 * ); boolean flag = false; logger.info(" AccountNo : " + accountNo); try {
	 * IBorrowingsAccountService service =
	 * KLSServiceFactory.getBorrowingsAccountService(); flag =
	 * service.checkForUniqueAccountNo(accountNo); } catch (Exception e) {
	 * e.printStackTrace(); flag = false; } logger.info(" flag : " + flag);
	 * logger.info(
	 * "End : Checking for unique AccountNo in checkForUniqueAccountNo() method."
	 * ); return flag; }
	 */

	// BankPacsGl 30/1/15
	// Save
	@POST
	@Path("/bankpacsglmap")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveBankPacsGl(String str) {
		logger.info("Start : Calling BankPacsGl in saveBankPacsGl() method." + str);
		BankPacsGlData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, BankPacsGlData.class);
			IBankPacsGlService service = KLSServiceFactory.getBankPacsGlService();
			service.saveBankPacsGl(data);
		} catch (Exception e) {
			logger.info("Error while saving BankPacsGl saveBankPacsGl() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End While Saving  saveBanlPacsGl() method");
		return " BankPacsGl Saved Successfully!";
	}

	// update

	@PUT
	@Path("/bankpacsglmap")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateBankPacsGl(String str) {
		logger.info("Start:Calling BankPacsGl updateBankPacsGl() method." + str);
		BankPacsGlData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(str, BankPacsGlData.class);
			IBankPacsGlService service = KLSServiceFactory.getBankPacsGlService();
			service.updateBankPacsGl(data);
		} catch (Exception e) {
			logger.info("Error while Updating BankPacsGl updateBankPacsGl() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End :Calling BankPacsGl updateBankPacsGl() method.");
		return "BankPacsGl Updated Successfully";

	}

	@GET
	@Path("/getbankpacsglaccno")
	@Consumes("*/*")
	@Produces("application/json")
	public String getBankPacsGlAccNo(@QueryParam("bankCode") String bankCode, @QueryParam("branchId") Integer branchId,
			@QueryParam("pacsId") Integer pacsId, @QueryParam("deviceNo") String deviceNo) {
		logger.info("Start : get Season Parameter in getSeasonParameter() method.");
		String bankPacsGlJsonString = "";
		try {
			Gson gson = new GsonBuilder().create();
			String data = KLSServiceFactory.getBankPacsGlService().getBankPacsGlAccNo(bankCode, branchId, pacsId, deviceNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" Error : exception thrown  BankPacsGlMapping  in getBanlPacsGlAccNo()");
			e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End :  get BankPacsGlAccNo in getBankPacsGlAccNo() method.");
		return bankPacsGlJsonString;
	}

	@GET
	@Path("/bankpacsglmap")
	@Produces("application/json")
	public String getAllBankPacsGl() {

		logger.info("Start : Calling BanlPacsGl  in getAllBankPacsGl() method.");
		List<BankPacsGlData> dataList = new ArrayList<BankPacsGlData>();
		String jsonAllBankPacsGlString = "";
		try {
			IBankPacsGlService service = KLSServiceFactory.getBankPacsGlService();
			dataList = service.getAllBankPacsGls();
			Gson gson = new GsonBuilder().create();
			jsonAllBankPacsGlString = gson.toJson(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling BankPacsGl data in getAllBankPacsGl() method.");

		return jsonAllBankPacsGlString;
	}
	
	@GET
	@Path("/accountinfo")
	@Consumes("*/*")
	@Produces("application/json")
	public String getAccountInfo(@QueryParam("custId") String custId) {

		logger.info("Start : Calling getAccountInfo() method.");
		logger.info("Customer Id : " + custId);
		String jsonAccountData = "";
		AccountData accData= new AccountData();
		Gson gson = new GsonBuilder().create();
		try {
			IAccountPropertyService accPropService = KLSServiceFactory.getAccountPropertyService();
			accData = accPropService.getAccountInfoByCustId(Long.parseLong(custId));
		} catch (Exception e) {
			e.printStackTrace();
			//eturn e.getMessage();
			accData.setErrorMssg(e.getMessage());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling getAccountInfo() method.");
		jsonAccountData = gson.toJson(accData);

		return jsonAccountData;
	}
	
	@GET
	@Path("/getLoanedLandDetails")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLoanedLandDetails(@QueryParam("customerId") Long customerId,@QueryParam("seasonId") Integer seasonId,@QueryParam("landTypeId") Integer landTypeId) {

		logger.info("Start : Calling getLoanedLandDetails() method.");
		List<PacsLoanApplicationDetailData> dataList = new ArrayList<PacsLoanApplicationDetailData>();
		String loanedLandDetails = "";
		Gson gson = new GsonBuilder().create();
		try {
			IPacsLoanApplicationDetailService pacsLoan = KLSServiceFactory.getPacsLoanApplicationDetailService();
			dataList=pacsLoan.getLoanedLandDetails(customerId, seasonId, landTypeId);
			loanedLandDetails = gson.toJson(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling getLoanedLandDetails() method.");
		return loanedLandDetails;
	}
	
	
	@POST
	@Path("/mapSavingsAccount")
	@Consumes("application/json")
	@Produces("application/text")
	public String mappingSavingsAccountWithCust(String mappingData) {
		logger.info("Start : Calling maping savings account number with customer in mappingSavingsAccountWithCust() method.");
		String respoString="";
		SBAccountMappingData data=null;
		try {
			Gson gson = new GsonBuilder().create();
			data=gson.fromJson(mappingData, SBAccountMappingData.class);
			IAccountPropertyService service = KLSServiceFactory.getAccountPropertyService();
			respoString=service.mapSavingsAccountWithCust(data);
		} catch (Exception e) {
			logger.info("Error while maping savings account number with customer mappingSavingsAccountWithCust() method");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Calling maping savings account number with customer in mappingSavingsAccountWithCust() method.");
		return respoString;
	}
	
	@GET
	@Path("/superuser")
	@Consumes("*/*")
	@Produces("application/json")
	public boolean isSuperUser(@QueryParam("role") String role) {

		logger.info("Start : Calling isSuperUser() method.");
		boolean flag=false;
		try {
			IBankParameterService service = KLSServiceFactory.getBankParameterService();
			flag=service.isSuperUser(role);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling isSuperUser() method.");
		return flag;
	}
	
	@GET
	@Path("/currentfutureseasons")
	@Consumes("*/*")
	@Produces("application/json")
	public String getCurrentFutureSeasons(@QueryParam("businessDate") String businessDate) {

		logger.info("Start : Calling Season master service in getAllSeasonYrs() method.");
		List<SeasonData> seasonDataList = new ArrayList<SeasonData>();
		String jsonAllSeasonsString = "";
		try {
			ISeasonService seasonService = KLSServiceFactory.getSeasonService();
			seasonDataList = seasonService.getCurrentFutureSeasons(businessDate);
			Gson gson = new GsonBuilder().create();
			jsonAllSeasonsString = gson.toJson(seasonDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling season master service in getAllSeasonYrs() method.");
		return jsonAllSeasonsString;
	}
	
	@GET
	@Path("/landtenantdetails")
	@Consumes("*/*")
	@Produces("application/json")
	public String checkLandTentantDetails(@QueryParam("landRefId") Long landRefId) {

		logger.info("Start : Calling Land service in checkLandTentantDetails() method.");
		List<TenantLandDetailsData> tenantDataList = new ArrayList<TenantLandDetailsData>();
		String jsonAllSeasonsString = "";
		try {
			ILandDetailService service = KLSServiceFactory.getLandDetailService();
			tenantDataList = service.checkLandTenantDetails(landRefId);
			Gson gson = new GsonBuilder().create();
			jsonAllSeasonsString = gson.toJson(tenantDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling season master service in getAllSeasonYrs() method.");
		return jsonAllSeasonsString;
	}
	
	@GET
	@Path("/seasondetails")
	@Consumes("*/*")
	@Produces("application/json")
	public String getSeasonDetails(@QueryParam("drawalStartDay") Integer drawalStartDay,@QueryParam("drawalStartMonth") Integer drawalStartMonth,@QueryParam("drawalEndDuration") Integer drawalEndDuration,@QueryParam("loanOverdueDuration") Integer loanOverdueDuration,@QueryParam("seasonYear") String seasonYear) {

		logger.info("Start : Calling Season master service in getSeasonDetails() method.");
		SeasonData seasonData = new SeasonData();
		String jsonAllSeasonsString = "";
		try {
			SeasonDefinition seasonDefinition = new SeasonDefinition();
			seasonDefinition.setDrawalStartDay(drawalStartDay);
			seasonDefinition.setDrawalStartMonth(drawalStartMonth);
			seasonDefinition.setDrawalEndDuration(drawalEndDuration);
			seasonDefinition.setLoanOverdueDuration(loanOverdueDuration);
			ISeasonService seasonService = KLSServiceFactory.getSeasonService();
			seasonData = seasonService.getSeasonDataBySeasonDef(seasonDefinition, seasonYear);
			Gson gson = new GsonBuilder().create();
			jsonAllSeasonsString = gson.toJson(seasonData);
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling season master service in getSeasonDetails() method.");
		return jsonAllSeasonsString;
	}
	
	@GET
	@Path("/landdetailsbyid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLandDetailsById(@QueryParam("id") Long id) {

		logger.info("Start : Calling Land service in checkLandTentantDetails() method.");
		String jsonAllSeasonsString = "";
		LandDetailData data = new LandDetailData();
		try {
			ILandDetailService service = KLSServiceFactory.getLandDetailService();
			data = service.getLandDetailsById(id);
			Gson gson = new GsonBuilder().create();
			jsonAllSeasonsString = gson.toJson(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling season master service in getAllSeasonYrs() method.");
		return jsonAllSeasonsString;
	}
	
	@GET
	@Path("/productsbyscheme")
	@Consumes("*/*")
	@Produces("application/json")
	public String getProductsBySchemeId(@QueryParam("schemeId") Integer schemeId) {

		logger.info("Start : Calling scheme service in getProductsBySchemeId() method.");
		String jsonAllSchemesString = "";
		List<ProductData> data = new ArrayList<>();
		try {
			IProductService service = KLSServiceFactory.getProductMasterService();
			data = service.getProductsBySchemeId(schemeId);
			Gson gson = new GsonBuilder().create();
			jsonAllSchemesString = gson.toJson(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling scheme master service in getProductsBySchemeId() method.");
		return jsonAllSchemesString;
	}
	@GET
	@Path("/pacsname/byid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsNameById(@QueryParam("pacsId") String pacsId) {

		logger.info("Start : Calling KlsApplicationRestfulService.getPacsById() ");
		PacsData pacsMasterData = new PacsData();
		String pacName = "";
		try {
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
			pacsMasterData = pacsMasterService.getPacsByPacId(Integer.parseInt(pacsId));
			
			pacName = pacsMasterData.getName()+"/"+pacsMasterData.getLocation()+","+pacsMasterData.getVillageName();
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.getPacsById() ");
		return pacName;
	}
	
	
	@GET
	@Path("/branch/pacs/byid")
	@Consumes("*/*")
	@Produces("application/json")
	public String getBranchByPacsId(@QueryParam("pacsId") String pacsId) {

		logger.info("Start : Calling KlsApplicationRestfulService.getBranchByPacsId() ");
		BranchData branchData = new BranchData();
		String branchJson = "";
		try {
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
			branchData = pacsMasterService.getBranchByPacId(Integer.parseInt(pacsId));
			Gson gson = new GsonBuilder().create();
			branchJson = gson.toJson(branchData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.getBranchByPacsId() ");
		return branchJson;
	}
	
	@GET
	@Path("/seasonParameters")
	@Consumes("*/*")
	@Produces("application/json")
	public String getSeasonParameters(@QueryParam("cropId") String cropID, @QueryParam("seasonId") String seasonId) {
		logger.info("Start : get Season Parameter in getSeasonParameter() method.");
		String seasonParameterJsonString = "";
		try {
			Gson gson = new GsonBuilder().create();
			if (cropID != null && seasonId != null) {
				SeasonParameterData data = KLSServiceFactory.getSeasonParameterService().getSeasonParameters(Long.parseLong(seasonId),
						Integer.parseInt(cropID));
				seasonParameterJsonString = gson.toJson(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" Error : exception thrown ");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End :  get Season Parameter in getSeasonParameter() method.");
		return seasonParameterJsonString;
	}

	@PUT
	@Path("/seasonParameters")
	@Consumes("application/json")
	@Produces("application/text")
	public String updateSeasonParameters(String theSeasonParameterData) {
		logger.info("In JSON Service for update");
		SeasonParameterData data = null;
		try {
			Gson gson = new GsonBuilder().create();
			data = gson.fromJson(theSeasonParameterData, SeasonParameterData.class);
				KLSServiceFactory.getSeasonParameterService().updateSeasonParameters(data);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("Updated Season Parameter  end");
		return "Crop Insurance Parameter Updated Successfully!";
	}
	
	@POST
	@Path("/pacsname/byList")
	@Consumes("*/*")
	@Produces("application/json")
	public String getPacsNameByIdList(String pacsIdListData) {

		logger.info("Start : Calling KlsApplicationRestfulService.getPacsByIdList() ");
		List<PacsData> pacsMasterDataList = new ArrayList<PacsData>();
		String pacsJson = "";
		try {
			Gson gson = new GsonBuilder().create();
			List<PacsData> pacsIdList = gson.fromJson(pacsIdListData, new TypeToken<ArrayList<PacsData>>(){}.getType());
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
			
			for (PacsData pacsId : pacsIdList) {
			PacsData pacsMasterData = pacsMasterService.getPacsByPacId(Integer.parseInt(pacsId.getId()));
			pacsMasterDataList.add(pacsMasterData);
			}
			
			pacsJson = gson.toJson(pacsMasterDataList);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling KlsApplicationRestfulService.getPacsByIdList() ");
		return pacsJson;
	}
	
	@POST
	@Path("/kccCardMapping")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveKccCardMapping(String kccMappingDataJson) {

		logger.info("Start : Calling getKccCardMappingService() ");
		String response = "";
		try {
			Gson gson = new GsonBuilder().create();
			IKccCardMappingService kccCardMappingService = KLSServiceFactory.getKccCardMappingService();
			KccCardMappingData kccMappingData = gson.fromJson(kccMappingDataJson, KccCardMappingData.class);
			response = kccCardMappingService.saveCardMappingData(kccMappingData);
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling getKccCardMappingService() ");
		return response;
	}
	
	@GET
	@Path("/getKccCardData")
	@Consumes("*/*")
	@Produces("application/json")
	public String getKccCardMappingData(@QueryParam("cardNumber") String cardNumber) {
		logger.info("Start : Fetching KCC Card Details in getKccCardMappingData() method.");
		String response = "";
		List<KccCardMappingData> cardList = new ArrayList<KccCardMappingData>();
		try {
			Gson gson = new GsonBuilder().create();
			IKccCardMappingService kccCardMappingService = KLSServiceFactory.getKccCardMappingService();
			KccCardMappingData kccCardMappingData = kccCardMappingService.getCardMappingDataByCardNumber(cardNumber);
			if(kccCardMappingData != null)
				cardList.add(kccCardMappingData);
			response = gson.toJson(cardList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" Error : exception thrown ");
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Fetching KCC Card Details in getKccCardMappingData() method.");
		return response;
	}
	
	
    @POST
    @Path("/borrowingloanProduct")
    @Consumes("application/json")
    @Produces("application/text")
    public String saveborrowingLoanProduct(String productJsonString) {

        logger.info("Start : saveborrowingLoanProduct method."+productJsonString);
        ProductDefinitionData productdef = null;
        String message="Product Data Saved Successfully!";
        try {
            IProductService productMasterService = KLSServiceFactory.getProductMasterService();
            IBorrowingProductService service = KLSServiceFactory.getBorrowingProductService();
            IBorrowingProductGlMapping  glMapservice = KLSServiceFactory.getBorrowingProductGlMappingService();
            IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
            
            Gson gson = new Gson();
            productdef = gson.fromJson(productJsonString, ProductDefinitionData.class);
            Integer borrowingProductId=0;
            if(productdef.getBorrowingProductData().getId()!=null && productdef.getBorrowingProductData().getId() > 0){
              borrowingProductId = productdef.getBorrowingProductData().getId();
             service.updateBorrowingProduct(productdef.getBorrowingProductData());
             }else{
             borrowingProductId = service.saveBorrowingProduct(productdef.getBorrowingProductData());
             }
            
            
            
            if(productdef.getLoanProductData().getId()!=null && Integer.parseInt(productdef.getLoanProductData().getId()) > 0){
            	List<PacsData> pacsMasterDataList = pacsMasterService.getAllPacs();
            	for (Iterator iterator = pacsMasterDataList.iterator(); iterator.hasNext();) {
            		
					PacsData pacsData = (PacsData) iterator.next();
					
					BorrowingProductGlMappingData existData =  glMapservice.getBorrowingProductGlMappingById(productdef.getBorrowingProductData().getId(), Integer.parseInt(pacsData.getId()));
					if(existData!=null && existData.getId()!=null){
					existData.setBankInterestReceivableGL(productdef.getBorrowingProductData().getBankInterestReceivableGL());
					existData.setBankInterestReceivedGL(productdef.getBorrowingProductData().getBankInterestReceivedGL());
					existData.setBankPenalInterestReceivableGL(productdef.getBorrowingProductData().getBankPenalInterestReceivableGL());
					existData.setBankPenalInterestReceivedGL(productdef.getBorrowingProductData().getBankPenalInterestReceivedGL());
					glMapservice.updateBorrowingProductGlMapping(BorrowingProductGlMappingHelper.getBorrowingProductGlMapping(existData));
					}
				}
            	
               }
            
            ProductData productData = productdef.getLoanProductData();
            productData.setBorrowingProductId(borrowingProductId);
            if(productdef.getLoanProductData().getId()!=null && Integer.parseInt(productdef.getLoanProductData().getId()) > 0){
             message="Product Data Updated Successfully!";
            productMasterService.updateProduct(productData);
            }else{
            productMasterService.saveProduct(productData);
            }
            
            
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            EntityManagerUtil.closeSession();
        }
        logger.info("End : Calling branch master service in saveProduct() method.");
        return message;
    }
    
    
    @GET
    @Path("/getborrowingloanProduct")
    @Consumes("application/json")
    @Produces("application/text")
    public String getBorrowingLoanProduct(@QueryParam("productTypeId") Integer productTypeId , @QueryParam("pacsId") Integer pacsId) {

        logger.info("Start : getBorrowingLoanProduct method.");
        List<ProductData> productMasterDataList = new ArrayList<ProductData>();
		String jsonAllProductsString = "";
        try {
        	IProductService productMasterService = KLSServiceFactory.getProductMasterService();
        	IBorrowingProductService service = KLSServiceFactory.getBorrowingProductService();
        	IBorrowingProductGlMapping  glMapservice = KLSServiceFactory.getBorrowingProductGlMappingService();
        	
			if (productTypeId != null){
			productMasterDataList = productMasterService.getAllProducts(productTypeId);
            }
			
			List<ProductData> loanBorrowingProductList = new ArrayList<ProductData>();
			
			for (ProductData productData : productMasterDataList) {
				if("Y".equalsIgnoreCase(productData.getBorrowingRequired())){
			    productData.setBorrowingProductData(service.getProductById(productData.getBorrowingProductId()));
			    loanBorrowingProductList.add(productData);
				}
				
			}
			
			Gson gson = new GsonBuilder().create();
			jsonAllProductsString = gson.toJson(loanBorrowingProductList);
           
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            EntityManagerUtil.closeSession();
        }
        logger.info("End : Calling branch master service in saveProduct() method.");
        return jsonAllProductsString;
    }
	
}
