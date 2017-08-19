/**
 * 
 */
package com.vsoftcorp.kls.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.entities.District;
import com.vsoftcorp.kls.data.BankParameterData;
import com.vsoftcorp.kls.dataaccess.IBankParameterDAO;
import com.vsoftcorp.kls.dataaccess.IDistrictDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IBankParameterService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.BankParameterHelper;
import com.vsoftcorp.kls.service.util.PropertiesUtil;
import com.vsoftcorp.kls.service.util.StringConstant;
import com.vsoftcorp.kls.valuetypes.BankProcessStatus;

/**
 * @author a9152
 * 
 */
public class BankParameterService implements IBankParameterService {

	private static final Logger logger = Logger
			.getLogger(BankParameterService.class);

	/**
	 * This method checks for bank parameter id in the db. If exists, then throw
	 * an exception. Else save it to the database.
	 */
	public void saveBankParameter(BankParameterData bankParameterData) {

		logger.info("Start : Calling bank parameter dao in saveBankParameter() method.");
		// get the bank parameter dao.
		IBankParameterDAO dao = KLSDataAccessFactory.getBankParameterDAO();
		BankParameter master = BankParameterHelper
				.getBankParameter(bankParameterData);
		// get the district dao.
		IDistrictDAO districtDao = KLSDataAccessFactory.getDistrictDAO();
		District district = null;
		try {
			district = districtDao.getDistrict(master.getDistrict());
			if (district != null) {
				master.setDistrict(district);
				if (master.getId() == null) {
					master.setBankProcessStatus(BankProcessStatus.DAY_END);
					dao.saveBankParameter(master);
				} else {
					logger.error("Bank Parameter id already exists.");
					throw new KlsRuntimeException(
							"Bank Parameter id already exists");
				}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Bank Parameter data cannot be saved.");
			throw new KlsRuntimeException(
					"Bank Parameter data cannot be saved", excp.getCause());
		}
		if (district == null) {
			logger.error("Bank Parameter data cannot be saved to the db as district id does not exists.");
			throw new KlsRuntimeException(
					"Bank Parameter data cannot be saved to the db as district id does not exists");
		}
		logger.info("End : Calling bank parameter dao in saveBankParameter() method.");
	}

	/**
	 * This method checks for bank parameter id in the db. If exists, then
	 * update the bank parameter data to the database. Else throw the exception.
	 */
	public void updateBankParameter(BankParameterData bankParameterData) {

		logger.info("Start : Calling bank parameter dao in updateBankParameter() method.");
		// get the bank parameter dao.
		IBankParameterDAO dao = KLSDataAccessFactory.getBankParameterDAO();
		BankParameter master = BankParameterHelper
				.getBankParameter(bankParameterData);
		// update the bank parameter data to the db.
		try {
			dao.updateBankParameter(master);
			KLSDataAccessFactory.resetBankParameter();
		} catch (Exception excp) {
			logger.error("Bank Parameter data cannot be updated as bank parameter id does not exist");
			throw new KlsRuntimeException(
					"Bank Parameter data cannot be updated as bank parameter id does not exist",
					excp.getCause());
		}
		logger.info("End : Calling bank parameter dao in updateBankParameter() method.");
	}

	@Override
	public List<BankParameterData> getAllBankParameters() {

		logger.info("Start : Calling bank parameter dao in getAllBankParameters() method.");
		// get the bank parameter dao.
		IBankParameterDAO dao = KLSDataAccessFactory.getBankParameterDAO();
		List<BankParameterData> bankParameterDataList = new ArrayList<BankParameterData>();
		try {
			List<BankParameter> bankParameterList = dao.getAllBankParameters();
			for (BankParameter data : bankParameterList) {
				BankParameterData paramData=BankParameterHelper.getBankParameterData(data);
			
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(getClass().getClassLoader().getResource("About.xml").getFile());				
				doc.getDocumentElement().normalize(); 
		       	logger.info("Root element :" + doc.getDocumentElement().getNodeName());
		      	NodeList nList = doc.getElementsByTagName("version");
			    logger.info("----------------------------"+nList.getLength());
	        	Node nNode = nList.item(0);		
		       	logger.info("nNode:" +nNode);
		      	logger.info("node:" +nNode.getFirstChild().getNodeValue());
		   		paramData.setVersion(nNode.getFirstChild().getNodeValue());
                bankParameterDataList.add(paramData);
		  	   	}  
			
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("Error in retrieving all the bank parameter records");
			throw new KlsRuntimeException(
					"Error in retrieving all the bank parameter records",
					excp.getCause());
		}
		logger.info("End : Calling bank parameter dao in getAllBankParameters() method.");
		return bankParameterDataList;
	}

	@Override
	public boolean isSuperUser(String role) {
		logger.info("Start: Checking the user is super User or not in isSuperUser()");
		boolean response=false;
		String user=PropertiesUtil.getDocumentProperty(StringConstant.USER_ROLE);
		if(user.equalsIgnoreCase(role))
			response= true;
		logger.info("End: Checking the user is super User or not in isSuperUser()");
		return response;
	}
}
