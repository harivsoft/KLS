package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.IOverdueReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class OverdueReportDAO implements IOverdueReportDAO {
	private static final Logger logger = Logger.getLogger(OverdueReportDAO.class);

	@Override
	public List<Object[]> getOverdueReport(Integer pacsId, Integer productId, String memberNo, Date instalmentDate,
			BigDecimal villageId, String loanType, String reportMode) {
		List<Object[]> overDueList = new ArrayList<Object[]>();
		logger.info("Start:Inside getOverdueReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			Date finDate = DateUtil.getFinancialYearBeginDate(instalmentDate);
			logger.info("finDate : " + finDate);
			stringBuilder
					.append("select distinct p.pacs_id,sum(d.disbursed_amount),ad.village_id,l.scheme_id,l.product_id,l.customer_id,p.first_name,ad.phone_no,a.account_number,l.sanctioned_limit,pt.loan_type,l.id,pt.id as loanTypeId from kls_schema.product prod,kls_schema.product_type pt,kls_schema.line_of_credit l,kls_schema.account a,kls_schema.account_property ap,membership.address ad,kls_schema.loan_repayment_schedule r,membership.person p,kls_schema.loan_disbursement_schedule d where l.customer_id=ad.party_id and ap.id = a.account_property_id and l.id = d.line_of_credit_id and l.customer_id = p.party_id and pt.id = prod.product_type_id and prod.id = l.product_id and"
							+ "  r.installment_date between '"
							+ finDate
							+ "'  and '"
							+ instalmentDate
							+ "' and ad.party_id = p.party_id and ap.customer_id = p.party_id and ap.customer_id = ad.party_id and ad.id = (select min(ad.id) from membership.address ad where ad.party_id = p.party_id)");
			if (!memberNo.equals("0") && (reportMode.equalsIgnoreCase("D") || reportMode.equalsIgnoreCase("S")))
				stringBuilder.append(" and l.customer_id =" + memberNo
						+ "  and ad.id = (select min(id) from membership.address where party_id = " + memberNo + ")");

			if (productId != 0) {
				stringBuilder.append(" and prod.id =" + productId + " ");
			}

			if (pacsId != 0)
				stringBuilder.append(" and p.pacs_id=" + pacsId + " ");

			if (!villageId.equals(new BigDecimal("0")))
				stringBuilder.append(" and ad.village_id=" + villageId + " ");

		//	if(reportMode.equalsIgnoreCase("D"))
			stringBuilder.append(" and pt.loan_type= '" + loanType + "'");
			stringBuilder
					.append(" and l.status!=0 and l.current_balance!=0 and p.status_id = 1 group by p.pacs_id,ad.village_id,pt.loan_type,l.scheme_id,l.product_id,l.customer_id,p.first_name,ad.phone_no,a.account_number,l.sanctioned_limit,l.id,pt.id order by p.pacs_id,ad.village_id,pt.loan_type,l.scheme_id,l.product_id,l.customer_id");
			overDueList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("overDueList---" + overDueList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getOverdueReport method");
			throw new KlsReportRuntimeException("Can not print overduelist report:", exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside getOverdueReport method");

		return overDueList;
	}

	@Override
	public Object[] getOutStandingValByLocIdAndBusinessDate(Long locId, Date instalmentDate) {
		logger.info("locId : " + locId);
		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		List<Object[]> overDueList = new ArrayList<Object[]>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + locId);
			if (locId != null) {
				Query query = em
						.createNativeQuery("select account_number,line_of_credit_id,princ_rec, int_rec, (princ_rec+int_rec) total_reco, loan_outstanding_amount,0, loan_outstanding_amount-princ_rec princ_Overdue,case when int_rec<0 then int_rec else 0 end int_overdue, (loan_outstanding_amount-princ_rec+(case when int_rec<0 then int_rec else 0 end ) ) total_OD from (select account_number,line_of_credit_id,loan_outstanding_amount, (select sum(transAmnt) from ( select sum(trans_amt*crdr) transAmnt from kls_schema.transaction_history ksh where business_date <='"
								+ instalmentDate
								+ "' and tran_code = 1 and account_number= a.account_number union select sum(trans_amt*crdr) from kls_schema.current_transaction ksh where business_date <='"
								+ instalmentDate
								+ "' and tran_code = 1 and account_number= a.account_number )) princ_rec, (select sum(transAmnt) from (select sum(trans_amt*crdr) transAmnt from kls_schema.transaction_history ksh where business_date <='"
								+ instalmentDate
								+ "' and tran_code in (7,2) and account_number= a.account_number  union select sum(trans_amt*crdr) from kls_schema.current_transaction ksh where business_date <='"
								+ instalmentDate
								+ "' and tran_code in (7,2) and account_number= a.account_number) ) int_rec from (select line_of_credit_id, loan_outstanding_amount from kls_schema.loan_repayment_schedule lrs where (line_of_credit_id,installment_date) in (select line_of_credit_id,max(installment_date ) from kls_schema.loan_repayment_schedule where installment_date <=add_months(date('"
								+ instalmentDate
								+ "'),15) group by line_of_credit_id)) klrs , kls_schema.account a where a.id= klrs.line_of_credit_id and klrs.line_of_credit_id = "
								+ locId + " ) ");
				overDueList = query.getResultList();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule list from the database.",
					excp.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		if (overDueList.size() > 0)
			return overDueList.get(0);
		else
			return null;
	}

	@Override
	public Object[] getOutStandingValByLocIdAndBusinessDateForST(Long locId, Date instalmentDate) {
		logger.info("locId : " + locId);
		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		List<Object[]> overDueList = new ArrayList<Object[]>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + locId);
			if (locId != null) {
				Query query = em
						.createNativeQuery("select line_of_credit_id,sum(case when tran_code = 1 then trans_amt*crdr else 0 end) princ_balance,sum(case when tran_code = 1 then trans_amt*crdr else 0 end) int_balance from kls_schema.transaction_history ksh where business_date <='"
								+ instalmentDate + "' and line_of_Credit_id = " + locId + " group by line_of_Credit_id");
				overDueList = query.getResultList();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule list from the database.",
					excp.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		if (overDueList.size() > 0)
			return overDueList.get(0);
		else
			return null;
	}

	@Override
	public Long getOverDueLoc(Long locId, Date instalmentDate) {
		logger.info("locId : " + locId);
		logger.info("Start: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		List<Long> ids = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info(" loanLocId : " + locId);
			if (locId != null) {
				Query query = em
						.createNativeQuery("select l.id from kls_schema.line_of_credit l,kls_schema.season s where s.overdue_date<='"
								+ instalmentDate + "' and l.id = " + locId + " and s.id=season_id");
				ids = query.getResultList();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the loan repayment schedule list from the "
					+ "database using line of credit id.Exception thrown.");

			throw new DataAccessException("Could not fetch the loan repayment schedule list from the database.",
					excp.getCause());
		}
		logger.info("End: Fetching the list of loan repayment schedule list by line of credit id in getLoanRepaymentScheduleByLocIdAndBusinessDate() method.");
		if (ids.size() > 0)
			return ids.get(0);
		else
			return null;
	}

	@Override
	public BigDecimal getDisbursedAmountByLocId(Long locId) {

		Money totalAmount = Money.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select sum(l.disbursedAmount) from PacsLoanDisbursement l where l.lineOfCredit.id=:locId");

			query.setParameter("locId", locId);
			totalAmount = (Money) query.getSingleResult();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error: in getTotalSanctionAmount()");
			// return BigDecimal.ZERO;
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: in getDisbursedAmountByLocId() totalAmount:" + totalAmount);
		if (totalAmount != null)
			return totalAmount.getAmount();
		else
			return BigDecimal.ZERO;
	}
}
