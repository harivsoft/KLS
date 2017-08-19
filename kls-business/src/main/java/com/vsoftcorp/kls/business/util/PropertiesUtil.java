package com.vsoftcorp.kls.business.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


public class PropertiesUtil {

	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
	private static final String GENERAL_LEDGER_PROPERTIES_FILE_PATH = "/document.properties";

	static Properties properties = new Properties();

	static {
		try {
			logger.info("Start: Reading the properties files.");
			properties.load(PropertiesUtil.class.getResourceAsStream(GENERAL_LEDGER_PROPERTIES_FILE_PATH));
			logger.info("End: Successfully read the properties files.");
		} catch (IOException excp) {
			excp.printStackTrace();
		}
	}

	public static String getProperty(String propName) {
		return properties.getProperty(propName);
	}

	// to set the value to the property.
	public static void setProperty(String thePropertyName, String thePropertyValue) {
		properties.setProperty(thePropertyName, thePropertyValue);
	}

	public static void storeProperty() throws FileNotFoundException, IOException {
		properties.store(new FileOutputStream(GENERAL_LEDGER_PROPERTIES_FILE_PATH),
				"The last exported upto date and time.");
	}

	public static String getProperty(String propName, Object[] args) {
		return MessageFormat.format(properties.getProperty(propName), args);
	}
}