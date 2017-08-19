package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.account.BorrowingsAccountProperty;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsAccountPropertyDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author a1691
 * 
 */
public class BorrowingsAccountPropertyDAO implements IBorrowingsAccountPropertyDAO {

	private static final Logger logger = Logger.getLogger(BorrowingsAccountPropertyDAO.class);

	public boolean checkIfAccountExists(int pacId, Integer productId, boolean isCloseSession) {

		logger.info("Start: Fetching the Borrowings Account Property from the database in checkIfAccountExists() method.");
		BorrowingsAccountProperty master = null;
		logger.info(" productId dao : " + productId);
		logger.info(" pacId dao : " + pacId);
		boolean flag = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (productId != null) {
				Query query = em.createQuery("SELECT a FROM BorrowingsAccountProperty a where a.accountPropertyType='B' and a.account.accountProperty.pacs.id = :pacId and"
						+ " a.product.id = :productId");
				query.setParameter("pacId", pacId);
				query.setParameter("productId", productId);
				master = (BorrowingsAccountProperty) query.getSingleResult();
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
			logger.error("Could not fetch the pacs account property data.");
			throw new DataAccessException("Could not fetch the pacs account property data from the database.", excp.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the Borrowings Account Property from the database in checkIfAccountExists() method.");
		return flag;

	}

	@Override
	public BorrowingsAccountProperty getBorrowingsAccountProperty(Long accountPropertyId) {

		logger.info("Start: Fetching the BorrowingsAccountProperty data from the database in getBorrowingsAccountProperty() method.DAO="
				+ accountPropertyId);
		BorrowingsAccountProperty account = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (accountPropertyId != null)
				account = em.find(BorrowingsAccountProperty.class, accountPropertyId);
		} catch (Exception excp) {
			logger.error("Could not fetch the BorrowingsAccountProperty master data from the " + "database using Account Id Exception thrown.");
			throw new DataAccessException("Could not fetch the BorrowingsAccountProperty data from the database.", excp.getCause());
		} /*
			* finally { EntityManagerUtil.closeSession(); }
			*/
		logger.info("End: Successfully fetched the BorrowingsAccountProperty data from the database in getBorrowingsAccountProperty() method.");
		return account;
	}

	@Override
	public BorrowingsAccountProperty saveBorrowingsAccountProperty(BorrowingsAccountProperty BorrowingsAccountProperty) {
		logger.info("Start: Saving the Borrowings account proprety to the database in saveBorrowingsAccountProperty() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		boolean flgTrans = false;
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
			flgTrans = true;
		}
		BorrowingsAccountProperty BorrowingsAcctProperty = null;
		try {
			//em.getTransaction().begin();
			BorrowingsAcctProperty = em.merge(BorrowingsAccountProperty);
			if(flgTrans)
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the Borrowings Account Property" + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of saving the Borrowings Account Property data to the database.",
					excp.getCause());
		}
		return BorrowingsAcctProperty;
	}

	@Override
	public List<BorrowingsAccountProperty> getAllBorrowingsAccountProperties(boolean isCloseSession) {

		logger.info("Start: Fetching all the BorrowingsAccountProperty master data from the database in getAllBorrowingsAccountProperties() method.");
		List<BorrowingsAccountProperty> borrowingsAccountPropertyMasterList = new ArrayList<BorrowingsAccountProperty>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			borrowingsAccountPropertyMasterList = em.createQuery("SELECT v FROM BorrowingsAccountProperty v").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all BorrowingsAccountProperty from the database");
			throw new DataAccessException("Error while retriving all BorrowingsAccountProperty from the database", e.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the BorrowingsAccountProperty master data from the database in getAllBorrowingsAccountProperties() method.");
		return borrowingsAccountPropertyMasterList;
	}

	@Override
	public void updateBorrowingAccountProperty(BorrowingsAccountProperty borrowingsAccountProperty) {

		logger.info("Start: Updating the Loan Account Property to the database in updateBorrowingAccountProperty() method.");
		try {
			BorrowingsAccountProperty master = null;
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = borrowingsAccountProperty.getId();
			if (id != null) {
				master = em.find(BorrowingsAccountProperty.class, id);
				em.getTransaction().begin();
				AccountProperty acctProperty = master.getAccount().getAccountProperty();
				master.setAccountType(borrowingsAccountProperty.getAccountType());
				master.setOperatorType(borrowingsAccountProperty.getOperatorType());
				master.setOperatingInstructionsType(borrowingsAccountProperty.getOperatingInstructionsType());
				master.setResolutionNumber(borrowingsAccountProperty.getResolutionNumber());
				master.setInterestCategory(borrowingsAccountProperty.getInterestCategory());
				master.setBorrowingType(borrowingsAccountProperty.getBorrowingType());
				acctProperty.setBranch(borrowingsAccountProperty.getAccount().getAccountProperty().getBranch());
				master.setCcbAccountNumber(borrowingsAccountProperty.getCcbAccountNumber());
				master.setLenderName(borrowingsAccountProperty.getLenderName());
				master.setName(borrowingsAccountProperty.getName());
				acctProperty.setPacs(borrowingsAccountProperty.getAccount().getAccountProperty().getPacs());
				acctProperty.setProduct(borrowingsAccountProperty.getProduct());
				master.setPurpose(borrowingsAccountProperty.getPurpose());
				master.setRemarks(borrowingsAccountProperty.getRemarks());
				master.setResolutionDate(borrowingsAccountProperty.getResolutionDate());
				master.setResolutionNumber(borrowingsAccountProperty.getResolutionNumber());
				master.setSanctionAuthority(borrowingsAccountProperty.getSanctionAuthority());
				master.setSanctionedAmount(borrowingsAccountProperty.getSanctionedAmount());
				master.setSanctionedDate(borrowingsAccountProperty.getSanctionedDate());
				master.getAccount().setAccountProperty(acctProperty);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the Borrowing Account Property" + " to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of updating the Borrowing Account Property data to the database.",
					excp.getCause());
		}
		logger.info("End: Updated the Borrowing Account Property to the database in updateBorrowingAccountProperty() method.");
	}

	@Override
	public BorrowingsAccountProperty getAccountPropertyByBorrowingProductId(Integer borrowingProductId) {

		logger.info("Start: Fetching the BorrowingsAccountProperty data from the database in getBorrowingsAccountProperty() method.");
		logger.info("borrowingProductId : " + borrowingProductId);
		BorrowingsAccountProperty borrowingsAccountProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (borrowingProductId != null) {
				Query query = em
						.createQuery("SELECT l FROM BorrowingsAccountProperty l where l.accountPropertyType='B' and l.borrowingProduct.id = :borrowingProductId");
				query.setParameter("borrowingProductId", borrowingProductId);

				borrowingsAccountProperty = (BorrowingsAccountProperty) query.getSingleResult();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the BorrowingsAccountProperty master data from the "
					+ "database using borrowing product Id Exception thrown.");
			throw new DataAccessException("Could not fetch the BorrowingsAccountProperty data from the database.", excp.getCause());
		}
		logger.info("End: Successfully fetched the BorrowingsAccountProperty data from the database in getBorrowingsAccountProperty() method.");
		return borrowingsAccountProperty;
	}

	@Override
	public BorrowingsAccountProperty getAccountPropertyByProductId(Integer productId) {

		logger.info("Start: Fetching the BorrowingsAccountProperty from the database in getAccountPropertyByProductId() method.");
		logger.info("productId : " + productId);
		BorrowingsAccountProperty borrowingsAccountProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (productId != null) {
				Query query = em
						.createQuery("SELECT l FROM BorrowingsAccountProperty l where l.accountPropertyType='B' and l.product.id = :productId");
				query.setParameter("productId", productId);

				borrowingsAccountProperty = (BorrowingsAccountProperty) query.getSingleResult();
			}
		} catch (NonUniqueResultException nonUnniqueExp) {
			throw new DataAccessException("Cannot create borrowing LOC. Product mapped to multiple borrowings accounts", nonUnniqueExp);

		} catch (NoResultException noResultExp) {
			throw new DataAccessException("Cannot create borrowing LOC. No borrowings account created for product", noResultExp);
		} catch (Exception excp) {
			logger.error("Could not fetch the BorrowingsAccountProperty master using product Id Exception thrown.");
			throw new DataAccessException("Could not fetch the BorrowingsAccountProperty master using product Id ", excp.getCause());
		}
		logger.info("End: Successfully fetched the BorrowingsAccountProperty from the database in getAccountPropertyByProductId() method.");
		return borrowingsAccountProperty;
	}

	@Override
	public BorrowingsAccountProperty getAccountPropertyByProductIdPacsId(Integer productId, Integer pacsId) {

		logger.info("Start: Fetching the BorrowingsAccountProperty from the database in getAccountPropertyByProductId() method.");
		logger.info("productid : " + productId + "pacsId:" + pacsId);
		BorrowingsAccountProperty borrowingsAccountProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (productId != null) {
				Query query = em
						.createQuery("SELECT l FROM BorrowingsAccountProperty l where l.accountPropertyType='B' and l.product.id = :productId and l.account.accountProperty.pacs.id = :pacsId");
				query.setParameter("productId", productId);
				query.setParameter("pacsId", pacsId);

				borrowingsAccountProperty = (BorrowingsAccountProperty) query.getSingleResult();
			}
		} catch (NonUniqueResultException nonUnniqueExp) {
			throw new DataAccessException("Cannot create borrowing LOC. Product mapped to multiple borrowings accounts", nonUnniqueExp);

		} catch (NoResultException noResultExp) {
			logger.info("Borrowing account not exist for  productid : " + productId + "pacsId:" + pacsId);
			throw new DataAccessException("Cannot create borrowing LOC. No borrowings account created for product", noResultExp);
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the BorrowingsAccountProperty master using product Id Exception thrown.");
			throw new DataAccessException("Could not fetch the BorrowingsAccountProperty master using product Id ", excp.getCause());
		}
		logger.info("End: Successfully fetched the BorrowingsAccountProperty from the database in getAccountPropertyByProductId() method.");
		return borrowingsAccountProperty;
	}

	@Override
	public void deleteBorrowingsAccount(Long borrowingAccountId) {
		// TODO Auto-generated method stub

		logger.info("Start: Deleting the product charges mapping  data in database in deleteProductChargesMapping() method.");
		try {

			EntityManager em = EntityManagerUtil.getEntityManager();
			BorrowingsAccountProperty account = getBorrowingsAccountProperty(borrowingAccountId);
			if (account != null) {
				em.getTransaction().begin();
				em.remove(account);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of Deleting the borrowing account  " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of Deleting the borrowing account data to the database.", excp.getCause());
		}/*
			* finally { EntityManagerUtil.closeSession(); }
			*/
		logger.info("End: Successfully Deleting the borrowing account data to the database in deleteProductChargesMapping() method.");

	}

	@Override
	public boolean checkIfAccountExistsWithBorrowingProduct(Integer borrowingProductId, Integer pacId) {

		logger.info("Start: Fetching the Borrowings Account Property from the database in checkIfAccountExistsWithProduct() method.DAO");
		BorrowingsAccountProperty master = null;
		logger.info(" borrowingProductId dao : " + borrowingProductId);
		boolean flag = true;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (borrowingProductId != null && pacId != null) {
				Query query = em
						.createQuery("SELECT a FROM BorrowingsAccountProperty a where a.accountPropertyType='B' and a.account.accountProperty.pacs.id = :pacId and a.borrowingProduct.id = :borrowingProductId");
				query.setParameter("pacId", pacId);
				query.setParameter("borrowingProductId", borrowingProductId);
				master = (BorrowingsAccountProperty) query.getSingleResult();
				if (master != null) {
					flag = true;
					logger.info("Borrowing account already exist for Borrowing product:" + borrowingProductId + " pacsId:" + pacId);
				}
			}
		} catch (NoResultException excp) {
			logger.info(" borrowing account with borrowing product does not exist.");
			flag = false;
		} catch (NonUniqueResultException excp) {
			logger.error("More than one borrowing account with borrowing product exists.");
			flag = true;
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the borrowing account property data.");
			throw new DataAccessException("Could not fetch the borrowing account property data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Borrowings Account Property from the database in checkIfAccountExistsWithProduct() method.");
		return flag;

	}

	@Override
	public boolean checkIfAccountExistsWithProduct(Integer productId, Integer pacId) {

		logger.info("Start: Fetching the Borrowings Account Property from the database in checkIfAccountExistsWithProduct() method.DAO");
		BorrowingsAccountProperty master = null;
		logger.info(" productId dao : " + productId);
		boolean flag = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (productId != null && pacId != null) {
				Query query = em
						.createQuery("SELECT a FROM BorrowingsAccountProperty a where a.accountPropertyType='B' and a.account.accountProperty.pacs.id = :pacId and a.product.id = :productId");
				query.setParameter("pacId", pacId);
				query.setParameter("productId", productId);
				master = (BorrowingsAccountProperty) query.getSingleResult();
				if (master != null) {
					flag = true;
					logger.info("Borrowing account already exist for  product:" + productId + " pacsId:" + pacId);
				}
			}
		} catch (NoResultException excp) {
			logger.info(" borrowing account with borrowing product does not exist.");
			flag = false;
		} catch (NonUniqueResultException excp) {
			logger.error("More than one borrowing account with borrowing product exists.");
			flag = true;
		} catch (Exception excp) {
			logger.error("Could not fetch the borrowing account property data.");
			throw new DataAccessException("Could not fetch the borrowing account property data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Borrowings Account Property from the database in checkIfAccountExistsWithProduct() method.");
		return flag;

	}

	@Override
	public BorrowingsAccountProperty getBorroingAccountPropertyByAccountId(Long accountId) {
		logger.info("start: getBorroingAccountPropertyByAccountId by accountid: " + accountId);
		BorrowingsAccountProperty bLoanAccountProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			bLoanAccountProperty = (BorrowingsAccountProperty) em
					.createQuery("select l from BorrowingsAccountProperty l where l.account.id=:accountId").setParameter("accountId", accountId)
					.getSingleResult();
		} catch (Exception excp) {
			logger.error("cannot find borrowing account property by accountId:" + accountId);
			throw new DataAccessException("cannot find borrowing account property by accountId.", excp.getCause());
		}
		logger.info("End: getBorroingAccountPropertyByAccountId by accountid");
		return bLoanAccountProperty;
	}

	@Override
	public BorrowingsAccountProperty getAccountPropertyByBorrowingProductIdPacsId(Integer pacsId, Integer borrowingProductId) {
		logger.info("Start: Fetching the BorrowingsAccountProperty from the database in getAccountPropertyByProductId() method.");
		logger.info("borrowingProductId : " + borrowingProductId + "pacsId:" + pacsId);
		BorrowingsAccountProperty borrowingsAccountProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (borrowingProductId != null && pacsId != null) {
				Query query = em
						.createQuery("SELECT a FROM BorrowingsAccountProperty a where a.accountPropertyType='B' and a.account.accountProperty.pacs.id = :pacId and a.borrowingProduct.id = :borrowingProductId");
				query.setParameter("pacId", pacsId);
				query.setParameter("borrowingProductId", borrowingProductId);
				borrowingsAccountProperty = (BorrowingsAccountProperty) query.getSingleResult();
			}
		} catch (NonUniqueResultException nonUnniqueExp) {
			throw new DataAccessException("Cannot create borrowing LOC. Product mapped to multiple borrowings accounts", nonUnniqueExp);

		} catch (NoResultException noResultExp) {
			logger.info("Borrowing account not exist for  borrowingProductId : " + borrowingProductId + "pacsId:" + pacsId);
			throw new DataAccessException("Cannot create borrowing LOC. No borrowings account created for product", noResultExp);
		} catch (Exception excp) {
			logger.error("Could not fetch the BorrowingsAccountProperty master using product Id Exception thrown.");
			throw new DataAccessException("Could not fetch the BorrowingsAccountProperty master using product Id ", excp.getCause());
		}
		logger.info("End: Successfully fetched the BorrowingsAccountProperty from the database in getAccountPropertyByProductId() method.");
		return borrowingsAccountProperty;
	}
	
	@Override
	public BorrowingsAccountProperty getAccountPropertyByLoanProductIdPacsId(Integer pacsId, Integer productId) {
		logger.info("Start: Fetching the BorrowingsAccountProperty from the database in getAccountPropertyByLoanProductIdPacsId() method.");
		logger.info("loanProductId : " + productId + "pacsId:" + pacsId);
		BorrowingsAccountProperty borrowingsAccountProperty = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (productId != null && pacsId != null) {
				Query query = em
						.createQuery("SELECT a FROM BorrowingsAccountProperty a where a.accountPropertyType='B' and "
								+ " a.account.accountProperty.pacs.id = :pacId and a.product.id = :loanProductId");
				query.setParameter("pacId", pacsId);
				query.setParameter("loanProductId", productId);
				borrowingsAccountProperty = (BorrowingsAccountProperty) query
						.getSingleResult();
			}
		} catch (NonUniqueResultException nonUnniqueExp) {
			throw new DataAccessException(
					"Cannot create borrowing LOC. Product mapped to multiple borrowings accounts",
					nonUnniqueExp);

		} catch (NoResultException noResultExp) {
			logger.info("Borrowing account not exist for  loanProductId : "
					+ productId + "pacsId:" + pacsId);
			throw new DataAccessException(
					"Cannot create borrowing LOC. No borrowings account created for product",
					noResultExp);
		} catch (Exception excp) {
			logger.error("Could not fetch the BorrowingsAccountProperty master using product Id Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the BorrowingsAccountProperty master using product Id ",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the BorrowingsAccountProperty from the database in getAccountPropertyByLoanProductIdPacsId() method.");
		return borrowingsAccountProperty;
	}
}
