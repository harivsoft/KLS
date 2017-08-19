package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Pacs;

/**
 * 
 * @author a9153
 * 
 */

public interface IPacsDAO {

	public void savePacs(Pacs pacs);

	public void updatePacs(Pacs pacs);

	public Pacs getPacs(Pacs pacs);
	
	public Pacs getPacs(Pacs pacs,boolean isCloseSession);
	
	public Pacs getPacs(Integer pacsId);

	public List<Pacs> getAllPacs();

	public List<Pacs> getAllPacsByBranch(Integer branchId);

	public Pacs getPacsByPacId(Integer pacsId);
	
	public List<Pacs> getClosedOfflinePacs();

	public List<Pacs> getSycedPacs();
	
	public void updatePacsStatus(List<Integer> pacsIdsList);

}
