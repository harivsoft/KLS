package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.BankParameterData;
import com.vsoftcorp.kls.data.DayEndResponse;
import com.vsoftcorp.kls.data.GlData;
import com.vsoftcorp.kls.dataaccess.IDayBeginDao;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.transaction.ICurrentTransactionDAO;
import com.vsoftcorp.kls.service.IOfflineDayEnd;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.loan.ILoanInterestService;
import com.vsoftcorp.kls.service.transaction.IBorrowingTransactionService;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.loan.LoanType;
import com.vsoftcorp.kls.valuetypes.transaction.BorrowingTransactionMethod;
import com.vsoftcorp.time.Date;

public class OfflineDayEnd implements IOfflineDayEnd {

	private static final Logger logger = Logger.getLogger(OfflineDayEnd.class);

	@Override
	public List<DayEndResponse> doOfflineDayEnd(BankParameterData bankParameterData,List<GlData> glDataList, String userName) {

		logger.info("Start: off line day end started in doOfflineDayEnd()");
		logger.info("User name" + userName);
		String status = "";
		List<DayEndResponse> dayendResponse = new ArrayList<DayEndResponse>();
		String dayValidation = null;
		String pacsDayEndStatus = null;
		String pacsBusinessDate = null;
		String branchId = null;
		String eventType="";
		try {
			IPacsDAO pacsDAO = KLSDataAccessFactory.getPacsDAO();
			IDayBeginDao dao = KLSDataAccessFactory.getDayBeginDAO();
			ICurrentTransactionDAO cDao = KLSDataAccessFactory.getCurrentTransactionDAO();
			List<Pacs> pacsList = pacsDAO.getClosedOfflinePacs();

			Date currentDate = null;

			for (Pacs pacs : pacsList) {

				try {

					BankParameter master = KLSDataAccessFactory.getBankParameter();
					pacsBusinessDate = RestClientUtil.getPacsBusinessDate(pacs.getId(), pacs.getBranch().getId());
					currentDate = DateUtil.getVSoftDateByString(pacsBusinessDate);
					Map businessDateData = KLSServiceFactory.getCalendarService().getNextBusinessDate(pacsBusinessDate,
							0);
					if (businessDateData.get("isValid") == "false") {
						DayEndResponse dayres = new DayEndResponse();
						dayres.setPacsId(pacs.getId());
						dayres.setPacsName(pacs.getName());
						dayres.setStatus("Failure");
						dayres.setPacsDate(DateUtil.getDateString(currentDate));
						dayres.setRemarks("Calendar / Next Business Date is not available");
						dayendResponse.add(dayres);
					} else {
						eventType=(String) businessDateData.get("type");
						branchId = getBranchIdString("" + pacs.getBranch().getId(), "" + pacs.getId());
						logger.info("Branch Id=" + branchId);
						dayValidation = RestClientUtil.dayEndValidation(pacsBusinessDate, branchId, userName);
						if ("true".equalsIgnoreCase(dayValidation)) {
							EntityManagerUtil.beginTransaction();
							List<Integer> pacsIdsList = new ArrayList<Integer>();
							pacsIdsList.add(pacs.getId());
							/*
							 * Generating Borrowing transactions For ST loans we
							 * have 3 methods as handled below
							 */
							List<CurrentTransaction> currTransLst = cDao
									.getTransactionsByPacs(LoanType.SHORT_TERM.getValue(), pacs.getId());
							IBorrowingTransactionService bTransService = KLSServiceFactory
									.getBorrowingTransactionService();
							if (master.getBorrowingTransactionMethod().getValue()
									.equals(BorrowingTransactionMethod.Grouping.getValue()))
								bTransService.saveBorrowingTransactionsGroupingMethod(currentDate, LoanType.SHORT_TERM,
										pacsIdsList);
							else if (master.getBorrowingTransactionMethod().getValue()
									.equals(BorrowingTransactionMethod.OneToOneStright.getValue()))
								bTransService.saveBorrowingTransactionsOnetoOneStraight(currTransLst, currentDate,
										LoanType.SHORT_TERM, pacsIdsList);
							else if (master.getBorrowingTransactionMethod().getValue()
									.equals(BorrowingTransactionMethod.OneToOneEarly.getValue()))
								bTransService.saveBorrowingTransactionsOnetoOneEarly(currTransLst, currentDate,
										LoanType.SHORT_TERM, pacsIdsList);

							// For MT, LT and LT loans one to one straight
							// method is
							// followed
							currTransLst = cDao.getTransactionsByPacs(LoanType.MEDIUM_TERM.getValue(), pacs.getId());
							bTransService.saveBorrowingTransactionsOnetoOneStraight(currTransLst, currentDate,
									LoanType.MEDIUM_TERM, pacsIdsList);
							currTransLst = cDao.getTransactionsByPacs(LoanType.LONG_TERM.getValue(), pacs.getId());
							bTransService.saveBorrowingTransactionsOnetoOneStraight(currTransLst, currentDate,
									LoanType.LONG_TERM, pacsIdsList);

							// Calculating Interest for the accounts of off-line
							// pacs
							ILoanInterestService loanInterestService = KLSServiceFactory.getLoanInterestService();

							loanInterestService.calculateInterest(currentDate, pacsIdsList);

							// *** Posting Interest ***//
							loanInterestService.postInterest(currentDate, eventType, pacsIdsList);

							// *** Resetting vouchers *** //
							dao.resetVoucherNumbers(eventType, pacsIdsList);

							// *** Generating PACGL entries excel file ***//
							currTransLst = cDao.getCurrentTransactionByStatus(pacsIdsList);

							if (!currTransLst.isEmpty())
								status = KLSServiceFactory.getPacsGLEntryService().extractPacsGLEntries(currTransLst,
										glDataList);

							if (status.equals("")) {
								cDao.moveCurrentTransactionRecordsToTransactionHistory(pacsIdsList);
							}

							// Opening closed pacs for next day entry.
							pacsDAO.updatePacsStatus(pacsIdsList);

							if (status != "") {
								DayEndResponse dayres = new DayEndResponse();
								dayres.setPacsId(pacs.getId());
								dayres.setPacsName(pacs.getName());
								dayres.setStatus("Failure");
								dayres.setPacsDate(DateUtil.getDateString(currentDate));
								dayres.setRemarks(status + " GL does not exist in Pacs Suvikas");
								dayendResponse.add(dayres);
								EntityManagerUtil.CommitOrRollBackTransaction(false);
							} else {
								pacsDayEndStatus = RestClientUtil.processDayEndAndDayBegin(pacsBusinessDate, branchId,
										userName);
								if ("true".equalsIgnoreCase(pacsDayEndStatus)) {
									DayEndResponse dayres = new DayEndResponse();
									dayres.setPacsId(pacs.getId());
									dayres.setPacsName(pacs.getName());
									dayres.setStatus("Success");
									dayres.setPacsDate(DateUtil.getDateString(currentDate));
									dayendResponse.add(dayres);
									EntityManagerUtil.CommitOrRollBackTransaction(true);
								} else {
									DayEndResponse dayres = new DayEndResponse();
									dayres.setPacsId(pacs.getId());
									dayres.setPacsName(pacs.getName());
									dayres.setStatus("Failure");
									dayres.setPacsDate(DateUtil.getDateString(currentDate));
									dayres.setRemarks("Pacs Suvikas Day End Failed");
									dayendResponse.add(dayres);
									EntityManagerUtil.CommitOrRollBackTransaction(false);
								}

							}
						} else {
							DayEndResponse dayres = new DayEndResponse();
							dayres.setPacsId(pacs.getId());
							dayres.setPacsName(pacs.getName());
							dayres.setStatus("Failure");
							dayres.setPacsDate(DateUtil.getDateString(currentDate));
							dayres.setRemarks("Pass all the pending vouchers");
							dayendResponse.add(dayres);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
					EntityManagerUtil.CommitOrRollBackTransaction(false);
					DayEndResponse dayres = new DayEndResponse();
					dayres.setPacsId(pacs.getId());
					dayres.setPacsName(pacs.getName());
					dayres.setStatus("Failure");
					dayres.setPacsDate(DateUtil.getDateString(currentDate));
					dayres.setRemarks(e.getMessage());
					dayendResponse.add(dayres);
					logger.error("day activity data cannot be updated");
					continue;
				}
			} // for loop end

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("day activity data cannot be updated");
		}
		logger.info("End: off line day end started in doOfflineDayEnd()");
		return dayendResponse;
	}

	public List<String> getPacIdList(List<Pacs> pacsList) {

		List<String> pacsIdList = new ArrayList<String>();
		try {
			for (Pacs p : pacsList) {
				pacsIdList.add("" + p.getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pacsIdList;
	}

	public String getBranchIdString(String branchId, String pacsid) {
		logger.info("Satrt: in getBranchIdString()");
		String branchIdStr = "";
		try {
			for (int i = branchId.length(); i < 4; i++)
				branchId = "0" + branchId;
			for (int i = pacsid.length(); i < 5; i++)
				pacsid = "0" + pacsid;
			branchIdStr = branchId + pacsid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("End: in getBranchIdString()");
		return branchIdStr;
	}

}
