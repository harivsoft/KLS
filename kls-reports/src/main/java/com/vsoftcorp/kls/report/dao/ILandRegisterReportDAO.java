package com.vsoftcorp.kls.report.dao;

import java.util.List;

public interface ILandRegisterReportDAO {
	public List<Object[]> getLandRegisterDetails(Integer customerId,Integer pacsId);

}
