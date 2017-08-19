package com.vsoftcorp.kls.service;


import java.util.List;

import com.vsoftcorp.kls.data.ProductTypeData;

public interface IProductTypeService {

	public void saveProductType(ProductTypeData theProductTypeData);

	public void updateProductType(ProductTypeData theProductTypeData);
	
	public List<ProductTypeData> getAllProductTypes(String loanType);
}
