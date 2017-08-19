/**
 * 
 */
package com.vsoftcorp.kls.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationDetailDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

/**
 * @author a9152
 * 
 */
public class PacsLoanApplicationDetailDAO implements IPacsLoanApplicationDetailDAO {

	private static final Logger logger = Logger.getLogger(PacsLoanApplicationDetailDAO.class);

	@Override
	public void savePacsLoanApplicationDetail(PacsLoanApplicationDetail pacsLoanApplicationDetail) {

		logger.info("Start: Saving the pacs loan application detail data to the database in savePacsLoanApplicationDetail() method.");
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(pacsLoanApplicationDetail);
			em.getTransaction().commit();
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of saving the pacs loan application detail " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of saving the pacs loan application detail data to the database.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully saved the pacs loan application detail to the database in savePacsLoanApplicationDetail() method.");
	}

	@Override
	public void updatePacsLoanApplicationDetail(PacsLoanApplicationDetail pacsLoanApplicationDetail) {

		logger.info("Start: Updating the pacs loan application detail data to the database in updatePacsLoanApplicationDetail() method.");
		PacsLoanApplicationDetail master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = pacsLoanApplicationDetail.getId();
			if (id != null) {
				master = em.find(PacsLoanApplicationDetail.class, id);
				if (master != null) {
					em.getTransaction().begin();
					master.setCalculatedAmount(pacsLoanApplicationDetail.getCalculatedAmount());
					master.setCropId(pacsLoanApplicationDetail.getCropId());
					master.setCustomerId(pacsLoanApplicationDetail.getCustomerId());
					master.setEnteredRemarks(pacsLoanApplicationDetail.getEnteredRemarks());
					master.setHeaderId(pacsLoanApplicationDetail.getHeaderId());
					master.setInspectionAmount(pacsLoanApplicationDetail.getInspectionAmount());
					master.setInspectedBy(pacsLoanApplicationDetail.getInspectedBy());
					master.setInspectedDate(pacsLoanApplicationDetail.getInspectedDate());
					master.setInspectionRemarks(pacsLoanApplicationDetail.getInspectionRemarks());
					master.setLandArea(pacsLoanApplicationDetail.getLandArea());
					master.setLandTypeId(pacsLoanApplicationDetail.getLandTypeId());
					master.setLoanApplicationNo(pacsLoanApplicationDetail.getLoanApplicationNo());
					master.setPriority(pacsLoanApplicationDetail.getPriority());
					master.setApplicationStatus(pacsLoanApplicationDetail.getApplicationStatus());
					master.setRecommendedAmount(pacsLoanApplicationDetail.getRecommendedAmount());
					master.setRequiredAmount(pacsLoanApplicationDetail.getRequiredAmount());
					master.setSanctionedAmount(pacsLoanApplicationDetail.getSanctionedAmount());
					master.setSanctionedBy(pacsLoanApplicationDetail.getSanctionedBy());
					master.setSanctionedDate(pacsLoanApplicationDetail.getSanctionedDate());
					master.setSanctionedRemarks(pacsLoanApplicationDetail.getSanctionedRemarks());
					master.setSeasonId(pacsLoanApplicationDetail.getSeasonId());
					master.setInsuranceAmount(pacsLoanApplicationDetail.getInsuranceAmount());
					master.setShareAmount(pacsLoanApplicationDetail.getShareAmount());
					master.setEligibleSanctionAmount(pacsLoanApplicationDetail.getEligibleSanctionAmount());
					master.setInspectionAuthRemarks(pacsLoanApplicationDetail.getInspectionAuthRemarks());

					em.getTransaction().commit();
				} else {
					logger.error("Cannot update the pacs loan application detail record as it does not exist in the database.");
					throw new DataAccessException("Cannot update the pacs loan application detail record as it does not exist in the database.");
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the pacs loan application detail " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of updating the pacs loan application detail data to the database.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update the pacs loan application detail record which does not exist in the database.");
			throw new DataAccessException("Trying to update the pacs loan application detail record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the pacs loan application detail data to the database in updatePacsLoanApplicationDetail() method.");
	}

	@Override
	public PacsLoanApplicationDetail getPacsLoanApplicationDetail(PacsLoanApplicationDetail pacsLoanApplicationDetail) {

		logger.info("Start: Fetching the pacs loan application detail data from the database in getPacsLoanApplicationDetail() method.");
		PacsLoanApplicationDetail master = new PacsLoanApplicationDetail();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = pacsLoanApplicationDetail.getId();
			if (id != null) {
				master = em.find(PacsLoanApplicationDetail.class, id);
			}
		} catch (Exception excp) {
			logger.error("Could not fetch the pacs loan application detail data from the " + "database using pacs loan application detail id.Exception thrown.");
			throw new DataAccessException("Could not fetch the pacs loan application detail data from the database.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully fetched the pacs loan application detail data from the database in getPacsLoanApplicationDetail() method.");
		return master;
	}

	@Override
	public List<PacsLoanApplicationDetail> getAllPacsLoanApplicationDetails() {

		logger.info("Start: Fetching all the pacs loan application detail data from the database in getAllPacsLoanApplicationDetails() method.");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = new ArrayList<PacsLoanApplicationDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			pacsLoanApplicationDetailList = em.createQuery("SELECT p FROM PacsLoanApplicationDetail p").getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving all pacs loan application detail records from the database");
			throw new DataAccessException("Error while retriving all pacs loan application detail records from the database", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the pacs loan application detail data from the database in getAllPacsLoanApplicationDetails() method.");
		return pacsLoanApplicationDetailList;
	}

	@Override
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationDetails(Long headerId) {
		logger.info("Start: Fetching  pacs loan application detail for given header data from the database in getPacsLoanApplicationDetails() method.");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = new ArrayList<PacsLoanApplicationDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM PacsLoanApplicationDetail p where p.headerId.id = :headerId");
			query.setParameter("headerId", headerId);

			pacsLoanApplicationDetailList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving  pacs loan application detail records for given header from the database");
			throw new DataAccessException("Error while retriving  pacs loan application detail records for given header from the database", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the pacs loan application detail for given header from the database in getPacsLoanApplicationDetails() method.");
		return pacsLoanApplicationDetailList;

	}

	@Override
	public void updateStatus(Long detailId, LoanApplicationState status) {
		logger.info("Start: Updating process status for pacs loan application detail data to the database in updateStaus() method.");
		PacsLoanApplicationDetail master = null;

		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (detailId != null) {
				master = em.find(PacsLoanApplicationDetail.class, detailId);
				if (master != null) {
					if (em.getTransaction().isActive() == false) {
						em.getTransaction().begin();
						master.setApplicationStatus(status);
						em.getTransaction().commit();
					} else {
						master.setApplicationStatus(status);
					}
				} else {
					logger.error("Cannot update process status for the pacs loan application detail record as it does not exist in the database.");
					throw new DataAccessException("Cannot update process status for the pacs loan application detail record as it does not exist in the database.");
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating process status for the pacs loan application detail " + "data to the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of updating process status for the pacs loan application detail data to the database.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		if (master == null) {
			logger.error("Trying to update status for the pacs loan application detail record which does not exist in the database.");
			throw new DataAccessException("Trying to update status for the pacs loan application detail record which does not exist in the database.");
		}
		logger.info("End: Successfully updated status for pacs loan application detail data to the database in updateStaus() method.");
	}

	public void deletePacsLoanApplicationDetailRecord(Long pacsLoanApplicationDetailId) {

		logger.info("Start: Deleting the pacs loan application detail data from the database in deletePacsLoanApplicationDetailRecord() method.");
		PacsLoanApplicationDetail master = null;
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			if (pacsLoanApplicationDetailId != null) {
				master = em.find(PacsLoanApplicationDetail.class, pacsLoanApplicationDetailId);
				if (master != null) {
					em.getTransaction().begin();
					em.remove(master);
					em.getTransaction().commit();
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of deleting the pacs loan application detail " + "data from the database.Exception thrown.");
			throw new DataAccessException("Could not commit the transaction of deleting the pacs loan application detail data from the database.", excp.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Successfully deleted the pacs loan application detail from the database in deletePacsLoanApplicationDetailRecord() method.");
	}
	
	/*
	 * This method return list of loans details based on customer id ,season and landtype
	 */
	public List<PacsLoanApplicationDetail> getLoanedLandDetails(Long customerId, Integer seasonId, Integer landTypeId){
		
		logger.info("Start: Fetching  pacs loan application detail based on customer id ,season and landtype in getLoanedLandDetails().");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = new ArrayList<PacsLoanApplicationDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM PacsLoanApplicationDetail p where p.customerId = "
					+customerId+" and p.seasonId.id = "+seasonId+" and p.landTypeId.id = "+landTypeId);
			pacsLoanApplicationDetailList = query.getResultList();
		}catch(NoResultException e){
			logger.error("There are no loans found for this customer in the specified season and landtype");
		}
		catch (Exception e) {
			logger.error("Error while retriving  pacs loan application detail records for given header from the database");
			throw new DataAccessException("Error while retriving  pacs loan application detail records for given header from the database", e.getCause());
		} 
		logger.info("End: Fetching  pacs loan application detail based on customer id ,season and landtype in getLoanedLandDetails().");
		return pacsLoanApplicationDetailList;
	}
	
	
	
	
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationDetailsForInspection(Long headerId) {
		logger.info("Start: Fetching  pacs loan application detail for given header data from the database in getPacsLoanApplicationDetails() method.");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = new ArrayList<PacsLoanApplicationDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM PacsLoanApplicationDetail p where p.headerId.id = :headerId and p.applicationStatus in (2,11) ");
			query.setParameter("headerId", headerId);

			pacsLoanApplicationDetailList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving  pacs loan application detail records for given header from the database");
			throw new DataAccessException("Error while retriving  pacs loan application detail records for given header from the database", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the pacs loan application detail for given header from the database in getPacsLoanApplicationDetails() method.");
		return pacsLoanApplicationDetailList;

	}
	//added for Inspection Authorization.
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationDetailsForInspectionWithStatus(Long headerId) {
		logger.info("Start: Fetching  pacs loan application detail for given header data from the database in getPacsLoanApplicationDetailsForInspectionWithStatus() method.");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = new ArrayList<PacsLoanApplicationDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM PacsLoanApplicationDetail p where p.headerId.id = :headerId and p.applicationStatus=11");
			query.setParameter("headerId", headerId);

			pacsLoanApplicationDetailList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving  pacs loan application detail records for given header from the database");
			throw new DataAccessException("Error while retriving  pacs loan application detail records for given header from the database", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the pacs loan application detail for given header from the database in getPacsLoanApplicationDetailsForInspectionWithStatus() method.");
		return pacsLoanApplicationDetailList;

	}
	
	public List<PacsLoanApplicationDetail> getPacsLoanApplicationDetailsEntryModify(Long headerId) {
		logger.info("Start: Fetching  pacs loan application detail for given header data from the database in getPacsLoanApplicationDetails() method.");
		List<PacsLoanApplicationDetail> pacsLoanApplicationDetailList = new ArrayList<PacsLoanApplicationDetail>();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em.createQuery("SELECT p FROM PacsLoanApplicationDetail p where p.headerId.id = :headerId and p.applicationStatus=1");
			query.setParameter("headerId", headerId);

			pacsLoanApplicationDetailList = query.getResultList();
		} catch (Exception e) {
			logger.error("Error while retriving  pacs loan application detail records for given header from the database");
			throw new DataAccessException("Error while retriving  pacs loan application detail records for given header from the database", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching the pacs loan application detail for given header from the database in getPacsLoanApplicationDetails() method.");
		return pacsLoanApplicationDetailList;

	}
}
