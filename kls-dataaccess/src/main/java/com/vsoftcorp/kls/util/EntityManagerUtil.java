package com.vsoftcorp.kls.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class EntityManagerUtil {

	private static Logger logger = Logger.getLogger(EntityManagerUtil.class);

	private static EntityManagerFactory SESSIONFACTORY = null;

	/**
	 * ThreadLocal variable which holds the Session for the current thread.
	 */
	private static final ThreadLocal<EntityManager> SESSION = new ThreadLocal<EntityManager>();

	/**
	 * Constructor for Hibernate Util.
	 */
	protected EntityManagerUtil() {
		super();
	}

	public static void init(String thePersistentUnit) {

		try {
			// Create the SessionFactory
			if (SESSIONFACTORY == null) {
				SESSIONFACTORY = Persistence
						.createEntityManagerFactory(thePersistentUnit);
			}
		} catch (final Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			logger.error("Initial SessionFactory creation failed.", ex);
			// throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * For getting Current Session.
	 * 
	 * @return Session
	 * 
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = (EntityManager) SESSION.get();
		// Open a new Session, if this Thread has none yet
		if (em == null || !em.isOpen()) {
			em = SESSIONFACTORY.createEntityManager();
			// em.getTransaction().begin();
		/*	em.setProperty("javax.persistence.cache.storeMode",
					CacheStoreMode.REFRESH);
			em.setProperty("javax.persistence.cache.retrieveMode",
					CacheRetrieveMode.BYPASS);*/
			SESSION.set(em);
		}
		return em;
	}

	/**
	 * Close the Current Session.
	 * 
	 */

	public static void closeSession() {
		logger.info("closing session");
		final EntityManager em = (EntityManager) SESSION.get();
		SESSION.set(null);
		if (em != null && em.isOpen())
			em.close();
	}

	public static Criteria createCriteria(Class<?> target) {
		final Session session = (Session) getEntityManager().getDelegate();
		final Criteria crit = session.createCriteria(target);
		return crit;
	}
	
	//Begin Transaction
	
	public static void beginTransaction() {
		EntityManager em = (EntityManager) SESSION.get();
		// Open a new Session, if this Thread has none yet
		if (em == null || !em.isOpen()) {
			em = SESSIONFACTORY.createEntityManager();
			em.getTransaction().begin();
			
			SESSION.set(em);
		}
		else{
			em.getTransaction().begin();			
		}
		
		return;
	}
	
	/**Commit Or RollBack Transaction Method.
	
	If True --> Commit  ... if False --> Rollback **/
	
	
	public static void CommitOrRollBackTransaction(Boolean flag) {
		logger.info("Start :Calling  CommitOrRollBackTransaction() in EntityManagerUtil ");
		EntityManager em = (EntityManager) SESSION.get();
		// Open a new Session, if this Thread has none yet
		logger.info("flag...:"+flag);
		logger.info("EntityManager...:"+em);
		logger.info("is transaction active ...:"+em.getTransaction().isActive());
		logger.info("is Entity manager open.."+em.isOpen());
		if (em != null && em.isOpen() && em.getTransaction().isActive()) {

			if(flag)
				em.getTransaction().commit();
			else
				em.getTransaction().rollback();
			
		}
		else {
			logger.error("Entity manager null/inactive while commit/rollback");
		}
		
		return;
	}
	
}
