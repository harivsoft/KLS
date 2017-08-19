/**
 * 
 */
package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.LandApplicationDetail;
import com.vsoftcorp.kls.data.LoanSanctionSummaryData;
import com.vsoftcorp.kls.data.LoanSummaryData;
import com.vsoftcorp.kls.data.PacsLoanApplicationDetailData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;


/**
 * @author a9152
 * 
 */
public class PacsLoanApplicationDetailHelper {

	private static final Logger logger = Logger.getLogger(PacsLoanApplicationDetailHelper.class);

	/**
	 * This method will convert the Entity class to
	 * getPacsLoanApplicationDetailData.
	 * 
	 * @param master
	 * @return
	 */
	public static PacsLoanApplicationDetailData getPacsLoanApplicationDetailData(PacsLoanApplicationDetail master) {

		PacsLoanApplicationDetailData data = new PacsLoanApplicationDetailData();
		data.setHeaderId(master.getHeaderId().getId());
		List<LandApplicationDetail> landApplicationDetailList = new ArrayList<LandApplicationDetail>();
		LandApplicationDetail landApplicationDetail = new LandApplicationDetail();
		/*
		 * BigDecimal calculatedAmount =
		 * MasterHelper.roundTo2DecimalPlaces(master .getCalculatedAmount());
		 */
		BigDecimal calculatedAmount = master.getCalculatedAmount().getAmount();
		if (calculatedAmount != null) {
			landApplicationDetail.setCalculatedAmount(calculatedAmount.toString());
		}
		if (master.getCropId() != null) {
			landApplicationDetail.setCropId(master.getCropId().getId());
			landApplicationDetail.setCropName(master.getCropId().getName());
		}
		if (master.getCustomerId() != null) {
//			landApplicationDetail.setCustomerId(master.getCustomerId().getId());
//			landApplicationDetail.setCustomerName(master.getCustomerId().getName());
			landApplicationDetail.setCustomerId(master.getCustomerId());
		}
		landApplicationDetail.setEnteredRemarks(master.getEnteredRemarks());
		landApplicationDetail.setId(master.getId());

		/*
		 * BigDecimal inspectionAmount =
		 * MasterHelper.roundTo2DecimalPlaces(master .getInspectionAmount());
		 */
		BigDecimal inspectionAmount = master.getInspectionAmount().getAmount();
		if (inspectionAmount != null) {
			landApplicationDetail.setInspectionAmount(inspectionAmount.toString());
		}
		landApplicationDetail.setInspectionRemarks(master.getInspectionRemarks());
		BigDecimal landArea = MasterHelper.roundTo2DecimalPlaces(master.getLandArea());
		if (landArea != null) {
			landApplicationDetail.setLandArea(landArea.toString());
		}
		if (master.getLandTypeId() != null) {
			landApplicationDetail.setLandTypeId(master.getLandTypeId().getId());
			landApplicationDetail.setLandTypeName(master.getLandTypeId().getName());
		}
		landApplicationDetail.setLoanApplicationNo(master.getLoanApplicationNo());
		landApplicationDetail.setPriority(master.getPriority());
		landApplicationDetail.setApplicationStatus(master.getApplicationStatus().getValue());
		/*
		 * BigDecimal recommendedAmount = MasterHelper
		 * .roundTo2DecimalPlaces(master.getRecommendedAmount());
		 */
		BigDecimal recommendedAmount = master.getRecommendedAmount().getAmount();
		if (recommendedAmount != null) {
			landApplicationDetail.setRecommendedAmount(recommendedAmount.toString());
		}
		/*
		 * BigDecimal requiredAmount = MasterHelper.roundTo2DecimalPlaces(master
		 * .getRequiredAmount());
		 */
		BigDecimal requiredAmount = master.getRequiredAmount().getAmount();
		if (requiredAmount != null) {
			landApplicationDetail.setRequiredAmount(requiredAmount.toString());
		}
		/*
		 * BigDecimal sanctionedAmount =
		 * MasterHelper.roundTo2DecimalPlaces(master .getSanctionedAmount());
		 */
		BigDecimal sanctionedAmount = master.getSanctionedAmount().getAmount();

		if (sanctionedAmount != null) {
			landApplicationDetail.setSanctionedAmount(sanctionedAmount.toString());
		}
		if (master.getSeasonId() != null) {
			landApplicationDetail.setSeasonId(master.getSeasonId().getId());
			landApplicationDetail.setSeasonName(master.getSeasonId().getName());
		}
		landApplicationDetailList.add(landApplicationDetail);
		data.setLandApplicationDetails(landApplicationDetailList);
		return data;
	}

	/**
	 * This method will convert the PacsLoanApplicationDetailData to Entity
	 * class.
	 * 
	 * @param data
	 * @return
	 */
	public static PacsLoanApplicationDetail getPacsLoanApplicationDetail(PacsLoanApplicationDetailData data) {

		PacsLoanApplicationDetail master = new PacsLoanApplicationDetail();
		return master;
	}

	public static List<PacsLoanApplicationDetail> getPacsLoanApplicationDetailList(PacsLoanApplicationDetailData data) {

		List<PacsLoanApplicationDetail> masterList = new ArrayList<PacsLoanApplicationDetail>();
		for (LandApplicationDetail landApplicationDetail : data.getLandApplicationDetails()) {
			PacsLoanApplicationDetail master = new PacsLoanApplicationDetail();
			String calculatedAmountString = landApplicationDetail.getCalculatedAmount();
			if (calculatedAmountString != null)
				master.setCalculatedAmount(Money.valueOf(Currency.getInstance("INR"), calculatedAmountString));
			else
				master.setCalculatedAmount(Money.ZERO);
			/*
			 * if (calculatedAmountString != null) { BigDecimal calculatedAmount
			 * = MasterHelper .roundTo2DecimalPlaces(calculatedAmountString);
			 * master.setCalculatedAmount(calculatedAmount); }
			 */
			Crop crop = new Crop();
			crop.setId(landApplicationDetail.getCropId());
			master.setCropId(crop);
			
//			Customer customer = new Customer();
//			customer.setCustomerId(landApplicationDetail.getCustId());
//			customer.setId(landApplicationDetail.getCustomerId());
//			customer.setName(landApplicationDetail.getCustomerName());
			master.setCustomerId(landApplicationDetail.getCustomerId());
			master.setEnteredRemarks(landApplicationDetail.getEnteredRemarks());
			PacsLoanApplicationHeader header = new PacsLoanApplicationHeader();
			header.setId(data.getHeaderId());
			master.setHeaderId(header);
			master.setId(landApplicationDetail.getId());
			String inspectionAmountString = landApplicationDetail.getInspectionAmount();
			if (inspectionAmountString != null) {
				/*
				 * BigDecimal inspectionAmount = MasterHelper
				 * .roundTo2DecimalPlaces(inspectionAmountString);
				 * master.setInspectionAmount(inspectionAmount);
				 */
				master.setInspectionAmount(Money.valueOf(Currency.getInstance("INR"), inspectionAmountString));
			}
			master.setInspectionRemarks(landApplicationDetail.getInspectionRemarks());
			BigDecimal landArea = MasterHelper.roundTo2DecimalPlaces(landApplicationDetail.getLandArea());

			master.setLandArea(landArea);
			LandType landType = new LandType();
			landType.setId(landApplicationDetail.getLandTypeId());
			master.setLandTypeId(landType);
			master.setLoanApplicationNo(landApplicationDetail.getLoanApplicationNo());
			master.setPriority(landApplicationDetail.getPriority());
			master.setApplicationStatus(LoanApplicationState.getType(landApplicationDetail.getApplicationStatus()));
			String recommendedAmountString = landApplicationDetail.getRecommendedAmount();
			if (recommendedAmountString != null) {
				/*
				 * BigDecimal recommendedAmount = MasterHelper
				 * .roundTo2DecimalPlaces(recommendedAmountString);
				 * master.setRecommendedAmount(recommendedAmount);
				 */
				master.setRecommendedAmount(Money.valueOf(Currency.getInstance("INR"), recommendedAmountString));
			}
			String requiredAmountString = landApplicationDetail.getRequiredAmount();
			if (requiredAmountString != null) {
				/*
				 * BigDecimal requiredAmount = MasterHelper
				 * .roundTo2DecimalPlaces(requiredAmountString);
				 * master.setRequiredAmount(requiredAmount);
				 */
				master.setRequiredAmount(Money.valueOf(Currency.getInstance("INR"), requiredAmountString));
			}
			String sanctionedAmountString = landApplicationDetail.getSanctionedAmount();
			if (sanctionedAmountString != null) {
				/*
				 * BigDecimal sanctionedAmount = MasterHelper
				 * .roundTo2DecimalPlaces(sanctionedAmountString);
				 * master.setSanctionedAmount(sanctionedAmount);
				 */
				master.setSanctionedAmount(Money.valueOf(Currency.getInstance("INR"), sanctionedAmountString));
			}
			Season season = new Season();
			season.setId(landApplicationDetail.getSeasonId());
			master.setSeasonId(season);
			if(null != landApplicationDetail.getInspectionAuthRemarks()){
				master.setInspectionAuthRemarks(landApplicationDetail.getInspectionAuthRemarks());				
			}
			masterList.add(master);
		}
		return masterList;
	}

	public static PacsLoanApplicationDetailData getPacsLoanApplicationDetailData(List<PacsLoanApplicationDetail> masterList) {

		logger.info("Start: Converting the pacs loan application detail entity data to pacs " + "loan application detail data in getPacsLoanApplicationDetailData() method.");
		PacsLoanApplicationDetailData data = new PacsLoanApplicationDetailData();
		List<LandApplicationDetail> landApplicationDetailList = new ArrayList<LandApplicationDetail>();
		BigDecimal loanedLand=BigDecimal.ZERO;
		for (PacsLoanApplicationDetail detailObj : masterList) {
			data.setHeaderId(detailObj.getHeaderId().getId());
			LandApplicationDetail landApplicationDetail = new LandApplicationDetail();
			/*
			 * BigDecimal calculatedAmount = MasterHelper
			 * .roundTo2DecimalPlaces(detailObj.getCalculatedAmount());
			 */
			if (detailObj.getCalculatedAmount() != null) {
				BigDecimal calculatedAmount = detailObj.getCalculatedAmount().getAmount();
				if (calculatedAmount != null) {
					landApplicationDetail.setCalculatedAmount((MoneyUtil.getAmountRoundedValue(calculatedAmount).toString()));

				}
			}
			if (detailObj.getCropId() != null) {
				landApplicationDetail.setCropId(detailObj.getCropId().getId());
				landApplicationDetail.setCropName(detailObj.getCropId().getName());
			}
			if (detailObj.getCustomerId() != null) {
//				landApplicationDetail.setCustomerId(detailObj.getCustomerId().getId());
//				landApplicationDetail.setCustomerName(detailObj.getCustomerId().getName());
				landApplicationDetail.setCustomerId(detailObj.getCustomerId());
				PersonData customer =RestClientUtil.getCustomerById(detailObj.getCustomerId());
				landApplicationDetail.setCustomerName(customer.getName());
				landApplicationDetail.setMemberNumber(customer.getMemberNumber());
			}
			landApplicationDetail.setEnteredRemarks(detailObj.getEnteredRemarks());
			landApplicationDetail.setId(detailObj.getId());

			/*
			 * BigDecimal inspectionAmount = MasterHelper
			 * .roundTo2DecimalPlaces(detailObj.getInspectionAmount());
			 */
			if (detailObj.getInspectionAmount() != null) {
				BigDecimal inspectionAmount = detailObj.getInspectionAmount().getAmount();
				if (inspectionAmount != null) {
					landApplicationDetail.setInspectionAmount((MoneyUtil.getAmountRoundedValue(inspectionAmount).toString()));
				}
			}
			landApplicationDetail.setInspectionRemarks(detailObj.getInspectionRemarks());
			BigDecimal landArea = MasterHelper.roundTo2DecimalPlaces(detailObj.getLandArea());
			if (landArea != null) {
				landApplicationDetail.setLandArea(landArea.toString());
			}
			if (detailObj.getLandTypeId() != null) {
				landApplicationDetail.setLandTypeId(detailObj.getLandTypeId().getId());
				landApplicationDetail.setLandTypeName(detailObj.getLandTypeId().getName());
			}
			landApplicationDetail.setLoanApplicationNo(detailObj.getLoanApplicationNo());
			landApplicationDetail.setPriority(detailObj.getPriority());
			landApplicationDetail.setApplicationStatus(detailObj.getApplicationStatus().getValue());
			/*
			 * BigDecimal recommendedAmount = MasterHelper
			 * .roundTo2DecimalPlaces(detailObj.getRecommendedAmount());
			 */
			if (detailObj.getRecommendedAmount() != null) {
				BigDecimal recommendedAmount = detailObj.getRecommendedAmount().getAmount();
				if (recommendedAmount != null) {
					landApplicationDetail.setRecommendedAmount((MoneyUtil.getAmountRoundedValue(recommendedAmount).toString()));
				}
			}
			/*
			 * BigDecimal requiredAmount = MasterHelper
			 * .roundTo2DecimalPlaces(detailObj.getRequiredAmount());
			 */
			if (detailObj.getRequiredAmount() != null) {
				BigDecimal requiredAmount = detailObj.getRequiredAmount().getAmount();
				if (requiredAmount != null) {
					landApplicationDetail.setRequiredAmount((MoneyUtil.getAmountRoundedValue(requiredAmount).toString()));
				}
			}
			if (detailObj.getSanctionedAmount() != null) {
				BigDecimal sanctionedAmount = detailObj.getSanctionedAmount().getAmount();

				if (sanctionedAmount != null) {
					landApplicationDetail.setSanctionedAmount((MoneyUtil.getAmountRoundedValue(sanctionedAmount).toString()));
				}
			}
			if (detailObj.getSeasonId() != null) {
				landApplicationDetail.setSeasonId(detailObj.getSeasonId().getId());
				landApplicationDetail.setSeasonName(detailObj.getSeasonId().getName());
			}

			landApplicationDetail.setSchemeName(detailObj.getHeaderId().getScheme().getName());
			landApplicationDetail.setProductName(detailObj.getHeaderId().getProduct().getName());

			if (detailObj.getShareAmount() != null)
				landApplicationDetail.setShareAmount(MoneyUtil.getAmountRoundedValue(detailObj.getShareAmount().getAmount()).toString());
			if (detailObj.getInsuranceAmount() != null)
				landApplicationDetail.setInsuranceAmount(MoneyUtil.getAmountRoundedValue(detailObj.getInsuranceAmount().getAmount()).toString());

			if (detailObj.getEligibleSanctionAmount() != null)
				landApplicationDetail.setTempSanctionAmount(MoneyUtil.getAmountRoundedValue(detailObj.getEligibleSanctionAmount().getAmount()).toString());
			
			loanedLand=loanedLand.add(detailObj.getLandArea());
			if(null != detailObj.getInspectionAuthRemarks()){
				landApplicationDetail.setInspectionAuthRemarks(detailObj.getInspectionAuthRemarks());				
			}

			landApplicationDetailList.add(landApplicationDetail);

		}

		List<BankParameter> bankParameterList = KLSDataAccessFactory.getBankParameterDAO().getAllBankParameters();

		if (bankParameterList != null && !bankParameterList.isEmpty()) {
			BankParameter bankParameter = bankParameterList.get(0);
			Money loanLimitPerFarmer = bankParameter.getLoanLimitPerFarmer();
			if (loanLimitPerFarmer != null)
				data.setLoanLimitPerFarmer((MoneyUtil.getAmountRoundedValue(loanLimitPerFarmer.getAmount()).toString()));
		}
		data.setLandApplicationDetails(landApplicationDetailList);
		data.setTotalLoanedLand(loanedLand);
		
		logger.info("End: Converting the pacs loan application detail entity data to pacs " + "loan application detail data in getPacsLoanApplicationDetailData() method.");
		return data;
	}

	public static LoanSanctionSummaryData getLoanSanctionSummaryData(List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList) {

		LoanSanctionSummaryData summaryData = new LoanSanctionSummaryData();
		LoanSummaryData pendingData = new LoanSummaryData();
		LoanSummaryData sanctionedData = new LoanSummaryData();
		Money pendingAmountAsPerSOF = Money.ZERO;
		Money pendingAmountInspected = Money.ZERO;
		Money pendingAmountRecomended = Money.ZERO;
		Money pendingAmountRequired = Money.ZERO;
		Money pendingAmountsanctioned = Money.ZERO;
		BigDecimal pendingCultivatedLand = new BigDecimal(0);
		Integer pendingAppSize = 0;

		Money sactionedAmountAsPerSOF = Money.ZERO;
		Money sactionedAmountInspected = Money.ZERO;
		Money sactionedAmountRecomended = Money.ZERO;
		Money sactionedAmountRequired = Money.ZERO;
		Money sactionedAmountsanctioned = Money.ZERO;
		BigDecimal sactionedCultivatedLand = new BigDecimal(0);
		Integer sactionedAppSize = 0;

		try {

			Set<Long> pendingCustomerIdSet = new HashSet<Long>();
			Set<Long> sanctionedCustomerIdSet = new HashSet<Long>();
			if (pacsLoanApplicationDetailList.size() > 0) {
				for (PacsLoanApplicationDetail pacsLoanApplicationDetail : pacsLoanApplicationDetailList) {

					if (pacsLoanApplicationDetail.getApplicationStatus().getValue() == LoanApplicationState.INSPECTED.getValue()) {
						pendingAppSize += 1;
						pendingAmountAsPerSOF = pendingAmountAsPerSOF.add(pacsLoanApplicationDetail.getCalculatedAmount() == null ? Money.ZERO : pacsLoanApplicationDetail.getCalculatedAmount());
						pendingAmountInspected = pendingAmountInspected.add(pacsLoanApplicationDetail.getInspectionAmount() == null ? Money.ZERO : pacsLoanApplicationDetail.getInspectionAmount());
						pendingAmountRecomended = pendingAmountRecomended.add(pacsLoanApplicationDetail.getRecommendedAmount() == null ? Money.ZERO : pacsLoanApplicationDetail.getRecommendedAmount());
						pendingAmountRequired = pendingAmountRequired.add(pacsLoanApplicationDetail.getRequiredAmount() == null ? Money.ZERO : pacsLoanApplicationDetail.getRequiredAmount());

						pendingAmountsanctioned = pendingAmountsanctioned.add(pacsLoanApplicationDetail.getSanctionedAmount() == null ? Money.ZERO : pacsLoanApplicationDetail.getSanctionedAmount());

						pendingCultivatedLand = pendingCultivatedLand.add(pacsLoanApplicationDetail.getLandArea() == null ? pendingCultivatedLand : pacsLoanApplicationDetail.getLandArea());
						pendingCustomerIdSet.add(pacsLoanApplicationDetail.getCustomerId());
					}
				}
				for (PacsLoanApplicationDetail pacsLoanApplicationDetail : pacsLoanApplicationDetailList) {

					if (pacsLoanApplicationDetail.getApplicationStatus().getValue() == LoanApplicationState.SANCTIONED.getValue()) {
						sactionedAppSize += 1;
						sactionedAmountAsPerSOF = sactionedAmountAsPerSOF.add(pacsLoanApplicationDetail.getCalculatedAmount() == null ? Money.ZERO : pacsLoanApplicationDetail.getCalculatedAmount());
						sactionedAmountInspected = sactionedAmountInspected.add(pacsLoanApplicationDetail.getInspectionAmount() == null ? Money.ZERO : pacsLoanApplicationDetail.getInspectionAmount());
						sactionedAmountRecomended = sactionedAmountRecomended.add(pacsLoanApplicationDetail.getRecommendedAmount() == null ? Money.ZERO : pacsLoanApplicationDetail
								.getRecommendedAmount());
						sactionedAmountRequired = sactionedAmountRequired.add(pacsLoanApplicationDetail.getRequiredAmount() == null ? Money.ZERO : pacsLoanApplicationDetail.getRequiredAmount());
						sactionedAmountsanctioned = sactionedAmountsanctioned.add(pacsLoanApplicationDetail.getSanctionedAmount() == null ? Money.ZERO : pacsLoanApplicationDetail
								.getSanctionedAmount());
						sactionedCultivatedLand = sactionedCultivatedLand.add(pacsLoanApplicationDetail.getLandArea() == null ? sactionedCultivatedLand : pacsLoanApplicationDetail.getLandArea());
						sanctionedCustomerIdSet.add(pacsLoanApplicationDetail.getCustomerId());
					}

				}
			}
			pendingData.setTotalNoOfAppls(pendingAppSize.toString());
			pendingData.setAmountAsPerSOF(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(pendingAmountAsPerSOF.getAmount())).getAmount().toString());
			pendingData.setAmountInspected(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(pendingAmountInspected.getAmount())).getAmount().toString());
			pendingData.setAmountRecomended(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(pendingAmountRecomended.getAmount())).getAmount().toString());
			pendingData.setAmountRequired(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(pendingAmountRequired.getAmount())).getAmount().toString());
			pendingData.setAmountSanctioned(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(pendingAmountsanctioned.getAmount())).getAmount().toString());
			pendingData.setCultivatedLand(pendingCultivatedLand.toPlainString());
			pendingData.setTotalNoOfFarmers(String.valueOf(pendingCustomerIdSet.size()));
			summaryData.setPendingSummary(pendingData);

			sanctionedData.setTotalNoOfAppls(sactionedAppSize.toString());
			sanctionedData.setAmountAsPerSOF(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(sactionedAmountAsPerSOF.getAmount())).getAmount().toString());
			sanctionedData.setAmountInspected(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(sactionedAmountInspected.getAmount())).getAmount().toString());
			sanctionedData.setAmountRecomended(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(sactionedAmountRecomended.getAmount())).getAmount().toString());
			sanctionedData.setAmountRequired(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(sactionedAmountRequired.getAmount())).getAmount().toString());
			sanctionedData.setAmountSanctioned(Money.valueOf(Currency.getInstance("INR"), MoneyUtil.getAmountRoundedValue(sactionedAmountsanctioned.getAmount())).getAmount().toString());
			sanctionedData.setCultivatedLand(sactionedCultivatedLand.toPlainString());
			sanctionedData.setTotalNoOfFarmers(String.valueOf(sanctionedCustomerIdSet.size()));
			summaryData.setSanctionSummary(sanctionedData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return summaryData;
	}
}
