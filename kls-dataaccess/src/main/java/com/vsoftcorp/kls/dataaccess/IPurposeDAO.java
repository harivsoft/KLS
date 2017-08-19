package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Purpose;

/****
 * 
 * @author a1565
 *
 */
public interface IPurposeDAO {

	public void savePurpose(Purpose data);

	public void updatePurPose(Purpose data);

	public Purpose getPurposeById(Integer id);
	
	public void deletePurpose(Integer id);

	public List<Purpose> getAllPurpose();
}
