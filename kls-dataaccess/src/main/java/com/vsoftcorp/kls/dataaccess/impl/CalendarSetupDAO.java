/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Calendar;
import com.vsoftcorp.kls.business.entities.CalendarSetup;
import com.vsoftcorp.kls.dataaccess.ICalendarSetupDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author a1621
 * 
 */
public class CalendarSetupDAO implements ICalendarSetupDAO {
	private static final Logger logger = Logger.getLogger(CalendarSetupDAO.class);

	@Override
	public void setupCalendar(CalendarSetup data, List<Calendar> calendars) {
		logger.info("Start: Calling setupCalendar() method in CalendarSetupDAO ..!");
		CalendarSetup master = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("SELECT t FROM CalendarSetup t where t.pacsId = :pacsId", CalendarSetup.class).setParameter("pacsId",
					data.getPacsId());
			if (!query.getResultList().isEmpty()) {
				master = (CalendarSetup) query.getSingleResult();
			}
			if (master == null) {
				master = new CalendarSetup();
				master.setPacsId(data.getPacsId());
				master.setBranchId(data.getBranchId());
				master.setLastDate(data.getLastDate());
				master.setWeeklyOff(data.getWeeklyOff());
				em.persist(master);
			} else {
				master.setPacsId(data.getPacsId());
				master.setBranchId(data.getBranchId());
				master.setLastDate(data.getLastDate());
				master.setWeeklyOff(data.getWeeklyOff());
			}

			for (Calendar calendar : calendars) {
				Calendar calendar2 = new Calendar();

				CalendarSetup setup = new CalendarSetup();
				setup.setId(master.getId());

				calendar2.setSetupId(setup);
				calendar2.setDate(calendar.getDate());
				calendar2.setDescription(calendar.getDescription());
				calendar2.setTitle(calendar.getTitle());
				calendar2.setType(calendar.getType());

				em.persist(calendar2);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to commit the transaction of CalendarSetup data in database " + ".Exception thrown.");
			throw new DataAccessException("Unable to commit the transaction of setup the CalendarSetup data.", e.getCause());
		} /*
		 * finally { EntityManagerUtil.closeSession(); }
		 */

		logger.info("End: Calling setupCalendar() method in CalendarSetupDAO ..!");
	}

	@Override
	public CalendarSetup getCalendarSetup(Integer pacsId) {
		logger.info("Start: Calling getCalendarSetup() method in CalendarSetupDAO ..!");
		CalendarSetup data = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			Query query = em.createQuery("SELECT t FROM CalendarSetup t where t.pacsId = :pacsId", CalendarSetup.class)
					.setParameter("pacsId", pacsId);
			if (!query.getResultList().isEmpty()) {
				data = (CalendarSetup) query.getSingleResult();
			}
			if (data == null) {
				data = new CalendarSetup();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to commit the transaction of CalendarSetup data in database " + ".Exception thrown.");
			throw new DataAccessException("Unable to commit the transaction of setup the CalendarSetup data.", e.getCause());
		} /*
		 * finally { EntityManagerUtil.closeSession(); }
		 */
		logger.info("End: Calling getCalendarSetup() method in CalendarSetupDAO ..!");
		return data;
	}

}
