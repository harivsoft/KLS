package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.BorrowingRecoveryEntry;

public interface IBorrowingRecoveryEntryDAO {

	BorrowingRecoveryEntry getBorrowingRecoveryEntry(
			BorrowingRecoveryEntry master, boolean b);

	public void saveBorrowingRecoveryEntry(BorrowingRecoveryEntry master);
	
	public Map getBorrowingRecoveryAmounts(Long accountId);

	List<BorrowingRecoveryEntry> getAllBorrowingRecoveryEntriesByProdId(
			Integer borrowingProdId, Integer status);

	void updateBorrowingRecoveryEntry(BorrowingRecoveryEntry master);

	void deleteBorrowingRecoveryEntry(Long borrowingRecoveryId);
	
	public List<BorrowingsLineOfCredit> getBlocsPerBproduct(Integer productId,Integer pacsId);
	
	public List<BorrowingRecoveryEntry> getAllBorrowingRecoveryEntriesByPacsId(
			Integer pacsId, Integer status); 
   
	public BorrowingRecoveryEntry getBorrowingRecoveryEntryById(Long id);
}
