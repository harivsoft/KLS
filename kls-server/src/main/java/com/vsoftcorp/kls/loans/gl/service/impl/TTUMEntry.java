package com.vsoftcorp.kls.loans.gl.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 *  @author rajendra
 *  TTUM is file format for of line upload of bulk transactions for Finacle CBS product.
 *  This Class represents single TTUM Entry. 
 */
public class TTUMEntry {
	private static Logger logger = Logger.getLogger(TTUMEntry.class);

	private LinkedHashMap<String, String> fields = new <String, String> LinkedHashMap();	 
	
	public void populateDefaultValues(){
		logger.info("Start: Populating default vlaues for TTUM Entry");
		try {
			for (TTUMField field : TTUMFormat.getTTUMFormat().getFields()) {
				fields.put(field.getName(), field.getFormattedValue());
			}
		}catch(Exception te){
			logger.error("Error in default TTUM entry: " + te.getMessage());
			throw te;
		}
		logger.info("End: Populating default vlaues for TTUM Entry");
	}

	public LinkedHashMap<String, String> getFields() {
		return fields;
	}

	public void setFields(LinkedHashMap<String, String> fields) {
		this.fields = fields;
	}	
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder("");
		
		for (Entry entry : fields.entrySet()){
			str.append(entry.getValue());
		}
		
		return str.toString();
	}
}
