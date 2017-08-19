package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.SlabwiseInterestRateData;

public interface ISlabwiseInterestRateService {

	/**
	 * Saves the slab wise interest rate data to the DB.
	 * 
	 * @param slabwiseInterestRateData
	 */
	public void saveSlabwiseInterestRate(
			SlabwiseInterestRateData slabwiseInterestRateData);

	/**
	 * Fetch all the slab wise interest rate data from the database.
	 * 
	 * @return
	 */
	public List<SlabwiseInterestRateData> getAllSlabwiseInterestRateRecords();
}
