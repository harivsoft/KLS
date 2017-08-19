package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.data.DistrictData;
import com.vsoftcorp.kls.data.ProductData;
import com.vsoftcorp.kls.data.VillageData;
import com.vsoftcorp.kls.data.VillageDetailsData;
import com.vsoftcorp.kls.dataaccess.IDistrictDAO;
import com.vsoftcorp.kls.dataaccess.ITalukaDAO;
import com.vsoftcorp.kls.dataaccess.IVillageDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IVillageService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.ProductHelper;
import com.vsoftcorp.kls.service.helper.VillageHelper;

/**
 * 
 * @author a9152
 * 
 */
public class VillageService implements IVillageService {

	private static final Logger logger = Logger.getLogger(VillageService.class);

	/**
	 * This method checks for village id in the db. If exists, then throw an
	 * exception. Else save it to the database.
	 * 
	 * @param villageData
	 */
	public void saveVillage(VillageData villageData) {

		logger.info("Start : Calling village master dao in saveVillage() method.");
		// get the village dao.
		IVillageDAO dao = KLSDataAccessFactory.getVillageDAO();
		Village master = VillageHelper.getVillage(villageData);
		// get the taluka dao.
		ITalukaDAO talukaDao = KLSDataAccessFactory.getTalukaDAO();
		Taluka taluka = null;

		try {
			// check the taluka id if it exists in the db.
			taluka = talukaDao.getTaluka(master.getTaluka());
			if (taluka != null) {
				master.setTaluka(taluka);
				if (master.getId() == null) {
					dao.saveVillage(master);
				}
			}
		} catch (Exception excp) {
			logger.error("Village id already exists");
			throw new KlsRuntimeException("Village id already exists",
					excp.getCause());
		}
		if (taluka == null) {
			logger.error("Village data cannot be saved to the db as taluka id does not exists");
			throw new KlsRuntimeException(
					"Village data cannot be saved to the db as taluka id does not exists");
		}
		logger.info("End : Calling village dao in saveVillage() method.");
	}

	/**
	 * This method checks for village id in the db. If exists, then update the
	 * village data to the database. Else throw the exception.
	 * 
	 * @param villageData
	 */
	public void updateVillage(VillageData villageData) {

		logger.info("Start : Calling village dao in updateVillage() method.");
		// get the village dao.
		IVillageDAO dao = KLSDataAccessFactory.getVillageDAO();
		Village master = VillageHelper.getVillage(villageData);
		// update the village data to the db.
		try {
			dao.updateVillage(master);
		} catch (Exception excp) {
			logger.error("Village data cannot be updated as village id does not exist");
			throw new KlsRuntimeException(
					"Village data cannot be updated as village id does not exist",
					excp.getCause());
		}
		logger.info("End : Calling village dao in updateVillage() method.");
	}

	public List<VillageData> getAllVillages() {

		logger.info("Start : Calling village dao in getAllVillages() method.");
		// get the village dao.
		IVillageDAO dao = KLSDataAccessFactory.getVillageDAO();
		List<VillageData> villageDataList = new ArrayList<VillageData>();
		try {
			List<Village> villageList = dao.getAllVillages();
			for (Village masterData : villageList) {
				villageDataList.add(VillageHelper.getVillageData(masterData));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the village records");
			throw new KlsRuntimeException(
					"Error in retrieving all the village records",
					excp.getCause());
		}
		logger.info("End : Calling village dao in getAllVillages() method.");
		return villageDataList;
	}

	@Override
	public List<VillageData> getAllVillagesByTaluka(Integer talukaId) {
		logger.info("Start : Calling village dao in getAllVillagesByTaluka() method.");
		// get the village dao.
		IVillageDAO dao = KLSDataAccessFactory.getVillageDAO();
		List<VillageData> villageDataList = new ArrayList<VillageData>();
		try {
			List<Village> villageList = dao.getAllVillagesByTaluka(talukaId);
			for (Village masterData : villageList) {
				villageDataList.add(VillageHelper.getVillageData(masterData));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the village records based on taluka");
			throw new KlsRuntimeException(
					"Error in retrieving all the village records based on taluka ",
					excp.getCause());
		}
		logger.info("End : Calling village dao in getAllVillagesByTaluka() method.");
		return villageDataList;	}

	@Override
	public VillageDetailsData getVillageDetailsById(Integer villageId) {
		logger.info("Start : Calling village dao in getVillageDetailsById() method.");
		// get the village dao.
		IVillageDAO villageDao = KLSDataAccessFactory.getVillageDAO();
		ITalukaDAO talukaDao = KLSDataAccessFactory.getTalukaDAO();
		IDistrictDAO districtDao = KLSDataAccessFactory.getDistrictDAO();
		VillageDetailsData villageDetailData = new VillageDetailsData();
		try {
			Village village = new Village();
			village.setId(villageId);
			village = villageDao.getVillage(village);
			villageDetailData.setVillageId(village.getId());
			villageDetailData.setVillageName(village.getName());
			
			Taluka taluka = new Taluka();
			taluka.setId(village.getTaluka().getId());
			taluka = talukaDao.getTaluka(taluka);
			villageDetailData.setTalukaId(taluka.getId());
			villageDetailData.setTalukaName(taluka.getName());
			
			District district = new District();
			district.setId(taluka.getDistrict().getId());
			district = districtDao.getDistrict(district);
			villageDetailData.setDistrictId(district.getId());
			villageDetailData.setDistrictName(district.getName());
			
		} catch (Exception excp) {
			logger.error("Error in retrieving all the village records");
			throw new KlsRuntimeException(
					"Error in retrieving all the village records",
					excp.getCause());
		}
		logger.info("End : Calling village dao in getVillageDetailsById() method.");
		return villageDetailData;
	}
}
