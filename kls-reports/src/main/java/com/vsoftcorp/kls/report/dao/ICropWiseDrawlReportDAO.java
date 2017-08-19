package com.vsoftcorp.kls.report.dao;

import java.util.List;

/**
 * 
 * @author a1605
 *
 */
public interface ICropWiseDrawlReportDAO {



	public List<Object[]> getCropWiseDrawlData(Integer pacsId, Integer cropId, Integer seasonId, Integer branchId);

	public Object[] getCropWiseDrawlPreviousData(Integer pacsId, Integer cropId, Integer seasonId, String month, Integer branchId);

	public Object[] getCropWiseDrawlDuringtheMonthData(Integer pacsId, Integer cropId, Integer seasonId, String month, Integer branchId);
	
}