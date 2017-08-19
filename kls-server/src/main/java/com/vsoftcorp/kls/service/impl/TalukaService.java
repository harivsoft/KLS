package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.data.TalukaData;
import com.vsoftcorp.kls.dataaccess.IDistrictDAO;
import com.vsoftcorp.kls.dataaccess.ITalukaDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ITalukaService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.TalukaHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * 
 * @author a9153
 * 
 */
public class TalukaService implements ITalukaService {

	private static final Logger logger = Logger
			.getLogger(TalukaService.class);

	/**
	 * This methods save the taluka only if not exists.
	 */
	public void saveTaluka(TalukaData theTalukaData) {

		logger.info("Start : Calling taluka master dao in saveTaluka() method.");

		ITalukaDAO dao = KLSDataAccessFactory.getTalukaDAO();
		Taluka master = TalukaHelper.getTaluka(theTalukaData);
		IDistrictDAO distDao = KLSDataAccessFactory.getDistrictDAO();
		
		Taluka taluka = null;
		District dist = null;
		try {
			dist = distDao.getDistrict(master.getDistrict());
			if (dist != null) {
				master.setDistrict(dist);
				// check taluka code against DB
				taluka = dao.getTaluka(master);
				//if taluka code does not exist, save taluka master
				if (taluka == null) {
					dao.saveTaluka(master);
				}
			}
		} catch (Exception excp) {
			logger.error("Taluka master data cannot be saved");
			throw new KlsRuntimeException("Taluka master data cannot be saved", excp);
		}
		if (taluka != null) {
			logger.error("Taluka master data already exists");
			throw new KlsRuntimeException("Taluka master data already exists");
		}
		if (dist == null){
			logger.error("Taluka cannot be saved as District code does not exist.");
			throw new KlsRuntimeException("Taluka cannot be saved as District code does not exist.");			
		}
		logger.info("End : Calling taluka master dao in saveTaluka() method.");
	}

	/**
	 * Updates a taluka if already exists.
	 * 
	 */
	public void updateTaluka(TalukaData theTalukaData) {
		
		logger.info("Start: Inside method updateTaluka()");
		try {
			ITalukaDAO dao = KLSDataAccessFactory.getTalukaDAO();
			Taluka master = TalukaHelper
					.getTaluka(theTalukaData);

			IDistrictDAO distDao = KLSDataAccessFactory
					.getDistrictDAO();
			master.setDistrict(distDao.getDistrict(master
					.getDistrict()));
			dao.updateTaluka(master);
		} catch (Exception e) {
			logger.error("Exception while updating taluka: " + e.getMessage());
			throw new KlsRuntimeException("Exception while saving taluka: ",
					e.getCause());
		}
		logger.info("End: Inside method updateTaluka()");
	}
	
	/**
	 * Retrives all talukas from Database
	 * 
	 */
	 public List<TalukaData> getAllTalukas(){
		logger.info("Start: Inside method getAllTalukas()");
		ITalukaDAO dao = KLSDataAccessFactory.getTalukaDAO();
		List<TalukaData> talukas = new ArrayList<TalukaData>();
		try {
			
			List<Taluka> talukaList = dao.getAllTalukas();
			for (Taluka taluka : talukaList) {
				talukas.add(TalukaHelper.getTalukaData(taluka));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all talukas from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all talukas",e.getCause());
		}		
		logger.info("End : Inside method getAllTalukas()");
		return talukas;
	 }
	 
}
