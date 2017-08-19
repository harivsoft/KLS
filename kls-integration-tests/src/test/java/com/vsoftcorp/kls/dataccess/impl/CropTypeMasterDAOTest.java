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
import com.vsoftcorp.kls.business.entities.CropTypeMaster;
import com.vsoftcorp.kls.dataaccess.ICropTypeMasterDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class CropTypeMasterDAOTest extends DBUnitTC {

	private static final Logger logger = Logger
			.getLogger(CropTypeMasterDAOTest.class);

	public CropTypeMasterDAOTest() {
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
	public void testSaveCropType() {

		try {
			CropTypeMaster theCropTypeMaster = new CropTypeMaster();
			theCropTypeMaster.setCropTypeCode("CT001");
			theCropTypeMaster.setCropTypeName("CERALS");
			ICropTypeMasterDAO dao = KLSDataAccessFactory
					.getCropTypeMasterDAO();

			dao.saveCropType(theCropTypeMaster);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("crop_type_mast"));
			actual1 = actualDataSet1.getTable("crop_type_mast");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());

			Assertion.assertEquals(expected1, actual1);
			Assert.assertEquals(expected1.getRowCount(), actual1.getRowCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetCropType() {
		CropTypeMaster theCropTypeMaster = new CropTypeMaster();
		theCropTypeMaster.setCropTypeCode("CT002");
		ICropTypeMasterDAO dao = KLSDataAccessFactory.getCropTypeMasterDAO();
		logger.info("factory dao instace" + dao);
		CropTypeMaster c = dao.getCropType(theCropTypeMaster);

		Assert.assertEquals("CERALS", c.getCropTypeName().trim());
	}
	
	
	@Test
	public void testUpdateCropType() {
		try {
			CropTypeMaster masterRecordInDB = new CropTypeMaster();
			masterRecordInDB.setCropTypeCode("9999");
			masterRecordInDB.setCropTypeName("CASHCROP");
			
			ICropTypeMasterDAO dao = KLSDataAccessFactory.getCropTypeMasterDAO();

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();
			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("crop_type_mast"));

			actual1 = actualDataSet1.getTable("crop_type_mast");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			dao.updateCropType(masterRecordInDB);
			actual1 = actualDataSet1.getTable("crop_type_mast");
			// logger.info("actual"+actual1.getValue(0, "crop_name"));
			// logger.info("expected"+expected1.getValue(0, "crop_name"));
			Assertion.assertEquals(expected1, actual1);
			Assert.assertEquals(expected1.getRowCount(), actual1.getRowCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
*/