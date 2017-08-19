package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.LineOfCredit;

public interface ILocClosureDAO {
	public void updateLocClosure(LineOfCredit loc);
	public List<Long> getAllLineOfCreditIdsForLocClosure(String businessDate);
}
