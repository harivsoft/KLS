package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Taluka;

/**
 * 
 * @author a9153
 *
 */

public interface ITalukaDAO {

	public void saveTaluka(Taluka taluka);

	public void updateTaluka(Taluka taluka);
	
	public Taluka getTaluka(Taluka taluka);
	
	public List<Taluka> getAllTalukas();

}
