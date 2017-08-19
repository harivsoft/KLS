package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Customer;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.dataaccess.ICustomerDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * This DAO class is used to the CRUD operations with customer table
 * 
 * @author a1605
 * 
 */

public class CustomerDAO implements ICustomerDAO {
	private static final Logger logger = Logger.getLogger(CustomerDAO.class);

	/**
	 * @param customerName
	 * @param pageNumber
	 */
	@Override
	public List<Customer> getAllCustomersByName(String customerName) {
		logger.info("Start: Fetching all the Customer" + " data from the database in getAllCustomersByName() method.");
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
			Root<Customer> root = criteria.from(Customer.class);
			Expression<String> pathName = root.get("name");
			Expression<String> pathId = root.get("customerId");
			criteria.where(
					builder.or(builder.like(builder.lower(pathName), "%" + customerName.toLowerCase() + "%"),
							builder.like(builder.lower(pathId), "%" + customerName.toLowerCase() + "%"))).orderBy(
					builder.asc(root.get("name")));
			customerList = em.createQuery(criteria).getResultList();
			/*
			 * Query query = em.createNativeQuery(
			 * "SELECT * FROM KLS_SCHEMA.CUSTOMER WHERE (NAME ILIKE '" +
			 * customerName + "%' OR CUST_ID ILIKE '" + customerName +
			 * "%' ) ORDER BY NAME ASC", Customer.class);
			 */
			/*
			 * query.setFirstResult((pageNo-1)*maxResult);
			 * query.setMaxResults(maxResult);
			 */
			// customerList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all Customer from the database getAllCustomersByName() method");
			throw new DataAccessException("Error while retriving all " + " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Customer data from the database in getAllCustomersByName() method.");
		return customerList;
	}

	@Override
	public Customer getCustomer(Long customerId) {

		logger.info("Start: Fetching the Customer" + " data from the database in getCustomerByName() method.");
		Customer master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (customerId != null) {
				master = em.find(Customer.class, customerId);
			}
		} catch (Exception e) {
			logger.error("Error while retriving all Customer from the database getCustomerByName() method");
			throw new DataAccessException("Error while retriving the customer " + " from the database", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the Customer data from the database in getCustomerByName() method.");
		return master;
	}

	@Override
	public void saveCustomer(Customer customer) {
		logger.info("Start: Saving the Customer data to the database in saveCustomer() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(customer);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the Customer  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Customer  data to the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the Customer  to the database in saveCustomer() method.");
	}

	@Override
	public void updateCustomer(Customer customer) {
		logger.info("Start: Updating the Customer  data to the database in updateCustomer() method.");
		Customer theCustomer = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = customer.getId();
			if (id != null) {
				theCustomer = em.find(Customer.class, id);
				if (theCustomer != null) {
					em.getTransaction().begin();
					theCustomer.setId(theCustomer.getId());
					theCustomer.setCustomerId(customer.getCustomerId());
					theCustomer.setCbsCustId(customer.getCbsCustId());
					theCustomer.setMemberSrlNo(customer.getMemberSrlNo());
					theCustomer.setMemberType(customer.getMemberType());
					theCustomer.setName(customer.getName());
					theCustomer.setVillageId(customer.getVillageId());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the Customer  "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the Customer  data to the database.", excp.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		if (theCustomer == null) {
			logger.error("Trying to update the Customer  record which does not exist in the database.");
			throw new DataAccessException("Trying to update the Customer  record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the Customer  data to the database in updateCustomer() method.");
	}
}