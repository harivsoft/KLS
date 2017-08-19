package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.subsidy.SlabwisesubsidyInterestRate;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.subsidy.ISubsidyInterestAmountsDAO;
import com.vsoftcorp.kls.report.service.data.InterestSubsidyReportData;

public class InterestSubsidyReportHelper {
	private static final Logger logger = Logger.getLogger(InterestSubsidyReportHelper.class);
	public static List<InterestSubsidyReportData> getInterestSubsidyReportDataList(List<Map> subsidyList, Long subsidySchemeId) {

		logger.info("Start: inside method getInterestSubsidyReportDataList()");
		InterestSubsidyReportData data = new InterestSubsidyReportData();
		List<InterestSubsidyReportData> interestSubsidyReportDataList = new ArrayList<InterestSubsidyReportData>();
		Map<Long,InterestSubsidyReportData> tempMap = new HashMap<Long,InterestSubsidyReportData>();
		List<SlabwisesubsidyInterestRate> subsidyROIList = KLSDataAccessFactory.getSlabwisesubsidyROIDAO().getSlabwisesubsidyROIDAO(subsidySchemeId);
		try {
			
				if (subsidyROIList != null && !subsidyROIList.isEmpty()) {
					for (SlabwisesubsidyInterestRate ss : subsidyROIList) {
						InterestSubsidyReportData subdata = new InterestSubsidyReportData();
						subdata.setFromSlab(ss.getFromAmount().getAmount().toString());
						subdata.setToSlab(ss.getToAmount().getAmount().toString());
						subdata.setNumOfAccountsInSlab(0);
						subdata.setSubsidyAmtInSlab(BigDecimal.ZERO);
						subdata.setAmountInSlab(BigDecimal.ZERO);
						tempMap.put(ss.getId(), subdata);
					}


				ISubsidyInterestAmountsDAO iDao = KLSDataAccessFactory
						.getSubsidyInterestAmountsDAO();

				BigDecimal amountRecovered = BigDecimal.ZERO;
				BigDecimal subvention = BigDecimal.ZERO;
				BigDecimal roi = BigDecimal.ZERO;
				BigDecimal subsidyReceivable = BigDecimal.ZERO;
				SlabwisesubsidyInterestRate sroi = new SlabwisesubsidyInterestRate();
				for (Map map : subsidyList) {
					Money sanctionedAmt = (Money) map.get("sanctionedAmount");
					sroi = getSubsidySlab(subsidyROIList, sanctionedAmt);
					if (((sanctionedAmt.getAmount().compareTo(sroi.getFromAmount().getAmount()) == 1) && (sanctionedAmt.getAmount().compareTo(sroi.getToAmount().getAmount()) == -1))
							|| (sanctionedAmt.getAmount().compareTo(sroi.getToAmount().getAmount()) == 0)) {
						
						data = tempMap.get(sroi.getId());
						if (data.getFromSlab() == null) {
							data.setFromSlab(sroi.getFromAmount().getAmount().toString());
						}
						if (data.getToSlab() == null) {
							data.setToSlab(sroi.getToAmount().getAmount().toString());
						}
						AccountingMoney paidAmt = (AccountingMoney) map.get("paidAmount");
						amountRecovered = paidAmt.getMoney().getAmount();
						BigDecimal subSecvable = (BigDecimal) map.get("subsidyReceivable");
						subsidyReceivable = subSecvable.setScale(2,RoundingMode.HALF_UP);
						roi = getSubsidyROI(subsidyROIList, sanctionedAmt);
						if (roi.compareTo(BigDecimal.ZERO)!=0) {
							subvention = subsidyReceivable.divide(roi, 2);
						}

						if (data.getAmountInSlab() != null) {
							data.setAmountInSlab(data.getAmountInSlab().add(amountRecovered.abs()).setScale(2, RoundingMode.HALF_UP));
						} else {
							data.setAmountInSlab(amountRecovered.abs().setScale(2, RoundingMode.HALF_UP));
						}
						if (data.getSubsidyAmtInSlab() != null) {
							data.setSubsidyAmtInSlab(data.getSubsidyAmtInSlab().add(subsidyReceivable.abs()).setScale(2, RoundingMode.HALF_UP));
						} else {
							data.setSubsidyAmtInSlab(subsidyReceivable.abs().setScale(2, RoundingMode.HALF_UP));
						}
						if (data.getNumOfAccountsInSlab() != null) {
							data.setNumOfAccountsInSlab(data.getNumOfAccountsInSlab() + 1);
						} else {
							data.setNumOfAccountsInSlab(1);
						}
						if (data.getSubventionAmtInSlab() != null) {
							data.setSubventionAmtInSlab(data.getSubventionAmtInSlab().add(subvention.abs()).setScale(2, RoundingMode.HALF_UP));
						} else {
							data.setSubventionAmtInSlab(subvention.abs().setScale(2, RoundingMode.HALF_UP));
						}
						tempMap.put(sroi.getId(), data);
					}
				}
			}

						
			interestSubsidyReportDataList= new ArrayList<InterestSubsidyReportData>(tempMap.values());
		} catch (Exception e) {
			logger.error("Exception while retriving records for Account statement report");
			e.printStackTrace();
		}
		logger.info("End: inside method getInterestSubsidyReportDataList()");

		return interestSubsidyReportDataList;
	}

	public static BigDecimal getSubsidyROI(List<SlabwisesubsidyInterestRate> subsidyROIList, Money sanctionAmount){
		BigDecimal roi=BigDecimal.ZERO;
		for(SlabwisesubsidyInterestRate slab : subsidyROIList){
			if(sanctionAmount.compareTo(slab.getFromAmount()) > 0 && sanctionAmount.compareTo(slab.getToAmount()) < 0){
				roi=slab.getSubsidyRoiPerAnnum();
			}
		}
		return roi;
	}
	public static SlabwisesubsidyInterestRate getSubsidySlab(List<SlabwisesubsidyInterestRate> subsidyROIList, Money sanctionAmount){
		SlabwisesubsidyInterestRate slab = new SlabwisesubsidyInterestRate();
		for(SlabwisesubsidyInterestRate s : subsidyROIList){
			
	if(sanctionAmount.compareTo(s.getFromAmount()) >0 && sanctionAmount.compareTo(s.getToAmount()) < 0 || sanctionAmount.compareTo(s.getToAmount()) == 0){
				slab=s;
				
			}
		}
		return slab;
	}
	
}
