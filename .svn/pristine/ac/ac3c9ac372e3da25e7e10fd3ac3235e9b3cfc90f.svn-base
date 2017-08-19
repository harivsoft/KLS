package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IAccountStatementReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class AccountStatementReportDAO implements IAccountStatementReportDAO {

	private static final Logger logger = Logger
			.getLogger(AccountStatementReportDAO.class);

	@Override
	public List<Map> getAccountStatementReport(String accNo, Date fromDate,
			Date toDate) {
		logger.info("kls Acc no:" +accNo);

		logger.info("Start: Indise of the method getAccountStatementReport()");
		List<Map> accountStatementList = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select distinct new Map(lc.scheme.id as schemeId,");
			query.append("ct.lineOfCredit.id as lineOfCreditId,");
			query.append("crp.name as cropName,");
			query.append("sn.name as seasonName,");
			query.append("ct.businessDate as businessDate,");
			query.append("ct.crDr as crDr,");
			query.append("ct.transactionAmount as transactionAmount,");
			query.append("ct.openingBalance as openingBalance,");
			query.append("ct.remarks as remarks,");
			query.append("ct.transactionCode as transactionCode)");
			query.append(" from ");
			query.append("LineOfCredit lc,");
			query.append("CurrentTransaction ct,");
			query.append("Crop crp,");
			query.append("Season sn");
			query.append(" where ");
			query.append("ct.accountNumber =:accNo");
			query.append(" and ct.businessDate>=:fromDate");
			query.append(" and ct.businessDate<=:toDate");
			query.append(" and ct.lineOfCredit.id=lc.id");
			query.append(" and lc.loanType=:loanType");
			query.append(" and lc.crop.id = crp.id");
			query.append(" and lc.season.id = sn.id");
				query.append(" order by ");
			query.append("ct.lineOfCredit.id,ct.id");

			Query qry = em.createQuery(query.toString());
			qry.setParameter("accNo", accNo);
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			qry.setParameter("loanType", "C");

			accountStatementList = qry.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error while retriving records for Account statement");
			throw new KlsReportRuntimeException(
					"Error while retriving records for Account statement:",
					exception.getCause());
		}
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Indise of the method getAccountStatementReport()  accountStatementList::"
				+ accountStatementList);
		return accountStatementList;
	}

	@Override
	public List<Map> getAccountStatementReportFromTransactionHistory(
			String accNo, Date fromDate, Date toDate) {
		logger.info("kls Acc no:" +accNo);
		logger.info("Start: Indise of the method getAccountStatementReportFromTransactionHistory()");
		List<Map> accountStatementList = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder();
			query.append("select distinct new Map(lc.scheme.id as schemeId,");
			query.append("th.lineOfCredit.id as lineOfCreditId,");
			query.append("crp.name as cropName,");
			query.append("sn.name as seasonName,");
			query.append("th.businessDate as businessDate,");
			query.append("th.crDr as crDr,");
			query.append("th.transactionAmount as transactionAmount,");
			query.append("th.openingBalance as openingBalance,");
			query.append("th.remarks as remarks,");
			query.append("th.transactionCode as transactionCode)");
			query.append(" from ");
			query.append("LineOfCredit lc,");
			query.append("TransactionHistory th,");
			query.append("Crop crp,");
			query.append("Season sn");
			query.append(" where ");
			query.append("th.accountNumber =:accNo");
			query.append(" and th.businessDate>=:fromDate");
			query.append(" and th.businessDate<=:toDate");
			query.append(" and th.lineOfCredit.id=lc.id");
			query.append(" and lc.loanType=:loanType");
			query.append(" and lc.crop.id = crp.id");
			query.append(" and lc.season.id = sn.id");
			query.append(" order by ");
			query.append("th.lineOfCredit.id,th.id");
			Query qry = em.createQuery(query.toString());
			qry.setParameter("accNo", accNo);
			qry.setParameter("fromDate", fromDate);
			qry.setParameter("toDate", toDate);
			qry.setParameter("loanType", "C");

			accountStatementList = qry.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error while retriving records for Account statement");
			throw new KlsReportRuntimeException(
					"Error while retriving records for Account statement:",
					exception.getCause());
		}
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Indise of the method getAccountStatementReportFromTransactionHistory() accountStatementList::"
				+ accountStatementList);
		return accountStatementList;
	}

	// Commenting below code as part of story SUB-843
	
//	public Customer getCustomerIdByAcountNumber(String accNoString) {
//		logger.info("Start :inside getCustomerIdByAcountNumber() ");
//		Customer customer =null;
//		try {
//			EntityManager em = EntityManagerUtil.getEntityManager();
//			Query query = em
//					.createQuery("select a.accountProperty.customer from Account a where a.accountNumber=:accNo");
//			customer = (Customer) query.setParameter("accNo", accNoString)
//					.getSingleResult();
//		} catch (NoResultException noExcp) {
//			throw new AccountDoesntExistsException(
//					"Account number does not exist:");
//		} catch (Exception e) {
//			logger.error("Error :inside getCustomerIdByAcountNumber()");
//		}
//		logger.info("End :inside getCustomerIdByAcountNumber()");
//		return customer;
//	}
}
