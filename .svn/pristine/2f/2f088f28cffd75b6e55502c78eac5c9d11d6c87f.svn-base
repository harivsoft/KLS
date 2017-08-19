package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Document;
import com.vsoftcorp.kls.business.entities.LoanProductDocumentMapping;
import com.vsoftcorp.kls.dataaccess.ILoanProductDocumentMappingDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class LoanProductDocumentMappingDAO implements
		ILoanProductDocumentMappingDAO {
	private static final Logger logger = Logger.getLogger(LoanProductDocumentMappingDAO.class);

	@Override
	public void saveLoanProductDocumentMapping(LoanProductDocumentMapping master) {

		// TODO Auto-generated method stub
		logger.info("Start: Calling saveLoanProductDocumentMapping() method in LoanProductDocumentMappingDAO");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(master);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of LoanProductDocumentMapping data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of saving the LoanProductDocumentMapping data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed saving the LoanProductDocumentMapping data in saveLoanProductDocumentMapping()");

	}

	public List<Document> getDocumentsBasedOnMappingId(Long id) {
		logger.info("Start: Calling getDocumentsBasedOnMappingId() method in LoanProductDocumentMappingDAO");

		List<Document> lst = new ArrayList<>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select l from LoanProductDocumentMapping l where l.id = :Id");
			query.setParameter("Id", id);
			List<LoanProductDocumentMapping> list = query.getResultList();
			for (LoanProductDocumentMapping docMapping : list) {
				lst.add(docMapping.getDocument());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("successfully got document details based on productdocMappingId");
		logger.info("lst sizz----" + lst.size());
		return lst;
	}

	public LoanProductDocumentMapping getProdDocumentMappingBasedOnId(Long id) {
		logger.info("Start: Calling getProdDocumentMappingBasedOnId() method in LoanProductDocumentMappingDAO");
		LoanProductDocumentMapping loanProductDocumentMapping = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("select l from LoanProductDocumentMapping l where l.id = :Id");
			query.setParameter("Id", id);
			loanProductDocumentMapping = (LoanProductDocumentMapping) query
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("successfully got LoanProductDocumentMapping details based on id");

		return loanProductDocumentMapping;
	}

	@Override
	public void deleteLoanProductDocumentMapping(Long id) {

		logger.info("Start: Deleting the LoanProductDocumentMapping  data in database in deleteLoanProductDocumentMapping() method.");
		try {

			EntityManager em = EntityManagerUtil.getEntityManager();
			LoanProductDocumentMapping loanProductDocumentMapping = getProdDocumentMappingBasedOnId(id);
			if (loanProductDocumentMapping != null) {
				em.getTransaction().begin();
				em.remove(loanProductDocumentMapping);
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of Deleting the LoanProductDocumentMapping  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Deleting the LoanProductDocumentMapping data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully Deleting the LoanProductDocumentMapping data to the database in deleteLoanProductDocumentMapping() method.");

	}

	@Override
	public List<LoanProductDocumentMapping> getLoanProductDocumentMapping(
			String productId) {

		logger.info("Start: Fetching all the LoanProductDocumentMapping data from the database in getLoanProductDocumentMapping() method.");
		List<LoanProductDocumentMapping> loanProductDocMappingList = new ArrayList<LoanProductDocumentMapping>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT v FROM LoanProductDocumentMapping v where v.product.id=:productId");
			query.setParameter("productId", Integer.valueOf(productId));
			loanProductDocMappingList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving all LoanProductDocumentMapping based on productId from the database");
			throw new DataAccessException(
					"Error while retriving all LoanProductDocumentMapping based on productId from the database",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the LoanProductDocumentMapping data from the database in getLoanProductDocumentMapping() method.");
		return loanProductDocMappingList;
	}

}
