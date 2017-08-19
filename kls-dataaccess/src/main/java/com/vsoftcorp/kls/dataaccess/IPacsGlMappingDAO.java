package com.vsoftcorp.kls.dataaccess;


import com.vsoftcorp.kls.business.entities.PacsGlMapping;

/**
 * 
 * @author udanaiah
 *
 */

public interface IPacsGlMappingDAO {
	
	public void savePacsGlMapping(PacsGlMapping pacsGlMapping); 
	
	public void updatePacsGlMapping(PacsGlMapping pacsGlmapping);

	public PacsGlMapping getPacsGlMapping(Integer productId,Integer pacsId);
	

}
