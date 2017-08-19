/*

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.loan.ILoanInterestCalculationDAO;
import com.vsoftcorp.kls.loan.util.InterestCalculationUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.interest.InterestCalculationBasis;
import com.vsoftcorp.time.Date;

*//**
 * @author sponnam
 * 
 *//*
public class intcal {

	private static final Logger logger = Logger
			.getLogger(LoanInterestCalculationDAO.class);

	@Override
	public void calculateInterest(Date theBusinessDate,
			InterestCalculationBasis theCalculationBasis) {

		if (theCalculationBasis.getValue().equalsIgnoreCase(
				InterestCalculationBasis.Daily.getValue())) {
			currentDayInterestCalculation(theBusinessDate);
		} else if (theCalculationBasis.getValue().equalsIgnoreCase(
				InterestCalculationBasis.Monthly.getValue())) {
			calculateInterestTillDate(theBusinessDate);
		}

	}

	@Override
	public List<LineOfCredit> getLineOfCreditList(Date theBusinessDate) {
		logger.info(" business date:" + theBusinessDate);
		logger.info(" START TIME::::::::::::::::::::::::::::"
				+ new java.util.Date());
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<LineOfCredit> cq = cb.createQuery(LineOfCredit.class);

			Root<LineOfCredit> LineOfCreditRoot = cq.from(LineOfCredit.class);

			CriteriaQuery<LineOfCredit> lineOfCredits = cq
					.select(LineOfCreditRoot);

			TypedQuery<LineOfCredit> tq = em.createQuery(lineOfCredits);

			List<LineOfCredit> lineOfCreditList = tq.getResultList();
			EntityManagerUtil.closeSession();
			return lineOfCreditList;

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error(" exception while getting loc");
			throw new DataAccessException(
					"Unable to fetch from database for loc", excp.getCause());
		}

	}

	@Override
	public BigDecimal getRateOfInterest(Integer interestCategoryId,
			Date theBusinessDate, Money theBalance) {

		logger.info("business date:" + theBusinessDate);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			BigDecimal roi = BigDecimal.ZERO;
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<SlabwiseInterestRate> cq = cb
					.createQuery(SlabwiseInterestRate.class);
			Root<SlabwiseInterestRate> SlabwiseInterestRateRoot = cq
					.from(SlabwiseInterestRate.class);

			Predicate predicate1 = cb.equal(
					SlabwiseInterestRateRoot.get("interestCategory"),
					interestCategoryId);
			Predicate predicate2 = cb.lessThanOrEqualTo(
					SlabwiseInterestRateRoot.<Money> get("fromSlab"),
					Money.absolute(theBalance));
			Predicate predicate3 = cb.greaterThanOrEqualTo(
					SlabwiseInterestRateRoot.<Money> get("toSlab"),
					Money.absolute(theBalance));

			cq.where(cb.and(predicate1, predicate2, predicate3));

			TypedQuery<SlabwiseInterestRate> tq = em.createQuery(cq);
			if (tq.getResultList().size() > 0)
				roi = tq.getResultList().get(0).getRoi();
			EntityManagerUtil.closeSession();
			return roi;

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error(" exception while fetching ROI");
			throw new DataAccessException("Unable to fetch ROI from database",
					excp.getCause());
		}
	}

	@Override
	public void updateInterest(LineOfCredit theLoc, Money theInterestDue) {

		logger.info("in updating interest");
		EntityManager em = EntityManagerUtil.getEntityManager();

		em.getTransaction().begin();
		logger.info("in updating interest::::" + theInterestDue);
		theLoc.setInterestDue(theInterestDue);
		em.merge(theLoc);
		em.getTransaction().commit();
		EntityManagerUtil.closeSession();

	}

	@Override
	public void currentDayInterestCalculation(Date theBusinessDate) {
		List<LineOfCredit> lineOfCreditList = getLineOfCreditList(theBusinessDate);
		logger.info("list size for loc" + lineOfCreditList.size());
		for (Iterator<LineOfCredit> iterator = lineOfCreditList.iterator(); iterator
				.hasNext();) {

			LineOfCredit lineOfCredit = iterator.next();
			logger.info("list size for loc"
					+ lineOfCredit.getCurrentBalance().getMoney().getAmount());
			BigDecimal roi = getRateOfInterest(lineOfCredit
					.getInterestCategory().getId(), theBusinessDate,
					lineOfCredit.getCurrentBalance().getMoney());
			Money interestDue = InterestCalculationUtil.perDaySimpleInterest(
					lineOfCredit.getCurrentBalance().getMoney(),
					roi.floatValue(), 1);
			logger.info("interest calculated is:::" + interestDue);
			updateInterest(lineOfCredit, interestDue);

			logger.info(" END TIME::::::::::::::::::::::::::::"
					+ new java.util.Date());

		}

	}

	@Override
	public void calculateInterestTillDate(Date theBusinessDate) {
		List<CurrentTransaction> list = getTransactionHistoryList();
		System.out.println("lllllllllllllllllllllllll size" + list.size());
	}

	
	 * select
	 * f.line_of_credit_id,f.business_date,f.voucher_number,f.opening_balance
	 * +(f.trans_amt * f.crdr) from (select
	 * line_of_credit_id,date(business_date) as date,max(voucher_number) as vno
	 * from kls_schema.current_transaction group by
	 * line_of_credit_id,date(business_date)) as x inner join
	 * kls_schema.current_transaction as f on
	 * f.line_of_credit_id=x.line_of_credit_id and f.voucher_number=x.vno and
	 * x.date=f.business_date
	 
	
	 * select
	 * x.last_int_cal_date,f.line_of_credit_id,f.business_date,f.voucher_number
	 * ,f.opening_balance+(f.trans_amt * f.crdr) from (select
	 * l.last_int_cal_date,line_of_credit_id,date(business_date) as
	 * date,max(voucher_number) as vno from kls_schema.current_transaction
	 * c,kls_schema.line_of_credit l where l.id=c.line_of_credit_id group by
	 * line_of_credit_id,date(business_date),l.last_int_cal_date) as x inner
	 * join kls_schema.current_transaction as f on
	 * f.line_of_credit_id=x.line_of_credit_id and f.voucher_number=x.vno and
	 * x.date=f.business_date and f.business_date='07 Aug 2014'
	 
	@Override
	public List<CurrentTransaction> getTransactionHistoryList() {

		List transactionList = new ArrayList();
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em
				.createNativeQuery("select x.last_int_cal_date,f.line_of_credit_id,f.business_date,f.voucher_number,f.opening_balance+(f.trans_amt * f.crdr) as clbal,x.interest_category_id from (select l.interest_category_id,l.last_int_cal_date,line_of_credit_id,date(business_date) as date,max(voucher_number) as vno from kls_schema.current_transaction c,kls_schema.line_of_credit l where l.id=c.line_of_credit_id group by line_of_credit_id,date(business_date),l.last_int_cal_date,l.interest_category_id) as x inner join kls_schema.current_transaction as f on f.line_of_credit_id=x.line_of_credit_id and f.voucher_number=x.vno and x.date=f.business_date");
		// Query query =
		// em.createQuery("select c.lineOfCredit.lastInterestCalculatedDate,c.lineOfCredit.id,c.businessDate,MAX(c.voucherNumber) from CurrentTransaction c GROUP BY c.lineOfCredit.lastInterestCalculatedDate,c.lineOfCredit.id,c.businessDate");
		
		 * transactionList = query.getResultList(); for(Object o:
		 * transactionList) { Object[] ans = (Object[])o;
		 * System.out.println("ssss"+ans[0]+ans[1]+ans[2]+ans[3]); }
		 

		
		 * TypedQuery<CurrentTransaction> query =
		 * em.createQuery("SELECT c FROM CurrentTransaction c",
		 * CurrentTransaction.class); List<CurrentTransaction> results =
		 * query.getResultList(); for (CurrentTransaction c : results) {
		 * System.out.println(c.getAccount() + " => " + c.getId()); }
		 

		// Query query =
		// em.createQuery("select c.lineOfCredit.id,c.businessDate,c.lineOfCredit.lastInterestCalculatedDate,MAX(c.voucherNumber) from CurrentTransaction c JOIN c.lineOfCredit l GROUP BY c.lineOfCredit.id,c.businessDate,c.lineOfCredit.lastInterestCalculatedDate");
		// Query query =
		// em.createQuery("select c.lineOfCredit.id,c.businessDate,c.lineOfCredit.lastInterestCalculatedDate,MAX(c.voucherNumber) from CurrentTransaction c  GROUP BY c.lineOfCredit.id,c.businessDate,c.lineOfCredit.lastInterestCalculatedDate");
		transactionList = query.getResultList();
		System.out.println("Transaction List:" + transactionList);
		for (Object o : transactionList) {
			Object[] transaction = (Object[]) o;
			CurrentTransaction trn = new CurrentTransaction();
			trn.setId(Long.valueOf(transaction[0].toString()));
			trn.setBusinessDate(DateUtil.getVSoftDateByString(transaction[1].toString()));
			LineOfCredit loc = new LineOfCredit();
			loc.setLastInterestCalculatedDate(DateUtil.getVSoftDateByString(transaction[2].toString()));
			InterestCategory interestCategory = new InterestCategory();
			interestCategory.setId(Integer.parseInt(transaction[4].toString()));
			loc.setInterestCategory(interestCategory);
			trn.setLineOfCredit(loc);
			trn.setVoucherNumber(Integer.parseInt(transaction[3].toString()));
			
		}
		EntityManagerUtil.closeSession();
		return transactionList;
	}

	
	 * @Override public List<TransactionHistory> getTransactionHistoryList() {
	 * 
	 * EntityManager em = EntityManagerUtil.getEntityManager(); CriteriaBuilder
	 * cb = em.getCriteriaBuilder();
	 * 
	 * CriteriaQuery<TransactionHistory> q =
	 * cb.createQuery(TransactionHistory.class); Root<TransactionHistory> c =
	 * q.from(TransactionHistory.class); logger.info("ddddd"+c.toString());
	 * Join<TransactionHistory,TransactionHistory> p =
	 * c.join("TransactionHistory", JoinType.LEFT); q.multiselect(c,
	 * p.get("id")); logger.info("aaaaa"+q.toString()); return null; }
	 

}
*/