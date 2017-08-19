/**
 * 
 */
package com.vsoftcorp.kls.service.helper;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.BankParameterData;
import com.vsoftcorp.kls.valuetypes.BankProcessStatus;
import com.vsoftcorp.kls.valuetypes.CBSMethodEnum;
import com.vsoftcorp.kls.valuetypes.transaction.BorrowingTransactionMethod;

/**
 * @author a9152
 * 
 */
public class BankParameterHelper {
	private static final Logger logger = Logger
			.getLogger(BankParameterHelper.class);
	public static BankParameterData getBankParameterData(BankParameter master) {
		BankParameterData bankParameterData = new BankParameterData();
		bankParameterData.setBankCode(master.getBankCode());
		bankParameterData.setBankName(master.getBankName());
		if (master.getBankProcessStatus() != null) {
			bankParameterData.setBankProcessStatus(master
					.getBankProcessStatus().getValue());
		}
		if(master.getBusinessDate() != null){
		bankParameterData.setBusinessDate(DateUtil.getDateString(master
				.getBusinessDate()));
		}
		bankParameterData.setDistrictId(master.getDistrict().getId());
		bankParameterData.setDistrictName(master.getDistrict().getName());
		bankParameterData.setId(master.getId());
		bankParameterData.setLoanLimitPerFarmer(MoneyUtil
				.getAmountRoundedValue(
						master.getLoanLimitPerFarmer().getAmount()).toString());
		if (master.getMaxShareAmount() != null) {
			bankParameterData.setMaximumShareAmount(MoneyUtil
					.getAmountRoundedValue(
							master.getMaxShareAmount().getAmount()).toString());
		}
		if (master.getPriorityOrder() != null) {
			bankParameterData.setPriorityOrder(master.getPriorityOrder());
		}
		bankParameterData.setMaxDaysForInconsistency(master.getMaxDaysForInconsistency());
		
		bankParameterData.setCbsIntegrationMethod(master.getCbsIntegrationMethod().getValue());
		
		BorrowingTransactionMethod borrowingTransactionMethod = master.getBorrowingTransactionMethod();
		if(borrowingTransactionMethod !=null)
			bankParameterData.setBorrowingTransactionMethod(borrowingTransactionMethod.getValue());
		
		if(master.getCashGl() != null) {
			bankParameterData.setCashGl(master.getCashGl());
		}
		bankParameterData.setOther_cbs(master.getOther_cbs());
		bankParameterData.setShowReceivableAtBranch(master.getShowReceivableAtBranch());
		bankParameterData.setImageUploadSize(master.getImageUploadSize());

		if(master.getAuthorizationRequired()!=null){
			bankParameterData.setAuthorizationRequired(master.getAuthorizationRequired());
		}
		if(master.getSuspenseAccount() != null){
			bankParameterData.setSuspenseAccount(master.getSuspenseAccount());
		}		
		if(master.getPendingReturnStatus()!=null){
			bankParameterData.setPendingReturnStatus(master.getPendingReturnStatus());
		
		}
		if(master.getMultipleLoginAllow()!=null){
			bankParameterData.setMultipleLoginAllow(master.getMultipleLoginAllow());
		logger.info("data:" +master.getMultipleLoginAllow());
		}
		
		return bankParameterData;
	}

	public static BankParameter getBankParameter(BankParameterData data) {
		BankParameter bankParameter = new BankParameter();
		bankParameter.setBankCode(data.getBankCode());
		bankParameter.setBankName(data.getBankName());
		Integer bankProcessStatus = data.getBankProcessStatus();
		if (bankProcessStatus != null) {
			bankParameter.setBankProcessStatus(BankProcessStatus
					.getType(new Integer(bankProcessStatus)));
		}
		
		String businessDate = data.getBusinessDate();
		if (businessDate != null) {
			bankParameter.setBusinessDate(DateUtil.getVSoftDateByString(data
					.getBusinessDate()));
		}
		District district = new District();
		district.setId(data.getDistrictId());
		district.setName(data.getDistrictName());
		bankParameter.setDistrict(district);
		bankParameter.setLoanLimitPerFarmer(Money.valueOf(
				Currency.getInstance("INR"), data.getLoanLimitPerFarmer()));
		if (data.getMaximumShareAmount() != null) {
			bankParameter.setMaxShareAmount(Money.valueOf(
					Currency.getInstance("INR"), data.getMaximumShareAmount()));
		}
		if (data.getPriorityOrder() != null) {
			bankParameter.setPriorityOrder(data.getPriorityOrder());
		}
		bankParameter.setId(data.getId());
		bankParameter.setMaxDaysForInconsistency(data.getMaxDaysForInconsistency());
		bankParameter.setCbsIntegrationMethod(CBSMethodEnum.getType(data.getCbsIntegrationMethod()));
		
		if(data.getBorrowingTransactionMethod() !=null){
		String	bTransactionMethod =data.getBorrowingTransactionMethod();
		bankParameter.setBorrowingTransactionMethod(BorrowingTransactionMethod.getType(bTransactionMethod));
		}
		
		if(data.getCashGl() != null) {
			bankParameter.setCashGl(data.getCashGl());
		}
		bankParameter.setOther_cbs(data.getOther_cbs());
		bankParameter.setShowReceivableAtBranch(data.getShowReceivableAtBranch());
		bankParameter.setImageUploadSize(data.getImageUploadSize());

		if(data.getSuspenseAccount()!=null)
			bankParameter.setSuspenseAccount(data.getSuspenseAccount());

		if(data.getAuthorizationRequired()!=null){
			bankParameter.setAuthorizationRequired(data.getAuthorizationRequired());
		}
		logger.info("testing===="+data.getPendingReturnStatus());
		if(data.getPendingReturnStatus()!=null){
			bankParameter.setPendingReturnStatus(data.getPendingReturnStatus());
		
		}
		if(data.getMultipleLoginAllow()!=null){
			bankParameter.setMultipleLoginAllow(data.getMultipleLoginAllow());
		logger.info("allow:" +data.getMultipleLoginAllow());
		}
		return bankParameter;
	}
}
