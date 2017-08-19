package com.vsoftcorp.kls.service.transaction;

import java.util.List;

import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.data.BorrowingRecoveryEntryData;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.valuetypes.loan.LoanType;
import com.vsoftcorp.time.Date;

public interface IBorrowingTransactionService {
	
	public void saveBorrowingTransactionsOnetoOneStraight(List<CurrentTransaction> currTransList,Date businessDate, LoanType loanType,List<Integer> pacsIdsList);
	
	public void saveBorrowingTransactionsGroupingMethod(Date businessDate, LoanType loanType,List<Integer> pacsIdsList) throws KlsRuntimeException;
	
	public void saveBorrowingTransactionsOnetoOneEarly(List<CurrentTransaction> currTransList, Date businessDate, LoanType loanType,List<Integer> pacsIdsList) ;
	
	public void borrowingDirectCollectionPassing(Long  recoveryId , String businessDate);

}
