package com.vsoftcorp.kls.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.business.entities.LandType;
import com.vsoftcorp.kls.business.entities.ScaleOfFinance;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.business.entities.Taluka;
import com.vsoftcorp.kls.data.ScaleOfFinanceData;
import com.vsoftcorp.kls.dataaccess.ICropDAO;
import com.vsoftcorp.kls.dataaccess.IDistrictDAO;
import com.vsoftcorp.kls.dataaccess.ILandTypeDAO;
import com.vsoftcorp.kls.dataaccess.IScaleOfFinanceDAO;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.dataaccess.ITalukaDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IScaleOfFinanceService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.ScaleOfFinanceHelper;

/**
 * 
 * @author a9152
 * 
 */
public class ScaleOfFinanceService implements IScaleOfFinanceService {

	private static final Logger logger = Logger
			.getLogger(ScaleOfFinanceService.class);

	/**
	 * This method checks for scale of finance code in the db. If exists, then
	 * throw an exception. Else save it to the database.
	 * 
	 * @param scaleOfFinanceData
	 */
	public void saveScaleOfFinance(ScaleOfFinanceData scaleOfFinanceData) {

		logger.info("Start : Calling scale of finance master dao in saveScaleOfFinance() method.");
		// get the scale of finance dao.
		IScaleOfFinanceDAO dao = KLSDataAccessFactory.getScaleOfFinanceDAO();
		ScaleOfFinance master = ScaleOfFinanceHelper
				.getScaleOfFinance(scaleOfFinanceData);
		// get all the dao's
		IDistrictDAO districtDao = KLSDataAccessFactory.getDistrictDAO();
		ITalukaDAO talukaDao = KLSDataAccessFactory.getTalukaDAO();
		ISeasonDAO seasonDao = KLSDataAccessFactory.getSeasonDAO();
		ICropDAO cropDao = KLSDataAccessFactory.getCropDAO();
		ILandTypeDAO landTypeDao = KLSDataAccessFactory.getLandTypeDAO();
		District district = null;
		Taluka taluka = null;
		Season season = null;
		Crop crop = null;
		LandType landType = null;

		try {
			// check the district id if it exists in the db.
			String areaType = master.getAreaType();
			if (areaType.equals("D")) {
				District dist = new District();
				dist.setId(master.getAreaTypeId());
				district = districtDao.getDistrict(dist);
			} else {
				Taluka tal = new Taluka();
				tal.setId(master.getAreaTypeId());
				taluka = talukaDao.getTaluka(tal);
			}
			if (district != null || taluka != null) {

				season = seasonDao.getSeason(master.getSeason());
				System.out.println("season : " + season.getId());
				if (season != null) {
					master.setSeason(season);
					crop = cropDao.getCrop(master.getCrop());
					if (crop != null) {
						master.setCrop(crop);
						landType = landTypeDao
								.getLandType(master.getLandType());
						if (landType != null) {
							master.setLandType(landType);
							// check the scale of finance data
							// against the db.
							if (master.getId() == null) {
								dao.saveScaleOfFinance(master);
							}
						}
					}
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Scale of finance already exists");
			throw new KlsRuntimeException("Scale of finance already exists",
					excp.getCause());
		}
		logger.info("End : Calling scale of finance dao in saveScaleOfFinance() method.");
	}

	/**
	 * This method checks for scale of finance code in the db. If exists, then
	 * update the scale of finance data to the database. Else throw the
	 * exception.
	 */
	public void updateScaleOfFinance(ScaleOfFinanceData scaleOfFinanceData) {

		logger.info("Start : Calling scale of finance master dao in updateScaleOfFinance() method.");
		// get the scale of finance master dao.
		IScaleOfFinanceDAO dao = KLSDataAccessFactory.getScaleOfFinanceDAO();
		ScaleOfFinance master = ScaleOfFinanceHelper
				.getScaleOfFinance(scaleOfFinanceData);
		// update the scale of finance data to the db.
		try {
			dao.updateScaleOfFinance(master);
		} catch (Exception excp) {
			logger.error("Scale Of Finance data cannot be updated as scale of finance id does not exist");
			throw new KlsRuntimeException(
					"Scale Of Finance data cannot be updated as scale of finance id does not exist",
					excp.getCause());
		}
		logger.info("End : Calling scale of finance dao in updateScaleOfFinance() method.");
	}

	/**
	 * This method will fetch all the scale of finance master records from the
	 * db and return to the client.
	 */
	public List<ScaleOfFinanceData> getAllScaleOfFinances() {

		logger.info("Start : Calling scale of finance dao in getAllScaleOfFinances() method.");
		// get the scale of finance dao.
		IScaleOfFinanceDAO dao = KLSDataAccessFactory.getScaleOfFinanceDAO();
		List<ScaleOfFinanceData> scaleOfFinanceDataList = new ArrayList<ScaleOfFinanceData>();
		try {
			List<ScaleOfFinance> scaleofFinMasterList = dao
					.getAllScaleOfFinances();
			for (ScaleOfFinance master : scaleofFinMasterList) {
				scaleOfFinanceDataList.add(ScaleOfFinanceHelper
						.getScaleOfFinanceData(master));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the scale of finance records");
			throw new KlsRuntimeException(
					"Error in retrieving all the scale of finance records",
					excp.getCause());
		}
		logger.info("End : Calling scale of finance dao in getAllScaleOfFinances() method.");
		return scaleOfFinanceDataList;
	}

	/**
	 * This method will fetch the scale of finance amount from the db and return
	 * to the client.
	 */
	public String getScaleOfFinanceAmount(Long seasonId, Integer cropId,
			Integer landTypeId, BigDecimal landArea, Integer pacsId) {

		logger.info("Start : Calling scale of finance dao in getScaleOfFinanceAmount() method.");
		BigDecimal totalAmount = new BigDecimal("0.00");
		String amountAsPerSOF = null;
		// get the scale of finance dao.
		IScaleOfFinanceDAO dao = KLSDataAccessFactory.getScaleOfFinanceDAO();
		try {
			BigDecimal scaleOfFinanceAmount = dao.getScaleOfFinanceAmount(
					seasonId, cropId, landTypeId, pacsId);
			if (scaleOfFinanceAmount != null) {
				totalAmount = landArea.multiply(scaleOfFinanceAmount);
				amountAsPerSOF = totalAmount.setScale(2,
						BigDecimal.ROUND_HALF_UP).toString();
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving the scale of finance amount");
			throw new KlsRuntimeException(
					"Error in retrieving all the scale of finance amount",
					excp.getCause());
		}
		logger.info("End : Calling scale of finance dao in getScaleOfFinanceAmount() method.");
		return amountAsPerSOF;
	}

	@Override
	public ScaleOfFinanceData getScaleOfFinance(String areaType,
			Integer areaTypeCode, Long seasonId, Integer landTypeId,
			Integer cropId) {
		ScaleOfFinanceData data = new ScaleOfFinanceData();
		logger.info("Start:  Calling scale of finance dao in getScaleOfFinance() method.");
		try {
			ScaleOfFinance scaleOfFinance=KLSDataAccessFactory.getScaleOfFinanceDAO().getScaleOfFinance(areaType,areaTypeCode,seasonId,landTypeId,cropId);
			if(scaleOfFinance!=null)
			data=ScaleOfFinanceHelper.getScaleOfFinanceData(scaleOfFinance);
			else 
			return null;
		} catch (Exception exception) {
			logger.error("Error in retrieving the scale of finance ");
			throw new KlsRuntimeException(
					"Error in retrieving  the scale of finance ",
					exception.getCause());
		}
		logger.info("End:  Calling scale of finance dao in getScaleOfFinance() method.");
		return data;
	}
}
