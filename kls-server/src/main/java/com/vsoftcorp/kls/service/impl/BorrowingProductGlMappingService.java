package com.vsoftcorp.kls.service.impl;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BorrowingProductGlMapping;
import com.vsoftcorp.kls.data.BorrowingProductGlMappingData;
import com.vsoftcorp.kls.dataaccess.IBorrowingProductGlMappingDAO;
import com.vsoftcorp.kls.dataaccess.IBorrowingsAccountDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.rest.service.BorrowingProductGlMappingRestService;
import com.vsoftcorp.kls.service.IBorrowingProductGlMapping;
import com.vsoftcorp.kls.service.helper.BorrowingProductGlMappingHelper;

public class BorrowingProductGlMappingService implements IBorrowingProductGlMapping{

	private static final Logger logger = Logger.getLogger(BorrowingProductGlMappingService.class);
	
	
	@Override
	public String saveBorrowingProductGlMapping(BorrowingProductGlMapping borrowingProductGlMapping) {
		logger.info("Start : Start of saveBorrowingProductGlMapping() method in Service Class.");
		String responce="";
		try{
			IBorrowingProductGlMappingDAO dao = KLSDataAccessFactory.getBorrowingProductGlMappingDAO();
			responce=dao.saveBorrowingProductGlMapping(borrowingProductGlMapping);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("Error : Error in saveBorrowingProductGlMapping() method in Service Class.");
			return e.getMessage();
		}
		return responce;
			
	}

	@Override
	public String updateBorrowingProductGlMapping(BorrowingProductGlMapping borrowingProductGlMapping) {
		
		
		logger.info("Start : Start of updateBorrowingProductGlMapping() method in Service Class.");
		String responce="";
		try{
			IBorrowingProductGlMappingDAO dao = KLSDataAccessFactory.getBorrowingProductGlMappingDAO();
			responce=dao.updateBorrowingProductGlMapping(borrowingProductGlMapping);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("Error : Error in updateBorrowingProductGlMapping() method in Service Class.");
			return e.getMessage();
		}
		return responce;
			
			
	}

	@Override
	public BorrowingProductGlMappingData getBorrowingProductGlMappingById(
			Integer id, Integer pacsId) {
		logger.info("Start : Start of updateBorrowingProductGlMapping() method in Service Class.");
		BorrowingProductGlMappingData borrowingProductGlMappingData= new BorrowingProductGlMappingData();
		try{
			IBorrowingProductGlMappingDAO dao = KLSDataAccessFactory.getBorrowingProductGlMappingDAO();
			borrowingProductGlMappingData=BorrowingProductGlMappingHelper.getBorrowingProductGlMappingData(dao.getBorrowingProductGlMapping(id, pacsId));
		}catch(NoResultException ex){
			logger.info("No Entity found with this id");
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("Error : Error in updateBorrowingProductGlMapping() method in Service Class.");
		}
		return borrowingProductGlMappingData;
	}

}
