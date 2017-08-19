package com.vsoftcorp.kls.report.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface IInterestChargedReportDAO {
	
	public List<Object[]> getInterestChargedDataReport(
			Integer pacsId, Integer productId, Integer purposeId,
			Integer branchId, String productType, String fromDateString,
			String toDateString);

	public BigDecimal getInterestAmountBasedOnLocId(BigInteger locId, String fromDate, String toDateString);

	public BigDecimal getBalanceAmount(BigDecimal interestAmt, BigInteger locId, String fromDate, String toDateString);

}
