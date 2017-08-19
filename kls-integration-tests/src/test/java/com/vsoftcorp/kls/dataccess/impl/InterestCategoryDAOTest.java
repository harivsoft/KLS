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
import com.vsoftcorp.kls.dataaccess.IInterestCategoryDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class InterestCategoryDAOTest extends DBUnitTC {

	private static final Logger logger = Logger
			.getLogger(InterestCategoryDAOTest.class);

	public InterestCategoryDAOTest() {
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
	public void testSaveInterestCategory() {

		try {
			InterestCategory theInterestCat = new InterestCategory();
			theInterestCat.setIntrCategoryDesc("FARMER");
			IInterestCategoryDAO dao = KLSDataAccessFactory.getInterestCategoryDAO();

			dao.saveInterestCategory(theInterestCat);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("interest_category"));
			actual1 = actualDataSet1.getTable("interest_category");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());

			Assert.assertEquals(expected1.getValue(0, "description"),
					actual1.getValue(0, "description"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetInterestCategory() {
		InterestCategory theInterestCat = new InterestCategory();
		theInterestCat.setId(2);
		IInterestCategoryDAO dao = KLSDataAccessFactory.getInterestCategoryDAO();
		logger.info("factory dao instace" + dao);
		InterestCategory c = dao.getInterestCategory(theInterestCat,false);

		Assert.assertEquals("FARMER", c.getIntrCategoryDesc().trim());
	}

	@Test
	public void testUpdateInterestCategory() {
		try {
			InterestCategory theInterestCat = new InterestCategory();
			theInterestCat.setId(1);
			theInterestCat.setIntrCategoryDesc("INCAT1");
			
			IInterestCategoryDAO dao = KLSDataAccessFactory.getInterestCategoryDAO();

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();
			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("interest_category"));

			actual1 = actualDataSet1.getTable("interest_category");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			dao.updateInterestCategory(theInterestCat);
			actual1 = actualDataSet1.getTable("interest_category");
			Assert.assertEquals(expected1.getValue(0, "description"),
					actual1.getValue(0, "description"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
*/