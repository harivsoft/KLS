package com.vsoftcorp.kls.service.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SubPurpose;
import com.vsoftcorp.kls.data.SubPurposeData;
import com.vsoftcorp.kls.dataaccess.ISubPurposeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ISubPurposeService;
import com.vsoftcorp.kls.service.helper.SubPurposeHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * 
 * @author a1565
 *
 */

public class SubPurposeService implements ISubPurposeService{
	private static final Logger logger = Logger.getLogger(SubPurposeService.class);

	@Override
	public void saveSubPurpose(SubPurposeData data) {
		// TODO Auto-generated method stub
		logger.info("Start:Calling saveSubPurpose() method in SubPurposeService.. ");
		ISubPurposeDAO dao=KLSDataAccessFactory.getSubPurposeDAO();
		SubPurpose master=SubPurposeHelper.getSubPurpose(data);
		try{
			if(master!=null&&data.getId()==null)
			dao.saveSubPurpose(master);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Unable to save subpurpose data  in saveSubPurpose() method..");
			throw new DataAccessException("Unable to save subpurpose data", e.getCause());
		}
		logger.info("End:Successfully completed saving subPurpose data in db");

	}

	@Override
	public void updateSubPurpose(SubPurposeData data) {
		// TODO Auto-generated method stub
		logger.info("Start:Calling updateSubPurpose() method in SubPurposeService.. ");
		ISubPurposeDAO dao=KLSDataAccessFactory.getSubPurposeDAO();
		SubPurpose master=SubPurposeHelper.getSubPurpose(data);
		try{
			Integer id=master.getId();
			if(master!=null&&id!=null)
			dao.updateSubPurpose(master);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Unable to update subpurpose data ..in updateSubPurpose() method..");
		throw new DataAccessException("Unable to updae subpurpose data", e.getCause());
		}
		logger.info("End:Successfully completed updating subPurpose data in db");

	}

	@Override
	public List<SubPurposeData> getAllSubPurpose() {
		// TODO Auto-generated method stub
		logger.info("Start:Calling getAllSubPurpose() method in SubPurposeService.. ");
		ISubPurposeDAO dao=KLSDataAccessFactory.getSubPurposeDAO();
		List<SubPurposeData> data= new ArrayList<SubPurposeData>();
		List<SubPurpose> master=null;
		try
		{
			master=dao.getAllSubPurpose();
			for (SubPurpose subPurpose : master) {
				data.add(SubPurposeHelper.getSubPurposeData(subPurpose));

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Unable to get Subpurpose data in getAllSubPurpose() method..");
			throw new DataAccessException("Unable to get subpurpose data", e.getCause());
		}
		logger.info("End:Successfully completed getting all subPurpose data from db");

		return data;
	}

	@Override
	public SubPurpose getSubPurposeById(Integer id) {
		// TODO Auto-generated method stub
		logger.info("Start:Calling getAllSubPurpose() method in SubPurposeService.. ");
		ISubPurposeDAO dao=KLSDataAccessFactory.getSubPurposeDAO();
		SubPurpose master=null;
		try{
			if(id!=null)
				master=dao.getSubPurposeById(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Unable to get Subpurpose data from getSubPurpose() method..");
			throw new DataAccessException("Unable to get subpurpose data", e.getCause());
		}
		logger.info("End:Successfully completed geting subPurpose data by id from db");
		return master;
	}

	@Override
	public void deleteSubPurpose(Integer id) {
		// TODO Auto-generated method stub
		logger.info("Start:Calling deleteSubPurpose() method in SubPurposeService.. ");
		ISubPurposeDAO dao=KLSDataAccessFactory.getSubPurposeDAO();
		try{
			if(id!=null)
				dao.deleteSubPurpose(id);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Unable to delete subpurpose data ..in deletePurpose() method..");
		throw new DataAccessException("Unable to delete subpurpose data", e.getCause());
		}
	logger.info("End:Successfully completed deleting subpurpose data in  deleteSubPurpose data from db");
	}	
	

}
