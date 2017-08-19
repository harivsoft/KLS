package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.data.BranchData;
import com.vsoftcorp.kls.dataaccess.IBranchDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IBranchService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.BranchHelper;

public class BranchService implements IBranchService {

	private static final Logger logger = Logger.getLogger(BranchService.class);

	@Override
	public void saveBranch(BranchData branchData) {

		logger.info("Start : Calling branch dao in saveBranch() method.");
		// get the branch dao.
		IBranchDAO dao = KLSDataAccessFactory.getBranchDAO();
		Branch master = BranchHelper.getBranch(branchData);

		try {
			if (master.getId() == null) {
				dao.saveBranch(master);
			}
		} catch (Exception excp) {
			logger.error("Branch id already exists");
			throw new KlsRuntimeException("Branch id already exists",
					excp.getCause());
		}
		logger.info("End : Calling branch dao in saveBranch() method.");
	}

	/**
	 * This method checks for branch id in the db. If exists, then update the
	 * branch id to the database. Else throw the exception.
	 */
	public void updateBranch(BranchData branchData) {

		logger.info("Start : Calling branch dao in updateBranch() method.");
		// get the branch dao.
		IBranchDAO dao = KLSDataAccessFactory.getBranchDAO();
		Branch master = BranchHelper.getBranch(branchData);
		// update the branch data to the db.
		try {
			dao.updateBranch(master);
		} catch (Exception excp) {
			logger.error("Branch data cannot be updated as branch id does not exist");
			throw new KlsRuntimeException(
					"Branch data cannot be updated as branch id does not exist",
					excp.getCause());
		}
		logger.info("End : Calling branch dao in updateBranch() method.");
	}

	/**
	 * This method will fetch all the branch records from the db and return to
	 * the client.
	 */
	public List<BranchData> getAllBranches() {

		logger.info("Start : Calling branch dao in getAllBranches() method.");
		// get the branch master dao.
		IBranchDAO dao = KLSDataAccessFactory.getBranchDAO();
		List<BranchData> branchMasterDataList = new ArrayList<BranchData>();
		try {
			List<Branch> branchMasterList = dao.getAllBranches();
			for (Branch masterData : branchMasterList) {
				branchMasterDataList
						.add(BranchHelper.getBranchData(masterData));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the branch records");
			throw new KlsRuntimeException(
					"Error in retrieving all the branch records",
					excp.getCause());
		}
		logger.info("End : Calling branch dao in getAllBranches() method.");
		return branchMasterDataList;
	}
}
