package com.vsoftcorp.kls.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.Activity;
import com.vsoftcorp.kls.business.entities.LoanProductPurposeMapping;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.business.entities.SubPurpose;
import com.vsoftcorp.kls.data.LoanProductPurposeMappingData;
import com.vsoftcorp.kls.data.SubPurposeActivityData;

public class LoanProductPurposeMappingHelper {
	public static LoanProductPurposeMappingData getLoanProductPurposeMappingData(
			List<LoanProductPurposeMapping> masterLst) {
		LoanProductPurposeMappingData data = new LoanProductPurposeMappingData();
		List<SubPurposeActivityData> loanActivityDataList = new ArrayList<SubPurposeActivityData>();
		SubPurposeActivityData SubPurposeActivityData = null;

		for (LoanProductPurposeMapping loanProductPurposeMapping : masterLst) {
			SubPurposeActivityData = new SubPurposeActivityData();

			data.setProductId(loanProductPurposeMapping.getProduct().getId());
			data.setPurposeId(loanProductPurposeMapping.getPurpose().getId());
			SubPurposeActivityData.setId(loanProductPurposeMapping.getId()
					.toString());
			SubPurposeActivityData.setActivityId(loanProductPurposeMapping
					.getActivity().getId());
			SubPurposeActivityData.setActivityName(loanProductPurposeMapping
					.getActivity().getName());
			if (loanProductPurposeMapping.getSubPurpose() != null) {
				SubPurposeActivityData
						.setSubPurposeId(loanProductPurposeMapping
								.getSubPurpose().getId());
				SubPurposeActivityData
						.setSubPurposeName(loanProductPurposeMapping
								.getSubPurpose().getName());
			}
			loanActivityDataList.add(SubPurposeActivityData);
		}
		data.setSubPurposeActivityList(loanActivityDataList);

		return data;

	}

	public static List<LoanProductPurposeMapping> getLoanProductPurposeMapping(
			LoanProductPurposeMappingData data) {
		List<LoanProductPurposeMapping> list = new ArrayList<LoanProductPurposeMapping>();
		LoanProductPurposeMapping loanProductPurposeMapping = null;
		List<SubPurposeActivityData> subPurposeActivityDataLst = data
				.getSubPurposeActivityList();

		for (SubPurposeActivityData subPurposeActivityData : subPurposeActivityDataLst) {
			loanProductPurposeMapping = new LoanProductPurposeMapping();

			if (subPurposeActivityData.getId() != null)
				loanProductPurposeMapping.setId(Long
						.parseLong(subPurposeActivityData.getId() + ""));
			SubPurpose subPurpose = new SubPurpose();
			if (subPurposeActivityData.getSubPurposeId() != null) {
				subPurpose.setId(subPurposeActivityData.getSubPurposeId());
				subPurpose.setName(subPurposeActivityData.getSubPurposeName());
				loanProductPurposeMapping.setSubPurpose(subPurpose);
			}

			Activity activity = new Activity();
			activity.setId(subPurposeActivityData.getActivityId());
			loanProductPurposeMapping.setActivity(activity);

			Purpose purpose = new Purpose();
			purpose.setId(data.getPurposeId());
			loanProductPurposeMapping.setPurpose(purpose);

			Product product = new Product();
			product.setId(data.getProductId());
			loanProductPurposeMapping.setProduct(product);
			
			list.add(loanProductPurposeMapping);
		}
		return list;
	}
}
