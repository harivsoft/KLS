package com.vsoftcorp.kls.dataaccess.loan;

import java.util.List;

import com.vsoftcorp.kls.business.entity.account.BorrowingsAccountProperty;
/**
 * @author a1691
 * 
 */
public interface IBorrowingsAccountPropertyDAO {

	/**
	 * 
	 * @param pacId 
	 * @param custId
	 * @param isCloseSession
	 * @return
	 */
	public boolean checkIfAccountExists(int pacId, Integer productId, boolean isCloseSession);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public BorrowingsAccountProperty getBorrowingsAccountProperty(Long accountPropertyId);

	/**
	 * 
	 * @return
	 */
	public List<BorrowingsAccountProperty> getAllBorrowingsAccountProperties(boolean b);

	/**
	 * 
	 * @param BorrowingsAccountProperty
	 * @return
	 */
	public BorrowingsAccountProperty saveBorrowingsAccountProperty(BorrowingsAccountProperty BorrowingsAccountProperty);

	public void updateBorrowingAccountProperty(BorrowingsAccountProperty borrowingsAccountProperty);

	public BorrowingsAccountProperty getAccountPropertyByBorrowingProductId(Integer borrowingProductId);

	public void deleteBorrowingsAccount(Long borrowingAccountId);
	
	public BorrowingsAccountProperty getAccountPropertyByProductId(Integer productId) ;
	public boolean checkIfAccountExistsWithBorrowingProduct(Integer borrowingProductId, Integer pacId);

	public boolean checkIfAccountExistsWithProduct(Integer productId,Integer pacId);

	public BorrowingsAccountProperty getAccountPropertyByProductIdPacsId(Integer productId, Integer pacsId);

	public BorrowingsAccountProperty getBorroingAccountPropertyByAccountId(Long accountId);

	public BorrowingsAccountProperty getAccountPropertyByBorrowingProductIdPacsId(Integer pacsId, Integer borrowingProductId);
	
	public BorrowingsAccountProperty getAccountPropertyByLoanProductIdPacsId(Integer pacsId, Integer productId);


}
