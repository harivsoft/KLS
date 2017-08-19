package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.data.DistrictData;
import com.vsoftcorp.kls.dataaccess.IDistrictDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IDistrictService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.DistrictHelper;

/**
 * 
 * @author a9152
 * 
 */

public class DistrictService implements IDistrictService {

	private static final Logger logger = Logger
			.getLogger(DistrictService.class);

	/**
	 * 
	 */
	public void saveDistrict(DistrictData theDistrictData) {

		logger.info("Start : Calling districtdao in saveDistrict() method.");
		// get the districtdao.
		IDistrictDAO dao = KLSDataAccessFactory.getDistrictDAO();
		// get the entity pojo.
		District master = DistrictHelper.getDistrict(theDistrictData);
		District dbMaster = null;
		try {
			dbMaster = dao.getDistrict(master);
			// if district code does not exist in db, then save.
			if (dbMaster == null) {
				dao.saveDistrict(master);
			}
		} catch (Exception excp) {
			logger.error("Districtdata cannot be saved");
			throw new KlsRuntimeException(
					"Districtdata cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("Districtdata already exists");
			throw new KlsRuntimeException(
					"Districtdata already exists");
		}
		logger.info("End : Calling districtdao in saveDistrict() method.");
	}

	/**
	 * This method checks for district code in the db. If exists, then update
	 * the district data to the database. Else throw the exception.
	 * 
	 * @param districtData
	 */
	public void updateDistrict(DistrictData districtData) {

		logger.info("Start : Calling districtdao in updateDistrict() method.");
		// get the districtdao.
		IDistrictDAO dao = KLSDataAccessFactory.getDistrictDAO();
		District master = DistrictHelper
				.getDistrict(districtData);
		// update the district code to the db.
		try {
			dao.updateDistrict(master);
		} catch (Exception excp) {
			logger.error("District data cannot be updated as district code does not exist");
			throw new KlsRuntimeException(
					"District data cannot be updated as district code does not exist",
					excp.getCause());
		}
		logger.info("End : Calling districtdao in updateDistrict() method.");
	}

	/**
	 * Retrieves all districts from the database.
	 * 
	 */
	public List<DistrictData> getAllDistricts() {

		logger.info("Start : Calling districtdao in getAllDistricts() method.");
		// get the districtdao.
		IDistrictDAO dao = KLSDataAccessFactory.getDistrictDAO();
		List<DistrictData> districtDataList = new ArrayList<DistrictData>();
		try {
			List<District> districtList = dao.getAllDistricts();
			for (District district : districtList) {
				districtDataList.add(DistrictHelper
						.getDistrictData(district));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the districtrecords");
			throw new KlsRuntimeException(
					"Error in retrieving all the districtrecords",
					excp.getCause());
		}
		logger.info("End : Calling districtdao in getAllDistricts() method.");
		return districtDataList;
	}
}
