package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.BorrowingsAccount;

/**
 * 
 * @author a9153
 *
 */

public interface IBorrowingsAccountDAO {

	public void saveBorrowingsAccount(BorrowingsAccount theBorrowingsAccount);

	public void updateBorrowingsAccount(BorrowingsAccount theBorrowingsAccount);
	
	public BorrowingsAccount getBorrowingsAccount(BorrowingsAccount theBorrowingsAccount, boolean isCloseSession);
	
	public List<BorrowingsAccount> getAllBorrowingsAccounts(boolean isCloseSession);
	
	public String getBorrowingsAccNo(String bankCode, int branchId, int pacsId, int productId);
	
	public BorrowingsAccount getPacsBorrowingsAccNo(String bankCode, int branchId, int pacsId, int productId);

}
