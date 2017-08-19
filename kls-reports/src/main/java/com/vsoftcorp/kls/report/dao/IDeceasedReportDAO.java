package com.vsoftcorp.kls.report.dao;

import java.util.List;

public interface IDeceasedReportDAO {
	public List<Object[]> getDeceasedDetails(Integer pacsId,String date);
}
