/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.transaction.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.dataaccess.transaction.result.classes.TransactionRecordByProductAndCrdr;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public class CurrentTransactionDAO implements ICurrentTransactionDAO {

	private static final Logger logger = Logger.getLogger(CurrentTransactionDAO.class);

	private static final String ACCOUNTS_LIST = "AccountsList";

	private static final String AMOUNTS_LIST = "AmountsList";

	private static final String FIRST_DRAWL_LIST = "FirstDrawlList";

	private static final String CREDIT_PRINCIPAL_AMOUNT_MAP = "creditPrincipalAmount";

	private static final String CREDIT_INTEREST_AMOUNT_MAP = "creditInterestAmount";

	private static final String CREDIT_PENAL_INTEREST_AMOUNT_MAP = "creditPenalInterestAmount";

	private static final String CREDIT_CHARGES_AMOUNT_MAP = "creditChargesAmount";

	public static final String CREDIT_INTEREST_ACCRUED_AMOUNT_MAP = "creditInterestAccruedAmount";

	public static final String CREDIT_OVERDUE_INTEREST_AMOUNT_MAP = "creditOverdueInterestAmount";

	/**
	 * Saves the current transaction data to the database.
	 * 
	 * @param currentTransaction
	 */
	public void saveCurrentTransaction(CurrentTransaction currentTransaction) {

		logger.info("Start: Saving the current transaction data to the database in saveCurrentTransaction() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(currentTransaction);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the current transaction data.");
			throw new DataAccessException("Could not commit the transaction of saving the current transaction data.", excp.getCause());
		}
		logger.info("End: Successfully saved the current transaction to the database in saveCurrentTransaction() method.");
	}

	/**
	 * Updates the existing current transaction data to the database.
	 * 
	 * @param currentTransaction
	 */
	public void updateCurrentTransaction(CurrentTransaction currentTransaction) {

		logger.info("Start: Updating the current transaction data to the database in updateCurrentTransaction() method.");
		CurrentTransaction master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = currentTransaction.getId();
			if (id != null) {
				master = em.find(CurrentTransaction.class, id);
				if (master != null) {
					em.getTransaction().begin();
					master.setAccount(currentTransaction.getAccount());
					master.setAccountNumber(currentTransaction.getAccountNumber());
					master.setBusinessDate(currentTransaction.getBusinessDate());
					master.setChannelType(currentTransaction.getChannelType());
					master.setCrDr(currentTransaction.getCrDr());
					master.setLineOfCredit(currentTransaction.getLineOfCredit());
					master.setOpeningBalance(currentTransaction.getOpeningBalance());
					master.setPacs(currentTransaction.getPacs());
					master.setTransactionAmount(currentTransaction.getTransactionAmount());
					master.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
					master.setTransactionType(currentTransaction.getTransactionType());
					master.setVoucherNumber(currentTransaction.getVoucherNumber());
					master.setTerminalId(currentTransaction.getTerminalId());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the current transaction data.");
			throw new DataAccessException("Could not commit the transaction of updating the current transaction data.", excp.getCause());
		}
		if (master == null) {
			logger.error("Trying to update the current transaction record which does not exist.");
			throw new DataAccessException("Trying to update the current transaction record which does not exist.");
		}
		logger.info("End: Successfully updated the current transaction data to the database in updateCurrentTransaction() method.");
	}

	/**
	 * Checks if the current transaction id exists in the database.
	 * 
	 * @param currentTransaction
	 * @return CurrentTransaction
	 */
	public CurrentTransaction getCurrentTransaction(CurrentTransaction currentTransaction) {

		logger.info("Start: Fetching the current transaction data from the database in getCurrentTransaction() method.");
		CurrentTransaction master = new CurrentTransaction();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = currentTransaction.getId();
			if (id != null) {
				master = em.find(CurrentTransaction.class, id);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the curreMARGINALnt transaction data.");
			throw new DataAccessException("Could not fetch the current transaction data.", excp.getCause());
		}
		logger.info("End: Successfully fetched the current transaction data from the database in getCurrentTransaction() method.");
		return master;
	}

	/**
	 * 
	 * @param currentTransactionId
	 * @return
	 */
	public CurrentTransaction getCurrentTransactionById(Long currentTransactionId) {

		logger.info("Start: Fetching the current transaction data from the database in getCurrentTransactionById() method.");
		CurrentTransaction master = new CurrentTransaction();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (currentTransactionId != null) {
				master = em.find(CurrentTransaction.class, currentTransactionId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the current transaction data.");
			throw new DataAccessException("Could not fetch the current transaction data.", excp.getCause());
		}
		logger.info("End: Successfully fetched the current transaction data from the database in getCurrentTransactionById() method.");
		return master;
	}

	/**
	 * Returns all the current transaction records to the client.
	 * 
	 * @return
	 */
	public List<CurrentTransaction> getAllCurrentTransactions() {

		logger.info("Start: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.");
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			currentTransactionList = em.createQuery("SELECT c FROM CurrentTransaction c").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all current transaction records from the database.");
			throw new DataAccessException("Error while retriving all current transaction records.", e.getCause());
		}
		logger.info("End: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.");
		return currentTransactionList;
	}

	/**
	 * This method saves the current transaction and also updates the debit
	 * amount in the line of credit accounts.This has to be done in a single
	 * transaction.
	 */
	public <T> void saveCurrentTransaction(List<CurrentTransaction> currentTransactionList, Map<String, List<T>> debitAccountMap) {

		logger.info("Start: Saving the current transaction data to the database in saveCurrentTransaction() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		// EntityTransaction tx = em.getTransaction();
		// tx.begin();
		boolean flgTrans = false;
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
			flgTrans = true;
		}
		try {
			IAccountDAO accountDao = KLSDataAccessFactory.getAccountDAO();
			IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
			List<T> firstDrawlList = debitAccountMap.get(CurrentTransactionDAO.FIRST_DRAWL_LIST);
			if(null != firstDrawlList){
				for (int i = 0; i < firstDrawlList.size(); i++) {
					if (firstDrawlList.get(i) instanceof LineOfCredit) {
						LineOfCredit lineOfCredit = (LineOfCredit) firstDrawlList.get(i);
						logger.info(" loc Id in firstdrawal list  : "+currentTransactionList.get(0).getLineOfCredit().getId());
						Date loan_exp_date;
						ILineOfCreditDAO lineOfCreditDao = KLSDataAccessFactory.getLineOfCreditDAO();
						try {
							if("S".equalsIgnoreCase(lineOfCredit.getSeason().getDueDateMethod())){
								loan_exp_date = lineOfCredit.getSeason().getOverdueDate();
								lineOfCredit = lineOfCreditDao.getLineOfCreditById(lineOfCredit.getId(), false);
								lineOfCredit.setLoanExpiryDate(loan_exp_date);
								em.persist(lineOfCredit);
							}else{
								loan_exp_date = currentTransactionList.get(0).getBusinessDate();
								Integer dueDateInMonths = lineOfCredit.getSeason().getDueDateInMonths() == null ? 0 : lineOfCredit.getSeason().getDueDateInMonths();
								loan_exp_date = DateUtil.getDateByAddingNoOfMonthsToVsoftDate(loan_exp_date, dueDateInMonths);
								lineOfCredit = lineOfCreditDao.getLineOfCreditById(lineOfCredit.getId(), false);
								lineOfCredit.setLoanExpiryDate(loan_exp_date);
								em.persist(lineOfCredit);
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.info(" Unable update loan_exp_date in line of credit ");
						}
					}
				}
			}
			 
			if (debitAccountMap != null && !debitAccountMap.isEmpty()) {
				updateDebitAmount(em, debitAccountMap);
			}
			for (CurrentTransaction currentTransaction : currentTransactionList) {
				Account account = accountDao.getAccount(currentTransaction.getAccount(), false);
				currentTransaction.setAccount(account);
				Pacs pacs = pacsDao.getPacs(currentTransaction.getPacs(), false);
				currentTransaction.setPacs(pacs);
				em.persist(currentTransaction);
			}
			if (flgTrans)
				em.getTransaction().commit();
			// tx.commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the current transaction data.");
			if (flgTrans && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new DataAccessException("Could not commit the transaction of saving the current transaction data.", excp.getCause());
		}
		logger.info("End: Successfully saved the current transaction to the database in saveCurrentTransaction() method.");
		return;
	}

	/**
	 * update the debit amount in line of credit accounts.
	 * 
	 * @param debitMap
	 */
	private <T> void updateDebitAmount(EntityManager em, Map<String, List<T>> debitMap) {

		logger.info("Start: Updating the debit amount in line of credit account in updateDebitAmount() method.");
		List<T> debitAccountList = debitMap.get(CurrentTransactionDAO.ACCOUNTS_LIST);
		List<T> debitAmountList = debitMap.get(CurrentTransactionDAO.AMOUNTS_LIST);
		List<T> firstDrawlList = debitMap.get(CurrentTransactionDAO.FIRST_DRAWL_LIST);
		ILineOfCreditDAO lineOfCreditDao = KLSDataAccessFactory.getLineOfCreditDAO();
		for (int i = 0; i < debitAccountList.size(); i++) {
			if (debitAccountList.get(i) instanceof LineOfCredit) {
				LineOfCredit lineOfCredit = (LineOfCredit) debitAccountList.get(i);
				lineOfCredit = lineOfCreditDao.getLineOfCreditById(lineOfCredit.getId(), false);
				if (debitAmountList.get(i) instanceof AccountingMoney) {
					AccountingMoney debitAmount = (AccountingMoney) debitAmountList.get(i);
					AccountingMoney currentBalance = lineOfCredit.getCurrentBalance();
					currentBalance = currentBalance.add(debitAmount);
					lineOfCredit.setCurrentBalance(currentBalance);
					em.persist(lineOfCredit);
				}
			}
		}
		updateFirstDrawlFlag(em, firstDrawlList);
		logger.info("End: Updated the debit amount in line of credit account in updateDebitAmount() method.");
	}

	/**
	 * 
	 * @param <T>
	 * @param firstDrawlList
	 */
	private <T> void updateFirstDrawlFlag(EntityManager em, List<T> firstDrawlList) {

		logger.info("Start: Updating the first drawl flag in line of credit account in updateFirstDrawlFlag() method.");
		ILineOfCreditDAO lineOfCreditDao = KLSDataAccessFactory.getLineOfCreditDAO();
		for (int i = 0; i < firstDrawlList.size(); i++) {
			if (firstDrawlList.get(i) instanceof LineOfCredit) {
				LineOfCredit lineOfCredit = (LineOfCredit) firstDrawlList.get(i);
				lineOfCredit = lineOfCreditDao.getLineOfCreditById(lineOfCredit.getId(), false);
				lineOfCredit.setIsFirstDrawal(1);
				em.persist(lineOfCredit);
			}
		}
		logger.info("End: Updating the first drawl flag in line of credit account in updateFirstDrawlFlag() method.");
	}

	@Override
	public Integer moveCurrentTransactionRecordsToTransactionHistory(List<Integer> pacsIdsList) {
		logger.info("Start: Deleting all the current transaction data from the database in deleteAllCurrentTransactions() method.");
		Integer num = 0;
		EntityManager em = EntityManagerUtil.getEntityManager();
		boolean flgTrans = false;
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
			flgTrans = true;
		}
		try {
			
			Query query = em
					.createNativeQuery("INSERT INTO kls_schema.transaction_history SELECT * FROM kls_schema.current_transaction ct where ct.status=1 and ct.pacs_id in (:pacsIdsList)")
			.setParameter("pacsIdsList", pacsIdsList);
			query.executeUpdate();
			query = em.createQuery("delete from CurrentTransaction c where c.postedStatus = :postedStatus and c.pacs.id in (:pacsIdsList)");
			query.setParameter("postedStatus", 1);
			query.setParameter("pacsIdsList", pacsIdsList);
			num = query.executeUpdate();
			if (flgTrans)
				em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(num + " Records are deleted Succesfully from the Current Transaction when those records are inserted in to trasaction history");
		return num;
	}

	@Override
	public List<CurrentTransaction> getCurrentTransactionByStatus(List<Integer> pacsIdsList) {

		logger.info("Start: Fetching all the current transaction data from the database in getCurrentTransactionByStatus() method.");
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			currentTransactionList = em.createQuery("SELECT c FROM CurrentTransaction c where c.postedStatus = :postedStatus and c.pacs.id in (:pacsIdsList)")
					.setParameter("postedStatus", 1)
					.setParameter("pacsIdsList", pacsIdsList).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all current transaction records from the database.");
			throw new DataAccessException("Error while retriving all current transaction records.", e.getCause());
		}
		logger.info("End: Fetching all the current transaction data from the database in getCurrentTransactionByStatus() method.");
		return currentTransactionList;
	}

	@Override
	public List<CurrentTransaction> getCurrentTransactionByStatus(String loanType,List<Integer> pacsIdsList) {

		logger.info("Start: Fetching all the current transaction data by loan type from the database in getCurrentTransactionByStatus() method.");
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String qryString = "SELECT c FROM CurrentTransaction c where c.postedStatus = :postedStatus and c.lineOfCredit.product.productType.loanType =:loanType and c.pacs.id in (:pacsIdsList)";
			Query qry = em.createQuery(qryString);
			qry.setParameter("postedStatus", 1);
			qry.setParameter("pacsIdsList", pacsIdsList);
			qry.setParameter("loanType", loanType);

			currentTransactionList = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all current transaction records from the database by loan type.");
			throw new DataAccessException("Error while retriving all current transaction records by loan type.", e.getCause());
		}
		logger.info("End: Fetching all the current transaction data by loan type from the database in getCurrentTransactionByStatus() method.");
		return currentTransactionList;
	}

	@Override
	public void saveCreditCurrentTransaction(List<CurrentTransaction> currentTransactionList, Map<String, Object> creditAccountMap) {

		logger.info("Start: Saving the current transaction data to the database in saveCreditCurrentTransaction() method.");
		boolean flgTrans = false;
		
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			IAccountDAO accountDao = KLSDataAccessFactory.getAccountDAO();
			IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			updateCreditAmounts(em, creditAccountMap);
			for (CurrentTransaction currentTransaction : currentTransactionList) {
				Account account = accountDao.getAccount(currentTransaction.getAccount(), false);
				currentTransaction.setAccount(account);
				Pacs pacs = pacsDao.getPacs(currentTransaction.getPacs(), false);
				currentTransaction.setPacs(pacs);
				em.persist(currentTransaction);
			}
			if (flgTrans){
				em.getTransaction().commit();
			
			}
		} catch (Exception excp) {
			if (flgTrans && em.getTransaction().isActive())
				em.getTransaction().rollback();
			logger.error("Could not commit the transaction of saving the credit current transaction data.");
			throw new DataAccessException("Could not commit the transaction of saving the credit current transaction data.", excp.getCause());
		}
		logger.info("End: Successfully saved the current transaction to the database in saveCreditCurrentTransaction() method.");
		return;
	}

	private void updateCreditAmounts(EntityManager em, Map<String, Object> creditAccountMap) {

		logger.info("Start: Updating the credit amount in line of credit account in updateCreditAmounts() method.");
		Map<Long, AccountingMoney> creditPrincipalAmountMap = (Map<Long, AccountingMoney>) creditAccountMap
				.get(CurrentTransactionDAO.CREDIT_PRINCIPAL_AMOUNT_MAP);
		Map<Long, AccountingMoney> creditInterestAmountMap = (Map<Long, AccountingMoney>) creditAccountMap
				.get(CurrentTransactionDAO.CREDIT_INTEREST_AMOUNT_MAP);
		Map<Long, AccountingMoney> creditPenalInterestAmountMap = (Map<Long, AccountingMoney>) creditAccountMap
				.get(CurrentTransactionDAO.CREDIT_PENAL_INTEREST_AMOUNT_MAP);
		Map<Long, AccountingMoney> creditInterestAccruedAmountMap = (Map<Long, AccountingMoney>) creditAccountMap
				.get(CurrentTransactionDAO.CREDIT_INTEREST_ACCRUED_AMOUNT_MAP);
		Map<Long, AccountingMoney> creditOverdueInterestAmountMap = (Map<Long, AccountingMoney>) creditAccountMap
				.get(CurrentTransactionDAO.CREDIT_OVERDUE_INTEREST_AMOUNT_MAP);
		ILineOfCreditDAO lineOfCreditDao = KLSDataAccessFactory.getLineOfCreditDAO();
		if (creditPrincipalAmountMap != null) {
			for (Long id : creditPrincipalAmountMap.keySet()) {
				LineOfCredit lineOfCredit = lineOfCreditDao.getLineOfCreditById(id, false);
				AccountingMoney creditPrincipalAmount = creditPrincipalAmountMap.get(id);
				logger.info(" creditPrincipalAmount : " + creditPrincipalAmount);
				AccountingMoney currentBalance = lineOfCredit.getCurrentBalance();
				logger.info(" current balance amount from the db : " + currentBalance);
				currentBalance = currentBalance.add(creditPrincipalAmount);
				logger.info(" current balance after addition : " + currentBalance);
				lineOfCredit.setCurrentBalance(currentBalance);
				em.persist(lineOfCredit);
			}
		}
		if (creditInterestAmountMap != null) {
			for (Long id : creditInterestAmountMap.keySet()) {
				LineOfCredit lineOfCredit = lineOfCreditDao.getLineOfCreditById(id, false);
				AccountingMoney creditInterestAmount = creditInterestAmountMap.get(id);
				logger.info(" creditInterestAmount : " + creditInterestAmount);
				BigDecimal interestReceivable = lineOfCredit.getInterestReceivable();
				logger.info(" interestReceivable from the db : " + interestReceivable);
				interestReceivable = interestReceivable.add(creditInterestAmount.getMoney().getAmount());
				logger.info(" interestReceivable after addition : " + interestReceivable);
				lineOfCredit.setInterestReceivable(interestReceivable);
				em.persist(lineOfCredit);
			}
		}
		if (creditInterestAccruedAmountMap != null) {
			for (Long id : creditInterestAccruedAmountMap.keySet()) {
				LineOfCredit lineOfCredit = lineOfCreditDao.getLineOfCreditById(id, false);
				AccountingMoney creditInterestAmount = creditInterestAccruedAmountMap.get(id);
				logger.info(" creditInterestAccruedAmount : " + creditInterestAmount);
				BigDecimal interestAccrued = lineOfCredit.getInterestAccrued().setScale(0, RoundingMode.HALF_UP);
				logger.info(" Interestaccrued from the db : " + interestAccrued);
				interestAccrued = interestAccrued.add(creditInterestAmount.getMoney().getAmount());
				logger.info(" Interestaccrued after addition : " + interestAccrued);
				lineOfCredit.setInterestAccrued(interestAccrued);
				interestAccrued.compareTo(BigDecimal.ZERO);
				em.persist(lineOfCredit);
			}
		}
		if (creditPenalInterestAmountMap != null) {
			for (Long id : creditPenalInterestAmountMap.keySet()) {
				LineOfCredit lineOfCredit = lineOfCreditDao.getLineOfCreditById(id, false);
				AccountingMoney creditPenalInterestAmount = creditPenalInterestAmountMap.get(id);
				logger.info(" creditPenalInterestAmount : " + creditPenalInterestAmount);
				BigDecimal penalInterestReceivable = lineOfCredit.getPenalInterestReceivable();
				logger.info(" penalInterestReceivable from the db : " + penalInterestReceivable);
				penalInterestReceivable = penalInterestReceivable.add(creditPenalInterestAmount.getMoney().getAmount());
				logger.info(" penalInterestReceivable after addition : " + penalInterestReceivable);
				lineOfCredit.setPenalInterestReceivable(penalInterestReceivable);
				em.persist(lineOfCredit);
			}
		}
		if (creditOverdueInterestAmountMap != null) {
			for (Long id : creditOverdueInterestAmountMap.keySet()) {
				LineOfCredit lineOfCredit = lineOfCreditDao.getLineOfCreditById(id, false);
				AccountingMoney creditOverdueInterestAmount = creditOverdueInterestAmountMap.get(id);
				logger.info(" creditOverdueInterestAmount : " + creditOverdueInterestAmount);
				BigDecimal overdueInterest = lineOfCredit.getOverdueInterest().setScale(0, RoundingMode.HALF_UP);
				logger.info(" overdueInterest from the db : " + overdueInterest);
				overdueInterest = overdueInterest.add(creditOverdueInterestAmount.getMoney().getAmount());
				logger.info(" overdueInterest after addition : " + overdueInterest);
				lineOfCredit.setOverdueInterest(overdueInterest);
				em.persist(lineOfCredit);
			}
		}
		logger.info("End: Updated the credit amount in line of credit account in updateCreditAmounts() method.");
	}

	/**
	 * 
	 */
	public List<CurrentTransaction> getCurrentTransactionsByDate(String date) {

		logger.info("Start: Fetching the current transaction data from the database in getCurrentTransactionsByDate() method.");
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT c FROM CurrentTransaction c where to_char(c.businessDate,'YYYY-MM-DD') = :date");
			query.setParameter("date", date);
			currentTransactionList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving current transaction records from the database for the effective date.");
			throw new DataAccessException("Error while retriving current transaction records for the effective date.", e.getCause());
		}
		logger.info("End: Fetching the current transaction data from the database in getCurrentTransactionsByDate() method.");
		return currentTransactionList;
	}

	/**
	 * 
	 */
	public <T> void saveKindCurrentTransaction(List<CurrentTransaction> currentTransactionList, Map<String, List<T>> kindAccountMap) {

		logger.info("Start: Saving the current kind transaction data to the database in saveKindCurrentTransaction() method.");
		EntityManager em = EntityManagerUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			IAccountDAO accountDao = KLSDataAccessFactory.getAccountDAO();
			IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
			if (kindAccountMap != null && !kindAccountMap.isEmpty()) {
				updateKindAmount(em, kindAccountMap);
			}
			for (CurrentTransaction currentTransaction : currentTransactionList) {
				Account account = accountDao.getAccount(currentTransaction.getAccount(), false);
				currentTransaction.setAccount(account);
				Pacs pacs = pacsDao.getPacs(currentTransaction.getPacs(), false);
				currentTransaction.setPacs(pacs);
				em.persist(currentTransaction);
			}
			tx.commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the kind current transaction data.");
			tx.rollback();
			throw new DataAccessException("Could not commit the transaction of saving the kind current transaction data.", excp.getCause());
		}
		logger.info("End: Successfully saved the kind current transaction to the database in saveKindCurrentTransaction() method.");
		return;
	}

	/**
	 * 
	 * @param em
	 * @param kindMap
	 */
	private <T> void updateKindAmount(EntityManager em, Map<String, List<T>> kindMap) {

		logger.info("Start: Updating the kind amount in line of credit account in updateKindAmount() method.");
		List<T> debitAccountList = kindMap.get(CurrentTransactionDAO.ACCOUNTS_LIST);
		List<T> debitAmountList = kindMap.get(CurrentTransactionDAO.AMOUNTS_LIST);
		ILineOfCreditDAO lineOfCreditDao = KLSDataAccessFactory.getLineOfCreditDAO();
		for (int i = 0; i < debitAccountList.size(); i++) {
			if (debitAccountList.get(i) instanceof LineOfCredit) {
				LineOfCredit lineOfCredit = (LineOfCredit) debitAccountList.get(i);
				lineOfCredit = lineOfCreditDao.getLineOfCreditById(lineOfCredit.getId(), false);
				if (debitAmountList.get(i) instanceof AccountingMoney) {
					AccountingMoney kindAmount = (AccountingMoney) debitAmountList.get(i);
					AccountingMoney kindBalance = lineOfCredit.getKindBalance();
					kindBalance = kindBalance.add(kindAmount);
					lineOfCredit.setKindBalance(kindBalance);
					AccountingMoney currentBalance = lineOfCredit.getCurrentBalance();
					currentBalance = currentBalance.add(kindAmount);
					lineOfCredit.setCurrentBalance(currentBalance);
					em.persist(lineOfCredit);
				}
			}
		}
		logger.info("End: Updated the kind amount in line of credit account in updateKindAmount() method.");
	}

	@Override
	public List<TransactionRecordByProductAndCrdr> getCurrentTransactionsByProductAndCrdr() {

		logger.info("Start: Fetching the current transaction data from the database in getCurrentTransactionsByProductAndCrdr() method.");
		List<TransactionRecordByProductAndCrdr> currentTransactionList = new ArrayList<TransactionRecordByProductAndCrdr>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryStr = "select NEW com.vsoftcorp.kls.dataaccess.transaction.result.classes."
					+ "TransactionRecordByProductAndCrdr(sum(ct.transactionAmount),s.product.id,ct.crDr,loc.id,loc.account.id)"
					+ " from CurrentTransaction ct,LineOfCredit loc ," + "Product p ,Scheme s where ct.lineOfCredit.id"
					+ " = loc.id and loc.scheme.id = s.id and s.product.id = p.id " + "group by s.product.id,ct.crDr,loc.id,loc.account.id";
			TypedQuery<TransactionRecordByProductAndCrdr> query = em.createQuery(queryStr, TransactionRecordByProductAndCrdr.class);
			currentTransactionList = query.getResultList();
			logger.info("results size : " + currentTransactionList.size());
		} catch (Exception e) {
			logger.error("Error while retriving current transaction records from the database by product and crdr.");
			throw new DataAccessException("Error while retriving current transaction records by product and crdr.", e.getCause());
		}
		logger.info("End: Fetching the current transaction data from the database in getCurrentTransactionsByProductAndCrdr() method.");
		return currentTransactionList;
	}

	public List<CurrentTransaction> saveCurrentTransactionsList(List<CurrentTransaction> currentTransactionsList) {
		logger.info("Start: saving the currentTransactionsList  in saveCurrentTransactionsList() method.");
		List<CurrentTransaction> list = new ArrayList<>();
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			for (CurrentTransaction currentTransaction : currentTransactionsList) {
				if (currentTransactionsList.size() > 0) {
					logger.info("Inside the saveCurrentTransactionsList() method currentTransactionsList size: " + currentTransactionsList.size());
					currentTransaction = em.merge(currentTransaction);
					list.add(currentTransaction);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while saving the currentTransactionsList  in saveCurrentTransactionsList() method.");
			throw new DataAccessException("Error while saving the currentTransactionsList in saveCurrentTransactionsList() method.", e.getCause());
		}
		return list;
	}

	@Override
	public List<Map> getTransactionsGroupedByDateProductPacs(Integer crDr, String loanType,List<Integer> pacsIdsList) {
		logger.info("Start: Calling  getLocsGroupedByDateProductPacs() method.");
		List<Map> groupedTransList = new ArrayList<Map>();

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select new Map(sum(ct.transactionAmount) as totalAmount,");
			query.append("lc.product.id as productId,");
			query.append("ct.pacs.id as pacsId,");
			query.append("ct.businessDate as businessDate)");
			query.append(" from ");
			query.append("LineOfCredit lc,");
			query.append("CurrentTransaction ct ");
			query.append(" where ");
			query.append(" lc.id = ct.lineOfCredit.id ");
			query.append(" and ct.lineOfCredit.product.productType.loanType =:loanType  ");
			query.append(" and ct.pacs.id in (:pacsIdsList)");
			query.append(" and ct.crDr =:crDr and ct.transactionType <> 'B'");
			// query.append(" and ( ct.transactionCode.compareTo(com.vsoftcorp.kls.valuetypes.transaction.TransactionCode.PRINCIPAL_BAL)) ");
			// // or
			// ct.transactionCode.compareTo(TransactionCode.SHARE_TRANSFER) or
			// ct.transactionCode.compareTo(TransactionCode.INSURANCE_DEDUCTION)
			// or
			// ct.transactionCode.compareTo(TransactionCode.PROCESSING_FEE))");
			query.append(" and ( ct.transactionCode = 1 or ct.transactionCode = 5 or ct.transactionCode = 6 or ct.transactionCode = 19 ) ");
			query.append(" group by lc.product.id,	ct.pacs.id,	ct.businessDate");

			Query qry = em.createQuery(query.toString());
			qry.setParameter("crDr", crDr);
			qry.setParameter("pacsIdsList", pacsIdsList);
			qry.setParameter("loanType", loanType);
			groupedTransList = qry.getResultList();

		} catch (Exception excp) {
			logger.error("Error while retriving transactions with grouping. " + excp.getMessage());
			throw new DataAccessException("Error while retriving transactions with grouping.", excp.getCause());
		}
		logger.info("End: Calling  getLocsGroupedByDateProductPacs() method.");
		return groupedTransList;
	}

	@Override
	public List<Map> getTransactionsGroupedByDateProductPacsLoc(Integer crDr, String loanType,List<Integer> pacsIdsList) {
		logger.info("Start: Calling  getLocsGroupedByDateProductPacs() method.");
		List<Map> groupedTransList = new ArrayList<Map>();

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select new Map(sum(ct.transactionAmount) as totalAmount,");
			query.append("lc.product.id as productId,");
			query.append("ct.pacs.id as pacsId,");
			query.append("ct.lineOfCredit.id as locId)");
			query.append(" from ");
			query.append("LineOfCredit lc,");
			query.append("CurrentTransaction ct ");
			query.append(" where ");
			query.append(" lc.id = ct.lineOfCredit.id ");
			query.append(" and ct.lineOfCredit.product.productType.loanType =:loanType  ");
			query.append(" and ct.pacs.id in (:pacsIdsList)");
			query.append(" and ct.crDr =:crDr and ct.transactionType <> 'B' ");
			query.append(" and ( ct.transactionCode = 1 or ct.transactionCode = 5 or ct.transactionCode = 6 or ct.transactionCode = 19 ) ");
			query.append(" group by lc.product.id,	ct.pacs.id,	ct.lineOfCredit.id");

			Query qry = em.createQuery(query.toString());
			qry.setParameter("crDr", crDr);
			qry.setParameter("pacsIdsList", pacsIdsList);
			qry.setParameter("loanType", loanType);
			groupedTransList = qry.getResultList();

		} catch (Exception excp) {
			logger.error("Error while retriving transactions with grouping. " + excp.getMessage());
			throw new DataAccessException("Error while retriving transactions with grouping.", excp.getCause());
		}
		logger.info("End: Calling  getLocsGroupedByDateProductPacs() method.");
		return groupedTransList;
	}

	@Override
	public List<Map> getTransactionsGroupedByDateProductPacsTransCode(Integer crDr, String loanType,List<Integer> pacsIdsList) {
		logger.info("Start: Calling  getTransactionsGroupedByDateProductPacsTransCode() method.");
		List<Map> groupedTransList = new ArrayList<Map>();

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select new Map(sum(ct.transactionAmount) as totalAmount,");
			query.append("lc.product.id as productId,");
			query.append("ct.pacs.id as pacsId,");
			query.append("ct.businessDate as businessDate,");
			query.append("ct.transactionCode as transactionCode,ct.lineOfCredit.id as locId)");
			query.append(" from ");
			query.append("LineOfCredit lc,");
			query.append("CurrentTransaction ct ");
			query.append(" where ");
			query.append(" lc.id = ct.lineOfCredit.id ");
			query.append(" and ct.lineOfCredit.product.productType.loanType =:loanType  ");
			query.append(" and ct.pacs.id in (:pacsIdsList)");
			query.append(" and ct.crDr =:crDr and ct.transactionType <> 'B' ");
			query.append(" group by lc.product.id,	ct.pacs.id,	ct.businessDate, ct.transactionCode,ct.lineOfCredit.id");

			Query qry = em.createQuery(query.toString());
			qry.setParameter("crDr", crDr);
			qry.setParameter("pacsIdsList", pacsIdsList);
			qry.setParameter("loanType", loanType);
			groupedTransList = qry.getResultList();

		} catch (Exception excp) {
			logger.error("Error while retriving transactions with grouping. " + excp.getMessage());
			throw new DataAccessException("Error while retriving transactions with grouping.", excp.getCause());
		}
		logger.info("End: Calling  getTransactionsGroupedByDateProductPacsTransCode() method.");
		return groupedTransList;
	}

	@Override
	public Date getFirstDrawalLocDates(Long id) {
		Date d = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
			
			 d = (Date) em.createQuery("select min(c.businessDate) from TransactionHistory c where c.transactionCode = 1 and c.lineOfCredit.id = "+id+" group by c.lineOfCredit.id order by c.lineOfCredit.id").getSingleResult();
			logger.info("d trans history=="+d);
			
		}catch(NoResultException e){
			try{
				 d = (Date) em.createQuery("select min(c.businessDate) from CurrentTransaction c where c.transactionCode = 1 and c.lineOfCredit.id = "+id+" group by c.lineOfCredit.id order by c.lineOfCredit.id").getSingleResult();
			
			logger.info("d crrnt=="+d);
			}catch(NoResultException ne){
				return new Date();
			}
			//e.printStackTrace();
			
		}
		catch(Exception e){
			
			e.printStackTrace();
			//return new Date();
			
		}
		return d;
	}

	@Override
	public List<CurrentTransaction> getSubsidyAllSubsidyTransactions(LineOfCredit loc) {

		logger.info("Start: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.");
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
	
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			currentTransactionList = em.createQuery("select k FROM CurrentTransaction k where k.lineOfCredit.id = "+loc.getId()+" and k.transactionCode = "+TransactionCode.INTEREST_SUBSIDY.getValue()+"").getResultList();
			logger.info("currentTransactionList : "+currentTransactionList.size());
		} catch (Exception e) {
			logger.error("Error while retriving all current transaction records from the database.");
			throw new DataAccessException("Error while retriving all current transaction records.", e.getCause());
		}
		logger.info("End: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.");
		return currentTransactionList;
	}

	@Override
	public List<TransactionHistory> getSubsidyAllSubsidyHisTransactions(
			LineOfCredit loc) {

		logger.info("Start: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.");
		List<TransactionHistory> currentTransactionList = new ArrayList<TransactionHistory>();
	
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			currentTransactionList = em.createQuery("select k FROM TransactionHistory k where k.lineOfCredit.id = "+loc.getId()+" and k.transactionCode = "+TransactionCode.INTEREST_SUBSIDY.getValue()+"").getResultList();
			logger.info("currentTransactionList : "+currentTransactionList.size());
		} catch (Exception e) {
			logger.error("Error while retriving all current transaction records from the database.");
			throw new DataAccessException("Error while retriving all current transaction records.", e.getCause());
		}
		logger.info("End: Fetching all the current transaction data from the database in getAllCurrentTransactions() method.");
		return currentTransactionList;
	}

	@Override
	public List<CurrentTransaction> getTransactionsByVoucherNumber(
			String voucherNumber) {

		logger.info("Strat: Fetching Transactions based on voucher number");
		List<CurrentTransaction> currentTransactions = new ArrayList<CurrentTransaction>();
		try{
			EntityManager  em = EntityManagerUtil.getEntityManager();
			currentTransactions = em.createQuery("select c from CurrentTransaction c where c.voucherNumber='"+voucherNumber+"'").getResultList();
			
		}catch(NoResultException nre){
			logger.info("No Records Found for voucher "+voucherNumber);
			return null;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("End: Fetching Transactions based on voucher number");
		
		return currentTransactions;
	}
	
	@Override
	public AccountingMoney getSubsidyTransactionsByLocId(Long locId,Integer prdId,Date businessDate){
		//List<AccountingMoney> subsidyAmtList=new ArrayList<AccountingMoney>();
		logger.info("inside subsidy dao service::");
		AccountingMoney subsidyAmt=AccountingMoney.ZERO;
		try{
			EntityManager  em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select sum(ct.transactionAmount) as totalAmount");
			query.append(" from ");
			query.append("LineOfCredit lc,");
			query.append("CurrentTransaction ct ");
			query.append(" where ");
			query.append(" lc.id = ct.lineOfCredit.id ");
			query.append(" and ct.lineOfCredit.product.id =:prdId  ");
			query.append(" and ct.lineOfCredit.id =:locId and ct.transactionType <> 'B' and ct.businessDate=:businessDate ");
			query.append(" and ct.transactionCode=29 group by lc.product.id,	ct.pacs.id,	ct.businessDate, ct.transactionCode");

			Query qry = em.createQuery(query.toString());
			qry.setParameter("prdId", prdId);
			qry.setParameter("locId", locId);
			qry.setParameter("businessDate", businessDate);
			subsidyAmt = (AccountingMoney)qry.getSingleResult();			
		}catch(NoResultException nre){
			logger.info("No Records Found for voucher "+subsidyAmt);
			return null;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return subsidyAmt;
	}
	@Override
	public CurrentTransaction getCurrentTransactionByAccountNum(String accountNum,Date transactionDate){
		logger.info("Start : getCurrentTransactionByAccountNum method");
		CurrentTransaction master=new CurrentTransaction();
		List<CurrentTransaction> masterList=new ArrayList<CurrentTransaction>();
		try{
			EntityManager em=EntityManagerUtil.getEntityManager();
			Query qry=em.createQuery("select c from CurrentTransaction c where c.accountNumber=:accountNum and c.businessDate=:transactionDate order by c.id desc limit 1");
			qry.setParameter("accountNum", accountNum);
			qry.setParameter("transactionDate", transactionDate);
			masterList=qry.getResultList();
			if(masterList.size()>0)
				master=masterList.get(0);
		}catch(NoResultException nre){
			logger.info("No Records Found for account"+accountNum);
			return null;
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("End : getCurrentTransactionByAccountNum method");
		return master;
	}
	@Override
	public String saveReversalTransaction(CurrentTransaction master){
		logger.info("Start : saveReversalTransaction method");
		String returnMsg="";
		boolean flag=false;
		try{
			logger.info("checking id:::"+master.getId());
			EntityManager em=EntityManagerUtil.getEntityManager();
			if(!em.getTransaction().isActive()){
				em.getTransaction().begin();
				flag=true;
			}
			if(master.getId()==null)
			em.persist(master);
			logger.info("after persist id==="+master.getId());
			if(flag){
				em.getTransaction().commit();
				returnMsg="Successfully inserted";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("End : saveReversalTransaction method");
		return returnMsg;
		
	}
	@Override
	public List<CurrentTransaction> getMiniStatement(String accountNum,Date transactionDate){
		logger.info("Start : Calling from getMiniStatement method .");
		List<CurrentTransaction> currentTransactionList=new ArrayList<CurrentTransaction>();
		try{
			EntityManager em=EntityManagerUtil.getEntityManager();
			String qryStr="select ct from CurrentTransaction ct where ct.accountNumber=:accountNum order by ct.businessDate desc";
			Query qry=em.createQuery(qryStr);
			qry.setParameter("accountNum", accountNum);
			currentTransactionList=qry.getResultList();
			
		}catch(NoResultException nre){
			logger.info("No Records Found for account"+accountNum);
			return null;
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("End : Calling getMiniStatement method .");
		return currentTransactionList;
	}

	@Override
	public List<CurrentTransaction> getTransactionsByPacs(String loanType, Integer pacsID) {
		
		logger.info("Start: Fetching all the current transaction data by loan type from the database in getTransactionsByPacs() method.");
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String qryString = "SELECT c FROM CurrentTransaction c where c.postedStatus = :postedStatus and c.lineOfCredit.product.productType.loanType =:loanType and c.pacs.id=:pacsId ";
			Query qry = em.createQuery(qryString);
			qry.setParameter("postedStatus", 1);
			qry.setParameter("loanType", loanType);
			qry.setParameter("pacsId", pacsID);

			currentTransactionList = qry.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all current transaction records from the database by loan type.");
			throw new DataAccessException("Error while retriving all current transaction records by loan type.", e.getCause());
		}
		logger.info("End: Fetching all the current transaction data by loan type from the database in getTransactionsByPacs() method.");
		return currentTransactionList;
		
	}
}
