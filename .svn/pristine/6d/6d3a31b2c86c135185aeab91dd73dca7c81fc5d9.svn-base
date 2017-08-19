package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.time.Date;

public interface ILineOfCreditDAO {
	public void saveLineOfCredit(LineOfCredit account);
	
	public void saveLineOfCredit(LineOfCredit account, BorrowingsLineOfCredit bLoc);


	public LineOfCredit getLineOfCredit(int season, int cropString, Long custId, int product);

	public void updateLineOfCredit(LineOfCredit account);

	public Money getTotalSanctionAmount(Long customerId,
			Date financialYearBeginDate, Date financialYearEndDate);

	public List getLineOfCreditAccountsList(Long accountId,boolean isCloseSession,String query);

	public LineOfCredit getLineOfCreditById(Long id,boolean isCloseSession);

	public void updateLineOfCreditInterestbyId(Long id,
			Money theInterestDueAmount);
	
	public Boolean isFirstDrawal(Long accountId,Long seasonId);

	public List<LineOfCredit> getLocListByCustomerId(Long customerId);
	
	public void updateCurrentBalence(Long Id, AccountingMoney CurrentBalence);
	
	public boolean checkLoanStatus(Long custId);
	
	public boolean isLOCExistDAO(Integer schemeId, Long seasonId);
	
	public LineOfCredit getLocId(Long locId);
	
	public List<LineOfCredit> getActiveLocListByCustomerId(Long customerId);
	public String getLineOfExpiryDate(Long locId);
}
