package com.vsoftcorp.kls.dataaccess.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.ScaleOfFinance;
import com.vsoftcorp.kls.dataaccess.IScaleOfFinanceDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a9152
 * 
 */
public class ScaleOfFinanceDAO implements IScaleOfFinanceDAO {

	private static final Logger logger = Logger
			.getLogger(ScaleOfFinanceDAO.class);

	/**
	 * Saves the scale of finance to the database.
	 */
	public void saveScaleOfFinance(ScaleOfFinance scaleOfFinance) {

		logger.info("Start: Saving the scale of finance master data to the database in saveScaleOfFinance() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(scaleOfFinance);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the scale of finance "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the scale of finance data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the scale of finance to the database in saveScaleOfFinance() method.@@@@=="
				+ scaleOfFinance.getId());
	}

	/**
	 * Updates the existing scale of finance in the database.
	 */
	public void updateScaleOfFinance(ScaleOfFinance scaleOfFinance) {

		logger.info("Start: Updating the scale of finance data to the database in updateScaleOfFinance() method.");
		ScaleOfFinance master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (scaleOfFinance.getId() != null) {
				master = em.find(ScaleOfFinance.class, scaleOfFinance.getId());
				if (master != null) {
					em.getTransaction().begin();
					master.setLoanAmtPerAcre(scaleOfFinance.getLoanAmtPerAcre());
					em.getTransaction().commit();
				} else {
					logger.error("Cannot update the scale of finance record as it does not exist in the database.");
					throw new DataAccessException(
							"Cannot update the scale of finance record as it does not exist in the database.");
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the scale of finance "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the scale of finance data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the scale of finance record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the scale of finance record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the scale of finance data to the database in updateScaleOfFinance() method.");
	}

	/**
	 * Checks for the scale of finance id if it exists in the database and
	 * returns the scale of finance master pojo.
	 */
	public ScaleOfFinance getScaleOfFinance(ScaleOfFinance scaleOfFinance) {

		logger.info("Start: Fetching the scale of finance data from the database in getScaleOfFinance() method.");
		ScaleOfFinance master = new ScaleOfFinance();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (scaleOfFinance.getId() != null) {
				master = em.find(ScaleOfFinance.class, scaleOfFinance.getId());
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the scale of finance data from the "
					+ "database using scale of finance id.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the scale of finance data from the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the scale of finance data from the database in getScaleOfFinance() method.");
		return master;
	}

	@Override
	public List<ScaleOfFinance> getAllScaleOfFinances() {

		logger.info("Start: Fetching all the scale of finance data from the database in getAllScaleOfFinances() method.");
		List<ScaleOfFinance> scaleOfFinanceMasterList = new ArrayList<ScaleOfFinance>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			scaleOfFinanceMasterList = em.createQuery(
					"SELECT s FROM ScaleOfFinance s").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all scale of finance records from the database");
			throw new DataAccessException(
					"Error while retriving all scale of finance records from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the scale of finance data from the database in getAllScaleOfFinances() method.");
		return scaleOfFinanceMasterList;
	}

	@Override
	public BigDecimal getScaleOfFinanceAmount(Long seasonId, Integer cropId,
			Integer landTypeId, Integer pacsId) {

		logger.info("Start: Fetching the scale of finance amount from the database in getScaleOfFinanceAmount() method.");
		BigDecimal scaleOfFinanceAmount = null;
		List list = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			TypedQuery<BigDecimal> query = em
					.createQuery(
							"SELECT s.loanAmtPerAcre FROM ScaleOfFinance s where s.season.id = :seasonId "
									+ "and s.crop.id = :cropId and s.landType.id = :landTypeId "
									+ "and s.areaType = :areaType and s.areaTypeId =(select v.taluka.id from Village v where v.id = (select p.village.id from Pacs p where p.id = :pacsId))",
							BigDecimal.class);
			query.setParameter("seasonId", seasonId);
			query.setParameter("cropId", cropId);
			query.setParameter("landTypeId", landTypeId);
			query.setParameter("areaType", "T");
			query.setParameter("pacsId", pacsId);
			list = query.getResultList();
			if (!list.isEmpty()) {
				scaleOfFinanceAmount = (BigDecimal) list.get(0);
			} else {
				TypedQuery<BigDecimal> query1 = em
						.createQuery(
								"SELECT s.loanAmtPerAcre FROM ScaleOfFinance s where s.season.id = :seasonId "
										+ "and s.crop.id = :cropId and s.landType.id = :landTypeId "
										+ "and s.areaType = :areaType and s.areaTypeId =(select d.district.id from Taluka d where d.id =(select v.taluka.id from Village v where v.id = (select p.village.id from Pacs p where p.id = :pacsId)))",
								BigDecimal.class);
				query1.setParameter("seasonId", seasonId);
				query1.setParameter("cropId", cropId);
				query1.setParameter("landTypeId", landTypeId);
				query1.setParameter("areaType", "D");
				query1.setParameter("pacsId", pacsId);
				list = query1.getResultList();
				if (!list.isEmpty())
					scaleOfFinanceAmount = (BigDecimal) list.get(0);

			}

		} catch (Exception excp) {
			logger.error("Could not fetch the scale of finance amount from the "
					+ "database using season,crop,landType id's.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the scale of finance amount from the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the scale of finance amount from the database in getScaleOfFinanceAmount() method.");
		return scaleOfFinanceAmount;
	}

	@Override
	public ScaleOfFinance getScaleOfFinance(String areaType,
			Integer areaTypeCode, Long seasonId, Integer landTypeId,
			Integer cropId) {
		logger.info("Start: Fetching the scale of finance data from the database in getScaleOfFinance() method.");
		ScaleOfFinance scaleOfFinance = new ScaleOfFinance();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT s FROM ScaleOfFinance s where s.areaType=:areaType and s.areaTypeId=:areaTypeId and s.season.id=:seasonId and s.landType.id=:landTypeId and s.crop.id=:cropId");
			query.setParameter("areaType", areaType);
			query.setParameter("areaTypeId", areaTypeCode);
			query.setParameter("seasonId", seasonId);
			query.setParameter("landTypeId", landTypeId);
			query.setParameter("cropId", cropId);
			scaleOfFinance = (ScaleOfFinance) query.getSingleResult();
		} catch (Exception e) {
			logger.error("Error while retriving scale of finance records from the database");
			return null;
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the scale of finance data from the database in getScaleOfFinance() method.");
		return scaleOfFinance;
	}
}
