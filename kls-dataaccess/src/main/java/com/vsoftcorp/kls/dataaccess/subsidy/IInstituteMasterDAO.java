package com.vsoftcorp.kls.dataaccess.subsidy;

import java.util.List;

import com.vsoftcorp.kls.business.entity.subsidy.InstituteMaster;

public interface IInstituteMasterDAO {

	public void savesInstituteMasterDAO(InstituteMaster master);

	public void modifiesInstituteMasterDAO(InstituteMaster master);

	public InstituteMaster getInstituteMasterDAO(Long id);
	
	public List<InstituteMaster> getAllInstituteMasterDAO();


}
