/*package com.vsoftcorp.kls.dataccess.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vsoftcorp.dbunit.DBUnitTC;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class CustomerDAOTest extends DBUnitTC {

	private static final Logger logger = Logger
			.getLogger(CustomerDAOTest.class);

	public CustomerDAOTest() {
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

//	@Test
//	public void testSaveCustomer() {
//		logger.info("Executing the test case saveCustomer()");
//		try {
//			Customer customer = new Customer();
//			customer.setCustomerId("CUS01");
//			customer.setCbsCustId("CBS01");
//			customer.setMemberSrlNo("ME01");
//			customer.setMemberType("MEM01");
//			customer.setName("chandra");
//			customer.setVillageId(1);
//			KLSDataAccessFactory.getCustomerDAO().saveCustomer(customer);
//			IDataSet actualDataSet1 = getConnection().createDataSet();
//			IDataSet expectedDataSet1 = getExpectedDataSet();
//
//			ITable expected1 = null;
//			ITable actual1 = null;
//			expected1 = new SortedTable(expectedDataSet1.getTable("customer"));
//			actual1 = actualDataSet1.getTable("customer");
//			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
//					actual1, expected1.getTableMetaData().getColumns()),
//					expected1.getTableMetaData());
//
//			Assertion.assertEquals(expected1, actual1);
//			Assert.assertEquals(expected1.getRowCount(), actual1.getRowCount());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("End:Executing the test case testSaveSCustomer()");
//
//	}

//	@Test
//	public void testUpdateCustomer() {
//		logger.info("Executing the test case testUpdateCustomer()");
//
//		try {
//			Customer customer = new Customer();
//			customer.setName("chandram");
//			customer.setCbsCustId("CBS01");
//			customer.setCustomerId("CUS01");
//			customer.setMemberSrlNo("ME01");
//			customer.setMemberType("MEM01");
//			customer.setVillageId(1);
//
//			IDataSet actualDataSet1 = getConnection().createDataSet();
//			IDataSet expectedDataSet1 = getExpectedDataSet();
//			ITable expected1 = null;
//			ITable actual1 = null;
//			expected1 = new SortedTable(expectedDataSet1.getTable("customer"));
//
//			actual1 = actualDataSet1.getTable("customer");
//			BigInteger id = (BigInteger) actual1.getValue(0, "id");
//			customer.setId(new Long(id.longValue()));
//			
//			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
//					actual1, expected1.getTableMetaData().getColumns()),
//					expected1.getTableMetaData());
//			KLSDataAccessFactory.getCustomerDAO().updateCustomer(customer);
//			actual1 = actualDataSet1.getTable("customer");
//			Assert.assertEquals(expected1.getValue(0, "name"),
//					actual1.getValue(0, "name"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(
//					"Exception thrown while running the testUpdateCustomer() dbunit test case.",
//					e.getCause());
//		}
//		logger.info("Executed the db unit test case testUpdateCustomer()");
//	}

//	@Test
//	public void testGetAllCustomersByName() {
//		List<Customer> list = new ArrayList<Customer>();
//		list = KLSDataAccessFactory.getCustomerDAO().getAllCustomersByName("chan");
//		logger.info("factory dao instace"
//				+ KLSDataAccessFactory.getCustomerDAO());
//		Assert.assertEquals(2, list.size());
//	}

//	@Test
//	public void testGetCustomer() {
//		Customer customer = KLSDataAccessFactory.getCustomerDAO().getCustomer(
//				14l);
//		logger.info("factory dao instace"
//				+ KLSDataAccessFactory.getCustomerDAO());
//		Assert.assertEquals("chandram", customer.getName().trim());
//
//	}

}
*/