package com.vsoftcorp.kls.report.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class ReportConfig {

	private static final String CONFIG_FILE = "Report.properties";
	
    static Properties properties = new Properties();

    static {
        try {

            ClassLoader classLoader = Thread.currentThread()
                    .getContextClassLoader();
            if (classLoader == null) {
                classLoader = Class.class.getClassLoader();
            }

            properties.load(classLoader.getResourceAsStream(CONFIG_FILE));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static String getProperty(String propName){
        return properties.getProperty(propName);
    }

    // to set the value to the property.
    public static void setProperty(String thePropertyName,
            String thePropertyValue) {
        properties.setProperty(thePropertyName, thePropertyValue);
    }

    public static void storeProperty() throws FileNotFoundException,
            IOException {
        properties.store(new FileOutputStream(CONFIG_FILE),
                "The last exported upto date and time.");
    }
    
    
    public static String getProperty(String propName,Object[] args){
    	return MessageFormat.format(properties.getProperty(propName), args);
    }
    
}
