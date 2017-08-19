package com.vosftcorp.kls.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.IAccountAssignReportService;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.SBAccountAssignedData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.AccountAssignHelper;

public class AccountSavingReportService implements IAccountAssignReportService{
	private static final Logger logger = Logger.getLogger(AccountSavingReportService.class);
	@Override
	public	List<SBAccountAssignedData> getAccountSavingStatus(Integer pacsId,String Status)
	{
		List<SBAccountAssignedData> accountInfoReportDataList = new ArrayList<SBAccountAssignedData>();
		List<SBAccountAssignedData> list = new ArrayList<SBAccountAssignedData>();
		
		try
		{
			List<Object[]> accList = KLSReportDataAccessFactory.getAccountSavingReportDAO().getAccountInfoReportStatus(pacsId, Status);
			
			accountInfoReportDataList =AccountAssignHelper .getAccountInfoReportList(accList,pacsId,Status);

			for (SBAccountAssignedData accReportData : accountInfoReportDataList) {

				list.add(accReportData);
			}
		} catch (Exception exception) {
			logger.info("Error:Inside getAccountSavingStatus method");
			throw new KlsReportRuntimeException("Can not print AccountInfo Report:", exception.getCause());
		}
		logger.info("End: Calling AccountInfoReportDAO inside getAccountSavingStatus()");
		

		
	return	accountInfoReportDataList;
	}

	}


