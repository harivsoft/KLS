package com.vosftcorp.kls.report.service.dcb.impl;

import java.util.ArrayList;
import java.util.List;

import com.vosftcorp.kls.report.dao.dcb.ISTMTLTDcbReportDAO;
import com.vosftcorp.kls.report.service.dcb.ISTMTLTDcbReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.STMTLTDcbReportData;
import com.vsoftcorp.kls.report.service.helper.STMTLTDCBReportHelper;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class STMTLTDcbReportService implements ISTMTLTDcbReportService {
	private ISTMTLTDcbReportDAO dao = KLSReportDataAccessFactory.getstmtltDcbReportDAO();

	@Override
	public List<STMTLTDcbReportData> getSTMTLTDcbReportData(Integer pacsId, Date asOnDate,Integer branchId,String branchName) {
		List<STMTLTDcbReportData> result = new ArrayList<STMTLTDcbReportData>();
		result = STMTLTDCBReportHelper.getSTMTLTDcbReportData(pacsId, asOnDate,branchId,branchName);
		return result;
	}
}
