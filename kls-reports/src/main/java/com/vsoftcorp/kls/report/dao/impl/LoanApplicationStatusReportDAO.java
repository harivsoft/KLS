package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.ILoanApplicationStatusReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class LoanApplicationStatusReportDAO implements ILoanApplicationStatusReportDAO {
	
	private static final Logger logger = Logger
			.getLogger(LoanApplicationStatusReportDAO.class);

	@Override
	public List<Object[]> getLoanStatusDetails(Integer pacsId, String loanType,
			String fromDate, String toDate,Integer status) {
		
		List<Object[]> loanAccountList = new ArrayList<Object[]>();
		logger.info("Start: getLoanStatusDetails method Inside LoanAccountStatusReportDAO");
		
		try {
			Date fDate = Date.valueOf(DateUtil.getFormattedDate(fromDate));
			Date tDate = Date.valueOf(DateUtil.getFormattedDate(toDate));
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
					.append("select p.member_number,l.id,p.first_name,p.father_spouse,v.name as village,a.mobile_no as contact,l.app_date,prod.name as loan_product,pur.purpose,(select sub_purpose_name from kls_schema.sub_purpose where id=l.sub_purpose_id) as sub_purpose,(select activity_name from kls_schema.activity where id = (Select activity_id from  kls_schema.pacs_loan_app_activity where application_id=l.id)) as activity,l.total_requested_amount,l.total_amount_as_per_unit_cost,l.recommended_amount,l.scrutiny_amount,l.scrutiny_remarks,l.inspection_amount,l.inspection_remarks,l.inspection_date,l.sanction_amount,l.sanction_date,l.loan_period,l.interest_category_id,pt.loan_type,pt.id loanTypeId from kls_schema.pacs_loan_application l, membership.person p,membership.address a,kls_schema.village v,kls_schema.product prod,kls_schema.purpose pur,kls_schema.sub_purpose spur,kls_schema.pacs_loan_app_activity aa,kls_schema.product_type pt where l.customer_id=p.party_id and v.id=a.village_id and a.id in (select min(id) from membership.address where party_id = a.party_id) and a.party_id=l.customer_id and prod.id=l.product_id and pur.id=l.purpose_id and aa.application_id=l.id and pt.id=prod.product_type_id and pt.loan_type='"+loanType+"' and p.pacs_id="+pacsId+" and l.application_status="+status+" and l.app_date between '"+fDate+"' and '"+tDate+"' order by id");

			loanAccountList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("LoanAccounts List---" + loanAccountList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:getLoanStatusDetails method Inside LoanAccountStatusReportDAO");
			throw new KlsReportRuntimeException(
					"Can not print LoanAccountStatus report:", exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:getLoanStatusDetails method Inside LoanAccountStatusReportDAO");
		return loanAccountList;
	}
	}


