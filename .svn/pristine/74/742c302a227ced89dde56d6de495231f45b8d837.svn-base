package com.vsoftcorp.kls.service.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;

public class AccountServiceUtil {
	private static final Logger logger = Logger.getLogger(AccountServiceUtil.class);

	public static String generateAccountNumber(String branchId, String pacsId, String productId) {

		logger.info("Start: Creating account number in createAccountNumber() method");
		IAccountDAO accountDAO = KLSDataAccessFactory.getAccountDAO();
		StringBuilder accountNumber = new StringBuilder();
		logger.info(" branchId : " + branchId);
		String appBranchId = null;
		appBranchId = StringUtils.leftPad(branchId, 4, '0');
		logger.info(" appBranchId : " + appBranchId);
		logger.info(" pacsId : " + pacsId);
		String appPacId = null;
		appPacId = StringUtils.leftPad(pacsId, 5, '0');
		logger.info(" appPacId : " + appPacId);

		String appProductId = null;
		if (productId != null) {
			appProductId = StringUtils.leftPad(productId, 3, "0");
			logger.info(" appProductId : " + appProductId);
		} else
			appProductId = StringConstant.PRODUCT_ID;
		int lastAccountNumber = accountDAO.getRecentAccount(appBranchId + appPacId + appProductId);
		if (lastAccountNumber == 0) {
			lastAccountNumber = 1;
		} else {
			lastAccountNumber = lastAccountNumber + 1;
		}
		logger.info(" lastAccountNumber : " + lastAccountNumber);
		String paddedAccountNumber = null;
		paddedAccountNumber = StringUtils.leftPad(Integer.toString(lastAccountNumber), 6, '0');
		logger.info(" paddedAccountNumber : " + paddedAccountNumber);
		String finalAccountNumber = null;
		finalAccountNumber = accountNumber.append(appBranchId).append(appPacId)
				.append(appProductId).append(paddedAccountNumber).toString();
		logger.info(" finalAccountNumber : " + finalAccountNumber);

		logger.info("End: Created account number in createAccountNumber() method");
		return finalAccountNumber;
	}
}
