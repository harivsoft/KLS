package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.DocumentData;

public interface IDocumentService {
	
	public void saveDocument(DocumentData data);
	public void updateDocument(DocumentData data);
	public List<DocumentData> getAllDocumentList();
	public DocumentData getDocumentById(Integer id);

}
