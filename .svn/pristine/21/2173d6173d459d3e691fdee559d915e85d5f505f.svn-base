/*package com.vsoftcorp.kls.dataccess.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

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
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.Customer;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.ProductType;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.IBranchDAO;
import com.vsoftcorp.kls.dataaccess.ICropDAO;
import com.vsoftcorp.kls.dataaccess.ICustomerDAO;
import com.vsoftcorp.kls.dataaccess.IInterestCategoryDAO;
import com.vsoftcorp.kls.dataaccess.ILandTypeDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationDetailDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationHeaderDAO;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.IProductTypeDAO;
import com.vsoftcorp.kls.dataaccess.ISchemeDAO;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.kls.valuetypes.SeasonStatus;

public class PacsLoanApplicationDetailDAOTest extends DBUnitTC {

	private static final Logger logger = Logger.getLogger(PacsLoanApplicationDetailDAOTest.class);

	public PacsLoanApplicationDetailDAOTest() {
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
	public void testSavePacsLoanApplicationDetail() {
		try {
			
			InterestCategory intCategory = new InterestCategory();
			ProductType productType = new ProductType();
			
			intCategory.setIntrCategoryDesc("INCAT");
			
			IInterestCategoryDAO iDao = KLSDataAccessFactory.getInterestCategoryDAO();
			iDao.saveInterestCategory(intCategory);
			
			productType.setDescription("PRODTYPEDESC");
			productType.setAtmApplicable("Y");
			productType.setInterestCategory(intCategory);
			
			IProductTypeDAO pDao = KLSDataAccessFactory.getProductTypeDAO();
			pDao.saveProductType(productType);
			
			Product product = new Product();
			product.setIntrCategory(intCategory);
			product.setProductType(productType);
			
			product.setName("PROD1");
			product.setShortName("PRO");
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			sdf1.setLenient(false);
			
				java.util.Date date = sdf1.parse("2014-02-15 00:00:00");
				
				product.setLastIntPostDate(DateUtil.getVSoftDateByString("15 Feb 2014"));
				
				product.setReleasedDate(DateUtil.getVSoftDateByString("01 Jan 2014"));

						
				product.setAtmApplicable("Y");
				product.setReleasedFlag("Y");
			
			
			IProductDAO dao = KLSDataAccessFactory.getProductMasterDAO();
			dao.saveProduct(product);
			
			
			
			PacsLoanApplicationDetail loanApp = new PacsLoanApplicationDetail();
			
			PacsLoanApplicationHeader loanHeader = new PacsLoanApplicationHeader();
			
			
			
			Crop crop = new Crop();
			
			LandType landType = new LandType();
			sdf1.setLenient(false);
			Customer cust = new Customer();
			
			cust.setCustomerId("CUS01");
			cust.setCbsCustId("cbs01");
			cust.setMemberSrlNo("MEM01");
			cust.setMemberType("MEM01");
			cust.setName("chandram");
			cust.setVillageId(1);

			ICustomerDAO cDao = KLSDataAccessFactory.getCustomerDAO();
			cDao.saveCustomer(cust);
			
			crop.setName("PADDY");
			ICropDAO cropDao = KLSDataAccessFactory.getCropDAO();
			cropDao.saveCrop(crop);
			
			Season season = new Season();
			season.setName("KHARIF");
			season.setProcessStatus(SeasonStatus.ENTRY_STARTED);
			season.setDrawalStartDate(DateUtil.getVSoftDateByString("15 Feb 2014"));
			season.setDrawalEndDate(DateUtil.getVSoftDateByString("15 Feb 2014"));
			season.setOverdueDate(DateUtil.getVSoftDateByString("15 Feb 2014"));
			
			ISeasonDAO sDao = KLSDataAccessFactory.getSeasonDAO();
			sDao.saveSeason(season);
			
			Branch branch = new Branch();
			branch.setName("Cuttack");
			branch.setLocation("Patnaik Nagar");
			branch.setCode("OR01");
			IBranchDAO branchDao = KLSDataAccessFactory.getBranchDAO();

			branchDao.saveBranch(branch);
			
			Pacs pacsMaster = new Pacs();
			pacsMaster.setBranch(branch);
			pacsMaster.setName("PAC");
			pacsMaster.setLocation("hyderabad");
			pacsMaster.setSecretaryName("nelson");

			IPacsDAO pdao = KLSDataAccessFactory.getPacsDAO();
			pdao.savePacs(pacsMaster);
			
			Scheme scheme = new Scheme();
			scheme.setName("SCHEME");
			scheme.setProduct(product);

			ISchemeDAO scDao = KLSDataAccessFactory.getSchemeDAO();
			scDao.saveScheme(scheme);
			
			landType.setName("DRY");
			ILandTypeDAO lDao = KLSDataAccessFactory.getLandTypeDAO();
			lDao.saveLandType(landType);
			
			loanHeader.setApplicationDate(new Date(date.getTime()));
			loanHeader.setApplicationType("R");
			loanHeader.setFinancialYear("2014-2015");
			loanHeader.setProcessStatus("A");
			loanHeader.setScheme(scheme);
			loanHeader.setCrop(crop);
			loanHeader.setPacs(pacsMaster);
			
			IPacsLoanApplicationHeaderDAO  pacsHeaderDao = KLSDataAccessFactory.getPacsLoanApplicationHeaderDAO();
			pacsHeaderDao.savePacsLoanApplicationHeader(loanHeader);
			
			BigDecimal b= new BigDecimal(1);
			Money amount = Money.valueOf(Currency.getInstance("INR"), BigDecimal.valueOf(1));
			loanApp.setApplicationStatus(LoanApplicationState.RECOMMENDED);
			loanApp.setCalculatedAmount(amount);
			loanApp.setCropId(crop);
			loanApp.setCustomerId(cust);
			loanApp.setEnteredRemarks("good");
			loanApp.setHeaderId(loanHeader);
			loanApp.setInspectionAmount(amount);
			loanApp.setInspectionRemarks("hi");
			loanApp.setLandArea(b);
			loanApp.setLandTypeId(landType);
			loanApp.setLoanApplicationNo("123");
			loanApp.setPriority(2);
			loanApp.setRecommendedAmount(amount);
			loanApp.setRequiredAmount(amount);
			loanApp.setSanctionedAmount(amount);
			loanApp.setSeasonId(season);

			IPacsLoanApplicationDetailDAO ldao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
			ldao.savePacsLoanApplicationDetail(loanApp);

			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();

			ITable expected1 = null;
			ITable actual1 = null;
			expected1 = new SortedTable(expectedDataSet1.getTable("pacs_loan_application_dtl"));
			actual1 = actualDataSet1.getTable("pacs_loan_application_dtl");
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			Assert.assertEquals(expected1.getValue(0, "inspection_remarks"),
					actual1.getValue(0, "inspection_remarks"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while excecuting the dbuint savePacsLoanApplicationDetail : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the savePacsLoanApplicationDetail() dbunit test case.",
					e.getCause());
		}
	}

	@Test
	public void testGetPacsLoanApplicationDetail() {
		try {

			PacsLoanApplicationDetail pacsDtl = new PacsLoanApplicationDetail();
			Long l = new Long("1");
			pacsDtl.setId(l);
			IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
			logger.info("factory dao instace" + dao);
			PacsLoanApplicationDetail c = dao.getPacsLoanApplicationDetail(pacsDtl);
			//Assert.assertEquals("good", c.getEnteredRemarks());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while excecuting the dbuint testGetPacsLoanApplicationDetail : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testGetPacsLoanApplicationDetail() dbunit test case.",
					e.getCause());
		}
	}

	@Test
	public void testUpdatePacsLoanApplicationDetail() {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			sdf1.setLenient(false);
			
				java.util.Date date = sdf1.parse("2014-02-15 00:00:00");
			PacsLoanApplicationDetail loanApp = new PacsLoanApplicationDetail(); 
			BigDecimal b= new BigDecimal(1);
			loanApp.setCalculatedAmount(b);
			
			loanApp.setEnteredRemarks("good");
			
			loanApp.setInspectionAmount(b);
			loanApp.setInspectionRemarks("hi");
			loanApp.setApplicationStatus(2);
			loanApp.setLandArea(b);
			loanApp.setLoanApplicationNo("123");
			loanApp.setPriority(2);
			loanApp.setRecommendedAmount(b);
			loanApp.setRequiredAmount(b);
			loanApp.setSanctionedAmount(b);
			Crop crop = new Crop();
			crop.setName("PADDY");
			IDataSet actualDataSet = getConnection().createDataSet();
			IDataSet actualDataSet1 = getConnection().createDataSet();
			IDataSet expectedDataSet1 = getExpectedDataSet();
			ITable expected1 = null;
			ITable actual = null;
			ITable actual1 = null;
			actual = actualDataSet.getTable("crop");
		//	expected1 = new SortedTable(expectedDataSet1.getTable("pacs_loan_application_dtl"));
			Integer cropId = (Integer) actual.getValue(0, "id");
			crop.setId(cropId);
			loanApp.setCropId(crop);
			
			Customer cust = new Customer();
			cust.setCustomerId("CUS01");
			cust.setCbsCustId("01");
			cust.setMemberSrlNo("MES01");
			cust.setMemberType("MEM01");
			cust.setName("chandu");
			cust.setVillageId(1);
			
			IDataSet actualDataSet2 = getConnection().createDataSet();
			ITable actual2 = null;
			actual2 = actualDataSet2.getTable("customer");
			//expected1 = new SortedTable(expectedDataSet1.getTable("pacs_loan_application_dtl"));
			BigInteger custId = (BigInteger) actual2.getValue(0, "id");
			cust.setId(custId.longValue());
			loanApp.setCustomerId(cust);
			
			PacsLoanApplicationHeader loanHeader = new PacsLoanApplicationHeader();
			loanHeader.setApplicationDate(new Date(date.getTime()));
			loanHeader.setApplicationType("R");
			loanHeader.setFinancialYear("2014-2015");
			loanHeader.setProcessStatus("A");
			Scheme s = new Scheme();
			s.setId(1);
			loanHeader.setScheme(s);
			loanHeader.setCrop(crop);
			Pacs p = new Pacs();
			p.setId(1);
			loanHeader.setPacs(p);

			IDataSet actualDataSet3 = getConnection().createDataSet();
			ITable actual3 = null;
			actual3 = actualDataSet3.getTable("customer");
		//	expected1 = new SortedTable(expectedDataSet1.getTable("pacs_loan_application_dtl"));
			BigInteger headerId = (BigInteger) actual3.getValue(0, "id");
			loanHeader.setId(headerId.longValue());
			loanApp.setHeaderId(loanHeader);	
			
			LandType landType = new LandType();
			landType.setName("DRY");
			IDataSet actualDataSet4 = getConnection().createDataSet();
			ITable actual4 = null;
			actual4 = actualDataSet4.getTable("land_type");
			//expected1 = new SortedTable(expectedDataSet1.getTable("pacs_loan_application_dtl"));
			Integer landTypeId = (Integer) actual4.getValue(0, "id");
			landType.setId(landTypeId);
			loanApp.setLandTypeId(landType);
			
			Season season = new Season();
			season.setName("KHARIF");
			season.setProcessStatus("E");
			season.setOverdueDate(new Date(date.getTime()));
			season.setDrawalStartDate(new Date(date.getTime()));
			season.setDrawalEndDate(new Date(date.getTime()));
			
			IDataSet actualDataSet5 = getConnection().createDataSet();
			ITable actual5 = null;
			actual5 = actualDataSet5.getTable("season");
			expected1 = new SortedTable(expectedDataSet1.getTable("pacs_loan_application_dtl"));
			BigInteger seasonId = (BigInteger) actual5.getValue(0, "id");
			season.setId(seasonId.longValue());
			loanApp.setSeasonId(season);
			
			actual1 = actualDataSet1.getTable("pacs_loan_application_dtl");
			BigInteger loanDtlId = (BigInteger) actual1.getValue(0, "id");
			loanApp.setId(loanDtlId.longValue());
			actual1 = new SortedTable(DefaultColumnFilter.includedColumnsTable(
					actual1, expected1.getTableMetaData().getColumns()),
					expected1.getTableMetaData());
			IPacsLoanApplicationDetailDAO ldao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
			ldao.updatePacsLoanApplicationDetail(loanApp);
			actual1 = actualDataSet1.getTable("pacs_loan_application_dtl");
			Assert.assertEquals(expected1.getValue(0, "inspection_remarks"),
					actual1.getValue(0, "inspection_remarks"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while excecuting the dbuint testUpdatePacsLoanApplicationDetail : "
					+ e.getMessage());
			throw new RuntimeException(
					"Exception thrown while running the testUpdatePacsLoanApplicationDetail() dbunit test case.",
					e.getCause());
		}
	}
}
*/