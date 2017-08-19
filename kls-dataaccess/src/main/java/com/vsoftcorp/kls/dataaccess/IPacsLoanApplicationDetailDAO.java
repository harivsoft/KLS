package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

/**
 * 
 * @author a9152
 * 
 */
public interface IPacsLoanApplicationDetailDAO {

	/**
	 * Saves the loan application detail data to the database.
	 * 
	 * @param pacsLoanApplicationDetail
	 */
	public void savePacsLoanApplicationDetail(
			PacsLoanApplicationDetail pacsLoanApplicationDetail);

	/**
	 * Updates the loan application detail data to the database.
	 * 
	 * @param pacsLoanApplicationDetail
	 */
	public void updatePacsLoanApplicationDetail(
			PacsLoanApplicationDetail pacsLoanApplicationDetail);

	/**
	 * Checks if the loan application detail id exists in the database.
	 * 
	 * @param pacsLoanApplicationDetail
	 * @return PacsLoanApplicationDetail
	 */
	public PacsLoanApplicationDetail getPacsLoanApplicationDetail(
			PacsLoanApplicationDetail pacsLoanApplicationDetail);

	public List<PacsLoanApplicationDetail> getAllPacsLoanApplicationDetails();

	public List<PacsLoanApplicationDetail> getPacsLoanApplicationDetails(
			Long headerId);

	public void updateStatus(Long detailId, LoanApplicationState status);

	/**
	 * Deletes the pacs loan application detail data record from the database.
	 * 
	 * @param pacsLoanApplicationDetailId
	 */
	public void deletePacsLoanApplicationDetailRecord(
			Long pacsLoanApplicationDetailId);
	
	public List<PacsLoanApplicationDetail> getLoanedLandDetails(Long customerId, Integer seasonId, Integer landTypeId);
	
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationDetailsForInspection(Long headerId);
	
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationDetailsEntryModify(Long headerId);
	
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationDetailsForInspectionWithStatus(Long headerId);
	
}
