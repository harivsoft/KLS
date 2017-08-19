package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BorrowingProduct;
import com.vsoftcorp.kls.data.BorrowingProductData;
import com.vsoftcorp.kls.dataaccess.IBorrowingProductDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IBorrowingProductService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.BorrowingProductHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class BorrowingProductService implements IBorrowingProductService{

	private static final Logger logger = Logger.getLogger(BorrowingProductService.class);
	private IBorrowingProductDAO dao = KLSDataAccessFactory.getBorrowingProductDAO();

	@Override
	public Integer saveBorrowingProduct(
			BorrowingProductData theBorrowingProductData) {
		
		logger.info("Start : Calling BorrowingProduct master dao in saveBorrowingProduct() method.");
		
		BorrowingProduct entity = null;
		Integer productId=0;
		try{
			entity=BorrowingProductHelper.getBorrowingProduct(theBorrowingProductData);
			if(entity!=null)
			productId=	dao.saveBorrowingProduct(entity);
			
		}
		catch(RollbackException re)
		{
			re.getMessage();
			logger.error("Borrowing Product Already Exits With Same Name Or Code .");
			throw new RollbackException("Borrowing Product Code Or Name Already Exits "+re.getCause());
		}
		catch(Exception e){
		
			logger.error("Error While Saving BorrowingProduct Data "+e.getMessage());
			throw new DataAccessException("Unable to save BorrowingProduct data ", e.getCause());
		}
		
		logger.info("End :Successfully Completed calling saveBorrowingProduct()");
		return productId;
	}

	@Override
	public void updateBorrowingProduct(BorrowingProductData data) {
		// TODO Auto-generated method stub
		BorrowingProduct master = null;
		try{
			if(data!=null&&data.getId()!=null)
			master=BorrowingProductHelper.getBorrowingProduct(data);
			if(master.getId()!=null){
				dao.updateBorrowingProduct(master);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error While Updating BorrowingProduct Data ");
			throw new KlsRuntimeException("Unable to update BorrowingProduct Data ", e.getCause());
		}
		logger.info("End :Successfully Completed calling updateBorrowingProduct()");

		
	}

	@Override
	public List<BorrowingProductData> getAllBorrowingProducts() {
		// TODO Auto-generated method stub
		logger.info("Start: Inside method getAllBorrowingProducts ()");
		List<BorrowingProductData> borrowingProductData = new ArrayList<BorrowingProductData>();
		List<BorrowingProduct> borrowingProductMasterList=null;
		BorrowingProductData data=null;
		try {
			borrowingProductMasterList= dao.getAllBorrowingProducts(false);
			for (BorrowingProduct master : borrowingProductMasterList) {
				data=BorrowingProductHelper.getBorrowingProductData(master);
				borrowingProductData.add(data);
			}
		} catch (Exception e) {
			logger.error("Error while retriving all BorrowingProductData  from the database");
			throw new DataAccessException("Error while retriving all Products ", e.getCause());
		}
		logger.info("End : Inside method getAllBorrowingProducts ()");
		return borrowingProductData;
		
		}

	@Override
	public boolean checkForUniqueProductCode(String productCode) {
		// TODO Auto-generated method stub
		logger.info("Start: checking the product using borrowing product code in checkForUniqueProductCode() method.");
		boolean flag = false;
		logger.info(" BorrowingProductCode : " + productCode);
		try {
			flag = dao.checkForUniqueProductCode(productCode, false);
		} catch (Exception e) {
			logger.error("Error while retriving the borrowing  product based on code from the database");
		}
		logger.info("End: checking the borrowing product using borrowing product code in checkForUniqueProductCode() method.");
		return flag;
		
	}

	@Override
	public BorrowingProductData getProductById(Integer id) {

		BorrowingProductData data=new BorrowingProductData();
		BorrowingProduct entity=new BorrowingProduct();
		try{
			if(id!=null)
			entity=dao.getBorrowingProduct(id,false);
			
			if(entity!=null)
			data=BorrowingProductHelper.getBorrowingProductData(entity);
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e.getMessage();
			logger.error("Error while retriving the product data from the database");
			throw new DataAccessException("Unable to get Product data"+e.getCause());
		}
		
		return data;
	}

	@Override
	public boolean checkForUniqueProductName(String productName) {
		// TODO Auto-generated method stub
		logger.info("Start: checking the product using borrowing product name in checkForUniqueProductName() method.");
		boolean flag = false;
		logger.info(" BorrowingProductName : " + productName);
		try {
			flag = dao.checkForUniqueProductName(productName, false);
		} catch (Exception e) {
			logger.error("Error while retriving the borrowing  product based on name from the database");
		}
		logger.info("End: checking the borrowing product using borrowing product name in checkForUniqueProductName() method.");
		return flag;
	}

	
}
