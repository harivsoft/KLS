package com.vsoftcorp.kls.dataaccess;
/***
 * @a1565
 */
import java.util.List;

import com.vsoftcorp.kls.business.entities.Unit;


public interface IUnitDAO {
	public void saveUnit(Unit data);

	public void updateUnit(Unit data);

	public Unit getUnitById(Integer id);
	
	public void deleteUnit(Integer id);

	public List<Unit> getAllUnit();
}
