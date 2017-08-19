package com.vsoftcorp.kls.service;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.kls.data.ScaleOfFinanceData;

/**
 * 
 * @author a9152
 * 
 */
public interface IScaleOfFinanceService {

	/**
	 * Saves the scale of finance data to the DB.
	 * 
	 * @param scaleOfFinanceData
	 */
	public void saveScaleOfFinance(ScaleOfFinanceData scaleOfFinanceData);

	/**
	 * Updates the scale of finance data to the DB.
	 * 
	 * @param scaleOfFinanceData
	 */
	public void updateScaleOfFinance(ScaleOfFinanceData scaleOfFinanceData);

	/**
	 * Fetch all the scale of finance data from the database.
	 * 
	 * @return
	 */
	public List<ScaleOfFinanceData> getAllScaleOfFinances();

	/**
	 * Get the scale of finance amount from the database.
	 * @param pacsId 
	 * 
	 * @return
	 */
	public String getScaleOfFinanceAmount(Long seasonId, Integer cropId,
			Integer landTypeId, BigDecimal landArea, Integer pacsId);
	
	/**
	 * Fetch the scale of finance data from the database.
	 * @return {@link ScaleOfFinanceData}
	 */

	public ScaleOfFinanceData getScaleOfFinance(String areaType,
			Integer areaTypeCode, Long seasonId, Integer landTypeId, Integer cropId);

}
