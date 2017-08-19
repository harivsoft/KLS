package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILoanInterestPostingDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestPostingFrequency;
import com.vsoftcorp.time.Date;

public class LoanInterestPostingDAO implements ILoanInterestPostingDAO {

	private static final Logger logger = Logger.getLogger(LoanInterestPostingDAO.class);

	@Override
	public List fetchLocs(Date theBusinessDate, String eventType,List<Integer> pacsIdsList) {
		logger.info("eventType: " + eventType);
		EntityManager em = EntityManagerUtil.getEntityManager();

		ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();

		// Query query =
		// em.createNamedQuery("LineOfCredit.getLocForIntPosting");
		List<InterestPostingFrequency> interestPostFreq = new ArrayList<InterestPostingFrequency>();
		if (eventType.equals("MonthEnd")) {
			interestPostFreq.add(InterestPostingFrequency.MONTHLY);
		} else if (eventType.equals("QuarterEnd")) {
			interestPostFreq.add(InterestPostingFrequency.MONTHLY);
			interestPostFreq.add(InterestPostingFrequency.QUATERLY);
		} else if (eventType.equals("HalfYearEnd")) {
			interestPostFreq.add(InterestPostingFrequency.MONTHLY);
			interestPostFreq.add(InterestPostingFrequency.QUATERLY);
			interestPostFreq.add(InterestPostingFrequency.HALF_YEARLY);
		} else if (eventType.equals("YearEnd")) {
			interestPostFreq.add(InterestPostingFrequency.MONTHLY);
			interestPostFreq.add(InterestPostingFrequency.QUATERLY);
			interestPostFreq.add(InterestPostingFrequency.HALF_YEARLY);
			interestPostFreq.add(InterestPostingFrequency.YEARLY);
		} else {
			interestPostFreq.add(InterestPostingFrequency.NOT_APPLICABLE);
		}
		List locList = new ArrayList();
		Query query = em
				.createQuery(
						"SELECT l FROM LineOfCredit l where l.product.asAndWhenImplemented =:asAndWhenImplemented and (l.interestAccrued!=0 or l.overdueInterest!=0) "
								+ " and l.account.accountProperty.pacs.id in (:pacsIdsList) and l.product.interestPostFreq in (:interestPostFreq))", LineOfCredit.class)
				.setParameter("interestPostFreq", interestPostFreq).setParameter("asAndWhenImplemented", false)
				.setParameter("pacsIdsList", pacsIdsList);
		locList = query.getResultList();
		logger.info("--------------------------");

		// This query is for getting Locs of as and when implemented(true) and
		// which are overdue
		query = em
				.createQuery(
						"SELECT l FROM LineOfCredit l where l.product.asAndWhenImplemented =:asAndWhenImplemented and (l.interestAccrued!=0 or l.overdueInterest!=0) and l.account.accountProperty.pacs.id in (:pacsIdsList)"
								+ " and l.loanExpiryDate=:theBusinessDate ",
						LineOfCredit.class).setParameter("theBusinessDate", theBusinessDate).setParameter("asAndWhenImplemented", true)
				.setParameter("pacsIdsList", pacsIdsList);
		locList.addAll(query.getResultList());
		logger.info("list size----------------::::::::::::::::" + locList.size());
		//and ((l.loanType='C' and l.season in (select s from Season s where s.overdueDate=:theBusinessDate)) or (l.loanType='L' and l.loanExpiryDate=:theBusinessDate)))
		return locList;
	}

	@Override
	public List<LineOfCredit> getLineOfCreditListForInterestPosting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getOverDueLocs(Date currentDate) {
		List locList = new ArrayList();
		EntityManager em = EntityManagerUtil.getEntityManager();
		try{
		Query query = em
				.createQuery(
						"SELECT l FROM LineOfCredit l where lc.season.overdueDate <= :currentDate", LineOfCredit.class)
				.setParameter("currentDate", currentDate);
		locList = query.getResultList();
		
		logger.info("list size----------------::::::::::::::::" + locList.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		return locList;
	}

}
