package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanInterestCalculationDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanInterestHistoryCalculationDAO;
import com.vsoftcorp.kls.loan.util.InterestCalculationUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LoanInterestHistoryCalculationDAO implements
		ILoanInterestHistoryCalculationDAO {

	private static final Logger logger = Logger
			.getLogger(LoanInterestHistoryCalculationDAO.class);

	@Override
	public void calculateInterestForHistory() {

		List transactionList = new ArrayList();
		EntityManager em = EntityManagerUtil.getEntityManager();

		ILineOfCreditDAO dao = new LineOfCreditDAO();
		ILoanInterestCalculationDAO intCalDao = new LoanInterestCalculationDAO();
		Query query = em
				.createNativeQuery("select f.line_of_credit_id,x.interest_category_id,f.business_date,x.last_int_cal_date,f.voucher_number,f.opening_balance+(f.trans_amt * f.crdr) as clbal from (select l.interest_category_id,l.last_int_cal_date,line_of_credit_id,date(business_date) as date,max(voucher_number) as vno from kls_schema.current_transaction c,kls_schema.line_of_credit l where l.id=c.line_of_credit_id group by line_of_credit_id,date(business_date),l.last_int_cal_date,l.interest_category_id) as x inner join kls_schema.current_transaction as f on f.line_of_credit_id=x.line_of_credit_id and f.voucher_number=x.vno and x.date=f.business_date order by business_date asc");
		transactionList = query.getResultList();
		System.out.println("Transaction List:" + transactionList);
		for (Object o : transactionList) {
			Object[] transaction = (Object[]) o;

			Money closingBalance = MoneyUtil.getMoney(Double
					.valueOf(transaction[5].toString()));
			int noOfDays = 1;
			Long id = Long.valueOf(transaction[0].toString());
			SlabwiseInterestRate interestRate = intCalDao.getRateOfInterest(
					Integer.parseInt(transaction[1].toString()),
					DateUtil.getVSoftDateByString(transaction[2].toString()),
					closingBalance);

			Money theInterestDueAmount = InterestCalculationUtil
					.perDaySimpleInterest(closingBalance, interestRate.getRoi()
							.floatValue(), noOfDays);
			dao.updateLineOfCreditInterestbyId(id, theInterestDueAmount);

		}
		EntityManagerUtil.closeSession();
	}

}
