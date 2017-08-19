package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.data.LoanProductEnumsData;
import com.vsoftcorp.kls.data.ProductData;

public interface IProductService {

	public void saveProduct(ProductData theProductData);

	public void updateProduct(ProductData theProductData);

	public List<ProductData> getAllProducts(Integer productTypeId);
	
	public List<ProductData> getAllProducts();

	public List<ProductData> getAllProductsBasedOnLoanType(String loanType);

	public LoanProductEnumsData getAllLoanProductEnums();

	public boolean checkForUniqueLoanProductCode(String loanProductCode);

	public ProductData getProductBasedOnId(Integer productId);
	
	public List<ProductData> getProductsBySchemeId(Integer schemeId);
}
