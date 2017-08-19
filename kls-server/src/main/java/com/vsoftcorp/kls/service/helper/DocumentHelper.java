package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Document;
import com.vsoftcorp.kls.data.DocumentData;

public class DocumentHelper {

	public static Document getDocument(DocumentData data)
	{
		Document master=new Document();
		master.setId(data.getId());
		master.setName(data.getName());
		master.setRemarks(data.getRemarks());
		master.setValidationRule(data.getValidationRule());
		master.setIsDocNoRequired(data.getIsDocNoRequired());
		return master;
	}
	public static DocumentData getDocumentData(Document master)	{
		DocumentData data=new DocumentData();
		data.setId(master.getId());
		data.setName(master.getName());
		data.setRemarks(master.getRemarks());
		data.setValidationRule(master.getValidationRule());
		data.setIsDocNoRequired(master.getIsDocNoRequired());
		return data;
	}
}
