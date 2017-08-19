package com.vosftcorp.kls.report.service;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.MemberandCardLinkingData;

public interface IMemberandCardLinkingService {

	public List<MemberandCardLinkingData> getMemberandCardLinkingReport(Integer pacsId, String rType, Integer customerId);

}