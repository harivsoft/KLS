package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BorrowingsAccount;
import com.vsoftcorp.kls.dataaccess.IBorrowingsAccountDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a9153 This class has DAO implementations for BorrowingsAccount
 * 
 */

public class BorrowingsAccountDAO implements IBorrowingsAccountDAO {

	private static final Logger logger = Logger.getLogger(BorrowingsAccountDAO.class);

	/**
	 * Saves borrowingsAccount to the database.
	 */
	public void saveBorrowingsAccount(BorrowingsAccount theBorrowingsAccountMaster) {

		logger.info("Start: Saving the borrowingsAccount master data to the database in saveBorrowingsAccount() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info("Got connection" + em);
			em.getTransaction().begin();
			em.persist(theBorrowingsAccountMaster);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the borrowingsAccount master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the borrowingsAccount master data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the borrowingsAccount master to the database in saveBorrowingsAccount() method.");
	}

	/**
	 * Updates an existing borrowingsAccount in the database.
	 */
	public void updateBorrowingsAccount(BorrowingsAccount theBorrowingsAccountMaster) {

		logger.info("Start: Updating the borrowingsAccount master data to the database in updateBorrowingsAccount() method.");
		BorrowingsAccount master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer borrowingsAccountCode = theBorrowingsAccountMaster.getId();
			if (borrowingsAccountCode != null) {
				master = em.find(BorrowingsAccount.class, borrowingsAccountCode);
				if (master != null) {
					em.getTransaction().begin();
					master.setAccountNo(theBorrowingsAccountMaster.getAccountNo());
					master.setBankCode(theBorrowingsAccountMaster.getBankCode());
					master.setBranch(theBorrowingsAccountMaster.getBranch());
					master.setPacs(theBorrowingsAccountMaster.getPacs());
					master.setProduct(theBorrowingsAccountMaster.getProduct());

					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the borrowingsAccount master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the borrowingsAccount master data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the borrowingsAccount master record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the borrowingsAccount master record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the borrowingsAccount master data to the database in updateBorrowingsAccount() method.");
	}

	/**
	 * Retrives and returns the borrowingsAccount record from database if
	 * already exists
	 * 
	 */
	public BorrowingsAccount getBorrowingsAccount(BorrowingsAccount theBorrowingsAccountMaster, boolean isCloseSession) {
		logger.info("Start: Inside method getBorrowingsAccount()");
		BorrowingsAccount borrowingsAccountMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer borrowingsAccountCode = theBorrowingsAccountMaster.getId();
			if (borrowingsAccountCode != null) {
				borrowingsAccountMaster = em.find(BorrowingsAccount.class, borrowingsAccountCode);
			}
		} catch (Exception e) {
			logger.error("Error while retriving borrowingsAccount from the database");
			throw new DataAccessException("Error while retriving borrowingsAccount from the database.", e.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getBorrowingsAccount()");
		return borrowingsAccountMaster;
	}

	/**
	 * Returns all the borrowingsAccount master records to the client.
	 */
	public List<BorrowingsAccount> getAllBorrowingsAccounts(boolean isCloseSession) {

		logger.info("Start: Fetching all the borrowingsAccount master data from the database in getAllBorrowingsAccounts() method.");
		List<BorrowingsAccount> borrowingsAccountMasterList = new ArrayList<BorrowingsAccount>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			borrowingsAccountMasterList = em.createQuery("SELECT v FROM BorrowingsAccount v").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all borrowingsAccounts from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all borrowingsAccounts from the database",
					e.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the borrowingsAccount master data from the database in getAllBorrowingsAccounts() method.");
		return borrowingsAccountMasterList;
	}

	public String getBorrowingsAccNo(String bankCode, int branchId, int pacsId, int productId) {

		logger.info("Start: Fetching borrowings account no. for given bankid, pacsid, branchid and product id  from the database.  Inside getBorrowingsAccNo() method.");
		List<String> accountNoList = new ArrayList<String>();
		String accountNo = "";
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select ba.accountNo from BorrowingsAccount ba where ba.bankCode =:bankId and ba.branch.id =:branchId and ba.pacs.id =:pacsId and ba.product.id =:productId");
			Query qry = em.createQuery(query.toString());

			qry.setParameter("bankId", bankCode);
			qry.setParameter("branchId", branchId);
			qry.setParameter("pacsId", pacsId);
			qry.setParameter("productId", productId);
			accountNoList = qry.getResultList();
			if (!accountNoList.isEmpty()) {
				accountNo = accountNoList.get(0);
			}
		} catch (Exception e) {
			logger.error("Error while retriving bankPacsGl from the database");
			throw new DataAccessException("Error while retriving borrowings account no. from the database",
					e.getCause());
		}
		logger.info("End: Fetching bankPacsGl for given bankid, pacsid, branchid and product id from the database.  Inside getBorrowingsAccNo() method.");
		return accountNo;
	}

	@Override
	public BorrowingsAccount getPacsBorrowingsAccNo(String bankCode, int branchId, int pacsId, int productId) {

		logger.info("Start: Fetching pacs borrowings account no. for given bankid, pacsid, branchid and product id  from the database in getPacsBorrowingsAccNo() method.");
		BorrowingsAccount borrowingAcct = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select ba from BorrowingsAccount ba where ba.bankCode =:bankId and ba.branch.id =:branchId and ba.pacs.id =:pacsId and ba.product.id =:productId");
			Query qry = em.createQuery(query.toString());
			qry.setParameter("bankId", bankCode);
			qry.setParameter("branchId", branchId);
			qry.setParameter("pacsId", pacsId);
			qry.setParameter("productId", productId);
			borrowingAcct = (BorrowingsAccount) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.error("No record found for pacs borrowing account from the database");
		} catch (NonUniqueResultException e) {
			logger.error("More than 1 pacs borrowing account records found from the database");
		} catch (Exception e) {
			logger.error("Error while retriving pacs borrowing account from the database");
			throw new DataAccessException("Error while retriving pacs borrowings account no. from the database",
					e.getCause());
		}
		logger.info("End: Fetching pacs borrowings account no. for given bankid, pacsid, branchid and product id  from the database in getPacsBorrowingsAccNo() method.");
		return borrowingAcct;
	}
}
