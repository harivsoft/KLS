package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRecovery;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.LoanRecoveryData;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1623
 * 
 */
public class LoanPaymentRecoveryHelper {
	public static LoanRecoveryData getLoanRecoveryData(LoanRecovery master) {
		LoanRecoveryData loanRecoveryData = new LoanRecoveryData();
		loanRecoveryData.setId(master.getId());
		loanRecoveryData.setTotalInterestReceivable(master.getLoanLineOfCredit().getInterestReceivable());
		loanRecoveryData.setTotalPenalInterestReceivable(master.getLoanLineOfCredit().getPenalInterestReceivable());
		loanRecoveryData.setTotalChargesReceivable(master.getLoanLineOfCredit().getChargesReceivable());
		loanRecoveryData.setTotalPrincipalReceivable(master.getLoanLineOfCredit().getCurrentBalance().getMoney().getAmount().setScale(2));

		if (master.getRemarks() != null)
			loanRecoveryData.setRemarks(master.getRemarks());

		if (master.getSavingsAccountNumber() != null)
			loanRecoveryData.setSavingsAccountNumber(master.getSavingsAccountNumber());
		if(master.getLoanLineOfCredit().getCustomerId()!=null){
			loanRecoveryData.setCustomerNumber(master.getLoanLineOfCredit().getCustomerId().toString());
		}
		if(master.getLoanLineOfCredit().getId()!=null)
			loanRecoveryData.setLocId(master.getLoanLineOfCredit().getId());
		if(master.getId()!=null)
			loanRecoveryData.setRecoveryEntryId(master.getId());
		if(master.getAmountPaid()!=null)
			loanRecoveryData.setAmountPaid(MoneyUtil.getAccountingMoney(master.getAmountPaid()).getMoney().getAmount());
		if(master.getInterestPaid()!=null)
			loanRecoveryData.setInterestPaid(MoneyUtil.getAccountingMoney(master.getInterestPaid()).getMoney().getAmount());
		if(master.getPenalInterestPaid()!=null)
			loanRecoveryData.setPenalInterestPaid(MoneyUtil.getAccountingMoney(master.getPenalInterestPaid()).getMoney().getAmount());
		if(master.getPrincipalPaid()!=null)
			loanRecoveryData.setPrincipalPaid(MoneyUtil.getAccountingMoney(master.getPrincipalPaid()).getMoney().getAmount());
		if(master.getChargesPaid()!=null)
			loanRecoveryData.setChargesPaid(MoneyUtil.getAccountingMoney(master.getChargesPaid()).getMoney().getAmount());
		if(master.getInstallmentAmount()!=null)
			loanRecoveryData.setInstallmentAmount(MoneyUtil.getAccountingMoney(master.getInstallmentAmount()).getMoney().getAmount());
		if(master.getModeOfPayment()!=null)
			loanRecoveryData.setModeOfPayment(master.getModeOfPayment().getValue());
		if(master.getOutStandingBalance()!=null)
			loanRecoveryData.setOutstandingBalance(MoneyUtil.getAccountingMoney(master.getOutStandingBalance()).getMoney().getAmount());
		if(master.getOutStandingBalanceAfterPayment()!=null)
			loanRecoveryData.setOutstandingBalanceAfterPayment(MoneyUtil.getAccountingMoney(master.getOutStandingBalanceAfterPayment()).getMoney().getAmount());
		if(master.getRejectionRemarks()!=null)
			loanRecoveryData.setRejectionRemarks(master.getRejectionRemarks());
		if(master.getRecoveredDate()!=null)
			loanRecoveryData.setRecoveryEntryDate(DateUtil.getDateString(master.getRecoveredDate()));
		return loanRecoveryData;
	}

	public static LoanRecovery getLoanRecovery(LoanRecoveryData data) {
		LoanRecovery master = new LoanRecovery();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		if (data.getId() != null)
			master.setId(null);

		if (data.getAmountPaid() != null)
			master.setAmountPaid(Money.valueOf("INR", data.getAmountPaid()));
		if (data.getChargesPaid() != null)
			master.setChargesPaid(Money.valueOf("INR", data.getChargesPaid()));
		if (data.getInstallmentAmount() != null)
			master.setInstallmentAmount(Money.valueOf("INR", data.getInstallmentAmount()));
		if (data.getCustomerNumber() != null)
			master.setCustomerNumber(data.getCustomerNumber());
		if (data.getInterestPaid() != null)
			master.setInterestPaid(Money.valueOf("INR", data.getInterestPaid()));
		if (data.getModeOfPayment() != null)
			master.setModeOfPayment(TransactionMode.getType(data.getModeOfPayment()));
		if (data.getOutstandingBalance() != null)
			master.setOutStandingBalance(Money.valueOf("INR", data.getOutstandingBalance()));
		if(null != data.getStatus()){ // Handled for, While doing Bulk Repayment at Branch Status will not come.
			if(data.getStatus().equals(LoanApplicationState.REJECTED)){
				if (data.getOutstandingBalanceAfterPayment() != null)
				master.setOutStandingBalanceAfterPayment(Money.valueOf("INR", data.getOutstandingBalance()));
			}
			else{
				if (data.getOutstandingBalanceAfterPayment() != null)
					master.setOutStandingBalanceAfterPayment(Money.valueOf("INR", data.getOutstandingBalanceAfterPayment()));
				}
		}
		if (data.getPenalInterestPaid() != null)
			master.setPenalInterestPaid(Money.valueOf("INR", data.getPenalInterestPaid()));
		if (data.getPrincipalPaid() != null)
			master.setPrincipalPaid(Money.valueOf("INR", data.getPrincipalPaid()));
		if (data.getId() != null) {
			LoanLineOfCredit loanLineOfCredit = new LoanLineOfCredit();
			loanLineOfCredit.setId(data.getLocId());
			master.setLoanLineOfCredit(loanLineOfCredit);
		}
		if (data.getTotalPrincipalReceivable() != null)
			master.setTotalPrincipalReceivable(Money.valueOf("INR", data.getTotalPrincipalReceivable()));
		if (data.getTotalInterestReceivable() != null)
			master.setTotalInterestReceivable(Money.valueOf("INR", data.getTotalInterestReceivable()));
		if (data.getTotalPenalInterestReceivable() != null)
			master.setTotalPenalInterestReceivable(Money.valueOf("INR", data.getTotalPenalInterestReceivable()));
		if (data.getTotalChargesReceivable() != null)
			master.setTotalChargesReceivable(Money.valueOf("INR", data.getTotalChargesReceivable()));
		if (data.getTotalReceivableAmount() != null)
			master.setTotalReceivableAmount(Money.valueOf("INR", data.getTotalReceivableAmount()));
		master.setRemarks(data.getRemarks());
		master.setSavingsAccountNumber(data.getSavingsAccountNumber());
		if (data.getCustomerName() != null)
			master.setRecoveredBy(data.getCustomerName());
		String businessDate="";
		Pacs pacs=pacsDao.getPacs(data.getLoggedInUserDetails().getPacsId());
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			businessDate=RestClientUtil.getPacsBusinessDate(data.getLoggedInUserDetails().getPacsId(),pacs.getBranch().getId());
			//businessDate="2017-05-01";
			master.setRecoveredDate(DateUtil.getVSoftDateByString(businessDate));
		}
		else
		master.setRecoveredDate(LoanServiceUtil.getBusinessDate());
		if (data.getTotalReceivableAmount() != null)
			master.setTotalReceivableAmount(Money.valueOf("INR", data.getTotalReceivableAmount()));
		if(data.getStatus()!=null)
			master.setStatus(data.getStatus());
		if (data.getRecoveryEntryId() != null)
			master.setId(data.getRecoveryEntryId());
		if(data.getRejectionRemarks()!=null)
			master.setRejectionRemarks(data.getRejectionRemarks());
		return master;
	}
}
