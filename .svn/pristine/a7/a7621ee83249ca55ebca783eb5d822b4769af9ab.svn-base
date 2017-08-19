package com.vsoftcorp.kls.service.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.data.BorrowingRecoveryEntryData;
import com.vsoftcorp.kls.data.LoanRecoveryData;


public interface IBorrowingRecoveryEntryService {

	public String saveBorrowingRecoveryEntry(BorrowingRecoveryEntryData borrowingData);

	public Map getBorrowingRecoveryAmounts(Long accountId);

	public List<BorrowingRecoveryEntryData> getBorrowingRecoveryEntries(
			Integer borrowingProdId, Integer status);

	public String updateBorrowingRecoveryEntry(BorrowingRecoveryEntryData data);

	public String deleteBorrowingRecoveryEntry(Long borrowingRecoveryId);
	
	public String doBorrowingRecovery(BorrowingRecoveryEntryData data);
	
	public List<BorrowingRecoveryEntryData> getBorrowingRecoveryEntriesByPacsId(
			Integer borrowingProdId, Integer status);
	public BorrowingRecoveryEntryData getBorrowingRecoveryEntryById(Long id);
	public LoanRecoveryData getRocoveryInfoBasedOnAmountPaid(BigDecimal amountPaid, Integer recoverySequenceId, Long loanLocId);
}
