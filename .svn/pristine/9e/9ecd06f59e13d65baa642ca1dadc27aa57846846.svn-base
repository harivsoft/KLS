package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.PurposeWiseDisbursmentData;

public class PurposeWiseDisbursmentHelper {

	public static List<PurposeWiseDisbursmentData> getPurposeWiseDisbursmentData(List<Map> dbResult, String month,Integer branchId) {

		List<PurposeWiseDisbursmentData> result = new ArrayList<PurposeWiseDisbursmentData>();

		PurposeWiseDisbursmentData data;

		for (Map map : dbResult) {

			Pacs pacs = (Pacs) map.get("pacs");

			Product product = (Product) map.get("product");

			Purpose purpose = (Purpose) map.get("purpose");
			
			System.out.println("pacs.getBranch.getId()" + pacs.getBranch().getId());
			List<Object[]> list = KLSReportDataAccessFactory.getPurposeDisbursmentReportDAO().getPurposeWiseDisbursmentUptoPeriod(pacs.getId(),
					product.getId(), purpose.getId(), month, branchId);
			
			

			for (Object[] object : list) {
				

				data = new PurposeWiseDisbursmentData();

				data.setActivityName((String) object[0]);
				BigInteger sfmfCount = (BigInteger) object[1];
				BigDecimal sfmfAmount = (BigDecimal) object[2];
				BigInteger othersCount = (BigInteger) object[3];
				BigDecimal othersAmount = (BigDecimal) object[4];
				BigInteger scstCount = (BigInteger) object[5];
				BigDecimal scstAmount = (BigDecimal) object[6];
				BigInteger womanCount = (BigInteger) object[7];
				BigDecimal womanAmount = (BigDecimal) object[8];

				if (sfmfAmount != BigDecimal.ZERO || othersAmount != BigDecimal.ZERO || scstAmount != BigDecimal.ZERO
						|| womanAmount != BigDecimal.ZERO) {
					data.setAddress(pacs.getLocation());
					//data.setBranchId(pacs.getBranch().getId());
					//System.out.println("branchId::::"+data.getBranchId());
				//	data.setBranchName(pacs.getBranch().getName());
				//	System.out.println("branchName:::::" +data.getBranchName());
					//data.setPacName(pacs.getName());
					//data.setPacsId(pacs.getId());
					data.setPurposeId(purpose.getId());
					data.setPurposeName(purpose.getName());
					data.setProductId(product.getId());
					data.setProductName(product.getName());

					data.setSfmfCount(sfmfCount.intValue());
					data.setSfmfAmount(sfmfAmount.setScale(2));
					data.setOthersCount(othersCount.intValue());
					data.setOthersAmount(othersAmount.setScale(2));
					data.setScstCount(scstCount.intValue());
					data.setScstAmount(scstAmount.setScale(2));
					data.setWomanCount(womanCount.intValue());
					data.setWomanAmount(womanAmount.setScale(2));
					result.add(data);
				}
			}
		}

		return result;
	}
}