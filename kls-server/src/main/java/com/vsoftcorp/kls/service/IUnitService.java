package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Unit;
import com.vsoftcorp.kls.data.UnitData;

public interface IUnitService {
	public void saveUnit(UnitData data);
	public void updateUnit(UnitData data);
	public List<UnitData> getAllUnit();
	public Unit getUnitById(Integer id);
	public void deleteUnit(Integer id);
}
