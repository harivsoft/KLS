package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.IPurposeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.dao.IInterestChargedReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.InterestChargedReportData;

public class InterestChargedReportHelper {
	private static final Logger logger = Logger
			.getLogger(InterestChargedReportHelper.class);
	
	public static List<InterestChargedReportData> getInterestChargedList(List<Object[]> interestChargedList, String fromDate, String toDateString) {
		
		logger.info("Enter: InterestChargedReportHelper in side.");
		
		List<InterestChargedReportData> interestChargedReportDataList = new ArrayList<InterestChargedReportData>();
		InterestChargedReportData data = null;
		//Integer i = 1;
		for(Object[] object : interestChargedList) {

			data = new InterestChargedReportData();
			
		//	data.setSno(i);
			
			BigInteger locId = (BigInteger) object[5];
			if(locId != null) {
				data.setLoanAccountNumber(locId.intValue());
			}
			
			Integer productId = (Integer) object[6];
			if(productId != null) {
				data.setProductId(productId.intValue());
				Product theProduct = new Product();
				IProductDAO pDao = KLSDataAccessFactory.getProductMasterDAO();
				theProduct.setId(productId);
				Product p = pDao.getProduct(theProduct, false);
				data.setProductName(p.getName());
			}
			
			String customerId = (String) object[0];
		/*	if(customerId != null) {
				PersonData customer = RestClientUtil.getCustomerById(Long.valueOf(customerId));
				if(customer != null) {*/
					data.setMemberName((String) object[1]);
					data.setMemberCode(customerId);
				/*}
			}*/
			
			//if(data.getLoanAccountNumber() != null) {
				//BigDecimal currentBalance = KLSReportDataAccessFactory.getDrawalReportDAO().getAccountBalance(data.getLoanAccountNumber(), DateUtil.getVSoftDateByString(toDate));
			BigDecimal currentBalance = (BigDecimal) object[2];	
			if(currentBalance != null) {
					data.setOutStandingAmount(currentBalance.setScale(2).abs());
				} else {
					data.setOutStandingAmount(BigDecimal.ZERO.setScale(2));
				}
		//	}
		
			IInterestChargedReportDAO iDao = KLSReportDataAccessFactory.getInterestChargedReportDAO();
			BigDecimal interestAmt =  iDao.getInterestAmountBasedOnLocId(locId, fromDate, toDateString);
			System.out.println("interestAmt==="+interestAmt);
			//BigDecimal interestCharged = (BigDecimal)object[3];
			if(interestAmt != null) {
				data.setInterestCharged(interestAmt.setScale(2,RoundingMode.HALF_EVEN));
			} else {
				data.setInterestCharged(BigDecimal.ZERO.setScale(2));
			}
			
			BigDecimal balance = iDao.getBalanceAmount(interestAmt,locId, fromDate, toDateString);
			logger.info("balance====="+balance);
			if(balance != null) {
				data.setBalance(balance.setScale(2,RoundingMode.HALF_EVEN));
			} else {
				data.setBalance(BigDecimal.ZERO.setScale(2));
			}
			
			Integer purposeId = (Integer) object[7];
			if(purposeId != null) {
				data.setPurposeId(purposeId.intValue());
				Purpose purpose = new Purpose();
				IPurposeDAO dao = KLSDataAccessFactory.getPurposeDAO();
				purpose = dao.getPurposeById(purposeId);
			
				data.setPurposeName(purpose.getName());
			}
			
			
			
			/*interestChargedReportDataList.add(data);
			i = i+1;*/
			interestChargedReportDataList.add(data);
		}
		logger.info("End: InterestChargedReportHelper in side.");
		return interestChargedReportDataList;
		
	}

}
