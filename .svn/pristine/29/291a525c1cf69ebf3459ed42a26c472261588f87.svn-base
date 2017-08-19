package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.dataaccess.loan.ILoanApplicationRenewalDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

/**
 * This class used to getting the loan headers and application details to loan
 * renewal
 * 
 * @author a1605
 * 
 */

public class LoanApplicationRenewalDAO implements ILoanApplicationRenewalDAO {

	private static final Logger logger = Logger.getLogger(LoanApplicationRenewalDAO.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<PacsLoanApplicationHeader> getLoanApplicationHeaderForRenewal(String financialYear, Integer pacId) {
		logger.info("Start: inside getLoanApplicationHeaderForRenewal()");
		List<PacsLoanApplicationHeader> headersList = new ArrayList<PacsLoanApplicationHeader>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select h from PacsLoanApplicationHeader h where h.financialYear=:financialYear");
			headersList = query.setParameter("financialYear", financialYear).getResultList();
		} catch (Exception e) {
			logger.error("Error: inside getLoanApplicationHeaderForRenewal()");
			throw new DataAccessException("Error while getting previous year loan applications masters.", e.getCause());
		}
		logger.info("End: inside getLoanApplicationHeaderForRenewal()");

		return headersList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PacsLoanApplicationDetail> getLoanApplicationDetailForRenewal(Long headerId) {
		logger.info("Start: inside getLoanApplicationDetailForRenewal()");

		List<PacsLoanApplicationDetail> detailsList = new ArrayList<PacsLoanApplicationDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select d from PacsLoanApplicationDetail d where d.headerId.id=:headerId and applicationStatus=:applicationStatus");
			detailsList = query.setParameter("headerId", headerId).setParameter("applicationStatus", LoanApplicationState.SANCTIONED).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: inside getLoanApplicationDetailForRenewal()");
			throw new DataAccessException("Error while getting previous year loan application details.", e.getCause());
		}
		logger.info("End: inside getLoanApplicationDetailForRenewal()");
		return detailsList;
	}

	@Override
	public Season getSeasonByName(String seasonName) {
		logger.info("Start: inside getSeasonByName()");
		Season season = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select s from Season s where s.name=:seasonName");
			@SuppressWarnings("unchecked")
			List<Season> seasonList = query.setParameter("seasonName", seasonName).getResultList();
			if (!seasonList.isEmpty())
				season = seasonList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: inside getSeasonByName()");
			return season;
		}
		logger.info("End: inside getSeasonByName()");
		return season;
	}

}
