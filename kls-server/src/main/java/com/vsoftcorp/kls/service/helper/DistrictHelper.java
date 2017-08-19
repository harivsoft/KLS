package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.data.DistrictData;

public class DistrictHelper {

	/**
	 * 
	 * @param district
	 * @return
	 */
	public static DistrictData getDistrictData(
			District district) {
		DistrictData data = new DistrictData();
		data.setId(district.getId().toString());
		data.setName(district.getName());
		data.setDccBankCode(district.getDccBankCode());
		return data;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static District getDistrict(DistrictData data) {

		District district = new District();
		if (data.getId() != null)
			district.setId(Integer.parseInt(data.getId()));
		district.setName(data.getName());
		String dccBankCode = data.getDccBankCode();
		if (dccBankCode == null) {
			district.setDccBankCode("B01");
		} else {
			district.setDccBankCode(dccBankCode);
		}
		return district;
	}
} 
