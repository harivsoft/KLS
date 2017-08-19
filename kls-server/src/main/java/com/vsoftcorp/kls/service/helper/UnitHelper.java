package com.vsoftcorp.kls.service.helper;
/**
 * @a1565
 */
import com.vsoftcorp.kls.business.entities.Unit;
import com.vsoftcorp.kls.data.UnitData;

public class UnitHelper {
	public static UnitData getUnitData(Unit master) {
		// TODO Auto-generated method stub

		UnitData data=new UnitData();
		data.setId(master.getId());
		data.setName(master.getName());
		data.setRemarks(master.getRemarks());
		return data;
	}

	public static Unit  getUnit(UnitData data) {
		Unit master=new Unit();
		if(data.getId()!=null)
			master.setId(data.getId());
		master.setName(data.getName());
		master.setRemarks(data.getRemarks());
		return master;
	}
}
