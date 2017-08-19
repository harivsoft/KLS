package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;

/**
 * 
 * @author a9153
 *
 */

public interface IPacsLoanApplicationHeaderDAO {

	public void savePacsLoanApplicationHeader(PacsLoanApplicationHeader thePacsLoanApplicationHeader);

	public void updatePacsLoanApplicationHeader(PacsLoanApplicationHeader thePacsLoanApplicationHeader);
	
	public void closePacsLoanApplicationHeader(PacsLoanApplicationHeader thePacsLoanApplicationHeader);
	
	public PacsLoanApplicationHeader getPacsLoanApplicationHeader(PacsLoanApplicationHeader thePacsLoanApplicationHeader);
	
	public PacsLoanApplicationHeader getPacsLoanApplicationHeader(Long thePacsLoanApplicationHeaderId);
	
	public List<PacsLoanApplicationHeader> getPacsLoanApplicationHeaders(String  processStatus, Integer pacsId,Integer branchId);
	
	public List<String> getAllFinancialYearsByPacId(String pacId);
	
	public List<PacsLoanApplicationHeader> getPacsLoanApplicationHeadersByPacsId(Integer pacsId);
}
