package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;
import com.vsoftcorp.time.Date;

public class LineOfCreditDAO implements ILineOfCreditDAO {
	private static final Logger logger = Logger.getLogger(LineOfCreditDAO.class);

	@Override
	public void saveLineOfCredit(LineOfCredit lineOfCredit) {
		logger.info("Start: Saving the LineOfCredit data to the database in saveLineOfCredit() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(lineOfCredit);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the lineOfCredit"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the lineOfCredit data to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully saved the LineOfCredit to the database in saveLineOfCredit() method.");

	}

	@Override
	public void saveLineOfCredit(LineOfCredit lineOfCredit, BorrowingsLineOfCredit bLoc) {
		logger.info("Start: Saving the LineOfCredit data to the database in saveLineOfCredit() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			if (bLoc != null) {
				em.persist(bLoc);
				lineOfCredit.setBorrowingsLocId(bLoc.getId());
			}
			em.persist(lineOfCredit);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the lineOfCredit"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the lineOfCredit data to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully saved the LineOfCredit to the database in saveLineOfCredit() method.");

	}

	@Override
	public LineOfCredit getLineOfCredit(int season, int crop, Long custId, int product) {

		logger.info("Start: Fetching  LineOfCredit for given header data from the database in getLineOfCredit() method.");
		LineOfCredit lineOfCredit = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT l FROM LineOfCredit l where l.season.id = :season and l.crop.id = :crop and l.customerId = :custId and l.product.id = :product");
			query.setParameter("season", Long.parseLong(String.valueOf(season)));
			query.setParameter("crop", crop);
			query.setParameter("custId", custId);
			query.setParameter("product", product);

			List list = query.getResultList();
			if (!list.isEmpty())
				lineOfCredit = (LineOfCredit) list.get(0);
		} catch (Exception e) {
			logger.error("Error while retriving  LineOfCredit records for given info from the database");
			throw new DataAccessException(
					"Error while retriving LineOfCreditrecords for given info from the database",
					e.getCause());
		}
		logger.info("End: Fetching the LineOfCredit for given info from the database in getLineOfCredit() method.");
		return lineOfCredit;

	}

	@Override
	public void updateLineOfCredit(LineOfCredit loc) {
		// TODO Auto-generated method stub
		logger.info("Start: Updating the lineOfCredit  data to the database in updateLineOfCredit() method.");
		boolean flgTrans = false;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			if (loc != null) {
				if(!em.getTransaction().isActive()) {
					em.getTransaction().begin();
					flgTrans = true;
				}
				em.merge(loc);
				if(flgTrans)
					em.getTransaction().commit();

			}
		} catch (Exception excp) {
			
			if(flgTrans && em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.error("Could not commit the transaction of updating the LineOfCredit"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the lineOfCredit  data to the database.",
					excp.getCause());
		}
		if (loc == null) {
			logger.error("Trying to update the lineOfCredit  record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the lineOfCredit  record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the lineOfCredit  data to the database in updatelineOfCredit() method.");
	}

	@Override
	public Money getTotalSanctionAmount(Long customerId, Date financialYearBeginDate,
			Date financialYearEndDate) {
		logger.info("Start: in getToalSanctionAmount()" + customerId
				+ financialYearBeginDate.toString() + financialYearEndDate.toString());
		Money totalSanctionAmount = Money.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select sum(sanctionedAmount) from LineOfCredit l where l.account.accountProperty.customerId=:customerId and sanctionedDate between :fyBeginDate and :fyEndDate");
			query.setParameter("fyBeginDate", financialYearBeginDate);
			query.setParameter("fyEndDate", financialYearEndDate);
			query.setParameter("customerId", customerId);
			totalSanctionAmount = (Money) query.getSingleResult();
		} catch (Exception exception) {
			logger.error("Error: in getTotalSanctionAmount()");
			return Money.ZERO;
		}
		logger.info("End: in getTotalSanctionAmount() ToatalSanctionAmount:" + totalSanctionAmount);
		return totalSanctionAmount;
	}

	/**
	 * 
	 */
	public List getLineOfCreditAccountsList(Long accountId, boolean isCloseSession,
			String queryString) {

		logger.info("Start: Fetching the line of credit accounts from the database in getLineOfCreditAccountsList() method.");
		List accountList = new ArrayList();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery(queryString);
			query.setParameter("status", LineOfCreditStatus.Active);
			query.setParameter("accountId", accountId);
			query.setParameter("loanType", "C");
			accountList = query.getResultList();
		} catch (Exception excp) {
			logger.error("Could not fetch list of all line of credit accounts"
					+ "from the database.Exception thrown.");
			throw new DataAccessException("Could not fetch list of all line of credit accounts.",
					excp.getCause());
		} finally {
			if (isCloseSession)
				EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the line of credit accounts successfully from the database in getLineOfCreditAccountsList() method.");
		return accountList;
	}

	@Override
	public LineOfCredit getLineOfCreditById(Long id, boolean isCloseSession) {
		logger.info("Fetching loc");
		EntityManager em = EntityManagerUtil.getEntityManager();
		LineOfCredit lineOfCredit = null;
		try {
			TypedQuery<LineOfCredit> query = em.createQuery(
					"SELECT l FROM LineOfCredit l WHERE l.id = :id", LineOfCredit.class);
			lineOfCredit = query.setParameter("id", id).getSingleResult();

		} catch (Exception e) {
			logger.error("Error while retriving  LineOfCredit ");
			throw new DataAccessException("Error while retriving loc", e.getCause());
		} 
		logger.info("fetching loc done");
		return lineOfCredit;
	}

	@Override
	public void updateLineOfCreditInterestbyId(Long id, Money theInterestDueAmount) {
		logger.info("Fetching loc" + id);
		logger.info("theInterestDueAmount:::" + theInterestDueAmount);
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			Query query = em
					.createQuery("UPDATE LineOfCredit l SET interestDue = :theAmount where l.id=:id");
			query.setParameter("theAmount", theInterestDueAmount);
			query.setParameter("id", id);
			int count = query.executeUpdate();
			logger.info("count" + count);

		} catch (Exception e) {
			logger.error("Error while retriving  LineOfCredit ");
			throw new DataAccessException("Error while retriving loc", e.getCause());
		}
		logger.info("fetching loc done");

	}

	@Override
	public Boolean isFirstDrawal(Long accountId, Long seasonId) {
		logger.info("is first drawal AccountId:-" + accountId + "seasonId:-" + seasonId);
		EntityManager em = EntityManagerUtil.getEntityManager();
		Boolean isFirstDrawal = false;
		try {
			Query query = em
					.createQuery("select l from LineOfCredit l where l.account.id = :accountId and l.season.id=:seasonId and l.isFirstDrawal=:isFirstDrawal");
			query.setParameter("accountId", accountId);
			query.setParameter("seasonId", seasonId);
			query.setParameter("isFirstDrawal", 1);
			List<LineOfCredit> list = query.getResultList();
			if (list != null && list.size() != 0)
				isFirstDrawal = true;

		} catch (Exception e) {
			logger.error("Error while checking LineOfCredit is first drawal or not");
			throw new DataAccessException(
					"Error while checking LineOfCredit is first drawal or not", e.getCause());
		}
		logger.info("End:is first drawal");
		return isFirstDrawal;
	}

	@Override
	public List<LineOfCredit> getLocListByCustomerId(Long customerId) {

		logger.info("Start: Fetching the loc list from the database in getLocListByCustomerId() method.");
		List<LineOfCredit> list = new ArrayList<LineOfCredit>();
		logger.info(" customerId : " + customerId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (customerId != null) {
				Query query = em
						.createQuery("SELECT l FROM LineOfCredit l where l.customerId = :customerId");
				query.setParameter("customerId", customerId);
				list = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loc id list from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the loc list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the loc id list from the database in getLocListByCustomerId() method.");
		return list;
	}

	@Override
	public void updateCurrentBalence(Long Id, AccountingMoney CurrentBalence) {
		// TODO Auto-generated method stub
		logger.info("Start: Updating the CurrentBalence  data to the database in updateCurrentBalence() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (Id != null) {
				em.getTransaction().begin();
				LineOfCredit loc = em.find(LineOfCredit.class, Id);
				loc.setCurrentBalance(CurrentBalence);
				em.merge(loc);
				em.getTransaction().commit();

			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the CurrentBalence"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the CurrentBalence  data to the database.",
					excp.getCause());
		} /*
		 * finally { EntityManagerUtil.closeSession(); }
		 */

		logger.info("End: Successfully updated the CurrentBalence  data to the database in updateCurrentBalence() method.");

	}

	@Override
	public boolean checkLoanStatus(Long custId) {
		// TODO Auto-generated method stub

		logger.info("Start: Fetching  LineOfCredit for given cust_id from the database in checkLoanStatus() method.");
		List<LineOfCredit> lineOfCredit = null;
		boolean status = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT l FROM LineOfCredit l where l.customerId = :custId and l.currentBalance < :current_balance");
			query.setParameter("custId", custId);
			query.setParameter("current_balance", AccountingMoney.ZERO);
			lineOfCredit = query.getResultList();
			if (!lineOfCredit.isEmpty())
				status = true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving  LineOfCredit records for given cust_id from the database");
			throw new DataAccessException(
					"Error while retriving LineOfCreditrecords for given cust_id from the database",
					e.getCause());
		}
		logger.info("End: Fetching the LineOfCredit for given cust_id from the database in getLineOfCredit() method.");

		return status;
	}

	@Override
	public boolean isLOCExistDAO(Integer schemeId, Long seasonId) {
		logger.info("Start: Checking whether LOC is exist in isLOCExistDAO() method.");
		List<LineOfCredit> lineOfCredit = null;
		boolean status = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT l FROM LineOfCredit l where l.scheme.id = :schemeId and l.season.id = :seasonId");
			query.setParameter("schemeId", schemeId);
			query.setParameter("seasonId", seasonId);
			lineOfCredit = query.getResultList();
			if (lineOfCredit.isEmpty())
				status = true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving  LineOfCredit records for given seasonId and schemeId from the database");
			throw new DataAccessException(
					"Error while retriving LineOfCreditrecords for given seasonId and schemeId from the database",
					e.getCause());
		}
		logger.info("End: Checking whether LOC is exist in isLOCExistDAO() method");
		return status;
	}
	
	public LineOfCredit getLocId(Long locId) {

		logger.info("Start: Fetching the loc from the database in getLocId() method.");
		LineOfCredit loc = new LineOfCredit();
		logger.info(" LOC : " + locId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (locId != null) {
				Query query = em
						.createQuery("SELECT l FROM LineOfCredit l where l.id ="+locId);
				loc = (LineOfCredit) query.getSingleResult();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loc id from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the loc  from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the loc id from the database in getLocId() method.");
		return loc;
	}
	
	public String getLineOfExpiryDate(Long locId){
		String expiryDate="";
		java.sql.Date date;
		try{
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query=
					em.createNativeQuery("select loan_exp_date from kls_schema.line_of_credit where  id ="+locId);
			
			List<java.sql.Date> expirydateList=query.getResultList();
			System.out.println("the expiry date"+expirydateList.get(0));
			
			date=expirydateList.get(0);
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			expiryDate = df.format(date);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return expiryDate;
	}
	
	@Override
	public List<LineOfCredit> getActiveLocListByCustomerId(Long customerId) {

		logger.info("Start: Fetching the loc list from the database in getLocListByCustomerId() method.");
		List<LineOfCredit> list = new ArrayList<LineOfCredit>();
		logger.info(" customerId : " + customerId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (customerId != null) {
				Query query = em
						.createQuery("SELECT l FROM LineOfCredit l where l.customerId = :customerId and l.currentBalance < :current_balance");
				query.setParameter("customerId", customerId);
				query.setParameter("current_balance", AccountingMoney.ZERO);
				list = query.getResultList();
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the loc id list from the "
					+ "database using customerId  Exception thrown.");
			throw new DataAccessException("Could not fetch the loc list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the loc id list from the database in getLocListByCustomerId() method.");
		return list;
	}


}
