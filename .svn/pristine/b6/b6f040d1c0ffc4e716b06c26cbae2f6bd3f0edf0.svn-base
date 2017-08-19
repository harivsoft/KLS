package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IMTLTLoanLedgerReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class MTLTLoanLedgerReportDAO implements IMTLTLoanLedgerReportDAO {

	private static final Logger logger = Logger.getLogger(MTLTLoanLedgerReportDAO.class);

	@Override
	public List<Map> getAccountStatementReport(Long locNo, Date fromDate, Date toDate, Long customerId,
			Integer pacsId) {
		
		logger.info("Start: Indise of the method getAccountStatementReport()");
		List<Map> accountStatementList = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select distinct new Map(");
			query.append("ct.lineOfCredit.id as lineOfCreditId,");
			query.append("ct.businessDate as businessDate,");
			query.append("ct.crDr as crDr,");
			query.append("ct.transactionAmount as transactionAmount,");
			query.append("ct.openingBalance as openingBalance,");
			query.append("ct.remarks as remarks,");
			query.append("ct.transactionCode as transactionCode)");
			query.append("from ");
			query.append("CurrentTransaction ct");
			query.append(" where ");
			query.append("ct.businessDate>=:fromDate");
			query.append(" and ct.businessDate<=:toDate");
		  	query.append(" and ct.pacs.id=:pacsId");
		    query.append(" and ct.lineOfCredit.id=:locNo"); 
			query.append(" order by ");
			query.append("ct.businessDate,ct.id");

			Query qry = em.createQuery(query.toString());
			qry.setParameter("locNo", locNo);
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			qry.setParameter("pacsId", pacsId);
			accountStatementList = qry.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error while retriving records for Account statement");
			throw new KlsReportRuntimeException("Error while retriving records for Account statement:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Indise of the method getAccountStatementReport() ");
		return accountStatementList;
	}

	@Override
	public List<Map> getAccountStatementReportFromTransactionHistory(Long locNo, Date fromDate, Date toDate,
			Long customerId, Integer pacsId) {

		logger.info("Start: Indise of the method getAccountStatementReportFromTransactionHistory()");
		List<Map> accountStatementList = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select distinct new Map(");
			query.append("th.lineOfCredit.id as lineOfCreditId,");
			query.append("th.businessDate as businessDate,");
			query.append("th.crDr as crDr,");
			query.append("th.transactionAmount as transactionAmount,");
			query.append("th.openingBalance as openingBalance,");
			query.append("th.remarks as remarks,");
			query.append("th.transactionCode as transactionCode)");
			query.append("from ");
			query.append("TransactionHistory th");
			query.append(" where ");
			query.append("th.businessDate>=:fromDate");
			query.append(" and th.businessDate<=:toDate");
			query.append(" and th.lineOfCredit.id=:locNo"); 
			query.append(" and th.pacs.id=:pacsId");
			query.append(" order by ");
			query.append("th.businessDate,th.id");
			Query qry = em.createQuery(query.toString());
			qry.setParameter("locNo", locNo);
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			qry.setParameter("pacsId", pacsId);

			accountStatementList = qry.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error while retriving records for Account statement");
			throw new KlsReportRuntimeException("Error while retriving records for Account statement:",
					exception.getCause());
		}
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Indise of the method getAccountStatementReportFromTransactionHistory() ");
		return accountStatementList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigInteger> getLineOfCreditListByCustId(Long customerId) {
		
		List<BigInteger> locIdList =null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		String loanType="L";
		
		try{
			locIdList = em.createNativeQuery("select id from kls_schema.line_of_credit where customer_id="+customerId+" and loan_type='"+loanType+"'").getResultList();
			logger.info("locID List Size "+locIdList.size());
		}
		catch(Exception e)
		{
			logger.info("Error accured while getting locIds ..");
			
			e.printStackTrace();
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return locIdList;
	}

	
}
