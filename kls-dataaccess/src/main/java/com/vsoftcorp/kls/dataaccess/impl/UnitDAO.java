package com.vsoftcorp.kls.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Unit;
import com.vsoftcorp.kls.dataaccess.IUnitDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class UnitDAO implements IUnitDAO{
	private static final Logger logger = Logger.getLogger(UnitDAO.class);

	@Override
	public void saveUnit(Unit data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling saveUnit() method in UnitDAO ..!");
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
			logger.error("Unable to commit the transaction of Unit data in database " +
					".Exception thrown.");
			throw new DataAccessException("Unable to commit the transaction of saving the Unit data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed saving the Unit data in saveUnit()");
	}

	@Override
	public void updateUnit(Unit data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling updateUnit() method in UnitDAO ..!");
		Unit master=null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
		
			Integer id = data.getId();
			if (id != null) 
				master = em.find(Unit.class, id);
				if (master != null)
				{
					em.getTransaction().begin();
					master.setName(data.getName());
					master.setRemarks(data.getRemarks());
					em.getTransaction().commit();
				}
		}
		catch(Exception excp) {
			logger.error("Unable to update the transaction of Unit data in database " +
					".Exception thrown.");
			throw new DataAccessException("Unable to update the transaction of updating the Unit data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed Updating the SubPurpsoe data in updateUnit() ");

	}

	@Override
	public List<Unit> getAllUnit() {
		// TODO Auto-generated method stub
		EntityManager em = EntityManagerUtil.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Unit> criteriaQuery = criteriaBuilder.createQuery(Unit.class);
		Root<Unit> root = criteriaQuery.from(Unit.class);
		criteriaQuery.select(root);
		List<Unit> master = em.createQuery(criteriaQuery).getResultList();
		return master;
	}

	@Override
	public Unit getUnitById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUnit(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
