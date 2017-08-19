package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vosftcorp.kls.report.service.IPurposeWiseDisbursmentService;
import com.vsoftcorp.kls.report.dao.IPurposeDisbursmentReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.PurposeWiseDisbursmentData;
import com.vsoftcorp.kls.report.service.helper.PurposeWiseDisbursmentHelper;
/**
 * 
 * @author a1605
 *
 */
public class PurposeWiseDisbursmentService implements IPurposeWiseDisbursmentService{

	IPurposeDisbursmentReportDAO dao=KLSReportDataAccessFactory.getPurposeDisbursmentReportDAO();
	@Override
	public List<PurposeWiseDisbursmentData> getPurposeWiseDisbursmentData(Integer pacsId, Integer purposeId, Integer productId, String loanType,String month ,Integer branchId) {
			List<PurposeWiseDisbursmentData> result = new ArrayList<PurposeWiseDisbursmentData>();
			List<Map> dbResult = dao.getPurposeWiseDisbursmentData(pacsId, purposeId, productId, loanType);
			result = PurposeWiseDisbursmentHelper.getPurposeWiseDisbursmentData(dbResult, month,branchId);
			return result;
		}
	
}