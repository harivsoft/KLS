package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Unit;
import com.vsoftcorp.kls.data.UnitData;
import com.vsoftcorp.kls.dataaccess.IUnitDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IUnitService;
import com.vsoftcorp.kls.service.helper.UnitHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;


public class UnitService implements IUnitService {
	
	private static final Logger logger = Logger.getLogger(UnitService.class);

	@Override
	public void saveUnit(UnitData data) {
		// TODO Auto-generated method stub
		logger.info("Start:Calling saveUnit() method in UnitService.. ");
		IUnitDAO dao=KLSDataAccessFactory.getUnitDAO();
		Unit master=UnitHelper.getUnit(data);
		try{
			if(master!=null&&data.getId()==null)
			dao.saveUnit(master);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Unable to save Unit data  in saveUnit() method..");
			throw new DataAccessException("Unable to save Unit data", e.getCause());
		}
		logger.info("End:Successfully completed saving Unit data in db");
	}

	@Override
	public void updateUnit(UnitData data) {
		// TODO Auto-generated method stub
		logger.info("Start:Calling updateUnit() method in UnitService.. ");
		IUnitDAO dao=KLSDataAccessFactory.getUnitDAO();
		Unit master=UnitHelper.getUnit(data);
		try{
			Integer id=master.getId();
			if(master!=null&&id!=null)
			dao.updateUnit(master);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		logger.error("Unable to update Unit data ..in updateUnit() method..");
		throw new DataAccessException("Unable to updae Unit data", e.getCause());
		}
		logger.info("End:Successfully completed updating Unit data in db");
	}

	@Override
	public List<UnitData> getAllUnit() {
		logger.info("Start:Calling getAllUnit() method in UnitService.. ");
		IUnitDAO dao=KLSDataAccessFactory.getUnitDAO();
		List<UnitData> data= new ArrayList<UnitData>();
		List<Unit> master=null;
		try
		{
			master=dao.getAllUnit();
			for (Unit Unit : master) {
				data.add(UnitHelper.getUnitData(Unit));

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Unable to get Unit data in getAllUnit() method..");
			throw new DataAccessException("Unable to get Unit data", e.getCause());
		}
		logger.info("End:Successfully completed getting all Unit data from db");

		return data;
	}

	
	@Override
	public void deleteUnit(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Unit getUnitById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
		
}
