package com.vsoftcorp.kls.dataaccess.loan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.InterestCategory;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationForActivity;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationDocument;
import com.vsoftcorp.kls.business.entity.loan.LoanApplicationForGuarantorDetails;
import com.vsoftcorp.kls.business.entity.loan.PacsLoanApplication;
import com.vsoftcorp.kls.dataaccess.IInterestCategoryDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IPacsLoanApplicationDAO;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

public class PacsLoanApplicationDAO implements IPacsLoanApplicationDAO {

	private static final Logger logger = Logger.getLogger(PacsLoanApplicationDAO.class);

	@Override
	public Long saveLoanApplication(PacsLoanApplication master) {
		// TODO Auto-generated method stub
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		logger.info("Start: Calling saveLoanApplication() method in LoanApplicationDAO ..!");
		try {
			em.persist(master);
			em.getTransaction().commit();
			logger.info("Application Id :" + master.getId());
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Unable to commit the transaction of LoanApplication Master data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of saving the LoanApplication Master data.", excp.getCause());
		}
		logger.info("END:Successfully Completed saving the LoanApplicationMaster data in saveLoanApplicationDAO()");
		return master.getId();
	}

	@Override
	public void updatePacsLoanApplication(PacsLoanApplication pacsLoanApplication) {

		logger.info("Start: Updating the pacs loan application to the database in updatePacsLoanApplication() method.");
		PacsLoanApplication master = null;
		IInterestCategoryDAO dao = KLSDataAccessFactory.getInterestCategoryDAO();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Long id = pacsLoanApplication.getId();
			if (id != null) {
				master = em.find(PacsLoanApplication.class, id);
				if (master != null) {
					em.getTransaction().begin();
					master.setApplicationDate(pacsLoanApplication.getApplicationDate());
					master.setApplicationStatus(pacsLoanApplication.getApplicationStatus());
					master.setCustomerId(pacsLoanApplication.getCustomerId());
					master.setInspectionAmount(pacsLoanApplication.getInspectionAmount());
					master.setInspectionDate(pacsLoanApplication.getInspectionDate());
					master.setInspectionRemarks(pacsLoanApplication.getInspectionRemarks());
					master.setInstallmentFrequency(pacsLoanApplication.getInstallmentFrequency());
					if (LoanApplicationState.SANCTIONED.equals(pacsLoanApplication.getApplicationStatus())) {
						InterestCategory intCategory = dao.getInterestCategory(
								pacsLoanApplication.getInterestCategory(), false);
						master.setInterestCategory(intCategory);
					}
					master.setLoanPeriod(pacsLoanApplication.getLoanPeriod());
					master.setMarginalAmount(pacsLoanApplication.getMarginalAmount());
					master.setMarginalPercentage(pacsLoanApplication.getMarginalPercentage());
					master.setMasterApplicationId(pacsLoanApplication.getMasterApplicationId());
					master.setNoOfInstallments(pacsLoanApplication.getNoOfInstallments());
					master.setProduct(pacsLoanApplication.getProduct());
					master.setPurpose(pacsLoanApplication.getPurpose());
					master.setRecommendedAmount(pacsLoanApplication.getRecommendedAmount());
					master.setSanctionAmount(pacsLoanApplication.getSanctionAmount());
					master.setSanctionDate(pacsLoanApplication.getSanctionDate());
					master.setSanctionRemarks(pacsLoanApplication.getSanctionRemarks());
					master.setScrutinyAmount(pacsLoanApplication.getScrutinyAmount());
					master.setScrutinyRemarks(pacsLoanApplication.getScrutinyRemarks());
					master.setSubPurpose(pacsLoanApplication.getSubPurpose());
					master.setTotalAmountAsPerUnitCost(pacsLoanApplication.getTotalAmountAsPerUnitCost());
					master.setTotalNumberOfUnits(pacsLoanApplication.getTotalNumberOfUnits());
					master.setTotalRequestedAmount(pacsLoanApplication.getTotalRequestedAmount());
					master.setMoratoriumPrinciplePeriod(pacsLoanApplication.getMoratoriumPrinciplePeriod());
					master.setMoratoriumInterestPeriod(pacsLoanApplication.getMoratoriumInterestPeriod());
					master.setInspectionAuthRemarks(pacsLoanApplication.getInspectionAuthRemarks());
					em.getTransaction().commit();
				} else {
					logger.error("Cannot update the pacs loan application as it does not exist in the database.");
					throw new DataAccessException(
							"Cannot update the pacs loan application as it does not exist in the database.");
				}
			}
		} catch (Exception excp) {
			logger.error("Could not commit the transaction of updating the pacs loan application "
					+ "to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of updating the pacs loan application data to the database.",
					excp.getCause());
		}
		if (master == null) {
			logger.error("Trying to update the pacs loan application record which does not exist in the database.");
			throw new DataAccessException(
					"Trying to update the pacs loan application record which does not exist in the database.");
		}
		logger.info("End: Successfully updated the pacs loan application to the database in updatePacsLoanApplication() method.");
	}

	@Override
	public void saveLoanDocuments(LoanApplicationDocument document) {
		// TODO Auto-generated method stub
		logger.info("Start: Calling saveLoanDocuments() method in LoanApplicationDAO ..!");
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			em.merge(document);
			em.getTransaction().commit();

		} catch (Exception excp) {
			excp.printStackTrace();
			em.getTransaction().rollback();
			logger.error("Unable to commit the transaction of LoanApplicationDocument data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of saving the LoanApplication Document data.", excp.getCause());
		}
		logger.info("END:Successfully Completed saving the LoanApplicationDocument data in saveLoanApplicationDAO()");
	}

	@Override
	public void saveLoanActivity(LoanApplicationForActivity activity) {
		logger.info("Start: Calling saveLoanActivity() method in LoanApplicationDAO ..!: "
				+ activity.getLoanApplicationActivityId().getApplicationId());
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			em.merge(activity);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			em.getTransaction().rollback();
			logger.error("Unable to commit the transaction of LoanApplicationActivity data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of saving the LoanApplication Activity data.", excp.getCause());
		}
		logger.info("END:Successfully Completed saving the LoanApplicationActivity data in saveLoanApplicationDAO()");
	}

	@Override
	public void saveLoanGuarantor(LoanApplicationForGuarantorDetails guarantorDetails) {
		logger.info("Start: Calling saveLoanGuarantor() method in LoanApplicationDAO ..!");
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			em.merge(guarantorDetails);
			em.getTransaction().commit();
		} catch (Exception excp) {
			excp.printStackTrace();
			em.getTransaction().rollback();
			logger.error("Unable to commit the transaction of LoanApplicationGuarantor data in database "
					+ ".Exception thrown.");
			throw new DataAccessException(
					"Unable to commit the transaction of saving the LoanApplication Guarantor data.", excp.getCause());
		}
		logger.info("END:Successfully Completed saving the LoanApplicationGuarantor data in saveLoanApplicationDAO()");
	}

	@Override
	public void updateLoanDocuments(LoanApplicationDocument document) {
		// TODO Auto-generated method stub

	}

	@Override
	public PacsLoanApplication getLoanApplicationById(Long applicationId) {
		logger.info("Inside the PaceLoanAppliction get method.....");
		logger.info("applciation id..:" + applicationId);
		EntityManager em = EntityManagerUtil.getEntityManager();
		PacsLoanApplication pacsLoanApplication = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<PacsLoanApplication> cq = cb.createQuery(PacsLoanApplication.class);
			Root<PacsLoanApplication> root = cq.from(PacsLoanApplication.class);
			cq.where(cb.equal(root.get("id"), applicationId));
			CriteriaQuery<PacsLoanApplication> query = cq.select(root);
			pacsLoanApplication = em.createQuery(query).getSingleResult();

		} catch (NoResultException e) {
			logger.error("No record in database");
			throw new DataAccessException("No record found.", e.getCause());
		} catch (Exception excp) {
			logger.error("Error: While getting loan application by application id");
			throw new DataAccessException("Could not get the loan application data from database.", excp.getCause());
		}
		return pacsLoanApplication;
	}

	public List<LoanApplicationForActivity> getActivityListByApplicationId(Long applicationId) {
		logger.info("Inside the getActivityListByApplicationId get method.....");
		List<LoanApplicationForActivity> applicationForActivityList = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<LoanApplicationForActivity> cq = cb.createQuery(LoanApplicationForActivity.class);
			Root<LoanApplicationForActivity> root = cq.from(LoanApplicationForActivity.class);
			cq.where(cb.equal(root.get("loanApplicationActivityId").get("applicationId"), applicationId));
			CriteriaQuery<LoanApplicationForActivity> query = cq.select(root);
			applicationForActivityList = em.createQuery(query).getResultList();

		} catch (Exception excp) {
			logger.error("Unable to get Activity List in  getActivityListByApplicationId().."
					+ "data from the database.Exception thrown.");
			throw new DataAccessException("Unable to get Activity List data from the database.", excp.getCause());
		}
		return applicationForActivityList;
	}

	public List<LoanApplicationForGuarantorDetails> getGuarantorListByApplicationId(Long applicationId) {
		logger.info("Inside the getGuarantorListByApplicationId get method.....");
		List<LoanApplicationForGuarantorDetails> applicationForGuarantorDetailList = null;
		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<LoanApplicationForGuarantorDetails> cq = cb
					.createQuery(LoanApplicationForGuarantorDetails.class);
			Root<LoanApplicationForGuarantorDetails> root = cq.from(LoanApplicationForGuarantorDetails.class);
			cq.where(cb.equal(root.get("loanApplicationGurantorId").get("applicationId"), applicationId));
			CriteriaQuery<LoanApplicationForGuarantorDetails> query = cq.select(root);
			applicationForGuarantorDetailList = em.createQuery(query).getResultList();

		} catch (Exception excp) {
			logger.error("Unable to get Guarantor List in  getGuarantorListByApplicationId().."
					+ "data from the database.Exception thrown.");
			throw new DataAccessException("Unable to get Guarantor List data from the database.", excp.getCause());
		}
		return applicationForGuarantorDetailList;
	}

	/**
	 * This method will return all loan applications which are in the given
	 * status of the customer
	 * 
	 * @param customerId
	 *            ,loanApplicationState
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PacsLoanApplication> getLoanApplicationsByCustomerId(Long customerId,
			LoanApplicationState loanApplicationState) {
		List<PacsLoanApplication> pacsLoanApplicationList = new ArrayList<PacsLoanApplication>();
		List<LoanApplicationState> loanApplicationStateList=new ArrayList<LoanApplicationState>();
		loanApplicationStateList.add(loanApplicationState);
		logger.info("Start: Getting loan application details for inspection of CustomerId: " + customerId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			pacsLoanApplicationList = em
					.createQuery(
							"select P from PacsLoanApplication p where p.customerId=:customerId and p.applicationStatus in (:applicationStatus)")
					.setParameter("customerId", customerId).setParameter("applicationStatus", loanApplicationStateList)
					.getResultList();
		} catch (Exception e) {
			logger.error("Error : while getting pacs loan applications data for inspection");
			throw new DataAccessException("Count not get PacsLoanApplications data.", e.getCause());
		}
		logger.info("list size: " + pacsLoanApplicationList.size());
		logger.info("End: Successfully got pacs loan applications for inspection ");
		return pacsLoanApplicationList;
	}

	@Override
	public List<Map> getRateOfInterest(Integer interestCategoryId, Money amount, Integer months) {
		logger.info("Start: Fetching  roi for interestCategoryId and amount and months from the database in getRateOfInterest() method.");
		List<Map> roiList = new ArrayList();
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			Query query = em
					.createQuery("SELECT new Map(s.roi as roi,s.penalRoi as penalRoi) FROM SlabwiseInterestRate s where s.interestCategory.id = :interestCategoryId and s.fromSlab  <= :amount and s.toSlab > :amount and s.fromPeriod <= :months and s.toPeriod > :months");
			query.setParameter("interestCategoryId", interestCategoryId);
			query.setParameter("amount", amount);
			query.setParameter("months", months);
			roiList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while retriving roi from the database");
			throw new DataAccessException("Error while retriving  roi from the database", e.getCause());
		}
		logger.info("End: Fetching  roi for interestCategoryId and amount and months from the database in getRateOfInterest() method.=="
				+ roiList.size());
		return roiList;
	}

	@Override
	public void deleteActivityByApplicationId(Long applicationId, Long activityId) {
		logger.info("Start: Calling deleteActivityByApplicationId() method in LoanApplicationDAO ..!: AppplicationId: "
				+ applicationId + "Activity Id: " + activityId);
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			Query query = em
					.createQuery("delete from LoanApplicationForActivity a where a.loanApplicationActivityId.applicationId ="
							+ applicationId + " and a.loanApplicationActivityId.activityId=" + activityId);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			logger.error("Error while Deleting Activities.");
			throw new DataAccessException("Error while Deleting Activities.", e.getCause());
		}
	}

	public void deleteGuarantorByApplicationId(Long applicationId, Long guarantorId) {
		logger.info("Start: Calling deleteGuarantorByApplicationId() method in LoanApplicationDAO ..!: AppplicationId: "
				+ applicationId + "Guarantor Id: " + guarantorId);
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			Query query = em
					.createQuery("delete from LoanApplicationForGuarantorDetails g where g.loanApplicationGurantorId.applicationId ="
							+ applicationId + " and g.loanApplicationGurantorId.guarantorId=" + guarantorId);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			logger.error("Error while Deleting Guarantor.");
			throw new DataAccessException("Error while Deleting Guarantor.", e.getCause());
		}
	}

	/*
	 * @Override public List<Product> getLoanProductListByType(Integer
	 * productTypeId) { List<Product> productList = new ArrayList<Product>();
	 * try{ logger.info("Product Type Id is: "+productTypeId); EntityManager em
	 * = EntityManagerUtil.getEntityManager(); CriteriaBuilder cb =
	 * em.getCriteriaBuilder(); CriteriaQuery<Product> cq =
	 * cb.createQuery(Product.class); Root<Product> root =
	 * cq.from(Product.class); Join<Product,ProductType> productType =
	 * root.join("productType", JoinType.INNER);
	 * cq.where(cb.equal(productType.get("id"), productTypeId));
	 * CriteriaQuery<Product> query = cq.select(root); productList =
	 * em.createQuery(query).getResultList(); }catch (Exception excp) {
	 * logger.error("Could not commit the transaction of saving the charges " +
	 * "data to the database.Exception thrown."); throw new DataAccessException(
	 * "Could not commit the transaction of saving the charges data to the database."
	 * , excp.getCause()); } finally{ EntityManagerUtil.closeSession(); }
	 * logger.info(
	 * "End: Successfully saved the charges to the database in saveCharges() method."
	 * ); return productList; }
	 */

	public List<LoanApplicationDocument> getDocumentListByApplicationId(Long applicationId) {

		logger.info("Inside the getDocumentListByApplicationId() method. In PacsLoanApplicationDAO ");

		List<LoanApplicationDocument> applicationForDocumentList = null;

		EntityManager em = EntityManagerUtil.getEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<LoanApplicationDocument> cq = cb.createQuery(LoanApplicationDocument.class);
			Root<LoanApplicationDocument> root = cq.from(LoanApplicationDocument.class);
			cq.where(cb.equal(root.get("applicationId"), applicationId));
			CriteriaQuery<LoanApplicationDocument> query = cq.select(root);
			applicationForDocumentList = em.createQuery(query).getResultList();
		} catch (Exception excp) {
			logger.error("Unable to get Document List in  getDocumentListByApplicationId().."
					+ "data from the database.Exception thrown.");
			throw new DataAccessException("Unable to get Document List data from the database.", excp.getCause());
		}
		logger.info("END: Successfully Completed Getting Documnet List ");

		return applicationForDocumentList;
	}

	public void deleteDocumentsById(Long documentId) {
		logger.info("Start: Calling deleteDocumentsByApplicationId() method in PacsLoanApplicationDAO ");

		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			Query query = em.createQuery("delete from LoanApplicationDocument d where d.id=" + documentId);
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			logger.error("Error while Deleting Documents.");
			throw new DataAccessException("Error while Deleting Documents.", e.getCause());
		}

		logger.info("END: Successfully Completed Deleting Documnet Data");
	}
	
	@Override
	public List<PacsLoanApplication> getLoanApplicationsByCustomerId(
			Long customerId, LoanApplicationState loanApplicationState,String applicationType) {
		List<PacsLoanApplication> pacsLoanApplicationList = new ArrayList<PacsLoanApplication>();
		List<LoanApplicationState> loanApplicationStateList=new ArrayList<LoanApplicationState>();
		loanApplicationStateList.add(loanApplicationState);
		if(loanApplicationState.getValue().equals(1) && applicationType.equals("E")){
		loanApplicationStateList.add(LoanApplicationState.getType(7));
		loanApplicationStateList.add(LoanApplicationState.getType(12));
		}
		/*if(loanApplicationState.getValue().equals(1) && applicationType.equals("I")){
			loanApplicationStateList.add(LoanApplicationState.getType(12));
		}*/
		if(loanApplicationState.getValue().equals(3) &&  applicationType.equals("S")){
		loanApplicationStateList.add(LoanApplicationState.getType(9));
		loanApplicationStateList.add(LoanApplicationState.getType(11));
		}
		if(loanApplicationState.getValue().equals(3) &&  applicationType.equals("I")){
		loanApplicationStateList.add(LoanApplicationState.getType(8));
		loanApplicationStateList.add(LoanApplicationState.getType(13));	
		loanApplicationStateList.add(LoanApplicationState.getType(1));
		}
		logger.info("Start: Getting loan application details for inspection of CustomerId: " + customerId);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			pacsLoanApplicationList = em
					.createQuery(
							"select P from PacsLoanApplication p where p.customerId=:customerId and p.applicationStatus in (:applicationStatus)")
					.setParameter("customerId", customerId).setParameter("applicationStatus", loanApplicationStateList)
					.getResultList();
		} catch (Exception e) {
			logger.error("Error : while getting pacs loan applications data for inspection");
			throw new DataAccessException("Count not get PacsLoanApplications data.", e.getCause());
		}
		logger.info("list size: " + pacsLoanApplicationList.size());
		logger.info("End: Successfully got pacs loan applications for inspection ");
		return pacsLoanApplicationList;
	}
	
	@Override
	public List<PacsLoanApplication> getLoanApplicationsByCustomerIdAndLoanType(
			Long customerId, LoanApplicationState loanApplicationState,String applicationType, String loanType) {
		List<PacsLoanApplication> pacsLoanApplicationList = new ArrayList<PacsLoanApplication>();
		List<LoanApplicationState> loanApplicationStateList=new ArrayList<LoanApplicationState>();
		loanApplicationStateList.add(loanApplicationState);
		if(loanApplicationState.getValue().equals(1) && applicationType.equals("E")){
		loanApplicationStateList.add(LoanApplicationState.getType(7));
		loanApplicationStateList.add(LoanApplicationState.getType(12));
		}
		if(loanApplicationState.getValue().equals(3) &&  applicationType.equals("S")){
		loanApplicationStateList.add(LoanApplicationState.getType(9));
		loanApplicationStateList.add(LoanApplicationState.getType(11));
		}
		if(loanApplicationState.getValue().equals(3) &&  applicationType.equals("I")){
		loanApplicationStateList.add(LoanApplicationState.getType(8));
		loanApplicationStateList.add(LoanApplicationState.getType(13));	
		loanApplicationStateList.add(LoanApplicationState.getType(1));
		}
		logger.info("Start: Getting loan application details for inspection of CustomerId: " + customerId +" loanType : "+loanType);
		try {
			EntityManager em = EntityManagerUtil.getEntityManager();
			pacsLoanApplicationList = em
					.createQuery(
							"select l from PacsLoanApplication l, Product p, ProductType pt  where l.customerId=:customerId and " +
							"l.applicationStatus in (:applicationStatus) and l.product.id = p.id and pt.loanType =:loanType and " +
							"p.productType.id = pt.id ")
					.setParameter("customerId", customerId).setParameter("applicationStatus", loanApplicationStateList).setParameter("loanType", loanType)
					.getResultList();
		} catch (Exception e) {
			logger.error("Error : while getting pacs loan applications data for inspection");
			throw new DataAccessException("Count not get PacsLoanApplications data.", e.getCause());
		}
		logger.info("list size: " + pacsLoanApplicationList.size());
		logger.info("End: Successfully got pacs loan applications for inspection ");
		return pacsLoanApplicationList;
	}
}