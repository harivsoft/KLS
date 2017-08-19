package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IMemberandCardLinkingService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.MemberandCardLinkingData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.MemberandCardLinkingReportHelper;


public class MemberandCardLinkingService implements IMemberandCardLinkingService {
	private static final Logger logger = Logger.getLogger(MemberandCardLinkingService.class);
	public List<MemberandCardLinkingData> getMemberandCardLinkingReport( Integer pacsId , String rType , Integer customerId) {

		logger.info("Start: Calling  MemberandCardLinkingReportDAO inside getMemberandCardLinkingReport()");
		List<MemberandCardLinkingData> memberandCardLinkingDataList = new ArrayList<MemberandCardLinkingData>();
		List<MemberandCardLinkingData> list = new ArrayList<MemberandCardLinkingData>();

		try {
			List<Object[]> MemberandCardLinkingDataList = KLSReportDataAccessFactory.getMemberandCardLinkingReportDAO().getMemberandCardLinkingReport(pacsId ,rType ,customerId);
			//logger.info("******** helper is called");
			memberandCardLinkingDataList = MemberandCardLinkingReportHelper.getMemberandCardLinkingDataListReport(MemberandCardLinkingDataList);
			//logger.info("**********************");
			/*for (MemberandCardLinkingData MemberandCardLinkingReportData : memberandCardLinkingDataList) {

				list.add(MemberandCardLinkingReportData);
				//logger.info("list" );
			}*/
		} catch (Exception exception) {
			logger.info("Error:Inside getMemberandCardLinkinglistReport method");
			throw new KlsReportRuntimeException("Can not print MemberandCardLinking list Report:", exception.getCause());
		}
		logger.info("End: Calling MemberandCardLinkingReportDAO inside getMemberandCardLinkingReport()");
		return memberandCardLinkingDataList;

	}
	}