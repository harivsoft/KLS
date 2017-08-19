package com.vsoftcorp.kls.service;
/***
 * @author a1565
 */
import java.util.List;

import com.vsoftcorp.kls.business.entities.SubPurpose;
import com.vsoftcorp.kls.data.SubPurposeData;

public interface ISubPurposeService {


	public void saveSubPurpose(SubPurposeData data);
	public void updateSubPurpose(SubPurposeData data);
	public List<SubPurposeData> getAllSubPurpose();
	public SubPurpose getSubPurposeById(Integer id);
	public void deleteSubPurpose(Integer id);
		
}
