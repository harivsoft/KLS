/**
 * 
 */
package com.vsoftcorp.kls.service.account;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.account.LoanAccountProperty;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.data.LoanAccountEnumsData;
import com.vsoftcorp.kls.data.LoanAccountPropertyData;
import com.vsoftcorp.kls.data.LoanLineOfCreditData;
import com.vsoftcorp.kls.data.TransactionModeEnumsData;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public interface ILoanAccountPropertyService {

	/**
	 * 
	 * @param custId
	 * @return
	 */
	public LoanAccountProperty checkIfAccountExists(Long custId);

	/**
	 * 
	 * @param pacsAcctPropertyData
	 * @return
	 */
	public String saveLoanAccountProperty(LoanAccountPropertyData pacsAcctPropertyData);

	/**
	 * 
	 * @return
	 */
	public LoanAccountEnumsData getAllLoanAccountEnums();

	/**
	 * 
	 * @param slabList
	 * @param amount
	 * @return
	 */
	public BigDecimal getInterestRate(List<SlabwiseInterestRate> slabList, Money amount);

	/**
	 * 
	 * @param interestCategoryId
	 * @param sanctionDate
	 * @return
	 */
	public List<SlabwiseInterestRate> getInterestSlabs(Integer interestCategoryId, Date sanctionDate);

	/**
	 * 
	 * @param amount
	 * @param interestRate
	 * @return
	 */
	public Money calculateInterestAmount(Money amount, double interestRate);

	/**
	 * 
	 * @param amount
	 * @param interestRate
	 * @return
	 */
	public Money calculateInterestAmount(Money amount, double interestRate, double reducingFrequency);

	/**
	 * 
	 * @return
	 */
	public TransactionModeEnumsData getTransactionModeEnums();

	/**
	 * 
	 * @param loanLocData
	 * @return
	 */
	public String closeAccount(LoanLineOfCreditData loanLocData);

	/**
	 * 
	 * @param sanctionAmount
	 * @param numOfInstallments
	 * @return
	 */
	public Money calculatePrincipalAmount(Money sanctionAmount, Integer numOfInstallments);

	/**
	 * 
	 * @param amount
	 * @param loanPeriod
	 * @param interestRate
	 * @return
	 */
	public Money calculateInstallmentAmount(Money amount, Integer loanPeriod, double interestRate);

	/**
	 * 
	 * @param custId
	 * @return
	 */
	public AccountProperty checkIfAccountPropertyExists(Long custId);
	
	public List<LoanRepaymentSchedule> generateRepaymentScheduleByMultipleDisbursement(LoanLineOfCredit loanLoc, Money sanctionAmount, List<LoanRepaymentSchedule> myltipleRepaymentScheduleList, Date businessDate, Date installmentDate , String loanType , Long borrowingLoc);
	
}
