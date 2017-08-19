package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.IPurposeDisbursmentReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class PurposeWiseDisbursmentReportDAO implements IPurposeDisbursmentReportDAO {

	Date businessDate = LoanServiceUtil.getBusinessDate();

	private static Logger logger = Logger.getLogger(PurposeWiseDisbursmentReportDAO.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getPurposeWiseDisbursmentData(Integer pacsId, Integer purposeId, Integer productId, String loanType) {
		List<Map> result = new ArrayList<Map>();

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder("");
			queryString
					.append("select distinct new Map(pla.purpose as purpose,pla.product as product,ap.pacs as pacs) from PacsLoanApplication pla,AccountProperty ap where ");
			if (pacsId != 0)
				queryString.append("ap.pacs.id=" + pacsId + " and ");
						queryString.append("ap.customerId=pla.customerId and ");
			if (productId != 0)
				queryString.append("pla.product.id=" + productId + " and ");
			queryString.append("pla.product.productType.loanType='" + loanType + "'");
			if (purposeId != 0)
				queryString.append(" and pla.purpose.id=" + purposeId + " ");
			Query query = em.createQuery(queryString.toString());
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new KlsReportRuntimeException("Could not get Purpose Wise disbursment report");
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getPurposeWiseDisbursmentUptoPeriod(Integer pacsId, Integer productId, Integer purposeId, String month,Integer branchId ) {
		List<Object[]> result = new ArrayList<Object[]>();

		Date financialYrstartDate = DateUtil.getFinancialYearBeginDate(businessDate);
		Date financialYrEndDate = DateUtil.getFinancialYearEndDate(businessDate);
		String year = "";
		if (Integer.parseInt(month) > 3)
			year = financialYrstartDate.toString().substring(0, 4);
		else
			year = financialYrEndDate.toString().substring(0, 4);
		logger.info("month&Year" + month + "  &  " + year);
		
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder("");

			queryString
					.append("select (select activity_name from kls_schema.activity where id =a.activity_id) "
							+ "activity_name, sum(sfmfcnt)sfmfcnt, sum(sfmfAmt)sfmfAmt, sum(otherscnt)otherscnt, sum(othersAmt)othersAmt, "
							+ "sum(scstcnt)scstcnt, sum(scstAmt)scstAmt, sum(womencnt)womencnt, sum(womenamt)womenamt from (select activity_id, case when "
							+ "mp.farmer_type_id in (1,2) and (mp.gender_id <>2 and (mp.caste_id is null or mp.caste_id not in (2,3))) then 1 else 0 end sfmfcnt,"
							+ "case when mp.farmer_type_id in (1,2) and (mp.gender_id <>2 and (mp.caste_id is null or mp.caste_id not in (2,3))) then pld.disbursed_amount else 0 end sfmfAmt,"
							+ "case when mp.farmer_type_id not in (1,2) then 1 else 0 end otherscnt,case when mp.farmer_type_id not in (1,2) then pld.disbursed_amount else 0 end othersAmt,"
							+ "case when mp.caste_id in (2,3) and mp.gender_id <>2 then 1 else 0 end scstcnt, case when mp.caste_id in (2,3) and mp.gender_id <>2 then pld.disbursed_amount else 0 end scstAmt,"
							+ "case when mp.gender_id  =2 then 1 else 0 end womencnt,case when mp.gender_id  =2 then pld.disbursed_amount else 0 end womenamt from "
							+ "kls_schema.pacs_loan_disbursement  pld, kls_schema.line_of_credit lc, "
							+ "kls_schema.pacs_loan_application pla, membership.person mp,kls_schema.pacs_loan_app_activity pa, "
							+ "kls_schema.pacs p "
							+ "where pld.line_of_credit_id  = lc.id and lc.customer_id =pla.customer_id and "
							+ "lc.pacs_loan_application_id = pla.id and pa.application_id  = pla.id and pla.product_id=" + productId + " "
							+ "and pla.purpose_id=" + purposeId + " and mp.party_id = lc.customer_id and mp.pacs_id= p.id and mp.pacs_id=" + pacsId +" and p.branch_id=" + branchId + " "
							+ "and pld.disbursement_date between '" + financialYrstartDate + "' and (select last_day(date('" + month + "  01 " + year
							+ "')))) a group by activity_id");
			Query query = em.createNativeQuery(queryString.toString());
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new KlsReportRuntimeException("Could not get Purpose Wise disbursment report");
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return result;
	}
	
}