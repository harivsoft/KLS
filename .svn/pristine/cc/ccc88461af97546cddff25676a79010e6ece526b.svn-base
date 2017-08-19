package com.vsoftcorp.kls.service.impl;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.PacsGlMapping;
import com.vsoftcorp.kls.data.PacsGlMappingData;
import com.vsoftcorp.kls.dataaccess.IPacsGlMappingDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IPacsGlMappingService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.PacsGlMappingHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

public class PacsGlMappingService implements IPacsGlMappingService {

	private static final Logger logger = Logger.getLogger(PacsGlMappingService.class);

	IPacsGlMappingDAO dao = KLSDataAccessFactory.getPacsGlMappingDAO();

	@Override
	public void savePacsGlMappingData(PacsGlMappingData pacsGlMappingData) {
		// TODO Auto-generated method stub

		logger.info("Start : Calling pacsGLMapping dao in savePacsGlMappingDAO() method.");

		PacsGlMapping entity = null;
		try {
			entity = PacsGlMappingHelper.getPacsGlMapping(pacsGlMappingData);
			PacsGlMapping entityDb = dao.getPacsGlMapping(pacsGlMappingData.getProductId(),
					pacsGlMappingData.getPacsId());
			if (entityDb == null)
				dao.savePacsGlMapping(entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While Saving PacsGlMapping Data " + e.getMessage());
			throw new DataAccessException("Unable to save PacsglMapping data ", e.getCause());
		}

		logger.info("End :Successfully Completed calling savePacsGlMapping()");
	}

	@Override
	public void updatePacsGlMappingData(PacsGlMappingData pacsGlmappingData) {
		// TODO Auto-generated method stub

		PacsGlMapping master = null;
		try {

			master = PacsGlMappingHelper.getPacsGlMapping(pacsGlmappingData);
			PacsGlMapping entityDb = dao.getPacsGlMapping(pacsGlmappingData.getProductId(),
					pacsGlmappingData.getPacsId());
			if (entityDb != null)
				dao.updatePacsGlMapping(master);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error While Updating PacsGlMapping Data ");
			throw new KlsRuntimeException("Unable to update PacsGlMapping Data ", e.getCause());
		}
		logger.info("End :Successfully Completed calling updatePacsGlMapping()");

	}

	@Override
	public PacsGlMappingData getPacsGlMapping(Integer productId, Integer pacsId) {
		// TODO Auto-generated method stub

		logger.info("Start : Calling pacsGlMapping  dao in getpacsGlMapping() method.");
		PacsGlMappingData data = null;
		IPacsGlMappingDAO dao = KLSDataAccessFactory.getPacsGlMappingDAO();
		try {
			PacsGlMapping pacsGlMapping = dao.getPacsGlMapping(productId, pacsId);
			data = PacsGlMappingHelper.getPacsGlMappingData(pacsGlMapping);
		} catch (Exception excp) {
			logger.error("Cannot get PacsGlMapping data");
			throw new KlsRuntimeException("Cannot get PacsGlMapping data", excp);
		}
		logger.info("End : Calling PacsGlMapping  dao in getpacsGlMapping() method.");
		return data;

	}

	@Override
	public PacsGlMapping getAllPacsGlMapping() {
		// TODO Auto-generated method stub
		return null;
	}

}
