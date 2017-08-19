package com.vosftcorp.kls.report.service.impl;

import java.util.List;

import com.vosftcorp.kls.report.service.IProductWiseCollectionReportService;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.ProductWiseCollectionReportData;
import com.vsoftcorp.kls.report.service.helper.ProductWiseCollectionReportHelper;
import com.vsoftcorp.time.Date;
/**
 * 
 * @author a1605
 *
 */
public class ProductWiseCollectionReportService implements IProductWiseCollectionReportService {
@Override
	public List<ProductWiseCollectionReportData> getProductWiseCollectionReportData(Integer pacsId, Integer productId, Integer purposeId,
			String loanType, Date fromDate, Date toDate) {

		List<LoanLineOfCredit> locList = KLSReportDataAccessFactory.getProductWiseCollectionReportDAO().getProductWiseCollectionReportData(pacsId,
				productId, purposeId, loanType);

		List<ProductWiseCollectionReportData> result = ProductWiseCollectionReportHelper
				.getProductWiseCollectionReportData(locList, fromDate, toDate);

		return result;

	}

}
