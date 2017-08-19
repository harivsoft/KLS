package com.vsoftcorp.kls.service.helper;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.userexceptions.DataAccessException;

/**
 * 
 * @author a9153 This Class contains helper methods common to all masters
 * 
 */

public class MasterHelper {

	private static final Logger logger = Logger.getLogger(MasterHelper.class);
	private static int codeLength = 5;

	/**
	 * This method generates unique code for a given Master name
	 * 
	 * @return
	 */
	public static <T> String generateUniqueMasterCode(List<T> masterList,
			String masterName) {

		List<String> codes = new ArrayList<String>();
		if (masterList != null && !masterList.isEmpty()) {
			String masterClassName = "";
			Class noparams[] = {};
			masterClassName = masterList.get(0).getClass().getName();
			String codeGetterMethodName = resolveCodeMethodForMaster(masterClassName);
			// String codeGetterMethodName = "getPacsCode";
			try {
				Class cls = masterList.get(0).getClass();
				Method method = cls.getDeclaredMethod(codeGetterMethodName,
						noparams);

				for (T item : masterList) {
					codes.add((String) method.invoke(item, null));
				}
			} catch (Exception e) {
				logger.error("Error while generating unique code:"
						+ e.getMessage());
				throw new DataAccessException(
						"Error while generating unique code.", e.getCause());
			}
		}

		if (masterName == null)
			masterName = "";
		// remove all spaces
		masterName = masterName.replaceAll(" ", "");
		// remove all vowels
		masterName = masterName.replaceAll("[aAeEiIoOuU]", "");

		if (masterName.length() > codeLength) {
			masterName = masterName.substring(0, codeLength);
		} else { // do padding
			for (int i = masterName.length(); i < codeLength; i++) {
				if (i == codeLength - 1) {
					masterName = masterName + "1";
					break;
				}
				masterName = masterName + "0";
			}
		}

		for (int i = 1; i < 10; i++) {
			if (codes.contains(masterName)) {
				masterName = masterName.replaceAll(".$", "" + i);
			}
		}

		return masterName;
	}

	/**
	 * Resolves getter method for the code of given Master class name.
	 * 
	 * @param masterClassName
	 * @return code
	 */
	private static String resolveCodeMethodForMaster(String masterClassName) {
		String code = "";
		switch (masterClassName) {
		case "com.vsoftcorp.kls.data.CropMasterData":
			code = "getCropCode";
			break;
		case "com.vsoftcorp.kls.data.CropTypeMasterData":
			code = "getCropTypeCode";
			break;
		case "com.vsoftcorp.kls.data.DistrictMasterData":
			code = "getDistrictCode";
			break;
		case "com.vsoftcorp.kls.data.TalukaMasterData":
			code = "getTalukaCode";
			break;
		case "com.vsoftcorp.kls.data.VillageMasterData":
			code = "getVillageCode";
			break;
		case "com.vsoftcorp.kls.data.PacsMasterData":
			code = "getPacsCode";
			break;
		case "com.vsoftcorp.kls.data.SeasonMasterData":
			code = "getSeasonCode";
			break;
		case "com.vsoftcorp.kls.data.ProductMasterData":
			code = "getProductCode";
			break;
		default:
			return code;
		}
		return code;
	}

	public static Date getDateFromString(String dateString, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setLenient(false);
		java.util.Date date = null;
		Date retDate = null;
		try {
			date = formatter.parse(dateString);
			retDate = (new Date(date.getTime()));

		} catch (ParseException e) {
			throw new KlsRuntimeException(
					" MasterHelper.getDateFromString(): Error while parsing date from string.",
					e.getCause());
		}
		return retDate;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static BigDecimal roundTo2DecimalPlaces(String inputString) {

		BigDecimal output = null;
		if (inputString != null) {
			BigDecimal input = new BigDecimal(inputString);
			output = input.setScale(2, RoundingMode.HALF_UP);
		}
		return output;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static BigDecimal roundTo2DecimalPlaces(BigDecimal input) {

		BigDecimal output = null;
		if (input != null) {
			output = input.setScale(2, RoundingMode.HALF_UP);
		}
		return output;
	}
	
	/**
	 * 
	 * @param amountParam
	 * @return
	 */
	public static Money populateAmountParam(String amountParam) {

		Money amountMoney = Money.ZERO;
		if (amountParam != null) {
			BigDecimal amount = new BigDecimal(amountParam);
			amountMoney = MoneyUtil.getAccountingMoney(amount).getMoney();
		}
		return amountMoney;
	}
	
	public static Double customerLandConversion(Double land, String units) {
		Double totalLand = 0d;
		if("H".equals(units)) {
			totalLand =  land * 2.471053;
		}
		if("C".equals(units)) {
			totalLand =  land / 99.804347;
		}
		if("G".equals(units)) {
			totalLand =  land / 40.008714;
		}
		if("A".equals(units)) {
			totalLand =  land;
		}
		return new BigDecimal(totalLand).setScale(2, RoundingMode.UP).doubleValue();
	}

}
