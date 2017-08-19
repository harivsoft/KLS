package com.vsoftcorp.kls.service.account.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.loan.ChargesMaster;
import com.vsoftcorp.kls.data.ChargesEnumData;
import com.vsoftcorp.kls.data.ChargesMasterData;
import com.vsoftcorp.kls.data.ChargesTypeData;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IChargesMasterDAO;
import com.vsoftcorp.kls.service.account.IChargesMasterService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.ChargesMasterHelper;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.charges.ChargesType;

public class ChargesMasterService implements IChargesMasterService {
	private static final Logger logger = Logger.getLogger(ChargesMasterService.class);

	@Override
	public void saveChargesMaster(ChargesMasterData data) {

		logger.info("Start : Calling charges dao in saveChargesMaster() method.");
		// get the charges master dao.
		IChargesMasterDAO dao = KLSDataAccessFactory.getChargesMasterDAO();
		// get the entity pojo.
		ChargesMaster master = ChargesMasterHelper.getChargesMaster(data);
		ChargesMaster dbMaster = null;
		try {
			dbMaster = dao.getChargesMaster(master, false);
			// if charges code does not exist in db, then save.
			if (dbMaster == null) {
				dao.saveChargesMaster(master);
			}
		} catch (Exception excp) {
			logger.error("ChargesMasterData cannot be saved");
			throw new KlsRuntimeException("ChargesMasterData cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("ChargesMasterData already exists");
			throw new KlsRuntimeException("ChargesMasterData already exists");
		}
		logger.info("End : Calling charges dao in saveChargesMaster() method.");
	}

	@Override
	public List<ChargesMasterData> getAllCharges() {

		logger.info("Start: Fetching all the charges  data from the database in getAllCharges() method.");
		IChargesMasterDAO dao = KLSDataAccessFactory.getChargesMasterDAO();
		List<ChargesMasterData> chrgesList = new ArrayList<ChargesMasterData>();
		try {

			List<ChargesMaster> chargesMastersList = dao.getAllChargesMaster();
			for (ChargesMaster chargesMaster : chargesMastersList) {
				chrgesList.add(ChargesMasterHelper.getChargesMasterData(chargesMaster));
			}
		} catch (Exception e) {
			logger.error("Error while retriving all charges from the database");
			throw new DataAccessException("Error while retriving all charges from the database", e.getCause());
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End: Fetching all the charges  data from the database in getAllCharges() method.");
		return chrgesList;
	}

	@Override
	public ChargesEnumData getAllChargesTypeEnums() {
		logger.info("Start: Inside charges service to get all loan charges enums in getAllChargesTypeEnums()");
		ChargesEnumData chargesEnumData = new ChargesEnumData();
		try {
			List<ChargesTypeData> chargesTypeList = new ArrayList<ChargesTypeData>();
			ChargesType[] chargesTypeArray = ChargesType.values();
			for (int i = 0; i < chargesTypeArray.length; i++) {
				ChargesTypeData chargesTypeData = new ChargesTypeData();
				chargesTypeData.setValue(chargesTypeArray[i].name());
				chargesTypeData.setId(chargesTypeArray[i].getValue());
				chargesTypeList.add(chargesTypeData);
			}
			chargesEnumData.setChargesTypeList(chargesTypeList);
		} catch (Exception excp) {
			logger.error(" Exception while populating loan charges enums.");
			throw new KlsRuntimeException("Exception while populating loan charges enums.");
		}
		logger.info("End: Inside charges service to get all loan charges enums in getAllChargesTypeEnums()");
		return chargesEnumData;
	}

	@Override
	public void updateChargesMaster(ChargesMasterData data) {

		logger.info("Start: Inside method updateChargesMaster()=="+data.getId());
		try {
			IChargesMasterDAO dao = KLSDataAccessFactory.getChargesMasterDAO();
			ChargesMaster master = ChargesMasterHelper.getChargesMaster(data);
			dao.updateChargesMaster(master);
		} catch (Exception e) {
			logger.error("Exception while updating charges: " + e.getMessage());
			throw new KlsRuntimeException("Exception while updating charges: ", e.getCause());
		}
		logger.info("End: Inside method updateChargesMaster()");
	}

}
