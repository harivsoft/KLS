package com.vsoftcorp.kls.report.dao;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.time.Date;


public interface IShareAccountLedgerReportDAO {
	public List<Object[]> getShareAccountLedgerReport(Integer pacId, Integer productId, String memberNo, Date fromDate,
			Date toDate);

	public Object[] getAllottedNoOfSharesAndValueByTransactionId(BigDecimal shareTransactionId, BigDecimal transCode1,
			BigDecimal transCode2);

	public Object[] getRedeemedNoOfSharesAndValueByTransactionId(BigDecimal shareTransactionId, BigDecimal transCode,
			BigDecimal transCode1);

	public Object[] getOpeningBal(Date fromDate, String customerId);

	public Object[] getCancelBal(Date fromDate, String customerId);

}
