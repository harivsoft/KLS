package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.dataaccess.ICropDAO;
import com.vsoftcorp.kls.dataaccess.ISchemeDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.DrawalReportData;
import com.vsoftcorp.kls.report.service.exception.KlsReportRuntimeException;
import com.vsoftcorp.kls.service.util.RestClientUtil;

/**
 * 
 * @author a1605
 */
public class DrawalReportHelper {

	private static final Logger logger = Logger.getLogger(DrawalReportHelper.class);

	public static List<DrawalReportData> getDrawalReportDataList(List<Object[]> drawalList) {
		logger.info("Start: inside getDrawalReportDataList ");
		List<DrawalReportData> drawalDataList = new ArrayList<DrawalReportData>();
		DrawalReportData data = null;
		try {
			for (Object[] object : drawalList) {
				data = new DrawalReportData();
				// data.setCustomerName((String) object[7]);
				BigInteger customerId = (BigInteger) object[6];
				PersonData customer = RestClientUtil.getCustomerById(customerId.longValue());
				if (customer != null){
					data.setCustomerName(customer.getName());
					data.setCustomerId(customer.getMemberNumber());
				}
				//data.setCustomerId(customerId.toString());
				Integer schemeId = (Integer) object[0];
				data.setSchemeId(schemeId.toString());
				BigDecimal amountDrawn = (BigDecimal) object[7];
				ISchemeDAO scDao = KLSDataAccessFactory.getSchemeDAO();
				Scheme scheme = new Scheme();
				scheme.setId(schemeId);
				scheme = scDao.getScheme(scheme, false);
				data.setSchemeName(scheme.getName());
				data.setAccountNumber((String) object[5]);
				data.setAmountDrawan(amountDrawn.setScale(2));
				Integer cropId = (Integer) object[2];
				ICropDAO cDao = KLSDataAccessFactory.getCropDAO();
				Crop crop = cDao.getCrop(cropId);
				data.setCropName(crop.getName());
				BigInteger locId = (BigInteger) object[1];
				data.setLocNo(locId.toString());
				java.sql.Date businessDate = (java.sql.Date) object[3];
				data.setSanctionedDate(DateFormatUtils.format(businessDate,"dd/MM/yyyy"));
				BigDecimal sanctionedAmount = (BigDecimal) object[4];
				data.setSanctionedAmount(sanctionedAmount.setScale(2));
				drawalDataList.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: inside getDrawalReportDataList ");
			throw new KlsReportRuntimeException("Error while generating Drawal report");

		}
		logger.info("End: inside getDrawalReportDataList ");

		return drawalDataList;
	}
}
