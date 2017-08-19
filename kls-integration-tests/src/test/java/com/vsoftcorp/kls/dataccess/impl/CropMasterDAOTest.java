/*package com.vsoftcorp.kls.dataccess.impl;

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
import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.dataaccess.ICropDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class CropMasterDAOTest extends DBUnitTC {

	private static final Logger logger = Logger
			.getLogger(CropMasterDAOTest.class);

	public CropMasterDAOTest() {
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
	public void testSaveCrop() {

		try {
			Crop theCropMaster = new Crop();
			theCropMaster.setName("PADDY");
			ICropDAO dao = KLSDataAccessFactory.getCropDAO();

			dao.saveCrop(theCropMaster);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("crop"));
			actual1 = actualDataSet1.getTable("crop");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			Assert.assertEquals(expected1.getValue(0, "name"),
					actual1.getValue(0, "name"));
			//Assertion.assertEquals(expected1, actual1);
			//Assert.assertEquals(expected1.getRowCount(), actual1.getRowCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetCrop() {
		Crop theCropMaster = new Crop();
		theCropMaster.setId(2);
		ICropDAO dao = KLSDataAccessFactory.getCropDAO();
		logger.info("factory dao instace" + dao);
		Crop c = dao.getCrop(theCropMaster);

		Assert.assertEquals("PADDY", c.getName().trim());
	}

	@Test
	public void testUpdateCrop() {
		try {
			Crop masterRecordInDB = new Crop();
			masterRecordInDB.setId(1234);
			masterRecordInDB.setName("JOWAR");
			
			ICropDAO dao = KLSDataAccessFactory.getCropDAO();

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();
			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("crop"));

			actual1 = actualDataSet1.getTable("crop");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			dao.updateCrop(masterRecordInDB);
			actual1 = actualDataSet1.getTable("crop");
			// logger.info("actual"+actual1.getValue(0, "crop_name"));
			// logger.info("expected"+expected1.getValue(0, "crop_name"));
			//Assertion.assertEquals(expected1, actual1);
			//Assert.assertEquals(expected1.getRowCount(), actual1.getRowCount());
			Assert.assertEquals(expected1.getValue(0, "name"),
					actual1.getValue(0, "name"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
*/