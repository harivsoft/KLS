package com.vsoftcorp.kls.GepRep.service.factory;

import com.vsoftcorp.kls.GepRep.service.IGenRepService;
import com.vsoftcorp.kls.GepRep.service.IGenerateReportService;
import com.vsoftcorp.kls.GepRep.service.impl.GenRepService;
import com.vsoftcorp.kls.GepRep.service.impl.GenerateReportService;

public class GenrepServiceFactory {

	private static IGenerateReportService generateReportService;
	private static IGenRepService genReportService;

	public static IGenerateReportService getGenerateReportService() {
		return generateReportService == null ? generateReportService = new GenerateReportService()
				: generateReportService;

	}

	public static IGenRepService getGenRepService() {
		return genReportService == null ? genReportService = new GenRepService()
				: genReportService;

	}

}
