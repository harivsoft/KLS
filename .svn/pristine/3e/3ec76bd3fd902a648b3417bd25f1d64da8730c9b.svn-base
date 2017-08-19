package com.vsoftcorp.kls.dataaccess.transaction;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.business.entity.transaction.AccountWiseConsistency;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
/**
 * 
 * @author a1565
 *
 */
public interface IAccountWiseConsistencyDAO{
	
	/**
	  *insert data if inconsistency
	  * @return
	  */
	public void saveInconsistency(AccountWiseConsistency accountWiseConsistency);
	
	/**
	 *To update status as Y when Inconsistency resolved
	 **/
	public void updateInconsistencyAsConsistency(AccountWiseConsistency acMaster);
	/**
	 * TO GET INCONSISTENCY RECORDS WHERE STATUS IS N
	 * @return
	 */
	public List<AccountWiseConsistency> getAllInConsistency();
	/**
	 * TO GET COUNT OF TRANSACTION BASED ON lineOfcredit id
	 * @param masterLid
	 * @return
	 */
	public List<TransactionHistory> getTransactionBasedOnLid(Long masterLid);
	/**
	 *To get  current transactions Based on  lineOfcredit id
	 * @param masterLid
	 * @return
	 */
	public List<CurrentTransaction> getCurrentTransactionBasedOnLid(Long masterLid);
	/**
	 * To get transaction Amount of all transactions group by lineOfcredit id 
	 * @return
	 */
	public List<Object[]> getAllTotalTransactionsAmount();
	/**
	 * To get CurrentBalance from lineOfCredit table  
	 * @return
	 */
	public List<Map> getAllLineOfCreditsAmount();
	

}
