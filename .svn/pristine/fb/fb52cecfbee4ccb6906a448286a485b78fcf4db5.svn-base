package com.vsoftcorp.kls.GepRep.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.GepRep.business.ReportParameters;
import com.vsoftcorp.kls.GepRep.business.ReportStructure;
import com.vsoftcorp.kls.GepRep.dao.IGenerateReportDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class GenerateReportDAO implements IGenerateReportDAO {
	
	private static final Logger logger = Logger.getLogger( GenerateReportDAO.class);

	@Override
	public List<ReportStructure> getAllReportStructure() {
		logger.info("Start: Fetching all the ReportStructure types master data from the database in getAllReportStructure() method.");
		List<ReportStructure> reportTypeMasterList = new ArrayList<ReportStructure>();
		try {
			/*EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
			EntityManager em = emf.createEntityManager();*/
			EntityManager em = EntityManagerUtil.getEntityManager();
			reportTypeMasterList = em.createQuery("SELECT v FROM ReportStructure v").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all structure Types from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all structure Types from the database",
					e.getCause());
		}
		logger.info("End: Fetching all the ReportStructure types master data from the database in getAllReportStructure() method.");
		return reportTypeMasterList;
		
	}


	@Override
	public List<ReportParameters> getAllReportParameterId(Integer reportId) {
		logger.info("Start: Fetching all the report parameter data from the database in getAllReportParameterId() method.");
		List<ReportParameters>  reportParametersList = new ArrayList<ReportParameters>();
		try {
			/*EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
			EntityManager em = emf.createEntityManager();*/
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT v FROM ReportParameters v where v.id=:reportId");
			query.setParameter("reportId", reportId);
			reportParametersList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all report parameter based on report id from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all report parameter based on report id from the database", e.getCause());
		}
		logger.info("End: Fetching all the report parameter data from the database in getAllReportParameterId() method.");
		return reportParametersList;
		
		
	}


	@Override
	public ReportStructure getReportStructureByReportId(Integer reportId) {
		ReportStructure reportStructure = new ReportStructure();
		logger.info("Start: Fetching the report Structure data from the database in getReportStructureByReportId() method.");
		try {
			/*EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
			EntityManager em = emf.createEntityManager();*/
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select r from ReportStructure r where r.id=:reportId");
			query.setParameter("reportId", reportId);
			reportStructure = (ReportStructure) query.getSingleResult();
		}catch(NoResultException ne) {
			reportStructure = null;
		}catch(Exception e) {
			logger.error("Error while retriving report structure based on report id from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving report structure based on report id from the database", e.getCause());
		}
		logger.info("End: Fetching the report structure data from the database in getReportStructureByReportId() method.");
		return reportStructure;
	}
	
	public static void main(String[] args) {
		
		
	}
	

}
