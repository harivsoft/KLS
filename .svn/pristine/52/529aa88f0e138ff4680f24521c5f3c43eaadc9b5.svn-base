/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SBAccountMapping;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.dataaccess.loan.IAccountPropertyDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author a9152
 * 
 */
public class AccountPropertyDAO implements IAccountPropertyDAO {

	private static final Logger logger = Logger.getLogger(AccountPropertyDAO.class);

	@Override
	public AccountProperty getAccountProperty(String savingsAccountNumber, boolean isCloseSession) {

		logger.info("Start: Fetching the Account Property data from the database in getAccountProperty() method.");
		AccountProperty master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (savingsAccountNumber != null) {
				Query query = em.createQuery("SELECT a FROM AccountProperty a where"
						+ " a.savingsAccountNumber = :savingsAccountNumber");
				query.setParameter("savingsAccountNumber", savingsAccountNumber);
				master = (AccountProperty) query.getSingleResult();
			}
		} catch(NoResultException e){
			master = null;
		}catch (Exception excp) {
			logger.error("Could not fetch the account property data.");
			throw new DataAccessException("Could not fetch the account property data.", excp.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the Account Property data from the database in getAccountProperty() method.");
		return master;
	}

	@Override
	public AccountProperty getAccountProperty(AccountProperty accountProperty, boolean isCloseSession) {

		logger.info("Start: Fetching the account property from the database in getAccountPropertyById() method.");
		AccountProperty acctProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (accountProperty != null) {
				Long accountPropertyId = accountProperty.getId();
				logger.info(" accountPropertyId : " + accountPropertyId);
				accountProperty = em.find(AccountProperty.class, accountPropertyId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the account property from the "
					+ "database using account property id.Exception thrown.");
			throw new DataAccessException("Could not fetch the account property from the database.", excp.getCause());
		} finally {
			if (isCloseSession) {
				EntityManagerUtil.closeSession();
			}
		}
		logger.info("End: Successfully fetched the account property from the database in getAccountPropertyById() method.");
		return acctProperty;
	}

	@Override
	public AccountProperty getAccountPropertyById(Long accountPropertyId, boolean isCloseSession) {

		logger.info("Start: Fetching the account property from the database in getAccountPropertyById() method.");
		AccountProperty accountProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (accountPropertyId != null) {
				accountProperty = em.find(AccountProperty.class, accountPropertyId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the account property from the "
					+ "database using account property id.Exception thrown.");
			throw new DataAccessException("Could not fetch the account property from the database.", excp.getCause());
		} finally {
			if (isCloseSession) {
				EntityManagerUtil.closeSession();
			}
		}
		logger.info("End: Successfully fetched the account property from the database in getAccountPropertyById() method.");
		return accountProperty;
	}

	@Override
	public boolean checkIfAccountExists(String custId, boolean isCloseSession) {

		logger.info("Start: Fetching the Account Property from the database in checkIfAccountExists() method.");
		AccountProperty master = null;
		logger.info(" custId : " + custId);
		boolean flag = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (custId != null) {
				Query query = em.createQuery("SELECT a FROM AccountProperty a where"
						+ " a.customerId = :savingsAccountNumber");
				query.setParameter("savingsAccountNumber", custId);
				master = (AccountProperty) query.getSingleResult();
				if (master != null) {
					flag = false;
				}
			}
		} catch (NoResultException excp) {
			logger.info(" Account does not exist.");
			flag = true;
		} catch (NonUniqueResultException excp) {
			logger.error("More than one account exists.");
			flag = false;
		} catch (Exception excp) {
			logger.error("Could not fetch the account property data.");
			flag = false;
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the Account Property from the database in checkIfAccountExists() method.");
		return flag;
	}
	
	@Override
	public boolean checkIfAccountPropertyExists(Long custId, boolean isCloseSession) {

		logger.info("Start: Fetching the Account Property from the database in checkIfAccountPropertyExists() method.");
		AccountProperty master = null;
		logger.info(" custId : " + custId);
		boolean flag = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (custId != null) {
				Query query = em.createQuery("SELECT a FROM AccountProperty a where"
						+ " a.customerId ="+custId);
				master = (AccountProperty) query.getSingleResult();
				if (master != null) {
					flag = true;
				}
			}
		} catch (NoResultException excp) {
			logger.info(" Account does not exist.");
			flag = false;
		} catch (NonUniqueResultException excp) {
			logger.error("More than one account exists.");
			flag = false;
		} catch (Exception excp) {
			logger.error("Could not fetch the account property data.");
			flag = false;
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the Account Property from the database in checkIfAccountPropertyExists() method.");
		return flag;
	}

	@Override
	public AccountProperty getAccountByCustId(Long custId) {
		logger.info("Start: Fetching the AccountProperty from the database in getAccountBycustId() method.");

		AccountProperty master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (custId != null) {
				Query query = em.createQuery("SELECT a FROM AccountProperty a where" + " a.customerId = :custId");
				query.setParameter("custId", custId);
				List<AccountProperty> masterList = (List<AccountProperty>) query.getResultList();
				if (masterList != null && !masterList.isEmpty()) {
					master = masterList.get(0);
				}
			}
		}catch (NoResultException ex) {
			return null;
			
		}catch (Exception e) {
			logger.error("Could not fetch the AccountProperty from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the AccountProperty from the database.", e.getCause());
		}
		return master;
	}

	@Override
	public String mapSavingsAccountWithCust(Long custId, String accountNo) {
		logger.info("Start: Fetching the account property from the database in getAccountPropertyById() method.");
		AccountProperty accountProperty = null;
		String res="";
		boolean flag=false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if(!em.getTransaction().isActive()){
				flag=true;
				em.getTransaction().begin();
			}
			if (custId != null) {
				Query query = em.createQuery("SELECT a FROM AccountProperty a where" + " a.customerId = :custId");
				query.setParameter("custId", custId);
				accountProperty= (AccountProperty) query.getSingleResult();
			}
			if(accountProperty != null){
				accountProperty.setSavingsAccountNumber(accountNo);
			}
			if(flag)
			em.getTransaction().commit();
			res="SB Account is assigned successfully";
		}catch(NoResultException e){
			res="Account Property not found with this member number";
		}
		catch (Exception excp) {
			logger.error("Could not map savings account with account property in the "
					+ "database using account property id.Exception thrown.");
			res="Error while mapping savings account with customer.";
			throw new DataAccessException("Could not map savings account with account property in database.", excp.getCause());
		} 
		logger.info("End: Successfully Mapped savings account with account property in the database in getAccountPropertyById() method.");
		return res;
	}
	
	@Override
	public String saveSBAccountMappingInfo(SBAccountMapping master) {
		logger.info("Start: saving the SB account mapping log in saveSBAccountMappingInfo() method.");
		SBAccountMapping bankInfo = null;
		String res="";
		boolean flag=false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if(!em.getTransaction().isActive()){
				flag=true;
				em.getTransaction().begin();
			}
			if (master.getPartyId() != null) {
				em.merge(master);
			}
			if(flag)
			em.getTransaction().commit();
		}catch (Exception excp) {
			logger.error("Could not saving sb account log details "
					+ "database using account property id.Exception thrown.");
			res="Error while mapping savings account with customer.";
			throw new DataAccessException("Could not saving sb account log details in database.", excp.getCause());
		} 
		logger.info("End: saving the SB account mapping log in saveSBAccountMappingInfo() method.");
		return res;
	}
	
	public AccountProperty getAccountPropertyByAtmLoanAccount(String atmLoanAccount, boolean isCloseSession) {

		logger.info("Start: Fetching the Account Property data from the database in getAccountPropertyByAtmLoanAccount() method.");
		AccountProperty master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (atmLoanAccount != null) {
				Query query = em.createQuery("SELECT a FROM AccountProperty a where"
						+ " a.atmLoanAccountNumber = :atmLoanAccount");
				query.setParameter("atmLoanAccount", atmLoanAccount);
				master = (AccountProperty) query.getSingleResult();
			}
		} catch(NoResultException e){
			master = null;
		}catch (Exception excp) {
			logger.error("Could not fetch the account property data.");
			throw new DataAccessException("Could not fetch the account property data.", excp.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the Account Property data from the database in getAccountPropertyByAtmLoanAccount() method.");
		return master;
	}
}
