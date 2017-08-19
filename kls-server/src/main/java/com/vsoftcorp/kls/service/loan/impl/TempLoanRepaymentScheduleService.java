/**
 * 
 */
package com.vsoftcorp.kls.service.loan.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentScheduleId;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.business.entity.loan.TempLoanRepaymentSchedule;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.LoanRepaymentEditTypeData;
import com.vsoftcorp.kls.data.LoanRepaymentScheduleData;
import com.vsoftcorp.kls.data.LoanRepaymentScheduleDataList;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.dataaccess.loan.ITempLoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.service.account.ILoanAccountPropertyService;
import com.vsoftcorp.kls.service.constants.ServiceConstants;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.LoanRepaymentScheduleHelper;
import com.vsoftcorp.kls.service.helper.TempLoanRepaymentScheduleHelper;
import com.vsoftcorp.kls.service.loan.ITempLoanRepaymentScheduleService;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.valuetypes.loan.repayment.LoanRepaymentEditType;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentSchedule;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentType;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public class TempLoanRepaymentScheduleService implements ITempLoanRepaymentScheduleService {

	private static final Logger logger = Logger.getLogger(TempLoanRepaymentScheduleService.class);

	@Override
	public void saveTempRepaymentSchedule(LoanRepaymentScheduleData loanRepaymentScheduleData) {

		logger.info("Start: Saving the loan repayment schedule in saveLoanRepaymentSchedule() method.");
		ILoanRepaymentScheduleDAO dao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
		List<LoanRepaymentSchedule> scheduleList = null;
		scheduleList = LoanRepaymentScheduleHelper.getLoanRepaymentScheduleList(loanRepaymentScheduleData);
		try {
			dao.saveLoanRepaymentScheduleList(scheduleList);
		} catch (Exception e) {
			logger.error("Error while saving the loan repayment schedule list to the database");
			throw new KlsRuntimeException("Error while saving the loan repayment schedule list to the database",
					e.getCause());
		}
		logger.info("End: Saving the loan repayment schedule in saveLoanRepaymentSchedule() method.");
	}

	/**
	 * 
	 */
	public void saveTempRepaymentSchedule(List<LoanRepaymentSchedule> scheduleList) {

		logger.info("Start: Saving the loan repayment schedule in saveLoanRepaymentSchedule() method.");
		ILoanRepaymentScheduleDAO dao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
		try {
			dao.saveLoanRepaymentScheduleList(scheduleList);
		} catch (Exception e) {
			logger.error("Error while saving the loan repayment schedule list to the database");
			throw new KlsRuntimeException("Error while saving the loan repayment schedule list to the database",
					e.getCause());
		}
		logger.info("End: Saving the loan repayment schedule in saveLoanRepaymentSchedule() method.");
	}

	/**
	 * 
	 */
	public LoanRepaymentScheduleDataList getModifiedRepaymentScheduleDataList(Long lineOfCreditId,
			Integer installmentNum, String principalAmt, Integer editType) {

		logger.info("Start: Fetching the loan repayment schedule list for a line of credit id in getLoanRepaymentScheduleDataList() method.");
		ILoanRepaymentScheduleDAO dao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
		ITempLoanRepaymentScheduleDAO tempDao = KLSDataAccessFactory.getTempLoanRepaymentScheduleDAO();
		ILoanLineOfCreditDAO locDao = null;
		List<LoanRepaymentScheduleData> scheduleDataList = null;
		LoanRepaymentScheduleDataList data = new LoanRepaymentScheduleDataList();
		LoanRepaymentEditTypeData editTypeData = null;
		List<TempLoanRepaymentSchedule> tempScheduleList = null;
		List<LoanRepaymentSchedule> scheduleList = null;
		Money sanctionAmount = null;
		logger.info(" lineOfCreditId : " + lineOfCreditId);
		logger.info(" installmentNum : " + installmentNum);
		logger.info(" principalAmt : " + principalAmt);
		if (installmentNum.intValue() == 1 || editType.equals(LoanRepaymentEditType.EQUATED.getValue())
				|| editType.equals(LoanRepaymentEditType.NON_EQUATED.getValue())) {
			locDao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
			LoanLineOfCredit loanLoc = locDao.getLoanLineOfCreditById(lineOfCreditId);
			sanctionAmount = loanLoc.getSanctionedAmount();
		}
		logger.info(" sanctionAmount : " + sanctionAmount);
		try {
			List<TempLoanRepaymentSchedule> tempScheduleListFromDb = tempDao
					.getTempLoanRepaymentScheduleByLocId(lineOfCreditId);
			if (tempScheduleListFromDb != null && !tempScheduleListFromDb.isEmpty()) {
				if ((tempScheduleListFromDb.size() == 1 && installmentNum == 1)
						|| (tempScheduleListFromDb.size() == installmentNum)) {
					if ((tempScheduleListFromDb.size() == 1 && installmentNum == 1)) {
						data.setErrorMessage(" Loan Repayment Schedule has only one schedule.Cannot be edited.");
					}
					if ((tempScheduleListFromDb.size() == installmentNum)) {
						data.setErrorMessage(" Last Schedule of the loan repayment schedule cannot be edited.");
					}
					scheduleDataList = TempLoanRepaymentScheduleHelper
							.getTempLoanRepaymentScheduleDataList(tempScheduleList);
				} else {
					tempScheduleList = updateTempRepaymentSchedule(tempScheduleListFromDb, installmentNum,
							principalAmt, sanctionAmount, editType);
					if (tempScheduleList != null) {
						scheduleDataList = TempLoanRepaymentScheduleHelper
								.getTempLoanRepaymentScheduleDataList(tempScheduleList);
						tempDao.saveTempLoanRepaymentScheduleList(tempScheduleList);
					} else {
						scheduleDataList = TempLoanRepaymentScheduleHelper
								.getTempLoanRepaymentScheduleDataList(tempScheduleListFromDb);
						data.setErrorMessage(" Principal Amount cannot be greater than the outstanding amount.");
					}
				}
			} else {
				scheduleList = dao.getLoanRepaymentScheduleByLocId(lineOfCreditId);
				if ((scheduleList.size() == 1 && installmentNum == 1) || (scheduleList.size() == installmentNum)) {
					if ((scheduleList.size() == 1 && installmentNum == 1)) {
						data.setErrorMessage(" Loan Repayment Schedule has only one schedule.Cannot be edited.");
					}
					if ((scheduleList.size() == installmentNum)) {
						data.setErrorMessage(" Last Schedule of the loan repayment schedule cannot be edited.");
					}
					scheduleDataList = LoanRepaymentScheduleHelper.getLoanRepaymentScheduleDataList(scheduleList);
				} else {
					tempScheduleList = generateNewRepaymentSchedule(scheduleList, installmentNum, principalAmt,
							sanctionAmount, editType);
					if (tempScheduleList != null) {
						scheduleDataList = TempLoanRepaymentScheduleHelper
								.getTempLoanRepaymentScheduleDataList(tempScheduleList);
						tempDao.saveTempLoanRepaymentScheduleList(tempScheduleList);
					} else {
						scheduleDataList = LoanRepaymentScheduleHelper.getLoanRepaymentScheduleDataList(scheduleList);
						data.setErrorMessage(" principal amount cannot be greater than the outstanding amount.");
					}
				}
			}
			data.setLoanRepaymentScheduleData(scheduleDataList);
			editTypeData = getRepaymentEditType(scheduleList, editType);
			data.setRepaymentEditTypeData(editTypeData);
		} catch (Exception e) {
			logger.error("Error while fetching the temp loan repayment schedule list from the database");
			throw new KlsRuntimeException(
					"Error while fetching the temp loan repayment schedule list from the database", e.getCause());
		}
		logger.info("End: Fetching the loan repayment schedule list for a line of credit id in getLoanRepaymentScheduleDataList() method.");
		return data;
	}

	/**
	 * 
	 * @param scheduleList
	 * @param editType
	 * @return
	 */
	private LoanRepaymentEditTypeData getRepaymentEditType(List<LoanRepaymentSchedule> scheduleList, Integer editType) {

		LoanRepaymentEditTypeData typeData = new LoanRepaymentEditTypeData();
		if (scheduleList != null && !scheduleList.isEmpty() && editType == null) {
			LoanRepaymentSchedule schedule = scheduleList.get(0);
			ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
			LoanLineOfCredit loanLoc = dao.getLoanLineOfCreditById(schedule.getLoanRepaymentScheduleId()
					.getLineOfCreditId());
			if (loanLoc != null) {
				Product product = loanLoc.getProduct();
				if (product != null) {
					RepaymentSchedule repaymentSchedule = product.getRepaymentSchedule();
					logger.info("repaymentSchedule : " + repaymentSchedule);
					RepaymentType repaymentType = product.getRepaymentType();
					logger.info("repaymentType : " + repaymentType);
					if (RepaymentSchedule.PRINCIPAL.equals(repaymentSchedule)) {
						typeData.setId(LoanRepaymentEditType.PRINCIPAL.toString());
						typeData.setValue(LoanRepaymentEditType.PRINCIPAL.getValue());
					} else if (RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
							&& RepaymentType.EQUATED_INSTALLMENTS.equals(repaymentType)) {
						typeData.setId(LoanRepaymentEditType.EQUATED.toString());
						typeData.setValue(LoanRepaymentEditType.EQUATED.getValue());
					} else if (RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
							&& RepaymentType.NON_EQUATED_INSTALLMENTS.equals(repaymentType)) {
						typeData.setId(LoanRepaymentEditType.NON_EQUATED.toString());
						typeData.setValue(LoanRepaymentEditType.NON_EQUATED.getValue());
					}
				}
			}
		}
		if (editType != null) {
			LoanRepaymentEditType editTypeEnum = LoanRepaymentEditType.getType(editType);
			typeData.setId(editTypeEnum.name());
			typeData.setValue(editTypeEnum.getValue());
		}
		return typeData;
	}

	/**
	 * 
	 */
	public void updateTempRepaymentScheduleDataList(LoanRepaymentScheduleDataList scheduleDataList) {

		logger.info("Start: Updating the loan repayment schedule list in updateRepaymentScheduleDataList() method.");
		ILoanRepaymentScheduleDAO dao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
		List<LoanRepaymentSchedule> scheduleList = LoanRepaymentScheduleHelper
				.getLoanRepaymentScheduleList(scheduleDataList.getLoanRepaymentScheduleData());
		try {
			dao.updateLoanRepaymentScheduleList(scheduleList);
		} catch (Exception e) {
			logger.error("Error while updating the loan repayment schedule list to the database");
			throw new KlsRuntimeException("Error while updating the loan repayment schedule list.", e.getCause());
		}
		logger.info("End: Updating the loan repayment schedule list in updateRepaymentScheduleDataList() method.");
	}

	/**
	 * 
	 * @param scheduleList
	 * @param installmentNum
	 * @param amount
	 * @return
	 */
	private List<TempLoanRepaymentSchedule> generateNewRepaymentSchedule(List<LoanRepaymentSchedule> scheduleList,
			Integer installmentNum, String amount, Money sanctionAmount, Integer editType) {

		logger.info("Start : Generating new repayment schedule in generateNewRepaymentSchedule() method.");
		List<TempLoanRepaymentSchedule> tempRepaymentScheduleList = new ArrayList<TempLoanRepaymentSchedule>();
		BigDecimal bd = new BigDecimal(amount);
		Money prevOutstandingAmt = null;
		Money prevInterestAmt = Money.ZERO;
		Money diff = null;
		Money newAmount = MoneyUtil.getAccountingMoney(bd).getMoney();
		logger.info(" newAmount : " + newAmount);
		logger.info(" installmentNum : " + installmentNum);
		logger.info(" sanctionAmount : " + sanctionAmount);
		boolean isPrincipal = editType.equals(LoanRepaymentEditType.PRINCIPAL.getValue());
		logger.info(" isPrincipal : " + isPrincipal);
		boolean isEquated = editType.equals(LoanRepaymentEditType.EQUATED.getValue());
		logger.info(" isEquated : " + isEquated);
		boolean isNonEquated = editType.equals(LoanRepaymentEditType.NON_EQUATED.getValue());
		logger.info(" isNonEquated : " + isNonEquated);
		List<SlabwiseInterestRate> interestSlabs = null;
		if (installmentNum == 1 || isEquated || isNonEquated) {
			prevOutstandingAmt = sanctionAmount;
		}
		double interestRatePerMonth = 0;
		BigDecimal interestRate = null;
		ILoanAccountPropertyService service = null;
		String reducingFrequency = PropertiesUtil.getRepaymentProperty(ServiceConstants.REDUCING_FREQUENCY);
		double reducingFreq = Double.parseDouble(reducingFrequency);
		logger.info(" reducingFreq : " + reducingFreq);
		int reducingFreqInt = Integer.parseInt(reducingFrequency);
		Money nonEquatedIntAmount = Money.ZERO;
		if (isEquated) {
			service = KLSServiceFactory.getLoanAccountPropertyService();
			interestRatePerMonth = getInterestRatePerMonth(service, scheduleList.get(0).getLoanRepaymentScheduleId()
					.getLineOfCreditId());
		} else if (isNonEquated) {
			service = KLSServiceFactory.getLoanAccountPropertyService();
			interestSlabs = getInterestSlabs(service, scheduleList.get(0).getLoanRepaymentScheduleId()
					.getLineOfCreditId());
			logger.info(" interestSlabs size : " + interestSlabs.size());
			interestRate = service.getInterestRate(interestSlabs, prevOutstandingAmt);
			interestRate = interestRate.divide(BigDecimal.valueOf(100));
			logger.info(" interestRate per annum reducing frequency : " + interestRate);
		}
		for (LoanRepaymentSchedule loanRepaymentSchedule : scheduleList) {
			TempLoanRepaymentSchedule tempSchedule = new TempLoanRepaymentSchedule();
			Integer installmentNumber = loanRepaymentSchedule.getLoanRepaymentScheduleId().getInstallmentNumber();
			logger.info(" installmentNumber : " + installmentNumber);
			Money principalAmount = loanRepaymentSchedule.getPrincipalAmount();
			logger.info(" principalAmount : " + principalAmount);
			Money installmentAmt = loanRepaymentSchedule.getInstallmentAmount();
			logger.info(" installmentAmt before : " + installmentAmt);
			Money outstandingAmt = loanRepaymentSchedule.getLoanOutstandingAmount();
			logger.info(" outstandingAmt before : " + outstandingAmt);
			Money interestAmount = loanRepaymentSchedule.getInterestAmount();
			if (isEquated) {
				interestAmount = service.calculateInterestAmount(prevOutstandingAmt, interestRatePerMonth);
			}
			logger.info(" interestAmount : " + interestAmount);
			if (installmentNumber.intValue() == installmentNum.intValue()) {
				if ((prevOutstandingAmt.subtract(newAmount)).isNegative()) {
					logger.info("entered amount greater than the outstanding amount.");
					tempRepaymentScheduleList = null;
					break;
				} else if (isPrincipal) {
					diff = principalAmount.subtract(newAmount);
					logger.info("diff : " + diff);
					principalAmount = newAmount;
					installmentAmt = principalAmount.add(interestAmount);
					outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
				} else if (isEquated) {
					installmentAmt = newAmount;
					logger.info(" installmentAmt after : " + installmentAmt);
					principalAmount = installmentAmt.subtract(interestAmount);
					logger.info(" principalAmount after : " + principalAmount);
					outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
					logger.info(" outstandingAmt after : " + outstandingAmt);
				} else if (isNonEquated) {
					if (interestSlabs != null) {
						principalAmount = newAmount;
						logger.info(" principalAmount after : " + principalAmount);
						installmentAmt = principalAmount.add(interestAmount);
						logger.info(" installmentAmt after : " + installmentAmt);
						outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
						logger.info(" outstandingAmt after : " + outstandingAmt);
						nonEquatedIntAmount = interestAmount;
						logger.info(" nonEquatedIntAmount : " + nonEquatedIntAmount);
					}
				}
			}
			if (installmentNumber.intValue() > installmentNum.intValue()) {
				if (isPrincipal) {
					if (prevOutstandingAmt.isZero()) {
						logger.info(" prevOutstandingAmt is ZERO ");
						outstandingAmt = Money.ZERO;
						principalAmount = Money.ZERO;
						installmentAmt = principalAmount.add(interestAmount);
					} else {
						outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
						logger.info(" outstandingAmt : " + outstandingAmt);
						if (outstandingAmt.isNegative()) {
							logger.info(" outstandingAmt is NEGATIVE ");
							principalAmount = prevOutstandingAmt;
							logger.info(" principalAmount : " + principalAmount);
							installmentAmt = principalAmount.add(interestAmount);
							outstandingAmt = Money.ZERO;
						}
					}
				} else if (isEquated) {
					Money equatedDiffAmt = prevOutstandingAmt.subtract(installmentAmt);
					logger.info(" equatedDiffAmt : " + equatedDiffAmt);
					if (equatedDiffAmt.isNegative()) {
						logger.info(" equatedDiffAmt is NEGATIVE ");
						installmentAmt = Money.ZERO;
						principalAmount = prevOutstandingAmt;
						outstandingAmt = Money.ZERO;
					} else {
						principalAmount = installmentAmt.subtract(interestAmount);
						logger.info(" principalAmount : " + principalAmount);
						outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
					}
				} else if (isNonEquated) {
					if (prevOutstandingAmt.isZero()) {
						logger.info(" prevOutstandingAmt is ZERO ");
						outstandingAmt = Money.ZERO;
						principalAmount = Money.ZERO;
						interestAmount = Money.ZERO;
						installmentAmt = principalAmount.add(interestAmount);
					} else {
						outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
						logger.info(" outstandingAmt : " + outstandingAmt);
						if (outstandingAmt.isNegative()) {
							logger.info(" outstandingAmt is NEGATIVE ");
							principalAmount = prevOutstandingAmt;
							logger.info(" principalAmount : " + principalAmount);
							interestAmount = prevInterestAmt;
							logger.info(" interestAmount : " + interestAmount);
							installmentAmt = principalAmount.add(interestAmount);
							logger.info(" installmentAmt after : " + installmentAmt);
							outstandingAmt = Money.ZERO;
						} else {
							if (((installmentNumber.intValue()) % reducingFreqInt) == 1) {
								logger.info(" interestRate per annum reducing frequency : " + interestRate);
								interestAmount = service.calculateInterestAmount(prevOutstandingAmt,
										interestRate.doubleValue(), reducingFreq);
								logger.info(" interestAmount : " + interestAmount);
								nonEquatedIntAmount = interestAmount;
							} else {
								interestAmount = nonEquatedIntAmount;
								logger.info(" interestAmount : " + interestAmount);
							}
							installmentAmt = principalAmount.add(interestAmount);
							logger.info(" installmentAmt after : " + installmentAmt);
						}
					}
				}
			}
			if (installmentNumber.equals(scheduleList.size())) {
				if (diff != null && !diff.isNegative() && (!outstandingAmt.isZero()) && isPrincipal) {
					principalAmount = principalAmount.add(diff);
					outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
					installmentAmt = principalAmount.add(interestAmount);
				} else if (isEquated) {
					principalAmount = prevOutstandingAmt;
					logger.info(" principalAmount : " + principalAmount);
					installmentAmt = principalAmount.add(interestAmount);
					outstandingAmt = Money.ZERO;
				} else if (isNonEquated) {
					principalAmount = prevOutstandingAmt;
					logger.info(" principalAmount : " + principalAmount);
					installmentAmt = principalAmount.add(interestAmount);
					logger.info(" installmentAmt : " + installmentAmt);
					outstandingAmt = Money.ZERO;
				}
			}
			prevOutstandingAmt = outstandingAmt;
			logger.info(" prevOutstandingAmt : " + prevOutstandingAmt);
			prevInterestAmt = interestAmount;
			logger.info(" prevInterestAmt : " + prevInterestAmt);
			tempSchedule.setInstallmentAmount(installmentAmt);
			tempSchedule.setInstallmentDate(loanRepaymentSchedule.getInstallmentDate());
			tempSchedule.setInterestAmount(interestAmount);
			tempSchedule.setPrincipalAmount(principalAmount);
			tempSchedule.setLoanOutstandingAmount(outstandingAmt);
			LoanRepaymentScheduleId id = new LoanRepaymentScheduleId();
			id.setLineOfCreditId(loanRepaymentSchedule.getLoanRepaymentScheduleId().getLineOfCreditId());
			id.setInstallmentNumber(installmentNumber);
			tempSchedule.setLoanRepaymentScheduleId(id);
			tempRepaymentScheduleList.add(tempSchedule);
		}
		logger.info("End : Generating new repayment schedule in generateNewRepaymentSchedule() method.");
		return tempRepaymentScheduleList;
	}

	/**
	 * 
	 * @param tempScheduleList
	 * @param installmentNum
	 * @param amount
	 * @return
	 */
	private List<TempLoanRepaymentSchedule> updateTempRepaymentSchedule(
			List<TempLoanRepaymentSchedule> tempScheduleList, Integer installmentNum, String amount,
			Money sanctionAmount, Integer editType) {

		logger.info("Start : Modifying temp repayment schedule in updateTempRepaymentSchedule() method.");
		BigDecimal bd = new BigDecimal(amount);
		Money prevOutstandingAmt = null;
		Money prevInterestAmt = Money.ZERO;
		Money diff = null;
		Map<BigDecimal, Integer> emiMap = new HashMap<BigDecimal, Integer>();
		Money newAmount = MoneyUtil.getAccountingMoney(bd).getMoney();
		logger.info(" newPrincipalAmount : " + newAmount);
		logger.info(" installmentNum : " + installmentNum);
		boolean isPrincipal = editType.equals(LoanRepaymentEditType.PRINCIPAL.getValue());
		logger.info(" isPrincipal : " + isPrincipal);
		boolean isEquated = editType.equals(LoanRepaymentEditType.EQUATED.getValue());
		logger.info(" isEquated : " + isEquated);
		boolean isNonEquated = editType.equals(LoanRepaymentEditType.NON_EQUATED.getValue());
		logger.info(" isNonEquated : " + isNonEquated);
		List<SlabwiseInterestRate> interestSlabs = null;
		if (installmentNum == 1 || isEquated || isNonEquated) {
			prevOutstandingAmt = sanctionAmount;
		}
		String reducingFrequency = PropertiesUtil.getRepaymentProperty(ServiceConstants.REDUCING_FREQUENCY);
		double reducingFreq = Double.parseDouble(reducingFrequency);
		logger.info(" reducingFreq : " + reducingFreq);
		int reducingFreqInt = Integer.parseInt(reducingFrequency);
		double interestRatePerMonth = 0;
		Money nonEquatedIntAmount = Money.ZERO;
		ILoanAccountPropertyService service = null;
		BigDecimal interestRate = null;
		if (isEquated) {
			service = KLSServiceFactory.getLoanAccountPropertyService();
			interestRatePerMonth = getInterestRatePerMonth(service, tempScheduleList.get(0)
					.getLoanRepaymentScheduleId().getLineOfCreditId());
		} else if (isNonEquated) {
			service = KLSServiceFactory.getLoanAccountPropertyService();
			interestSlabs = getInterestSlabs(service, tempScheduleList.get(0).getLoanRepaymentScheduleId()
					.getLineOfCreditId());
			logger.info(" interestSlabs size : " + interestSlabs.size());
			interestRate = service.getInterestRate(interestSlabs, prevOutstandingAmt);
			interestRate = interestRate.divide(BigDecimal.valueOf(100));
			logger.info(" interestRate per annum reducing frequency : " + interestRate);
		}
		for (TempLoanRepaymentSchedule tempSchedule : tempScheduleList) {
			Integer installmentNumber = tempSchedule.getLoanRepaymentScheduleId().getInstallmentNumber();
			logger.info(" installmentNumber : " + installmentNumber);
			Money principalAmount = tempSchedule.getPrincipalAmount();
			logger.info(" principalAmount : " + principalAmount);
			Money installmentAmt = tempSchedule.getInstallmentAmount();
			logger.info(" installmentAmt before : " + installmentAmt);
			if (isPrincipal || isNonEquated) {
				putEmiAmountInAMap(emiMap, principalAmount);
			} else if (isEquated) {
				putEmiAmountInAMap(emiMap, installmentAmt);
			}
			Money outstandingAmt = tempSchedule.getLoanOutstandingAmount();
			logger.info(" outstandingAmt before : " + outstandingAmt);
			Money interestAmount = tempSchedule.getInterestAmount();
			if (isEquated) {
				interestAmount = service.calculateInterestAmount(prevOutstandingAmt, interestRatePerMonth);
			}
			logger.info(" interestAmount : " + interestAmount);
			if (installmentNumber.intValue() == installmentNum.intValue()) {
				if ((prevOutstandingAmt.subtract(newAmount)).isNegative()) {
					logger.info("new principal amount is greater than outstanding amount.");
					tempScheduleList = null;
					break;
				} else if (isPrincipal) {
					diff = principalAmount.subtract(newAmount);
					logger.info("diff : " + diff);
					principalAmount = newAmount;
					installmentAmt = principalAmount.add(interestAmount);
					outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
				} else if (isEquated) {
					installmentAmt = newAmount;
					logger.info(" installmentAmt after : " + installmentAmt);
					principalAmount = installmentAmt.subtract(interestAmount);
					logger.info(" principalAmount after : " + principalAmount);
					outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
					logger.info(" outstandingAmt after : " + outstandingAmt);
				} else if (isNonEquated) {
					if (interestSlabs != null) {
						principalAmount = newAmount;
						logger.info(" principalAmount after : " + principalAmount);
						installmentAmt = principalAmount.add(interestAmount);
						logger.info(" installmentAmt after : " + installmentAmt);
						outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
						logger.info(" outstandingAmt after : " + outstandingAmt);
						nonEquatedIntAmount = interestAmount;
						logger.info(" nonEquatedIntAmount : " + nonEquatedIntAmount);
					}
				}
			}
			if (installmentNumber.intValue() > installmentNum.intValue()) {
				if (isPrincipal) {
					if (prevOutstandingAmt.isZero()) {
						logger.info(" prevOutstandingAmt is ZERO ");
						outstandingAmt = Money.ZERO;
						principalAmount = Money.ZERO;
						installmentAmt = principalAmount.add(interestAmount);
					} else {
						if (principalAmount.isZero()) {
							Money emiAmount = getEmiAmount(emiMap);
							Money diffEmi = prevOutstandingAmt.subtract(emiAmount);
							logger.info(" diffEmi : " + diffEmi);
							if (diffEmi.isNegative() || diffEmi.isZero()) {
								principalAmount = prevOutstandingAmt;
								logger.info(" principalAmount : " + principalAmount);
								installmentAmt = principalAmount.add(interestAmount);
								outstandingAmt = Money.ZERO;
							} else {
								principalAmount = emiAmount;
								logger.info(" principalAmount : " + principalAmount);
								installmentAmt = principalAmount.add(interestAmount);
								outstandingAmt = prevOutstandingAmt.subtract(emiAmount);
							}
						} else {
							outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
							logger.info(" outstandingAmt : " + outstandingAmt);
							if (outstandingAmt.isNegative()) {
								logger.info(" outstandingAmt is NEGATIVE ");
								principalAmount = prevOutstandingAmt;
								logger.info(" principalAmount : " + principalAmount);
								installmentAmt = principalAmount.add(interestAmount);
								outstandingAmt = Money.ZERO;
							}
						}
					}
				} else if (isEquated) {
					if (prevOutstandingAmt.isZero()) {
						logger.info(" prevOutstandingAmt is ZERO ");
						outstandingAmt = Money.ZERO;
						principalAmount = Money.ZERO;
						installmentAmt = Money.ZERO;
					} else {
						if (installmentAmt.isZero()) {
							Money installmentAmount = getEmiAmount(emiMap);
							Money diffEquatedEmi = prevOutstandingAmt.subtract(installmentAmount);
							logger.info(" diffEquatedEmi : " + diffEquatedEmi);
							if (diffEquatedEmi.isNegative() || diffEquatedEmi.isZero()) {
								principalAmount = prevOutstandingAmt;
								logger.info(" principalAmount : " + principalAmount);
								installmentAmt = Money.ZERO;
								outstandingAmt = Money.ZERO;
							} else {
								installmentAmt = installmentAmount;
								logger.info(" installmentAmt : " + installmentAmt);
								principalAmount = installmentAmt.subtract(interestAmount);
								logger.info(" principalAmount : " + principalAmount);
								outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
							}
						} else {
							Money equatedDiffAmt = prevOutstandingAmt.subtract(installmentAmt);
							logger.info(" equatedDiffAmt : " + equatedDiffAmt);
							if (equatedDiffAmt.isNegative()) {
								logger.info(" equatedDiffAmt is NEGATIVE ");
								principalAmount = prevOutstandingAmt;
								logger.info(" principalAmount : " + principalAmount);
								installmentAmt = Money.ZERO;
								outstandingAmt = Money.ZERO;
							} else {
								principalAmount = installmentAmt.subtract(interestAmount);
								logger.info(" principalAmount : " + principalAmount);
								outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
							}
						}
					}
				} else if (isNonEquated) {
					if (prevOutstandingAmt.isZero()) {
						logger.info(" prevOutstandingAmt is ZERO ");
						outstandingAmt = Money.ZERO;
						principalAmount = Money.ZERO;
						interestAmount = Money.ZERO;
						installmentAmt = principalAmount.add(interestAmount);
					} else {
						if (principalAmount.isZero()) {
							Money emiAmount = getEmiAmount(emiMap);
							Money diffEmi = prevOutstandingAmt.subtract(emiAmount);
							logger.info(" diffEmi : " + diffEmi);
							if (diffEmi.isNegative() || diffEmi.isZero()) {
								principalAmount = prevOutstandingAmt;
								logger.info(" principalAmount : " + principalAmount);
								interestAmount = prevInterestAmt;
								logger.info(" interestAmount : " + interestAmount);
								installmentAmt = principalAmount.add(interestAmount);
								outstandingAmt = Money.ZERO;
							} else {
								principalAmount = emiAmount;
								logger.info(" principalAmount : " + principalAmount);
								nonEquatedIntAmount = prevInterestAmt;
								if (((installmentNumber.intValue()) % reducingFreqInt) == 1) {
									logger.info(" interestRate per annum reducing frequency : " + interestRate);
									interestAmount = service.calculateInterestAmount(prevOutstandingAmt,
											interestRate.doubleValue(), reducingFreq);
									logger.info(" interestAmount : " + interestAmount);
									nonEquatedIntAmount = interestAmount;
								} else {
									interestAmount = nonEquatedIntAmount;
									logger.info(" interestAmount : " + interestAmount);
								}
								installmentAmt = principalAmount.add(interestAmount);
								logger.info(" installmentAmt after : " + installmentAmt);
								outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
							}
						} else {
							Money emiAmount = getEmiAmount(emiMap);
							int compare = principalAmount.compareTo(emiAmount);
							logger.info(" compare : " + compare);
							if (compare == -1) {
								principalAmount = emiAmount;
								logger.info(" principalAmount : " + principalAmount);
							}
							outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
							logger.info(" outstandingAmt : " + outstandingAmt);
							if (outstandingAmt.isNegative()) {
								logger.info(" outstandingAmt is NEGATIVE ");
								principalAmount = prevOutstandingAmt;
								logger.info(" principalAmount : " + principalAmount);
								installmentAmt = principalAmount.add(interestAmount);
								outstandingAmt = Money.ZERO;
							}
						}
					}
				}
			}
			if (installmentNumber.equals(tempScheduleList.size())) {
				if (diff != null && !diff.isNegative() && (!outstandingAmt.isZero()) && isPrincipal) {
					principalAmount = principalAmount.add(diff);
					outstandingAmt = prevOutstandingAmt.subtract(principalAmount);
					installmentAmt = principalAmount.add(interestAmount);
				} else if (isEquated) {
					principalAmount = prevOutstandingAmt;
					logger.info(" principalAmount : " + principalAmount);
					installmentAmt = principalAmount.add(interestAmount);
					outstandingAmt = Money.ZERO;
				} else if (isNonEquated) {
					principalAmount = prevOutstandingAmt;
					logger.info(" principalAmount : " + principalAmount);
					installmentAmt = principalAmount.add(interestAmount);
					logger.info(" installmentAmt : " + installmentAmt);
					outstandingAmt = Money.ZERO;
				}
			}
			prevOutstandingAmt = outstandingAmt;
			logger.info(" prevOutstandingAmt : " + prevOutstandingAmt);
			prevInterestAmt = interestAmount;
			logger.info(" prevInterestAmt : " + prevInterestAmt);
			tempSchedule.setInstallmentAmount(installmentAmt);
			tempSchedule.setInterestAmount(interestAmount);
			tempSchedule.setPrincipalAmount(principalAmount);
			tempSchedule.setLoanOutstandingAmount(outstandingAmt);
		}
		logger.info("End : Modifying temp repayment schedule in updateTempRepaymentSchedule() method.");
		return tempScheduleList;
	}

	/**
	 * 
	 * @param emiMap
	 * @return
	 */
	private Money getEmiAmount(Map<BigDecimal, Integer> emiMap) {

		logger.info("Start : Getting emi amount from the map in getEmiAmount() method.");
		if (emiMap != null) {
			Integer maxValue = 0;
			BigDecimal emiAmount = null;
			Iterator iterator = emiMap.keySet().iterator();
			while (iterator.hasNext()) {
				BigDecimal key = (BigDecimal) iterator.next();
				Integer value = emiMap.get(key);
				if (value > maxValue && (key.compareTo(BigDecimal.ZERO) != 0)) {
					emiAmount = key;
					maxValue = value;
				}
			}
			logger.info("End : Getting emi amount from the map in getEmiAmount() method.");
			logger.info(" emiAmount : " + emiAmount);
			return (MoneyUtil.getAccountingMoney(emiAmount).getMoney());
		}
		return Money.ZERO;
	}

	/**
	 * 
	 * @param emiMap
	 * @param amount
	 */
	private void putEmiAmountInAMap(Map<BigDecimal, Integer> emiMap, Money amount) {

		logger.info("Start : Putting the emi amount into a map in putEmiAmountInAMap() method.");
		if (emiMap.containsKey(amount.getAmount())) {
			int count = emiMap.get(amount.getAmount());
			emiMap.put(amount.getAmount(), (count + 1));
		} else {
			emiMap.put(amount.getAmount(), 1);
		}
		logger.info("End : Putting the emi amount into a map in putEmiAmountInAMap() method.");
	}

	/**
	 * 
	 * @param service
	 * @param lineOfCreditId
	 * @return
	 */
	private double getInterestRatePerMonth(ILoanAccountPropertyService service, Long lineOfCreditId) {

		logger.info("Start : Calculating the interest rate per month in getInterestRatePerMonth() method.");
		BigDecimal interestRate = getInterestRate(service, lineOfCreditId);
		logger.info(" interestRate per annum : " + interestRate);
		double interestRatePerMonth = interestRate.doubleValue() / 12;
		logger.info(" interestRatePerMonth : " + interestRatePerMonth);
		logger.info("End : Calculating the interest rate per month in getInterestRatePerMonth() method.");
		return interestRatePerMonth;
	}

	/**
	 * 
	 * @param service
	 * @param lineOfCreditId
	 * @return
	 */
	private BigDecimal getInterestRate(ILoanAccountPropertyService service, Long lineOfCreditId) {

		logger.info("Start : Calculating the interest rate in getInterestRate() method.");
		ILoanLineOfCreditDAO locDao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		LoanLineOfCredit loanLoc = locDao.getLoanLineOfCreditById(lineOfCreditId);
		PacsLoanApplication loanApplication = loanLoc.getPacsLoanApplication();
		InterestCategory interestCategory = loanApplication.getInterestCategory();
		Date sanctionDate = loanLoc.getSanctionedDate();
		Money sanctionAmount = loanLoc.getSanctionedAmount();
		logger.info(" sanctionAmount : " + sanctionAmount);
		List<SlabwiseInterestRate> interestSlabs = service.getInterestSlabs(interestCategory.getId(), sanctionDate);
		BigDecimal interestRate = (service.getInterestRate(interestSlabs, sanctionAmount)).divide(BigDecimal
				.valueOf(100));
		logger.info("End : Calculating the interest rate in getInterestRate() method.");
		return interestRate;
	}

	/**
	 * 
	 * @param service
	 * @param lineOfCreditId
	 * @return
	 */
	private List<SlabwiseInterestRate> getInterestSlabs(ILoanAccountPropertyService service, Long lineOfCreditId) {

		logger.info("Start : Fetching the interest slabs in getInterestSlabs() method.");
		ILoanLineOfCreditDAO locDao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		LoanLineOfCredit loanLoc = locDao.getLoanLineOfCreditById(lineOfCreditId);
		PacsLoanApplication loanApplication = loanLoc.getPacsLoanApplication();
		InterestCategory interestCategory = loanApplication.getInterestCategory();
		Date sanctionDate = loanLoc.getSanctionedDate();
		Money sanctionAmount = loanLoc.getSanctionedAmount();
		logger.info(" sanctionAmount : " + sanctionAmount);
		List<SlabwiseInterestRate> interestSlabs = service.getInterestSlabs(interestCategory.getId(), sanctionDate);
		logger.info("End : Fetching the interest slabs in getInterestSlabs() method.");
		return interestSlabs;
	}
}
