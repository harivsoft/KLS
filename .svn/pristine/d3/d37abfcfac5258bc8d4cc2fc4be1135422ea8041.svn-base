/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan;

import com.vsoftcorp.kls.business.entities.SBAccountMapping;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;

/**
 * @author a9152
 * 
 */
public interface IAccountPropertyDAO {

	public AccountProperty getAccountProperty(String savingsAccountNumber, boolean isCloseSession);

	public AccountProperty getAccountProperty(AccountProperty accountProperty, boolean isCloseSession);

	public AccountProperty getAccountPropertyById(Long accountPropertyId, boolean isCloseSession);

	public boolean checkIfAccountExists(String custId, boolean isCloseSession);

	public AccountProperty getAccountByCustId(Long custId);
	
	public String mapSavingsAccountWithCust(Long CustId, String accountNo);
	
	public boolean checkIfAccountPropertyExists(Long custId, boolean isCloseSession);
	
	public String saveSBAccountMappingInfo(SBAccountMapping master);
	
	public AccountProperty getAccountPropertyByAtmLoanAccount(String atmLoanAccount, boolean isCloseSession);
}
