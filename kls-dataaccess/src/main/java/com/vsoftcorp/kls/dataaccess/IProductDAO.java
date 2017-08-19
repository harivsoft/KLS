package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Product;

/**
 * 
 * @author a9153
 * 
 */

public interface IProductDAO {

	public void saveProduct(Product theProduct);

	public void updateProduct(Product theProduct);

	public Product getProduct(Product theProduct, boolean isCloseSession);

	public List<Product> getAllProducts(Integer productTypeId,boolean isCloseSession);

	public List<Product> getAllProductsBasedOnLoanType(String loanType, boolean isCloseSession);

	public boolean checkForUniqueLoanProductCode(String loanProductCode, boolean isCloseSession);

	public List<Product> getProductsBySchemeId(Integer schemeId, boolean b);
	
	public Product getProductById(Integer productId);
}
