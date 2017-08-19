package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.loan.ProductChargesMapping;
import com.vsoftcorp.kls.dataaccess.loan.IProductChargesMappingDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class ProductChargesMappingDAO implements IProductChargesMappingDAO {
	private static final Logger logger = Logger.getLogger(ProductChargesMappingDAO.class);

	@Override
	public void saveProductChargesMapping(ProductChargesMapping productChargesMapping) {

		// TODO Auto-generated method stub
		logger.info("Start: Calling saveProductChargesMapping() method in ProductChargesMappingDAO");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(productChargesMapping);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of product charges mapping data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of saving the product charges mapping data.", excp.getCause());
		}
		logger.info("END:Successfully Completed saving the product charges mapping data in saveProductChargesMapping()");

	}

	@Override
	public void deleteProductChargesMapping(Long mappingId) {

		logger.info("Start: Deleting the product charges mapping  data in database in deleteProductChargesMapping() method.");
		try {

			EntityManager em = EntityManagerUtil.getEntityManager();
			ProductChargesMapping productChargesMapping = getProductChargesMappingBasedOnId(mappingId);
			if (productChargesMapping != null) {
				em.getTransaction().begin();
				em.remove(productChargesMapping);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of Deleting the product charges mapping  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Deleting the product charges mapping data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully Deleting the product charges mapping data to the database in deleteProductChargesMapping() method.");

	}

	public ProductChargesMapping getProductChargesMappingBasedOnId(Long id) {
		logger.info("Start: Calling getProductChargesMappingBasedOnId() method in ProductChargesMappingDAO");
		ProductChargesMapping productChargesMapping = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (id != null) {
				productChargesMapping = em.find(ProductChargesMapping.class, id);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		logger.info("successfully got product charges mapping details based on id");

		return productChargesMapping;
	}

	@Override
	public List<ProductChargesMapping> getProductChargesMapping(Integer productId) {

		logger.info("Start: Fetching all the product charges mapping data from the database in getProductChargesMapping() method.");
		List<ProductChargesMapping> productChargesMappingList = new ArrayList<ProductChargesMapping>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT v FROM ProductChargesMapping v where v.product.id=:productId");
			query.setParameter("productId", productId);
			productChargesMappingList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all product charges mapping based on productId from the database");
			throw new DataAccessException(
					"Error while retriving all product charges mapping based on productId from the database",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the product charges mapping data from the database in getProductChargesMapping() method.");
		return productChargesMappingList;
	}

	@Override
	public ProductChargesMapping getProductChargesMappingByProductIdAndChargesId(
			Integer productId, Long chargeId) {
		logger.info("Start: Fetching all the product charges mapping data from the database in getProductChargesMappingByProductIdAndChargesId() method.");
		ProductChargesMapping productChargesMappingList = new ProductChargesMapping();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT v FROM ProductChargesMapping v where v.product.id=:productId and v.chargesMaster.id=:chargeId");
			query.setParameter("productId", productId);
			query.setParameter("chargeId", chargeId);
			productChargesMappingList = (ProductChargesMapping)query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all product charges mapping based on productId from the database");
			throw new DataAccessException(
					"Error while retriving all product charges mapping based on productId from the database",
					e.getCause());
		} 
		logger.info("End: Fetching all the product charges mapping data from the database in getProductChargesMappingByProductIdAndChargesId() method.");
		return productChargesMappingList;
	}

}
