/*package com.vsoftcorp.kls.dataccess.impl;

import java.math.BigDecimal;

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
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.dataaccess.ISchemeDAO;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SchemeDAOTest extends DBUnitTC {

	private static final Logger logger = Logger.getLogger(SchemeDAOTest.class);

	public SchemeDAOTest() {
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
	public void testSaveScheme() {
		try {

			Product product = new Product();
			product.setName("Product1");

			IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
			dao.saveProduct(product);

			Scheme master = new Scheme();
			master.setName("Scheme1");
			master.setProduct(product);
			master.setInsurancePercentage(BigDecimal.valueOf(2));

			ISchemeDAO sDao = KLSDataAccessFactory.getSchemeDAO();
			sDao.saveScheme(master);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("scheme"));
			actual1 = actualDataSet1.getTable("scheme");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			Assert.assertEquals(expected1.getValue(0, "name"),
					actual1.getValue(0, "name"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while excecuting the dbuint testSaveScheme+"
					+ " : " + e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testSaveScheme() dbunit test case.",
					e.getCause());
		}
	}

	@Test
	public void testGetScheme() {
		try {

			Scheme scheme = new Scheme();
			scheme.setId(100);
			ISchemeDAO dao = KLSDataAccessFactory.getSchemeDAO();
			logger.info("factory dao instace" + dao);
			Scheme s = dao.getScheme(scheme);
			Assert.assertEquals("Scheme1", s.getName().trim());
		} catch (Exception e) {
			logger.error("Exception while excecuting the dbuint testGetScheme : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testGetScheme() dbunit test case.",
					e.getCause());
		}
	}

	@Test
	public void testGetSchemeBasedOnProduct() {
		try {

			Product product = new Product();
			product.setId(50);
			// product.setName("Product1");
			Scheme scheme = new Scheme();
			scheme.setProduct(product);
			ISchemeDAO dao = KLSDataAccessFactory.getSchemeDAO();
			logger.info("factory dao instace" + dao);
			Scheme s = dao.getSchemeBasedOnProduct(scheme);
			Assert.assertEquals("Product1", s.getName().trim());
		} catch (Exception e) {
			logger.error("Exception while excecuting the dbuint testGetSchemeBasedOnProduct() : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testGetSchemeBasedOnProduct() dbunit test case.",
					e.getCause());
		}
	}

	
	 * @Test public void testUpdateScheme() { try {
	 * 
	 * Scheme scheme = new Scheme(); scheme.setName("Scheme1"); Product product
	 * = new Product(); product.setName("Product"); ISchemeDAO dao =
	 * KLSDataAccessFactory.getSchemeDAO(); IDataSet actualDataSet =
	 * getConnection().createDataSet(); IDataSet actualDataSet1 =
	 * getConnection().createDataSet(); IDataSet expectedDataSet1 =
	 * getExpectedDataSet(); ITable expected1 = null; ITable actual = null;
	 * ITable actual1 = null; actual = actualDataSet.getTable("product");
	 * expected1 = new SortedTable(expectedDataSet1.getTable("scheme")); Integer
	 * productId = (Integer) actual.getValue(0, "id"); product.setId(productId);
	 * scheme.setProduct(product); actual1 = actualDataSet1.getTable("scheme");
	 * Integer schemeId = (Integer) actual1.getValue(0, "id");
	 * scheme.setId(schemeId); actual1 = new
	 * SortedTable(DefaultColumnFilter.includedColumnsTable( actual1,
	 * expected1.getTableMetaData().getColumns()),
	 * expected1.getTableMetaData()); dao.updateScheme(scheme); actual1 =
	 * actualDataSet1.getTable("scheme");
	 * Assert.assertEquals(expected1.getValue(0, "name"), actual1.getValue(0,
	 * "name")); } catch (Exception e) {
	 * logger.error("Exception while excecuting the dbuint testUpdateScheme : "
	 * + e.getMessage()); throw new RuntimeException(
	 * "Exception thrown while running the testUpdateSchemee() dbunit test case."
	 * , e.getCause()); } }
	 
}
*/