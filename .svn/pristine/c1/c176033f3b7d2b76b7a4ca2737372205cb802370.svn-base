package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.report.dao.IBalanceListReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class BalanceListReportDAO implements IBalanceListReportDAO {

	@Override
	public List<LineOfCredit> getLineOfCreditByPacsAndCustomerId(Integer pacsId, Integer productId, Long customerId) {
		List<LineOfCredit> locList = new ArrayList<LineOfCredit>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder("");
			query.append("select lc from LineOfCredit lc where ");
			if (pacsId != 0)
				query.append("lc.account.accountProperty.pacs.id=" + pacsId);
			if (productId != 0)
				query.append(" and lc.product.id=" + productId);
			if (customerId != 0)
				query.append(" and lc.customerId=" + customerId);
			query.append(" and lc.lineOfCreditStatus=" + LineOfCreditStatus.Active.getValue());
			query.append(" and lc.loanType<>'B' order by lc.product.id, lc.customerId,lc.id");

			locList = em.createQuery(query.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting Balance List report data");
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return locList;
	}

	@Override
	public List<Object[]> getBalanceListReportData(Long lineOfcreditId, Date asOnDate) {
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder query = new StringBuilder("");
			query.append("select sum(principle_balance) principle_bal,sum(int_balance)  int_bal,sum(charges_balance) charges_bal from "
					+ "(select sum(case when tran_code =1 then trans_amt*crdr else 0 end) principle_balance,sum(case when tran_code in (7,2) then trans_amt*crdr else 0 end) int_balance,"
					+ "sum(case when tran_code in (17,4) then trans_amt*crdr else 0 end) charges_balance from kls_schema.transaction_history th where business_date <='"
					+ asOnDate + "' and line_of_Credit_id = " + lineOfcreditId + " union ");
			query.append("select sum(case when tran_code =1 then trans_amt*crdr else 0 end) principle_balance,sum(case when tran_code in (7,2)"
					+ " then trans_amt*crdr else 0 end) int_balance,sum(case when tran_code=17 then trans_amt*crdr else 0 end) charges_balance from kls_schema.current_transaction th where business_date <='"
					+ asOnDate + "' and line_of_Credit_id = " + lineOfcreditId + ") ");
			result = em.createNativeQuery(query.toString()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting Balance List report data");
		}
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return result;
	}
}
