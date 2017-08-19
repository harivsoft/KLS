package com.vosftcorp.kls.report.service.impl;

import java.util.List;

import com.vosftcorp.kls.report.service.IInconsistencyReportService;
import com.vsoftcorp.kls.business.entity.transaction.AccountWiseConsistency;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.InconsistencyConsistencyData;
import com.vsoftcorp.kls.report.service.helper.InconsistencyReportHelper;

public class InconsistencyReportService implements IInconsistencyReportService {
	@Override
	public List<InconsistencyConsistencyData> getInConsistencyReport() {
		List<AccountWiseConsistency> inconsistencyList = null;
		try {
			inconsistencyList = KLSReportDataAccessFactory
					.getInconsistencyReportDAO().getInconsistencyReport();
			InconsistencyReportHelper
					.getInconsistencyReportDataList(inconsistencyList);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return InconsistencyReportHelper
				.getInconsistencyReportDataList(inconsistencyList);
	}

}
