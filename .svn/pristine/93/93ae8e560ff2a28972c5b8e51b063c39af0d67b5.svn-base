package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.DayStatus;
import com.vsoftcorp.kls.valuetypes.PacsStatus;

/**
 * 
 * @author a9153 This class has DAO implementations for Pacs
 * 
 */

public class PacsDAO implements IPacsDAO {

	private static final Logger logger = Logger.getLogger(PacsDAO.class);

	/**
	 * Saves pacs to the database.
	 */
	public void savePacs(Pacs thePacs) {

		logger.info("Start: Saving the pacsdata to the database in savePacs() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info("Got connection" + em);
			em.getTransaction().begin();
			em.persist(thePacs);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction for saving the pacs" + "data to the database.Exception thrown.");
			excp.printStackTrace();
			throw new DataAccessException("Could not commit the transaction for saving the pacsdata to the database.", excp.getCause());
		}
		logger.info("End: Successfully saved the pacs to the database in savePacs() method.");
	}

	/**
	 * Updates an existing pacs in the database.
	 */
	public void updatePacs(Pacs thePacs) {

		logger.info("Start: Updating the pacs data to the database in updatePacs() method.");
		Pacs master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer pacsId = thePacs.getId();
			if (pacsId != null) {
				master = em.find(Pacs.class, pacsId);
				if (master != null) {
					logger.info("inside dataaccesse layer::"+thePacs.getLandValidation());
					em.getTransaction().begin();
					master.setName(thePacs.getName());
					master.setLocation(thePacs.getLocation());
					master.setSecretaryName(thePacs.getSecretaryName());
					master.setBranch(thePacs.getBranch());
					master.setVillage(thePacs.getVillage());
					master.setPacsBankAccNumber(thePacs.getPacsBankAccNumber());
					master.setCashGl(thePacs.getCashGl());
					master.setMarginGl(thePacs.getMarginGl());
					master.setVoucherResetFrequency(thePacs.getVoucherResetFrequency());
					master.setInventoryImplemented(thePacs.getInventoryImplemented());
					master.setBankPacsAccNumber(thePacs.getBankPacsAccNumber());
					master.setCashInTransitGL(thePacs.getCashInTransitGL());
					master.setIsBorrwingTransRequiredInGLExtract(thePacs.getIsBorrwingTransRequiredInGLExtract());
					master.setPacsGlExtarctUpload(thePacs.getPacsGlExtarctUpload());
					master.setIsInterestPaidEditable(thePacs.getIsInterestPaidEditable());
					master.setLandValidation(thePacs.getLandValidation());
					logger.info("inside dataaccesse layer::"+thePacs.getLandValidation());
					master.setCashInTransitAccNo(thePacs.getCashInTransitAccNo());
					master.setPacsStatus(thePacs.getPacsStatus());
					master.setDayStatus(thePacs.getDayStatus());
					em.merge(master);
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the pacs" + "data to the database.Exception thrown.");
			excp.printStackTrace();
			throw new DataAccessException("Could not commit the transaction of updating the pacsdata to the database.", excp.getCause());
		}
		if (master == null) {
			logger.error("Trying to update the pacs record which does not exist in the database.");
			throw new DataAccessException("Trying to update the pacsrecord which does not exist in the database.");
		}
		logger.info("End: Successfully updated the pacsdata to the database in updatePacs() method.");
	}

	/**
	 * Retrives and returns the pacs record from database if already exists
	 * 
	 */
	public Pacs getPacs(Pacs thePacs) {
		logger.info("Start: Inside method getPacs()");
		Pacs pacsMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer pacsId = thePacs.getId();
			if (pacsId != null) {
				pacsMaster = em.find(Pacs.class, pacsId);
			}
		} catch (Exception e) {
			logger.error("Error while retriving pacs from the database");
			throw new DataAccessException("Error while retriving pacs from the database.", e.getCause());
		}
		logger.info("End: Inside method getPacs()");
		return pacsMaster;
	}

	public Pacs getPacs(Integer pacsId) {
		logger.info("Start: Inside method getPacs()");
		Pacs pacsMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (pacsId != null) {
				pacsMaster = em.find(Pacs.class, pacsId);
			}
		} catch (Exception e) {
			logger.error("Error while retriving pacs from the database");
			throw new DataAccessException("Error while retriving pacs from the database.", e.getCause());
		}
		logger.info("End: Inside method getPacs()");
		return pacsMaster;
	}

	/**
	 * Returns all the pacsrecords to the client.
	 */
	public List<Pacs> getAllPacs() {

		logger.info("Start: Fetching all the pacs data from the database in getAllPacs() method.");
		List<Pacs> pacsList = new ArrayList<Pacs>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			pacsList = em.createQuery("SELECT v FROM Pacs v").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all pacs from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all pacs from the database", e.getCause());
		}
		logger.info("End: Fetching all the pacs data from the database in getAllPacs() method.");
		return pacsList;
	}

	@Override
	public List<Pacs> getAllPacsByBranch(Integer branchId) {
		logger.info("Start: Fetching all the pacs data from the database in getAllPacsByBranch() method.");
		List<Pacs> pacsList = new ArrayList<Pacs>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT v FROM Pacs v where v.branch.id=:branchId");
			query.setParameter("branchId", branchId);
			pacsList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all pacs based on Branch from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all pacs based on Branch from the database", e.getCause());
		}
		logger.info("End: Fetching all the pacs data from the database in getAllPacsByBranch() method.");
		return pacsList;
	}

	/**
	 * This api is not to close the session using entity manager. Setting the
	 * isCloseSession value to false, will not close the Entity manager session.
	 * 
	 */
	public Pacs getPacs(Pacs thePacs, boolean isCloseSession) {

		logger.info("Start: Inside method getPacs()");
		Pacs pacsMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer pacsId = thePacs.getId();
			logger.info(" pacsId : " + pacsId);
			if (pacsId != null) {
				pacsMaster = em.find(Pacs.class, pacsId);
			}
		} catch (Exception e) {
			logger.error("Error while retriving pacs from the database");
			throw new DataAccessException("Error while retriving pacs from the database.", e.getCause());
		}
		logger.info("End: Inside method getPacs()");
		return pacsMaster;
	}

	@Override
	public Pacs getPacsByPacId(Integer pacsId) {
		logger.info("Start: Inside method getPacsByPacId()");
		EntityManager em = EntityManagerUtil.getEntityManager();

		Pacs entity = null;
		try {
			entity = em.find(Pacs.class, pacsId);
		} catch (Exception e) {
			e.getMessage();
			logger.error("Error while retriving pacs data based on pacid from the database");
			throw new DataAccessException("Error while retriving pacs data from the database.", e.getCause());
		}
		logger.info("End: Inside method getPacsByPacId()..");

		return entity;
	}

	@Override
	public List<Pacs> getClosedOfflinePacs() {
		
		logger.info("Start: Fetching all closed Offline pacs data from the database in getClosedOfflinePacs() method.");
		List<Pacs> pacsList = new ArrayList<Pacs>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM Pacs p where p.dayStatus=:dayStatus and p.pacsStatus= :pacsStatus");
			query.setParameter("dayStatus", DayStatus.CLOSED);
			query.setParameter("pacsStatus", PacsStatus.Offline);
			pacsList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all closed Offline pacs  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all closed Offline pacs from the database", e.getCause());
		}
		logger.info("End:Fetching all closed Offline pacs data from the database in getClosedOfflinePacs() method.");
		return pacsList;
	}
	
	@Override
	public List<Pacs> getSycedPacs() {
		
		logger.info("Start: Fetching all closed Offline pacs data from the database in getClosedOfflinePacs() method.");
		List<Pacs> pacsList = new ArrayList<Pacs>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM Pacs p where p.pacsStatus= :pacsStatus");
			query.setParameter("pacsStatus", PacsStatus.Syncd);
			pacsList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all closed Offline pacs  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all closed Offline pacs from the database", e.getCause());
		}
		logger.info("End:Fetching all closed Offline pacs data from the database in getClosedOfflinePacs() method.");
		return pacsList;
	}

	@Override
	public void updatePacsStatus(List<Integer> pacsIdsList) {
		
		logger.info("Start: Updating day status of closed pacs in updatePacsStatus()");
		boolean flgTrans = false;
		try {
			
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			Query query = em.createQuery("update Pacs set dayStatus=:dayStatus where id in (:pacsIdsList)");
			query.setParameter("dayStatus", DayStatus.OPEN);
			query.setParameter("pacsIdsList", pacsIdsList);
			query.executeUpdate();
			if (flgTrans)
				em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("End: Updating day status of closed pacs in updatePacsStatus()");
	}


}
