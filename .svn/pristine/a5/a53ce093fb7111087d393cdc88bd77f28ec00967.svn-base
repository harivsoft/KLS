package com.vsoftcorp.kls.dataaccess.subsidy.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.subsidy.SubsidySchemeGlMapping;
import com.vsoftcorp.kls.dataaccess.subsidy.ISubsidySchemeGlMappingDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SubsidySchemeGlMappingDAO implements ISubsidySchemeGlMappingDAO{
	private static final Logger logger = Logger
			.getLogger(SubsidySchemeGlMappingDAO.class);
	@Override
	public void saveSubsidySchemeGlMapping(SubsidySchemeGlMapping master) {
		logger.info("Start: Saving subsidy/subvention gl mapping to the database in saveSubsidySchemeGlMapping() method.");
		boolean flgTrans = false;
		
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			em.merge(master);
			
			if (flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the subsidy/subvention gl mapping"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the subsidy/subvention gl mapping to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully saved subsidy/subvention gl mapping to the database in saveSubsidySchemeGlMapping() method.");
		
	}

	@Override
	public void modifySubsidySchemeGlMapping(SubsidySchemeGlMapping master) {

		logger.info("Start: Saving the SubsidySchemeGlMapping data to the database in modifySubsidySchemeGlMapping() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(master);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of modify the SubsidySchemeGlMapping"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of modify the SubsidySchemeGlMapping data to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully modified the SubsidySchemeGlMapping to the database in modifySubsidySchemeGlMapping() method.");
	}

	@Override
	public SubsidySchemeGlMapping getSubsidySchemeGlMapping(Integer pacId,
			Long id) {
		logger.info("Start: Fetching the SubsidySchemeGlMapping data from the database in getSubsidySchemeGlMapping() method.");
		SubsidySchemeGlMapping subsidySchemeGlMapping = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (id != null) {
				Query query = em
						.createQuery("SELECT a FROM SubsidySchemeGlMapping a where a.pacsId = "+pacId+ " and a.subsidySchemeId.id="+id);
				subsidySchemeGlMapping = (SubsidySchemeGlMapping) query.getSingleResult();

			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the SubsidySchemeGlMapping data from the "
					+ "database using institute master id Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the SubsidySchemeGlMapping data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the SubsidySchemeGlMapping data from the database in getSubsidySchemeGlMapping() method.");
		return subsidySchemeGlMapping;
	}

	@Override
	public List<SubsidySchemeGlMapping> getAllSubsidySchemeGlMapping(Integer pacId) {

		logger.info("Start: Fetching all the SubsidySchemeGlMapping data from the database in getAllInstituteMasterDAO() method.");
		List<SubsidySchemeGlMapping> institutemasterList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT a FROM SubsidySchemeGlMapping a where a.pacsId = "+pacId);
			institutemasterList = query.getResultList();

			logger.info("getting the list after" + institutemasterList.size());
		} catch (Exception e) {
			logger.error("Error while retriving all SubsidySchemeGlMapping from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all SubsidySchemeGlMapping from the database",
					e.getCause());
		}
		logger.info("End: Fetching all the SubsidySchemeGlMapping data from the database in getAllInstituteMasterDAO() method.");
		return institutemasterList;
	}

	@Override
	public boolean isTransactionExist(String subsidyReceievableGl, Integer pacsId) {
		logger.info("Start: Checking whether Transaction exist in isTransactionExist() method.");
		List transList = null;
		boolean status = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createNativeQuery("select * from (select * from kls_schema.current_transaction union select * from kls_schema.transaction_history) k where k.account_number = '"+subsidyReceievableGl+"' and k.pacs_id = "+pacsId);
			
			transList = query.getResultList();
			if (transList.isEmpty())
				status = true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving  Transaction records for given subsidyReceievableGl from the database");
			throw new DataAccessException(
					"Error while retriving Transaction for given subsidyReceievableGl from the database",
					e.getCause());
		}
		logger.info("End: Checking whether Transaction exist in isTransactionExist() method");
		return status;
	}

}
