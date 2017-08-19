package com.vsoftcorp.kls.service.subsidy.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.subsidy.InstituteMaster;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.subsidy.IInstituteMasterDAO;
import com.vsoftcorp.kls.service.helper.InstituteMasterHelper;
import com.vsoftcorp.kls.service.subsidy.IInstituteMasterService;
import com.vsoftcorp.kls.subsidy.data.InstituteMasterData;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class InstituteMasterService implements IInstituteMasterService {

	private static final Logger logger = Logger
			.getLogger(InstituteMasterService.class);

	public void savesInstituteMasterservice(InstituteMasterData data) {

		logger.info("Start: Iin saveInstituteMasterService() method");
		IInstituteMasterDAO dao = KLSDataAccessFactory.getInstituteMasterDAO();
		try {
			dao.savesInstituteMasterDAO(InstituteMasterHelper
					.getInstituteMaster(data));
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the InstituteMaster"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the InstituteMasterdata to the database.",
					excp.getCause());
		}
		logger.info("END: saving InstituteMaster in savesInstitute MasterService() method");
	}

	public void modifiesInstituteMasterservice(InstituteMasterData data) {

		logger.info("Start: Iin modifying modifiesInstituteMasterservice() method");
		IInstituteMasterDAO dao = KLSDataAccessFactory.getInstituteMasterDAO();
		try {
			dao.modifiesInstituteMasterDAO(InstituteMasterHelper
					.getInstituteMaster(data));
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of modifying the InstituteMaster"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of modifying the InstituteMasterdata to the database.",
					excp.getCause());
		}
		logger.info("END: modifying InstituteMaster in modifiesInstituteMasterservice() method");
	}
		
		
		


	public InstituteMasterData getInstituteMasterservice(Long id) {
		logger.info("Start: Fetching institute master in getInstituteMasterservice() method");
		IInstituteMasterDAO dao = KLSDataAccessFactory.getInstituteMasterDAO();
		InstituteMasterData data = null;
		try{
			data= InstituteMasterHelper.getInstituteMasterData(dao.getInstituteMasterDAO(id));
		}catch(Exception excp){
			logger.error("Could not commit the transaction of modifing InstituteMaster"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of modifing the InstituteMaster data to the database.",
					excp.getCause());
		}
		logger.info("END: Fetching institute master in getInstituteMasterservice() method");
		return data;

	}

	public List<InstituteMasterData> getAllInstituteMasterservice() {
		logger.info("Start: Fetching institute master in getAllInstituteMasterservice() method");
		IInstituteMasterDAO dao = KLSDataAccessFactory.getInstituteMasterDAO();
		List<InstituteMaster> masterList = null;
		List<InstituteMasterData> data = new ArrayList<InstituteMasterData>();
		try{
			Long id;
			masterList=dao.getAllInstituteMasterDAO();
			if(masterList != null && !masterList.isEmpty()){
				for(InstituteMaster  master : masterList ){
					data.add(InstituteMasterHelper.getInstituteMasterData(master));
				}
			}
		}catch(Exception excp){
			logger.error("Could not commit the transaction of fetching InstituteMaster"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of fetching the InstituteMaster data to the database.",
					excp.getCause());
		}
		logger.info("END: Fetching institute master in getAllInstituteMasterservice() method");
		return data;

	}
}
