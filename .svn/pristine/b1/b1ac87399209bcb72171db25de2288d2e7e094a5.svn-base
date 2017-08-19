package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanDisbursement;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.PacsLoanDisbursementData;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;

public class PacsLoanDisbursementHelper {

	public static PacsLoanDisbursement getLoanDisbursement(PacsLoanDisbursementData loanDisbursementData) {

		PacsLoanDisbursement loanDisbursement = new PacsLoanDisbursement();
		if (loanDisbursementData.getId() != null) {
			loanDisbursement.setId(loanDisbursementData.getId());
		}
		loanDisbursement.setDisbursementDate(DateUtil.getVSoftDateByString(loanDisbursementData.getDateOfDisbursement()));
		loanDisbursement.setDisbursedAmount(MasterHelper.populateAmountParam(loanDisbursementData.getAmountDisbursed()));
		//loanDisbursement.setModeOfDisbursement(loanDisbursementData.getModeOfDisbursed());
		loanDisbursement.setToAccountNumber(loanDisbursementData.getToAccountNumber());
		loanDisbursement.setRemarks(loanDisbursementData.getRemarks());

		if (loanDisbursementData.getLineOfCreditId() != null) {
			LineOfCredit lineOfCredit = new LineOfCredit();
			lineOfCredit.setId(loanDisbursementData.getLineOfCreditId());
			loanDisbursement.setLineOfCredit(lineOfCredit);
		}

		if (loanDisbursementData.getDeductFrom() != null) {
			loanDisbursement.setAmountDeductedFrom(loanDisbursementData.getDeductFrom());
		}

		if (loanDisbursementData.getPacsSuvikasVoucherNumber() != null) {
			loanDisbursement.setPacsSuvikasVoucherNumber(loanDisbursementData.getPacsSuvikasVoucherNumber());
		}

		if (loanDisbursementData.getPacsSuvikasVoucherDate() != null) {
			loanDisbursement.setPacsSuvikasVoucherDate(DateUtil.getVSoftDateByString(loanDisbursementData.getPacsSuvikasVoucherDate()));
		}

		if (loanDisbursementData.getModeOfDisbursed() != null)
			loanDisbursement.setModeOfDisbursement(TransactionMode.getType(loanDisbursementData.getModeOfDisbursed()));

		return loanDisbursement;
	}

	public static PacsLoanDisbursementData getLoanDisbursementData(PacsLoanDisbursement loanDisbursement) {
		PacsLoanDisbursementData loanDisbursementData = new PacsLoanDisbursementData();

		if (loanDisbursement.getId() != null) {
			loanDisbursementData.setId(loanDisbursement.getId());
		}
		if (loanDisbursement.getDisbursementDate() != null) {
			loanDisbursementData.setDateOfDisbursement(loanDisbursement.getDisbursementDate().toString());
		}
		if (loanDisbursement.getDisbursedAmount() != null) {
			loanDisbursementData.setAmountDisbursed(loanDisbursement.getDisbursedAmount().toString());
		}
		//loanDisbursementData.setModeOfDisbursed(loanDisbursement.getModeOfDisbursement());
		loanDisbursementData.setToAccountNumber(loanDisbursement.getToAccountNumber());
		loanDisbursementData.setRemarks(loanDisbursement.getRemarks());
		if (loanDisbursement.getLineOfCredit() != null) {
			loanDisbursementData.setLineOfCreditId(loanDisbursement.getLineOfCredit().getId());
		}

		if (loanDisbursement.getAmountDeductedFrom() != null) {
			loanDisbursementData.setDeductFrom(loanDisbursement.getAmountDeductedFrom());
		}

		if (loanDisbursement.getPacsSuvikasVoucherNumber() != null) {
			loanDisbursementData.setPacsSuvikasVoucherNumber(loanDisbursement.getPacsSuvikasVoucherNumber());
		}

		if (loanDisbursement.getPacsSuvikasVoucherDate() != null) {
			loanDisbursementData.setPacsSuvikasVoucherDate(loanDisbursement.getPacsSuvikasVoucherDate().toString());
		}

		if (loanDisbursement.getModeOfDisbursement() != null)
			loanDisbursementData.setModeOfDisbursed(loanDisbursement.getModeOfDisbursement().getValue());

		return loanDisbursementData;
	}

}
