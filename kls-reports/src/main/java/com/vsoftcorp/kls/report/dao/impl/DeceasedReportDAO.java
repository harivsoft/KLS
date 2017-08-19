package com.vsoftcorp.kls.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.IDeceasedReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class DeceasedReportDAO implements IDeceasedReportDAO {

	private static final Logger logger = Logger
			.getLogger(DeceasedReportDAO.class);

	@Override
	public List<Object[]> getDeceasedDetails(Integer pacsId,String date) {

		List<Object[]> deceasedList = new ArrayList<Object[]>();
		logger.info("Start:Inside DeceasedReportDAO method");
		
		try {
			Date asOnDate = Date.valueOf(DateUtil.getFormattedDate(date));
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
					.append("select p.first_name as member_name,p.member_number,p.father_spouse,p.date_of_death,p.death_cause,a.sub_village,v.name as village_name,t.name as taluka_name,d.name as district_name,p.death_cause as remarks from membership.person p,membership.address a,kls_schema.village v,kls_schema.taluka t,kls_schema.district d where p.status_id = 3 and p.party_id=a.party_id and v.id=a.village_id and t.id=v.taluka_id and d.id=t.dist_id and p.date_of_death <= '"+asOnDate+"' and pacs_id="+pacsId+" and a.id in (select min(id) from membership.address where party_id = a.party_id)");

			deceasedList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("Deceased List---" + deceasedList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside DeceasedReportDAO method");
			throw new KlsReportRuntimeException(
					"Can not print Deceased report:", exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside DeceasedReportDAO method");
		return deceasedList;
	}

}
