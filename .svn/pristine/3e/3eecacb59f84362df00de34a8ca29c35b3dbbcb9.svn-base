package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.vosftcorp.kls.report.service.ICropWiseDrawlReportService;
import com.vsoftcorp.kls.report.dao.ICropWiseDrawlReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.CropWiseDrawalReportData;
import com.vsoftcorp.kls.report.service.helper.CropWiseDrawlReportHelper;

/**
 * 
 * @author a1605
 * 
 */
public class CropWiseDrawlReportService implements ICropWiseDrawlReportService {

	private ICropWiseDrawlReportDAO dao = KLSReportDataAccessFactory.getCropWiseDrawlReportDAO();

	@Override
	public List<CropWiseDrawalReportData> getCropWiseDrawlData(Integer pacsId, Integer cropId, Integer seasonId, String month, Integer branchId) {
		
		List<CropWiseDrawalReportData> result = new ArrayList<CropWiseDrawalReportData>();
		List<Object[]> dbResult = dao.getCropWiseDrawlData(pacsId, cropId, seasonId, branchId);
		result = CropWiseDrawlReportHelper.getCropWiseDrawlReportData(dbResult, month,branchId);
		return result;
	}

}