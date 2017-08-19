package com.vsoftcorp.kls.service;


import java.util.List;

import com.vsoftcorp.kls.data.BranchData;
import com.vsoftcorp.kls.data.PacsData;

public interface IPacsService {

	public void savePacs(PacsData thePacsData);

	public void updatePacs(PacsData thePacsData);
	
	public List<PacsData> getAllPacs();

	public List<PacsData> getAllPacsByBranch(Integer branchId);

	public PacsData getPacsByPacId(Integer pacsId);
	
	public BranchData getBranchByPacId(Integer pacsId);
}
