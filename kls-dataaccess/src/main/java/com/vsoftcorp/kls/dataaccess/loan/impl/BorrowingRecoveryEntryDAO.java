package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.BorrowingRecoveryEntry;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingRecoveryEntryDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.transaction.DisbursementStatus;

public class BorrowingRecoveryEntryDAO implements IBorrowingRecoveryEntryDAO{
	private static final Logger logger = Logger.getLogger(AccountPropertyDAO.class);

	@Override
	public BorrowingRecoveryEntry getBorrowingRecoveryEntry(
			BorrowingRecoveryEntry master, boolean isCloseSession) {

		logger.info("Start: Fetching the BorrowingRecoveryEntry from the database in getBorrowingRecoveryEntry() method.");
		BorrowingRecoveryEntry recMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = master.getId();
			if (id != null) {
				recMaster = em.find(BorrowingRecoveryEntry.class, id);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the BorrowingRecoveryEntry from the "
					+ "database using BorrowingRecoveryEntry id.Exception thrown.");
			throw new DataAccessException("Could not fetch the BorrowingRecoveryEntry from the database.", excp.getCause());
		} finally {
			if (isCloseSession) {
				EntityManagerUtil.closeSession();
			}
		}
		logger.info("End: Successfully fetched the BorrowingRecoveryEntry from the database in getChargesMaster() method.");
		return recMaster;
	}

	@Override
	public void saveBorrowingRecoveryEntry(BorrowingRecoveryEntry master) {

		logger.info("Start: Saving the BorrowingRecoveryEntry data to the database in saveBorrowingRecoveryEntry() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(master);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the BorrowingRecoveryEntry"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the BorrowingRecoveryEntry data to the database.",
					excp.getCause());
		} 
		logger.info("End: Successfully saved the BorrowingRecoveryEntry to the database in saveBorrowingRecoveryEntry() method.");
	}
	
	@Override
	public Map getBorrowingRecoveryAmounts(Long accountId) {
		 Map map = new HashMap<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select new Map(sum(l.currentBalance) as principleBalance,sum(l.interestReceivable) as interestPayable,sum(l.penalInterestReceivable) as penalInterestPayable,sum(l.chargesReceivable) as chargesPayable) from BorrowingsLineOfCredit l where l.loanAccountProperty.account.id = :accountId");
			query.setParameter("accountId", accountId);
			
			map = (Map)query.getSingleResult();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the BorrowingRecoveryAmounts ");
			throw new DataAccessException("Error while getting the BorrowingRecoveryAmounts data from the database.",
					excp.getCause());
		}
		return map;
	}

	@Override
	public List<BorrowingRecoveryEntry> getAllBorrowingRecoveryEntriesByProdId(
			Integer borrowingProdId, Integer status) {
		// TODO Auto-generated method stub
		List<BorrowingRecoveryEntry> list = new ArrayList<>();
		try {
				EntityManager em = EntityManagerUtil.getEntityManager();
				Query query = em
						.createQuery("select b from BorrowingRecoveryEntry b where b.borrowingProduct.id = :borrowingProdId and b.status = :status");
				query.setParameter("borrowingProdId", borrowingProdId);
				query.setParameter("status", status);
				list = query.getResultList();
			} catch (Exception excp) {
				excp.printStackTrace();
				logger.error("Error while getting the BorrowingRecoveryEntries ");
				throw new DataAccessException("Error while getting the BorrowingRecoveryEntries data from the database.",
						excp.getCause());
			}
		return list;
	}
	
	
	
	@Override
	public List<BorrowingRecoveryEntry> getAllBorrowingRecoveryEntriesByPacsId(
			Integer pacsId, Integer status) {
		List<BorrowingRecoveryEntry> list = new ArrayList<>();
		try {
				EntityManager em = EntityManagerUtil.getEntityManager();
				Query query = em
						.createQuery("select b from BorrowingRecoveryEntry b where b.pacs.id = :pacsId and b.status = :status");
				query.setParameter("pacsId", pacsId);
				query.setParameter("status", DisbursementStatus.valueOf(status));
				list = query.getResultList();
			} catch (Exception excp) {
				excp.printStackTrace();
				logger.error("Error while getting the BorrowingRecoveryEntries ");
				throw new DataAccessException("Error while getting the BorrowingRecoveryEntries data from the database.",
						excp.getCause());
			}
		return list;
	}

	@Override
	public void updateBorrowingRecoveryEntry(BorrowingRecoveryEntry master) {
		logger.info("Start: updating BorrowingRecoveryEntry in updateBorrowingRecoveryEntry()");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			if (master != null)
				em.merge(master);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while updating BorrowingRecoveryEntry");
			throw new DataAccessException("Error while updating BorrowingRecoveryEntry", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: updating BorrowingRecoveryEntry in updateBorrowingRecoveryEntry()");
	}
	
	@Override
	public BorrowingRecoveryEntry getBorrowingRecoveryEntryById(Long id){
		BorrowingRecoveryEntry borrowingRecoveryEntry = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		if (id != null) {
			borrowingRecoveryEntry = em.find(BorrowingRecoveryEntry.class, id);
		}
		return borrowingRecoveryEntry;
	}

	@Override
	public void deleteBorrowingRecoveryEntry(Long borrowingRecoveryId) {
		// TODO Auto-generated method stub

		logger.info("Start: Deleting the BorrowingRecoveryEntry data in database in deleteBorrowingRecoveryEntry() method.");
		try {
			BorrowingRecoveryEntry recMaster = null;
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (borrowingRecoveryId != null) {
				recMaster = em.find(BorrowingRecoveryEntry.class, borrowingRecoveryId);
			}
			if (recMaster != null) {
				em.getTransaction().begin();
				em.remove(recMaster);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of Deleting the BorrowingRecoveryEntry " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of Deleting the BorrowingRecoveryEntry data to the database.", excp.getCause());
		}/*
			* finally { EntityManagerUtil.closeSession(); }
			*/
		logger.info("End: Successfully Deleting the BorrowingRecoveryEntry data to the database in deleteBorrowingRecoveryEntry() method.");

	}
	
	@Override
	public List<BorrowingsLineOfCredit> getBlocsPerBproduct(Integer productId,Integer pacsId){
		logger.info("Start: Retriving all Borrowings locs for given loan loc getBlocsPerLoneLocs()");
		List<BorrowingsLineOfCredit> borrowingLocList = null; //new ArrayList<>();
		try{
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT l FROM BorrowingsLineOfCredit l where l.loanAccountProperty.borrowingProduct.id =:productId and l.account.accountProperty.pacs.id = :pacsId order by l.sanctionedDate");
			query.setParameter("productId", productId);
			query.setParameter("pacsId", pacsId);

			borrowingLocList = query.getResultList();

		} catch (Exception excp) {
			//excp.printStackTrace();
			logger.error("Error while Retriving all Borrowings locs for given loan loc : ");
			throw new DataAccessException("Error while retriving all Borrowings locs for given loan loc", excp.getCause());
		}

		logger.info("End: Retriving all Borrowings locs for given loan loc getBlocsPerLoneLocs()");
		return borrowingLocList;
	}

}
