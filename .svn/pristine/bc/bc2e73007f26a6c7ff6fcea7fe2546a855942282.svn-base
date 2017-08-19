package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.dataaccess.loan.IPacsLoanSanctionProcessDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class PacsLoanSanctionProcessDAO implements IPacsLoanSanctionProcessDAO {
	private static final Logger logger = Logger.getLogger(PacsLoanSanctionProcessDAO.class);

	@Override
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationByCustomer(String customerId, String pacId,
			String financialYear) {
		logger.info("Start: Fetching  pacs loan application detail for given customer data from the database in getPacsLoanApplicationByCustomer() method.");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = new ArrayList<PacsLoanApplicationDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT p FROM PacsLoanApplicationDetail p,PacsLoanApplicationHeader h where (h.pacs.id=:pacId and h.financialYear=:financialYear and h.id=p.headerId.id and p.customerId = :customerId and (p.applicationStatus=3 or p.applicationStatus=4 or p.applicationStatus=5)) order by p.priority asc");
			query.setParameter("customerId", Long.valueOf(customerId));
			query.setParameter("pacId", Integer.valueOf(pacId));
			query.setParameter("financialYear", financialYear);

			pacsLoanApplicationDetailList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving  pacs loan application detail records for given customer from the database");
			throw new DataAccessException(
					"Error while retriving  pacs loan application detail records for given customer from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the pacs loan application detail for given customer from the database in getPacsLoanApplicationByCustomer() method.");
		return pacsLoanApplicationDetailList;
	}

	@Override
	public List<Map> getLoanSanctionDetails(String pacId, String financialYear) {
		logger.info("Start: Fetching  pacs loan sanction detail for given pac and financial year data from the database in getLoanSanctionDetails() method.");
		List<Map> loanSanctionList = new ArrayList();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT DISTINCT new Map (p.customerId as customerId, '' as customerName, sum(p.sanctionedAmount) as sanctionedAmount) FROM 	PacsLoanApplicationHeader h,	PacsLoanApplicationDetail p where h.pacs.id=:pacId and 	h.financialYear=:financialYear and 	h.id=p.headerId.id and 	p.applicationStatus=3 group by p.customerId order by p.customerId asc");
			query.setParameter("pacId", Integer.valueOf(pacId));
			query.setParameter("financialYear", financialYear);
			// query.setParameter("applicationStatus",
			// LoanApplicationState.INSPECTED.getValue());
			loanSanctionList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving  pacs loan sanction  detail records for given pac and financial year from the database");
			throw new DataAccessException(
					"Error while retriving  pacs loan sanction  detail records for given pac and financial year from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the pacs loan sanction  detail for given pac and financial year from the database in getLoanSanctionDetails() method.");
		return loanSanctionList;
	}

	@Override
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationList(String pacId, String financialYear) {
		logger.info("Start: Fetching  pacs loan application detail for given pac and financial year  data from the database in getPacsLoanApplicationList() method.");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT p FROM PacsLoanApplicationHeader h,PacsLoanApplicationDetail p where h.pacs.id=:pacId and h.financialYear=:financialYear and h.id=p.headerId.id and p.applicationStatus in (3,4)");
			query.setParameter("pacId", Integer.valueOf(pacId));
			query.setParameter("financialYear", financialYear);
			// query.setParameter("applicationStatus",
			// LoanApplicationState.INSPECTED.getValue());
			pacsLoanApplicationDetailList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving  pacs loan application detail records for given pac and financial year  from the database");
			throw new DataAccessException(
					"Error while retriving  pacs loan application detail records for given pac and financial year  from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the pacs loan application detail for given pac and financial year  from the database in getPacsLoanApplicationList() method.");
		return pacsLoanApplicationDetailList;
	}
	
	@Override
	public List<PacsLoanApplicationDetail> getPacsLoanInspectedApplicationList(String pacId, String financialYear,Integer pageIndex) {
		logger.info("Start: Fetching  pacs loan application detail for given pac and financial year  data from the database in getPacsLoanApplicationList() method.");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = null;
		try {
			Integer maxRowsForPage=5;
			Integer startIndex = pageIndex*maxRowsForPage;
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT p FROM PacsLoanApplicationHeader h,PacsLoanApplicationDetail p where h.pacs.id=:pacId and h.financialYear=:financialYear and h.id=p.headerId.id and p.applicationStatus=3").setFirstResult(startIndex).setMaxResults(maxRowsForPage);
			query.setParameter("pacId", Integer.valueOf(pacId));
			query.setParameter("financialYear", financialYear);
			// query.setParameter("applicationStatus",
			// LoanApplicationState.INSPECTED.getValue());
			pacsLoanApplicationDetailList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving  pacs loan application detail records for given pac and financial year  from the database");
			throw new DataAccessException(
					"Error while retriving  pacs loan application detail records for given pac and financial year  from the database",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the pacs loan application detail for given pac and financial year  from the database in getPacsLoanApplicationList() method.");
		return pacsLoanApplicationDetailList;
	}

	@Override
	public void createOrUpdateAccount(Product theProduct, Account theAccount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSanctionAmount(PacsLoanApplicationDetail pacsLoanApplicationDetail) {
		logger.info("Start: updating sanction amount in updateSanctionAmount()");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			if (pacsLoanApplicationDetail != null)
				em.merge(pacsLoanApplicationDetail);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while updating sanction amount");
			throw new DataAccessException("Error while updating sanction amount", e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: updating sanction amount in updateSanctionAmount()");
	}
	
	
	@Override
	public Integer getTotalNoOfLoanInspectedApplications(String pacId, String financialYear) {
		logger.info("Start: Fetchin total no of Inspected records in  getTotalNoOfLoanInspectedApplications() method.");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = null;
		Integer totalInspecteRecords=0;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT p FROM PacsLoanApplicationHeader h,PacsLoanApplicationDetail p where h.pacs.id=:pacId and h.financialYear=:financialYear and h.id=p.headerId.id and p.applicationStatus=3");
			query.setParameter("pacId", Integer.valueOf(pacId));
			query.setParameter("financialYear", financialYear);
			pacsLoanApplicationDetailList = query.getResultList();
			totalInspecteRecords = pacsLoanApplicationDetailList.size();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Fetchin total no of Inspected records in  getTotalNoOfLoanInspectedApplications() method.");
			throw new DataAccessException(
					"Error while Fetchin total no of Inspected records in  getTotalNoOfLoanInspectedApplications() method.",
					e.getCause());
		}
		finally{
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetchin total no of Inspected records in  getTotalNoOfLoanInspectedApplications() method.");
		return totalInspecteRecords;
	}

	

}
