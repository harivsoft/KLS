package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Branch;

public interface IBranchDAO {

	/**
	 * Saves the branch data to the database.
	 * 
	 * @param branch
	 */
	public void saveBranch(Branch branch);

	/**
	 * Updates the existing branch data to the database.
	 * 
	 * @param branch
	 */
	public void updateBranch(Branch branch);

	/**
	 * Checks if the branch id exists in the database.
	 * 
	 * @param branch
	 * @return Branch
	 */
	public Branch getBranch(Branch branch);

	/**
	 * Returns all the branch records to the client.
	 * 
	 * @return
	 */
	public List<Branch> getAllBranches();

}
