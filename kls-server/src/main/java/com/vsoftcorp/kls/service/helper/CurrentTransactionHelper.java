package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.CurrentTransactionData;
import com.vsoftcorp.kls.data.MiniStatementData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.dataaccess.loan.IAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.impl.AccountDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;

/**
 * @author a9153
 * 
 *         Helper Class for conversion from pojo to entity and vice versa.
 */

public class CurrentTransactionHelper {

	private static final Logger logger = Logger.getLogger(CurrentTransactionHelper.class);

	public static CurrentTransactionData getCurrentTransactionData(CurrentTransaction master) {

		CurrentTransactionData data = new CurrentTransactionData();

		// private String id;
		// private String voucherNumber;
		// private String transactionType;
		// private String transactionTowards;
		// private String openingBalance;
		// private String transactionAmount;
		// private String crDr;
		// private String accountId;
		// private String accountNumber;
		// private String pacsId;
		// private String businessDate;

		return data;
	}

	public static CurrentTransaction getCurrentTransaction(CurrentTransactionData data) {

		logger.info("Start: Converting current transaction data to current transaction in getCurrentTransaction() method.");
		CurrentTransaction master = new CurrentTransaction();
		master.setAccountNumber(data.getAccountNumber());
		String businessDate = data.getBusinessDate();
		if (businessDate != null) {
			master.setBusinessDate(DateUtil.getVSoftDateByString(businessDate));
		}
		master.setChannelType(ChannelType.getType(data.getChannelType()));
		master.setCrDr(new Integer(data.getCrDr()));
		AccountingMoney transAmountMoney = AccountingMoney.ZERO;
		master.setTransactionType(TransactionType.getType(data.getTransactionType()));
		String amt = data.getTransactionAmount();
		if (amt != null) {
			
			if (data.isBulkRecoveryFromEntry()) {
				transAmountMoney = MoneyUtil.getAccountingMoney(new BigDecimal(amt));
			}else{
			if ("YES".equalsIgnoreCase(data.getBulkRecovery())) {
				transAmountMoney = MoneyUtil.getAccountingMoney(new BigDecimal(amt));
			} else {
				StringBuilder sBuilder = new StringBuilder(amt);
				amt = sBuilder.insert(amt.length() - 2, '.').toString();
				BigDecimal amount = new BigDecimal(amt);
				transAmountMoney = MoneyUtil.getAccountingMoney(amount);
			}
			}
		}
		master.setTransactionAmount(transAmountMoney);
		master.setTransactionCode(TransactionCode.getType(new Integer(data.getTransactionTowards())));
		String voucherNumber = data.getVoucherNumber();
		if (voucherNumber != null) {
			master.setVoucherNumber(master.getTransactionType().getValue()+"-"+voucherNumber);
		} else {
			master.setVoucherNumber(data.getTransactionType()+"-"+1);
		}
		master.setPostedStatus(1);
		master.setRemarks(data.getRemarks());
		master.setTerminalId(data.getTerminalId());
		logger.info("End: Converting current transaction data to current transaction in getCurrentTransaction() method.");
		return master;
	}
	
	public static CurrentTransaction getReversalTransaction(Map<String,String> isoDataElements){
		logger.info("inside helper");
		ICurrentTransactionDAO currentTranDao=KLSDataAccessFactory.getCurrentTransactionDAO();
		CurrentTransaction master=new CurrentTransaction();
		CurrentTransaction newMaster=new CurrentTransaction();
		String accountNum=isoDataElements.get("102");	
		String amount=isoDataElements.get("4");
		BigDecimal transAmt=new BigDecimal(amount);
		transAmt = transAmt.divide(new BigDecimal(100));
		//String transactionDate=isoDataElements.get("7");
		
		//Date transDate=DateUtil.getVSoftDateByString(transactionDate);
		BankParameter bankParameter = KLSDataAccessFactory.getBankParameter();
		IAccountPropertyDAO accountPropDao=KLSDataAccessFactory.getAccountPropertyDAO();
		AccountProperty accPorper=new AccountProperty();
		accPorper=accountPropDao.getAccountProperty(accountNum, false);
		IAccountDAO accDao=KLSDataAccessFactory.getAccountDAO();
		Account acc=accDao.getAccount(accPorper.getId(), false);
		master=currentTranDao.getCurrentTransactionByAccountNum(acc.getAccountNumber(),bankParameter.getBusinessDate());
		Integer voucherNumber = VoucherNumberUtil.getVoucherNumber(master.getPacs().getId().toString(), master.getTransactionType().getValue());
	    Long locId=master.getLineOfCredit().getId();
	    newMaster.setAccount(master.getAccount());
	    newMaster.setAccountNumber(master.getAccountNumber());
		newMaster.setBusinessDate(bankParameter.getBusinessDate());
		newMaster.setTransactionAmount(MoneyUtil.getAccountingMoney(transAmt));
		newMaster.setRemarks("Reversal of Previous Transaction with id:"+master.getId());
		newMaster.setCrDr(1);
		newMaster.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
		newMaster.setTransactionType(TransactionType.Reversal);
		newMaster.setVoucherNumber("D-"+voucherNumber.toString());
		AccountingMoney openingBal=master.getOpeningBalance();
	    newMaster.setOpeningBalance(openingBal.add(master.getTransactionAmount()));
	    newMaster.setChannelType(ChannelType.ATM);
	    newMaster.setLineOfCredit(master.getLineOfCredit());
	    newMaster.setPacs(master.getPacs());
	    newMaster.setPostedStatus(master.getPostedStatus());
	    newMaster.setTerminalId(master.getTerminalId());
	    newMaster.setTransactionAmount(MoneyUtil.getAccountingMoney(transAmt));
	    return newMaster;
	    }
	
	public static List<MiniStatementData> getMiniStatementData(List<CurrentTransaction> currentTransactionList,List<TransactionHistory> transactionHistoryList){
		logger.info("Start : Converting to data object in helper");
		List<MiniStatementData> miniStatementList=new ArrayList<MiniStatementData>();
		if(transactionHistoryList.size()>0){
		for(TransactionHistory hist:transactionHistoryList){
			MiniStatementData miniData=new MiniStatementData();
			if(hist.getBusinessDate()!=null)
				miniData.setTransactionDate(DateUtil.getDateString(hist.getBusinessDate()));
			if(hist.getCrDr()!=null)
				miniData.setCrDr(hist.getCrDr().toString());
			if(hist.getOpeningBalance()!=null)
				miniData.setCurrentBal(hist.getOpeningBalance().getMoney().getAmount().setScale(2).toString());
			if(hist.getTransactionAmount()!=null)
				miniData.setTransAmt(hist.getTransactionAmount().getMoney().getAmount().setScale(2).toString());
			if(hist.getRemarks()!=null)
				miniData.setRemarks(hist.getRemarks());
			miniStatementList.add(miniData);
		}
		}
		for(CurrentTransaction currentTran:currentTransactionList){
			MiniStatementData miniData=new MiniStatementData();
			if(currentTran.getBusinessDate()!=null)
				miniData.setTransactionDate(DateUtil.getDateString(currentTran.getBusinessDate()));
			if(currentTran.getCrDr()!=null)
				miniData.setCrDr(currentTran.getCrDr().toString());
			if(currentTran.getOpeningBalance()!=null)
				miniData.setCurrentBal(currentTran.getOpeningBalance().getMoney().getAmount().setScale(2).toString());
			if(currentTran.getTransactionAmount()!=null)
				miniData.setTransAmt(currentTran.getTransactionAmount().getMoney().getAmount().setScale(2).toString());
			if(currentTran.getRemarks()!=null)
				miniData.setRemarks(currentTran.getRemarks());
			miniStatementList.add(miniData);
		}
		return miniStatementList;
	}
}
