package com.vsoftcorp.kls.loans.gl.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.BorrowingsAccount;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.AccountProperty;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.business.util.PropertiesUtil;
import com.vsoftcorp.kls.dataaccess.IBankParameterDAO;
import com.vsoftcorp.kls.dataaccess.IBorrowingsAccountDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.dataaccess.loan.IAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.dataaccess.transaction.ITransactionHistoryDAO;
import com.vsoftcorp.kls.dataaccess.transaction.result.classes.TransactionRecordByProductAndCrdr;
import com.vsoftcorp.kls.gl.GLEntrySummary;
import com.vsoftcorp.kls.gl.GLExtractFile;
import com.vsoftcorp.kls.loans.gl.service.GLExtractRecordBuilder;
import com.vsoftcorp.kls.loans.gl.service.ILoansGLEntriesService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

public class LoansGLEntriesSerivce implements ILoansGLEntriesService {

	private static Logger logger = Logger.getLogger(LoansGLEntriesSerivce.class);

	private static volatile ILoansGLEntriesService INSTANCE = null;

	private static final String GL_DATE_FORMAT = "dd-MM-yyyy_HH:mm:ss";

	public static ILoansGLEntriesService getInstance() {
		if (INSTANCE == null) {
			return INSTANCE = new LoansGLEntriesSerivce();
		}
		return INSTANCE;
	}

	public void extractGLEntries() {

	}

	public void extractGLEntries(final Integer theInstitutionId) {

	}

	public void extractGLEntries(final Integer theInstitutionId, final Date theCurrentBusinessDate,
			final Set<Date> theProcessingDates) {

	}

	private synchronized void writeIntoTheFile(List<GLEntrySummary> theGlEntrySummaries, GLExtractFile extractFile) {

		for (GLEntrySummary glEntrySummary : theGlEntrySummaries) {
			GLExtractRecordBuilder recordBuilder = new GLExtractRecordBuilder(glEntrySummary);
			extractFile.write(recordBuilder.constructGLRecord());
		}
	}

	/**
	 * 
	 * @param theGlEntrySummaries
	 * @param extractFile
	 */
	private synchronized void writeIntoTheExcelFile(List<GLEntrySummary> theGlEntrySummaries, GLExtractFile extractFile) {

		GLExtractExcelRecordBuilder recordExcelBuilder = new GLExtractExcelRecordBuilder(theGlEntrySummaries);
		extractFile.write(recordExcelBuilder.constructExcelGLRecord());
	}

	public static List<Integer> getInstitutionIds() {

		List<Integer> instutionIds = new ArrayList<Integer>();
		instutionIds.add(1);
		instutionIds.add(2);
		return instutionIds;
	}

	/**
	 * 
	 */
	public void extractGLEntries(String effectiveDateString) {

		logger.info("Start: extracting gl entries from the current transaction/transaction history tables in extractGLEntries() method.");
		IBankParameterDAO bankParameterDao = KLSDataAccessFactory.getBankParameterDAO();
		ICurrentTransactionDAO currentTransactionDao = KLSDataAccessFactory.getCurrentTransactionDAO();
		ITransactionHistoryDAO transactionHistoryDao = KLSDataAccessFactory.getTransactionHistoryDAO();
		List<BankParameter> bankParameterList = bankParameterDao.getAllBankParameters();
		logger.info(" effectiveDateString : " + effectiveDateString);
		if (!bankParameterList.isEmpty()) {
			BankParameter bankParameter = bankParameterList.get(0);
			Date businessDate = bankParameter.getBusinessDate();
			logger.info(" businessDate : " + businessDate);
			String bankCode = bankParameter.getBankCode();
			logger.info(" bankCode : " + bankCode);
			long businessDateInSecs = businessDate.getTimeInMillis();
			Date effectiveDate = DateUtil.getVSoftDateByString(effectiveDateString);
			logger.info(" effectiveDate : " + effectiveDate);
			long effectiveDateInSecs = effectiveDate.getTimeInMillis();
			List<GLEntrySummary> glEntrySummaryList = new ArrayList<GLEntrySummary>();
			if (businessDateInSecs == effectiveDateInSecs) {
				boolean isCurrTransaction = true;
				fetchGLTransactionRecords(isCurrTransaction);
				List<CurrentTransaction> currTranList = currentTransactionDao
						.getCurrentTransactionsByDate(effectiveDate.toString());
				glEntrySummaryList = generateGLEntrySummaryList(currTranList, effectiveDate, bankCode, businessDate);
			} else if (effectiveDateInSecs < businessDateInSecs) {
				fetchGLTransactionRecords(false);
				List<TransactionHistory> tranHistoryList = transactionHistoryDao
						.getTransactionHistoryRecordsByDate(effectiveDate.toString());
				glEntrySummaryList = generateTranHistoryGLEntrySummaryList(tranHistoryList, effectiveDate, bankCode,
						businessDate);
			} else {
				logger.error("Cannot generate gl extract file as effective date is after the business date.");
				throw new KlsRuntimeException(
						"Cannot generate gl extract file as effective date is after the business date.");
			}
			String fileName = PropertiesUtil.getProperty("PACS_GL_FILE_NAME");
			logger.info(" fileName : " + fileName);
			fileName = getFileNameWithTimestamp(fileName);
			logger.info(" fileName with timestamp : " + fileName);
			GLExtractFile glExtractFile = new GLExtractFile(fileName);
			writeIntoTheExcelFile(glEntrySummaryList, glExtractFile);
		} else {
			logger.error("Cannot generate gl extract file as bank parameter record does not exist in the database.");
			throw new KlsRuntimeException(
					"Cannot generate gl extract file as bank parameter record does not exist in the database.");
		}
		logger.info("End: extracting gl entries from the current transaction/transaction history tables in extractGLEntries() method.");
	}

	/**
	 * 
	 * @param flag
	 */
	private void fetchGLTransactionRecords(boolean flag) {

		logger.info("Start: fetch gl transaction records from the current transaction/transaction history tables in fetchGLTransactionRecords() method.");
		ICurrentTransactionDAO currentTransactionDao = KLSDataAccessFactory.getCurrentTransactionDAO();
		ITransactionHistoryDAO transactionHistoryDao = KLSDataAccessFactory.getTransactionHistoryDAO();
		if (flag) {
			List<TransactionRecordByProductAndCrdr> currTranList = currentTransactionDao
					.getCurrentTransactionsByProductAndCrdr();
			getBorrowingAccount(currTranList);
		} else {
			List<TransactionRecordByProductAndCrdr> tranHistoryList = transactionHistoryDao
					.getTransactionHistoryRecordsByProductAndCrdr();
			getBorrowingAccount(tranHistoryList);
		}
		logger.info("End: fetch gl transaction records from the current transaction/transaction history tables in fetchGLTransactionRecords() method.");
	}

	private void getBorrowingAccount(List<TransactionRecordByProductAndCrdr> tranRecordList) {

		logger.info("Start: get borrowings account in getBorrowingAccount() method.");
		IAccountPropertyDAO acctPropertyDao = KLSDataAccessFactory.getAccountPropertyDAO();
		IAccountDAO acctDao = KLSDataAccessFactory.getAccountDAO();
		IBorrowingsAccountDAO borrowingsAccDao = KLSDataAccessFactory.getBorrowingsAccountDAO();
		List<CurrentTransaction> currentTransactionList = new ArrayList<CurrentTransaction>();
		for (TransactionRecordByProductAndCrdr trRecord : tranRecordList) {
			Long accountId = trRecord.getAccountId();
			logger.info(" accountId : " + accountId);
			Account account = acctDao.getAccountById(accountId, false);
			if (account != null) {
				AccountProperty accountProperty = acctPropertyDao.getAccountPropertyById(account.getAccountProperty()
						.getId(), false);
				if (accountProperty != null) {
					int branchId = accountProperty.getBranch().getId();
					logger.info(" branchId : " + branchId);
					int pacsId = accountProperty.getPacs().getId();
					logger.info(" pacsId : " + pacsId);
					String bankCode = accountProperty.getBranch().getCode();
					logger.info(" bankCode : " + bankCode);
					int productId = trRecord.getProductId();
					logger.info(" productId : " + productId);
					BorrowingsAccount borrowingAcct = borrowingsAccDao.getPacsBorrowingsAccNo(bankCode, branchId,
							pacsId, productId);
					if (borrowingAcct != null) {
						populateCurrentTransaction(borrowingAcct, trRecord.getCrDr(), currentTransactionList,
								trRecord.getTransactionAmtSum());
					} else {
						logger.error("Cannot generate gl system excel file as pacs borrowing account number does not exist.");
						throw new KlsRuntimeException(
								"Cannot generate gl system excel file as pacs borrowing account number does not exist.");
					}
				} else {
					logger.error("Cannot generate gl system excel file as pacs borrowing line of credit account does not exist.");
					throw new KlsRuntimeException(
							"Cannot generate gl system excel file as pacs borrowing line of credit account does not exist.");
				}
			} else {
				logger.error("Cannot generate gl system excel file as kls account number does not exist.");
				throw new KlsRuntimeException(
						"Cannot generate gl system excel file as kls account number does not exist.");
			}
		}
		if (!currentTransactionList.isEmpty()) {
			ICurrentTransactionDAO currDao = KLSDataAccessFactory.getCurrentTransactionDAO();
			currDao.saveCurrentTransaction(currentTransactionList, null);
		}
		logger.info("End: get borrowings account in getBorrowingAccount() method.");
	}

	/**
	 * 
	 * @param borrowingAcct
	 * @param crdr
	 * @param currentTransactionList
	 * @param transactionAmount
	 */
	private void populateCurrentTransaction(BorrowingsAccount borrowingAcct, int crdr,
			List<CurrentTransaction> currentTransactionList, AccountingMoney transactionAmount) {

		logger.info("Start: populate current transaction in populateCurrentTransaction() method.");
		CurrentTransaction currTransaction = new CurrentTransaction();
		try {
			IAccountDAO acctDao = KLSDataAccessFactory.getAccountDAO();
			logger.info(" pacs borrowing account number " + borrowingAcct.getPacsAccountNo());
			Account account = acctDao.getAccount(borrowingAcct.getPacsAccountNo());
			currTransaction.setAccount(account);
			currTransaction.setAccountNumber(borrowingAcct.getPacsAccountNo());
			currTransaction.setBusinessDate(Date.now());
			currTransaction.setChannelType(ChannelType.SYSTEM);
			if (crdr == 1) {
				currTransaction.setCrDr(-1 * crdr);
			} else {
				currTransaction.setCrDr(-1 * crdr);
			}
			ILineOfCreditDAO dao = KLSDataAccessFactory.getLineOfCreditDAO();
			LineOfCredit lineOfCredit = dao.getLineOfCreditById(borrowingAcct.getLineOfCredit().getId(), false);
			currTransaction.setLineOfCredit(lineOfCredit);
			currTransaction.setOpeningBalance(AccountingMoney.ZERO);
			IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
			Pacs pacs = pacsDao.getPacs(borrowingAcct.getPacs().getId());
			currTransaction.setPacs(pacs);
			currTransaction.setPostedStatus(1);
			currTransaction.setRemarks("Trf borrowing");
			currTransaction.setTerminalId(null);
			currTransaction.setTransactionAmount(transactionAmount);
			currTransaction.setTransactionCode(TransactionCode.PRINCIPAL_BAL);
			currTransaction.setTransactionType(TransactionType.Transfer);
			//currTransaction.setVoucherNumber(1);
			currentTransactionList.add(currTransaction);
		} catch (Exception excp) {
			logger.error("Cannot find pacs borrowing account number in account table.");
			throw new KlsRuntimeException("Cannot find pacs borrowing account number in account table.");
		}
		logger.info("End: populate current transaction in populateCurrentTransaction() method.");
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	private String getFileNameWithTimestamp(String fileName) {

		logger.info("Start: appending timestamp to the current date in  getFileNameWithTimestamp() method.");
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat(LoansGLEntriesSerivce.GL_DATE_FORMAT);
		String dateTimestamp = sdf.format(date);
		logger.info(" dateTimestamp : " + dateTimestamp);
		String fileNameWithTimestamp = fileName.replaceFirst("_", ("_" + dateTimestamp));
		logger.info(" fileNameWithTimestamp : " + fileNameWithTimestamp);
		logger.info("End: appending timestamp to the current date in  getFileNameWithTimestamp() method.");
		return fileNameWithTimestamp;
	}

	/**
	 * 
	 * @param currTranList
	 * @param effectiveDate
	 * @param bankCode
	 * @param postingDate
	 */
	private List<GLEntrySummary> generateGLEntrySummaryList(List<CurrentTransaction> currTranList, Date effectiveDate,
			String bankCode, Date postingDate) {

		logger.info("Start: generating gl entry summary list from current transaction in generateGLEntrySummaryList() method.");
		List<GLEntrySummary> glEntrySummaryList = new ArrayList<GLEntrySummary>();
		for (CurrentTransaction currentTransaction : currTranList) {
			GLEntrySummary glEntrySummary = createGLEntrySummaryRecord(currentTransaction, effectiveDate, bankCode,
					postingDate);
			glEntrySummaryList.add(glEntrySummary);
		}
		logger.info("End: generating gl entry summary list from current transaction in generateGLEntrySummaryList() method.");
		return glEntrySummaryList;
	}

	/**
	 * 
	 * @param object
	 * @param effectiveDate
	 * @param bankCode
	 * @param postingDate
	 * @return
	 */
	/*
	 * private GLEntrySummary createGLEntrySummaryRecord(Object object, Date
	 * effectiveDate, String bankCode, Date postingDate) {
	 * 
	 * logger.info(
	 * "Start: create gl entry summary object in createGLEntrySummaryRecord() method."
	 * ); CurrentTransaction currentTransaction = null; TransactionHistory
	 * transactionHistory = null; GLEntrySummary glEntrySummary = new
	 * GLEntrySummary(); if (object instanceof CurrentTransaction) {
	 * currentTransaction = (CurrentTransaction) object;
	 * glEntrySummary.setBranchId
	 * (currentTransaction.getPacs().getBranch().getId().toString());
	 * glEntrySummary.setDescription(currentTransaction.getRemarks());
	 * glEntrySummary.setAccountNumber(currentTransaction.getAccountNumber());
	 * glEntrySummary
	 * .setProductId(currentTransaction.getLineOfCredit().getScheme
	 * ().getProduct().getId() .toString());
	 * glEntrySummary.setProductTypeId(currentTransaction
	 * .getLineOfCredit().getScheme().getProduct()
	 * .getProductType().getId().toString());
	 * glEntrySummary.setTrancode(currentTransaction
	 * .getTransactionCode().getValue().toString());
	 * glEntrySummary.setTransactionAmount
	 * (currentTransaction.getTransactionAmount());
	 * glEntrySummary.setTransactionId(currentTransaction.getId()); } else {
	 * transactionHistory = (TransactionHistory) object;
	 * glEntrySummary.setBranchId
	 * (transactionHistory.getPacs().getBranch().getId().toString());
	 * glEntrySummary.setDescription(transactionHistory.getRemarks());
	 * glEntrySummary.setAccountNumber(transactionHistory.getAccountNumber());
	 * glEntrySummary
	 * .setProductId(transactionHistory.getLineOfCredit().getScheme
	 * ().getProduct().getId() .toString());
	 * glEntrySummary.setProductTypeId(transactionHistory
	 * .getLineOfCredit().getScheme().getProduct()
	 * .getProductType().getId().toString());
	 * glEntrySummary.setTrancode(transactionHistory
	 * .getTransactionCode().getValue().toString());
	 * glEntrySummary.setTransactionAmount
	 * (transactionHistory.getTransactionAmount());
	 * glEntrySummary.setTransactionId(transactionHistory.getId()); }
	 * glEntrySummary.setAccountType("0");
	 * glEntrySummary.setEffectiveDate(effectiveDate); //
	 * glEntrySummary.setFileName(null);
	 * glEntrySummary.setInstitutionId(bankCode);
	 * glEntrySummary.setLoanStatus(null);
	 * glEntrySummary.setMonetaryComponentId("0");
	 * glEntrySummary.setPostingDate(postingDate);
	 * glEntrySummary.setPostStatus("1"); // glEntrySummary.setSourceType(null);
	 * GLExtractRecordBuilder glExtractRecordBuilder = new
	 * GLExtractRecordBuilder(glEntrySummary); String s =
	 * glExtractRecordBuilder.constructGLRecord();
	 * System.out.println(" GL text file string is : " + s);
	 * System.out.println(); logger.info(
	 * "End: create gl entry summary object in createGLEntrySummaryRecord() method."
	 * ); return glEntrySummary; }
	 */

	private GLEntrySummary createGLEntrySummaryRecord(Object object, Date effectiveDate, String bankCode,
			Date postingDate) {

		logger.info("Start: create gl entry summary object in createGLEntrySummaryRecord() method.");
		CurrentTransaction currentTransaction = null;
		TransactionHistory transactionHistory = null;
		String crDr = "";
		GLEntrySummary glEntrySummary = new GLEntrySummary();
		if (object instanceof CurrentTransaction) {
			currentTransaction = (CurrentTransaction) object;
			glEntrySummary.setAccountNumber(currentTransaction.getAccountNumber());
			
			Integer cd = currentTransaction.getCrDr();
			if(cd == 1)
				crDr = "C";
			else
				crDr = "D";
			
			glEntrySummary.setCrdr(crDr);
			glEntrySummary.setRemarks(currentTransaction.getRemarks());
			glEntrySummary.setTransactionAmount(currentTransaction.getTransactionAmount().getMoney().getAmount()
					.toString());

		} else {
			transactionHistory = (TransactionHistory) object;
			glEntrySummary.setAccountNumber(transactionHistory.getAccountNumber());
			Integer cd = transactionHistory.getCrDr();
			if(cd == 1)
				crDr = "C";
			else
				crDr = "D";
			glEntrySummary.setCrdr(crDr);
			glEntrySummary.setRemarks(transactionHistory.getRemarks());
			glEntrySummary.setTransactionAmount(transactionHistory.getTransactionAmount().getMoney().getAmount()
					.toString());
		}
		logger.info("End: create gl entry summary object in createGLEntrySummaryRecord() method.");
		return glEntrySummary;
	}

	/**
	 * 
	 * @param tranHistoryList
	 * @param effectiveDate
	 * @param bankCode
	 * @param postingDate
	 */
	private List<GLEntrySummary> generateTranHistoryGLEntrySummaryList(List<TransactionHistory> tranHistoryList,
			Date effectiveDate, String bankCode, Date postingDate) {

		logger.info("Start: generating gl entry summary list from transaction history in generateTranHistoryGLEntrySummaryList() method.");
		List<GLEntrySummary> glEntrySummaryList = new ArrayList<GLEntrySummary>();
		for (TransactionHistory transactionHistory : tranHistoryList) {
			GLEntrySummary glEntrySummary = createGLEntrySummaryRecord(transactionHistory, effectiveDate, bankCode,
					postingDate);
			glEntrySummaryList.add(glEntrySummary);
		}
		logger.info("End: generating gl entry summary list from transaction history in generateTranHistoryGLEntrySummaryList() method.");
		return glEntrySummaryList;
	}

}
