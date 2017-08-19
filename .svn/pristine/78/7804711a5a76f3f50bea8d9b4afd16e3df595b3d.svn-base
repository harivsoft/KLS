package com.vsoftcorp.kls.service.account;

import java.util.List;

import com.vsoftcorp.kls.data.BorrowingsAccountPropertyData;

public interface IBorrowingAccountPropertyService {
	/**
	 * 
	 * @param pacId
	 * @param productId
	 * @return
	 */
	public boolean checkIfAccountExists(int pacId, Integer productId);

	public String saveBorrowingAccountProperty(BorrowingsAccountPropertyData borrowingsAccountPropertyData);

	public List<BorrowingsAccountPropertyData> getAllAccounts();

	public String deleteBorrowingAccountProperty(Long accountId);

	public BorrowingsAccountPropertyData getAccountPropertyByBorrowingProductId(Integer borrowingProductId);

	public BorrowingsAccountPropertyData getAccountPropertyByBorrowingProductIdPacsId(Integer pacsId,Integer borrowingProductId);
	
	public BorrowingsAccountPropertyData getAccountPropertyByProductIdPacsId(Integer pacsId,Integer productId);
}
