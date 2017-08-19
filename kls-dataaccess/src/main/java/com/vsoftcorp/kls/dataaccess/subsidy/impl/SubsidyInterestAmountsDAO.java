package com.vsoftcorp.kls.dataaccess.subsidy.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.subsidy.SubsidyInterestAmounts;
import com.vsoftcorp.kls.dataaccess.subsidy.ISubsidyInterestAmountsDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SubsidyInterestAmountsDAO implements ISubsidyInterestAmountsDAO {
	private static final Logger logger = Logger
			.getLogger(SubsidyInterestAmountsDAO.class);

	@Override
	public void saveSubsidyInterestAmounts(SubsidyInterestAmounts master) {
		logger.info("Start: Saving Interest subsidy amounts to the database in saveSubsidyInterestAmounts() method.");
		boolean flgTrans = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			SubsidyInterestAmounts subsidy=em.find(SubsidyInterestAmounts.class, master.getId());
			if(subsidy != null){
				subsidy.setSubsidyAccrued(subsidy.getSubsidyAccrued().add(master.getSubsidyAccrued()));
			}else{
				em.merge(master);
			}
			
			if (flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the Interest subsidy amounts"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Interest subsidy amounts to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully Saving Interest subsidy amounts to the database in saveSubsidyInterestAmounts() method.");

	}

	public List<Map> getSubsidySchemeAndGlMap(Long locId, String schemeType) {
		// TODO Auto-generated method stub
		logger.info("inside getSubsidySchemeAndGlMap()");
		logger.info("locId : " + locId + " schemeType : " + schemeType);
		List<Map> map = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select new Map(s.subsidyReceivable as subsidyReceivable, s.id.subsidySchemeId.id as subsidySchemeId, g.subsidyReceievableGl as subsidyReceievableGl, s.subsidyAccrued as subsidyAccrued ) "
							+ "from SubsidyInterestAmounts s,SubsidySchemeGlMapping g "
							+ "where s.id.locId.id = :locId and s.id.subsidySchemeId.id = g.subsidySchemeId and s.id.subsidySchemeId.typeOfScheme = 'SS'");
			query.setParameter("locId", locId);
			//query.setParameter("schemeType", schemeType);

			map = query.getResultList();
			
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the getSubsidySchemeAndGlMap ");
			throw new DataAccessException(
					"Error while getting the LoanInfo data from the database.",
					excp.getCause());
		}
		logger.info("end getSubsidySchemeAndGlMap().");

		return map;
	}

	@Override
	public void updateSubsidyInterestAmounts(SubsidyInterestAmounts master) {
		logger.info("Start: Modifing Interest subsidy/subvention to the database in updateSubsidyInterestAmounts() method.");
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
		logger.info("End: Successfully Modifing Interest subsidy/subvention to the database in updateSubsidyInterestAmounts() method.");
	}
	@Override
	public List<SubsidyInterestAmounts> getSubsidyListByLocId(Long locId) {
		
		List<SubsidyInterestAmounts> subsidyList = null;
		logger.info("Start:inside getSubsidyListByLocId()");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select s from SubsidyInterestAmounts s where s.id.locId.id="+locId);
			subsidyList = query.getResultList();
		}catch(NoResultException e){
			logger.error("No records found with this interest subsidy id"+locId);
			subsidyList = null;
		}catch (Exception excp) {
			logger.error("Could not fetch the slab wise subsidy ROI data from the "
					+ "database using interestSubsidyId Exception thrown.");
			throw new DataAccessException("Could not fetch the slab wise subsidy ROI data from the database.",
					excp.getCause());
		}
		logger.info("END: inside getSubsidyListByLocId()");
		return subsidyList;
	}

	@Override
	public SubsidyInterestAmounts getSubsidyInterestAmountsbyLocId(Long locId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubsidyInterestAmounts> getSubsidyInterestAmtObj(Long locId,
			Long subSchemeId) {
		// TODO Auto-generated method stub
		logger.info("inside getSubsidyInterestAmtObj()");
		logger.info("locId : " + locId + " subSchemeId : " + subSchemeId);
		List<SubsidyInterestAmounts> sInterestAmounts = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select s as subsidyReceivable from SubsidyInterestAmounts s"
							+ " where s.id.locId.id = :locId and s.id.subsidySchemeId.id = :subSchemeId");
			query.setParameter("locId", locId);
			query.setParameter("subSchemeId", subSchemeId);

			sInterestAmounts = query.getResultList();
			
		}
		
		catch (Exception excp) {
			//excp.printStackTrace();
			logger.error("Error while getting the getSubsidyInterestAmtObj ");
			throw new DataAccessException(
					"Error while getting the SubsidyInterestAmt data from the database.",
					excp.getCause());
		}
		logger.info("end getSubsidyInterestAmtObj().");

		return sInterestAmounts;
	}

	@Override
	public BigDecimal getSubventionAmount(Long locId) {
		logger.info("Start: in getSubventionAmount()" + locId);
		BigDecimal totalSubventionAmount = BigDecimal.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select sum(s.subsidyReceivable) from SubsidyInterestAmounts s where s.id.locId.id = :locId and s.id.subsidySchemeId.typeOfScheme = 'SB'");
			query.setParameter("locId", locId);
			totalSubventionAmount = (BigDecimal) query.getSingleResult();
		} catch (Exception exception) {
			logger.error("Error: in getTotalSanctionAmount()");
			return BigDecimal.ZERO;
		}
		logger.info("End: in getSubventionAmount() totalSubventionAmount:" + totalSubventionAmount);
		return totalSubventionAmount;
	}

}
