package com.vsoftcorp.kls.service.helper;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.InterestSlab;
import com.vsoftcorp.kls.data.SlabwiseInterestRateData;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;

/**
 * 
 * @author a9152
 * 
 */
public class SlabwiseInterestRateHelper {

	/**
	 * This method will convert the Entity class to SlabwiseInterestRateData.
	 * 
	 * @param master
	 * @return
	 */
	public static SlabwiseInterestRateData getSlabwiseInterestRateData(
			SlabwiseInterestRate master) {

		SlabwiseInterestRateData data = new SlabwiseInterestRateData();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String effectiveDate = formatter.format(master.getEffectiveDate());
		
		data.setInterestCategoryId(master.getInterestCategory().getId());
		data.setEffectiveDate(effectiveDate);
		List<InterestSlab> interestSlabsList = new ArrayList<InterestSlab>();
		InterestSlab interestSlab = new InterestSlab();
		interestSlab.setFromPeriod(master.getFromPeriod());
		interestSlab.setFromSlab(MoneyUtil.getAmountRoundedValue(master.getFromSlab().getAmount()));
		interestSlab.setPenalRoi(master.getPenalRoi());
		interestSlab.setRoi(master.getRoi());
		interestSlab.setToPeriod(master.getToPeriod());
		interestSlab.setToSlab(MoneyUtil.getAmountRoundedValue(master.getToSlab().getAmount()));
		interestSlab.setId(master.getSlabwiseInterestRateId());
		interestSlabsList.add(interestSlab);
		data.setInterestSlabs(interestSlabsList);
		return data;
	}

	/**
	 * This method will convert the SlabwiseInterestMasterData to Entity class.
	 * 
	 * @param data
	 * @return
	 */
	public static List<SlabwiseInterestRate> getSlabwiseInterestRate(
			SlabwiseInterestRateData data) {

		List<SlabwiseInterestRate> slabwiseInterestRateMasterList = new ArrayList<SlabwiseInterestRate>();
		for (InterestSlab interestSlab : data.getInterestSlabs()) {
			SlabwiseInterestRate master = new SlabwiseInterestRate();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			formatter.setLenient(false);
			try {
				java.util.Date utilDate = formatter.parse(data
						.getEffectiveDate());
				master.setEffectiveDate(new Date(utilDate.getTime()));
			} catch (ParseException e) {
				throw new KlsRuntimeException(
						" Enter the valid date only in dd/MM/yyyy format.",
						e.getCause());
			}
			InterestCategory interestCategory=new InterestCategory();
			interestCategory.setId(data.getInterestCategoryId());
			master.setInterestCategory(interestCategory);
			master.setFromPeriod(interestSlab.getFromPeriod());
			master.setFromSlab(Money.valueOf(Currency.getInstance("INR"),interestSlab.getFromSlab()));
			master.setPenalRoi(interestSlab.getPenalRoi());
			master.setRoi(interestSlab.getRoi());
			master.setToPeriod(interestSlab.getToPeriod());
			master.setToSlab(Money.valueOf(Currency.getInstance("INR"),interestSlab.getToSlab()));
			
			master.setSlabwiseInterestRateId(interestSlab.getId());
			slabwiseInterestRateMasterList.add(master);
		}
		return slabwiseInterestRateMasterList;
	}

	public static SlabwiseInterestRateData getSlabwiseInterestRateData(
			List<SlabwiseInterestRate> slabwiseIntMasterList) {

		SlabwiseInterestRateData data = new SlabwiseInterestRateData();
		List<InterestSlab> interestSlabsList = new ArrayList<InterestSlab>();
		for (SlabwiseInterestRate master : slabwiseIntMasterList) {
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String effectiveDate = formatter.format(master.getEffectiveDate());
			data.setInterestCategoryId(master.getInterestCategory().getId());
			data.setEffectiveDate(effectiveDate);
			InterestSlab interestSlab = new InterestSlab();
			interestSlab.setFromPeriod(master.getFromPeriod());
			interestSlab.setFromSlab(MoneyUtil.getAmountRoundedValue(master.getFromSlab().getAmount()));
			interestSlab.setPenalRoi(master.getPenalRoi());
			interestSlab.setRoi(master.getRoi());
			interestSlab.setToPeriod(master.getToPeriod());
			interestSlab.setToSlab(MoneyUtil.getAmountRoundedValue(master.getToSlab().getAmount()));
			interestSlab.setId(master.getSlabwiseInterestRateId());
			interestSlabsList.add(interestSlab);
		}
		data.setInterestSlabs(interestSlabsList);
		return data;
	}
}
