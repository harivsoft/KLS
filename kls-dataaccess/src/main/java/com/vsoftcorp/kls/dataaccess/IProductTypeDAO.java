package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.ProductType;

/**
 * 
 * @author a9153
 *
 */

public interface IProductTypeDAO {

	public void saveProductType(ProductType theProductType);

	public void updateProductType(ProductType theProductType);
	
	public ProductType getProductType(ProductType theProductType, boolean isCloseSession);
	
	public List<ProductType> getAllProductTypes(String loanType,boolean isCloseSession);

}
