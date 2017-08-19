package com.vsoftcorp.kls.report.dao;

import java.util.List;

import com.vsoftcorp.time.Date;
/**
 * @author a1605
 *
 */
public interface ICollectionReportDAO {

	List<Object[]> getCollectionReport(Integer pacId, Integer schemeId,
			Long seasonId, Date fromDate, Date toDate);
}
