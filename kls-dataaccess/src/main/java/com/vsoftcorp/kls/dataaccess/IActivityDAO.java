package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Activity;

/***
 * 
 * @author a1565
 * 
 */
public interface IActivityDAO {

	public void saveActivity(Activity data);

	public void updateActivity(Activity data);

	public Activity getActivityById(Integer id, boolean isCloseSession);

	public void deleteActivity(Integer id);

	public List<Activity> getAllActivity(boolean isCloseSession);

}
