package com.vsoftcorp.kls.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entity.loan.ChargesMaster;
import com.vsoftcorp.kls.business.entity.loan.ProductChargesMapping;
import com.vsoftcorp.kls.data.ChargesData;
import com.vsoftcorp.kls.data.ProductChargesMappingData;

public class ProductChargesMappingHelper {

	public static ProductChargesMappingData getProductChargesMappingData(List<ProductChargesMapping> masterLst) {
		ProductChargesMappingData data = new ProductChargesMappingData();
		List<ChargesData> chargesDataList = new ArrayList<ChargesData>();
		ChargesData chargesData = null;

		for (ProductChargesMapping productChargesMapping : masterLst) {
			chargesData = new ChargesData();
			if (productChargesMapping.getProduct() != null) {
				data.setProductId(productChargesMapping.getProduct().getId());
				data.setProductName(productChargesMapping.getProduct().getName());
			}
			if (productChargesMapping.getId() != null)
				chargesData.setId(productChargesMapping.getId());

			if (productChargesMapping.getChargesMaster() != null) {
				chargesData.setChargesId(productChargesMapping.getChargesMaster().getId());
				chargesData.setChargesDescription(productChargesMapping.getChargesMaster().getChargesDescription());
				chargesData.setChargesCode(productChargesMapping.getChargesMaster().getChargesCode());
				if(productChargesMapping.getChargesMaster().getMinAmount() != null)
					chargesData.setMinAmount(productChargesMapping.getChargesMaster().getMinAmount().getAmount().setScale(2).toString());
				if(productChargesMapping.getChargesMaster().getMaxAmount() != null)
					chargesData.setMaxAmount(productChargesMapping.getChargesMaster().getMaxAmount().getAmount().setScale(2).toString());
				//if(data.getBankPercentage()!=0)
					//productChargesMapping.setBankPercentage(data.getBankPercentage());
				
				chargesData.setBankPercentage(productChargesMapping.getBankPercentage());
			}
			chargesDataList.add(chargesData);
		}
		data.setChargesMasterDataList(chargesDataList);

		return data;

	}

	public static List<ProductChargesMapping> getProductChargesMapping(ProductChargesMappingData data) {
		List<ProductChargesMapping> list = new ArrayList<ProductChargesMapping>();
		ProductChargesMapping productChargesMapping = null;
		List<ChargesData> chargesList = new ArrayList<>();
		if (data.getChargesMasterDataList() != null)
			chargesList = data.getChargesMasterDataList();
		for (ChargesData chargesData : chargesList) {
			productChargesMapping = new ProductChargesMapping();

			if (chargesData.getId() != null)
				productChargesMapping.setId(chargesData.getId());
			if (chargesData.getChargesId() != null) {
				ChargesMaster chargesMaster = new ChargesMaster();
				chargesMaster.setId(chargesData.getChargesId());

				productChargesMapping.setChargesMaster(chargesMaster);
			}
			if (data.getProductId() != null) {
				Product product = new Product();
				product.setId(data.getProductId());
				productChargesMapping.setProduct(product);
			}
			
			productChargesMapping.setBankPercentage(chargesData.getBankPercentage());
			list.add(productChargesMapping);

		}
		return list;
	}
}
