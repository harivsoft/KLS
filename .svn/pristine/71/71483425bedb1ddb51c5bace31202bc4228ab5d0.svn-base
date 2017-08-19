package com.vsoftcorp.kls.loans.gl.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.core.banking.service.IAccountDetailsService;
import com.vsoftcorp.kls.business.entities.BorrowingProductGlMapping;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsGLExtract;
import com.vsoftcorp.kls.business.entities.PacsGlMapping;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.SeasonParameter;
import com.vsoftcorp.kls.business.entity.account.BorrowingsAccountProperty;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.ChargesMaster;
import com.vsoftcorp.kls.business.entity.loan.ChargesRecovery;
import com.vsoftcorp.kls.business.entity.loan.LoanDisbursementEntry;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.AccountData;
import com.vsoftcorp.kls.data.GlData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareAccountData;
import com.vsoftcorp.kls.data.gateway.datahelpers.ShareProductGlMappingData;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.gl.GLEntrySummary;
import com.vsoftcorp.kls.gl.GLExtractFile;
import com.vsoftcorp.kls.gl.PacsGLEntry;
import com.vsoftcorp.kls.loans.gl.service.IPacsGLEntryService;
import com.vsoftcorp.kls.service.ICustomerService;
import com.vsoftcorp.kls.service.account.IAccountPropertyService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.PacsGLEntryHelper;
import com.vsoftcorp.kls.service.helper.PacsGLExtractHelper;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.service.util.StringConstant;
import com.vsoftcorp.kls.valuetypes.CBSMethodEnum;
import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;
import com.vsoftcorp.kls.valuetypes.transaction.PacsGLExtractUpload;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionCode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionMode;
import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;
import com.vsoftcorp.time.Date;

/**
 * To generate PACS GL entries text file
 * 
 * @author a1605
 * 
 */
public class PacsGLEntryService implements IPacsGLEntryService {
	
	private static Logger logger = Logger.getLogger(PacsGLEntryService.class);
	
	List<CurrentTransaction> currenttranslist = null;
	AccountingMoney borrowingsDebitedAmountForPacsSB; // added code for IR-29 ( for pacs SB acc debit transaction, Transaction Amount = borrowingsDebitedAmountForPacsSB( sum of borrowings debit amount).
	//IBankParameterDAO bankParameterDao = KLSDataAccessFactory.getBankParameterDAO();
	//BankParameter bankParameter = KLSDataAccessFactory.getBankParameter().get(0);

	@Override
	public String extractPacsGLEntries(List<CurrentTransaction> curentTransactionList, List<GlData> glDataList) {
		logger.info("Start: genarating pacs gl entries");
		logger.info("Bank Cash GL::::" + KLSDataAccessFactory.getBankParameter().getCashGl());
		String status = "";
		Date currentDate =null;
		currenttranslist =  curentTransactionList;
		//AccountingMoney subsidyAmt=AccountingMoney.ZERO;
		//boolean subsidyFlag=false;
		try {
			//extractBankGLTransactions();
			Set<Integer> pacsIdSet = new HashSet<Integer>();
			for (CurrentTransaction currentTransaction : curentTransactionList) {
				pacsIdSet.add(currentTransaction.getPacs().getId());
			}
			PacsGlMapping glMapping = null;
			List<PacsGLEntry> pacsGlEntries = null;
			PacsGLEntry pacsGLEntry = null;
			Integer credit = Integer.parseInt("1");
			Integer debit = Integer.parseInt("-1");
			boolean borrowingtran=false;
			for (Integer pacsId : pacsIdSet) {

				Pacs pacs = KLSDataAccessFactory.getPacsDAO().getPacs(pacsId);
				currentDate = DateUtil.getVSoftDateByString(RestClientUtil.getPacsBusinessDate(pacs.getId(),
						pacs.getBranch().getId()));
				pacsGlEntries = new ArrayList<PacsGLEntry>();
				
				// Code added as per IR-29 ( calculating sum of borrowing debit transaction amounts, this amount is used in doing debit transaction for Bank Gl.  
				AccountingMoney borrowingsDebitedAmount = AccountingMoney.ZERO;
				
				for (CurrentTransaction currentTransactions : curentTransactionList) {
					
					TransactionCode transCode = currentTransactions.getTransactionCode();
					if (pacs.getIsBorrwingTransRequiredInGLExtract().equals("Y")) {
						if ( transCode == TransactionCode.PRINCIPAL_BAL && currentTransactions.getCrDr() == -1 
								&& currentTransactions.getTransactionType() == TransactionType.Borrowings ){
							borrowingsDebitedAmount = borrowingsDebitedAmount.add(currentTransactions.getTransactionAmount()); 
						}else if ( transCode == TransactionCode.INTEREST && currentTransactions.getCrDr() == -1
								&& currentTransactions.getTransactionType() == TransactionType.Borrowings ){
							borrowingsDebitedAmount = borrowingsDebitedAmount.add(currentTransactions.getTransactionAmount()); 
						}
						else if ( transCode == TransactionCode.PENAL_INTEREST && currentTransactions.getCrDr() == -1 
								&& currentTransactions.getTransactionType() == TransactionType.Borrowings ){
							borrowingsDebitedAmount = borrowingsDebitedAmount.add(currentTransactions.getTransactionAmount());
						}
					}
					logger.info(" Total Borrowing Debited Amount := "+borrowingsDebitedAmount);
				}
				
				
				for (CurrentTransaction currentTransaction : curentTransactionList) {
					if (pacsId == currentTransaction.getPacs().getId()) {
						Integer crDr = currentTransaction.getCrDr();
						LineOfCredit lineOfCredit = currentTransaction.getLineOfCredit();
						TransactionCode transactionCode = currentTransaction.getTransactionCode();
						/*if(transactionCode==TransactionCode.INTEREST_SUBSIDY){
							subsidyAmt=currentTransaction.getTransactionAmount();
							subsidyFlag=true;
						}*/
						pacsGLEntry = new PacsGLEntry();
						pacsGLEntry.setTransactionAmount(currentTransaction.getTransactionAmount());
						pacsGLEntry.setCrDr(currentTransaction.getCrDr());
						pacsGLEntry.setTransType(currentTransaction.getTransactionType().getValue());
						if (!currentTransaction.getTransactionType().equals(TransactionType.Borrowings)) {
							logger.info("currentTransaction id::------" + currentTransaction.getId());
							Boolean flag = transactionCode == TransactionCode.PRINCIPAL_BAL || transactionCode == TransactionCode.SHARE_TRANSFER
									|| transactionCode == TransactionCode.INSURANCE_DEDUCTION || transactionCode == TransactionCode.PROCESSING_FEE
									|| transactionCode == TransactionCode.INTEREST || transactionCode == TransactionCode.PENAL_INTEREST
									|| transactionCode == TransactionCode.INTEREST_RECEIVABLE || transactionCode == TransactionCode.INTEREST_RECEIVED
									|| transactionCode == TransactionCode.INTEREST_SUBSIDY_ON_LOAN || transactionCode == TransactionCode.PENAL_INTEREST_RECEIVABLE 								
									|| transactionCode == TransactionCode.PENAL_INTEREST_RECEIVED || transactionCode == TransactionCode.MARGINAL || transactionCode == TransactionCode.PACS_BANK_ACC_BORROWING;

							if (flag) {
								if(lineOfCredit.getProduct().getBorrowingRequired() != null && "Y".equalsIgnoreCase(lineOfCredit.getProduct().getBorrowingRequired())){
										
									glMapping = KLSDataAccessFactory.getPacsGlMappingDAO().getPacsGlMapping(lineOfCredit.getProduct().getId(),
											currentTransaction.getPacs().getId());
	
									if (glMapping != null) {
										status = checkValidGl(glMapping,glDataList,status);
																				
										if ((borrowingtran==false && transactionCode == TransactionCode.PRINCIPAL_BAL) || transactionCode == TransactionCode.SHARE_TRANSFER
												|| transactionCode == TransactionCode.INSURANCE_DEDUCTION
												|| transactionCode == TransactionCode.PROCESSING_FEE || transactionCode == TransactionCode.MARGINAL)
											pacsGLEntry.setAccountNo(glMapping.getGlCode());
										else if (borrowingtran==false && transactionCode == TransactionCode.INTEREST) {
											pacsGLEntry.setAccountNo(glMapping.getIntReceivableGL());
											pacsGLEntry.setCrDr(credit);
										} else if (borrowingtran==false && transactionCode == TransactionCode.PENAL_INTEREST) {
											pacsGLEntry.setAccountNo(glMapping.getPenalintReceivableGL());
											pacsGLEntry.setCrDr(credit);
										} else if (transactionCode == TransactionCode.INTEREST_RECEIVABLE || transactionCode == TransactionCode.INTEREST_SUBSIDY_ON_LOAN)   // subsidy also
											pacsGLEntry.setAccountNo(glMapping.getIntReceivableGL());
										else if (transactionCode == TransactionCode.INTEREST_RECEIVED)
											pacsGLEntry.setAccountNo(glMapping.getIntReceivedGL());
										else if (transactionCode == TransactionCode.PENAL_INTEREST_RECEIVABLE)
											pacsGLEntry.setAccountNo(glMapping.getPenalintReceivableGL());
										else if (transactionCode == TransactionCode.PENAL_INTEREST_RECEIVED)
											pacsGLEntry.setAccountNo(glMapping.getPenalIntReceivedGL());
										
										if (borrowingtran==false && transactionCode == TransactionCode.PRINCIPAL_BAL && crDr == -1 && ChannelType.SYSTEM.equals(currentTransaction.getChannelType()))  { //Only for Stand alone
											PacsGLEntry pacsBankCrTrans = new PacsGLEntry();
											pacsBankCrTrans.setAccountNo(pacs.getPacsBankAccNumber());
											pacsBankCrTrans.setCrDr(1);
											pacsBankCrTrans.setTransactionAmount(currentTransaction.getTransactionAmount());
											pacsBankCrTrans.setTransType(currentTransaction.getTransactionType().getValue());
											pacsGlEntries.add(pacsBankCrTrans);
										}
										
										if (transactionCode == TransactionCode.PACS_BANK_ACC_BORROWING && crDr == -1 && ChannelType.SYSTEM.equals(currentTransaction.getChannelType()))  { //Only for Stand alone
											logger.info("testing borrowing");
											PacsGLEntry pacsBankCrTrans = new PacsGLEntry();
											pacsBankCrTrans.setAccountNo(pacs.getPacsBankAccNumber());
											pacsBankCrTrans.setCrDr(1);
											pacsBankCrTrans.setTransactionAmount(currentTransaction.getTransactionAmount());
											pacsBankCrTrans.setTransType(currentTransaction.getTransactionType().getValue());
											pacsGlEntries.add(pacsBankCrTrans);
											borrowingtran=true;
										}
										if (transactionCode == TransactionCode.INSURANCE_DEDUCTION) {
											if (lineOfCredit.getLoanType().equals(StringConstant.CROP_LOAN)) {
												PacsGLEntry insuranceGlEntry = new PacsGLEntry();
												insuranceGlEntry.setCrDr((crDr * (-1)));
												insuranceGlEntry.setTransType("T");
												insuranceGlEntry.setTransactionAmount(currentTransaction.getTransactionAmount());
												SeasonParameter seasonParameter = KLSDataAccessFactory.getSeasonParameterDAO().getSeasonParameter(
														lineOfCredit.getSeason().getId(), lineOfCredit.getCrop().getId(), pacsId);
												if (seasonParameter != null)
													insuranceGlEntry.setAccountNo(seasonParameter.getInsuranceGL());
												else {
													logger.info("Insurance GLs are not mapped in crop insurance parameter:" + pacsId);
													throw new KlsRuntimeException("Insurance GLs are not mapped ");
												}
												pacsGlEntries.add(insuranceGlEntry);
											}
	
										}
									} else {
										logger.info("Gl's are not mapped for pacsid:" + pacsId + " product id :" + lineOfCredit.getProduct().getId());
										throw new KlsRuntimeException("GL's are not available for pacs");
									}
								} else {
									logger.info("PacsGLExtract : extractPacsGLEntries: Borrowing Product is not Defined for this loan product id := "+lineOfCredit.getProduct().getId());
								}
							}
								
							if (transactionCode == TransactionCode.CHARGES_RECEIVED
									|| (transactionCode == TransactionCode.CHARGES_RECEIVABLE && crDr == -1)) {
								ChargesMaster charges = KLSDataAccessFactory.getChargesDebitDAO()
										.getChargesDebitByVoucher(currentTransaction.getVoucherNumber(),
												currentTransaction.getBusinessDate());
								if (charges != null) {
									if (transactionCode == TransactionCode.CHARGES_RECEIVED)
										pacsGLEntry.setAccountNo(charges.getChargesReceivedGL());
									if (transactionCode == TransactionCode.CHARGES_RECEIVABLE)
										pacsGLEntry.setAccountNo(charges.getChargesReceivableGL());
								}
							}
							
							if (transactionCode == TransactionCode.CHARGES_RECEIVABLE && crDr == 1) {

								PacsGLEntry pacsBankCrTrans;
								List<ChargesRecovery> chargesRecoveryList = KLSDataAccessFactory.getChargesDebitDAO()
										.getChargesMasterListByVoucher(currentTransaction.getVoucherNumber(),
												currentTransaction.getBusinessDate());

								for (ChargesRecovery chargesRecovery : chargesRecoveryList) {

									if (!chargesRecovery.getAmount().isZero()) {
										pacsBankCrTrans = new PacsGLEntry();
										pacsBankCrTrans.setAccountNo(chargesRecovery.getChargeDebitId()
												.getChargesMaster().getChargesReceivableGL());
										pacsBankCrTrans.setCrDr(1);
										pacsBankCrTrans.setTransactionAmount(AccountingMoney.valueOf(
												chargesRecovery.getAmount(), DebitOrCredit.CREDIT));
										pacsBankCrTrans
												.setTransType(currentTransaction.getTransactionType().getValue());
										pacsGlEntries.add(pacsBankCrTrans);
									}
									if (!chargesRecovery.getBankAmount().isZero()) {
										pacsBankCrTrans = new PacsGLEntry();
										pacsBankCrTrans.setAccountNo(currentTransaction.getPacs().getCashInTransitGL());
										pacsBankCrTrans.setCrDr(1);
										pacsBankCrTrans.setTransactionAmount(AccountingMoney.valueOf(
												chargesRecovery.getBankAmount(), DebitOrCredit.CREDIT));
										pacsBankCrTrans
												.setTransType(currentTransaction.getTransactionType().getValue());
										pacsGlEntries.add(pacsBankCrTrans);
									}
								}
							}
							
							
							if (transactionCode == TransactionCode.SHARE_TRANSFER_ON_SHARE_ACC) {
								ShareAccountData shareAccountData = RestClientUtil.getShareAccountByAccountNo(currentTransaction.getAccountNumber());
								ShareProductGlMappingData shareProductGlMappingData = RestClientUtil.getShareProductGlMapping(shareAccountData
										.getShareProductId().longValue(), pacsId);
								if (shareProductGlMappingData.getShareSuspense())
									pacsGLEntry.setAccountNo(shareProductGlMappingData.getShareSuspenseGlCode());
								else
									pacsGLEntry.setAccountNo(shareProductGlMappingData.getProductGlCode());
							}

							if (transactionCode == TransactionCode.MARGINAL_GL || transactionCode == TransactionCode.CASH_TRANSFER
									|| transactionCode == TransactionCode.SHARE_CHARGES || transactionCode == TransactionCode.CLOSING_CHARGES
									|| transactionCode == TransactionCode.PROCESSING_FEE_GL
									|| transactionCode == TransactionCode.INSURANCE_DEDUCTION_GL
									|| transactionCode == TransactionCode.CASH_IN_TRANSIT
									|| transactionCode == TransactionCode.INTEREST_SUBSIDY)
								pacsGLEntry.setAccountNo(currentTransaction.getAccountNumber());
							if(transactionCode == TransactionCode.PACS_BANK_ACC){
								pacsGLEntry.setAccountNo(currentTransaction.getAccountNumber());
								pacsGLEntry.setTransactionAmount(borrowingsDebitedAmount);  // Bank GL debit transaction amount = sum of borrowings debit amount. IR-29
								PacsGLEntry pacsBankCrTrans = new PacsGLEntry();
								pacsBankCrTrans.setAccountNo(pacs.getPacsBankAccNumber());
								pacsBankCrTrans.setCrDr(1);
								pacsBankCrTrans.setTransactionAmount(currentTransaction.getTransactionAmount());
								pacsBankCrTrans.setTransType(currentTransaction.getTransactionType().getValue());
								pacsGlEntries.add(pacsBankCrTrans);
							}
								

						} else {
							if(lineOfCredit.getProduct().getBorrowingRequired() != null && "Y".equalsIgnoreCase(lineOfCredit.getProduct().getBorrowingRequired())){
								
								BorrowingsAccountProperty bAccountProperty = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO()
										.getBorroingAccountPropertyByAccountId(lineOfCredit.getAccount().getId());
								BorrowingProductGlMapping bglMapping = KLSDataAccessFactory.getBorrowingProductGlMappingDAO()
										.getBorrowingProductGlMapping(bAccountProperty.getBorrowingProduct().getId(), pacsId);
								if (bglMapping != null) {
									if (transactionCode == TransactionCode.PRINCIPAL_BAL && crDr == 1)
										pacsGLEntry.setAccountNo(bglMapping.getGlCode());
									if (transactionCode == TransactionCode.INTEREST_RECEIVABLE)
										pacsGLEntry.setAccountNo(bglMapping.getInterestpayableGl());
									if (transactionCode == TransactionCode.INTEREST_RECEIVED)
										pacsGLEntry.setAccountNo(bglMapping.getInterestpaidGl());
									else if (transactionCode == TransactionCode.PENAL_INTEREST_RECEIVABLE)
										pacsGLEntry.setAccountNo(bglMapping.getPenalInterestPayableGlCode());
									else if (transactionCode == TransactionCode.PENAL_INTEREST_RECEIVED)
										pacsGLEntry.setAccountNo(bglMapping.getPenalInterestPaidGlCode());
	
									if (pacs.getIsBorrwingTransRequiredInGLExtract().equals("Y")) {
										if (transactionCode == TransactionCode.PRINCIPAL_BAL && crDr == -1)
											pacsGLEntry.setAccountNo(bglMapping.getGlCode()); 
										else if (transactionCode == TransactionCode.INTEREST){
											pacsGLEntry.setAccountNo(bglMapping.getInterestpayableGl());
											/*if(subsidyFlag){
												pacsGLEntry.setTransactionAmount(currentTransaction.getTransactionAmount().subtract(subsidyAmt));
												subsidyAmt=AccountingMoney.ZERO;
												subsidyFlag=false;
											}*/
										}
										else if (transactionCode == TransactionCode.PENAL_INTEREST)
											pacsGLEntry.setAccountNo(bglMapping.getPenalInterestPayableGlCode());
									}
								} else {
									logger.info("Borroings GLs are not mapped for pacsid:" + pacsId + " Borrowing product id :"
											+ bAccountProperty.getBorrowingProduct().getId());
									throw new KlsRuntimeException("Borroings Gls are not available pacsid:" + pacsId + " Borrowing product id :"
											+ bAccountProperty.getBorrowingProduct().getId());
								}
							}else{
								logger.info("PacsGLExtract : extractPacsGLEntries1: Borrowing product is not defined for this product id := "+lineOfCredit.getProduct().getId());
							}
						}
						if (pacsGLEntry.getAccountNo() != null)
							pacsGlEntries.add(pacsGLEntry);
					}// end id
				} // end for

				AccountingMoney transAmt = KLSDataAccessFactory.getPacsGLEntryDAO().getAmountForPacsBankAccount(pacsId);
				if (transAmt != null) {
					String pacsBankAccNo = pacs.getPacsBankAccNumber();
					if (pacsBankAccNo != null) {
						populateBackGLEntry(pacsGlEntries, transAmt, pacsBankAccNo, debit, TransactionType.Transfer.getValue(),"");
						//papulatePacsGLEntry(pacsGlEntries, transAmt, pacsBankAccNo, credit, TransactionType.Transfer.getValue());
					} else {
						logger.info("Pacs Bank Account number not availblefor Pacs:" + pacsId);
						throw new KlsRuntimeException("Pacs Bank Account number not availble for Pacs:" + pacsId);
					}
				}
				pacsGlEntries = compressPacsGLEntries(pacsGlEntries);
				if (pacs.getPacsGlExtarctUpload().equals(PacsGLExtractUpload.MANUAL)) {
					String fileName = PropertiesUtil.getDocumentProperty(StringConstant.PACS_GL_FILE_NAME);
					logger.info(" pacs_gl_fileName : " + fileName);
					fileName = fileName.replaceFirst("_", ("_" + pacsId + "_" + currentDate.toString(StringConstant.DATE_FORMAT)));
					logger.info("pacsgl fileName with date : " + fileName);
					GLExtractFile glExtractFile = new GLExtractFile(fileName);
					writeIntoTheExcelFile(PacsGLEntryHelper.getGlEntrySummarys(pacsGlEntries,"pac"), glExtractFile);
				} else {
					savePacsGlExtracts(pacsGlEntries, pacs);
				}
			}// end pacsId main for
				//Extract bank gl file
			extractBankGLTransactions();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error:while genarating pacs gl entries excel file");
			throw new KlsRuntimeException("Could not able to genarate pacs gl excel file.");
		}
		return status;

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

	/**
	 * 
	 * @param pacsGLEntries
	 * @return
	 */
	private List<PacsGLEntry> compressPacsGLEntries(List<PacsGLEntry> pacsGLEntries) {

		List<PacsGLEntry> compressedPacsGlEntries = new ArrayList<PacsGLEntry>();
		PacsGLEntry glEntry = null;
		for (int i = 0; i < pacsGLEntries.size(); i++) {
			glEntry = pacsGLEntries.get(i);
			pacsGLEntries.remove(i);
			for (int j = 0; j < pacsGLEntries.size(); j++) {
				if (glEntry.equals(pacsGLEntries.get(j)) && glEntry.getTransType().equals(pacsGLEntries.get(j).getTransType())) {
					glEntry.setTransactionAmount(glEntry.getTransactionAmount().add(pacsGLEntries.get(j).getTransactionAmount()));
					pacsGLEntries.remove(j);
					j--;
				}
			}
			compressedPacsGlEntries.add(glEntry);
			i--;
		}
		return compressedPacsGlEntries;
	}

	/**
	 * 
	 */
	private void extractBankGLTransactions() throws Exception{

		List<PacsGLEntry> bankGlEntries = new ArrayList<PacsGLEntry>();
		BorrowingsAccountProperty borrowingsAccountProperty = null;
		PacsGLEntry bankGlEntry = null;

		// Extract from borrowing entries -- Recovery credits
		extractFromBorrowingTrans(bankGlEntries);
		
		// Extract from loan entries -- With drawl deductions and Recovery debit
		extractFromLoanTrans(currenttranslist, bankGlEntries);

		String CBSAppln = PropertiesUtil.getDocumentProperty(StringConstant.CBS_APPLICATION);
		
		String fileName = PropertiesUtil.getDocumentProperty(StringConstant.BANK_GL_FILE_NAME);
		logger.info(" bank_gl_fileName : " + fileName);
		fileName = fileName.replaceFirst("_", ("_" + LoanServiceUtil.getBusinessDate().toString(StringConstant.DATE_FORMAT)));
		logger.info("bankgl fileName with date : " + fileName);
		
		
		if ("FINACLE".equalsIgnoreCase(CBSAppln)) {
			File ttumFile = new File(getFileDirectory()+fileName);
			
			logger.info("PATH:" + ttumFile.getAbsolutePath());

			FileWriter fw = null;
			BufferedWriter bw = null;
			try {
				ttumFile.createNewFile();
				fw = new FileWriter(ttumFile.getAbsoluteFile());
				bw = new BufferedWriter(fw);
				
				for (PacsGLEntry glEntry : bankGlEntries) {
					TTUMEntry entry = new TTUMEntry();
					entry.populateDefaultValues();

					TTUMField accountNumber = new TTUMField("ACCOUNT_NUMBER", 16, 'L', " ");
					accountNumber.setValue(glEntry.getAccountNo());

					TTUMField serviceOutLet = new TTUMField("SERVICE_OUTLET", 8, 'L', " ");
					serviceOutLet.setValue("9999"); // Inserting Dummy

					TTUMField tranType = new TTUMField("PART_TRAN_TYPE", 1, 'N', " ");
					
					if (glEntry.getCrDr() == -1) {
						tranType.setValue("D");
					} else {
						tranType.setValue("C");
					}

					TTUMField transAmount = new TTUMField("TRANSACTION_AMOUNT", 17, 'R', " ");
					BigDecimal amt = glEntry.getTransactionAmount().getMoney().getAmount();
					amt = amt.setScale(2, BigDecimal.ROUND_HALF_UP);
					transAmount.setValue(amt.toString());
					
					TTUMField referenceAmount = new TTUMField("REFERENCE_AMOUNT", 17, 'R', " ");
					BigDecimal refAmt = glEntry.getTransactionAmount().getMoney().getAmount();
					refAmt = refAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
					referenceAmount.setValue(refAmt.toString());

					entry.getFields().put(TTUMConstants.ACCOUNT_NUMBER, accountNumber.getFormattedValue());
					entry.getFields().put(TTUMConstants.SERVICE_OUTLET, serviceOutLet.getFormattedValue());
					entry.getFields().put(TTUMConstants.PART_TRAN_TYPE, tranType.getFormattedValue());
					entry.getFields().put(TTUMConstants.TRANSACTION_AMOUNT, transAmount.getFormattedValue());
					entry.getFields().put(TTUMConstants.REFERENCE_AMOUNT, referenceAmount.getFormattedValue());

					bw.write(entry.toString() + '\n');
//					bw.close();
				}
			} catch (Exception e) {
				logger.error("Error occurred while writting TTUM GL extract. " + e.getMessage());
				e.printStackTrace();
				//throw e;
			} finally {
				try {
					bw.close();
				} catch (Exception e) {
					logger.error("Error occurred while closing bufferred wirter. " + e.getMessage());
				}
			}
		}else {
			GLExtractFile glExtractFile = new GLExtractFile(fileName);
			writeIntoTheExcelFile(PacsGLEntryHelper.getGlEntrySummarys(bankGlEntries,"bank"), glExtractFile);			
		}
	}
	
	/**
	 * 
	 * @param bankGlEntries
	 */
	private void extractFromBorrowingTrans (List<PacsGLEntry> bankGlEntries) {
		List<Map> bankGlTransactionsData;
		BorrowingsAccountProperty borrowingsAccountProperty = null;
		PacsGLEntry bankGlEntry = null;
		IProductDAO productDAO = KLSDataAccessFactory.getProductMasterDAO();
		Product product = null;
		Integer pacsId = null;
		Integer productId = null;
		TransactionCode transCode = null;
		
		bankGlTransactionsData = KLSDataAccessFactory.getPacsGLEntryDAO().getBankGLTransactions();
		
		borrowingsDebitedAmountForPacsSB = AccountingMoney.ZERO;
		
		for (Map map : bankGlTransactionsData) {
			
			bankGlEntry = new PacsGLEntry();
			pacsId = (Integer) map.get("pacsId");
			productId = (Integer) map.get("productId");
			transCode = (TransactionCode) map.get("transactionCode");
			/*if((Integer) map.get("crDr") == -1)
				bankGlEntry.setCrDrForBank("D");
			else
				bankGlEntry.setCrDrForBank("C");*/
			logger.info("checking tran code::"+transCode);
			bankGlEntry.setCrDr((Integer) map.get("crDr"));
			bankGlEntry.setTransactionAmount((AccountingMoney) map.get("transactionAmount"));
			bankGlEntry.setTransType(TransactionType.Transfer.getValue()); 
			//bankGlEntry.setRemarks("Remarks...");
			// Type is always transfer w.r.t bank as no direct cash involved from Bank with recovery/int calc.
			
			if(productId != null){
				product = productDAO.getProductById(productId);
			}
			if(product.getBorrowingRequired() != null && "Y".equalsIgnoreCase(product.getBorrowingRequired())){
				
				borrowingsAccountProperty = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO()
				.getAccountPropertyByProductIdPacsId(productId, pacsId);
				//String pacsSavingAcc = borrowingsAccountProperty.getAccount().getAccountProperty().getPacs().getBankPacsAccNumber();
				if (borrowingsAccountProperty != null) {
					logger.info("CcbAccountNumber:" + borrowingsAccountProperty.getCcbAccountNumber());
					if (transCode.equals(TransactionCode.PRINCIPAL_BAL)) {
						if (bankGlEntry.getCrDr() == 1) { // Loan recovery transaction - Credit
							//populateBackGLEntry(bankGlEntries,
							//		bankGlEntry.getTransactionAmount(), pacsSavingAcc, -1, 
								//	TransactionType.Transfer.getValue());
							borrowingsDebitedAmountForPacsSB = borrowingsDebitedAmountForPacsSB.add((AccountingMoney) map.get("transactionAmount"));
							bankGlEntry.setAccountNo(borrowingsAccountProperty.getCcbAccountNumber());
							bankGlEntry.setRemarks("Principal Credit");
						} 
					} else {
						Integer borrowingProductId = borrowingsAccountProperty.getBorrowingProduct().getId();
						BorrowingProductGlMapping glMapping = KLSDataAccessFactory.getBorrowingProductGlMappingDAO()
								.getBorrowingProductGlMapping(borrowingProductId, pacsId);
						if (glMapping != null) {
							if (transCode.equals(TransactionCode.INTEREST)) {
								//populateBackGLEntry(bankGlEntries,
									//	bankGlEntry.getTransactionAmount(), pacsSavingAcc, -1, 
										//TransactionType.Transfer.getValue());	
								borrowingsDebitedAmountForPacsSB = borrowingsDebitedAmountForPacsSB.add((AccountingMoney) map.get("transactionAmount"));
								bankGlEntry.setAccountNo(glMapping.getBankInterestReceivableGL());
								bankGlEntry.setRemarks("Interest Credit");
							} else if (transCode.equals(TransactionCode.INTEREST_RECEIVABLE)){
								bankGlEntry.setAccountNo(glMapping.getBankInterestReceivableGL());
							bankGlEntry.setRemarks("Interest Receivable");
							}
							else if (transCode.equals(TransactionCode.INTEREST_RECEIVED)){
								bankGlEntry.setAccountNo(glMapping.getBankInterestReceivedGL());
								bankGlEntry.setRemarks("Interest Received");
							}
							else if (transCode.equals(TransactionCode.PENAL_INTEREST)) {
								//populateBackGLEntry(bankGlEntries,
									//	bankGlEntry.getTransactionAmount(), pacsSavingAcc, -1, 
										//TransactionType.Transfer.getValue());	
								borrowingsDebitedAmountForPacsSB = borrowingsDebitedAmountForPacsSB.add((AccountingMoney) map.get("transactionAmount"));
								bankGlEntry.setAccountNo(glMapping.getBankPenalInterestReceivableGL());
								bankGlEntry.setRemarks("Penal Interest ");
							} else if (transCode.equals(TransactionCode.PENAL_INTEREST_RECEIVABLE)){
								bankGlEntry.setAccountNo(glMapping.getBankPenalInterestReceivableGL());
							bankGlEntry.setRemarks("Penal Interest Receivable");
							}
							else if (transCode.equals(TransactionCode.PENAL_INTEREST_RECEIVED)){
								bankGlEntry.setAccountNo(glMapping.getBankPenalInterestReceivedGL());
								bankGlEntry.setRemarks("Penal Interest Received");
							}
							
						} else {
							logger.error("Bank GL's not found for pacs: " + pacsId + " borrowing productId: "
									+ borrowingProductId);
							throw new KlsRuntimeException("Bank GL's not found for pacs: " + pacsId
									+ " borrowing productId: " + borrowingProductId);
						}
					}
					if (bankGlEntry.getAccountNo() != null)
						bankGlEntries.add(bankGlEntry);
				}
				else {
					logger.error("Borrowing not exist");
					throw new KlsRuntimeException("Borrowing not exist");
				}
			}else{ 
				logger.info("BankGlExtract: extractFromBorrowingTrans : Borrowing Product is not Defined for this loan product id := "+productId);
			}
		}		
		logger.info(" Total Borrowing Debited Amount := "+ borrowingsDebitedAmountForPacsSB); 
	}

    /**
	 * 
	 * @param currenttranslist
	 * @param bankGlEntries
	 */
	private void extractFromLoanTrans (List<CurrentTransaction> currenttranslist, List<PacsGLEntry> bankGlEntries) {
		AccountingMoney subsidyAmt=AccountingMoney.ZERO;
		ICurrentTransactionDAO currentTransactionDAO = KLSDataAccessFactory.getCurrentTransactionDAO();
		boolean borrowingCollection=false;
		for (CurrentTransaction currentTran : currenttranslist) {

			if(TransactionType.Borrowings.equals(currentTran.getTransactionType())) {
				continue;
			}
			if(currentTran.getTransactionCode().equals(TransactionCode.PACS_BANK_ACC_BORROWING))	
				borrowingCollection=true;
			BorrowingsAccountProperty borrowingsAccountProperty = null;
			String cashInTransitAccNo = null;
			String mATMAccNo = null;

			// Find CCB
			if(currentTran.getPacs() != null && currentTran.getLineOfCredit() != null) {
				if(currentTran.getLineOfCredit().getProduct().getBorrowingRequired() != null 
						&& "Y".equalsIgnoreCase(currentTran.getLineOfCredit().getProduct().getBorrowingRequired())){
					borrowingsAccountProperty = KLSDataAccessFactory
						.getBorrowingsAccountPropertyDAO()
						.getAccountPropertyByLoanProductIdPacsId(
								currentTran.getPacs().getId(),
								currentTran.getLineOfCredit().getProduct()
										.getId());
				
					if (borrowingsAccountProperty == null) {
						// Error
						logger.error("No borrowing for loan product#pacs: " + currentTran.getPacs().getId() + "#" + 
								currentTran.getLineOfCredit().getProduct()
								.getId());
						//throw new Exception("No borrowing for loan product#pacs: ");
					}
				}else{ 
					logger.info("BankGlExtract : extractFromLoanTrans : Borrowing Product is not Defined " +
							"for this loan product id := "+currentTran.getLineOfCredit().getProduct().getId());
				}
			}
			System.out.println(KLSDataAccessFactory.getBankParameter().getCbsIntegrationMethod());
			// With drwal on-line transaction
			if ( CBSMethodEnum.OFFLINE.equals(KLSDataAccessFactory.getBankParameter().getCbsIntegrationMethod()) && currentTran.getCrDr() == -1 && 
					currentTran.getTransactionCode().equals(TransactionCode.PRINCIPAL_BAL) ) {
				// Debit Entry
			if(currentTran.getLineOfCredit().getProduct().getBorrowingRequired() != null 
					&& "Y".equalsIgnoreCase(currentTran.getLineOfCredit().getProduct().getBorrowingRequired())){	
				populateBackGLEntry(bankGlEntries,
						currentTran.getTransactionAmount(), borrowingsAccountProperty.getCcbAccountNumber(), -1, 
						currentTran.getTransactionType().getValue(), currentTran.getTransactionCode()+" Debit");
			}else{
				logger.error("extractFromLoanTrans: Borrowing Transaction has stopped due to borrowing account does'nt exist");
			}

				// Debit - Credit to Saving
				populateDebitCredit(currentTran, bankGlEntries);
				
				//Credit entry   ATM, MATM, Cash GL or Farmer Savings  (DD not considered)
				populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), getBankWithdrawlCreditAccount(currentTran), 
						1, currentTran.getTransactionType().getValue(), currentTran.getTransactionCode()+" Credit");
				
			}
			
			// Deductions
			if(currentTran.getLineOfCredit() != null && currentTran.getLineOfCredit().getLoanType() != null && "C".equals(currentTran.getLineOfCredit().getLoanType())){
				if (currentTran.getTransactionCode().equals(TransactionCode.PROCESSING_FEE)
						|| currentTran.getTransactionCode().equals(TransactionCode.SHARE_TRANSFER)
						|| currentTran.getTransactionCode().equals(TransactionCode.INSURANCE_DEDUCTION)) {
                if(currentTran.getChannelType().equals(ChannelType.SYSTEM)){
                	if(currentTran.getLineOfCredit().getProduct().getBorrowingRequired() != null 
        					&& "Y".equalsIgnoreCase(currentTran.getLineOfCredit().getProduct().getBorrowingRequired())){
                		populateBackGLEntry(bankGlEntries,currentTran.getTransactionAmount(),
						borrowingsAccountProperty.getCcbAccountNumber(), -1, currentTran.getTransactionType().getValue(), currentTran.getTransactionCode()+" Debit");
				}else{
					logger.error("extractFromLoanTrans: Borrowing Transaction has stopped due to borrowing account does'nt exist");
				}
                }

					// Credit PACS Saving A/C
					populateBackGLEntry(bankGlEntries,
							currentTran.getTransactionAmount(), currentTran
									.getPacs().getBankPacsAccNumber(), 1, currentTran.getTransactionType().getValue(), currentTran.getTransactionCode()+" Credit");
				if(!currentTran.getChannelType().equals(ChannelType.SYSTEM)){
					populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), getBankWithdrawlCreditAccount(currentTran), 
							1, currentTran.getTransactionType().getValue(), currentTran.getTransactionCode()+" Credit");
					
					populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), getBankWithdrawlCreditAccount(currentTran), 
							-1, currentTran.getTransactionType().getValue(), currentTran.getTransactionCode()+" Debit");
				}
				}
			}
			if(currentTran.getLineOfCredit() != null && currentTran.getLineOfCredit().getLoanType() != null && "L".equals(currentTran.getLineOfCredit().getLoanType())){
			if ( CBSMethodEnum.OFFLINE.equals(KLSDataAccessFactory.getBankParameter().getCbsIntegrationMethod())){
			if (currentTran.getTransactionCode().equals(TransactionCode.PROCESSING_FEE)
					|| currentTran.getTransactionCode().equals(TransactionCode.SHARE_TRANSFER)
					|| currentTran.getTransactionCode().equals(TransactionCode.INSURANCE_DEDUCTION)) {
				if(currentTran.getLineOfCredit().getProduct().getBorrowingRequired() != null 
    					&& "Y".equalsIgnoreCase(currentTran.getLineOfCredit().getProduct().getBorrowingRequired())){
					populateBackGLEntry(bankGlEntries,
						currentTran.getTransactionAmount(),
						borrowingsAccountProperty.getCcbAccountNumber(), -1, currentTran.getTransactionType().getValue(), currentTran.getTransactionCode()+" Debit");
				}else{
					logger.error("extractFromLoanTrans: Borrowing Transaction has stopped due to borrowing account does'nt exist");
				}
				// Credit PACS Saving A/C
				populateBackGLEntry(bankGlEntries,
						currentTran.getTransactionAmount(), currentTran
								.getPacs().getBankPacsAccNumber(), 1, currentTran.getTransactionType().getValue(), currentTran.getTransactionCode()+" Credit");
			}}
			}
			// Recovery Debit
			if ( currentTran.getCrDr() == 1  && 
					(currentTran.getTransactionCode().equals(TransactionCode.PRINCIPAL_BAL) ||
							currentTran.getTransactionCode().equals(TransactionCode.INTEREST) ||
							currentTran.getTransactionCode().equals(TransactionCode.PENAL_INTEREST) || currentTran.getTransactionCode().equals(TransactionCode.PACS_BANK_ACC_BORROWING))
							) {
				
				if(currentTran.getTerminalId() == null) { // Stand Alone mode
					
					
					if("BULK".equalsIgnoreCase(currentTran.getRemarks().trim())){
						populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), currentTran.getPacs().getBankPacsAccNumber(), -1, 
								TransactionType.Transfer.getValue(), currentTran.getTransactionCode()+" Debit");
					}else if(!ChannelType.BRANCH.getValue().equals(currentTran.getChannelType().getValue())){
						
						cashInTransitAccNo = currentTran.getPacs().getCashInTransitAccNo();
						
						if (cashInTransitAccNo == null) {
							logger.error("cashInTransitAccNo shouldn't be null for pacs:" + currentTran.getPacs().getId());
						}
						if(currentTran.getTransactionCode().equals(TransactionCode.INTEREST)){
							subsidyAmt=currentTransactionDAO.getSubsidyTransactionsByLocId(currentTran.getLineOfCredit().getId(), currentTran.getLineOfCredit().getProduct().getId(), currentTran.getBusinessDate());
							logger.info("subsidy amount in borrowing:::"+subsidyAmt);
							AccountingMoney transAmt=currentTran.getTransactionAmount();
							if(subsidyAmt!=null && !subsidyAmt.isZero())
							transAmt=transAmt.subtract(subsidyAmt);
							else
								transAmt=transAmt;
							if(borrowingCollection==false)
						 populateBackGLEntry(bankGlEntries, transAmt, cashInTransitAccNo, -1, 
								TransactionType.Transfer.getValue(), currentTran.getTransactionCode()+" Debit");
						}
						else{
							if(borrowingCollection==false)
							populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), cashInTransitAccNo, -1, 
									TransactionType.Transfer.getValue(), currentTran.getTransactionCode()+" Debit");
						}
						// Type is always transfer w.r.t bank as no direct cash involved from Bank with recovery/int calc.
						
					}
										
				} else  { // Normal mode

					if (CBSMethodEnum.OFFLINE.equals(KLSDataAccessFactory.getBankParameter().getCbsIntegrationMethod())){// offline 
				
						mATMAccNo = KLSDataAccessFactory.getBankPacsGlDAO().getBankPacsGlAccNo(currentTran.getTerminalId());
						
						if (mATMAccNo == null) {
							logger.error("Micro ATM Account shouldn't be null for terminal, pacs:" + currentTran.getTerminalId() + 
												", " + currentTran.getPacs().getId());
						}
						
						populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), mATMAccNo, -1, 
								currentTran.getTransactionType().getValue(), currentTran.getTransactionCode()+" Debit");
						
						// Debit - Credit to Farmer savings
						populateDebitCredit(currentTran, bankGlEntries);
					} else { // online
						populateBackGLEntry(bankGlEntries,
								currentTran.getTransactionAmount(), currentTran.getPacs().getBankPacsAccNumber(), -1, 
								TransactionType.Transfer.getValue(), currentTran.getTransactionCode()+" Debit");						
						
					}
					
				}
			}
			
			//Code added for Bulk recovery At Branch - IRKLS-404 (CBS Transactions when Bulk recovery At Branch in OFFLINE mode)
			// Debit transactions for Collection Amount
			if(CBSMethodEnum.OFFLINE.equals(KLSDataAccessFactory.getBankParameter().getCbsIntegrationMethod())){
				logger.info(" Start : CBS Transactions in Offline ");
				if(currentTran.getTerminalId() == null) { // Stand Alone mode
					logger.info(" CBS Transaction when Bulk recovery At Branch ");
					
					if ( currentTran.getCrDr() == -1  && 
							currentTran.getTransactionCode().equals(TransactionCode.PACS_BANK_ACC) &&
							ChannelType.BRANCH.getValue().equals(currentTran.getChannelType().getValue())){
						//Debit for Bank cash in Hand gl 	
						populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), KLSDataAccessFactory.getBankParameter().getCashGl(), -1, 
							TransactionType.Transfer.getValue(), "Bank Cash in Hand GL - Debit");
						
						populateBackGLEntry(bankGlEntries, borrowingsDebitedAmountForPacsSB, currentTran.getPacs().getBankPacsAccNumber(), -1, 
								TransactionType.Transfer.getValue(), "Pacs SB A/c - Debit");
						 
						populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), currentTran.getPacs().getBankPacsAccNumber(), 1, 
								TransactionType.Transfer.getValue(), "Pacs SB A/c - Credit");
					}
					if ( currentTran.getCrDr() == -1  && 
							currentTran.getTransactionCode().equals(TransactionCode.PACS_BANK_ACC) &&
							ChannelType.BRANCH_PACS_SB.getValue().equals(currentTran.getChannelType().getValue()) ){
						//Debit for pacs SB/CA account
						populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), currentTran.getPacs().getBankPacsAccNumber(), -1, 
								TransactionType.Transfer.getValue(), "Pacs SB A/c - Debit");
						
						populateBackGLEntry(bankGlEntries, borrowingsDebitedAmountForPacsSB, currentTran.getPacs().getBankPacsAccNumber(), -1, 
								TransactionType.Transfer.getValue(), "Pacs SB A/c - Debit");
						 
						populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), currentTran.getPacs().getBankPacsAccNumber(), 1, 
								TransactionType.Transfer.getValue(), "Pacs SB A/c - Credit");
					}
					if ( currentTran.getCrDr() == -1  && 
							currentTran.getTransactionCode().equals(TransactionCode.PACS_BANK_ACC) &&
							ChannelType.BRANCH_MEM_SB.getValue().equals(currentTran.getChannelType().getValue()) ){
						//Debit for Member SB account 
						Long custId = currentTran.getLineOfCredit().getCustomerId();
						logger.info("  customer Id in CBS Transactions : "+custId);
						AccountData	accData = new AccountData();
						IAccountPropertyService accPropService = KLSServiceFactory.getAccountPropertyService();
						accData = accPropService.getAccountInfoByCustId(custId);
						logger.info("  Transfer from Member SB Accountt : "+accData.getSavingBankAccNo());
						
						populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), accData.getSavingBankAccNo(), -1, 
							TransactionType.Transfer.getValue(), "Mem SB A/c - Debit");
						
						populateBackGLEntry(bankGlEntries, borrowingsDebitedAmountForPacsSB, currentTran.getPacs().getBankPacsAccNumber(), -1, 
								TransactionType.Transfer.getValue(), "Pacs SB A/c - Debit");
						 
						populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), currentTran.getPacs().getBankPacsAccNumber(), 1, 
								TransactionType.Transfer.getValue(), "Pacs SB A/c - Credit");
					}
					 
					//Borrowing direct Recovery
					if(currentTran.getCrDr()==-1 && currentTran.getTransactionCode().equals(TransactionCode.PACS_BANK_ACC_BORROWING)){
						logger.info("inside borrowing testing::"+currentTran.getPacs().getBankPacsAccNumber());
						populateBackGLEntry(bankGlEntries, currentTran.getTransactionAmount(), currentTran.getPacs().getBankPacsAccNumber(), -1, 
								TransactionType.Transfer.getValue(), "Pacs SB A/c - Debit");	
					}
				}
				logger.info(" END: CBS Transactions in Offline ");
			}
			// Bank Charges & related recovery
			extractBankCharges(currentTran, bankGlEntries);
						
		}
		
	}

	
	/**
	 * 
	 * @param curTrans
	 * @param bankGlEntries
	 */
	private void populateDebitCredit(CurrentTransaction curTrans, List<PacsGLEntry> bankGlEntries) {
		String strAcct = null;  // Farmer or PACs saving A/C
		
		// Normal - MATM
		if(ChannelType.MATM.equals(curTrans.getChannelType()) || 
				ChannelType.ATM.equals(curTrans.getChannelType())) {
			strAcct = KLSDataAccessFactory.getAccountPropertyDAO().getAccountByCustId(
					curTrans.getLineOfCredit().getCustomerId()).getSavingsAccountNumber();
		}
		else { // Stand Alone
			strAcct = curTrans.getPacs().getBankPacsAccNumber();			
		}

		populateBackGLEntry(bankGlEntries, curTrans.getTransactionAmount(), 
				strAcct, -1, curTrans.getTransactionType().getValue(),curTrans.getTransactionCode()+" Debit"); // Debit

		populateBackGLEntry(bankGlEntries, curTrans.getTransactionAmount(), 
				strAcct, 1, curTrans.getTransactionType().getValue(),curTrans.getTransactionCode()+" Credit"); // Credit
	}
	
	/**
	 * 
	 * @param currentTransaction
	 * @return
	 */
	private String getBankWithdrawlCreditAccount(CurrentTransaction currentTransaction) {
		
		String strAcct =null;
		
		TransactionType transType = currentTransaction.getTransactionType();
		
		LoanDisbursementEntry entry = KLSDataAccessFactory.getLoanDisbursementEntryDAO().getLoanDisbursementEntryByVoucherNumber(currentTransaction.getBusinessDate(), currentTransaction.getVoucherNumber(),currentTransaction.getPacs().getId());
		if(entry != null) {
			if(entry.getModeOfDisbursement() == TransactionMode.Cash) {
				transType = TransactionType.Withdrawal;
			}
			else {
				transType = TransactionType.Transfer;				
			}
		}
		
		
		//Credit entry   ATM, MATM, Cash GL or Farmer Savings  (DD not considered)
		if(ChannelType.ATM.equals(currentTransaction.getChannelType())) { // Cash GL.. For offline this is not applicable
			strAcct = KLSDataAccessFactory.getBankParameter().getCashGl();
			if (strAcct == null) {
				logger.error("Cash Gl shouldn't be null for ATM:" + currentTransaction.getPacs().getId());
			}
			
		} else if (ChannelType.MATM.equals(currentTransaction.getChannelType())) { // MATM Acc
			strAcct = KLSDataAccessFactory.getBankPacsGlDAO().getBankPacsGlAccNo(currentTransaction.getTerminalId());

			if (strAcct == null) {
				logger.error("Micro ATM Account shouldn't be null for terminal:" + currentTransaction.getTerminalId());
			}
			
		} else if (TransactionType.Withdrawal.equals(transType)) { // Cash GL
			strAcct = KLSDataAccessFactory.getBankParameter().getCashGl();
			if (strAcct == null) {
				logger.error("Cash Gl shouldn't be null for Withdrawal:" + currentTransaction.getPacs().getId());
			}
			
		} else if (TransactionType.Transfer.equals(transType)) { // Farmer Saving A/C
			if(entry!=null && entry.getModeOfDisbursement()==TransactionMode.Demand_Draft){
				logger.info("inside else if part");
				strAcct=entry.getToAccountNumber();
				logger.info("account number:::"+strAcct);
				if(entry.getToAccountNumber()==null)
					logger.error("No DD Payable GL");
			}
			else{
				strAcct = KLSDataAccessFactory.getAccountPropertyDAO().getAccountByCustId(currentTransaction.getLineOfCredit().getCustomerId()).getSavingsAccountNumber();
				if (strAcct == null) {
					logger.error("Farmer Saving A/C shouldn't be null for Transfer:" + currentTransaction.getLineOfCredit().getCustomerId());
				}
			}
		}
		logger.info("account number after:::"+strAcct);
		return strAcct;
	}
	
	/**
	 * 
	 * @param currentTran
	 * @param bankGlEntries
	 */
	private void extractBankCharges(CurrentTransaction currentTran, List<PacsGLEntry> bankGlEntries) {
		String crDr = null;
		if(currentTran.getCrDr() == -1)
			crDr = "Debit";
		else
			crDr = "Credit";
		
		if ((currentTran.getTransactionCode().equals(TransactionCode.BANK_CHARGES_RECEIVED))
				|| (currentTran.getTransactionCode().equals(TransactionCode.BANK_CHARGES_RECEIVABLE))) {
			
			ChargesMaster charges = KLSDataAccessFactory.getChargesDebitDAO().getChargesDebitByVoucher(
					currentTran.getVoucherNumber(), currentTran.getBusinessDate());
			if (charges != null) {
				PacsGLEntry bankGlEntry = new PacsGLEntry();
				bankGlEntry.setCrDr((Integer) currentTran.getCrDr());
				bankGlEntry.setTransactionAmount((AccountingMoney) currentTran.getTransactionAmount());
				if (currentTran.getTransactionCode().equals(TransactionCode.BANK_CHARGES_RECEIVED))
					bankGlEntry.setAccountNo(charges.getBankChargesReceivedGL());
				if (currentTran.getTransactionCode().equals(TransactionCode.BANK_CHARGES_RECEIVABLE))
					bankGlEntry.setAccountNo(charges.getBankChargesReceivableGL());
				bankGlEntry.setRemarks(currentTran.getTransactionCode()+" "+crDr);
				bankGlEntry.setTransType(TransactionType.Transfer.getValue());
				bankGlEntries.add(bankGlEntry);
			} else {
				logger.error("No Bank charge gls defined!!");
				//throw new Exception("No Bank charge gls defined!!");
			}
		}

		// bank charges recovery
		if (currentTran.getTransactionCode() == TransactionCode.CHARGES_RECEIVABLE && 
				currentTran.getCrDr() == 1) {

			PacsGLEntry pacsBankCrTrans;
			List<ChargesRecovery> chargesRecoveryList = KLSDataAccessFactory.getChargesDebitDAO()
					.getChargesMasterListByVoucher(currentTran.getVoucherNumber(),
							currentTran.getBusinessDate());

			for (ChargesRecovery chargesRecovery : chargesRecoveryList) {

				if (!chargesRecovery.getBankAmount().isZero()) {
					pacsBankCrTrans = new PacsGLEntry();
					pacsBankCrTrans.setAccountNo(chargesRecovery.getChargeDebitId()
							.getChargesMaster().getBankChargesReceivableGL());
					pacsBankCrTrans.setCrDr(1);
					pacsBankCrTrans.setTransactionAmount(AccountingMoney.valueOf(
							chargesRecovery.getBankAmount(), DebitOrCredit.CREDIT));
					pacsBankCrTrans
							.setTransType(currentTran.getTransactionType().getValue());
					pacsBankCrTrans.setRemarks(currentTran.getTransactionCode()+" "+crDr);
					bankGlEntries.add(pacsBankCrTrans);
				}
				if (!chargesRecovery.getBankAmount().isZero()) {
					pacsBankCrTrans = new PacsGLEntry();
					pacsBankCrTrans.setAccountNo(currentTran.getPacs().getCashInTransitAccNo());
					pacsBankCrTrans.setCrDr(-1);
					pacsBankCrTrans.setTransactionAmount(AccountingMoney.valueOf(
							chargesRecovery.getBankAmount(), DebitOrCredit.CREDIT));
					pacsBankCrTrans
							.setTransType(currentTran.getTransactionType().getValue());
					pacsBankCrTrans.setRemarks(currentTran.getTransactionCode()+" "+crDr);
					bankGlEntries.add(pacsBankCrTrans);
				}
			}
		}
		
	}

/*	private void populateBackGLEntry(List<PacsGLEntry> bankGlEntries, AccountingMoney transactionAmount,
			String ccbAccountNumber, int crDr) {
		PacsGLEntry glEntry = new PacsGLEntry();
		glEntry.setTransactionAmount(transactionAmount);
		glEntry.setAccountNo(ccbAccountNumber);
		glEntry.setCrDr(crDr);
		bankGlEntries.add(glEntry);
	}
*/

	private void populateBackGLEntry(List<PacsGLEntry> pacsGlEntries, AccountingMoney transAmt, String AccNo, Integer crDr, String transType, String remarks) {
		PacsGLEntry glEntry = new PacsGLEntry();
		glEntry.setTransactionAmount(transAmt);
		glEntry.setAccountNo(AccNo);
		glEntry.setCrDr(crDr);
		glEntry.setTransType(transType);
		glEntry.setRemarks(remarks);
		pacsGlEntries.add(glEntry);
	}
	

	private void savePacsGlExtracts(List<PacsGLEntry> pacsGLEntryList, Pacs pacs) {

		List<PacsGLExtract> pacsGLExtList = PacsGLExtractHelper.getPacsGLExtracts(pacsGLEntryList, pacs);

		KLSDataAccessFactory.getPacsGLExtractDAO().savePacsGLExtract(pacsGLExtList);

	}
	
	public static String getFileDirectory() {
				
		String fileDirectory = PropertiesUtil.getDocumentProperty("filesLocation")+"GLExtract/";
		
		logger.info(" fileDirectory : " + fileDirectory);
		File outputDir = new File(fileDirectory);
		if(!outputDir.exists()){
			outputDir.mkdir();
		}
		return fileDirectory;
	}
	
	public String checkValidGl(PacsGlMapping glMapping, List<GlData> glDataList, String status){
		glDataList.contains(glMapping.getGlCode());
		List<String> glAccnts = new ArrayList<>();
		for(GlData data : glDataList){
			glAccnts.add(data.getGlAccountNumber());
		}
		
			if(glAccnts.contains(glMapping.getGlCode()));
			else{
				status = glMapping.getGlCode();
				return status;
			}
			
			if(glAccnts.contains(glMapping.getIntReceivableGL()));
			else{
				status = glMapping.getIntReceivableGL();
				return status;
			}
			
			if(glAccnts.contains(glMapping.getPenalintReceivableGL()));
			else{
				status = glMapping.getPenalintReceivableGL();
				return status;
			}
			
			if(glAccnts.contains(glMapping.getIntReceivedGL()));
			else{
				status = glMapping.getIntReceivedGL();
				return status;
			}
			
			if(glAccnts.contains(glMapping.getPenalIntReceivedGL()));
				
			else{
				status = glMapping.getPenalIntReceivedGL();
				return status;
			}
			return status;
	}

}
