package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.EventType;
import com.vsoftcorp.kls.business.entities.EventTypeDefinition;
import com.vsoftcorp.kls.business.entities.ProductType;
import com.vsoftcorp.kls.dataaccess.IRecoveryOrderDao;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class RecoveryOrderDAO implements IRecoveryOrderDao {
	private static final Logger logger = Logger
			.getLogger(RecoveryOrderDAO.class);

	@Override
	public EventTypeDefinition saveRecoverySequence(
			EventTypeDefinition theRecoverySeq) {

		logger.info("Start: Saving the Recovery Sequence data to the database in saveRecoverySequence() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(theRecoverySeq);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the RecoverySequence "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the RecoverySequence data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the Recovery Sequence to the database in saveRecoverySequence() method.");
		return theRecoverySeq;
	}

	@Override
	public List<EventTypeDefinition> getAllEventTypeDefinitions(boolean isCloseSession) {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching all the Recovery Sequence data from the database in getAllEventTypeDefinitions() method.");
		List<EventTypeDefinition> evntTypeDefList = new ArrayList<EventTypeDefinition>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			evntTypeDefList = em.createQuery(
					"SELECT e FROM EventTypeDefinition e").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all Recovery Sequence records from the database.");
			throw new DataAccessException(
					"Error while retriving all Recovery Sequence records.",
					e.getCause());
		} finally {
				if (isCloseSession) {
			EntityManagerUtil.closeSession();
		}
	}
		logger.info("End: Fetching all the Recovery Sequence data from the database in getAllEventTypeDefinitions() method.");
		return evntTypeDefList;
	}

	@Override
	public List<EventType> getAllEventTypeBasedOnEventDefinition(
			Integer eventDefId, boolean isCloseSession) {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching all the Recovery Sequence data from the database in getAllEventTypeBasedOnEventDefinition() method.");
		List<EventType> evntTypeList = new ArrayList<EventType>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT et FROM EventType et where et.eventDefId.id=:eventDefId order by 1");
			query.setParameter("eventDefId", eventDefId);
			evntTypeList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all Recovery Sequence records from the database.");
			throw new DataAccessException(
					"Error while retriving all Recovery Sequence records.",
					e.getCause());
		} finally{
			if (isCloseSession)
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Recovery Sequence data from the database in getAllEventTypeBasedOnEventDefinition() method.");
		return evntTypeList;
	}

	@Override
	public void updateRecoverySequence(EventTypeDefinition theMaster) {

		logger.info("Start: Updating the Recovery Sequence data to the database in updateRecoverySequence() method.");
		EventTypeDefinition master = new EventTypeDefinition();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			master = em.find(EventTypeDefinition.class, theMaster.getId());
			master.setSequenceName(theMaster.getSequenceName());
			em.merge(master);
			em.getTransaction().commit();

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the Recovery Sequence "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Recovery Sequence data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the Recovery Sequence record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the Recovery Sequence record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the Recovery Sequence data to the database in updateRecoverySequence() method.");
	}

	@Override
	public void saveEventType(EventType theRecoverySeq) {

		logger.info("Start: Saving the Recovery Sequence data to the database in saveEventType() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(theRecoverySeq);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the RecoverySequence "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the RecoverySequence data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the Recovery Sequence to the database in saveEventType() method.");
	}

	@Override
	public void updateEventType(EventType theMaster) {

		logger.info("Start: Updating the Recovery Sequence data to the database in updateRecoverySequence() method.");
		EventType master = new EventType();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			logger.info("in dao id ==="+theMaster.getId());
			master = em.find(EventType.class, theMaster.getId());
			logger.info("after retrieving values::"+master.getRecoverySequence());
			logger.info("value sending from service::"+theMaster.getRecoverySequence());
			master.setRecoverySequence(theMaster.getRecoverySequence());
			logger.info("after setting values::"+master.getRecoverySequence());
			em.merge(master);
			em.getTransaction().commit();

		} catch (Exception excp) {
			// excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the Recovery Sequence "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Recovery Sequence data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the Recovery Sequence record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the Recovery Sequence record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the Recovery Sequence data to the database in updateRecoverySequence() method.");
	}

	@Override
	public EventTypeDefinition getEventTypeDefinition(
			EventTypeDefinition theEventDef, boolean isCloseSession) {

		logger.info("Start: Fetching the Recovery Sequence data from the database in getEventTypeDefinition() method.");
		EventTypeDefinition master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer eventDefId = theEventDef.getId();
			if (eventDefId != null) {
				master = em.find(EventTypeDefinition.class, eventDefId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the Recovery Sequence data from the "
					+ "database using Recovery Sequence code.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the Recovery Sequence data from the database.",
					excp.getCause());
		}finally{
			if (isCloseSession)
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the Recovery Sequence data from the database in getEventTypeDefinition() method.");
		return master;
	}

	@Override
	public EventType getEventType(EventType theEventType, boolean isCloseSession) {

		logger.info("Start: Fetching the Recovery Sequence data from the database in getEventType() method.");
		EventType master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer eventTypeId = theEventType.getId();
			if (eventTypeId != null) {
				master = em.find(EventType.class, eventTypeId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the Recovery Sequence data from the "
					+ "database using Recovery Sequence code.Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the Recovery Sequence data from the database.",
					excp.getCause());
		}finally{
			if (isCloseSession)
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the Recovery Sequence data from the database in getEventType() method.");
		return master;
	}

}
