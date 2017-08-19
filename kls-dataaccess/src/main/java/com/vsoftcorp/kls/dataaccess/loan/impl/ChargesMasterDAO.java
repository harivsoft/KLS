package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.loan.ChargesMaster;
import com.vsoftcorp.kls.dataaccess.loan.IChargesMasterDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class ChargesMasterDAO implements IChargesMasterDAO {
	private static final Logger logger = Logger.getLogger(ChargesMasterDAO.class);

	@Override
	public void saveChargesMaster(ChargesMaster master) {

		logger.info("Start: Saving the Charges Master data to the database in saveAccount() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(master);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the Charges Master"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Charges Master data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the Charges Master to the database in saveAccount() method.");
	}

	@Override
	public ChargesMaster getChargesMaster(ChargesMaster master, boolean isCloseSession) {

		logger.info("Start: Fetching the Charges Master from the database in getChargesMaster() method.");
		ChargesMaster chargesMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long chargesMasterId = master.getId();
			if (chargesMasterId != null) {
				chargesMaster = em.find(ChargesMaster.class, chargesMasterId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the Charges Master from the "
					+ "database using Charges Master id.Exception thrown.");
			throw new DataAccessException("Could not fetch the Charges Master from the database.", excp.getCause());
		} finally {
			if (isCloseSession) {
				EntityManagerUtil.closeSession();
			}
		}
		logger.info("End: Successfully fetched the Charges Master from the database in getChargesMaster() method.");
		return chargesMaster;
	}

	@Override
	public List<ChargesMaster> getAllChargesMaster() {

		logger.info("Start: Fetching all the charges data from the database in getAllChargesMaster() method.");
		List<ChargesMaster> chargesList = new ArrayList<ChargesMaster>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			chargesList = em.createQuery("SELECT c FROM ChargesMaster c").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all charges from the database");
			throw new DataAccessException("Error while retriving all charges from the database", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the charges data from the database in getAllChargesMaster() method.");
		return chargesList;
	}

	@Override
	public void updateChargesMaster(ChargesMaster thecharges) {

		logger.info("Start: Updating the charges  data to the database in updateChargesMaster() method.");
		ChargesMaster charges = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = thecharges.getId();
			if (id != null) {
				charges = em.find(ChargesMaster.class, id);
				if (charges != null) {
					em.getTransaction().begin();
					charges.setChargesCode(thecharges.getChargesCode());
					charges.setChargesDescription(thecharges.getChargesDescription());
					charges.setChargesType(thecharges.getChargesType());
					charges.setMaxAmount(thecharges.getMaxAmount());
					charges.setMinAmount(thecharges.getMinAmount());
					charges.setChargesReceivableGL(thecharges.getChargesReceivableGL());
					charges.setChargesReceivedGL(thecharges.getChargesReceivedGL());
					charges.setBankChargesReceivableGL(thecharges.getBankChargesReceivableGL());
					charges.setBankChargesReceivedGL(thecharges.getBankChargesReceivedGL());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the charges"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the charges  data to the database.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (charges == null) {
			logger.error("Trying to update the charges  record which does not exist in the database.");
			throw new DataAccessException("Trying to update the charges  record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the charges  data to the database in updateChargesMaster() method.");
	}

}
