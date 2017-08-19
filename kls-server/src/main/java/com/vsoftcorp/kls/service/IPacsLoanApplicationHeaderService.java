package com.vsoftcorp.kls.service;


import java.util.List;

import com.vsoftcorp.kls.data.PacsLoanApplicationHeaderData;

public interface IPacsLoanApplicationHeaderService {

	public Long savePacsLoanApplicationHeader(PacsLoanApplicationHeaderData thePacsLoanApplicationHeaderData);

	public void updatePacsLoanApplicationHeader(PacsLoanApplicationHeaderData thePacsLoanApplicationHeaderData);
	
	public void closePacsLoanApplicationHeader(PacsLoanApplicationHeaderData thePacsLoanApplicationHeaderData);
	
	public List<PacsLoanApplicationHeaderData> getPacsLoanApplicationHeaders(String  processStatus, Integer pacsId,Integer branchId);
	
	public List<String> getAllFinancialYearsByPacId(String pacId);
	
	public List<PacsLoanApplicationHeaderData> getPacsLoanApplicationHeadersByPacsId(Integer pacsId);

}
