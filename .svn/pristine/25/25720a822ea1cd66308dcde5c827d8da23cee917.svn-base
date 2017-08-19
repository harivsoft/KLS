/*package com.vsoftcorp.kls.dataccess.impl;

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
import com.vsoftcorp.kls.business.entities.SeasonDefinition;
import com.vsoftcorp.kls.dataaccess.ISeasonMasterDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SeasonMasterDAOTest extends DBUnitTC {

	private static final Logger logger = Logger
			.getLogger(SeasonMasterDAOTest.class);

	public SeasonMasterDAOTest() {
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


			SeasonDefinition season = new SeasonDefinition();
			season.setName("KHARIF");
			season.setDrawalStartDay(1);
			season.setDrawalStartMonth(1);
			season.setDrawalEndDay(1);
			season.setDrawalEndMonth(1);
			season.setLoanOverdueDay(1);
			season.setLoanOverdueMonth(1);

			ISeasonMasterDAO dao = KLSDataAccessFactory.getSeasonDefinitionDAO();

			dao.saveSeason(season);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("season_definition"));
			actual1 = actualDataSet1.getTable("season_definition");
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
	public void testGetAllSeasons() {
		try {
			List<SeasonDefinition> list = new ArrayList<SeasonDefinition>();
			ISeasonMasterDAO dao = KLSDataAccessFactory.getSeasonDefinitionDAO();
			list = dao.getAllSeasonDefinition();
			// logger.info("season list"+list.size());
			Assert.assertEquals(2, list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSeason() {
		try {
			SeasonDefinition theSeason =new SeasonDefinition();
			theSeason.setId(100);
			ISeasonMasterDAO dao = KLSDataAccessFactory.getSeasonDefinitionDAO();
			SeasonDefinition season = dao.getSeason(theSeason);
			// logger.info("season list"+list.size());
			Assert.assertEquals("KHARIF",season.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
*/