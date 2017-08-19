package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.ICollectionReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

/**
 * This class is to get the data for Collection report
 * 
 * @author a1605
 * 
 */
public class CollectionReportDAO implements ICollectionReportDAO {
	private static final Logger logger = Logger.getLogger(CollectionReportDAO.class);

	@Override
	public List<Object[]> getCollectionReport(Integer pacId, Integer schemeId, Long seasonId,
			Date fromDate, Date toDate) {
		List<Object[]> collectionList = new ArrayList<Object[]>();
		logger.info("Start: Inside getCollectionReport()");
		try {

			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder collectionReportQuery = new StringBuilder();

			collectionReportQuery
					.append("CREATE OR REPLACE VIEW kls_schema.temp_collection_report1 AS (select locid,schemeid,cropid,memId,sanctionedAmt,sDate,COALESCE(sum(credit),0) credit,COALESCE(sum(debit),0) debit,accNo from ( select l.id as locId,l.scheme_id as schemeId,l.crop_id as cropId,l.customer_id as memId,l.sanctioned_date as sDate,"
							+ "l.sanctioned_limit as sanctionedAmt"
							+ ",case when (th.crdr=1 and th.tran_code=13) then sum(th.trans_amt) end credit,"
							+ "case when th.crdr=-1 then sum(th.trans_amt) end debit,"
							+ "a.account_number as accNo,l.current_balance as openingBal"
							+ " from kls_schema.line_of_credit l,"
							+ "kls_schema.transaction_history th, "
							+ "kls_schema.account a"
							+ " where");
			if (pacId != 0) {
				collectionReportQuery.append(" th.pacs_id=" + pacId + " and");
			}
			collectionReportQuery.append(" th.line_of_credit_id=l.id and th.business_date>='"
					+ fromDate + "' and th.business_date<='" + toDate + "'"
					+ " and l.loan_type='C' and l.account_id=a.id" + " and l.season_id=" + seasonId
					+ "");
			if (schemeId != 0) {
				collectionReportQuery.append(" and l.scheme_id=" + schemeId);
			}
			collectionReportQuery
					.append(" group by l.id,l.customer_id,l.crop_id,l.scheme_id,th.crdr,l.sanctioned_date,a.account_number,th.tran_code union select l.id as locId,l.scheme_id as schemeId,l.crop_id as cropId,l.customer_id as memId,l.sanctioned_date as sDate,"
							+ "l.sanctioned_limit as sanctionedAmt"
							+ ",case when (ct.crdr=1 and ct.tran_code=13) then sum(ct.trans_amt) end credit,"
							+ "case when ct.crdr=-1 then sum(ct.trans_amt) end debit,"
							+ "a.account_number as accNo,l.current_balance as openingBal"
							+ " from kls_schema.line_of_credit l,"
							+ "kls_schema.current_transaction ct, "
							+ "kls_schema.account a"
							+ " where");
			if (pacId != 0) {
				collectionReportQuery.append(" ct.pacs_id=" + pacId + " and");
			}
			collectionReportQuery
					.append(" ct.line_of_credit_id=l.id and ct.business_date>='"
							+ fromDate
							+ "' and ct.business_date<='"
							+ toDate
							+ "'"
							+ " and l.loan_type='C' and l.account_id=a.id"
							+ " and l.season_id="
							+ seasonId
							+ " group by l.id,l.crop_id,l.scheme_id,ct.crdr,l.sanctioned_date,a.account_number,ct.tran_code)"
							+ " group by locid,schemeid,cropid,sanctionedAmt,sDate,accNo,memId)");
			em.getTransaction().begin();
			em.createNativeQuery(collectionReportQuery.toString()).executeUpdate();
			em.getTransaction().commit();
			logger.info("Created temp_collection_report view");
			collectionList = em
					.createNativeQuery(
							"select schemeid,locid,cropid,sdate,sanctionedAmt,accno,memid,debit,credit from kls_schema.temp_collection_report1 order by schemeid asc")
					.getResultList();
		} catch (Exception exception) {
			logger.info("End: Inside getCollectionReport()");
			throw new KlsReportRuntimeException("Can not print collection report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside getCollectionReport()");

		return collectionList;
	}
}
