package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.EventTypeDefinition;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.ProductType;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.ProductData;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.ISchemeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.valuetypes.loanproduct.DisbursementType;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestCalculationMethod;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestPostingFrequency;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestType;
import com.vsoftcorp.kls.valuetypes.loanproduct.PenalInterestApplicable;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentSchedule;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentType;

/**
 * @author a9153
 * 
 *         Helper Class for conversion from pojo to entity and vice versa.
 */

public class ProductHelper {

	/**
	 * The method converts product entity to product pojo.
	 * 
	 * @param theMaster
	 * @return
	 */
	public static ProductData getProductMasterData(Product theMaster) {

		ProductData data = new ProductData();
		if (theMaster != null) {
			data.setId(theMaster.getId().toString());
			data.setName(theMaster.getName());
			if (theMaster.getProductType() != null) {
				data.setProductTypeId(theMaster.getProductType().getId().toString());
				data.setProductTypeDesc(theMaster.getProductType().getDescription());
			}
			data.setShortName(theMaster.getShortName());
			data.setAtmApplicable(theMaster.getAtmApplicable());
			data.setReleasedFlag(theMaster.getReleasedFlag());
			data.setLastIntPostDate(DateUtil.getDateString(theMaster.getLastIntPostDate()));
			data.setReleasedDate(DateUtil.getDateString(theMaster.getReleasedDate()));
			if (theMaster.getIntrCategory() != null) {
				data.setIntrCategoryId(theMaster.getIntrCategory().getId().toString());
				data.setIntrCategoryDesc(theMaster.getIntrCategory().getIntrCategoryDesc());
			}
			data.setSharePercentage(theMaster.getSharePercentage().toString());
			data.setIsPenalInterestAllowed(theMaster.getIsPenalInterestAllowed());
			data.setPenalInterestOn(theMaster.getPenalInterestOn());
			if (theMaster.getRecoverySequence() != null) {
				data.setRecoverySequenceId(Integer.toString(theMaster.getRecoverySequence().getId()));
				data.setRecoverySequence(theMaster.getRecoverySequence().getSequenceName());
			}
			DisbursementType disbursementType = theMaster.getDisbursementType();
			if (disbursementType != null) {
				data.setDisbursementType(disbursementType.getValue());
			}
			data.setMinPeriod(theMaster.getMinPeriod());
			data.setMaxPeriod(theMaster.getMaxPeriod());
			data.setMoratoriumPrinciplePeriod(theMaster.getMoratoriumPrinciplePeriod());
			data.setMoratoriumInterestPeriod(theMaster.getMoratoriumInterestPeriod());
			data.setMoratoriumPeriodIncludedInLoan(theMaster.getMoratoriumPeriodIncludedInLoan());
			data.setLoanProductCode(theMaster.getLoanProductCode());
			RepaymentType repaymentType = theMaster.getRepaymentType();
			if (repaymentType != null) {
				data.setRepaymentType(repaymentType.getValue());
			}
			RepaymentSchedule repaymentSchedule = theMaster.getRepaymentSchedule();
			if (repaymentSchedule != null) {
				data.setRepaymentSchedule(repaymentSchedule.getValue());
			}
			InterestCalculationMethod interestCalcMethod = theMaster.getInterestCalcMethod();
			if (interestCalcMethod != null) {
				data.setInterestCalcMethod(interestCalcMethod.getValue());
			}
			PenalInterestApplicable penalIntApplicable = theMaster.getPenalInterestApplicable();
			if (penalIntApplicable != null) {
				data.setPenalInterestApplicable(penalIntApplicable.getValue());
			}
			InterestType interestType = theMaster.getInterestType();
			if (interestType != null) {
				data.setInterestType(interestType.getValue());
			}
			InterestPostingFrequency interestpostFreq = theMaster.getInterestPostFreq();
			if (interestpostFreq != null) {
				data.setInterestPostFreq(interestpostFreq.getValue());
			}
			data.setGuarantorsReqd(theMaster.getGuarantorsReqd());
			data.setDocRequired(theMaster.getDocRequired());
			data.setInsuranceRequired(theMaster.getInsuranceRequired());
			data.setSecurityRequired(theMaster.getSecurityRequired());
			data.setSubsidy(theMaster.getSubsidy());
			data.setProcessingFee(theMaster.getProcessingFee());
			data.setPreClosureMinPeriod(theMaster.getPreClosureMinPeriod());
			data.setPreClosureMinChargedPeriod(theMaster.getPreClosureMinChargedPeriod());
			data.setPreClosureIntRecoverablePeriod(theMaster.getPreClosureIntRecoverablePeriod());
			data.setAfterClosureMinPeriod(theMaster.getAfterClosureMinPeriod());
			data.setProductType(theMaster.getProductType().getLoanType());
			data.setAsAndWhenImplemented(theMaster.getAsAndWhenImplemented());
			if(theMaster.getSchemeId()!=null){
				data.setSchemeId(theMaster.getSchemeId());
				ISchemeDAO pDao = KLSDataAccessFactory.getSchemeDAO();
				Scheme scheme = new Scheme();
				scheme.setId(theMaster.getSchemeId());
				scheme = pDao.getScheme(scheme, false);
				data.setSchemeName(scheme.getName());
			}
			Money maxShareAmt = theMaster.getMaxShareAmount();
			if (maxShareAmt != null)
				data.setMaxShareAmount(maxShareAmt.getAmount().setScale(2));
			
			data.setBorrowingProductId(theMaster.getBorrowingProductId());
			data.setBorrowingRequired(theMaster.getBorrowingRequired());
		}
		return data;
	}

	/**
	 * This method converts product pojo and product entity.
	 * 
	 * @param data
	 * @return
	 */
	public static Product getProduct(ProductData data) {
		Product master = new Product();

		InterestCategory intCategory = new InterestCategory();
		ProductType productType = new ProductType();
		EventTypeDefinition eventDef = new EventTypeDefinition();

		if (data.getId() != null)
			master.setId(Integer.parseInt(data.getId()));
		master.setName(data.getName());
		master.setShortName(data.getShortName());
		master.setReleasedDate(DateUtil.getVSoftDateByString(data.getReleasedDate()));
		master.setLastIntPostDate(DateUtil.getVSoftDateByString(data.getReleasedDate()));
		master.setAtmApplicable(data.getAtmApplicable());
		if (master.getReleasedDate() != null)
			master.setReleasedFlag("Y");
		else
			master.setReleasedFlag("N");
		if (data.getIntrCategoryId() != null)
			intCategory.setId(Integer.parseInt(data.getIntrCategoryId()));
		intCategory.setIntrCategoryDesc(data.getIntrCategoryDesc());
		productType.setId(Integer.parseInt(data.getProductTypeId()));
		productType.setDescription(data.getProductTypeDesc());
		master.setIntrCategory(intCategory);
		master.setProductType(productType);
		if (data.getSharePercentage() != null)
			master.setSharePercentage(BigDecimal.valueOf(Double.parseDouble(data.getSharePercentage())));
		if (data.getIsPenalInterestAllowed().equalsIgnoreCase("Y")) {
			master.setIsPenalInterestAllowed("Y");
			master.setPenalInterestOn(data.getPenalInterestOn());
		} else
			master.setIsPenalInterestAllowed("N");
		eventDef.setId(Integer.parseInt(data.getRecoverySequenceId()));
		eventDef.setSequenceName(data.getRecoverySequence());
		master.setRecoverySequence(eventDef);
		String disbursementType = data.getDisbursementType();
		if (disbursementType != null) {
			master.setDisbursementType(DisbursementType.getType(disbursementType));
		} else {
			master.setDisbursementType(DisbursementType.NOT_APPLICABLE);
		}
		master.setMinPeriod(data.getMinPeriod());
		master.setMaxPeriod(data.getMaxPeriod());
		master.setMoratoriumPrinciplePeriod(data.getMoratoriumPrinciplePeriod());
		master.setMoratoriumInterestPeriod(data.getMoratoriumInterestPeriod());
		master.setMoratoriumPeriodIncludedInLoan(data.getMoratoriumPeriodIncludedInLoan());
		master.setLoanProductCode(data.getLoanProductCode());
		String repaymentType = data.getRepaymentType();
		if (repaymentType != null) {
			master.setRepaymentType(RepaymentType.getType(repaymentType));
		} else {
			master.setRepaymentType(RepaymentType.NOT_APPLICABLE);
		}
		String repaymentSchedule = data.getRepaymentSchedule();
		if (repaymentSchedule != null) {
			master.setRepaymentSchedule(RepaymentSchedule.getType(repaymentSchedule));
		} else {
			master.setRepaymentSchedule(RepaymentSchedule.NOT_APPLICABLE);
		}
		String interestCalcMethod = data.getInterestCalcMethod();
		if (interestCalcMethod != null) {
			master.setInterestCalcMethod(InterestCalculationMethod.getType(interestCalcMethod));
		} else {
			master.setInterestCalcMethod(InterestCalculationMethod.NOT_APPLICABLE);
		}
		String penalInterestApplicable = data.getPenalInterestApplicable();
		if (penalInterestApplicable != null) {
			master.setPenalInterestApplicable(PenalInterestApplicable.getType(penalInterestApplicable));
		} else {
			master.setPenalInterestApplicable(PenalInterestApplicable.NOT_APPLICABLE);
		}
		String interestType = data.getInterestType();
		if (interestType != null) {
			master.setInterestType(InterestType.getType(interestType));
		} else {
			master.setInterestType(InterestType.NOT_APPLICABLE);
		}
		String interestPostingFrequency = data.getInterestPostFreq();
		if (interestPostingFrequency != null) {
			master.setInterestPostFreq(InterestPostingFrequency.getType(interestPostingFrequency));
		} else {
			master.setInterestPostFreq(InterestPostingFrequency.NOT_APPLICABLE);
		}
		master.setGuarantorsReqd(data.getGuarantorsReqd());
		master.setDocRequired(data.getDocRequired());
		master.setInsuranceRequired(data.getInsuranceRequired());
		master.setSecurityRequired(data.getSecurityRequired());
		master.setSubsidy(data.getSubsidy());
		master.setProcessingFee(data.getProcessingFee());
		master.setPreClosureMinPeriod(data.getPreClosureMinPeriod());
		master.setPreClosureMinChargedPeriod(data.getPreClosureMinChargedPeriod());
		master.setPreClosureIntRecoverablePeriod(data.getPreClosureIntRecoverablePeriod());
		master.setAfterClosureMinPeriod(data.getAfterClosureMinPeriod());
		master.setAsAndWhenImplemented(data.getAsAndWhenImplemented());
		if(data.getSchemeId()!=null){
			master.setSchemeId(data.getSchemeId());
		}
		
		BigDecimal maxShareAmt = data.getMaxShareAmount();
		if (maxShareAmt != null) {
			Money maxShareAmtMoney = Money.valueOf(Currency.getInstance("INR"), maxShareAmt);
			master.setMaxShareAmount(maxShareAmtMoney);
		}
		
		master.setBorrowingProductId(data.getBorrowingProductId());
		master.setBorrowingRequired(data.getBorrowingRequired());

		return master;
	}
}
