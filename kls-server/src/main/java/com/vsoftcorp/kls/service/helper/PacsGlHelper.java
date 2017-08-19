package com.vsoftcorp.kls.service.helper;

import java.sql.Date;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsGl;
import com.vsoftcorp.kls.business.entities.PacsGlMapping;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.PacsGlData;
import com.vsoftcorp.kls.data.PacsGlMappingData;

/**
 * @author a1605
 */
public class PacsGlHelper {

	public static PacsGlData getPacsGlData(PacsGl pacsGl) {
		PacsGlData pacsGlData = new PacsGlData();

		// String halfGLCode = pacsGl.getGlCode();
		// if ( halfGLCode != null )
		// halfGLCode = halfGLCode.substring(halfGLCode.length() - 8);

		pacsGlData.setId(pacsGl.getId().toString());
		pacsGlData.setGlCode(pacsGl.getGlCode());
		pacsGlData.setName(pacsGl.getName());
		pacsGlData.setType(pacsGl.getType());
		pacsGlData.setPacsId(pacsGl.getPacs().getId().toString());

		// pacsGlData.setRbiBranchCode(pacsGl.getPacs().getBranch().getCode());
		return pacsGlData;
	}

	public static PacsGl getPacsGl(PacsGlData pacsGlData) {
		PacsGl pacsGl = new PacsGl();
		// StringBuilder fullGLCode = new StringBuilder();
		StringBuilder paddedGLCode = new StringBuilder();
		// StringBuilder paddedPacsId = new StringBuilder();

		for (int i = pacsGlData.getGlCode().length(); i < 8; i++) {
			paddedGLCode = paddedGLCode.append("0");
		}

		paddedGLCode = paddedGLCode.append(pacsGlData.getGlCode());

		// fullGLCode.append(pacsGlData.getRbiBranchCode());

		// for (int i= pacsGlData.getPacsId().length() ; i < 4 ; i++){
		// paddedPacsId = paddedPacsId.append("0");
		// }
		// paddedPacsId.append(pacsGlData.getPacsId());
		//
		// fullGLCode.append(paddedPacsId);
		// fullGLCode.append(paddedGLCode.toString());

		if (pacsGlData.getId() != null)
			pacsGl.setId(Long.parseLong(pacsGlData.getId()));
		pacsGl.setGlCode(paddedGLCode.toString());
		pacsGl.setName(pacsGlData.getName());
		pacsGl.setType(pacsGlData.getType());
		pacsGl.setEnteredBy(pacsGlData.getLoggedInUserDetails().getUserName());
		Pacs pacs = new Pacs();
		// if (pacsGlData.getPacsId() != null)
		pacs.setId(pacsGlData.getLoggedInUserDetails().getPacsId());

		pacsGl.setPacs(pacs);
		pacsGl.setEnteredDate(new Date(DateUtil.getFormattedDate(
				pacsGlData.getLoggedInUserDetails().getBusinessDate()).getTime()));

		return pacsGl;
	}

	
}
