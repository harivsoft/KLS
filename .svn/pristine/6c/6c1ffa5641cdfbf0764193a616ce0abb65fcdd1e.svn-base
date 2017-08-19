package com.vsoftcorp.kls.report.dao;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.time.Date;

public interface IOverdueReportDAO {

	List<Object[]> getOverdueReport(Integer pacsId, Integer productId, String memberNo,
			Date instalmentDate, BigDecimal villageId, String loanType, String reportMode);

	public Object[] getOutStandingValByLocIdAndBusinessDate(Long locId, Date instalmentDate);

	Object[] getOutStandingValByLocIdAndBusinessDateForST(Long locId, Date instalmentDate);

	Long getOverDueLoc(Long locId, Date instalmentDate);

	BigDecimal getDisbursedAmountByLocId(Long locId);

}
