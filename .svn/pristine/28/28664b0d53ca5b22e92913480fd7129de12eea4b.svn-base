package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.ActivityData;
import com.vsoftcorp.kls.data.LoanProductPurposeMappingData;
import com.vsoftcorp.kls.data.PurposeData;
import com.vsoftcorp.kls.data.SubPurposeData;

public interface ILoanProductPurposeMappingService {
	public void saveLoanProductPurposeMapping(LoanProductPurposeMappingData data);

	public List<LoanProductPurposeMappingData> getLoanProductPurposeMapping(
			String productId, String purposeId);

	public List<PurposeData> getAllPurposesBasedOnProduct(Integer productId);

	public List<SubPurposeData> getAllSubPurposeBasedOnProductAndPurpose(
			Integer productId, Integer purposeId);

	public List<ActivityData> getAllActivityBasedOnProductPurposeAndSubPurpose(
			Integer productId, Integer purposeId, Integer subPurposeId);
}
