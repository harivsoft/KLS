package com.vsoftcorp.kls.dataaccess.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SubPurpose;
import com.vsoftcorp.kls.dataaccess.ISubPurposeDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class SubPurposeDAO implements ISubPurposeDAO{

	private static final Logger logger = Logger.getLogger(SubPurposeDAO.class);

	@Override
	public void saveSubPurpose(SubPurpose data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling saveSubPurpose() method in SubPurposeDAO ..!");
		try
		{
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(data);
			em.getTransaction().commit();
		}
		catch (Exception excp)
		{
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of subpurpose data in database " +
					".Exception thrown.");
			throw new DataAccessException("Unable to commit the transaction of saving the subpurpose data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed saving the SubPurpose data in saveSubPurpose()");
	}

	@Override
	public void updateSubPurpose(SubPurpose data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling updateSubPurpose() method in SubPurposeDAO ..!");
		SubPurpose master=null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
		
			Integer id = data.getId();
			if (id != null) 
				master = em.find(SubPurpose.class, id);
				if (master != null)
				{
					em.getTransaction().begin();
					master.setName(data.getName());
					master.setRemarks(data.getRemarks());
					em.getTransaction().commit();
				}
		}
		catch(Exception excp) {
			logger.error("Unable to update the transaction of subpurpose data in database " +
					".Exception thrown.");
			throw new DataAccessException("Unable to update the transaction of updating the subpurpose data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed Updating the SubPurpsoe data in updateSubPurpose() ");

	}

	@Override
	public List<SubPurpose> getAllSubPurpose() {
		// TODO Auto-generated method stub
		EntityManager em = EntityManagerUtil.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SubPurpose> criteriaQuery = criteriaBuilder.createQuery(SubPurpose.class);
		Root<SubPurpose> root = criteriaQuery.from(SubPurpose.class);
		criteriaQuery.select(root);
		List<SubPurpose> master = em.createQuery(criteriaQuery).getResultList();
		return master;
	}

	@Override
	public SubPurpose getSubPurposeById(Integer id) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling getSubPurposeById() method in SubPurposeDAO ..!");
		SubPurpose master=null;
	
		try{
			EntityManager em=EntityManagerUtil.getEntityManager();
				if(id!=null)
					master=em.find(SubPurpose.class, id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Error while getting SubPurpose data in getSubPurposeById()");
			throw new DataAccessException("Unable to get all subpurpose data in getSubPurposeById()",e.getCause());
		}
		logger.info("END:Successfully Completed getting the Purpsoe data by id  in getPurposeById()");
		return master;
	}

	@Override
	public void deleteSubPurpose(Integer id) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling deleteSubPurpose() method in SubPurposeDAO ..!");

		// TODO Auto-generated method stub
		SubPurpose master=null;
		try
		{
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (id != null) 
			{
				master = em.find(SubPurpose.class, id);
				em.getTransaction().begin();
				em.remove(master);
				em.getTransaction().commit();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Error while deleting subpurpose data in deleteSubPurpose()");
			throw new DataAccessException("Unable to delete all subpurpose data in deleteSubPurpose()",e.getCause());	
		}
		logger.info("END:Successfully Completed deleting the subpurpose data in deleteSubPurpose()");
	}
}
