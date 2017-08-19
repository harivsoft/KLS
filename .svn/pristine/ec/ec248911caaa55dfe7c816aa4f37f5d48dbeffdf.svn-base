/**
 * 
 */
package com.vsoftcorp.kls.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.SeasonParameter;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareAccountData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.result.classes.ChargesForLineOfCredit;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public class LoanServiceUtil {

	private static final Logger logger = Logger.getLogger(LoanServiceUtil.class);

	public static Money calculateSanctionAmount(Money... vargs) {

		Money sanctionedAmount = Money.ZERO;
		if (vargs.length > 0) {
			Money minAmount = vargs[0];
			for (Money arg : vargs) {
				if (arg != null) {
					minAmount = Money.lesser(arg, minAmount);
				}
			}
			sanctionedAmount = minAmount;
		}
		return sanctionedAmount;
	}

	public static Money getLoanLimitPerFarmer() {
		List<BankParameter> bankParameterList = KLSDataAccessFactory.getBankParameterDAO().getAllBankParameters();
		BankParameter bankParameter = new BankParameter();
		if (bankParameterList != null && !bankParameterList.isEmpty()) {
			bankParameter = bankParameterList.get(0);
		}
		return bankParameter.getLoanLimitPerFarmer();
	}

	public static Date getBusinessDate() {
		List<BankParameter> bankParameterList = KLSDataAccessFactory.getBankParameterDAO().getAllBankParameters();
		BankParameter bankParameter = new BankParameter();
		if (bankParameterList != null && !bankParameterList.isEmpty()) {
			bankParameter = bankParameterList.get(0);
		}
		return bankParameter.getBusinessDate();
	}

	public static Money getMaxShareAmountPerFarmer() {
		List<BankParameter> bankParameterList = KLSDataAccessFactory.getBankParameterDAO().getAllBankParameters();
		BankParameter bankParameter = new BankParameter();
		if (bankParameterList != null && !bankParameterList.isEmpty()) {
			bankParameter = bankParameterList.get(0);
		}
		return bankParameter.getMaxShareAmount();
	}

	/**
	 * 
	 * @param pacsLoanApplicationDetail
	 * @return
	 */
	public static Money calculateInsuranceAmount(PacsLoanApplicationDetail pacsLoanApplicationDetail) {
		Money insuranceAmount = Money.ZERO;
		try {

			SeasonParameter seasonParameter = KLSDataAccessFactory.getSeasonParameterDAO().getSeasonParameter(
					pacsLoanApplicationDetail.getSeasonId().getId(), pacsLoanApplicationDetail.getCropId().getId(),
					pacsLoanApplicationDetail.getHeaderId().getPacs().getId());
			if (seasonParameter != null) {
				BigDecimal insurancePercentage = seasonParameter.getInsuranceByFarmer();

				if (insurancePercentage != null && pacsLoanApplicationDetail.getSanctionedAmount() != null)
					insuranceAmount = (pacsLoanApplicationDetail.getSanctionedAmount().multiply(insurancePercentage.floatValue())).divide(100);
				// pacsLoanApplicationDetail.setInsuranceAmount(insuranceAmount);
			}

		} catch (ArithmeticException e) {
			return Money.ZERO;
		} catch (Exception e) {
			throw new KlsRuntimeException("Error occured while calculating Insurance amount");
		}
		return insuranceAmount;
	}

	/**
	 * 
	 * @param pacsLoanApplicationDetail
	 * @return
	 */
	public static Money calculateShareAmount(PacsLoanApplicationDetail pacsLoanApplicationDetail) {
		Money shareAmount = Money.ZERO;
		try {
			// int testVal = 0;
			// Changed as part story SUB-843. Done
//hardcoding prod id
			//pacsLoanApplicationDetail.getHeaderId().getScheme().getProduct()
			
			BigDecimal sharePercentage = KLSDataAccessFactory.getProductMasterDAO()
					.getProduct(pacsLoanApplicationDetail.getHeaderId().getProduct(), false).getSharePercentage();

			if (sharePercentage != null && pacsLoanApplicationDetail.getSanctionedAmount() != null) {
				shareAmount = pacsLoanApplicationDetail.getSanctionedAmount().multiply(sharePercentage.floatValue()).divide(100);

				/*
				 * testVal = shareAmount.compareTo(shareBalance); if (testVal ==
				 * -1 || testVal == 0) { shareAmount = Money.ZERO; } else {
				 * shareAmount = shareAmount.subtract(shareBalance); }
				 */
			}
			pacsLoanApplicationDetail.setShareAmount(shareAmount);
		} catch (ArithmeticException e) {
			return Money.ZERO;
		} catch (Exception e) {
			throw new KlsRuntimeException("Error occured while calculating share amount");
		}
		return shareAmount;
	}

	/**
	 * 
	 * @param pacsLoanApplicationDetail
	 * @return
	 */
	public static Money calculateInsuranceSubsidyAmount(PacsLoanApplicationDetail pacsLoanApplicationDetail) {
		Money insuranceSubsidyAmount = Money.ZERO;
		try {

			SeasonParameter seasonParameter = KLSDataAccessFactory.getSeasonParameterDAO().getSeasonParameter(
					pacsLoanApplicationDetail.getSeasonId().getId(), pacsLoanApplicationDetail.getCropId().getId(),
					pacsLoanApplicationDetail.getHeaderId().getPacs().getId());
			if (seasonParameter != null) {
				BigDecimal insurancePercentage = seasonParameter.getInsuranceSubsidy();

				if (insurancePercentage != null && pacsLoanApplicationDetail.getInsuranceAmount() != null)
					insuranceSubsidyAmount = (pacsLoanApplicationDetail.getInsuranceAmount().multiply(insurancePercentage.floatValue())).divide(100);
			}

		} catch (ArithmeticException e) {
			return Money.ZERO;
		} catch (Exception e) {
			throw new KlsRuntimeException("Error occured while calculating Insurance Subsidy amount");
		}
		return insuranceSubsidyAmount;
	}

	/**
	 * 
	 * @param pacsLoanApplication
	 * @return
	 */
	public static BigDecimal calculateShareAmountToDeduct(PacsLoanApplication pacsLoanApplication) {
		Money shareAmountOnLoanAccount = Money.ZERO;
		Money shareAmountToBeDeducted = Money.ZERO;
		Money shareBalance = Money.ZERO;
		Money maxShareAmountOfProduct = Money.ZERO;
		int testVal = 0;
		int testVal1 = 0;
		try {
			BigDecimal sharePercentage = pacsLoanApplication.getProduct().getSharePercentage();
			if (sharePercentage != null && sharePercentage.compareTo(BigDecimal.ZERO) != 0) {
				logger.info("***sharePercentage : " + sharePercentage);
				if (sharePercentage != null && pacsLoanApplication.getSanctionAmount() != null)
					shareAmountOnLoanAccount = pacsLoanApplication.getSanctionAmount().multiply(sharePercentage.floatValue()).divide(100);
				logger.info("shareAmountOnLoanAccount--" + shareAmountOnLoanAccount);
				if (pacsLoanApplication.getProduct().getMaxShareAmount() != null)
					maxShareAmountOfProduct = pacsLoanApplication.getProduct().getMaxShareAmount();
				logger.info("maxShareAmountOfProduct--" + maxShareAmountOfProduct);
				testVal = shareAmountOnLoanAccount.compareTo(maxShareAmountOfProduct);
				logger.info("testVal==" + testVal);
				if (testVal == 1) {
					shareAmountToBeDeducted = maxShareAmountOfProduct;
				} else {
					shareAmountToBeDeducted = shareAmountOnLoanAccount;
				}

				Money maxShareAmountPerFarmer = LoanServiceUtil.getMaxShareAmountPerFarmer();
				logger.info("maxShareAmountPerFarmer bank :" + maxShareAmountPerFarmer);

				// Share balance to fetched with share call. story sub-843. Done
				// if (pacsLoanApplication.getCustomer().getShareBalance() !=
				// null)
				// shareBalance =
				// pacsLoanApplication.getCustomer().getShareBalance();
				ShareAccountData shareAccountData = RestClientUtil.getMemberShareAccountByCustomerId(pacsLoanApplication.getCustomerId());
				shareBalance = Money.valueOf("INR", BigDecimal.valueOf(shareAccountData.getCurrentBalance()));

				if (shareBalance == null)
					shareBalance = Money.ZERO;

				logger.info("shareBalance cust :" + shareBalance);
				ILoanLineOfCreditDAO lDao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
				ILineOfCreditDAO locDao = KLSDataAccessFactory.getLineOfCreditDAO();

				List<LoanLineOfCredit> loanLocsList = lDao.getLoanLocListByCustomerId(pacsLoanApplication.getCustomerId());
				List<LineOfCredit> locList = locDao.getLocListByCustomerId(pacsLoanApplication.getCustomerId());
				for (LoanLineOfCredit l : loanLocsList) {
					locList.add(l);
				}
				logger.info("locList size : " + locList.size());
				Money totShareBalance = Money.ZERO;
				for (LineOfCredit l : locList) {
					sharePercentage = l.getProduct().getSharePercentage();
					totShareBalance = totShareBalance.add(l.getCurrentBalance().getMoney().multiply(sharePercentage.floatValue()).divide(100));

				}

				// loc current balace
				Money idealShareAmountOnLoanAccount = totShareBalance.add(shareAmountToBeDeducted);
				/*
				 * Money idealShareAmountOnLoanAccount =
				 * loanOutStandingBalance.multiply
				 * (sharePercentage.floatValue()).divide( 100);
				 */
				testVal = idealShareAmountOnLoanAccount.compareTo(maxShareAmountPerFarmer);
				if (testVal == 1) {
					idealShareAmountOnLoanAccount = maxShareAmountPerFarmer;

				}

				testVal1 = idealShareAmountOnLoanAccount.compareTo(shareBalance);
				if (testVal1 == -1 || testVal1 == 0) {
					shareAmountToBeDeducted = Money.ZERO;
				} else {
					shareAmountToBeDeducted = idealShareAmountOnLoanAccount.subtract(shareBalance);
				}

				int val = (int) Math.ceil(shareAmountToBeDeducted.getAmount().doubleValue() / shareAccountData.getFaceValue().intValue());

				shareAmountToBeDeducted = MoneyUtil.getAccountingMoney(new BigDecimal(val * shareAccountData.getFaceValue())).getMoney();

			}
		}/**
		 * catch (ArithmeticException e) {
		 * 
		 * return Money.ZERO.getAmount(); }
		 **/
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DataAccessException("error while calculating share amount to be deducted", e.getCause());
		}

		return shareAmountToBeDeducted.getAmount().setScale(2);
	}

	/**
	 * 
	 * @param amount
	 * @return
	 */
	public static long getNearestRoundedTenRupee(long amount) {
		long amountRoundedToTenRupee = 0;
		try {
			amountRoundedToTenRupee = (long) (Math.rint((long) amount / 10) * 10);

		} catch (ArithmeticException e) {
			return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return amountRoundedToTenRupee;
	}

	/**
	 * 
	 * @param pacsLoanApplication
	 * @return
	 */
	public static String getInstallmentAmount(PacsLoanApplication pacsLoanApplication) {

		String installmentAmount = "0.00";
		if (pacsLoanApplication.getSanctionAmount() != null && pacsLoanApplication.getNoOfInstallments() != null) {
			int amount = pacsLoanApplication.getSanctionAmount().getAmount().intValue() / pacsLoanApplication.getNoOfInstallments();
			logger.info("amount :" + amount);
			installmentAmount = String.valueOf(LoanServiceUtil.getNearestRoundedTenRupee(amount));
		}
		return installmentAmount;
	}
	
	
	public static Map<Long,Money> getShareAmountWhileDisbursement(List<LineOfCredit> firstDrawlLocList) {

		List<PacsLoanApplicationDetail> list = new ArrayList<PacsLoanApplicationDetail>();
		Money maxShareAmount = LoanServiceUtil.getMaxShareAmountPerFarmer();
		AccountingMoney finalShareAmt=AccountingMoney.ZERO;
		Money totalShareAmount = Money.ZERO;
		Money shareBalance = Money.ZERO;
		Map<Long,Money> shareMap = new HashMap<Long,Money>();
		for (LineOfCredit loc : firstDrawlLocList) {
			totalShareAmount = totalShareAmount.add(LoanServiceUtil.getShareAmountByProduct(loc));
		}

		if (maxShareAmount != null) {
			if (!firstDrawlLocList.isEmpty()) {
				
				LineOfCredit shareLOC = KLSDataAccessFactory.getLineOfCreditDAO().getLocId(firstDrawlLocList.get(0).getId());
				//hardcoding prod id
				//pacsLoanApplDtl.getHeaderId().getScheme().getProduct()
				
				Money maxShareAmountProuctWise = shareLOC.getProduct().getMaxShareAmount();
				if (maxShareAmountProuctWise != null)
					if (maxShareAmount.compareTo(maxShareAmountProuctWise) == 1)
						maxShareAmount = maxShareAmountProuctWise;

				Long customerId = shareLOC.getCustomerId();

				ILoanLineOfCreditDAO lDao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
				ILineOfCreditDAO locDao = KLSDataAccessFactory.getLineOfCreditDAO();
				List<LoanLineOfCredit> loanLocsList = lDao.getLoanLocListByCustomerId(customerId);
				List<LineOfCredit> locList = locDao.getLocListByCustomerId(customerId);
				for (LoanLineOfCredit l : loanLocsList) {
					locList.add(l);
				}
				logger.info("locList size : " + locList.size());
				Money totShareBalance = Money.ZERO;
				for (LineOfCredit l : locList) {
					BigDecimal sharePercentage = l.getProduct().getSharePercentage();
					totShareBalance = totShareBalance.add(l.getCurrentBalance().getMoney().multiply(sharePercentage.floatValue()).divide(100));

				}

				totalShareAmount = totalShareAmount.add(totShareBalance);

				int testVal = 0;

				ShareAccountData shareAccountData = RestClientUtil.getMemberShareAccountByCustomerId(customerId);
				if (shareAccountData.getCurrentBalance() != null)
					shareBalance = Money.valueOf("INR", BigDecimal.valueOf(shareAccountData.getCurrentBalance()));

				if (shareBalance == null)
					shareBalance = Money.ZERO;
				testVal = maxShareAmount.compareTo(shareBalance);
				if (testVal == -1 && testVal == 0)
					maxShareAmount = Money.ZERO;
				else
					maxShareAmount = maxShareAmount.subtract(shareBalance);
				if (maxShareAmount.compareTo(Money.ZERO) == -1)
					maxShareAmount = Money.ZERO;

				int testVal1 = totalShareAmount.compareTo(maxShareAmount);
				Integer faceValue = shareAccountData.getFaceValue().intValue();

				if (testVal1 == -1 || testVal1 == 0) {
					for (LineOfCredit l : firstDrawlLocList) {
						Money shareAmount = LoanServiceUtil.getShareAmountByProduct(l);
						int val = (int) Math.ceil(shareAmount.getAmount().doubleValue() / faceValue);
						shareAmount = MoneyUtil.getAccountingMoney(new BigDecimal(val * faceValue)).getMoney();
						shareMap.put(l.getId(), shareAmount);
						finalShareAmt=finalShareAmt.add(MoneyUtil.getAccountingMoney(shareAmount));
					}
					shareMap.put(0l, finalShareAmt.getMoney());//Here Zero indicates final share amount as LOCID cannot be zero
				} else {
					Money totalSharingAmount = Money.ZERO;
					int testVal2 = 0;
					for (LineOfCredit l1 : firstDrawlLocList) {
						Money shareAmount = LoanServiceUtil.getShareAmountByProduct(l1);
						testVal2 = totalSharingAmount.add(shareAmount).compareTo(maxShareAmount);
						if (testVal2 == -1 || testVal2 == 0) {
							int val = (int) Math.ceil(shareAmount.getAmount().doubleValue() / faceValue);
							shareAmount = MoneyUtil.getAccountingMoney(new BigDecimal(val * faceValue)).getMoney();
							shareMap.put(l1.getId(), shareAmount);
							totalSharingAmount = totalSharingAmount.add(shareAmount);
							
						} else if (testVal2 == 1) {
							int val = (int) Math.ceil(shareAmount.getAmount().doubleValue() / faceValue);
							shareAmount = MoneyUtil.getAccountingMoney(new BigDecimal(val * faceValue)).getMoney();
							shareAmount = maxShareAmount.subtract(totalSharingAmount);
							shareMap.put(l1.getId(), shareAmount);
							totalSharingAmount = totalSharingAmount.add(shareAmount);
						}
						finalShareAmt=finalShareAmt.add(MoneyUtil.getAccountingMoney(shareAmount));
						shareMap.put(0l, finalShareAmt.getMoney());
					}
				}
			}
		}
		return shareMap;
	}
	
	public static Money getShareAmountByProduct(LineOfCredit loc) {
		Money shareAmount = Money.ZERO;
		try {
			
			LineOfCredit updatedLoc= KLSDataAccessFactory.getLineOfCreditDAO().getLocId(loc.getId());
			BigDecimal sharePercentage = updatedLoc.getProduct().getSharePercentage();

			if (sharePercentage != null && loc.getSanctionedAmount() != null) {
				shareAmount = loc.getSanctionedAmount().multiply(sharePercentage.floatValue()).divide(100);
			}
		} catch (ArithmeticException e) {
			return Money.ZERO;
		} catch (Exception e) {
			throw new KlsRuntimeException("Error occured while calculating share amount");
		}
		return shareAmount;
	}

	public static List<ChargesForLineOfCredit> getUpdatedShareAmts(List<ChargesForLineOfCredit> sharesFromCharges,Map<Long,Money> updatedShareAmts){
		
		List<ChargesForLineOfCredit>  shareAmtList = new ArrayList<ChargesForLineOfCredit>();
		
		for(ChargesForLineOfCredit c : sharesFromCharges){
			c.setChargeAmount(updatedShareAmts.get(c.getLineOfCreditId()));
			shareAmtList.add(c);
		}
		return shareAmtList;
	}
	
}
