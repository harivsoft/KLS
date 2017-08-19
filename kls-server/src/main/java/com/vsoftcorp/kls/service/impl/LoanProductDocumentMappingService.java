package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.LoanProductDocumentMapping;
import com.vsoftcorp.kls.data.LoanProductDocumentMappingData;
import com.vsoftcorp.kls.dataaccess.ILoanProductDocumentMappingDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ILoanProductDocumentMappingService;
import com.vsoftcorp.kls.service.helper.LoanProductDocumentMappingHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LoanProductDocumentMappingService implements
		ILoanProductDocumentMappingService {
	private static final Logger logger = Logger
			.getLogger(LoanProductDocumentMappingService.class);

	@Override
	public void saveLoanProductDocumentMapping(
			LoanProductDocumentMappingData data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling  saveLoanProductDocumentMapping() method .");
		List<LoanProductDocumentMapping> masterLst = LoanProductDocumentMappingHelper
				.getLoanProductDocumentMapping(data);
		ILoanProductDocumentMappingDAO dao = KLSDataAccessFactory
				.getLoanProductDocumentMappingDAO();
		try {
			for (LoanProductDocumentMapping loanProductDocMapping : masterLst) {
				if (loanProductDocMapping.getId() == null)
					dao.saveLoanProductDocumentMapping(loanProductDocMapping);
				else
					logger.error("Loan Product Document Mapping already exists.Could not save.");
			}
			List<Long> deleteDocIdsLst = data.getDeleteDocumentsList();
			for (Long prodDocId : deleteDocIdsLst) {
				dao.deleteLoanProductDocumentMapping(prodDocId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to save Loan Product Document Mapping data ..in saveLoanProductDocumentMapping() method..");
			throw new DataAccessException(
					"Unable to save Loan Product Document Mapping data",
					e.getCause());
		}
		logger.info("End: Successfully Completed saving Loan Product Document Mapping data  in saveLoanProductDocumentMapping() method .");
	}

	@Override
	public List<LoanProductDocumentMappingData> getLoanProductDocumentMapping(
			String productId) {

		logger.info("Start: Fetching the LoanProductPurposeMapping from the database in getLoanProductDocumentMapping() method.");
		List<LoanProductDocumentMappingData> dataList = new ArrayList<>();
		ILoanProductDocumentMappingDAO dao = KLSDataAccessFactory
				.getLoanProductDocumentMappingDAO();

		try {
			List<LoanProductDocumentMapping> list = dao
					.getLoanProductDocumentMapping(productId);
			dataList.add(LoanProductDocumentMappingHelper
					.getLoanProductDocumentMappingData(list));

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
		logger.info("End: Successfully fetched the LoanProductPurposeMapping from the database in getLoanProductDocumentMapping() method.");
		return dataList;
	}

}
