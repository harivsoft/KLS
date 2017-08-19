package com.vsoftcorp.kls.report.dao;

import java.util.List;
import java.util.Map;

public interface IPurposeDisbursmentReportDAO {

	public List<Map> getPurposeWiseDisbursmentData(Integer pacsId, Integer purposeId, Integer productId,String loanType);

	public List<Object[]> getPurposeWiseDisbursmentUptoPeriod(Integer pacsId, Integer productId, Integer purposeId, String month, Integer branchId);

}