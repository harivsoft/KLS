/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;
import com.vsoftcorp.time.Date;

/**
 * @author a9153
 * 
 */
public class BorrowingsLineOfCreditDAO implements IBorrowingsLineOfCreditDAO {

	private static final Logger logger = Logger.getLogger(BorrowingsLineOfCreditDAO.class);

	@Override
	public BorrowingsLineOfCredit getBorrowingsLineOfCreditById(Long locId) {

		logger.info("Start: Fetching the Borrowings line of credit id from the database in getBorrowingsLineOfCreditById() method.");
		BorrowingsLineOfCredit master = null;
		logger.info(" loanLocId : " + locId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (locId != null) {
				master = em.find(BorrowingsLineOfCredit.class, locId);
				logger.debug("master.getAccount()==" + master);
				logger.debug("Account# " + master.getAccount().getAccountNumber());
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the Borrowings loc data from the "
					+ "database using loc id  Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the Borrowings loc data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Borrowings line of credit data from the database in getBorrowingsLineOfCreditById() method.");
		return master;
	}

	@Override
	public void saveBorrowingsLineOfCredit(BorrowingsLineOfCredit borrowingsLoc) {

		logger.info("Start: Saving the borrowings line of credit data to the database in saveBorrowingsLineOfCredit() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(borrowingsLoc);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the borrowings line of credit data to the database. Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the borrowings line of credit data to the database.",
					excp.getCause());
		} /**finally {
			EntityManagerUtil.closeSession();
		}**/
		logger.info("End: Successfully saved the borrowings line of credit to the database in saveBorrowingsLineOfCredit() method.");
	}

	@Override
	public BorrowingsLineOfCredit getBorrowingsLineOfCreditByBorrowingAccountId(
			Long borrowingAccountId) {

		logger.info("Start: Fetching the Borrowings line of credit id from the database in getBorrowingsLineOfCreditByBorrowingAccountId() method.");
		BorrowingsLineOfCredit master = null;
		logger.info(" borrowingAccountId : " + borrowingAccountId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (borrowingAccountId != null) {
				Query query = em
						.createQuery("SELECT l FROM BorrowingsLineOfCredit l where l.loanAccountProperty.id = :borrowingAccountId");
				query.setParameter("borrowingAccountId", borrowingAccountId);

				List list = query.getResultList();
				if (!list.isEmpty())
					master = (BorrowingsLineOfCredit) list.get(0);
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the Borrowings loc data from the "
					+ "database using loc id  Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the Borrowings loc data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Borrowings line of credit data from the database in getBorrowingsLineOfCreditByBorrowingAccountId() method.");
		return master;
	}

	@Override
	public void updateBorrowingsLineOfCredit(BorrowingsLineOfCredit borrowingsLoc,
			boolean beginNewTransaction) {
		logger.info("Start: Updating  Borrowings line of credit  in updateBorrowingsLineOfCredit() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		BorrowingsLineOfCredit bloc = null;
		try {
			Long blocId = borrowingsLoc.getId();
			if (blocId != null) {
				bloc = em.find(BorrowingsLineOfCredit.class, blocId);
				if (bloc != null) {
					if (beginNewTransaction)
						em.getTransaction().begin();
					bloc.setAccount(borrowingsLoc.getAccount());
					bloc.setBorrowingsLocId(borrowingsLoc.getBorrowingsLocId());
					bloc.setChargesReceivable(borrowingsLoc.getChargesReceivable());
					bloc.setCrop(borrowingsLoc.getCrop());
					bloc.setCurrentBalance(borrowingsLoc.getCurrentBalance());
					bloc.setCustomerId(borrowingsLoc.getCustomerId());
					bloc.setDrawalPriority(borrowingsLoc.getDrawalPriority());
					bloc.setFirstDueDate(borrowingsLoc.getFirstDueDate());
					bloc.setInterestAccrued(borrowingsLoc.getInterestAccrued());
					bloc.setInterestCategory(borrowingsLoc.getInterestCategory());
					bloc.setInterestReceivable(borrowingsLoc.getInterestReceivable());
					bloc.setIsFirstDrawal(borrowingsLoc.getIsFirstDrawal());
					bloc.setKindBalance(borrowingsLoc.getKindBalance());
					bloc.setKindLimit(borrowingsLoc.getKindLimit());
					bloc.setLastInterestCalculatedDate(borrowingsLoc
							.getLastInterestCalculatedDate());
					bloc.setLineOfCreditStatus(borrowingsLoc.getLineOfCreditStatus());
					bloc.setLoanAccountProperty(borrowingsLoc.getLoanAccountProperty());
					bloc.setLoanExpiryDate(borrowingsLoc.getLoanExpiryDate());
					bloc.setLoanType(borrowingsLoc.getLoanType());
					bloc.setOperatingCashLimit(borrowingsLoc.getOperatingCashLimit());
					bloc.setOverdueInterest(borrowingsLoc.getOverdueInterest());
					bloc.setPacsLoanApplication(borrowingsLoc.getPacsLoanApplication());
					bloc.setPenalInterestReceivable(borrowingsLoc.getPenalInterestReceivable());
					bloc.setProduct(borrowingsLoc.getProduct());
					bloc.setSanctionedAmount(borrowingsLoc.getSanctionedAmount());
					bloc.setSanctionedDate(borrowingsLoc.getSanctionedDate());
					bloc.setScheme(borrowingsLoc.getScheme());
					bloc.setSeason(borrowingsLoc.getSeason());
					if (beginNewTransaction)
						em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not update the Borrowings loc Exception thrown."
					+ excp.getMessage());
			throw new DataAccessException("Could not update the Borrowings loc Exception thrown.",
					excp.getCause());
		}

		logger.info("End: Updating  Borrowings line of credit  in updateBorrowingsLineOfCredit() method.");
	}

	@Override
	public BorrowingsLineOfCredit saveBorrowingsLOCAndPostTransaction(BorrowingsLineOfCredit borrowingsLoc,CurrentTransaction currTrans) {
		logger.info("Start: Saving Borrowings line of credit and posting transaction in saveBorrowingsLOCAndPostTransaction() method.---"
				+ borrowingsLoc.getId());

		Boolean isTrasactionAlreadyActive = true;
		EntityManager em = null;
		ICurrentTransactionDAO currTransDAO = KLSDataAccessFactory.getCurrentTransactionDAO();
		try {
			em = EntityManagerUtil.getEntityManager();
			
			if ( !em.getTransaction().isActive() ){
				em.getTransaction().begin();
				isTrasactionAlreadyActive = false;
			}
			if(borrowingsLoc!=null)
			borrowingsLoc = em.merge(borrowingsLoc);
			/*
			 * else updateBorrowingsLineOfCredit(borrowingsLoc, false);
			 */
           
			currTrans.setLineOfCredit(borrowingsLoc);
			em.merge(currTrans);
			if ( !isTrasactionAlreadyActive )
				em.getTransaction().commit();

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not update the Borrowings loc Exception thrown."+ excp.getMessage());
			if ( !isTrasactionAlreadyActive )
				em.getTransaction().rollback();
			throw new DataAccessException("Could not update the Borrowings loc Exception thrown.",excp.getCause());
		}
		logger.info("End: Saving Borrowings line of credit and posting transaction in saveBorrowingsLOCAndPostTransaction() method.");
		return borrowingsLoc;
	}

	@Override
	public List<BorrowingsLineOfCredit> getOrderedBorrowingLocListByAccountId(Long accountId) {

		logger.info("Start: Fetching the Borrowings line of credit from the database in getEarliestBorrowingLoc() method.");
		List<BorrowingsLineOfCredit> borrowingLocList = new ArrayList<BorrowingsLineOfCredit>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT l FROM BorrowingsLineOfCredit l where l.account.id=:accountId and l.lineOfCreditStatus=:status order by l.sanctionedDate");
			query.setParameter("accountId", accountId);
			query.setParameter("status", LineOfCreditStatus.Active);

			borrowingLocList = query.getResultList();

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the Borrowings loc data from the "
					+ "database using loc id  Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the Borrowings loc data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Borrowings line of credit data from the database in getEarliestBorrowingLoc() method.");
		return borrowingLocList;
	}

	@Override
	public Boolean isAllAmountsClear(Long locId) {

		logger.info("Start: Fetching the Borrowings line of credit from the database in getEarliestBorrowingLoc() method.");
		Boolean flag = false;
		List<BorrowingsLineOfCredit> master = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT l FROM BorrowingsLineOfCredit l where l.currentBalance = :currentBalance and l.interestReceivable = :interestReceivable and l.penalInterestReceivable = :penalInterestReceivable and l.chargesReceivable = :chargesReceivable and l.id = :locId)");
			query.setParameter("currentBalance", AccountingMoney.ZERO);
			query.setParameter("interestReceivable", BigDecimal.ZERO);
			query.setParameter("penalInterestReceivable", BigDecimal.ZERO);
			query.setParameter("chargesReceivable", BigDecimal.ZERO);
			query.setParameter("locId", locId);
			master = query.getResultList();
			if (master.size() > 0)
				flag = true;
			else
				flag = false;
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the Borrowings loc data from the "
					+ "database using loc id  Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the Borrowings loc data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Borrowings line of credit data from the database in getEarliestBorrowingLoc() method.");
		return flag;
	}

	@Override
	public BorrowingsLineOfCredit getBorrowingsLineOfCreditsByProductPacAndDate(Integer productId,
			Integer pacsId, Date businessDate) {
		logger.info("productId : " + productId);
		logger.info("pacsId : " + pacsId);
		logger.info("businessdate : " + businessDate);
		logger.info("Start: Fetching the Borrowings line of credits from the database in getBorrowingsLineOfCreditsByProductPacAndDate() method.");
		BorrowingsLineOfCredit master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT l FROM BorrowingsLineOfCredit l,CurrentTransaction ct where l.id = ct.lineOfCredit.id and l.product.id = :productId and ct.pacs.id = :pacsId and ct.businessDate = :businessDate");
			query.setParameter("productId", productId);
			query.setParameter("pacsId", pacsId);
			query.setParameter("businessDate", businessDate);
			master = (BorrowingsLineOfCredit) query.getSingleResult();

		} catch (NoResultException noResultException) {
			logger.error("no result while getting BLOC ");
			return null;
		} catch (Exception excp) {

			excp.printStackTrace();
			logger.error("Could not fetch the Borrowings locs data from the "
					+ "database using loc id  Exception thrown.");
			throw new DataAccessException(
					"Could not fetch the Borrowings locs data from the database.", excp.getCause());
		}
		logger.info("End: Fetching the Borrowings line of credits data from the database in getBorrowingsLineOfCreditsByProductPacAndDate() method.");
		return master;
	}

	@Override
	public void deleteAllBorrowingsLocs() {
		// TODO Auto-generated method stub
		logger.info("Start: Deleting the Charges Debit data to the database in deleteAllBorrowingsLocs() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			Query query = em.createQuery("delete from BorrowingsLineOfCredit b");
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			logger.error("Error while Deleting BorrowingsLocs.");
			throw new DataAccessException("Error while Deleting deleteAllBorrowingsLocs.",
					e.getCause());
		} /**finally {
			EntityManagerUtil.closeSession();
		}**/

	}
	
	@Override
	public List<BorrowingsLineOfCredit> getBlocsPerLoanLoc(Long locId, Integer productId,Integer pacsId){
		logger.info("Start: Retriving all Borrowings locs for given loan loc getBlocsPerLoneLocs()");
		List<BorrowingsLineOfCredit> borrowingLocList = null; //new ArrayList<>();
		try{
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT l FROM BorrowingsLineOfCredit l where l.borrowingsLocId =:locId order by l.sanctionedDate");
//			query.setParameter("productId", productId);
//			query.setParameter("pacsId", pacsId);
			query.setParameter("locId", locId);

			borrowingLocList = query.getResultList();

		} catch (Exception excp) {
			//excp.printStackTrace();
			logger.error("Error while Retriving all Borrowings locs for given loan loc : "+locId);
			throw new DataAccessException("Error while retriving all Borrowings locs for given loan loc", excp.getCause());
		}

		logger.info("End: Retriving all Borrowings locs for given loan loc getBlocsPerLoneLocs()");
		return borrowingLocList;
	}
}
