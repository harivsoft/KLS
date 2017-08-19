package com.vsoftcorp.kls.report.dao;

import java.util.List;

public interface IAccountSavingReportDAO
{
	List<Object[]> getAccountInfoReportStatus(Integer pacsId,String Status);

}
