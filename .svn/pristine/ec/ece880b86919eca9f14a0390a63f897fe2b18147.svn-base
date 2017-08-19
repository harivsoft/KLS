package com.vsoftcorp.kls.GepRep.dao.factory;

import com.vsoftcorp.kls.GepRep.dao.IGenerateReportDAO;
import com.vsoftcorp.kls.GepRep.dao.impl.GenerateReportDAO;

public class GenrepDataaccessFactory {

	private static IGenerateReportDAO generateReportDAO;

	public static IGenerateReportDAO getGenerateReportDao() {
		return generateReportDAO == null ? generateReportDAO = new GenerateReportDAO()
				: generateReportDAO;
	}

}
