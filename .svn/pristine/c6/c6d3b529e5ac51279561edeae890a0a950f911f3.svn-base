package com.vsoftcorp.kls.service.subsidy.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.subsidy.SlabwisesubsidyInterestRate;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.subsidy.ISlabwisesubsidyROIDAO;
import com.vsoftcorp.kls.service.helper.subsidy.SlabwisesubsidyROIHelper;
import com.vsoftcorp.kls.service.subsidy.ISlabwisesubsidyROIService;
import com.vsoftcorp.kls.subsidy.data.SlabwisesubsidyInterestRateData;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class SlabwisesubsidyROIService implements ISlabwisesubsidyROIService {

	private static final Logger logger = Logger
			.getLogger(SlabwisesubsidyROIService.class);
	
	@Override
	public void saveSlabwisesubsidyROIService(SlabwisesubsidyInterestRateData data) {
		
		logger.info("Start: saving slab wise subsidy ROI in saveSlabwisesubsidyROIService() method");
		ISlabwisesubsidyROIDAO dao = KLSDataAccessFactory.getSlabwisesubsidyROIDAO();
		try{
			dao.saveSlabwisesubsidyROIDAO(SlabwisesubsidyROIHelper.getSlabwisesubsidyInterestRate(data));
		}catch(Exception excp){
			logger.error("Could not commit the transaction of saving the slab wise subsidy ROI"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the slab wise subsidy ROI data to the database.",
					excp.getCause());
		}
		logger.info("END: saving slab wise subsidy ROI in saveSlabwisesubsidyROIService() method");
	}

	@Override
	public void modifySlabwisesubsidyROIService(
			SlabwisesubsidyInterestRateData data) {
		
		logger.info("Start: Modifing slab wise subsidy ROI in modifySlabwisesubsidyROIService() method");
		ISlabwisesubsidyROIDAO dao = KLSDataAccessFactory.getSlabwisesubsidyROIDAO();
		try{
			dao.modifySlabwisesubsidyROIDAO(SlabwisesubsidyROIHelper.getSlabwisesubsidyInterestRate(data));
		}catch(Exception excp){
			logger.error("Could not commit the transaction of modifing the slab wise subsidy ROI"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of modifing the slab wise subsidy ROI data to the database.",
					excp.getCause());
		}
		logger.info("END: Modifing slab wise subsidy ROI in saveSlabwisesubsidyROIService() method");
	}

	@Override
	public List<SlabwisesubsidyInterestRateData> getSlabwisesubsidyROIService(Long id) {
		logger.info("Start: Modifing slab wise subsidy ROI in modifySlabwisesubsidyROIService() method");
		ISlabwisesubsidyROIDAO dao = KLSDataAccessFactory.getSlabwisesubsidyROIDAO();
		List<SlabwisesubsidyInterestRate> master = null;
		List<SlabwisesubsidyInterestRateData> data = new ArrayList<SlabwisesubsidyInterestRateData>();
		try{
			master=dao.getSlabwisesubsidyROIDAO(id);
			if(master != null && !master.isEmpty()){
				for(SlabwisesubsidyInterestRate roi : master){
					data.add(SlabwisesubsidyROIHelper.getSlabwisesubsidyInterestRateData(roi));
				}
			}
		}catch(Exception excp){
			logger.error("Could not commit the transaction of modifing the slab wise subsidy ROI"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of modifing the slab wise subsidy ROI data to the database.",
					excp.getCause());
		}
		logger.info("END: Modifing slab wise subsidy ROI in saveSlabwisesubsidyROIService() method");
		return data;
	}

}
