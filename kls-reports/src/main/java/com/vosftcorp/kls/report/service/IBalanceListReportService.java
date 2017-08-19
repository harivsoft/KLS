package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.BalanceListReportData;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public interface IBalanceListReportService {

	public List<BalanceListReportData> getBalanceListReportData(Integer pacsId, Integer productId, Long customerId, Date asOnDate);

}
