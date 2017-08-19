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
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SeasonDAOTest extends DBUnitTC {

	private static final Logger logger = Logger
			.getLogger(SeasonDAOTest.class);

	public SeasonDAOTest() {
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
	public void testSaveSeason() {

		try { 

		
			Season master = new Season();
			master.setName("KARIF_2014-2015");
			master.setDrawalStartDate(DateUtil
					.getVSoftDateByString("15/04/2014"));
			master.setDrawalEndDate(DateUtil
					.getVSoftDateByString("10/08/2014"));
			master.setOverdueDate(DateUtil
					.getVSoftDateByString("26/10/2014"));
						

			ISeasonDAO dao = KLSDataAccessFactory.getSeasonDAO();

			dao.saveSeason(master);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("season"));
			actual1 = actualDataSet1.getTable("season");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());

		//	Assertion.assertEquals(expected1, actual1);
			Assert.assertEquals(expected1.getRowCount(), actual1.getRowCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void testGetSeason() {
		try {
			Season theSeason =new Season();
			theSeason.setId(100l);
			ISeasonDAO dao = KLSDataAccessFactory.getSeasonDAO();
			Season season = dao.getSeason(theSeason);
			// logger.info("season list"+list.size());
			Assert.assertEquals("KHARIF_2014-2015",season.getName().trim());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while excecuting the dbuint testGetSeason : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testGetSeason() dbunit test case.",
					e.getCause());
		}
	}

}
*/