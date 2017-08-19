/**
 * 
 */
package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Scheme;

/**
 * @author a9152
 *
 */
public interface ISchemeDAO {
	
	/**
	 * Saves the scheme data to the database.
	 * 
	 * @param scheme
	 */
	public void saveScheme(Scheme scheme);

	/**
	 * Updates the existing scheme data to the database.
	 * 
	 * @param scheme
	 */
	public void updateScheme(Scheme scheme);

	/**
	 * Checks if the scheme id exists in the database.
	 * 
	 * @param scheme
	 * @return Scheme
	 */
	public Scheme getScheme(Scheme scheme,boolean isCloseSession);

	/**
	 * Returns all the scheme records to the client.
	 * 
	 * @return
	 */
	
	public  Scheme getSchemeBasedOnProduct(Scheme scheme,boolean isCloseSession);
	/**
	 * Returns all the scheme records to the client Based On Product_Id.
	 * 
	 * @return
	 */
	
	public List<Scheme> getAllSchemes();
	
	/**
	 * Deletes the scheme data record from the database.
	 * 
	 * @param Scheme
	 */
	public void deleteSchemeRecord(Scheme scheme);
	
	public Scheme getScheme(Integer schemeId);


}
