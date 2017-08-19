package com.vsoftcorp.kls.service;

import com.vsoftcorp.kls.business.entities.BorrowingProductGlMapping;
import com.vsoftcorp.kls.data.BorrowingProductGlMappingData;

public interface IBorrowingProductGlMapping {
	
	public String saveBorrowingProductGlMapping(BorrowingProductGlMapping borrowingProductGlMapping);

	public String updateBorrowingProductGlMapping(BorrowingProductGlMapping borrowingProductGlMapping);
	
	public BorrowingProductGlMappingData getBorrowingProductGlMappingById(Integer id, Integer pacsId);

}
