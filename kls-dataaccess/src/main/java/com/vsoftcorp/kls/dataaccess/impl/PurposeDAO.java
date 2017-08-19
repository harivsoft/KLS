package com.vsoftcorp.kls.dataaccess.impl;
/**
 * @author a1565
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.dataaccess.IPurposeDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class PurposeDAO implements IPurposeDAO{
	private static final Logger logger = Logger.getLogger(PurposeDAO.class);
	@Override
	public void savePurpose(Purpose data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling savePurpose() method in PurposeDAO ..!");
		try
		{
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(data);
			em.getTransaction().commit();
		}
		catch (Exception excp)
		{
			logger.error("Unable to commit the transaction of purpose data in database " +
					".Exception thrown.");
			throw new DataAccessException("Unable to commit the transaction of saving the purpose data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed saving the Purpsoe data in savePurpose()");
	}

	@Override
	public void updatePurPose(Purpose data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling updatePurPose() method in PurposeDAO ..!");
		Purpose master=null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			
			Integer id = data.getId();
			if (id != null) {
					em.getTransaction().begin();
					master = em.find(Purpose.class,id);
					master.setName(data.getName());
					master.setActivityRequired(data.getActivityRequired());
					master.setMasterAppReq(data.getMasterAppReq());
					master.setSubPurposeReq(data.getSubPurposeReq());
					master.setRemarks(data.getRemarks());
					em.getTransaction().commit();
			}else
				{
					logger.error("Trying to update the purpose record which does not exist in the database.");
					throw new DataAccessException("Trying to update the purpose record which does not exist in the database.");
				}
		}
		catch(Exception excp) {
			logger.error("Unable to update the transaction of purpose data in database " +
					".Exception thrown.");
			throw new DataAccessException("Unable to update the transaction of updating the purpose data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed Updating the Purpsoe data in updatePurpose() ");

	}

	@Override
	public List<Purpose> getAllPurpose() {
		// TODO Auto-generated method stub
		logger.info("Start: Calling getAllPurpose() method in PurposeDAO ..!");
		List<Purpose> master=null;
	
		try{
			EntityManager em = EntityManagerUtil.getEntityManager();
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Purpose> criteriaQuery = criteriaBuilder.createQuery(Purpose.class);
			Root<Purpose> root = criteriaQuery.from(Purpose.class);
			criteriaQuery.select(root);
			master = em.createQuery(criteriaQuery).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Error while getting purpose data in getAllPurpose()");
			throw new DataAccessException("Unable to get all purpose data in getAllPurpose()",e.getCause());
						
		}
		logger.info("END:Successfully Completed getting the Purpsoe data in getAllPurpose()");
	return master;
}


	@Override
	public Purpose getPurposeById(Integer id) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling getPurposeById() method in PurposeDAO ..!");
		Purpose master=null;
		try{
			EntityManager em=EntityManagerUtil.getEntityManager();
				if(id!=null)
					master=em.find(Purpose.class, id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Error while getting purpose data in getPurposeById()");
			throw new DataAccessException("Unable to get all purpose data in getPurposeById()",e.getCause());
		}
		logger.info("END:Successfully Completed getting the Purpsoe data by id  in getPurposeById()");
		return master;
	}

	@Override
	public void deletePurpose(Integer id) 
	{
		logger.info("Start: Calling deletePurpose() method in PurposeDAO ..!");

		// TODO Auto-generated method stub
		Purpose master=null;
		try
		{
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (id != null) 
			{
				master = em.find(Purpose.class, id);
				em.getTransaction().begin();
				em.remove(master);
				em.getTransaction().commit();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Error while deleting purpose data in deletePurpose()");
			throw new DataAccessException("Unable to delete all purpose data in deletePurpose()",e.getCause());	
		}
		logger.info("END:Successfully Completed deleting the purpose data in deletePurpose()");
	}

}