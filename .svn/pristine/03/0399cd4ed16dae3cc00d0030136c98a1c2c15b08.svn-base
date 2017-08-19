package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.BorrowingsAccount;
import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.data.BorrowingsAccountData;

/**
 * @author a9153
 * 
 *         Helper Class for conversion from pojo to entity and vice versa.
 */

public class BorrowingsAccountHelper {

	/**
	 * The method converts borrowingsAccount entity to borrowingsAccount pojo.
	 * 
	 * @param theMaster
	 * @return
	 */
	public static BorrowingsAccountData getBorrowingsAccountMasterData(BorrowingsAccount theMaster) {
		BorrowingsAccountData data = new BorrowingsAccountData();

		data.setId(theMaster.getId().toString());
		
		data.setAccountNo(theMaster.getAccountNo());
		data.setBankCode(theMaster.getBankCode());
		data.setBranchId(theMaster.getBranch().getId().toString());
		data.setPacsId(theMaster.getPacs().getId().toString());
		data.setProductId(theMaster.getProduct().getId().toString());
		return data;
	}

	/**
	 * This method converts borrowingsAccount pojo and borrowingsAccount entity.
	 * 
	 * @param data
	 * @return
	 */
	public static BorrowingsAccount getBorrowingsAccount(BorrowingsAccountData data) {
		BorrowingsAccount master = new BorrowingsAccount();
		
		master.setId(Integer.parseInt(data.getId()));
		master.setAccountNo(data.getAccountNo());
		master.setBankCode(data.getBankCode());
		
		
		Pacs pacs = new Pacs();
		Product product = new Product();
		Branch branch = new Branch();
		
		pacs.setId(Integer.parseInt(data.getPacsId()));
		product.setId(Integer.parseInt(data.getProductId()));
		branch.setId(Integer.parseInt(data.getBranchId()));

		master.setBranch(branch);
		master.setPacs(pacs);
		master.setProduct(product);

		return master;
	}
}
