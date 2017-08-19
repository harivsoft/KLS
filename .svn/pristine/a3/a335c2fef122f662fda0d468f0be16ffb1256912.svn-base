/*package com.vsoftcorp.kls.dataccess.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
import com.vsoftcorp.kls.business.entities.LandDetail;
import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LandDetailDAOTest extends DBUnitTC {
	private static final Logger logger = Logger
			.getLogger(LandDetailDAOTest.class);

	public LandDetailDAOTest() {
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
	public void testSaveLandDetail() {

		logger.info("Executing the test case saveLandDetail()");

		try {
			LandDetail landDetail = new LandDetail();

			landDetail.setAreaCultivated(11d);
			landDetail.setAreaOwned(13d);
			landDetail.setBsrNo("BSRNO1");
			landDetail.setRsrNo("RSRNO1");
			landDetail.setIsCharged("Y");
			landDetail.setChargedRemarks("Charged Remarks");
			landDetail.setMortgazedRemarks("Mortgaged remarks");
			landDetail.setSubVillageCode("subvillage 1");
			landDetail.setSurveyNo("SUR01");

			Village village = new Village();
			village.setName("Namapur");
			village.setPin(51133);

			Taluka taluka = new Taluka();
			taluka.setName("Sirpur");

			District district = new District();
			district.setName("KRMNGR");
			district.setDccBankCode("KRMo2");
			KLSDataAccessFactory.getDistrictDAO().saveDistrict(district);

			taluka.setDistrict(district);
			KLSDataAccessFactory.getTalukaDAO().saveTaluka(taluka);

			village.setTaluka(taluka);
			KLSDataAccessFactory.getVillageDAO().saveVillage(village);
			landDetail.setVillage(village);

			LandType landType = new LandType();
			landType.setName("Ltype1");
			KLSDataAccessFactory.getLandTypeDAO().saveLandType(landType);

			landDetail.setLandType(landType);
			
//			Customer customer=new Customer();
//			customer.setCbsCustId("CBS1");
//			customer.setCustomerId("CUS01");
//			customer.setMemberSrlNo("MEM1");
//			customer.setMemberType("MT1");
//			customer.setName("testName");
//			customer.setVillageId(1);
//			
//			KLSDataAccessFactory.getCustomerDAO().saveCustomer(customer);
			
			landDetail.setCustomerId(Long.parseLong("1"));
			KLSDataAccessFactory.getLandDetailDAO().saveLandDetail(landDetail);
			
		

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(
					expectedDataSet1.getTable("cust_land_detail"));
			actual1 = actualDataSet1.getTable("cust_land_detail");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			Assert.assertEquals(expected1.getValue(0, "sub_village_name"),
					actual1.getValue(0, "sub_village_name"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("End:Executed the test case saveLandDetail()");
	}

	@Test
	public void testUpdateLandDetail() {
		logger.info("Start:Executed the db unit test case updateLandDetail()");
		try {
			LandDetail landDetail = new LandDetail();
			landDetail.setAreaCultivated(112d);
			landDetail.setAreaOwned(132d);
			landDetail.setBsrNo("BSRNO2");
			landDetail.setRsrNo("RSRNO2");
			landDetail.setChargedRemarks("Charged Remarks");
			landDetail.setMortgazedRemarks("Mortgaged remarks");
			landDetail.setSubVillageCode("subvillage 2");
			landDetail.setSurveyNo("SUR01");
			Village village = new Village();
			village.setName("Namapur");
			village.setPin(51133);
			Taluka taluka = new Taluka();
			taluka.setName("Sirpur");
			District district = new District();
			district.setName("KRMNGR");
			district.setDccBankCode("KRMo2");
			LandType landType = new LandType();
			landType.setName("Lype1");

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();
			ITable expected1 = null;
			ITable actual1 = null;
			ITable distActaul1 = actualDataSet1.getTable("district");
			Integer distId = (Integer) distActaul1.getValue(0, "id");
			district.setId(distId);

			ITable talActaul1 = actualDataSet1.getTable("taluka");
			Integer talId = (Integer) talActaul1.getValue(0, "id");
			taluka.setId(talId);
			taluka.setDistrict(district);

			village.setTaluka(taluka);

			ITable villActual1 = actualDataSet1.getTable("village");
			Integer villageId = (Integer) villActual1.getValue(0, "id");
			village.setId(villageId);
			landDetail.setVillage(village);

			ITable landTypeActaul1 = actualDataSet1.getTable("land_type");
			Integer landTypeId = (Integer) landTypeActaul1.getValue(0, "id");
			landType.setId(landTypeId);
			landDetail.setLandType(landType);
			
			ITable customerActaul1 = actualDataSet1.getTable("customer");
			BigInteger customerId = (BigInteger) customerActaul1.getValue(0, "id");
//			Customer customer=new Customer();
//			customer.setId(customerId.longValue());
			landDetail.setCustomerId(Long.valueOf(customerId.toString()));

			actual1 = actualDataSet1.getTable("cust_land_detail");
			BigInteger landDetailId = (BigInteger) actual1.getValue(0, "id");
			landDetail.setId(landDetailId.longValue());

			KLSDataAccessFactory.getLandDetailDAO()
					.updateLandDetail(landDetail);
			expected1 = new SortedTable(
					expectedDataSet1.getTable("cust_land_detail"));

			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			actual1 = actualDataSet1.getTable("cust_land_detail");
			Assert.assertEquals(expected1.getValue(0, "sub_village_name"),
					actual1.getValue(0, "sub_village_name"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Executed the db unit test case updateLandDetail()");
	}

	@Test
	public void testGetLandDetailsByCustomerId() {
		logger.info("Start:Executed the db unit test case getLandDetailsByCustomerId()");
		try {
			List<LandDetail> list = new ArrayList<LandDetail>();
			list = KLSDataAccessFactory.getLandDetailDAO()
					.getLandDetailsByCustomerId(1L);
			Assert.assertEquals(2, list.size());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error while excuting the db unit test case getLandDetailsByCustomerId()");

		}
		logger.info("Start:Executed the db unit test case getLandDetailsByCustomerId()");

	}
}
*/