/**
 * 
 */
package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.BankParameter;

/**
 * @author a9152
 * 
 */
public interface IBankParameterDAO {

	/**
	 * Saves the bank parameter data to the database.
	 * 
	 * @param bankParameter
	 */
	public void saveBankParameter(BankParameter bankParameter);

	/**
	 * Updates the existing bank parameter data to the database.
	 * 
	 * @param bankParameter
	 */
	public void updateBankParameter(BankParameter bankParameter);

	/**
	 * Checks if the bank parameter id exists in the database.
	 * 
	 * @param bankParameter
	 * @return BankParameter
	 */
	public BankParameter getBankParameter(BankParameter bankParameter);

	/**
	 * 
	 * @param bankParameterId
	 * @return
	 */
	public BankParameter getBankParameterById(Integer bankParameterId);

	/**
	 * Returns all the bank parameter records to the client.
	 * 
	 * @return
	 */
	public List<BankParameter> getAllBankParameters();

}
