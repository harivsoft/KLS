package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.Account;

public interface IAccountDAO {

	public void saveAccount(Account account);

	public void updateAccount(Account account);

	public Account getAccount(Account id);

	public Account getAccount(Account account, boolean isCloseSession);

	public List<Account> getAllAccounts();

	public Account getAccountByCustomer(String customerId);

	public int getRecentAccount(String branchIdPacsIdProductIdString);

	public Account getAccount(String accountNumber);

	public Account getAccount(Long accountPropertyId, boolean isCloseSession);

	public Account getAccountById(Long accountId, boolean isCloseSession);

	public void deleteAccount(Long accountId);

	List<String> getAccountNumbersByPacsId(Integer pacsId);
	
	public Account getAccountByCardNumber(String cardNumber);
}
