/**
 * 
 */
package com.vsoftcorp.kls.service.account.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.core.banking.data.CoreBankingResponse;
import com.vsoftcorp.core.banking.data.values.BalanceType;
import com.vsoftcorp.core.banking.data.values.RequestType;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.PacsGlMapping;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.account.Charges;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanAccountProperty;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementCompositeId;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementSchedule;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentScheduleId;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.AccountTypeData;
import com.vsoftcorp.kls.data.LoanAccountEnumsData;
import com.vsoftcorp.kls.data.LoanAccountPropertyData;
import com.vsoftcorp.kls.data.LoanLineOfCreditData;
import com.vsoftcorp.kls.data.OperatingInstructionsTypeData;
import com.vsoftcorp.kls.data.OperatorTypeData;
import com.vsoftcorp.kls.data.SanctionAuthorityData;
import com.vsoftcorp.kls.data.TransactionModeEnumsData;
import com.vsoftcorp.kls.data.TransactionModeTypeData;
import com.vsoftcorp.kls.dataaccess.IInterestCategoryDAO;
import com.vsoftcorp.kls.dataaccess.IPacsGlMappingDAO;
import com.vsoftcorp.kls.dataaccess.ISlabwiseInterestRateDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.dataaccess.loan.IAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.dataaccess.loan.IPacsLoanApplicationDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.service.account.ILoanAccountPropertyService;
import com.vsoftcorp.kls.service.constants.ServiceConstants;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.LoanAccountPropertyHelper;
import com.vsoftcorp.kls.service.helper.LoanInterestPostingServiceHelper;
import com.vsoftcorp.kls.service.util.AccountServiceUtil;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.service.util.StringConstant;
import com.vsoftcorp.kls.service.util.SuvikasService;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.kls.valuetypes.account.AccountType;
import com.vsoftcorp.kls.valuetypes.account.OperatingInstructionsType;
import com.vsoftcorp.kls.valuetypes.account.OperatorType;
import com.vsoftcorp.kls.valuetypes.account.SanctionAuthority;
import com.vsoftcorp.kls.valuetypes.charges.ChargeType;
import com.vsoftcorp.kls.valuetypes.loanapplication.InstallmentFrequency;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentSchedule;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentType;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public class LoanAccountPropertyService implements ILoanAccountPropertyService {

	private static final Logger logger = Logger.getLogger(LoanAccountPropertyService.class);

	@Override
	public LoanAccountProperty checkIfAccountExists(Long custId) {

		logger.info("Start: checking the account using cust id in checkIfAccountExists() method.");
		ILoanAccountPropertyDAO dao = KLSDataAccessFactory.getLoanAccountPropertyDAO();
		logger.info(" custId : " + custId);
		LoanAccountProperty loanAcctProperty = null;
		try {
			loanAcctProperty = dao.checkIfAccountExists(custId, false);
		} catch (Exception e) {
			logger.error("Error while retriving the account based on cust id from the database");
		}
		logger.info("End: checking the account using cust id in checkIfAccountExists() method.");
		return loanAcctProperty;
	}

	@Override
	public String saveLoanAccountProperty(LoanAccountPropertyData loanAcctPropertyData) {

		logger.info("Start : Creating loan loan account in saveAccountProperty() method.");
		// get the loan account property dao.
		ILoanAccountPropertyDAO dao = KLSDataAccessFactory.getLoanAccountPropertyDAO();
		LoanAccountProperty loanAccountProperty = LoanAccountPropertyHelper.getLoanAccountProperty(loanAcctPropertyData);
		Map<String, Object> scheduleMap = new HashMap<String, Object>();
		String accountNumber = "";
		LoanLineOfCredit loanLineOfCredit = new LoanLineOfCredit();
		try {
			if (loanAccountProperty.getId() == null) {
				if (checkIfShareAccountExists(loanAcctPropertyData)) {
					if (checkAccount(loanAcctPropertyData, loanAccountProperty)) {
						loanLineOfCredit = createLoanLineOfCredit(loanAccountProperty, loanAcctPropertyData, scheduleMap);
						Account account = loanAccountProperty.getAccount();
						accountNumber = account.getAccountNumber();
						loanLineOfCredit.setAccount(account);
						generateDefaultLoanDisbursementSchedule(scheduleMap, loanLineOfCredit);
						updateLoanApplicationStatus(loanLineOfCredit, loanAcctPropertyData);
						// Generate share and insurance charges records.
						List<Charges> chargesList = generateChargesRecords(loanLineOfCredit, loanAcctPropertyData);
						// Generate repayment schedule.
						//Generate repayment schedule at Loan Disbursement time
//						List<LoanRepaymentSchedule> scheduleList = generateRepaymentSchedule(loanLineOfCredit);
						
						Object object = scheduleMap.get(StringConstant.DEFAULT_DISBURSEMENT_SCHEDULE);
						LoanDisbursementSchedule disbursementSchedule = null;
						if (object instanceof LoanDisbursementSchedule) {
							disbursementSchedule = (LoanDisbursementSchedule) object;
						}
						loanLineOfCredit = dao.saveLoanAccountProperty(loanAccountProperty, loanLineOfCredit, chargesList, null,
								disbursementSchedule, null);
					} else {
						return "Cannot create account.";
					}
				} else {
					return "Cannot create account as share account does not exists for the customer.";
				}
			} else {
				logger.info(" loan account property id : " + loanAccountProperty.getId());
				LoanAccountProperty master = dao.getLoanAccountProperty(loanAccountProperty.getId());
				if (master != null) {
					Account account = master.getAccount();
					accountNumber = account.getAccountNumber();
					ILoanLineOfCreditDAO loanDao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
					LoanLineOfCredit loanLoc = loanDao.getLoanLineOfCreditByPropertyId(master.getId());
					if (loanLoc != null) {
						AccountingMoney currBal = loanLoc.getCurrentBalance();
						logger.info(" currBal : " + currBal);
						if (currBal.isZero()) {
							Charges charge = updateInsuranceCharges(loanAcctPropertyData, loanLoc);
							boolean flag = checkForNewRepaymentSchedule(loanLoc, loanAcctPropertyData);
							modifyLoanApplicationStatus(loanLoc, loanAcctPropertyData);
							updateRemarks(loanAccountProperty, loanAcctPropertyData);
							loanLineOfCredit = dao.updateLoanAccountProperty(loanAccountProperty, charge, loanLoc);
							/*if (loanLineOfCredit != null && flag) {
								ILoanRepaymentScheduleDAO repaymentDao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
								List<LoanRepaymentSchedule> newScheduleList = generateNewRepaymentSchedule(repaymentDao, loanLineOfCredit);
								repaymentDao.saveLoanRepaymentScheduleList(newScheduleList);
							}*/
						} else if (currBal.isDebit()) {
							dao.updateLoanAccountProperty(loanAccountProperty);
						}
					}
					return "Account updated with account number " + accountNumber;
				}
			}
		} catch (Exception excp) {
			logger.error(" Cannot create account");
			if (excp instanceof DataAccessException)
				throw excp;
			throw new KlsRuntimeException("Cannot create account", excp.getCause());
		}
		logger.info("End : Created loan account in saveAccountProperty() method.");
		return "Account created with account number " + accountNumber + " and line of credit id " + loanLineOfCredit.getId();
	}

	/**
	 * 
	 * @param loanLineOfCredit
	 * @param loanAcctPropertyData
	 * @return
	 */
	private boolean checkForNewRepaymentSchedule(LoanLineOfCredit loanLineOfCredit, LoanAccountPropertyData loanAcctPropertyData) {

		logger.info("Start : Check whether to generate new loan repayment schedule list in checkForNewRepaymentSchedule() method.");
		boolean flag = false;
		PacsLoanApplication loanApplication = loanLineOfCredit.getPacsLoanApplication();
		if (loanApplication != null) {
			Integer interestCategoryId = loanAcctPropertyData.getInterestCategoryId();
			if (interestCategoryId.intValue() != loanApplication.getInterestCategory().getId().intValue()) {
				flag = true;
			} else if (loanAcctPropertyData.getLoanPeriod().intValue() != loanApplication.getLoanPeriod().intValue()) {
				flag = true;
			} else if (loanAcctPropertyData.getInstallmentFrequency().intValue() != loanApplication.getInstallmentFrequency().getValue().intValue()) {
				flag = true;
			} else if (loanAcctPropertyData.getMoratoriumPrinciplePeriod().intValue() != loanApplication.getMoratoriumPrinciplePeriod().intValue()) {
				flag = true;
			} else if (loanAcctPropertyData.getMoratoriumInterestPeriod().intValue() != loanApplication.getMoratoriumInterestPeriod().intValue()) {
				flag = true;
			}
		}
		logger.info("End : Check whether to generate new loan repayment schedule list in checkForNewRepaymentSchedule() method.");
		logger.info(" flag : " + flag);
		return flag;
	}

	/**
	 * 
	 * @param repaymentDao
	 * @param loanLoc
	 * @return
	 */
	private List<LoanRepaymentSchedule> generateNewRepaymentSchedule(ILoanRepaymentScheduleDAO repaymentDao, LoanLineOfCredit loanLoc) {

		logger.info("Start : Generating new loan repayment schedule list in generateNewRepaymentSchedule() method.");
		logger.info(" loan loc id : " + loanLoc.getId());
		List<LoanRepaymentSchedule> newScheduleList = new ArrayList<LoanRepaymentSchedule>();
		repaymentDao.deleteLoanRepaymentScheduleByLocId(loanLoc.getId());
//		newScheduleList = generateRepaymentSchedule(loanLoc);
		logger.info("End : Generating new loan repayment schedule list in generateNewRepaymentSchedule() method.");
		return newScheduleList;
	}

	private Charges updateInsuranceCharges(LoanAccountPropertyData loanAcctPropertyData, LoanLineOfCredit loanLoc) {

		logger.info("Start : Updating insurance charge record in updateInsuranceCharges() method.");
		IChargesDAO chargesDao = KLSDataAccessFactory.getChargesDAO();
		Charges charge = null;
		List<Charges> chargesList = chargesDao.getAnyCharges(loanLoc.getId(), ChargeType.INSURANCE);
		if (chargesList != null && !chargesList.isEmpty()) {
			charge = chargesList.get(0);
			String insuranceAmount = loanAcctPropertyData.getInsuranceAmount();
			if (insuranceAmount != null) {
				BigDecimal insuranceAmt = new BigDecimal(insuranceAmount);
				charge.setChargeAmount(MoneyUtil.getAccountingMoney(insuranceAmt).getMoney());
			}
		}
		logger.info("End : Updating insurance charge record in updateInsuranceCharges() method.");
		return charge;
	}

	/**
	 * 
	 * @param loanLoc
	 * @param loanAcctPropertyData
	 */
	private List<Charges> generateChargesRecords(LoanLineOfCredit loanLoc, LoanAccountPropertyData loanAcctPropertyData) {

		logger.info("Start : Generate charges and insurance records in generateChargesRecords() method.");
		PacsLoanApplication loanApplication = loanLoc.getPacsLoanApplication();
		List<Charges> chargesList = new ArrayList<Charges>();
		if (loanApplication != null) {
			Charges charges = createChargeRecord(loanLoc, loanAcctPropertyData, ChargeType.SHARE);
			if (charges != null) {
				chargesList.add(charges);
			}
			charges = createChargeRecord(loanLoc, loanAcctPropertyData, ChargeType.INSURANCE);
			if (charges != null) {
				chargesList.add(charges);
			}
		}
		logger.info("End : Generate charges and insurance records in generateChargesRecords() method.");
		return chargesList;
	}

	/**
	 * 
	 * @param loanLoc
	 */
	private List<LoanRepaymentSchedule> generateRepaymentSchedule(LoanLineOfCredit loanLoc, Money sanctionAmount, Date businessDate, Date firstInstallmentDate , String loanType , Long borrowingLoc) {

		logger.info("Start : Generating default repayment schedule to loan repayment schedule table in generateRepaymentSchedule() method.");
		PacsLoanApplication loanApplication = loanLoc.getPacsLoanApplication();
		Money prevOutstandingAmount = Money.ZERO;
		String reducingFrequency = null;
		double reducingFreq = 0;
		int reducingFreqInt = 0;
		List<LoanRepaymentSchedule> repaymentScheduleList = new ArrayList<LoanRepaymentSchedule>();
		if (loanApplication != null && loanLoc != null) {
			Date sanctionDate = businessDate;
			logger.info(" sanctionDate : " + sanctionDate);
			//Date firstDueDate = loanLoc.getFirstDueDate();
			//logger.info(" firstDueDate : " + firstDueDate);
//			Money sanctionAmount = loanApplication.getSanctionAmount();
			logger.info(" sanctionAmount : " + sanctionAmount);
			Integer numOfInstallments = loanApplication.getNoOfInstallments();
			logger.info(" numOfInstallments : " + numOfInstallments);
			Integer loanPeriod = loanApplication.getLoanPeriod();
			logger.info(" loanPeriod : " + loanPeriod);
			Integer frequencyValue = loanApplication.getInstallmentFrequency().getValue();
			logger.info(" frequencyValue : " + frequencyValue);
			Product product = loanApplication.getProduct();
			InterestCategory interestCategory = loanApplication.getInterestCategory();
			if (product != null && interestCategory != null) {
				RepaymentSchedule repaymentSchedule = product.getRepaymentSchedule();
				logger.info(" repaymentSchedule : " + repaymentSchedule.getValue());
				RepaymentType repaymentType = product.getRepaymentType();
				logger.info(" repaymentType : " + repaymentType.getValue());
				Money principalAmountSum = Money.ZERO;
				Money principalAmount = calculatePrincipalAmount(sanctionAmount, numOfInstallments);
				logger.info(" principalAmount : " + principalAmount);
				Money interestAmount = Money.ZERO;
				
				Integer interestCategoryId = interestCategory.getId();
				if("B".equalsIgnoreCase(loanType)){
				interestCategoryId=	KLSServiceFactory.getBorrowingProductService().getProductById(loanLoc.getProduct().getBorrowingProductId()).getInterestCategoryId();
				}else{
				interestCategoryId =	interestCategory.getId();
				}
				
				List<SlabwiseInterestRate> interestSlabs = getInterestSlabs(interestCategoryId, sanctionDate);
				BigDecimal interestRate = (getInterestRate(interestSlabs, sanctionAmount)).divide(BigDecimal.valueOf(100));
				
				logger.info(" interestRate per annum : " + interestRate);
				double interestRatePerMonth = 0d;
				if(frequencyValue != null) {
					if(frequencyValue == 1) {
						interestRatePerMonth = interestRate.doubleValue() / 12;
					} else if(frequencyValue == 3) {
						interestRatePerMonth = interestRate.doubleValue() / 4;
					} else if(frequencyValue == 6) {
						interestRatePerMonth = interestRate.doubleValue() / 2;
					} else if(frequencyValue == 12) {
						interestRatePerMonth = interestRate.doubleValue() / 1;
					}
				}
				logger.info(" interestRate per month : " + interestRatePerMonth);
				Calendar calendar = Calendar.getInstance();
				double difference = getNoOfMonthsDifference(businessDate,firstInstallmentDate);
				Money additionalInterestAmount = Money.ZERO;
				Integer noOfDays = 0;
				if(difference!=frequencyValue.doubleValue()){
					 businessDate = Date.valueOf(getInstallmentDate(calendar, businessDate, frequencyValue));
					  noOfDays = getNoOfDaysBetweenDates(businessDate,firstInstallmentDate);
					 Money interestAmountForDays = calculateInterestForDays(sanctionAmount,noOfDays,interestRate);
					 additionalInterestAmount = interestAmountForDays.divide(numOfInstallments);
				}
				Money outstandingAmount = sanctionAmount;
				//Date installmentDate = firstDueDate;
				Money installmentAmount = principalAmount.add(interestAmount);
				boolean isEquated = RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
						&& RepaymentType.EQUATED_INSTALLMENTS.equals(repaymentType);
				logger.info(" isEquated : " + isEquated);
				boolean isNonEquated = RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
						&& RepaymentType.NON_EQUATED_INSTALLMENTS.equals(repaymentType);
				logger.info(" isNonEquated : " + isNonEquated);
				if (isEquated) {
					installmentAmount = calculateInstallmentAmount(outstandingAmount, numOfInstallments, interestRatePerMonth);
				} else if (isNonEquated) {
					reducingFrequency = PropertiesUtil.getRepaymentProperty(ServiceConstants.REDUCING_FREQUENCY);
					reducingFreq = new Double(reducingFrequency).doubleValue();
					reducingFreqInt = new Double(reducingFrequency).intValue();
					logger.info(" reducingFreq : " + reducingFreq);
					logger.info(" interestRate : " + interestRate);
				}
				logger.info(" installmentAmount : " + installmentAmount);
				logger.info(" interestAmount : " + interestAmount);
				for (int i = 1; i <= numOfInstallments; i++) {
					LoanRepaymentSchedule loanRepaymentSchedule = new LoanRepaymentSchedule();
					if (isEquated) {
						interestAmount = calculateInterestAmount(outstandingAmount, interestRatePerMonth);
						principalAmount = installmentAmount.subtract(interestAmount);
						logger.info(" principalAmount : " + principalAmount);
						prevOutstandingAmount = outstandingAmount;
						logger.info(" prevOutstandingAmount : " + prevOutstandingAmount);
					} else if (isNonEquated) {
						if ((reducingFreqInt != 0 && (i % reducingFreqInt) == 1) || reducingFreqInt == 1) {
							logger.info(" interestRate per annum reducing frequency : " + interestRate);
							interestAmount = calculateInterestAmount(outstandingAmount, interestRate.doubleValue(), reducingFreq);
						}
						installmentAmount = principalAmount.add(interestAmount);
						logger.info(" installmentAmount reducing frequency : " + installmentAmount);
						prevOutstandingAmount = outstandingAmount;
						logger.info(" prevOutstandingAmount : " + prevOutstandingAmount);
					}
					logger.info(" interestAmount : " + interestAmount);
					if(noOfDays < 0){
						loanRepaymentSchedule.setInterestAmount(interestAmount.subtract(additionalInterestAmount));
					}else{
						loanRepaymentSchedule.setInterestAmount(interestAmount.add(additionalInterestAmount));
					}
					loanRepaymentSchedule.setInstallmentAmount(installmentAmount);
					loanRepaymentSchedule.setInstallmentDate(firstInstallmentDate);
					firstInstallmentDate = Date.valueOf(getInstallmentDate(calendar, firstInstallmentDate, frequencyValue));
					logger.info(" installmentDate : " + firstInstallmentDate);
					outstandingAmount = outstandingAmount.subtract(principalAmount);
					logger.info(" outstandingAmount : " + outstandingAmount);
					loanRepaymentSchedule.setLoanOutstandingAmount(outstandingAmount);
					loanRepaymentSchedule.setPrincipalAmount(principalAmount);
					LoanRepaymentScheduleId id = new LoanRepaymentScheduleId();
					
					if("B".equalsIgnoreCase(loanType)){
					id.setLineOfCreditId(borrowingLoc);	
					}else{
					id.setLineOfCreditId(loanLoc.getId());	
					}
					
					id.setInstallmentNumber(i);
					loanRepaymentSchedule.setLoanRepaymentScheduleId(id);
					if (RepaymentSchedule.PRINCIPAL.equals(repaymentSchedule)) {
						principalAmountSum = principalAmountSum.add(principalAmount);
						logger.info(" principalAmountSum : " + principalAmountSum);
					}
					if (i == numOfInstallments) {
						if (RepaymentSchedule.PRINCIPAL.equals(repaymentSchedule)) {
							Money adjustment = sanctionAmount.subtract(principalAmountSum);
							logger.info(" adjustment : " + adjustment.getAmount());
							if (!adjustment.isZero()) {
								loanRepaymentSchedule.setLoanOutstandingAmount(Money.ZERO);
							}
							principalAmount = principalAmount.add(adjustment);
							logger.info(" principalAmount after adjustment : " + principalAmount);
							installmentAmount = principalAmount.add(interestAmount);
							logger.info(" installmentAmount after adjustment : " + installmentAmount);
							loanRepaymentSchedule.setInstallmentAmount(installmentAmount);
						} else if (isEquated) {
							principalAmount = prevOutstandingAmount;
							logger.info(" principalAmount for the last schedule : " + principalAmount);
							installmentAmount = principalAmount.add(interestAmount);
							logger.info(" installmentAmount for the last schedule : " + installmentAmount);
							loanRepaymentSchedule.setInstallmentAmount(installmentAmount);
							loanRepaymentSchedule.setLoanOutstandingAmount(Money.ZERO);
						} else if (isNonEquated) {
							principalAmount = prevOutstandingAmount;
							logger.info(" principalAmount for the last schedule : " + principalAmount);
							installmentAmount = principalAmount.add(interestAmount);
							logger.info(" installmentAmount for the last schedule : " + installmentAmount);
							loanRepaymentSchedule.setInstallmentAmount(installmentAmount);
							loanRepaymentSchedule.setLoanOutstandingAmount(Money.ZERO);
						}
						loanRepaymentSchedule.setPrincipalAmount(principalAmount);
					}
					repaymentScheduleList.add(loanRepaymentSchedule);
				}
			}
		}
		logger.info("End : Generating default repayment schedule to loan repayment schedule table in generateRepaymentSchedule() method.");
		return repaymentScheduleList;
	}

	/**
	 * 
	 * @param calendar
	 * @param installmentDate
	 * @param frequencyValue
	 * @return
	 */
	private java.util.Date getInstallmentDate(Calendar calendar, Date installmentDate, Integer frequencyValue) {

		logger.info("Start : Generating installment date in getInstallmentDate() method.");
		calendar.setTime(installmentDate.getJavaDate());
		calendar.add(Calendar.MONTH, frequencyValue);
		logger.info(" installment date : " + calendar.getTime());
		logger.info("End : Generating installment date in getInstallmentDate() method.");
		return calendar.getTime();
	}
	/*public static int getNoOfMonthsDifference(Date fromDate, Date toDate) {
	 int noOfMonths = toDate.getMonthOfYear().getValue()- fromDate.getMonthOfYear().getValue();

	return noOfMonths;
	int noOfMonths = 0;
	 int days = getNoOfDaysBetweenDates(fromDate,toDate);
	 if(days/30 >= 1){
		 noOfMonths = new Double(Math.ceil(days/30)).intValue();
	 }
	return noOfMonths;

    }*/
	public static Integer getNoOfMonths(Date dueDate,Date npaDate){
		logger.info("in getMonths");
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(dueDate.getJavaDate());
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(npaDate.getJavaDate());
	
		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		logger.info("end getMonths");
		return diffMonth;
	}
	
	public static double getNoOfMonthsDifference(Date fromDate, Date toDate) {
		double noOfMonths = 0;
		 int days = getNoOfDaysBetweenDates(fromDate,toDate);
		 BigDecimal bd = new BigDecimal(days);
		 
		 noOfMonths = bd.doubleValue()/30;
		 
		return noOfMonths;
		
	}
	
	public static int getNoOfDaysBetweenDates(Date fromDate, Date toDate) {
		/*int noOfDays = 0;
		noOfDays = toDate.subtract(fromDate);
		return noOfDays + 1;*/
		int noOfDays = 0;
		if(fromDate.compareTo(toDate)>0){
		noOfDays = fromDate.subtract(toDate);
		noOfDays = -(noOfDays + 1);
		}else if(fromDate.compareTo(toDate)==0){
			noOfDays = fromDate.subtract(toDate);
			noOfDays = noOfDays + 1;
		}else{
			noOfDays = toDate.subtract(fromDate);
			noOfDays = noOfDays + 1;
		}
		return noOfDays;
	}

	public Money calculateInterestForDays(Money sanctionAmount, Integer noOfDays, BigDecimal interestRate) {

		Money interestAmount = Money.ZERO;
		BigDecimal days = new BigDecimal(noOfDays);
		BigDecimal totalDays = new BigDecimal(365);
		BigDecimal interestAmt = ((sanctionAmount.getAmount()).multiply(interestRate).multiply(days));
		interestAmt = interestAmt.divide(totalDays,2,1);
		interestAmount = MoneyUtil.getAccountingMoney(interestAmt).getMoney();
		return interestAmount;
	}
	/**
	 * 
	 * @param sanctionAmount
	 * @param numOfInstallments
	 * @return
	 */
	public Money calculatePrincipalAmount(Money sanctionAmount, Integer numOfInstallments) {

		logger.info("Start : Principal amount calculation in calculatePrincipalAmount() method.");
		Money principalAmount = Money.ZERO;
		logger.info(" sanctionAmount : " + sanctionAmount);
		logger.info(" numOfInstallments : " + numOfInstallments);
		principalAmount = sanctionAmount.divide(numOfInstallments, RoundingMode.DOWN);
		principalAmount = MoneyUtil.getAccountingMoney(principalAmount.getAmount().setScale(2)).getMoney();
		logger.info(" principalAmount : " + principalAmount);
		logger.info("End : Principal amount calculation in calculatePrincipalAmount() method.");
		return principalAmount;
	}

	/**
	 * 
	 * @param amount
	 * @param interestRate
	 * @return
	 */
	public Money calculateInterestAmount(Money amount, double interestRate) {

		logger.info("Start : Interest amount calculation in calculateInterestAmount() method.");
		Money interestAmount = Money.ZERO;
		logger.info(" amount : " + amount);
		logger.info(" interestRate : " + interestRate);
		BigDecimal bd = new BigDecimal(interestRate);
		BigDecimal interestAmt = ((amount.getAmount()).multiply(bd)).setScale(2, RoundingMode.HALF_UP);
		interestAmount = MoneyUtil.getAccountingMoney(interestAmt).getMoney();
		logger.info(" interestAmount : " + interestAmount);
		logger.info("End : Interest amount calculation in calculateInterestAmount() method.");
		return interestAmount;
	}

	/**
	 * 
	 * @param amount
	 * @param interestRate
	 * @param reducingFrequency
	 * @return
	 */
	public Money calculateInterestAmount(Money amount, double interestRate, double reducingFrequency) {

		logger.info("Start : Interest amount calculation for scenario 3 in calculateInterestAmount() method.");
		Money interestAmount = Money.ZERO;
		logger.info(" amount : " + amount);
		logger.info(" interestRate : " + interestRate);
		logger.info(" reducingFrequency : " + reducingFrequency);
		double intermediateValue = ((amount.getAmount().doubleValue()) * (reducingFrequency / 12) * interestRate);
		double interestAmtPerMonth = intermediateValue / reducingFrequency;
		logger.info(" interestAmtPerMonth : " + interestAmtPerMonth);
		BigDecimal interestAmt = new BigDecimal(interestAmtPerMonth).setScale(2, RoundingMode.HALF_UP);
		interestAmount = MoneyUtil.getAccountingMoney(interestAmt).getMoney();
		logger.info(" interestAmount : " + interestAmount);
		logger.info("End : Interest amount calculation for scenario 3 in calculateInterestAmount() method.");
		return interestAmount;
	}

	/**
	 * 
	 * @param amount
	 * @param loanPeriod
	 * @param interestRate
	 * @return
	 */
	public Money calculateInstallmentAmount(Money amount, Integer loanPeriod, double interestRate) {

		logger.info("Start : Installment amount calculation in calculateInstallmentAmount() method.");
		Money installmentAmount = Money.ZERO;
		logger.info(" amount : " + amount);
		logger.info(" loanPeriod : " + loanPeriod);
		logger.info(" interestRate per month : " + interestRate);
		double numerator = Math.pow((interestRate + 1), loanPeriod.doubleValue());
		logger.info(" numerator : " + numerator);
		double denominator = numerator - 1;
		logger.info(" denominator : " + denominator);
		double intermediateValue1 = (numerator / denominator) * interestRate;
		logger.info(" intermediateValue1 : " + intermediateValue1);
		BigDecimal intermediateValue2 = new BigDecimal(intermediateValue1);
		BigDecimal installmentAmt = ((amount.getAmount()).multiply(intermediateValue2)).setScale(2, RoundingMode.HALF_UP);
		installmentAmount = MoneyUtil.getAccountingMoney(installmentAmt).getMoney();
		logger.info(" installmentAmount : " + installmentAmount);
		logger.info("End : Installment amount calculation in calculateInstallmentAmount() method.");
		return installmentAmount;
	}

	/**
	 * 
	 * @param slabList
	 * @param amount
	 * @return
	 */
	public BigDecimal getInterestRate(List<SlabwiseInterestRate> slabList, Money amount) {

		logger.info("Start : Fetching interest rate from slabwise interest rate in getInterestRate() method.");
		BigDecimal interestRate = null;
		for (SlabwiseInterestRate slab : slabList) {
			Money fromSlab = slab.getFromSlab();
			logger.info("fromSlab : " + fromSlab.getAmount());
			Money toSlab = slab.getToSlab();
			logger.info("toSlab : " + toSlab.getAmount());
			int diff1 = amount.getAmount().compareTo(fromSlab.getAmount());
			logger.info("diff1 : " + diff1);
			int diff2 = toSlab.getAmount().compareTo(amount.getAmount());
			logger.info("diff2 : " + diff2);
			if ((diff1 == 0 || diff1 == 1) && (diff2 == 0 || diff2 == 1)) {
				interestRate = slab.getRoi();
				break;
			}
		}
		logger.info("End : Fetching interest rate from slabwise interest rate in getInterestRate() method.");
		return interestRate;
	}

	/**
	 * 
	 * @param interestCategoryId
	 * @param sanctionDate
	 * @return
	 */
	public List<SlabwiseInterestRate> getInterestSlabs(Integer interestCategoryId, Date sanctionDate) {

		logger.info("Start : Fetching interest rate slabs from slabwise interest rate in getInterestSlabs() method.");
		ISlabwiseInterestRateDAO dao = KLSDataAccessFactory.getSlabwiseInterestRateDAO();
		List<SlabwiseInterestRate> slabList = null;
		try {
			slabList = dao.getSlabwiseInterestRateRecords(interestCategoryId, sanctionDate);
		} catch (Exception excp) {
			logger.error(" Cannot fetch interest rate slabs from slabwise interest rate table");
			throw new KlsRuntimeException("Cannot fetch interest rate slabs from slabwise interest rate table", excp.getCause());
		}
		logger.info("End : Fetching interest rate slabs from slabwise interest rate in getInterestSlabs() method.");
		return slabList;
	}

	/**
	 * 
	 * @param loanLoc
	 * @param loanAcctPropertyData
	 * @param chargeType
	 * @return
	 */
	private Charges createChargeRecord(LoanLineOfCredit loanLoc, LoanAccountPropertyData loanAcctPropertyData, ChargeType chargeType) {

		Charges charges = null;
		String amount = "";
		if (chargeType.equals(ChargeType.SHARE)) {
			amount = loanAcctPropertyData.getShareAmount();
		} else if (chargeType.equals(ChargeType.INSURANCE)) {
			amount = loanAcctPropertyData.getInsuranceAmount();
		}
		if (amount != null && !amount.equals("")) {
			charges = new Charges();
			charges.setAccount(loanLoc.getAccount());
			charges.setChargeAmount(MoneyUtil.getAccountingMoney(new BigDecimal(amount)).getMoney());
			charges.setChargeType(chargeType);
			charges.setLineOfCredit(loanLoc);
			charges.setLoanApplication(loanLoc.getPacsLoanApplication());
		}
		return charges;
	}

	/**
	 * 
	 * @param loanAcctPropertyData
	 * @param loanAccountProperty
	 * @return
	 */
	private boolean checkAccount(LoanAccountPropertyData loanAcctPropertyData, LoanAccountProperty loanAccountProperty) {

		logger.info("Start : check if account exists in checkAccount() method.");
		boolean flag = true;
		try {
			AccountProperty acctPropertyFromDb = checkIfAccountPropertyExists(loanAcctPropertyData.getCustomerId());
			if (acctPropertyFromDb != null) {
				IAccountDAO acctDao = KLSDataAccessFactory.getAccountDAO();
				Account account = acctDao.getAccount(acctPropertyFromDb.getId(), false);
				if (account != null) {
					account.setAccountProperty(acctPropertyFromDb);
					loanAccountProperty.setAccount(account);
				} else {
					flag = false;
				}
			} else {
				generateAccountNumber(loanAccountProperty);
			}
		} catch (Exception excp) {
			flag = false;
		}
		logger.info("flag : " + flag);
		logger.info("End : check if account exists in checkAccount() method.");
		return flag;
	}

	/**
	 * 
	 * @param loanAcctPropertyData
	 * @return
	 */
	private boolean checkIfShareAccountExists(LoanAccountPropertyData loanAcctPropertyData) {

		logger.info("Start : check if share account exists in checkIfShareAccountExists() method.");
		boolean flag = false;
		try {
			flag = RestClientUtil.checkIfShareAccountExists(loanAcctPropertyData.getCustomerId());
		} catch (Exception excp) {
			flag = false;
		}
		logger.info("flag : " + flag);
		logger.info("End : check if share account exists in checkIfShareAccountExists() method.");
		return flag;
	}

	/**
	 * 
	 * @param loanLoc
	 */
	private void updateLoanApplicationStatus(LoanLineOfCredit loanLoc, LoanAccountPropertyData loanAcctPropertyData) {

		logger.info("Start : Update the loan application status to account created in updateLoanApplicationStatus() method.");
		PacsLoanApplication loanApplication = loanLoc.getPacsLoanApplication();
		if (loanApplication != null) {
			loanApplication.setApplicationStatus(LoanApplicationState.ACCOUNT_CREATED);
			loanApplication.setLoanPeriod(loanAcctPropertyData.getLoanPeriod());
			Integer installmentFrequency = loanAcctPropertyData.getInstallmentFrequency();
			if (installmentFrequency != null) {
				loanApplication.setInstallmentFrequency(InstallmentFrequency.getType(installmentFrequency));
			}
			loanApplication.setMoratoriumPrinciplePeriod(loanAcctPropertyData.getMoratoriumPrinciplePeriod());
			loanApplication.setMoratoriumInterestPeriod(loanAcctPropertyData.getMoratoriumInterestPeriod());
			loanApplication.setNoOfInstallments(loanAcctPropertyData.getNoOfInstallments());
		}
		loanLoc.setPacsLoanApplication(loanApplication);
		logger.info("End : Updated the loan application status to account created in updateLoanApplicationStatus() method.");
	}

	/**
	 * 
	 * @param master
	 * @param loanAcctPropertyData
	 */
	private void updateRemarks(LoanAccountProperty master, LoanAccountPropertyData loanAcctPropertyData) {

		logger.info("Start : Update the remarks in updateRemarks() method.");
		master.setRemarks(loanAcctPropertyData.getRemarks());
		logger.info("End : Update the remarks in updateRemarks() method.");
	}

	/**
	 * 
	 * @param loanLoc
	 */
	private void modifyLoanApplicationStatus(LoanLineOfCredit loanLoc, LoanAccountPropertyData loanAcctPropertyData) {

		logger.info("Start : Update the loan application status to account created in updateLoanApplicationStatus() method.");
		PacsLoanApplication loanApplication = loanLoc.getPacsLoanApplication();
		if (loanApplication != null) {
			Integer interestCategoryId = loanAcctPropertyData.getInterestCategoryId();
			if (interestCategoryId != null) {
				IInterestCategoryDAO iDao = KLSDataAccessFactory.getInterestCategoryDAO();
				InterestCategory category = iDao.getInterestCategoryById(interestCategoryId);
				if (category != null) {
					loanApplication.setInterestCategory(category);
				}
			}
			loanApplication.setLoanPeriod(loanAcctPropertyData.getLoanPeriod());
			Integer installmentFrequency = loanAcctPropertyData.getInstallmentFrequency();
			if (installmentFrequency != null) {
				loanApplication.setInstallmentFrequency(InstallmentFrequency.getType(installmentFrequency));
			}
			loanApplication.setMoratoriumPrinciplePeriod(loanAcctPropertyData.getMoratoriumPrinciplePeriod());
			loanApplication.setMoratoriumInterestPeriod(loanAcctPropertyData.getMoratoriumInterestPeriod());
			loanApplication.setNoOfInstallments(loanAcctPropertyData.getNoOfInstallments());
		}
		loanLoc.setPacsLoanApplication(loanApplication);
		logger.info("End : Updated the loan application status to account created in updateLoanApplicationStatus() method.");
	}

	/**
	 * 
	 * @param loanAccountProperty
	 * @param loanAcctPropertyData
	 * @param scheduleMap
	 * @return
	 */
	private LoanLineOfCredit createLoanLineOfCredit(LoanAccountProperty loanAccountProperty, LoanAccountPropertyData loanAcctPropertyData,
			Map<String, Object> scheduleMap) {

		logger.info("Start : creating new loan line of credit record in createLoanLineOfCredit() method.");
		LoanLineOfCredit loanLineOfCredit = new LoanLineOfCredit();
		loanLineOfCredit.setAccount(loanAccountProperty.getAccount());
		loanLineOfCredit.setCrop(null);
		loanLineOfCredit.setCurrentBalance(AccountingMoney.ZERO);
		loanLineOfCredit.setCustomerId(loanAccountProperty.getAccount().getAccountProperty().getCustomerId());
		loanLineOfCredit.setDrawalPriority(null);
		loanLineOfCredit.setProduct(loanAccountProperty.getProduct());
		/*String firstDueDate = loanAcctPropertyData.getFirstDueDate();
		if (firstDueDate != null) {
			loanLineOfCredit.setFirstDueDate(DateUtil.getVSoftDateByString(firstDueDate));
		}*/
		Long loanApplicationId = loanAcctPropertyData.getLoanApplicationId();
		IPacsLoanApplicationDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDAO();
		PacsLoanApplication loanApplication = dao.getLoanApplicationById(loanApplicationId);
		if (loanApplication != null) {
			loanLineOfCredit.setInterestCategory(loanApplication.getInterestCategory());
			loanLineOfCredit.setSanctionedAmount(loanApplication.getSanctionAmount());
			loanLineOfCredit.setSanctionedDate(loanApplication.getSanctionDate());
			generateDefaultDisbursementSchedule(loanApplication, scheduleMap);
		}
		loanLineOfCredit.setInterestReceivable(BigDecimal.ZERO);
		loanLineOfCredit.setIsFirstDrawal(null);
		loanLineOfCredit.setKindBalance(AccountingMoney.ZERO);
		loanLineOfCredit.setKindLimit(null);
		loanLineOfCredit.setLastInterestCalculatedDate(null);
		loanLineOfCredit.setLineOfCreditStatus(LineOfCreditStatus.Active);
		/*String loanExpiryDate = loanAcctPropertyData.getLoanExpiryDate();
		loanLineOfCredit.setLoanExpiryDate(DateUtil.getVSoftDateByString(loanExpiryDate));*/
		loanLineOfCredit.setOperatingCashLimit(Money.ZERO);
		loanLineOfCredit.setOverdueInterest(BigDecimal.ZERO);
		loanLineOfCredit.setPacsLoanApplication(loanApplication);
		loanLineOfCredit.setPenalInterestReceivable(BigDecimal.ZERO);
		loanLineOfCredit.setInterestAccrued(BigDecimal.ZERO);
		loanLineOfCredit.setScheme(null);
		loanLineOfCredit.setSeason(null);

		loanLineOfCredit.setLoanAccountProperty(loanAccountProperty);
		logger.info("End : creating new loan line of credit record in createLoanLineOfCredit() method.");
		return loanLineOfCredit;
	}

	@Override
	public LoanAccountEnumsData getAllLoanAccountEnums() {

		logger.info("Start: Inside loan account service to get all loan account enums in getAllLoanAccountEnums()");
		LoanAccountEnumsData loanAcccountEnumsData = new LoanAccountEnumsData();
		try {
			List<AccountTypeData> accountTypeList = new ArrayList<AccountTypeData>();
			AccountType[] accountTypeArray = AccountType.values();
			for (int i = 0; i < accountTypeArray.length; i++) {
				AccountTypeData accountTypeData = new AccountTypeData();
				accountTypeData.setValue(accountTypeArray[i].name());
				accountTypeData.setId(accountTypeArray[i].getValue());
				accountTypeList.add(accountTypeData);
			}
			loanAcccountEnumsData.setAccountType(accountTypeList);
			List<OperatorTypeData> operatorTypeDataList = new ArrayList<OperatorTypeData>();
			OperatorType[] operatorTypeArray = OperatorType.values();
			for (int i = 0; i < operatorTypeArray.length; i++) {
				OperatorTypeData operatorTypeData = new OperatorTypeData();
				operatorTypeData.setValue(operatorTypeArray[i].name());
				operatorTypeData.setId(operatorTypeArray[i].getValue());
				operatorTypeDataList.add(operatorTypeData);
			}
			loanAcccountEnumsData.setOperatorType(operatorTypeDataList);
			List<OperatingInstructionsTypeData> operatingInsTypeDataList = new ArrayList<OperatingInstructionsTypeData>();
			OperatingInstructionsType[] operatingInsTypeArray = OperatingInstructionsType.values();
			for (int i = 0; i < operatingInsTypeArray.length; i++) {
				OperatingInstructionsTypeData operatingInsTypeData = new OperatingInstructionsTypeData();
				operatingInsTypeData.setValue(operatingInsTypeArray[i].name());
				operatingInsTypeData.setId(operatingInsTypeArray[i].getValue());
				operatingInsTypeDataList.add(operatingInsTypeData);
			}
			loanAcccountEnumsData.setOperatingInstructionsType(operatingInsTypeDataList);
			List<SanctionAuthorityData> sanctionAuthorityDataList = new ArrayList<SanctionAuthorityData>();
			SanctionAuthority[] sanctionAuthoritiesArray = SanctionAuthority.values();
			for (int i = 0; i < sanctionAuthoritiesArray.length; i++) {
				SanctionAuthorityData sanctionAuthorityData = new SanctionAuthorityData();
				sanctionAuthorityData.setValue(sanctionAuthoritiesArray[i].name());
				sanctionAuthorityData.setId(sanctionAuthoritiesArray[i].getValue());
				sanctionAuthorityDataList.add(sanctionAuthorityData);
			}
			loanAcccountEnumsData.setSanctionAuthority(sanctionAuthorityDataList);
		} catch (Exception excp) {
			logger.error(" Exception while populating loan account enums.");
			throw new KlsRuntimeException("Exception while populating loan account enums.");
		}
		logger.info("End: Inside loan account service to get all loan account enums in getAllLoanAccountEnums()");
		return loanAcccountEnumsData;
	}

	/**
	 * 
	 * @param loanAccountProperty
	 */
	private void generateAccountNumber(LoanAccountProperty loanAccountProperty) {

		logger.info("Start: Creating account number in createAccountNumber() method");
		if (loanAccountProperty != null) {
			String branchId = loanAccountProperty.getAccount().getAccountProperty().getBranch().getId().toString();
			String pacsId = loanAccountProperty.getAccount().getAccountProperty().getPacs().getId().toString();
			String finalAccountNumber = AccountServiceUtil.generateAccountNumber(branchId, pacsId, null);
			Account account = loanAccountProperty.getAccount();
			account.setAccountNumber(finalAccountNumber);
			account.setAccountProperty(loanAccountProperty.getAccount().getAccountProperty());
			loanAccountProperty.setAccount(account);
		}
		logger.info("End: Created account number in createAccountNumber() method");
	}

	/**
	 * 
	 * @param loanApplication
	 * @param loanAccountProperty
	 * @param scheduleMap
	 */
	private void generateDefaultDisbursementSchedule(PacsLoanApplication loanApplication, Map<String, Object> scheduleMap) {

		logger.info("Start: Generating default loan disbursement schedule in generateDefaultDisbursementSchedule() method");
		LoanDisbursementSchedule disbSchedule = new LoanDisbursementSchedule();
		disbSchedule.setDisbursedAmount(Money.ZERO);
		disbSchedule.setDisbursementAmount(loanApplication.getSanctionAmount());
		disbSchedule.setDisbursementDate(loanApplication.getSanctionDate());
		disbSchedule.setRemainingBalance(Money.ZERO);
		scheduleMap.put(StringConstant.DEFAULT_DISBURSEMENT_SCHEDULE, disbSchedule);
		logger.info("End: Generating default loan disbursement schedule in generateDefaultDisbursementSchedule() method");
	}

	/**
	 * 
	 * @param scheduleMap
	 * @param loanLoc
	 */
	private void generateDefaultLoanDisbursementSchedule(Map<String, Object> scheduleMap, LoanLineOfCredit loanLoc) {

		logger.info("Start: Generating default loan disbursement schedule in generateDefaultLoanDisbursementSchedule() method");
		if (!scheduleMap.isEmpty()) {
			Object object = scheduleMap.get(StringConstant.DEFAULT_DISBURSEMENT_SCHEDULE);
			if (object instanceof LoanDisbursementSchedule) {
				LoanDisbursementSchedule disbSchedule = (LoanDisbursementSchedule) object;
				LoanDisbursementCompositeId compositeId = new LoanDisbursementCompositeId();
				compositeId.setNoOfDisbursement(1);
				disbSchedule.setLoanDisbursmentCompositeId(compositeId);
			}
		}
		logger.info("End: Generating default loan disbursement schedule in generateDefaultLoanDisbursementSchedule() method");
	}

	@Override
	public TransactionModeEnumsData getTransactionModeEnums() {

		logger.info("Start: Inside loan account property service to get all transaction mode enums in getTransactionModeEnums()");
		TransactionModeEnumsData transanctionModeEnumsData = new TransactionModeEnumsData();
		try {
			List<TransactionModeTypeData> transactionModeList = new ArrayList<TransactionModeTypeData>();
			TransactionMode[] transactionModeArray = TransactionMode.values();
			for (int i = 0; i < transactionModeArray.length; i++) {
				TransactionModeTypeData tranModeTypeData = new TransactionModeTypeData();
				tranModeTypeData.setValue(transactionModeArray[i].name());
				tranModeTypeData.setId(transactionModeArray[i].getValue());
				transactionModeList.add(tranModeTypeData);
			}
			transanctionModeEnumsData.setTransanctionMode(transactionModeList);
		} catch (Exception excp) {
			logger.error(" Exception while populating transaction mode enums.");
			throw new KlsRuntimeException("Exception while populating transaction mode enums.");
		}
		logger.info("End: Inside loan account property service to get all transaction mode enums in getTransactionModeEnums()");
		return transanctionModeEnumsData;
	}

	@Override
	public String closeAccount(LoanLineOfCreditData loanLocData) {

		logger.info("Start: Inside loan account property service to close account in closeAccount()");
		ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		logger.info(" loan loc id : " + loanLocData.getId());
		String voucherNumber = "";
		try {
			LoanLineOfCredit loanLoc = dao.getLoanLineOfCreditById(loanLocData.getId());
			String transactionMode = loanLocData.getClosureData().getTransactionMode();
			TransactionMode transMode = null;
			if (transactionMode != null) {
				transMode = TransactionMode.getType(transactionMode);
			}
			logger.info(" transaction mode : " + transMode);
			String transactionAmount = loanLocData.getClosureData().getTransactionAmount();
			AccountingMoney transAmount = AccountingMoney.ZERO;
			if (transactionAmount != null) {
				transAmount = MoneyUtil.getAccountingMoney(new BigDecimal(transactionAmount));
			}
			logger.info(" transaction amount : " + transAmount);
			boolean isCashMode = TransactionMode.Cash.equals(transMode);
			if (isCashMode) {
				String cashGl = loanLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs().getCashGl();
				if (cashGl != null) {
					voucherNumber = closeLoanAccount(dao, loanLocData, loanLoc, cashGl);
				} else {
					return "Cash GL is not found.Loan Account cannot be Closed.";
				}
			} else {
				LoanAccountProperty loanAccountProperty = loanLoc.getLoanAccountProperty();
				logger.info(" Savings account number : " + loanAccountProperty.getAccount().getAccountProperty().getSavingsAccountNumber());
				if (loanAccountProperty.getAccount().getAccountProperty().getSavingsAccountNumber() != null) {
					CoreBankingResponse response = SuvikasService.checkAccountAndBalance(loanAccountProperty.getAccount().getAccountProperty()
							.getSavingsAccountNumber(), RequestType.BalInq);
					if (response != null) {
						AccountingMoney savingsBalance = response.getBalances().get(BalanceType.Avail);
						logger.info(" savingsBalance : " + savingsBalance);
						if (!transAmount.isZero() && transAmount.isCredit() && (transAmount.compareTo(savingsBalance) <= 0)) {
							Integer branchId = loanAccountProperty.getAccount().getAccountProperty().getPacs().getBranch().getId();
							logger.info(" branchId : " + branchId);
							String transactionResponse = SuvikasService.postSuvikasTransaction(loanAccountProperty.getAccount().getAccountProperty()
									.getSavingsAccountNumber(), RequestType.DebitTxn, branchId, transAmount, "towards closure");
							if (transactionResponse.equals("0")) {
								voucherNumber = closeLoanAccount(dao, loanLocData, loanLoc, null);
							} else {
								return "Suvikas Transaction Posting was not successful.Loan Account cannot be Closed.";
							}
						} else {
							return "Transaction Amount is zero or greater than Savings Balance Amount.Loan Account cannot be Closed.";
						}
					} else {
						return "Savings Balance cannot be fetched.Loan Account cannot be Closed.";
					}
				} else {
					return "Savings Account Number does not exists.Loan Account cannot be Closed.";
				}
			}
		} catch (Exception excp) {
			logger.error(" Exception while closing the account.");
			throw new KlsRuntimeException("Exception while closing the account.");
		}
		if (voucherNumber.equals("-1")) {
			return "Closing charges GL is not found.";
		}
		logger.info("End: Inside loan account property service to close account in closeAccount()");
		return "Loan Account Closed Successfully. Voucher Number : " + voucherNumber;
	}

	private void updateLocAmounts(LoanLineOfCreditData loanLocData, LoanLineOfCredit loanLoc) {

		logger.info("Start: Updating the line of credit amounts in updateLocAmounts()");
		String principalString = loanLocData.getClosureData().getLoanOutstandingAmount();
		if (principalString != null) {
			BigDecimal principal = new BigDecimal(principalString);
			AccountingMoney principalAmount = MoneyUtil.getAccountingMoney(principal);
			logger.info(" principalAmount : " + principalAmount);
			AccountingMoney currentBalance = loanLoc.getCurrentBalance();
			logger.info(" currentBalance : " + currentBalance);
			AccountingMoney updatedCurrentBalance = currentBalance.add(principalAmount);
			logger.info(" newCurrentBalance : " + updatedCurrentBalance);
			loanLoc.setCurrentBalance(updatedCurrentBalance);
		}
		String interestString = loanLocData.getClosureData().getInterestReceivable();
		if (interestString != null) {
			BigDecimal interestAmount = new BigDecimal(interestString);
			logger.info(" interestAmount : " + interestAmount);
			BigDecimal interestReceivable = loanLoc.getInterestReceivable();
			logger.info(" interestReceivable : " + interestReceivable);
			BigDecimal updatedInterestReceivable = interestReceivable.add(interestAmount);
			logger.info(" updatedInterestReceivable : " + updatedInterestReceivable);
			loanLoc.setInterestReceivable(updatedInterestReceivable);
		}
		String penalInterestString = loanLocData.getClosureData().getPenalInterest();
		if (penalInterestString != null) {
			BigDecimal penalInterest = new BigDecimal(penalInterestString);
			logger.info(" penalInterest : " + penalInterest);
			BigDecimal penalInterestReceivable = loanLoc.getPenalInterestReceivable();
			logger.info(" penalInterestReceivable : " + penalInterestReceivable);
			BigDecimal updatedPenalInterest = penalInterestReceivable.add(penalInterest);
			logger.info(" updatedPenalInterest : " + updatedPenalInterest);
			loanLoc.setPenalInterestReceivable(updatedPenalInterest);
		}
		String chargesString = loanLocData.getClosureData().getOtherCharges();
		if (chargesString != null) {
			BigDecimal charges = new BigDecimal(chargesString);
			logger.info(" charges : " + charges);
			BigDecimal chargesReceivable = loanLoc.getChargesReceivable();
			logger.info(" chargesReceivable : " + chargesReceivable);
			BigDecimal updatedChargesReceivable = chargesReceivable.add(charges);
			logger.info(" updatedChargesReceivable : " + updatedChargesReceivable);
			loanLoc.setChargesReceivable(updatedChargesReceivable);
		}
		logger.info("End: Updating the line of credit amounts in updateLocAmounts()");
	}

	/**
	 * 
	 * @param loanLocData
	 * @param loanLoc
	 */
	private String populateClosureCurrentTransactionRecords(LoanLineOfCreditData loanLocData, LoanLineOfCredit loanLoc, Account account,
			List<CurrentTransaction> currentTransactionList, String cashGl) {

		logger.info("Start: Populating the closure current transaction records in populateClosureCurrentTransactionRecords()");
		String accountClosingDate = loanLocData.getClosureData().getAccountClosingDate();
		Integer pacsId = loanLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs().getId();
		logger.info("pacsId : " + pacsId);
		Integer voucherNumber = -1;
		String voucher = null;
		Date businessDate = null;
		Long loanLocId = loanLoc.getId();
		if (accountClosingDate != null) {
			businessDate = DateUtil.getVSoftDateByString(accountClosingDate);
		} else {
			businessDate = Date.now();
		}
		AccountingMoney transactionAmount = AccountingMoney.ZERO;
		String totalDues = loanLocData.getClosureData().getTotalDues();
		if (totalDues != null) {
			transactionAmount = MoneyUtil.getAccountingMoney(new BigDecimal(totalDues));
		}
		Integer credit = Integer.parseInt("1");
		Integer debit = Integer.parseInt("-1");
		TransactionType transactionType = null;
		if (cashGl != null) {
			transactionType = TransactionType.Deposit;
		} else {
			transactionType = TransactionType.Transfer;
		}
		voucherNumber = VoucherNumberUtil.getVoucherNumber(pacsId.toString(), transactionType.getValue());
		voucher = transactionType.getValue() + "-" + voucherNumber;
		logger.info("voucherNumber : " + voucherNumber);
		if (cashGl != null) {
			populateCurrentTransactionRecord(loanLocData, loanLoc, account, currentTransactionList, businessDate, TransactionCode.CASH_TRANSFER,
					transactionAmount, "cash gl trasaction for closure loc no. " + loanLocId, debit, cashGl, true, voucher, transactionType);
		}
		populateCurrentTransactionRecord(loanLocData, loanLoc, account, currentTransactionList, businessDate, TransactionCode.CLOSURE,
				transactionAmount, "closure loc no. " + loanLocId, credit, account.getAccountNumber(), false, voucher, transactionType);
		populateCurrentTransactionRecord(loanLocData, loanLoc, account, currentTransactionList, businessDate, TransactionCode.CLOSURE,
				transactionAmount, "closure loc no. " + loanLocId, debit, account.getAccountNumber(), false, voucher, TransactionType.Transfer);
		String principalAmt = loanLocData.getClosureData().getLoanOutstandingAmount();
		if (principalAmt != null) {
			transactionAmount = MoneyUtil.getAccountingMoney(new BigDecimal(principalAmt));
		}
		if (!transactionAmount.isZero()) {
			populateCurrentTransactionRecord(loanLocData, loanLoc, account, currentTransactionList, businessDate, TransactionCode.PRINCIPAL_BAL,
					transactionAmount, "closure loc no. " + loanLocId + " towards principal", credit, account.getAccountNumber(), false, voucher,
					TransactionType.Transfer);
		}
		String interestDue = loanLocData.getClosureData().getInterestDue();
		if (interestDue != null) {
			transactionAmount = MoneyUtil.getAccountingMoney(new BigDecimal(interestDue));
		}
		String interestReceivable = loanLocData.getClosureData().getInterestReceivable();
		if (interestReceivable != null) {
			AccountingMoney intReceivable = MoneyUtil.getAccountingMoney(new BigDecimal(interestReceivable));
			transactionAmount = transactionAmount.add(intReceivable);
		}
		if (!transactionAmount.isZero()) {
			populateCurrentTransactionRecord(loanLocData, loanLoc, account, currentTransactionList, businessDate, TransactionCode.INTEREST,
					transactionAmount, "closure loc no. " + loanLocId + " towards interest", credit, account.getAccountNumber(), false, voucher,
					TransactionType.Transfer);
		}
		String penalInterest = loanLocData.getClosureData().getPenalInterest();
		if (penalInterest != null) {
			transactionAmount = MoneyUtil.getAccountingMoney(new BigDecimal(penalInterest));
		}
		if (!transactionAmount.isZero()) {
			populateCurrentTransactionRecord(loanLocData, loanLoc, account, currentTransactionList, businessDate, TransactionCode.PENAL_INTEREST,
					transactionAmount, "closure loc no. " + loanLocId + " towards penal interest", credit, account.getAccountNumber(), false,
					voucher, TransactionType.Transfer);
		}
		String charges = loanLocData.getClosureData().getOtherCharges();
		if (charges != null) {
			transactionAmount = MoneyUtil.getAccountingMoney(new BigDecimal(charges));
		}
		if (!transactionAmount.isZero()) {
			populateCurrentTransactionRecord(loanLocData, loanLoc, account, currentTransactionList, businessDate, TransactionCode.CHARGES_RECEIVABLE,
					transactionAmount, "closure loc no. " + loanLocId + " towards charges", credit, account.getAccountNumber(), false, voucher,
					TransactionType.Transfer);
		}
		String closingCharges = loanLocData.getClosureData().getClosingCharges();
		if (closingCharges != null) {
			transactionAmount = MoneyUtil.getAccountingMoney(new BigDecimal(closingCharges));
		}
		if (!transactionAmount.isZero()) {
			IPacsGlMappingDAO dao = KLSDataAccessFactory.getPacsGlMappingDAO();
			PacsGlMapping pacsGlMapping = dao.getPacsGlMapping(loanLoc.getProduct().getId(), pacsId);
			if (pacsGlMapping != null) {
				String closingChargesGl = pacsGlMapping.getClosingChargesGl();
				populateCurrentTransactionRecord(loanLocData, loanLoc, account, currentTransactionList, businessDate,
						TransactionCode.CLOSING_CHARGES, transactionAmount, "closure loc no. " + loanLocId + " towards closing charges", credit,
						closingChargesGl, false, voucher, TransactionType.Transfer);
			} else {
				return "-1";
			}
		}
		String transactionModeString = loanLocData.getClosureData().getTransactionMode();
		TransactionMode transactionMode = TransactionMode.Cash;
		if (transactionModeString != null) {
			transactionMode = TransactionMode.getType(transactionModeString);
		}
		if (TransactionMode.SB_Account_Of_Branch_Of_Bank.equals(transactionMode)) {
			String savingsAccNumber = loanLocData.getClosureData().getSavingsAccountNumber();
			logger.info("savingsAccNumber : " + savingsAccNumber);
			String transAmt = loanLocData.getClosureData().getTransactionAmount();
			if (transAmt != null) {
				transactionAmount = MoneyUtil.getAccountingMoney(new BigDecimal(transAmt));
			}
			populateCurrentTransactionRecord(loanLocData, loanLoc, account, currentTransactionList, businessDate, TransactionCode.CLOSURE,
					transactionAmount, "closure loc no. " + loanLocId + " from SB account in branch", debit, savingsAccNumber, false, voucher,
					transactionType);
		}
		logger.info("End: Populating the closure current transaction records in populateClosureCurrentTransactionRecords()");
		return transactionType.getValue() + " - " + voucherNumber.toString();
	}

	/**
	 * 
	 * @param loanLocData
	 * @param loanLoc
	 * @param account
	 * @param currentTransactionList
	 * @param businessDate
	 * @param transactionCode
	 * @param transactionAmount
	 * @param remarks
	 * @param crdr
	 */
	private void populateCurrentTransactionRecord(LoanLineOfCreditData loanLocData, LoanLineOfCredit loanLoc, Account account,
			List<CurrentTransaction> currentTransactionList, Date businessDate, TransactionCode transactionCode, AccountingMoney transactionAmount,
			String remarks, Integer crdr, String accountNumber, boolean isCashGl, String voucherNumber, TransactionType transactionType) {

		logger.info("Start: Populating the closure current transaction record in populateCurrentTransactionRecord()");
		CurrentTransaction currentTransaction = new CurrentTransaction();
		currentTransaction.setAccount(account);
		currentTransaction.setAccountNumber(accountNumber);
		currentTransaction.setBusinessDate(businessDate);
		currentTransaction.setChannelType(ChannelType.SYSTEM);
		currentTransaction.setCrDr(crdr);
		if (isCashGl) {
			currentTransaction.setLineOfCredit(null);
			currentTransaction.setOpeningBalance(null);
		} else {
			currentTransaction.setLineOfCredit(loanLoc);
			currentTransaction.setOpeningBalance(loanLoc.getCurrentBalance());
		}
		currentTransaction.setPacs(loanLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs());
		currentTransaction.setPostedStatus(1);
		currentTransaction.setRemarks(remarks);
		currentTransaction.setTerminalId(null);
		currentTransaction.setTransactionAmount(transactionAmount);
		currentTransaction.setTransactionCode(transactionCode);
		currentTransaction.setTransactionType(transactionType);
		currentTransaction.setVoucherNumber(voucherNumber);
		currentTransactionList.add(currentTransaction);
		logger.info("End: Populating the closure current transaction record in populateCurrentTransactionRecord()");
	}

	/**
	 * 
	 * @param dao
	 * @param loanLocData
	 * @param loanLoc
	 * @param cashGl
	 */
	private String closeLoanAccount(ILoanLineOfCreditDAO dao, LoanLineOfCreditData loanLocData, LoanLineOfCredit loanLoc, String cashGl) {

		logger.info("Start: Closing the loan account in closeLoanAccount()");
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		Account account = loanLoc.getAccount();
		loanLoc.setLineOfCreditStatus(LineOfCreditStatus.Closed);
		loanLoc.setCloseDate(LoanServiceUtil.getBusinessDate());
		updateLocAmounts(loanLocData, loanLoc);
		String voucherNumber = populateClosureCurrentTransactionRecords(loanLocData, loanLoc, account, currentTransactionList, cashGl);
		try {
			if (!(voucherNumber.equals("-1"))) {
				EntityManagerUtil.beginTransaction();

				if (loanLoc.getProduct().getAsAndWhenImplemented()) {
					List<LineOfCredit> locList = new ArrayList<LineOfCredit>();
					locList.add(loanLoc);
					List<CurrentTransaction> intPostTransactionList = LoanInterestPostingServiceHelper.getCurrentTransaction(locList,
							LoanServiceUtil.getBusinessDate());
					ICurrentTransactionDAO currTrnDAO = KLSDataAccessFactory.getCurrentTransactionDAO();
					currTrnDAO.saveCurrentTransaction(intPostTransactionList, Collections.<String, List<Object>> emptyMap());
				}

				dao.closeLoanLineOfCredit(loanLoc, currentTransactionList);
				EntityManagerUtil.CommitOrRollBackTransaction(true);

			}
		} catch (Exception e) {
			EntityManagerUtil.CommitOrRollBackTransaction(false);
			e.printStackTrace();
			logger.error("Error occured while closing line of credit");
			throw new KlsRuntimeException("Could not close loan account, some problem occured");
		}
		logger.info("End: Closing the loan account in closeLoanAccount()");
		return voucherNumber;
	}

	public AccountProperty checkIfAccountPropertyExists(Long custId) {

		logger.info("Start: checking the account property using cust id in checkIfAccountPropertyExists() method.");
		IAccountPropertyDAO dao = KLSDataAccessFactory.getAccountPropertyDAO();
		logger.info(" custId : " + custId);
		AccountProperty acctProperty = null;
		try {
			acctProperty = dao.getAccountByCustId(custId);
		} catch (Exception e) {
			logger.error("Error while retriving the account based on cust id from the database");
		}
		logger.info("End: checking the account property using cust id in checkIfAccountPropertyExists() method.");
		return acctProperty;
	}
	//Added conditions for IR-71
	@Override
	public List<LoanRepaymentSchedule> generateRepaymentScheduleByMultipleDisbursement(LoanLineOfCredit loanLoc, Money sanctionAmount, List<LoanRepaymentSchedule> myltipleRepaymentScheduleList, Date businessDate, Date firstInstallmentDate , String loanType , Long borrowingLoc ) {
		logger.info("Start : Generating default repayment schedule to loan repayment schedule table in generateRepaymentSchedule() method.");
		PacsLoanApplication loanApplication = loanLoc.getPacsLoanApplication();
		Money prevOutstandingAmount = Money.ZERO;
		String reducingFrequency = null;
		double reducingFreq = 0;
		int reducingFreqInt = 0;
		List<LoanRepaymentSchedule> repaymentScheduleList = new ArrayList<LoanRepaymentSchedule>();
		if (loanApplication != null && loanLoc != null) {
			Date sanctionDate  = null;
			//Date firstDueDate = null;
			Integer numOfInstallments = null;
			if(myltipleRepaymentScheduleList.size() > 0) {
				sanctionDate = businessDate;
				logger.info(" sanctionDate : " + sanctionDate);
				//firstDueDate = myltipleRepaymentScheduleList.get(0).getInstallmentDate();
				//logger.info(" firstDueDate : " + firstDueDate);
				numOfInstallments = myltipleRepaymentScheduleList.size();
				logger.info(" numOfInstallments : " + numOfInstallments);
				logger.info(" sanctionAmount : " + sanctionAmount);
				Integer loanPeriod = loanApplication.getLoanPeriod();
				logger.info(" loanPeriod : " + loanPeriod);
				Integer frequencyValue = loanApplication.getInstallmentFrequency().getValue();
				logger.info(" frequencyValue : " + frequencyValue);
				Product product = loanApplication.getProduct();
				InterestCategory interestCategory = loanApplication.getInterestCategory();
				if (product != null && interestCategory != null) {
					RepaymentSchedule repaymentSchedule = product.getRepaymentSchedule();
					logger.info(" repaymentSchedule : " + repaymentSchedule.getValue());
					RepaymentType repaymentType = product.getRepaymentType();
					logger.info(" repaymentType : " + repaymentType.getValue());
					Money principalAmountSum = Money.ZERO;
					Money principalAmount = calculatePrincipalAmount(sanctionAmount, numOfInstallments);
					Money tempPrincipalAmount = Money.ZERO;
					logger.info(" principalAmount : " + principalAmount);
					Money interestAmount = Money.ZERO;
					Money tempInterestAmount = Money.ZERO;
					
					Integer interestCategoryId = interestCategory.getId();
					if("B".equalsIgnoreCase(loanType)){
					interestCategoryId=	KLSServiceFactory.getBorrowingProductService().getProductById(loanLoc.getProduct().getBorrowingProductId()).getInterestCategoryId();
					}else{
					interestCategoryId =	interestCategory.getId();
					}
					
					List<SlabwiseInterestRate> interestSlabs = getInterestSlabs(interestCategoryId, sanctionDate);
					BigDecimal interestRate = (getInterestRate(interestSlabs, sanctionAmount)).divide(BigDecimal.valueOf(100));
					logger.info(" interestRate per annum : " + interestRate);
					double interestRatePerMonth = 0d;
					if(frequencyValue != null) {
						if(frequencyValue == 1) {
							interestRatePerMonth = interestRate.doubleValue() / 12;
						} else if(frequencyValue == 3) {
							interestRatePerMonth = interestRate.doubleValue() / 4;
						} else if(frequencyValue == 6) {
							interestRatePerMonth = interestRate.doubleValue() / 2;
						} else if(frequencyValue == 12) {
							interestRatePerMonth = interestRate.doubleValue() / 1;
						}
					}
					logger.info(" interestRate per month : " + interestRatePerMonth);
					Money outstandingAmount = sanctionAmount;
					Money tempOutstandingAmount = Money.ZERO;
					//Date installmentDate = firstDueDate;
					Money installmentAmount = principalAmount.add(interestAmount);
					Money tempInstallmentAmount = Money.ZERO;
					boolean isEquated = RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
							&& RepaymentType.EQUATED_INSTALLMENTS.equals(repaymentType);
					logger.info(" isEquated : " + isEquated);
					boolean isNonEquated = RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
							&& RepaymentType.NON_EQUATED_INSTALLMENTS.equals(repaymentType);
					logger.info(" isNonEquated : " + isNonEquated);
					if (isEquated) {
						installmentAmount = calculateInstallmentAmount(outstandingAmount, numOfInstallments, interestRatePerMonth);
					} else if (isNonEquated) {
						reducingFrequency = PropertiesUtil.getRepaymentProperty(ServiceConstants.REDUCING_FREQUENCY);
						reducingFreq = new Double(reducingFrequency).doubleValue();
						reducingFreqInt = new Double(reducingFrequency).intValue();
						logger.info(" reducingFreq : " + reducingFreq);
						logger.info(" interestRate : " + interestRate);
					}
					logger.info(" installmentAmount : " + installmentAmount);
					logger.info(" interestAmount : " + interestAmount);
					Calendar calendar = Calendar.getInstance();
					for (int i = 0; i < numOfInstallments; i++) {
						LoanRepaymentSchedule loanRepaymentSchedule = myltipleRepaymentScheduleList.get(i);
						if (isEquated) {
							interestAmount = calculateInterestAmount(outstandingAmount, interestRatePerMonth);
							principalAmount = installmentAmount.subtract(interestAmount);
							logger.info(" principalAmount : " + principalAmount);
							prevOutstandingAmount = outstandingAmount;
							logger.info(" prevOutstandingAmount : " + prevOutstandingAmount);
						} else if (isNonEquated) {
							int j = i+1;
							if ((reducingFreqInt != 0 && (j % reducingFreqInt) == 1) || reducingFreqInt == 1) {
								logger.info(" interestRate per annum reducing frequency : " + interestRate);
								interestAmount = calculateInterestAmount(outstandingAmount, interestRate.doubleValue(), reducingFreq);
							}
							installmentAmount = principalAmount.add(interestAmount);
							logger.info(" installmentAmount reducing frequency : " + installmentAmount);
							prevOutstandingAmount = outstandingAmount;
							logger.info(" prevOutstandingAmount : " + prevOutstandingAmount);
						}
						logger.info(" interestAmount : " + interestAmount);
						tempInterestAmount = loanRepaymentSchedule.getInterestAmount().add(interestAmount);
						loanRepaymentSchedule.setInterestAmount(tempInterestAmount);
						tempInterestAmount = Money.ZERO;
						tempInstallmentAmount = loanRepaymentSchedule.getInstallmentAmount().add(installmentAmount);
						loanRepaymentSchedule.setInstallmentAmount(tempInstallmentAmount);
						tempInstallmentAmount = Money.ZERO;
//						loanRepaymentSchedule.setInstallmentDate(installmentDate);
						//installmentDate = Date.valueOf(getInstallmentDate(calendar, installmentDate, frequencyValue));
						//logger.info(" installmentDate : " + installmentDate);
						outstandingAmount = outstandingAmount.subtract(principalAmount);
						logger.info(" outstandingAmount : " + outstandingAmount);
						tempOutstandingAmount = loanRepaymentSchedule.getLoanOutstandingAmount().add(outstandingAmount);
						loanRepaymentSchedule.setLoanOutstandingAmount(tempOutstandingAmount);
						tempPrincipalAmount =loanRepaymentSchedule.getPrincipalAmount().add(principalAmount);
						loanRepaymentSchedule.setPrincipalAmount(tempPrincipalAmount);
						tempPrincipalAmount = Money.ZERO;
						if(loanRepaymentSchedule.getLoanRepaymentScheduleId() == null) {
							LoanRepaymentScheduleId id = new LoanRepaymentScheduleId();
							if("B".equalsIgnoreCase(loanType)){
							id.setLineOfCreditId(borrowingLoc);
							}else{
							id.setLineOfCreditId(loanLoc.getId());	
							}
							id.setInstallmentNumber(i);
							loanRepaymentSchedule.setLoanRepaymentScheduleId(id);
						}
						
						if (RepaymentSchedule.PRINCIPAL.equals(repaymentSchedule)) {
							principalAmountSum = principalAmountSum.add(principalAmount);
							logger.info(" principalAmountSum : " + principalAmountSum);
						}
						if (i == numOfInstallments) {
							if (RepaymentSchedule.PRINCIPAL.equals(repaymentSchedule)) {
								Money adjustment = sanctionAmount.subtract(principalAmountSum);
								logger.info(" adjustment : " + adjustment.getAmount());
								if (!adjustment.isZero()) {
									loanRepaymentSchedule.setLoanOutstandingAmount(Money.ZERO);
								}
								principalAmount = principalAmount.add(adjustment);
								logger.info(" principalAmount after adjustment : " + principalAmount);
								installmentAmount = principalAmount.add(interestAmount);
								logger.info(" installmentAmount after adjustment : " + installmentAmount);
								tempInstallmentAmount = loanRepaymentSchedule.getInstallmentAmount().add(installmentAmount);
								loanRepaymentSchedule.setInstallmentAmount(tempInstallmentAmount);
							} else if (isEquated) {
								principalAmount = prevOutstandingAmount;
								logger.info(" principalAmount for the last schedule : " + principalAmount);
								installmentAmount = principalAmount.add(interestAmount);
								logger.info(" installmentAmount for the last schedule : " + installmentAmount);
								tempInstallmentAmount = loanRepaymentSchedule.getInstallmentAmount().add(installmentAmount);
								loanRepaymentSchedule.setInstallmentAmount(tempInstallmentAmount);
								loanRepaymentSchedule.setLoanOutstandingAmount(Money.ZERO);
							} else if (isNonEquated) {
								principalAmount = prevOutstandingAmount;
								logger.info(" principalAmount for the last schedule : " + principalAmount);
								installmentAmount = principalAmount.add(interestAmount);
								logger.info(" installmentAmount for the last schedule : " + installmentAmount);
								tempInstallmentAmount = loanRepaymentSchedule.getInstallmentAmount().add(installmentAmount);
								loanRepaymentSchedule.setInstallmentAmount(tempInstallmentAmount);
								loanRepaymentSchedule.setLoanOutstandingAmount(Money.ZERO);
							}
							tempPrincipalAmount = loanRepaymentSchedule.getPrincipalAmount().add(principalAmount);
							loanRepaymentSchedule.setPrincipalAmount(tempPrincipalAmount);
						}
						repaymentScheduleList.add(loanRepaymentSchedule);
					}
				}
			} else {
				repaymentScheduleList = generateRepaymentSchedule(loanLoc, sanctionAmount, businessDate, firstInstallmentDate , loanType , borrowingLoc);
			}
		}
		logger.info("End : Generating default repayment schedule to loan repayment schedule table in generateRepaymentSchedule() method.");
		return repaymentScheduleList;
	}

}