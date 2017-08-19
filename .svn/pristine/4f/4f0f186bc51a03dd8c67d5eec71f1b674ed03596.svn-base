package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.IMemberandCardLinkingReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class MemberandCardLinkingReportDAO implements  IMemberandCardLinkingReportDAO{
	private static final Logger logger = Logger.getLogger(MemberandCardLinkingReportDAO.class);
	@Override
	public List<Object[]> getMemberandCardLinkingReport(Integer pacsId , String rType , Integer memberId) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		logger.info("Start:Inside getMemberandCardLinkingReport method");
				try {
			StringBuilder stringBuilder = new StringBuilder();
			EntityManager em = EntityManagerUtil.getEntityManager();
			stringBuilder.append("select p.member_number,concat(first_name,last_name) as member_name,ap.kcc_card_number,ap.savings_account_number as Sbaccno,a.account_number as Loanaccno,ap.dummy_sb_account_number as DummySbaccno,ap.atm_loan_account_number as DummyLoanaccno from membership.person p , kls_schema.account_property ap ,kls_schema.account a where p.party_id=ap.customer_id and ap.id=a.account_property_id");
			if (pacsId != 0)
				stringBuilder.append(" and p.pacs_id =" + pacsId + " ");
			
					
			if(rType.equalsIgnoreCase("l")) {
				stringBuilder.append(" and ap.kcc_card_number is not null");
				}
		    if(rType.equalsIgnoreCase("n")){
				stringBuilder.append(" and ap.kcc_card_number is null");
				}
				
			
			if(memberId!=0){
				stringBuilder.append(" and p.party_id="+memberId);
			}
			
			stringBuilder.append("  order by member_number");

			
			dataList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			
			logger.info("dataList---" + dataList.size());
			} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMemberandCardLinkingReport method");
			throw new KlsReportRuntimeException("Can not print MemberandCardLinkingReport report:", exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside getMemberandCardLinkingReport method");

		return dataList;
	}
		}