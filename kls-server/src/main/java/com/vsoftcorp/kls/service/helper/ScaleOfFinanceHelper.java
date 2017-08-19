package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.business.entities.ScaleOfFinance;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.ScaleOfFinanceData;

/**
 * 
 * @author a9152
 * 
 */
public class ScaleOfFinanceHelper {

	/**
	 * This method will convert the Entity class to ScaleOfFinanceData.
	 * 
	 * @param master
	 * @return
	 */
	public static ScaleOfFinanceData getScaleOfFinanceData(ScaleOfFinance master) {

		ScaleOfFinanceData data = new ScaleOfFinanceData();
		data.setCropId(master.getCrop().getId());
		data.setLandTypeId(master.getLandType().getId());
		data.setLoanAmtPerAcre(MoneyUtil.getAmountRoundedValue(master.getLoanAmtPerAcre()).toString());
		data.setSeasonId(master.getSeason().getId().intValue());
		String areaType = master.getAreaType();
		if (areaType.equals("D")) {
			data.setAreaType("District");
		} else {
			data.setAreaType("Taluka");
		}
		data.setAreaTypeCode(master.getAreaTypeId());
		data.setId(master.getId());
		return data;
	}

	/**
	 * This method will convert the ScaleOfFinanceData to Entity class.
	 * 
	 * @param data
	 * @return
	 */
	public static ScaleOfFinance getScaleOfFinance(ScaleOfFinanceData data) {

		ScaleOfFinance master = new ScaleOfFinance();
		Crop crop = new Crop();
		crop.setId(data.getCropId());
		master.setCrop(crop);
		LandType landType = new LandType();
		landType.setId(data.getLandTypeId());
		master.setLandType(landType);
		String loanAmtPerAcre = data.getLoanAmtPerAcre();
		BigDecimal bigDecimal = new BigDecimal(loanAmtPerAcre);
		master.setLoanAmtPerAcre(MoneyUtil.getAmountRoundedValue(bigDecimal));
		Season season = new Season();
		season.setId(data.getSeasonId().longValue());
		master.setSeason(season);
		if (data.getAreaType().equals("District")) {
			master.setAreaType("D");
		} else {
			master.setAreaType("T");
		}
		master.setAreaTypeId(data.getAreaTypeCode());
		master.setId(data.getId());
		return master;
	}
}
