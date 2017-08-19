package com.vsoftcorp.kls.report.dao;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.time.Date;

public interface IShareAccountBalancingReportDAO {

	List<Object[]> getShareAccountBalancingReport(Integer pacsId, Integer productId, String memberNo, Date asOnDate);

	Integer getCancelledSharesBasedOnCustId(String partyId, Date asOnDate);

	Object[] getShareAccountDetails(BigDecimal shareAccountId,Integer pacsId, Integer productId, String memberNo);

}
