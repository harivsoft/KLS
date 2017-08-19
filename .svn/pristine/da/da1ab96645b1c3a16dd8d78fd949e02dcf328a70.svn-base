package com.vosftcorp.kls.report.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.service.INPAReportService;
import com.vsoftcorp.kls.GepRep.business.NPAParameters;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.report.action.BorrowingsNPAReportActionServlet;
import com.vsoftcorp.kls.report.dao.IBorrowingAccountLedgerReportDAO;
import com.vsoftcorp.kls.report.dao.INPAParameterDAO;
import com.vsoftcorp.kls.report.dao.INPAReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.NPAReportData;
import com.vsoftcorp.time.Date;

public class NPAReportService implements INPAReportService {

	private static final Logger logger = Logger.getLogger(NPAReportService.class);
	
	@Override
	public List<NPAReportData> getNPAReportData(String asOnDate, String loanType, String reportType,
			String excludeStndLoan) {

		List<NPAReportData> npaList = new ArrayList<NPAReportData>();
		try {
			Integer subStandard = 0;
			Integer doubtful1 = 0;
			Integer doubtful2 = 0;
			Integer doubtful3 = 0;
			Integer loss = 0;
			Integer noOfMonths = 0;
			Date onDate = DateUtil.getVSoftDateByString(asOnDate);
			NPAReportData npaReportData = null;
			INPAReportDAO npaReportDAO = KLSReportDataAccessFactory.getNPAReportDAO();
			List<LineOfCredit> lineOfCreditsList = new ArrayList<LineOfCredit>();
			INPAParameterDAO parameterDAO = KLSReportDataAccessFactory.getNPAParameterDAO();
			List<NPAParameters> npaParameters = parameterDAO.getNPAParameters();
			if ("C".equalsIgnoreCase(loanType)) {
				lineOfCreditsList = npaReportDAO.getSTOverDueList(onDate);
				for (LineOfCredit loc : lineOfCreditsList) {

					Date npaDate = npaReportDAO.getNpaDate(loc.getSeason(), asOnDate);

					noOfMonths = npaReportDAO.getNoOfMonths(loc.getSeason().getOverdueDate(), npaDate);

					npaReportData = new NPAReportData();
					if (noOfMonths != npaParameters.get(0).getPeriod()) {
						subStandard = npaReportDAO.getSeasonMonths(loc.getSeason(),
								npaParameters.get(0).getNoOfSeasons());
						doubtful1 = npaReportDAO.getSeasonMonths(loc.getSeason(),
								npaParameters.get(1).getNoOfSeasons());
						doubtful2 = npaReportDAO.getSeasonMonths(loc.getSeason(),
								npaParameters.get(2).getNoOfSeasons());
						doubtful3 = npaReportDAO.getSeasonMonths(loc.getSeason(),
								npaParameters.get(3).getNoOfSeasons());
						loss = npaReportDAO.getSeasonMonths(loc.getSeason(), npaParameters.get(4).getNoOfSeasons());
					} else {
						subStandard = npaParameters.get(0).getPeriod();
						doubtful1 = subStandard + npaParameters.get(1).getPeriod();
						doubtful2 = doubtful1 + npaParameters.get(2).getPeriod();
						doubtful3 = doubtful2 + npaParameters.get(3).getPeriod();
						loss = doubtful3 + npaParameters.get(4).getPeriod();
					}
					String status = "Standard";

					if (noOfMonths > 0) {

						npaReportData.setNPADate(DateUtil.getDateString(npaDate));
						if (noOfMonths >= subStandard) {
							status = "SubStandard";
						}
						if (noOfMonths >= doubtful1) {
							status = "Doubtful1";
						}
						if (noOfMonths >= doubtful2) {
							status = "Doubtful2";
						}
						if (noOfMonths >= doubtful3) {
							status = "Doubtful3";
						}
						if (noOfMonths >= loss) {
							status = "Loss";
						}
						npaReportData.setStatus(status);
						npaReportData.setAccountNumber(loc.getAccount().getAccountNumber());
						npaReportData.setSanctionedAmount(loc.getSanctionedAmount().getAmount());
						npaReportData.setSanctionedDate(DateUtil.getDateString(loc.getSeason().getOverdueDate()));
						npaReportData.setODAmount(loc.getCurrentBalance().getMoney().getAmount());
						npaReportData.setCurrentBalance(loc.getCurrentBalance().getMoney().getAmount());
						Object[] member = npaReportDAO.getMemberData(loc.getCustomerId());
						npaReportData.setMemberName((String) member[0]);
						npaReportData.setMemberNumber((String) member[1]);
						npaList.add(npaReportData);
						System.out.println("npa innnnn===" + npaList.size());
					} else {
						if ("NO".equalsIgnoreCase(excludeStndLoan)) {
							npaReportData.setStatus(status);
							npaReportData.setAccountNumber(loc.getAccount().getAccountNumber());
							npaReportData.setSanctionedAmount(loc.getSanctionedAmount().getAmount());
							npaReportData.setSanctionedDate(DateUtil.getDateString(loc.getSeason().getOverdueDate()));
							npaReportData.setODAmount(loc.getCurrentBalance().getMoney().getAmount());
							npaReportData.setCurrentBalance(BigDecimal.ZERO);
							Object[] member = npaReportDAO.getMemberData(loc.getCustomerId());
							npaReportData.setMemberName((String) member[0]);
							npaReportData.setMemberNumber((String) member[1]);
							npaList.add(npaReportData);
						}
					}
				}
			}else{
				
				lineOfCreditsList = npaReportDAO.getMTOverDueList(onDate);
				for (LineOfCredit loc : lineOfCreditsList) {
					
					List<LoanRepaymentSchedule> repaymentScheduleList = npaReportDAO.getLoanRepaymentScheduleByLocIdAndBusinessDate(loc.getId(), onDate);
					BigDecimal installmentAmt = repaymentScheduleList.get(0).getInstallmentAmount().getAmount();
					BigDecimal idealBalance = getTotalInstallmentAmount(repaymentScheduleList);
					BigDecimal currentBal = npaReportDAO.getCurrentBalanceAsOnDate(loc.getId(), onDate);
					if(currentBal.compareTo(idealBalance)==1){
						
						BigDecimal od=currentBal.subtract(idealBalance);
						od=od.setScale(2);
						BigDecimal dueInstalments = od.divide(installmentAmt.setScale(2),2, RoundingMode.HALF_UP);
						Integer installments =dueInstalments.intValue()+1;
						Date dueDate = repaymentScheduleList.get(installments).getInstallmentDate();
						Season currentSeason = npaReportDAO.getCurrentSeason(asOnDate);
						Date npaDate = npaReportDAO.getNpaDate(currentSeason, asOnDate);
						npaReportData = new NPAReportData();
						if (noOfMonths != npaParameters.get(0).getPeriod()) {
							subStandard = npaReportDAO.getSeasonMonths(currentSeason,
									npaParameters.get(0).getNoOfSeasons());
							doubtful1 = npaReportDAO.getSeasonMonths(currentSeason,
									npaParameters.get(1).getNoOfSeasons());
							doubtful2 = npaReportDAO.getSeasonMonths(currentSeason,
									npaParameters.get(2).getNoOfSeasons());
							doubtful3 = npaReportDAO.getSeasonMonths(currentSeason,
									npaParameters.get(3).getNoOfSeasons());
							loss = npaReportDAO.getSeasonMonths(currentSeason, npaParameters.get(4).getNoOfSeasons());
						} else {
							subStandard = npaParameters.get(0).getPeriod();
							doubtful1 = subStandard + npaParameters.get(1).getPeriod();
							doubtful2 = doubtful1 + npaParameters.get(2).getPeriod();
							doubtful3 = doubtful2 + npaParameters.get(3).getPeriod();
							loss = doubtful3 + npaParameters.get(4).getPeriod();
						}
						String status = "Standard";

						if (noOfMonths > 0) {

							npaReportData.setNPADate(DateUtil.getDateString(npaDate));
							if (noOfMonths >= subStandard) {
								status = "SubStandard";
							}
							if (noOfMonths >= doubtful1) {
								status = "Doubtful1";
							}
							if (noOfMonths >= doubtful2) {
								status = "Doubtful2";
							}
							if (noOfMonths >= doubtful3) {
								status = "Doubtful3";
							}
							if (noOfMonths >= loss) {
								status = "Loss";
							}
							npaReportData.setStatus(status);
							npaReportData.setAccountNumber(loc.getAccount().getAccountNumber());
							npaReportData.setSanctionedAmount(loc.getSanctionedAmount().getAmount());
							npaReportData.setSanctionedDate(DateUtil.getDateString(loc.getSeason().getOverdueDate()));
							npaReportData.setODAmount(od);
							npaReportData.setCurrentBalance(currentBal);
							npaReportData.setInstallmentAmount(installmentAmt);
							Object[] member = npaReportDAO.getMemberData(loc.getCustomerId());
							npaReportData.setMemberName((String) member[0]);
							npaReportData.setMemberNumber((String) member[1]);
							npaList.add(npaReportData);
							System.out.println("npa innnnn===" + npaList.size());
						} else {
							if ("NO".equalsIgnoreCase(excludeStndLoan)) {
								npaReportData.setNPADate(DateUtil.getDateString(npaDate));
								npaReportData.setStatus(status);
								npaReportData.setAccountNumber(loc.getAccount().getAccountNumber());
								npaReportData.setSanctionedAmount(loc.getSanctionedAmount().getAmount());
								npaReportData.setSanctionedDate(DateUtil.getDateString(loc.getSeason().getOverdueDate()));
								npaReportData.setODAmount(od);
								npaReportData.setCurrentBalance(BigDecimal.ZERO);
								npaReportData.setInstallmentAmount(installmentAmt);
								Object[] member = npaReportDAO.getMemberData(loc.getCustomerId());
								npaReportData.setMemberName((String) member[0]);
								npaReportData.setMemberNumber((String) member[1]);
								npaList.add(npaReportData);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("npa========" + npaList.size());
		return npaList;
	}
	
	public BigDecimal getTotalInstallmentAmount(List<LoanRepaymentSchedule> repaymentScheduleList){
		
		BigDecimal totalInstallmentAmount=BigDecimal.ZERO;
		try {
			
			for(LoanRepaymentSchedule rep : repaymentScheduleList)
			
				totalInstallmentAmount=totalInstallmentAmount.add(rep.getInstallmentAmount().getAmount());
			System.out.println("total ins="+totalInstallmentAmount);
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return totalInstallmentAmount;
	}

	/* For Borrowing NPA */
	
	public List<NPAReportData> getBorrowingsNPAReportData(String asOnDate, String loanType, String reportType,
			String excludeStndLoan) {
		logger.info("Start : in getBorrowingsNPAReportData Method.");
		List<NPAReportData> borrowingNpaList = new ArrayList<NPAReportData>();
		try {
			Integer subStandard = 0;
			Integer doubtful1 = 0;
			Integer doubtful2 = 0;
			Integer doubtful3 = 0;
			Integer loss = 0;
			Integer noOfMonths = 0;
			Date onDate = DateUtil.getVSoftDateByString(asOnDate);
			NPAReportData npaReportData = null;
			INPAReportDAO npaReportDAO = KLSReportDataAccessFactory.getNPAReportDAO();
			IBorrowingAccountLedgerReportDAO balReportDao =  KLSReportDataAccessFactory.getBorrowingAccountLedgerReportDAO(); // For Member details
				
			List<LineOfCredit> lineOfCreditsList = new ArrayList<LineOfCredit>();
			INPAParameterDAO parameterDAO = KLSReportDataAccessFactory.getNPAParameterDAO();
			List<NPAParameters> npaParameters = parameterDAO.getNPAParameters();
			if ("BC".equalsIgnoreCase(loanType)) {
				logger.info("  Lona Type = BC  ");
				lineOfCreditsList = npaReportDAO.getBorrowingSTOverDueList(onDate);
				for (LineOfCredit loc : lineOfCreditsList) {

					String sanctionedDate = DateUtil.getDateString(loc.getSanctionedDate());
					Season currentSeason = npaReportDAO.getCurrentSeason(asOnDate);
					
					logger.info("cure season id = "+currentSeason.getId()+", currentSeason = "+currentSeason.getName()+", currentSeason overdue date ="+currentSeason.getOverdueDate());
				
					Date npaDate = npaReportDAO.getNpaDate(currentSeason, asOnDate);
					logger.info("npa date1 = "+npaDate );
					
					String overDueDate = DateUtil.getDateByAddingNoOfMonths(sanctionedDate,12);//npaReportDAO.getNpaDate(currentSeason, asOnDate);
					Date locOverDueDate = DateUtil.getVSoftDateByString(overDueDate);
					//noOfMonths = npaReportDAO.getNoOfMonths(onDate, npaDate); 
					noOfMonths = npaReportDAO.getNoOfMonths(locOverDueDate, npaDate);
					logger.info("Selected date ="+onDate+" ,sanctionedDate = "+sanctionedDate+" , loc_overdue_date = "+overDueDate);
					logger.info("noOfMonths = "+noOfMonths);
					
					
					
					npaReportData = new NPAReportData();
					/*if (noOfMonths != npaParameters.get(0).getPeriod()) {
						subStandard = npaReportDAO.getSeasonMonths(loc.getSeason(),
								npaParameters.get(0).getNoOfSeasons());
						doubtful1 = npaReportDAO.getSeasonMonths(loc.getSeason(),
								npaParameters.get(1).getNoOfSeasons());
						doubtful2 = npaReportDAO.getSeasonMonths(loc.getSeason(),
								npaParameters.get(2).getNoOfSeasons());
						doubtful3 = npaReportDAO.getSeasonMonths(loc.getSeason(),
								npaParameters.get(3).getNoOfSeasons());
						loss = npaReportDAO.getSeasonMonths(loc.getSeason(), npaParameters.get(4).getNoOfSeasons());
					} else {*/
						subStandard = npaParameters.get(0).getPeriod();
						doubtful1 = subStandard + npaParameters.get(1).getPeriod();
						doubtful2 = doubtful1 + npaParameters.get(2).getPeriod();
						doubtful3 = doubtful2 + npaParameters.get(3).getPeriod();
						loss = doubtful3 + npaParameters.get(4).getPeriod();
					//}
					
					String status = "Standard";

					if (noOfMonths > 0) {

						npaReportData.setNPADate(DateUtil.getDateString(npaDate));
						if (noOfMonths >= subStandard) {
							status = "SubStandard";
						}
						if (noOfMonths >= doubtful1) {
							status = "Doubtful1";
						}
						if (noOfMonths >= doubtful2) {
							status = "Doubtful2";
						}
						if (noOfMonths >= doubtful3) {
							status = "Doubtful3";
						}
						if (noOfMonths >= loss) {
							status = "Loss";
						}
						npaReportData.setStatus(status);
						npaReportData.setAccountNumber(loc.getAccount().getAccountNumber());
						npaReportData.setSanctionedAmount(loc.getSanctionedAmount().getAmount());
						npaReportData.setSanctionedDate(overDueDate);
						npaReportData.setODAmount(loc.getSanctionedAmount().getAmount().subtract(loc.getCurrentBalance().getMoney().getAmount()));
						npaReportData.setCurrentBalance(loc.getSanctionedAmount().getAmount().subtract(loc.getCurrentBalance().getMoney().getAmount()));
						npaReportData.setInstallmentAmount(loc.getSanctionedAmount().getAmount());
						/*List<Object[]> member = null;
						if(loc.getId() != null){
							logger.info("in member details for loc  "+loc.getId());
							member = balReportDao.getCustomerName(loc.getId().toString());
							if(member != null && !member.isEmpty()){
								for (Object[] objects : member) {
									npaReportData.setMemberName(objects[0].toString());
									npaReportData.setMemberNumber(objects[1].toString());
								}
							}
						}*/
						borrowingNpaList.add(npaReportData);
						logger.info(" list data : "+npaReportData.getStatus()+","+npaReportData.getAccountNumber());
						logger.info(" list data : "+npaReportData.getCurrentBalance() +","+npaReportData.getSanctionedAmount());
						logger.info(" npa innnnn===" + borrowingNpaList.size());
					} else {
						if ("NO".equalsIgnoreCase(excludeStndLoan)) {
							npaReportData.setStatus(status);
							npaReportData.setAccountNumber(loc.getAccount().getAccountNumber());
							npaReportData.setSanctionedAmount(loc.getSanctionedAmount().getAmount());
							npaReportData.setSanctionedDate(overDueDate);
							npaReportData.setODAmount(loc.getSanctionedAmount().getAmount().subtract(loc.getCurrentBalance().getMoney().getAmount()));
							npaReportData.setCurrentBalance(loc.getSanctionedAmount().getAmount().subtract(loc.getCurrentBalance().getMoney().getAmount()));
							npaReportData.setInstallmentAmount(loc.getSanctionedAmount().getAmount());
							/*
							List<Object[]> member = null;
							if(loc.getId() != null){
								member = balReportDao.getCustomerName(loc.getId().toString());
								if(member != null && !member.isEmpty()){
									for (Object[] objects : member) {
										npaReportData.setMemberName(objects[0].toString());
										npaReportData.setMemberNumber(objects[1].toString());
									}
								}
							}*/
							borrowingNpaList.add(npaReportData);
						}
					}
				}
			}else{
				logger.info("  Lona Type = BL  ");
				lineOfCreditsList = npaReportDAO.getBorrowingMTOverDueList(onDate); 
				for (LineOfCredit loc : lineOfCreditsList) {
					
					/*	String endDate =  DateUtil.getDateByAddingNoOfMonths(DateUtil.getDateString(loc.getSanctionedDate()), 12);  // For Borrowings it should be 1 Year from loc created date.
						Date locOverDueDate = DateUtil.getVSoftDateByString(endDate);
						String npaAddDate = DateUtil.getDateByAddingNoOfMonths(asOnDate,12);//npaReportDAO.getNpaDate(currentSeason, asOnDate);
						Date npaDate = DateUtil.getVSoftDateByString(npaAddDate);	
						Season currentSeason = npaReportDAO.getCurrentSeason(asOnDate);
						noOfMonths = npaReportDAO.getNoOfMonths(onDate, npaDate);*/
						
						String sanctionedDate = DateUtil.getDateString(loc.getSanctionedDate());
						Season currentSeason = npaReportDAO.getCurrentSeason(asOnDate);
						logger.info("cure season id = "+currentSeason.getId()+", currentSeason = "+currentSeason.getName()+", currentSeason overdue date ="+currentSeason.getOverdueDate());
						Date npaDate = npaReportDAO.getNpaDate(currentSeason, asOnDate);
						logger.info("npa date1 = "+npaDate );
						String overDueDate = DateUtil.getDateByAddingNoOfMonths(sanctionedDate,12);//npaReportDAO.getNpaDate(currentSeason, asOnDate);
						Date locOverDueDate = DateUtil.getVSoftDateByString(overDueDate);
						noOfMonths = npaReportDAO.getNoOfMonths(locOverDueDate, npaDate);
						logger.info("Selected date ="+onDate+" ,sanctionedDate = "+sanctionedDate+" , loc_overdue_date = "+overDueDate);
						logger.info("noOfMonths = "+noOfMonths);
						
						npaReportData = new NPAReportData();
						/*if (noOfMonths != npaParameters.get(0).getPeriod()) {
							subStandard = npaReportDAO.getSeasonMonths(currentSeason,
									npaParameters.get(0).getNoOfSeasons());
							doubtful1 = npaReportDAO.getSeasonMonths(currentSeason,
									npaParameters.get(1).getNoOfSeasons());
							doubtful2 = npaReportDAO.getSeasonMonths(currentSeason,
									npaParameters.get(2).getNoOfSeasons());
							doubtful3 = npaReportDAO.getSeasonMonths(currentSeason,
									npaParameters.get(3).getNoOfSeasons());
							loss = npaReportDAO.getSeasonMonths(currentSeason, npaParameters.get(4).getNoOfSeasons());
						} else {*/
							subStandard = npaParameters.get(0).getPeriod();
							doubtful1 = subStandard + npaParameters.get(1).getPeriod();
							doubtful2 = doubtful1 + npaParameters.get(2).getPeriod();
							doubtful3 = doubtful2 + npaParameters.get(3).getPeriod();
							loss = doubtful3 + npaParameters.get(4).getPeriod();
						//}
						
						logger.info("locOverDueDate = "+locOverDueDate+", currentSeason = "+currentSeason.getId()+", currentSeason overdue date ="+currentSeason.getOverdueDate() );
						logger.info("npa date = "+npaDate +", NoOfMonths = "+noOfMonths);
						
						String status = "Standard";

						if (noOfMonths > 0) {

							npaReportData.setNPADate(DateUtil.getDateString(npaDate));
							if (noOfMonths >= subStandard) {
								status = "SubStandard";
							}
							if (noOfMonths >= doubtful1) {
								status = "Doubtful1";
							}
							if (noOfMonths >= doubtful2) {
								status = "Doubtful2";
							}
							if (noOfMonths >= doubtful3) {
								status = "Doubtful3";
							}
							if (noOfMonths >= loss) {
								status = "Loss";
							}
							npaReportData.setNPADate(DateUtil.getDateString(npaDate));
							npaReportData.setStatus(status);
							npaReportData.setAccountNumber(loc.getAccount().getAccountNumber());
							npaReportData.setSanctionedDate(overDueDate);
							npaReportData.setSanctionedAmount(loc.getSanctionedAmount().getAmount());
							npaReportData.setODAmount(loc.getSanctionedAmount().getAmount().subtract(loc.getCurrentBalance().getMoney().getAmount()));
							npaReportData.setCurrentBalance(loc.getSanctionedAmount().getAmount().subtract(loc.getCurrentBalance().getMoney().getAmount()));
							npaReportData.setInstallmentAmount(loc.getSanctionedAmount().getAmount());
						
						/*	List<Object[]> member = null;
							if(loc.getId() != null){
								member = balReportDao.getCustomerName(loc.getId().toString());
								if(member != null && !member.isEmpty()){
									for (Object[] objects : member) {
										npaReportData.setMemberName(objects[0].toString());
										npaReportData.setMemberNumber(objects[1].toString());
									}
								}
							}*/
							borrowingNpaList.add(npaReportData);
							System.out.println(" list data : "+npaReportData.getStatus()+","+npaReportData.getAccountNumber());
							System.out.println(" list data : "+npaReportData.getCurrentBalance() +","+npaReportData.getSanctionedAmount());
							System.out.println("npa innnnn===" + borrowingNpaList.size());
						} else {
							if ("NO".equalsIgnoreCase(excludeStndLoan)) {
								npaReportData.setNPADate(DateUtil.getDateString(npaDate));
								npaReportData.setStatus(status);
								npaReportData.setAccountNumber(loc.getAccount().getAccountNumber());
								npaReportData.setSanctionedDate(overDueDate);
								npaReportData.setSanctionedAmount(loc.getSanctionedAmount().getAmount());
								npaReportData.setODAmount(loc.getSanctionedAmount().getAmount().subtract(loc.getCurrentBalance().getMoney().getAmount()));
								npaReportData.setCurrentBalance(loc.getSanctionedAmount().getAmount().subtract(loc.getCurrentBalance().getMoney().getAmount()));
								npaReportData.setInstallmentAmount(loc.getSanctionedAmount().getAmount());
							
								/*List<Object[]> member = null;
								if(loc.getId() != null){
									member = balReportDao.getCustomerName(loc.getId().toString());
									if(member != null && !member.isEmpty()){
										for (Object[] objects : member) {
											npaReportData.setMemberName(objects[0].toString());
											npaReportData.setMemberNumber(objects[1].toString());
										}
									}
								}*/
								borrowingNpaList.add(npaReportData);
							}
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Borwwing npa List ========" + borrowingNpaList.size());
		logger.info("End : in getBorrowingsNPAReportData Method.");
		return borrowingNpaList;
	}
	
}
