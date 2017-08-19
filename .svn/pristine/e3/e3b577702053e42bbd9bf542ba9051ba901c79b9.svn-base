package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IAccountSavingReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class AccountSavingReportDAO implements IAccountSavingReportDAO {

	private static final Logger logger = Logger.getLogger(AccountSavingReportDAO.class);
	@Override
		public List<Object[]> getAccountInfoReportStatus(Integer pacsId,String Status)
		{
			List<Object[]> accList = new ArrayList<Object[]>();
			      
			try
			{
				EntityManager em = EntityManagerUtil.getEntityManager();
				StringBuilder stringBuilder = new StringBuilder();
				
				if(Status.equalsIgnoreCase("assigned"))
				{
				stringBuilder.append("select * from(select p.first_name,p.member_number,ap.account_number, ap.savings_account_number,ap.sb_status,ap.remarks,p.pacs_id from "
						+ "membership.person p left join ((select * from kls_schema.account a,kls_schema.account_property ap "
						+ " where a.account_property_id=ap.id) a left join kls_schema.sb_account_mappig_info sb "
						+ "on a.customer_id=sb.party_id "
						+ "and sb.sb_status in (1,2,5)) ap on p.party_id=ap.customer_id )"
						+ "where pacs_id="+ pacsId+" "
						+ " and savings_account_number is not null and savings_account_number <> '' order by member_number asc ");
				}
				
				
				
				
				
			
				else
				{
					stringBuilder.append("select * from (select p.first_name,p.member_number,ap.account_number, ap.savings_account_number,ap.sb_status,ap.remarks,p.pacs_id from  "
							+ " membership.person p left join ((select * from kls_schema.account a,kls_schema.account_property ap where a.account_property_id=ap.id) a  "
							+ "left join kls_schema.sb_account_mappig_info sb on a.customer_id=sb.party_id and sb.sb_status in (3,6,7)) ap on p.party_id=ap.customer_id )where pacs_id="+ pacsId+"  "
							+ "and ( savings_account_number is  null or savings_account_number = '') order by member_number asc ");
				}
				accList = em.createNativeQuery(stringBuilder.toString()).getResultList();
				logger.info("accounts list size======="+accList.size());
			}catch (Exception exception) {
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


