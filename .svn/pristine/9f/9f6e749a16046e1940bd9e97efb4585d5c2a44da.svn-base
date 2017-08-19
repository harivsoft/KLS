package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.Charges;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDAO;
import com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLineOfCredit;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.charges.ChargeType;

/**
 * @author a1605
 */
public class ChargesDAO implements IChargesDAO {

	private static final Logger logger = Logger.getLogger(ChargesDAO.class);

	@Override
	public void saveCharges(Charges charges) {
		logger.info("Start: Saving the charges data to the database in saveCharges() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(charges);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the charges "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the charges data to the database.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the charges to the database in saveCharges() method.");
	}

	@Override
	public List<ChargesForLineOfCredit> getShareCharges(Long accountId, List<Long> seasonIdList) {
		logger.info("Start:   getting share charges data to the database in getShareCharges() method.");
		List<ChargesForLineOfCredit> list = new ArrayList<ChargesForLineOfCredit>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryStr = "select NEW com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLineOfCredit(c.chargeAmount,c.lineOfCredit.id) "
					+ "from Charges c where c.account.id=:accountId and c.season.id in (:seasonIdList) and c.chargeType=:chargeType";
			TypedQuery<ChargesForLineOfCredit> query = em.createQuery(queryStr, ChargesForLineOfCredit.class);
			query.setParameter("accountId", accountId);
			query.setParameter("seasonIdList", seasonIdList);
			query.setParameter("chargeType", ChargeType.SHARE);
			list = query.getResultList();
			logger.info("results size : " + list.size());
		} catch (Exception excp) {
			logger.error("Error while getting the share charges " + "data from the database.Exception thrown.");
			throw new DataAccessException("Error while getting the share charges data from the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: getting share charges data to the database in getShareCharges() method.");
		return list;
	}

	@Override
	public List<ChargesForLineOfCredit> getInsuranceCharges(Long accountId, List<Long> seasonIdList) {

		logger.info("Start:   getting insurance charges data to the database in getInsuranceCharges() method.");
		List<ChargesForLineOfCredit> list = new ArrayList<ChargesForLineOfCredit>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryStr = "select NEW com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLineOfCredit(c.chargeAmount,c.lineOfCredit.id) "
					+ "from Charges c where c.account.id=:accountId and c.season.id in (:seasonIdList) and c.chargeType=:chargeType";
			TypedQuery<ChargesForLineOfCredit> query = em.createQuery(queryStr, ChargesForLineOfCredit.class);
			query.setParameter("accountId", accountId);
			query.setParameter("seasonIdList", seasonIdList);
			query.setParameter("chargeType", ChargeType.INSURANCE);
			list = query.getResultList();
			logger.info("results size : " + list.size());
		} catch (Exception excp) {
			logger.error("Error while getting the insurance charges " + "data from the database.Exception thrown.");
			throw new DataAccessException("Error while getting the  insurance charges data from the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: getting insurance charges data to the database in getInsuranceCharges() method.");
		return list;
	}

	@Override
	public void saveChargesList(List<Charges> chargesList) {

		logger.info("Start: Saving the charges list to the database in saveCharges() method.");
		for (Charges charges : chargesList) {
			saveCharges(charges);
		}
		logger.info("End: Saving the charges list to the database in saveCharges() method.");
	}

	public List<ChargesForLineOfCredit> getCharges(Long loanLocId, ChargeType chargeType) {

		logger.info("Start:   getting insurance charges data to the database in getInsuranceCharges() method.");
		List<ChargesForLineOfCredit> list = new ArrayList<ChargesForLineOfCredit>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryStr = "select NEW com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLineOfCredit(c.chargeAmount,c.lineOfCredit.id) "
					+ "from Charges c where c.lineOfCredit.id=:loanLocId and c.chargeType=:chargeType";
			TypedQuery<ChargesForLineOfCredit> query = em.createQuery(queryStr, ChargesForLineOfCredit.class);
			query.setParameter("loanLocId", loanLocId);
			query.setParameter("chargeType", chargeType);
			list = query.getResultList();
			logger.info("results size : " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the charges " + "data from the database.Exception thrown.");
			throw new DataAccessException("Error while getting the  charges data from the database.", excp.getCause());
		}
		logger.info("End: getting insurance charges data to the database in getInsuranceCharges() method.");
		return list;
	}

	public List<Charges> getAnyCharges(Long loanLocId, ChargeType chargeType) {

		logger.info("Start:   getting insurance charges data to the database in getInsuranceCharges() method.");
		List<Charges> list = new ArrayList<Charges>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryStr = "select c from Charges c where c.lineOfCredit.id=:loanLocId and c.chargeType=:chargeType";
			Query query = em.createQuery(queryStr);
			query.setParameter("loanLocId", loanLocId);
			query.setParameter("chargeType", chargeType);
			list = query.getResultList();
			logger.info("results size : " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the charges " + "data from the database.Exception thrown.");
			throw new DataAccessException("Error while getting the  charges data from the database.", excp.getCause());
		}
		logger.info("End: getting insurance charges data to the database in getInsuranceCharges() method.");
		return list;
	}
	
	public List<Charges> getChargesByAccountIdAndLoc(Long loanLocId, Long accountId,Long seasonId) {

		logger.info("Start:   getting insurance charges data to the database in getInsuranceCharges() method.");
		List<Charges> list = new ArrayList<Charges>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryStr = "select c from Charges c where c.lineOfCredit.id=:loanLocId and c.account.id=:accountId and c.season.id=:seasonId";
			Query query = em.createQuery(queryStr);
			query.setParameter("loanLocId", loanLocId);
			query.setParameter("accountId", accountId);
			query.setParameter("seasonId", seasonId);
			list = query.getResultList();
			logger.info("results size : " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the charges " + "data from the database.Exception thrown.");
			throw new DataAccessException("Error while getting the  charges data from the database.", excp.getCause());
		}
		logger.info("End: getting insurance charges data to the database in getInsuranceCharges() method.");
		return list;
	}

	/**
	 * 
	 */
	public void updateCharges(Charges charges) {

		logger.info("Start: Merging the charges data to the database in updateCharges() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.merge(charges);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of merging the charges"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the charges data to the database.", excp.getCause());
		}
		logger.info("End: Merging the charges data to the database in updateCharges() method.");
	}
}
