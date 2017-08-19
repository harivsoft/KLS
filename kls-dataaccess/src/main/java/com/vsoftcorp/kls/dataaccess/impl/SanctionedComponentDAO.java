package com.vsoftcorp.kls.dataaccess.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SanctionedComponentDetail;
import com.vsoftcorp.kls.dataaccess.ISanctionedComponentDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SanctionedComponentDAO implements ISanctionedComponentDAO {
	private static final Logger logger = Logger
			.getLogger(SanctionedComponentDAO.class);

	@Override
	public void saveSanctionedComponent(SanctionedComponentDetail master) {

		logger.info("Start: Saving the SanctionedComponentDetail  data in database in saveSanctionedComponent() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(master);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the SanctionedComponentDetail  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the SanctionedComponentDetail data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the SanctionedComponentDetail  data to the database in saveSanctionedComponent() method.");
	}

	@Override
	public void updateSanctionedComponent(SanctionedComponentDetail master) {

		logger.info("Start: Updating the SanctionedComponentDetail data to the database in updateSanctionedComponent() method.");
		SanctionedComponentDetail dbComponentDetail = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			Integer id = master.getId();
			if (id != null) {
				dbComponentDetail = em
						.find(SanctionedComponentDetail.class, id);
				if (dbComponentDetail != null) {
					em.getTransaction().begin();
					dbComponentDetail.setSeason(master.getSeason());
					dbComponentDetail.setPercentageOfKind(master
							.getPercentageOfKind());
					dbComponentDetail.setComponentDescription(master
							.getComponentDescription());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the SanctionedComponentDetail "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the SanctionedComponentDetail data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (dbComponentDetail == null) {
			logger.error("Trying to update the SanctionedComponentDetail record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the SanctionedComponentDetail record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the SanctionedComponentDetail data to the database in updateSanctionedComponent() method.");
	}

	@Override
	public List<SanctionedComponentDetail> getSanctionedComponentsDetailsBySeasonId(
			Long seasonId) {

		logger.info("Start: Fetching the LandDetail "
				+ " data from the database in getLandDetailsBySeasonId() method."
				+ seasonId);
		List<SanctionedComponentDetail> sanctionedComponentsList = new ArrayList<SanctionedComponentDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			sanctionedComponentsList = em.createQuery(
					"SELECT s FROM SanctionedComponentDetail s where s.season.id='"
							+ seasonId + "'").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving SanctionedComponentDetail from the database");
			throw new DataAccessException("Error while retriving LandDetails "
					+ " from the database", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Customer Sanctioned Component Detail data from the database in getLandDetailsBySeasonId() method.");
		return sanctionedComponentsList;
	}

	@Override
	public void deleteComponentDetail(String id) {
		logger.info("Start: Deleting the SanctionedComponentDetail  data in database in deleteComponentDetail() method.");
		try {

			EntityManager em = EntityManagerUtil.getEntityManager();
			SanctionedComponentDetail componentDetail = em.find(
					SanctionedComponentDetail.class,Integer.parseInt(id));
			if (componentDetail != null) {
				em.getTransaction().begin();
				em.remove(componentDetail);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of Deleting the SanctionedComponentDetail  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Error while deleting sanction component detail.",
					excp.getCause());

		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully Deleting the SanctionedComponentDetail data to the database in deleteComponentDetail() method.");
	}

	@Override
	public BigDecimal getTotalKindPercentage(Long seasonId) {
		logger.info("Start: inside getTotalKindPercentage()");
		BigDecimal totalKindPercentage = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select sum(s.percentageOfKind) from SanctionedComponentDetail s where s.season.id=:seasonId");
			query.setParameter("seasonId", seasonId);
			totalKindPercentage = (BigDecimal) query.getSingleResult();
		} catch (NoResultException noExcp) {
			logger.error("No record found in the database.");
			return totalKindPercentage;
		} catch (Exception excp) {
			logger.error("Error while getting total kind percentage");
			throw new DataAccessException(
					"Error while getting total kind percentage.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully got total kind percentage :"+totalKindPercentage);
		return totalKindPercentage;
	}

}
