package com.vsoftcorp.kls.service.factory;

import com.vsoftcorp.kls.business.rule.IScaleOfFinanceLoanAmountEligibility;
import com.vsoftcorp.kls.business.rule.impl.ScaleOfFinanceLoanAmountEligibility;
import com.vsoftcorp.kls.loans.gl.service.ILoansGLEntriesService;
import com.vsoftcorp.kls.loans.gl.service.IPacsGLEntryService;
import com.vsoftcorp.kls.loans.gl.service.impl.LoansGLEntriesSerivce;
import com.vsoftcorp.kls.loans.gl.service.impl.PacsGLEntryService;
import com.vsoftcorp.kls.service.IActivityService;
import com.vsoftcorp.kls.service.IBankPacsGlService;
import com.vsoftcorp.kls.service.IBankParameterService;
import com.vsoftcorp.kls.service.IBorrowingProductGlMapping;
import com.vsoftcorp.kls.service.IBorrowingProductService;
import com.vsoftcorp.kls.service.IBorrowingsAccountService;
import com.vsoftcorp.kls.service.IBranchService;
import com.vsoftcorp.kls.service.ICalendarService;
import com.vsoftcorp.kls.service.ICalendarSetupService;
import com.vsoftcorp.kls.service.ICropMasterService;
import com.vsoftcorp.kls.service.ICropTypeMasterService;
import com.vsoftcorp.kls.service.ICustomerService;
import com.vsoftcorp.kls.service.IDayBeginService;
import com.vsoftcorp.kls.service.IDistrictService;
import com.vsoftcorp.kls.service.IDocumentService;
import com.vsoftcorp.kls.service.IInterestCategoryService;
import com.vsoftcorp.kls.service.IKLSOmniIntegrationService;
import com.vsoftcorp.kls.service.IKccCardMappingService;
import com.vsoftcorp.kls.service.ILandDetailService;
import com.vsoftcorp.kls.service.ILandTypeService;
import com.vsoftcorp.kls.service.ILoanProductDocumentMappingService;
import com.vsoftcorp.kls.service.ILoanProductPurposeMappingService;
import com.vsoftcorp.kls.service.ILocClosureService;
import com.vsoftcorp.kls.service.IOfflineDayEnd;
import com.vsoftcorp.kls.service.IPacsGlMappingService;
import com.vsoftcorp.kls.service.IPacsGlService;
import com.vsoftcorp.kls.service.IPacsLoanApplicationDetailService;
import com.vsoftcorp.kls.service.IPacsLoanApplicationHeaderService;
import com.vsoftcorp.kls.service.IPacsService;
import com.vsoftcorp.kls.service.IProductService;
import com.vsoftcorp.kls.service.IProductTypeService;
import com.vsoftcorp.kls.service.IPurposeService;
import com.vsoftcorp.kls.service.IRecoveryOrderService;
import com.vsoftcorp.kls.service.ISanctionedComponentService;
import com.vsoftcorp.kls.service.IScaleOfFinanceService;
import com.vsoftcorp.kls.service.ISchemeService;
import com.vsoftcorp.kls.service.ISeasonMasterService;
import com.vsoftcorp.kls.service.ISeasonParameterService;
import com.vsoftcorp.kls.service.ISeasonService;
import com.vsoftcorp.kls.service.ISlabwiseInterestRateService;
import com.vsoftcorp.kls.service.ISubPurposeService;
import com.vsoftcorp.kls.service.ITalukaService;
import com.vsoftcorp.kls.service.IUnitService;
import com.vsoftcorp.kls.service.IVillageService;
import com.vsoftcorp.kls.service.account.IAccountPropertyService;
import com.vsoftcorp.kls.service.account.IBorrowingAccountPropertyService;
import com.vsoftcorp.kls.service.account.IBorrowingRecoveryEntryService;
import com.vsoftcorp.kls.service.account.IBorrowingsLineOfCreditService;
import com.vsoftcorp.kls.service.account.IChargesDebitService;
import com.vsoftcorp.kls.service.account.IChargesMasterService;
import com.vsoftcorp.kls.service.account.ILoanAccountPropertyService;
import com.vsoftcorp.kls.service.account.ILoanLineOfCreditService;
import com.vsoftcorp.kls.service.account.IProductChargesMappingService;
import com.vsoftcorp.kls.service.account.impl.AccountPropertyService;
import com.vsoftcorp.kls.service.account.impl.BorrowingRecoveryEntryService;
import com.vsoftcorp.kls.service.account.impl.BorrowingsAccountPropertyService;
import com.vsoftcorp.kls.service.account.impl.BorrowingsLineOfCreditService;
import com.vsoftcorp.kls.service.account.impl.ChargesDebitService;
import com.vsoftcorp.kls.service.account.impl.ChargesMasterService;
import com.vsoftcorp.kls.service.account.impl.LoanAccountPropertyService;
import com.vsoftcorp.kls.service.account.impl.LoanLineOfCreditService;
import com.vsoftcorp.kls.service.account.impl.ProductChargesMappingService;
import com.vsoftcorp.kls.service.impl.ActivityService;
import com.vsoftcorp.kls.service.impl.BankPacsGlService;
import com.vsoftcorp.kls.service.impl.BankParameterService;
import com.vsoftcorp.kls.service.impl.BorrowingProductGlMappingService;
import com.vsoftcorp.kls.service.impl.BorrowingProductService;
import com.vsoftcorp.kls.service.impl.BorrowingsAccountService;
import com.vsoftcorp.kls.service.impl.BranchService;
import com.vsoftcorp.kls.service.impl.CalendarService;
import com.vsoftcorp.kls.service.impl.CalendarSetupService;
import com.vsoftcorp.kls.service.impl.CropMasterService;
import com.vsoftcorp.kls.service.impl.CropTypeMasterService;
import com.vsoftcorp.kls.service.impl.CustomerService;
import com.vsoftcorp.kls.service.impl.DayBeginService;
import com.vsoftcorp.kls.service.impl.DistrictService;
import com.vsoftcorp.kls.service.impl.DocumentService;
import com.vsoftcorp.kls.service.impl.InterestCategoryService;
import com.vsoftcorp.kls.service.impl.KLSOmniIntegrationService;
import com.vsoftcorp.kls.service.impl.KccCardMappingService;
import com.vsoftcorp.kls.service.impl.LandDetailService;
import com.vsoftcorp.kls.service.impl.LandTypeService;
import com.vsoftcorp.kls.service.impl.LoanProductDocumentMappingService;
import com.vsoftcorp.kls.service.impl.LoanProductPurposeMappingService;
import com.vsoftcorp.kls.service.impl.LocClosureService;
import com.vsoftcorp.kls.service.impl.OfflineDayEnd;
import com.vsoftcorp.kls.service.impl.PacsGlMappingService;
import com.vsoftcorp.kls.service.impl.PacsGlService;
import com.vsoftcorp.kls.service.impl.PacsLoanApplicationDetailService;
import com.vsoftcorp.kls.service.impl.PacsLoanApplicationHeaderService;
import com.vsoftcorp.kls.service.impl.PacsService;
import com.vsoftcorp.kls.service.impl.ProductService;
import com.vsoftcorp.kls.service.impl.ProductTypeService;
import com.vsoftcorp.kls.service.impl.PurposeService;
import com.vsoftcorp.kls.service.impl.RecoveryOrderService;
import com.vsoftcorp.kls.service.impl.SanctionedComponentService;
import com.vsoftcorp.kls.service.impl.ScaleOfFinanceService;
import com.vsoftcorp.kls.service.impl.SchemeService;
import com.vsoftcorp.kls.service.impl.SeasonMasterService;
import com.vsoftcorp.kls.service.impl.SeasonParameterService;
import com.vsoftcorp.kls.service.impl.SeasonService;
import com.vsoftcorp.kls.service.impl.SlabwiseInterestRateService;
import com.vsoftcorp.kls.service.impl.SubPurposeService;
import com.vsoftcorp.kls.service.impl.TalukaService;
import com.vsoftcorp.kls.service.impl.UnitService;
import com.vsoftcorp.kls.service.impl.VillageService;
import com.vsoftcorp.kls.service.loan.ILoanApplicationRenewalService;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementEntryService;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementScheduleService;
import com.vsoftcorp.kls.service.loan.ILoanDisbursementService;
import com.vsoftcorp.kls.service.loan.ILoanInterestService;
import com.vsoftcorp.kls.service.loan.ILoanRecoveryService;
import com.vsoftcorp.kls.service.loan.ILoanRepaymentScheduleService;
import com.vsoftcorp.kls.service.loan.IPacsLoanApplicationService;
import com.vsoftcorp.kls.service.loan.IPacsLoanSanctionProcessService;
import com.vsoftcorp.kls.service.loan.ITempLoanRepaymentScheduleService;
import com.vsoftcorp.kls.service.loan.impl.LoanApplicationRenewalService;
import com.vsoftcorp.kls.service.loan.impl.LoanDisbursementEntryService;
import com.vsoftcorp.kls.service.loan.impl.LoanDisbursementScheduleService;
import com.vsoftcorp.kls.service.loan.impl.LoanDisbursementService;
import com.vsoftcorp.kls.service.loan.impl.LoanInterestService;
import com.vsoftcorp.kls.service.loan.impl.LoanRecoveryService;
import com.vsoftcorp.kls.service.loan.impl.LoanRepaymentScheduleService;
import com.vsoftcorp.kls.service.loan.impl.PacsLoanApplicationService;
import com.vsoftcorp.kls.service.loan.impl.PacsLoanSanctionProcessService;
import com.vsoftcorp.kls.service.loan.impl.TempLoanRepaymentScheduleService;
import com.vsoftcorp.kls.service.subsidy.IInstituteMasterService;
import com.vsoftcorp.kls.service.subsidy.IInterestSubsidyService;
import com.vsoftcorp.kls.service.subsidy.ISubsidySchemeGlService;
import com.vsoftcorp.kls.service.subsidy.impl.InstituteMasterService;
import com.vsoftcorp.kls.service.subsidy.impl.InterestSubsidyService;
import com.vsoftcorp.kls.service.subsidy.impl.SubsidySchemeGlService;
import com.vsoftcorp.kls.service.transaction.IAccountWiseConsistencyService;
import com.vsoftcorp.kls.service.transaction.IBorrowingTransactionService;
import com.vsoftcorp.kls.service.transaction.ICurrentTransactionService;
import com.vsoftcorp.kls.service.transaction.ISTLoanRecoveryTransactionService;
import com.vsoftcorp.kls.service.transaction.ITransactionHistoryService;
import com.vsoftcorp.kls.service.transaction.impl.AccountWiseConsistencyService;
import com.vsoftcorp.kls.service.transaction.impl.BorrowingTransactionService;
import com.vsoftcorp.kls.service.transaction.impl.CurrentTransactionService;
import com.vsoftcorp.kls.service.transaction.impl.STLoanRecoveryTransactionService;
import com.vsoftcorp.kls.service.transaction.impl.TransactionHistoryService;

/**
 * 
 * @author a9152
 * 
 */
public class KLSServiceFactory {

	private static ICropMasterService cropMasterService;

	private static IVillageService villageService;

	private static ITalukaService talukaService;

	private static IDistrictService districtService;

	private static IPacsGlService pacsGlService;

	private static IAccountWiseConsistencyService accountWiseConsistencyService;

	private static IBankPacsGlService bankPacsGlService;

	private static IPacsService pacsService;

	private static IPacsLoanApplicationDetailService pacsLoanApplicationDetailService;

	private static IPacsLoanApplicationHeaderService pacsLoanApplicationHeaderService;

	private static IPacsLoanSanctionProcessService pacsLoanSanctionProcessService;

	private static IBankParameterService bankParameterService;

	private static IBranchService branchService;

	private static IBorrowingsAccountService borrowingsAccountService;

	private static ICropTypeMasterService cropTypeMasterService;

	private static ISeasonMasterService seasonMasterService;

	private static ISeasonService seasonService;

	private static ISeasonParameterService seasonParameterService;

	private static ILandDetailService landDetailService;

	private static ILandTypeService landTypeService;

	private static IScaleOfFinanceService scaleOfFinanceService;

	private static ISchemeService schemeService;

	private static IInterestCategoryService interestCategoryService;

	private static IProductService productService;

	private static IProductTypeService productTypeService;

	private static ISlabwiseInterestRateService slabwiseInterestRateService;

	private static ICustomerService customerService;

	private static IScaleOfFinanceLoanAmountEligibility scaleOfFinanceLoanAmountEligibility;

	private static ICurrentTransactionService currentTransactionService;

	private static ITransactionHistoryService transactionHistoryService;

	private static IDayBeginService dayBeginService;

	private static IRecoveryOrderService recoveryOrderService;

	private static ILoanApplicationRenewalService loanApplicationRenewalService;

	private static ILocClosureService locClosureService;

	private static ILoansGLEntriesService loansGLEntriesService;

	private static ISanctionedComponentService sanctionedComponentService;

	private static IBorrowingsLineOfCreditService borrowingLineOfCreditService;

	private static IPacsGlMappingService pacsGlMappingService;

	private static IPacsGLEntryService pacsGLEntryService;

	private static IBorrowingProductGlMapping borrowingProductGlMapping;

	private static ILoanInterestService loanInterestService;

	private static IBorrowingTransactionService borrowingTransactionService;

	private static ICalendarSetupService calendarSetupService;

	private static ICalendarService calendarService;

	private static IPurposeService purposeService;

	private static ISubPurposeService subPurposeService;

	private static IActivityService activityService;

	private static IUnitService unitService;

	private static IDocumentService documentService;

	private static ILoanProductDocumentMappingService loanProductDocumentMappingService;

	private static ILoanProductPurposeMappingService loanProductPurposeMappingService;

	private static IPacsLoanApplicationService pacsLoanApplicationService;

	private static ILoanDisbursementScheduleService loanDisbursementScheduleService;

	private static IAccountPropertyService accountPropertyService;

	private static ILoanAccountPropertyService loanAccountPropertyService;

	private static ILoanLineOfCreditService loanLineOfCreditService;

	private static ILoanDisbursementService loanDisbursementService;

	private static ILoanRepaymentScheduleService loanRepaymentScheduleService;

	private static ITempLoanRepaymentScheduleService tempLoanRepaymentScheduleService;

	private static IChargesMasterService chargesMasterService;

	private static IProductChargesMappingService productChargesMappingService;

	private static IChargesDebitService chargesDebitService;

	private static IBorrowingAccountPropertyService borrowingAccountPropertyService;

	private static IBorrowingProductService borrowingProductService;

	private static ILoanRecoveryService loanRecoveryService;

	private static ILoanDisbursementEntryService loanDisbursementEntryService;
	
	private static ISTLoanRecoveryTransactionService stRecoveryTransactionService;
	
	private static IInterestSubsidyService interestSubsidyService;
	
	private static IInstituteMasterService institutemasterService;
	
	private static ISubsidySchemeGlService subsidySchemeGlService;
	
	private static IKLSOmniIntegrationService kLSOmniIntegrationService;
	
	private static IBorrowingRecoveryEntryService borrowingRecoveryEntryService;
	
	private static IKccCardMappingService kccCardMappingService;
	
	private static IOfflineDayEnd offlineDayEnd;
	

	/**
	 * 
	 * @return
	 */
	
	public static IOfflineDayEnd getOfflineDayEnd(){
		return offlineDayEnd == null ? offlineDayEnd =new OfflineDayEnd() :offlineDayEnd;
		 
	 }
	
	public static IKccCardMappingService getKccCardMappingService(){
		return kccCardMappingService == null ? kccCardMappingService =new KccCardMappingService() :kccCardMappingService;
		 
	 }
	
	
	public static IKLSOmniIntegrationService getKLSOmniIntegrationService(){
		return kLSOmniIntegrationService == null ? kLSOmniIntegrationService =new KLSOmniIntegrationService() :kLSOmniIntegrationService;
		 
	 }
	
 public static IInstituteMasterService getInstituteMasterService(){
	return institutemasterService == null ? institutemasterService =new InstituteMasterService() :institutemasterService;
	 
 }

 public static ISubsidySchemeGlService getSubsidySchemeGlService(){
		return subsidySchemeGlService == null ? subsidySchemeGlService =new SubsidySchemeGlService() :subsidySchemeGlService;
		 
	 }


	public static IBorrowingProductGlMapping getBorrowingProductGlMappingService() {
		return borrowingProductGlMapping == null ? borrowingProductGlMapping = new BorrowingProductGlMappingService()
				: borrowingProductGlMapping;
	}

	public static IInterestSubsidyService getInterestSubsidyService() {
		return interestSubsidyService == null ? interestSubsidyService = new InterestSubsidyService() : interestSubsidyService;
	}
	
	public static ICropMasterService getCropMasterService() {
		return cropMasterService == null ? cropMasterService = new CropMasterService() : cropMasterService;
	}

	public static ICropTypeMasterService getCropTypeMasterService() {
		return cropTypeMasterService == null ? cropTypeMasterService = new CropTypeMasterService()
				: cropTypeMasterService;
	}

	/**
	 * 
	 * @return
	 */
	public static IVillageService getVillageMasterService() {
		return villageService == null ? villageService = new VillageService() : villageService;
	}

	/**
	 * 
	 * @return
	 */
	public static ITalukaService getTalukaService() {
		return talukaService == null ? talukaService = new TalukaService() : talukaService;
	}

	/**
	 * 
	 * @return
	 */
	public static IDistrictService getDistrictService() {
		return districtService == null ? districtService = new DistrictService() : districtService;
	}

	/**
	 * 
	 * @return
	 */
	public static IPacsService getPacsService() {
		return pacsService == null ? pacsService = new PacsService() : pacsService;
	}

	/**
	 * 
	 * @return
	 */
	public static IBranchService getBranchMasterService() {
		return branchService == null ? branchService = new BranchService() : branchService;
	}

	public static ISeasonMasterService getSeasonMasterService() {
		return seasonMasterService == null ? seasonMasterService = new SeasonMasterService() : seasonMasterService;
	}

	public static ISeasonService getSeasonService() {
		return seasonService == null ? seasonService = new SeasonService() : seasonService;
	}

	public static ILandTypeService getLandTypeService() {
		return landTypeService == null ? landTypeService = new LandTypeService() : landTypeService;
	}

	/**
	 * 
	 * @return
	 */
	public static IScaleOfFinanceService getScaleOfFinanceService() {
		return scaleOfFinanceService == null ? scaleOfFinanceService = new ScaleOfFinanceService()
				: scaleOfFinanceService;
	}

	public static IPacsGlService getPacsGlService() {
		return pacsGlService == null ? pacsGlService = new PacsGlService() : pacsGlService;
	}

	public static IInterestCategoryService getInterestCategoryMasterService() {
		return interestCategoryService == null ? interestCategoryService = new InterestCategoryService()
				: interestCategoryService;
	}

	public static IProductTypeService getProductTypeService() {
		return productTypeService == null ? productTypeService = new ProductTypeService() : productTypeService;
	}

	/**
	 * 
	 * @return
	 */
	public static ISlabwiseInterestRateService getSlabwiseInterestRateService() {
		return slabwiseInterestRateService == null ? slabwiseInterestRateService = new SlabwiseInterestRateService()
				: slabwiseInterestRateService;
	}

	public static IProductService getProductMasterService() {
		return productService == null ? productService = new ProductService() : productService;
	}

	public static ILandDetailService getLandDetailService() {
		return landDetailService == null ? landDetailService = new LandDetailService() : landDetailService;
	}

	public static ISchemeService getSchemeMasterService() {
		return schemeService == null ? schemeService = new SchemeService() : schemeService;
	}

	public static ICustomerService getCustomerService() {
		return customerService == null ? customerService = new CustomerService() : customerService;
	}

	public static IPacsLoanApplicationHeaderService getPacsLoanApplicationHeaderService() {
		return pacsLoanApplicationHeaderService == null ? pacsLoanApplicationHeaderService = new PacsLoanApplicationHeaderService()
				: pacsLoanApplicationHeaderService;
	}

	public static IPacsLoanApplicationDetailService getPacsLoanApplicationDetailService() {
		return pacsLoanApplicationDetailService == null ? pacsLoanApplicationDetailService = new PacsLoanApplicationDetailService()
				: pacsLoanApplicationDetailService;
	}

	public static IScaleOfFinanceLoanAmountEligibility getIScaleOfFinanceLoanAmountEligibility() {
		return scaleOfFinanceLoanAmountEligibility == null ? scaleOfFinanceLoanAmountEligibility = new ScaleOfFinanceLoanAmountEligibility()
				: scaleOfFinanceLoanAmountEligibility;
	}

	public static IBankParameterService getBankParameterService() {
		return bankParameterService == null ? bankParameterService = new BankParameterService() : bankParameterService;
	}

	public static IPacsLoanSanctionProcessService getPacsLoanSanctionProcessService() {
		return pacsLoanSanctionProcessService == null ? pacsLoanSanctionProcessService = new PacsLoanSanctionProcessService()
				: pacsLoanSanctionProcessService;
	}

	public static ICurrentTransactionService getCurrentTransactionService() {
		return currentTransactionService == null ? currentTransactionService = new CurrentTransactionService()
				: currentTransactionService;
	}

	public static ITransactionHistoryService getTransactionHistoryService() {
		return transactionHistoryService == null ? transactionHistoryService = new TransactionHistoryService()
				: transactionHistoryService;
	}

	public static IDayBeginService getDayBeginService() {
		return dayBeginService == null ? dayBeginService = new DayBeginService() : dayBeginService;

	}

	public static IAccountWiseConsistencyService getAccountWiseConsistencyService() {
		return accountWiseConsistencyService == null ? accountWiseConsistencyService = new AccountWiseConsistencyService()
				: accountWiseConsistencyService;
	}

	public static IRecoveryOrderService getRecoveryOrderService() {
		return recoveryOrderService == null ? recoveryOrderService = new RecoveryOrderService() : recoveryOrderService;
	}

	public static ILoansGLEntriesService getLoansGLEntriesService() {
		return loansGLEntriesService == null ? loansGLEntriesService = new LoansGLEntriesSerivce()
				: loansGLEntriesService;
	}

	public static ILocClosureService getLocClosureService() {
		return locClosureService == null ? locClosureService = new LocClosureService() : locClosureService;
	}

	public static ISanctionedComponentService getSanctionedComponentService() {
		return sanctionedComponentService == null ? sanctionedComponentService = new SanctionedComponentService()
				: sanctionedComponentService;
	}

	public static ILoanApplicationRenewalService getLoanApplicationRenewalService() {
		return loanApplicationRenewalService == null ? loanApplicationRenewalService = new LoanApplicationRenewalService()
				: loanApplicationRenewalService;
	}

	public static IBorrowingsAccountService getBorrowingsAccountService() {
		return borrowingsAccountService == null ? borrowingsAccountService = new BorrowingsAccountService()
				: borrowingsAccountService;
	}

	public static IBankPacsGlService getBankPacsGlService() {
		return bankPacsGlService == null ? bankPacsGlService = new BankPacsGlService() : bankPacsGlService;
	}

	public static ISeasonParameterService getSeasonParameterService() {
		return seasonParameterService == null ? seasonParameterService = new SeasonParameterService()
				: seasonParameterService;
	}

	public static IPacsGlMappingService getPacsGlMappingService() {
		return pacsGlMappingService == null ? pacsGlMappingService = new PacsGlMappingService() : pacsGlMappingService;
	}

	public static IPurposeService getPurposeService() {
		return purposeService == null ? purposeService = new PurposeService() : purposeService;
	}

	public static ISubPurposeService getSubPurposeService() {
		return subPurposeService == null ? subPurposeService = new SubPurposeService() : subPurposeService;
	}

	public static IActivityService getActivityService() {
		return activityService == null ? activityService = new ActivityService() : activityService;
	}

	public static IUnitService getUnitService() {
		return unitService == null ? unitService = new UnitService() : unitService;
	}

	public static IDocumentService getDocumentService() {
		return documentService == null ? documentService = new DocumentService() : documentService;
	}

	public static ILoanProductDocumentMappingService getLoanProductDocumentMappingService() {
		return loanProductDocumentMappingService == null ? loanProductDocumentMappingService = new LoanProductDocumentMappingService()
				: loanProductDocumentMappingService;
	}

	public static ILoanProductPurposeMappingService getLoanProductPurposeMappingService() {
		return loanProductPurposeMappingService == null ? loanProductPurposeMappingService = new LoanProductPurposeMappingService()
				: loanProductPurposeMappingService;
	}

	public static IPacsLoanApplicationService getPacsLoanApplicationService() {
		return pacsLoanApplicationService == null ? pacsLoanApplicationService = new PacsLoanApplicationService()
				: pacsLoanApplicationService;
	}

	public static ILoanDisbursementScheduleService getLoanDisbursementScheduleService() {
		return loanDisbursementScheduleService == null ? loanDisbursementScheduleService = new LoanDisbursementScheduleService()
				: loanDisbursementScheduleService;
	}

	public static IAccountPropertyService getAccountPropertyService() {
		return accountPropertyService == null ? accountPropertyService = new AccountPropertyService()
				: accountPropertyService;
	}

	public static ILoanAccountPropertyService getLoanAccountPropertyService() {
		return loanAccountPropertyService == null ? loanAccountPropertyService = new LoanAccountPropertyService()
				: loanAccountPropertyService;
	}

	public static ILoanLineOfCreditService getLoanLineOfCreditService() {
		return loanLineOfCreditService == null ? loanLineOfCreditService = new LoanLineOfCreditService()
				: loanLineOfCreditService;
	}

	public static ILoanDisbursementService getLoanDisbursementService() {
		return loanDisbursementService == null ? loanDisbursementService = new LoanDisbursementService()
				: loanDisbursementService;
	}

	public static ILoanRepaymentScheduleService getLoanRepaymentScheduleService() {
		return loanRepaymentScheduleService == null ? loanRepaymentScheduleService = new LoanRepaymentScheduleService()
				: loanRepaymentScheduleService;
	}

	public static ITempLoanRepaymentScheduleService getTempLoanRepaymentScheduleService() {
		return tempLoanRepaymentScheduleService == null ? tempLoanRepaymentScheduleService = new TempLoanRepaymentScheduleService()
				: tempLoanRepaymentScheduleService;
	}

	public static IChargesMasterService getChargesMasterService() {
		return chargesMasterService == null ? chargesMasterService = new ChargesMasterService() : chargesMasterService;
	}

	public static IProductChargesMappingService getProductChargesMappingService() {
		return productChargesMappingService == null ? productChargesMappingService = new ProductChargesMappingService()
				: productChargesMappingService;
	}

	public static IChargesDebitService getChargesDebitService() {
		return chargesDebitService == null ? chargesDebitService = new ChargesDebitService() : chargesDebitService;
	}

	public static IBorrowingsLineOfCreditService getBorrowingLineOfCreditService() {
		return borrowingLineOfCreditService == null ? borrowingLineOfCreditService = new BorrowingsLineOfCreditService()
				: borrowingLineOfCreditService;
	}

	public static IBorrowingAccountPropertyService getBorrowingAccountPropertyService() {
		return borrowingAccountPropertyService == null ? borrowingAccountPropertyService = new BorrowingsAccountPropertyService()
				: borrowingAccountPropertyService;
	}

	public static IBorrowingProductService getBorrowingProductService() {
		return borrowingProductService == null ? borrowingProductService = new BorrowingProductService()
				: borrowingProductService;
	}

	public static IPacsGLEntryService getPacsGLEntryService() {
		return pacsGLEntryService == null ? pacsGLEntryService = new PacsGLEntryService() : pacsGLEntryService;
	}

	public static ILoanRecoveryService getLoanRecoveryService() {
		return loanRecoveryService == null ? loanRecoveryService = new LoanRecoveryService() : loanRecoveryService;
	}

	public static ILoanInterestService getLoanInterestService() {
		return loanInterestService == null ? loanInterestService = new LoanInterestService() : loanInterestService;
	}

	public static IBorrowingTransactionService getBorrowingTransactionService() {
		return borrowingTransactionService == null ? borrowingTransactionService = new BorrowingTransactionService()
				: borrowingTransactionService;
	}

	/**
	 * @return the calendarSetupService
	 */
	public static ICalendarSetupService getCalendarSetupService() {
		return calendarSetupService == null ? calendarSetupService = new CalendarSetupService() : calendarSetupService;
	}

	public static ICalendarService getCalendarService() {
		return calendarService == null ? calendarService = new CalendarService() : calendarService;
	}

	public static ILoanDisbursementEntryService getLoanDisbursementEntryService() {
		return loanDisbursementEntryService == null ? loanDisbursementEntryService = new LoanDisbursementEntryService()
				: loanDisbursementEntryService;
	}
	
	public static ISTLoanRecoveryTransactionService getStRecoveryTransactionService() {
		return stRecoveryTransactionService == null ? stRecoveryTransactionService = new STLoanRecoveryTransactionService()
				: stRecoveryTransactionService;
	}
	
	public static IBorrowingRecoveryEntryService getBorrowingRecoveryEntryService() {
		return borrowingRecoveryEntryService == null ? borrowingRecoveryEntryService = new BorrowingRecoveryEntryService()
				: borrowingRecoveryEntryService;
	}

}
