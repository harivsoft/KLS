package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.userexceptions.AccountDoesntExistsException;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * This Entity class used to do CRUD operations with 'account' table
 * 
 * @author a1605
 * 
 */
public class AccountDAO implements IAccountDAO {
	private static final Logger logger = Logger.getLogger(AccountDAO.class);

	@Override
	public void saveAccount(Account account) {

		logger.info("Start: Saving the Account data to the database in saveAccount() method.");
		boolean flag=false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if(!em.getTransaction().isActive()){
				em.getTransaction().begin();
				flag=true;
			}
			em.persist(account);
			if(flag)
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the Account"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Account data to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully saved the Account to the database in saveAccount() method.");
	}

	@Override
	public void updateAccount(Account account) {
		logger.info("Start: Updating the Account data to the database in updateAccount() method.");
		Account dbAccount = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = account.getId();
			if (id != null) {
				dbAccount = em.find(Account.class, id);
				if (dbAccount != null) {
					em.getTransaction().begin();
					dbAccount.setId(account.getId());
					dbAccount.setAccountNumber(account.getAccountNumber());
					dbAccount.setAccountProperty(account.getAccountProperty());
					dbAccount.setCloseDate(account.getCloseDate());
					dbAccount.setOpenDate(account.getOpenDate());
					dbAccount.setStatus(account.getStatus());
					em.getTransaction().commit();
				}
			}

		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the Account"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Account data to the database.",
					excp.getCause());
		}
		if (dbAccount == null) {
			logger.error("Trying to update the Account record which does not exist in the database.");
			throw new AccountDoesntExistsException(
					"Trying to update the Account record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the Account data to the database in updateAccount() method.");
	}

	@Override
	public Account getAccount(Account theAccount) {

		logger.info("Start: Fetching the Account data from the database in getAccount() method.");
		Account account = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = theAccount.getId();
			if (id != null)
				account = em.find(Account.class, id);
		} catch (Exception excp) {
			logger.error("Could not fetch the Account master data from the "
					+ "database using Account Id Exception thrown.");
			throw new DataAccessException("Could not fetch the Account data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the Account data from the database in getAccount() method.");
		return account;
	}

	@Override
	public List<Account> getAllAccounts() {

		logger.info("Start: Fetching all the Account data from the database in getAllAccounts() method.");
		List<Account> accountList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			TypedQuery<Account> query = em.createQuery("SELECT v FROM Account v", Account.class);
			accountList = query.getResultList();

			logger.info("getting the list after" + accountList.size());
		} catch (Exception e) {
			logger.error("Error while retriving all Accounts from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all Accounts from the database",
					e.getCause());
		}
		logger.info("End: Fetching all the Account data from the database in getAllAccounts() method.");
		return accountList;
	}

	@Override
	public Account getAccountByCustomer(String customerId) {
		logger.info("Start: Fetching the Account data from the database in getAccountByCustomer() method.");
		Account account = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (customerId != null) {
				Query query = em
						.createQuery("SELECT a FROM Account a where a.accountProperty.customerId = :customerId");
				query.setParameter("customerId", Long.parseLong(customerId));
				List list = query.getResultList();
				if (!list.isEmpty())
					account = (Account) list.get(0);
			}
		}catch (NoResultException e){
			return null;
		}catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the Account data from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the Account data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the Account data from the database in getAccountByCustomer() method.");
		return account;
	}

	@Override
	public int getRecentAccount(String branchIdPacsIdProductIdString) {
		// TODO Auto-generated method stub
		logger.info("Fetching the Recent Account number from the database in getRecentAccount() method. branchIdPacsIdProductIdString:"
				+ branchIdPacsIdProductIdString);
		String lastAccount = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery(
							"select max(substring(a.accountNumber,13,18)) from Account a where substring(a.accountNumber,1,12)=:branchIdPacsIdProductIdString")
					.setParameter("branchIdPacsIdProductIdString", branchIdPacsIdProductIdString);
			lastAccount = (String) query.getSingleResult();
		} catch (NoResultException noResultException) {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Could not get recent account from database");
			throw new DataAccessException("Could not get recent account from database.",
					e.getCause());
		}
		logger.info("Successfully Fetched the Recent Account number from the database in getRecentAccount() method.");

		return lastAccount==null?0:Integer.parseInt(lastAccount);
	}

	@Override
	public Account getAccount(String accountNumber) {

		logger.info("Start: Fetching the Account data from the database in getAccount() method.");
		Account account = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" account number " + accountNumber);
			if (accountNumber != null) {
				Query query = em
						.createQuery("SELECT a FROM Account a where a.accountNumber = :accountNumber");
				query.setParameter("accountNumber", accountNumber);
				account = (Account) query.getSingleResult();
				logger.info(" account number " + accountNumber);

			}
		} catch (NoResultException noResultException) {
			logger.error("Account not found for the accountnumber:" + accountNumber);
			// throw new AccountDoesntExistsException("Account not found");
			return null;
		} catch (Exception excp) {
			logger.error("Could not fetch the account data from the "
					+ "database using Account Number.Exception thrown.");

			throw new DataAccessException("Could not fetch the Account data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the Account data from the database in getAccount() method.");
		return account;
	}

	@Override
	public Account getAccount(Long accountPropertyId, boolean isCloseSession) {

		logger.info("Start: Fetching the Account data from the database in getAccount() method.");
		Account account = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (accountPropertyId != null) {
				Query query = em
						.createQuery("SELECT a FROM Account a where a.accountProperty.id = :accountPropertyId");
				query.setParameter("accountPropertyId", accountPropertyId);
				account = (Account) query.getSingleResult();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the account data from the "
					+ "database using Account Property Id.Exception thrown.");

			throw new DataAccessException("Could not fetch the Account data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the Account data from the database in getAccount() method.");
		return account;
	}

	/**
	 * This api is not to close the session using entity manager. Setting the
	 * isCloseSession value to false, will not close the Entity manager session.
	 * 
	 */
	public Account getAccount(Account theAccount, boolean isCloseSession) {

		logger.info("Start: Fetching the Account data from the database in getAccount() method.");
		Account account = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = theAccount.getId();
			if (id != null)
				account = em.find(Account.class, id);
		} catch (Exception excp) {
			logger.error("Could not fetch the Account master data from the "
					+ "database using Account Id Exception thrown.");
			throw new DataAccessException("Could not fetch the Account data from the database.",
					excp.getCause());
		} /*
		 * finally { if (isCloseSession) { EntityManagerUtil.closeSession(); } }
		 */
		logger.info("End: Successfully fetched the Account data from the database in getAccount() method.");
		return account;
	}

	@Override
	public Account getAccountById(Long accountId, boolean isCloseSession) {

		logger.info("Start: Fetching the Account from the database in getAccountById() method.");
		Account account = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (accountId != null)
				account = em.find(Account.class, accountId);
		} catch (Exception excp) {
			logger.error("Could not fetch the Account from the "
					+ "database using Account Id Exception thrown.");
			throw new DataAccessException("Could not fetch the Account from the database.",
					excp.getCause());
		} /*
		 * finally { if (isCloseSession) { EntityManagerUtil.closeSession(); } }
		 */
		logger.info("End: Successfully fetched the Account from the database in getAccountById() method.");
		return account;
	}

	@Override
	public void deleteAccount(Long accountId) {

		logger.info("Start: Deleting the account  data in database in deleteProductChargesMapping() method.");
		try {

			EntityManager em = EntityManagerUtil.getEntityManager();
			Account account = getAccountById(accountId, false);
			if (account != null) {
				em.getTransaction().begin();
				em.remove(account);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of Deleting the account  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Deleting the account data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully Deleting the account data to the database in deleteProductChargesMapping() method.");

	}

	@Override
	public List<String> getAccountNumbersByPacsId(Integer pacsId) {
		logger.info("Start: Fetching the AccountProperty from the database in getAccountNumbersByPacsId() method.");

		List<String> list = new ArrayList();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			if (pacsId != null) {
				Query query = em
						.createQuery(
								"SELECT distinct a.accountNumber FROM Account a,AccountProperty ap where ap.pacs.id = :pacsId and ap.id=a.accountProperty.id")
						.setParameter("pacsId", pacsId);
				list = query.getResultList();
				/*query = em
						.createQuery(
								"SELECT distinct a.accountNumber FROM Account a,LoanAccountProperty lap where lap.pacs.id = :pacsId and lap.account.id=a.id and lap.accountPropertyType=:accountPropertyType")
						.setParameter("pacsId", pacsId).setParameter("accountPropertyType", "L");
				list.addAll(query.getResultList());*/

			}
		} catch (Exception e) {
			logger.error("Could not fetch the AccountProperty from the "
					+ "database using pacsId  Exception thrown.");
			throw new DataAccessException("Could not fetch the AccountProperty from the database.",
					e.getCause());
		}
		return list;
	}
	
	@Override
	public Account getAccountByCardNumber(String cardNumber) {
		logger.info("Start: Fetching the Account data from the database in getAccountByCardNumber() method.");
		Account account = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (cardNumber != null) {
				Query query = em
						.createQuery("SELECT a FROM Account a where a.accountProperty.cardNumber = :cardNumber");
				query.setParameter("cardNumber", cardNumber);
				account = (Account) query.getSingleResult();
			}
		}catch (NoResultException e){
			logger.error("No results fount with card number="+cardNumber);
			return null;
		}catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the Account data from the "
					+ "database using cardNumber  Exception thrown.");
			throw new DataAccessException("Could not fetch the Account data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the Account data from the database in getAccountByCardNumber() method.");
		return account;
	}
}
