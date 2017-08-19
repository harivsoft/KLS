package com.vsoftcorp.kls.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Activity;
import com.vsoftcorp.kls.dataaccess.IActivityDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class ActivityDAO implements IActivityDAO {

	private static final Logger logger = Logger.getLogger(ActivityDAO.class);

	@Override
	public void saveActivity(Activity data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling saveActivity() method in ActivityDAO ..!");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(data);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Unable to commit the transaction of Activity data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of saving the Activity data.",
					excp.getCause());
		}
		 
		logger.info("END:Successfully Completed saving the Purpsoe data in saveActivity()");
	}

	@Override
	public void updateActivity(Activity data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling updateActivity() method in ActivityDAO ..!");
		Activity master = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {

			Integer id = data.getId();
			if (id != null) {
				em.getTransaction().begin();
				master = em.find(Activity.class, id);
				if (data.getName() != null)
					master.setName(data.getName());
				if (data.getMaximumLimit() != null)
					master.setMaximumLimit(data.getMaximumLimit());
				else
					master.setMaximumLimit(null);
				master.setUnitCost(data.getUnitCost());
				if (data.getUnit() != null)
					master.setUnit(data.getUnit());
				em.getTransaction().commit();
			} else {
				logger.error("Trying to update the Activity record which does not exist in the database.");
				throw new DataAccessException(
						"Trying to update the Activity record which does not exist in the database.");
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to update the transaction of Activity data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to update the transaction of updating the Activity data.",
					excp.getCause());
		}
		 
		logger.info("END:Successfully Completed Updating the Purpsoe data in updateActivity() ");
	}

	@Override
	public List<Activity> getAllActivity(boolean isCloseSession) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling getAllActivity() method in ActivityDAO ..!");
		List<Activity> master = null;

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Activity> criteriaQuery = criteriaBuilder
					.createQuery(Activity.class);
			Root<Activity> root = criteriaQuery.from(Activity.class);
			criteriaQuery.select(root);
			master = em.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting Activity data in getAllActivity()");
			throw new DataAccessException(
					"Unable to get all Activity data in getAllActivity()",
					e.getCause());

		}
		logger.info("END:Successfully Completed getting the Purpsoe data in getAllActivity()");
		return master;
	}

	@Override
	public Activity getActivityById(Integer id, boolean isCloseSession) {
		logger.info("Start: Inside method getActivityById()");
		Activity activity = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (id != null) {
				activity = em.find(Activity.class, id);
			}
		} catch (Exception e) {
			logger.error("Error while retriving activity from the database");
			throw new DataAccessException(
					"Error while retriving activity from the database.",
					e.getCause());
		}
		logger.info("End: Inside method getActivityById()");
		return activity;
	}

	@Override
	public void deleteActivity(Integer id) {
		// TODO Auto-generated method stub

	}

}
