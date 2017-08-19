package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a9153 This class has DAO implementations for Product
 * 
 */

public class ProductDAO implements IProductDAO {

	private static final Logger logger = Logger.getLogger(ProductDAO.class);

	/**
	 * Saves product to the database.
	 */
	public void saveProduct(Product theProductMaster) {

		logger.info("Start: Saving the product master data to the database in saveProduct() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info("Got connection" + em);
			em.getTransaction().begin();
			em.persist(theProductMaster);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the product master " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of saving the product master data to the database.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the product master to the database in saveProduct() method.");
	}

	/**
	 * Updates an existing product in the database.
	 */
	public void updateProduct(Product theProductMaster) {

		logger.info("Start: Updating the product master data to the database in updateProduct() method.");
		Product master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer productCode = theProductMaster.getId();
			if (productCode != null) {
				master = em.find(Product.class, productCode);
				if (master != null) {
					em.getTransaction().begin();
					master.setName(theProductMaster.getName());
					master.setShortName(theProductMaster.getShortName());
					master.setProductType(theProductMaster.getProductType());
					master.setAtmApplicable(theProductMaster.getAtmApplicable());
					master.setLastIntPostDate(theProductMaster.getLastIntPostDate());
					master.setReleasedDate(theProductMaster.getReleasedDate());
					master.setReleasedFlag(theProductMaster.getReleasedFlag());
					master.setIntrCategory(theProductMaster.getIntrCategory());
					master.setProductType(theProductMaster.getProductType());
					master.setSharePercentage(theProductMaster.getSharePercentage());
					master.setIsPenalInterestAllowed(theProductMaster.getIsPenalInterestAllowed());
					master.setPenalInterestOn(theProductMaster.getPenalInterestOn());
					master.setRecoverySequence(theProductMaster.getRecoverySequence());
					master.setDisbursementType(theProductMaster.getDisbursementType());
					master.setMinPeriod(theProductMaster.getMinPeriod());
					master.setMaxPeriod(theProductMaster.getMaxPeriod());
					master.setMoratoriumPrinciplePeriod(theProductMaster.getMoratoriumPrinciplePeriod());
					master.setMoratoriumInterestPeriod(theProductMaster.getMoratoriumInterestPeriod());
					master.setMoratoriumPeriodIncludedInLoan(theProductMaster.getMoratoriumPeriodIncludedInLoan());
					master.setLoanProductCode(theProductMaster.getLoanProductCode());
					master.setRepaymentType(theProductMaster.getRepaymentType());
					master.setRepaymentSchedule(theProductMaster.getRepaymentSchedule());
					master.setInterestCalcMethod(theProductMaster.getInterestCalcMethod());
					master.setPenalInterestApplicable(theProductMaster.getPenalInterestApplicable());
					master.setInterestType(theProductMaster.getInterestType());
					master.setInterestPostFreq(theProductMaster.getInterestPostFreq());
					master.setGuarantorsReqd(theProductMaster.getGuarantorsReqd());
					master.setDocRequired(theProductMaster.getDocRequired());
					master.setInsuranceRequired(theProductMaster.getInsuranceRequired());
					master.setSecurityRequired(theProductMaster.getSecurityRequired());
					master.setSubsidy(theProductMaster.getSubsidy());
					master.setProcessingFee(theProductMaster.getProcessingFee());
					master.setPreClosureMinPeriod(theProductMaster.getPreClosureMinPeriod());
					master.setPreClosureMinChargedPeriod(theProductMaster.getPreClosureMinChargedPeriod());
					master.setPreClosureIntRecoverablePeriod(theProductMaster.getPreClosureIntRecoverablePeriod());
					master.setAfterClosureMinPeriod(theProductMaster.getAfterClosureMinPeriod());
					master.setAsAndWhenImplemented(theProductMaster.getAsAndWhenImplemented());
					master.setMaxShareAmount(theProductMaster.getMaxShareAmount());
					master.setBorrowingProductId(theProductMaster.getBorrowingProductId());
					if(theProductMaster.getSchemeId()!=null){
						master.setSchemeId(theProductMaster.getSchemeId());
					}
					
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the product master " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of updating the product master data to the database.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the product master record which does not exist in the database.");
			throw new DataAccessException("Trying to update the product master record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the product master data to the database in updateProduct() method.");
	}

	/**
	 * Retrives and returns the product record from database if already exists
	 * 
	 */
	public Product getProduct(Product theProductMaster, boolean isCloseSession) {
		logger.info("Start: Inside method getProduct()");
		Product productMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer productCode = theProductMaster.getId();
			if (productCode != null) {
				productMaster = em.find(Product.class, productCode);
			}
		} catch (Exception e) {
			logger.error("Error while retriving product from the database");
			throw new DataAccessException("Error while retriving product from the database.", e.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getProduct()");
		return productMaster;
	}

	/**
	 * Returns all the product master records to the client.
	 */
	public List<Product> getAllProducts(Integer produtcTypeId, boolean isCloseSession) {

		logger.info("Start: Fetching all the product master data from the database in getAllProducts() method.");
		List<Product> productMasterList = new ArrayList<Product>();
		Query query = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			productMasterList = new ArrayList<>();
			if (produtcTypeId != null) {
				query = em.createQuery("SELECT v FROM Product v where v.productType.id = :produtcTypeId");
				query.setParameter("produtcTypeId", produtcTypeId);
			} else
				query = em.createQuery("SELECT v FROM Product v");
			productMasterList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all products from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all products from the database", e.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the product master data from the database in getAllProducts() method.");
		return productMasterList;
	}

	@Override
	public List<Product> getAllProductsBasedOnLoanType(String loanType, boolean isCloseSession) {

		logger.info("Start: Fetching all the product master data from the database in getAllProductsBasedOnLoanType() method.");
		List<Product> productMasterList = new ArrayList<Product>();
		try {
			logger.info("loanType : " + loanType);
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT v FROM Product v where v.productType.loanType = :loanType");
			query.setParameter("loanType", loanType);
			productMasterList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all products from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all products from the database", e.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the product master data from the database in getAllProductsBasedOnLoanType() method.");
		return productMasterList;
	}

	@Override
	public boolean checkForUniqueLoanProductCode(String loanProductCode, boolean isCloseSession) {

		logger.info("Start: Fetching product data from the database in checkForUniqueLoanProductCode() method.");
		Product product = null;
		boolean flag = false;
		logger.info(" loanProductCode : " + loanProductCode);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT v FROM Product v where v.loanProductCode = :loanProductCode");
			query.setParameter("loanProductCode", loanProductCode);
			product = (Product) query.getSingleResult();
			if (product != null) {
				flag = false;
			}
		} catch (NoResultException e) {
			logger.info("No product found using loan product code");
			flag = true;
		} catch (NonUniqueResultException e) {
			logger.error("More than 1 product found using loan product code");
			flag = false;
		} catch (Exception e) {
			logger.error("Error while retriving the product from the database using loan product code");
			flag = false;
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching product data from the database in checkForUniqueLoanProductCode() method.");
		return flag;
	}

	@Override
	public List<Product> getProductsBySchemeId(Integer schemeId, boolean isCloseSession) {

		logger.info("Start: Fetching all the product master data from the database in getProductsBySchemeId() method.");
		List<Product> productMasterList = new ArrayList<Product>();
		try {
			logger.info("schemeId : " + schemeId);
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT v FROM Product v where v.schemeId = :schemeId");
			query.setParameter("schemeId", schemeId);
			productMasterList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all products from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all products from the database", e.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the product master data from the database in getProductsBySchemeId() method.");
		return productMasterList;
	}
	
	public Product getProductById(Integer productId) {
		logger.info("Start: Inside method getProduct()");
		Product productMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (productId != null) {
				Query query = em.createQuery("SELECT v FROM Product v where v.id ="+productId);
				productMaster= (Product) query.getSingleResult();
			}
		} catch (Exception e) {
			logger.error("Error while retriving product from the database");
			throw new DataAccessException("Error while retriving product from the database.", e.getCause());
		} 
		logger.info("End: Inside method getProduct()");
		return productMaster;
	}

}
