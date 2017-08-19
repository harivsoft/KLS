package com.vsoftcorp.kls.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.business.entities.TenantLandDetails;
import com.vsoftcorp.kls.data.LandDetailData;
import com.vsoftcorp.kls.report.dao.IJindagiReportDAO;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class JindagiReportDAO implements IJindagiReportDAO {
	
	
	private static final Logger logger = Logger.getLogger(JindagiReportDAO.class);
	
	@Override
	public List<LandDetail> getLandDetailsListByCustomerId(Long customerId) {
		
		logger.info("Start: Fetching the LandDetail "
				+ " data from the database in getLandDetailsByCustomerId() method."+customerId);
		List<LandDetail> landDetailsMasterList = new ArrayList<LandDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			landDetailsMasterList = em.createQuery("SELECT d FROM LandDetail d where d.customerId='"+ customerId + "'").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving LandDetails from the database");
			throw new DataAccessException("Error while retriving LandDetails "
					+ " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Customer Land detail data from the database in getLandDetailsByCustomerId() method.");
		return landDetailsMasterList;
	}
	
	@Override
	public LandDetail getLandDetailsById(Long landId) {
		
		logger.info("Start: Fetching the LandDetail "
				+ " data from the database in getLandDetailsById() method."+landId);
		LandDetail landDetail = new LandDetail();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			landDetail = (LandDetail) em.createQuery("SELECT d FROM LandDetail d where d.id='"+ landId + "'").getSingleResult();
		} catch (Exception e) {
			logger.error("Error while retriving LandDetails from the database");
			throw new DataAccessException("Error while retriving LandDetails "
					+ " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Customer Land detail data from the database in getLandDetailsById() method.");
		
		return landDetail;
	}
	
	
	@Override
	public List<TenantLandDetails> getRentedDetailsByCustomerId(Long customerId) {
		logger.info("Start: Fetching the tRentedDetails "
				+ " data from the database in getRentedDetailsByCustomerId() method."+customerId);
		List<TenantLandDetails> tenantLandList= new ArrayList<TenantLandDetails>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			tenantLandList = em.createQuery("SELECT d FROM TenantLandDetails d where d.guarantorId="+customerId).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving tRentedDetails from the database");
			throw new DataAccessException("Error while retriving TenantLandDetail "
					+ " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the tRentedDetailss data from the database in getRentedDetailsByCustomerId() method.");
		return tenantLandList;
	}


	@Override
	public List<TenantLandDetails> getTenantLandDetailsByCustomerId(Long customerId) {
		logger.info("Start: Fetching the TenantLandDetail "
				+ " data from the database in getTenantLandDetailsByCustomerId() method."+customerId);
		List<TenantLandDetails> tenantLandList= new ArrayList<TenantLandDetails>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			tenantLandList = em.createQuery("SELECT d FROM TenantLandDetails d where d.customerId="+customerId).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving TenantLandDetail from the database");
			throw new DataAccessException("Error while retriving TenantLandDetail "
					+ " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the TenantLandDetail data from the database in getTenantLandDetailsByCustomerId() method.");
		return tenantLandList;
	}


	@Override
	public Object[] getCustomerDetails(Integer customerId) {
		Object[] customerDetails = null;
		logger.info("Start:Inside JindagiReportDAO method");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select member_number,first_name from membership.person where party_id = "+customerId);
			customerDetails = (Object[]) em.createNativeQuery(stringBuilder.toString()).getSingleResult();
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info("Error:Inside JindagiReportDAO method");
			throw new KlsReportRuntimeException(
					"Can not print JindagiReportDAO report:", exception.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End:Inside JindagiReportDAO method");
		
		return customerDetails;
	}

}
