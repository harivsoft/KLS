package com.vsoftcorp.kls.report.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.dao.IMemberProfileReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.time.Date;

public class MemberProfileReportDAO implements IMemberProfileReportDAO {
	private static final Logger logger = Logger.getLogger(MemberProfileReportDAO.class);

	@Override
	public List<Object[]> getMemberProfileReport(Integer villageId, Integer farmerTypeId, Integer casteId,
			String asOnDate, Integer pacsId) {
		List<Object[]> shareAccList = new ArrayList<Object[]>();
		logger.info("Start:Inside getMemberProfileReport method");
		try {
			Date asOnDate1 = Date.valueOf(DateUtil.getFormattedDate(asOnDate));
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder
					.append("select kv.name villagename,mft.farmer_type,mc.caste_name,mp.member_number, mp.first_name, mp.father_spouse,mp.registration_date, (select mg.gender from membership.gender mg where mp.gender_id = mg.id) gender, mp.dob dob, (select me.education_name from membership.education me where mp.education_id = me.id) education , (select me.occupation_name from membership.occupation me where mp.occupation_id = me.id) occupation,(select sum(area_owned) from kls_schema.cust_land_detail where  customer_id= mp.party_id) total_area,(select sum(area_cultivated) from kls_schema.cust_land_detail where  customer_id= mp.party_id) area_cultivated, mri.document_number, kd.name, (select account_no from membership.party_bank_info where party_id=mp.party_id and account_type_id in (select id from membership.account_type where account_type ='SB' )) as account_no,ma.mobile_no,mc.id as custId,kv.id as villageId,mft.id as ftId from membership.person mp, membership.registration_id mri, kls_schema.document kd, membership.address ma, membership.farmer_type mft, kls_schema.village kv, membership.caste mc  where mri.party_id = mp.party_id and registration_type_id = kd.id  and ma.party_id = mp.party_id and mp.farmer_type_id = mft.id and kv.id = ma.village_id and mc.id = mp.caste_id  and ma.id in (select min(id) from membership.address where party_id = ma.party_id)");
			if (farmerTypeId != 0)
				stringBuilder.append(" and mft.id=" + farmerTypeId + " ");

			if (villageId != 0)
				stringBuilder.append(" and kv.id=" + villageId + " ");
			if (casteId != 0)
				stringBuilder.append(" and mc.id=" + casteId + " ");

			if (pacsId != 0)
				stringBuilder.append(" and mp.pacs_id =" + pacsId + " ");
			if (asOnDate != null)
				stringBuilder.append(" and registration_date <= '" + asOnDate1 + "'");
			stringBuilder.append("  order by member_number,mft.id,mc.id");

			shareAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
			logger.info("shareAccList---" + shareAccList.size());
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside getMemberProfileReport method");
			throw new KlsReportRuntimeException("Can not print MemberProfileReport report:", exception.getCause());
		}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside getMemberProfileReport method");

		return shareAccList;
	}

	@Override
	public Integer getMemberAge(String curDate, String dob) {
		Double age = null;
		try{
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em.createNativeQuery("SELECT extract(year from age('"+curDate+"', '"+dob+"'))");
		age = (Double)query.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return age.intValue();
	}

	@Override
	public Integer getTotMembers(Integer villageId, Integer farmerType, Integer caste, String asOnDate,
			Integer pacsId) {
		Integer totMembers = 0;
		try {
				Date asOnDate1 = Date.valueOf(DateUtil.getFormattedDate(asOnDate));
				EntityManager em = EntityManagerUtil.getEntityManager();
				StringBuilder stringBuilder = new StringBuilder();

				stringBuilder
						.append("select count(mp.party_id) from membership.person mp, membership.registration_id mri, kls_schema.document kd, membership.address ma, membership.farmer_type mft, kls_schema.village kv, membership.caste mc  where mri.party_id = mp.party_id and registration_type_id = kd.id  and ma.party_id = mp.party_id and mp.farmer_type_id = mft.id and kv.id = ma.village_id and mc.id = mp.caste_id  and ma.id in (select min(id) from membership.address where party_id = ma.party_id)");
				if (farmerType != 0)
					stringBuilder.append(" and mft.id=" + farmerType + " ");

				if (villageId != 0)
					stringBuilder.append(" and kv.id=" + villageId + " ");
				if (caste != 0)
					stringBuilder.append(" and mc.id=" + caste + " ");

				if (pacsId != 0)
					stringBuilder.append(" and mp.pacs_id =" + pacsId + " ");
				if (asOnDate != null)
					stringBuilder.append(" and registration_date <= '" + asOnDate1 + "'");
				//stringBuilder.append("  order by member_number,mft.id,mc.id");

				totMembers = ((BigInteger) em.createNativeQuery(stringBuilder.toString()).getSingleResult()).intValue();
				logger.info("totMembers---" + totMembers);
			} catch (Exception exception) {
				exception.printStackTrace();
				logger.info("Error:Inside getMemberProfileReport method");
				throw new KlsReportRuntimeException("Can not print MemberProfileReport report:", exception.getCause());
			}
		
		finally
		{
			EntityManagerUtil.closeSession();
		}
	return totMembers;
	}

	/*@Override
	public BigDecimal getTotMembers(Integer villageId, Integer farmerType, Integer caste, String asOnDate,
			Integer pacsId) {
		BigDecimal totMembers = BigDecimal.ZERO;
		try {
				Date asOnDate1 = Date.valueOf(DateUtil.getFormattedDate(asOnDate));
				EntityManager em = EntityManagerUtil.getEntityManager();
				StringBuilder stringBuilder = new StringBuilder();

				stringBuilder
						.append("select mp.member_number, mp.first_name, mp.father_spouse,mp.registration_date, (select mg.gender from membership.gender mg where mp.gender_id = mg.id) gender, mp.dob dob, (select me.education_name from membership.education me where mp.education_id = me.id) education , (select me.occupation_name from membership.occupation me where mp.occupation_id = me.id) occupation,(select sum(area_owned) from kls_schema.cust_land_detail where  customer_id= mp.party_id) total_area,(select sum(area_cultivated) from kls_schema.cust_land_detail where  customer_id= mp.party_id) area_cultivated, mri.document_number, kd.name, (select account_no from membership.party_bank_info where party_id=mp.party_id and account_type_id in (select id from membership.account_type where account_type ='SB' )) as account_no,ma.mobile_no,mc.id as custId,kv.id as villageId,mft.id as ftId from membership.person mp, membership.registration_id mri, kls_schema.document kd, membership.address ma, membership.farmer_type mft, kls_schema.village kv, membership.caste mc  where mri.party_id = mp.party_id and registration_type_id = kd.id  and ma.party_id = mp.party_id and mp.farmer_type_id = mft.id and kv.id = ma.village_id and mc.id = mp.caste_id  and ma.id in (select min(id) from membership.address where party_id = ma.party_id)");
				if (farmerTypeId != 0)
					stringBuilder.append(" and mft.id=" + farmerTypeId + " ");

				if (villageId != 0)
					stringBuilder.append(" and kv.id=" + villageId + " ");
				if (casteId != 0)
					stringBuilder.append(" and mc.id=" + casteId + " ");

				if (pacsId != 0)
					stringBuilder.append(" and mp.pacs_id =" + pacsId + " ");
				if (asOnDate != null)
					stringBuilder.append(" and registration_date <= '" + asOnDate1 + "'");
				stringBuilder.append("  order by member_number,mft.id,mc.id");

				shareAccList = em.createNativeQuery(stringBuilder.toString()).getResultList();
				logger.info("shareAccList---" + shareAccList.size());
			} catch (Exception exception) {
				exception.printStackTrace();
				logger.info("Error:Inside getMemberProfileReport method");
				throw new KlsReportRuntimeException("Can not print MemberProfileReport report:", exception.getCause());
			}
	return 		
	}*/

}
