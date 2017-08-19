package com.vsoftcorp.kls.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.SanctionedComponentDetail;
import com.vsoftcorp.kls.data.DeleteSanctionedComponent;
import com.vsoftcorp.kls.data.SanctionedComponentData;
import com.vsoftcorp.kls.dataaccess.ISanctionedComponentDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ISanctionedComponentService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.SanctionedComponentHelper;

public class SanctionedComponentService implements ISanctionedComponentService {
	private static final Logger logger = Logger
			.getLogger(SanctionedComponentService.class);

	@Override
	public void saveSanctionedComponent(SanctionedComponentData data) {

		logger.info("Start : Calling Sanctioned Component dao in saveSanctionedComponent() method.");
		ISanctionedComponentDAO dao = KLSDataAccessFactory
				.getSanctionedComponentDAO();
		List<SanctionedComponentDetail> componentsList = SanctionedComponentHelper
				.getSanctionedComponentDetail(data);
		try {
			for (SanctionedComponentDetail componentDetail : componentsList) {
				if (componentDetail.getId() == null)
					dao.saveSanctionedComponent(componentDetail);
				else
					dao.updateSanctionedComponent(componentDetail);
			}
			List<DeleteSanctionedComponent> deleteComponentsList = data
					.getDeleteSanctionedComponent();
			if (deleteComponentsList != null && !deleteComponentsList.isEmpty()) {
				for (DeleteSanctionedComponent deleteComponent : deleteComponentsList) {
					dao.deleteComponentDetail(deleteComponent.getId());
				}
			}

		} catch (Exception excp) {
			logger.error("Sanctioned Component Details master data cannot be saved");
			throw new KlsRuntimeException(
					"Sanctioned Component Details master data cannot be saved",
					excp);
		}
		logger.info("End : Calling Sanctioned Component Details master dao in saveSanctionedComponent() method.");

	}

	@Override
	public SanctionedComponentData getSanctionedComponentsBySeasonId(
			Long seasonId) {

		logger.info("Start : Calling SanctionedComponentDetail dao in getSanctionedComponentsBySeasonId() method.");
		ISanctionedComponentDAO dao = KLSDataAccessFactory
				.getSanctionedComponentDAO();
		SanctionedComponentData data = new SanctionedComponentData();
		try {
			List<SanctionedComponentDetail> sanctionedComponentsList = dao
					.getSanctionedComponentsDetailsBySeasonId(seasonId);
			data = SanctionedComponentHelper
					.getSanctionedComponentData(sanctionedComponentsList);
		} catch (Exception excp) {
			logger.error("Error in retrieving all the SanctionedComponentDetail records");
			throw new KlsRuntimeException(
					"Error in retrieving all the SanctionedComponentDetail records",
					excp.getCause());
		}
		logger.info("End : Calling SanctionedComponentDetail dao in getSanctionedComponentsBySeasonId() method.");
		return data;
	}

}
