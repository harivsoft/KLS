package com.vosftcorp.kls.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IMemberProfileReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.MemberProfileData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.MemberProfileReportHelper;

public class MemberProfileReportService implements IMemberProfileReportService {
	private static final Logger logger = Logger.getLogger(MemberProfileReportService.class);

	@Override
	public List<MemberProfileData> getMemberProfileReport(Integer villageId, Integer farmerType, Integer caste,
			String asOnDate, Integer pacsId) {

		logger.info("Start: Calling  MemberProfilelistReportDAO inside getMemberProfileReport()");
		List<MemberProfileData> memberProfileReportDataList = new ArrayList<MemberProfileData>();
		List<MemberProfileData> list = new ArrayList<MemberProfileData>();

		try {
			List<Object[]> MemberProfileList = KLSReportDataAccessFactory.getMemberProfileReportDAO()
					.getMemberProfileReport(villageId, farmerType, caste, asOnDate, pacsId);
			memberProfileReportDataList = MemberProfileReportHelper.getMemberProfileListReport(MemberProfileList);

			for (MemberProfileData MemberProfileReportData : memberProfileReportDataList) {

				list.add(MemberProfileReportData);
			}
		} catch (Exception exception) {
			logger.info("Error:Inside getMemberProfilelistReport method");
			throw new KlsReportRuntimeException("Can not print MemberProfile list Report:", exception.getCause());
		}
		logger.info("End: Calling ShareAccountLedgerReportDAO inside getMemberProfileReport()");
		return memberProfileReportDataList;

	}

	@Override
	public int getTotalNoOfMembers(Integer villageId, Integer farmerType, Integer caste, String asOnDate,
			Integer pacsId) {
		int totMembers = 0;
		try {
			totMembers = KLSReportDataAccessFactory.getMemberProfileReportDAO()
						.getTotMembers(villageId, farmerType, caste, asOnDate, pacsId);

				
			} catch (Exception exception) {
				logger.info("Error:Inside getMemberProfilelistReport method");
				throw new KlsReportRuntimeException("Can not print MemberProfile list Report:", exception.getCause());
			}
		return totMembers;
		}

}
