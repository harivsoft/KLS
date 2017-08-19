/**
 * 
 */
package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.LoanAccountProperty;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.LoanAccountPropertyData;
import com.vsoftcorp.kls.data.LoanLineOfCreditData;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;

/**
 * @author a9152
 * 
 */
public class LoanLineOfCreditHelper {

	// Setting Entity Data to Data Class
	public static LoanLineOfCreditData getLoanLineOfCreditData(LoanLineOfCredit master, List<Map> listMap) {

		LoanLineOfCreditData data = new LoanLineOfCreditData();
		if (master != null) {
			if(master.getFirstDueDate()!=null){
				data.setFirstDueDate(DateUtil.getDateString(master.getFirstDueDate()));
				}
				if(master.getLoanExpiryDate()!=null){
				data.setLoanExpiryDate(DateUtil.getDateString(master.getLoanExpiryDate()));
				}
			data.setSanctionAmount(master.getSanctionedAmount().getAmount().toString());
			data.setSanctionDate(DateUtil.getDateString(master.getSanctionedDate()));
			data.setId(master.getId());
			data.setInterestReceivable(master.getInterestReceivable().multiply(new BigDecimal("-1")).setScale(2));
			data.setOutstandingBalance(master.getCurrentBalance().getMoney().getAmount().setScale(2));
			PacsLoanApplication loanApplication = master.getPacsLoanApplication();
			if (loanApplication != null) {
				data.setLoanApplicationId(loanApplication.getId());
				data.setInstallmentAmount(LoanServiceUtil.getInstallmentAmount(loanApplication));
				Product product = loanApplication.getProduct();
				if (product != null) {
					data.setProcessingFee(master.getSanctionedAmount().getAmount().multiply(product.getProcessingFee().divide(new BigDecimal(100))).setScale(2).toString());
					data.setProductType(product.getProductType().getLoanType());
				}
				InterestCategory intCategory = loanApplication.getInterestCategory();
				if (intCategory != null) {
					data.setInterestCategoryId(intCategory.getId());
					data.setInterestCategoryDesc(intCategory.getIntrCategoryDesc());
				}
			}
			LoanAccountProperty loanAccountProperty = master.getLoanAccountProperty();
			if (loanAccountProperty != null) {
				LoanAccountPropertyData propertyData = LoanAccountPropertyHelper
						.getLoanAccountPropertyData(loanAccountProperty);
				data.setLoanAccountPropertyData(propertyData);
			}
			Account account = master.getAccount();
			if (account != null) {
				data.setAccountNumber(account.getAccountNumber());
			}
		}
		if (listMap != null && !listMap.isEmpty()) {
			Map map = listMap.get(0);
			String roi = ((BigDecimal) map.get("roi")).toString();
			String penalRoi = ((BigDecimal) map.get("penalRoi")).toString();
			data.setRateOfInterest(roi);
			data.setPenalInterest(penalRoi);
		}
		return data;
	}

	// Setting LineOfCredit Data List
	public static List<LoanLineOfCreditData> getLoanLocDataList(List<LoanLineOfCredit> loanLocList) {

		List<LoanLineOfCreditData> dataList = new ArrayList<LoanLineOfCreditData>();
		for (LoanLineOfCredit loanLoc : loanLocList) {
			LoanLineOfCreditData data = getLoanLineOfCreditData(loanLoc, null);
			dataList.add(data);
		}
		return dataList;
	}

	// Setting data to entity
	public static LoanLineOfCredit getLoanLineOfCredit(LoanLineOfCreditData data) {

		LoanLineOfCredit loanLoc = new LoanLineOfCredit();

		return loanLoc;
	}

	// Setting LineOfCredit Id
	public static List<LoanLineOfCreditData> getLoanLocIdList(List<LoanLineOfCredit> list) {

		List<LoanLineOfCreditData> dataList = new ArrayList<LoanLineOfCreditData>();
		for (LoanLineOfCredit loc : list) {
			LoanLineOfCreditData data = getLoanLineOfCreditId(loc);
			dataList.add(data);
		}
		return dataList;
	}

	public static LoanLineOfCreditData getLoanLineOfCreditId(LoanLineOfCredit master) {

		LoanLineOfCreditData data = new LoanLineOfCreditData();
		if (master != null) {
			data.setId(master.getId());
			Account account = master.getAccount();
			if (account != null) {
				data.setAccountNumber(account.getAccountNumber());
			}
		}
		return data;
	}
}
