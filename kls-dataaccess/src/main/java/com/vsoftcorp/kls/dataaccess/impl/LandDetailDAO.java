package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.business.entities.TenantLandDetails;
import com.vsoftcorp.kls.dataaccess.ILandDetailDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * This DAO class is used to do CRUD operations with cust_land_detail table
 * 
 * @author a1605
 * 
 */

public class LandDetailDAO implements ILandDetailDAO {

	private static final Logger logger = Logger.getLogger(LandDetailDAO.class);

	@Override
	public void saveLandDetail(LandDetail landDetail) {

		logger.info("Start: Saving the LandDetail  data in database in saveLandDetail() method.");
		try {
			boolean flgTrans=false;
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			em.persist(landDetail);
			if(flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the LandDetail  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the LandDetail data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the LandDetail  data to the database in saveLandDetail() method.");
	}

	@Override
	public void updateLandDetail(LandDetail landDetail) {

		logger.info("Start: Updating the LandDetails data to the database in updateLandDetails() method."+landDetail.getId());
		LandDetail dbLandDetail = null;
		try {
			boolean flgTrans=false;
			EntityManager em = EntityManagerUtil.getEntityManager();

			Long id = landDetail.getId();
			if (id != null) {
				String strQry="select l from LandDetail l where l.id=:id";
				Query qry=em.createQuery(strQry);
				qry.setParameter("id", id);
				dbLandDetail =(LandDetail)qry.getSingleResult();
				if (dbLandDetail != null) {
					if (!em.getTransaction().isActive()) {
						em.getTransaction().begin();
						flgTrans = true;
					}
					dbLandDetail.setCustomerId((landDetail.getCustomerId()));
					dbLandDetail.setVillage(landDetail.getVillage());
					dbLandDetail.setSubVillageCode(landDetail
							.getSubVillageCode());
					dbLandDetail.setSurveyNo(landDetail.getSurveyNo());
					dbLandDetail.setBsrNo(landDetail.getBsrNo());
					dbLandDetail.setRsrNo(landDetail.getRsrNo());
					dbLandDetail.setLandType(landDetail.getLandType());
					dbLandDetail.setAreaOwned(landDetail.getAreaOwned());
					dbLandDetail.setAreaCultivated(landDetail
							.getAreaCultivated());
					dbLandDetail.setIsCharged(landDetail.getIsCharged());
					dbLandDetail.setChargedRemarks(landDetail
							.getChargedRemarks());
					dbLandDetail.setIsMortgazed(landDetail.getIsMortgazed());
					dbLandDetail.setMortgazedRemarks(landDetail
							.getMortgazedRemarks());
					dbLandDetail.setAvailableCulLand(landDetail.getAvailableCulLand());
					dbLandDetail.setTaluka(landDetail.getTaluka());
					if(flgTrans)
						em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the LandDetail "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the LandDetail data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (dbLandDetail == null) {
			logger.error("Trying to update the LandDetail record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the LandDetail record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the LandDetail data to the database in updateLandDetail() method.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LandDetail> getLandDetailsByCustomerId(Long customerId) {
		
		logger.info("Start: Fetching the LandDetail "
				+ " data from the database in getLandDetailsByCustomerId() method."+customerId);
		List<LandDetail> landDetailsMasterList = new ArrayList<LandDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info("before query");
			landDetailsMasterList = em.createQuery(
					"SELECT d FROM LandDetail d where d.customerId='"
							+ customerId + "'").getResultList();
			logger.info(landDetailsMasterList.size());
		} catch (Exception e) {
			e.printStackTrace();
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
	public List<LandDetail> getLandDetailsByCustIdAndLandType(Long customerId, Integer landTypeId) {
		
		logger.info("Start: Fetching the LandDetail "
				+ " data from the database in getLandDetailsByCustIdAndLandType() method."+customerId);
		List<LandDetail> landDetailsMasterList = new ArrayList<LandDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			landDetailsMasterList = em.createQuery(
					"SELECT d FROM LandDetail d where d.customerId='"
							+ customerId + "' and d.landType.id="+landTypeId).getResultList();
		}catch(NoResultException e){
			landDetailsMasterList=null;
		}
		catch (Exception e) {
			logger.error("Error while retriving LandDetails from the database");
			throw new DataAccessException("Error while retriving LandDetails "
					+ " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Customer Land detail data from the database in getLandDetailsByCustIdAndLandType() method.");
		return landDetailsMasterList;
	}


	@Override
	public void deleteLandDetail(String id) {
		logger.info("Start: Deleting the LandDetail  data in database in deleteLandDetail() method.");
		try {
			boolean flgTrans=false;
			EntityManager em = EntityManagerUtil.getEntityManager();
			LandDetail landDetail = em.find(
					LandDetail.class, Long.parseLong(id));
			if (landDetail!=null) {
				if (!em.getTransaction().isActive()) {
					em.getTransaction().begin();
					flgTrans = true;
				}
			em.remove(landDetail);
			if(flgTrans)
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of Deleting the LandDetail  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Deleting the LandDetail data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully Deleting the LandDetail data to the database in deleteLandDetail() method.");
	}

	@Override
	public void saveTenantLandDetail(TenantLandDetails tenantLandDetail) {
		logger.info("Start: Saving the TenantLandDetail  data in database in saveTenantLandDetail() method.");
		try {
			boolean flgTrans=false;
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			em.persist(tenantLandDetail);
			if(flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the TenantLandDetail  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the TenantLandDetail data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the TenantLandDetail  data to the database in saveTenantLandDetail() method.");
		
	}

	@Override
	public void updateTenantLandDetail(TenantLandDetails tenantLandDetail) {
		logger.info("Start: modifing the TenantLandDetail  data in database in saveTenantLandDetail() method.");
		try {
			boolean flgTrans=false;
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			em.merge(tenantLandDetail);
			if(flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of modifing the TenantLandDetail  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of modifing the TenantLandDetail data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully modifing the TenantLandDetail  data to the database in saveTenantLandDetail() method.");
		
	}

	@Override
	public void deleteTenantLandDetail(Long id) {
		logger.info("Start: Deleting the LandDetail  data in database in deleteLandDetail() method.");
		try {
			boolean flgTrans=false;
			EntityManager em = EntityManagerUtil.getEntityManager();
			TenantLandDetails landDetail = em.find(
					TenantLandDetails.class, id);
			if (landDetail!=null) {
				if (!em.getTransaction().isActive()) {
					em.getTransaction().begin();
					flgTrans = true;
				}
			em.remove(landDetail);
			if(flgTrans)
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of Deleting the LandDetail  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Deleting the LandDetail data to the database.",
					excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully Deleting the LandDetail data to the database in deleteLandDetail() method.");
		
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
	
	
	public LandDetail getLandDetailsById(Long id) {
		
		logger.info("Start: Fetching the LandDetail "
				+ " data from the database in getLandDetailsByCustIdAndLandType() method.");
		LandDetail landDetails = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			landDetails = (LandDetail) em.createQuery("SELECT d FROM LandDetail d where d.id="+id).getSingleResult();
		}catch(NoResultException e){
			landDetails=null;
		}
		catch (Exception e) {
			logger.error("Error while retriving LandDetails from the database");
			throw new DataAccessException("Error while retriving LandDetails "
					+ " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Customer Land detail data from the database in getLandDetailsByCustIdAndLandType() method.");
		return landDetails;
	}
	
	public TenantLandDetails getTenantLandDetailsById(Long id) {
		
		logger.info("Start: Fetching the LandDetail "
				+ " data from the database in getTenantLandDetailsById() method.");
		TenantLandDetails landDetails = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			landDetails = (TenantLandDetails) em.createQuery("SELECT d FROM TenantLandDetails d where d.id="+id).getSingleResult();
		}catch(NoResultException e){
			landDetails=null;
		}
		catch (Exception e) {
			logger.error("Error while retriving LandDetails from the database");
			throw new DataAccessException("Error while retriving LandDetails "
					+ " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Customer Land detail data from the database in getTenantLandDetailsById() method.");
		return landDetails;
	}
	
	public List<TenantLandDetails> getTenantLandDetailsByRefId(Long refId) {
		
		logger.info("Start: Fetching the LandDetail "
				+ " data from the database in getTenantLandDetailsByRefId() method.");
		List<TenantLandDetails> landDetails = new ArrayList<TenantLandDetails>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			landDetails = em.createQuery("SELECT d FROM TenantLandDetails d where d.landRefId="+refId).getResultList();
		}catch (Exception e) {
			logger.error("Error while retriving LandDetails from the database");
			throw new DataAccessException("Error while retriving LandDetails "
					+ " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Tenant Land detail data from the database in getTenantLandDetailsByRefId() method.");
		return landDetails;
	}

	@Override
	public List<TenantLandDetails> getTenantLandDetailsByCustIdAndLandType(
			Long customerId, Integer landTypeId) {
		logger.info("Start: Fetching the LandDetail "
				+ " data from the database in getTenantLandDetailsByCustIdAndLandType() method."+customerId);
		List<TenantLandDetails> landDetailsMasterList = new ArrayList<TenantLandDetails>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			landDetailsMasterList = em.createQuery(
					"SELECT d FROM TenantLandDetails d where d.customerId='"
							+ customerId + "' and d.landType="+landTypeId).getResultList();
		}catch(NoResultException e){
			landDetailsMasterList=null;
		}
		catch (Exception e) {
			logger.error("Error while retriving LandDetails from the database");
			throw new DataAccessException("Error while retriving LandDetails "
					+ " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Tenant Land detail data from the database in getTenantLandDetailsByCustIdAndLandType() method.");
		return landDetailsMasterList;
	}

}