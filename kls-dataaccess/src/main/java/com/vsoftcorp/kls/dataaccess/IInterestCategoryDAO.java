package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.InterestCategory;

/**
 * 
 * @author a9153
 *
 */

public interface IInterestCategoryDAO {

	public void saveInterestCategory(InterestCategory interestCategory);

	public void updateInterestCategory(InterestCategory interestCategory);
	
	public InterestCategory getInterestCategory(InterestCategory interestCategory,boolean isCloseSession);
	
	public List<InterestCategory> getAllInterestCategories(boolean isCloseSession);

	public InterestCategory getInterestCategoryById(Integer interestCategoryId);
}
