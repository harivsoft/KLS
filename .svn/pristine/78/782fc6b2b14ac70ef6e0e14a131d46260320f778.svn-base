package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.dataaccess.IBranchDAO;
import com.vsoftcorp.kls.dataaccess.ICropDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.dao.ICropWiseDrawlReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.CropWiseDrawalReportData;

/**
 * 
 * @author a1605
 * 
 */
public class CropWiseDrawlReportHelper {

	public static List<CropWiseDrawalReportData> getCropWiseDrawlReportData(List<Object[]> dbResult, String month, Integer branchId) {

		BigDecimal zero = BigDecimal.ZERO.setScale(2);
		ICropWiseDrawlReportDAO dao = KLSReportDataAccessFactory.getCropWiseDrawlReportDAO();
		ICropDAO cropDAO = KLSDataAccessFactory.getCropDAO();
		ISeasonDAO seasonDAO = KLSDataAccessFactory.getSeasonDAO();
		IPacsDAO pacsDAO = KLSDataAccessFactory.getPacsDAO();
		

		List<CropWiseDrawalReportData> result = new ArrayList<CropWiseDrawalReportData>();
		CropWiseDrawalReportData data = null;

		for (Object[] object : dbResult) {
			data = new CropWiseDrawalReportData();

			Integer pacsId = (Integer) object[0];
			Integer cropId = (Integer) object[1];
			BigInteger seasonId = (BigInteger) object[2];
			//Integer branchId = (Integer) object[3];
			
			Pacs pacs = pacsDAO.getPacs(pacsId);
			Crop crop = cropDAO.getCrop(cropId);
			Season season = seasonDAO.getSeason(seasonId.longValue());
			System.out.println("season value ::::" +data.getSeasonId());
			//Integer branchId = pacs.getBranch().getId();
			


			//data.setBranchId(pacs.getBranch().getId());
			//System.out.println("branchId:::" +data.getBranchId());
			//data.setBranchName(pacs.getBranch().getName());
		//	System.out.println("branchName::::"+data.getBranchName());
			 
			data.setPacsId(pacs.getId());
			data.setPacName(pacs.getName());
			data.setCropId(crop.getId());
			data.setCropName(crop.getName());
			data.setSeasonId(season.getId());
			data.setSeasonName(season.getName());

			
			Object[] duringMonthData = dao.getCropWiseDrawlDuringtheMonthData(pacsId, cropId, seasonId.intValue(), month, branchId);
			Object[] previousData = dao.getCropWiseDrawlPreviousData(pacsId, cropId, seasonId.intValue(), month, branchId);
			BigDecimal sfmfAmtEndOfPrevMonth = (BigDecimal) previousData[0];
			BigDecimal sfmfAmtDuringMonth = (BigDecimal) duringMonthData[0];
			BigDecimal sfmfAcresOfPrevMonth;
			BigDecimal sfmfAcreDuringMonth;
			BigDecimal scstAmtEndOfPrevMonth = (BigDecimal) previousData[1];
			BigDecimal scstAmtDuringMonth = (BigDecimal) duringMonthData[1];
			BigDecimal scstAcresOfPrevMonth;
			BigDecimal scstAcreDuringMonth;
			BigDecimal womanAmtEndOfPrevMonth = (BigDecimal) previousData[2];
			BigDecimal womanAmtDuringMonth = (BigDecimal) duringMonthData[2];
			BigDecimal womanAcresOfPrevMonth;
			BigDecimal womanAcreDuringMonth;
			data.setSfmfAmtDuringMonth(sfmfAmtDuringMonth != null ? sfmfAmtDuringMonth.setScale(2) : zero);
			data.setScstAmtDuringMonth(scstAmtDuringMonth != null ? scstAmtDuringMonth.setScale(2) : zero);
			data.setWomanAmtDuringMonth(womanAmtDuringMonth != null ? womanAmtDuringMonth.setScale(2) : zero);
			data.setSfmfAmtEndOfPrevMonth(sfmfAmtEndOfPrevMonth != null ? sfmfAmtEndOfPrevMonth.setScale(2) : zero);
			data.setScstAmtEndOfPrevMonth(scstAmtEndOfPrevMonth != null ? scstAmtEndOfPrevMonth.setScale(2) : zero);
			data.setWomanAmtEndOfPrevMonth(womanAmtEndOfPrevMonth != null ? womanAmtEndOfPrevMonth.setScale(2) : zero);


			result.add(data);
		}
		return result;
	}
}