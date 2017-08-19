package com.vosftcorp.kls.report.service.dcb;

import java.util.List;

import com.vsoftcorp.kls.report.service.data.STMTLTDcbReportData;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public interface ISTMTLTDcbReportService {

	public List<STMTLTDcbReportData> getSTMTLTDcbReportData(Integer pacsId, Date asOnDate,Integer branchId,String branchName);

}
