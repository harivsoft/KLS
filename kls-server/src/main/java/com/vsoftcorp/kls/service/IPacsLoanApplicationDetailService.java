/**
 * 
 */
package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.PacsLoanApplicationDetailData;

/**
 * @author a9152
 * 
 */
public interface IPacsLoanApplicationDetailService {

	/**
	 * Saves the PacsLoanApplicationDetail data to the DB.
	 * 
	 * @param pacsLoanApplicationDetailData
	 */
	public void savePacsLoanApplicationDetail(
			PacsLoanApplicationDetailData pacsLoanApplicationDetailData);

	/**
	 * Updates the PacsLoanApplicationDetail data to the DB.
	 * 
	 * @param pacsLoanApplicationDetailData
	 */
	public void updatePacsLoanApplicationDetail(
			PacsLoanApplicationDetailData pacsLoanApplicationDetailData);

	/**
	 * Returns all the PacsLoanApplicationDetail data to the client.
	 * 
	 * @return
	 */
	public List<PacsLoanApplicationDetailData> getAllPacsLoanApplicationDetails();

	/**
	 * Returns all the PacsLoanApplicationDetailList data to the client.
	 * 
	 * @return
	 */
	public List<PacsLoanApplicationDetailData> getPacsLoanApplicationDetailsByHeaderId(
			Long headerId);

	public List<PacsLoanApplicationDetailData> getLoanedLandDetails(Long customerId,
			Integer seasonId, Integer landTypeId);
	
	public List<PacsLoanApplicationDetailData> getPacsLoanApplicationDetailsEntryModify(Long headerId);
	
	public List<PacsLoanApplicationDetailData> getPacsLoanApplicationDetailsByHeaderIdWithStatus(
			Long headerId);
	
}
