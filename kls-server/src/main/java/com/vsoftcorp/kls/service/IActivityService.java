package com.vsoftcorp.kls.service;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Activity;
import com.vsoftcorp.kls.data.ActivityData;
/***
 * 
 * @author a1565
 *
 */

public interface IActivityService {


	
	public void saveActivity(ActivityData data);
	
	public void updateActivity(ActivityData data);

	public List<ActivityData> getActivityList();

	public Activity getActivityById(Integer id);
	
	public void deleteActivityById(Integer id);

}
