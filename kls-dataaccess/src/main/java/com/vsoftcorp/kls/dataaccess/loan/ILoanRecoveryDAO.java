package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.StLoanRecovery;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRecovery;
import com.vsoftcorp.time.Date;

public interface ILoanRecoveryDAO {
	public List<LoanRecovery> getLoanRecoveryByLocId(Long locId);
	
	public Money getLoanRecoveryByLocIdAndDate(Long locId, Date asOnDate);

	public LoanRecovery getLoanRecovery(LoanRecovery master, boolean b);

	public void saveLoanRecovery(LoanRecovery master);
		
	public String saveSTRecovery(StLoanRecovery master);
	
	public List<StLoanRecovery> getStLoanRecoveryData(Integer pacsId);
	
	public String saveMtLoanRecovery(LoanRecovery master);
	
	public List<LoanRecovery> getMtLoanRecovery(Integer pacsId);

}
