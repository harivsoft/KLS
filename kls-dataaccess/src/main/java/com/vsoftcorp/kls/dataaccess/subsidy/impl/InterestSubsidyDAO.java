package com.vsoftcorp.kls.dataaccess.subsidy.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.subsidy.InterestSubsidy;
import com.vsoftcorp.kls.dataaccess.subsidy.IInterestSubsidyDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class InterestSubsidyDAO implements IInterestSubsidyDAO {

	private static final Logger logger = Logger.getLogger(InterestSubsidyDAO.class);
	@Override
	public Long saveInterestSubsidyDAO(InterestSubsidy master) {
		logger.info("Start: Saving Interest subsidy/subvention to the database in saveInterestSubsidyDAO() method.");
		boolean flgTrans = false;
		Long subsidyId;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			InterestSubsidy interestSubsidy=em.merge(master);
			subsidyId = interestSubsidy.getId();
			if (flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the Interest subsidy/subvention"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Interest subsidy/subvention to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully saved Interest subsidy/subvention to the database in saveInterestSubsidyDAO() method.");
		return subsidyId;
	}

	@Override
	public void modifyInterestSubsidyDAO(InterestSubsidy master) {
		logger.info("Start: Modifing Interest subsidy/subvention to the database in modifyInterestSubsidyDAO() method.");
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
			logger.error("Could not commit the transaction of Modifing the Interest subsidy/subvention"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Modifing the Interest subsidy/subvention to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully Modifing Interest subsidy/subvention to the database in modifyInterestSubsidyDAO() method.");
	}

	@Override
	public InterestSubsidy getInterestSubsidyDAO(Long id) {
		logger.info("Start: Fetching the Interest Subsidy data from the database in getInterestSubsidyDAO() method.");
		InterestSubsidy interestSubsidy = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select s from InterestSubsidy s where s.id="+id);
			interestSubsidy = (InterestSubsidy) query.getSingleResult();
		}catch(NoResultException e){
			logger.error("No records found with this interest subsidy id"+id);
			interestSubsidy = null;
			
		}catch (Exception excp) {
			logger.error("Could not fetch the InterestSubsidy data from the "
					+ "database using interestSubsidyId Exception thrown.");
			throw new DataAccessException("Could not fetch the InterestSubsidy data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the InterestSubsidy data from the database in getInterestSubsidyDAO() method.");
		return interestSubsidy;
	}

	@Override
	public List<InterestSubsidy> getAllInterestSubsidyDAO() {
		logger.info("Start: Fetching the Interest Subsidy data from the database in getAllInterestSubsidyDAO() method.");
		List<InterestSubsidy> interestSubsidy = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select s from InterestSubsidy s");
			interestSubsidy = query.getResultList();
		}catch(NoResultException e){
			logger.error("No records found");
			interestSubsidy = null;
			
		}catch (Exception excp) {
			logger.error("Could not fetch the InterestSubsidy data from the "
					+ "database  Exception thrown.");
			throw new DataAccessException("Could not fetch the InterestSubsidy data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the InterestSubsidy data from the database in getAllInterestSubsidyDAO() method.");
		return interestSubsidy;
	}

	@Override
	public List<InterestSubsidy> getInterestSubsidySchemesByType(
			String subsidySchemeType) {
		logger.info("Start: Fetching the Interest Subsidy data from the database in getInterestSubsidySchemesByType() method.=="+subsidySchemeType);
		List<InterestSubsidy> interestSubsidyList = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select s from InterestSubsidy s where s.typeOfScheme='"+subsidySchemeType+"'");
			interestSubsidyList =  query.getResultList();
		}catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the InterestSubsidy data from the "
					+ "database using interestSubsidyId Exception thrown.");
			throw new DataAccessException("Could not fetch the InterestSubsidy data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the InterestSubsidy data from the database in getInterestSubsidySchemesByType() method.");
		return interestSubsidyList;
	}

}
