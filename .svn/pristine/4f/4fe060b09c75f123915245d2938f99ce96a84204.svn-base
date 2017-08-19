package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.data.LandTypeData;
import com.vsoftcorp.kls.dataaccess.ILandTypeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ILandTypeService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.LandTypeHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * 
 * @author a9152
 * 
 */
public class LandTypeService implements ILandTypeService {

	private static final Logger logger = Logger
			.getLogger(LandTypeService.class);

	@Override
	public void saveLandType(LandTypeData landTypeData) {
		// TODO Auto-generated method stub
		logger.info("Start : Calling land Type dao in saveLandType() method.");
		// get the land Type dao.
		ILandTypeDAO dao = KLSDataAccessFactory.getLandTypeDAO();
		// get the entity pojo.
		LandType master = LandTypeHelper.getLandType(landTypeData);
		LandType dbMaster = null;
		try {
			dbMaster = dao.getLandType(master);
			// if Land Type  code does not exist in db, then save.
			if (dbMaster == null) {
				dao.saveLandType(master);
			}
		} catch (Exception excp) {
			logger.error("LandTypedata cannot be saved");
			throw new KlsRuntimeException(
					"LandTypedata cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("LandTypedata already exists");
			throw new KlsRuntimeException(
					"LandTypedata already exists");
		}
		logger.info("End : Calling land Type dao in saveLandType() method.");

	}

	@Override
	public void updateLandType(LandTypeData landTypeData) {
		// TODO Auto-generated method stub
		logger.info("Start : Calling Land Type dao in updateLandType() method.");
		// get the Land Type dao.
		ILandTypeDAO dao = KLSDataAccessFactory.getLandTypeDAO();
		LandType master = LandTypeHelper.getLandType(landTypeData);
		// update the Land Type  code to the db.
		try {
			dao.updateLandType(master);
		} catch (Exception excp) {
			logger.error("LandType data cannot be updated as Land Type  code does not exist");
			throw new KlsRuntimeException(
					"LandType data cannot be updated as Land Type  code does not exist",
					excp.getCause());
		}
		logger.info("End : Calling Land Type dao in updateLandType() method.");
	}

	@Override
	public List<LandTypeData> getAllLandTypes() {

		logger.info("Start: Inside method getAllLandTypes()");
		ILandTypeDAO dao = KLSDataAccessFactory.getLandTypeDAO();
		List<LandTypeData> landTypes = new ArrayList<LandTypeData>();
		try {

			List<LandType> landTypeList = dao.getAllLandTypes();
			for (LandType masterData : landTypeList) {
				landTypes.add(LandTypeHelper.getLandTypeData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all productTypes from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all productTypes from the database",
					e.getCause());
		}
		logger.info("End : Inside method getAllLandTypes()");
		return landTypes;
	}

}
