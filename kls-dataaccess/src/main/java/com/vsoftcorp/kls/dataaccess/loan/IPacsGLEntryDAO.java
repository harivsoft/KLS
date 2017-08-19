package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.accounting.types.AccountingMoney;

public interface IPacsGLEntryDAO {
	
	public AccountingMoney getAmountForPacsBankAccount(Integer pacsId);

	public List<Map> getBankGLTransactions();

}
