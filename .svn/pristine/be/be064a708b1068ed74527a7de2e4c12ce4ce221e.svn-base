/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.dataaccess.IBankParameterDAO;
import com.vsoftcorp.kls.dataaccess.IDistrictDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

/**
 * @author a9152
 * 
 */
public class BankParameterDAO implements IBankParameterDAO {

	private static final Logger logger = Logger
			.getLogger(BankParameterDAO.class);

	/**
	 * Saves the bank parameter to the database.
	 */
	public void saveBankParameter(BankParameter bankParameter) {

		logger.info("Start: Saving the bank parameter data to the database in saveBankParameter() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(bankParameter);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of saving the bank parameter data.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the bank parameter data.",
					excp.getCause());
		}
		logger.info("End: Successfully saved the bank parameter to the database in saveBankParameter() method.");
	}

	@Override
	public void updateBankParameter(BankParameter bankParameter) {

		logger.info("Start: Updating the bank parameter data to the database in updateBankParameter() method.");
		BankParameter master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer id = bankParameter.getId();
			if (id != null) {
				master = em.find(BankParameter.class, id);
				if (master != null) {
					em.getTransaction().begin();
					master.setBankCode(bankParameter.getBankCode());
					/*master.setBankProcessStatus(bankParameter
							.getBankProcessStatus());
					master.setBusinessDate(bankParameter.getBusinessDate());*/
					IDistrictDAO districtDao = KLSDataAccessFactory
							.getDistrictDAO();
					District district = districtDao.getDistrict(bankParameter
							.getDistrict());
					master.setDistrict(district);
					master.setLoanLimitPerFarmer(bankParameter
							.getLoanLimitPerFarmer());
					master.setMaxShareAmount(bankParameter.getMaxShareAmount());
					master.setPriorityOrder(bankParameter.getPriorityOrder());
					master.setMaxDaysForInconsistency(bankParameter.getMaxDaysForInconsistency());
					master.setBorrowingTransactionMethod(bankParameter.getBorrowingTransactionMethod());
					master.setCbsIntegrationMethod(bankParameter.getCbsIntegrationMethod());
					master.setImageUploadSize(bankParameter.getImageUploadSize());
					master.setAuthorizationRequired(bankParameter.getAuthorizationRequired());
					master.setPendingReturnStatus(bankParameter.getPendingReturnStatus());
					master.setShowReceivableAtBranch(bankParameter.getShowReceivableAtBranch());
					master.setCashGl(bankParameter.getCashGl());
					master.setMultipleLoginAllow(bankParameter.getMultipleLoginAllow());
					//em.merge(master);
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the bank parameter data.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the bank parameter data.",
					excp.getCause());
			

		}
		if (master == null) {
			logger.error("Trying to update the bank parameter record which does not exist.");
			throw new DataAccessException(
					"Trying to update the bank parameter record which does not exist.");
		}
		logger.info("End: Successfully updated the bank parameter data to the database in updateBankParameter() method.");
	}

	@Override
	public BankParameter getBankParameter(BankParameter bankParameter) {

		logger.info("Start: Fetching the bank parameter data from the database in getBankParameter() method.");
		BankParameter master = new BankParameter();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Integer id = bankParameter.getId();
			if (id != null) {
				master = em.find(BankParameter.class, id);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the bank parameter data.");
			throw new DataAccessException(
					"Could not fetch the bank parameter data.", excp.getCause());
		}
		logger.info("End: Successfully fetched the bank parameter data from the database in getBankParameter() method.");
		return master;
	}

	@Override
	public BankParameter getBankParameterById(Integer bankParameterId) {

		logger.info("Start: Fetching the bank parameter data from the database in getBankParameter() method.");
		BankParameter master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (bankParameterId != null) {
				master = em.find(BankParameter.class, bankParameterId);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the bank parameter data.");
			throw new DataAccessException(
					"Could not fetch the bank parameter data.", excp.getCause());
		}
		logger.info("End: Successfully fetched the bank parameter data from the database in getBankParameter() method.");
		return master;
	}

	@Override
	public List<BankParameter> getAllBankParameters() {
		// TODO Auto-generated method stub
		logger.info("Start: Fetching all the bank parameter data from the database in getAllBankParameters() method.");
		List<BankParameter> bankParameterList = new ArrayList<BankParameter>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			bankParameterList = em.createQuery("SELECT b FROM BankParameter b")
					.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all bank parameter records from the database.");
			throw new DataAccessException(
					"Error while retriving all bank parameter records.",
					e.getCause());
		}
		logger.info("End: Fetching all the bank parameter data from the database in getAllBankParameters() method.");
		return bankParameterList;
	}

}
