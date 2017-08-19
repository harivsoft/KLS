package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.action.LoanRegisterSummeryReportActionServlet;
import com.vsoftcorp.kls.report.dao.ILoanRegisterReportDAO;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class LoanRegisterReportDAO implements ILoanRegisterReportDAO {

	private static final Logger logger = Logger.getLogger(LoanRegisterSummeryReportActionServlet.class);
	
	@Override
	public List<Object[]> getLoanRegisterReport(Integer pacsId,	Integer productId, Date businessDate, String loanType) {
		
		List<Object[]> loanAccList = new ArrayList<Object[]>();

		try
		{
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			if("L".equals(loanType)) {
				stringBuilder.append("select  mp.first_name,mp.father_spouse,mp.member_number, "
						+ "ka.account_number accountNumber,loc.id loc_id,loc.loan_type loanType,(select name crop_name from kls_schema.crop where id = loc.crop_id), "
						+ "(select a.activity_name from kls_schema.activity a, kls_schema.pacs_loan_app_activity pa where a.id = pa.activity_id and pa.application_id = loc.pacs_loan_application_id),  "
						+ "(select v.name village_name from kls_schema.village v, membership.address a where v.id = a.village_id and a.party_id = loc.customer_id limit 1), "
						+ "(select p.purpose from kls_schema.purpose p, kls_schema.pacs_loan_application a where p.id = a.purpose_id and a.id = loc.pacs_loan_application_id), "
						+ "loc.sanctioned_date, "
						+ "(select a.sanction_amount from kls_schema.pacs_loan_application a where a.id = loc.pacs_loan_application_id), "
						+ "(select a.loan_period from kls_schema.pacs_loan_application a where a.id = loc.pacs_loan_application_id), "
						+ "(select installment_amount from kls_schema.loan_repayment_schedule where  line_of_credit_id = loc.id and installment_date=(select min(installment_date) from kls_schema.loan_repayment_schedule where installment_date >='"+businessDate+"')),"
						+ "(select disbursed_amount from kls_schema.pacs_loan_disbursement where line_of_credit_id = loc.id), "
						+ "(select disbursement_date from kls_schema.pacs_loan_disbursement where line_of_credit_id = loc.id) "
						+ "from kls_schema.line_of_credit loc, kls_schema.account ka,membership.person mp "
						+ "where ka.id=loc.account_id and mp.party_id = loc.customer_id ");
						if(pacsId != null) {
							stringBuilder.append("and mp.pacs_id= "+pacsId);
						}
						if(productId != null) {
							stringBuilder.append(" and loc.product_id = "+productId);
						}
						if(businessDate != null) {
							stringBuilder.append(" and ka.open_date <= '"+businessDate+"'");
						}
						stringBuilder.append(" and loc.loan_type = 'L' group by loc.id, mp.first_name,mp.member_number,accountNumber, "
						+ "mp.father_spouse order by accountNumber ");

				loanAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			} else if("C".equals(loanType)) {
				stringBuilder.append("select  mp.first_name,mp.father_spouse,mp.member_number, "
						+ "ka.account_number accountNumber,loc.id loc_id,loc.loan_type loanType,(select name corp_name from kls_schema.crop where id = loc.crop_id), "
						+ "(select v.name village_name from kls_schema.village v, membership.address a where v.id = a.village_id and a.party_id = loc.customer_id limit 1), "
						+ "loc.sanctioned_date, "
						+ "loc.sanctioned_limit, "
						+ "(select sum(trans_amt) from (select * from kls_schema.current_transaction union select * from kls_schema.transaction_history) tran where tran.line_of_credit_id = loc.id and tran.tran_code in(1,5,6) and tran.crdr=-1), "
						+ "loc.season_id, (select s.name season_name from kls_schema.season s where s.id = loc.season_id)"
						+ "from kls_schema.line_of_credit loc,kls_schema.account ka, membership.person mp "
						+ "where ka.id=loc.account_id and mp.party_id = loc.customer_id ");
						if(pacsId != null) {
							stringBuilder.append("and mp.pacs_id= "+pacsId);
						}
						if(productId != null && productId >0) {
							stringBuilder.append(" and loc.season_id = "+productId);
						}
						if(businessDate != null) {
							stringBuilder.append(" and loc.sanctioned_date<= '"+businessDate+"'");
						}
						stringBuilder.append(" and loc.loan_type = 'C' group by loc.id, mp.first_name,mp.member_number,accountNumber, "
						+ "mp.father_spouse order by loc.season_id");

				loanAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			}

		}catch(Exception e) {
			e.printStackTrace();
		}


		return loanAccList;
	}
	
	@Override
	public List<Object[]> getLoanRegisterSummeryReport(Integer pacsId,	Integer productId, Date businessDate, String loanType) {

		List<Object[]> loanAccList = new ArrayList<Object[]>();

		try
		{
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			if("L".equals(loanType)) {
				stringBuilder.append("select fname,fspouse,memNum,accountNumber,loanType,cropName,actName,villName,purpose,min(sancDate) sancDate," 
						+"sum(sanLimt) sanLimit,lnPeriod,instAmt,disAmt,disDate  from " 
						+"(select  mp.first_name fname,mp.father_spouse fspouse,mp.member_number memNum, "
						+ "ka.account_number accountNumber,loc.id loc_id,loc.loan_type loanType,(select name crop_name from kls_schema.crop where id = loc.crop_id) cropName, "
						+ "(select a.activity_name from kls_schema.activity a, kls_schema.pacs_loan_app_activity pa where a.id = pa.activity_id and pa.application_id = loc.pacs_loan_application_id) actName,  "
						+ "(select v.name village_name from kls_schema.village v, membership.address a where v.id = a.village_id and a.party_id = loc.customer_id limit 1) villName, "
						+ "(select p.purpose from kls_schema.purpose p, kls_schema.pacs_loan_application a where p.id = a.purpose_id and a.id = loc.pacs_loan_application_id) purpose, "
						+ "loc.sanctioned_date sancDate, "
						+ "(select a.sanction_amount from kls_schema.pacs_loan_application a where a.id = loc.pacs_loan_application_id) sanLimt, "
						+ "(select a.loan_period from kls_schema.pacs_loan_application a where a.id = loc.pacs_loan_application_id) lnPeriod, "
						+ "(select installment_amount from kls_schema.loan_repayment_schedule where  line_of_credit_id = loc.id and " 
						+ "installment_date=(select min(installment_date) from kls_schema.loan_repayment_schedule where line_of_credit_id = loc.id and installment_date >='"+businessDate+"')) instAmt,"
						+ "(select coalesce(disbursed_amount,0) from kls_schema.pacs_loan_disbursement where line_of_credit_id = loc.id) disAmt, "
						+ "(select disbursement_date from kls_schema.pacs_loan_disbursement where line_of_credit_id = loc.id) disDate "
						+ "from kls_schema.line_of_credit loc, kls_schema.account ka,membership.person mp "
						+ "where ka.id=loc.account_id and mp.party_id = loc.customer_id ");
						if(pacsId != null) {
							stringBuilder.append("and mp.pacs_id= "+pacsId);
						}
						if(productId != null) {
							stringBuilder.append(" and loc.product_id = "+productId);
						}
						if(businessDate != null) {
							stringBuilder.append(" and ka.open_date <= '"+businessDate+"'");
						}
						stringBuilder.append(" and loc.loan_type = 'L' group by ka.account_number,loc.id, mp.first_name,mp.member_number,accountNumber, "
						+ "mp.father_spouse order by accountNumber) group by accountNumber,fname, fspouse,memNum,"
						+ "loanType,cropName,actName,villName,purpose,lnPeriod,instAmt,disAmt,disDate ");
						
				loanAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			} else if("C".equals(loanType)) {
				
				stringBuilder.append(" select fname, fspouse,memNum,accountNumber,coalesce(villName,' '),min(sancDate) sDate,sum(sancLimit) sLimit,coalesce(sum(total),0) tTotal,sId,sName from "
						+ "(select  mp.first_name fname,mp.father_spouse fspouse,mp.member_number memNum,ka.account_number accountNumber,loc.id loc_id, "
						+ "loc.loan_type loanType,(select name  from kls_schema.crop where id = loc.crop_id) corpName, "
						+ "(select v.name village_name from kls_schema.village v, membership.address a where v.id = a.village_id and a.party_id = loc.customer_id limit 1) villName, "
						+ "loc.sanctioned_date as sancDate, loc.sanctioned_limit sancLimit, "
						+ "(select sum(trans_amt) from (select * from kls_schema.current_transaction union select * from kls_schema.transaction_history) tran" 
						+ " where tran.line_of_credit_id = loc.id and tran.tran_code in(1,5,6) and tran.crdr=-1) total, "
						+ "loc.season_id sId, (select s.name sName from kls_schema.season s where s.id = loc.season_id)"
						+ "from kls_schema.line_of_credit loc,kls_schema.account ka, membership.person mp "
						+ "where ka.id=loc.account_id and mp.party_id = loc.customer_id ");
						if(pacsId != null) {
							stringBuilder.append("and mp.pacs_id= "+pacsId);
						}
						if(productId != null && productId >0) {
							stringBuilder.append(" and loc.season_id = "+productId);
						}
						if(businessDate != null) {
							stringBuilder.append(" and loc.sanctioned_date<= '"+businessDate+"'");
						}
						stringBuilder.append(" and loc.loan_type = 'C' group by ka.account_number,loc.id, mp.first_name,mp.member_number,accountNumber, mp.father_spouse "
								+ "order by ka.account_number ) group by accountNumber,fname, fspouse,memNum,villName,sId,sName ");

				loanAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			}

		}catch(Exception e) {
			e.printStackTrace();
		}


		return loanAccList;
	}

}
