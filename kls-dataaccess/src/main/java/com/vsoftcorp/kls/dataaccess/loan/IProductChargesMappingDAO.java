package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.loan.ProductChargesMapping;

public interface IProductChargesMappingDAO {

	public void saveProductChargesMapping(ProductChargesMapping productChargesMapping);

	public void deleteProductChargesMapping(Long mappingId);

	public List<ProductChargesMapping> getProductChargesMapping(Integer productId);
	
	public ProductChargesMapping getProductChargesMappingByProductIdAndChargesId(Integer productId, Long chargeId);

}
