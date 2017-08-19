package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.BranchData;

/**
 * 
 * @author a9152
 * 
 */
public interface IBranchService {

	/**
	 * Save the branch data to the database.
	 * 
	 * @param branchData
	 */
	public void saveBranch(BranchData branchData);

	/**
	 * Updates the branch data to the database.
	 * 
	 * @param branchData
	 */
	public void updateBranch(BranchData branchData);

	/**
	 * Fetch all the branches from the database.
	 * 
	 * @return
	 */
	public List<BranchData> getAllBranches();
}
