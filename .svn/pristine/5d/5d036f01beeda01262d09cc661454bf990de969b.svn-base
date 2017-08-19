package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.AutoGenerationReportDef;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.PacsGLExtract;
import com.vsoftcorp.kls.business.entities.PacsRepotsLog;
import com.vsoftcorp.kls.dataaccess.IDayBeginDao;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.VoucherResetFrequency;
import com.vsoftcorp.time.Date;

public class DayBeginDAO implements IDayBeginDao {
	private static final Logger logger = Logger.getLogger(DayBeginDAO.class);

	@Override
	public void updateDayBegin(BankParameter bankParameter, Date nextBusinessDate) {

		logger.info("Start: Updating the day begin data to the database in updateDayBegin() method==.");
		BankParameter master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer id = bankParameter.getId();
			if (id != null) {
				master = em.find(BankParameter.class, id);
				if (master != null) {
					// em.getTransaction().begin();
					master.setBankProcessStatus(bankParameter.getBankProcessStatus());
					if (bankParameter.getBankProcessStatus().getValue() == 3) {
						// master.setBusinessDate(master.getBusinessDate().next());
						master.setBusinessDate(nextBusinessDate);
					}

					em.merge(master);
					// em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the day begin data.");
			throw new DataAccessException("Could not commit the transaction of updating the day begin data.", excp.getCause());
		}
		if (master == null) {
			logger.error("Trying to update the day begin record which does not exist.");
			throw new DataAccessException("Trying to update the day begin record which does not exist.");
		}
		logger.info("End: Successfully updated the day begin data to the database in updateDayBegin() method.");
	}

	@Override
	public void resetVoucherNumbers(String eventType,List<Integer> pacsIdsList) {
		logger.info("Start: Resetting voucher numbers eventType:  " + eventType);
		try {

			List<VoucherResetFrequency> voucherResetFreq = new ArrayList<VoucherResetFrequency>();
			if (eventType.equals("DayEnd")) {
				voucherResetFreq.add(VoucherResetFrequency.DAILY);
			 }if (eventType.equals("WeeklyOff")){
				 voucherResetFreq.add(VoucherResetFrequency.WEEKLY);
			} else if (eventType.equals("MonthEnd")) {
				voucherResetFreq.add(VoucherResetFrequency.DAILY);
				voucherResetFreq.add(VoucherResetFrequency.MONTHLY);
			} else if (eventType.equals("QuarterEnd")) {
				voucherResetFreq.add(VoucherResetFrequency.DAILY);
				voucherResetFreq.add(VoucherResetFrequency.MONTHLY);
				voucherResetFreq.add(VoucherResetFrequency.QUARTERLY);
			} else if (eventType.equals("HalfYearEnd")) {
				voucherResetFreq.add(VoucherResetFrequency.DAILY);
				voucherResetFreq.add(VoucherResetFrequency.MONTHLY);
				voucherResetFreq.add(VoucherResetFrequency.QUARTERLY);
				voucherResetFreq.add(VoucherResetFrequency.HALF_YEARLY);
			} else if (eventType.equals("YearEnd")) {
				voucherResetFreq.add(VoucherResetFrequency.DAILY);
				voucherResetFreq.add(VoucherResetFrequency.MONTHLY);
				voucherResetFreq.add(VoucherResetFrequency.QUARTERLY);
				voucherResetFreq.add(VoucherResetFrequency.HALF_YEARLY);
				voucherResetFreq.add(VoucherResetFrequency.YEARLY);
			}
			else if (eventType.equals("Holiday")) {
					voucherResetFreq.add(VoucherResetFrequency.DAILY);
				 }
			EntityManager em = EntityManagerUtil.getEntityManager();
			boolean flgTrans = false;
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			Query query = em
					.createQuery("UPDATE SBNextNumbers sb SET sb.lastUsedNumber =:ZERO where pacsId in (select p.id from Pacs p where p.voucherResetFrequency in (:voucherResetFrequency) and p.id in (:pacsIdsList))");
			query.setParameter("ZERO", 0);
			query.setParameter("pacsIdsList", pacsIdsList);
			query.setParameter("voucherResetFrequency", voucherResetFreq);
			int count = query.executeUpdate();
			logger.info(count + " records voucher number resetted");
			if(flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error: while  Resetting voucher numbers");
			throw new DataAccessException("Could not reset voucher numbers", excp.getCause());
		}
		logger.info("End: successfully reset voucher numbers: ");
	}

	@Override
	public List<AutoGenerationReportDef> getAutoGenerationReportValues(String eventType){
		logger.info("Start: Saving the pacsGLExtract  data to the database in savePacsGLExtract() method.");
		//AutoGenerationReportDef autoGenRep=new AutoGenerationReportDef();
		List<AutoGenerationReportDef> autoGenRepList=new ArrayList<AutoGenerationReportDef>();
		try {

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
			EntityManager em = emf.createEntityManager();
			logger.info("Got connection" + em);
			String agriYesNo="Y";
			em.getTransaction().begin();
			logger.info("testing=====");
			String str="select a from AutoGenerationReportDef a where a.agriYesNo=:agriYesNo  and a.event=:eventType";
			logger.info("query===="+str);
		    Query qry = em.createQuery(str.toString());
		   qry.setParameter("agriYesNo", agriYesNo);
		    qry.setParameter("eventType", eventType);
		    autoGenRepList = (List<AutoGenerationReportDef>)qry.getResultList();
		    logger.info("after executing query==");
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the pacsGLExtract  " + "data to the database.Exception thrown."+excp.getCause());
			throw new DataAccessException("Could not commit the transaction of saving the pacsGLExtract  data to the database.", excp.getCause());
		}
		logger.info("End: Successfully saved the pacsGLExtract  to the database in savePacsGLExtract() method.");
		return autoGenRepList;
	}

@Override
public void saveAutoGeneratedReportLog(List<PacsRepotsLog> pacsReportLogList){
	logger.info("Start: Saving the saveAutoGeneratedReportLog  data to the database in savePacsGLExtract() method.");
	try {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		logger.info("after getting connection");
		for(PacsRepotsLog pacsReportLog:pacsReportLogList){
			logger.info("inside for loop"+pacsReportLog.getReportName());
			logger.info("checking id value==="+pacsReportLog.getId());
			em.merge(pacsReportLog);
			logger.info("after persisting dat"+pacsReportLog.getId());
		}
	} catch (Exception excp) {
		excp.printStackTrace();
		logger.error("Could not commit the transaction of saving the pacsGLExtract  " + "data to the database.Exception thrown."+excp.getCause());
		throw new DataAccessException("Could not commit the transaction of saving the pacsGLExtract  data to the database.", excp.getCause());
	}
	logger.info("End: Successfully saved the pacsGLExtract  to the database in savePacsGLExtract() method.");
}
	
}
