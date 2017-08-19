package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.ICropWiseDrawlReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class CropWiseDrawlReportDAO implements ICropWiseDrawlReportDAO {

	private static final Logger logger = Logger.getLogger(CropWiseDrawlReportDAO.class);

	Date businessDate = LoanServiceUtil.getBusinessDate();
	Date financialYrEndDate = DateUtil.getFinancialYearEndDate(businessDate);
	String financialYrStartDate = DateUtil.getFinancialYearBeginDate(businessDate).toString();

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCropWiseDrawlData(Integer pacsId, Integer cropId, Integer seasonId, Integer branchId) {
		List<Object[]> result = new ArrayList<Object[]>();

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder("");

			queryString.append("select th.pacs_id,l.crop_id,l.season_id");
			queryString.append(" from kls_schema.transaction_history th,kls_schema.line_of_credit l,membership.person p,kls_schema.pacs pa,kls_schema.branch b ");
			queryString.append("where l.loan_type='C' and ");
			if (cropId != 0)
				queryString.append("l.crop_id=" + cropId + " and ");
			if (seasonId != 0)
				queryString.append("l.season_id=" + seasonId + " and ");
			if (pacsId != 0)
				queryString.append("th.pacs_id=" + pacsId + " and ");
			queryString.append("pa.branch_id=" + branchId + " and ");

			queryString.append("pa.branch_id = b.id and ");
			//queryString.append("th.pacs_id=pa.id and ");
			queryString.append("l.id=th.line_of_credit_id and ");			
			queryString.append("th.crDr=-1 and l.customer_id=p.party_id");
			queryString.append(" group by th.pacs_id,l.crop_id,l.season_id union select th.pacs_id,l.crop_id,l.season_id");
			queryString.append(" from kls_schema.current_transaction th,kls_schema.line_of_credit l,membership.person p,kls_schema.pacs pa,kls_schema.branch b ");
			queryString.append("where l.loan_type='C' and ");
			if (cropId != 0)
				queryString.append("l.crop_id=" + cropId + " and ");
			if (seasonId != 0)
				queryString.append("l.season_id=" + seasonId + " and ");
			if (pacsId != 0)
				queryString.append("th.pacs_id=" + pacsId + " and ");
			
			queryString.append("pa.branch_id=" + branchId + " and ");
			queryString.append("pa.branch_id = b.id and ");

			//queryString.append("th.pacs_id=pa.id and ");
			queryString.append("l.id=th.line_of_credit_id and ");
			queryString.append("th.crDr=-1 and l.customer_id=p.party_id");
			queryString.append(" group by th.pacs_id,l.crop_id,l.season_id");

			Query query = em.createNativeQuery(queryString.toString());
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new KlsReportRuntimeException("Could not get Cropwise disbursment report");
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getCropWiseDrawlPreviousData(Integer pacsId, Integer cropId, Integer seasonId, String month, Integer branchId ) {
		List<Object[]> result = new ArrayList<Object[]>();
		
		String year = "";
		if (Integer.parseInt(month) > 3)
			year = financialYrStartDate;
		else
			year = financialYrEndDate.toString();
		logger.info("month&Year" + month + "  &  " + year.substring(0, 4));
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder("");

			queryString.append("select sum(sfmfAmt) as abc,sum(scstAmt) as aa,sum(womanAmt) as cc from (select sum(case when p.farmer_type_id in (1,2) and (p.gender_id <>2 and p.caste_id not in (2,3)) then th.trans_amt else 0 end) sfmfAmt,sum(case when p.caste_id in (2,3) and p.gender_id <>2 then th.trans_amt else 0 end) scstAmt,sum(case when p.gender_id =2 then th.trans_amt else 0 end) womanAmt from kls_schema.transaction_history th,kls_schema.line_of_credit l,membership.person p,kls_schema.pacs pa");
			queryString.append(" where l.loan_type='C' and ");
			queryString.append("l.crop_id=" + cropId + " and ");
			queryString.append("l.season_id=" + seasonId + " and ");
			queryString.append("th.pacs_id=" + pacsId + " and ");
			queryString.append("l.id=th.line_of_credit_id and ");
			queryString.append("pa.branch_id= " + branchId +" and ");
			
			queryString.append("th.pacs_id=pa.id and ");
			queryString.append("th.crDr=-1 and l.customer_id=p.party_id and th.business_date>='" + financialYrStartDate
					+ "' and th.business_date<to_date('01-'||'" + month + "'||'-'||to_char(date('" + year + "'),'yyyy'),'dd-mm-yyyy') union ");
			queryString.append("select sum(case when p.farmer_type_id in (1,2) and (p.gender_id <>2 and p.caste_id not in (2,3)) then th.trans_amt else 0 end) sfmfAmt,sum(case when p.caste_id in (2,3) and p.gender_id <>2 then th.trans_amt else 0 end) scstAmt,sum(case when p.gender_id =2 then th.trans_amt else 0 end) womanAmt from kls_schema.current_transaction th,kls_schema.line_of_credit l,membership.person p,kls_schema.pacs pa ");
			queryString.append(" where l.loan_type='C' and ");
			queryString.append("l.crop_id=" + cropId + " and ");
			queryString.append("l.season_id=" + seasonId + " and ");
			queryString.append("th.pacs_id=" + pacsId + " and ");
			queryString.append("l.id=th.line_of_credit_id and ");
			queryString.append("pa.branch_id= " + branchId +" and ");
			
			queryString.append("th.pacs_id=pa.id and ");
			queryString.append("th.crDr=-1 and l.customer_id=p.party_id and th.business_date>='" + financialYrStartDate
					+ "' and th.business_date<to_date('01-'||'" + month + "'||'-'||to_char(date('" + year + "'),'yyyy'),'dd-mm-yyyy')) ");

			Query query = em.createNativeQuery(queryString.toString());
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new KlsReportRuntimeException("Could not get Cropwise disbursment report");
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return result.isEmpty() ? null : result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getCropWiseDrawlDuringtheMonthData(Integer pacsId, Integer cropId, Integer seasonId, String month, Integer branchId) {
		List<Object[]> result = new ArrayList<Object[]>();

		String year = "";
		if (Integer.parseInt(month) > 3)
			year = financialYrStartDate;
		else
			year = financialYrEndDate.toString();
		logger.info("month&Year----------------" + month + "  &  " + year.substring(0, 4));

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder("");

			queryString.append("select sum(sfmfAmt) as abc,sum(scstAmt) as aa,sum(womanAmt) as cc from (select sum(case when p.farmer_type_id in (1,2) and (p.gender_id <>2 and p.caste_id not in (2,3)) then th.trans_amt else 0 end) sfmfAmt,sum(case when p.caste_id in (2,3) and p.gender_id <>2 then th.trans_amt else 0 end) scstAmt,sum(case when p.gender_id =2 then th.trans_amt else 0 end) womanAmt from kls_schema.transaction_history th,kls_schema.line_of_credit l,membership.person p,kls_schema.pacs pa ");
			queryString.append(" where l.loan_type='C' and ");
			queryString.append("l.crop_id=" + cropId + " and ");
			queryString.append("l.season_id=" + seasonId + " and ");
			queryString.append("l.id=th.line_of_credit_id and ");
			queryString.append("pa.branch_id= " + branchId +" and ");
			
			queryString.append("th.pacs_id= " + pacsId +" and ");
			queryString.append("th.pacs_id=pa.id and ");
			queryString
					.append("th.crDr=-1 and (th.tran_code=1 or th.tran_code=5 or th.tran_code=6) and l.customer_id=p.party_id and th.business_date>='"
							+ financialYrStartDate
							+ "' and (to_char (business_date,'yy')=to_char(date('"
							+ year
							+ "'),'yy') and to_char (business_date,'mm')='" + month + "') union ");
			queryString.append("select sum(case when p.farmer_type_id in (1,2) and (p.gender_id <>2 and p.caste_id not in (2,3)) then th.trans_amt else 0 end) sfmfAmt,sum(case when p.caste_id in (2,3) and p.gender_id <>2 then th.trans_amt else 0 end) scstAmt,sum(case when p.gender_id =2 then th.trans_amt else 0 end) womanAmt from kls_schema.current_transaction th,kls_schema.line_of_credit l,membership.person p,kls_schema.pacs pa ");
			queryString.append(" where l.loan_type='C' and ");
			queryString.append("l.crop_id=" + cropId + " and ");
			queryString.append("l.season_id=" + seasonId + " and ");
			queryString.append("l.id=th.line_of_credit_id and ");
			queryString.append("pa.branch_id= " + branchId +" and ");
			
			queryString.append("th.pacs_id= " + pacsId +" and ");
			queryString.append("th.pacs_id=pa.id and ");
			queryString
					.append("th.crDr=-1 and (th.tran_code=1 or th.tran_code=5 or th.tran_code=6) and l.customer_id=p.party_id and th.business_date>='"
							+ financialYrStartDate
							+ "' and (to_char (business_date,'yy')=to_char(date('"
							+ year
							+ "'),'yy') and to_char (business_date,'mm')='" + month + "')) ");

			Query query = em.createNativeQuery(queryString.toString());
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new KlsReportRuntimeException("Could not get Cropwise disbursment report");
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return result.isEmpty() ? null : result.get(0);
	}
}