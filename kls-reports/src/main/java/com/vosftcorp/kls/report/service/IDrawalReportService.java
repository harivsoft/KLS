package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.DrawalReportData;
import com.vsoftcorp.time.Date;
/**
 * @author a1605
 *
 */
public interface IDrawalReportService {

	public List<DrawalReportData> getDrawalReport(Integer pacId,
			Integer schemeId, Long seasonId, Date fromDate, Date toDate);

}
