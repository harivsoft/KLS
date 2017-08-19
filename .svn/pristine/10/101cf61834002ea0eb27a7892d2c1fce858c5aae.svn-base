package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.entities.SeasonParameter;
import com.vsoftcorp.kls.data.SeasonParameterData;

/**
 * 
 * @author a1605
 * 
 */
public class SeasonParameterHelper {

	public static SeasonParameter getSeasonParamter(SeasonParameterData seasonParameterData) {

		SeasonParameter seasonParameter = new SeasonParameter();
		if (seasonParameterData.getId() != null)
			seasonParameter.setId(Integer.parseInt(seasonParameterData.getId()));
		seasonParameter.setInsuranceByFarmer(BigDecimal.valueOf(Double.parseDouble(seasonParameterData.getInsuranceByFarmer())));
		seasonParameter.setInsuranceSubsidy(BigDecimal.valueOf(Double.parseDouble(seasonParameterData.getInsuranceSubsidy())));
		Crop crop = new Crop();
		crop.setId(Integer.parseInt(seasonParameterData.getCropId()));
		crop.setName(seasonParameterData.getCropName());
		seasonParameter.setCrop(crop);
		Season season = new Season();
		season.setId(Long.parseLong(seasonParameterData.getSeasonId()));
		season.setName(seasonParameterData.getSeasonName());
		seasonParameter.setSeason(season);
		if(seasonParameterData.getInsuranceGL()!=null)
		seasonParameter.setInsuranceGL(seasonParameterData.getInsuranceGL());

		if(seasonParameterData.getUserDetails().getPacsId()!=null){
		Pacs pacs = new Pacs();
		pacs.setId(Integer.parseInt(seasonParameterData.getUserDetails().getPacsId()));
		seasonParameter.setPacs(pacs);
		}
		if(seasonParameterData.getInsuranceCutoffPeriod()!=null)
			seasonParameter.setInsurancePeriod(Integer.parseInt(seasonParameterData.getInsuranceCutoffPeriod()));
		return seasonParameter;
	}

	public static SeasonParameterData getSeasonParamterData(SeasonParameter seasonParameter) {

		SeasonParameterData seasonParameterData = new SeasonParameterData();
		if (seasonParameter.getId() != null)
			seasonParameterData.setId(seasonParameter.getId().toString());
		seasonParameterData.setInsuranceByFarmer(seasonParameter.getInsuranceByFarmer().toString());
		seasonParameterData.setInsuranceSubsidy(seasonParameter.getInsuranceSubsidy().toString());
		seasonParameterData.setCropId(seasonParameter.getCrop().getId().toString());
		seasonParameterData.setCropName(seasonParameter.getCrop().getName());
		seasonParameterData.setSeasonId(seasonParameter.getSeason().getId().toString());
		seasonParameterData.setSeasonName(seasonParameter.getSeason().getName());
		if(seasonParameter.getInsuranceGL()!=null)
		seasonParameterData.setInsuranceGL(seasonParameter.getInsuranceGL());
		if(seasonParameter.getInsurancePeriod()!=null)
		seasonParameterData.setInsuranceCutoffPeriod(seasonParameter.getInsurancePeriod().toString());
		return seasonParameterData;
	}

}
