package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.vsoftcorp.kls.business.entities.BorrowingProduct;
import com.vsoftcorp.kls.dataaccess.IBorrowingProductDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;


/**
 * 
 * @author Danaiah This class has DAO implementations for BorrowingProduct
 * 
 */

public class BorrowingProductDAO  implements IBorrowingProductDAO {
	

	private static final Logger logger = Logger.getLogger(BorrowingProductDAO.class);

	/**
	 * Saves product to the database.
	 */

	@Override
	public Integer saveBorrowingProduct(BorrowingProduct theBorrowingProductMaster) {
		// TODO Auto-generated method stub
		

		logger.info("Start: Calling  Borrowing saveBorrowingProduct() method..");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(theBorrowingProductMaster);
			em.getTransaction().commit();
		}
		catch(RollbackException re)
		{
			re.getMessage();
			logger.error("Borrowing Product Already Exits With Same Name Or Code ..ConstraintViolationException..");
			throw new RollbackException("Borrowing Product Code Or Name Already Exits "+re.getCause());
		}
		catch (Exception e) {
			e.getMessage();
			logger.error("Could not commit the transaction of saving the Borrowing product master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
			"Could not commit the transaction of saving the  Borrowing product master data to the database.",
					e.getCause());
		} 
		logger.info("End: Successfully Completed Calling  Borrowing saveBorrowingProduct() method.");
		return theBorrowingProductMaster.getId();
	}
	
	@Override
	public void updateBorrowingProduct(BorrowingProduct master) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling updateBorrowingProduct() method in BorrowingProductDAO ..!");
		BorrowingProduct dbMaster=null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		try {
			if(master.getId()!=null)
				dbMaster = em.find(BorrowingProduct.class, master.getId());
				if (dbMaster != null)
				{
					if(master.getId()!=null)
						dbMaster.setId(master.getId());
					
					if(master.getName()!=null)
						dbMaster.setName(master.getName());
					
					dbMaster.setCode(master.getCode());
					
					dbMaster.setShortName(master.getShortName());
					
					dbMaster.setGlName(master.getGlName());
					
					dbMaster.setReleaseDate(master.getReleaseDate());
					
					if(master.getInterestFrequency()!=null)
						dbMaster.setInterestFrequency(master.getInterestFrequency());

					if(master.getInterestCategory()!=null)
						dbMaster.setInterestCategory(master.getInterestCategory());
					
					if(master.getProductType()!=null)
						dbMaster.setProductType(master.getProductType());
					
					dbMaster.setGlCode(master.getGlCode());;
					
					dbMaster.setInterestpayableGl(master.getInterestpayableGl());
					
					if(master.getScheme().getId()!=null)
						dbMaster.setScheme(master.getScheme());
					
					dbMaster.setMinPeriod(master.getMinPeriod());
						
					dbMaster.setMaxPeriod(master.getMaxPeriod());
						
					dbMaster.setInterestpaidGl(master.getInterestpaidGl());
						
					dbMaster.setInterestSubsidyGl(master.getInterestSubsidyGl());
						
					dbMaster.setPrincipleSubsidyGl(master.getPrincipleSubsidyGl());	
					
					dbMaster.setBankInterestReceivableGL(master.getBankInterestReceivableGL());
					dbMaster.setBankInterestReceivedGL(master.getBankInterestReceivedGL());
					dbMaster.setBankPenalInterestReceivableGL(master.getBankPenalInterestReceivableGL());
					dbMaster.setBankPenalInterestReceivedGL(master.getBankPenalInterestReceivedGL());
						
					em.getTransaction().begin();
						
					em.persist(dbMaster);
						
					em.getTransaction().commit();
				}
				
		}
		catch(ConstraintViolationException c)
		{
			c.getMessage();
			logger.error("Borrowing Product Already Exits With Same Name Or Code");
			throw new DataAccessException("Borrowing Product Code Or Name Already Exits "+c.getCause());
		}
		catch(Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to update the transaction of BorrowingProduct data in database " +
					".Exception thrown.");
			throw new DataAccessException("Unable to update the transaction of updating the BorrowingProduct data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed Calling  Borrowing saveBorrowingProduct() method.updateBorrowingProduct() ");

	}
	
	@Override
	public BorrowingProduct getBorrowingProduct(Integer borrowingProductId,boolean isCloseSession) {
		// TODO Auto-generated method stub
		logger.info("Start: Inside method getBorrowingProduct()");
		BorrowingProduct productMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (borrowingProductId != null) {
				productMaster = em.find(BorrowingProduct.class, borrowingProductId);
			}
		} catch (Exception e) {
			logger.error("Error while retriving Borrowing product from the database");
			throw new DataAccessException("Error while retriving Borrowing product from the database.", e.getCause());
		} 
		logger.info("End: Inside method getBorrowingProduct()");
		return productMaster;
	}

	
	/**
	 * Returns all the product master records to the client.
	 */
	

	@Override
	public List<BorrowingProduct> getAllBorrowingProducts(boolean isCloseSession) {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching all the Borrowing product master data from the database in getAllBorrowingProducts() method.");
		List<BorrowingProduct> productMasterList = new ArrayList<BorrowingProduct>();
		try{
		EntityManager em = EntityManagerUtil.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<BorrowingProduct> criteriaQuery = criteriaBuilder.createQuery(BorrowingProduct.class);
		Root<BorrowingProduct> root = criteriaQuery.from(BorrowingProduct.class);
		criteriaQuery.select(root);
		productMasterList = em.createQuery(criteriaQuery).getResultList();
		}
		catch(Exception e)
		{
			logger.error("Error while retriving Borrowing product from the database in getAllBorrowingProducts()");
			throw new DataAccessException("Error while retriving Borrowing product from the database.", e.getCause());
		}
		logger.info("End: Fetching All Borrowing Product Data from the database in getBorrowingAllProducts() method.");
		return productMasterList;
		
	}

	@Override
	public List<BorrowingProduct> getAllBorrowingProductsBasedOnLoanType(
			String loanType, boolean isCloseSession) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkForUniqueProductCode(String productCode, boolean isCloseSession) {
		// TODO Auto-generated method stub

		logger.info("Start: Fetching product data from the database in checkForUniqueProductCode() method.");
		BorrowingProduct entity = null;
		boolean flag = false;
		logger.info(" ProductCode : " + productCode);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT b FROM BorrowingProduct b where b.code = :productCode");
			query.setParameter("productCode", productCode);
			entity = (BorrowingProduct) query.getSingleResult();
			if (entity != null) {
				flag = false;
			}
		} catch (NoResultException e) {
			logger.info("No Borrowing product found using Borrowing product code");
			flag = true;
		} catch (NonUniqueResultException e) {
			logger.error("More than 1 Borrowing product found using Borrowing product code");
			flag = false;
		} catch (Exception e) {
			logger.error("Error while retriving the Borrowing product from the database using Borrowing product code");
			flag = false;
		} 
		logger.info("End: Fetching Borrowing product data from the database in checkForUniqueProductCode() method.");
		return flag;
	}

	@Override
	public boolean checkForUniqueProductName(String productName, boolean isCloseSession) {
		// TODO Auto-generated method stub

		logger.info("Start: Fetching product data from the database in checkForUniqueProductCode() method.");
		BorrowingProduct entity = null;
		boolean flag = false;
		logger.info(" ProductName : " + productName);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT b FROM BorrowingProduct b where b.name = :productName");
			query.setParameter("productName", productName);
			entity = (BorrowingProduct) query.getSingleResult();
			if (entity != null) 
				flag = false;
			
		} catch (NoResultException e) {
			logger.info("No Borrowing product found using Borrowing product name");
			flag = true;
		} catch (NonUniqueResultException e) {
			logger.error("More than 1 Borrowing product found using Borrowing product code");
			flag = false;
		} catch (Exception e) {
			logger.error("Error while retriving the Borrowing product from the database using Borrowing product code");
			flag = false;
		} 
		logger.info("End: Fetching Borrowing product data from the database in checkForUniqueProductCode() method.");
		return flag;
	}

/*	
	@Override
	public Product getProductByBorrowingProductId(Integer borrowingProductId) {
		// TODO Auto-generated method stub
		logger.info("Start: Inside method getBorrowingProduct()");
		Product productMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (borrowingProductId != null) {
				
						Query query = em.createQuery("SELECT b. FROM BorrowingProduct b where b.id = :borrowingProductId");
				query.setParameter("productName", productName);
				productMaster = (Product) query.getSingleResult();
			}
		} catch (Exception e) {
			logger.error("Error while retriving Borrowing product from the database");
			throw new DataAccessException("Error while retriving Borrowing product from the database.", e.getCause());
		}
		logger.info("End: Inside method getBorrowingProduct()");
		return productMaster;
	}*/

}
