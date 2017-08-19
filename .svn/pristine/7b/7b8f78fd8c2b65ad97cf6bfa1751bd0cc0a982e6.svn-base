/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.dataaccess.ISchemeDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

import javax.persistence.Query;

/**
 * @author a9152
 * 
 */
public class SchemeDAO implements ISchemeDAO {

	private static final Logger logger = Logger.getLogger(SchemeDAO.class);

	@Override
	public void saveScheme(Scheme scheme) {

		logger.info("Start: Saving the scheme data to the database in saveScheme() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(scheme);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the scheme "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the scheme data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the scheme to the database in saveScheme() method.");
	}

	@Override
	public void updateScheme(Scheme scheme) {

		logger.info("Start: Updating the scheme data to the database in updateScheme() method.");
		Scheme master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer id = scheme.getId();
			if (id != null) {
				master = em.find(Scheme.class, id);
				if (master != null) {
					em.getTransaction().begin();
					master.setName(scheme.getName());
				/*	master.setInsurancePercentage(scheme
							.getInsurancePercentage());*/
					em.merge(master);
					em.getTransaction().commit();
				} else {
					logger.error("Cannot update the scheme record as it does not exist in the database.");
					throw new DataAccessException(
							"Cannot update the scheme record as it does not exist in the database.");
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the scheme "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the scheme  data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the Scheme record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the Scheme record which does not exist in the database.");
		}

		logger.info("End: Successfully updated the scheme data to the database in updateTenureSlab() method.");
	}

	@Override
	public Scheme getScheme(Scheme scheme,boolean isCloseSession) {
		logger.info("Start: Fetching the scheme data from the database in getScheme() method.");
		Scheme master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer id = scheme.getId();

			if (id != null) {
				master = em.find(Scheme.class, id);
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the scheme data from the "
					+ "database using scheme id.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the scheme data from the database.",
					excp.getCause());
		}
		finally{
			if(isCloseSession)
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the scheme data from the database in getScheme() method.");
		return master;
	}

	// To check scheme data based on product Id @a1565
	@Override
	public Scheme getSchemeBasedOnProduct(Scheme scheme,boolean isCloseSession) {
		logger.info("Start: Fetching the scheme data from the database in getSchemeBasedOnProduct() method.");
		Scheme master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			//Integer product_Id = scheme.getProduct().getId();
			Integer product_Id = 1;
			// if product_id already exits with scheme name return value else
			// null
			if (product_Id != null) {
				Query query = em
						.createQuery("SELECT s FROM Scheme s");

				List list = query.getResultList();
				if (!list.isEmpty())
					master = (Scheme) list.get(0);
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the scheme data from the "
					+ "database using scheme id.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the scheme data from the database.",
					excp.getCause());
		}
		finally{
			if(isCloseSession){
			EntityManagerUtil.closeSession();
			}
		}
		logger.info("End: Successfully fetched the scheme data from the database in getSchemeBasedOnProduct() method.");

		return master;
	}

	@Override
	public List<Scheme> getAllSchemes() {

		logger.info("Start: Fetching all the scheme data from the database in getAllSchemes() method.");
		List<Scheme> schemeList = new ArrayList<Scheme>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			schemeList = em.createQuery("SELECT s FROM Scheme s")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all schemes from the database");
			throw new DataAccessException(
					"Error while retriving all schemes from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the scheme data from the database in getAllSchemes() method.");
		return schemeList;
	}

	@Override
	public void deleteSchemeRecord(Scheme Scheme) {
		// TODO Auto-generated method stub

	}

	@Override
	public Scheme getScheme(Integer schemeId) {
		
		logger.info("Start: Inside method getScheme()");
		Scheme scheme = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (schemeId != null) {
				scheme = em.find(Scheme.class, schemeId);
			} else {
				logger.info("scheme id not set for retrival of scheme record");
				throw new DataAccessException(
						"scheme id not set for retrival of scheme record");
			}
		} catch (Exception e) {
			logger.error("Error while retriving scheme from the database");
			throw new DataAccessException(
					"Error while retriving scheme from the database.",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method scheme()");
		return scheme;
	}

}
