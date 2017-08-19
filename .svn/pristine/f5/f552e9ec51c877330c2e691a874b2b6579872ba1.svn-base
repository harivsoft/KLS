package com.vsoftcorp.kls.service.impl;

/**
 * @author a1565
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Activity;
import com.vsoftcorp.kls.data.ActivityData;
import com.vsoftcorp.kls.dataaccess.IActivityDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IActivityService;
import com.vsoftcorp.kls.service.helper.ActivityHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class ActivityService implements IActivityService {

	private static final Logger logger = Logger.getLogger(ActivityService.class);

	@Override
	public void saveActivity(ActivityData data) {
		// TODO Auto-generated method stub

		logger.info("Start: Calling  saveActivity() method .");
		Activity master = ActivityHelper.getActivity(data);
		IActivityDAO dao = KLSDataAccessFactory.getActivityDAO();
		try {
			if (data.getId() == null)
				dao.saveActivity(master);
			else
				logger.error("Activity already exists.Could not save.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to save Activity data ..in saveActivity() method..");
			throw new DataAccessException("Unable to save Activity data", e.getCause());
		}
		logger.info("End: Successfully Completed saving Activity data  in saveActivity() method .");

	}

	@Override
	public void updateActivity(ActivityData data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling  updateActivity() method .");
		Activity master = ActivityHelper.getActivity(data);
		IActivityDAO dao = KLSDataAccessFactory.getActivityDAO();
		try {
			Integer id = master.getId();
			if (id != null)
				dao.updateActivity(master);
			else
				logger.info("Unable to Update the Activity data as ID is :" + id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While updating Activity data ..in updateActivity() method..");
			throw new DataAccessException("Error While updating Activity data", e.getCause());
		}
		logger.info("End: Successfully Completed updating Activity data  in updateActivity() method .");
	}

	@Override
	public List<ActivityData> getActivityList() {
		// TODO Auto-generated method stub
		logger.info("Start: Calling  getAllActivity() method .");
		List<ActivityData> data = new ArrayList<ActivityData>();
		IActivityDAO dao = KLSDataAccessFactory.getActivityDAO();
		try {
			List<Activity> master = dao.getAllActivity(true);
			for (Activity Activity : master) {
				data.add(ActivityHelper.getActivityData(Activity));
			}
		} catch (Exception e) {
			logger.error("Error While getting all Activity data ..in getAllActivity() method..");
			throw new DataAccessException("Error While getting all Activity data", e.getCause());
		}
		logger.info("End: Successfully Completed getting all Activity data in  getAllActivity() method .");

		return data;

	}

	@Override
	public Activity getActivityById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteActivityById(Integer id) {
		// TODO Auto-generated method stub

	}

}
