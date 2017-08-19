package com.vosftcorp.kls.report.service;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.kls.report.service.data.OverdueListReportData;
import com.vsoftcorp.time.Date;

public interface IOverdueReportService {

	List<OverdueListReportData> getOverdueReport(Integer pacsId, Integer productId, String memberNo,
			Date instalmentDate, BigDecimal villageId, String loanType, String reportMode);

}
