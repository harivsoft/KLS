package com.vosftcorp.kls.report.dao.dcb;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.time.Date;

/**
 * @author a1605
 */
public interface IDCBReportDAO {
	
public List<Map> getDCBReport(Integer branchId,Integer pacId,Integer schemeId,Long customerId,Date fromDate,Date toDate);	

public AccountingMoney getPreviousPrincipalOverdueDemand(Long accountId,Date fromDate,Integer pacId,Integer schemeId);

public AccountingMoney getPreviousInterestOverdueDemand(Long accountId, Date fromDate,Integer pacId,Integer schemeId);

public AccountingMoney getCurrentPrincipalDemand(Long accountId, Date fromDate,Date toDate,Integer demandFreequency,Integer pacId,Integer schemeId);

public AccountingMoney getCurrentInterestDemand(Long accountId, Date fromDate, Date toDate, Integer demandFreequency,Integer pacId,Integer schemeId);

public AccountingMoney getOverduePrincipleCollection(Long accountId, Date fromDate,Date toDate,Integer pacId,Integer schemeId,Integer demandFreequency);

public AccountingMoney getOverdueInterestCollection(Long accountId, Date fromDate, Date toDate,Integer pacId,Integer schemeId,Integer demandFreequency);

public AccountingMoney getAdvancePrincipleCollection(Long accountId, Date fromDate,
		Date toDate, Integer pacId, Integer schemeId,Integer demandFreequency);

public AccountingMoney getAdvanceInterestCollection(Long accountId, Date fromDate, Date toDate,
		Integer pacId, Integer schemeId,Integer demandFreequency);

public AccountingMoney getTotalPrincipleCollection(Long accountId, Date fromDate,
		Date toDate, Integer pacId, Integer schemeId,Integer demandFreequency);

public AccountingMoney getTotalInterestCollection(Long accountId, Date fromDate, Date toDate,
		Integer pacId, Integer schemeId,Integer demandFreequency);

public AccountingMoney getOverduePrincipleAsOnFromDate(Long accountId, Date fromDate,
		Date toDate, Integer pacId, Integer schemeId,Integer demandFreequency);

public AccountingMoney getOverdueInterestAsOnFromDate(Long accountId, Date fromDate, Date toDate,
		Integer pacId, Integer schemeId,Integer demandFreequency);
}
