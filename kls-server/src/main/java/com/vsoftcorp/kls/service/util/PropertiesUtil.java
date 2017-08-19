/**
 * 
 */
package com.vsoftcorp.kls.service.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author a9152
 * 
 */
public class PropertiesUtil {

	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
	private static final String DB_QUERIES_PROPERTIES_FILE_PATH = "/locqueries.properties";
	private static final String REPAYMENT_PROPERTIES_FILE_PATH = "/repayment.properties";
	private static final String Document_PROPERTIES_FILE_PATH = "/document.properties";
	private static final String SUVIKAS_PROPERTIES_FILE_PATH = "/suvikas.properties";

	static Properties properties = new Properties();
	static Properties docProperties = new Properties();
	static Properties repaymentProperties = new Properties();
	static Properties suvikasProperties = new Properties();
	static {
		try {
			logger.info("Start: Reading the properties files.");
			properties.load(PropertiesUtil.class.getResourceAsStream(DB_QUERIES_PROPERTIES_FILE_PATH));
			docProperties.load(PropertiesUtil.class.getResourceAsStream(Document_PROPERTIES_FILE_PATH));
			repaymentProperties.load(PropertiesUtil.class.getResourceAsStream(REPAYMENT_PROPERTIES_FILE_PATH));
			suvikasProperties.load(PropertiesUtil.class.getResourceAsStream(SUVIKAS_PROPERTIES_FILE_PATH));
			//properties.putAll(repaymentProperties);
			logger.info("End: Successfully read the properties files.");
		} catch (IOException excp) {
			excp.printStackTrace();
		}
	}

	public static String getProperty(String propName) {
		return properties.getProperty(propName);
	}

	public static String getDocumentProperty(String propName) {
		return docProperties.getProperty(propName);
	}

	public static String getRepaymentProperty(String propName) {
		return repaymentProperties.getProperty(propName);
	}
	
	public static String getSuvikasProperty(String propName) {
		return suvikasProperties.getProperty(propName);
	}

	// to set the value to the property.
	public static void setProperty(String thePropertyName, String thePropertyValue) {
		properties.setProperty(thePropertyName, thePropertyValue);
	}

	/*public static void storeProperty() throws FileNotFoundException, IOException {
		properties
				.store(new FileOutputStream(DB_QUERIES_PROPERTIES_FILE_PATH), "The last exported upto date and time.");
	}*/

	public static String getProperty(String propName, Object[] args) {
		return MessageFormat.format(properties.getProperty(propName), args);
	}

	public static String getDocumentProperty(String propName, Object[] args) {
		return MessageFormat.format(docProperties.getProperty(propName), args);
	}

}
