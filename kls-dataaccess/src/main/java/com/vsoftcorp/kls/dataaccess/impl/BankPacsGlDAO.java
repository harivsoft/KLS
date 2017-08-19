package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BankPacsGl;
import com.vsoftcorp.kls.dataaccess.IBankPacsGlDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a9153 This class has DAO implementations for BankPacsGl
 * 
 */

public class BankPacsGlDAO implements IBankPacsGlDAO {

	private static final Logger logger = Logger
			.getLogger(BankPacsGlDAO.class);

	/**
	 * Saves bankPacsGl to the database.
	 */
	public void saveBankPacsGl(BankPacsGl theBankPacsGlMaster) {

		logger.info("Start: Saving the bankPacsGl master data to the database in saveBankPacsGl() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info("Got connection" + em);
			em.getTransaction().begin();
			em.persist(theBankPacsGlMaster);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the bankPacsGl master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the bankPacsGl master data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the bankPacsGl master to the database in saveBankPacsGl() method.");
	}

	/**
	 * Updates an existing bankPacsGl in the database.
	 */
	public void updateBankPacsGl(BankPacsGl theBankPacsGlMaster) {
		
		logger.info("Start: Updating the bankPacsGl master data to the database in updateBankPacsGl() method.");
		BankPacsGl master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer  bankPacsGlCode = theBankPacsGlMaster.getId();
			if (bankPacsGlCode != null) {
				master = em.find(BankPacsGl.class, bankPacsGlCode);
				if (master != null) {
					em.getTransaction().begin();
					master.setAccountNo(theBankPacsGlMaster.getAccountNo());
					master.setBankCode(theBankPacsGlMaster.getBankCode());
					master.setBranch(theBankPacsGlMaster.getBranch());
					master.setPacs(theBankPacsGlMaster.getPacs());
					master.setPosDeviceNo(theBankPacsGlMaster.getPosDeviceNo());
					
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the bankPacsGl master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the bankPacsGl master data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the bankPacsGl master record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the bankPacsGl master record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the bankPacsGl master data to the database in updateBankPacsGl() method.");
	}

	/**
	 * Retrives and returns the bankPacsGl record from database if already exists
	 * 
	 */
	public BankPacsGl getBankPacsGl(BankPacsGl theBankPacsGlMaster, boolean isCloseSession) {
		logger.info("Start: Inside method getBankPacsGl()");
		BankPacsGl bankPacsGlMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer  bankPacsGlCode = theBankPacsGlMaster.getId();
			if (bankPacsGlCode != null) {
				bankPacsGlMaster = em.find(BankPacsGl.class, bankPacsGlCode);
			} 
		} catch (Exception e) {
			logger.error("Error while retriving bankPacsGl from the database");
			throw new DataAccessException(
					"Error while retriving bankPacsGl from the database.",
					e.getCause());
		}
		finally{
			if (isCloseSession)
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getBankPacsGl()");
		return bankPacsGlMaster;
	}
	
	
	/**
	 * Returns all the bankPacsGl master records to the client.
	 */
	public List<BankPacsGl> getAllBankPacsGls(boolean isCloseSession) {

		logger.info("Start: Fetching all the bankPacsGl master data from the database in getAllBankPacsGls() method.");
		List<BankPacsGl> bankPacsGlMasterList = new ArrayList<BankPacsGl>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			bankPacsGlMasterList = em.createQuery("SELECT v FROM BankPacsGl v")
					.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all bankPacsGls from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all bankPacsGls from the database",
					e.getCause());
		}
		finally{
			if (isCloseSession)
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the bankPacsGl master data from the database in getAllBankPacsGls() method.");
		return bankPacsGlMasterList;
	}

	public String getBankPacsGlAccNo(String bankCode, int branchId, int pacsId, String deviceNo) {
		logger.info("Start: Fetching bankPacsGl for given bankid, pacsid, branchid and deviceno from the database.  Inside getBankPacsGl() method.");
		List<String> accountNoList = new ArrayList<String>();
		String accountNo = "";
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select bp.accountNo from BankPacsGl bp where bp.bankCode =:bankId and bp.branch.id =:branchId and bp.pacs.id =:pacsId and bp.posDeviceNo =:deviceNo");
			Query qry = em.createQuery(query.toString());

			qry.setParameter("bankId", bankCode);
			qry.setParameter("branchId", branchId);
			qry.setParameter("pacsId", pacsId);
			qry.setParameter("deviceNo", deviceNo);
			accountNoList = qry.getResultList();
			
			if (!accountNoList.isEmpty()){
				accountNo = accountNoList.get(0);
			}
			
		} catch (Exception e) {
			logger.error("Error while retriving bankPacsGl from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving bankPacsGl from the database", e.getCause());
		}

		logger.info("End: Fetching bankPacsGl for given bankid, pacsid, branchid and deviceno from the database.  Inside getBankPacsGl() method.");
		return accountNo;
	}

    @Override
	public String getBankPacsGlAccNo(String deviceNo) {
		logger.info("Start: Fetching bankPacsGl for given deviceno from the database.  Inside getBankPacsGl() method.");
		List<String> accountNoList = new ArrayList<String>();
		String accountNo = "";
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select bp.accountNo from BankPacsGl bp where bp.posDeviceNo =:deviceNo");
			Query qry = em.createQuery(query.toString());

			qry.setParameter("deviceNo", deviceNo);
			accountNoList = qry.getResultList();
			
			if (!accountNoList.isEmpty()){
				accountNo = accountNoList.get(0);
			}
			
		} catch (Exception e) {
			logger.error("Error while retriving bankPacsGl from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving bankPacsGl from the database", e.getCause());
		}

		logger.info("End: Fetching bankPacsGl for given bankid, pacsid, branchid and deviceno from the database.  Inside getBankPacsGl() method.");
		return accountNo;
	}
	
	@Override
	public void deleteBankPacsGl(Integer bankPacsGlId) {
		
		// TODO Auto-generated method stub
				logger.info("Start: Deleting the BankPacsGl data to the database in deleteBankPacsGl() method.with BankPacsGl ID: " + bankPacsGlId  );
						
				EntityManager em = EntityManagerUtil.getEntityManager();
				BankPacsGl bankPacsGl=null;
				em.getTransaction().begin();
				try {
					bankPacsGl = em.find(BankPacsGl.class, bankPacsGlId);
					em.remove(bankPacsGl);
					em.getTransaction().commit();
				} catch (Exception e) {
					//em.getTransaction().rollback();
					e.printStackTrace();
					logger.error("Error while Deleting BankPacsGl.");
					throw new DataAccessException("Error while Deleting BankPacsGl.", e.getCause());
				}
	
     }
}