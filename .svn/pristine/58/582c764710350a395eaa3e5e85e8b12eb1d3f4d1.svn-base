package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.data.BranchData;
import com.vsoftcorp.kls.data.PacsData;
import com.vsoftcorp.kls.dataaccess.IBranchDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IVillageDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IPacsService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.BranchHelper;
import com.vsoftcorp.kls.service.helper.PacsHelper;

/**
 * 
 * @author a9153
 * 
 */

public class PacsService implements IPacsService {

	private static final Logger logger = Logger.getLogger(PacsService.class);

	/**
	 * This method saves PACS to the database if it does exist already.
	 */
	public void savePacs(PacsData thePacsData) {

		logger.info("Start : Calling pacs dao in savePacs() method.");
		// get the pacs dao.
		IPacsDAO dao = KLSDataAccessFactory.getPacsDAO();
		IBranchDAO branchDao = KLSDataAccessFactory.getBranchDAO();
		IVillageDAO villageDao = KLSDataAccessFactory.getVillageDAO();
		// get the entity pojo.
		Pacs master = PacsHelper.getPacs(thePacsData);
		Pacs dbMaster = null;
		Branch branch = null;
		Village village = null;
		try {
			village = villageDao.getVillage(master.getVillage());
			if (village != null) {
				master.setVillage(village);
				branch = branchDao.getBranch(master.getBranch());
				if (branch != null) {
					master.setBranch(branch);

					dbMaster = dao.getPacs(master);
					// if pacs code does not exist in db, then save.
					if (dbMaster == null) {
						dao.savePacs(master);
					}

				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Pacsdata cannot be saved");
			throw new KlsRuntimeException("Pacsdata cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("Pacsdata already exists");
			throw new KlsRuntimeException("Pacsdata already exists");
		}
		if (branch == null) {
			logger.error("Pacs cannot be saved as branch does not exist");
			throw new KlsRuntimeException(
					"Pacs cannot be saved as branch does not exist");
		}
		if (village == null) {
			logger.error("Pacs cannot be saved as village does not exist");
			throw new KlsRuntimeException(
					"Pacs cannot be saved as village does not exist");
		}

		logger.info("End : Calling pacs dao in savePacs() method.");
	}

	/**
	 * This method checks for pacs code in the db. If exists, then update the
	 * pacs data to the database. Else throw the exception.
	 * 
	 * @param pacsData
	 */
	public void updatePacs(PacsData pacsData) {

		logger.info("Start : Calling pacs dao in updatePacs() method.");
		// get the pacs dao.
		IPacsDAO dao = KLSDataAccessFactory.getPacsDAO();
		IBranchDAO branchDao = KLSDataAccessFactory.getBranchDAO();
		IVillageDAO villageDao = KLSDataAccessFactory.getVillageDAO();
		logger.info("testing land validation::"+pacsData.getLandValidation());
		Pacs master = PacsHelper.getPacs(pacsData);
		logger.info("after setting value::"+master.getLandValidation());
		// update the pacs code to the db.
		try {
			master.setBranch(branchDao.getBranch(master.getBranch()));
			master.setVillage(villageDao.getVillage(master.getVillage()));
			dao.updatePacs(master);
		} catch (Exception excp) {
			logger.error("Pacs data cannot be updated as pacs code does not exist");
			throw new KlsRuntimeException(
					"Pacs data cannot be updated as pacs code does not exist",
					excp.getCause());
		}
		logger.info("End : Calling pacs dao in updatePacs() method.");
	}

	/**
	 * Retrieves all pacs from the database.
	 * 
	 */
	public List<PacsData> getAllPacs() {

		logger.info("Start : Calling pacs dao in getAllPacs() method.");
		// get the pacsdao.
		IPacsDAO dao = KLSDataAccessFactory.getPacsDAO();
		List<PacsData> pacsDataList = new ArrayList<PacsData>();
		try {
			List<Pacs> pacsMasterList = dao.getAllPacs();
			if (pacsMasterList.size() > 0) {
				for (Pacs masterData : pacsMasterList) {
					pacsDataList.add(PacsHelper.getPacsData(masterData));
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error in retrieving all the pacs records");
			throw new KlsRuntimeException(
					"Error in retrieving all the pacs records", excp.getCause());
		}
		logger.info("End : Calling pacs dao in getAllPacs() method.");
		return pacsDataList;
	}

	@Override
	public List<PacsData> getAllPacsByBranch(Integer branchId) {

		logger.info("Start : Calling pacs dao in getAllPacsByBranch() method.");
		// get the pacsdao.
		IPacsDAO dao = KLSDataAccessFactory.getPacsDAO();
		List<PacsData> pacsDataList = new ArrayList<PacsData>();
		List<Pacs> pacsMasterList=new ArrayList<Pacs>();
		logger.info("branch id in service==="+branchId);
		try {
			if(branchId!=0)
			pacsMasterList = dao.getAllPacsByBranch(branchId);
			else
			 pacsMasterList = dao.getAllPacs();
			if (pacsMasterList.size() > 0) {
				for (Pacs masterData : pacsMasterList) {
					pacsDataList.add(PacsHelper.getPacsData(masterData));
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error in retrieving all the pacs records based on branch");
			throw new KlsRuntimeException(
					"Error in retrieving all the pacs records based on branch", excp.getCause());
		}
		logger.info("End : Calling pacs dao in getAllPacsByBranch() method.");
		return pacsDataList;
	}

	@Override
	public PacsData getPacsByPacId(Integer pacsId) {
		// TODO Auto-generated method stub
	
		logger.info("Start : Calling pacs dao in getPacsByPacId() method."+pacsId);
	
		Pacs entity=new Pacs();
		PacsData data=new PacsData();
		IPacsDAO dao=KLSDataAccessFactory.getPacsDAO();
		try
		{
		if(pacsId!=null)
		entity=dao.getPacsByPacId(pacsId);
		
		if(entity!=null)
		data=PacsHelper.getPacsData(entity);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Error in retrieving  pac record based on pacId");
			throw new KlsRuntimeException(
					"Error in retrieving  pac record based on pacId", e.getCause());

		}
		logger.info("End : Calling pacs dao in getPacsByPacId() method.");

		return data;
	}
	
	@Override
	public BranchData getBranchByPacId(Integer pacsId) {
		// TODO Auto-generated method stub
	
		logger.info("Start : Calling pacs dao in getPacsByPacId() method."+pacsId);
	
		Pacs pacs=new Pacs();
		BranchData branch = new BranchData();
		IPacsDAO dao=KLSDataAccessFactory.getPacsDAO();
		IBranchDAO bdao = KLSDataAccessFactory.getBranchDAO();
		try
		{
		if(pacsId!=null)
			pacs=dao.getPacsByPacId(pacsId);
		
		if(pacs!=null)
			branch = BranchHelper.getBranchData(bdao.getBranch(pacs.getBranch()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Error in retrieving  branch record based on pacId");
			throw new KlsRuntimeException(
					"Error in retrieving  branch record based on pacId", e.getCause());

		}
		logger.info("End : Calling pacs dao in getPacsByPacId() method.");

		return branch;
	}
}
