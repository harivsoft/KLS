/**
 * 
 */
package com.vsoftcorp.kls.service.account;

import java.util.List;

import com.vsoftcorp.kls.data.AccountData;
import com.vsoftcorp.kls.data.SBAccountMappingData;

/**
 * @author a9152
 *
 */
public interface IAccountPropertyService {
	
	public boolean checkIfAccountExists(String custId);

	public List<String> getAccountNumberListByPacsId(Integer pacsId);
	
	public AccountData getAccountInfoByCustId(Long custId) ;
	
	public String mapSavingsAccountWithCust(SBAccountMappingData data);
	
	public String getSbByAtmLoanAccount(String atmLoanAccount);

}
