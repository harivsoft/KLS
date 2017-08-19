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
import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.dataaccess.ILandTypeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LandTypeDAOTest extends DBUnitTC {

	private static final Logger logger = Logger
			.getLogger(LandTypeDAOTest.class);

	public LandTypeDAOTest() {
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
	public void testSaveLandType() {

		try {
			LandType theLandType = new LandType();
			theLandType.setName("LANDTYPE");
			ILandTypeDAO dao = KLSDataAccessFactory.getLandTypeDAO();

			dao.saveLandType(theLandType);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("land_type"));
			actual1 = actualDataSet1.getTable("land_type");
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
	public void testGetLandType() {
		LandType theLandType = new LandType();
		theLandType.setId(50);
		ILandTypeDAO dao = KLSDataAccessFactory.getLandTypeDAO();
		logger.info("factory dao instace" + dao);
		LandType c = dao.getLandType(theLandType);

		Assert.assertEquals("LANDTYPE1", c.getName().trim());
	}

	@Test
	public void testUpdateLandType() {
		try {
			LandType theLandTypeDB = new LandType();
			theLandTypeDB.setId(50);
			theLandTypeDB.setName("LANDTYPE1");
			
			ILandTypeDAO dao = KLSDataAccessFactory.getLandTypeDAO();

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();
			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("land_type"));

			actual1 = actualDataSet1.getTable("land_type");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			dao.updateLandType(theLandTypeDB);
			actual1 = actualDataSet1.getTable("land_type");
			Assert.assertEquals(expected1.getValue(0, "name"),actual1.getValue(0, "name"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
*/