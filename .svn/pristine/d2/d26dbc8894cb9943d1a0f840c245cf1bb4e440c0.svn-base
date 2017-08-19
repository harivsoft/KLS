package com.vsoftcorp.kls.service.helper;

/**
 * @author a1605
 */

import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsGLExtract;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.gl.PacsGLEntry;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.time.Date;

public class PacsGLExtractHelper {

	public static List<PacsGLExtract> getPacsGLExtracts(List<PacsGLEntry> pacsGLEntries, Pacs pacs) {

		List<PacsGLExtract> pacglExtracts = new ArrayList<PacsGLExtract>();
		Date businessDate = null;
		
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
		 businessDate= DateUtil.getVSoftDateByString(RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId()));
		}else{
		 businessDate = LoanServiceUtil.getBusinessDate();
		}
		
		PacsGLExtract pacsGLExtract = null;
		for (PacsGLEntry pacsGlEntry : pacsGLEntries) {
			pacsGLExtract = new PacsGLExtract();
			pacsGLExtract.setPacs_id(pacs.getId());
			pacsGLExtract.setBranch_id(pacs.getBranch().getId());
			pacsGLExtract.setBusinessDate(businessDate);
			pacsGLExtract.setAccountNo(pacsGlEntry.getAccountNo());
			pacsGLExtract.setCrDr(pacsGlEntry.getCrDr());
			pacsGLExtract.setTransactionAmount(pacsGlEntry.getTransactionAmount());
			pacsGLExtract.setTransType(pacsGlEntry.getTransType());
			pacglExtracts.add(pacsGLExtract);
		}
		return pacglExtracts;
	}

}
