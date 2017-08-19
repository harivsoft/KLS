package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.SeasonParameter;
import com.vsoftcorp.kls.data.PacsData;
import com.vsoftcorp.kls.data.SeasonParameterData;
import com.vsoftcorp.kls.dataaccess.ISeasonParameterDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IPacsService;
import com.vsoftcorp.kls.service.ISeasonParameterService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.helper.SeasonParameterHelper;

/**
 * 
 * @author a1605
 * 
 */
public class SeasonParameterService implements ISeasonParameterService {

	private static final Logger logger = Logger.getLogger(SeasonParameterService.class);

	@Override
	public void saveSeasonParameter(SeasonParameterData seasonParameterData) {
		logger.info("Start : Calling SeasonParameter master dao in saveSeasonParameter() method.");
		
		ISeasonParameterDAO dao = KLSDataAccessFactory.getSeasonParameterDAO();
		SeasonParameter dbMaster = null;
		List<PacsData> pacsMasterDataList = new ArrayList<PacsData>();
		try {
			Integer pacsId=0;
			String branchId=seasonParameterData.getUserDetails().getBranchId();
			if(seasonParameterData.getUserDetails().getPacsUserType().equals("H"))
				branchId=null;
			logger.info("branch id====="+branchId);
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
			if (branchId != null && branchId != "")
				pacsMasterDataList = pacsMasterService.getAllPacsByBranch(Integer.parseInt(branchId));
			else{
				branchId="0";
				pacsMasterDataList = pacsMasterService.getAllPacsByBranch(Integer.parseInt(branchId));
			}
			for(PacsData data:pacsMasterDataList){
			SeasonParameter master = SeasonParameterHelper.getSeasonParamter(seasonParameterData);
			pacsId=Integer.parseInt(data.getId());
			dbMaster = dao.getSeasonParameter(master.getSeason().getId(), master.getCrop().getId(), pacsId);
			if (dbMaster == null) {
				Pacs pacs=new Pacs();
				pacs.setId(pacsId);
				master.setPacs(pacs);
				dao.saveSeasonParameter(master);
			}
			}
		} catch (Exception excp) {
			logger.error("SeasonParameter master data cannot be saved");
			throw new KlsRuntimeException("Crop Insurance Parameter master data cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("Crop Insurance Parameter master data already exists");
			throw new KlsRuntimeException("Crop Insurance Parameter master data already exists");
		}
		logger.info("End : Calling SeasonParameter master dao in saveSeasonParameter() method.");
	}

	@Override
	public String updateSeasonParameter(SeasonParameterData seasonParameterData) {
		logger.info("Start : Calling SeasonParameter master dao in updateSeasonParameter() method.");
		SeasonParameter master = SeasonParameterHelper.getSeasonParamter(seasonParameterData);
		ISeasonParameterDAO dao = KLSDataAccessFactory.getSeasonParameterDAO();
		SeasonParameter dbMaster = null;
		String returnMsg="";
		try {
			returnMsg=dao.updateSeasonParameter(master);
		} catch (Exception excp) {
			logger.error("Crop Insurance Parameter master data cannot be saved");
			throw new KlsRuntimeException("Crop Insurance Parameter master data cannot be saved", excp);
		}
		logger.info("End : Calling SeasonParameter master dao in updateSeasonParameter() method.");
		return returnMsg;
	}

	@Override
	public SeasonParameterData getSeasonParameter(Long seasonId, Integer cropId,Integer pacsId) {
		logger.info("Start : Calling SeasonParameter master dao in getSeasonParameter() method.");
		SeasonParameterData data = null;
		ISeasonParameterDAO dao = KLSDataAccessFactory.getSeasonParameterDAO();
		try {
			SeasonParameter seasonParameter = dao.getSeasonParameter(seasonId, cropId,pacsId);
			if(seasonParameter!=null)
			data = SeasonParameterHelper.getSeasonParamterData(seasonParameter);
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Cannot get Season parameter data");
			throw new KlsRuntimeException("Cannot get Crop Insurance parameter data", excp);
		}
		logger.info("End : Calling SeasonParameter master dao in getSeasonParameter() method.");
		return data;
	}
	
	@Override
	public SeasonParameterData getSeasonParameters(Long seasonId, Integer cropId) {
		logger.info("Start : Calling SeasonParameter master dao in getSeasonParameter() method.");
		SeasonParameterData data = null;
		ISeasonParameterDAO dao = KLSDataAccessFactory.getSeasonParameterDAO();
		List<SeasonParameter> seasonParameter=new ArrayList<SeasonParameter>();
		try {
			seasonParameter = dao.getSeasonParameters(seasonId, cropId);
			data = SeasonParameterHelper.getSeasonParamterData(seasonParameter.get(0));
		} catch (Exception excp) {
			logger.error("Cannot get Season parameter data");
			throw new KlsRuntimeException("Cannot get Crop Insurance parameter data", excp);
		}
		logger.info("End : Calling SeasonParameter master dao in getSeasonParameter() method.");
		return data;
	}

	@Override
	public void updateSeasonParameters(SeasonParameterData seasonParameterData) {
		logger.info("Start : Calling SeasonParameter master dao in updateSeasonParameter() method.");
		
		ISeasonParameterDAO dao = KLSDataAccessFactory.getSeasonParameterDAO();
		SeasonParameter dbMaster = null;
		//List<PacsData> pacsMasterDataList = new ArrayList<PacsData>();
		Integer pacsId=0;
		try {
			String branchId=seasonParameterData.getUserDetails().getBranchId();
			IPacsService pacsMasterService = KLSServiceFactory.getPacsService();
			//if (branchId != null && branchId != "")
			//	pacsMasterDataList = pacsMasterService.getAllPacsByBranch(Integer.parseInt(branchId));
			List<SeasonParameter> seasonParameter = dao.getSeasonParameters(Long.parseLong(seasonParameterData.getSeasonId()), Integer.parseInt(seasonParameterData.getCropId()));
			for(SeasonParameter data:seasonParameter){
				SeasonParameter master = SeasonParameterHelper.getSeasonParamter(seasonParameterData);
				master.setId(data.getId());
			   Pacs pacs=new Pacs();
			   pacs.setId(data.getPacs().getId());
			   master.setPacs(pacs);
				dao.updateSeasonParameter(master);
			}
		} catch (Exception excp) {
			logger.error("Crop Insurance Parameter master data cannot be saved");
			throw new KlsRuntimeException("Crop Insurance Parameter master data cannot be saved", excp);
		}
		logger.info("End : Calling SeasonParameter master dao in updateSeasonParameter() method.");
	}

}
