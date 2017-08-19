package com.vsoftcorp.kls.service.account.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.ChargesDebit;
import com.vsoftcorp.kls.business.entity.loan.ChargesMaster;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.ChargesDebitData;
import com.vsoftcorp.kls.data.ChargesDebitItem;
import com.vsoftcorp.kls.data.ChargesMasterData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IChargesDebitDAO;
import com.vsoftcorp.kls.service.account.IChargesDebitService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.MasterHelper;
import com.vsoftcorp.kls.service.loan.ILoanRecoveryService;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;

public class ChargesDebitService implements IChargesDebitService {
	private static final Logger logger = Logger.getLogger(ChargesDebitService.class);

	@Override
	public void saveChargesDebit(ChargesDebitData data) {

		logger.info("Start : Calling chargesDebit dao in saveChargesDebit() method.");
		// get the charges master DAO.
		IChargesDebitDAO dao = KLSDataAccessFactory.getChargesDebitDAO();
		LoanLineOfCredit lineOfCredit = KLSDataAccessFactory.getLoanLineOfCreditDAO().getLoanLineOfCreditById(
				data.getLineOfCreditId());
		// get the entity POJO.
		CurrentTransaction currentTransaction = new CurrentTransaction();
		try {
			EntityManagerUtil.beginTransaction();

			if (data.getChargesMasterDataList().size() > 0) {

				for (ChargesDebitItem chargesDebitItem : data.getChargesMasterDataList()) {
					
					ChargesDebit chargesDebit = new ChargesDebit();
					if (chargesDebitItem.getId() != null) {
						chargesDebit.setId(chargesDebitItem.getId());
					}
					if (chargesDebitItem.getChargesMasterData() != null) {
						ChargesMaster chargesMaster = new ChargesMaster();
						ChargesMasterData chargesMasterData = chargesDebitItem.getChargesMasterData();

						if (chargesMasterData.getId() != null) {
							chargesMaster.setId(chargesMasterData.getId());
						}
						if (chargesMasterData.getChargesCode() != null) {
							chargesMaster.setChargesCode(chargesMasterData.getChargesCode());
						}
						if (chargesMasterData.getChargesDescription() != null) {
							chargesMaster.setChargesDescription(chargesMasterData.getChargesDescription());
						}
						
						currentTransaction = chargesDebitTransactions(data, chargesDebitItem.getAmount(), lineOfCredit,chargesMasterData.getBankPercentage());
						chargesDebit.setChargesMaster(chargesMaster);
					}
					chargesDebit.setAmount(MasterHelper.populateAmountParam(chargesDebitItem.getAmount()));
					chargesDebit.setBalanceAmount(MasterHelper.populateAmountParam(chargesDebitItem.getAmount()));
					chargesDebit.setRemarks(chargesDebitItem.getRemarks());
					chargesDebit.setBusinessDate(LoanServiceUtil.getBusinessDate());
					chargesDebit.setVoucherNumber(currentTransaction.getVoucherNumber());
					if (data.getLineOfCreditId() != null) {
						LineOfCredit loc = new LineOfCredit();
						loc.setId(data.getLineOfCreditId());
						chargesDebit.setLineOfCredit(loc);
					}
					logger.info("ChargesMasterDataList size==" + data.getChargesMasterDataList().size());
					dao.saveChargesDebit(chargesDebit);
					logger.info(" ChargesDebit Data saved Successfully in saveChargesDebit() method.");
				}
			}
			if (data.getChargesDebitDeleteList().size() > 0) {
				for (ChargesDebitItem chargesDebitItem : data.getChargesDebitDeleteList()) {
					if (chargesDebitItem.getId() != null) {
						dao.deleteChargesDebit(chargesDebitItem.getId(), data.getLineOfCreditId());
						logger.info(" ChargesDebit Data deleted Successfully in saveChargesDebit() method.");
					}
				}

			}
			EntityManagerUtil.CommitOrRollBackTransaction(true);
		} catch (RollbackException excp) {

			excp.printStackTrace();
			logger.error("RollbackException cannot be saved");
			throw new KlsRuntimeException("RollbackException cannot be saved", excp);
		}

		catch (Exception excp) {

			EntityManagerUtil.CommitOrRollBackTransaction(false);
			excp.printStackTrace();
			logger.error("ChargesDebitData cannot be saved");
			throw new KlsRuntimeException("ChargesDebitData cannot be saved", excp);
		}
		logger.info("End : Calling chargesDebit dao in saveChargesDebit() method.");
	}

	@Override
	public ChargesDebitData getAllChargesDebit(Long locId) {
		logger.info("Start: Fetching all the charges Debit data from the database in getAllChargesDebit() method.");
		List<ChargesDebitItem> chargesMasterDataList = new ArrayList<ChargesDebitItem>();
		ChargesDebitData chargesDebitData = new ChargesDebitData();
		IChargesDebitDAO dao = KLSDataAccessFactory.getChargesDebitDAO();
		try {
			List<ChargesDebit> chargesDebitList = dao.getAllChargesDebit(locId);
			for (ChargesDebit chargesDebit : chargesDebitList) {
				ChargesDebitItem chargesDebitItem = new ChargesDebitItem();
				chargesDebitItem.setId(chargesDebit.getId());
				if (chargesDebit.getChargesMaster() != null) {
					ChargesMaster chargesMaster = chargesDebit.getChargesMaster();
					ChargesMasterData chargesMasterData = new ChargesMasterData();
					chargesMasterData.setId(chargesMaster.getId());
					chargesMasterData.setChargesCode(chargesMaster.getChargesCode());
					chargesMasterData.setChargesDescription(chargesMaster.getChargesDescription());
					chargesDebitItem.setChargesMasterData(chargesMasterData);
				}
				if (chargesDebit.getLineOfCredit() != null) {
					chargesDebitData.setLineOfCreditId(chargesDebit.getLineOfCredit().getId());
				}
				chargesDebitItem.setRemarks(chargesDebit.getRemarks());
				chargesDebitItem.setAmount(MoneyUtil.getAccountingMoney(chargesDebit.getAmount()).getMoney()
						.getAmount().toString());
				chargesMasterDataList.add(chargesDebitItem);
			}
			chargesDebitData.setChargesMasterDataList(chargesMasterDataList);
		} catch (Exception e) {
			logger.error("Error while retriving all charges Debit from the database");
			throw new DataAccessException("Error while retriving all charges Debits from the database", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the charges  data from the database in getAllChargesDebit() method.");
		return chargesDebitData;
	}

	public CurrentTransaction chargesDebitTransactions(ChargesDebitData chargesDebitData, String amount,
			LoanLineOfCredit lineOfCredit,String percentage) {
		lineOfCredit.setChargesReceivable(lineOfCredit.getChargesReceivable().add(
				new BigDecimal(amount).multiply(new BigDecimal("-1"))));
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();

		ILoanRecoveryService service = KLSServiceFactory.getLoanRecoveryService();
		String accountNumber = lineOfCredit.getAccount().getAccountNumber();
		Integer voucherNumber = VoucherNumberUtil.getVoucherNumber(lineOfCredit.getAccount().getAccountProperty()
				.getPacs().getId().toString(), TransactionType.Transfer.getValue());
		String voucher = TransactionType.Transfer.getValue() + " - " + voucherNumber;
		
		
		Double amt=Double.parseDouble(amount);
		Integer bankPercentage=Integer.parseInt(percentage);
		
		if(bankPercentage<100){
			Double bankAmount=(amt*bankPercentage)/100;
			amt=amt-bankAmount;
			amount=amt.toString();
			String bankAmt=bankAmount.toString();
			// Bank Credit
			service.populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList,
					TransactionCode.BANK_CHARGES_RECEIVED, "For Loan Charges Credit.", 1,
					MoneyUtil.getAccountingMoney(Money.valueOf("INR", bankAmt)), false, TransactionType.Transfer,
					voucher);
			// Bank Debit
			service.populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList,
					TransactionCode.BANK_CHARGES_RECEIVABLE, "For Loan Charges Debit.", -1,
					MoneyUtil.getAccountingMoney(Money.valueOf("INR", bankAmt)), false, TransactionType.Transfer,
					voucher);
			}
		
		// Credit
		service.populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList,
				TransactionCode.CHARGES_RECEIVED, "For Loan Charges Credit.", 1,
				MoneyUtil.getAccountingMoney(Money.valueOf("INR", amount)), false, TransactionType.Transfer,
				voucher);

		// Debit
		service.populateCurrentTransactionRecord(lineOfCredit, accountNumber, currentTransactionsList,
				TransactionCode.CHARGES_RECEIVABLE, "For Loan Charges Debit.", -1,
				MoneyUtil.getAccountingMoney(Money.valueOf("INR", amount)), false, TransactionType.Transfer,
				voucher);

		currentTransactionsList = KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(
				currentTransactionsList);
		updateLineOfCredit(lineOfCredit);
		return currentTransactionsList.get(0);

	}

	public void updateLineOfCredit(LineOfCredit lineOfCredit) {
		KLSDataAccessFactory.getLineOfCreditDAO().updateLineOfCredit(lineOfCredit);
	}

}