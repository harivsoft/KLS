package com.vsoftcorp.kls.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.PacsGLExtract;
import com.vsoftcorp.kls.dataaccess.IPacsGLExtractDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * 
 * @author a9153 This class has DAO implementations for PacsGLExtract
 * 
 */

public class PacsGLExtractDAO implements IPacsGLExtractDAO {

	private static final Logger logger = Logger.getLogger(PacsGLExtractDAO.class);

	@Override
	public void savePacsGLExtract(List<PacsGLExtract> pacsGLExtracts) {

		logger.info("Start: Saving the pacsGLExtract  data to the database in savePacsGLExtract() method.");
		try {

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
			EntityManager em = emf.createEntityManager();
			logger.info("Got connection" + em);
			em.getTransaction().begin();
			for (PacsGLExtract pacsGLExtract : pacsGLExtracts) {
				em.persist(pacsGLExtract);
			}
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the pacsGLExtract  " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of saving the pacsGLExtract  data to the database.", excp.getCause());
		}
		logger.info("End: Successfully saved the pacsGLExtract  to the database in savePacsGLExtract() method.");
	}

}
