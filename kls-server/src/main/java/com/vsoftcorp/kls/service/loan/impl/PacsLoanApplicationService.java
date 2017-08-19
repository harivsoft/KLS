package com.vsoftcorp.kls.service.loan.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Activity;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationActivityId;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationDocument;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationForActivity;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationForGuarantorDetails;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationGurantorId;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.data.InstallmentFrequencyData;
import com.vsoftcorp.kls.data.LoanApplicationEnumsData;
import com.vsoftcorp.kls.data.LoanApplicationStateData;
import com.vsoftcorp.kls.data.PacsLoanApplicationData;
import com.vsoftcorp.kls.data.PacsLoanEntryActivityData;
import com.vsoftcorp.kls.data.PacsLoanEntryDocumentDetailsData;
import com.vsoftcorp.kls.data.PacsLoanEntryGuarantorDetailsData;
import com.vsoftcorp.kls.data.VillageDetailsData;
import com.vsoftcorp.kls.data.gateway.datahelpers.AddressData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.IActivityDAO;
import com.vsoftcorp.kls.dataaccess.ICustomerDAO;
import com.vsoftcorp.kls.dataaccess.IDocumentDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IPacsLoanApplicationDAO;
import com.vsoftcorp.kls.service.IVillageService;
import com.vsoftcorp.kls.service.account.ILoanAccountPropertyService;
import com.vsoftcorp.kls.service.constants.ServiceConstants;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.exception.PACSRunTimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.PacsLoanApplicationHelper;
import com.vsoftcorp.kls.service.loan.IPacsLoanApplicationService;
import com.vsoftcorp.kls.service.util.FileUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.kls.valuetypes.loanapplication.InstallmentFrequency;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentSchedule;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentType;
import com.vsoftcorp.time.Date;

public class PacsLoanApplicationService implements IPacsLoanApplicationService {

	private static final Logger logger = Logger.getLogger(PacsLoanApplicationService.class);
	private IPacsLoanApplicationDAO pacsDao = KLSDataAccessFactory.getPacsLoanApplicationDAO();

	@Override
	public void saveLoanApplication(PacsLoanApplicationData data) {
		// TODO Auto-generated method stub
		logger.info("Start:Calling   saveLoanApplication() method in PacsLoanApplicationService.. ");
		logger.info("Customer Id " + data.getCustomerId());
		PacsLoanApplication master = PacsLoanApplicationHelper.getPacsLoanApplication(data);
		Long applicationId = null;
		try {
			// For New Application
			if (master != null && data.getId() == null) {
				applicationId = pacsDao.saveLoanApplication(master);

				logger.info("LoanApplication data Saved Successfully : " + applicationId);

				if (applicationId != null) {
					data.setId(applicationId);
					logger.info("Saving LoanApplicationDocument Details");
					saveLoanApplicationActivity(data, applicationId);
					saveLoanApplicationGuarantor(data, applicationId);
					saveLoanApplicationDocument(data, applicationId);

				}

				data.setId(applicationId);
			} else {
				// Updating all Application Data. //For Existing Application
				pacsDao.updatePacsLoanApplication(master);

				if (LoanApplicationState.ENTERED.equals(master.getApplicationStatus())) {
					deleteActivityByApplicationId(data, data.getId());
					deleteGuarantorByApplicationId(data, data.getId());
					deleteDocumentById(data);
					saveLoanApplicationActivity(data, data.getId());
					saveLoanApplicationGuarantor(data, data.getId());
					saveLoanApplicationDocument(data, data.getId());
				}
			}
			data.setApplicationStatus(master.getApplicationStatus().getValue());
		} catch (Exception e) {
			e.getMessage();
			logger.error("Unable to save loanApplication data ..in saveLoanApplication() method..");
			throw new DataAccessException("Unable to save loanApplication data", e.getCause());
		}
		logger.info("END: Successfully Saved LoanApplication data");
	}

	public void saveLoanApplicationActivity(PacsLoanApplicationData data, Long appId) {
		logger.info("Start :Calling saveLoanApplicationActivity() method in PacsLoanApplicationService");
		LoanApplicationForActivity master = null;
		try {
			PacsLoanApplication loanApp = new PacsLoanApplication();
			loanApp.setId(appId);

			logger.info("Activity List size: " + data.getActivtityList().size());

			for (PacsLoanEntryActivityData activityData : data.getActivtityList()) {

				master = new LoanApplicationForActivity();
				LoanApplicationActivityId applicationActivityId = new LoanApplicationActivityId();

				if (data.getId() != null) {
					applicationActivityId.setApplicationId(data.getId());
				} else {
					applicationActivityId.setApplicationId(appId);
				}

				applicationActivityId.setActivityId(activityData.getActivityId());

				master.setLoanApplicationActivityId(applicationActivityId);
				master.setNoOfUnit(activityData.getNoOfUnit());
				master.setRequestedAmount(Money.valueOf(Currency.getInstance("INR"),
						activityData.getRequestedAmount() == null ? BigDecimal.ZERO : activityData.getRequestedAmount()));
				master.setAmountAsPerUnitCost(Money.valueOf(Currency.getInstance("INR"), activityData
						.getAmountAsPerUnitCost() == null ? BigDecimal.ZERO : activityData.getAmountAsPerUnitCost()));
				pacsDao.saveLoanActivity(master);
			}
		} catch (Exception e) {
			e.getMessage();
			logger.error("Unable to save loan Application Activity Data ..in saveLoanApplicationActivity() method..");
			throw new DataAccessException("Unable to save LoanApplication Activity data", e.getCause());
		}
		logger.info("END: Successfully Saved Loan Application Activity data");
	}

	public void saveLoanApplicationGuarantor(PacsLoanApplicationData data, Long appId) {
		logger.info("Start :Calling  saveLoanApplicationGuarantor() method in PacsLoanApplicationService ");
		LoanApplicationForGuarantorDetails master = null;
		try {
			PacsLoanApplication loanApp = new PacsLoanApplication();
			loanApp.setId(appId);

			logger.info("Gurantor List size: " + data.getGuarantorDetails().size());

			for (PacsLoanEntryGuarantorDetailsData guarantorDetailsData : data.getGuarantorDetails()) {
				master = new LoanApplicationForGuarantorDetails();
				LoanApplicationGurantorId applicationGurantorId = new LoanApplicationGurantorId();
				if (data.getId() != null) {
					applicationGurantorId.setApplicationId(data.getId());
				} else {
					applicationGurantorId.setApplicationId(appId);
				}
				applicationGurantorId.setGuarantorId(guarantorDetailsData.getCustomerId());
				master.setLoanApplicationGurantorId(applicationGurantorId);
				pacsDao.saveLoanGuarantor(master);
			}
		} catch (Exception e) {
			e.getMessage();
			logger.error("Unable to save loanApplication data ..in saveLoanApplicationGuarantor() method..");
			throw new DataAccessException("Unable to save Loan Application Guarantor data", e.getCause());
		}
		logger.info("END: Successfully Saved Loan Application Guarantor data");
	}

	@Override
	public List<Map> getAllApplicationsBasedOnCustId(Long customerId, LoanApplicationState applicationStatus) {
		logger.info("Start :Calling  getAllApplicationsBasedOnCustId() method in PacsLoanApplicationService ");
		List<Map> list = new ArrayList<>();
		Map<String, String> map = null;
		List<PacsLoanApplication> applicationslist = new ArrayList<>();
		try {
			applicationslist = pacsDao.getLoanApplicationsByCustomerId(customerId, applicationStatus);
			for (PacsLoanApplication pacsLoanApplication : applicationslist) {
				map = new HashMap<>();
				map.put("applicationNumber", pacsLoanApplication.getId().toString());
				map.put("purpose", pacsLoanApplication.getPurpose().getName());
				map.put("value", pacsLoanApplication.getId().toString() + " : "
						+ pacsLoanApplication.getPurpose().getName());
				list.add(map);
			}

		} catch (Exception e) {

			e.getMessage();
			logger.error("Unable to get loanApplication data by customer id and application status in  getAllApplicationsBasedOnCustId() method..");
			throw new DataAccessException("Error While Getting Application Data", e.getCause());

		}
		logger.info("Successfully Completed Getting Loan Applications Data using getAllApplicationsBasedOnCustId() method ");

		return list;
	}

	@Override
	public PacsLoanApplicationData getLoanApplicationById(Long applicationId) {
		logger.info("Start :Calling  getLoanApplicationById() method in PacsLoanApplicationService ");
 
		PacsLoanApplicationData applicationData = new PacsLoanApplicationData();
		try {
			applicationData = PacsLoanApplicationHelper.getPacsLoanApplicationData(pacsDao
					.getLoanApplicationById(applicationId));
			List<LoanApplicationDocument> loanDocumentsList = pacsDao.getDocumentListByApplicationId(applicationId);
			List<LoanApplicationForActivity> loanActivityList = pacsDao.getActivityListByApplicationId(applicationId);
			List<LoanApplicationForGuarantorDetails> loanGuarantorList = pacsDao
					.getGuarantorListByApplicationId(applicationId);

			if (loanDocumentsList.size() > 0) {
				List<PacsLoanEntryDocumentDetailsData> documentDataList = new ArrayList<PacsLoanEntryDocumentDetailsData>();
				IDocumentDAO dao = KLSDataAccessFactory.getDocumentDAO();
				
				for (LoanApplicationDocument docMaster : loanDocumentsList) {
					PacsLoanEntryDocumentDetailsData docData = new PacsLoanEntryDocumentDetailsData();
					docData = PacsLoanApplicationHelper.getDocumentData(docMaster);
					documentDataList.add(docData);
				}
				logger.info("Successfully Completed Setting Document List" + documentDataList.size());
				applicationData.setDocumentDetails(documentDataList);
			}
			IActivityDAO activityDao = KLSDataAccessFactory.getActivityDAO();
			Activity activity = null;
			if (loanActivityList.size() > 0) {
				List<PacsLoanEntryActivityData> pacsLoanEntryActivityDataList = new ArrayList<PacsLoanEntryActivityData>();
				for (LoanApplicationForActivity loanApplicationForActivity : loanActivityList) {
					PacsLoanEntryActivityData activityData = new PacsLoanEntryActivityData();
					activity = activityDao.getActivityById(loanApplicationForActivity.getLoanApplicationActivityId()
							.getActivityId().intValue(), false);
					activityData.setActivityName(activity.getName());
					if (activity.getUnitCost() != null) {
						activityData.setUnitCost(activity.getUnitCost().toString());
					}
					activityData.setActivityId(loanApplicationForActivity.getLoanApplicationActivityId()
							.getActivityId());
					activityData
							.setAmountAsPerUnitCost(loanApplicationForActivity.getAmountAsPerUnitCost().getAmount());
					activityData.setNoOfUnit(loanApplicationForActivity.getNoOfUnit());
					activityData.setUnitName(activity.getUnit().getName());
					activityData.setRequestedAmount(loanApplicationForActivity.getRequestedAmount().getAmount());
					pacsLoanEntryActivityDataList.add(activityData);
				}
				applicationData.setActivtityList(pacsLoanEntryActivityDataList);
			}
			// Customer customer =null;
			PersonData person = null;
			ICustomerDAO customerDao = KLSDataAccessFactory.getCustomerDAO();
			if (loanGuarantorList.size() > 0) {
				List<PacsLoanEntryGuarantorDetailsData> pacsLoanEntryGuarantorDetailsDataList = new ArrayList<PacsLoanEntryGuarantorDetailsData>();
				for (LoanApplicationForGuarantorDetails loanApplicationForGuarantorDetails : loanGuarantorList) {
					PacsLoanEntryGuarantorDetailsData guarantorDetailsData = new PacsLoanEntryGuarantorDetailsData();
					// customer=customerDao.getCustomer(loanApplicationForGuarantorDetails.getLoanApplicationGurantorId().getGuarantorId());
					person = RestClientUtil.getCustomerById(loanApplicationForGuarantorDetails
							.getLoanApplicationGurantorId().getGuarantorId());
					// guarantorDetailsData.setGurantorName(customer.getName());
					guarantorDetailsData.setGurantorName(person.getName());
					guarantorDetailsData.setCustomerId(loanApplicationForGuarantorDetails
							.getLoanApplicationGurantorId().getGuarantorId());
					guarantorDetailsData.setMemberNumber(person.getMemberNumber());

					List<AddressData> contactDetailList = new ArrayList<AddressData>();
					AddressData contactDetail = new AddressData();

					contactDetailList = person.getContactDetailList();
					contactDetail = contactDetailList.get(0);
					VillageDetailsData villageDetailsData = new VillageDetailsData();
					IVillageService villageMasterService = KLSServiceFactory.getVillageMasterService();
					villageDetailsData = villageMasterService.getVillageDetailsById(contactDetail.getVillageId());
					guarantorDetailsData.setVillage(villageDetailsData.getVillageName());

					guarantorDetailsData.setAnnualIncome(person.getAnnualIncome());
					guarantorDetailsData.setOccupation(person.getOccupationName());
					guarantorDetailsData.setPhoneNumber(contactDetail.getPhoneNo());
					if (guarantorDetailsData.getPhoneNumber() == null) {
						guarantorDetailsData.setPhoneNumber(contactDetail.getMobileNo());
					}
					pacsLoanEntryGuarantorDetailsDataList.add(guarantorDetailsData);
				}
				applicationData.setGuarantorDetails(pacsLoanEntryGuarantorDetailsDataList);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: While getting Loan Application data");
			throw new PACSRunTimeException("Unable to Get Loan Application Data", e.getCause());
		}
		logger.info("END: Successfully Completed  Getting Loan Application Data");

		return applicationData;
	}

	@Override
	public LoanApplicationEnumsData getAllLoanApplicationEnums() {

		logger.info("Start :Calling  getAllLoanApplicationEnums() method in PacsLoanApplicationService ");
		LoanApplicationEnumsData loanApplicationEnumsData = new LoanApplicationEnumsData();
		try {
			List<InstallmentFrequencyData> installmentFrequencyList = new ArrayList<InstallmentFrequencyData>();
			InstallmentFrequency[] installmentFrequencyArray = InstallmentFrequency.values();
			InstallmentFrequencyData installmentFrequencyData = new InstallmentFrequencyData();
			for (int i = 0; i < installmentFrequencyArray.length; i++) {

				installmentFrequencyData.setValue(installmentFrequencyArray[i].name());
				installmentFrequencyData.setId(installmentFrequencyArray[i].getValue());
				installmentFrequencyList.add(installmentFrequencyData);
			}
			loanApplicationEnumsData.setInstallmentFrequency(installmentFrequencyList);
			List<LoanApplicationStateData> loanApplicationStateList = new ArrayList<LoanApplicationStateData>();
			LoanApplicationState[] LoanApplicationStateArray = LoanApplicationState.values();
			LoanApplicationStateData loanApplicationStateData = new LoanApplicationStateData();

			for (int i = 0; i < LoanApplicationStateArray.length; i++) {
				loanApplicationStateData.setValue(LoanApplicationStateArray[i].name());
				loanApplicationStateData.setId(LoanApplicationStateArray[i].getValue());
				loanApplicationStateList.add(loanApplicationStateData);
			}
			loanApplicationEnumsData.setLoanApplicationState(loanApplicationStateList);
		} catch (Exception excp) {
			logger.error(" Exception while populating loan application enums.");
			throw new KlsRuntimeException("Exception while populating loan application enums.");
		}
		logger.info("End: Inside pacs loan application service to get all loan application enums in getAllLoanApplicationEnums()");
		return loanApplicationEnumsData;
	}

	@Override
	public List<Map> getAllRateOfInterest(Integer interestCategoryId, Money amount, Integer months) {
		logger.info("in getAllRateOfInterest() method to get loan applications..---" + amount);
		List<Map> list = new ArrayList<>();
		try {
			list = pacsDao.getRateOfInterest(interestCategoryId, amount, months);
		} catch (Exception e) {
			e.getMessage();
			logger.error("Unable to get data ..in getAllRateOfInterest() method..");
			throw new DataAccessException("Unable to get roi", e.getCause());
		}
		logger.info("successfully got roi list using getAllRateOfInterest() method ");

		return list;
	}

	public void deleteActivityByApplicationId(PacsLoanApplicationData data, Long applicationId) {
		logger.info("Start:Inside deleteActivityByApplicationId() method");
		try {
			if (data.getActivtityDeleteList() != null && data.getActivtityDeleteList().size() > 0) {
				for (PacsLoanEntryActivityData activityData : data.getActivtityDeleteList()) {
					pacsDao.deleteActivityByApplicationId(applicationId, activityData.getActivityId());
				}
			}
		} catch (Exception e) {
			e.getMessage();
			logger.error("Unable to delete Activity in deleteActivityByApplicationId() method..");
			throw new DataAccessException("Unable to delete the activity.", e.getCause());
		}
		logger.info("Successfully delte the Activity in  deleteActivityByApplicationId() method ");
	}

	public void deleteGuarantorByApplicationId(PacsLoanApplicationData data, Long applicationId) {
		logger.info("Start:Inside deleteGuarantorByApplicationId() method");
		try {
			if (data.getGuarantorDeleteDetails() != null && data.getGuarantorDeleteDetails().size() > 0) {
				for (PacsLoanEntryGuarantorDetailsData guarantorDetailsData : data.getGuarantorDeleteDetails()) {
					pacsDao.deleteGuarantorByApplicationId(applicationId, guarantorDetailsData.getCustomerId());
				}
			}
		} catch (Exception e) {
			e.getMessage();
			logger.error("Unable to delete Gurantor in deleteGuarantorByApplicationId() method..");
			throw new DataAccessException("Unable to delete the Guarantor.", e.getCause());
		}
		logger.info("Successfully delte the Gurantor in  deleteGuarantorByApplicationId() method ");
	}

	public void deleteDocumentById(PacsLoanApplicationData data) {
		logger.info("Start:Inside deleteDocumentByApplicationId() method");
		try {
			if (data.getDocumentDeleteDetails() != null && data.getDocumentDeleteDetails().size() > 0) {
				for (PacsLoanEntryDocumentDetailsData documentDetailsData : data.getDocumentDeleteDetails()) {

					pacsDao.deleteDocumentsById(documentDetailsData.getId());
					PacsLoanApplicationHelper.deleteDocument(documentDetailsData.getFile());
				}
			}
		} catch (Exception e) {
			e.getMessage();
			logger.error("Unable to delete Document in deleteDocumentByApplicationId() method..");
			throw new DataAccessException("Unable to delete the Document.", e.getCause());
		}
		logger.info("Successfully delte the Document in  deleteDocumentByApplicationId() method ");
	}

	@Override
	public void saveLoanApplicationDocument(PacsLoanApplicationData data, Long appId) {

		LoanApplicationDocument master = null;

		try {
			logger.info("Document List size: " + data.getDocumentDetails().size());

			if (data.getId() != null) {
				for (PacsLoanEntryDocumentDetailsData docData : data.getDocumentDetails()) {
					master = new LoanApplicationDocument();
					if (docData.getDocumentId() != null && docData.getFile() != null && docData.getId() == null) {
						logger.info("Calling Helper Class To set Data .." + docData.getDocumentId());

						master = PacsLoanApplicationHelper.getApplicationDocument(docData, data.getId());

						logger.info("Setting data to entity  is Completed Successfully ");

						pacsDao.saveLoanDocuments(master);
					}
				}
			}
		} catch (Exception e) {
			e.getMessage();

			logger.error("Unable to save loanApplication document data in saveLoanApplicationDocument() method..");

			throw new DataAccessException("Error While Saving LoanApplication document data", e.getCause());
		}
		logger.info("END: Successfully Saved LoanApplicationDocument data");
	}

	/**
	 * 
	 * @param loanPeriod
	 * @param installmentFrequency
	 * @return
	 */
	public List<Map> getNumOfInstallmentsAndInstallmentAmount(Integer loanPeriod, Integer installmentFrequency,
			Long applicationId) {

		logger.info("Start: Calculating number of installments and installment amount in getNumOfInstallmentsAndInstallmentAmount() method");
		logger.info("loanPeriod : " + loanPeriod);
		logger.info("installmentFrequency : " + installmentFrequency);
		List<Map> list = new ArrayList<>();
		Map map = new HashMap<>();
		Integer numOfInstallments = null;
		String installmentAmount = null;
		if (installmentFrequency != null) {
			InstallmentFrequency installmentFrequencyEnum = InstallmentFrequency.getType(installmentFrequency);
			if (loanPeriod < installmentFrequencyEnum.getValue()) {
				numOfInstallments = 1;
			} else {
				numOfInstallments = (loanPeriod / (installmentFrequencyEnum.getValue()));
			}
			map.put("noOfInstallments", numOfInstallments);
			PacsLoanApplication pacsLoanApplication = pacsDao.getLoanApplicationById(applicationId);
			if (pacsLoanApplication.getSanctionAmount() != null && pacsLoanApplication.getNoOfInstallments() != null) {
				installmentAmount = getInstallmentAmount(pacsLoanApplication, numOfInstallments);
			}
			map.put("installmentAmount", installmentAmount);
			logger.info("map==" + map);
			list.add(map);
		}
		logger.info("numOfInstallments : " + numOfInstallments);
		logger.info("End: Calculating number of installments and installment amount in getNumOfInstallmentsAndInstallmentAmount() method");
		return list;
	}

	private String getInstallmentAmount(PacsLoanApplication pacsLoanApplication, int numOfInstallments) {

		logger.info("Start: Calculating installment amount in getInstallmentAmount() method");
		Product product = pacsLoanApplication.getProduct();
		InterestCategory interestCategory = pacsLoanApplication.getInterestCategory();
		Money installmentAmount = Money.ZERO;
		double reducingFreq = 0;
		String reducingFrequency = null;
		if (product != null && interestCategory != null) {
			RepaymentSchedule repaymentSchedule = product.getRepaymentSchedule();
			logger.info(" repaymentSchedule : " + repaymentSchedule.getValue());
			RepaymentType repaymentType = product.getRepaymentType();
			logger.info(" repaymentType : " + repaymentType.getValue());
			boolean isPrincipal = RepaymentSchedule.PRINCIPAL.equals(repaymentSchedule);
			logger.info(" isPrincipal : " + isPrincipal);
			boolean isEquated = RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
					&& RepaymentType.EQUATED_INSTALLMENTS.equals(repaymentType);
			logger.info(" isEquated : " + isEquated);
			boolean isNonEquated = RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
					&& RepaymentType.NON_EQUATED_INSTALLMENTS.equals(repaymentType);
			logger.info(" isNonEquated : " + isNonEquated);
			Money sanctionAmount = pacsLoanApplication.getSanctionAmount();
			logger.info(" sanctionAmount : " + sanctionAmount);
			ILoanAccountPropertyService service = KLSServiceFactory.getLoanAccountPropertyService();
			Money interestAmount = Money.ZERO;
			Money principalAmount = Money.ZERO;
			if (isPrincipal) {
				principalAmount = service.calculatePrincipalAmount(sanctionAmount, numOfInstallments);
				logger.info(" principalAmount : " + principalAmount);
				installmentAmount = principalAmount;
			} else {
				Date sanctionDate = pacsLoanApplication.getSanctionDate();
				logger.info(" sanctionDate : " + sanctionDate);
				List<SlabwiseInterestRate> interestSlabs = service.getInterestSlabs(interestCategory.getId(),
						sanctionDate);
				BigDecimal interestRate = (service.getInterestRate(interestSlabs, sanctionAmount)).divide(BigDecimal
						.valueOf(100));
				logger.info(" interestRate per annum : " + interestRate);
				double interestRatePerMonth = interestRate.doubleValue() / 12;
				logger.info(" interestRate per month : " + interestRatePerMonth);
				Integer loanPeriod = pacsLoanApplication.getLoanPeriod();
				logger.info(" loanPeriod : " + loanPeriod);
				if (isEquated) {
					installmentAmount = service.calculateInstallmentAmount(sanctionAmount, numOfInstallments,
							interestRatePerMonth);
				}
				if (isNonEquated) {
					principalAmount = service.calculatePrincipalAmount(sanctionAmount, numOfInstallments);
					logger.info(" principalAmount : " + principalAmount);
					reducingFrequency = PropertiesUtil.getRepaymentProperty(ServiceConstants.REDUCING_FREQUENCY);
					reducingFreq = new Double(reducingFrequency).doubleValue();
					logger.info(" reducingFreq : " + reducingFreq);
					logger.info(" interestRate : " + interestRate);
					interestAmount = service.calculateInterestAmount(sanctionAmount, interestRate.doubleValue(),
							reducingFreq);
					logger.info(" interestAmount : " + interestAmount);
					installmentAmount = principalAmount.add(interestAmount);
				}
			}
			logger.info(" installmentAmount : " + installmentAmount);
		}
		logger.info("End: Calculating installment amount in getInstallmentAmount() method");
		return installmentAmount.getAmount().toString();
	}

	@Override
	public String getdocument(String file) {
		String fileData = null;
		try {
			fileData = FileUtil.readFile(file);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return fileData;
	}
	@Override
	public List<Map> getAllApplicationsBasedOnCustId(Long customerId, LoanApplicationState applicationStatus,String applicationType) {
		logger.info("Start :Calling  getAllApplicationsBasedOnCustId() method in PacsLoanApplicationService ");
		List<Map> list = new ArrayList<>();
		Map<String, String> map = null;
		List<PacsLoanApplication> applicationslist = new ArrayList<>();
		try {
			applicationslist = pacsDao.getLoanApplicationsByCustomerId(customerId, applicationStatus,applicationType);
			for (PacsLoanApplication pacsLoanApplication : applicationslist) {
				map = new HashMap<>();
				map.put("applicationNumber", pacsLoanApplication.getId().toString());
				map.put("purpose", pacsLoanApplication.getPurpose().getName());
				map.put("value", pacsLoanApplication.getId().toString() + " : "
						+ pacsLoanApplication.getPurpose().getName());
				list.add(map);
			}

		} catch (Exception e) {

			e.getMessage();
			logger.error("Unable to get loanApplication data by customer id and application status in  getAllApplicationsBasedOnCustId() method..");
			throw new DataAccessException("Error While Getting Application Data", e.getCause());

		}
		logger.info("Successfully Completed Getting Loan Applications Data using getAllApplicationsBasedOnCustId() method ");

		return list;
	}
	
	@Override
	public List<Map> getAllApplicationsBasedOnCustIdAndLoanType(Long customerId, LoanApplicationState applicationStatus,String applicationType, String loanType) {
		logger.info("Start :Calling  getAllApplicationsBasedOnCustIdAndLoanType() method in PacsLoanApplicationService ");
		List<Map> list = new ArrayList<>();
		Map<String, String> map = null;
		List<PacsLoanApplication> applicationslist = new ArrayList<>();
		try {
			applicationslist = pacsDao.getLoanApplicationsByCustomerIdAndLoanType(customerId, applicationStatus,applicationType,loanType);
			for (PacsLoanApplication pacsLoanApplication : applicationslist) {
				map = new HashMap<>();
				map.put("applicationNumber", pacsLoanApplication.getId().toString());
				map.put("purpose", pacsLoanApplication.getPurpose().getName());
				map.put("value", pacsLoanApplication.getId().toString() + " : "
						+ pacsLoanApplication.getPurpose().getName());
				list.add(map);
			}

		} catch (Exception e) {

			e.getMessage();
			logger.error("Unable to get loanApplication data by customer id and application status in  getAllApplicationsBasedOnCustIdAndLoanType() method..");
			throw new DataAccessException("Error While Getting Application Data", e.getCause());

		}
		logger.info("Successfully Completed Getting Loan Applications Data using getAllApplicationsBasedOnCustIdAndLoanType() method ");

		return list;
	}
}