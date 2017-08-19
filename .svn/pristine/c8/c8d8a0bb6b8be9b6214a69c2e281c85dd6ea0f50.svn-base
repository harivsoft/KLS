package com.vsoftcorp.kls.dataaccess;

import java.math.BigDecimal;
import java.util.List;

import com.vsoftcorp.kls.business.entities.ScaleOfFinance;

/**
 * 
 * @author a9152
 * 
 */
public interface IScaleOfFinanceDAO {

	/**
	 * Saves the scale of finance data to the database.
	 * 
	 * @param scaleOfFinance
	 */
	public void saveScaleOfFinance(ScaleOfFinance scaleOfFinance);

	/**
	 * Updates the existing scale of finance data to the database.
	 * 
	 * @param scaleOfFinance
	 */
	public void updateScaleOfFinance(ScaleOfFinance scaleOfFinance);

	/**
	 * Checks if the scale of finance code exists in the database.
	 * 
	 * @param scaleOfFinance
	 * @return ScaleOfFinance
	 */
	public ScaleOfFinance getScaleOfFinance(ScaleOfFinance scaleOfFinance);

	/**
	 * Returns all the scale of finance records to the client.
	 * 
	 * @return
	 */
	public List<ScaleOfFinance> getAllScaleOfFinances();

	/**
	 * Returns the scale of finance amount to the client.
	 * 
	 * @return
	 */
	public BigDecimal getScaleOfFinanceAmount(Long seasonId,
			Integer cropId, Integer landTypeId, Integer pacsId);

	public ScaleOfFinance getScaleOfFinance(String areaType,
			Integer areaTypeCode, Long seasonId, Integer landTypeId,
			Integer cropId);

}
