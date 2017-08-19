package com.vsoftcorp.kls.service;
/**
 * @author a1565
 */

import java.util.List;

import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.data.PurposeData;

public interface IPurposeService {

	public void savePurpose(PurposeData data);
	public void updatePurpose(PurposeData data);
	public List<PurposeData> getAllPurpose();
	public Purpose getPurposeById(Integer id);
	public void deletePurpose(Integer id);
		

}
