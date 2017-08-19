package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.ProductType;
import com.vsoftcorp.kls.dataaccess.IProductTypeDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class ProductTypeDAO implements IProductTypeDAO {

	private static final Logger logger = Logger.getLogger(ProductTypeDAO.class);

	/**
	 * Saves the Product Type to the database.
	 */
	public void saveProductType(ProductType theProductType) {

		logger.info("Start: Saving the Product Type  data to the database in saveProductType() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(theProductType);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the Product Type  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Product Type  data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the Product Type  to the database in saveProductType() method.");
	}

	/**
	 * Updates the existing Product Type in the database.
	 */
	public void updateProductType(ProductType productType) {

		logger.info("Start: Updating the Product Type  data to the database in updateProductType() method.");
		ProductType master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer productTypeId = productType.getId();
			if (productTypeId != null) {
				master = em.find(ProductType.class, productTypeId);
				if (master != null) {
					em.getTransaction().begin();
					master.setDescription(productType.getDescription());
					master.setAtmApplicable(productType.getAtmApplicable());
					master.setInterestCategory(productType
							.getInterestCategory());
					master.setLoanType(productType.getLoanType());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the Product Type  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Product Type  data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the Product Type  record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the Product Type  record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the Product Typedata to the database in updateProductType() method.");
	}

	/**
	 * Checks for the Product Type code if it exists in the database and returns
	 * the Product Typepojo.
	 */
	public ProductType getProductType(ProductType productType,
			boolean isCloseSession) {

		logger.info("Start: Fetching the Product Typedata from the database in getProductType() method.");
		ProductType master = null;// new ProductType();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer productTypeId = productType.getId();
			if (productTypeId != null) {
				master = em.find(ProductType.class, productTypeId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the Product Typedata from the "
					+ "database using Product Type code.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the Product Typedata from the database.",
					excp.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the Product Typedata from the database in getProductType() method.");
		return master;
	}

	/**
	 * Returns all the Product Typerecords to the client.
	 */
	public List<ProductType> getAllProductTypes(String loanType,
			boolean isCloseSession) {

		logger.info("Start: Fetching all the product type from the database in getAllProductTypes() method.");
		List<ProductType> productTypeList = new ArrayList<ProductType>();
		try {
			String query = "";
			if (loanType != null) {
				query = "SELECT d FROM ProductType d where d.loanType='"
						+ loanType + "'";
			} else {
				query = "SELECT d FROM ProductType d";
			}
			EntityManager em = EntityManagerUtil.getEntityManager();
			productTypeList = em.createQuery(query).getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all Product Types from the database");
			throw new DataAccessException(
					"Error while retriving all Product Types from the database",
					e.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Product Typedata from the database in getAllProductTypes() method.");
		return productTypeList;
	}
}
