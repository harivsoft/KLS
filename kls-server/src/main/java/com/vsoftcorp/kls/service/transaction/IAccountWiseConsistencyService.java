package com.vsoftcorp.kls.service.transaction;

import java.util.List;

import com.vsoftcorp.kls.business.entity.transaction.AccountWiseConsistency;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1565
 *
 */


public interface IAccountWiseConsistencyService {

	public List<AccountWiseConsistency> checkAccountWiseConsistency(Date date);
	
}
