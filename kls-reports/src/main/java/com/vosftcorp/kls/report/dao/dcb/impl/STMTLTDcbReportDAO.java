package com.vosftcorp.kls.report.dao.dcb.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.dao.dcb.ISTMTLTDcbReportDAO;
import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class STMTLTDcbReportDAO implements ISTMTLTDcbReportDAO {

	private static final Logger logger = Logger.getLogger(STMTLTDcbReportDAO.class);

	Date financYrStartDate = DateUtil.getFinancialYearBeginDate(LoanServiceUtil.getBusinessDate());

	AccountingMoney zero = AccountingMoney.ZERO;

	@Override
	public AccountingMoney getSTAgriPricipleReceivable(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getSTAgriPricipleReceivable()");
		AccountingMoney result = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			if(pacsIdList.size()==0)
				pacsIdList.add(0);
			Query query = em
					.createQuery("select sum(t.transactionAmount) from TransactionHistory t where t.pacs.id in (:pacsIdList)"
							+ " and t.transactionCode=:transactionCode"
							+ " and t.crDr=-1 and t.lineOfCredit.loanType=:loanType and t.businessDate>=:financYrStartDate and t.lineOfCredit.season.overdueDate<:asOnDate");
			query.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL);
			query.setParameter("asOnDate", asOnDate);
			query.setParameter("financYrStartDate", financYrStartDate);
			query.setParameter("loanType", "C");
			if(pacsIdList.size()>0)
			query.setParameter("pacsIdList",pacsIdList);
			if (!query.getResultList().isEmpty())
				result = (AccountingMoney) query.getResultList().get(0);
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(t.transactionAmount) from CurrentTransaction t where ");
			if(pacsIdList.size()> 0)
				stringBuilder.append("t.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("t.transactionCode=:transactionCode and t.lineOfCredit.loanType=:loanType and t.businessDate>=:financYrStartDate and t.lineOfCredit.season.overdueDate<=:asOnDate");
/*			query = em
					.createQuery("select sum(t.transactionAmount) from CurrentTransaction t where t.pacs.id="
							+ pacsId
							+ " and t.transactionCode=:transactionCode"
							+ " and t.lineOfCredit.loanType=:loanType and t.businessDate>=:financYrStartDate and t.lineOfCredit.season.overdueDate<=:asOnDate");*/
			query  = em.createQuery(stringBuilder.toString());
			query.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL);
			query.setParameter("asOnDate", asOnDate);
			query.setParameter("financYrStartDate", financYrStartDate);
			query.setParameter("loanType", "C");
			if(pacsIdList.size()> 0)
				query.setParameter("pacsIdList", pacsIdList);
			AccountingMoney curTransAmt = zero;
			if (!query.getResultList().isEmpty())
				curTransAmt = (AccountingMoney) query.getResultList().get(0);

			if (result == null)
				result = zero;
			if (curTransAmt == null)
				curTransAmt = zero;

			result = result.add(curTransAmt);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting getSTAgriPricipleReceivable");
			throw new KlsReportRuntimeException("Could not get getSTAgriPricipleReceivable");
		}
		logger.info("STAgriPricipleReceivable: " + result);
		logger.info("End: inside getSTAgriPricipleReceivable()");
		return result;
	}

	@Override
	public AccountingMoney getSTAgriPricipleReceivableTillDate(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getSTAgriPricipleReceivable()");
		AccountingMoney result = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(t.transactionAmount) from TransactionHistory t where ");
			if(pacsIdList.size()> 0)
				stringBuilder.append("t.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("t.transactionCode=:transactionCode and t.crDr=-1 and t.lineOfCredit.loanType=:loanType and t.businessDate>=:financYrStartDate and t.lineOfCredit.season.overdueDate<=:asOnDate");
/*			Query query = em
					.createQuery("select sum(t.transactionAmount) from TransactionHistory t where t.pacs.id="
							+ pacsId
							+ " and t.transactionCode=:transactionCode"
							+ " and t.crDr=-1 and t.lineOfCredit.loanType=:loanType and t.businessDate>=:financYrStartDate and t.lineOfCredit.season.overdueDate<=:asOnDate");*/
			Query query = em.createQuery(stringBuilder.toString());
			query.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL);
			query.setParameter("asOnDate", asOnDate);
			query.setParameter("financYrStartDate", financYrStartDate);
			query.setParameter("loanType", "C");
			if(pacsIdList.size()> 0)
				query.setParameter("pacsIdList", pacsIdList);
			if (!query.getResultList().isEmpty())
				result = (AccountingMoney) query.getResultList().get(0);
			StringBuilder stringBuilder2 = new StringBuilder();
			stringBuilder2.append("select sum(t.transactionAmount) from CurrentTransaction t where ");
			if(pacsIdList.size()> 0)
				stringBuilder2.append("t.pacs.id in (:pacsIdList) and ");
			stringBuilder2.append("t.transactionCode=:transactionCode and t.lineOfCredit.loanType=:loanType and t.businessDate>=:financYrStartDate and t.lineOfCredit.season.overdueDate<=:asOnDate");
			/*query = em
					.createQuery("select sum(t.transactionAmount) from CurrentTransaction t where t.pacs.id="
							+ pacsId
							+ " and t.transactionCode=:transactionCode"
							+ " and t.lineOfCredit.loanType=:loanType and t.businessDate>=:financYrStartDate and t.lineOfCredit.season.overdueDate<=:asOnDate");*/
		query = em.createQuery(stringBuilder2.toString());
			query.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL);
			query.setParameter("asOnDate", asOnDate);
			query.setParameter("financYrStartDate", financYrStartDate);
			query.setParameter("loanType", "C");
			if(pacsIdList.size()>0)
				query.setParameter("pacsIdList", pacsIdList);
			AccountingMoney curTransAmt = zero;
			if (!query.getResultList().isEmpty())
				curTransAmt = (AccountingMoney) query.getResultList().get(0);

			if (result == null)
				result = zero;
			if (curTransAmt == null)
				curTransAmt = zero;

			result = result.add(curTransAmt);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting getSTAgriPricipleReceivable");
			throw new KlsReportRuntimeException("Could not get getSTAgriPricipleReceivable");
		}
		logger.info("STAgriPricipleReceivable: " + result);
		logger.info("End: inside getSTAgriPricipleReceivable()");
		return result;
	}

	@Override
	public AccountingMoney getSTAgriPrincipleCollectionTotal(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getSTAgriPrincipleCollectionTotal()");
		AccountingMoney result = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(th.transactionAmount) from TransactionHistory th where ");
			if(pacsIdList.size()> 0)
				stringBuilder.append("th.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("th.transactionCode=:transactionCode and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate");
		/*	Query query = em.createQuery("select sum(th.transactionAmount) from TransactionHistory th where th.pacs.id=" + pacsId
					+ " and th.transactionCode=:transactionCode and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate"
					+ " and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate");*/
			Query query = em.createQuery(stringBuilder.toString());
			query.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL);
			query.setParameter("asOnDate", asOnDate);
			query.setParameter("loanType", "C");
			query.setParameter("financYrStartDate", financYrStartDate);
			if(pacsIdList.size()> 0)
				query.setParameter("pacsIdList", pacsIdList);
			if (!query.getResultList().isEmpty())
				result = (AccountingMoney) query.getResultList().get(0);
			
			StringBuilder stringBuilder2 = new StringBuilder();
			stringBuilder2.append("select sum(th.transactionAmount) from CurrentTransaction th where ");
			if(pacsIdList.size()> 0)
				stringBuilder2.append("th.pacs.id in (:pacsIdList) and ");
			stringBuilder2.append("th.transactionCode=:transactionCode and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate");
			/*query = em.createQuery("select sum(th.transactionAmount) from CurrentTransaction th where th.pacs.id=" + pacsId
					+ " and th.transactionCode=:transactionCode"
					+ " and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate");*/
			query = em.createQuery(stringBuilder2.toString());
			query.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL);
			query.setParameter("asOnDate", asOnDate);
			query.setParameter("loanType", "C");
			query.setParameter("financYrStartDate", financYrStartDate);
			if(pacsIdList.size()>0)
				query.setParameter("pacsIdList", pacsIdList);
			AccountingMoney curTransAmt = zero;
			if (!query.getResultList().isEmpty())
				curTransAmt = (AccountingMoney) query.getResultList().get(0);

			if (result == null)
				result = zero;
			if (curTransAmt == null)
				curTransAmt = zero;

			result = result.add(curTransAmt);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting STAgriPrincipleCollectionTotal");
			throw new KlsReportRuntimeException("Could not get STAgriPrincipleCollectionTotal");
		}
		logger.info("STAgriPrincipleCollectionTotal: " + result);
		logger.info("End: inside getSTAgriPrincipleCollectionTotal()");
		return result;
	}

	@Override
	public BigDecimal getSTAgriInterestReceivable(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getSTAgriPricipleReceivable()");
		BigDecimal result = BigDecimal.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(l.interestAccrued) from LineOfCredit l where ");
			if(pacsIdList.size()>0)
				stringBuilder.append("l.account.accountProperty.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("l.loanType=:loanType and l.season.overdueDate between :financYrStartDate and :asOnDate");
			
		/*	Query query = em.createQuery("select sum(l.interestAccrued) from LineOfCredit l where l.account.accountProperty.pacs.id=" + pacsId
					+ " and l.loanType=:loanType and l.season.overdueDate between :financYrStartDate and :asOnDate");*/
			Query query = em.createQuery(stringBuilder.toString());
			query.setParameter("asOnDate", asOnDate);
			query.setParameter("financYrStartDate", financYrStartDate);
			query.setParameter("loanType", "C");
			if(pacsIdList.size()>0)
				query.setParameter("pacsIdList", pacsIdList);
			if (!query.getResultList().isEmpty())
				result = (BigDecimal) query.getResultList().get(0);
		} catch (NoResultException noresultException) {
			logger.info("getSTAgriInterestReceivable:" + result);
			return BigDecimal.ZERO;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting getSTAgriPricipleReceivable");
			throw new KlsReportRuntimeException("Could not get getSTAgriPricipleReceivable");
		}
		logger.info("STAgriPricipleReceivable: " + result);
		logger.info("End: inside getSTAgriPricipleReceivable()");
		return result == null ? BigDecimal.ZERO : result;
	}

	@Override
	public AccountingMoney getSTAgriIntCollectionTotal(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getSTAgriIntCollectionTotal()");
		AccountingMoney result = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(th.transactionAmount) from TransactionHistory th where ");
			if(pacsIdList.size()> 0)
				stringBuilder.append("th.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("th.transactionCode=:transactionCode and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate");
			/*Query query = em.createQuery("select sum(th.transactionAmount) from TransactionHistory th where th.pacs.id=" + pacsId
					+ " and th.transactionCode=:transactionCode"
					+ " and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate");*/
			Query query = em.createQuery(stringBuilder.toString());
			query.setParameter("transactionCode", TransactionCode.INTEREST);
			query.setParameter("asOnDate", asOnDate);
			query.setParameter("loanType", "C");
			query.setParameter("financYrStartDate", financYrStartDate);
			if(pacsIdList.size()>0)
				query.setParameter("pacsIdList", pacsIdList);
			if (!query.getResultList().isEmpty())
				result = (AccountingMoney) query.getResultList().get(0);
			
			StringBuilder stringBuilder2 = new StringBuilder();
			stringBuilder2.append("select sum(th.transactionAmount) from CurrentTransaction th where ");
			if(pacsIdList.size()> 0)
				stringBuilder2.append("th.pacs.id in (:pacsIdList) and ");
			stringBuilder2.append("th.transactionCode=:transactionCode and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate group by th.lineOfCredit.id");
			/*query = em
					.createQuery("select sum(th.transactionAmount) from CurrentTransaction th where th.pacs.id="
							+ pacsId
							+ " and th.transactionCode=:transactionCode"
							+ " and th.lineOfCredit.loanType=:loanType and th.crDr=1 and th.businessDate between :financYrStartDate and :asOnDate group by th.lineOfCredit.id");*/
			query = em.createQuery(stringBuilder2.toString());
			
			query.setParameter("transactionCode", TransactionCode.INTEREST);
			query.setParameter("asOnDate", asOnDate);
			query.setParameter("loanType", "C");
			query.setParameter("financYrStartDate", financYrStartDate);
			if(pacsIdList.size()>0)
				query.setParameter("pacsIdList",pacsIdList);
			AccountingMoney curTransAmt = zero;
			if (!query.getResultList().isEmpty())
				curTransAmt = (AccountingMoney) query.getResultList().get(0);

			if (result == null)
				result = zero;
			if (curTransAmt == null)
				curTransAmt = zero;

			result = result.add(curTransAmt);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting STAgriIntCollectionTotal");
			throw new KlsReportRuntimeException("Could not get STAgriIntCollectionTotal");
		}
		logger.info("STAgriIntCollectionTotal: " + result);
		logger.info("End: inside getSTAgriIntCollectionTotal()");
		return result;
	}

	@Override
	public Money getMTLTAgriPricipleReceivable(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getMTLTAgriPricipleReceivable()");
		Money result = Money.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(lpc.principalAmount) from LoanRepaymentSchedule lpc,Account a ,LineOfCredit l "
									+ "where ");
			if(pacsIdList.size()> 0)
				stringBuilder.append("a.accountProperty.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("l.account.id=a.id and l.id=lpc.loanRepaymentScheduleId.lineOfCreditId and lpc.installmentDate>=:financYrStartDate and lpc.installmentDate<:asOnDate");
/*			Query query = em
					.createQuery(
							"select sum(lpc.principalAmount) from LoanRepaymentSchedule lpc,Account a ,LineOfCredit l "
									+ "where a.accountProperty.pacs.id=:pacsId and l.account.id=a.id and l.id=lpc.loanRepaymentScheduleId.lineOfCreditId "
									+ "and lpc.installmentDate>=:financYrStartDate and lpc.installmentDate<:asOnDate")*/
			
			Query query = em.createQuery(stringBuilder.toString());
			//query.setParameter("pacsId", pacsId);
					query.setParameter("asOnDate", asOnDate);
					query.setParameter("financYrStartDate", financYrStartDate);
					if(pacsIdList.size()>0)
						query.setParameter("pacsIdList", pacsIdList);
			result = (Money) query.getSingleResult();

		} catch (NoResultException noresultException) {
			logger.info("getMTLTAgriPricipleReceivable:" + result);
			return Money.ZERO;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting getMTLTAgriPricipleReceivable");
			throw new KlsReportRuntimeException("Could not get getMTLTAgriPricipleReceivable");
		}
		logger.info("MTLTAgriPricipleReceivable: " + result);
		logger.info("End: inside getMTLTAgriPricipleReceivable()");
		return result == null ? Money.ZERO : result;
	}

	@Override
	public Money getMTLTAgriPricipleReceivableTillDate(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getMTLTAgriPricipleReceivable()");
		Money result = Money.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(lpc.principalAmount) from LoanRepaymentSchedule lpc,Account a ,LineOfCredit l where ");
			if(pacsIdList.size()> 0)
				stringBuilder.append("a.accountProperty.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("l.account.id=a.id and l.id=lpc.loanRepaymentScheduleId.lineOfCreditId and lpc.installmentDate>=:financYrStartDate and lpc.installmentDate<=:asOnDate");
			Query qry=em.createQuery(stringBuilder.toString());
			qry.setParameter("asOnDate", asOnDate).setParameter("financYrStartDate", financYrStartDate);
			if(pacsIdList.size()>0)
				qry.setParameter("pacsIdList", pacsIdList);
			result = (Money) qry.getSingleResult();
			
			
			/*result = (Money) em
					.createQuery(
							"select sum(lpc.principalAmount) from LoanRepaymentSchedule lpc,Account a ,LineOfCredit l "
									+ "where a.accountProperty.pacs.id=:pacsId and l.account.id=a.id and l.id=lpc.loanRepaymentScheduleId.lineOfCreditId "
									+ "and lpc.installmentDate>=:financYrStartDate and lpc.installmentDate<=:asOnDate")
					.setParameter("pacsId", pacsId).setParameter("asOnDate", asOnDate).setParameter("financYrStartDate", financYrStartDate)
					.getSingleResult();*/

		} catch (NoResultException noresultException) {
			logger.info("getMTLTAgriPricipleReceivableTillDate:" + result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting getMTLTAgriPricipleReceivable");
			throw new KlsReportRuntimeException("Could not get getMTLTAgriPricipleReceivable");
		}
		logger.info("MTLTAgriPricipleReceivable: " + result);
		logger.info("End: inside getMTLTAgriPricipleReceivable()");
		return result == null ? Money.ZERO : result;
	}

	@Override
	public BigDecimal getMTLTAgriPriciplePaid(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getMTLTAgriPriciplePaid()");
		Money result = Money.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(lc.principalPaid) from LoanRecovery lc where ");
			if(pacsIdList.size()> 0)
				stringBuilder.append("lc.loanLineOfCredit.account.accountProperty.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("lc.recoveredDate between :financYrStartDate and :asOnDate");
			Query qry=em.createQuery(stringBuilder.toString());
			qry.setParameter("financYrStartDate", financYrStartDate).setParameter("asOnDate", asOnDate);
			if(pacsIdList.size()>0)
				qry.setParameter("pacsIdList", pacsIdList);
			result = (Money)qry.getSingleResult();
		/*	result = (Money) em
					.createQuery(
							"select sum(lc.principalPaid) from LoanRecovery lc where lc.loanLineOfCredit.account.accountProperty.pacs.id=:pacsId and lc.recoveredDate between :financYrStartDate and :asOnDate")
					.setParameter("pacsId", pacsId).setParameter("financYrStartDate", financYrStartDate).setParameter("asOnDate", asOnDate)
					.getSingleResult();*/
		} catch (NoResultException noresultException) {
			logger.info("MTLTAgriPriciplePaid:" + result);
			return BigDecimal.ZERO;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting getMTLTAgriPriciplePaid");
			throw new KlsReportRuntimeException("Could not get getMTLTAgriPriciplePaid");
		}
		logger.info("MTLTAgriPriciplePaid: " + result);
		logger.info("End: inside getMTLTAgriPriciplePaid()");
		return result == null ? BigDecimal.ZERO : result.getAmount();
	}

	@Override
	public BigDecimal getMTLTAgriInterestReceivable(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getMTLTAgriInterestReceivable()");
		Money result = Money.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(lpc.interestAmount) from LoanRepaymentSchedule lpc,Account a ,LineOfCredit l where ");
			if(pacsIdList.size()> 0)
				stringBuilder.append("a.accountProperty.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("l.account.id=a.id and l.id=lpc.loanRepaymentScheduleId.lineOfCreditId and lpc.installmentDate>=:financYrStartDate and lpc.installmentDate<=:asOnDate");
			Query qry=em.createQuery(stringBuilder.toString());
			qry.setParameter("asOnDate", asOnDate).setParameter("financYrStartDate", financYrStartDate);
			if(pacsIdList.size()>0)
				qry.setParameter("pacsIdList", pacsIdList);
			result = (Money) qry.getSingleResult();
			/*result = (Money) em
					.createQuery(
							"select sum(lpc.interestAmount) from LoanRepaymentSchedule lpc,Account a ,LineOfCredit l "
									+ "where a.accountProperty.pacs.id=:pacsId and l.account.id=a.id and l.id=lpc.loanRepaymentScheduleId.lineOfCreditId "
									+ "and lpc.installmentDate>=:financYrStartDate and lpc.installmentDate<=:asOnDate")
					.setParameter("pacsId", pacsId).setParameter("asOnDate", asOnDate).setParameter("financYrStartDate", financYrStartDate)
					.getSingleResult();*/
		} catch (NoResultException noresultException) {
			logger.info("MTLTAgriInterestReceivable:" + BigDecimal.ZERO);
			return BigDecimal.ZERO;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting getMTLTAgriInterestReceivable");
			throw new KlsReportRuntimeException("Could not get getMTLTAgriInterestReceivable");
		}
		logger.info("MTLTAgriInterestReceivable: " + result);
		logger.info("End: inside getMTLTAgriInterestReceivable()");
		return result == null ? BigDecimal.ZERO : result.getAmount();
	}

	@Override
	public BigDecimal getMTLTAgriInterestPaid(Integer pacsId, Date asOnDate,Integer branchId) {
		logger.info("start: inside getMTLTAgriPriciplePaid()");
		Money result = Money.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			List<Integer> pacsIdList=new ArrayList<Integer>();
			pacsIdList=getPacsBasedOnBranchId(branchId);
			if(pacsId!=0)
				pacsIdList.add(pacsId);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select sum(lc.interestPaid) from LoanRecovery lc where ");
			if(pacsIdList.size()>0)
				stringBuilder.append("lc.loanLineOfCredit.account.accountProperty.pacs.id in (:pacsIdList) and ");
			stringBuilder.append("lc.recoveredDate between :financYrStartDate and :asOnDate");
			Query qry=em.createQuery(stringBuilder.toString());
			qry.setParameter("financYrStartDate", financYrStartDate).setParameter("asOnDate", asOnDate);
			if(pacsIdList.size()>0)
				qry.setParameter("pacsIdList", pacsIdList);
			result = (Money) qry.getSingleResult();
			/*result = (Money) em
					.createQuery(
							"select sum(lc.interestPaid) from LoanRecovery lc where lc.loanLineOfCredit.account.accountProperty.pacs.id=:pacsId and lc.recoveredDate between :financYrStartDate and :asOnDate")
					.setParameter("pacsId", pacsId).setParameter("financYrStartDate", financYrStartDate).setParameter("asOnDate", asOnDate)
					.getSingleResult();*/
		} catch (NoResultException noresultException) {
			logger.info("MTLTAgriInterestPaid:" + result);
			return BigDecimal.ZERO;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while getting getMTLTAgriInterestPaid");
			throw new KlsReportRuntimeException("Could not get getMTLTAgriInterestPaid");
		}
		logger.info("MTLTAgriInterestPaid: " + result);
		logger.info("End: inside getMTLTAgriInterestPaid()");
		return result != null ? result.getAmount() : BigDecimal.ZERO;
	}
private List<Integer> getPacsBasedOnBranchId(Integer branchId){
	logger.info("start: inside getPacsBasedOnBranchId()");
	List<Integer> pacsIdList=new ArrayList<Integer>();
	try {
		EntityManager em = EntityManagerUtil.getEntityManager();
	if(branchId!=0)
	{
		Query qry=em.createQuery("select p.id from Pacs p where p.branch.id="+branchId);
		pacsIdList=qry.getResultList();
	}
	}catch (Exception e) {
		e.printStackTrace();
		logger.error("Error while getting getMTLTAgriInterestPaid");
		throw new KlsReportRuntimeException("Could not get getMTLTAgriInterestPaid");
	}
	logger.info("End: inside getPacsBasedOnBranchId()");
	return pacsIdList;
}
}
