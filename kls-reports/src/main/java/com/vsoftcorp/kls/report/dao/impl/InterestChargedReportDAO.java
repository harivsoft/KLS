package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.IInterestChargedReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class InterestChargedReportDAO implements IInterestChargedReportDAO {
	private static final Logger logger = Logger.getLogger(InterestChargedReportDAO.class);

	@Override
	public List<Object[]> getInterestChargedDataReport(Integer pacsId,
			Integer productId, Integer purposeId, Integer branchId,
			String productType, String fromDateString, String toDateString) {
		
		List<Object[]> interestChargedReportList = new ArrayList<Object[]>();
		logger.info("Start:Inside getInterestChargedDataReport method");
		
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("(select distinct p.member_number,p.first_name,th.opening_balance as outstanding,p.member_number as interest, p.member_number as balance,th.line_of_credit_id,pl.product_id,pl.purpose_id from kls_schema.current_transaction th, kls_schema.line_of_credit loc,kls_schema.pacs_loan_application pl,kls_schema.product_type pt ,kls_schema.purpose pur,kls_schema.product prod,membership.person p,kls_schema.pacs pac"
					+ " where th.tran_code=7 and th.crdr=-1 and th.business_date"
					+ " >= '"+DateUtil.getVSoftDateByString(fromDateString)+"' and business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"' and loc.id=th.line_of_credit_id and pl.id=loc.pacs_loan_application_id and p.party_id=pl.customer_id and loc.product_id=prod.id and prod.product_type_id=pt.id ");
			if(productType != null && !productType.equals("0")) {
				stringBuilder.append(" and pt.loan_type = '" + productType+"'");
			}
			if(productId != null && productId != 0) {
				stringBuilder.append(" and loc.product_id = " + productId);
			}
			if(pacsId != null & pacsId != 0) {
				stringBuilder.append(" and p.pacs_id = " + pacsId);
			}
			if(purposeId != null && purposeId != 0) {
				stringBuilder.append(" and pl.purpose_id = " + purposeId);
			}
			if(branchId != 0)
				stringBuilder.append(" and pac.branch_id = "+branchId);
			stringBuilder.append(" order by p.first_name,product_id, purpose_id) union ");
			stringBuilder.append("(select distinct p.member_number,p.first_name,th.opening_balance as outstanding,p.member_number as interest, p.member_number as balance,th.line_of_credit_id,pl.product_id,pl.purpose_id from kls_schema.transaction_history th, kls_schema.line_of_credit loc,kls_schema.pacs_loan_application pl,kls_schema.product_type pt ,kls_schema.purpose pur,kls_schema.product prod,membership.person p,kls_schema.pacs pac"
					+ " where th.tran_code=7 and th.crdr=-1 and th.business_date"
					+ " >= '"+DateUtil.getVSoftDateByString(fromDateString)+"' and business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"' and loc.id=th.line_of_credit_id and pl.id=loc.pacs_loan_application_id and p.party_id=pl.customer_id and loc.product_id=prod.id and prod.product_type_id=pt.id ");
			if(productType != null && !productType.equals("0")) {
				stringBuilder.append(" and pt.loan_type = '" + productType+"'");
			}
			if(productId != null && productId != 0) {
				stringBuilder.append(" and loc.product_id = " + productId);
			}
			if(pacsId != null & pacsId != 0) {
				stringBuilder.append(" and p.pacs_id = " + pacsId);
			}
			if(purposeId != null && purposeId != 0) {
				stringBuilder.append(" and pl.purpose_id = " + purposeId);
			}
			if(branchId != 0)
				stringBuilder.append(" and pac.branch_id = "+branchId);
			stringBuilder.append(" order by p.first_name,product_id, purpose_id) ");
			System.out.println("Interest Charged Report Query: "+stringBuilder.toString());
			interestChargedReportList = em.createNativeQuery(
					stringBuilder.toString()).getResultList();
			logger.info("loanLedgerReportList---" + interestChargedReportList.size());
		}catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMTLTLoanLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print MTLTLoanLedger Teport:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return interestChargedReportList;
	}

	@Override
	public BigDecimal getInterestAmountBasedOnLocId(BigInteger locId, String fromDateString, String toDateString) {
		BigDecimal val = BigDecimal.ZERO;
		try {
			List<Object> list = new ArrayList<>();
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("(select sum(amount) from ((select sum(th1.trans_amt) amount from kls_schema.current_transaction th1 where th1.line_of_credit_id = "+locId+" and th1.business_date >= '"+DateUtil.getVSoftDateByString(fromDateString)+"' and th1.business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"' and  th1.tran_code=7) union (select sum(th1.trans_amt) amount from kls_schema.transaction_history th1 where th1.line_of_credit_id = "+locId+" and th1.business_date >= '"+DateUtil.getVSoftDateByString(fromDateString)+"' and th1.business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"' and  th1.tran_code=7)))");
			list = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("list size : "+list.size());
			if(list.size()>0)
			val = (BigDecimal) list.get(0);
			 logger.info("val=========="+val);
		}catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMTLTLoanLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print MTLTLoanLedger Teport:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return val;
	}

	@Override
	public BigDecimal getBalanceAmount(BigDecimal interestAmt, BigInteger locId, String fromDateString, String toDateString) {
		BigDecimal val = BigDecimal.ZERO;
		try {
			List<Object> list = new ArrayList<>();
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("(select sum(balance) from ((select distinct (th1.opening_balance+"+interestAmt+"*th1.crdr) balance from kls_schema.current_transaction th1 where th1.line_of_credit_id = "+locId+" and th1.business_date >= '"+DateUtil.getVSoftDateByString(fromDateString)+"' and th1.business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"' and  th1.tran_code=7) union (select distinct (th1.opening_balance+"+interestAmt+"*th1.crdr) balance from kls_schema.transaction_history th1 where th1.line_of_credit_id = "+locId+" and th1.business_date >= '"+DateUtil.getVSoftDateByString(fromDateString)+"' and th1.business_date <= '"+DateUtil.getVSoftDateByString(toDateString)+"' and  th1.tran_code=7)))");
			list = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("list size : "+list.size());
			if(list.size()>0)
			val = (BigDecimal) list.get(0);
		    logger.info("val=========="+val);
		}catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMTLTLoanLedgerReport method");
			throw new KlsReportRuntimeException(
					"Can not print MTLTLoanLedger Teport:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		return val;
	}

}
