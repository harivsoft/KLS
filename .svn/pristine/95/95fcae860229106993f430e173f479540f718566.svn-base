/*package com.vsoftcorp.kls.dataccess.impl;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vsoftcorp.dbunit.DBUnitTC;
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class ChargesDAOTest extends DBUnitTC {
	private static final Logger logger = Logger.getLogger(ChargesDAOTest.class);

	public ChargesDAOTest() {
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
	public void getCharges() {
		Money charges = KLSDataAccessFactory.getChargesDAO()
				.getInsuranceCharges(8L, 1L);
		logger.info("Charges:" + charges);
		Assert.assertEquals(Money.valueOf(Currency.getInstance("INR"), "340"),
				charges);
	}

}
*/