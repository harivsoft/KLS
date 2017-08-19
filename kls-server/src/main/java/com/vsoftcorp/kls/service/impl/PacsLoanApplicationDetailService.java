/**
 * 
 */
package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.DeleteLandApplicationDetail;
import com.vsoftcorp.kls.data.PacsLoanApplicationDetailData;
import com.vsoftcorp.kls.dataaccess.ICropDAO;
import com.vsoftcorp.kls.dataaccess.ICustomerDAO;
import com.vsoftcorp.kls.dataaccess.ILandTypeDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationDetailDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationHeaderDAO;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IPacsLoanApplicationDetailService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.PacsLoanApplicationDetailHelper;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.kls.valuetypes.PacsStatus;

/**
 * @author a9152
 * 
 */
public class PacsLoanApplicationDetailService implements IPacsLoanApplicationDetailService {

	private static final Logger logger = Logger.getLogger(PacsLoanApplicationDetailService.class);

	@Override
	public void savePacsLoanApplicationDetail(
			PacsLoanApplicationDetailData pacsLoanApplicationDetailData) {

		logger.info("Start : Calling pacs loan application detail dao in savePacsLoanApplicationDetail() method.");
		// get the pacs loan application detail dao.
		IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
		List<PacsLoanApplicationDetail> masterList = PacsLoanApplicationDetailHelper
				.getPacsLoanApplicationDetailList(pacsLoanApplicationDetailData);
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		for (PacsLoanApplicationDetail master : masterList) {
			try {
				IPacsLoanApplicationHeaderDAO headerDao = KLSDataAccessFactory
						.getPacsLoanApplicationHeaderDAO();
				PacsLoanApplicationHeader header = headerDao.getPacsLoanApplicationHeader(master
						.getHeaderId());
				if (header != null) {
					master.setHeaderId(header);
					ICustomerDAO customerDao = KLSDataAccessFactory.getCustomerDAO();
					//Customer customer = customerDao.getCustomer(master.getCustomerId().getId());
					if (master.getCustomerId() != null) {
						master.setCustomerId(master.getCustomerId());
						ICropDAO cropDao = KLSDataAccessFactory.getCropDAO();
						Crop crop = cropDao.getCrop(master.getCropId());
						if (crop != null) {
							master.setCropId(crop);
							ISeasonDAO seasonDao = KLSDataAccessFactory.getSeasonDAO();
							Season season = seasonDao.getSeason(master.getSeasonId().getId());
							if (season != null) {
								master.setSeasonId(season);
								ILandTypeDAO landTypeDao = KLSDataAccessFactory.getLandTypeDAO();
								LandType landType = landTypeDao.getLandType(master.getLandTypeId()
										.getId());
								if (landType != null) {
									master.setLandTypeId(landType);
									if (master.getId() == null) {
										/*String businessDate="";
										Pacs pacs=pacsDao.getPacs(header.getPacs().getId());
										logger.info("pacs type::"+pacs.getPacsStatus());
										if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
											businessDate=RestClientUtil.getPacsBusinessDate(header.getPacs().getId(),pacs.getBranch().getId());
											//businessDate="2017-05-01";
											master.setInspectedDate(DateUtil.getVSoftDateByString(businessDate));
										}*/
										dao.savePacsLoanApplicationDetail(master);
									} else {
										if (master.getApplicationStatus().getValue() == LoanApplicationState.INSPECTED
												.getValue() || master.getApplicationStatus().getValue() == LoanApplicationState.AUTHORIZATION_PENDING.getValue()) {
											master.setInspectedBy(pacsLoanApplicationDetailData
													.getLoggedInUserDetails().getUserName());
											String businessDate="";
											Pacs pacs=pacsDao.getPacs(header.getPacs().getId());
											logger.info("pacs type::"+pacs.getPacsStatus());
											if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
												businessDate=RestClientUtil.getPacsBusinessDate(header.getPacs().getId(),pacs.getBranch().getId());
												//businessDate="2017-05-01";
												master.setInspectedDate(DateUtil.getVSoftDateByString(businessDate));
											}
											else
											master.setInspectedDate(LoanServiceUtil
													.getBusinessDate());
										}
										dao.updatePacsLoanApplicationDetail(master);
									}
								} else {
									logger.error("Please enter valid pacs loan application land type id.");
									throw new KlsRuntimeException(
											"Please enter valid pacs loan application land type id.");
								}
							} else {
								logger.error("Please enter valid pacs loan application season id.");
								throw new KlsRuntimeException(
										"Please enter valid pacs loan application season id.");
							}
						} else {
							logger.error("Please enter valid pacs loan application crop id.");
							throw new KlsRuntimeException(
									"Please enter valid pacs loan application crop id.");
						}
					} else {
						logger.error("Please enter valid pacs loan application customer id.");
						throw new KlsRuntimeException(
								"Please enter valid pacs loan application customer id.");
					}
				} else {
					logger.error("Please enter valid pacs loan application header id.");
					throw new KlsRuntimeException(
							"Please enter valid pacs loan application header id.");
				}
			} catch (Exception excp) {
				excp.printStackTrace();
				logger.error("Pacs Loan Application Detail id already exists");
				throw new KlsRuntimeException("Pacs Loan Application Detail id already exists",
						excp.getCause());
			}
		}
		List<DeleteLandApplicationDetail> deleteLandApplDetailList = pacsLoanApplicationDetailData
				.getDeleteLandApplicationDetails();
		if (deleteLandApplDetailList != null && !deleteLandApplDetailList.isEmpty()) {
			for (DeleteLandApplicationDetail deleteLandApplDetail : deleteLandApplDetailList) {
				dao.deletePacsLoanApplicationDetailRecord(deleteLandApplDetail.getId());
			}
		}
		logger.info("End : Calling pacs loan application detail dao in savePacsLoanApplicationDetail() method.");
	}

	@Override
	public void updatePacsLoanApplicationDetail(
			PacsLoanApplicationDetailData pacsLoanApplicationDetailData) {

		logger.info("Start : Calling pacs loan application detail dao in updatePacsLoanApplicationDetail() method.");
		// get the pacs loan application detail dao.
		IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
		PacsLoanApplicationDetail master = PacsLoanApplicationDetailHelper
				.getPacsLoanApplicationDetail(pacsLoanApplicationDetailData);
		// update the pacs loan application detail data to the db.
		/*
		 * if(master.getApplicationStatus()==2) { master.setSanctionedAmount(new
		 * BigDecimal(Math.min(Math.min(Math
		 * .min(master.getRequiredAmount().doubleValue(), master
		 * .getRecommendedAmount().doubleValue()), master
		 * .getCalculatedAmount().doubleValue()), master
		 * .getInspectionAmount().doubleValue()))); }
		 */
		try {
			dao.updatePacsLoanApplicationDetail(master);
		} catch (Exception excp) {
			logger.error("Pacs Loan Application Detail data cannot be updated as pacs loan application detail id does not exist");
			throw new KlsRuntimeException(
					"Pacs Loan Application Detail data cannot be updated as pacs loan application detail id does not exist",
					excp.getCause());
		}
		logger.info("End : Calling pacs loan application detail dao in updatePacsLoanApplicationDetail() method.");
	}

	@Override
	public List<PacsLoanApplicationDetailData> getAllPacsLoanApplicationDetails() {

		logger.info("Start : Calling pacs loan application detail dao in getAllPacsLoanApplicationDetails() method.");
		// get the village dao.
		IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
		List<PacsLoanApplicationDetailData> pacsLoanApplicationDetailDataList = new ArrayList<PacsLoanApplicationDetailData>();
		try {
			List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = dao
					.getAllPacsLoanApplicationDetails();
			for (PacsLoanApplicationDetail data : pacsLoanApplicationDetailList) {
				pacsLoanApplicationDetailDataList.add(PacsLoanApplicationDetailHelper
						.getPacsLoanApplicationDetailData(data));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the pacs loan application detail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the pacs loan application detail records",
					excp.getCause());
		}
		logger.info("End : Calling pacs loan application detail dao in getAllPacsLoanApplicationDetails() method.");
		return pacsLoanApplicationDetailDataList;
	}

	@Override
	public List<PacsLoanApplicationDetailData> getPacsLoanApplicationDetailsByHeaderId(Long headerId) {

		logger.info("Start : Calling pacs loan application detail dao in getPacsLoanApplicationDetailsByHeaderId() method.");
		// get the pacs loan application detail dao.
		IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
		List<PacsLoanApplicationDetailData> pacsLoanApplicationDetailDataList = new ArrayList<PacsLoanApplicationDetailData>();
		try {
			List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = dao
					.getPacsLoanApplicationDetailsForInspection(headerId);
			pacsLoanApplicationDetailDataList.add(PacsLoanApplicationDetailHelper
					.getPacsLoanApplicationDetailData(pacsLoanApplicationDetailList));
		} catch (Exception excp) {
			logger.error("Error in retrieving all the pacs loan application detail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the pacs loan application detail records",
					excp.getCause());
		}
		logger.info("End : Calling pacs loan application detail dao in getPacsLoanApplicationDetailsByHeaderId() method.");
		return pacsLoanApplicationDetailDataList;
	}
	
	@Override
	public List<PacsLoanApplicationDetailData> getPacsLoanApplicationDetailsByHeaderIdWithStatus(Long headerId) {

		logger.info("Start : Calling pacs loan application detail dao in getPacsLoanApplicationDetailsByHeaderIdWithStatus() method.");
		// get the pacs loan application detail dao.
		IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
		List<PacsLoanApplicationDetailData> pacsLoanApplicationDetailDataList = new ArrayList<PacsLoanApplicationDetailData>();
		try {
			List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = dao
					.getPacsLoanApplicationDetailsForInspectionWithStatus(headerId);
			pacsLoanApplicationDetailDataList.add(PacsLoanApplicationDetailHelper
					.getPacsLoanApplicationDetailData(pacsLoanApplicationDetailList));
		} catch (Exception excp) {
			logger.error("Error in retrieving all the pacs loan application detail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the pacs loan application detail records",
					excp.getCause());
		}
		logger.info("End : Calling pacs loan application detail dao in getPacsLoanApplicationDetailsByHeaderIdWithStatus() method.");
		return pacsLoanApplicationDetailDataList;
	}
	
	
	@Override
	public List<PacsLoanApplicationDetailData> getLoanedLandDetails(
			Long customerId, Integer seasonId, Integer landTypeId) {
		logger.info("Start : Calling pacs loan application detail dao in getLoanedLandDetails() method.");
		IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
		List<PacsLoanApplicationDetailData> pacsLoanApplicationDetailDataList = new ArrayList<PacsLoanApplicationDetailData>();
		try {
			List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = dao.getLoanedLandDetails(customerId, seasonId, landTypeId);
			if(!pacsLoanApplicationDetailList.isEmpty()){
				pacsLoanApplicationDetailDataList.add(PacsLoanApplicationDetailHelper
						.getPacsLoanApplicationDetailData(pacsLoanApplicationDetailList));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the pacs loan application detail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the pacs loan application detail records",
					excp.getCause());
		}
		logger.info("End : Calling pacs loan application detail dao in getLoanedLandDetails() method.");
		return pacsLoanApplicationDetailDataList;
	}
	
	
	@Override
	public List<PacsLoanApplicationDetailData> getPacsLoanApplicationDetailsEntryModify(Long headerId) {

		logger.info("Start : Calling pacs loan application detail dao in getPacsLoanApplicationDetailsByHeaderId() method.");
		// get the pacs loan application detail dao.
		IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
		List<PacsLoanApplicationDetailData> pacsLoanApplicationDetailDataList = new ArrayList<PacsLoanApplicationDetailData>();
		try {
			List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = dao
					.getPacsLoanApplicationDetailsEntryModify(headerId);
			pacsLoanApplicationDetailDataList.add(PacsLoanApplicationDetailHelper
					.getPacsLoanApplicationDetailData(pacsLoanApplicationDetailList));
		} catch (Exception excp) {
			logger.error("Error in retrieving all the pacs loan application detail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the pacs loan application detail records",
					excp.getCause());
		}
		logger.info("End : Calling pacs loan application detail dao in getPacsLoanApplicationDetailsByHeaderId() method.");
		return pacsLoanApplicationDetailDataList;
	}

}
