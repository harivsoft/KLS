package com.vsoftcorp.kls.service.account.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.BorrowingsAccountProperty;
import com.vsoftcorp.kls.business.entity.account.BorrowingsLineOfCredit;
import com.vsoftcorp.kls.data.BorrowingsAccountPropertyData;
import com.vsoftcorp.kls.dataaccess.IBranchDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsAccountPropertyDAO;
import com.vsoftcorp.kls.dataaccess.loan.IBorrowingsLineOfCreditDAO;
import com.vsoftcorp.kls.service.account.IBorrowingAccountPropertyService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.BorrowingsAccountPropertyHelper;
import com.vsoftcorp.kls.service.util.AccountServiceUtil;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class BorrowingsAccountPropertyService implements IBorrowingAccountPropertyService {
	private static final Logger logger = Logger.getLogger(BorrowingsAccountPropertyService.class);

	@Override
	public boolean checkIfAccountExists(int pacId, Integer productId) {

		logger.info("Start: checking the account using cust id in checkIfAccountExists() method.");
		IBorrowingsAccountPropertyDAO dao = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
		boolean flag = false;
		logger.info(" productId : " + productId);
		logger.info("pacId : " + pacId);
		try {
			flag = dao.checkIfAccountExists(pacId, productId, false);
		} catch (Exception e) {
			logger.error("Error while retriving the account based on cust id from the database");
		}
		logger.info("End: checking the account using cust id in checkIfAccountExists() method.");
		return flag;
	}

	@Override
	public String saveBorrowingAccountProperty(BorrowingsAccountPropertyData borrowingsAccountPropertyData) {

		logger.info("Start : Creating loan loan account in saveAccountProperty() method.");
		// get the loan account property dao.
		IBranchDAO branchDAO = KLSDataAccessFactory.getBranchDAO();
		IPacsDAO pacsDAO = KLSDataAccessFactory.getPacsDAO();
		IBorrowingsAccountPropertyDAO dao = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
		BorrowingsAccountProperty borrowingsAccountProperty = BorrowingsAccountPropertyHelper
				.getBorrowingsAccountProperty(borrowingsAccountPropertyData);
		String accountNumber = "";
		BorrowingsAccountProperty loanAcctProperty = new BorrowingsAccountProperty();
		Pacs pacs = null;
		Branch branch = null;
		try {
			pacs = pacsDAO.getPacs(borrowingsAccountProperty.getAccount().getAccountProperty().getPacs().getId());
			branch = branchDAO.getBranch(borrowingsAccountProperty.getAccount().getAccountProperty().getBranch());
			if (branch != null && pacs != null) {
				borrowingsAccountProperty.getAccount().getAccountProperty().setPacs(pacs);
				borrowingsAccountProperty.getAccount().getAccountProperty().setBranch(branch);
				if (borrowingsAccountProperty.getId() == null) {
					if (checkAccount(borrowingsAccountProperty)) {
						boolean val = dao.checkIfAccountExistsWithBorrowingProduct(borrowingsAccountProperty.getBorrowingProduct().getId(),
								borrowingsAccountProperty.getAccount().getAccountProperty().getPacs().getId());
						logger.info("value from dao layer::"+val);
						boolean flag = dao.checkIfAccountExistsWithProduct(borrowingsAccountProperty.getProduct().getId(), borrowingsAccountProperty
								.getAccount().getAccountProperty().getPacs().getId());
						logger.info("val : " + val);
						logger.info("flag : " + flag);
						if ((val == false && flag == false) || (val==true && flag==false)) {
							generateAccountNumber(borrowingsAccountProperty);
							loanAcctProperty = dao.saveBorrowingsAccountProperty(borrowingsAccountProperty);
							accountNumber = loanAcctProperty.getAccount().getAccountNumber();
						} /*else if (val)
							return "Borrowings Account already exists with the borrowing product";
						else if (flag)
							return "Borrowings Account already exists with the product";*/
						else
							return "Borrowings Account already exists with the borrowing product";
					}

				}

				else {
					BorrowingsAccountProperty master = dao.getBorrowingsAccountProperty(borrowingsAccountProperty.getId());
					if (master != null) {
						Account account = master.getAccount();
						accountNumber = account.getAccountNumber();
						dao.updateBorrowingAccountProperty(borrowingsAccountProperty);
						return "Account updated with account number " + accountNumber;
					}
				}
			} else {
				logger.error(" PACS ID :" + borrowingsAccountProperty.getAccount().getAccountProperty().getPacs().getId() + ": OR Branch ID: "
						+ borrowingsAccountProperty.getAccount().getAccountProperty().getBranch().getId() + "  not exist");
				throw new KlsRuntimeException("Cannot create account as pacid or branch id does not exist");
			}

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error(" Cannot create account");
			throw new KlsRuntimeException("Cannot create account", excp.getCause());
		}
		logger.info("End : Created borrowing account in saveAccountProperty() method.");
		return "Borrowings Account created with account number " + accountNumber;
	}

	private boolean checkAccount(BorrowingsAccountProperty accountProperty) {

		logger.info("Start : check if account exists in checkAccount() method.");
		boolean flag = true;
		Account acct = accountProperty.getAccount();
		IAccountDAO dao = KLSDataAccessFactory.getAccountDAO();
		if (acct != null && acct.getId() != null) {
			try {
				Account account = dao.getAccount(accountProperty.getAccount(), false);
				System.out.println("account--" + account);
				if (account != null) {
					accountProperty.setAccount(account);
				}
			} catch (Exception excp) {
				flag = false;
			}
		}
		logger.info("flag : " + flag);
		logger.info("End : check if account exists in checkAccount() method.");
		return flag;
	}

	private void generateAccountNumber(BorrowingsAccountProperty accountProperty) {

		logger.info("Start: Creating account number in createAccountNumber() method");
		if (accountProperty != null) {
			String branchId = accountProperty.getAccount().getAccountProperty().getBranch().getId().toString();
			String pacsId = accountProperty.getAccount().getAccountProperty().getPacs().getId().toString();
			String productId = accountProperty.getBorrowingProduct().getId().toString();
			String finalAccountNumber = AccountServiceUtil.generateAccountNumber(branchId, pacsId, productId);
			Account account = accountProperty.getAccount();
			account.setAccountNumber(finalAccountNumber);
			accountProperty.setAccount(account);
		}
		logger.info("End: Created account number in createAccountNumber() method");
	}

	@Override
	public List<BorrowingsAccountPropertyData> getAllAccounts() {
		logger.info("Start: Inside method getAllAccounts ()");
		IBorrowingsAccountPropertyDAO dao = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
		List<BorrowingsAccountPropertyData> products = new ArrayList<BorrowingsAccountPropertyData>();
		try {
			List<BorrowingsAccountProperty> productMasterList = dao.getAllBorrowingsAccountProperties(false);
			for (BorrowingsAccountProperty masterData : productMasterList) {
				products.add(BorrowingsAccountPropertyHelper.getBorrowingsAccountPropertyData(masterData));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all BorrowingsAccountProperties  from the database");
			throw new DataAccessException("Error while retriving all BorrowingsAccountProperties ", e.getCause());
		}
		logger.info("End : Inside method getAllAccounts ()");
		return products;
	}

	@Override
	public String deleteBorrowingAccountProperty(Long borrowingAccountId) {
		logger.info("Start : inside deleteBorrowingAccountProperty() method.--" + borrowingAccountId);
		IBorrowingsAccountPropertyDAO dao = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
		if (borrowingAccountId != null) {
			try {
				IBorrowingsLineOfCreditDAO lDao = KLSDataAccessFactory.getBorrowingsLineOfCreditDAO();
				BorrowingsLineOfCredit borrowingsLineOfCredit = lDao.getBorrowingsLineOfCreditByBorrowingAccountId(borrowingAccountId);
				logger.info("borrowingsLineOfCredit : " + borrowingsLineOfCredit);
				if (borrowingsLineOfCredit == null)
					dao.deleteBorrowingsAccount(borrowingAccountId);
				else
					return "can not delete the account as loc is created for this account";
			} catch (Exception excp) {
				logger.error("Error while deleting the account information from the database");
			}
		}

		logger.info("successfully deleted the borrowings account information");
		return "account deleted successfully";
	}

	@Override
	public BorrowingsAccountPropertyData getAccountPropertyByBorrowingProductId(Integer borrowingProductId) {

		logger.info("Start: Fetching the BorrowingsAccountProperty from the database in getAccountPropertyByBorrowingProductId() method.");
		BorrowingsAccountProperty master = new BorrowingsAccountProperty();
		IBorrowingsAccountPropertyDAO dao = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
		BorrowingsAccountPropertyData data = null;
		try {
			master = dao.getAccountPropertyByBorrowingProductId(borrowingProductId);
			data = BorrowingsAccountPropertyHelper.getBorrowingsAccountPropertyData(master);

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the BorrowingsAccountProperty master data from the "
					+ "database using borrowing product Id Exception thrown.");
			throw new DataAccessException("Could not fetch the BorrowingsAccountProperty data from the database.", excp.getCause());
		} /*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End: Successfully fetched the BorrowingsAccountProperty from the database in getAccountPropertyByBorrowingProductId() method.");
		return data;
	}

	@Override
	public BorrowingsAccountPropertyData getAccountPropertyByProductIdPacsId(Integer pacsId,Integer productId) {

		logger.info("Start: Fetching the BorrowingsAccountProperty from the database in getAccountPropertyByProductIdPacsId.");
		BorrowingsAccountProperty master = new BorrowingsAccountProperty();
		IBorrowingsAccountPropertyDAO dao = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
		BorrowingsAccountPropertyData data = null;
		try {
			master = dao.getAccountPropertyByProductIdPacsId(productId, pacsId);
			data = BorrowingsAccountPropertyHelper.getBorrowingsAccountPropertyData(master);

		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the BorrowingsAccountProperty master data from the "
					+ "database using borrowing product Id Exception thrown.");
			throw new DataAccessException("Could not fetch the BorrowingsAccountProperty data from the database.", excp.getCause());
		} /*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End: Successfully fetched the BorrowingsAccountProperty from the database in getAccountPropertyByProductIdPacsId() method.");
		return data;
	}

	@Override
	public BorrowingsAccountPropertyData getAccountPropertyByBorrowingProductIdPacsId(Integer pacsId, Integer borrowingProductId) {
		logger.info("Start: Fetching the BorrowingsAccountProperty from the database in getAccountPropertyByBorrowingProductIdPacsId() method.");
		BorrowingsAccountProperty master = new BorrowingsAccountProperty();
		IBorrowingsAccountPropertyDAO dao = KLSDataAccessFactory.getBorrowingsAccountPropertyDAO();
		BorrowingsAccountPropertyData data = null;
		try {
			master = dao.getAccountPropertyByBorrowingProductIdPacsId(pacsId,borrowingProductId);
			if(master!=null)
			data = BorrowingsAccountPropertyHelper.getBorrowingsAccountPropertyData(master);
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not fetch the BorrowingsAccountProperty master data from the "
					+ "database using borrowing product Id Exception thrown.");
			throw new DataAccessException("Could not fetch the BorrowingsAccountProperty data from the database.", excp.getCause());
		} /*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End: Successfully fetched the BorrowingsAccountProperty from the database in getAccountPropertyByBorrowingProductIdPacsId() method.");
		return data;
	}

}
