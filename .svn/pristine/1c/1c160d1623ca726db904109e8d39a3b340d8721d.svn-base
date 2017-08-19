package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Activity;
import com.vsoftcorp.kls.business.entities.LoanProductPurposeMapping;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.business.entities.SubPurpose;
import com.vsoftcorp.kls.dataaccess.ILoanProductPurposeMappingDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LoanProductPurposeMappingDAO implements
		ILoanProductPurposeMappingDAO {
	private static final Logger logger = Logger.getLogger(LoanProductPurposeMappingDAO.class);

	@Override
	public void saveLoanProductPurposeMapping(LoanProductPurposeMapping master) {

		logger.info("Start: Calling saveLoanProductPurposeMapping() method in LoanProductPurposeMappingDAO");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(master);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of Loan Product Purpose Mapping data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of saving the saveLoanProductPurposeMapping data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed saving the LoanProduct Purpose Mapping data in saveLoanProductPurposeMapping()");

	}

	@Override
	public void deleteLoanProductPurposeMapping(Long id) {

		logger.info("Start: Deleting the LoanProductPurposeMapping  data in database in deleteLoanProductPurposeMapping() method.");
		try {

			EntityManager em = EntityManagerUtil.getEntityManager();
			LoanProductPurposeMapping loanMapping = em.find(
					LoanProductPurposeMapping.class, id);
			if (loanMapping != null) {
				em.getTransaction().begin();
				em.remove(loanMapping);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of Deleting the LoanProductPurposeMapping  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Deleting the LoanProductPurposeMapping data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully Deleting the LoanProductPurposeMapping data to the database in deleteLoanProductPurposeMapping() method.");

	}

	@Override
	public List<LoanProductPurposeMapping> getLoanProductPurposeMapping(
			String productId, String purposeId) {

		logger.info("Start: Fetching all the LoanProductPurposeMapping data from the database in getLoanProductPurposeMapping() method.");
		List<LoanProductPurposeMapping> loanProductPurposeMappingList = new ArrayList<LoanProductPurposeMapping>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT v FROM LoanProductPurposeMapping v where v.product.id=:productId and v.purpose.id=:purposeId");
			query.setParameter("productId", Integer.valueOf(productId));
			query.setParameter("purposeId", Integer.parseInt(purposeId));
			loanProductPurposeMappingList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all LoanProductPurposeMappings based on productId and purposeId from the database");
			throw new DataAccessException(
					"Error while retriving all LoanProductPurposeMappings based on productId and purposeId from the database",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the LoanProductPurposeMapping data from the database in getLoanProductPurposeMapping() method.");
		return loanProductPurposeMappingList;
	}

	@Override
	public List<Purpose> getPurposeBasedOnProductId(Integer productId) {
		logger.info("Start: Inside method getPurposeBasedOnProductId()");
		List<Purpose> purposeLst = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT distinct p FROM Purpose p,LoanProductPurposeMapping v where p.id = v.purpose and v.product.id=:productId");
			query.setParameter("productId", productId);
			purposeLst = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving Purpose list from the database");
			throw new DataAccessException(
					"Error while retriving Purpose list from the database.",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getPurposeBasedOnProductId()");
		return purposeLst;
	}

	@Override
	public List<SubPurpose> getSubPurposeBasedOnProductIdAndPurposeId(
			Integer productId, Integer purposeId) {
		logger.info("Start: Inside method getSubPurposeBasedOnProductIdAndPurposeId()");
		List<SubPurpose> subPurposeLst = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT distinct p FROM SubPurpose p,LoanProductPurposeMapping v where p.id = v.subPurpose and v.product.id=:productId and v.purpose.id = :purposeId");
			query.setParameter("productId", productId);
			query.setParameter("purposeId", purposeId);
			subPurposeLst = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving SubPurpose list from the database");
			throw new DataAccessException(
					"Error while retriving SubPurpose list from the database.",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getSubPurposeBasedOnProductIdAndPurposeId()");
		return subPurposeLst;
	}

	@Override
	public List<Activity> getActivityBasedOnProductAndPurposeAndSubPurpseId(
			Integer productId, Integer purposeId, Integer subPurposeId) {
		logger.info("Start: Inside method getActivityBasedOnProductAndPurposeAndSubPurpseId()---"+subPurposeId);
		List<Activity> activityLst = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder activityQuery = new StringBuilder();
			activityQuery
					.append("SELECT distinct a FROM Activity a,LoanProductPurposeMapping v where a.id = v.activity and v.product.id=:productId and v.purpose.id = :purposeId");
			if (subPurposeId != null) {
				activityQuery.append(" and v.subPurpose.id = :subPurposeId");
			}
			Query query = em.createQuery(activityQuery.toString());
			query.setParameter("productId", productId);
			query.setParameter("purposeId", purposeId);
			if (subPurposeId != null)
				query.setParameter("subPurposeId", subPurposeId);
			activityLst = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving SubPurpose list from the database");
			throw new DataAccessException(
					"Error while retriving SubPurpose list from the database.",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getActivityBasedOnProductAndPurposeAndSubPurpseId()");
		return activityLst;
	}

	/*
	 * @Override public List<Activity> getActivityBasedOnProductAndPurposeId(
	 * Integer productId, Integer purposeId) {
	 * logger.info("Start: Inside method getActivityBasedOnProductAndPurposeId()"
	 * ); List<Activity> activityLst = new ArrayList<>(); try { EntityManager em
	 * = EntityManagerUtil.getEntityManager(); Query query = em .createQuery(
	 * "SELECT distinct a FROM Activity a,LoanProductPurposeMapping v where a.id = v.activity and v.product.id=:productId and v.purpose.id = :purposeId"
	 * ); query.setParameter("productId", productId);
	 * query.setParameter("purposeId", purposeId); activityLst =
	 * query.getResultList(); } catch (Exception e) {
	 * logger.error("Error while retriving SubPurpose list from the database");
	 * throw new DataAccessException(
	 * "Error while retriving SubPurpose list from the database.",
	 * e.getCause()); } finally { EntityManagerUtil.closeSession(); }
	 * logger.info
	 * ("End: Inside method getActivityBasedOnProductAndPurposeId()"); return
	 * activityLst; }
	 */

	/*
	 * @Override public void
	 * updateLoanProductPurposeMapping(LoanProductPurposeMapping master) {
	 * logger.info(
	 * "Start: Calling saveLoanProductPurposeMapping() method in LoanProductPurposeMappingDAO"
	 * ); LoanProductPurposeMapping loanMapping = new
	 * LoanProductPurposeMapping(); try { EntityManager em =
	 * EntityManagerUtil.getEntityManager(); em.getTransaction().begin(); master
	 * = em.find(LoanProductPurposeMapping.class, master.getId());
	 * loanMapping.setActivity(master.getActivity());
	 * loanMapping.setProduct(master.getProduct());
	 * loanMapping.setPurpose(master.getPurpose());
	 * loanMapping.setSubPurpose(master.getSubPurpose()); em.merge(loanMapping);
	 * em.getTransaction().commit(); } catch (Exception e) { logger.info(
	 * "Error while Updating the LoanProduct Purpose Mapping  data to the database in updateLoanProductPurposeMapping() method."
	 * ); e.printStackTrace(); } logger.info(
	 * "END:Successfully Completed updating the LoanProduct Purpose Mapping data in updateLoanProductPurposeMapping()"
	 * );
	 * 
	 * }
	 */

}
