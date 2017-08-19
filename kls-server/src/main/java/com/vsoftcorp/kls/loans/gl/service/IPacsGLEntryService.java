package com.vsoftcorp.kls.loans.gl.service;

import java.util.List;

import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.data.GlData;

public interface IPacsGLEntryService {
	
	public String extractPacsGLEntries(List<CurrentTransaction> curentTransactionList, List<GlData> glDataList);

}
