package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.data.SchemeData;

public class SchemeHelper {

	public static SchemeData getSchemeData(Scheme theScheme) {

		SchemeData schemeData = new SchemeData();
		schemeData.setId(theScheme.getId().toString());
		schemeData.setName(theScheme.getName());
		/*schemeData.setProductId(theScheme.getProduct().getId());
		schemeData.setProductName(theScheme.getProduct().getName());*/
		//schemeData.setInsurancePercentage(theScheme.getInsurancePercentage().toString());
		return schemeData;
	}

	public static Scheme getScheme(SchemeData data) {
		Scheme scheme = new Scheme();
		if (data.getId() != null)
			scheme.setId(Integer.parseInt(data.getId()));
		scheme.setName(data.getName());
		Product product = new Product();
		product.setId(data.getProductId());
		//scheme.setProduct(product);
		/*if (data.getInsurancePercentage() != null)
			scheme.setInsurancePercentage(BigDecimal.valueOf(Double
					.parseDouble(data.getInsurancePercentage())).setScale(2));*/
		return scheme;
	}

}
