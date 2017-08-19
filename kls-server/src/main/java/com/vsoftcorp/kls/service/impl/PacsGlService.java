package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.PacsGl;
import com.vsoftcorp.kls.data.PacsGlData;
import com.vsoftcorp.kls.dataaccess.IPacsGlDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IPacsGlService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.PacsGlHelper;

/**
 * 
 * @author a9152
 * 
 */

public class PacsGlService implements IPacsGlService {

	private static final Logger logger = Logger.getLogger(PacsGlService.class);

	/**
	 * 
	 */
	public void savePacsGl(PacsGlData pacsGlData) {

		logger.info("Start : Calling GL master dao in saveGL() method.");
		// get the GL master dao.
		IPacsGlDAO dao = KLSDataAccessFactory.getPacsGlDAO();
		// get the entity pojo.
		PacsGl master = PacsGlHelper.getPacsGl(pacsGlData);
		PacsGl dbMaster = null;
		try {
			dbMaster = dao.getPacsGl(master,true);
			// if GL code does not exist in db, then save.
			if (dbMaster == null) {
				dao.savePacsGl(master);
			}
		} catch (Exception excp) {
			logger.error("GL master data cannot be saved");
			throw new KlsRuntimeException(
					"GL master data cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("GL master data already exists");
			throw new KlsRuntimeException(
					"GL master data already exists");
		}
		logger.info("End : Calling GL master dao in saveGL() method.");
	}

	/**
	 * This method checks for GL code in the db. If exists, then update
	 * the GL data to the database. Else throw the exception.
	 * 
	 * @param gLMasterData
	 */
	public void updatePacsGl(PacsGlData pacsGlData) {

		logger.info("Start : Calling GL master dao in updateGL() method.");
		// get the GL master dao.
		IPacsGlDAO dao = KLSDataAccessFactory.getPacsGlDAO();
		PacsGl master = PacsGlHelper.getPacsGl(pacsGlData);
		// update the GL code to the db.
		try {
			dao.updatePacsGl(master);
		} catch (Exception excp) {
			logger.error("GL data cannot be updated as GL code does not exist");
			throw new KlsRuntimeException(
					"GL data cannot be updated as GL code does not exist",
					excp.getCause());
		}
		logger.info("End : Calling GL master dao in updateGL() method.");
	}

	/**
	 * Retrieves all GLs from the database.
	 * 
	 */
	public List<PacsGlData> getAllPacsGls() {

		logger.info("Start : Calling GL master dao in getAllGLs() method.");
		// get the GL master dao.
		IPacsGlDAO dao = KLSDataAccessFactory.getPacsGlDAO();
		List<PacsGlData> pacsGlMasterDataList = new ArrayList<PacsGlData>();
		PacsGlData masterData  = new PacsGlData();
		try {
			List<PacsGl> gLMasterList = dao.getAllPacsGls(false);
			for (PacsGl master : gLMasterList) {
				masterData = PacsGlHelper.getPacsGlData(master);
				//masterData.setGlCode(master.getGlCode());
				pacsGlMasterDataList.add(masterData);
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the GL master records");
			throw new KlsRuntimeException(
					"Error in retrieving all the GL master records",
					excp.getCause());
		}
		logger.info("End : Calling GL master dao in getAllGLs() method.");
		return pacsGlMasterDataList;
	}
}
