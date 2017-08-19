package com.vsoftcorp.kls.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.Document;
import com.vsoftcorp.kls.business.entities.LoanProductDocumentMapping;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.data.LoanDocumentData;
import com.vsoftcorp.kls.data.LoanProductDocumentMappingData;

public class LoanProductDocumentMappingHelper {
	public static LoanProductDocumentMappingData getLoanProductDocumentMappingData(
			List<LoanProductDocumentMapping> masterLst) {
		LoanProductDocumentMappingData data = new LoanProductDocumentMappingData();
		List<LoanDocumentData> loanDocDataList = new ArrayList<LoanDocumentData>();
		LoanDocumentData loanDocumentData = null;

		for (LoanProductDocumentMapping loanProductDocumentMapping : masterLst) {
			loanDocumentData = new LoanDocumentData();

			data.setLoanType(loanProductDocumentMapping.getLoanType());
			data.setProductId(loanProductDocumentMapping.getProduct().getId());
			loanDocumentData.setId(loanProductDocumentMapping.getId());
			if(loanProductDocumentMapping
					.getDocument() != null){
			loanDocumentData.setDocId(String.valueOf(loanProductDocumentMapping
					.getDocument().getId()));
			
			loanDocumentData.setName(loanProductDocumentMapping.getDocument()
					.getName());
			}
			loanDocDataList.add(loanDocumentData);
		}
		data.setLoanDocumentsList(loanDocDataList);

		return data;

	}

	public static List<LoanProductDocumentMapping> getLoanProductDocumentMapping(
			LoanProductDocumentMappingData data) {
		List<LoanProductDocumentMapping> list = new ArrayList<LoanProductDocumentMapping>();
		LoanProductDocumentMapping loanProductDocumentMapping = null;
		List<LoanDocumentData> loanDocDataLst = data.getLoanDocumentsList();

		for (LoanDocumentData loanDocumentData : loanDocDataLst) {
			loanProductDocumentMapping = new LoanProductDocumentMapping();

			if (loanDocumentData.getId() != null)
				loanProductDocumentMapping.setId(Long
						.parseLong(loanDocumentData.getId() + ""));
			loanProductDocumentMapping.setLoanType(data.getLoanType());
			Document document = new Document();
			if (loanDocumentData.getDocId() != null) {
				document.setId(Integer.parseInt(loanDocumentData.getDocId()));
				document.setName(loanDocumentData.getName());
				loanProductDocumentMapping.setDocument(document);
			}

			Product product = new Product();
			product.setId(data.getProductId());
			loanProductDocumentMapping.setProduct(product);
			list.add(loanProductDocumentMapping);
		}
		return list;
	}
}
