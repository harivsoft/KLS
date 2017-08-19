package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IShareAccountBalancingReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class ShareAccountBalancingReportDAO implements IShareAccountBalancingReportDAO {
	private static final Logger logger = Logger.getLogger(ShareAccountBalancingReportDAO.class);

	@Override
	public List<Object[]> getShareAccountBalancingReport(Integer pacsId, Integer productId, String memberNo,
			Date asOnDate) {
		List<Object[]> shareAccList = new ArrayList<Object[]>();
		logger.info("Start:Inside getShareAccountLedgerReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select share_account_id,( (select coalesce(sum(no_of_shares),0)  from membership.share_transaction where share_account_id=a.share_account_id and trans_code_id in (1,2) and trans_date <= '"
							+ asOnDate
							+ "') - (select coalesce(sum(no_of_shares),0) from membership.share_transaction where share_account_id=a.share_account_id and trans_code_id in (3,4)  and trans_date <= '"
							+ asOnDate
							+ "'))noOfShares, acc.share_product_id from membership.share_transaction a, membership.share_account sa,membership.account acc where a.trans_date <= '"
							+ asOnDate
							+ "' and acc.account_id=a.share_account_id and a.share_account_id=acc.account_id and sa.pacs_id="
							+ pacsId + "");
			if (productId != 0) {
				stringBuilder.append(" and acc.share_product_id=" + productId + " ");
			}
			if (!memberNo.equals("0"))
				stringBuilder.append(" and acc.party_id=" + memberNo + " ");
			stringBuilder
					.append(" and sa.account_id = a.share_account_id group by  acc.share_product_id,share_account_id order by acc.share_product_id");

			shareAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("shareAccList---" + shareAccList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getShareAccountLedgerReport method");
			throw new KlsReportRuntimeException("Can not print ShareAccountLedger report:", exception.getCause());
		}
		
		/*finally
		{
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End:Inside getShareAccountLedgerReport method");

		return shareAccList;
	}

	@Override
	public Integer getCancelledSharesBasedOnCustId(String partyId, Date asOnDate) {
		List<BigDecimal> shareAccList = new ArrayList<BigDecimal>();
		BigDecimal val = BigDecimal.ZERO;
		logger.info("Start:Inside getShareAccountLedgerReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select distinct sa.no_of_shares noOfShares from membership.account a,membership.person p,membership.share_product sp,membership.share_account sa,membership.share_transaction st where a.party_id = p.party_id and sp.id = a.share_product_id and st.share_account_id = a.account_id and (st.trans_code_id = 3 or st.trans_code_id = 4) and sa.account_id = a.account_id and sa.account_id = st.share_account_id and st.trans_date<='"
							+ asOnDate + "'");

			stringBuilder.append(" and p.party_id = " + partyId);

			shareAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("shareAccList---" + shareAccList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getShareAccountLedgerReport method");
			throw new KlsReportRuntimeException("Can not print ShareAccountLedger report:", exception.getCause());
		}
		logger.info("End:Inside getShareAccountLedgerReport method");
		if (shareAccList.size() > 0) {
			val = (BigDecimal) (shareAccList.get(0));
			return val.intValue();
		} else
			return 0;
	}

	@Override
	public Object[] getShareAccountDetails(BigDecimal shareAccountId, Integer pacsId, Integer productId, String memberNo) {
		Object[] objects = null;
		List<Object[]> shareAccList = new ArrayList<Object[]>();
		logger.info("Start:Inside getShareAccountLedgerReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select p.first_name,p.member_number,a.account_no,sp.product_name,sp.id,sp.face_value from membership.account a,membership.share_account sa,membership.person p,membership.share_product sp where p.party_id = a.party_id and a.account_id = sa.account_id and sp.id = a.share_product_id and sa.account_id = "
							+ shareAccountId + "");
			if (!memberNo.equals("0"))
				stringBuilder.append(" and a.party_id=" + memberNo + " ");

			if (productId != 0) {
				stringBuilder.append(" and a.share_product_id=" + productId + " ");
			}

			if (pacsId != null)
				stringBuilder.append(" and p.pacs_id=" + pacsId + " ");
			shareAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getShareAccountLedgerReport method");
			throw new KlsReportRuntimeException("Can not print ShareAccountLedger report:", exception.getCause());
		}
		/*finally
		{
			EntityManagerUtil.closeSession();
		}*/
		
		logger.info("End:Inside getShareAccountLedgerReport method");
		if (shareAccList.size() > 0)
			return shareAccList.get(0);
		else
			return null;
	}

}
