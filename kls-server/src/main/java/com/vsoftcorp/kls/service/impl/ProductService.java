package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.EventTypeDefinition;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.ProductType;
import com.vsoftcorp.kls.data.DisbursementTypeData;
import com.vsoftcorp.kls.data.InterestCalculationMethodData;
import com.vsoftcorp.kls.data.InterestPostingFrequencyData;
import com.vsoftcorp.kls.data.InterestTypeData;
import com.vsoftcorp.kls.data.LoanProductEnumsData;
import com.vsoftcorp.kls.data.PenalInterestApplicableData;
import com.vsoftcorp.kls.data.ProductData;
import com.vsoftcorp.kls.data.RepaymentScheduleData;
import com.vsoftcorp.kls.data.RepaymentTypeData;
import com.vsoftcorp.kls.dataaccess.IInterestCategoryDAO;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.IProductTypeDAO;
import com.vsoftcorp.kls.dataaccess.IRecoveryOrderDao;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IProductService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.ProductHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.valuetypes.loanproduct.DisbursementType;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestCalculationMethod;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestPostingFrequency;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestType;
import com.vsoftcorp.kls.valuetypes.loanproduct.PenalInterestApplicable;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentSchedule;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentType;

/**
 * 
 * @author a9153
 * 
 */
public class ProductService implements IProductService {

	private static final Logger logger = Logger.getLogger(ProductService.class);

	/**
	 * This methods save the Product only if not exists.
	 */
	public void saveProduct(ProductData theProductMasterData) {

		logger.info("Start : Calling Product master dao in saveProduct() method.");

		IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
		Product master = ProductHelper.getProduct(theProductMasterData);
		IInterestCategoryDAO intrCatDao = KLSDataAccessFactory.getInterestCategoryDAO();
		IProductTypeDAO prodTypeDao = KLSDataAccessFactory.getProductTypeDAO();
		IRecoveryOrderDao rDao = KLSDataAccessFactory.getRecoveryOrderDAO();
		Product dbMaster = null;
		InterestCategory intrCatMast = null;
		ProductType productType = null;
		EventTypeDefinition evntDef = null;
		try {
			intrCatMast = intrCatDao.getInterestCategory(master.getIntrCategory(), false);
			productType = prodTypeDao.getProductType(master.getProductType(), false);
			evntDef = rDao.getEventTypeDefinition(master.getRecoverySequence(), false);
			if (intrCatMast != null && productType != null && evntDef != null) {
				master.setIntrCategory(intrCatMast);
				master.setProductType(productType);
				master.setRecoverySequence(evntDef);
				dbMaster = dao.getProduct(master, false);
				if (dbMaster == null)
					dao.saveProduct(master);
			}
		} catch (Exception excp) {
			logger.error("Product master data cannot be saved");
			throw new KlsRuntimeException("Product master data cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("Product master data already exists");
			throw new KlsRuntimeException("Product master data already exists");
		}
		if (intrCatMast == null) {
			logger.error("Product cannot be saved as Interest Category code does not exist.");
			throw new KlsRuntimeException("Product cannot be saved as Interest Category  code does not exist.");
		}
		if (productType == null) {
			logger.error("Product cannot be saved as Product type id does not exist.");
			throw new KlsRuntimeException("Product cannot be saved as Product type id does not exist.");
		}
		logger.info("End : Calling Product master dao in saveProduct() method.");
	}

	/**
	 * Updates a Product if already exists.
	 * 
	 */
	public void updateProduct(ProductData theProductMasterData) {

		logger.info("Start: Inside method updateProduct()");
		try {
			IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
			Product master = ProductHelper.getProduct(theProductMasterData);
			IInterestCategoryDAO intrCatDao = KLSDataAccessFactory.getInterestCategoryDAO();
			IProductTypeDAO prodTypeDao = KLSDataAccessFactory.getProductTypeDAO();
			IRecoveryOrderDao rDao = KLSDataAccessFactory.getRecoveryOrderDAO();
			master.setIntrCategory(intrCatDao.getInterestCategory(master.getIntrCategory(), false));
			master.setProductType(prodTypeDao.getProductType(master.getProductType(), false));
			master.setRecoverySequence(rDao.getEventTypeDefinition(master.getRecoverySequence(), false));
			dao.updateProduct(master);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while updating Product: " + e.getMessage());
			throw new KlsRuntimeException("Exception while saving Product: ", e.getCause());
		}
		logger.info("End: Inside method updateProduct()");
	}

	/**
	 * Retrives all Products from Database
	 * 
	 */
	public List<ProductData> getAllProducts(Integer productTypeId) {
		logger.info("Start: Inside method getAllProducts ()");
		IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
		List<ProductData> products = new ArrayList<ProductData>();
		try {
			List<Product> productMasterList = dao.getAllProducts(productTypeId,false);
			for (Product masterData : productMasterList) {
				products.add(ProductHelper.getProductMasterData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all Products  from the database");
			throw new DataAccessException("Error while retriving all Products ", e.getCause());
		}
		logger.info("End : Inside method getAllProducts ()");
		return products;
	}

	@Override
	public LoanProductEnumsData getAllLoanProductEnums() {

		logger.info("Start: Inside product service to get all loan product enums in getAllLoanProductEnums()");
		LoanProductEnumsData loanProductEnumsData = new LoanProductEnumsData();
		try {
			List<DisbursementTypeData> disbursementTypeList = new ArrayList<DisbursementTypeData>();
			DisbursementType[] disbursementTypeArray = DisbursementType.values();
			for (int i = 0; i < disbursementTypeArray.length; i++) {
				DisbursementTypeData disbursementTypeData = new DisbursementTypeData();
				if (!(disbursementTypeArray[i].getValue().equals("N"))) {
					disbursementTypeData.setValue(disbursementTypeArray[i].name());
					disbursementTypeData.setId(disbursementTypeArray[i].getValue());
					disbursementTypeList.add(disbursementTypeData);
				}
			}
			loanProductEnumsData.setDisbursementType(disbursementTypeList);
			List<InterestCalculationMethodData> interestCalcMethodDataList = new ArrayList<InterestCalculationMethodData>();
			InterestCalculationMethod[] interestCalcTypeArray = InterestCalculationMethod.values();
			for (int i = 0; i < interestCalcTypeArray.length; i++) {
				InterestCalculationMethodData interestCalcMethodData = new InterestCalculationMethodData();
				if (!(interestCalcTypeArray[i].getValue().equals("N"))) {
					interestCalcMethodData.setValue(interestCalcTypeArray[i].name());
					interestCalcMethodData.setId(interestCalcTypeArray[i].getValue());
					interestCalcMethodDataList.add(interestCalcMethodData);
				}
			}
			loanProductEnumsData.setInterestCalculationMethod(interestCalcMethodDataList);
			List<InterestPostingFrequencyData> interestPostFreqDataList = new ArrayList<InterestPostingFrequencyData>();
			InterestPostingFrequency[] interestPostFreqTypeArray = InterestPostingFrequency.values();
			for (int i = 0; i < interestPostFreqTypeArray.length; i++) {
				InterestPostingFrequencyData interestPostingFreqData = new InterestPostingFrequencyData();
				if (!(interestPostFreqTypeArray[i].getValue().equals("N"))) {
					interestPostingFreqData.setValue(interestPostFreqTypeArray[i].name());
					interestPostingFreqData.setId(interestPostFreqTypeArray[i].getValue());
					interestPostFreqDataList.add(interestPostingFreqData);
				}
			}
			loanProductEnumsData.setInterestPostingFrequency(interestPostFreqDataList);
			List<InterestTypeData> interestTypeDataList = new ArrayList<InterestTypeData>();
			InterestType[] interestTypeArray = InterestType.values();
			for (int i = 0; i < interestTypeArray.length; i++) {
				InterestTypeData interestTypeData = new InterestTypeData();
				if (!(interestTypeArray[i].getValue().equals("N"))) {
					interestTypeData.setValue(interestTypeArray[i].name());
					interestTypeData.setId(interestTypeArray[i].getValue());
					interestTypeDataList.add(interestTypeData);
				}
			}
			loanProductEnumsData.setInterestType(interestTypeDataList);
			List<PenalInterestApplicableData> penalInterestAppTypeList = new ArrayList<PenalInterestApplicableData>();
			PenalInterestApplicable[] penalInterestAppTypeArray = PenalInterestApplicable.values();
			for (int i = 0; i < penalInterestAppTypeArray.length; i++) {
				PenalInterestApplicableData penalInterestAppTypeData = new PenalInterestApplicableData();
				penalInterestAppTypeData.setValue(penalInterestAppTypeArray[i].name());
				penalInterestAppTypeData.setId(penalInterestAppTypeArray[i].getValue());
				penalInterestAppTypeList.add(penalInterestAppTypeData);
			}
			loanProductEnumsData.setPenalInterestApplicable(penalInterestAppTypeList);
			List<RepaymentScheduleData> repaymentScheduleList = new ArrayList<RepaymentScheduleData>();
			RepaymentSchedule[] repaymentScheduleTypeArray = RepaymentSchedule.values();
			for (int i = 0; i < repaymentScheduleTypeArray.length; i++) {
				RepaymentScheduleData repaymentScheduleData = new RepaymentScheduleData();
				if (!(repaymentScheduleTypeArray[i].getValue().equals("N"))) {
					repaymentScheduleData.setValue(repaymentScheduleTypeArray[i].name());
					repaymentScheduleData.setId(repaymentScheduleTypeArray[i].getValue());
					repaymentScheduleList.add(repaymentScheduleData);
				}
			}
			loanProductEnumsData.setRepaymentSchedule(repaymentScheduleList);
			List<RepaymentTypeData> repaymentTypeList = new ArrayList<RepaymentTypeData>();
			RepaymentType[] repaymentTypeArray = RepaymentType.values();
			for (int i = 0; i < repaymentTypeArray.length; i++) {
				RepaymentTypeData repaymentTypeData = new RepaymentTypeData();
				if (!(repaymentTypeArray[i].getValue().equals("N"))) {
					repaymentTypeData.setValue(repaymentTypeArray[i].name());
					repaymentTypeData.setId(repaymentTypeArray[i].getValue());
					repaymentTypeList.add(repaymentTypeData);
				}
			}
			loanProductEnumsData.setRepaymentType(repaymentTypeList);
		} catch (Exception excp) {
			logger.error(" Exception while populating loan product enums.");
			throw new KlsRuntimeException("Exception while populating loan product enums.");
		}
		logger.info("End: Inside product service to get all loan product enums in getAllLoanProductEnums()");
		return loanProductEnumsData;
	}

	@Override
	public List<ProductData> getAllProductsBasedOnLoanType(String loanType) {
		logger.info("Start: Inside method getAllProductsBasedOnLoanType ()");
		IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
		List<ProductData> products = new ArrayList<ProductData>();
		try {
			List<Product> productMasterList = dao.getAllProductsBasedOnLoanType(loanType, false);
			for (Product masterData : productMasterList) {
				products.add(ProductHelper.getProductMasterData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all Products based on loan type from the database");
			throw new DataAccessException("Error while retriving all Products based on loan type ", e.getCause());
		}
		logger.info("End : Inside method getAllProductsBasedOnLoanType ()");
		return products;
	}

	@Override
	public boolean checkForUniqueLoanProductCode(String loanProductCode) {

		logger.info("Start: checking the product using loan product code in checkForUniqueLoanProductCode() method.");
		IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
		boolean flag = false;
		logger.info(" loanProductCode : " + loanProductCode);
		try {
			flag = dao.checkForUniqueLoanProductCode(loanProductCode, false);
		} catch (Exception e) {
			logger.error("Error while retriving the product based on loan product code from the database");
		}
		logger.info("End: checking the product using loan product code in checkForUniqueLoanProductCode() method.");
		return flag;
	}

	@Override
	public ProductData getProductBasedOnId(Integer productId) {
		logger.info("Start: Inside method getProductBasedOnId()");
		IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
		ProductData productData = new ProductData();
		Product product = new Product();
		product.setId(productId);
		logger.info("productId : " + productId);
		try {
			product = dao.getProduct(product, false);
			productData = ProductHelper.getProductMasterData(product);
		} catch (Exception e) {
			logger.error("Error while retriving product from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving product ", e.getCause());
		}
		logger.info("End : Inside method getProductBasedOnId()");
		return productData;
	}

	@Override
	public List<ProductData> getAllProducts() {
		logger.info("Start: Inside method getAllProducts ()");
		IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
		List<ProductData> products = new ArrayList<ProductData>();
		try {
			List<Product> productMasterList = dao.getAllProducts(null,false);
			for (Product masterData : productMasterList) {
				products.add(ProductHelper.getProductMasterData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all Products  from the database");
			throw new DataAccessException("Error while retriving all Products ", e.getCause());
		}
		logger.info("End : Inside method getAllProducts ()");
		return products;
	}
	
	@Override
	public List<ProductData> getProductsBySchemeId(Integer schemeId) {
		logger.info("Start: Inside method getProductsBySchemeId ()");
		IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
		List<ProductData> products = new ArrayList<ProductData>();
		try {
			List<Product> productMasterList = dao.getProductsBySchemeId(schemeId, false);
			for (Product masterData : productMasterList) {
				products.add(ProductHelper.getProductMasterData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all Products based on schemeId from the database");
			throw new DataAccessException("Error while retriving all Products based on schemeId ", e.getCause());
		}
		logger.info("End : Inside method getProductsBySchemeId ()");
		return products;
	}
}
