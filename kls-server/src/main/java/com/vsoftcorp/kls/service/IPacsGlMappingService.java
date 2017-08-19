package com.vsoftcorp.kls.service;

import com.vsoftcorp.kls.business.entities.PacsGlMapping;
import com.vsoftcorp.kls.data.PacsGlMappingData;

public interface IPacsGlMappingService {
	

	public void savePacsGlMappingData(PacsGlMappingData pacsGlMappingData); 
	
	public void updatePacsGlMappingData(PacsGlMappingData pacsGlmappingData);

	public PacsGlMappingData getPacsGlMapping(Integer productId, Integer pacsId);
	
	public PacsGlMapping getAllPacsGlMapping();

}
