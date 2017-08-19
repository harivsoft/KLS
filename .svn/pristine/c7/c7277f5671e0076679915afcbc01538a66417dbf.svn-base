package com.vsoftcorp.kls.GepRep.service.helper;

import com.vsoftcorp.kls.GepRep.business.ReportParameters;
import com.vsoftcorp.kls.GepRep.business.ReportStructure;
import com.vsoftcorp.kls.GepRep.data.ReportParametersData;
import com.vsoftcorp.kls.GepRep.data.ReportStructureData;

	public class GenerateReportServiceHelper {
		public static ReportStructureData getReportGenerateData(ReportStructure theMaster) {
			ReportStructureData structureData=new ReportStructureData();
			if(theMaster.getId() !=null) {
				structureData.setId(theMaster.getId());
			}
			structureData.setReportBranchidFilter(theMaster.getReportBranchidFilter());
			structureData.setReportCallFunction(theMaster.getReportCallFunction());
			structureData.setReportColAlignment(theMaster.getReportColAlignment());
			structureData.setReportColTotal(theMaster.getReportColTotal());
			structureData.setReportColumn(theMaster.getReportColumn());
			structureData.setReportColumnSize(theMaster.getReportColumnSize());
			structureData.setReportFunctionPara(theMaster.getReportFunctionPara());
			structureData.setReportGroupby(theMaster.getReportGroupby());
			structureData.setReportOrderby(theMaster.getReportOrderby());
			structureData.setReportPrintGroup(theMaster.getReportPrintGroup());
			structureData.setReportQuery(theMaster.getReportQuery());
			structureData.setReportTitle(theMaster.getReportTitle());
			return structureData;
		}
	
	public static ReportParametersData getReportParameterId(ReportParameters master) {
		ReportParametersData data = new ReportParametersData();
		if(master.getId() !=null) {
			data.setId(master.getId());
		}
		data.setSerialNumber(master.getSerialNumber());
		data.setUserCaption(master.getUserCaption());
		data.setUserDatabaseField(master.getUserDatabaseField());
		data.setUserDataType(master.getUserDataType());
		data.setUserDefaultValues(master.getUserDefaultValues());
		data.setUserListofValues(master.getUserListofValues());
		
		if(master.getRestUrl() != null)
			data.setRestUrl(master.getRestUrl());
		
		if(master.getContext() != null)
			data.setContext(master.getContext());
		
		return data;
	}
	
}



