package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.ILocClosureDAO;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;
import com.vsoftcorp.time.Date;

public class LocClosureDAO implements ILocClosureDAO {
	private static final Logger logger = Logger.getLogger(LocClosureDAO.class);

	@Override
	public void updateLocClosure(LineOfCredit loc) {
		logger.info("Start: Updating the LOC Closure data to the database in updateLocCloser() method.");
		LineOfCredit master = new LineOfCredit();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			master = em.find(LineOfCredit.class, loc.getId());
			master.setLineOfCreditStatus(LineOfCreditStatus.Closed);
			em.merge(master);
			em.getTransaction().commit();
		} catch (Exception e) {
			logger.info("Error while Updating the LOC Closure data to the database in updateLocCloser() method.");
			e.printStackTrace();
		}
		logger.info("End: Successfully updated the LOC Closure data to the database in updateLocCloser() method.");

	}

	@Override
	public List<Long> getAllLineOfCreditIdsForLocClosure(String businessDate) {
		logger.info("Start: retriving line of credit ids using getAllLineOfCreditIdsForLocCloser() method.");
		List<Long> list = new ArrayList<Long>();
		try {

			EntityManager em = EntityManagerUtil.getEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<LineOfCredit> criteria = builder
					.createQuery(LineOfCredit.class);
			Root<LineOfCredit> root = criteria.from(LineOfCredit.class);
			Join<LineOfCredit, Season> details = root.join("season");

			List<Predicate> conditions = new ArrayList();
			Long l = Long.valueOf(0);
			conditions.add(builder.equal(root.get("lineOfCreditStatus"), 1));
			conditions.add(builder.lessThan(
					details.<Date> get("drawalEndDate"),
					DateUtil.getVSoftDateByString(businessDate)));
			conditions.add(builder.not(builder.between(details.<Date> get("drawalEndDate"),
					DateUtil.getFinancialYearBeginDate(DateUtil
							.getVSoftDateByString(businessDate)), DateUtil
							.getFinancialYearEndDate(DateUtil
									.getVSoftDateByString(businessDate)))));
			conditions.add(builder.equal(root.get("currentBalance"), 0));
			conditions.add(builder.equal(root.get("interestAccrued"), 0));
			conditions.add(builder.equal(root.get("interestReceivable"), 0));
			conditions
					.add(builder.equal(root.get("penalInterestReceivable"), 0));
			conditions.add(builder.equal(root.get("overdueInterest"), 0));
			TypedQuery<LineOfCredit> typedQuery = em
					.createQuery(criteria.select(root).where(
							conditions.toArray(new Predicate[] {})));
			for (int i = 0; typedQuery.getResultList().size() > i; i++) {
				l = typedQuery.getResultList().get(i).getId();
				list.add(l);
			}
			//System.out.println("list siz---" + list.size());
			/*
			 * Query query = em .createQuery(
			 * "SELECT l.id FROM LineOfCredit l,Season s where l.season.id = s.id and l.lineOfCreditStatus = 1 "
			 * + "and s.drawalEndDate < '" +
			 * DateUtil.getVSoftDateByString(businessDate) +
			 * "' and s.drawalEndDate not between '" +
			 * DateUtil.getFinancialYearBeginDate(DateUtil
			 * .getVSoftDateByString(businessDate)) + "' and '" +
			 * DateUtil.getFinancialYearEndDate(DateUtil
			 * .getVSoftDateByString(businessDate)) +
			 * "' and l.currentBalance = 0" + " and l.interestAccrued=" +
			 * BigDecimal.ZERO + " and l.interestReceivable = " +
			 * BigDecimal.ZERO + " and l.penalInterestReceivable = " +
			 * BigDecimal.ZERO + " and l.overdueInterest = " + BigDecimal.ZERO);
			 */

			// list = query.getResultList();

		} catch (Exception excp) {
			logger.info("Error while retriving line of credit ids using getAllLineOfCreditIdsForLocCloser() method.");

			excp.printStackTrace();
		}
		logger.info("Successfully got all line of credit ids using getAllLineOfCreditIdsForLocCloser() method.");

		return list;
	}

}
