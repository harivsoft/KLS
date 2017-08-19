package com.vsoftcorp.kls.dataaccess.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vsoftcorp.kls.business.entities.BorrowingProductGlMapping;
import com.vsoftcorp.kls.dataaccess.IBorrowingProductGlMappingDAO;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class BorrowingProductGlMappingDAO implements
		IBorrowingProductGlMappingDAO {
	private static Logger logger = LoggerFactory.getLogger(BorrowingProductGlMappingDAO.class);
	@Override
	public String saveBorrowingProductGlMapping(BorrowingProductGlMapping borrowingProductGlMapping) {
		
		logger.info("Start : Creating Borrowing product Gl mapping in saveBorrowingProductGlMapping() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try{
			em.persist(borrowingProductGlMapping);
			transaction.commit();
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("Exception " + e.getMessage());
			transaction.rollback();
			return "Borrowing product Gl mapping not Saved.";
		}/*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End : updating Borrowing product Gl mapping in updateBorrowingProductGlMapping() method.");
		return "Borrowing product Gl mapping Saved Successfully.";
		
	}

	@Override
	public String updateBorrowingProductGlMapping(BorrowingProductGlMapping borrowingProductGlMapping) {
		
		logger.info("Start : updating Borrowing product Gl mapping in updateBorrowingProductGlMapping() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try{
			em.merge(borrowingProductGlMapping);
			transaction.commit();
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("Exception " + e.getMessage());
			transaction.rollback();
			return "Borrowing product Gl mapping not Updated.";
		}/*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End : updating Borrowing product Gl mapping in updateBorrowingProductGlMapping() method.");
		return "Borrowing product Gl mapping Updated Successfully.";
		
	}

	@Override
	public BorrowingProductGlMapping getBorrowingProductGlMapping(Integer id,Integer pacsId) {
		
		logger.info("Start : get Borrowing product Gl mapping in getBorrowingProductGlMapping() method.");
		
		EntityManager em = EntityManagerUtil.getEntityManager();
		BorrowingProductGlMapping borrowingProductGlMapping = null;
	    try{			   	    	
	    	String query = "SELECT b FROM BorrowingProductGlMapping b where b.productId="+id+" and b.pacsId="+pacsId+"";
	    	borrowingProductGlMapping = em.createQuery(query, BorrowingProductGlMapping.class).getSingleResult();
	    	
	    }catch(NoResultException ex){
			logger.info("No Entity found with this id");
			return null;
		}catch(Exception ex){
	    	ex.printStackTrace();
	    	logger.info("Exception " + ex.getMessage());	    
	    }
	    logger.info("End : get Borrowing product Gl mapping in getBorrowingProductGlMapping() method.");
		return borrowingProductGlMapping;
	}

}
