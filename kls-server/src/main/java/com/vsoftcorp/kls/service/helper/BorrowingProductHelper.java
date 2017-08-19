package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.BorrowingProduct;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.ProductType;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.BorrowingProductData;
import com.vsoftcorp.kls.valuetypes.loanproduct.InterestFrequency;

public class BorrowingProductHelper {

	
	//Get Borrowing Data
	public static BorrowingProductData getBorrowingProductData(BorrowingProduct entity)
	{
		BorrowingProductData data =new BorrowingProductData();
		data.setId(entity.getId());
		
		InterestFrequency interestfrequency = entity.getInterestFrequency();
		if (data.getInterestCategoryId() != null) {
			data.setInterestCategoryId(entity.getInterestCategory().getId());
		}
		if (interestfrequency != null) {
			data.setInterestFrequency(interestfrequency.getValue());
		}
		data.setMinPeriod(entity.getMinPeriod());
		data.setMaxPeriod(entity.getMaxPeriod());
		data.setShortName(entity.getShortName());
		
		data.setNabardScheme(entity.getScheme().getName());
		data.setInterstSubsidy(entity.getInterestSubsidyGl());
		data.setReleaseDate(DateUtil.getDateString(entity.getReleaseDate()));
		data.setPrincipleSubsidy(entity.getPrincipleSubsidyGl());
		data.setInterestpaidGlCode(entity.getInterestpaidGl());
		data.setInterestpayableGlCode(entity.getInterestpayableGl());
		data.setInterstSubsidy(entity.getInterestSubsidyGl());
		
		data.setProductName(entity.getName());
		data.setGlCode(entity.getGlCode());
		data.setGlName(entity.getGlName());
		if (data.getInterestCategoryName() != null) {
			data.setInterestCategoryName(entity.getProductType().getId().toString());
		}
		
		data.setProductCode(entity.getCode());
		data.setInterestCategoryName(entity.getInterestCategory().getIntrCategoryDesc());
		data.setInterestCategoryId(entity.getInterestCategory().getId());
		data.setSchemeId(entity.getScheme().getId());
		
		data.setProductType(entity.getProductType().getDescription());
		data.setProductTypeId(entity.getProductType().getId());
		data.setBankInterestReceivableGL(entity.getBankInterestReceivableGL());
		data.setBankInterestReceivedGL(entity.getBankInterestReceivedGL());
		data.setBankPenalInterestReceivableGL(entity.getBankPenalInterestReceivableGL());
		data.setBankPenalInterestReceivedGL(entity.getBankPenalInterestReceivedGL());
		

	return data;
	}

	//Get Gorrowing Entity
	
	public static BorrowingProduct getBorrowingProduct(BorrowingProductData data)
	{
		BorrowingProduct entity=new BorrowingProduct();
		if(data.getId()!=null)
			entity.setId(data.getId());
		
		if(data.getProductName()!=null){
			entity.setName(data.getProductName());
		}
		entity.setCode(data.getProductCode());
		entity.setShortName(data.getShortName());
		entity.setGlName(data.getGlName());
		if(data.getInterestFrequency()!=null){
			String interestFreq=data.getInterestFrequency();
			entity.setInterestFrequency(InterestFrequency.getType(interestFreq));
		}
		entity.setReleaseDate(DateUtil.getVSoftDateByString(data.getReleaseDate()));
		if(data.getInterestCategoryId()!=null){
			InterestCategory interestcat=new InterestCategory();
			interestcat.setId(data.getInterestCategoryId());
			entity.setInterestCategory(interestcat);
		}
		if(data.getProductTypeId()!=null){
			ProductType productType=new ProductType();
			productType.setId(data.getProductTypeId());
			entity.setProductType(productType);
		}
		entity.setGlCode(data.getGlCode());;
		entity.setInterestpayableGl(data.getInterestpayableGlCode());
		Scheme scheme=new Scheme();
		scheme.setId(data.getSchemeId());
		entity.setScheme(scheme);
		entity.setMinPeriod(data.getMinPeriod());
		entity.setMaxPeriod(data.getMaxPeriod());
		entity.setInterestpaidGl(data.getInterestpaidGlCode());
		entity.setInterestSubsidyGl(data.getInterstSubsidy());
		entity.setPrincipleSubsidyGl(data.getPrincipleSubsidy());
		entity.setBankInterestReceivableGL(data.getBankInterestReceivableGL());
		entity.setBankInterestReceivedGL(data.getBankInterestReceivedGL());
		entity.setBankPenalInterestReceivableGL(data.getBankPenalInterestReceivableGL());
		entity.setBankPenalInterestReceivedGL(data.getBankPenalInterestReceivedGL());
		
		return entity;
	}
	
}
