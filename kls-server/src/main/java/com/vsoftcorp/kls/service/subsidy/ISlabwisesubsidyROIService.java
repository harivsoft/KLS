package com.vsoftcorp.kls.service.subsidy;

import java.util.List;

import com.vsoftcorp.kls.subsidy.data.SlabwisesubsidyInterestRateData;

public interface ISlabwisesubsidyROIService {
	
	public void saveSlabwisesubsidyROIService(SlabwisesubsidyInterestRateData data);
	
	public void modifySlabwisesubsidyROIService(SlabwisesubsidyInterestRateData data);
	
	public List<SlabwisesubsidyInterestRateData> getSlabwisesubsidyROIService(Long id);

}
