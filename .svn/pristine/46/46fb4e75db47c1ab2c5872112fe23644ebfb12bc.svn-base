package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.BorrowingProduct;

public interface IBorrowingProductDAO {

	public Integer saveBorrowingProduct(BorrowingProduct theBorrowingProduct);

	public List<BorrowingProduct> getAllBorrowingProducts(boolean isCloseSession);
	
	public List<BorrowingProduct> getAllBorrowingProductsBasedOnLoanType(String loanType,boolean isCloseSession);
	
	public boolean checkForUniqueProductCode(String ProductCode,boolean isCloseSession);

	public void updateBorrowingProduct(BorrowingProduct data);

	public BorrowingProduct getBorrowingProduct(Integer borrowingProductId,
			boolean isCloseSession);

	public boolean checkForUniqueProductName(String productName, boolean isCloseSession);



}
