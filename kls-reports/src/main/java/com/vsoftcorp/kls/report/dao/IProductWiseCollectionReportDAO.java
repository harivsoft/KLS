package com.vsoftcorp.kls.report.dao;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.time.Date;
/**
 * 
 * @author a1605
 *
 */
public interface IProductWiseCollectionReportDAO {

	public List<LoanLineOfCredit> getProductWiseCollectionReportData(Integer pacsId, Integer productId, Integer purposeId, String loanType);

	public Object[] getProductWiseCollectionReportDataByLocId(Long locId, Date fromDate, Date toDate);

}
