/*package com.vsoftcorp.kls.dataccess.impl;

import org.apache.log4j.Logger;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vsoftcorp.dbunit.DBUnitTC;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.ProductType;
import com.vsoftcorp.kls.dataaccess.IInterestCategoryDAO;
import com.vsoftcorp.kls.dataaccess.IProductTypeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class ProductTypeDAOTest extends DBUnitTC {

	private static final Logger logger = Logger.getLogger(ProductTypeDAOTest.class);

	public ProductTypeDAOTest() {
		super("dbunit.properties");
	}

	@BeforeClass
	public static void setupHibernate() {
		EntityManagerUtil.init("klsTestPU");
	}

	@AfterClass
	public static void teardownHibernate() {
		logger.info("After Class Teardown shutdown");
		EntityManagerUtil.closeSession();
	}

	@Test
	public void testSaveProductType() {
		try {
			InterestCategory master = new InterestCategory();
			//master.setId(150);
			master.setIntrCategoryDesc("INCAT1");

			IInterestCategoryDAO inDao = KLSDataAccessFactory.getInterestCategoryDAO();
			inDao.saveInterestCategory(master);

			ProductType prodType = new ProductType();
			prodType.setDescription("PRODTYPEDESC");
			prodType.setAtmApplicable("Y");
			prodType.setInterestCategory(master);
			
			IProductTypeDAO dao = KLSDataAccessFactory.getProductTypeDAO();
			dao.saveProductType(prodType);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("product_type"));
			actual1 = actualDataSet1.getTable("product_type");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			Assert.assertEquals(expected1.getValue(0, "description"),
					actual1.getValue(0, "description"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while excecuting the dbuint testSaveproductType : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testSaveproductType() dbunit test case.",
					e.getCause());
		}
	}

	@Test
	public void testGetProductType() {
		try {

			ProductType productType = new ProductType();
			productType.setId(100);
			IProductTypeDAO dao = KLSDataAccessFactory.getProductTypeDAO();
			logger.info("factory dao instace" + dao);
			ProductType c = dao.getProductType(productType,false);
			Assert.assertEquals("PRODTYPEDESC", c.getDescription().trim());
		} catch (Exception e) {
			logger.error("Exception while excecuting the dbuint testGetProductType : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testGetProductType() dbunit test case.",
					e.getCause());
		}
	}

	@Test
	public void testUpdateProductType() {
		try {

			ProductType productType = new ProductType();
			productType.setDescription("PRODTYPEDESC");
			InterestCategory interestCategory = new InterestCategory();
			interestCategory.setIntrCategoryDesc("INCAT");
			IProductTypeDAO dao = KLSDataAccessFactory.getProductTypeDAO();
			IDataSet actualDataSet = getConnection().createDataSet();
			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();
			ITable expected1 = null;
			ITable actual = null;
			ITable actual1 = null;
			actual = actualDataSet.getTable("interest_category");
			expected1 = new SortedTable(expectedDataSet1.getTable("product_type"));
			Integer interestCatId = (Integer) actual.getValue(0, "id");
			interestCategory.setId(interestCatId);
			productType.setInterestCategory(interestCategory);
			actual1 = actualDataSet1.getTable("product_type");
			Integer prodTypeId = (Integer) actual1.getValue(0, "id");
			productType.setId(prodTypeId);
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			dao.updateProductType(productType);
			actual1 = actualDataSet1.getTable("product_type");
			Assert.assertEquals(expected1.getValue(0, "description"),
					actual1.getValue(0, "description"));
		} catch (Exception e) {
			logger.error("Exception while excecuting the dbuint testUpdateProductType : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testUpdateProductType() dbunit test case.",
					e.getCause());
		}
	}
}
*/