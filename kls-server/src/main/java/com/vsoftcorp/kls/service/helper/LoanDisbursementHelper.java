package com.vsoftcorp.kls.service.helper;

import java.util.ArrayList;
import java.util.Calendar;

import com.vsoftcorp.time.Date;

import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementEntry;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.AccountData;
import com.vsoftcorp.kls.data.DisbursementDetailsData;
import com.vsoftcorp.kls.data.LoanDisbursementEntryData;
import com.vsoftcorp.kls.data.LoanLineOfCreditData;
import com.vsoftcorp.kls.data.MTMultipleDisburseData;
import com.vsoftcorp.kls.data.PacsLoanApplicationData;
import com.vsoftcorp.kls.data.PacsLoanDisbursementData;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.valuetypes.PacsStatus;
import com.vsoftcorp.kls.valuetypes.transaction.DisbursementStatus;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.kls.data.DisbursementPassingData;
/**
 * 
 * @author a1623
 * 
 */
public class LoanDisbursementHelper {
	private static final Logger logger = Logger.getLogger(LoanDisbursementHelper.class);
	public static LoanDisbursementEntry getPacsLoanPassing(LoanDisbursementEntryData pacsLoanPassingData) {
		LoanDisbursementEntry pacsLoanPassing = new LoanDisbursementEntry();
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		pacsLoanPassing.setId(pacsLoanPassingData.getId());
		pacsLoanPassing.setAmountDeductedFrom(pacsLoanPassingData.getDeductFrom());
		pacsLoanPassing.setChequeDate(DateUtil.getVSoftDateByString(pacsLoanPassingData.getChequeDate()));
		pacsLoanPassing.setChequeNumber(pacsLoanPassingData.getChequeNumber());
		if (pacsLoanPassingData.getPacsSuvikasVoucherNumber() != null) {
			pacsLoanPassing.setPacsSuvikasVoucherNumber(pacsLoanPassingData.getPacsSuvikasVoucherNumber());
		}

		if (pacsLoanPassingData.getPacsSuvikasVoucherDate() != null) {
			pacsLoanPassing.setPacsSuvikasVoucherDate(DateUtil.getVSoftDateByString(pacsLoanPassingData.getPacsSuvikasVoucherDate()));
		}
		
		pacsLoanPassing.setDisbursementAmount(Money.valueOf(Currency.getInstance("INR"), pacsLoanPassingData.getDisbursementAmount()));

		if (pacsLoanPassingData.getModeOfDisbursed() != null)
			pacsLoanPassing.setModeOfDisbursement(TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursed()));
		
		if (pacsLoanPassingData.getModeOfDisbursed() != null) {
			if (pacsLoanPassingData.getModeOfDisbursed().equalsIgnoreCase("C"))
				pacsLoanPassing.setRemarks(pacsLoanPassingData.getRemarks() + "_"
						+ TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursed()) + "_" + pacsLoanPassingData.getTransferedAmount());
			if (pacsLoanPassingData.getModeOfDisbursed().equalsIgnoreCase("Q"))
				pacsLoanPassing.setRemarks(pacsLoanPassingData.getRemarks() + "_"
						+ TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursed()) + "_" + pacsLoanPassingData.getNumber() + "_"
						+ pacsLoanPassingData.getDate() + "_" + pacsLoanPassingData.getTransferedAmount());

			if (pacsLoanPassingData.getModeOfDisbursed().equalsIgnoreCase("D"))
				pacsLoanPassing.setRemarks(pacsLoanPassingData.getRemarks() + "_"
						+ TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursed()) + "_" + pacsLoanPassingData.getNumber() + "_"
						+ pacsLoanPassingData.getDate() + pacsLoanPassingData.getTransferedAmount());
			if (pacsLoanPassingData.getModeOfDisbursed().equalsIgnoreCase("B"))
				pacsLoanPassing.setRemarks(pacsLoanPassingData.getRemarks() == null ? "" : pacsLoanPassingData.getRemarks() + "_"
						+ TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursed()) + "_" + pacsLoanPassingData.getAccountNumber() + "_"
						+ pacsLoanPassingData.getTransferedAmount());
		}
		
		
		if (pacsLoanPassingData.getModeOfDisbursement() != null)
			pacsLoanPassing.setModeOfDisbursement(TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursement()));
		pacsLoanPassing.setToAccountNumber(pacsLoanPassingData.getToAccountNumber());

		if (pacsLoanPassingData.getModeOfDisbursement() != null) {
			if (pacsLoanPassingData.getModeOfDisbursement().equalsIgnoreCase("C"))
				pacsLoanPassing.setRemarks(pacsLoanPassingData.getRemarks() + "_"
						+ TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursement()) + "_" + pacsLoanPassingData.getTransferedAmount());
			if (pacsLoanPassingData.getModeOfDisbursement().equalsIgnoreCase("Q"))
				pacsLoanPassing.setRemarks(pacsLoanPassingData.getRemarks() + "_"
						+ TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursement()) + "_" + pacsLoanPassingData.getNumber() + "_"
						+ pacsLoanPassingData.getDate() + "_" + pacsLoanPassingData.getTransferedAmount());

			if (pacsLoanPassingData.getModeOfDisbursement().equalsIgnoreCase("D"))
				pacsLoanPassing.setRemarks(pacsLoanPassingData.getRemarks() + "_"
						+ TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursement()) + "_" + pacsLoanPassingData.getNumber() + "_"
						+ pacsLoanPassingData.getDate() + pacsLoanPassingData.getTransferedAmount());
			if (pacsLoanPassingData.getModeOfDisbursement().equalsIgnoreCase("B"))
				pacsLoanPassing.setRemarks(pacsLoanPassingData.getRemarks() == null ? "" : pacsLoanPassingData.getRemarks() + "_"
						+ TransactionMode.getType(pacsLoanPassingData.getModeOfDisbursement()) + "_" + pacsLoanPassingData.getAccountNumber() + "_"
						+ pacsLoanPassingData.getTransferedAmount());
		}
		if (pacsLoanPassingData.getLocId() != null) {
			LoanLineOfCredit lineOfCredit = new LoanLineOfCredit();
			lineOfCredit.setId(pacsLoanPassingData.getLocId());
			pacsLoanPassing.setLineOfCredit(lineOfCredit);
		}
		pacsLoanPassing.setCustomerNumber(Long.valueOf(pacsLoanPassingData.getCustomerNumber()));
		pacsLoanPassing.setStatus(DisbursementStatus.getType(pacsLoanPassingData.getStatus()));
		if (pacsLoanPassingData.getPacsId() != null) {
			Pacs pacs = new Pacs();
			pacs.setId(pacsLoanPassingData.getPacsId());
			pacsLoanPassing.setPacs(pacs);
		}
		pacsLoanPassing.setLoanType(pacsLoanPassingData.getLoanType());
		if(pacsLoanPassingData.getInsuranceAmount() != null){
			pacsLoanPassing.setInsuranceAmount(pacsLoanPassingData.getInsuranceAmount());
		}
		if(pacsLoanPassingData.getShareAmount() != null){
			pacsLoanPassing.setShareAmount(pacsLoanPassingData.getShareAmount());
		}
		if(pacsLoanPassingData.getBusinessDate() != null){
			String businessDate="";
			Pacs pacs=pacsDao.getPacs(pacsLoanPassingData.getPacsId());
			logger.info("pacs type::"+pacs.getPacsStatus());
			if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
				businessDate=RestClientUtil.getPacsBusinessDate(pacsLoanPassingData.getPacsId(),pacs.getBranch().getId());
				//businessDate="2017-05-01";
				pacsLoanPassing.setPassingDate(DateUtil.getVSoftDateByString(businessDate));
			}
			else
			pacsLoanPassing.setPassingDate(DateUtil.getVSoftDateByString(pacsLoanPassingData.getBusinessDate()));
		}
		if(pacsLoanPassingData.getVoucherNumber() != null) {
			pacsLoanPassing.setVoucherNumber(pacsLoanPassingData.getVoucherNumber());
		}
		if(pacsLoanPassingData.getDateOfDisbursement()!=null){
			String businessDate="";
			Pacs pacs=pacsDao.getPacs(pacsLoanPassingData.getPacsId());
			logger.info("pacs type::"+pacs.getPacsStatus());
			if(pacs.getPacsStatus().equals(PacsStatus.Offline)){
				businessDate=RestClientUtil.getPacsBusinessDate(pacsLoanPassingData.getPacsId(),pacs.getBranch().getId());
				//businessDate="2017-05-01";
				pacsLoanPassing.setDisbursementDate(DateUtil.getVSoftDateByString(businessDate));
			}
			else
			pacsLoanPassing.setDisbursementDate(DateUtil.getVSoftDateByString(pacsLoanPassingData.getDateOfDisbursement()));
		}
		pacsLoanPassing.setInstallmentDate(DateUtil.getVSoftDateByString(pacsLoanPassingData.getInstallmentDate()));
		Integer noOfInstallments = null;
		Date loanExpiryDate = null;
		Calendar calendar = Calendar.getInstance();
	   noOfInstallments = Integer.parseInt(pacsLoanPassingData.getNoOfInstallments() == null ? "0" : pacsLoanPassingData.getNoOfInstallments());
	   Integer installmentFrequency = Integer.parseInt(pacsLoanPassingData.getInstallmentFrequency() == null ? "0" : pacsLoanPassingData.getInstallmentFrequency());
	   Date installmentDate = DateUtil.getVSoftDateByString(pacsLoanPassingData.getInstallmentDate());
	   for (int i = 0; i < noOfInstallments-1; i++) {
		   loanExpiryDate = Date.valueOf(getInstallmentDate(calendar, installmentDate, installmentFrequency));
		   installmentDate = loanExpiryDate;
	   }
		pacsLoanPassing.setLoanExpiryDate(loanExpiryDate);
		return pacsLoanPassing;
	}
	public static java.util.Date getInstallmentDate(Calendar calendar, Date installmentDate, Integer frequencyValue) {

		logger.info("Start : Generating installment date in getInstallmentDate() method.");
		calendar.setTime(installmentDate.getJavaDate());
		calendar.add(Calendar.MONTH, frequencyValue);
		logger.info(" installment date : " + calendar.getTime());
		logger.info("End : Generating installment date in getInstallmentDate() method.");
		return calendar.getTime();
	}

	public static LoanDisbursementEntryData getLoanDisbursementEntryData(LoanDisbursementEntry loanDisbursementEntry) {
		// TODO Auto-generated method stub

		LoanDisbursementEntryData loanDisbursementEntryData = new LoanDisbursementEntryData();
		loanDisbursementEntryData.setId(loanDisbursementEntry.getId());
		if(loanDisbursementEntry.getChequeDate()!=null)
		loanDisbursementEntryData.setChequeDate(DateUtil.getDateString(loanDisbursementEntry.getChequeDate()));
		loanDisbursementEntryData.setChequeNumber(loanDisbursementEntry.getChequeNumber());
		if (loanDisbursementEntry.getPacsSuvikasVoucherNumber() != null) {
			loanDisbursementEntryData.setPacsSuvikasVoucherNumber(loanDisbursementEntry.getPacsSuvikasVoucherNumber());
		}

		if (loanDisbursementEntry.getPacsSuvikasVoucherDate() != null) {

			loanDisbursementEntryData.setPacsSuvikasVoucherDate(DateUtil.getDateString(loanDisbursementEntry.getPacsSuvikasVoucherDate()));
		}
		loanDisbursementEntryData.setDeductFrom(loanDisbursementEntry.getAmountDeductedFrom());
		if (loanDisbursementEntry.getDisbursementAmount() != null)
			loanDisbursementEntryData.setDisbursementAmount(loanDisbursementEntry.getDisbursementAmount().getAmount());

		if (loanDisbursementEntry.getModeOfDisbursement() != null)
			loanDisbursementEntryData.setModeOfDisbursement(loanDisbursementEntry.getModeOfDisbursement().getValue());

		if (loanDisbursementEntry.getToAccountNumber() != null)
			loanDisbursementEntryData.setToAccountNumber(loanDisbursementEntry.getToAccountNumber());
		if (loanDisbursementEntry.getRemarks() != null)
			loanDisbursementEntryData.setRemarks(loanDisbursementEntry.getRemarks());
		if (loanDisbursementEntry.getStatus() != null)
			loanDisbursementEntryData.setStatus(loanDisbursementEntry.getStatus().getValue());

		loanDisbursementEntryData.setPacsId(loanDisbursementEntry.getPacs().getId());
		if (loanDisbursementEntry.getCustomerNumber() != null) {
			loanDisbursementEntryData.setCustomerNumber(loanDisbursementEntry.getCustomerNumber().toString());
			PersonData customerData = RestClientUtil.getCustomerById(loanDisbursementEntry.getCustomerNumber());
			loanDisbursementEntryData.setCustomerName(customerData.getName());
		}
		if(loanDisbursementEntry.getInsuranceAmount() != null){
			loanDisbursementEntryData.setInsuranceAmount(loanDisbursementEntry.getInsuranceAmount());
		}
		if(loanDisbursementEntry.getShareAmount() != null){
			loanDisbursementEntryData.setShareAmount(loanDisbursementEntry.getShareAmount());
		}
		if (loanDisbursementEntry.getLoanType() != null)
			loanDisbursementEntryData.setLoanType(loanDisbursementEntry.getLoanType());
		if (loanDisbursementEntry.getLineOfCredit() != null)
			loanDisbursementEntryData.setLocId(loanDisbursementEntry.getLineOfCredit().getId());
		
		if(loanDisbursementEntry.getPassingDate() != null) {
			loanDisbursementEntryData.setBusinessDate(DateUtil.getDateString(loanDisbursementEntry.getPassingDate()));
		}
		if(loanDisbursementEntry.getVoucherNumber() != null) {
			loanDisbursementEntryData.setVoucherNumber(loanDisbursementEntry.getVoucherNumber());
		}
		if(loanDisbursementEntry.getDisbursementDate()!=null){
			loanDisbursementEntryData.setDateOfDisbursement(DateUtil.getDateString(loanDisbursementEntry.getDisbursementDate()));
		}
		if(loanDisbursementEntry.getInstallmentDate() != null){
			loanDisbursementEntryData.setInstallmentDate(DateUtil.getDateString(loanDisbursementEntry.getInstallmentDate()));
		}
		if(loanDisbursementEntry.getLoanExpiryDate() != null){
			loanDisbursementEntryData.setLoanExpiryDate(DateUtil.getDateString(loanDisbursementEntry.getLoanExpiryDate()));
		}
		
		return loanDisbursementEntryData;
	}
	
  public static DisbursementPassingData getLoanDisbursementPassingData(Object[] data){
		DisbursementPassingData passingData=new DisbursementPassingData();
		if(data[2]!=null){		
		passingData.setInstrumentNo(data[2].toString());
		}
		else
			passingData.setInstrumentNo("");
		if(data[0]!=null){
			passingData.setEntryDate(data[0].toString());
		}
		if(data[1]!=null){
		 passingData.setTotDisburseAmt(data[1].toString());
		}
return passingData;

}


public static PacsLoanDisbursementData getPacsLoanDisbursementData(LoanLineOfCreditData list,LoanDisbursementEntryData dataLst,List<PacsLoanApplicationData> loanApplicationDataList,List<AccountData> accountDataList){
	logger.info("inside helper::"+loanApplicationDataList);
	List<PacsLoanDisbursementData> pacsDisbursementDataList=new ArrayList<PacsLoanDisbursementData>();
	PacsLoanDisbursementData pacsDisbursementData=new PacsLoanDisbursementData();
	
		pacsDisbursementData.setPacsId(dataLst.getPacsId());
		pacsDisbursementData.setCustomerId(Long.parseLong(dataLst.getCustomerNumber()));
		logger.info("checking:::");
		pacsDisbursementData.setCustomerName(loanApplicationDataList.get(0).getCustomerName());
		logger.info("checking:::"+dataLst.getDateOfDisbursement());
		pacsDisbursementData.setDateOfDisbursement(dataLst.getDateOfDisbursement());
		pacsDisbursementData.setDeductFrom("L");
		pacsDisbursementData.setDisbursementscheduleAmount("0.00");
		pacsDisbursementData.setInsuranceAmountDeducted(list.getInsuranceAmount());
		pacsDisbursementData.setInterestCategory(list.getInterestCategoryDesc());
		pacsDisbursementData.setMarginalAmount(loanApplicationDataList.get(0).getMarginalAmount());
		pacsDisbursementData.setMarginalPercentage(loanApplicationDataList.get(0).getMarginalPercentage());
		pacsDisbursementData.setLineOfCreditId(list.getId());
		logger.info("disbursement amount::"+dataLst.getAmountDisbursed());
		pacsDisbursementData.setAmountDisbursed(dataLst.getAmountDisbursed());
		pacsDisbursementData.setPenalRoi(list.getPenalInterest());
		pacsDisbursementData.setProcessingFee(list.getProcessingFee());
		pacsDisbursementData.setProductId(list.getLoanAccountPropertyData().getProductId());
		pacsDisbursementData.setProductName(loanApplicationDataList.get(0).getProductName());
		pacsDisbursementData.setPurposeId(loanApplicationDataList.get(0).getPurposeId());
		pacsDisbursementData.setPurposeName(loanApplicationDataList.get(0).getPurposeName());
		pacsDisbursementData.setRoi(list.getRateOfInterest());
		pacsDisbursementData.setSanctionDate(list.getSanctionDate());
		pacsDisbursementData.setSanctionedAmount(list.getSanctionAmount());
		pacsDisbursementData.setShareAmountDeducted(list.getShareAmount());
		pacsDisbursementData.setStandAloneStatus(true);
		pacsDisbursementData.setSubPurposeId(loanApplicationDataList.get(0).getSubPurposeId());
		pacsDisbursementData.setSubPurposeName(loanApplicationDataList.get(0).getSubPurposeName());
		pacsDisbursementData.setToAccountNumber(accountDataList.get(0).getSavingBankAccNo());
		logger.info("after setting"+pacsDisbursementData);
	//	pacsDisbursementDataList.add(pacsDisbursementData);
		
	
	logger.info("after setting");
	return pacsDisbursementData;
}

public static MTMultipleDisburseData getMTPassingData(LoanDisbursementEntryData passingData){
	MTMultipleDisburseData disburseData=new MTMultipleDisburseData();
	if(passingData.getCustomerName()!=null)
		disburseData.setMemeberName(passingData.getCustomerName());
	if(passingData.getCustomerNumber()!=null)
		disburseData.setMemberNum(passingData.getCustomerNumber());
	if(passingData.getDisbursementAmount()!=null)
		disburseData.setDisburseAmt(passingData.getDisbursementAmount().toString());
	if(passingData.getModeOfDisbursement()!=null)
		disburseData.setDisburseMode(passingData.getModeOfDisbursement());
	if(passingData.getRemarks()!=null)
	disburseData.setRemarks(passingData.getRemarks());
	if(passingData.getLocId()!=null)
		disburseData.setLocId(passingData.getLocId().toString());
	return disburseData;
}
public static DisbursementDetailsData getLoanDisbursementDetailsData(Object[] data){
	DisbursementDetailsData disData=new DisbursementDetailsData();
	if(data[0]!=null)
		disData.setDisbursementAmt(data[0].toString());
	if(data[1]!=null)
		disData.setCustomerId(data[1].toString());
	if(data[2]!=null)
		disData.setId(data[2].toString());
	return disData;
}
}
