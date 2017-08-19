package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.SubPurpose;
import com.vsoftcorp.kls.data.SubPurposeData;

/**
 *  @author a1565
 */
public class SubPurposeHelper {

	/**
	 * @param args
	 */
	public static SubPurposeData getSubPurposeData(SubPurpose master) {
		// TODO Auto-generated method stub

		SubPurposeData data=new SubPurposeData();
		data.setId(master.getId());
		data.setName(master.getName());
		data.setRemarks(master.getRemarks());
		return data;
	}

	public static SubPurpose  getSubPurpose(SubPurposeData data) {
		SubPurpose master=new SubPurpose();
		if(data.getId()!=null)
			master.setId(data.getId());
		master.setName(data.getName());
		master.setRemarks(data.getRemarks());
		return master;
	}
}
