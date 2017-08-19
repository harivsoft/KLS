package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.data.TalukaData;

/**
 * @author a9153
 *
 *Helper Class for conversion from pojo to entity and vice versa.
 */

public class TalukaHelper {

	/**
	 * The method converts taluka etity to taluka pojo.
	 * @param theMaster
	 * @return
	 */
	public static TalukaData getTalukaData(Taluka theMaster) {
		TalukaData data = new TalukaData();
		data.setId(theMaster.getId().toString());
		data.setName(theMaster.getName());
		data.setDistrictId(theMaster.getDistrict().getId());
		return data;
	}

	/**
	 * This method converts taluka pojo and taluka entity.
	 * @param data
	 * @return
	 */
	public static Taluka getTaluka(TalukaData data) {
		Taluka master = new Taluka();
		if (data.getId() != null)
			master.setId(Integer.parseInt(data.getId()));
		master.setName(data.getName());
		District dist = new District();
		dist.setId(data.getDistrictId());
		master.setDistrict(dist);
		return master;
	}
}
