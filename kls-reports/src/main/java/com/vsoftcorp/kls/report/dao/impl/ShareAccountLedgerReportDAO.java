package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IShareAccountLedgerReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;


public class ShareAccountLedgerReportDAO implements IShareAccountLedgerReportDAO {
	private static final Logger logger = Logger.getLogger(ShareAccountLedgerReportDAO.class);

	@Override
	public List<Object[]> getShareAccountLedgerReport(Integer pacId, Integer productId, String memberNo, Date fromDate,
			Date toDate) {
		List<Object[]> shareAccList = new ArrayList<Object[]>();
		logger.info("Start:Inside getShareAccountLedgerReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select p.member_number,p.pacs_id,sp.id,sp.product_name,a.account_no,st.trans_date,st.certificate_number,st.distinctive_no_from,st.distinctive_no_to,t.trans_type,st.remarks,st.transaction_id,st.trans_code_id,p.first_name"
							+ " from membership.share_account sa,membership.account a,membership.share_transaction st,membership.trans_code t,membership.person p,membership.share_product sp"
							+ " where sa.account_id = a.account_id and st.share_account_id = sa.account_id and t.id = st.trans_code_id and a.party_id = p.party_id and sp.id = a.share_product_id and st.trans_date between '"
							+ fromDate + "' and '" + toDate + "' ");
			if (!memberNo.equals("0"))
				stringBuilder.append("and a.party_id=" + memberNo + " ");

			if (productId != 0) {
				stringBuilder.append(" and a.share_product_id=" + productId + " ");
			}

			if (pacId != null)
				stringBuilder.append(" and p.pacs_id=" + pacId + " ");
			stringBuilder.append(" order by p.member_number,st.trans_code_id");
			shareAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("shareAccList---" + shareAccList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getShareAccountLedgerReport method");
			throw new KlsReportRuntimeException("Can not print ShareAccountLedger report:", exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside getShareAccountLedgerReport method");

		return shareAccList;
	}

	@Override
	public Object[] getAllottedNoOfSharesAndValueByTransactionId(BigDecimal shareTransactionId, BigDecimal transCode1,
			BigDecimal transCode2) {
		List<Object[]> object = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			String query = "select distinct st.no_of_shares,st.no_of_shares*st.face_value allowted from membership.share_transaction st,membership.share_account sa where (st.trans_code_id = "
					+ transCode1
					+ " or st.trans_code_id = "
					+ transCode2
					+ ") and st.transaction_id = "
					+ shareTransactionId + "";

			object = em.createNativeQuery(query).getResultList();

		} catch (Exception e) {
			throw new KlsReportRuntimeException("entity not found:", e.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		if (object.size() > 0)
			return object.get(0);
		else
			return null;
	}

	@Override
	public Object[] getRedeemedNoOfSharesAndValueByTransactionId(BigDecimal shareTransactionId, BigDecimal transCode,BigDecimal transCode1) {
		List<Object[]> object = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			String query = "select distinct st.no_of_shares,st.no_of_shares*st.face_value allowted from membership.share_transaction st,membership.share_account sa where (st.trans_code_id = "
					+ transCode
					+ " or st.trans_code_id = "
					+ transCode1
					+ ") and st.transaction_id = "
					+ shareTransactionId + "";

			object = em.createNativeQuery(query).getResultList();

		} catch (Exception e) {
			throw new KlsReportRuntimeException("entity not found:", e.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		if (object.size() > 0)
			return object.get(0);
		else
			return null;
	}

	@Override
	public Object[] getOpeningBal(Date fromDate, String customerId) {
		List<Object[]> object = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			String query = "select sum(nos) nos, sum(allowted) altd from (select  st.no_of_shares nos ,st.no_of_shares*st.face_value allowted from membership.share_transaction st,membership.person p, membership.account ma  where (st.trans_code_id = 1 or st.trans_code_id = 2) and st.trans_date < '"+fromDate+"' and ma.account_id = st.share_account_id and ma.party_id = p.party_id and  p.member_number = '"+customerId+"' )";

			object = em.createNativeQuery(query).getResultList();

		} catch (Exception e) {
			throw new KlsReportRuntimeException("entity not found:", e.getCause());
		}
		if (object.size() > 0)
			return object.get(0);
		else
			return null;
	}

	@Override
	public Object[] getCancelBal(Date fromDate, String customerId) {
		List<Object[]> object = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			String query = "select sum(nos) nos, sum(allowted) altd from (select  st.no_of_shares nos ,st.no_of_shares*st.face_value allowted from membership.share_transaction st,membership.person p, membership.account ma  where (st.trans_code_id = 3 or st.trans_code_id = 4) and st.trans_date < '"+fromDate+"' and ma.account_id = st.share_account_id and ma.party_id = p.party_id and  p.member_number = '"+customerId+"' )";

			object = em.createNativeQuery(query).getResultList();

		} catch (Exception e) {
			throw new KlsReportRuntimeException("entity not found:", e.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		if (object.size() > 0)
			return object.get(0);
		else
			return null;
	}

}
