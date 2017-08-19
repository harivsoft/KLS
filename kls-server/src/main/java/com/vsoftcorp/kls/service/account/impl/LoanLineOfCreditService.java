/**
 * 
 */
package com.vsoftcorp.kls.service.account.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entity.account.Charges;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.ChargesDebit;
import com.vsoftcorp.kls.business.entity.loan.LoanRecovery;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.LoanAccountClosureData;
import com.vsoftcorp.kls.data.LoanLineOfCreditData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDAO;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDebitDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanInterestCalculationDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRecoveryDAO;
import com.vsoftcorp.kls.dataaccess.loan.IPacsLoanApplicationDAO;
import com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLineOfCredit;
import com.vsoftcorp.kls.service.account.ILoanLineOfCreditService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.LoanLineOfCreditHelper;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.valuetypes.charges.ChargeType;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public class LoanLineOfCreditService implements ILoanLineOfCreditService {

	private static final Logger logger = Logger.getLogger(LoanLineOfCreditService.class);

	// Service to Get LineOfCreditId List By customerId

	@Override
	public List<LoanLineOfCreditData> getLineOfCreditList(Long customerId) {

		logger.info("Start: Get the loan loc list using customer id in getLineOfCreditList() method.");
		ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		List<LoanLineOfCreditData> dataList = new ArrayList<LoanLineOfCreditData>();
		try {
			List<LoanLineOfCredit> list = dao.getLoanLocListByCustomerId(customerId);
			List<LoanLineOfCredit> loanLocList = new ArrayList<LoanLineOfCredit>();
			for (LoanLineOfCredit loanLoc : list) {
				if (loanLoc.getLoanType() != null && loanLoc.getLoanType().equals("L")) {
					loanLocList.add(loanLoc);
				}
			}
			if (loanLocList.size() > 0) {
				dataList = LoanLineOfCreditHelper.getLoanLocIdList(loanLocList);
			}
		} catch (Exception e) {
			logger.error("Error while retriving the loan loc list based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the loan loc list based on cust id from the database");
		}
		logger.info("Start: Get the loan loc list using customer id in getLineOfCreditList() method.");
		return dataList;
	}

	// Service to Get LineOfCredit Data Using LineOfCreditId

	@Override
	public LoanLineOfCreditData getLineOfCreditDataById(Long loanLocId) {

		logger.info("Start: Get the loan line of credit data using loac id in getLineOfCreditDataById() method.");
		ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		LoanLineOfCreditData loanLocdata = new LoanLineOfCreditData();
		LoanLineOfCredit master = null;
		BigDecimal overdueAmt = BigDecimal.ZERO;
		try {
			master = dao.getLoanLineOfCreditById(loanLocId);
			if (master != null) {
				IPacsLoanApplicationDAO loanDao = KLSDataAccessFactory.getPacsLoanApplicationDAO();
				IChargesDAO chargesDao = KLSDataAccessFactory.getChargesDAO();
				PacsLoanApplication loanApplication = master.getPacsLoanApplication();
				if (loanApplication != null) {
					InterestCategory intCategory = loanApplication.getInterestCategory();
					if (intCategory != null) {
						List<Map> map = loanDao.getRateOfInterest(intCategory.getId(),
								loanApplication.getSanctionAmount(), loanApplication.getLoanPeriod());
						BigDecimal shareAmount = BigDecimal.ZERO;
						if (master.getCurrentBalance().isZero()) {
							shareAmount = LoanServiceUtil.calculateShareAmountToDeduct(loanApplication);
							List<Charges> list = chargesDao.getAnyCharges(loanLocId, ChargeType.SHARE);
							if (!list.isEmpty()) {
								Charges charges = list.get(0);
								charges.setChargeAmount(MoneyUtil.getAccountingMoney(shareAmount).getMoney());
								chargesDao.updateCharges(charges);
							}
						} else {
							shareAmount = getAmount(chargesDao, loanLocId, ChargeType.SHARE);
						}
						BigDecimal insuranceAmount = getAmount(chargesDao, loanLocId, ChargeType.INSURANCE);
						loanLocdata = LoanLineOfCreditHelper.getLoanLineOfCreditData(master, map);
						loanLocdata.setShareAmount(shareAmount.toString());
						loanLocdata.setInsuranceAmount(insuranceAmount.toString());
						overdueAmt = calculateOverdueAmount(master, LoanServiceUtil.getBusinessDate());
						loanLocdata.setOverdueAmount(overdueAmt);
						if (master.getCurrentBalance().isZero()) {
							loanLocdata.setIsDisbursed(Boolean.FALSE);
						} else {
							loanLocdata.setIsDisbursed(Boolean.TRUE);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error while retriving the loan line of credit data using loan loc id from the database");
			throw new KlsRuntimeException(
					"Error while retriving the loan line of credit using loan loc id from the database");
		}
		logger.info("End: Get the loan line of credit using id in getLineOfCreditDataById() method.");
		return loanLocdata;
	}

	/**
	 * 
	 * @param chargesDao
	 * @param loanLocId
	 * @param chargeType
	 * @return
	 */
	private BigDecimal getAmount(IChargesDAO chargesDao, Long loanLocId, ChargeType chargeType) {

		logger.info("Start: Fetching the charges record using loanLocId in getAmount() method.");
		BigDecimal value = null;
		Money sum = Money.ZERO;
		try {
			List<ChargesForLineOfCredit> chargesList = chargesDao.getCharges(loanLocId, chargeType);
			for (ChargesForLineOfCredit charge : chargesList) {
				sum = sum.add(charge.getChargeAmount());
			}
			value = sum.getAmount();
		} catch (Exception e) {
			logger.error("Error while retriving the charges record using loan loc id from the database");
			throw new KlsRuntimeException(
					"Error while retriving the charges record using loan loc id from the database");
		}
		logger.info("End: Fetching the charges record using loanLocId in getAmount() method.");
		return value;
	}

	/**
	 * 
	 * @param loanLocId
	 * @return
	 */
	public LoanLineOfCredit getLoanLineOfCreditById(Long loanLocId) {

		logger.info("Start: Get the loan line of credit using id in getLineOfCreditById() method.");
		ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		LoanLineOfCredit data = null;
		logger.info(" loanLocId : " + loanLocId);
		try {
			LoanLineOfCredit master = dao.getLoanLineOfCreditById(loanLocId);
			if (master == null) {
				data = new LoanLineOfCredit();
			}
		} catch (Exception e) {
			logger.error("Error while retriving the loan line of credit using loan loc id from the database");
			throw new KlsRuntimeException(
					"Error while retriving the loan line of credit using loan loc id from the database");
		}
		logger.info("End: Get the loan line of credit using id in getLineOfCreditById() method.");
		return data;
	}

	@Override
	public List<LoanLineOfCreditData> getDisbursedLineOfCreditList(Long customerId) {

		logger.info("Start: Get the loan loc list using customer id in getDisbursedLineOfCreditList() method.");
		ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		List<LoanLineOfCreditData> dataList = new ArrayList<LoanLineOfCreditData>();
		try {
			List<LoanLineOfCredit> list = dao.getDisbursedLocList(customerId);
			if (list.size() > 0)
				dataList = LoanLineOfCreditHelper.getLoanLocIdList(list);
		} catch (Exception e) {
			logger.error("Error while retriving the loan loc list based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the loan loc list based on cust id from the database");
		}
		logger.info("Start: Get the loan loc list using customer id in getDisbursedLineOfCreditList() method.");
		return dataList;
	}

	@Override
	public List<Map> getDisbursedLineOfCreditIdsList(Long customerId) {

		logger.info("Start: Get the loan loc list using customer id in getDisbursedLineOfCreditList() method.");
		ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		List<Map> listMap = new ArrayList<>();
		try {
			List<LoanLineOfCredit> list = dao.getDisbursedLocList(customerId);

			Map<String, String> map = null;
			for (LoanLineOfCredit loc : list) {
				map = new HashMap<>();
				map.put("locId", loc.getId().toString());
				listMap.add(map);
			}

		} catch (Exception e) {
			logger.error("Error while retriving the loan loc list based on cust id from the database");
			throw new KlsRuntimeException("Error while retriving the loan loc list based on cust id from the database");
		}
		logger.info("Start: Get the loan loc list using customer id in getDisbursedLineOfCreditList() method.");
		return listMap;
	}

	/**
	 * 
	 */
	public LoanLineOfCreditData getLineOfCreditDataForClosure(Long loanLocId) {

		logger.info("Start: Get the loan line of credit data for closure using loc id in getLineOfCreditDataForClosure() method.");
		LoanLineOfCreditData loanLocData = getLineOfCreditDataById(loanLocId);
		ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		LoanLineOfCredit master = null;
		LoanAccountClosureData closureData = new LoanAccountClosureData();
		try {
			master = dao.getLoanLineOfCreditById(loanLocId);
			populateAccountClosureData(master, loanLocData, closureData);
		} catch (Exception excp) {
			logger.error("Error while retriving the loan line of credit data using loan loc id from the database");
			throw new KlsRuntimeException("Error while retriving the loan line of credit using loan loc id.");
		}
		logger.info("End: Get the loan line of credit data for closure using loc id in getLineOfCreditDataForClosure() method.");
		return loanLocData;
	}

	/**
	 * 
	 * @param master
	 * @param loanLocData
	 * @param closureData
	 */
	private void populateAccountClosureData(LoanLineOfCredit master, LoanLineOfCreditData loanLocData,
			LoanAccountClosureData closureData) {

		logger.info("Start: Populating the account closure data in populateAccountClosureData() method.");
		if (master != null) {
			AccountingMoney currentBalance = master.getCurrentBalance();
			if (currentBalance == null) {
				currentBalance = MoneyUtil.getAccountingMoney(Money.ZERO);
			}
			closureData.setLoanOutstandingAmount(currentBalance.getMoney().getAmount().setScale(2, RoundingMode.FLOOR)
					.toString());
			BigDecimal overdueInterest = master.getOverdueInterest();
			if (overdueInterest == null || overdueInterest.compareTo(BigDecimal.ZERO) > 0) {
				overdueInterest = BigDecimal.ZERO;
			} else if (overdueInterest.compareTo(BigDecimal.ZERO) < 0) {
				overdueInterest = overdueInterest.abs();
			}
			closureData.setInterestDue(overdueInterest.setScale(2, RoundingMode.FLOOR).toString());
			BigDecimal interestReceivable = master.getInterestReceivable();
			if (interestReceivable == null || interestReceivable.compareTo(BigDecimal.ZERO) > 0) {
				interestReceivable = BigDecimal.ZERO;
			} else if (interestReceivable.compareTo(BigDecimal.ZERO) < 0) {
				//interestReceivable = interestReceivable;
			}
			interestReceivable =interestReceivable.add(master.getInterestAccrued()).abs();
			closureData.setInterestReceivable(interestReceivable.setScale(2, RoundingMode.FLOOR).toString());
			BigDecimal penalInterest = master.getPenalInterestReceivable();
			if (penalInterest == null || penalInterest.compareTo(BigDecimal.ZERO) > 0) {
				penalInterest = BigDecimal.ZERO;
			} else if (penalInterest.compareTo(BigDecimal.ZERO) < 0) {
				penalInterest = penalInterest.abs();
			}
			closureData.setPenalInterest(penalInterest.setScale(2, RoundingMode.FLOOR).toString());
			Money otherCharges = getOtherCharges(master);
			if (otherCharges.getAmount().compareTo(BigDecimal.ZERO) < 0) {
				otherCharges = MoneyUtil.getAccountingMoney(otherCharges.getAmount(), DebitOrCredit.CREDIT).getMoney();
			}
			closureData.setOtherCharges(otherCharges.getAmount().setScale(2, RoundingMode.FLOOR).toString());
			BigDecimal totalCharges = getTotalDues(currentBalance, overdueInterest, interestReceivable, penalInterest,
					otherCharges);
			closureData.setTotalDues(totalCharges.setScale(2, RoundingMode.FLOOR).toString());
			closureData.setTransactionAmount(totalCharges.setScale(2, RoundingMode.FLOOR).toString());
			loanLocData.setClosureData(closureData);
		}
		logger.info("End: Populating the account closure data in populateAccountClosureData() method.");
	}

	/**
	 * 
	 * @param master
	 * @return
	 */
	private Money getOtherCharges(LoanLineOfCredit master) {

		logger.info("Start: Adding the charges amount in getOtherCharges() method.");
		IChargesDebitDAO debitDao = KLSDataAccessFactory.getChargesDebitDAO();
		List<ChargesDebit> chargesDebitList = debitDao.getAllChargesDebit(master.getId());
		Money sum = Money.ZERO;
		for (ChargesDebit cDebit : chargesDebitList) {
			Money amount = cDebit.getAmount();
			if (amount != null) {
				sum = sum.add(amount);
			}
		}
		logger.info(" Charges amount : " + sum);
		logger.info("End: Adding the charges amount in getOtherCharges() method.");
		return sum;
	}

	/**
	 * 
	 * @param currentBalance
	 * @param overdueInterest
	 * @param interestReceivable
	 * @param penalInterest
	 * @param otherCharges
	 * @return
	 */
	private BigDecimal getTotalDues(AccountingMoney currentBalance, BigDecimal overdueInterest,
			BigDecimal interestReceivable, BigDecimal penalInterest, Money otherCharges) {

		logger.info("Start: Calculating the total dues amount in getTotalDues() method.");
		BigDecimal totalDues = currentBalance.getMoney().getAmount().add(overdueInterest).add(interestReceivable)
				.add(penalInterest).add(otherCharges.getAmount());
		logger.info(" total dues : " + totalDues);
		logger.info("End: Calculating the total dues amount in getTotalDues() method.");
		return totalDues;
	}

	private BigDecimal calculateOverdueAmount(LineOfCredit loc, Date businessDate) {
		BigDecimal overDueAmount = BigDecimal.ZERO;

		ILoanInterestCalculationDAO iDao = KLSDataAccessFactory.getLoanInterestCalculationDAO();

		Map map = iDao.getPrincipleAndInterestAmtByLocId(loc.getId(), businessDate);
		if (map != null) {
			Money totalPrincipleReceivableAmt = (Money) map.get("princlipleAmount");

			if (totalPrincipleReceivableAmt != null) {
				Money totalPrincipleReceivedAmt = totalPrincipleReceivableAmt.subtract(loc.getCurrentBalance()
						.getMoney());

				BigDecimal totalInterestReceivableAmt = loc.getInterestReceivable();
				if (totalInterestReceivableAmt == null)
					totalInterestReceivableAmt = BigDecimal.ZERO;

				overDueAmount = (totalPrincipleReceivableAmt.subtract(totalPrincipleReceivedAmt)).getAmount().add(
						totalInterestReceivableAmt);
			}
		}
		return overDueAmount;
	}

	public LoanLineOfCreditData getLineOfCreditForChargeDebit(Long loanLocId) {

		logger.info("Start: Get the loan line of credit data using loac id in getLineOfCreditDataById() method.");
		ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
		LoanLineOfCreditData loanLocdata = new LoanLineOfCreditData();
		LoanLineOfCredit master = null;
		BigDecimal overdueAmt = BigDecimal.ZERO;
		BigDecimal outstandingBalance = BigDecimal.ZERO;
		try {
			master = dao.getLoanLineOfCreditById(loanLocId);
			if (master != null) {
				IPacsLoanApplicationDAO loanDao = KLSDataAccessFactory.getPacsLoanApplicationDAO();
				IChargesDAO chargesDao = KLSDataAccessFactory.getChargesDAO();
				PacsLoanApplication loanApplication = master.getPacsLoanApplication();
				if (loanApplication != null) {
					InterestCategory intCategory = loanApplication.getInterestCategory();
					if (intCategory != null) {
						List<Map> map = loanDao.getRateOfInterest(intCategory.getId(),
								loanApplication.getSanctionAmount(), loanApplication.getLoanPeriod());
						String shareAmount = getAmount(chargesDao, loanLocId, ChargeType.SHARE).toString();
						String insuranceAmount = getAmount(chargesDao, loanLocId, ChargeType.INSURANCE).toString();
						loanLocdata = LoanLineOfCreditHelper.getLoanLineOfCreditData(master, map);
						loanLocdata.setShareAmount(shareAmount);
						loanLocdata.setInsuranceAmount(insuranceAmount);
						overdueAmt = calculateOverdueAmount(master, LoanServiceUtil.getBusinessDate());
						loanLocdata.setOverdueAmount(overdueAmt);
					}
				}
				outstandingBalance = getOutstandingBalance(master);
				if (outstandingBalance.compareTo(BigDecimal.ZERO) != 0) {
					loanLocdata.setOutstandingBalance(outstandingBalance.setScale(2));
				}
			}
		} catch (Exception e) {
			logger.error("Error while retriving the loan line of credit data using loan loc id from the database");
			throw new KlsRuntimeException(
					"Error while retriving the loan line of credit using loan loc id from the database");
		}
		logger.info("End: Get the loan line of credit using id in getLineOfCreditDataById() method.");
		return loanLocdata;
	}

	public BigDecimal getOutstandingBalance(LoanLineOfCredit loanLineOfCredit) {
		BigDecimal principalPaid = BigDecimal.ZERO;
		BigDecimal outStandingBalance = BigDecimal.ZERO;
		ILoanRecoveryDAO loanRecoveryDAO = KLSDataAccessFactory.getLoanRecoveryDAO();
		List<LoanRecovery> loanRecoveryList = loanRecoveryDAO.getLoanRecoveryByLocId(loanLineOfCredit.getId());
		logger.info("loanRecovery size : " + loanRecoveryList.size());

		if (loanRecoveryList.size() > 0) {
			for (LoanRecovery l : loanRecoveryList) {
				principalPaid = principalPaid.add(l.getPrincipalPaid().getAmount());
			}
			if (principalPaid != null) {
				outStandingBalance = loanLineOfCredit.getSanctionedAmount().getAmount().subtract(principalPaid)
						.setScale(2);
			} else {
				outStandingBalance = loanLineOfCredit.getSanctionedAmount().getAmount().setScale(2);
			}
		}
		return outStandingBalance.abs();
	}

}