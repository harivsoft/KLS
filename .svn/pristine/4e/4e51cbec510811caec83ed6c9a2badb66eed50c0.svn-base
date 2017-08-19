package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.business.entities.SubPurpose;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationDocument;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.DocumentData;
import com.vsoftcorp.kls.data.PacsLoanApplicationData;
import com.vsoftcorp.kls.data.PacsLoanEntryDocumentDetailsData;
import com.vsoftcorp.kls.data.VillageDetailsData;
import com.vsoftcorp.kls.data.gateway.datahelpers.AddressData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountPropertyDAO;
import com.vsoftcorp.kls.service.IVillageService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.util.FileUtil;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.loanapplication.InstallmentFrequency;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1565
 * 
 */
public class PacsLoanApplicationHelper {

	private static final Logger logger = Logger.getLogger(PacsLoanApplicationHelper.class);

	public static PacsLoanApplication getPacsLoanApplication(PacsLoanApplicationData pacsLoanApplicationData) {

		PacsLoanApplication pacsLoanApplication = new PacsLoanApplication();
		
		pacsLoanApplication.setId(pacsLoanApplicationData.getId());
		Product product = new Product();
		product.setId(pacsLoanApplicationData.getProductId());
		pacsLoanApplication.setProduct(product);

		Purpose purpose = new Purpose();
		purpose.setId(pacsLoanApplicationData.getPurposeId());
		pacsLoanApplication.setPurpose(purpose);

		if (pacsLoanApplicationData.getSubPurposeId() != null) {
			SubPurpose subPurpose = new SubPurpose();
			subPurpose.setId(pacsLoanApplicationData.getSubPurposeId());
			pacsLoanApplication.setSubPurpose(subPurpose);
		}
		pacsLoanApplication.setMoratoriumPrinciplePeriod(pacsLoanApplicationData.getMoratoriumPrinciplePeriod());
		pacsLoanApplication.setMoratoriumInterestPeriod(pacsLoanApplicationData.getMoratoriumInterestPeriod());
		// Customer customer = new Customer();
		// customer.setId(pacsLoanApplicationData.getCustomerId());
		pacsLoanApplication.setCustomerId(pacsLoanApplicationData.getCustomerId());

		// Scheme scheme = new Scheme();
		// scheme.setId(pacsLoanApplicationData.getSchemeId());
		// pacsLoanApplication.setScheme(scheme);
		String applicationDate="";
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		PersonData person=RestClientUtil.getCustomerById(pacsLoanApplicationData.getCustomerId());
		Pacs pacs=pacsDao.getPacs(person.getPacsId());
		logger.info("pacs type::"+pacs.getPacsStatus());
		if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
			applicationDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
			//businessDate="2017-05-01";
			//pacsLoanApplication.setApplicationDate(DateUtil.getVSoftDateByString(applicationDate));
		}
		else
		applicationDate = pacsLoanApplicationData.getApplicationDate();
		if (applicationDate != null) {
			pacsLoanApplication.setApplicationDate(DateUtil.getVSoftDateByString(applicationDate));
		}
	
		pacsLoanApplication.setTotalAmountAsPerUnitCost(MasterHelper.populateAmountParam(pacsLoanApplicationData
				.getTotalAmountAsPerUnitCost()));
		pacsLoanApplication.setTotalRequestedAmount(MasterHelper.populateAmountParam(pacsLoanApplicationData
				.getTotalRequestedAmount()));
		pacsLoanApplication.setRecommendedAmount(MasterHelper.populateAmountParam(pacsLoanApplicationData
				.getRecommendedAmount()));

		Long pacsLoanApplicationId = pacsLoanApplicationData.getId();
		Integer appStatus = pacsLoanApplicationData.getApplicationStatus();
		if (pacsLoanApplicationId == null || appStatus == null) {
			pacsLoanApplication.setApplicationStatus(LoanApplicationState.ENTERED);
		}
		if (appStatus != null) {
			LoanApplicationState applicationStatus = LoanApplicationState.getType(appStatus);
			pacsLoanApplication.setApplicationStatus(applicationStatus);
			if(LoanApplicationState.AUTHORIZATION_PENDING.equals(applicationStatus)){ //Added for Inspection authorization
				populateInspectionParams(pacsLoanApplicationData, pacsLoanApplication);
			}
			if (LoanApplicationState.INSPECTED.equals(applicationStatus) || LoanApplicationState.INSPECTION_PENDING.equals(applicationStatus)) {
				pacsLoanApplication.setApplicationStatus(applicationStatus);
				populateInspectionParams(pacsLoanApplicationData, pacsLoanApplication);
				if(null != pacsLoanApplicationData.getInspectionAuthRemarks()){
					pacsLoanApplication.setInspectionAuthRemarks(pacsLoanApplicationData.getInspectionAuthRemarks());				
				}
			} else if (LoanApplicationState.SANCTIONED.equals(applicationStatus) || LoanApplicationState.SANCTION_PENDING.equals(applicationStatus)) {
				pacsLoanApplication.setApplicationStatus(applicationStatus);
				populateInspectionParams(pacsLoanApplicationData, pacsLoanApplication);
				InterestCategory intCategory = new InterestCategory();
				intCategory.setId(pacsLoanApplicationData.getInterestCategoryId());
				pacsLoanApplication.setInterestCategory(intCategory);
				String marginalPercentage = pacsLoanApplicationData.getMarginalPercentage();
				BigDecimal marginalPerAmt = BigDecimal.ZERO;
				if (marginalPercentage != null) {
					marginalPerAmt = new BigDecimal(marginalPercentage);
				}
				pacsLoanApplication.setMarginalPercentage(marginalPerAmt);
				pacsLoanApplication.setMarginalAmount(MasterHelper.populateAmountParam(pacsLoanApplicationData
						.getMarginalAmount()));

				pacsLoanApplication.setSanctionAmount(MasterHelper.populateAmountParam(pacsLoanApplicationData
						.getSanctionAmount()));
                String sanctionDate=getPacsBusinessDate(pacsLoanApplicationData);
                if(sanctionDate=="")
				sanctionDate = pacsLoanApplicationData.getSanctionDate();
				if (sanctionDate != null) {
					pacsLoanApplication.setSanctionDate(DateUtil.getVSoftDateByString(sanctionDate));
				}
				pacsLoanApplication.setSanctionRemarks(pacsLoanApplicationData.getSanctionRemarks());
				pacsLoanApplication.setLoanPeriod(pacsLoanApplicationData.getLoanPeriod());

				pacsLoanApplication.setNoOfInstallments(pacsLoanApplicationData.getNoOfInstallments());
				Integer installmentFrequency = pacsLoanApplicationData.getInstallmentFrequency();
				if (installmentFrequency != null) {
					pacsLoanApplication.setInstallmentFrequency(InstallmentFrequency.getType(installmentFrequency));
				}
			} else if (LoanApplicationState.REJECTED.equals(applicationStatus)) {
				pacsLoanApplication.setSanctionRemarks(pacsLoanApplicationData.getSanctionRemarks());
				if(null != pacsLoanApplicationData.getInspectionAuthRemarks()){
					pacsLoanApplication.setInspectionAuthRemarks(pacsLoanApplicationData.getInspectionAuthRemarks());				
				}
			}
			
		}
		return pacsLoanApplication;
	}

	public static PacsLoanEntryDocumentDetailsData getPacsLoanEntryDocumentDetailsData(LoanApplicationDocument master) {
		PacsLoanEntryDocumentDetailsData data = new PacsLoanEntryDocumentDetailsData();

		return data;

	}

	public static PacsLoanApplicationData getPacsLoanApplicationData(PacsLoanApplication pacsLoanApplication) {

		PacsLoanApplicationData applicationData = new PacsLoanApplicationData();
		applicationData.setId(pacsLoanApplication.getId());
		Money totalAmtPerUnitCostMoney = pacsLoanApplication.getTotalAmountAsPerUnitCost();
		if (totalAmtPerUnitCostMoney != null) {
			applicationData.setTotalAmountAsPerUnitCost(MoneyUtil.getAmountRoundedValue(
					totalAmtPerUnitCostMoney.getAmount()).toString());
		} else {
			applicationData.setTotalAmountAsPerUnitCost(null);
		}
		Money recommendedMoney = pacsLoanApplication.getRecommendedAmount();
		if (recommendedMoney != null) {
			applicationData.setRecommendedAmount(MoneyUtil.getAmountRoundedValue(recommendedMoney.getAmount())
					.toString());
		} else {
			applicationData.setRecommendedAmount(null);
		}
		Money totalRequestedMoney = pacsLoanApplication.getTotalRequestedAmount();
		if (totalRequestedMoney != null) {
			applicationData.setTotalRequestedAmount(MoneyUtil.getAmountRoundedValue(totalRequestedMoney.getAmount())
					.toString());
		} else {
			applicationData.setTotalRequestedAmount(null);
		}
		applicationData.setScrutinyRemarks(pacsLoanApplication.getScrutinyRemarks());
		Money scrutinyMoney = pacsLoanApplication.getScrutinyAmount();
		if (scrutinyMoney != null) {
			applicationData.setScrutinyAmount(MoneyUtil.getAmountRoundedValue(scrutinyMoney.getAmount()).toString());
		} else {
			applicationData.setScrutinyAmount(null);
		}
		applicationData.setSanctionRemarks(pacsLoanApplication.getSanctionRemarks());
		Money sanctionMoney = pacsLoanApplication.getSanctionAmount();
		if (sanctionMoney != null) {
			applicationData.setSanctionAmount(MoneyUtil.getAmountRoundedValue(sanctionMoney.getAmount()).toString());
		} else {
			applicationData.setSanctionAmount(null);
		}
		Date applicationDate = pacsLoanApplication.getApplicationDate();
		if (applicationDate != null) {
			applicationData.setApplicationDate(DateUtil.getDateString(pacsLoanApplication.getApplicationDate()));
		} else {
			applicationData.setApplicationDate(null);
		}
		LoanApplicationState applicationStatus = pacsLoanApplication.getApplicationStatus();
		if (applicationStatus != null) {
			applicationData.setApplicationStatus(applicationStatus.getValue());
		} else {
			applicationData.setApplicationStatus(LoanApplicationState.ENTERED.getValue());
		}

		// applicationData.setCustId(pacsLoanApplication.getCustomerId().toString());

		PersonData person = RestClientUtil.getCustomerById(pacsLoanApplication.getCustomerId());

		// Customer customer = pacsLoanApplication.getCustomer();
		if (person != null) {
			// applicationData.setCustId(pacsLoanApplication.getCustomerId().toString());
			applicationData.setCustomerId(pacsLoanApplication.getCustomerId());
			applicationData.setCustomerName(person.getName());
			applicationData.setFatherSpouseName(person.getFatherName());
			List<AddressData> addressDatas = new ArrayList<AddressData>();
			addressDatas = person.getContactDetailList();
			AddressData addressData = new AddressData();
			addressData = addressDatas.get(0);
			applicationData.setContactNumber(addressData.getPhoneNo());
			if (applicationData.getContactNumber() == null)
				applicationData.setContactNumber(addressData.getMobileNo());
			VillageDetailsData villageDetailsData = new VillageDetailsData();
			IVillageService villageMasterService = KLSServiceFactory.getVillageMasterService();
			villageDetailsData = villageMasterService.getVillageDetailsById(addressData.getVillageId());
			applicationData.setVillageName(villageDetailsData.getVillageName());
			applicationData.setTalukaName(villageDetailsData.getTalukaName());
			applicationData.setDistrictName(villageDetailsData.getDistrictName());

		}
		Money inspectionMoney = pacsLoanApplication.getInspectionAmount();
		if (inspectionMoney != null) {
			applicationData
					.setInspectionAmount(MoneyUtil.getAmountRoundedValue(inspectionMoney.getAmount()).toString());
		} else {
			applicationData.setInspectionAmount(null);
		}
		Date inspectionDate = pacsLoanApplication.getInspectionDate();
		if (inspectionDate != null) {
			applicationData.setInspectionDate(DateUtil.getDateString(inspectionDate));
		} else {
			applicationData.setInspectionDate(null);
		}
		applicationData.setInspectionRemarks(pacsLoanApplication.getInspectionRemarks());
		InstallmentFrequency installmentFreq = pacsLoanApplication.getInstallmentFrequency();
		if (installmentFreq != null) {
			applicationData.setInstallmentFrequency(installmentFreq.getValue());
		}
		applicationData.setNoOfInstallments(pacsLoanApplication.getNoOfInstallments());
		applicationData.setMasterApplicationId(pacsLoanApplication.getMasterApplicationId());
		applicationData.setLoanPeriod(pacsLoanApplication.getLoanPeriod());

		Money marginalMoney = pacsLoanApplication.getMarginalAmount();
		if (marginalMoney != null) {
			applicationData.setMarginalAmount(MoneyUtil.getAmountRoundedValue(marginalMoney.getAmount()).toString());
		} else {
			applicationData.setMarginalAmount(null);
		}
		BigDecimal marginalPercentage = pacsLoanApplication.getMarginalPercentage();
		if (marginalPercentage != null) {
			applicationData.setMarginalPercentage(MasterHelper.roundTo2DecimalPlaces(marginalPercentage).toString());
		} else {
			applicationData.setMarginalPercentage(null);
		}
		Date sanctionDate = pacsLoanApplication.getSanctionDate();
		if (sanctionDate != null) {
			applicationData.setSanctionDate(DateUtil.getDateString(sanctionDate));
		} else {
			applicationData.setSanctionDate(null);
		}

		Purpose purpose = pacsLoanApplication.getPurpose();
		if (purpose != null) {
			applicationData.setPurposeId(purpose.getId());
			applicationData.setPurposeName(purpose.getName());
		}
		SubPurpose subPurpose = pacsLoanApplication.getSubPurpose();
		if (subPurpose != null) {
			applicationData.setSubPurposeId(subPurpose.getId());
			applicationData.setSubPurposeName(subPurpose.getName());
		}
		/*
		 * if (pacsLoanApplication.getScheme() != null) {
		 * applicationData.setSchemeId(pacsLoanApplication.getScheme().getId());
		 * applicationData
		 * .setSchemeName(pacsLoanApplication.getScheme().getName()); }
		 */
		Product product = pacsLoanApplication.getProduct();
		if (product != null) {
			applicationData.setProductId(product.getId());
			applicationData.setProductName(product.getName());
		
		applicationData.setMoratoriumPrinciplePeriod(pacsLoanApplication.getMoratoriumPrinciplePeriod());
		//applicationData.setMoratoriumPrinciplePeriod(pacsLoanApplication.getProduct().getMoratoriumPrinciplePeriod());
		}
		applicationData.setMoratoriumInterestPeriod(pacsLoanApplication.getMoratoriumInterestPeriod());
		InterestCategory interestCategory = pacsLoanApplication.getInterestCategory();
		if (interestCategory != null) {
			applicationData.setInterestCategoryId(interestCategory.getId());
		}

		if (pacsLoanApplication.getApplicationStatus() == LoanApplicationState.SANCTIONED) {
			applicationData.setShareAmountToBeDeducted(LoanServiceUtil
					.calculateShareAmountToDeduct(pacsLoanApplication).toString());
			if (pacsLoanApplication.getProduct().getProcessingFee() != null)
				applicationData.setProcessingFee((pacsLoanApplication.getSanctionAmount().getAmount().multiply(pacsLoanApplication.getProduct().getProcessingFee())).divide(new BigDecimal(100)).setScale(2).toString());
						String accountOpeningDate = DateUtil.getDateString(pacsLoanApplication.getSanctionDate());
			logger.info("accountOpeningDate :" + accountOpeningDate);
			Integer noOfMonthsToAdd = null;
			Integer noOfMonthsToAddToExp = null;
			if (pacsLoanApplication.getInstallmentFrequency() != null && pacsLoanApplication.getProduct() != null
					&& pacsLoanApplication.getMoratoriumPrinciplePeriod() != null) {
				noOfMonthsToAdd = pacsLoanApplication.getInstallmentFrequency().getValue()
						+ pacsLoanApplication.getMoratoriumPrinciplePeriod();
				logger.info("noOfMonthsToAdd :" + noOfMonthsToAdd);
			}
			String firstDueDate = DateUtil.getDateByAddingNoOfMonths(accountOpeningDate, noOfMonthsToAdd);
			logger.info("firstDueDate :" + firstDueDate);
			applicationData.setFirstDueDate(firstDueDate);
			if (pacsLoanApplication.getLoanPeriod() != null) {
				noOfMonthsToAddToExp = pacsLoanApplication.getLoanPeriod();
			}

			logger.info("noOfMonthsToAddToExp :" + noOfMonthsToAddToExp);
			String loanExpiryDate = DateUtil.getDateByAddingNoOfMonths(accountOpeningDate, noOfMonthsToAddToExp);
			logger.info("loanExpiryDate :" + loanExpiryDate);
			applicationData.setLoanExpiryDate(loanExpiryDate);

			if (pacsLoanApplication.getSanctionAmount() != null && pacsLoanApplication.getNoOfInstallments() != null) {
				int amount = pacsLoanApplication.getSanctionAmount().getAmount().intValue()
						/ pacsLoanApplication.getNoOfInstallments();
				logger.info("amount :" + amount);
				applicationData.setInstallmentAmount(String.valueOf(LoanServiceUtil.getNearestRoundedTenRupee(amount)));
			}

		}

		return applicationData;
	}

	/**
	 * 
	 * @param pacsLoanApplicationData
	 * @param pacsLoanApplication
	 */
	private static void populateInspectionParams(PacsLoanApplicationData pacsLoanApplicationData,
			PacsLoanApplication pacsLoanApplication) {

		pacsLoanApplication.setScrutinyAmount(MasterHelper.populateAmountParam(pacsLoanApplicationData
				.getScrutinyAmount()));
		pacsLoanApplication.setScrutinyRemarks(pacsLoanApplicationData.getScrutinyRemarks());
		pacsLoanApplication.setInspectionAmount(MasterHelper.populateAmountParam(pacsLoanApplicationData
				.getInspectionAmount()));
		pacsLoanApplication.setInspectionRemarks(pacsLoanApplicationData.getInspectionRemarks());
		String inspectionDate =getPacsBusinessDate(pacsLoanApplicationData);
		if(inspectionDate=="")
		inspectionDate=pacsLoanApplicationData.getInspectionDate();
		if (inspectionDate != null) {
			pacsLoanApplication.setInspectionDate(DateUtil.getVSoftDateByString(inspectionDate));
		}
	}

	public static LoanApplicationDocument getApplicationDocument(PacsLoanEntryDocumentDetailsData data,
			Long applicationId) {

		LoanApplicationDocument master = new LoanApplicationDocument();

		if (data.getId() != null) {
			master.setId(data.getId());
		}
		master.setApplicationId(applicationId);
		if (data.getRemarks() != null) {
			master.setRemarks(data.getRemarks());
		}
		if (data.getDocumentId() != null) {
			master.setDocumentId(data.getDocumentId());
		}
		if (data.getDocumentNumber() != null)
			master.setDocumentNumber(data.getDocumentNumber());
		if (data.getValidUpto() != null)
			master.setValidUpto(DateUtil.getVSoftDateByString(data.getValidUpto()));

		if (data.getFile() != null && !data.getFile().trim().isEmpty()) {
			try {
				master.setImageValue(FileUtil.writeFile(data.getFile()));
			} catch (Exception e) {
				e.getMessage();
				e.printStackTrace();

			}
		}

		return master;

	}

	public static PacsLoanEntryDocumentDetailsData getDocumentData(LoanApplicationDocument docMaster) {
		PacsLoanEntryDocumentDetailsData documentDetailsData = new PacsLoanEntryDocumentDetailsData();

		documentDetailsData.setApplicationId(docMaster.getApplicationId());
		documentDetailsData.setDocumentId(docMaster.getDocumentId());

		DocumentData documentData = new DocumentData();
		documentData = KLSServiceFactory.getDocumentService().getDocumentById(
				Integer.valueOf(docMaster.getDocumentId().toString()));
		documentDetailsData.setDocumentName(documentData.getName());

		documentDetailsData.setDocumentNumber(docMaster.getDocumentNumber());
		if (docMaster.getImageValue() != null)
			documentDetailsData.setFile(docMaster.getImageValue());
		documentDetailsData.setId(docMaster.getId());
		if (docMaster.getRemarks() != null)
			documentDetailsData.setRemarks(docMaster.getRemarks());
		if (docMaster.getValidUpto() != null)
			documentDetailsData.setValidUpto(DateUtil.getDateString(docMaster.getValidUpto()));

		return documentDetailsData;
	}

	public static void deleteDocument(String file) {
		try {
			FileUtil.deleteFile(file);
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();

		}
	}
	
private static String getPacsBusinessDate(PacsLoanApplicationData pacsLoanApplicationData){
	String applicationDate="";
	IAccountPropertyDAO accountPropDao=KLSDataAccessFactory.getAccountPropertyDAO();
	AccountProperty property=accountPropDao.getAccountByCustId(pacsLoanApplicationData.getCustomerId());
	
	IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
	PersonData person=RestClientUtil.getCustomerById(pacsLoanApplicationData.getCustomerId());
	Pacs pacs=pacsDao.getPacs(person.getPacsId());
	logger.info("pacs type::"+pacs.getPacsStatus());
	if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
		applicationDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
		
	}else{
		applicationDate="";
	}
	return applicationDate;
}
}