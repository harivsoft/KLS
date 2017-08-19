package com.vsoftcorp.kls.service.loan.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.SeasonParameter;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.account.Charges;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.LoanSanctionData;
import com.vsoftcorp.kls.data.LoanSanctionSummaryData;
import com.vsoftcorp.kls.data.PacsLoanApplicationDetailData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareAccountData;
import com.vsoftcorp.kls.dataaccess.IBankParameterDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationDetailDAO;
import com.vsoftcorp.kls.dataaccess.ISeasonParameterDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.IPacsLoanSanctionProcessDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.PacsLoanApplicationDetailHelper;
import com.vsoftcorp.kls.service.loan.IPacsLoanSanctionProcessService;
import com.vsoftcorp.kls.service.util.AccountServiceUtil;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.valuetypes.AccountStatus;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.charges.ChargeType;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 */
public class PacsLoanSanctionProcessService implements IPacsLoanSanctionProcessService {

	private static final Logger logger = Logger.getLogger(PacsLoanSanctionProcessService.class);

	@Override
	public List<LoanSanctionData> getLoanSanctionDetails(String pacId, String financialYear,Integer pageIndex) {
		logger.info("Start : Calling pacs loan sanction  detail dao in getLoanSanctionDetails() method.");
		List<LoanSanctionData> sancationDataList = new ArrayList<LoanSanctionData>();
		try {
			List<PacsLoanApplicationDetail> list = KLSDataAccessFactory.getPacsLoanSanctionProcessDAO().getPacsLoanInspectedApplicationList(pacId,
					financialYear,pageIndex);
			logger.info("PacsLoanApplicationDetail:---" + list);
			if (list != null) {
				Set<Long> customerIdSet = new HashSet<Long>();

				for (PacsLoanApplicationDetail pacsLoanApplicationDetail : list) {
					if (pacsLoanApplicationDetail.getApplicationStatus().getValue() == LoanApplicationState.INSPECTED.getValue())
						customerIdSet.add(pacsLoanApplicationDetail.getCustomerId());
				}
				logger.info("customerIdSet:---" + customerIdSet);
				Money sanctionAmount = null;
				// Getting loan limit Per former
				Money loanLimitPerFarmer = LoanServiceUtil.getLoanLimitPerFarmer();
				logger.info("loanLimitPerFarmer:---" + loanLimitPerFarmer);
				Date businessDate = LoanServiceUtil.getBusinessDate();

				Date financialYearBeginDate = DateUtil.getFinancialYearBeginDate(businessDate);
				Date financialYearEndDate = DateUtil.getFinancialYearEndDate(businessDate);
				PersonData customer = null;
				for (Long customerId : customerIdSet) {

					sanctionAmount = Money.ZERO;

					// Getting already sanctioned amount for the customer
					Money totalSanctionedAmount = KLSDataAccessFactory.getLineOfCreditDAO().getTotalSanctionAmount(customerId,
							financialYearBeginDate, financialYearEndDate);

					logger.info("totalSanctionedAmount:-----" + totalSanctionedAmount);

					LoanSanctionData loanSanctionData = new LoanSanctionData();

					for (PacsLoanApplicationDetail pacsLoanApplicationDetail : list) {

						if (customerId.longValue() == pacsLoanApplicationDetail.getCustomerId().longValue()) {

							customer = RestClientUtil.getCustomerById(customerId);
							loanSanctionData.setCustomerName(customer.getName());
							loanSanctionData.setMemberNumber(customer.getMemberNumber());

							if (pacsLoanApplicationDetail.getApplicationStatus().getValue() == LoanApplicationState.INSPECTED.getValue()) {
								if (pacsLoanApplicationDetail.getSanctionedAmount() == null)
									sanctionAmount = sanctionAmount.add(calculateSanctionAmount(pacsLoanApplicationDetail));
								else
									sanctionAmount = sanctionAmount.add(pacsLoanApplicationDetail.getSanctionedAmount());
								// loanSanctionData.setCustomerName(pacsLoanApplicationDetail.getCustomerId().getName());
							}
						}
					}

					// checking sanctioned amount with cap limit
					if (loanLimitPerFarmer != null) {
						if (sanctionAmount.compareTo(loanLimitPerFarmer) == 1) {
							sanctionAmount = loanLimitPerFarmer;
						}

						if (totalSanctionedAmount != null) {
							if (totalSanctionedAmount.add(sanctionAmount).compareTo(loanLimitPerFarmer) == 1)
								sanctionAmount = loanLimitPerFarmer.subtract(totalSanctionedAmount);
						}

					}
					if (sanctionAmount.compareTo(Money.ZERO) == -1)
						sanctionAmount = Money.ZERO;

					loanSanctionData.setSanctionedAmount(sanctionAmount.getAmount().toString());

					loanSanctionData.setCustomerId(customerId.toString());

					sancationDataList.add(loanSanctionData);
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error in retrieving all the pacs loan sanction  detail records");
			throw new KlsRuntimeException("Error in retrieving all the pacs loan sanction detail records", exception.getCause());
		}
		logger.info("End : Calling pacs loan sanction  detail dao in getLoanSanctionDetails() method.");
		return sancationDataList;
	}

	@Override
	public PacsLoanApplicationDetailData getPacsLoanApplicationByCustomer(String customerId, String pacId, String financialYear,
			Money sanctionedAmount) {
		logger.info("Start : Calling pacs loan application detail dao in getPacsLoanApplicationByCustomer() method.");
		logger.info("sanctionedAmount:" + sanctionedAmount);
		PacsLoanApplicationDetailData loanApplicationDetailData = null;
		try {
			List<PacsLoanApplicationDetail> detailList = KLSDataAccessFactory.getPacsLoanSanctionProcessDAO().getPacsLoanApplicationByCustomer(
					customerId, pacId, financialYear);
			List<PacsLoanApplicationDetail> inspectedApplicationDetailList = new ArrayList<PacsLoanApplicationDetail>();
			List<PacsLoanApplicationDetail> allApplicationList = new ArrayList<PacsLoanApplicationDetail>();

			for (PacsLoanApplicationDetail pacsLoanApplicationDetail : detailList) {
				if (pacsLoanApplicationDetail.getApplicationStatus().getValue() == LoanApplicationState.INSPECTED.getValue())
					inspectedApplicationDetailList.add(pacsLoanApplicationDetail);
			}
			List<PacsLoanApplicationDetail> list = setSanctionAmount(inspectedApplicationDetailList, sanctionedAmount);
			list = setInsuranceAmount(list);
			list = setShareAmount(list);
			allApplicationList.addAll(list);
			for (PacsLoanApplicationDetail pacsLoanApplicationDetail : detailList) {
				if (pacsLoanApplicationDetail.getApplicationStatus().getValue() == LoanApplicationState.SANCTIONED.getValue())
					allApplicationList.add(pacsLoanApplicationDetail);
			}
			loanApplicationDetailData = PacsLoanApplicationDetailHelper.getPacsLoanApplicationDetailData(allApplicationList);
		} catch (Exception exception) {
			logger.error("Error in retrieving all the pacs loan application detail records by Customer Id");
			throw new KlsRuntimeException("Error in retrieving all the pacs loan application detail records by Customer Id", exception.getCause());
		}
		return loanApplicationDetailData;
	}

	@Override
	public void updateLoanSanction(LoanSanctionData loanSanctionData) {
		try {

			String customerId = loanSanctionData.getCustomerId();
			Money sanctionedAmount = Money.valueOf(Currency.getInstance("INR"), loanSanctionData.getSanctionedAmount());
			logger.info("Start:calling PacsLoanSanctionProcessDAO in updateLoanSanction()");
			IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
			List<PacsLoanApplicationDetail> loanApplicationDetailList = KLSDataAccessFactory.getPacsLoanSanctionProcessDAO()
					.getPacsLoanApplicationByCustomer(customerId, loanSanctionData.getPacId(), loanSanctionData.getFinancialYear());
			IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
			List<PacsLoanApplicationDetail> inspectedApplicationDetailList = new ArrayList<PacsLoanApplicationDetail>();

			for (PacsLoanApplicationDetail pacsLoanApplicationDetail : loanApplicationDetailList) {
				if (pacsLoanApplicationDetail.getApplicationStatus().getValue() == LoanApplicationState.INSPECTED.getValue()) {
					inspectedApplicationDetailList.add(pacsLoanApplicationDetail);
				}
			}
			List<PacsLoanApplicationDetail> list = setSanctionAmount(inspectedApplicationDetailList, sanctionedAmount);

			list = setShareAmount(list);
			list = setInsuranceAmountAtSantion(list);

			if (loanSanctionData.getStatus().equals("1")) { // "1" indicates
															// sanction
				// Creatting account for customer
				createAccount(customerId, loanSanctionData.getPacId());
				Account account = KLSDataAccessFactory.getAccountDAO().getAccountByCustomer(customerId);

				for (PacsLoanApplicationDetail pacsLoanApplicationDetail : list) {
					String businessDate="";
					PersonData person=RestClientUtil.getCustomerById(Long.parseLong(loanSanctionData.getCustomerId()));
					Pacs pacs=pacsDao.getPacs(person.getPacsId());
					logger.info("pacs type::"+pacs.getPacsStatus());
					
					if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
						businessDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
						//businessDate="2017-05-01";
						pacsLoanApplicationDetail.setSanctionedDate(DateUtil.getVSoftDateByString(businessDate));
					}
					else
					pacsLoanApplicationDetail.setSanctionedDate(LoanServiceUtil.getBusinessDate());
					pacsLoanApplicationDetail.setSanctionedBy(loanSanctionData.getLoggedInUserDetails().getUserName());

					pacsLoanApplicationDetail.setSanctionedRemarks(loanSanctionData.getSanctionRemarks());
					pacsLoanApplicationDetail.setApplicationStatus(LoanApplicationState.SANCTIONED);

					// Creating line of credit
					LineOfCredit lineOfCredit = createLineOfCredit(pacsLoanApplicationDetail, Long.parseLong(customerId), account);

					// Saving insurance charges
					saveCharges(pacsLoanApplicationDetail, account, lineOfCredit, ChargeType.INSURANCE);
					// Saving share charges
					saveCharges(pacsLoanApplicationDetail, account, lineOfCredit, ChargeType.SHARE);
					// Saving insurance subsidy
					saveCharges(pacsLoanApplicationDetail, account, lineOfCredit, ChargeType.SUBSIDY);

					dao.updatePacsLoanApplicationDetail(pacsLoanApplicationDetail);
				}

			} else if (loanSanctionData.getStatus().equals("0")) { // "0"indiacates
																	// rejected
				for (PacsLoanApplicationDetail pacsLoanApplicationDetail : loanApplicationDetailList) {
					if (pacsLoanApplicationDetail.getApplicationStatus().getValue() == LoanApplicationState.INSPECTED.getValue()) {
						pacsLoanApplicationDetail.setApplicationStatus(LoanApplicationState.REJECTED);
						dao.updatePacsLoanApplicationDetail(pacsLoanApplicationDetail);
					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error: While updatitng loan sanction data:" + exception.getMessage());
			throw new KlsRuntimeException("Loan cannot be approved", exception.getCause());
		}
		logger.info("Start:calling PacsLoanSanctionProcessDAO in updateLoanSanction()");
	}

	@Override
	public LoanSanctionSummaryData getLoanSanctionSummary(String pacId, String financialYear) {
		LoanSanctionSummaryData loanSanctionSummaryData = new LoanSanctionSummaryData();
		try {
			logger.info("Start:calling PacsLoanSanctionProcessDAO in getLoanSanctionSummary()");
			List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = KLSDataAccessFactory.getPacsLoanSanctionProcessDAO()
					.getPacsLoanApplicationList(pacId, financialYear);
			loanSanctionSummaryData = PacsLoanApplicationDetailHelper.getLoanSanctionSummaryData(pacsLoanApplicationDetailList);
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error while retrieving loan sanction summary data in getLoanSanctionSummary()" + exception.getMessage());
		}
		logger.info("Start:calling PacsLoanSanctionProcessDAO in getLoanSanctionSummary()");

		return loanSanctionSummaryData;
	}

	public void createAccount(String customerId, String pacId) {
		IAccountDAO accountDAO = KLSDataAccessFactory.getAccountDAO();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		Account account = accountDAO.getAccountByCustomer(customerId);
		try {
			logger.info("Start:Calling Account DAO in createAccount()");
			if (account == null) {
				account = new Account();
				AccountProperty accountProperty = new AccountProperty();
				Pacs pacs = new Pacs();
				pacs.setId(Integer.parseInt(pacId));
				pacs = KLSDataAccessFactory.getPacsDAO().getPacs(pacs);
				if (pacs != null) {
					accountProperty.setPacs(pacs);
					accountProperty.setBranch(pacs.getBranch());
				}
				/*
				 * Product product = new Product(); product.setId(1);
				 * accountProperty.setProduct(product);
				 */
				accountProperty.setCustomerId(Long.parseLong(customerId));
				String branchId = pacs.getBranch().getId().toString();
				String finAcnt = AccountServiceUtil.generateAccountNumber(branchId, pacId, null);
				String businessDate="";
				PersonData person=RestClientUtil.getCustomerById(Long.parseLong(customerId));
				Pacs pacsData=pacsDao.getPacs(person.getPacsId());
				logger.info("pacs type::"+pacs.getPacsStatus());
				
				if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
					businessDate=RestClientUtil.getPacsBusinessDate(pacs.getId(),pacs.getBranch().getId());
					//businessDate="2017-05-01";
					account.setOpenDate(DateUtil.getVSoftDateByString(businessDate));
				}
				else
				account.setOpenDate(Date.now());
				account.setAccountNumber(finAcnt);
				account.setStatus(AccountStatus.Active);
				account.setAccountProperty(accountProperty);
				accountDAO.saveAccount(account);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Error:While creating Account in createAccount()");
		}
		logger.info("End:Calling Account DAO in createAccount()");

	}

	@Override
	public void updateSanctionAmount(List<LoanSanctionData> loanSanctionDataList) {
		try {
			logger.info("Start:calling pacs loan application service in updateSanctionAmount()");
			Money totalSanctionAmount = Money.ZERO;
			Money loanLimitPerFarmer = LoanServiceUtil.getLoanLimitPerFarmer();
			for (LoanSanctionData loanSanctionData : loanSanctionDataList) {
				totalSanctionAmount = totalSanctionAmount.add(Money.valueOf(Currency.getInstance("INR"), loanSanctionData.getSanctionedAmount()));
			}
			if (totalSanctionAmount.compareTo(loanLimitPerFarmer) == -1) {
				for (LoanSanctionData loanSanctionData : loanSanctionDataList) {
					PacsLoanApplicationDetail pacsLoanApplicationDetail = new PacsLoanApplicationDetail();
					pacsLoanApplicationDetail.setId(Long.valueOf(loanSanctionData.getId()));
					pacsLoanApplicationDetail = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO().getPacsLoanApplicationDetail(
							pacsLoanApplicationDetail);
					pacsLoanApplicationDetail.setSanctionedAmount(Money.valueOf(Currency.getInstance("INR"), loanSanctionData.getSanctionedAmount()));
					pacsLoanApplicationDetail.setShareAmount(LoanServiceUtil.calculateShareAmount(pacsLoanApplicationDetail));
					pacsLoanApplicationDetail.setInsuranceAmount(LoanServiceUtil.calculateInsuranceAmount(pacsLoanApplicationDetail));
					pacsLoanApplicationDetail.setInsuranceAmount(Money.valueOf(Currency.getInstance("INR"), loanSanctionData.getInsuranceAmount()));
					if (pacsLoanApplicationDetail.getEligibleSanctionAmount() == null)
						pacsLoanApplicationDetail.setEligibleSanctionAmount(Money.valueOf(Currency.getInstance("INR"),
								loanSanctionData.getTempSanctionAmount()));
					KLSDataAccessFactory.getPacsLoanSanctionProcessDAO().updateSanctionAmount(pacsLoanApplicationDetail);
				}
			} else {
				throw new KlsRuntimeException("Total sanction amount should not exceed loan limit");
			}
		} catch (Exception exception) {
			logger.info("Error while updating sanction amount in  updateSanctionAmount()");
		}
		logger.info("End:calling pacs loan application service in updateSanctionAmount()");
	}

	private Money calculateSanctionAmount(PacsLoanApplicationDetail master) {

		Money requestedAmount = master.getRequiredAmount();
		Money recommendedAmount = master.getRecommendedAmount();
		Money inspectionAmount = master.getInspectionAmount();
		Money calculatedAmount = master.getCalculatedAmount();
		Money sanctionedAmount = Money.ZERO;
		Money loanLimitPerFarmer = Money.ZERO;

		IBankParameterDAO dao = KLSDataAccessFactory.getBankParameterDAO();
		List<BankParameter> bankParameterList = dao.getAllBankParameters();

		if (bankParameterList != null && !bankParameterList.isEmpty()) {
			BankParameter bankParameter = bankParameterList.get(0);
			loanLimitPerFarmer = bankParameter.getLoanLimitPerFarmer();
		}
		sanctionedAmount = LoanServiceUtil.calculateSanctionAmount(requestedAmount, recommendedAmount, inspectionAmount, calculatedAmount,
				loanLimitPerFarmer);
		return sanctionedAmount;
	}

	private LineOfCredit createLineOfCredit(PacsLoanApplicationDetail pacsLoanApplicationDetail, Long customerId, Account account) {
		int season = pacsLoanApplicationDetail.getSeasonId().getId().intValue();
		int crop = pacsLoanApplicationDetail.getCropId().getId();
		int product = pacsLoanApplicationDetail.getHeaderId().getProduct().getId();
		ILineOfCreditDAO lDao = KLSDataAccessFactory.getLineOfCreditDAO();
		IAccountDAO aDao = KLSDataAccessFactory.getAccountDAO();
		Account acc = aDao.getAccountByCustomer(customerId.toString());
		LineOfCredit lineOfCredit = new LineOfCredit();
		LineOfCredit lineOfCreditObj = null;
		lineOfCreditObj = lDao.getLineOfCredit(season, crop, customerId, product);
		Money insuranceAmount = pacsLoanApplicationDetail.getInsuranceAmount();
		Money shareAmount = pacsLoanApplicationDetail.getShareAmount();
		AccountingMoney chargesToBeDeduct = AccountingMoney.valueOf(insuranceAmount.add(shareAmount), DebitOrCredit.DEBIT);
		BigDecimal totalKindPercentage = KLSDataAccessFactory.getSanctionedComponentDAO().getTotalKindPercentage(
				pacsLoanApplicationDetail.getSeasonId().getId());

		if (lineOfCreditObj == null) {
			lineOfCredit.setCustomerId(pacsLoanApplicationDetail.getCustomerId());
			//hardcoding prod id
			
			lineOfCredit.setInterestCategory(pacsLoanApplicationDetail.getHeaderId().getProduct().getIntrCategory());
			lineOfCredit.setCrop(pacsLoanApplicationDetail.getCropId());
			lineOfCredit.setSeason(pacsLoanApplicationDetail.getSeasonId());
			lineOfCredit.setAccount(acc);
			boolean firstDrawalFlag = lDao.isFirstDrawal(account.getId(), pacsLoanApplicationDetail.getSeasonId().getId());
			if (firstDrawalFlag) {
				lineOfCredit.setCurrentBalance(chargesToBeDeduct);
				lineOfCredit.setIsFirstDrawal(1);
			} else {
				lineOfCredit.setCurrentBalance(AccountingMoney.ZERO);
				lineOfCredit.setIsFirstDrawal(0);// By default zero
			}
			lineOfCredit.setScheme(pacsLoanApplicationDetail.getHeaderId().getScheme());
			lineOfCredit.setProduct(pacsLoanApplicationDetail.getHeaderId().getProduct());
			lineOfCredit.setDrawalPriority(pacsLoanApplicationDetail.getPriority());
			lineOfCredit.setInterestAccrued(BigDecimal.ZERO);

			lineOfCredit.setLastInterestCalculatedDate(pacsLoanApplicationDetail.getSanctionedDate());
			lineOfCredit.setOverdueInterest(BigDecimal.ZERO);
			lineOfCredit.setSanctionedAmount(pacsLoanApplicationDetail.getSanctionedAmount());
			lineOfCredit.setSanctionedDate(pacsLoanApplicationDetail.getSanctionedDate());
			if (totalKindPercentage != null) {
				Money kindLimit = pacsLoanApplicationDetail.getSanctionedAmount().multiply(totalKindPercentage.floatValue()).divide(100);
				lineOfCredit.setKindLimit(kindLimit);
				lineOfCredit.setOperatingCashLimit(pacsLoanApplicationDetail.getSanctionedAmount().subtract(kindLimit));
			} else {
				lineOfCredit.setOperatingCashLimit(pacsLoanApplicationDetail.getSanctionedAmount());
				lineOfCredit.setKindLimit(Money.ZERO);
			}
			if (pacsLoanApplicationDetail.getSanctionedAmount().compareTo(Money.ZERO) != 0) {

				lDao.saveLineOfCredit(lineOfCredit, null);
				if (firstDrawalFlag) {
					saveCurrentTransactionRecords(lineOfCredit, insuranceAmount, shareAmount);
				}
			}

			return lineOfCredit;
		} else {
			if (lDao.isFirstDrawal(account.getId(), pacsLoanApplicationDetail.getSeasonId().getId())) {
				lineOfCreditObj.setCurrentBalance(lineOfCreditObj.getCurrentBalance().add(chargesToBeDeduct));
				saveCurrentTransactionRecords(lineOfCreditObj, insuranceAmount, shareAmount);
			}
			lineOfCreditObj.setLastInterestCalculatedDate(pacsLoanApplicationDetail.getSanctionedDate());
			lineOfCreditObj.setSanctionedDate(pacsLoanApplicationDetail.getSanctionedDate());
			lineOfCreditObj.setSanctionedAmount(pacsLoanApplicationDetail.getSanctionedAmount().add(lineOfCreditObj.getSanctionedAmount()));

			if (totalKindPercentage != null) {
				Money kindLimit = pacsLoanApplicationDetail.getSanctionedAmount().multiply(totalKindPercentage.floatValue()).divide(100);
				lineOfCreditObj.setKindLimit(lineOfCreditObj.getKindLimit().add(kindLimit));
				lineOfCreditObj.setOperatingCashLimit(lineOfCreditObj.getOperatingCashLimit().add(
						pacsLoanApplicationDetail.getSanctionedAmount().subtract(kindLimit)));
			} else
				lineOfCreditObj.setOperatingCashLimit(lineOfCreditObj.getOperatingCashLimit().add(pacsLoanApplicationDetail.getSanctionedAmount()));

			lDao.updateLineOfCredit(lineOfCreditObj);
			return lineOfCreditObj;
		}

	}
	private List<PacsLoanApplicationDetail> setSanctionAmount(List<PacsLoanApplicationDetail> inspectedApplicationDetailList, Money sanctionedAmount) {
		List<PacsLoanApplicationDetail> list = new ArrayList<PacsLoanApplicationDetail>();
		Money sanctionAmount = Money.ZERO;
		try {
			for (PacsLoanApplicationDetail pacsLoanApplicationDetail : inspectedApplicationDetailList) {
				if (pacsLoanApplicationDetail.getSanctionedAmount() == null)
					sanctionAmount = sanctionAmount.add(calculateSanctionAmount(pacsLoanApplicationDetail));
				else
					sanctionAmount = sanctionAmount.add(pacsLoanApplicationDetail.getSanctionedAmount());
			}
			int testVal = 0;
			testVal = sanctionedAmount.compareTo(sanctionAmount);

			if (testVal == 0 || testVal == 1) {
				for (PacsLoanApplicationDetail pacsLoanApplicationDetail : inspectedApplicationDetailList) {
					logger.info("getPriority:" + pacsLoanApplicationDetail.getPriority());
					if (pacsLoanApplicationDetail.getSanctionedAmount() == null) {
						Money sanctionAmt = calculateSanctionAmount(pacsLoanApplicationDetail);
						pacsLoanApplicationDetail.setSanctionedAmount(sanctionAmt);
						pacsLoanApplicationDetail.setEligibleSanctionAmount(sanctionAmt);
					}
					list.add(pacsLoanApplicationDetail);
				}
			} else {
				Money totalSanctionAmount = Money.ZERO;
				for (PacsLoanApplicationDetail pacsLoanApplicationDetail : inspectedApplicationDetailList) {
					logger.info("getPriority:" + pacsLoanApplicationDetail.getPriority());
					if (pacsLoanApplicationDetail.getSanctionedAmount() == null) {
						Money sanctionAmt = calculateSanctionAmount(pacsLoanApplicationDetail);
						int testVal1 = 0;
						testVal1 = totalSanctionAmount.add(sanctionAmt).compareTo(sanctionedAmount);
						if (testVal1 == -1 || testVal1 == 0) {
							totalSanctionAmount = totalSanctionAmount.add(sanctionAmt);
							pacsLoanApplicationDetail.setSanctionedAmount(sanctionAmt);
							pacsLoanApplicationDetail.setEligibleSanctionAmount(sanctionAmt);
							list.add(pacsLoanApplicationDetail);
						} else {
							sanctionAmt = sanctionedAmount.subtract(totalSanctionAmount);
							totalSanctionAmount = totalSanctionAmount.add(sanctionAmt);
							pacsLoanApplicationDetail.setSanctionedAmount(sanctionAmt);
							pacsLoanApplicationDetail.setEligibleSanctionAmount(sanctionAmt);
							list.add(pacsLoanApplicationDetail);
						}
					} else
						totalSanctionAmount = totalSanctionAmount.add(pacsLoanApplicationDetail.getSanctionedAmount());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private static void saveCharges(PacsLoanApplicationDetail pacsLoanApplicationDetail, Account account, LineOfCredit lineOfCredit,
			ChargeType chargeType) {
		Charges charges = new Charges();
		charges.setSeason(pacsLoanApplicationDetail.getSeasonId());
		charges.setAccount(account);
		charges.setPacsLoanApplicationDtl(pacsLoanApplicationDetail);
		charges.setLineOfCredit(lineOfCredit);
		if (chargeType.equals(ChargeType.INSURANCE)){
			if(pacsLoanApplicationDetail.getInsuranceAmount() == null){
				charges.setChargeAmount(LoanServiceUtil.calculateInsuranceAmount(pacsLoanApplicationDetail));
			}else{
				charges.setChargeAmount(pacsLoanApplicationDetail.getInsuranceAmount());
			}
		}
		else if (chargeType.equals(ChargeType.SHARE))
			charges.setChargeAmount(pacsLoanApplicationDetail.getShareAmount());
		else {
			charges.setChargeAmount(LoanServiceUtil.calculateInsuranceSubsidyAmount(pacsLoanApplicationDetail));
		}
		charges.setChargeType(chargeType);
		if (charges.getChargeAmount().compareTo(Money.ZERO) != 0)
			KLSDataAccessFactory.getChargesDAO().saveCharges(charges);
	}

	private static List<PacsLoanApplicationDetail> setShareAmount(List<PacsLoanApplicationDetail> applicationDetails) {

		List<PacsLoanApplicationDetail> list = new ArrayList<PacsLoanApplicationDetail>();
		Money maxShareAmount = LoanServiceUtil.getMaxShareAmountPerFarmer();

		Money totalShareAmount = Money.ZERO;
		Money shareBalance = Money.ZERO;
		for (PacsLoanApplicationDetail pacsLoanApplicationDetail : applicationDetails) {
			totalShareAmount = totalShareAmount.add(LoanServiceUtil.calculateShareAmount(pacsLoanApplicationDetail));
		}

		if (maxShareAmount != null) {
			if (!applicationDetails.isEmpty()) {
				PacsLoanApplicationDetail pacsLoanApplDtl = applicationDetails.get(0);
				//hardcoding prod id
				//pacsLoanApplDtl.getHeaderId().getScheme().getProduct()
				
				Money maxShareAmountProuctWise = pacsLoanApplDtl.getHeaderId().getProduct().getMaxShareAmount();
				if (maxShareAmountProuctWise != null)
					if (maxShareAmount.compareTo(maxShareAmountProuctWise) == 1)
						maxShareAmount = maxShareAmountProuctWise;

				Long customerId = pacsLoanApplDtl.getCustomerId();

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

				// Changed as part story SUB-843. Done
				// shareBalance =
				// pacsLoanApplDtl.getCustomerId().getShareBalance();
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
					for (PacsLoanApplicationDetail pacsLoanApplicationDetail : applicationDetails) {
						Money shareAmount = LoanServiceUtil.calculateShareAmount(pacsLoanApplicationDetail);
						int val = (int) Math.ceil(shareAmount.getAmount().doubleValue() / faceValue);
						shareAmount = MoneyUtil.getAccountingMoney(new BigDecimal(val * faceValue)).getMoney();
						pacsLoanApplicationDetail.setShareAmount(shareAmount);
						list.add(pacsLoanApplicationDetail);
					}
				} else {
					Money totalSharingAmount = Money.ZERO;
					int testVal2 = 0;
					for (PacsLoanApplicationDetail pacsLoanApplicationDetail : applicationDetails) {
						Money shareAmount = LoanServiceUtil.calculateShareAmount(pacsLoanApplicationDetail);
						testVal2 = totalSharingAmount.add(shareAmount).compareTo(maxShareAmount);
						if (testVal2 == -1 || testVal2 == 0) {
							int val = (int) Math.ceil(shareAmount.getAmount().doubleValue() / faceValue);
							shareAmount = MoneyUtil.getAccountingMoney(new BigDecimal(val * faceValue)).getMoney();
							totalSharingAmount = totalSharingAmount.add(shareAmount);
							pacsLoanApplicationDetail.setShareAmount(shareAmount);
						} else if (testVal2 == 1) {
							int val = (int) Math.ceil(shareAmount.getAmount().doubleValue() / faceValue);
							shareAmount = MoneyUtil.getAccountingMoney(new BigDecimal(val * faceValue)).getMoney();
							shareAmount = maxShareAmount.subtract(totalSharingAmount);
							totalSharingAmount = totalSharingAmount.add(shareAmount);
							pacsLoanApplicationDetail.setShareAmount(shareAmount);
						}
						list.add(pacsLoanApplicationDetail);
					}
				}
			}
		}
		return list;
	}

	private static List<PacsLoanApplicationDetail> setInsuranceAmount(List<PacsLoanApplicationDetail> applicationDetails) {
		List<PacsLoanApplicationDetail> list = new ArrayList<PacsLoanApplicationDetail>();
		ISeasonParameterDAO seasonParamDao = KLSDataAccessFactory.getSeasonParameterDAO();
		String businessDate = DateUtil.getDateString(LoanServiceUtil.getBusinessDate());
		String startDate="";
		for (PacsLoanApplicationDetail pacsLoanApplicationDetail : applicationDetails) {
			SeasonParameter seasonParameter = seasonParamDao.getSeasonParameter(pacsLoanApplicationDetail.getSeasonId().getId(), pacsLoanApplicationDetail.getCropId().getId(),pacsLoanApplicationDetail.getHeaderId().getPacs().getId());
			startDate=DateUtil.getDateString(pacsLoanApplicationDetail.getSeasonId().getDrawalStartDate());
			if(seasonParameter!=null){
			String insuranceDate=DateUtil.getDateByAddingNoOfDays(startDate, seasonParameter.getInsurancePeriod());
			if (pacsLoanApplicationDetail.getInsuranceAmount() == null){
				if(DateUtil.compareDate(businessDate,insuranceDate)<=0)
				pacsLoanApplicationDetail.setInsuranceAmount(LoanServiceUtil.calculateInsuranceAmount(pacsLoanApplicationDetail));
				else
					pacsLoanApplicationDetail.setInsuranceAmount(Money.ZERO);	
			}
			}
			else
				pacsLoanApplicationDetail.setInsuranceAmount(Money.ZERO);
			list.add(pacsLoanApplicationDetail);
		}
		return list;
	}

	private static List<PacsLoanApplicationDetail> setInsuranceAmountAtSantion(List<PacsLoanApplicationDetail> applicationDetails) {
		List<PacsLoanApplicationDetail> list = new ArrayList<PacsLoanApplicationDetail>();
		IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
		PacsLoanApplicationDetail loanDetails = new PacsLoanApplicationDetail();
		for (PacsLoanApplicationDetail pacsLoanApplicationDetail : applicationDetails) {
			loanDetails=dao.getPacsLoanApplicationDetail(pacsLoanApplicationDetail);
			if (loanDetails.getInsuranceAmount() == null)
				pacsLoanApplicationDetail.setInsuranceAmount(LoanServiceUtil.calculateInsuranceAmount(pacsLoanApplicationDetail));
			list.add(pacsLoanApplicationDetail);
		}
		return list;
	}
	/**
	 * 
	 * @param lineOfCredit
	 * @param chargesToDeduct
	 * @param insuranceAmount
	 * @param shareAmount
	 */
	private void saveCurrentTransactionRecords(LineOfCredit lineOfCredit, Money insuranceAmount, Money shareAmount) {

		logger.info("Start : Populating the current transaction in createCurrentTransaction() method.");
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		AccountingMoney insuranceAmt = AccountingMoney.valueOf(insuranceAmount, DebitOrCredit.CREDIT);
		AccountingMoney chargesAmt = AccountingMoney.valueOf(shareAmount, DebitOrCredit.CREDIT);
		CurrentTransaction currentTransaction = createCurrentTransactionRecord(lineOfCredit, TransactionCode.SHARE_TRANSFER, chargesAmt);
		currentTransactionList.add(currentTransaction);
		currentTransaction = createCurrentTransactionRecord(lineOfCredit, TransactionCode.INSURANCE_DEDUCTION, insuranceAmt);
		currentTransactionList.add(currentTransaction);
		// get the current transaction dao.
		ICurrentTransactionDAO dao = KLSDataAccessFactory.getCurrentTransactionDAO();
		try {
			dao.saveCurrentTransaction(currentTransactionList, null);
		} catch (Exception excp) {
			excp.printStackTrace();
		}
		logger.info("End : Populating the current transaction in createCurrentTransaction() method.");

	}

	/**
	 * 
	 * @param lineOfCredit
	 * @param transactionCode
	 * @param transactionAmount
	 * @return
	 */
	private CurrentTransaction createCurrentTransactionRecord(LineOfCredit lineOfCredit, TransactionCode transactionCode,
			AccountingMoney transactionAmount) {

		logger.info("Start : Populating the current transaction in createCurrentTransaction() method.");
		CurrentTransaction copyObject = new CurrentTransaction();
		ILineOfCreditDAO lDao = KLSDataAccessFactory.getLineOfCreditDAO();
		if (lineOfCredit.getId() != null) {
			lineOfCredit = lDao.getLineOfCreditById(lineOfCredit.getId(), false);
		}
		IAccountDAO accountDAO = KLSDataAccessFactory.getAccountDAO();
		if (lineOfCredit.getAccount() != null) {
			Account account = accountDAO.getAccount(lineOfCredit.getAccount(), false);
			copyObject.setPacs(account.getAccountProperty().getPacs());
		}
		Integer pacsId= copyObject.getPacs().getId();
		Integer voucherNumber = VoucherNumberUtil.getVoucherNumber(pacsId.toString(),
				TransactionType.Transfer.getValue());

		copyObject.setAccount(lineOfCredit.getAccount());
		copyObject.setAccountNumber(lineOfCredit.getAccount().getAccountNumber());
		copyObject.setBusinessDate(Date.now());
		copyObject.setChannelType(ChannelType.SYSTEM);
		copyObject.setCrDr(-1);
		copyObject.setLineOfCredit(lineOfCredit);
		copyObject.setOpeningBalance(AccountingMoney.ZERO);
		copyObject.setPostedStatus(1);
		copyObject.setRemarks(null);
		copyObject.setTransactionAmount(transactionAmount);
		copyObject.setTransactionCode(transactionCode);
		copyObject.setTransactionType(TransactionType.Transfer);
		copyObject.setVoucherNumber(TransactionType.Transfer.getValue() + "-" + voucherNumber);
		logger.info("End : Populating the current transaction in createCurrentTransaction() method.");
		return copyObject;
	}

	@Override
	public String getShareAndInsuranceAmount(long id, String sanctionedAmount) {
		logger.info("start:inside getShareAndInsuranceAmount");
		PacsLoanApplicationDetail pacsLoanApplicationDetail = new PacsLoanApplicationDetail();
		pacsLoanApplicationDetail.setId(id);
		Money shareAmount = Money.ZERO;
		Money insuranceAmount = Money.ZERO;
		try {
			pacsLoanApplicationDetail = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO()
					.getPacsLoanApplicationDetail(pacsLoanApplicationDetail);
			pacsLoanApplicationDetail.setSanctionedAmount(Money.valueOf(Currency.getInstance("INR"), sanctionedAmount));
			shareAmount = LoanServiceUtil.calculateShareAmount(pacsLoanApplicationDetail);
			// insuranceAmount =
			// LoanServiceUtil.calculateInsuranceAmount(pacsLoanApplicationDetail).add(LoanServiceUtil.calculateInsuranceSubsidyAmount(pacsLoanApplicationDetail));
			insuranceAmount = LoanServiceUtil.calculateInsuranceAmount(pacsLoanApplicationDetail);

		} catch (Exception e) {
			logger.error("Error : while getting insurance and share charges");
			e.printStackTrace();
			throw new KlsRuntimeException("Cannot get share and insurance charges");
		}
		logger.info("End : inside getShareAndInsuranceAmount");

		return "{\"id\" :" + id + ",\"shareAmount\" :" + shareAmount.getAmount().setScale(2) + ",\"insuranceAmount\" :"
				+ insuranceAmount.getAmount().setScale(2) + "}";
	}

	@Override
	public Integer getTotalNoOfLoanInspectedApplications(String pacId,
			String financialYear) {
		logger.info("START: Getting total no of inspectecd loans in PacsLoanSanctionProcess Service class");
		Integer totalInspectedLoans=0;
		try {
			IPacsLoanSanctionProcessDAO pDAO = KLSDataAccessFactory.getPacsLoanSanctionProcessDAO();
			totalInspectedLoans = pDAO.getTotalNoOfLoanInspectedApplications(pacId, financialYear);
		} catch (Exception e) {
			logger.info("ERROR: Error while Getting total no of inspectecd loans in PacsLoanSanctionProcess Service class");
			e.printStackTrace();
		}
		logger.info("END: Getting total no of inspectecd loans in PacsLoanSanctionProcess Service class");
		return totalInspectedLoans;
	}
}
