package com.vsoftcorp.kls.service.helper.subsidy;

import com.vsoftcorp.camelintg.util.MoneyUtil;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.subsidy.InterestSubsidy;
import com.vsoftcorp.kls.business.entity.subsidy.SlabwisesubsidyInterestRate;
import com.vsoftcorp.kls.subsidy.data.SlabwisesubsidyInterestRateData;

public class SlabwisesubsidyROIHelper {

	public static SlabwisesubsidyInterestRate getSlabwisesubsidyInterestRate(
			SlabwisesubsidyInterestRateData data) {

		SlabwisesubsidyInterestRate master = new SlabwisesubsidyInterestRate();

		if (data.getId() != null) {
			master.setId(data.getId());
		}
		master.setFromAmount(MoneyUtil.getAccountingMoney(data.getFromAmount()).getMoney());
		master.setToAmount(MoneyUtil.getAccountingMoney(data.getToAmount()).getMoney());
		if (data.getInterestSubsidyId() != null) {
			InterestSubsidy interestsubsidy = new InterestSubsidy();
			interestsubsidy.setId(data.getInterestSubsidyId());
			master.setInterestSubsidy(interestsubsidy);
		}
		master.setSubsidyRoiPerAnnum(data.getSubsidyRoiPerAnnum());

		return master;
	}
	
	public static SlabwisesubsidyInterestRateData getSlabwisesubsidyInterestRateData(
			SlabwisesubsidyInterestRate master) {

		SlabwisesubsidyInterestRateData data = new SlabwisesubsidyInterestRateData();

		if (master.getId() != null) {
			data.setId(master.getId());
		}
		data.setFromAmount(master.getFromAmount().getAmount());
		data.setToAmount(master.getToAmount().getAmount());
		data.setInterestSubsidyId(master.getInterestSubsidy().getId());
		data.setSubsidyRoiPerAnnum(master.getSubsidyRoiPerAnnum());

		return data;
	}

}
