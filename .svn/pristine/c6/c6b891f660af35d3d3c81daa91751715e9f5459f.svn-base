package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.CurrentTransactionData;
import com.vsoftcorp.kls.data.TransactionHistoryData;
import com.vsoftcorp.kls.service.transaction.impl.TransactionHistoryService;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

/**
 * @author a9153
 * 
 *         Helper Class for conversion from pojo to entity and vice versa.
 */

public class TransactionHistoryHelper {
	
	
	public static TransactionHistoryData getTransactionHistoryData(TransactionHistory master) {

		TransactionHistoryData data = new TransactionHistoryData();

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
		
        data.setId(master.getId().toString());
		
		data.setTransactionAmount(master.getTransactionAmount().getMoney().getAmount().setScale(2));
		
		//data.setBusinessDate(DateUtil.getDateString(master.getBusinessDate()));
		data.setTransactionDate(DateUtil.getDateString(master.getBusinessDate()));
		
		//data.setTransactionType(master.getChannelType().getName());
		data.setTransType(master.getTransactionType().getName());
		
		data.setChannelType(master.getChannelType().getName());
		
		//data.setCrDr(master.getCrDr().toString());
		data.setCrdr(master.getCrDr().toString());
		
		data.setTransCode(getTransactionDescription(master.getTransactionCode().getValue()));
		

		return data;
	}

	public static TransactionHistory getTransactionHistory(CurrentTransactionData data) {

		TransactionHistory master = new TransactionHistory();
		Account account = new Account();
		account.setId(new Long(data.getAccountId()));
		master.setAccount(account);
		master.setAccountNumber(data.getAccountNumber());
		// master.setBusinessDate(data.getBusinessDate());
		master.setBusinessDate(Date.now());
		master.setChannelType(ChannelType.getType(data.getChannelType()));
		master.setCrDr(new Integer(data.getCrDr()));
		// master.setId(new Long(data.getId()));
		LineOfCredit lineOfCredit = new LineOfCredit();
		lineOfCredit.setId(new Long(data.getLineOfCreditId()));
		master.setLineOfCredit(lineOfCredit);
		// master.setOpeningBalance(data.getOpeningBalance());
		master.setOpeningBalance(AccountingMoney.ZERO);
		Pacs pacs = new Pacs();
		pacs.setId(new Integer(data.getPacsId()));
		master.setPacs(pacs);
		// master.setTransactionAmount(data.getTransactionAmount());
		master.setTransactionAmount(AccountingMoney.ZERO);
		master.setTransactionCode(TransactionCode.getType(new Integer(data.getTransactionTowards())));
		master.setTransactionType(TransactionType.getType(data.getTransactionType()));
		master.setVoucherNumber(data.getVoucherNumber());
		master.setTerminalId(data.getTerminalId());
		return master;
	}

	public static List<TransactionHistory> getTransactionHistoryList(List<CurrentTransaction> currentTransactionList) {

		List<TransactionHistory> transactionHistoryList = new ArrayList<TransactionHistory>();
		for (CurrentTransaction currentTransaction : currentTransactionList) {
			TransactionHistory master = new TransactionHistory();
			Account account = new Account();
			account.setId(currentTransaction.getAccount().getId());
			master.setAccount(account);
			master.setAccountNumber(currentTransaction.getAccountNumber());
			master.setBusinessDate(currentTransaction.getBusinessDate());
			master.setChannelType(currentTransaction.getChannelType());
			master.setCrDr(currentTransaction.getCrDr());
			LineOfCredit lineOfCredit = new LineOfCredit();
			lineOfCredit.setId(currentTransaction.getLineOfCredit().getId());
			master.setLineOfCredit(lineOfCredit);
			master.setOpeningBalance(currentTransaction.getOpeningBalance());
			Pacs pacs = new Pacs();
			pacs.setId(currentTransaction.getPacs().getId());
			master.setPacs(pacs);
			master.setTransactionAmount(currentTransaction.getTransactionAmount());
			master.setTransactionCode(currentTransaction.getTransactionCode());
			master.setTransactionType(currentTransaction.getTransactionType());
			master.setVoucherNumber(currentTransaction.getVoucherNumber());
			master.setTerminalId(currentTransaction.getTerminalId());
			transactionHistoryList.add(master);
		}
		return transactionHistoryList;
	}

	public static TransactionHistory getTransactionHistory(TransactionHistoryData data) {

		TransactionHistory master = new TransactionHistory();
		Account account = new Account();
		account.setId(new Long(data.getAccountId()));
		master.setAccount(account);
		master.setAccountNumber(data.getAccountNumber());
		// master.setBusinessDate(data.getBusinessDate());
		master.setBusinessDate(Date.now());
		master.setChannelType(ChannelType.getType(data.getChannelType()));
		master.setCrDr(new Integer(data.getCrDr()));
		// master.setId(new Long(data.getId()));
		LineOfCredit lineOfCredit = new LineOfCredit();
		lineOfCredit.setId(new Long(data.getLineOfCreditId()));
		master.setLineOfCredit(lineOfCredit);
		// master.setOpeningBalance(data.getOpeningBalance());
		master.setOpeningBalance(AccountingMoney.ZERO);
		Pacs pacs = new Pacs();
		pacs.setId(new Integer(data.getPacsId()));
		master.setPacs(pacs);
		// master.setTransactionAmount(data.getTransactionAmount());
		master.setTransactionAmount(AccountingMoney.ZERO);
		master.setTransactionCode(TransactionCode.getType(new Integer(data.getTransactionTowards())));
		master.setTransactionType(TransactionType.getType(data.getTransactionType()));
		master.setVoucherNumber(data.getVoucherNumber());
		master.setTerminalId(data.getTerminalId());
		return master;
	}
public static String getTransactionDescription(Integer transCode) {
		
		Map<Integer, String> transDesc = new HashMap<Integer, String>();
		transDesc.put(1, "PRINCIPAL BALANCE");
		
		transDesc.put(2, "INTEREST RECOVERY");
		
		transDesc.put(3, "PENAL INTEREST RECOVERY");
		transDesc.put(4, "CHARGES RECOVERY");
		
		transDesc.put(5, "SHARE DEBIT");
		transDesc.put(6, "INSURANCE DEBIT");
		transDesc.put(7, "INTEREST POSTING - RECEIVABLE");
		transDesc.put(9, "PENAL INTEREST POSTING - RECEIVABLE");
		
		transDesc.put(11, "KIND BALANCE DEBIT");
		
		transDesc.put(15, "MARGIN AMOUNT");
		transDesc.put(17, "CHARGES POSTING - RECEIVABLE");
		transDesc.put(19, "PROCESSING FEE DEBIT");
		
		transDesc.put(22, "LOAN CLOSURE CHARGES");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return transDesc.get(transCode);
	}

public static TransactionHistoryData getTransactionHistoryData(Object master) {

	TransactionHistoryData data = new TransactionHistoryData();

	Object []fields = (Object []) master;
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
	
    //data.setId(master.get("id").toString());
	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
	data.setTransactionDate(sf.format((java.util.Date) fields[0]));
	
	// business_date 0 as transactionDate,channel_type 1 as channelType,
	//crdr 2 as crdr,trans_type 3 as transType,tran_code 4 as transCode,trans_amt 5
	
	data.setTransactionAmount(new BigDecimal(fields[5].toString()).setScale(2));
	
	//data.setBusinessDate(DateUtil.getDateString(master.getBusinessDate()));
	//data.setTransactionType(master.getChannelType().getName());
	if(fields[3]!=null) {
		data.setTransType(TransactionType.getType(fields[3].toString()).getName());
	}
	
	if(fields[1]!=null) {		
		data.setChannelType(ChannelType.getType(fields[1].toString()).getName());
	}

	
	//data.setCrDr(master.getCrDr().toString());
	data.setCrdr(fields[2].toString());
	if(fields[4]!=null) 
	data.setTransCode(getTransactionDescription(Integer.parseInt(fields[4].toString())));
	

	return data;
}

}
