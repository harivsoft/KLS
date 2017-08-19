/**
 * 
 */
package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.data.SchemeData;
import com.vsoftcorp.kls.dataaccess.ISchemeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ISchemeService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.SchemeHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * @author sponnam
 * 
 */
public class SchemeService implements ISchemeService {

	 private static final Logger logger = Logger.getLogger(SchemeService.class);
	
	@Override
	public void saveScheme(SchemeData data) {
		// TODO Auto-generated method stub  @a1565
		logger.info("Start : Calling  Scheme dao in saveScheme() method.");
		ISchemeDAO dao=KLSDataAccessFactory.getSchemeDAO();
		Scheme master=SchemeHelper.getScheme(data);
		Scheme dbMaster=null;
		//Scheme dbProductMaster=null;
		//Product product=null;
		try
		{
			//product=daoProduct.getProduct(master.getProduct(), false);
			
				//master.setProduct(product);
				
					dbMaster=dao.getScheme(master,false);
					// Check if Scheme id does not exist in db, then save.
					if (dbMaster == null)
					{
						dao.saveScheme(master);
					}
				
							
		}
		catch (Exception excp) {
			excp.printStackTrace();
			logger.error(" Scheme data cannot be saved");
			throw new KlsRuntimeException(
					" Scheme data cannot be saved", excp);
		}
		/*
		if(dbProductMaster!=null)
		{
			if(master.getProduct().getId() == dbProductMaster.getProduct().getId())
			{
			logger.error("Product Already Exists with Scheme . Scheme Name :"+dbProductMaster.getName());
			throw new KlsRuntimeException("Product Already Exists with "+dbProductMaster.getName());
			}
		}*/
		
		if (dbMaster != null) {
			logger.error("Scheme Already Exists");
			throw new KlsRuntimeException("Scheme Already Exists");
		}
		
		/*if (product== null){
			logger.error("Scheme cannot be saved as Product Id does not exist.");
			throw new KlsRuntimeException("Scheme cannot be saved as Product Id does not exist.");			
		}*/
		
		logger.info("End : Calling  Scheme dao in saveScheme() method.");
}

	@Override
	public void updateScheme(SchemeData data) {
		// TODO Auto-generated method stub @a1565
		logger.info("Start : Calling  Scheme dao in updateScheme() method.");
		ISchemeDAO dao=KLSDataAccessFactory.getSchemeDAO();
		Scheme master=SchemeHelper.getScheme(data);
		try
		{
			dao.updateScheme(master);
		}
		catch(Exception exc)
		{
			logger.error(" Scheme data cannot be updated as  Scheme id does not exist");
			throw new KlsRuntimeException(
				" Scheme data cannot be updated as Scheme id does not exist",exc.getCause());
		}
			logger.info("End : Calling  Scheme dao in updateInterestCategory() method.");

	}

	@Override
	public List<SchemeData> getAllSchemes() {
		logger.info("Start: Inside method getAllSchemes()");
		ISchemeDAO dao = KLSDataAccessFactory.getSchemeDAO();
		List<SchemeData> schemes = new ArrayList<SchemeData>();
		try {
			List<Scheme> schemeMasterList = dao.getAllSchemes();
			for (Scheme scheme : schemeMasterList) {
				schemes.add(SchemeHelper.getSchemeData(scheme));
			}
		} catch (Exception e) {
			logger.error("Error while retriving getAllSchemes from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all Schemes ",
					e.getCause());
		}
		logger.info("End : Inside method getAllSchemes()");
		return schemes;
	}

	@Override
	public SchemeData getScheme(Integer schemeId) {
		ISchemeDAO dao = KLSDataAccessFactory.getSchemeDAO();
		SchemeData schemes = SchemeHelper.getSchemeData(dao.getScheme(schemeId));
		return schemes;
	}
	
}
