package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.data.InterestCategoryData;
import com.vsoftcorp.kls.dataaccess.IInterestCategoryDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IInterestCategoryService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.InterestCategoryHelper;

/**
 * 
 * @author a9152
 * 
 */

public class InterestCategoryService implements IInterestCategoryService {

	private static final Logger logger = Logger
			.getLogger(InterestCategoryService.class);

	/**
	 * 
	 */
	public void saveInterestCategory(InterestCategoryData interestCategoryData) {

		logger.info("Start : Calling  Interest category dao in saveInterestCategory() method.");
		// get the Interest category dao.
		IInterestCategoryDAO dao = KLSDataAccessFactory
				.getInterestCategoryDAO();
		// get the entity pojo.
		InterestCategory master = InterestCategoryHelper
				.getInterestCategory(interestCategoryData);
		InterestCategory dbMaster=null;

		try {
			dbMaster = dao.getInterestCategory(master,false);
			
			// Check if interest category id does not exist in db, then save.
			if (dbMaster == null) {
				dao.saveInterestCategory(master);
			}
			
		} catch (Exception excp) {
			logger.error(" Interest category data cannot be saved");
			throw new KlsRuntimeException(
					" Interest category data cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("Interest Category already exists");
			throw new KlsRuntimeException(
					"InterestCategory already exists");
		}
		logger.info("End : Calling  Interest category dao in saveInterestCategory() method.");
	}

	/**
	 * This method checks for Interest category id in the db. If exists, then
	 * update the Interest category data to the database. Else throw the
	 * exception.
	 * 
	 * @param interestCategoryMasterData
	 */
	public void updateInterestCategory(
			InterestCategoryData interestCategoryMasterData) {

		logger.info("Start : Calling  Interest category dao in updateInterestCategory() method.");
		// get the Interest category dao.
		IInterestCategoryDAO dao = KLSDataAccessFactory
				.getInterestCategoryDAO();
		InterestCategory master = InterestCategoryHelper
				.getInterestCategory(interestCategoryMasterData);
		// update the Interest category id to the db.
		try {
			dao.updateInterestCategory(master);
		} catch (Exception excp) {
			logger.error(" Interest category data cannot be updated as  Interest category id does not exist");
			throw new KlsRuntimeException(
					" Interest category data cannot be updated as  Interest category id does not exist",
					excp.getCause());
		}
		logger.info("End : Calling  Interest category dao in updateInterestCategory() method.");
	}

	/**
	 * Retrieves all Interest categories from the database.
	 * 
	 */
	public List<InterestCategoryData> getAllInterestCategories() {

		logger.info("Start : Calling  Interest category dao in getAllInterestCategories() method.");
		// get the Interest category master dao.
		IInterestCategoryDAO dao = KLSDataAccessFactory
				.getInterestCategoryDAO();
		List<InterestCategoryData> interestCategoryDataList = new ArrayList<InterestCategoryData>();
		try {
			List<InterestCategory> interestCategoryMasterList = dao
					.getAllInterestCategories(true);
			for (InterestCategory masterData : interestCategoryMasterList) {
				interestCategoryDataList.add(InterestCategoryHelper
						.getInterestCategoryData(masterData));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the  Interest category records");
			throw new KlsRuntimeException(
					"Error in retrieving all the  Interest category records",
					excp.getCause());
		}
		logger.info("End : Calling  Interest category dao in getAllInterestCategories() method.");
		return interestCategoryDataList;
	}
}
