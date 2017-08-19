/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Calendar;
import com.vsoftcorp.kls.business.entities.CalendarSetup;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.ICalendarDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author a1621
 *
 */
public class CalendarDAO implements ICalendarDAO{
	private static final Logger logger = Logger
			.getLogger(CalendarDAO.class);
	@Override
	public List<Calendar> getCalendar(Integer pacsId, String businessDate) {
		logger.info("Start: Calling getCalendarSetup() method in CalendarSetupDAO ..!");
		List<Calendar> data = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		CalendarSetup master = null;
		try {
			Query query1 = em
					.createQuery(
							"SELECT t FROM CalendarSetup t where t.pacsId = :pacsId",
							CalendarSetup.class)
					.setParameter("pacsId", pacsId);
			if(!query1.getResultList().isEmpty()){
				master = (CalendarSetup) query1.getSingleResult();
			}
			Query query = em
					.createQuery(
							"SELECT t FROM Calendar t where t.date >= :startDate and t.date <= :endDate and t.setupId = :setupId",
							Calendar.class)
					.setParameter("startDate", DateUtil.getVSoftDateByString(DateUtil.getDateBySubtractingNoOfDays(businessDate,45)))
					.setParameter("endDate", DateUtil.getVSoftDateByString(DateUtil.getDateByAddingNoOfDays(businessDate,45)))
					.setParameter("setupId",master);
			if(!query.getResultList().isEmpty()){
			data =  query.getResultList();
			}
			if (data == null) {
				data = new ArrayList<Calendar>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to commit the transaction of CalendarSetup data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of setup the CalendarSetup data.",
					e.getCause());
		} /*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End: Calling getCalendarSetup() method in CalendarSetupDAO ..!");
		return data;
	}
	@Override
	public void newCalendar(List<Long> deleteList, List<Calendar> calendars, Integer pacsId) {
		logger.info("Start: Calling newCalendar() method in CalendarDAO ..!");
		CalendarSetup master = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em
					.createQuery(
							"SELECT t FROM CalendarSetup t where t.pacsId = :pacsId",
							CalendarSetup.class)
					.setParameter("pacsId", pacsId);
			if(!query.getResultList().isEmpty()){
			master = (CalendarSetup) query.getSingleResult();
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
			for (Long deleteId : deleteList){
				try{
					Calendar calendar = em.find(Calendar.class, Integer.parseInt(deleteId.toString()));
					em.remove(calendar);
				} catch (Exception exception){
					exception.printStackTrace();
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to commit the transaction of newCalendar data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of setup the newCalendar data.",
					e.getCause());
		} /*finally {
			EntityManagerUtil.closeSession();
		}*/

		logger.info("End: Calling newCalendar() method in CalendarDAO ..!");
		
	}
	@Override
	public Calendar getCalendarForDate(Integer pacsId, String date) {
		logger.info("Start: Calling getCalendarSetup() method in CalendarSetupDAO ..!");
		Calendar data = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		CalendarSetup master = null;
		try {
			Query query1 = em
					.createQuery(
							"SELECT t FROM CalendarSetup t where t.pacsId = :pacsId",
							CalendarSetup.class)
					.setParameter("pacsId", pacsId);
			if(!query1.getResultList().isEmpty()){
				master = (CalendarSetup) query1.getSingleResult();
			}
			Query query = em
					.createQuery(
							"SELECT t FROM Calendar t where t.date = :date and t.setupId = :setupId",
							Calendar.class)
					.setParameter("date", DateUtil.getVSoftDateByString(date))
					.setParameter("setupId",master);
			data =  (Calendar) query.getSingleResult();
			
			
		} catch(NoResultException e){
			data = new Calendar();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to commit the transaction of CalendarSetup data in database "
					+ ".Exception thrown.");
/*			throw new DataAccessException(
					"Unable to commit the transaction of setup the CalendarSetup data.",
					e.getCause());
*/		} /*finally {
			EntityManagerUtil.closeSession();
		}*/
		logger.info("End: Calling getCalendarSetup() method in CalendarSetupDAO ..!");
		return data;
	}

}
