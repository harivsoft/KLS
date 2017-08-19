package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.ProductType;
import com.vsoftcorp.kls.data.ProductTypeData;
import com.vsoftcorp.kls.dataaccess.IProductTypeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IProductTypeService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.ProductTypeHelper;

/**
 * 
 * @author a9152
 * 
 */

public class ProductTypeService implements IProductTypeService {

	private static final Logger logger = Logger
			.getLogger(ProductTypeService.class);

	/**
	 * 
	 */
	public void saveProductType(ProductTypeData theProductTypeData) {

		logger.info("Start : Calling Product Type dao in saveProductType() method.");
		// get the Product Type dao.
		IProductTypeDAO dao = KLSDataAccessFactory.getProductTypeDAO();
		// get the entity pojo.
		ProductType master = ProductTypeHelper.getProductType(theProductTypeData);
		ProductType dbMaster = null;
		try {
			dbMaster = dao.getProductType(master, false);
			// if Product Type  code does not exist in db, then save.
			if (dbMaster == null) {
				dao.saveProductType(master);
			}
		} catch (Exception excp) {
			logger.error("ProductTypedata cannot be saved");
			throw new KlsRuntimeException(
					"ProductTypedata cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("ProductTypedata already exists");
			throw new KlsRuntimeException(
					"ProductTypedata already exists");
		}
		logger.info("End : Calling Product Type dao in saveProductType() method.");
	}

	/**
	 * This method checks for Product Type  code in the db. If exists, then update
	 * the Product Type  data to the database. Else throw the exception.
	 * 
	 * @param productTypeMasterData
	 */
	public void updateProductType(ProductTypeData productTypeData) {

		logger.info("Start : Calling Product Type dao in updateProductType() method.");
		// get the Product Type dao.
		IProductTypeDAO dao = KLSDataAccessFactory.getProductTypeDAO();
		ProductType master = ProductTypeHelper.getProductType(productTypeData);
		// update the Product Type  code to the db.
		try {
			dao.updateProductType(master);
		} catch (Exception excp) {
			logger.error("ProductType data cannot be updated as Product Type  code does not exist");
			throw new KlsRuntimeException(
					"ProductType data cannot be updated as Product Type  code does not exist",
					excp.getCause());
		}
		logger.info("End : Calling Product Type dao in updateProductType() method.");
	}

	/**
	 * Retrieves all Product Types  from the database.
	 * 
	 */
	public List<ProductTypeData> getAllProductTypes(String loanType) {

		logger.info("Start : Calling Product Type dao in getAllProductTypes() method.");
		// get the Product Type dao.
		IProductTypeDAO dao = KLSDataAccessFactory.getProductTypeDAO();
		List<ProductTypeData> productTypeDataList = new ArrayList<ProductTypeData>();
		try {
			List<ProductType> productTypeMasterList = dao.getAllProductTypes(loanType,false);
			for (ProductType masterData : productTypeMasterList) {
				productTypeDataList.add(ProductTypeHelper
						.getProductTypeData(masterData));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the Product Type records");
			throw new KlsRuntimeException(
					"Error in retrieving all the Product Type records",
					excp.getCause());
		}
		logger.info("End : Calling Product Type dao in getAllProductTypes() method.");
		return productTypeDataList;
	}
}
