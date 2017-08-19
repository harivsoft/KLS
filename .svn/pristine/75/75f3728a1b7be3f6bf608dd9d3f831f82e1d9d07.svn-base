package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IDrawalReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

/**
 * This class is used to get the data for Drawl Register report
 * 
 * @author a1605
 * 
 */
public class DrawalReportDAO implements IDrawalReportDAO {

	private static final Logger logger = Logger.getLogger(DrawalReportDAO.class);

	@Override
	public List<Object[]> getDrawalReport(Integer pacId, Integer schemeId, Long seasonId,
			Date fromDate, Date toDate) {
		List<Object[]> drawalList = new ArrayList<Object[]>();

		logger.info("Start:Inside getDrawalReport method");
		try {

			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder collectionReportQuery = new StringBuilder();

			collectionReportQuery
					.append("CREATE OR REPLACE VIEW kls_schema.temp_drawal AS (select locid,schemeid,cropid,memId,sanctionedAmt,sDate,COALESCE(sum(debit),0) debit,accNo from ( select l.id as locId,l.scheme_id as schemeId,l.crop_id as cropId,l.customer_id as memId,l.sanctioned_date as sDate,"
							+ "l.sanctioned_limit as sanctionedAmt,"
							+ "sum(th.trans_amt) as debit,"
							+ "a.account_number as accNo,l.current_balance as openingBal"
							+ " from kls_schema.line_of_credit l,"
							+ "kls_schema.transaction_history th,"
							+ "kls_schema.account a"
							+ " where ");
			if (pacId != 0) {
				collectionReportQuery.append("th.pacs_id=" + pacId + " and ");
			}
			collectionReportQuery.append("th.line_of_credit_id=l.id and th.business_date>='"
					+ fromDate + "' and th.business_date<='" + toDate + "'"
					+ " and th.crdr=-1 and l.loan_type='C' and l.account_id=a.id"
					+ " and l.season_id=" + seasonId + "");
			if (schemeId != 0) {
				collectionReportQuery.append(" and l.scheme_id=" + schemeId);
			}
			collectionReportQuery
					.append(" group by l.id,l.customer_id,l.crop_id,l.scheme_id,th.crdr,l.sanctioned_date,a.account_number union select l.id as locId,l.scheme_id as schemeId,l.crop_id as cropId,l.customer_id as memId,l.sanctioned_date as sDate,"
							+ "l.sanctioned_limit as sanctionedAmt,"
							+ "sum(ct.trans_amt) as debit,"
							+ "a.account_number as accNo,l.current_balance as openingBal"
							+ " from kls_schema.line_of_credit l,"
							+ "kls_schema.current_transaction ct,"
							+ "kls_schema.account a"
							+ " where ");
			if (pacId != 0) {
				collectionReportQuery.append("ct.pacs_id=" + pacId + " and ");
			}
			collectionReportQuery
					.append("ct.line_of_credit_id=l.id and ct.business_date>='"
							+ fromDate
							+ "' and ct.business_date<='"
							+ toDate
							+ "'"
							+ " and ct.crdr=-1 and l.account_id=a.id"
							+ " and l.loan_type='C' and l.season_id="
							+ seasonId
							+ " group by l.id,l.customer_id,l.crop_id,l.scheme_id,ct.crdr,l.sanctioned_date,a.account_number)"
							+ " group by locid,schemeid,cropid,sanctionedAmt,sDate,accNo,memId)");
			em.getTransaction().begin();
			em.createNativeQuery(collectionReportQuery.toString()).executeUpdate();
			em.getTransaction().commit();
			logger.info("Created temp_drawal_report view");
			drawalList = em
					.createNativeQuery(
							"select schemeid,locid,cropid,sdate,sanctionedAmt,accno,memid,debit from kls_schema.temp_drawal order by schemeid asc")
					.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Can not print Drawal report:",
					exception.getCause());
		}
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside getDrawalReport method");

		return drawalList;
	}

	/*
	 * @Override public AccountingMoney getAccountBalance(long locId, Date
	 * toDate) { AccountingMoney accountBalance = AccountingMoney.ZERO; try {
	 * EntityManager em = EntityManagerUtil.getEntityManager(); Query query = em
	 * .createQuery(
	 * "select ct1.openingBalance from CurrentTransaction ct1 where id=(select min(ct2.id) from CurrentTransaction ct2 where ct2.businessDate<=:businessDate)"
	 * ); query.setParameter("businessDate", toDate); accountBalance =
	 * (AccountingMoney) query.getSingleResult(); } catch (NoResultException
	 * noExcp) { return getAccountBalanceFromTransactionHistory(locId, toDate);
	 * } catch (Exception exception) { exception.printStackTrace(); throw new
	 * KlsReportRuntimeException( "Error while getting Account Balance:",
	 * exception.getCause()); } return accountBalance; }
	 * 
	 * public static AccountingMoney getAccountBalanceFromTransactionHistory(
	 * long locId, Date toDate) { AccountingMoney accountBalance =
	 * AccountingMoney.ZERO; try { EntityManager em =
	 * EntityManagerUtil.getEntityManager(); Query query = em .createQuery(
	 * "select th1.openingBalance from TransactionHistory th1 where id=(select min(th2.id) from TransactionHistory th2 where th2.businessDate<=:businessDate)"
	 * ); query.setParameter("businessDate", toDate); accountBalance =
	 * (AccountingMoney) query.getSingleResult(); } catch (NoResultException
	 * noExcp) { return accountBalance; } catch (Exception exception) {
	 * exception.printStackTrace(); throw new KlsReportRuntimeException(
	 * "Error while getting Account Balance from transaction history:",
	 * exception.getCause()); } return accountBalance; }
	 */

	public BigDecimal getAccountBalance(long locId, Date toDate) {
		logger.info("Start:Inside getAccountBalance method toDate:" + toDate.toString());
		BigDecimal accountBalance = BigDecimal.ZERO;
		try {
			// SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
			// String toDateString = sm.format(toDate.getJavaDate());
			String toDateString = toDate.toString();

			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createNativeQuery("select accountBal from (select 1 as tid, (opening_balance+trans_amt)*crdr as accountBal,business_date from kls_schema.current_transaction where id=(select max(id) from kls_schema.current_transaction where (line_of_credit_id=:locId and business_date <='"
							+ toDateString
							+ "')) union select 1 as tid,(opening_balance+trans_amt)*crdr as accountBal,business_date from kls_schema.transaction_history where id=(select max(id) from kls_schema.transaction_history where (line_of_credit_id=:locId and business_date <='"
							+ toDateString
							+ "'))) where business_date = ( select max(business_date) from ( select business_date from (select 1 as tid,(opening_balance+trans_amt)*crdr,business_date from kls_schema.current_transaction where id=(select max(id) from kls_schema.current_transaction where (line_of_credit_id=:locId and business_date <='"
							+ toDateString
							+ "')) union select 1 as tid,(opening_balance+trans_amt)*crdr as accountBal,business_date from kls_schema.transaction_history where id=(select max(id) from kls_schema.transaction_history where (line_of_credit_id=:locId and business_date <='"
							+ toDateString + "')))));");
			query.setParameter("locId", locId);
			accountBalance = (BigDecimal) query.getSingleResult();
		} catch (NoResultException noExcp) {
			return accountBalance;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting Account Balance:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside getAccountBalance method toDate:" + toDate);
		return accountBalance;
	}

}
