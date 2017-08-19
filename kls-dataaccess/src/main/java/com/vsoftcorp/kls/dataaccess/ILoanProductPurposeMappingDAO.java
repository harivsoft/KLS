package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Activity;
import com.vsoftcorp.kls.business.entities.LoanProductPurposeMapping;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.business.entities.SubPurpose;

public interface ILoanProductPurposeMappingDAO {
	public void saveLoanProductPurposeMapping(LoanProductPurposeMapping master);

	/*
	 * public void updateLoanProductPurposeMapping(LoanProductPurposeMapping
	 * master);
	 */public void deleteLoanProductPurposeMapping(Long id);

	public List<LoanProductPurposeMapping> getLoanProductPurposeMapping(
			String productId, String purposeId);

	public List<Purpose> getPurposeBasedOnProductId(Integer productId);

	public List<SubPurpose> getSubPurposeBasedOnProductIdAndPurposeId(
			Integer productId, Integer purposeId);

	public List<Activity> getActivityBasedOnProductAndPurposeAndSubPurpseId(
			Integer productId, Integer purposeId, Integer subPurposeId);

}
