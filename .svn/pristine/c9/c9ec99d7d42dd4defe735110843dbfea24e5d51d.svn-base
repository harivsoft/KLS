package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementEntry;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanDisbursement;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.loan.ILoanDisbursementDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LoanDisbursementDAO implements ILoanDisbursementDAO {
	private static final Logger logger = Logger.getLogger(LoanDisbursementDAO.class);

	public void saveLoanDisbursement(PacsLoanDisbursement loanDisbursement) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		logger.info("Start: Calling saveLoanDisbursement() method in LoanDisbursementDAO ..!");
		try {
			logger.info("sssssssss-----"+em.getTransaction().isActive());
			em.persist(loanDisbursement);
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of Loan Disbursement in database " + ".Exception thrown.");
			throw new DataAccessException("Unable to commit the transaction of saving the Loan Disbursement.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed saving the Loan Disbursement in saveLoanDisbursement()");
	}

	public String getDisburshmentScheduleAmout(long id, String date) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		String disburshmentAmount = null;
		logger.info("Start: Calling getDisburshmentScheduleAmout() method in LoanDisbursementDAO ..!" + id + "   "
				+ date);
		try {
			String query = "select sum(l.disbursementAmount-l.disbursedAmount) from LoanDisbursementSchedule l where l.loanDisbursmentCompositeId.lineOfCreditId = "
					+ id + " and l.disbursementDate <= '" + DateUtil.getVSoftDateByString(date) + "'";
			logger.info("Query::::::::::: " + query);
			Money amount = em.createQuery(query, Money.class).getSingleResult();
			if (amount != null) {
				disburshmentAmount = amount.getAmount().toString();
			}
			logger.info("Disbrushment Schedule Amount is: " + disburshmentAmount);
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of Loan Disbursement in database " + ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of getDisburshmentScheduleAmout the Loan Disbursement.",
					excp.getCause());
		}
		em.close();
		logger.info("END:Successfully Completed get DisburshmentScheduleAmout in getDisburshmentScheduleAmout()");
		return disburshmentAmount;
	}

	public List<LoanDisbursementSchedule> getDisbursementSchedulesByDate(long id, String date) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<LoanDisbursementSchedule> disbursementList = new ArrayList<LoanDisbursementSchedule>();
		try {
			String query = "select l from LoanDisbursementSchedule l where l.loanDisbursmentCompositeId.lineOfCreditId = "
					+ id
					+ "and l.disbursementDate <= '"
					+ DateUtil.getVSoftDateByString(date)
					+ "' and l.disbursementAmount > l.disbursedAmount order by l.disbursementDate";
			disbursementList = em.createQuery(query, LoanDisbursementSchedule.class).getResultList();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of Loan Disbursement in database " + ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of getDisburshmentScheduleAmout the Loan Disbursement.",
					excp.getCause());
		}
		return disbursementList;
	}

	public boolean isFirstDisbursement(long id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		Boolean isFirstDisbursement = null;
		try {
			String query = "select sum(l.disbursedAmount) from LoanDisbursementSchedule l where l.loanDisbursmentCompositeId.lineOfCreditId = "
					+ id;
			Money amount = em.createQuery(query, Money.class).getSingleResult();
			if (amount != null && amount.getAmount().doubleValue() == 0d) {
				isFirstDisbursement = true;
			} else {
				isFirstDisbursement = false;
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of Loan Disbursement in database " + ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of getDisburshmentScheduleAmout the Loan Disbursement.",
					excp.getCause());
		}
		return isFirstDisbursement;
	}

	public boolean isPacsSuvikasVoucherExist(String suvikasVoucherId, String suvikasVoucherDate) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		Boolean isVoucherExist = false;
		try {
			String query = "select l from PacsLoanDisbursement l where l.pacsSuvikasVoucherNumber = '"
					+ suvikasVoucherId + "' and l.pacsSuvikasVoucherDate = '"
					+ DateUtil.getVSoftDateByString(suvikasVoucherDate) + "'";

			List<PacsLoanDisbursement> loanDisbursementList = em.createQuery(query, PacsLoanDisbursement.class)
					.getResultList();
			if (loanDisbursementList != null) {
				if (loanDisbursementList.size() > 0) {
					isVoucherExist = true;
				}
			}
		} catch (NoResultException ne) {
			logger.error("Pacs Suvikas Voucher Number does not exist.");
			isVoucherExist = false;
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of Loan Disbursement in database " + ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of isPacsSuvikasVoucerExist the Loan Disbursement.",
					excp.getCause());
		}
		return isVoucherExist;
	}

	@Override
	public Money getDisbursedAmountByLocId(Long locId) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		Money disbursedAmount = Money.ZERO;
		try {

			if (locId != null) {
				Query query = em
						.createQuery("select sum(l.disbursedAmount) from PacsLoanDisbursement l where l.lineOfCredit.id = :locId");
				query.setParameter("locId", locId);
				disbursedAmount = (Money) query.getSingleResult();
			}

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of Loan Disbursement in database " + ".Exception thrown.");
			throw new DataAccessException("Unable to commit the transaction of getDisburshment the Loan Disbursement.",
					excp.getCause());
		}
		return disbursedAmount;
	}

	@Override
	public List<Object[]> getloanDisburseEntryList(String pacsId,String loanType){
		logger.info("inside getloanDisburseEntryList method::"+pacsId+","+loanType);
		EntityManager em=EntityManagerUtil.getEntityManager();
		List<Object[]> disbursementPassingMap=new ArrayList<Object[]>();
		try{
			Integer pacId=Integer.parseInt(pacsId);
			String strQuery="select l.disbursement_date,sum(l.disbursement_amount),l.instrument_no from kls_schema.loan_disbursement_entry l where l.pacs_id="+pacsId+" and l.loan_type='"+loanType+"' and l.status=1 group by l.instrument_no,l.disbursement_date order by l.disbursement_date";
			Query query=em.createNativeQuery(strQuery);
			disbursementPassingMap=query.getResultList();
			logger.info("inside data access::"+disbursementPassingMap.size());
		}catch(Exception excp){
			excp.printStackTrace();
			logger.error("Unable to rerieve values from database");
			throw new DataAccessException("Unable to commit the transaction of getDisburshment the Loan Disbursement.",
					excp.getCause());
		}
		return disbursementPassingMap;
	}
	@Override
	public List<Object[]> getBulkDisbursementDetails(String loanType,String disbursementDate,String instrumentNo,String pacsId){
		logger.info("Start : Calling getBulkDisbursementDetails method");
		EntityManager em=EntityManagerUtil.getEntityManager();
		List<Object[]> disbursementDetails=new ArrayList<Object[]>();
		Integer pacId=Integer.parseInt(pacsId);
		try{
			StringBuilder sqlQuery=new StringBuilder();
			sqlQuery.append("select sum(disbursement_amount),customer_id,id from kls_schema.loan_disbursement_entry where status=1 and");
			if(disbursementDate!=null)
			sqlQuery.append(" disbursement_date='"+disbursementDate+"' and ");
			if(instrumentNo!=null && !"".equalsIgnoreCase(instrumentNo))
				sqlQuery.append("instrument_no='"+instrumentNo+"' and ");
			sqlQuery.append(" loan_type='"+loanType+"' and total_disburse_amt is not null and pacs_id="+pacsId+" group by customer_id,disbursement_date,id");
			Query qry=em.createNativeQuery(sqlQuery.toString());
			disbursementDetails=qry.getResultList();
		}catch(Exception ex){
			ex.printStackTrace();
			logger.info("Unable to retrieve values from database");
			throw new DataAccessException("Unable to retrieve valeus for Database.",ex.getCause());
		}
		return disbursementDetails;
	}
}
