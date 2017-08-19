package com.vsoftcorp.kls.service.impl;
/**
 * @author a1565
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.data.PurposeData;
import com.vsoftcorp.kls.dataaccess.IPurposeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IPurposeService;
import com.vsoftcorp.kls.service.helper.PurposeHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class PurposeService implements IPurposeService{
	
	private static final Logger logger = Logger.getLogger(PurposeService.class);

	@Override
	public void savePurpose(PurposeData data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling  savePurpose() method .");
		Purpose master=PurposeHelper.getPurpose(data);
		IPurposeDAO dao=KLSDataAccessFactory.getPurposeDAO();
		try
		{
			if(data.getId()==null)
				dao.savePurpose(master);
			else
			logger.error("Purpose already exists.Could not save.");
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
			logger.error("Unable to save purpose data ..in savePurpose() method..");
			throw new DataAccessException("Unable to save purpose data", e.getCause());
		}
		logger.info("End: Successfully Completed saving purpose data  in savePurpose() method .");
	}
	
	@Override
	public void updatePurpose(PurposeData data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling  updatePurpose() method .");
		Purpose master=PurposeHelper.getPurpose(data);
		IPurposeDAO dao=KLSDataAccessFactory.getPurposeDAO();
		try
		{
			Integer id=master.getId();
			if(id!=null)
			dao.updatePurPose(master);
			else
			logger.info("Unable to Update the Purpose data as ID is :"+id);
		}
		catch(Exception e)
		{ 
			logger.error("Error While updating purpose data ..in updatePurpose() method..");
			throw new DataAccessException("Error While updating purpose data", e.getCause());
		}
		logger.info("End: Successfully Completed updating purpose data  in updatePurpose() method .");
	}

	@Override
	public List<PurposeData> getAllPurpose() {
		logger.info("Start: Calling  getAllPurpose() method .");
		List<PurposeData> data= new ArrayList<PurposeData>();
		IPurposeDAO dao=KLSDataAccessFactory.getPurposeDAO();
		try
		{
			List<Purpose> master=dao.getAllPurpose();
			for (Purpose purpose : master) {
				data.add(PurposeHelper.getPurposeData(purpose));
			}
		}
		catch(Exception e)
		{ 
			logger.error("Error While getting all purpose data ..in getAllPurpose() method..");
			throw new DataAccessException("Error While getting all purpose data", e.getCause());
		}
		logger.info("End: Successfully Completed getting all purpose data in  getAllPurpose() method .");

		return data;
	}

	
	@Override
	public void deletePurpose(Integer id) {
		logger.info("Start: Calling  deletePurpose() method .");
		IPurposeDAO dao=KLSDataAccessFactory.getPurposeDAO();
		try
		{
			if(id!=null)
			dao.deletePurpose(id);
			else
			logger.info("Unable to delete purpose data as ID Is Null");
		}
		catch(Exception e)
		{ 
			logger.error("Error While deleting purpose data ..in deletePurpose() method..");
			throw new DataAccessException("Error While deleting purpose data", e.getCause());
		}
		logger.info("End: Successfully Completed deleting purpose data in  deletePurpose() method .");
	}

	@Override
	public Purpose getPurposeById(Integer id) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling  getPurposeById() method .");
		Purpose master=null;
		IPurposeDAO dao=KLSDataAccessFactory.getPurposeDAO();
		try
		{
			if(id!=null)
				master=dao.getPurposeById(id);
			else
				logger.info("Unable to get Purpose based on id as Id is null ");
		}
		catch(Exception e)
		{ 
			logger.error("Error While get purpose data by id ..in getPurposeById() method..");
			throw new DataAccessException("Error While getting purpose data", e.getCause());
		}
		logger.info("End: Successfully Completed getting purpose data by id in  getPurposeById() method .");
		return master;
	}



}
