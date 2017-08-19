package com.vsoftcorp.kls.report.dao;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.time.Date;
/**
 * @author a1605
 *
 */
public interface IDrawalReportDAO {
	List<Object[]> getDrawalReport(Integer pacId,Integer schemeId,Long seasonId,Date fromDate,Date toDate);

	BigDecimal getAccountBalance(long locId, Date toDate);
}