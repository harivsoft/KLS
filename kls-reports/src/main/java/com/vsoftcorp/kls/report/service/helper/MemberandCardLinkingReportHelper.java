package com.vsoftcorp.kls.report.service.helper;

import java.util.ArrayList;
import java.util.List;


import com.vsoftcorp.kls.report.service.data.MemberandCardLinkingData;

public class MemberandCardLinkingReportHelper {

	public static List<MemberandCardLinkingData> getMemberandCardLinkingDataListReport(List<Object[]> MemberandCardLinkingDataList) {
		List<MemberandCardLinkingData> memandcardlinkingList = new ArrayList<MemberandCardLinkingData>();
		MemberandCardLinkingData data ;
		try {
			
			for (Object[] memberandCardLinkingData : MemberandCardLinkingDataList) {
				
				data = new MemberandCardLinkingData();
				if(memberandCardLinkingData[0]!=null)
				data.setmemberNumber(memberandCardLinkingData[0].toString());
				if(memberandCardLinkingData[1]!=null)
				data.setmemberName(memberandCardLinkingData[1].toString());
				if(memberandCardLinkingData[2]!=null)
				data.setkccCardNumber(memberandCardLinkingData[2].toString());
				if(memberandCardLinkingData[3]!=null)
				data.setactualSBAccountNumber(memberandCardLinkingData[3].toString());
				if(memberandCardLinkingData[4]!=null)
				data.setactualLoanAccountNumber(memberandCardLinkingData[4].toString());
				if(memberandCardLinkingData[5]!=null)
				data.setlinkedSBAccountNumber(memberandCardLinkingData[5].toString());
				if(memberandCardLinkingData[6]!=null)
				data.setlinkedLoanAccountNumber(memberandCardLinkingData[6].toString());
				
				//System.out.println("memandcardlinkingList:");
				memandcardlinkingList.add(data);

				}
				
	} catch (Exception e) {
		e.printStackTrace();
	}
	return memandcardlinkingList;
}

	
}
	

