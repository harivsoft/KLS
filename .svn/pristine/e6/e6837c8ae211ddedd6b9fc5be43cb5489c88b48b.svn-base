package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.data.BranchData;

public class BranchHelper {

	/**
	 * This method will convert the Entity class to BranchData.
	 * 
	 * @param master
	 * @return
	 */
	public static BranchData getBranchData(Branch master) {

		BranchData data = new BranchData();
		data.setCode(master.getCode());
		data.setName(master.getName());
		data.setLocation(master.getLocation());
		data.setId(master.getId());
		return data;
	}

	/**
	 * This method will convert the BranchData to Entity class.
	 * 
	 * @param data
	 * @return
	 */
	public static Branch getBranch(BranchData data) {

		Branch master = new Branch();
		master.setCode(data.getCode());
		master.setName(data.getName());
		master.setLocation(data.getLocation());
		master.setId(data.getId());
		return master;
	}

}
