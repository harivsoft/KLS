package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.subsidy.SubsidyInterestAmounts;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.subsidy.ISubsidyInterestAmountsDAO;
import com.vsoftcorp.kls.service.util.StringConstant;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

public class LoanInterestPostingServiceHelper {

	private static final Logger logger = Logger.getLogger(LoanInterestPostingServiceHelper.class);

	/**
	 * Build transactions for interest posting on loc list given
	 * 
	 * @param locList
	 * @param theBusinessDate
	 * @return
	 */
	public static List<CurrentTransaction> getCurrentTransaction(List<LineOfCredit> locList, Date theBusinessDate) {

		List<CurrentTransaction> currentTrxnList = new ArrayList<CurrentTransaction>();

		Integer voucherNumber = null;
		String voucher = null;

		// Loop for what you want and commit
		for (Iterator iterator = locList.iterator(); iterator.hasNext();) {
			LineOfCredit loc = (LineOfCredit) iterator.next();
			logger.info("LOC ID: ----------" + loc.getId());
			Pacs pacs = null;
			if (loc.getLoanType().equals(StringConstant.CROP_LOAN) || loc.getLoanType().equals(StringConstant.MTLT_LOAN)
					|| loc.getLoanType().equals(StringConstant.BORROWINGS_LOAN))
				pacs = loc.getAccount().getAccountProperty().getPacs();
			if (pacs == null) {
				logger.info("Un Known loan type for LOC - " + loc.getId());
				continue;
			}
			String transType = null;
			if (!loc.getLoanType().equals(StringConstant.BORROWINGS_LOAN))
				transType = TransactionType.Transfer.getValue();
			else
				transType = TransactionType.Borrowings.getValue();

			voucherNumber = VoucherNumberUtil.getVoucherNumber(pacs.getId().toString(), transType);
			voucher = TransactionType.Transfer.getValue() + "-" + voucherNumber;

			if (loc.getInterestAccrued().compareTo(BigDecimal.ZERO) != 0) {
				/*
				 * subsidy interest post
				 */
				postSubsidyInterest(loc);
				
				//loan interest posting
				BigDecimal interestAccrued = loc.getInterestAccrued();
				loc.setInterestReceivable((loc.getInterestReceivable().add(interestAccrued)).setScale(0, RoundingMode.HALF_UP));
				loc.setInterestAccrued(BigDecimal.ZERO);
				KLSDataAccessFactory.getLineOfCreditDAO().updateLineOfCredit(loc);
				CurrentTransaction intReceivableDrTrxn = new CurrentTransaction();
				intReceivableDrTrxn.setLineOfCredit(loc);
				intReceivableDrTrxn.setBusinessDate(theBusinessDate);
				intReceivableDrTrxn.setAccount(loc.getAccount());
				intReceivableDrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
				intReceivableDrTrxn.setChannelType(ChannelType.SYSTEM);
				AccountingMoney transAmt = MoneyUtil.getAccountingMoney(interestAccrued.abs().setScale(0, RoundingMode.HALF_UP));
				intReceivableDrTrxn.setOpeningBalance(loc.getCurrentBalance());
				intReceivableDrTrxn.setPostedStatus(1);
				intReceivableDrTrxn.setTransactionAmount(transAmt);
				if (!loc.getLoanType().equals(StringConstant.BORROWINGS_LOAN)) {
					intReceivableDrTrxn.setTransactionType(TransactionType.Transfer);
					intReceivableDrTrxn.setCrDr(-1);
					intReceivableDrTrxn.setRemarks("Interest Accrued posted Receivable");
				} else {
					intReceivableDrTrxn.setTransactionType(TransactionType.Borrowings);
					intReceivableDrTrxn.setCrDr(1);
					intReceivableDrTrxn.setRemarks("Borrowings Interest Accrued posted Receivable");
				}
				intReceivableDrTrxn.setPacs(pacs);

				intReceivableDrTrxn.setTransactionCode(TransactionCode.INTEREST_RECEIVABLE);

				intReceivableDrTrxn.setVoucherNumber(voucher);

				// For Credit Transaction
				CurrentTransaction intReceivableCrTrxn = new CurrentTransaction();
				intReceivableCrTrxn.setLineOfCredit(loc);
				intReceivableCrTrxn.setBusinessDate(theBusinessDate);
				intReceivableCrTrxn.setAccount(loc.getAccount());
				intReceivableCrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
				intReceivableCrTrxn.setChannelType(ChannelType.SYSTEM);

				intReceivableCrTrxn.setOpeningBalance(loc.getCurrentBalance());
				intReceivableCrTrxn.setPostedStatus(1);
				intReceivableCrTrxn.setTransactionAmount(transAmt);
				if (!loc.getLoanType().equals(StringConstant.BORROWINGS_LOAN)) {
					intReceivableCrTrxn.setTransactionType(TransactionType.Transfer);
					intReceivableCrTrxn.setCrDr(1);
					intReceivableCrTrxn.setRemarks("Interest Accrued Posted Received");
				} else {
					intReceivableCrTrxn.setTransactionType(TransactionType.Borrowings);
					intReceivableCrTrxn.setCrDr(-1);
					intReceivableCrTrxn.setRemarks("Borrowings Interest Accrued Posted Received");
				}
				intReceivableCrTrxn.setPacs(pacs);

				intReceivableCrTrxn.setTransactionCode(TransactionCode.INTEREST_RECEIVED);
				intReceivableCrTrxn.setVoucherNumber(voucher);
				currentTrxnList.add(intReceivableDrTrxn);
				currentTrxnList.add(intReceivableCrTrxn);
			} // end for interest

			if (loc.getOverdueInterest().compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal overDueInterest = loc.getOverdueInterest();
				loc.setPenalInterestReceivable((loc.getPenalInterestReceivable().add(overDueInterest)).setScale(0, RoundingMode.HALF_UP));
				loc.setOverdueInterest(BigDecimal.ZERO);
				KLSDataAccessFactory.getLineOfCreditDAO().updateLineOfCredit(loc);
				CurrentTransaction penalIntDrTrxn = new CurrentTransaction();
				penalIntDrTrxn.setLineOfCredit(loc);
				penalIntDrTrxn.setBusinessDate(theBusinessDate);
				penalIntDrTrxn.setAccount(loc.getAccount());
				penalIntDrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
				penalIntDrTrxn.setChannelType(ChannelType.SYSTEM);

				penalIntDrTrxn.setOpeningBalance(loc.getCurrentBalance());
				penalIntDrTrxn.setPostedStatus(1);
				AccountingMoney transAmt = MoneyUtil.getAccountingMoney(overDueInterest.abs().setScale(0, RoundingMode.HALF_UP));
				penalIntDrTrxn.setTransactionAmount(transAmt);

				if (!loc.getLoanType().equals(StringConstant.BORROWINGS_LOAN)) {
					penalIntDrTrxn.setTransactionType(TransactionType.Transfer);
					penalIntDrTrxn.setCrDr(-1);
					penalIntDrTrxn.setRemarks("Penal Interest Posting Receivable");
				} else {
					penalIntDrTrxn.setTransactionType(TransactionType.Borrowings);
					penalIntDrTrxn.setCrDr(1);
					penalIntDrTrxn.setRemarks("Borrowings Penal Interest Posting Receivable");
				}
				penalIntDrTrxn.setPacs(pacs);
				penalIntDrTrxn.setVoucherNumber(voucher);
				penalIntDrTrxn.setTransactionCode(TransactionCode.PENAL_INTEREST_RECEIVABLE);

				// For Penal Credit Transaction
				CurrentTransaction penalIntCrTrxn = new CurrentTransaction();
				penalIntCrTrxn.setLineOfCredit(loc);
				penalIntCrTrxn.setBusinessDate(theBusinessDate);
				penalIntCrTrxn.setAccount(loc.getAccount());
				penalIntCrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
				penalIntCrTrxn.setChannelType(ChannelType.SYSTEM);

				penalIntCrTrxn.setOpeningBalance(loc.getCurrentBalance());
				penalIntCrTrxn.setPostedStatus(1);
				penalIntCrTrxn.setTransactionAmount(transAmt);
				if (!loc.getLoanType().equals(StringConstant.BORROWINGS_LOAN)) {
					penalIntCrTrxn.setTransactionType(TransactionType.Transfer);
					penalIntCrTrxn.setCrDr(1);
					penalIntCrTrxn.setRemarks("Penal Interest Posting Received");

				} else {
					penalIntCrTrxn.setTransactionType(TransactionType.Borrowings);
					penalIntCrTrxn.setCrDr(-1);
					penalIntCrTrxn.setRemarks("Borroings Penal Interest Posting Received");
				}
				penalIntCrTrxn.setPacs(pacs);

				penalIntCrTrxn.setTransactionCode(TransactionCode.PENAL_INTEREST_RECEIVED);
				penalIntCrTrxn.setVoucherNumber(voucher);

				currentTrxnList.add(penalIntDrTrxn);
				currentTrxnList.add(penalIntCrTrxn);
			} // end for penal

		}
		return currentTrxnList;
	}
	/*
	 * Here we r posting the subsidy interest from accuered to receivable
	 */
	public static void postSubsidyInterest(LineOfCredit loc){
		logger.info("START: posting the subsidy interest in postSubsidyInterest() mehtod");
		ISubsidyInterestAmountsDAO dao = KLSDataAccessFactory.getSubsidyInterestAmountsDAO();
		List<SubsidyInterestAmounts> samountsList = dao.getSubsidyListByLocId(loc.getId());
		for(SubsidyInterestAmounts subsidyAmount : samountsList){
			if(subsidyAmount.getSubsidyReceivable() == null){
				subsidyAmount.setSubsidyReceivable(subsidyAmount.getSubsidyAccrued().setScale(0, RoundingMode.HALF_UP));
			}else{
				subsidyAmount.setSubsidyReceivable(subsidyAmount.getSubsidyReceivable().add(subsidyAmount.getSubsidyAccrued()).setScale(0, RoundingMode.HALF_UP));
			}
			subsidyAmount.setSubsidyAccrued(BigDecimal.ZERO);
			dao.updateSubsidyInterestAmounts(subsidyAmount);
		}
		logger.info("END: posting the subsidy interest in postSubsidyInterest() mehtod");
	}
	
	public static void doSubsidyInterestPosting(LineOfCredit loc, Date theBusinessDate, BigDecimal subsidyReceivable) {
		
		Pacs pacs = null;
		Integer voucherNumber = null;
		String voucher = null;

		pacs = loc.getAccount().getAccountProperty().getPacs();
	
		String transType = TransactionType.Transfer.getValue();
		voucherNumber = VoucherNumberUtil.getVoucherNumber(pacs.getId().toString(), transType);
		voucher = TransactionType.Transfer.getValue() + "-" + voucherNumber;
		List<CurrentTransaction> currentTrxnList = new ArrayList<CurrentTransaction>();
		if (subsidyReceivable.compareTo(BigDecimal.ZERO) != 0) {
					
			//loan interest posting
			CurrentTransaction intReceivableDrTrxn = new CurrentTransaction();
			intReceivableDrTrxn.setLineOfCredit(loc);
			intReceivableDrTrxn.setBusinessDate(theBusinessDate);
			intReceivableDrTrxn.setAccount(loc.getAccount());
			intReceivableDrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
			intReceivableDrTrxn.setChannelType(ChannelType.SYSTEM);
			AccountingMoney transAmt = MoneyUtil.getAccountingMoney(subsidyReceivable.abs().setScale(0, RoundingMode.HALF_UP));
			intReceivableDrTrxn.setOpeningBalance(loc.getCurrentBalance());
			intReceivableDrTrxn.setPostedStatus(1);
			intReceivableDrTrxn.setTransactionAmount(transAmt);
			
			intReceivableDrTrxn.setTransactionType(TransactionType.Transfer);
			intReceivableDrTrxn.setCrDr(-1);
			intReceivableDrTrxn.setRemarks("Interest Subsidy posted Receivable");
			
			intReceivableDrTrxn.setPacs(pacs);

			intReceivableDrTrxn.setTransactionCode(TransactionCode.INTEREST_RECEIVABLE);

			intReceivableDrTrxn.setVoucherNumber(voucher);

			// For Credit Transaction
			CurrentTransaction intReceivableCrTrxn = new CurrentTransaction();
			intReceivableCrTrxn.setLineOfCredit(loc);
			intReceivableCrTrxn.setBusinessDate(theBusinessDate);
			intReceivableCrTrxn.setAccount(loc.getAccount());
			intReceivableCrTrxn.setAccountNumber(loc.getAccount().getAccountNumber());
			intReceivableCrTrxn.setChannelType(ChannelType.SYSTEM);

			intReceivableCrTrxn.setOpeningBalance(loc.getCurrentBalance());
			intReceivableCrTrxn.setPostedStatus(1);
			intReceivableCrTrxn.setTransactionAmount(transAmt);
			
			intReceivableCrTrxn.setTransactionType(TransactionType.Transfer);
			intReceivableCrTrxn.setCrDr(1);
			intReceivableCrTrxn.setRemarks("Interest Subsidy Posted Received");
			
			intReceivableCrTrxn.setPacs(pacs);

			intReceivableCrTrxn.setTransactionCode(TransactionCode.INTEREST_RECEIVED);
			intReceivableCrTrxn.setVoucherNumber(voucher);
			currentTrxnList.add(intReceivableDrTrxn);
			currentTrxnList.add(intReceivableCrTrxn);
		} // end for interest		
	}
}
