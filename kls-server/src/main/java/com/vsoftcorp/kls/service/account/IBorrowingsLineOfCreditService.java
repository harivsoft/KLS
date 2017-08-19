package com.vsoftcorp.kls.service.account;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.data.BorrowingsLineOfCreditData;

/**
 * 
 * @author a1605
 * 
 */
public interface IBorrowingsLineOfCreditService {

	public BorrowingsLineOfCredit createBorrowingLineOfCredit(LineOfCredit lineOfCredit);

	public BorrowingsLineOfCredit createBorrowingLineOfCredit(LoanLineOfCredit loanLineOfCredit);

	public void saveBorrowingLineOfCredit(BorrowingsLineOfCredit borrowingslineOfCredit);

	
//	public List<LoanLineOfCreditData> getLineOfCreditList(Long customerId);
//
//	public LoanLineOfCreditData getLineOfCreditDataById(Long loanLocId);
	
	public BorrowingsLineOfCreditData getBorrowingsLineOfCreditById(Long loanLocId);
    
    public List<BorrowingsLineOfCreditData> getOrderedBorrowingLocListByAccountId(Long accountId);

}
