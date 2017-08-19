package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.CropTypeMaster;
import com.vsoftcorp.kls.data.CropTypeMasterData;
import com.vsoftcorp.kls.dataaccess.ICropTypeMasterDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ICropTypeMasterService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.CropTypeMasterHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * @author sponnam
 * 
 */
public class CropTypeMasterService implements ICropTypeMasterService {

	private static final Logger logger = Logger
			.getLogger(CropTypeMasterService.class);

	@Override
	public void saveCropType(CropTypeMasterData theCropTypeMasterData) {

		logger.info("Start : Calling crop type master dao in saveCropType() method.");
		ICropTypeMasterDAO dao = KLSDataAccessFactory.getCropTypeMasterDAO();
		CropTypeMaster master = CropTypeMasterHelper
				.getCropTypeMaster(theCropTypeMasterData);
		CropTypeMaster dbMaster = null;
		try {
			dbMaster = dao.getCropType(master);
			if (dbMaster == null) {
				dao.saveCropType(master);
			}
		} catch (Exception excp) {
			logger.error("Crop type master data cannot be saved");
			throw new KlsRuntimeException(
					"Crop type master data cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("Crop type master data already exists");
			throw new KlsRuntimeException(
					"Crop type master data already exists");
		}
		logger.info("End : Calling croptype master dao in saveCropType() method.");
	}

	@Override
	public void updateCropType(CropTypeMasterData theCropTypeMasterData) {

		logger.info("Start : Calling crop type master dao in updateCropType() method.");
		ICropTypeMasterDAO dao = KLSDataAccessFactory.getCropTypeMasterDAO();
		CropTypeMaster master = CropTypeMasterHelper
				.getCropTypeMaster(theCropTypeMasterData);
		try {
			dao.updateCropType(master);
		} catch (Exception excp) {
			logger.error("Croptype data cannot be updated as croptype code does not exist");
			throw new KlsRuntimeException(
					"Croptype data cannot be updated as croptype code does not exist",
					excp.getCause());
		}
		logger.info("End : Calling croptype master dao in updateCropType() method.");
	}

	@Override
	public CropTypeMasterData getCropList(
			CropTypeMasterData theCropTypeMasterData) {
		logger.info("in service for getting crop details");
		ICropTypeMasterDAO dao = KLSDataAccessFactory.getCropTypeMasterDAO();
		CropTypeMaster master = CropTypeMasterHelper
				.getCropTypeMaster(theCropTypeMasterData);
		CropTypeMaster daoMasterData = dao.getCropType(master);
		return CropTypeMasterHelper.getCropTypeMasterData(daoMasterData);
	}
	
	/**
	 * Retrives all crop types from Database
	 * 
	 */
	 public List<CropTypeMasterData> getAllCropTypes(){
		logger.info("Start: Inside method getAllCropTypes()");
		ICropTypeMasterDAO dao = KLSDataAccessFactory.getCropTypeMasterDAO();
		List<CropTypeMasterData> cropTypes = new ArrayList<CropTypeMasterData>();
		try {
			
			List<CropTypeMaster> cropTypeMasterList = dao.getAllCropTypes();
			for (CropTypeMaster masterData : cropTypeMasterList) {
				cropTypes.add(CropTypeMasterHelper.getCropTypeMasterData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all cropTypes from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all cropTypes from the database",e.getCause());
		}		
		logger.info("End : Inside method getAllCropTypes()");
		return cropTypes;
	 }

}
