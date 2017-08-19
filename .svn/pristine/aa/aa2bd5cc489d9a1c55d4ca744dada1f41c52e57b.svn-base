package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.data.DeleteSlab;
import com.vsoftcorp.kls.data.SlabwiseInterestRateData;
import com.vsoftcorp.kls.dataaccess.ISlabwiseInterestRateDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ISlabwiseInterestRateService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.SlabwiseInterestRateHelper;

public class SlabwiseInterestRateService implements
		ISlabwiseInterestRateService {

	private static final Logger logger = Logger
			.getLogger(SlabwiseInterestRateService.class);

	@Override
	public void saveSlabwiseInterestRate(
			SlabwiseInterestRateData slabwiseInterestRateData) {

		logger.info("Start : Calling slab wise interest rate dao in saveSlabwiseInterest() method.");
		// get the slab wise interest rate dao.
		ISlabwiseInterestRateDAO dao = KLSDataAccessFactory
				.getSlabwiseInterestRateDAO();
		List<SlabwiseInterestRate> masterList = SlabwiseInterestRateHelper
				.getSlabwiseInterestRate(slabwiseInterestRateData);
		try {
			dao.saveSlabwiseInterestRate(masterList);
			List<DeleteSlab> deleteSlabs = slabwiseInterestRateData
					.getDeleteInterestSlabs();
			if (deleteSlabs != null && !deleteSlabs.isEmpty()) {
				for (DeleteSlab deleteInterestSlab : deleteSlabs) {
					dao.deleteSlabwiseInterestRateRecord(deleteInterestSlab
							.getId());
				}
			}
		} catch (Exception excp) {
			logger.error("slab wise interest id already exists");
			throw new KlsRuntimeException(
					"slab wise interest id already exists", excp.getCause());
		}
		logger.info("End : Calling slab wise interest rate dao in saveSlabwiseInterest() method.");
	}

	/**
	 * This method will fetch all the slab wise interest rate records from the
	 * db and return to the client.
	 */
	public List<SlabwiseInterestRateData> getAllSlabwiseInterestRateRecords() {

		logger.info("Start : Calling slab wise interest rate dao in getAllSlabwiseInterestRecords() method.");
		// get the slab wise interest rate dao.
		ISlabwiseInterestRateDAO dao = KLSDataAccessFactory
				.getSlabwiseInterestRateDAO();
		List<SlabwiseInterestRateData> slabwiseIntrRateDataList = new ArrayList<SlabwiseInterestRateData>();
		try {
			List<SlabwiseInterestRate> slabwiseIntMasterList = dao
					.getAllSlabwiseInterestRateRecords();
			for (SlabwiseInterestRate masterData : slabwiseIntMasterList) {
				slabwiseIntrRateDataList.add(SlabwiseInterestRateHelper
						.getSlabwiseInterestRateData(masterData));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the slab wise interest rate records");
			throw new KlsRuntimeException(
					"Error in retrieving all the slab wise interest rate records",
					excp.getCause());
		}
		logger.info("End : Calling slab wise interest rate dao in getAllSlabwiseInterestRecords() method.");
		return slabwiseIntrRateDataList;
	}
}
