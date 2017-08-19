package com.vosftcorp.kls.report.service.dcb.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vosftcorp.kls.report.dao.dcb.IDCBReportDAO;
import com.vosftcorp.kls.report.service.dcb.IDCBReportService;
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.report.action.DCBReportActionServlet;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.DCBReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.report.service.helper.DCBReportHelper;
import com.vsoftcorp.time.Date;
import com.vsoftcorp.accounting.types.AccountingMoney;
/**
 * 
 * @author a1605
 */
public class DCBReportService implements IDCBReportService {

	private static final Logger logger = Logger.getLogger(DCBReportActionServlet.class);

	@Override
	public List<DCBReportData> getDCBReport(Integer branchId, Integer pacId, Integer schemeId, Long customerId, Date fromDate, Date toDate, Integer demandFreequency) {
		logger.info("Start: inside getDCBReport");

		List<DCBReportData> dcbList = new ArrayList<DCBReportData>();

		IDCBReportDAO dao = KLSReportDataAccessFactory.getDCBReportDAO();

		try {
			List<Map> list = dao.getDCBReport(branchId, pacId, schemeId, customerId, fromDate, toDate);
			dcbList = DCBReportHelper.getDCBReportData(list);

			IAccountDAO accountDAO = KLSDataAccessFactory.getAccountDAO();

			Long accountId = null;

			Money previousPrincipalOverdueDemand = Money.ZERO;
			Money previousInterestOverdueDemand = Money.ZERO;
			Money overduePrincipalCollection = Money.ZERO;
			Money overdueInterestCollection = Money.ZERO;
			Money currentPrincipleDemand = Money.ZERO;
			Money currentInterestDemand = Money.ZERO;
			Money totalPrincipleDemand = Money.ZERO;
			Money totalInterestDemand = Money.ZERO;
			Money principleAdvanceCollection = Money.ZERO;
			Money interestAdvanceCollection = Money.ZERO;
			Money overduePrincipleAsOnFromDate = Money.ZERO;
			Money overdueInterestAsOnFromDate = Money.ZERO;
			Money principalOverdueFromCurrentDemand = Money.ZERO;
			Money interestOverdueFromCurrentDemand = Money.ZERO;
			Integer newSchemeId = null;
			Integer newPacId = null;
            BigDecimal interestAccrued=BigDecimal.ZERO;
            boolean asOnWhen=false;
            
			for (DCBReportData dcbReportData : dcbList) {
				newSchemeId = Integer.parseInt(dcbReportData.getSchemeCode());
				newPacId = Integer.parseInt(dcbReportData.getPacsId());

				accountId = accountDAO.getAccount(dcbReportData.getAccountNumber()).getId();
				previousPrincipalOverdueDemand =  dao.getPreviousPrincipalOverdueDemand(accountId, fromDate, newPacId, newSchemeId).getMoney();
				dcbReportData.setPreviousPrincipalOverdueDemand(previousPrincipalOverdueDemand.getAmount().setScale(2));
				previousInterestOverdueDemand = dao.getPreviousInterestOverdueDemand(accountId, fromDate, newPacId, newSchemeId).getMoney();
				dcbReportData.setPreviousInterestOverdueDemand(previousInterestOverdueDemand.getAmount().setScale(2));
				currentPrincipleDemand = dao.getCurrentPrincipalDemand(accountId, fromDate, toDate, demandFreequency, newPacId, newSchemeId).getMoney();
				dcbReportData.setCurrentPrincipalDemand(currentPrincipleDemand.getAmount().setScale(2));
				interestAccrued=dcbReportData.getInterestAccrued();
				currentInterestDemand = dao.getCurrentInterestDemand(accountId, fromDate, toDate, demandFreequency, newPacId, newSchemeId).getMoney();
				if(asOnWhen){
					Money interest_Accrued=Money.valueOf(Currency.getInstance("INR"), interestAccrued);
					currentInterestDemand=currentInterestDemand.add(interest_Accrued);
				}
				else
					currentInterestDemand=currentInterestDemand;
				dcbReportData.setCurrentInterestDemand(currentInterestDemand.getAmount().setScale(2));
				overduePrincipalCollection = dao.getOverduePrincipleCollection(accountId, fromDate, toDate, newPacId, newSchemeId,demandFreequency).getMoney();
				overdueInterestCollection = dao.getOverdueInterestCollection(accountId, fromDate, toDate, newPacId, newSchemeId,demandFreequency).getMoney();
logger.info("testing collection===="+overdueInterestCollection);
				if (overduePrincipalCollection.compareTo(previousPrincipalOverdueDemand) > 0) {
					dcbReportData.setOverduePrincipalCollection(previousPrincipalOverdueDemand.getAmount().setScale(2));
					//dcbReportData.setOverduePrincipalCollection(dcbReportData.);
				} else {
					logger.info("testing overdue==="+previousPrincipalOverdueDemand+","+overduePrincipalCollection);
					dcbReportData.setOverduePrincipalCollection(previousPrincipalOverdueDemand.subtract(overduePrincipalCollection).getAmount().setScale(2));
					//overduePrincipleAsOnFromDate = previousPrincipalOverdueDemand.subtract(overduePrincipalCollection);
				}
				overduePrincipleAsOnFromDate=dao.getOverduePrincipleAsOnFromDate(accountId, fromDate, toDate, demandFreequency, newPacId, newSchemeId).getMoney();
				if(overduePrincipleAsOnFromDate.compareTo(previousPrincipalOverdueDemand)<0)
				dcbReportData.setOverduePrincipleAsOnFromDate(previousPrincipalOverdueDemand.subtract(overduePrincipleAsOnFromDate).getAmount().setScale(2));
				else
					dcbReportData.setOverduePrincipleAsOnFromDate(BigDecimal.ZERO.setScale(2));	
				if (overdueInterestCollection.compareTo(previousInterestOverdueDemand) > 0) {
					dcbReportData.setOverdueInterestCollection(previousInterestOverdueDemand.getAmount().setScale(2));
					//dcbReportData.setOverdueInterestCollection(BigDecimal.ZERO);
				} else {
					logger.info("testing overdueInterest==="+previousPrincipalOverdueDemand+","+overduePrincipalCollection);
					dcbReportData.setOverdueInterestCollection(previousInterestOverdueDemand.subtract(overdueInterestCollection).getAmount().setScale(2));
					//overdueInterestAsOnFromDate = previousInterestOverdueDemand.subtract(overdueInterestCollection);
				}
				overdueInterestAsOnFromDate=dao.getOverdueInterestAsOnFromDate(accountId, fromDate, toDate, newPacId, newSchemeId, demandFreequency).getMoney();
				if(overdueInterestAsOnFromDate.compareTo(previousInterestOverdueDemand)<0)
				dcbReportData.setOverdueInterestAsOnFromDate(previousInterestOverdueDemand.subtract(overdueInterestAsOnFromDate).getAmount().setScale(2));
				else
					dcbReportData.setOverdueInterestAsOnFromDate(BigDecimal.ZERO.setScale(2));
				//dcbReportData.setTotalPrincipalCollection(overduePrincipalCollection.getAmount().setScale(2));
				dcbReportData.setTotalPrincipalCollection(dao.getTotalPrincipleCollection(accountId, fromDate, toDate, newPacId, newSchemeId, demandFreequency).getMoney().getAmount().setScale(2));
				dcbReportData.setTotalInterestCollection(dao.getTotalInterestCollection(accountId, fromDate, toDate, newPacId, newSchemeId, demandFreequency).getMoney().getAmount().setScale(2));
logger.info("total collection==="+dao.getTotalInterestCollection(accountId, fromDate, toDate, newPacId, newSchemeId, demandFreequency));
				totalPrincipleDemand = previousPrincipalOverdueDemand.add(currentPrincipleDemand);
				totalInterestDemand = previousInterestOverdueDemand.add(currentInterestDemand);
				logger.info("total interest demand==="+totalInterestDemand);
				dcbReportData.setTotalPrincipalDemand(totalPrincipleDemand.getAmount().setScale(2));
				dcbReportData.setTotalInterestDemand(totalInterestDemand.getAmount().setScale(2));
				//principleAdvanceCollection = totalPrincipleDemand.compareTo(overduePrincipalCollection) > 1 ? totalPrincipleDemand.subtract(overduePrincipalCollection) : Money.ZERO;
				//dcbReportData.setAdvancePrincipalCollection(principleAdvanceCollection.getAmount().setScale(2));
				//principleAdvanceCollection=dao.getAdvancePrincipleCollection(accountId, fromDate, toDate, newPacId, newSchemeId, demandFreequency).getMoney();
				//dcbReportData.setAdvancePrincipalCollection(principleAdvanceCollection.getAmount().setScale(2));
				principleAdvanceCollection = Money.valueOf("INR",dcbReportData.getTotalPrincipalCollection()).compareTo(totalPrincipleDemand) > 0 ? Money.valueOf("INR",dcbReportData.getTotalPrincipalCollection()).subtract(totalPrincipleDemand) : Money.ZERO;
				dcbReportData.setAdvancePrincipalCollection(principleAdvanceCollection.getAmount().setScale(2));
				
				//interestAdvanceCollection = totalInterestDemand.compareTo(overdueInterestCollection) > 1 ? totalInterestDemand.subtract(overdueInterestCollection) : Money.ZERO;
				//dcbReportData.setAdvanceInterestCollection(interestAdvanceCollection.getAmount().setScale(2));
				//interestAdvanceCollection=dao.getAdvanceInterestCollection(accountId, fromDate, toDate, newPacId, newSchemeId, demandFreequency).getMoney();
				//dcbReportData.setAdvanceInterestCollection(interestAdvanceCollection.getAmount().setScale(2));
				logger.info("Advance Interest collection===="+Money.valueOf("INR",dcbReportData.getTotalInterestCollection()).subtract(totalInterestDemand));
				interestAdvanceCollection = Money.valueOf("INR",dcbReportData.getTotalInterestCollection()).compareTo(totalInterestDemand) > 0 ? Money.valueOf("INR",dcbReportData.getTotalInterestCollection()).subtract(totalInterestDemand) : Money.ZERO;
				dcbReportData.setAdvanceInterestCollection(interestAdvanceCollection.getAmount().setScale(2));
				
				dcbReportData.setPrincipalBalanceOutstanding(totalPrincipleDemand.compareTo(Money.valueOf("INR",dcbReportData.getTotalPrincipalCollection())) > 0 ? totalPrincipleDemand.subtract(Money.valueOf("INR",dcbReportData.getTotalPrincipalCollection())).getAmount()
						.setScale(2) : BigDecimal.ZERO.setScale(2));
				dcbReportData.setInterestBalanceOutstanding(totalInterestDemand.compareTo(Money.valueOf("INR",dcbReportData.getTotalInterestCollection())) > 0 ? totalInterestDemand.subtract(Money.valueOf("INR",dcbReportData.getTotalInterestCollection())).getAmount()
						.setScale(2) : BigDecimal.ZERO.setScale(2));

				//principalOverdueFromCurrentDemand = overduePrincipalCollection.subtract(overduePrincipleAsOnFromDate).compareTo(overduePrincipalCollection) < -1 ? overduePrincipalCollection
				//		.subtract(overduePrincipalCollection.add(overduePrincipleAsOnFromDate)) : Money.ZERO;
				logger.info("addition====="+(overduePrincipleAsOnFromDate).add(overduePrincipalCollection));
				principalOverdueFromCurrentDemand=Money.valueOf("INR", dcbReportData.getTotalPrincipalCollection()).subtract(overduePrincipleAsOnFromDate).compareTo(Money.valueOf("INR",dcbReportData.getOverduePrincipalCollection()))<0?(Money.valueOf("INR", dcbReportData.getTotalPrincipalCollection()).subtract((overduePrincipleAsOnFromDate).add(Money.valueOf("INR",dcbReportData.getOverduePrincipalCollection())))):Money.ZERO;
                 if(principalOverdueFromCurrentDemand.isNegative())
                	 principalOverdueFromCurrentDemand=Money.ZERO;
				//interestOverdueFromCurrentDemand = overdueInterestCollection.subtract(overdueInterestAsOnFromDate).compareTo(overdueInterestCollection) < -1 ? overdueInterestCollection
				//		.subtract(overdueInterestCollection.add(overdueInterestAsOnFromDate)) : Money.ZERO;
				interestOverdueFromCurrentDemand=Money.valueOf("INR", dcbReportData.getTotalInterestCollection()).subtract(overdueInterestAsOnFromDate).compareTo(Money.valueOf("INR",dcbReportData.getOverdueInterestCollection()))<0?Money.valueOf("INR", dcbReportData.getTotalInterestCollection()).subtract((overdueInterestAsOnFromDate).add(Money.valueOf("INR",dcbReportData.getOverdueInterestCollection()))):Money.ZERO;
                  if(interestOverdueFromCurrentDemand.isNegative())
                	  interestOverdueFromCurrentDemand=Money.ZERO;
				dcbReportData.setPrincipalOverdueFromCurrentDemand(principalOverdueFromCurrentDemand.getAmount().setScale(2));
				dcbReportData.setInterestOverdueFromCurrentDemand(interestOverdueFromCurrentDemand.getAmount().setScale(2));
				//dcbReportData.setTotalPrincipalOverdue(dcbReportData.getOverduePrincipleAsOnFromDate().add(dcbReportData.getPrincipalOverdueFromCurrentDemand()));
				//dcbReportData.setTotalInterestOverdue(dcbReportData.getOverdueInterestAsOnFromDate().add(dcbReportData.getInterestOverdueFromCurrentDemand()));
				dcbReportData.setTotalPrincipalOverdue(dcbReportData.getCurrentPrincipalDemand().add(dcbReportData.getPreviousPrincipalOverdueDemand()));
				dcbReportData.setTotalInterestOverdue(dcbReportData.getCurrentInterestDemand().add(dcbReportData.getPreviousInterestOverdueDemand()));

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error:inside getDCBReport"+e.getCause());
			throw new KlsReportRuntimeException("Can not get DCB report");
		}
		logger.info("End: inside getDCBReport");
		return dcbList;
	}
}
