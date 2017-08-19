package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Customer;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementEntry;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.loan.ILoanDisbursementEntryDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.transaction.DisbursementStatus;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1623
 * 
 */

public class LoanDisbursementEntryDAO implements ILoanDisbursementEntryDAO {
	private static final Logger logger = Logger.getLogger(LoanDisbursementEntryDAO.class);

	@Override
	public void saveLoanDisbursementEntry(LoanDisbursementEntry master) {

		logger.info("Start: Saving the LoanDisbursementEntry data to the database in saveLoanDisbursementEntry() method.");
		boolean flgTrans = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			em.persist(master);
			if (flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the LoanDisbursementEntry"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the LoanDisbursementEntry data to the database.",
					excp.getCause());
		} /*
		 * finally { EntityManagerUtil.closeSession(); }
		 */
		logger.info("End: Successfully saved the LoanDisbursementEntry to the database in saveLoanDisbursementEntry() method.");
	}

	@Override
	public List<LoanDisbursementEntry> getDisbursementEntriesList( long customerNumber, Integer status, String loanType) {
		// TODO Auto-generated method stub
		List<LoanDisbursementEntry> list = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select l from LoanDisbursementEntry l where l.customerNumber = :customerNumber and l.status = :status and l.loanType = :loanType and l.totalDisburseAmt is null");
			query.setParameter("customerNumber", customerNumber);
			query.setParameter("status", DisbursementStatus.Entry);
			query.setParameter("loanType", loanType);
			list = query.getResultList();
			logger.info("results size : " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the LoanDisbursementEntry ");
			throw new DataAccessException("Error while getting the LoanDisbursementEntry data from the database.",
					excp.getCause());
		}
		return list;
	}

	@Override
	public void updateLoanDisbursementEntry(LoanDisbursementEntry master) {

		logger.info("Start: Updating the LoanDisbursementEntry data to the database in updateLoanDisbursementEntry() method.");
		//LoanDisbursementEntry loanDisbursementEntry = new LoanDisbursementEntry();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = master.getId();
			logger.info("id : " + id);
			em.getTransaction().begin();
			em.merge(master);
			em.getTransaction().commit();
	

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the LoanDisbursementEntry"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the LoanDisbursementEntry  data to the database.",
					excp.getCause());
		}
		/*if (loanDisbursementEntry == null) {
			logger.error("Trying to update the LoanDisbursementEntry  record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the LoanDisbursementEntry  record which does not exist in the database.");
		}*/
		logger.info("End: Successfully updated the LoanDisbursementEntry  data to the database in updateLoanDisbursementEntry() method.");
	}

	@Override
	public List<LoanDisbursementEntry> getDisbursementEntriesListByLocId(long customerNumber,
			Integer status, long locId, String loanType) {
		// TODO Auto-generated method stub
		List<LoanDisbursementEntry> list = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select l from LoanDisbursementEntry l where l.customerNumber = :customerNumber and l.status = :status and l.lineOfCredit.id = :locId and l.loanType = :loanType");
			query.setParameter("customerNumber", customerNumber);
			query.setParameter("status", DisbursementStatus.Entry);
			query.setParameter("locId", locId);
			query.setParameter("loanType", loanType);
			list = query.getResultList();
			logger.info("results size : " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the LoanDisbursementEntry ");
			throw new DataAccessException("Error while getting the LoanDisbursementEntry data from the database.",
					excp.getCause());
		}
		return list;
	}

	/*@Override
	public List<LineOfCredit> getLineOfCredit(){
		List<LineOfCredit> list = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT l FROM LineOfCredit l where l.account.id = :accountId and l.loanType = :loanType order by l.drawalPriority asc,l.sanctionedDate asc");
			
			list = query.getResultList();
			logger.info("results size :== " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the LoanInfo ");
			throw new DataAccessException("Error while getting the LoanInfo data from the database.",
					excp.getCause());
		}
		return list;
	}*/
	
	public List<Map> getLoanInfos(Integer pacsId, Long customerNumber, String loanType) {
		// TODO Auto-generated method stub
		logger.info("pacsId : "+pacsId+" customerNumber : "+customerNumber+" loanType : "+loanType);
		List<Map> list = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select new Map(sum(l.currentBalance) as currentBalance,sum(l.sanctionedAmount) as sanctionedAmount,sum(l.interestReceivable) as interestReceivable,sum(l.chargesReceivable) as chargesReceivable,sum(l.operatingCashLimit) as operatingCashLimit,sum(l.penalInterestReceivable) as penalInterestReceivable) from LineOfCredit l where l.account.accountProperty.pacs.id = :pacsId and l.customerId = :customerNumber and l.loanType = 'C' group by l.customerId");
			query.setParameter("pacsId", pacsId);
			query.setParameter("customerNumber", customerNumber);
			
			
			list = query.getResultList();
			logger.info("results size :== " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the LoanInfo ");
			throw new DataAccessException("Error while getting the LoanInfo data from the database.",
					excp.getCause());
		}
		return list;
	}
	
	
	//@Override
	public List<Map> getLoanInfo(Integer pacsId, Long customerNumber, String loanType, List<LineOfCredit> locList) {
		// TODO Auto-generated method stub
		logger.info("pacsId : "+pacsId+" customerNumber : "+customerNumber+" loanType : "+loanType);
		List<Map> list = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select new Map(sum(l.currentBalance) as currentBalance,sum(l.sanctionedAmount) as sanctionedAmount,sum(l.interestReceivable) as interestReceivable,sum(l.chargesReceivable) as chargesReceivable,sum(l.operatingCashLimit) as operatingCashLimit,sum(l.penalInterestReceivable) as penalInterestReceivable) from LineOfCredit l where l.account.accountProperty.pacs.id = :pacsId and l.customerId = :customerNumber and l.loanType = 'C' and l.id in ("+locList+") group by l.customerId");
			query.setParameter("pacsId", pacsId);
			query.setParameter("customerNumber", customerNumber);
			
			
			list = query.getResultList();
			logger.info("results size :== " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the LoanInfo ");
			throw new DataAccessException("Error while getting the LoanInfo data from the database.",
					excp.getCause());
		}
		return list;
	}

	@Override
	public List<Long> getDisbursementLocIdsEntriesList(Long customerId, int i,
			String loanType) {
		// TODO Auto-generated method stub
		List<Long> list = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select distinct l.lineOfCredit.id from LoanDisbursementEntry l where l.customerNumber = :customerNumber and l.status = :status and l.loanType = 'L'");
			query.setParameter("customerNumber", customerId);
			query.setParameter("status", DisbursementStatus.Entry);
			//query.setParameter("loanType", loanType);
			list = query.getResultList();
			logger.info("results size : " + list.size());
			
			//Customer[] myIntArray = new Customer[3];
			Customer c= new Customer();
			Customer c1 = new Customer();
			ArrayList<Customer> aa= new ArrayList<>();
			aa.add(c);
			aa.add(c1);
			
			
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the LoanDisbursementEntry ");
			throw new DataAccessException("Error while getting the LoanDisbursementEntry data from the database.",
					excp.getCause());
		}
		return list;
	}

	@Override
	public List<Map> getLineOfCredit(Integer pacsId, Long customerNumber, String loanType) {
		List<LineOfCredit> list = new ArrayList<>();
		 List<Map> map = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT l FROM LineOfCredit l where l.customerId = :customerNumber and l.loanType = 'C' order by l.drawalPriority asc,l.sanctionedDate asc");
			query.setParameter("customerNumber", customerNumber);
			
			 list = query.getResultList();
			  map = getLoanInfo(pacsId,customerNumber,loanType,	list);
			logger.info("results size :== " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the LoanInfo ");
			throw new DataAccessException("Error while getting the LoanInfo data from the database.",
					excp.getCause());
		}
		return map;
	}
	
	public LoanDisbursementEntry getLoanDisbursementEntryByVoucherNumber(Date businessDate, String voucherType,Integer pacsId) {
		LoanDisbursementEntry loanDisbursementEntry = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			loanDisbursementEntry = (LoanDisbursementEntry) em.createQuery("select l from LoanDisbursementEntry l where l.passingDate = '"+businessDate+"' and l.voucherNumber = '"+voucherType+"' and l.pacs.id="+pacsId).getSingleResult();
		}catch(NoResultException ne) {
			logger.error("No Result LoanDisbursementEntry");
			loanDisbursementEntry = null;
		}catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the LoanDisbursementEntry ");
			throw new DataAccessException("Error while getting the LoanDisbursementEntry data from the database.",
					excp.getCause());
		}
		return loanDisbursementEntry;
	}

	
	@Override
	public LoanDisbursementEntry getDisbursementEntries(long customerNumber,
			Integer status, long locId, String loanType,String transactionDate) {
		// TODO Auto-generated method stub
		logger.info("date::"+DateUtil.getVSoftDateByString(transactionDate));
		LoanDisbursementEntry list = new LoanDisbursementEntry();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select l from LoanDisbursementEntry l where l.customerNumber = :customerNumber and l.status = :status and l.lineOfCredit.id = :locId and l.loanType = :loanType and disbursementDate=:transactionDate");
			query.setParameter("customerNumber", customerNumber);
			query.setParameter("status", DisbursementStatus.Entry);
			query.setParameter("locId", locId);
			query.setParameter("loanType", loanType);
			query.setParameter("transactionDate", DateUtil.getVSoftDateByString(transactionDate));
			list = (LoanDisbursementEntry)query.getSingleResult();
		//	logger.info("results size : " + list.size());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error while getting the LoanDisbursementEntry ");
			throw new DataAccessException("Error while getting the LoanDisbursementEntry data from the database.",
					excp.getCause());
		}
		return list;
	}

	@Override
	public void updateBulkDisbursementEntry(Integer pacsId,String customerId,String loanType) {

		logger.info("Start: Updating the LoanDisbursementEntry data to the database in updateLoanDisbursementEntry() method.");
		//LoanDisbursementEntry loanDisbursementEntry = new LoanDisbursementEntry();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			String sql="update kls_schema.loan_disbursement_entry set status=3 where pacs_id="+pacsId+" and loan_type='C'  and customer_id="+customerId;
			Query query=em.createNativeQuery(sql);
			int n=query.executeUpdate();
	        logger.info("n value is=="+n);
	        em.getTransaction().commit();

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the LoanDisbursementEntry"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the LoanDisbursementEntry  data to the database.",
					excp.getCause());
		}
		/*if (loanDisbursementEntry == null) {
			logger.error("Trying to update the LoanDisbursementEntry  record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the LoanDisbursementEntry  record which does not exist in the database.");
		}*/
		logger.info("End: Successfully updated the LoanDisbursementEntry  data to the database in updateLoanDisbursementEntry() method.");
	}
	
	@Override
	public String removeBulkDisbursementEntry(String rId){
		logger.info("Start: Inside removeBulkDisbursementEntry()");
		String returnMsg="Successfully deleted record";
		LoanDisbursementEntry master=new LoanDisbursementEntry();
		Long id=Long.parseLong(rId);
		try{
			EntityManager em = EntityManagerUtil.getEntityManager();
			String strQuery="select l from LoanDisbursementEntry l where l.id=:id";
			Query query=em.createQuery(strQuery, LoanDisbursementEntry.class);
			query.setParameter("id", id);
			master=(LoanDisbursementEntry)query.getSingleResult();
			em.getTransaction().begin();
			em.remove(master);
			em.getTransaction().commit();
			
		}catch(Exception excp){
			excp.printStackTrace();
			throw new DataAccessException(
					"Could not remove the transaction of updating the LoanDisbursementEntry  data to the database.",
					excp.getCause());
		}
		return returnMsg;
	}
}
