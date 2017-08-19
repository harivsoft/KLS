package com.vsoftcorp.kls.service.loan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.data.BulkCustomerData;
import com.vsoftcorp.kls.data.CustXLData;
import com.vsoftcorp.kls.data.LoanRecoveryData;
import com.vsoftcorp.kls.data.StLoanRecoveryData;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;

public interface ILoanRecoveryService {
	public LoanRecoveryData getLoanRecoveryDataByLocId(Long locId);

	public LoanRecoveryData getRocoveryInfoBasedOnAmountPaid(BigDecimal amountPaid, Integer recoverySequenceId, Long loanLocId);

	public String saveLoanRecovery(LoanRecoveryData data);

	public List<Map> getAccountByModeOfPayment(String modeOfPayment, Long loanLocId);
	
	public void populateCurrentTransactionRecord(LoanLineOfCredit loanLoc,String accountNumber,
			List<CurrentTransaction> currentTransactionList, TransactionCode transactionCode, String remarks,
			Integer crdr, AccountingMoney transactionAmount, boolean flag,TransactionType transactionType, String voucherNumber);
	
	public List<CustXLData> validateAndProcessMtLtRecovery(List<CustXLData> uploadedExcelData,Long pacsId);
	
	public List<BulkCustomerData> validateAndProcessBulkMtLtRecovery( List<BulkCustomerData> bulkCustData) ;

	public void populateCurrentTransactionRecordForBranchTransaction(LoanLineOfCredit loanLoc, String accountNumber,
			List<CurrentTransaction> currentTransactionList,TransactionCode transactionCode, String remarks, Integer crdr,
			AccountingMoney transactionAmount, boolean isCashGl,TransactionType transactionType, String voucherNumber, String channelType);
	
	
	public String saveStLoanRecoveryEntry(StLoanRecoveryData data);
	
	public List<StLoanRecoveryData> getStLoanRecoveryEntry(String pacsId);
	
	public String updateStLoanRecoveryEntry(StLoanRecoveryData data);
	
	public String saveMtLoanRecoveryEntry(LoanRecoveryData recoveryData);
	
	public List<LoanRecoveryData> getMtLoanRecovery(Integer pacsId);
	
	public String updateMtRecoveryStatus(LoanRecoveryData recoveryData);
}
