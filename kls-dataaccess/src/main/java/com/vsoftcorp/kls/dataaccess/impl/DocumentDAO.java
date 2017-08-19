package com.vsoftcorp.kls.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Document;
import com.vsoftcorp.kls.dataaccess.IDocumentDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class DocumentDAO implements IDocumentDAO {

	private static final Logger logger = Logger.getLogger(DocumentDAO.class);

	@Override
	public void saveDocument(Document data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling saveDocument() method in DocumentDAO ..!");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(data);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of Document data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of saving the Document data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed saving the Document data in saveDocument()");
	}

	@Override
	public void updateDocument(Document data) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling updateDocument() method in DocumentDAO ..!");
		Document master = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {

			Integer id = data.getId();
			if (id != null)
				master = em.find(Document.class, id);
			if (master != null) {
				em.getTransaction().begin();
				master.setName(data.getName());
				em.getTransaction().commit();
			}
		} catch (Exception excp) {
			logger.error("Unable to update the transaction of Document data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to update the transaction of updating the Document data.",
					excp.getCause());
		}
		logger.info("END:Successfully Completed Updating the SubPurpsoe data in updateDocument() ");
	}

	@Override
	public Document getDocumentById(Integer id) {
		logger.info("Start: Inside method getDocumentById()");
		Document document = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (id != null) {
				document = em.find(Document.class, id);
			}
		} catch (Exception e) {
			logger.error("Error while retriving document from the database");
			throw new DataAccessException(
					"Error while retriving document from the database.",
					e.getCause());
		}
		logger.info("End: Inside method getDocumentById()");
		return document;
	}

	@Override
	public void deleteDocument(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Document> getAllDocument() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		EntityManager em = EntityManagerUtil.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Document> criteriaQuery = criteriaBuilder
				.createQuery(Document.class);
		Root<Document> root = criteriaQuery.from(Document.class);
		criteriaQuery.select(root);
		List<Document> master = em.createQuery(criteriaQuery).getResultList();
		return master;
	}

}
