package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.loan.ChargesDebit;
import com.vsoftcorp.kls.business.entity.loan.ChargesMaster;
import com.vsoftcorp.kls.business.entity.loan.ChargesRecovery;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDebitDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class ChargesDebitDAO implements IChargesDebitDAO {
	private static final Logger logger = Logger.getLogger(ChargesDebitDAO.class);

	@Override
	public void saveChargesDebit(ChargesDebit master) {
		// TODO Auto-generated method stub
		EntityManager em = EntityManagerUtil.getEntityManager();

		logger.info("Start: Saving the Charges Debit data to the database in saveChargesDebit() method.");
		try {
			//em.getTransaction().begin();
			em.merge(master);
			//em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the Charges Debit"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Charges Debit data to the database.",
					excp.getCause());
		} 
		
		logger.info("End: Successfully saved the Charges Debit to the database in saveChargesDebit() method.");
	}

	@Override
	public ChargesDebit getChargesDebit(ChargesDebit master, boolean isCloseSession) {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching the Charges debit from the database in getChargesDebit() method.");
		ChargesDebit chargesDebit = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long chargesDebitId = master.getId();
			if (chargesDebitId != null) {
				chargesDebit = em.find(ChargesDebit.class, chargesDebitId);
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the Charges Debit from the "
					+ "database using Charges Debit id.Exception thrown.");
			throw new DataAccessException("Could not fetch the Charges Debit from the database.",
					excp.getCause());
		} finally {
			if (isCloseSession) {
				EntityManagerUtil.closeSession();
			}
		}
		logger.info("End: Successfully fetched the Charges Debit from the database in getChargesDebit() method.");
		return chargesDebit;
	}

	@Override
	public List<ChargesDebit> getAllChargesDebit(Long lineOfCreditID) {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching all the charges data from the database in getAllChargesDebit() method.");
		List<ChargesDebit> chargesDebitList = new ArrayList<ChargesDebit>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			chargesDebitList = em.createQuery(
					"SELECT c FROM ChargesDebit c where c.lineOfCredit.id= " + lineOfCreditID)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all charges debit from the database");
			throw new DataAccessException(
					"Error while retriving all charges debit from the database", e.getCause());
		} 
		logger.info("End: Fetching all the charges data from the database in getAllChargesDebit() method.");
		return chargesDebitList;
	}

	@Override
	public void deleteChargesDebit(Long chargesDebitID, Long lineOfCreditID) {
		// TODO Auto-generated method stub
		logger.info("Start: Deleting the Charges Debit data to the database in deleteChargesDebit() method.with ChargeDebit ID: "
				+ chargesDebitID + " and Line_Of_Credit_ID: " + lineOfCreditID);
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			Query query = em.createQuery("delete from ChargesDebit c where c.id =" + chargesDebitID
					+ " and c.lineOfCredit.id=" + lineOfCreditID);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			logger.error("Error while Deleting ChargesDebit.");
			throw new DataAccessException("Error while Deleting ChargesDebit.", e.getCause());
		}

	}

	@Override
	public ChargesMaster getChargesDebitByVoucher(String voucherNumber) {
		logger.info("Start: getting charges master by voucher no");
		ChargesMaster charges = new ChargesMaster();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery(
							"select cd.chargesMaster from ChargesDebit cd where cd.voucherNumber=:voucherNumber")
					.setParameter("voucherNumber", voucherNumber);
			charges = (ChargesMaster) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		} catch (Exception e) {
			logger.error("Error while getting  ChargesMaster by voucher no.:" + voucherNumber);
			throw new DataAccessException("Error while  ChargesMaster by voucher no..",
					e.getCause());
		}
		return charges;
	}
	
	
	

	@Override
	public void saveChargesRecovery(ChargesRecovery chargesRecovery) {
		// TODO Auto-generated method stub
		
		
		EntityManager em = EntityManagerUtil.getEntityManager();

		logger.info("Start: Saving the Charges Debit data to the database in saveChargesDebit() method.");
		boolean flgTrans = false;
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			em.persist(chargesRecovery);
			if (flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the Charges Debit"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Charges Debit data to the database.",
					excp.getCause());
		}

		logger.info("End: Successfully saved the Charges Debit to the database in saveChargesDebit() method.");
		
	}
	
	
	@Override
	public void updateChargesDebit(ChargesDebit theMaster) {
		logger.info("Start: Updating the ChargesDebit data to the database in updateRecoverySequence() method.");
		ChargesDebit master = new ChargesDebit();
		boolean flgTrans = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			master = em.find(ChargesDebit.class, theMaster.getId());
			master.setBalanceAmount(theMaster.getBalanceAmount());
			em.merge(master);
			if (flgTrans)
				em.getTransaction().commit();

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the ChargesDebit "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the ChargesDebit data to the database.",
					excp.getCause());
		}
		if (master == null) {
			logger.error("Trying to update the ChargesDebit record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the ChargesDebit record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the ChargesDebit data to the database in updateRecoverySequence() method.");
	}

	@Override
	public List<ChargesRecovery> getChargesMasterListByVoucher(
			String voucherNumber, Date bussinessDate) {
		// TODO Auto-generated method stub
		logger.info("Start: getting charges master by voucher no");
		List<ChargesRecovery> chargesList = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery(
							"select cd from ChargesRecovery cd where cd.voucherNumber=:voucherNumber and cd.businessDate = :businessDate")
					.setParameter("voucherNumber", voucherNumber).setParameter("businessDate", bussinessDate);
			chargesList = query.getResultList();
		} catch (NoResultException exception) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting  ChargesMaster by voucher no.:" + voucherNumber);
			throw new DataAccessException("Error while  ChargesMaster by voucher no..",
					e.getCause());
		}
		return chargesList;
	}

	@Override
	public ChargesMaster getChargesDebitByVoucher(String voucherNumber,
			Date bussinessDate) {
	
			logger.info("Start: getting charges master by voucher no");
			ChargesMaster charges = new ChargesMaster();
			try {
				EntityManager em = EntityManagerUtil.getEntityManager();
				Query query = em
						.createQuery(
								"select cd.chargesMaster from ChargesDebit cd where cd.voucherNumber=:voucherNumber and cd.businessDate = :businessDate")
						.setParameter("voucherNumber", voucherNumber).setParameter("businessDate",bussinessDate);
				charges = (ChargesMaster) query.getSingleResult();
			} catch (NoResultException exception) {
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error while getting  ChargesMaster by voucher no.:" + voucherNumber);
				throw new DataAccessException("Error while  ChargesMaster by voucher no..",
						e.getCause());
			}
			return charges;
		}
	}
		
	
	
	
	
	
	

