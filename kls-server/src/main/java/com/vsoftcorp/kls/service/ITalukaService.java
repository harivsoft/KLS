package com.vsoftcorp.kls.service;


import java.util.List;

import com.vsoftcorp.kls.data.TalukaData;

public interface ITalukaService {

	public void saveTaluka(TalukaData theTalukaData);

	public void updateTaluka(TalukaData theTalukaData);
	
	public List<TalukaData> getAllTalukas();
}
