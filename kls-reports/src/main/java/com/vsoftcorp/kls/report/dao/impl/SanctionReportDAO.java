package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.ISanctionReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SanctionReportDAO implements ISanctionReportDAO {
	private static final Logger logger = Logger.getLogger(SanctionReportDAO.class);

	@Override
	public List<Map> getSanctionReport(Integer memNumber, Long seasonId, String seasonYear,
			String pacsId) {
		List<Map> sanctionList = new ArrayList<Map>();
		logger.info("Start:Inside getSanctionReport method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select distinct new Map(p.id as applicationNo,p.sanctionedDate as sanctionedDate,p.customerId as memberNum,s.name as seasonName,cr.name as cropName,sum(p.requiredAmount) as appliedAmount,"
							+ "sum(p.sanctionedAmount) as sanctionedAmount,sum(p.shareAmount) as shareAmount,sum(p.insuranceAmount) as insuranceAmount)"
							+ " from PacsLoanApplicationDetail p,PacsLoanApplicationHeader h,"
							+ "Season s, Crop cr  where");
			if (memNumber != 0)
				stringBuilder.append(" p.customerId=" + memNumber.longValue() + " and");
			stringBuilder.append(" p.applicationStatus=4 ");
			if (seasonId != 0) {
				stringBuilder.append("and p.seasonId.id=" + seasonId + " ");
			} else
				stringBuilder.append("and s.drawalStartDate >= :financialYrStartDate and s.draw"
						+ "alEndDate <= :financialYrEndDate");

			stringBuilder.append(" and p.seasonId.id=s.id and p.headerId=h.id and p.cropId.id=cr.id");
			if (pacsId != null)
				stringBuilder.append(" and h.pacs.id=" + Integer.parseInt(pacsId));
			stringBuilder
					.append(" group by p.customerId,s.name,cr.name,p.id order by p.customerId");
			Query query = em.createQuery(stringBuilder.toString());
			if (seasonId == 0) {
				query.setParameter("financialYrStartDate",
						DateUtil.getFinancialYearBeginDate(seasonYear));
				query.setParameter("financialYrEndDate",
						DateUtil.getFinancialYearEndDate(seasonYear));
			}
			sanctionList = query.getResultList();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getSanctionReport method");
			throw new KlsReportRuntimeException("Can not print Sanction report:",
					exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside getSanctionReport method");

		return sanctionList;
	}
}
