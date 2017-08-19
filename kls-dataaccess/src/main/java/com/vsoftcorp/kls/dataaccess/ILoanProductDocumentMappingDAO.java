package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.LoanProductDocumentMapping;

public interface ILoanProductDocumentMappingDAO {
	public void saveLoanProductDocumentMapping(LoanProductDocumentMapping master);
	public void deleteLoanProductDocumentMapping(Long id);
	public List<LoanProductDocumentMapping> getLoanProductDocumentMapping(
			String productId);
	}
