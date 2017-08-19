package com.vsoftcorp.kls.dataaccess.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.PacsGlMapping;
import com.vsoftcorp.kls.dataaccess.IPacsGlMappingDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * 
 * @author udanaiah
 * 
 */

public class PacsGlMappingDAO implements IPacsGlMappingDAO {

	private static final Logger logger = Logger.getLogger(PacsGlMappingDAO.class);

	/**
	 * Saves PacsGlMapping to the database.
	 */

	@Override
	public void savePacsGlMapping(PacsGlMapping pacsGlMapping) {
		// TODO Auto-generated method stub

		logger.info("Start: Calling  pacsGlMapping savePacsGlMapping() method..");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(pacsGlMapping);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.getMessage();
			logger.error("Could not commit the transaction of saving the PacsGlmapping "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the  PacsGlMapping data to the database.", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}

		logger.info("End: Successfully Completed Calling  pacsGlMapping savePacsGlMapping() method.");

	}

	/**
	 * update pacsGlMapping to the DataBase
	 */

	@Override
	public void updatePacsGlMapping(PacsGlMapping pacsGlmapping) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling updatePacsGlMapping() method in PacsGlMappingDAO ..!");
		PacsGlMapping dbMaster = null;
		EntityManager em = EntityManagerUtil.getEntityManager();

		try {
			if (pacsGlmapping.getId() != null)
				dbMaster = em.find(PacsGlMapping.class, pacsGlmapping.getId());

			if (dbMaster != null) {
				em.getTransaction().begin();

				if (pacsGlmapping.getId() != null)
					dbMaster.setId(pacsGlmapping.getId());

				dbMaster.setProduct(pacsGlmapping.getProduct());

				dbMaster.setPacs(pacsGlmapping.getPacs());

				dbMaster.setGlCode(pacsGlmapping.getGlCode());

				dbMaster.setIntReceivableGL(pacsGlmapping.getIntReceivableGL());

				dbMaster.setIntReceivedGL(pacsGlmapping.getIntReceivedGL());

				dbMaster.setPenalintReceivableGL(pacsGlmapping.getPenalintReceivableGL());

				dbMaster.setPenalIntReceivedGL(pacsGlmapping.getPenalIntReceivedGL());

				dbMaster.setProcessingFeeGl(pacsGlmapping.getProcessingFeeGl());

				dbMaster.setInsuranceGl(pacsGlmapping.getInsuranceGl());
				dbMaster.setClosingChargesGl(pacsGlmapping.getClosingChargesGl());
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to update the transaction of PacsGlMappingdata in database " + ".Exception thrown.");
			throw new DataAccessException("Unable to update the transaction of updating the PacsGlMapping data.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("END:Successfully Completed Calling  PacsGlMapping updatePacsGlMapping() method ");

	}

	@Override
	public PacsGlMapping getPacsGlMapping(Integer productId, Integer pacsId) {
		// TODO Auto-generated method stub

		logger.info("Start: getting PacsGlMapping by passing productId :" + productId + ", pacsId:" + pacsId);
		PacsGlMapping pacsGlMapping = null;

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select pgl from PacsGlMapping pgl where pgl.product.id=:productId and pgl.pacs.id=:pacsId");
			query.setParameter("productId", productId);
			query.setParameter("pacsId", pacsId);
			pacsGlMapping = (PacsGlMapping) query.getSingleResult();
		} catch (NoResultException noExcp) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: while getting pacsGlMapping.");
			throw new DataAccessException("Could not get PacsGlMapping getPacsGlMapping() ", e.getCause());
		}
		logger.info("End: Successfully got PacsGlMapping.");
		return pacsGlMapping;
	}

}
