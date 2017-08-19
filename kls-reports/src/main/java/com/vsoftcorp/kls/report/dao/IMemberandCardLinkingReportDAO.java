package com.vsoftcorp.kls.report.dao;

import java.util.List;

public interface IMemberandCardLinkingReportDAO {

	List<Object[]> getMemberandCardLinkingReport(Integer pacsId , String rType , Integer customerId);

}