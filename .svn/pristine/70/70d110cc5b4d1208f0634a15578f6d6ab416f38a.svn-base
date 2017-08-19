package com.vsoftcorp.kls.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Document;
import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.business.entities.TenantLandDetails;
import com.vsoftcorp.kls.data.LandDetailData;
import com.vsoftcorp.kls.data.MemberSummaryData;
import com.vsoftcorp.kls.data.TenantLandDetailsData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.data.gateway.datahelpers.RegistrationIdData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareAccountData;
import com.vsoftcorp.kls.dataaccess.IDocumentDAO;
import com.vsoftcorp.kls.dataaccess.IKLSOmniDAO;
import com.vsoftcorp.kls.dataaccess.ILandDetailDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IKLSOmniIntegrationService;
import com.vsoftcorp.kls.service.ILandDetailService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.LandDetailHelper;
import com.vsoftcorp.kls.service.util.RestClientUtil;

public class KLSOmniIntegrationService implements IKLSOmniIntegrationService {

	private static final Logger logger = Logger.getLogger(KLSOmniIntegrationService.class);

	@Override
	public MemberSummaryData getMemberSummaryService(String memberNumber) {
		
		logger.info("START: getMemberSummary() in KLSOmniIntegrationService");
		MemberSummaryData memberSummary= new MemberSummaryData();
		try{
			ILandDetailService landService = KLSServiceFactory.getLandDetailService();
			PersonData person=RestClientUtil.getCustomerByMemberNumber(memberNumber);
			Long memberId=person.getCustomerId();
			ShareAccountData shareData = RestClientUtil.getMemberShareAccountByCustomerId(memberId);
			//String houseHoldCardName = RestClientUtil.getHouseHoldCardName(memberId);
			String houseHoldCardName = null;
			Double totalLand = landService.getTotalLandByCustId(memberId);
			memberSummary.setMemberName(person.getDisplayName());
			memberSummary.setFamilyHeadName(person.getDisplayName());
			memberSummary.setFamilyMembersCount(person.getDependentDetailList().size());
			if(person.getDigitalProofDetailList() != null && !person.getDigitalProofDetailList().isEmpty()){
				RegistrationIdData proof=person.getDigitalProofDetailList().get(0);
				IDocumentDAO dao = KLSDataAccessFactory.getDocumentDAO();
				Document document = dao.getDocumentById(proof.getTypeId().intValue());
				if(document != null && "AADHAAR CARD".equalsIgnoreCase(document.getName())){
					memberSummary.setAadhaarCard(proof.getDocumentNumber());
				}else{
					memberSummary.setAadhaarCard("");
				}
			}else{
				memberSummary.setAadhaarCard("");
			}
			if(shareData.getNumberOfShares() != null)
				memberSummary.setSharesCount(shareData.getNumberOfShares());
			else
				memberSummary.setSharesCount(0);
			if(shareData.getCurrentBalance() != null)
				memberSummary.setSharesValue(shareData.getCurrentBalance());
			else 
				memberSummary.setSharesValue(0d);
			if(totalLand != null)
				memberSummary.setTotalLandInAcres(totalLand);
			else 
				memberSummary.setTotalLandInAcres(0d);
			if(houseHoldCardName != null)
				memberSummary.setPDSCardType(houseHoldCardName);
			else
				memberSummary.setPDSCardType("");
		}catch(Exception e){
			logger.error("ERROR: while  getMemberSummary() in KLSOmniIntegrationService"+e.getMessage());
			e.printStackTrace();
		}
		logger.info("END: getMemberSummary() in KLSOmniIntegrationService");
		return memberSummary;
	}
	public LandDetailData getLandDetailsByMemberNumber(String memberNumber){
		logger.info("Start : Calling LandDetails dao in getLandDetailsByMemberNumber() method.");
		ILandDetailDAO dao = KLSDataAccessFactory.getLandDetailDAO();
		PersonData personData = RestClientUtil.getCustomerByMemberNumber(memberNumber);
		Long memberId = personData.getCustomerId();
		LandDetailData data = new LandDetailData();
		List <TenantLandDetailsData> tenantLandData = new ArrayList<TenantLandDetailsData>();
		try {
			List<LandDetail> landDetailsMasterList = dao
					.getLandDetailsByCustomerId(memberId);
			data = LandDetailHelper.getLandDetailData(landDetailsMasterList);
			for(TenantLandDetails tenantLandDetails : dao.getTenantLandDetailsByCustomerId(memberId)){
				tenantLandData.add(LandDetailHelper.getTenantLandDetailsData(tenantLandDetails));
			}
			data.setTenantLandList(tenantLandData);
			
		} catch (Exception excp) {
			logger.error("Error in retrieving all the LandDetail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the LandDetail records",
					excp.getCause());
		}
		logger.info("End : Calling LandDetail dao in getLandDetailsByMemberNumber() method.");
		return data;
	}
	@Override
	public String getData(String inputParam) {
		logger.info("START: Fetching data for Omni based on query param and the parameter is :"+inputParam);
		IKLSOmniDAO dao = KLSDataAccessFactory.getKLSOmniDAO();
		final String OMNI_QUERIES_PROPERTIES_FILE_PATH = "/omni.properties";
		Properties prop = new Properties();
		InputStream input = null;
		String query;
		String dataJson=null;
		try {
			prop.load(KLSOmniIntegrationService.class.getResourceAsStream(OMNI_QUERIES_PROPERTIES_FILE_PATH));
			query = prop.getProperty(inputParam);
			if(query == null){
				throw new Exception("Query not found for input parameter : "+inputParam);
			}
			dataJson = dao.getData(query);
			
		} catch (IOException ioe) {
			logger.info("Error while loading properties file");
			ioe.printStackTrace();
		}catch (Exception e) {
			logger.info("Error while Fetching data for omni based on query parameter");
			e.printStackTrace();
		}
		
		return dataJson;
	}

}
