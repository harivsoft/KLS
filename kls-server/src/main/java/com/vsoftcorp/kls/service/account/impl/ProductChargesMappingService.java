package com.vsoftcorp.kls.service.account.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.loan.ProductChargesMapping;
import com.vsoftcorp.kls.data.ProductChargesMappingData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IProductChargesMappingDAO;
import com.vsoftcorp.kls.service.account.IProductChargesMappingService;
import com.vsoftcorp.kls.service.helper.ProductChargesMappingHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class ProductChargesMappingService implements IProductChargesMappingService {
	private static final Logger logger = Logger.getLogger(LoanLineOfCreditService.class);

	@Override
	public void saveProductChargesMapping(ProductChargesMappingData data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling  saveProductChargesMapping() method .");
		List<ProductChargesMapping> masterLst = ProductChargesMappingHelper.getProductChargesMapping(data);
		IProductChargesMappingDAO dao = KLSDataAccessFactory.getProductChargesMappingDAO();
		try {
			for (ProductChargesMapping productChargesMapping : masterLst) {
				if (productChargesMapping.getId() == null)
					dao.saveProductChargesMapping(productChargesMapping);
				else
					logger.error("product charges mapping already exists.Could not save.");
			}
			List<Long> deleteIdsLst = data.getDeleteProductChargesMappingList();
			for (Long mappingId : deleteIdsLst) {
				dao.deleteProductChargesMapping(mappingId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to save product charges mapping data ..in saveProductChargesMapping() method..");
			throw new DataAccessException("Unable to save product charges mapping data", e.getCause());
		}
		logger.info("End: Successfully Completed saving product charges mapping data  in saveProductChargesMapping() method .");
	}

	@Override
	public List<ProductChargesMappingData> getProductChargesMapping(Integer productId) {

		logger.info("Start: Fetching the product charges mapping from the database in getProductChargesMapping() method.");
		List<ProductChargesMappingData> dataList = new ArrayList<>();
		IProductChargesMappingDAO dao = KLSDataAccessFactory.getProductChargesMappingDAO();

		try {
			List<ProductChargesMapping> list = dao.getProductChargesMapping(productId);
			dataList.add(ProductChargesMappingHelper.getProductChargesMappingData(list));

		} catch (Exception excp) {
			logger.error("Could not fetch the product charges mapping from the " + "databaseException thrown.");
			excp.printStackTrace();
			throw new DataAccessException("Could not fetch the product charges mapping from the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the product charges mapping from the database in getProductChargesMapping() method.");
		return dataList;
	}

}
