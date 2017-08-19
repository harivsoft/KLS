package com.vsoftcorp.kls.dataaccess.subsidy.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.subsidy.InstituteMaster;
import com.vsoftcorp.kls.dataaccess.subsidy.IInstituteMasterDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class InstituteMasterDAO implements IInstituteMasterDAO {
	private static final Logger logger = Logger
			.getLogger(InstituteMasterDAO.class);

	@Override
	public void savesInstituteMasterDAO(InstituteMaster master) {
		logger.info("Start: Saving the InstituteMaster data to the database in saveInstituteMaster() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(master);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the InstituteMaster"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the InstituteMaster data to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully saved the InstituteMaster to the database in saveAccount() method.");

	}

	@Override
	public void modifiesInstituteMasterDAO(InstituteMaster master) {

		logger.info("Start: Saving the InstituteMaster data to the database in modifiesInstituteMasterDAO() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(master);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of modify the InstituteMaster"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of modify the InstituteMaster data to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully modified the InstituteMaster to the database in modifiesInstituteMasterDAO() method.");
	}

	@Override
	public InstituteMaster getInstituteMasterDAO(Long id) {
		logger.info("Start: Fetching the institute  master data from the database in getInstituteMasterDAO() method.");
		InstituteMaster instituteMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (id != null) {
				Query query = em
						.createQuery("SELECT a FROM InstituteMaster a where a.id ="
								+ id);
				instituteMaster = (InstituteMaster) query.getSingleResult();

			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the InstituteMaster data from the "
					+ "database using institute master id Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the InstituteMaster data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the InstituteMaster data from the database in getInstituteMasterDAO() method.");
		return instituteMaster;
	}
	@Override
	public List<InstituteMaster> getAllInstituteMasterDAO() {

		logger.info("Start: Fetching all the institute master data from the database in getAllInstituteMasterDAO() method.");
		List<InstituteMaster> institutemasterList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			TypedQuery<InstituteMaster> query = em.createQuery(
					"SELECT v FROM InstituteMaster v", InstituteMaster.class);
			institutemasterList = query.getResultList();

			logger.info("getting the list after" + institutemasterList.size());
		} catch (Exception e) {
			logger.error("Error while retriving all institute master from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all Institute Master from the database",
					e.getCause());
		}
		logger.info("End: Fetching all the InstituteMaster data from the database in getAllInstituteMasterDAO() method.");
		return institutemasterList;
	}

}
