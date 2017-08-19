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
import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.dataaccess.IDistrictDAO;
import com.vsoftcorp.kls.dataaccess.ITalukaDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class TalukaDAOTest extends DBUnitTC {

	private static final Logger logger = Logger.getLogger(TalukaDAOTest.class);

	public TalukaDAOTest() {
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
	public void testSaveTaluka() {
		try {
			District master = new District();

			master.setDccBankCode("001");
			master.setName("Hyd");

			IDistrictDAO distDao = KLSDataAccessFactory.getDistrictDAO();
			distDao.saveDistrict(master);

			Taluka talukaMaster = new Taluka();
			talukaMaster.setName("ABC");
			talukaMaster.setDistrict(master);

			ITalukaDAO dao = KLSDataAccessFactory.getTalukaDAO();
			dao.saveTaluka(talukaMaster);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("taluka"));
			actual1 = actualDataSet1.getTable("taluka");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			Assert.assertEquals(expected1.getValue(0, "name"),
					actual1.getValue(0, "name"));
		} catch (Exception e) {
			logger.error("Exception while excecuting the dbuint testSaveTaluka : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testSaveTaluka() dbunit test case.",
					e.getCause());
		}
	}

	@Test
	public void testGetTaluka() {
		try {

			Taluka talukaMaster = new Taluka();
			talukaMaster.setId(1001);
			ITalukaDAO dao = KLSDataAccessFactory.getTalukaDAO();
			logger.info("factory dao instace" + dao);
			Taluka c = dao.getTaluka(talukaMaster);
			Assert.assertEquals("ABCD", c.getName().trim());
		} catch (Exception e) {
			logger.error("Exception while excecuting the dbuint testGetTaluka : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testGetTaluka() dbunit test case.",
					e.getCause());
		}
	}

	@Test
	public void testUpdateTaluka() {
		try {

			Taluka taluka = new Taluka();
			taluka.setName("EFGHI");
			District district = new District();
			// district.setId(100);
			district.setDccBankCode("004");
			district.setName("SEC");
			ITalukaDAO dao = KLSDataAccessFactory.getTalukaDAO();
			IDataSet actualDataSet = getConnection().createDataSet();
			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();
			ITable expected1 = null;
			ITable actual = null;
			ITable actual1 = null;
			actual = actualDataSet.getTable("district");
			expected1 = new SortedTable(expectedDataSet1.getTable("taluka"));
			Integer districtId = (Integer) actual.getValue(0, "id");
			district.setId(districtId);
			taluka.setDistrict(district);
			actual1 = actualDataSet1.getTable("taluka");
			Integer talukaId = (Integer) actual1.getValue(0, "id");
			taluka.setId(talukaId);
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			dao.updateTaluka(taluka);
			actual1 = actualDataSet1.getTable("taluka");
			Assert.assertEquals(expected1.getValue(0, "name"),
					actual1.getValue(0, "name"));
		} catch (Exception e) {
			logger.error("Exception while excecuting the dbuint testUpdateTaluka : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testUpdateTaluka() dbunit test case.",
					e.getCause());
		}
	}
}
*/