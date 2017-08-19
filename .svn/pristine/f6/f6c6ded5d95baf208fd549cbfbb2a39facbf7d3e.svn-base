package com.vsoftcorp.kls.dataaccess.subsidy.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.subsidy.SlabwisesubsidyInterestRate;
import com.vsoftcorp.kls.dataaccess.subsidy.ISlabwisesubsidyROIDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SlabwisesubsidyROIDAO implements ISlabwisesubsidyROIDAO {

	private static final Logger logger = Logger
			.getLogger(SlabwisesubsidyROIDAO.class);

	@Override
	public void saveSlabwisesubsidyROIDAO(SlabwisesubsidyInterestRate master) {

		logger.info("Start: Saving slab wise subsidy ROI to the database in saveSlabwisesubsidyROIDAO() method.");
		boolean flgTrans = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			em.persist(master);
			if (flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the slab wise subsidy ROI"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the slab wise subsidy ROI data to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully saved slab wise subsidy ROI to the database in saveSlabwisesubsidyROIDAO() method.");
	}

	@Override
	public void modifySlabwisesubsidyROIDAO(SlabwisesubsidyInterestRate master) {
		
		logger.info("Start: Modifing slab wise subsidy ROI to the database in modifySlabwisesubsidyROIDAO() method.");
		boolean flgTrans = false;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			em.merge(master);
			if (flgTrans)
				em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of modifing the slab wise subsidy ROI"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of modifing the slab wise subsidy ROI data to the database.",
					excp.getCause());
		}
		logger.info("End: Successfully modified slab wise subsidy ROI to the database in modifySlabwisesubsidyROIDAO() method.");
	}

	@Override
	public List<SlabwisesubsidyInterestRate> getSlabwisesubsidyROIDAO(Long interestSubsidyId) {
		
		logger.info("Start: Fetching the slab wise subsidy ROI data from the database in getSlabwisesubsidyROIDAO() method.");
		List<SlabwisesubsidyInterestRate> slabwisesubsidyInterestRate = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("select s from SlabwisesubsidyInterestRate s where s.interestSubsidy.id="+interestSubsidyId);
			slabwisesubsidyInterestRate = query.getResultList();
		}catch(NoResultException e){
			logger.error("No records found with this interest subsidy id"+interestSubsidyId);
			slabwisesubsidyInterestRate = null;
			
		}catch (Exception excp) {
			logger.error("Could not fetch the slab wise subsidy ROI data from the "
					+ "database using interestSubsidyId Exception thrown.");
			throw new DataAccessException("Could not fetch the slab wise subsidy ROI data from the database.",
					excp.getCause());
		}
		logger.info("End: Successfully fetched the slab wise subsidy ROI data from the database in getSlabwisesubsidyROIDAO() method.");
		return slabwisesubsidyInterestRate;
	}
	
	@Override
	public void removeSlabwiseSubsidyROI(SlabwisesubsidyInterestRate master){
		
		logger.info("START: deleting SlabwisesubsidyInterestRate in removeSlabwiseSubsidyROI()");
		boolean flgTrans = false;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
				flgTrans = true;
			}
			Query query = em.createQuery("delete from SlabwisesubsidyInterestRate where id="+master.getId());
			query.executeUpdate();
			if (flgTrans)
				em.getTransaction().commit();

		} catch (Exception excp) {
			excp.printStackTrace();
			if (flgTrans && em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.error("Could not commit the transaction of deleting SlabwisesubsidyInterestRate");
			throw new DataAccessException("Could not commit the transaction of deleting SlabwisesubsidyInterestRate to the database.",
					excp.getCause());
		}
		logger.info("END: deleting SlabwisesubsidyInterestRate in removeSlabwiseSubsidyROI()");
		
	}

}
