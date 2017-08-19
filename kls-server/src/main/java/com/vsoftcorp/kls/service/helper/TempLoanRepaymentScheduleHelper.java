/**
 * 
 */
package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentScheduleId;
import com.vsoftcorp.kls.business.entity.loan.TempLoanRepaymentSchedule;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.LoanRepaymentScheduleData;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 *
 */
public class TempLoanRepaymentScheduleHelper {
	
	public static TempLoanRepaymentSchedule getTempLoanRepaymentSchedule(LoanRepaymentScheduleData data) {

		TempLoanRepaymentSchedule schedule = new TempLoanRepaymentSchedule();
		String installmentAmount = data.getContributionAmount();
		Money installmentAmtMoney = null;
		if (installmentAmount != null) {
			installmentAmtMoney = MoneyUtil.getAccountingMoney(new BigDecimal(installmentAmount)).getMoney();
		}
		schedule.setInstallmentAmount(installmentAmtMoney);
		String installmentDate = data.getInstallmentDate();
		Date installDate = null;
		if (installmentDate != null) {
			installDate = DateUtil.getVSoftDateByString(installmentDate);
		}
		schedule.setInstallmentDate(installDate);

		String interestAmount = data.getInterestAmount();
		Money interestAmtMoney = null;
		if (interestAmount != null) {
			interestAmtMoney = MoneyUtil.getAccountingMoney(new BigDecimal(interestAmount)).getMoney();
		}
		schedule.setInterestAmount(interestAmtMoney);

		String loanOustandingAmount = data.getLoanOutstandingAmount();
		Money loanOustandingAmtMoney = null;
		if (loanOustandingAmount != null) {
			loanOustandingAmtMoney = MoneyUtil.getAccountingMoney(new BigDecimal(loanOustandingAmount)).getMoney();
		}
		schedule.setLoanOutstandingAmount(loanOustandingAmtMoney);

		String principalAmount = data.getPrincipalAmount();
		Money principalAmtMoney = null;
		if (principalAmount != null) {
			principalAmtMoney = MoneyUtil.getAccountingMoney(new BigDecimal(principalAmount)).getMoney();
		}
		schedule.setPrincipalAmount(principalAmtMoney);

		LoanRepaymentScheduleId loanRepaymentScheduleId = new LoanRepaymentScheduleId();
		loanRepaymentScheduleId.setLineOfCreditId(data.getLineOfCreditId());
		loanRepaymentScheduleId.setInstallmentNumber(data.getInstallmentNumber());
		schedule.setLoanRepaymentScheduleId(loanRepaymentScheduleId);

		return schedule;
	}

	public static LoanRepaymentScheduleData getTempLoanRepaymentScheduleData(TempLoanRepaymentSchedule schedule) {

		LoanRepaymentScheduleData data = new LoanRepaymentScheduleData();
		data.setContributionAmount(schedule.getInstallmentAmount().getAmount().setScale(2).toString());
		data.setInstallmentDate(DateUtil.getDateString(schedule.getInstallmentDate()));
		data.setInstallmentNumber(schedule.getLoanRepaymentScheduleId().getInstallmentNumber());
		data.setInterestAmount(schedule.getInterestAmount().getAmount().setScale(2).toString());
		data.setLineOfCreditId(schedule.getLoanRepaymentScheduleId().getLineOfCreditId());
		data.setLoanOutstandingAmount(schedule.getLoanOutstandingAmount().getAmount().setScale(2).toString());
		data.setPrincipalAmount(schedule.getPrincipalAmount().getAmount().setScale(2).toString());
		return data;
	}

	public static List<LoanRepaymentScheduleData> getTempLoanRepaymentScheduleDataList(
			List<TempLoanRepaymentSchedule> scheduleList) {

		List<LoanRepaymentScheduleData> scheduleDataList = new ArrayList<LoanRepaymentScheduleData>();
		for (TempLoanRepaymentSchedule schedule : scheduleList) {
			LoanRepaymentScheduleData data = getTempLoanRepaymentScheduleData(schedule);
			scheduleDataList.add(data);
		}
		return scheduleDataList;
	}

	public static List<TempLoanRepaymentSchedule> getLoanRepaymentScheduleList(List<LoanRepaymentScheduleData> dataList) {

		List<TempLoanRepaymentSchedule> scheduleList = new ArrayList<TempLoanRepaymentSchedule>();
		for (LoanRepaymentScheduleData data : dataList) {
			TempLoanRepaymentSchedule schedule = getTempLoanRepaymentSchedule(data);
			scheduleList.add(schedule);
		}
		return scheduleList;
	}
	
	public static List<TempLoanRepaymentSchedule> getTempLoanRepaymentScheduleList(LoanRepaymentScheduleData data) {

		List<TempLoanRepaymentSchedule> scheduleList = new ArrayList<TempLoanRepaymentSchedule>();
		
		return scheduleList;
	}
}
