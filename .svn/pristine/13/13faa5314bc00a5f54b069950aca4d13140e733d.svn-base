package com.vsoftcorp.kls.service.account.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.kls.business.entities.EventType;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.BorrowingRecoveryEntry;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.BorrowingRecoveryEntryData;
import com.vsoftcorp.kls.data.BorrowingsLineOfCreditData;
import com.vsoftcorp.kls.data.LoanRecoveryData;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IRecoveryOrderDao;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingRecoveryEntryDAO;
import com.vsoftcorp.kls.service.account.IBorrowingRecoveryEntryService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.BorrowingRecoveryEntryHelper;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.VoucherNumberUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.valuetypes.CBSMethodEnum;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.DisbursementStatus;
import com.vsoftcorp.kls.valuetypes.transaction.RecoveryOrder;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;

public class BorrowingRecoveryEntryService implements IBorrowingRecoveryEntryService{
	private static final Logger logger = Logger.getLogger(BorrowingRecoveryEntryService.class);

	@Override
	public String saveBorrowingRecoveryEntry(
			BorrowingRecoveryEntryData borrowingData) {

		logger.info("Start : Calling BorrowingRecoveryEntry dao in saveBorrowingRecoveryEntryMaster() method.");
		// get the BorrowingRecoveryEntry master dao.
		IBorrowingRecoveryEntryDAO dao = KLSDataAccessFactory.getBorrowingRecoveryEntryDao();
		// get the entity pojo.
		BorrowingRecoveryEntry master = BorrowingRecoveryEntryHelper.getBorrowingRecoveryEntry(borrowingData);
		BorrowingRecoveryEntry dbMaster = null;
		String status = null;
		try {
			dbMaster = dao.getBorrowingRecoveryEntry(master, false);
			// if BorrowingRecoveryEntry code does not exist in db, then save.
			if (dbMaster == null) {
				dao.saveBorrowingRecoveryEntry(master);
				status = "Borrowing Recovery Entry saved successfully";
			}else{
				dao.updateBorrowingRecoveryEntry(master);
				status = "Borrowing Recovery Entry updated successfully";
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("BorrowingRecoveryEntryData cannot be saved");
			throw new KlsRuntimeException("Borrowing Recovery Entry cannot be saved", excp);
		}
	
		logger.info("End : Calling BorrowingRecoveryEntry dao in saveBorrowingRecoveryEntryMaster() method.");
		return status;
	}

	@Override
	public Map getBorrowingRecoveryAmounts(Long accountId) {
		// TODO Auto-generated method stub
		Map map = new HashMap<>();
		IBorrowingRecoveryEntryDAO dao = KLSDataAccessFactory.getBorrowingRecoveryEntryDao();
		map = dao.getBorrowingRecoveryAmounts(accountId);
		AccountingMoney currBal = (AccountingMoney)map.get("principleBalance");
		map.put("principleBalance", currBal.getMoney().getAmount());
		return map;
	}

	@Override
	public List<BorrowingRecoveryEntryData> getBorrowingRecoveryEntries(
			Integer borrowingProdId, Integer status) {

		logger.info("Start: Fetching all the BorrowingRecoveryEntry  data from the database in getAllBorrowingRecoveryEntry() method.");
		IBorrowingRecoveryEntryDAO dao = KLSDataAccessFactory.getBorrowingRecoveryEntryDao();
		List<BorrowingRecoveryEntryData> chrgesList = new ArrayList<BorrowingRecoveryEntryData>();
		try {

			List<BorrowingRecoveryEntry> entriesList = dao.getAllBorrowingRecoveryEntriesByProdId(borrowingProdId, status);
			for (BorrowingRecoveryEntry master : entriesList) {
				chrgesList.add(BorrowingRecoveryEntryHelper.getBorrowingRecoveryEntryData(master));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all BorrowingRecoveryEntry from the database");
			throw new DataAccessException("Error while retriving all BorrowingRecoveryEntry from the database", e.getCause());
		} 
		logger.info("End: Fetching all the BorrowingRecoveryEntry  data from the database in getAllBorrowingRecoveryEntry() method.");
		return chrgesList;
	}
	
	
	@Override
	public List<BorrowingRecoveryEntryData> getBorrowingRecoveryEntriesByPacsId(
			Integer borrowingProdId, Integer status) {

		logger.info("Start: Fetching all the BorrowingRecoveryEntry  data from the database in getAllBorrowingRecoveryEntry() method.");
		IBorrowingRecoveryEntryDAO dao = KLSDataAccessFactory.getBorrowingRecoveryEntryDao();
		List<BorrowingRecoveryEntryData> chrgesList = new ArrayList<BorrowingRecoveryEntryData>();
		try {

			List<BorrowingRecoveryEntry> entriesList = dao.getAllBorrowingRecoveryEntriesByPacsId(borrowingProdId, status);
			for (BorrowingRecoveryEntry master : entriesList) {
				chrgesList.add(BorrowingRecoveryEntryHelper.getBorrowingRecoveryEntryData(master));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all BorrowingRecoveryEntry from the database");
			throw new DataAccessException("Error while retriving all BorrowingRecoveryEntry from the database", e.getCause());
		} 
		logger.info("End: Fetching all the BorrowingRecoveryEntry  data from the database in getAllBorrowingRecoveryEntry() method.");
		return chrgesList;
	}

	@Override
	public String updateBorrowingRecoveryEntry(BorrowingRecoveryEntryData data) {
		// TODO Auto-generated method stub
		IBorrowingRecoveryEntryDAO dao = KLSDataAccessFactory.getBorrowingRecoveryEntryDao();
		BorrowingRecoveryEntry master = null;
		try{
			if(data!=null&&data.getId()!=null)
			master=BorrowingRecoveryEntryHelper.getBorrowingRecoveryEntry(data);
			if(master.getId()!=null){
				dao.updateBorrowingRecoveryEntry(master);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error While Updating BorrowingRecoveryEntry Data ");
			throw new KlsRuntimeException("Unable to update BorrowingRecoveryEntry Data ", e.getCause());
		}
		logger.info("End :Successfully Completed calling updateBorrowingRecoveryEntry()");

		return "Borrowing Recovery Entry updated successfully";
	}

	@Override
	public String deleteBorrowingRecoveryEntry(Long borrowingRecoveryId) {
		logger.info("Start : inside deleteBorrowingAccountProperty() method.--");
		IBorrowingRecoveryEntryDAO dao = KLSDataAccessFactory.getBorrowingRecoveryEntryDao();
		
			try {
				
				if (borrowingRecoveryId != null) 
					dao.deleteBorrowingRecoveryEntry(borrowingRecoveryId);
				else
					return "can not delete the BorrowingRecoveryEntry";
			} catch (Exception excp) {
				logger.error("Error while deleting the BorrowingRecoveryEntry from the database");
			}
		

		logger.info("successfully deleted the Borrowing Recovery Entry");
		return "Borrowing Recovery Entry deleted successfully";
	}
	
	@Override
	public BorrowingRecoveryEntryData getBorrowingRecoveryEntryById(Long id) {
		IBorrowingRecoveryEntryDAO dao = KLSDataAccessFactory.getBorrowingRecoveryEntryDao();
		return BorrowingRecoveryEntryHelper.getBorrowingRecoveryEntryData(dao.getBorrowingRecoveryEntryById(id));
	}
	
	
	@Override
	public String doBorrowingRecovery(BorrowingRecoveryEntryData data){
		logger.info("Start : inside doBorrowingRecovery() method.");
		IBorrowingRecoveryEntryDAO dao = KLSDataAccessFactory.getBorrowingRecoveryEntryDao();
		String status = "false";
		List<CurrentTransaction> currentTransactionsList = new ArrayList<CurrentTransaction>();
		Integer pacsId = data.getPacsId();
		IPacsDAO pdao = KLSDataAccessFactory.getPacsDAO();
		Pacs pac = pdao.getPacs(pacsId);
		String cashInHandGl = pac.getCashGl();
		Integer voucherNumberforIntPosting = VoucherNumberUtil.getVoucherNumber(pacsId.toString(), TransactionType.Borrowings.getValue());
		String voucherForIntPost = TransactionType.Transfer.getValue() + "-" + voucherNumberforIntPosting;
		AccountingMoney totalAmountPaid = MoneyUtil.getAccountingMoney(data.getRecoveryAmount());
		/*AccountingMoney principlePaid = MoneyUtil.getAccountingMoney(data.getPrinciplaBalance());
		AccountingMoney interestPaid = MoneyUtil.getAccountingMoney(data.getInterestPayable());
		AccountingMoney penalInterestPaid = MoneyUtil.getAccountingMoney(data.getPenalInterestPayable());*/
		try{
			List<BorrowingsLineOfCredit> bLocsList = dao.getBlocsPerBproduct(data.getBorrowingProdId(), data.getPacsId());
			CurrentTransaction master = new CurrentTransaction();
			
			if(!KLSDataAccessFactory.getBankParameter().getCbsIntegrationMethod().equals(CBSMethodEnum.PROPRIETARY)){
				
				if(data.getModeOfCollection().equalsIgnoreCase("C")){
					populateCurrentTransactionRecord(null, data.getAccountNo(), currentTransactionsList, TransactionCode.CASH_TRANSFER, "Reocovery:Cash in hand GL", 1,
							totalAmountPaid, true, TransactionType.Borrowings, voucherForIntPost);
					
					populateCurrentTransactionRecord(null, cashInHandGl, currentTransactionsList, TransactionCode.CASH_TRANSFER, "Reocovery:Cash in hand GL", -1,
							totalAmountPaid, true, TransactionType.Borrowings, voucherForIntPost);
					
				}else if(data.getModeOfCollection().equalsIgnoreCase("Q")){
					
					String pacsBankAccount = pac.getPacsBankAccNumber();
					
					populateCurrentTransactionRecord(null, data.getAccountNo(), currentTransactionsList, TransactionCode.CASH_TRANSFER, "Reocovery:Cash in hand GL", 1,
							totalAmountPaid, true, TransactionType.Borrowings, voucherForIntPost);
					
					populateCurrentTransactionRecord(null, pacsBankAccount, currentTransactionsList, TransactionCode.PACS_BANK_ACC, "Reocovery:Pacs bank account", -1,
							totalAmountPaid, true, TransactionType.Borrowings, voucherForIntPost);
				}
				
				
				for(BorrowingsLineOfCredit bLoc : bLocsList){
					Pacs pacs = bLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs();
					
					logger.info("pacsId : " + pacsId);
					
					String cashGl = pacs.getCashGl();
					
						AccountingMoney currentBalance = bLoc.getCurrentBalance();
						logger.info(" currentBalance : " + currentBalance);
						AccountingMoney interestReceivable = MoneyUtil.getAccountingMoney(bLoc.getInterestReceivable());
						AccountingMoney penalInterestReceivable = MoneyUtil.getAccountingMoney(bLoc.getPenalInterestReceivable());
						logger.info(" interestReceivable : " + interestReceivable);
						logger.info(" penalInterestReceivable : " + penalInterestReceivable);
						if (!(penalInterestReceivable.isZero() || penalInterestReceivable.isCredit())) {
								AccountingMoney netMoney = penalInterestReceivable.add(totalAmountPaid);
								logger.info(" netMoney : " + netMoney);
								if (netMoney.isZero() || netMoney.isDebit()) {
									bLoc.setPenalInterestReceivable(netMoney.getMoney().getAmount());
									populateRemarks(master, bLoc, "towards penal interest:");
									populateCurrentTransactionRecord(bLoc, cashGl, currentTransactionsList, TransactionCode.CASH_TRANSFER, master.getRemarks(), -1,
											totalAmountPaid, false, TransactionType.Borrowings, voucherForIntPost);
									totalAmountPaid = AccountingMoney.ZERO;
								} else {
									bLoc.setPenalInterestReceivable(BigDecimal.ZERO);
									AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(penalInterestReceivable.getMoney().getAmount(),
											DebitOrCredit.CREDIT);
									logger.info(" creditAmount : " + creditAmount);
									populateRemarks(master, bLoc, "towards penal interest:");
									populateCurrentTransactionRecord(bLoc, cashGl, currentTransactionsList, TransactionCode.CASH_TRANSFER, master.getRemarks(), -1,
											creditAmount, false, TransactionType.Borrowings, voucherForIntPost);
									totalAmountPaid = netMoney;
								}
							}
						
						if (!(interestReceivable.isZero() || interestReceivable.isCredit())) {
								AccountingMoney netMoney = interestReceivable.add(totalAmountPaid);
								logger.info(" netMoney : " + netMoney);
								if (netMoney.isZero() || netMoney.isDebit()) {
									bLoc.setInterestReceivable(netMoney.getMoney().getAmount());
									populateRemarks(master, bLoc, "towards interest:");
									populateCurrentTransactionRecord(bLoc, cashGl, currentTransactionsList, TransactionCode.CASH_TRANSFER, master.getRemarks(), -1,
											totalAmountPaid, false, TransactionType.Borrowings, voucherForIntPost);
									totalAmountPaid = AccountingMoney.ZERO;
								} else {
									bLoc.setInterestReceivable(BigDecimal.ZERO);
									AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(interestReceivable.getMoney().getAmount(),
											DebitOrCredit.CREDIT);
									logger.info(" creditAmount : " + creditAmount);
									populateRemarks(master, bLoc, "towards interest:");
									populateCurrentTransactionRecord(bLoc, cashGl, currentTransactionsList, TransactionCode.CASH_TRANSFER, master.getRemarks(), -1,
											creditAmount, false, TransactionType.Borrowings, voucherForIntPost);
									totalAmountPaid = netMoney;
								}
							}
							
						if (!(currentBalance.isZero() || currentBalance.isCredit())) {
								AccountingMoney netMoney = currentBalance.add(totalAmountPaid);
								logger.info(" netMoney : " + netMoney);
								
								if (netMoney.isZero() || netMoney.isDebit()) {
									bLoc.setCurrentBalance(netMoney);
									populateRemarks(master, bLoc, "towards principal:");
										// Debit
									populateCurrentTransactionRecord(bLoc, cashGl, currentTransactionsList, TransactionCode.CASH_TRANSFER, master.getRemarks(), -1,
												totalAmountPaid, false, TransactionType.Borrowings, voucherForIntPost);
									
									totalAmountPaid = AccountingMoney.ZERO;
								} else {
									bLoc.setCurrentBalance(AccountingMoney.ZERO);
									AccountingMoney creditAmount = MoneyUtil.getAccountingMoney(currentBalance.getMoney().getAmount(), DebitOrCredit.CREDIT);
									logger.info(" creditAmount : " + creditAmount);
									populateRemarks(master, bLoc, "towards principal:");
									populateCurrentTransactionRecord(bLoc, cashGl, currentTransactionsList, TransactionCode.CASH_TRANSFER, master.getRemarks(), -1,
											creditAmount, false, TransactionType.Borrowings, voucherForIntPost);
									totalAmountPaid = netMoney;
								}
				
						}
						updateLineOfCredit(bLoc);
				}
			}
			logger.info("currentTransactionsList size : " + currentTransactionsList.size());

			KLSDataAccessFactory.getCurrentTransactionDAO().saveCurrentTransactionsList(currentTransactionsList);
			
			status = "true";
		}catch(Exception e){
			status = "false";
			e.printStackTrace();
			return "Error while Passing the Borrowing Recovery Entry";
		}
		if(status.equalsIgnoreCase("true")){
			
			BorrowingRecoveryEntry master = BorrowingRecoveryEntryHelper.getBorrowingRecoveryEntry(data);
			BorrowingRecoveryEntry master1 = dao.getBorrowingRecoveryEntry(master, false);
			master1.setStatus(DisbursementStatus.Passed);
			dao.updateBorrowingRecoveryEntry(master1);
			return "Borrowing Recovery Entry Passed Successfully with voucher number "+voucherForIntPost;
		}
		else
			return "Error while Passing the Borrowing Recovery Entry";
	}
	
	
	public void populateCurrentTransactionRecord(BorrowingsLineOfCredit loanLoc, String accountNumber, List<CurrentTransaction> currentTransactionList,
			TransactionCode transactionCode, String remarks, Integer crdr, AccountingMoney transactionAmount, boolean isCashGl,
			TransactionType transactionType, String voucherNumber) {

		logger.info("Start: Populating the recovery current transaction record in populateCurrentTransactionRecord()");
		CurrentTransaction currentTransaction = new CurrentTransaction();
		if(loanLoc!=null){
			currentTransaction.setAccount(loanLoc.getAccount());
			currentTransaction.setPacs(loanLoc.getLoanAccountProperty().getAccount().getAccountProperty().getPacs());
		}
		currentTransaction.setAccountNumber(accountNumber);
		currentTransaction.setBusinessDate(LoanServiceUtil.getBusinessDate());
		currentTransaction.setChannelType(ChannelType.SYSTEM);
		currentTransaction.setCrDr(crdr);
		if (isCashGl) {
			currentTransaction.setLineOfCredit(null);
			currentTransaction.setOpeningBalance(null);
		} else {
			currentTransaction.setLineOfCredit(loanLoc);
			currentTransaction.setOpeningBalance(loanLoc.getCurrentBalance());
		}
		
		currentTransaction.setPostedStatus(1);
		currentTransaction.setRemarks(remarks);
		currentTransaction.setTerminalId(null);
		currentTransaction.setTransactionAmount(transactionAmount);
		currentTransaction.setTransactionCode(transactionCode);
		currentTransaction.setTransactionType(transactionType);
		currentTransaction.setVoucherNumber(voucherNumber);
		currentTransactionList.add(currentTransaction);
		logger.info("End: Populating the recovery current transaction record in populateCurrentTransactionRecord()");

	}
	
	private void populateRemarks(CurrentTransaction master, LineOfCredit lineOfCredit, String appendString) {

		String remarks = master.getRemarks();
		StringBuilder remarksBuilder = new StringBuilder();
		if (remarks != null) {
			remarksBuilder.append(appendString);
			remarksBuilder.append(lineOfCredit.getId());
			remarksBuilder.append(remarks);
		} else {
			remarksBuilder.append(appendString);
			remarksBuilder.append(lineOfCredit.getId());
		}
		if (remarksBuilder.length() > 60) {
			master.setRemarks(remarksBuilder.substring(0, 60));
		} else {
			master.setRemarks(remarksBuilder.toString());
		}
	}
	
	public void updateLineOfCredit(LineOfCredit lineOfCredit) {
		KLSDataAccessFactory.getLineOfCreditDAO().updateLineOfCredit(lineOfCredit);
	}
	
	@Override
	public LoanRecoveryData getRocoveryInfoBasedOnAmountPaid(BigDecimal amountPaid, Integer recoverySequenceId, Long loanLocId){
		LoanRecoveryData loanRecoveryData = new LoanRecoveryData();
		IRecoveryOrderDao rDao = KLSDataAccessFactory.getRecoveryOrderDAO();
		List<EventType> seqList = rDao.getAllEventTypeBasedOnEventDefinition(recoverySequenceId, false);
		
		BorrowingsLineOfCreditData loanRecoveryData1 = KLSServiceFactory.getBorrowingLineOfCreditService().getBorrowingsLineOfCreditById(loanLocId);
		loanRecoveryData.setPrincipalPaid(BigDecimal.ZERO);
		loanRecoveryData.setInterestPaid(BigDecimal.ZERO);
		loanRecoveryData.setPenalInterestPaid(BigDecimal.ZERO);
		if (seqList.size() > 0) {
			for (EventType e : seqList) {
				if(amountPaid.compareTo(BigDecimal.ZERO)<=0){
				break;
				}
				if (e.getRecoverySequence() == RecoveryOrder.PRINCIPAL.getValue()) {
					if(loanRecoveryData1.getCurrentBalance().compareTo(amountPaid) < 0 ){
					loanRecoveryData.setPrincipalPaid(loanRecoveryData1.getCurrentBalance());
					amountPaid =amountPaid.subtract(loanRecoveryData1.getCurrentBalance());
					}else{
					 loanRecoveryData.setPrincipalPaid(amountPaid);
					 amountPaid=BigDecimal.ZERO;
					}
				}
				
				if (e.getRecoverySequence() == RecoveryOrder.INTEREST.getValue()) {
					if(loanRecoveryData1.getInterestReceivable().compareTo(amountPaid) < 0 ){
						loanRecoveryData.setInterestPaid(loanRecoveryData1.getInterestReceivable());
						amountPaid =amountPaid.subtract(loanRecoveryData1.getInterestReceivable());
					}else{
						loanRecoveryData.setInterestPaid(amountPaid);
						amountPaid=BigDecimal.ZERO;
					}
				}
				
				if (e.getRecoverySequence() == RecoveryOrder.PENAL_INTEREST.getValue()) {
					if(loanRecoveryData1.getPenalInterestReceivable().compareTo(amountPaid) < 0 ){
						loanRecoveryData.setPenalInterestPaid(loanRecoveryData1.getPenalInterestReceivable());
						amountPaid =amountPaid.subtract(loanRecoveryData1.getPenalInterestReceivable());
					}else{
						loanRecoveryData.setPenalInterestPaid(amountPaid);
						amountPaid=BigDecimal.ZERO;
					}
				}
				
			}
	 }

	return loanRecoveryData;
 }
	
}
