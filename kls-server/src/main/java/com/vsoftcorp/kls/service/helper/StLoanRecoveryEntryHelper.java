package com.vsoftcorp.kls.service.helper;


import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.StLoanRecovery;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRecovery;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.LoanRecoveryData;
import com.vsoftcorp.kls.data.StLoanRecoveryData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.time.Date;

public class StLoanRecoveryEntryHelper {
	public static StLoanRecoveryData getLoanRecoveryData(StLoanRecovery master) {
			StLoanRecoveryData loanRecoveryData = new StLoanRecoveryData();
			loanRecoveryData.setId(master.getId());
			loanRecoveryData.setInterestReceivable(MoneyUtil.getAccountingMoney(master.getTotalInterestReceivable()).getMoney().getAmount());
			loanRecoveryData.setTotalPenalInterestReceivable(MoneyUtil.getAccountingMoney(master.getTotalPenalInterestReceivable()).getMoney().getAmount());
			loanRecoveryData.setChargesReceivable(MoneyUtil.getAccountingMoney(master.getChargesReceivable()).getMoney().getAmount());
            if(master.getId()!=null)
            	loanRecoveryData.setId(master.getId());
			if (master.getRemarks() != null)
				loanRecoveryData.setRemarks(master.getRemarks());
            if(master.getAmountPaid()!=null)
			   loanRecoveryData.setAmountPaid(MoneyUtil.getAccountingMoney(master.getAmountPaid()).getMoney().getAmount());
            if(master.getChargesPaid()!=null)
            	loanRecoveryData.setChargesPaid(MoneyUtil.getAccountingMoney(master.getChargesPaid()).getMoney().getAmount());
            if(master.getCustomerNumber()!=null)
            	loanRecoveryData.setCustomerNumber(master.getCustomerNumber());
            if(master.getInterestPaid()!=null)
            	loanRecoveryData.setInterestPaid(MoneyUtil.getAccountingMoney(master.getInterestPaid()).getMoney().getAmount());
            if(master.getPacsId()!=null)
            	loanRecoveryData.setPacsId(master.getPacsId());
            if(master.getPenalInterestPaid()!=null)
            	loanRecoveryData.setPenalInterestPaid(MoneyUtil.getAccountingMoney(master.getPenalInterestPaid()).getMoney().getAmount());
            if(master.getPrincipalPaid()!=null)
            	loanRecoveryData.setPrincipalPaid(MoneyUtil.getAccountingMoney(master.getPrincipalPaid()).getMoney().getAmount());
            if(master.getStatus()!=null)
            	loanRecoveryData.setStatus(master.getStatus());
            if(master.getTotalReceivableAmount()!=null)
            	loanRecoveryData.setTotalReceivableAmount(MoneyUtil.getAccountingMoney(master.getTotalReceivableAmount()).getMoney().getAmount());
            if(master.getTotalSubsidyReceivable()!=null)
            	loanRecoveryData.setSubsidyInterestReceivable(MoneyUtil.getAccountingMoney(master.getTotalSubsidyReceivable()).getMoney().getAmount());
            if(master.getAvailableBalance()!=null)
            	loanRecoveryData.setDrawableAmount(MoneyUtil.getAccountingMoney(master.getAvailableBalance()).getMoney().getAmount());
            if(master.getTotalPrincipalReceivable()!=null)
            	loanRecoveryData.setPrincipleBalance(MoneyUtil.getAccountingMoney(master.getTotalPrincipalReceivable()).getMoney().getAmount());
            if(master.getSanctionedLimit()!=null)
            	loanRecoveryData.setSanctionedAmount(MoneyUtil.getAccountingMoney(master.getSanctionedLimit()).getMoney().getAmount());
            if(master.getMemberNum()!=null)
            	loanRecoveryData.setMemberNumber(master.getMemberNum());
            PersonData personData=RestClientUtil.getAllCustomerDataByCusomterId(Long.parseLong(loanRecoveryData.getCustomerNumber()));
            loanRecoveryData.setCustomerName(personData.getDisplayName());
            if(master.getRecoveryEntryDate()!=null)
            	loanRecoveryData.setPassingDate(DateUtil.getDateString(master.getRecoveryEntryDate()));
            if(master.getRejectionRemarks()!=null)
            	loanRecoveryData.setRejectionRemarks(master.getRejectionRemarks());
			return loanRecoveryData;
		}

		public static StLoanRecovery getLoanRecovery(StLoanRecoveryData data) {
			StLoanRecovery master = new StLoanRecovery();
			IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
			if (data.getId() != null)
				master.setId(data.getId());

			if (data.getAmountPaid() != null)
				master.setAmountPaid(Money.valueOf("INR", data.getAmountPaid()));
			//if (data.getSubsidyPaid() != null)
			//	master.setChargesPaid(Money.valueOf("INR", data.getChargesPaid()));
			if (data.getCustomerNumber() != null)
				master.setCustomerNumber(data.getCustomerNumber());
			if (data.getInterestPaid() != null)
				master.setInterestPaid(Money.valueOf("INR", data.getInterestPaid()));
								
			if (data.getPenalInterestPaid() != null)
				master.setPenalInterestPaid(Money.valueOf("INR", data.getPenalInterestPaid()));
			if (data.getPrincipalPaid() != null)
				master.setPrincipalPaid(Money.valueOf("INR", data.getPrincipalPaid()));
			
			if (data.getPrincipleBalance() != null)
				master.setTotalPrincipalReceivable(Money.valueOf("INR", data.getPrincipleBalance()));
			if (data.getInterestReceivable() != null)
				master.setTotalInterestReceivable(Money.valueOf("INR", data.getInterestReceivable()));
			if (data.getTotalPenalInterestReceivable() != null)
				master.setTotalPenalInterestReceivable(Money.valueOf("INR", data.getTotalPenalInterestReceivable()));
			if (data.getSubsidyInterestReceivable() != null)
				master.setTotalSubsidyReceivable(Money.valueOf("INR", data.getSubsidyInterestReceivable()));
			if (data.getTotalReceivableAmount() != null)
				master.setTotalReceivableAmount(Money.valueOf("INR", data.getTotalReceivableAmount()));
			master.setRemarks(data.getRemarks());
			String businessDate="";
			Pacs pacs=pacsDao.getPacs(data.getPacsId());
			if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
				businessDate=RestClientUtil.getPacsBusinessDate(data.getPacsId(),pacs.getBranch().getId());
				//businessDate="2017-05-01";
				master.setRecoveryEntryDate(DateUtil.getVSoftDateByString(businessDate));
			}
			else
			master.setRecoveryEntryDate(LoanServiceUtil.getBusinessDate());
			if (data.getTotalReceivableAmount() != null)
				master.setTotalReceivableAmount(Money.valueOf("INR", data.getTotalReceivableAmount()));
			if(data.getDrawableAmount()!=null)
				master.setAvailableBalance(Money.valueOf("INR",data.getDrawableAmount()));
			if(data.getPacsId()!=null)
				master.setPacsId(data.getPacsId());
			if(data.getChargesReceivable()!=null)
				master.setChargesReceivable(Money.valueOf("INR",data.getChargesReceivable()));
			if(data.getChargesPaid()!=null)
				master.setChargesPaid(Money.valueOf("INR",data.getChargesPaid()));
			/*if(data.getCustomerName()!=null)
				master.setCustomerNumber(data.getCustomerName());*/
			if(data.getStatus()!=null)
				master.setStatus(data.getStatus());
			if(data.getSanctionedAmount()!=null)
				master.setSanctionedLimit(Money.valueOf("INR",data.getSanctionedAmount()));
			if(data.getMemberNumber()!=null)
				master.setMemberNum(data.getMemberNumber());
			if(data.getRejectionRemarks()!=null)
				master.setRejectionRemarks(data.getRejectionRemarks());
			return master;
		}
}
	