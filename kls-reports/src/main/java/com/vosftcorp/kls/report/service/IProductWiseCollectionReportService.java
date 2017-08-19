package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.ProductWiseCollectionReportData;
import com.vsoftcorp.time.Date;

/**
 * @author a1605
 */
public interface IProductWiseCollectionReportService {

	public List<ProductWiseCollectionReportData> getProductWiseCollectionReportData(Integer pacsId, Integer productId, Integer purposeId, String loanType,
			Date fromDate, Date toDate);
	
}
