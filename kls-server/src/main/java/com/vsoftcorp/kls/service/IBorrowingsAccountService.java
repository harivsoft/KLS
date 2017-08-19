package com.vsoftcorp.kls.service;


import java.util.List;

import com.vsoftcorp.kls.data.BorrowingsAccountData;

public interface IBorrowingsAccountService {

	public void saveBorrowingsAccount(BorrowingsAccountData theBorrowingsAccountData);

	public void updateBorrowingsAccount(BorrowingsAccountData theBorrowingsAccountData);
	
	public List<BorrowingsAccountData> getAllBorrowingsAccounts();
	
	public String getBorrowingsAccNo(String bankCode, int branchId, int pacsId, int productId);

}
