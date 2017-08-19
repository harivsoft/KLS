package com.vsoftcorp.kls.report.dao;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 *
 */
public interface IBalanceListReportDAO {

	List<LineOfCredit> getLineOfCreditByPacsAndCustomerId(Integer pacsId, Integer productId, Long customerId);

	List<Object[]> getBalanceListReportData(Long lineOfcreditId, Date asOnDate);

}
