package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.address.EMailAddress;
import com.vsoftcorp.kls.report.dao.IAccountInfoReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class AccountInfoReportDAO implements IAccountInfoReportDAO {
	private static final Logger logger = Logger.getLogger(ShareAccountBalancingReportDAO.class);

	@Override
	public List<Object[]> getAccountInfoReport(String businessType, Integer productId, String accountStatus,
			Date fromDate, Date toDate, Integer pacsId) {
		List<Object[]> accList = new ArrayList<Object[]>();
		logger.info("Start:Inside getAccountInfoReport method");
		logger.info("pacsId : " + pacsId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			if (businessType.equalsIgnoreCase("S")) {

				stringBuilder
						.append("select opening_date, closed_date, account_no, first_name, member_number,msp.product_name,msp.id from membership.account ma, membership.account_type mat, membership.person mp ,membership.share_product msp where msp.id = ma.share_product_id and  ma.account_type_id=  mat.id and mp.party_id = ma.party_id and pacs_id ="
								+ pacsId + " and business_type = 'Share'");
				if (productId != 0) {
					stringBuilder.append(" and msp.id = " + productId + " ");
				}

				if (accountStatus.equalsIgnoreCase("C"))
					stringBuilder.append(" and ma.status_id = 2 and (closed_date between '" + fromDate + "'  and '"
							+ toDate + "') ");

				if (accountStatus.equalsIgnoreCase("O"))
					stringBuilder.append(" and ma.status_id = 1 and (opening_date between '" + fromDate + "'  and '"
							+ toDate + "') ");

				if (accountStatus.equalsIgnoreCase("A"))
					stringBuilder.append(" and ((closed_date between '" + fromDate + "'  and '" + toDate
							+ "') or (opening_date between '" + fromDate + "'  and '" + toDate + "') )");
				stringBuilder.append(" order by msp.id,opening_date,account_no");

			} else {
				stringBuilder
						.append("select ka.open_date,loc.close_date,ka.account_number||'-'||loc.id accountNumber,mp.first_name,mp.member_number,kp.name,kp.id,mp.party_id from kls_schema.account ka, kls_schema.loan_account_property kap,kls_schema.product kp ,membership.person mp,kls_schema.line_of_credit loc where ka.id = kap.account_id and kp.id=kap.product_id and mp.party_id = loc.customer_id and kap.account_id = loc.account_id and ka.id = loc.account_id and kap.account_property_type = 'L' and kap.product_id=loc.product_id and loc.loan_account_property_id = kap.id and mp.pacs_id = "
								+ pacsId + "");
				if (productId != 0) {
					stringBuilder.append(" and kp.id = " + productId + " ");
				}

				if (accountStatus.equalsIgnoreCase("C"))
					stringBuilder.append(" and loc.status = 0 and (loc.close_date between '" + fromDate + "'  and '"
							+ toDate + "') ");

				if (accountStatus.equalsIgnoreCase("O"))
					stringBuilder.append(" and loc.status = 1 and (ka.open_date between '" + fromDate + "'  and '"
							+ toDate + "') ");

				if (accountStatus.equalsIgnoreCase("A"))
					stringBuilder.append(" and ((loc.close_date between '" + fromDate + "'  and '" + toDate
							+ "') or (ka.open_date between '" + fromDate + "'  and '" + toDate + "') )");
				stringBuilder.append(" order by kp.id,ka.open_date,ka.account_number");
			}
			accList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("accList---" + accList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getAccountInfoReport method");
			throw new KlsReportRuntimeException("Can not print AccountInfo report:", exception.getCause());
		}
		finally
		{
			EntityManagerUtil.closeSession();
		}
		
		
		logger.info("End:Inside getAccountInfoReport method");

		return accList;
	
		
	
	}


}
