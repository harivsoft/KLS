/*package com.vsoftcorp.kls.dataccess.impl;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vsoftcorp.dbunit.DBUnitTC;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.loan.ILoanInterestCalculationDAO;
import com.vsoftcorp.kls.dataaccess.loan.impl.LoanInterestCalculationDAO;
import com.vsoftcorp.kls.dataaccess.loan.impl.LoanInterestHistoryCalculationDAO;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LoanInterestCalculationDAOTest extends DBUnitTC {

	private static final Logger logger = Logger
			.getLogger(LoanInterestCalculationDAOTest.class);

	public LoanInterestCalculationDAOTest() {
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
	public void testCurrentDayInterestCalculation() {
		try {
			ILoanInterestCalculationDAO calculationDAO = new LoanInterestCalculationDAO();
			calculationDAO.currentDayInterestCalculation(DateUtil
					.getVSoftDateByString("2/12/2014"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void testPostingInterest() {
		try {
			ILoanInterestPostingDAO postingDAO = new LoanInterestPostingDAO();
			postingDAO.postInterest(DateUtil
					.getVSoftDateByString("22/12/2013"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
*/