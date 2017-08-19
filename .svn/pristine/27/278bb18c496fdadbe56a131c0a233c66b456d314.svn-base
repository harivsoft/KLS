/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.time.Date;

/**
 * @author a9153
 * 
 */
public interface IBorrowingsLineOfCreditDAO {

	// public List<BorrowingsLineOfCredit> getLoanLocListByCustomerId(Long
	// customerId);

	public BorrowingsLineOfCredit getBorrowingsLineOfCreditById(Long loanLocId);

	// public List<Long> getLoanLocListByApplicationId(Long id);

	public void saveBorrowingsLineOfCredit(BorrowingsLineOfCredit borrowingsLoc);

	public BorrowingsLineOfCredit getBorrowingsLineOfCreditByBorrowingAccountId(
			Long borrowingAccountId);

	public void updateBorrowingsLineOfCredit(BorrowingsLineOfCredit borrowingsLoc,
			boolean beginNewTransaction);

	public BorrowingsLineOfCredit saveBorrowingsLOCAndPostTransaction(BorrowingsLineOfCredit borrowingsLoc,
			CurrentTransaction currTrans);

	//public List<BorrowingsLineOfCredit> getOrderedBorrowingLocList();

	public Boolean isAllAmountsClear(Long locId);

	public BorrowingsLineOfCredit getBorrowingsLineOfCreditsByProductPacAndDate(Integer productId,
			Integer pacsId, Date businessdate);

	public void deleteAllBorrowingsLocs();

	public List<BorrowingsLineOfCredit> getOrderedBorrowingLocListByAccountId(Long accountId);

	public List<BorrowingsLineOfCredit> getBlocsPerLoanLoc(Long locId, Integer productId,Integer pacsId);
	// public BorrowingsLineOfCredit
	// mergeBorrowingsLineOfCredit(BorrowingsLineOfCredit borrowingsLoc);

}
