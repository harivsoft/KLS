package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementEntry;
import com.vsoftcorp.time.Date;



/**
 * 
 * @author a1623
 *
 */
public interface ILoanDisbursementEntryDAO {

	public void saveLoanDisbursementEntry(LoanDisbursementEntry master);

	public List<LoanDisbursementEntry> getDisbursementEntriesList(long customerNumber,
			Integer status, String loanType);

	public void updateLoanDisbursementEntry(LoanDisbursementEntry master);

	public List<LoanDisbursementEntry> getDisbursementEntriesListByLocId(long customerNumber,
			Integer status, long locId, String loanType);

	//public List<Map> getLoanInfo(Integer pacsId, Long customerNumber, String loanType);

	public List<Long> getDisbursementLocIdsEntriesList(Long customerId, int i,
			String loanType);

	public List<Map> getLineOfCredit(Integer pacsId, Long customerNumber, String loanType);
	
	public LoanDisbursementEntry getLoanDisbursementEntryByVoucherNumber(Date businessDate, String voucherType,Integer pacsId);
	
	public LoanDisbursementEntry getDisbursementEntries(long customerNumber,
			Integer status, long locId, String loanType,String transactionDate);
	
	public void updateBulkDisbursementEntry(Integer pacsId,String customerId,String loanType);
	
	public String removeBulkDisbursementEntry(String id);

}
