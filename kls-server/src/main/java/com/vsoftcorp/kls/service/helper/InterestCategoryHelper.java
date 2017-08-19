package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.data.InterestCategoryData;

/**
 * @author a9153
 *
 *Helper Class for conversion from pojo to entity and vice versa.
 */

public class InterestCategoryHelper {
	/**
	 * The method converts Interest category entity to its corresponding pojo.
	 * @param master
	 * @return
	 */
	public static InterestCategoryData getInterestCategoryData(InterestCategory master) {
		InterestCategoryData data = new InterestCategoryData();
		data.setIntrCategoryId(master.getId());
		data.setIntrCategoryDesc(master.getIntrCategoryDesc());
		return data;
	}

	/**
	 * This method converts Interest category pojo to its corresponding entity
	 * @param data
	 * @return
	 */
	public static InterestCategory getInterestCategory(InterestCategoryData data) {
		InterestCategory master = new InterestCategory();
		if(data.getIntrCategoryId()!=null)
		master.setId(data.getIntrCategoryId());
		master.setIntrCategoryDesc(data.getIntrCategoryDesc());
		return master;
	}
}
