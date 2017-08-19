package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.data.PacsLoanApplicationHeaderData;
import com.vsoftcorp.kls.dataaccess.ICropDAO;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationDetailDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationHeaderDAO;
import com.vsoftcorp.kls.dataaccess.ISchemeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IPacsLoanApplicationHeaderService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.PacsLoanApplicationHeaderHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * 
 * @author a9153
 * 
 */
public class PacsLoanApplicationHeaderService implements IPacsLoanApplicationHeaderService {

	private static final Logger logger = Logger
			.getLogger(PacsLoanApplicationHeaderService.class);

	/**
	 * This methods save the PacsLoanApplicationHeader only if not exists.
	 */
	public Long savePacsLoanApplicationHeader(PacsLoanApplicationHeaderData thePacsLoanApplicationHeaderMasterData) {

		logger.info("Start : Calling PacsLoanApplicationHeader master dao in savePacsLoanApplicationHeader() method.");

		IPacsLoanApplicationHeaderDAO dao = KLSDataAccessFactory.getPacsLoanApplicationHeaderDAO();
		PacsLoanApplicationHeader master = PacsLoanApplicationHeaderHelper.getPacsLoanApplicationHeader(thePacsLoanApplicationHeaderMasterData);
		IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
		ISchemeDAO schemeDao = KLSDataAccessFactory.getSchemeDAO();
		//ICropDAO cropDAO = KLSDataAccessFactory.getCropDAO();
		PacsLoanApplicationHeader dbMaster = null;
		
		Pacs pacs = null;
		Scheme scheme = null;
		//Crop crop = null;
		try {
			pacs = pacsDao.getPacs(master.getPacs());
			scheme = schemeDao.getScheme(master.getScheme(),false);
			//crop = cropDAO.getCrop(master.getCrop());
			if (pacs != null && scheme != null) {
				master.setPacs(pacs);
				//master.setCrop(crop);
				master.setScheme(scheme);
				dbMaster = dao.getPacsLoanApplicationHeader(master);
				if (dbMaster == null)
					dao.savePacsLoanApplicationHeader(master);
			}
		} catch (Exception excp) {
			logger.error("PacsLoanApplicationHeader master data cannot be saved");
			throw new KlsRuntimeException("PacsLoanApplicationHeader master data cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("PacsLoanApplicationHeader master data already exists");
			throw new KlsRuntimeException("PacsLoanApplicationHeader master data already exists");
		}
		if (pacs == null){
			logger.error("PacsLoanApplicationHeader cannot be saved as pacs id does not exist.");
			throw new KlsRuntimeException("PacsLoanApplicationHeader cannot be saved as pacs id does not exist.");			
		}
		if (scheme == null){
			logger.error("PacsLoanApplicationHeader cannot be saved as scheme id does not exist.");
			throw new KlsRuntimeException("PacsLoanApplicationHeader cannot be saved as scheme id does not exist.");			
		}
		/*if (crop == null){
			logger.error("PacsLoanApplicationHeader cannot be saved as crop id does not exist.");
			throw new KlsRuntimeException("PacsLoanApplicationHeader cannot be saved as crop id does not exist.");			
		}*/
		logger.info("End : Calling PacsLoanApplicationHeader master dao in savePacsLoanApplicationHeader() method.");
		return master.getId();
	}

	/**
	 * Updates a PacsLoanApplicationHeader if already exists.
	 * 
	 */
	public void updatePacsLoanApplicationHeader(PacsLoanApplicationHeaderData thePacsLoanApplicationHeaderMasterData) {
		
		logger.info("Start: Inside method updatePacsLoanApplicationHeader()");
		try {
			IPacsLoanApplicationHeaderDAO dao = KLSDataAccessFactory.getPacsLoanApplicationHeaderDAO();
			PacsLoanApplicationHeader master = PacsLoanApplicationHeaderHelper
					.getPacsLoanApplicationHeader(thePacsLoanApplicationHeaderMasterData);

			IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
			ISchemeDAO schemeDao = KLSDataAccessFactory.getSchemeDAO();
			ICropDAO cropDAO = KLSDataAccessFactory.getCropDAO();

			//master.setCrop(cropDAO.getCrop(master.getCrop()));
			master.setScheme(schemeDao.getScheme(master.getScheme(),false));
			master.setPacs(pacsDao.getPacs(master.getPacs()));
			
			dao.updatePacsLoanApplicationHeader(master);
		} catch (Exception e) {
			logger.error("Exception while updating PacsLoanApplicationHeader: " + e.getMessage());
			throw new KlsRuntimeException("Exception while saving PacsLoanApplicationHeader: ",
					e.getCause());
		}
		logger.info("End: Inside method updatePacsLoanApplicationHeader()");
	}
	
	/**
	 * Retrives all PacsLoanApplicationHeaders  from Database
	 * 
	 */
	 public List<PacsLoanApplicationHeaderData> getPacsLoanApplicationHeaders (String  processStatus,Integer pacsId,Integer branchId){
		logger.info("Start: Inside method getAllPacsLoanApplicationHeaders ()");
		IPacsLoanApplicationHeaderDAO dao = KLSDataAccessFactory.getPacsLoanApplicationHeaderDAO();
		List<PacsLoanApplicationHeaderData> pacsLoanApplicationHeaders  = new ArrayList<PacsLoanApplicationHeaderData>();
		try {
			
			List<PacsLoanApplicationHeader> pacsLoanApplicationHeaderMasterList = dao.getPacsLoanApplicationHeaders(processStatus,pacsId,branchId);
			if (pacsLoanApplicationHeaderMasterList != null
					&& !pacsLoanApplicationHeaderMasterList.isEmpty()) {
				
				pacsLoanApplicationHeaderMasterList = getHeaderApplicationsWithLoanAppEntries(pacsLoanApplicationHeaderMasterList);

				for (PacsLoanApplicationHeader masterData : pacsLoanApplicationHeaderMasterList) {
					pacsLoanApplicationHeaders
							.add(PacsLoanApplicationHeaderHelper
									.getPacsLoanApplicationHeaderData(masterData));
				}
			}
		} catch (Exception e) {
			logger.error("Error while retriving all PacsLoanApplicationHeaders  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all PacsLoanApplicationHeaders ",e.getCause());
		}		
		logger.info("End : Inside method getAllPacsLoanApplicationHeaders ()");
		return pacsLoanApplicationHeaders ;
	 }
	 
	 public void closePacsLoanApplicationHeader(PacsLoanApplicationHeaderData thePacsLoanApplicationHeaderMasterData) {

		 logger.info("Start: Inside method updatePacsLoanApplicationHeader()");
		 try {
			 IPacsLoanApplicationHeaderDAO dao = KLSDataAccessFactory.getPacsLoanApplicationHeaderDAO();
			 PacsLoanApplicationHeader master = PacsLoanApplicationHeaderHelper
					 .getPacsLoanApplicationHeader(thePacsLoanApplicationHeaderMasterData);

			 IPacsDAO pacsDao = KLSDataAccessFactory.getPacsDAO();
			 ISchemeDAO schemeDao = KLSDataAccessFactory.getSchemeDAO();
			 ICropDAO cropDAO = KLSDataAccessFactory.getCropDAO();

			 //master.setCrop(cropDAO.getCrop(master.getCrop()));
			 master.setScheme(schemeDao.getScheme(master.getScheme(),false));
			 master.setPacs(pacsDao.getPacs(master.getPacs()));

			 dao.closePacsLoanApplicationHeader(master);
		 } catch (Exception e) {
			 logger.error("Exception while updating PacsLoanApplicationHeader: " + e.getMessage());
			 throw new KlsRuntimeException("Exception while saving PacsLoanApplicationHeader: ",
					 e.getCause());
		 }
		 logger.info("End: Inside method updatePacsLoanApplicationHeader()");
	 }

	@Override
	public List<String> getAllFinancialYearsByPacId(
			String pacId) {
		 logger.info("Start: Inside method getAllPacsLoanApplicationHeadersByPacId()");
		 List<String> fiancialYearList=new ArrayList<String>();
		 try{
			fiancialYearList=KLSDataAccessFactory.getPacsLoanApplicationHeaderDAO().getAllFinancialYearsByPacId(pacId);
			 
		 }catch(Exception e)
		 {
			 logger.error("Exception while getting list of PacsLoanApplicationHeader: " + e.getMessage());
			 throw new KlsRuntimeException("Exception while getting list of PacsLoanApplicationHeader",
					 e.getCause());
		 }
		 logger.info("End: Inside method getAllPacsLoanApplicationHeadersByPacId()");
		return fiancialYearList;
	}
	
	
	public List<PacsLoanApplicationHeaderData> getPacsLoanApplicationHeadersByPacsId(Integer pacsId){
		logger.info("Start: Inside method getPacsLoanApplicationHeadersByPacsId ()");
		IPacsLoanApplicationHeaderDAO dao = KLSDataAccessFactory.getPacsLoanApplicationHeaderDAO();
		List<PacsLoanApplicationHeaderData> pacsLoanApplicationHeaders  = new ArrayList<PacsLoanApplicationHeaderData>();
		try {
			
			List<PacsLoanApplicationHeader> pacsLoanApplicationHeaderMasterList = dao.getPacsLoanApplicationHeadersByPacsId(pacsId);
			if (pacsLoanApplicationHeaderMasterList != null
					&& !pacsLoanApplicationHeaderMasterList.isEmpty()) {

				for (PacsLoanApplicationHeader masterData : pacsLoanApplicationHeaderMasterList) {
					pacsLoanApplicationHeaders
							.add(PacsLoanApplicationHeaderHelper
									.getPacsLoanApplicationHeaderData(masterData));
				}
			}
		} catch (Exception e) {
			logger.error("Error while retriving all PacsLoanApplicationHeaders  from the database");
			e.printStackTrace();
			throw new DataAccessException("Error while retriving all PacsLoanApplicationHeaders ",e.getCause());
		}		
		logger.info("End : Inside method getPacsLoanApplicationHeadersByPacsId ()");
		return pacsLoanApplicationHeaders ;
	 }
	
	 public List<PacsLoanApplicationHeader> getHeaderApplicationsWithLoanAppEntries(List<PacsLoanApplicationHeader> pacsLoanApplicationHeader){
		 
		 
		 logger.info("Start: In getHeaderApplicationsWithLoanAppEntries()");
		 List<PacsLoanApplicationHeader> withLoanEntries = new ArrayList<PacsLoanApplicationHeader>();
		 try{
			 IPacsLoanApplicationDetailDAO dao = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
			 if(pacsLoanApplicationHeader != null && !pacsLoanApplicationHeader.isEmpty()){
				 
				 for(PacsLoanApplicationHeader p : pacsLoanApplicationHeader){
					 
					 if(!dao.getPacsLoanApplicationDetailsForInspection(p.getId()).isEmpty()){
						 withLoanEntries.add(p);
					 }
					 
				 }
			 }
			 
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 logger.info("End: In getHeaderApplicationsWithLoanAppEntries()");
		 return withLoanEntries;
		 
	 }
	 
}
