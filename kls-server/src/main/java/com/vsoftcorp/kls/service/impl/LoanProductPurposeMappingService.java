package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Activity;
import com.vsoftcorp.kls.business.entities.LoanProductPurposeMapping;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.business.entities.SubPurpose;
import com.vsoftcorp.kls.data.ActivityData;
import com.vsoftcorp.kls.data.LoanProductPurposeMappingData;
import com.vsoftcorp.kls.data.PurposeData;
import com.vsoftcorp.kls.data.SubPurposeData;
import com.vsoftcorp.kls.dataaccess.ILoanProductPurposeMappingDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ILoanProductPurposeMappingService;
import com.vsoftcorp.kls.service.helper.ActivityHelper;
import com.vsoftcorp.kls.service.helper.LoanProductPurposeMappingHelper;
import com.vsoftcorp.kls.service.helper.PurposeHelper;
import com.vsoftcorp.kls.service.helper.SubPurposeHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LoanProductPurposeMappingService implements
		ILoanProductPurposeMappingService {
	private static final Logger logger = Logger
			.getLogger(LoanProductPurposeMappingService.class);

	@Override
	public void saveLoanProductPurposeMapping(LoanProductPurposeMappingData data) {
		logger.info("Start: Calling  saveLoanProductPurposeMapping() metho");
		List<LoanProductPurposeMapping> masterLst = LoanProductPurposeMappingHelper
				.getLoanProductPurposeMapping(data);
		ILoanProductPurposeMappingDAO dao = KLSDataAccessFactory
				.getLoanProductPurposeMappingDAO();
		try {
			for (LoanProductPurposeMapping loanProductMapping : masterLst) {
				if (loanProductMapping.getId() == null)
					dao.saveLoanProductPurposeMapping(loanProductMapping);
				else
					logger.info("alredy exist");
				// dao.updateLoanProductPurposeMapping(loanProductMapping); }

			}
			List<Long> deleteDocIdsLst = data.getDeleteList();
			for (Long prodPurposeId : deleteDocIdsLst) {
				dao.deleteLoanProductPurposeMapping(prodPurposeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to save Loan Product Purpose Mapping data ..in saveLoanProductPurposeMapping() method..");
		}
		logger.info("End: Successfully Completed saving Loan Product Purpose Mapping data  in saveLoanProductPurposeMapping() method .");
	}

	@Override
	public List<LoanProductPurposeMappingData> getLoanProductPurposeMapping(
			String productId, String purposeId) {

		logger.info("Start: Fetching the LoanProductPurposeMapping from the database in getLoanProductPurposeMapping() method.");
		List<LoanProductPurposeMappingData> dataList = new ArrayList<>();
		ILoanProductPurposeMappingDAO dao = KLSDataAccessFactory
				.getLoanProductPurposeMappingDAO();

		try {
			List<LoanProductPurposeMapping> list = dao
					.getLoanProductPurposeMapping(productId, purposeId);
			dataList.add(LoanProductPurposeMappingHelper
					.getLoanProductPurposeMappingData(list));

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the LoanProductPurposeMapping from the "
					+ "databaseException thrown.");
			throw new DataAccessException(
					"Could not fetch the LoanProductPurposeMapping from the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the LoanProductPurposeMapping from the database in getLoanProductPurposeMapping() method.");
		return dataList;
	}

	@Override
	public List<PurposeData> getAllPurposesBasedOnProduct(Integer productId) {
		logger.info("Start: Calling  getAllPurposesBasedOnProduct() method .");
		List<PurposeData> dataList = new ArrayList<PurposeData>();
		ILoanProductPurposeMappingDAO dao = KLSDataAccessFactory
				.getLoanProductPurposeMappingDAO();
		try {
			List<Purpose> masterList = dao
					.getPurposeBasedOnProductId(productId);
			for (Purpose purpose : masterList) {
				dataList.add(PurposeHelper.getPurposeData(purpose));
			}
		} catch (Exception e) {
			logger.error("Error While getting all purpose data ..in getAllPurposesBasedOnProduct() method..");
			throw new DataAccessException(
					"Error While getting all purpose data", e.getCause());
		}
		logger.info("End: Successfully Completed getting all purpose data in  getAllPurposesBasedOnProduct() method .");

		return dataList;
	}

	@Override
	public List<SubPurposeData> getAllSubPurposeBasedOnProductAndPurpose(
			Integer productId, Integer purposeId) {
		logger.info("Start: Calling  getAllSubPurposeBasedOnProductAndPurpose() method .");
		List<SubPurposeData> dataList = new ArrayList<SubPurposeData>();
		ILoanProductPurposeMappingDAO dao = KLSDataAccessFactory
				.getLoanProductPurposeMappingDAO();
		try {
			List<SubPurpose> masterList = dao
					.getSubPurposeBasedOnProductIdAndPurposeId(productId,
							purposeId);
			for (SubPurpose subPurpose : masterList) {
				dataList.add(SubPurposeHelper.getSubPurposeData(subPurpose));
			}
		} catch (Exception e) {
			logger.error("Error While getting all subpurpose data ..in getAllSubPurposeBasedOnProductAndPurpose() method..");
			throw new DataAccessException(
					"Error While getting all sub purpose data", e.getCause());
		}
		logger.info("End: Successfully Completed getting all sub purpose data in  getAllSubPurposeBasedOnProductAndPurpose() method .");

		return dataList;
	}

	@Override
	public List<ActivityData> getAllActivityBasedOnProductPurposeAndSubPurpose(
			Integer productId, Integer purposeId, Integer subPurposeId) {
		logger.info("Start: Calling  getAllActivityBasedOnProductPurposeAndSubPurpose() method .");
		List<ActivityData> dataList = new ArrayList<ActivityData>();
		ILoanProductPurposeMappingDAO dao = KLSDataAccessFactory
				.getLoanProductPurposeMappingDAO();
		try {
			List<Activity> masterLst = dao
					.getActivityBasedOnProductAndPurposeAndSubPurpseId(
							productId, purposeId, subPurposeId);
			for (Activity activity : masterLst) {
				dataList.add(ActivityHelper.getActivityData(activity));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While getting all activity data ..in getAllActivityBasedOnProductPurposeAndSubPurpose() method..");
			throw new DataAccessException(
					"Error While getting all activity data", e.getCause());
		}
		logger.info("End: Successfully Completed getting all activity data in  getAllActivityBasedOnProductPurposeAndSubPurpose() method .");

		return dataList;
	}

}
