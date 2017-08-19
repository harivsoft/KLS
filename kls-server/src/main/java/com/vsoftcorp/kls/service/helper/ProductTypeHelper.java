package com.vsoftcorp.kls.service.helper;


import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.ProductType;
import com.vsoftcorp.kls.data.ProductTypeData;

/**
 * @author a9153
 *
 *Helper Class for conversion from pojo to entity and vice versa.
 */

public class ProductTypeHelper {

	/**
	 * The method converts Product Type  etity to Product Type  pojo.
	 * @param theMaster
	 * @return
	 */
	public static ProductTypeData getProductTypeData(ProductType theMaster) {
		ProductTypeData data = new ProductTypeData();
		data.setId(Integer.toString(theMaster.getId()));
		data.setAtmApplicable(theMaster.getAtmApplicable());
		data.setDescription(theMaster.getDescription());
		data.setIntCategoryId(theMaster.getInterestCategory().getId().toString());
		data.setLoanType(theMaster.getLoanType());
		return data;
	}

	/**
	 * This method converts Product Type  pojo and Product Type  entity.
	 * @param data
	 * @return
	 */
	public static ProductType getProductType(ProductTypeData data) {
		ProductType master = new ProductType();
		/*ProductTypeId prodTypeId = new ProductTypeId();
		
		prodTypeId.setBankCode(data.getBankCode());
		prodTypeId.setProductCode(data.getProductTypeCode());*/
		
		if (data.getId() != null)
			master.setId(Integer.parseInt(data.getId()));
		master.setDescription(data.getDescription());
		master.setAtmApplicable(data.getAtmApplicable());
		
		InterestCategory intrCat = new InterestCategory();
		intrCat.setId(Integer.parseInt(data.getIntCategoryId()));
		
		master.setInterestCategory(intrCat);
		master.setLoanType(data.getLoanType());
		return master;
	}
}
