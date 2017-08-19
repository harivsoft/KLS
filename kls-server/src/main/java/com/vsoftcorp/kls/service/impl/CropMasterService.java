package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.data.CropMasterData;
import com.vsoftcorp.kls.dataaccess.ICropDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ICropMasterService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.CropMasterHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class CropMasterService implements ICropMasterService {

	private static final Logger logger = Logger
			.getLogger(CropMasterService.class);

	@Override
	public void saveCrop(CropMasterData theCropMasterData) {

		logger.info("Start : Calling crop master dao in saveCrop() method.");
		ICropDAO dao = KLSDataAccessFactory.getCropDAO();
		Crop master = CropMasterHelper.getCropMaster(theCropMasterData);
		Crop dbMaster = null;
		try {
			dbMaster = dao.getCrop(master);
			if (dbMaster == null) {
				dao.saveCrop(master);
			}
		} catch (Exception excp) {
			logger.error("Crop master data cannot be saved");
			throw new KlsRuntimeException(
					"Crop master data cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("Crop master data already exists");
			throw new KlsRuntimeException(
					"Crop master data already exists");
		}
		logger.info("End : Calling crop master dao in saveCrop() method.");
	}

	@Override
	public void updateCrop(CropMasterData theCropMasterData) {

		logger.info("Start : Calling crop master dao in updateCrop() method.");
		ICropDAO dao = KLSDataAccessFactory.getCropDAO();
		Crop master = CropMasterHelper.getCropMaster(theCropMasterData);
		try {
			dao.updateCrop(master);
		} catch (Exception excp) {
			logger.error("Crop data cannot be updated as crop code does not exist");
			throw new KlsRuntimeException(
					"Crop data cannot be updated as crop code does not exist",
					excp.getCause());
		}
		logger.info("End : Calling crop master dao in updateCrop() method.");
	}

	@Override
	public CropMasterData getCropList(CropMasterData theCropMasterData) {
		logger.info("in service for getting crop details");
		ICropDAO dao = KLSDataAccessFactory.getCropDAO();
		Crop master = CropMasterHelper.getCropMaster(theCropMasterData);
		Crop daoMasterData = dao.getCrop(master);
		return CropMasterHelper.getCropMasterData(daoMasterData);
	}
	
	/**
	 * Retrives all crops from Database
	 * 
	 */
	 public List<CropMasterData> getAllCrops(){
		logger.info("Start: Inside method getAllCrops()");
		ICropDAO dao = KLSDataAccessFactory.getCropDAO();
		List<CropMasterData> crops = new ArrayList<CropMasterData>();
		try {
			
			List<Crop> cropMasterList = dao.getAllCrops();
			for (Crop masterData : cropMasterList) {
				crops.add(CropMasterHelper.getCropMasterData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all crops from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all crops",e.getCause());
		}		
		logger.info("End : Inside method getAllCrops()");
		return crops;
	 }

}
