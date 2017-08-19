package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a9152
 * 
 */
public interface ISlabwiseInterestRateDAO {

	/**
	 * Saves the slab wise interest rate data to the database.
	 * 
	 * @param slabwiseInterestRate
	 */
	public void saveSlabwiseInterestRate(SlabwiseInterestRate slabwiseInterestRate);

	/**
	 * Updates the existing slab wise interest rate data to the database.
	 * 
	 * @param slabwiseInterestMaster
	 */
	public void updateSlabwiseInterestRate(SlabwiseInterestRate slabwiseInterestRate);

	/**
	 * Checks if the slab wise interest rate data exists in the database.
	 * 
	 * @param slabwiseInterestMaster
	 * @return SlabwiseInterestMaster
	 */
	public SlabwiseInterestRate getSlabwiseInterestRate(SlabwiseInterestRate slabwiseInterestRate);

	/**
	 * Returns all the slab wise interest rate records to the client.
	 * 
	 * @return
	 */
	public List<SlabwiseInterestRate> getAllSlabwiseInterestRateRecords();

	/**
	 * Saves the list of slab wise interest data to the database.
	 * 
	 * @param slabwiseInterestRateList
	 */
	public void saveSlabwiseInterestRate(List<SlabwiseInterestRate> slabwiseInterestRateList);

	/**
	 * Deletes the slab wise interest data record from the database.
	 * 
	 * @param slabwiseInterestRateId
	 */
	public void deleteSlabwiseInterestRateRecord(Long slabwiseInterestRateId);

	/**
	 * Returns all the slab wise interest rate records for a particular interest
	 * category id to the client.
	 * 
	 * @return
	 */
	public List<SlabwiseInterestRate> getSlabwiseInterestRateRecords(Integer interestCategoryId, Date effectivedate);
}
