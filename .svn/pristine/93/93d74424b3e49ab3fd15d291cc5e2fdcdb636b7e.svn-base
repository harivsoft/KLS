package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationDetailDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationHeaderDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

/**
 * 
 * @author a9153 This class has DAO implementations for
 *         PacsLoanApplicationHeader
 * 
 */

public class PacsLoanApplicationHeaderDAO implements IPacsLoanApplicationHeaderDAO {

	private static final Logger logger = Logger.getLogger(PacsLoanApplicationHeaderDAO.class);

	/**
	 * Saves pacsLoanApplicationHeader to the database.
	 */
	public void savePacsLoanApplicationHeader(
			PacsLoanApplicationHeader thePacsLoanApplicationHeaderMaster) {

		logger.info("Start: Saving the pacsLoanApplicationHeader master data to the database in savePacsLoanApplicationHeader() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			logger.info("Got connection" + em);
			em.getTransaction().begin();
			em.persist(thePacsLoanApplicationHeaderMaster);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the pacsLoanApplicationHeader master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the pacsLoanApplicationHeader master data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the pacsLoanApplicationHeader master to the database in savePacsLoanApplicationHeader() method.");
	}

	/**
	 * Updates an existing pacsLoanApplicationHeader in the database.
	 */
	public void updatePacsLoanApplicationHeader(
			PacsLoanApplicationHeader thePacsLoanApplicationHeaderMaster) {

		logger.info("Start: Updating the pacsLoanApplicationHeader master data to the database in updatePacsLoanApplicationHeader() method.");
		PacsLoanApplicationHeader master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long pacsLoanApplicationHeaderCode = thePacsLoanApplicationHeaderMaster.getId();
			if (pacsLoanApplicationHeaderCode != null) {
				master = em.find(PacsLoanApplicationHeader.class, pacsLoanApplicationHeaderCode);
				if (master != null) {
					em.getTransaction().begin();
					master.setApplicationDate(thePacsLoanApplicationHeaderMaster
							.getApplicationDate());
					master.setApplicationType(thePacsLoanApplicationHeaderMaster
							.getApplicationType());
					//master.setCrop(thePacsLoanApplicationHeaderMaster.getCrop());
					master.setPacs(thePacsLoanApplicationHeaderMaster.getPacs());
					master.setProcessStatus(thePacsLoanApplicationHeaderMaster.getProcessStatus());
					master.setScheme(thePacsLoanApplicationHeaderMaster.getScheme());
					master.setFinancialYear(thePacsLoanApplicationHeaderMaster.getFinancialYear());
					master.setIsRenewed(thePacsLoanApplicationHeaderMaster.getIsRenewed());
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the pacsLoanApplicationHeader master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the pacsLoanApplicationHeader master data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the pacsLoanApplicationHeader master record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the pacsLoanApplicationHeader master record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the pacsLoanApplicationHeader master data to the database in updatePacsLoanApplicationHeader() method.");
	}

	/**
	 * Retrives and returns the pacsLoanApplicationHeader record from database
	 * if already exists
	 * 
	 */
	public PacsLoanApplicationHeader getPacsLoanApplicationHeader(
			PacsLoanApplicationHeader thePacsLoanApplicationHeaderMaster) {
		logger.info("Start: Inside method getPacsLoanApplicationHeader()");
		PacsLoanApplicationHeader pacsLoanApplicationHeaderMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long pacsLoanApplicationHeaderCode = thePacsLoanApplicationHeaderMaster.getId();
			if (pacsLoanApplicationHeaderCode != null) {
				pacsLoanApplicationHeaderMaster = em.find(PacsLoanApplicationHeader.class,
						pacsLoanApplicationHeaderCode);
			}
		} catch (Exception e) {
			logger.error("Error while retriving pacsLoanApplicationHeader from the database");
			throw new DataAccessException(
					"Error while retriving pacsLoanApplicationHeader from the database.",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getPacsLoanApplicationHeader()");
		return pacsLoanApplicationHeaderMaster;
	}

	/**
	 * Returns all the pacsLoanApplicationHeader master records to the client
	 * for a given status
	 */
	public List<PacsLoanApplicationHeader> getPacsLoanApplicationHeaders(String processStatus,
			Integer pacsId,Integer branchId) {

		logger.info("Start: Fetching all the pacsLoanApplicationHeader master data from the database in getAllPacsLoanApplicationHeaders() method.");
		List<PacsLoanApplicationHeader> pacsLoanApplicationHeaderMasterList = new ArrayList<PacsLoanApplicationHeader>();
		if (processStatus == null || "".equals(processStatus))
			processStatus = "A";
			String Ids=null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT * FROM KLS_SCHEMA.PACS_LOAN_APPLICATION_HDR WHERE PROCESS_STATUS='"+ processStatus+"'");
			if(branchId != 0){
				IPacsDAO pacs = KLSDataAccessFactory.getPacsDAO();
				List<Pacs> pacIDs=pacs.getAllPacsByBranch(branchId);
				if (pacIDs != null && !pacIDs.isEmpty()){
					Ids =getPacsIDs(pacIDs);
					queryString.append(" and PACS_ID IN " + Ids);
				}else{
					return pacsLoanApplicationHeaderMasterList;
				}
			}
			
			queryString.append(" ORDER BY ID ASC");

			// Query query =
			// em.createQuery("SELECT p FROM PacsLoanApplicationHeader p where p.processStatus = :processStatus");
			// query.setParameter("processStatus" ,processStatus);

			Query query = em.createNativeQuery(queryString.toString(),
					PacsLoanApplicationHeader.class);
			pacsLoanApplicationHeaderMasterList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all pacsLoanApplicationHeaders from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all pacsLoanApplicationHeaders from the database",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the pacsLoanApplicationHeader master data from the database in getAllPacsLoanApplicationHeaders() method.");
		return pacsLoanApplicationHeaderMasterList;
	}

	@Override
	public PacsLoanApplicationHeader getPacsLoanApplicationHeader(
			Long thePacsLoanApplicationHeaderId) {

		logger.info("Start: Inside method getPacsLoanApplicationHeader()");
		PacsLoanApplicationHeader pacsLoanApplicationHeaderMaster = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			if (thePacsLoanApplicationHeaderId != null) {
				pacsLoanApplicationHeaderMaster = em.find(PacsLoanApplicationHeader.class,
						thePacsLoanApplicationHeaderId);
			} else {
				logger.info("PacsLoanApplicationHeader id not set for retrival of pacsLoanApplicationHeader record");
				throw new DataAccessException(
						"PacsLoanApplicationHeader id not set for retrival of pacsLoanApplicationHeader record");
			}
		} catch (Exception e) {
			logger.error("Error while retriving pacsLoanApplicationHeader from the database");
			throw new DataAccessException(
					"Error while retriving pacsLoanApplicationHeader from the database.",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getPacsLoanApplicationHeader()");
		return pacsLoanApplicationHeaderMaster;
	}

	public void closePacsLoanApplicationHeader(
			PacsLoanApplicationHeader thePacsLoanApplicationHeaderMaster) {
		logger.info("Start: Updating the pacsLoanApplicationHeader master data to the database in updatePacsLoanApplicationHeader() method.");
		IPacsLoanApplicationDetailDAO detailDAO = KLSDataAccessFactory
				.getPacsLoanApplicationDetailDAO();
		List<PacsLoanApplicationDetail> detailRecords = new ArrayList<PacsLoanApplicationDetail>();
		PacsLoanApplicationHeader master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long pacsLoanApplicationHeaderCode = thePacsLoanApplicationHeaderMaster.getId();
			if (pacsLoanApplicationHeaderCode != null) {
				master = em.find(PacsLoanApplicationHeader.class, pacsLoanApplicationHeaderCode);
				if (master != null) {
					em.getTransaction().begin();
					master.setProcessStatus("C");
					master.setApplicationDate(thePacsLoanApplicationHeaderMaster
							.getApplicationDate());
					detailRecords = detailDAO.getPacsLoanApplicationDetails(master.getId());
					if (detailRecords != null && detailRecords.size() > 0)
						for (PacsLoanApplicationDetail detailRec : detailRecords) {
							detailDAO.updateStatus(detailRec.getId(),
									LoanApplicationState.RECOMMENDED);
						}
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Could not commit the transaction of updating the pacsLoanApplicationHeader master "
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the pacsLoanApplicationHeader master data to the database.",
					excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the pacsLoanApplicationHeader master record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the pacsLoanApplicationHeader master record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the pacsLoanApplicationHeader master data to the database in updatePacsLoanApplicationHeader() method.");
	}

	@Override
	public List<String> getAllFinancialYearsByPacId(String pacId) {
		logger.info("Start: Inside method getAllPacsLoanApplicationHeadersByPacId()");
		List<String> financialYearList = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();

			if (pacId != null) {
				Query query = em
						.createQuery("SELECT DISTINCT p.financialYear FROM PacsLoanApplicationHeader p where p.pacs.id = :pacId");
				query.setParameter("pacId", Integer.valueOf(pacId));
				financialYearList = query.getResultList();
			} else {
				logger.info("pac id not set for retrival of Financial Years record");
				throw new DataAccessException(
						"pac id not set for retrival of Financial Years record");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving given Pac from the database");
			throw new DataAccessException(
					"Error while retriving Financial Years for given Pac from the database.",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Inside method getAllPacsLoanApplicationHeadersByPacId()");
		System.out.println("financialYearList:" + financialYearList);
		return financialYearList;
	}
	
	public String getPacsIDs(List<Pacs> pacs){
		
		logger.info("Start: In farmatting pacs id's for header id list query");
		String ids=null;
		try{
			if(pacs != null && !pacs.isEmpty()){
				ids="(";
				for(Pacs p : pacs){
					ids=ids+"'"+p.getId()+"',";
				}
				ids= ids.substring(0, ids.length()-1)+")";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("End: In farmatting pacs id's for header id list query");
		return ids;
		
	}
	
	public List<PacsLoanApplicationHeader> getPacsLoanApplicationHeadersByPacsId(Integer pacsId) {

		logger.info("Start: Fetching all the pacsLoanApplicationHeader master data from the database in getAllPacsLoanApplicationHeaders() method.");
		List<PacsLoanApplicationHeader> pacsLoanApplicationHeaderMasterList = new ArrayList<PacsLoanApplicationHeader>();
		String processStatus = "A";
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT * FROM KLS_SCHEMA.PACS_LOAN_APPLICATION_HDR WHERE PROCESS_STATUS='"+ processStatus+"'  and PACS_ID=" + pacsId);
			queryString.append(" ORDER BY ID ASC");
			Query query = em.createNativeQuery(queryString.toString(),
					PacsLoanApplicationHeader.class);
			pacsLoanApplicationHeaderMasterList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all pacsLoanApplicationHeaders from the database");
			e.printStackTrace();
			throw new DataAccessException(
					"Error while retriving all pacsLoanApplicationHeaders from the database",
					e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the pacsLoanApplicationHeader master data from the database in getAllPacsLoanApplicationHeaders() method.");
		return pacsLoanApplicationHeaderMasterList;
	}
}
