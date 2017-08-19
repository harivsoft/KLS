package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementEntry;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanDisbursement;

public interface ILoanDisbursementDAO {
	
	public void saveLoanDisbursement(PacsLoanDisbursement loanDisbursement);
	
	public String getDisburshmentScheduleAmout(long id, String date);
	
	public List<LoanDisbursementSchedule> getDisbursementSchedulesByDate(long id, String date);
	
	public boolean isFirstDisbursement(long id);
	
	public boolean isPacsSuvikasVoucherExist(String suvikasVoucherId, String suvikasVoucherDate);

	public Money getDisbursedAmountByLocId(Long locId);
	
	public List<Object[]> getloanDisburseEntryList(String pacsId,String transactionDate);
	
	public List<Object[]> getBulkDisbursementDetails(String loanType,String disbursementDate,String instrumentNo,String pacsId);

}
