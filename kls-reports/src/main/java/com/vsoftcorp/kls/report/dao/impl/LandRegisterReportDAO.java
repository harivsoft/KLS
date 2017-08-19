package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.report.dao.ILandRegisterReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LandRegisterReportDAO implements ILandRegisterReportDAO {

	private static final Logger logger = Logger
			.getLogger(LandRegisterReportDAO.class);

	@Override
	public List<Object[]> getLandRegisterDetails(Integer customerId,Integer pacsId) {

		List<Object[]> landRegisterList = new ArrayList<Object[]>();
		logger.info("Start:Inside LandRegisterReportDAO method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
					.append("select l.id,l.area_cultivated,l.area_owned,l.bsr_no,l.charged_remarks,l.is_charged,l.is_mortgazed,l.mortgazed_remarks,l.rsr_no,l.sub_village_name,l.survey_no,l.land_type_id,l.village_id,l.customer_id,v.name,p1.first_name,a.mobile_no,lt.name as landtype from kls_schema.cust_land_detail l,kls_schema.village v,membership.person p1,membership.address a,kls_schema.land_type lt where p1.party_id=l.customer_id and v.id=l.village_id and a.party_id=l.customer_id and lt.id=l.land_type_id  and a.id in (select min(id) from membership.address where party_id = a.party_id) and p1.pacs_id="+pacsId);
			if (customerId!=0) {
				stringBuilder.append("and l.customer_id=" + customerId+"  order by customer_id");
			}else{
				stringBuilder.append(" order by customer_id");
			}
			landRegisterList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("LandRegister List---" + landRegisterList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside LandRegisterReportDAO method");
			throw new KlsReportRuntimeException(
					"Can not print LandRegister report:", exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside LandRegisterReportDAO method");
		return landRegisterList;
	}

}
