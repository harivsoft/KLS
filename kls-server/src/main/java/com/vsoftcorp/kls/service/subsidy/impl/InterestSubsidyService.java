package com.vsoftcorp.kls.service.subsidy.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.subsidy.InterestSubsidy;
import com.vsoftcorp.kls.business.entity.subsidy.SlabwisesubsidyInterestRate;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.subsidy.IInterestSubsidyDAO;
import com.vsoftcorp.kls.dataaccess.subsidy.ISlabwisesubsidyROIDAO;
import com.vsoftcorp.kls.service.helper.subsidy.InterestSubsidyHelper;
import com.vsoftcorp.kls.service.helper.subsidy.SlabwisesubsidyROIHelper;
import com.vsoftcorp.kls.service.subsidy.IInterestSubsidyService;
import com.vsoftcorp.kls.subsidy.data.InterestSubsidyData;
import com.vsoftcorp.kls.subsidy.data.SlabwisesubsidyInterestRateData;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class InterestSubsidyService implements IInterestSubsidyService {

	private static final Logger logger = Logger.getLogger(InterestSubsidyService.class);
	
	@Override
	public void saveInterestSubsidyService(InterestSubsidyData data) {
		
		logger.info("Start: saving Interest Subsidy in saveInterestSubsidyService() method");
		IInterestSubsidyDAO dao = KLSDataAccessFactory.getInterestSubsidyDAO();
		ISlabwisesubsidyROIDAO slabdao = KLSDataAccessFactory.getSlabwisesubsidyROIDAO();
		Long subsidyId = null;
		try{
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			subsidyId=dao.saveInterestSubsidyDAO(InterestSubsidyHelper.getInterestSubsidy(data));
			List<SlabwisesubsidyInterestRateData> slabList = data.getSlabwiseRoiList();
			if(slabList != null && !slabList.isEmpty()){
				for (SlabwisesubsidyInterestRateData slabData : slabList){
					slabData.setInterestSubsidyId(subsidyId);
					slabdao.saveSlabwisesubsidyROIDAO(SlabwisesubsidyROIHelper.getSlabwisesubsidyInterestRate(slabData));
				}
			}
			em.getTransaction().commit();
		}catch(Exception excp){
			logger.error("Could not commit the transaction of saving the Interest Subsidy"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of saving the Interest Subsidy data to the database.",
					excp.getCause());
		}
		logger.info("END: saving Interest Subsidy in saveInterestSubsidyService() method");
	}

	@Override
	public void modifyInterestSubsidyService(InterestSubsidyData data) {
		
		logger.info("Start: Modifing Interest Subsidy in saveInterestSubsidyService() method");
		IInterestSubsidyDAO dao = KLSDataAccessFactory.getInterestSubsidyDAO();
		ISlabwisesubsidyROIDAO slabdao = KLSDataAccessFactory.getSlabwisesubsidyROIDAO();
		try{
			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			dao.modifyInterestSubsidyDAO(InterestSubsidyHelper.getInterestSubsidy(data));
			List<SlabwisesubsidyInterestRateData> slabList = data.getSlabwiseRoiList();
			List<SlabwisesubsidyInterestRateData> deleteList = data.getDeleteList();
			if(slabList != null && !slabList.isEmpty()){
				for (SlabwisesubsidyInterestRateData slabData : slabList){
					slabData.setInterestSubsidyId(data.getId());
					slabdao.modifySlabwisesubsidyROIDAO(SlabwisesubsidyROIHelper.getSlabwisesubsidyInterestRate(slabData));
				}
			}
			if(deleteList != null && !deleteList.isEmpty()){
				for (SlabwisesubsidyInterestRateData deleteData : deleteList){
					slabdao.removeSlabwiseSubsidyROI(SlabwisesubsidyROIHelper.getSlabwisesubsidyInterestRate(deleteData));
				}
			}
			em.getTransaction().commit();
		}catch(Exception excp){
			logger.error("Could not commit the transaction of Modifing the Interest Subsidy"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Modifing the Interest Subsidy data to the database.",
					excp.getCause());
		}
		logger.info("END: Modifing Interest Subsidy in saveInterestSubsidyService() method");
		
	}

	@Override
	public InterestSubsidyData getInterestSubsidyService(Long id) {
		logger.info("Start: Fetching Interest Subsidy in saveInterestSubsidyService() method");
		IInterestSubsidyDAO dao = KLSDataAccessFactory.getInterestSubsidyDAO();
		ISlabwisesubsidyROIDAO slabdao = KLSDataAccessFactory.getSlabwisesubsidyROIDAO();
		InterestSubsidyData interestSubsidyData = null;
		List<SlabwisesubsidyInterestRateData> slabDataList = new ArrayList<SlabwisesubsidyInterestRateData>(); 
		try{
			interestSubsidyData=InterestSubsidyHelper.getInterestSubsidyData(dao.getInterestSubsidyDAO(id));
			List<SlabwisesubsidyInterestRate> slabList = slabdao.getSlabwisesubsidyROIDAO(id);
			if(slabList != null && !slabList.isEmpty()){
				for (SlabwisesubsidyInterestRate master : slabList){
					slabDataList.add(SlabwisesubsidyROIHelper.getSlabwisesubsidyInterestRateData(master));
				}
			}
			interestSubsidyData.setSlabwiseRoiList(slabDataList);
		}catch(Exception excp){
			logger.error("Could not commit the transaction of Fetching the Interest Subsidy"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Fetching the Interest Subsidy data to the database.",
					excp.getCause());
		}
		logger.info("END: Fetching Interest Subsidy in saveInterestSubsidyService() method");
		return interestSubsidyData;
	}

	@Override
	public List<InterestSubsidyData> getAllInterestSubsidyService() {
		logger.info("Start: Fetching Interest Subsidy in getAllInterestSubsidyService() method");
		IInterestSubsidyDAO dao = KLSDataAccessFactory.getInterestSubsidyDAO();
		List<InterestSubsidyData> interestSubsidyDataList = new ArrayList<InterestSubsidyData>();
		List<InterestSubsidy> interestSubsidyList = null;
		try{
			interestSubsidyList=dao.getAllInterestSubsidyDAO();
			if(interestSubsidyList != null && !interestSubsidyList.isEmpty()){
				for (InterestSubsidy master : interestSubsidyList){
					interestSubsidyDataList.add(InterestSubsidyHelper.getInterestSubsidyData(master));
				}
			}
		}catch(Exception excp){
			excp.printStackTrace();
			logger.error("Could not  Fetch the Interest Subsidy"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could Fetch the Interest Subsidy data to the database.",
					excp.getCause());
		}
		logger.info("END: Fetching Interest Subsidy in getAllInterestSubsidyService() method");
		return interestSubsidyDataList;
	}

	@Override
	public List<InterestSubsidyData> getInterestSubsidySchemeByType(
			String subsidySchemeType) {
		logger.info("Start: Fetching Interest Subsidy in getInterestSubsidySchemeByType() method");
		IInterestSubsidyDAO dao = KLSDataAccessFactory.getInterestSubsidyDAO();
		List<InterestSubsidyData> interestSubsidyDataList = new ArrayList<>();
		List<InterestSubsidy> interestSubsidyList = new ArrayList<>();
		try{
			interestSubsidyList = dao.getInterestSubsidySchemesByType(subsidySchemeType);
			for (InterestSubsidy master : interestSubsidyList){
				interestSubsidyDataList.add(InterestSubsidyHelper.getInterestSubsidyData(master));
			}
			
		}catch(Exception excp){
			excp.printStackTrace();
			logger.error("Could not commit the transaction of Fetching the Interest Subsidy"
					+ "data to the database.Exception thrown.");
			throw new DataAccessException(
					"Could not commit the transaction of Fetching the Interest Subsidy data to the database.",
					excp.getCause());
		}
		logger.info("END: Fetching Interest Subsidy in getInterestSubsidySchemeByType() method");
		return interestSubsidyDataList;
	}

	@Override
	public boolean isLOCExistService(Integer schemeId, Long seasonId) {
		logger.info("Start: Checking whether LOC is exist in isLOCExistService() method");
		boolean status = false;
		try{
			ILineOfCreditDAO lDAO = KLSDataAccessFactory.getLineOfCreditDAO();
			status=lDAO.isLOCExistDAO(schemeId, seasonId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error while retriving  LineOfCredit records for given seasonId and schemeId from the database");
			throw new DataAccessException(
					"Error while retriving LineOfCreditrecords for given seasonId and schemeId from the database",
					e.getCause());
		}
		logger.info("END: Checking whether LOC is exist in isLOCExistService() method");
		return status;
	}

	

}
