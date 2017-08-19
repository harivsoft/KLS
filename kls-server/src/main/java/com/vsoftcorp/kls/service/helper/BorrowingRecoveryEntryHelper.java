package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.BorrowingProduct;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.loan.BorrowingRecoveryEntry;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.BorrowingRecoveryEntryData;
import com.vsoftcorp.kls.valuetypes.transaction.DisbursementStatus;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;

public class BorrowingRecoveryEntryHelper {

	public static BorrowingRecoveryEntry getBorrowingRecoveryEntry(
			BorrowingRecoveryEntryData borrowingData) {
		// TODO Auto-generated method stub
		
			
		BorrowingRecoveryEntry master = new BorrowingRecoveryEntry();
		if(borrowingData.getId()!=null)
			master.setId(borrowingData.getId());
		Account account = new Account();
		account.setId(borrowingData.getAccountId());
		master.setAccount(account);
		
		BorrowingProduct borrowingProduct = new BorrowingProduct();
		borrowingProduct.setId(borrowingData.getBorrowingProdId());
		
		master.setBorrowingProduct(borrowingProduct);
		
		master.setChequeDate(DateUtil.getVSoftDateByString(borrowingData.getChequeDate()));
		master.setChequeNumber(borrowingData.getChequeNumber());
		master.setModeOfCollection(TransactionMode.getType(borrowingData.getModeOfCollection()));
		Pacs pacs = new Pacs();
		pacs.setId(borrowingData.getPacsId());
		master.setPacs(pacs);
		//master.setPassingDate(borrowingData.get);
		master.setRecoveryAmount(Money.valueOf("INR", borrowingData.getRecoveryAmount()));
		master.setRecoveryEntryDate(DateUtil.getVSoftDateByString(borrowingData.getRecoveryEntryDate()));
		master.setRemarks(borrowingData.getRemarks());
		master.setStatus(DisbursementStatus.getType(borrowingData.getStatus()));
		
		master.setLineOfCredit(borrowingData.getLineOfCredit());
		master.setPrinciplaBalance(Money.valueOf("INR",borrowingData.getPrinciplaBalance()));
		master.setInterestPayable(Money.valueOf("INR",borrowingData.getInterestPayable()));
		master.setPenalInterestPayable(Money.valueOf("INR",borrowingData.getPenalInterestPayable()));
		master.setPurpose(borrowingData.getPurpose());
		master.setCcbLoanAccountNumber(borrowingData.getCcbLoanAccountNumber());
		if(borrowingData.getPassingDate()!=null && !"".equalsIgnoreCase(borrowingData.getPassingDate())){
		master.setPassingDate(DateUtil.getVSoftDateByString(borrowingData.getPassingDate()));
		}
		
		return master;
	}

	public static BorrowingRecoveryEntryData getBorrowingRecoveryEntryData(
			BorrowingRecoveryEntry master) {
		// TODO Auto-generated method stub
		BorrowingRecoveryEntryData data = new BorrowingRecoveryEntryData();
		
		
		data.setId(master.getId());
		data.setAccountId(master.getAccount().getId());
		data.setBorrowingProdId(master.getBorrowingProduct().getId()); 
		data.setAccountNo(master.getAccount().getAccountNumber());
		data.setCcbLoanAccountNumber(master.getCcbLoanAccountNumber());
		data.setPurpose(master.getPurpose());
		data.setBorrowingProdName(master.getBorrowingProduct().getName());
		data.setModeOfCollection(master.getModeOfCollection().getValue());
		data.setPacsId(master.getPacs().getId());
		data.setRecoveryAmount(master.getRecoveryAmount().getAmount());
		data.setRecoveryEntryDate(DateUtil.getDateString(master.getRecoveryEntryDate()));
		data.setRemarks(master.getRemarks());
		data.setStatus(master.getStatus().getValue());
		
		data.setLineOfCredit(master.getLineOfCredit());
		data.setPrinciplaBalance(master.getPrinciplaBalance().getAmount());
		data.setInterestPayable(master.getInterestPayable().getAmount());
		data.setPenalInterestPayable(master.getPenalInterestPayable().getAmount());
		
		return data;
	}

}
