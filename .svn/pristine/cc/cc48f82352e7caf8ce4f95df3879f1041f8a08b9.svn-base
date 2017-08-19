package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.business.entities.BorrowingsAccount;
import com.vsoftcorp.kls.data.BorrowingsAccountData;
import com.vsoftcorp.kls.dataaccess.IBankPacsGlDAO;
import com.vsoftcorp.kls.dataaccess.IBankParameterDAO;
import com.vsoftcorp.kls.dataaccess.IBranchDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.ITalukaDAO;
import com.vsoftcorp.kls.dataaccess.IBorrowingsAccountDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IBorrowingsAccountService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.BorrowingsAccountHelper;

/**
 * 
 * @author a9152
 * 
 */
public class BorrowingsAccountService implements IBorrowingsAccountService {

	private static final Logger logger = Logger.getLogger(BorrowingsAccountService.class);

	/**
	 * This method checks for borrowingsAccount id in the db. If exists, then
	 * throw an exception. Else save it to the database.
	 * 
	 * @param borrowingsAccountData
	 */
	public void saveBorrowingsAccount(BorrowingsAccountData borrowingsAccountData) {

		logger.info("Start : Calling borrowingsAccount master dao in saveBorrowingsAccount() method.");
		// get the borrowingsAccount dao.
		IBorrowingsAccountDAO dao = KLSDataAccessFactory.getBorrowingsAccountDAO();
		BorrowingsAccount master = BorrowingsAccountHelper.getBorrowingsAccount(borrowingsAccountData);
		// get the taluka dao.
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		IBranchDAO branchDao = KLSDataAccessFactory.getBranchDAO();
		IProductDAO productDao = KLSDataAccessFactory.getProductMasterDAO();

		BorrowingsAccount borrowingsAccount = null;

		Pacs pacs = null;
		Branch branch = null;
		Product product = null;

		try {
			pacs = pacsDao.getPacs(master.getPacs());
			branch = branchDao.getBranch(master.getBranch());
			product = productDao.getProduct(master.getProduct(), false);

			if (pacs != null && branch != null && product != null) {
				master.setBranch(branch);
				master.setPacs(pacs);
				master.setProduct(product);

				borrowingsAccount = dao.getBorrowingsAccount(master, false);
				if (master.getId() == null) {
					dao.saveBorrowingsAccount(master);
				}
			}
		} catch (Exception excp) {
			logger.error("BorrowingsAccount id already exists");
			throw new KlsRuntimeException("BorrowingsAccount id already exists", excp.getCause());
		}
		if (pacs == null) {
			logger.error("BorrowingsAccount data cannot be saved to the db as pacs id does not exists");
			throw new KlsRuntimeException("BorrowingsAccount data cannot be saved to the db as pacs id does not exists");
		}
		if (branch == null) {
			logger.error("BorrowingsAccount data cannot be saved to the db as branch id does not exists");
			throw new KlsRuntimeException("BorrowingsAccount data cannot be saved to the db as branch id does not exists");
		}
		if (product == null) {
			logger.error("BorrowingsAccount data cannot be saved to the db as product id does not exists");
			throw new KlsRuntimeException("BorrowingsAccount data cannot be saved to the db as product id does not exists");
		}

		logger.info("End : Calling borrowingsAccount dao in saveBorrowingsAccount() method.");
	}

	/**
	 * This method checks for borrowingsAccount id in the db. If exists, then
	 * update the borrowingsAccount data to the database. Else throw the
	 * exception.
	 * 
	 * @param borrowingsAccountData
	 */
	public void updateBorrowingsAccount(BorrowingsAccountData borrowingsAccountData) {

		logger.info("Start : Calling borrowingsAccount dao in updateBorrowingsAccount() method.");
		// get the borrowingsAccount dao.
		IBorrowingsAccountDAO dao = KLSDataAccessFactory.getBorrowingsAccountDAO();
		BorrowingsAccount master = BorrowingsAccountHelper.getBorrowingsAccount(borrowingsAccountData);
		// update the borrowingsAccount data to the db.
		try {
			dao.updateBorrowingsAccount(master);
		} catch (Exception excp) {
			logger.error("BorrowingsAccount data cannot be updated as borrowingsAccount id does not exist");
			throw new KlsRuntimeException("BorrowingsAccount data cannot be updated as borrowingsAccount id does not exist", excp.getCause());
		}
		logger.info("End : Calling borrowingsAccount dao in updateBorrowingsAccount() method.");
	}

	@Override
	public List<BorrowingsAccountData> getAllBorrowingsAccounts() {

		logger.info("Start : Calling borrowingsAccount dao in getAllBorrowingsAccounts() method.");
		// get the borrowingsAccount dao.
		IBorrowingsAccountDAO dao = KLSDataAccessFactory.getBorrowingsAccountDAO();
		List<BorrowingsAccountData> borrowingsAccountDataList = new ArrayList<BorrowingsAccountData>();
		try {
			List<BorrowingsAccount> borrowingsAccountList = dao.getAllBorrowingsAccounts(false);
			for (BorrowingsAccount masterData : borrowingsAccountList) {
				borrowingsAccountDataList.add(BorrowingsAccountHelper.getBorrowingsAccountMasterData(masterData));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the borrowingsAccount records");
			throw new KlsRuntimeException("Error in retrieving all the borrowingsAccount records", excp.getCause());
		}
		logger.info("End : Calling borrowingsAccount dao in getAllBorrowingsAccounts() method.");
		return borrowingsAccountDataList;
	}
	
	public String getBorrowingsAccNo(String bankCode, int branchId, int pacsId, int productId){
		logger.info("Start : Calling borrowings account dao in getBorrowingsAccNo() method.");
		// get the bankPacsGl dao.
		String accNo = "";
		IBorrowingsAccountDAO dao = KLSDataAccessFactory.getBorrowingsAccountDAO();
		try {
			accNo = dao.getBorrowingsAccNo(bankCode, branchId, pacsId, productId);
		} catch (Exception excp) {
			logger.error("Error in retrieving borrowings account record. Inside getBorrowingsAccNo()");
			throw new KlsRuntimeException("Error in retrieving the borrowings account record. Inside getBorrowingsAccNo()", excp.getCause());
		}
		logger.info("End : Calling  borrowings account dao in getBorrowingsAccNo() method.");
		return accNo;		
	}
}
