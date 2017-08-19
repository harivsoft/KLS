package com.vosftcorp.kls.report.dao.dcb.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.dao.dcb.IDCBReportDAO;
import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.util.DCBReportUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.time.Date;

/**
 * @author a1605
 */

public class DCBReportDAO implements IDCBReportDAO {
	private static final Logger logger = Logger.getLogger(DCBReportDAO.class);

	@Override
	public List<Map> getDCBReport(Integer branchId, Integer pacId, Integer schemeId,
			Long customerId, Date fromDate, Date toDate) {
		logger.info("Start :inside getDCBReport()");
		List<Map> dcbReportList = new ArrayList<Map>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder collectionReportQuery = new StringBuilder();
			collectionReportQuery
					.append("select distinct new Map(p.branch as branch,p as pacs,l.scheme as scheme,l.customerId as customerId,a.accountNumber as accNo,l.product.asAndWhenImplemented as asOnWhen,l.interestAccrued as interestAccrued) from Pacs p,Branch b,LineOfCredit l,Account a,AccountProperty ap where ");
			if (branchId != 0)
				collectionReportQuery.append("p.branch.id=" + branchId + " and ");
			if (pacId != 0)
				collectionReportQuery.append("p.id=" + pacId+" and ap.pacs.id="+pacId+" and ");
			if (customerId != 0)
				collectionReportQuery.append("l.customerId=" + customerId+ " and ");
			if (schemeId != 0)
				collectionReportQuery.append("l.scheme.id=" + schemeId+" and ");
			collectionReportQuery.append("l.lineOfCreditStatus=:lineOfCreditStatus");
			collectionReportQuery.append(" and l.isFirstDrawal=1");
			collectionReportQuery.append(" and l.account.id=a.id");
            collectionReportQuery.append(" and ap.id=a.accountProperty.id");
			collectionReportQuery
					.append(" group by p.branch.id,p.id,l.scheme.id,l.customerId,a.accountNumber,l.product.asAndWhenImplemented,l.interestAccrued");
			Query query = em.createQuery(collectionReportQuery.toString());
			query.setParameter("lineOfCreditStatus", LineOfCreditStatus.Active);
			dcbReportList = (List<Map>) query.getResultList();
		} catch (NoResultException noExcp) {
			logger.error("Error :No records found for dcb report");
			throw new KlsReportRuntimeException("Data not found for DCB report", noExcp.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :inside getDCBReport()");
			throw new KlsReportRuntimeException("Error while getting DCB report:", e.getCause());

		}
		logger.info("End: getDCB report" + dcbReportList);
		return dcbReportList;
	}

	@Override
	public AccountingMoney getPreviousPrincipalOverdueDemand(Long accountId, Date fromDate,
			Integer pacId, Integer schemeId) {
		AccountingMoney previousPrincipalOverdueDemand = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			//String queryString = "select sum((th.openingBalance*th.crDr)+th.transactionAmount) from TransactionHistory th where th.id in (select tr.id from TransactionHistory tr where tr.id in (select max(t.id) from TransactionHistory t where t.account.id="
			//		+ accountId
			//		+ " and t.pacs.id="
			//		+ pacId
			//		+ " and t.lineOfCredit.scheme.id="
			//		+ schemeId
			//		+ " and t.businessDate<=:previousDate"
			//		+ " and t.transactionCode=:transactionCode"
			//		+ " and t.lineOfCredit.season.overdueDate<:fromDate"
			//		+ " and t.lineOfCredit.lineOfCreditStatus=:lineOfCreditStatus group by t.lineOfCredit.id))";
			String queryString="select sum(currentBalance) from LineOfCredit l where l.account.id="+ accountId+" and l.account.accountProperty.pacs.id="+ pacId+ " and l.scheme.id="+ schemeId+ " and l.loanExpiryDate<:fromDate and l.lineOfCreditStatus=:lineOfCreditStatus";
			previousPrincipalOverdueDemand = (AccountingMoney)em.createQuery(queryString)
					.setParameter("fromDate", fromDate)
					.setParameter("lineOfCreditStatus", LineOfCreditStatus.Active)
					.getSingleResult();
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException(
					"Error while getting getPreviousPrincipalOverdueDemand:", exception.getCause());
		}
		logger.info("End:Inside getAccountBalance method toDate:" + previousPrincipalOverdueDemand);
		return previousPrincipalOverdueDemand == null ? AccountingMoney.ZERO
				: previousPrincipalOverdueDemand;
	}

	@Override
	public AccountingMoney getPreviousInterestOverdueDemand(Long accountId, Date fromDate,
			Integer pacId, Integer schemeId) {
		AccountingMoney previousInterestOverdueDemand = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select case when (sum(th.transactionAmount)) is not null then sum(th.transactionAmount) else 0 end-(select case when (sum(t.transactionAmount)) is not null then sum(t.transactionAmount) else 0 end from TransactionHistory t where t.account.id="
					+ accountId
					+ " and t.pacs.id="
					+ pacId
					+ " and t.lineOfCredit.scheme.id="
					+ schemeId
					+ " and t.businessDate<=:previousDate"
					+ " and t.transactionCode=:crtransactionCode and t.crDr=1 and t.lineOfCredit.lineOfCreditStatus=:lineOfCreditStatus and t.lineOfCredit.loanExpiryDate<:fromDate) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+ " and th.businessDate<=:previousDate"
					+ " and th.transactionCode=:transactionCode and th.crDr=-1 and th.lineOfCredit.lineOfCreditStatus=:lineOfCreditStatus and th.lineOfCredit.loanExpiryDate<:fromDate";

			previousInterestOverdueDemand = (AccountingMoney) em.createQuery(queryString)
					.setParameter("transactionCode", TransactionCode.INTEREST_RECEIVABLE)
					.setParameter("previousDate", fromDate.previous())
					.setParameter("fromDate", fromDate)
					.setParameter("lineOfCreditStatus", LineOfCreditStatus.Active).setParameter("crtransactionCode", TransactionCode.INTEREST)
					.getSingleResult();
			if (previousInterestOverdueDemand != null
					&& previousInterestOverdueDemand.compareTo(AccountingMoney.ZERO) == -1)
				return AccountingMoney.ZERO;
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException(
					"Error while getting getPreviousPrincipalOverdueDemand:", exception.getCause());
		}
		logger.info("End:Inside getAccountBalance method toDate:" + previousInterestOverdueDemand);
		return previousInterestOverdueDemand == null ? AccountingMoney.ZERO
				: previousInterestOverdueDemand;
	}

	@Override
	public AccountingMoney getCurrentPrincipalDemand(Long accountId, Date fromDate, Date toDate,
			Integer demandFreequency, Integer pacId, Integer schemeId) {
		logger.info("Start:Inside getCurrentPrincipalDemand method");
		AccountingMoney currentPrincipalDemand = AccountingMoney.ZERO;
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		AccountingMoney currentPrincipalDemandDuringFreequency=AccountingMoney.ZERO;
		try {
			List<TransactionCode> transactionCode=new ArrayList<TransactionCode>();
			transactionCode.add(TransactionCode.PRINCIPAL_BAL);
			transactionCode.add(TransactionCode.SHARE_TRANSFER);
			transactionCode.add(TransactionCode.INSURANCE_DEDUCTION);
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select case when (sum(ct.transactionAmount)) is not null then sum(ct.transactionAmount) else 0 end+(select case when (sum(th.transactionAmount)) is not null then sum(th.transactionAmount) else 0 end from TransactionHistory th where th.account.id=:accountId and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+ " and th.crDr=-1 and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.lineOfCredit.loanExpiryDate>=:fromDate and th.transactionCode in (:transactionCode)) from CurrentTransaction ct where ct.account.id=:accountId and ct.pacs.id="
					+ pacId
					+ " and ct.lineOfCredit.scheme.id="
					+ schemeId
					+ " and ct.crDr=-1 and ct.businessDate>=:fromDate and ct.businessDate<=:toDate and ct.lineOfCredit.loanExpiryDate>=:fromDate and ct.lineOfCredit.loanExpiryDate<=:toDate and ct.transactionCode in (:transactionCode)";
			Query query = em.createQuery(queryString);
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
			query.setParameter("accountId", accountId);
			query.setParameter("transactionCode", transactionCode);
			currentPrincipalDemand = (AccountingMoney) query.getSingleResult();
           if(toDate.compareTo(freequencyDate)<1){
			queryString = "select case when (sum(ct.transactionAmount)) is not null then sum(ct.transactionAmount) else 0 end+(select case when (sum(th.transactionAmount)) is not null then sum(th.transactionAmount) else 0 end from TransactionHistory th where th.account.id=:accountId and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+ " and th.crDr=-1 and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.lineOfCredit.loanExpiryDate>=:fromDate and th.transactionCode in (:transactionCode)) from CurrentTransaction ct where ct.account.id=:accountId and ct.pacs.id="
					+ pacId
					+ " and ct.lineOfCredit.scheme.id="
					+ schemeId
					+ " and ct.crDr=-1 and ct.businessDate>=:fromDate and ct.businessDate<=:toDate and ct.lineOfCredit.loanExpiryDate>=:fromDate and ct.lineOfCredit.loanExpiryDate<=:toDate and ct.transactionCode in (:transactionCode)";

			query = em.createQuery(queryString);
			query.setParameter("fromDate", toDate.next());
			query.setParameter("toDate", freequencyDate);
			query.setParameter("accountId", accountId);
			query.setParameter("transactionCode", transactionCode);
			currentPrincipalDemandDuringFreequency = (AccountingMoney) query
					.getSingleResult();
            }
			if (currentPrincipalDemand != null)
				currentPrincipalDemand = currentPrincipalDemand
						.add(currentPrincipalDemandDuringFreequency != null ? currentPrincipalDemandDuringFreequency
								: AccountingMoney.ZERO);
			else
				currentPrincipalDemand = currentPrincipalDemandDuringFreequency;
		} catch (NoResultException noExcp) {
			return currentPrincipalDemand;
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error :Inside getCurrentPrincipalDemand method");
			throw new KlsReportRuntimeException(
					"Error while getting getPreviousPrincipalOverdueDemand:", exception.getCause());
		}
		logger.info("End:Inside getCurrentPrincipalDemand method :" + currentPrincipalDemand);
		return currentPrincipalDemand != null ? currentPrincipalDemand : AccountingMoney.ZERO;
	}

	@Override
	public AccountingMoney getCurrentInterestDemand(Long accountId, Date fromDate, Date toDate,
			Integer demandFreequency, Integer pacId, Integer schemeId) {
		logger.info("Start:Inside getCurrentInterestDemand method");
		AccountingMoney currentInterestDemand = AccountingMoney.ZERO;
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		AccountingMoney currentInterestDemandDuringFreequency=AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select case when (sum(ct.transactionAmount)) is not null then sum(ct.transactionAmount) else 0 end+(select case when (sum(th.transactionAmount)) is not null then sum(th.transactionAmount) else 0 end from TransactionHistory th where th.account.id=:accountId and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+ " and th.crDr=-1 and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.lineOfCredit.loanExpiryDate>=:fromDate and th.transactionCode=:transactionCode) from CurrentTransaction ct where ct.account.id=:accountId and ct.pacs.id="
					+ pacId
					+ " and ct.lineOfCredit.scheme.id="
					+ schemeId
					+ " and ct.crDr=-1 and ct.businessDate>=:fromDate and ct.businessDate<=:toDate and ct.lineOfCredit.loanExpiryDate>=:fromDate and ct.lineOfCredit.loanExpiryDate<=:toDate and ct.transactionCode=:transactionCode";
			Query query = em.createQuery(queryString);
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
			query.setParameter("accountId", accountId);
			query.setParameter("transactionCode", TransactionCode.INTEREST_RECEIVABLE);
			currentInterestDemand = (AccountingMoney) query.getSingleResult();
            if(toDate.compareTo(freequencyDate)<1){
			queryString = "select case when (sum(ct.transactionAmount)) is not null then sum(ct.transactionAmount) else 0 end+(select case when (sum(th.transactionAmount)) is not null then sum(th.transactionAmount) else 0 end from TransactionHistory th where th.account.id=:accountId and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+ " and th.crDr=-1 and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.lineOfCredit.loanExpiryDate>=:fromDate and th.transactionCode=:transactionCode) from CurrentTransaction ct where ct.account.id=:accountId and ct.pacs.id="
					+ pacId
					+ " and ct.lineOfCredit.scheme.id="
					+ schemeId
					+ " and ct.crDr=-1 and ct.businessDate>=:fromDate and ct.businessDate<=:toDate and ct.lineOfCredit.loanExpiryDate>=:fromDate and ct.lineOfCredit.loanExpiryDate<=:toDate and ct.transactionCode=:transactionCode";
			query = em.createQuery(queryString);
			query.setParameter("fromDate", toDate.next());
			query.setParameter("toDate", freequencyDate);
			query.setParameter("accountId", accountId);
			query.setParameter("transactionCode", TransactionCode.INTEREST_RECEIVABLE);
			currentInterestDemandDuringFreequency = (AccountingMoney) query
					.getSingleResult();
            }
			if (currentInterestDemand != null)
				currentInterestDemand = currentInterestDemand
						.add(currentInterestDemandDuringFreequency != null ? currentInterestDemandDuringFreequency
								: AccountingMoney.ZERO);
			else
				currentInterestDemand = currentInterestDemandDuringFreequency;
		} catch (NoResultException noExcp) {
			return currentInterestDemand;
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error :Inside getCurrentInterestDemand method");
			throw new KlsReportRuntimeException("Error while getting currentInterestDemand:",
					exception.getCause());
		}
		logger.info("End:Inside getCurrentInterestDemand method :" + currentInterestDemand);
		return currentInterestDemand != null ? currentInterestDemand : AccountingMoney.ZERO;
	}

	@Override
	public AccountingMoney getOverduePrincipleCollection(Long accountId, Date fromDate,
			Date toDate, Integer pacId, Integer schemeId,Integer demandFreequency) {
		logger.info("Start: inside getOverduePrincipleCollection method ");
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		AccountingMoney overduePrincipleCollection = AccountingMoney.ZERO;
		AccountingMoney overduePrincipleCollectionDmdFrequency=AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+" and th.lineOfCredit.loanExpiryDate<=:previousDate"
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";
			overduePrincipleCollection = (AccountingMoney) em.createQuery(queryString)
					.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL)
					.setParameter("toDate", toDate).setParameter("fromDate", fromDate).setParameter("previousDate", fromDate.previous())
					.getSingleResult();
			if(toDate.compareTo(freequencyDate)<1){
				String queryString1 = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
						+ accountId
						+ " and th.pacs.id="
						+ pacId
						+ " and th.lineOfCredit.scheme.id="
						+ schemeId
						+" and th.lineOfCredit.loanExpiryDate<=:previousDate"
						+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";
				overduePrincipleCollectionDmdFrequency = (AccountingMoney) em.createQuery(queryString1)
						.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL)
						.setParameter("toDate", freequencyDate).setParameter("fromDate", toDate.next()).setParameter("previousDate", fromDate.previous())
						.getSingleResult();
			}
			if (overduePrincipleCollection != null)
				overduePrincipleCollection = overduePrincipleCollection
				.add(overduePrincipleCollectionDmdFrequency != null ? overduePrincipleCollectionDmdFrequency
						: AccountingMoney.ZERO);
	   else
		overduePrincipleCollection = overduePrincipleCollectionDmdFrequency;
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting OverduePrincipleCollection:",
					exception.getCause());
		}
		logger.info("End:Inside getAccountBalance method toDate:" + overduePrincipleCollection);
		return overduePrincipleCollection == null ? AccountingMoney.ZERO
				: overduePrincipleCollection;
	}

	@Override
	public AccountingMoney getOverdueInterestCollection(Long accountId, Date fromDate, Date toDate,
			Integer pacId, Integer schemeId,Integer demandFreequency) {
		logger.info("Start: inside getOverdueInterestCollection");
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		AccountingMoney overdueInterestCollection = AccountingMoney.ZERO;
		AccountingMoney overdueInterestCollectionDmdFrequency = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+" and th.lineOfCredit.loanExpiryDate<=:previousDate"
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";

			overdueInterestCollection = (AccountingMoney) em.createQuery(queryString)
					.setParameter("transactionCode", TransactionCode.INTEREST)
					.setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("previousDate", fromDate.previous())
					.getSingleResult();
			
			if(toDate.compareTo(freequencyDate)<1){
			String queryString1 = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+" and th.lineOfCredit.loanExpiryDate<=:previousDate"
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";

			overdueInterestCollectionDmdFrequency = (AccountingMoney) em.createQuery(queryString1)
					.setParameter("transactionCode", TransactionCode.INTEREST)
					.setParameter("fromDate", toDate.next()).setParameter("toDate", freequencyDate).setParameter("previousDate", fromDate.previous())
					.getSingleResult();
			}
			if (overdueInterestCollection != null)
				overdueInterestCollection = overdueInterestCollection
				.add(overdueInterestCollectionDmdFrequency != null ? overdueInterestCollectionDmdFrequency
						: AccountingMoney.ZERO);
	   else
		   overdueInterestCollection = overdueInterestCollectionDmdFrequency;
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting OverdueInterestCollection:",
					exception.getCause());
		}
		logger.info("End:Inside getOverdueInterestCollection method");
		return overdueInterestCollection == null ? AccountingMoney.ZERO : overdueInterestCollection;
	}
	@Override
	public AccountingMoney getAdvancePrincipleCollection(Long accountId, Date fromDate,
			Date toDate, Integer pacId, Integer schemeId,Integer demandFreequency) {
		logger.info("Start: inside getOverduePrincipleCollection method ");
		AccountingMoney overduePrincipleCollection = AccountingMoney.ZERO;
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		AccountingMoney overduePrincipleCollectionDmdFreequency = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+" and th.lineOfCredit.loanExpiryDate>:toDate"
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";
			overduePrincipleCollection = (AccountingMoney) em.createQuery(queryString)
					.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL)
					.setParameter("toDate", toDate).setParameter("fromDate", fromDate)
					.getSingleResult();
			
			if(toDate.compareTo(freequencyDate)<1){
				String queryString1 = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
						+ accountId
						+ " and th.pacs.id="
						+ pacId
						+ " and th.lineOfCredit.scheme.id="
						+ schemeId
						+" and th.lineOfCredit.loanExpiryDate>:frequencyDate"
						+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";
				overduePrincipleCollectionDmdFreequency = (AccountingMoney) em.createQuery(queryString1)
						.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL)
						.setParameter("toDate", freequencyDate).setParameter("fromDate", toDate.next()).setParameter("frequencyDate", freequencyDate)
						.getSingleResult();
			}
			
			if (overduePrincipleCollection != null)
				overduePrincipleCollection = overduePrincipleCollection
				.add(overduePrincipleCollectionDmdFreequency != null ? overduePrincipleCollectionDmdFreequency
						: AccountingMoney.ZERO);
	   else
		   overduePrincipleCollection = overduePrincipleCollectionDmdFreequency;
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting OverduePrincipleCollection:",
					exception.getCause());
		}
		logger.info("End:Inside getAccountBalance method toDate:" + overduePrincipleCollection);
		return overduePrincipleCollection == null ? AccountingMoney.ZERO
				: overduePrincipleCollection;
	}

	@Override
	public AccountingMoney getAdvanceInterestCollection(Long accountId, Date fromDate, Date toDate,
			Integer pacId, Integer schemeId,Integer demandFreequency) {
		logger.info("Start: inside getOverdueInterestCollection");
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		AccountingMoney overdueInterestCollection = AccountingMoney.ZERO;
		AccountingMoney overdueInterestCollectionDmdFrequency = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+" and th.lineOfCredit.loanExpiryDate>:toDate"
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";

			overdueInterestCollection = (AccountingMoney) em.createQuery(queryString)
					.setParameter("transactionCode", TransactionCode.INTEREST)
					.setParameter("fromDate", fromDate).setParameter("toDate", toDate)
					.getSingleResult();
			if(toDate.compareTo(freequencyDate)<1){
				String queryString1 = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
						+ accountId
						+ " and th.pacs.id="
						+ pacId
						+ " and th.lineOfCredit.scheme.id="
						+ schemeId
						+" and th.lineOfCredit.loanExpiryDate>:frequencyDate"
						+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";

				overdueInterestCollection = (AccountingMoney) em.createQuery(queryString1)
						.setParameter("transactionCode", TransactionCode.INTEREST)
						.setParameter("fromDate", toDate.next()).setParameter("toDate", freequencyDate).setParameter("frequencyDate", freequencyDate)
						.getSingleResult();	
			}
			if (overdueInterestCollection != null)
				overdueInterestCollection = overdueInterestCollection
				.add(overdueInterestCollectionDmdFrequency != null ? overdueInterestCollectionDmdFrequency
						: AccountingMoney.ZERO);
	   else
		   overdueInterestCollection = overdueInterestCollectionDmdFrequency;
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting OverdueInterestCollection:",
					exception.getCause());
		}
		logger.info("End:Inside getOverdueInterestCollection method");
		return overdueInterestCollection == null ? AccountingMoney.ZERO : overdueInterestCollection;
	}
	@Override
	public AccountingMoney getTotalPrincipleCollection(Long accountId, Date fromDate,
			Date toDate, Integer pacId, Integer schemeId,Integer demandFreequency) {
		logger.info("Start: inside getOverduePrincipleCollection method ");
		AccountingMoney overduePrincipleCollection = AccountingMoney.ZERO;
		AccountingMoney overduePrincipleCollectionDmdFrequency = AccountingMoney.ZERO;
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";
			overduePrincipleCollection = (AccountingMoney) em.createQuery(queryString)
					.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL)
					.setParameter("toDate", toDate).setParameter("fromDate", fromDate)
					.getSingleResult();
			if(toDate.compareTo(freequencyDate)<1){
				String queryString1 = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
						+ accountId
						+ " and th.pacs.id="
						+ pacId
						+ " and th.lineOfCredit.scheme.id="
						+ schemeId
						+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";
				overduePrincipleCollectionDmdFrequency = (AccountingMoney) em.createQuery(queryString1)
						.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL)
						.setParameter("toDate", freequencyDate).setParameter("fromDate", toDate.next()).getSingleResult();
			}
			if (overduePrincipleCollection != null)
				overduePrincipleCollection = overduePrincipleCollection
				.add(overduePrincipleCollectionDmdFrequency != null ? overduePrincipleCollectionDmdFrequency
						: AccountingMoney.ZERO);
	   else
		   overduePrincipleCollection = overduePrincipleCollectionDmdFrequency;
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting OverduePrincipleCollection:",
					exception.getCause());
		}
		logger.info("End:Inside getAccountBalance method toDate:" + overduePrincipleCollection);
		return overduePrincipleCollection == null ? AccountingMoney.ZERO
				: overduePrincipleCollection;
	}
	@Override
	public AccountingMoney getTotalInterestCollection(Long accountId, Date fromDate, Date toDate,
			Integer pacId, Integer schemeId,Integer demandFreequency) {
		logger.info("Start: inside getOverdueInterestCollection");
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		AccountingMoney overdueInterestCollection = AccountingMoney.ZERO;
		AccountingMoney overdueInterestCollectionDmdFrequency = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";

			overdueInterestCollection = (AccountingMoney) em.createQuery(queryString)
					.setParameter("transactionCode", TransactionCode.INTEREST)
					.setParameter("fromDate", fromDate).setParameter("toDate", toDate)
					.getSingleResult();
			
			if(toDate.compareTo(freequencyDate)<1){
			String queryString1 = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";

			overdueInterestCollectionDmdFrequency = (AccountingMoney) em.createQuery(queryString1)
					.setParameter("transactionCode", TransactionCode.INTEREST)
					.setParameter("fromDate", toDate.next()).setParameter("toDate", freequencyDate)
					.getSingleResult();
			}
			if (overdueInterestCollection != null)
				overdueInterestCollection = overdueInterestCollection
				.add(overdueInterestCollectionDmdFrequency != null ? overdueInterestCollectionDmdFrequency
						: AccountingMoney.ZERO);
	   else
		   overdueInterestCollection = overdueInterestCollectionDmdFrequency;
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting OverdueInterestCollection:",
					exception.getCause());
		}
		logger.info("End:Inside getOverdueInterestCollection method");
		return overdueInterestCollection == null ? AccountingMoney.ZERO : overdueInterestCollection;
	}
	
	@Override
	public AccountingMoney getOverduePrincipleAsOnFromDate(Long accountId, Date fromDate,
			Date toDate, Integer pacId, Integer schemeId,Integer demandFreequency) {
		logger.info("Start: inside getOverduePrincipleCollection method ");
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		AccountingMoney overduePrincipleCollection = AccountingMoney.ZERO;
		AccountingMoney overduePrincipleCollectionDmdFrequency=AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+" and th.lineOfCredit.loanExpiryDate<=:fromDate"
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";
			overduePrincipleCollection = (AccountingMoney) em.createQuery(queryString)
					.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL)
					.setParameter("toDate", toDate).setParameter("fromDate", fromDate)
					.getSingleResult();
			if(toDate.compareTo(freequencyDate)<1){
				String queryString1 = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
						+ accountId
						+ " and th.pacs.id="
						+ pacId
						+ " and th.lineOfCredit.scheme.id="
						+ schemeId
						+" and th.lineOfCredit.loanExpiryDate<=:fromDate"
						+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";
				overduePrincipleCollectionDmdFrequency = (AccountingMoney) em.createQuery(queryString1)
						.setParameter("transactionCode", TransactionCode.PRINCIPAL_BAL)
						.setParameter("toDate", freequencyDate).setParameter("fromDate", toDate.next())
						.getSingleResult();
			}
			if (overduePrincipleCollection != null)
				overduePrincipleCollection = overduePrincipleCollection
				.add(overduePrincipleCollectionDmdFrequency != null ? overduePrincipleCollectionDmdFrequency
						: AccountingMoney.ZERO);
	   else
		overduePrincipleCollection = overduePrincipleCollectionDmdFrequency;
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting OverduePrincipleCollection:",
					exception.getCause());
		}
		logger.info("End:Inside getAccountBalance method toDate:" + overduePrincipleCollection);
		return overduePrincipleCollection == null ? AccountingMoney.ZERO
				: overduePrincipleCollection;
	}

	@Override
	public AccountingMoney getOverdueInterestAsOnFromDate(Long accountId, Date fromDate, Date toDate,
			Integer pacId, Integer schemeId,Integer demandFreequency) {
		logger.info("Start: inside getOverdueInterestCollection");
		Date freequencyDate = DCBReportUtil.getDateByFreequncyPeroid(fromDate, demandFreequency);
		AccountingMoney overdueInterestCollection = AccountingMoney.ZERO;
		AccountingMoney overdueInterestCollectionDmdFrequency = AccountingMoney.ZERO;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			String queryString = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+" and th.lineOfCredit.loanExpiryDate<=:fromDate"
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";

			overdueInterestCollection = (AccountingMoney) em.createQuery(queryString)
					.setParameter("transactionCode", TransactionCode.INTEREST)
					.setParameter("fromDate", fromDate).setParameter("toDate", toDate).getSingleResult();
			
			if(toDate.compareTo(freequencyDate)<1){
			String queryString1 = "select sum(th.transactionAmount) from TransactionHistory th where th.account.id="
					+ accountId
					+ " and th.pacs.id="
					+ pacId
					+ " and th.lineOfCredit.scheme.id="
					+ schemeId
					+" and th.lineOfCredit.loanExpiryDate<=:fromDate"
					+ " and th.businessDate>=:fromDate and th.businessDate<=:toDate and th.transactionCode=:transactionCode and th.crDr=1";

			overdueInterestCollectionDmdFrequency = (AccountingMoney) em.createQuery(queryString1)
					.setParameter("transactionCode", TransactionCode.INTEREST)
					.setParameter("fromDate", toDate.next()).setParameter("toDate", freequencyDate)
					.getSingleResult();
			}
			if (overdueInterestCollection != null)
				overdueInterestCollection = overdueInterestCollection
				.add(overdueInterestCollectionDmdFrequency != null ? overdueInterestCollectionDmdFrequency
						: AccountingMoney.ZERO);
	   else
		   overdueInterestCollection = overdueInterestCollectionDmdFrequency;
		} catch (NoResultException noExcp) {
			return AccountingMoney.ZERO;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new KlsReportRuntimeException("Error while getting OverdueInterestCollection:",
					exception.getCause());
		}
		logger.info("End:Inside getOverdueInterestCollection method");
		return overdueInterestCollection == null ? AccountingMoney.ZERO : overdueInterestCollection;
	}

}
