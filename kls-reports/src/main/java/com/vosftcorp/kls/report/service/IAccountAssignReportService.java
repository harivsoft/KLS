package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.AccountInfoData;
import com.vsoftcorp.kls.report.service.data.SBAccountAssignedData;

public interface IAccountAssignReportService 
{
	
	
	List<SBAccountAssignedData> getAccountSavingStatus(Integer pacsId,String Status);
}
