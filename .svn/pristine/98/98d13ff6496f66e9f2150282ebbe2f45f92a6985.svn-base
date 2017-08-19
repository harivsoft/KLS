package com.vsoftcorp.kls.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementCompositeId;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.DisbursementActivityData;
import com.vsoftcorp.kls.data.DisbursementData;
import com.vsoftcorp.kls.data.LoanDisbursementScheduleData;
import com.vsoftcorp.kls.data.PacsLoanApplicationData;
import com.vsoftcorp.kls.data.PacsLoanEntryActivityData;

public class LoanDisbursementScheduleHelper {
//Setting  Disbursement Data
	public static DisbursementData getLoanDisbursementScheduleData(LoanDisbursementSchedule master){
		
		DisbursementData data=new DisbursementData();
		if(master != null)
		{
		data.setDisbursementDate(DateUtil.getDateString(master.getDisbursementDate()).toString());
		data.setNoOfDisbursement(master.getLoanDisbursmentCompositeId().getNoOfDisbursement());
		data.setLineOfCreditId(master.getLoanDisbursmentCompositeId().getLineOfCreditId());
		data.setRemainingBalance(MoneyUtil.getAmountRoundedValue(master.getRemainingBalance().getAmount()));
		data.setDisbursedAmount(MoneyUtil.getAmountRoundedValue(master.getDisbursedAmount().getAmount()));
		data.setDisbursementAmount(MoneyUtil.getAmountRoundedValue(master.getDisbursementAmount().getAmount()));
		data.setRemarks(master.getRemarks());
		}	
		return data;
		
	}
	
	public static LoanDisbursementSchedule  getDisbursement(DisbursementData data) {

		LoanDisbursementSchedule master = new LoanDisbursementSchedule();
		if (master != null) {

			master.setDisbursedAmount(MoneyUtil.getAccountingMoney(data.getDisbursedAmount()).getMoney());	
			master.setDisbursementAmount(MoneyUtil.getAccountingMoney(data.getDisbursementAmount()).getMoney());
			master.setDisbursementDate(DateUtil.getVSoftDateByString(data.getDisbursementDate()));
			master.setRemainingBalance(MoneyUtil.getAccountingMoney(data.getRemainingBalance()).getMoney());
			master.setRemarks(data.getRemarks());
			
			LoanDisbursementCompositeId compositId = new LoanDisbursementCompositeId();
			compositId.setLineOfCreditId(data.getLineOfCreditId());
			compositId.setNoOfDisbursement(data.getNoOfDisbursement());
			master.setLoanDisbursmentCompositeId(compositId);
		}
		return master;
	}

	
	public static List<LoanDisbursementSchedule> getLoanDisbursementScheduleList(List<DisbursementData> data) {
		List<LoanDisbursementSchedule> masterList =new ArrayList<LoanDisbursementSchedule>();
	
		for (DisbursementData disbursementscheduleData : data ) {
			LoanDisbursementSchedule master= getDisbursement(disbursementscheduleData);
			masterList.add(master);
		}
		return masterList;
	}

	public static List<DisbursementData> getLoanDisbursementScheduleListData(List<LoanDisbursementSchedule> master) {
		List<DisbursementData> dataList=new ArrayList<DisbursementData>(); 
		for (LoanDisbursementSchedule loanDisbursementSchedule : master) {
			dataList.add(LoanDisbursementScheduleHelper.getLoanDisbursementScheduleData(loanDisbursementSchedule));

		}
		
		return dataList;
	}

	//Setting LoanDisbursementSchedule Data 
	public static LoanDisbursementScheduleData getDisbursementScheduleData( LoanLineOfCredit loc,
																			PacsLoanApplicationData applicationData,
																			List<DisbursementData> disbursementDataList) 
	{
		LoanDisbursementScheduleData result = new LoanDisbursementScheduleData();
		//Setting Line Of Credit Data
		result.setAccountOpeningDate(loc.getAccount().getOpenDate().toString());
		result.setFirstDueDate(loc.getFirstDueDate().toString());
		result.setLoanExpiryDate(loc.getLoanExpiryDate().toString());
		
		//Setting Application Data
		result.setSanctionDate(applicationData.getSanctionDate());			
		result.setInstallmentFrequency(applicationData.getInstallmentFrequency());
		result.setSanctionAmount(applicationData.getSanctionAmount());		
		result.setProductName(applicationData.getProductName());
		result.setProductId(applicationData.getProductId());			
		result.setPurpose(applicationData.getPurposeName());
		result.setSubPurpose(applicationData.getSubPurposeName());
		result.setInstallmentFrequency(applicationData.getInstallmentFrequency());

		 //changes required 	LoanAmountAfterDeduction		
		result.setLoanAmountAfterDeduction(applicationData.getInspectionAmount());
		
		//Setting Activity Data
		List<DisbursementActivityData> activityDataList=new ArrayList<DisbursementActivityData>();
		DisbursementActivityData activityData = null;
		
		for (PacsLoanEntryActivityData data : applicationData.getActivtityList()) {
			activityData=new DisbursementActivityData();
			activityData.setActivityName(data.getActivityName());
			activityDataList.add(activityData);
		}
		result.setActivtityList(activityDataList);

	   //Setting Disbursement Data	
		result.setDisbursementList(disbursementDataList);
		
		// TODO Auto-generated method stub
		return result;
	}
	
	
}
