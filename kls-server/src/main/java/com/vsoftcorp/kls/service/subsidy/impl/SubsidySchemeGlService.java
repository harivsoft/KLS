package com.vsoftcorp.kls.service.subsidy.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.subsidy.SubsidySchemeGlMapping;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.subsidy.ISubsidySchemeGlMappingDAO;
import com.vsoftcorp.kls.service.helper.subsidy.SubsidySchemeGlMappingHelper;
import com.vsoftcorp.kls.service.subsidy.ISubsidySchemeGlService;
import com.vsoftcorp.kls.subsidy.data.SubsidySchemeGlMappingData;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class SubsidySchemeGlService implements ISubsidySchemeGlService{
	private static final Logger logger = Logger.getLogger(SubsidySchemeGlService.class);
	@Override
	public void saveSubsidySchemeGlMapping(SubsidySchemeGlMappingData subsidyGlData) {

		logger.info("Start: in saveSubsidySchemeGlMapping() method");
		ISubsidySchemeGlMappingDAO dao = KLSDataAccessFactory.getSubsidySchemeGlMappingDAO();
		try {
			dao.saveSubsidySchemeGlMapping(SubsidySchemeGlMappingHelper
					.getSubsidySchemeGlMapping(subsidyGlData));
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the SubsidySchemeGlMapping"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the SubsidySchemeGlMappingData to the database.",
					excp.getCause());
		}
		logger.info("END: saving SubsidySchemeGlMapping in saveSubsidySchemeGlMapping() method");
	}
	
	@Override
	public void modifySubsidySchemeGlMapping(SubsidySchemeGlMappingData data) {

		logger.info("Start: Iin modifying modifiesInstituteMasterservice() method");
		ISubsidySchemeGlMappingDAO dao = KLSDataAccessFactory.getSubsidySchemeGlMappingDAO();
		try {
			dao.modifySubsidySchemeGlMapping(SubsidySchemeGlMappingHelper
					.getSubsidySchemeGlMapping(data));
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of modifying the InstituteMaster"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of modifying the InstituteMasterdata to the database.",
					excp.getCause());
		}
		logger.info("END: modifying InstituteMaster in modifiesInstituteMasterservice() method");
	}
	
	@Override
	public List<SubsidySchemeGlMappingData> getAllSubsidySchemeGlMapping(Integer pacsId) {
		logger.info("Start: Fetching SubsidySchemeGlMapping in getAllSubsidySchemeGlMapping() method");
		ISubsidySchemeGlMappingDAO dao = KLSDataAccessFactory.getSubsidySchemeGlMappingDAO();
		List<SubsidySchemeGlMapping> masterList = null;
		List<SubsidySchemeGlMappingData> dataList = new ArrayList<SubsidySchemeGlMappingData>();
		try{
			Long id;
			masterList=dao.getAllSubsidySchemeGlMapping(pacsId);
			if(masterList != null && !masterList.isEmpty()){
				for(SubsidySchemeGlMapping master : masterList ){
					dataList.add(SubsidySchemeGlMappingHelper.getSubsidySchemeGlMappingData(master));
				}
				logger.info("dataList=="+dataList.size());
			}
		}catch(Exception excp){
			logger.error("Could not commit the transaction of fetching SubsidySchemeGlMapping"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of fetching the SubsidySchemeGlMapping data to the database.",
					excp.getCause());
		}
		logger.info("END: Fetching SubsidySchemeGlMapping in getAllSubsidySchemeGlMapping() method");
		return dataList;

	}

	@Override
	public SubsidySchemeGlMappingData getSubsidySchemeGlMapping(
			Long subsidySchemeId, Integer pacsId) {
		logger.info("Start: Fetching institute master in getInstituteMasterservice() method");
		ISubsidySchemeGlMappingDAO dao = KLSDataAccessFactory.getSubsidySchemeGlMappingDAO();
		SubsidySchemeGlMappingData data = null;
		try{
			data= SubsidySchemeGlMappingHelper.getSubsidySchemeGlMappingData(dao.getSubsidySchemeGlMapping(pacsId, subsidySchemeId));
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

	@Override
	public boolean isTransactionExist(String subsidyReceievableGl, Integer pacsId) {
		logger.info("Start: Checking whether transaction exist in isTransactionExist() method");
		boolean status = false;
		try{
			ISubsidySchemeGlMappingDAO dao = KLSDataAccessFactory.getSubsidySchemeGlMappingDAO();
			status=dao.isTransactionExist(subsidyReceievableGl, pacsId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error while checking  transaction records for given subsidyReceievableGl from the database");
			throw new DataAccessException(
					"Error while checking transaction records for given subsidyReceievableGl from the database",
					e.getCause());
		}
		logger.info("END: Checking whether transaction exist in isTransactionExist() method");
		return status;
	}

}
