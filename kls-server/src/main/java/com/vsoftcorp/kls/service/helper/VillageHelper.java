package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.data.VillageData;

/**
 * Helper class to wrap VillageMasterData to Entity and vice-versa.
 * 
 * @author a9152
 * 
 */
public class VillageHelper {

	/**
	 * This method will convert the Entity class to VillageData.
	 * 
	 * @param master
	 * @return
	 */
	public static VillageData getVillageData(Village master) {

		VillageData data = new VillageData();
		data.setId(master.getId());
		data.setName(master.getName());
		data.setTalukaId(master.getTaluka().getId());
		data.setPin(master.getPin());
		return data;
	}

	/**
	 * This method will convert the VillageData to Entity class.
	 * 
	 * @param data
	 * @return
	 */
	public static Village getVillage(VillageData data) {

		Village master = new Village();
		master.setId(data.getId());
		master.setName(data.getName());
		Taluka taluka = new Taluka();
		taluka.setId(data.getTalukaId());
		master.setTaluka(taluka);
		master.setPin(data.getPin());
		return master;
	}
}
