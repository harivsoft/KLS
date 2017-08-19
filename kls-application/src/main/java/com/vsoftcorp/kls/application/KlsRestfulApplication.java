package com.vsoftcorp.kls.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.vsoftcorp.kls.GepRep.rest.GenerateReportRestService;
import com.vsoftcorp.kls.report.rest.ReportsRestService;
import com.vsoftcorp.kls.rest.service.BorrowingProductGlMappingRestService;
import com.vsoftcorp.kls.rest.service.KLSBorrowingRestService;
import com.vsoftcorp.kls.rest.service.KLSDayActivityRestService;
import com.vsoftcorp.kls.rest.service.KLSLoanRestService;
import com.vsoftcorp.kls.rest.service.KLSLoginRestService;
import com.vsoftcorp.kls.rest.service.KLSMasterRestService;
import com.vsoftcorp.kls.rest.service.KLSOmniIntegrationRestService;
import com.vsoftcorp.kls.rest.service.KLSSubsidyRestService;
import com.vsoftcorp.kls.rest.service.KLSTransactionRestService;
import com.vsoftcorp.kls.rest.service.LoanAccountRestService;
import com.vsoftcorp.kls.rest.service.PacsGLRestService;
import com.vsoftcorp.kls.rest.service.PacsLoanApplicationRestService;
import com.vsoftcorp.kls.rest.service.PacsLoanProductRestService;

public class KlsRestfulApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public KlsRestfulApplication() {
		singletons.add(KLSMasterRestService.getInstance());
		singletons.add(KLSLoanRestService.getInstance());
		singletons.add(KLSTransactionRestService.getInstance());
		singletons.add(KLSLoginRestService.getInstance());
		singletons.add(KLSDayActivityRestService.getInstance());
		singletons.add(PacsLoanProductRestService.getInstance());
		singletons.add(PacsLoanApplicationRestService.getInstance());
		singletons.add(LoanAccountRestService.getInstance());
		singletons.add(KLSBorrowingRestService.getInstance());
		singletons.add(BorrowingProductGlMappingRestService.getInstance());
		singletons.add(PacsGLRestService.getInstance());
		singletons.add(KLSSubsidyRestService.getInstance());
		singletons.add(KLSOmniIntegrationRestService.getInstance());
		singletons.add(ReportsRestService.getInstance());
		singletons.add(GenerateReportRestService.getInstance());  // This service used for User Generated Reports
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
