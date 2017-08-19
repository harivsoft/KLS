package com.vsoftcorp.kls.report.dao;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.time.Date;

public interface INPAReportDAO {
	
	public List<LineOfCredit> getSTOverDueList(Date asOnDate);
	
	public List<LineOfCredit> getMTOverDueList(Date asOnDate);
	
	public Date getNpaDate(Season season, String asOnDate) ;
	
	public Integer getNoOfMonths(Date drawalStartDate,Date dueDate);
	
	public Object[] getMemberData(Long customerId);
	
	public Integer getSeasonMonths(Season season, Integer noOfSeasons);
	
	public List<LoanRepaymentSchedule> getLoanRepaymentScheduleByLocIdAndBusinessDate(Long locId, Date businessDate);
	
	public BigDecimal getCurrentBalanceAsOnDate(Long locId,Date asOnDate);
	
	public Season getCurrentSeason(String businessDate);

	//For Borrowing NPA.
	public List<LineOfCredit> getBorrowingSTOverDueList(Date asOnDate);

	public List<LineOfCredit> getBorrowingMTOverDueList(Date asOnDate);
	
	public String getLoanLineOfCreditById(Long loanLocId,Date asOnDate);

	public BigDecimal geMtLtPrincipleOverDue(Long loanLocId, Date asOnDate);
	
	public BigDecimal geMtLtIntOverDue(Long loanLocId, Date asOnDate);

	public List<LineOfCredit> getSTOverDueListBasedOnproductId(Date asOnDate,Integer productId,Integer pacsId);

	public List<LineOfCredit> getMTOverDueListBasedOnproductId(Date asOnDate,Integer productId,Integer pacsId);
	
	public String getProductName(Integer id);


}
