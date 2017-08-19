package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.business.entities.TenantLandDetails;
import com.vsoftcorp.kls.data.DeleteLand;
import com.vsoftcorp.kls.data.LandDetailData;
import com.vsoftcorp.kls.data.TenantLandDetailsData;
import com.vsoftcorp.kls.dataaccess.ILandDetailDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.impl.LandDetailDAO;
import com.vsoftcorp.kls.service.ILandDetailService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.LandDetailHelper;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author a1605
 * 
 */
public class LandDetailService implements ILandDetailService {

	private static final Logger logger = Logger.getLogger(LandDetailDAO.class);

	@Override
	public void saveLandDetails(LandDetailData landDetailsData) {
		logger.info("Start : Calling LandDetails master dao in saveLandDetails() method.");
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
//		List<LandDetail> landDetailsList = LandDetailHelper
//				.getLandDetail(landDetailsData);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			if (landDetailsData.getLandRegister() != null && !(landDetailsData.getLandRegister()).isEmpty()) {
				List<LandDetail> landDetailsList = LandDetailHelper.getLandDetail(landDetailsData);
				for (LandDetail landDetail : landDetailsList) {
					if (landDetail.getId() == null)
						dao.saveLandDetail(landDetail);
					else{
						LandDetail oldlandDetails = dao.getLandDetailsById(landDetail.getId());
						Double temp=oldlandDetails.getAreaCultivated()-landDetail.getAreaCultivated();
						landDetail.setAvailableCulLand(oldlandDetails.getAvailableCulLand()-temp);
						dao.updateLandDetail(landDetail);
					}
				}
			}
			List<DeleteLand> deleteLandList = landDetailsData.getDeleteLand();
			if (deleteLandList != null && !deleteLandList.isEmpty()) {
			 for (DeleteLand deleteLand : deleteLandList) {
				dao.deleteLandDetail(deleteLand.getId());
				}
			}
			List<TenantLandDetailsData> tenantLandDetails = landDetailsData.getTenantLandList();
			if(tenantLandDetails != null && !tenantLandDetails.isEmpty()){
				for(TenantLandDetailsData tenantLand : tenantLandDetails){
					if(tenantLand.getId() == null){// adding New tenant land
						dao.saveTenantLandDetail(LandDetailHelper.getTenantLandDetails(tenantLand));
						//updating guarantor land details
						LandDetail guarantorlandDetails = dao.getLandDetailsById(tenantLand.getLandRefId());
						if(guarantorlandDetails.getAvailableCulLand() != null){
							guarantorlandDetails.setAvailableCulLand(guarantorlandDetails.getAvailableCulLand()-tenantLand.getAreaCultivated());
						}else{
							guarantorlandDetails.setAvailableCulLand(tenantLand.getAreaCultivated());
						}
						dao.updateLandDetail(guarantorlandDetails);
					}
					else{//updating Tenant land
						TenantLandDetails oldtenantLand =dao.getTenantLandDetailsById(tenantLand.getId());
						dao.updateTenantLandDetail(LandDetailHelper.getTenantLandDetails(tenantLand));
						//finding the diff b/w old and new tenant land details
						
						Double temp =oldtenantLand.getAreaCultivated()-tenantLand.getAreaCultivated();
						//updating guarantor land details
						LandDetail guarantorlandToUpdate = dao.getLandDetailsById(tenantLand.getLandRefId());
						guarantorlandToUpdate.setAvailableCulLand(guarantorlandToUpdate.getAvailableCulLand()+temp);
						dao.updateLandDetail(guarantorlandToUpdate);
						
					}
				}
			}
			List<TenantLandDetailsData> tenantLandDeleteList = landDetailsData.getDeleteTenantLandList();
			if(tenantLandDeleteList != null && !tenantLandDeleteList.isEmpty()){
				for(TenantLandDetailsData deleteTenantLand : tenantLandDeleteList){
					dao.deleteTenantLandDetail(deleteTenantLand.getId());
					//updating guarantor land details
					LandDetail guarantorlandDetails = dao.getLandDetailsById(deleteTenantLand.getLandRefId());
					guarantorlandDetails.setAvailableCulLand(guarantorlandDetails.getAvailableCulLand()+deleteTenantLand.getAreaCultivated());
					dao.updateLandDetail(guarantorlandDetails);
				}
			}
			em.getTransaction().commit();

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("LandDetails master data cannot be saved");
			throw new KlsRuntimeException(
					"LandDetails master data cannot be saved", excp);
		}
		logger.info("End : Calling LandDetails master dao in saveLandDetails() method.");
	}

	@Override
	public LandDetailData getLandDetailsByCustomerId(Long customerId) {

		logger.info("Start : Calling LandDetails dao in getLandDetailsByCustomerId() method.");
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
		LandDetailData data = new LandDetailData();
		List <TenantLandDetailsData> tenantLandData = new ArrayList<TenantLandDetailsData>();
		try {
			List<LandDetail> landDetailsMasterList = dao
					.getLandDetailsByCustomerId(customerId);
			logger.info("inside service::"+landDetailsMasterList.size());
			data = LandDetailHelper.getLandDetailData(landDetailsMasterList);
			logger.info("after setting details::");
			for(TenantLandDetails tenantLandDetails : dao.getTenantLandDetailsByCustomerId(customerId)){
				tenantLandData.add(LandDetailHelper.getTenantLandDetailsData(tenantLandDetails));
			}
			data.setTenantLandList(tenantLandData);
			
		} catch (Exception excp) {
			logger.error("Error in retrieving all the LandDetail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the LandDetail records",
					excp.getCause());
		}
		logger.info("End : Calling LandDetail dao in getLandDetailsByCustomerId() method.");
		return data;
	}
	@Override
	public Double getTotalCultivatedLandDetailsByCustomerId(Long customerId) {

		logger.info("Start : Calling LandDetails dao in getTotalCultivatedLandDetailsByCustomerId() method.");
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
		Double totalLand=0d;
		try {
			List<LandDetail> landDetailsMasterList = dao.getLandDetailsByCustomerId(customerId);
			if(landDetailsMasterList != null && !landDetailsMasterList.isEmpty()){
				for(LandDetail land : landDetailsMasterList){
					totalLand=totalLand+land.getAreaCultivated();
				}
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the LandDetail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the LandDetail records",
					excp.getCause());
		}
		logger.info("End : Calling LandDetail dao in getTotalCultivatedLandDetailsByCustomerId() method.");
		return totalLand;
	}
	
	@Override
	public Double getTotalLandDetailsByCustomerId(Long customerId,Integer landTypeId) {

		logger.info("Start : Calling LandDetails dao in getTotalLandDetailsByCustomerId() method.");
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
		Double totalLand=0d;
		try {
			List<LandDetail> landDetailsMasterList = dao
					.getLandDetailsByCustIdAndLandType(customerId, landTypeId);
			if(landDetailsMasterList != null && !landDetailsMasterList.isEmpty()){
				for(LandDetail land : landDetailsMasterList){
					totalLand=totalLand+land.getAvailableCulLand();
				}
			}
			List<TenantLandDetails> tenantLandDetailsList = dao.getTenantLandDetailsByCustIdAndLandType(customerId, landTypeId);
			if(tenantLandDetailsList != null && !tenantLandDetailsList.isEmpty()){
				for(TenantLandDetails tenantLand : tenantLandDetailsList){
					totalLand=totalLand+tenantLand.getAreaCultivated();
				}
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the LandDetail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the LandDetail records",
					excp.getCause());
		}
		logger.info("End : Calling LandDetail dao in getTotalLandDetailsByCustomerId() method.");
		return totalLand;
	}
	
	
	
	@Override
	public Double getTotalAvailableLandByCustId(Long customerId) {

		logger.info("Start : Calling LandDetails dao in getTotalLandDetailsByCustomerId() method.");
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
		Double totalLand=0d;
		try {
			List<LandDetail> landDetailsMasterList = dao
					.getLandDetailsByCustomerId(customerId);
			if(landDetailsMasterList != null && !landDetailsMasterList.isEmpty()){
				for(LandDetail land : landDetailsMasterList){
					totalLand=totalLand+land.getAvailableCulLand();
				}
			}
			List<TenantLandDetails> tenantLandDetailsList = dao.getTenantLandDetailsByCustomerId(customerId);
			if(tenantLandDetailsList != null && !tenantLandDetailsList.isEmpty()){
				for(TenantLandDetails tenantLand : tenantLandDetailsList){
					totalLand=totalLand+tenantLand.getAreaCultivated();
				}
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the LandDetail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the LandDetail records",
					excp.getCause());
		}
		logger.info("End : Calling LandDetail dao in getTotalLandDetailsByCustomerId() method.");
		return totalLand;
	}

	@Override
	public List<TenantLandDetailsData> checkLandTenantDetails(Long landRefId) {
		logger.info("Start : Calling LandDetails dao in checkLandTenantDetails() method.");
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
		List<TenantLandDetails> landtenantDetails = new ArrayList<TenantLandDetails>();
		List<TenantLandDetailsData> landtenantDataList = new ArrayList<TenantLandDetailsData>();
		try {
			landtenantDetails=dao.getTenantLandDetailsByRefId(landRefId);
			for(TenantLandDetails tentantLand : landtenantDetails){
				landtenantDataList.add(LandDetailHelper.getTenantLandDetailsData(tentantLand));
			}
			
		} catch (Exception excp) {
			logger.error("Error in retrieving all the LandDetail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the LandDetail records",
					excp.getCause());
		}
		logger.info("End : Calling LandDetail dao in checkLandTenantDetails() method.");
		return landtenantDataList;
	}

	@Override
	public LandDetailData getLandDetailsById(Long id) {
		logger.info("Start : Calling LandDetails service in getLandDetailsById() method.");
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
		LandDetailData data = new LandDetailData();
		LandDetail landDetails= new LandDetail();
		try {
			landDetails =dao.getLandDetailsById(id);
			if(landDetails != null)
				data = LandDetailHelper.getLandDetailsData(landDetails);
			
		} catch (Exception excp) {
			logger.error("Error in retrieving all the LandDetail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the LandDetail records",
					excp.getCause());
		}
		logger.info("End : Calling LandDetail service in getLandDetailsById() method.");
		return data;
	}
	
	@Override
	public Double getTotalLandByCustId(Long customerId) {

		logger.info("Start : Calling LandDetails dao in getTotalLandByCustId() method.");
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
		Double totalLand=0d;
		try {
			List<LandDetail> landDetailsMasterList = dao.getLandDetailsByCustomerId(customerId);
			if(landDetailsMasterList != null && !landDetailsMasterList.isEmpty()){
				for(LandDetail land : landDetailsMasterList){
					totalLand=totalLand+land.getAreaOwned();
				}
			}
			List<TenantLandDetails> tenantLandDetailsList = dao.getTenantLandDetailsByCustomerId(customerId);
			if(tenantLandDetailsList != null && !tenantLandDetailsList.isEmpty()){
				for(TenantLandDetails tenantLand : tenantLandDetailsList){
					totalLand=totalLand+tenantLand.getAreaCultivated();
				}
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the LandDetail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the LandDetail records",
					excp.getCause());
		}
		logger.info("End : Calling LandDetail dao in getTotalLandByCustId() method.");
		return totalLand;
	}

}