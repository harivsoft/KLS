package com.vsoftcorp.kls.util;

import com.google.gson.Gson;
import com.vsoftcorp.kls.data.DistrictData;
import org.apache.log4j.Logger;

/**
 * 
 * @author a9152
 * 
 */
public class JsonToJavaConverter {

	private static final Logger logger = Logger
			.getLogger(JsonToJavaConverter.class);

	public static DistrictData getJavaObjectFromJson(String jsonString) {

		Gson gson = new Gson();
		DistrictData data = gson.fromJson(jsonString,
				DistrictData.class);
		return data;
	}
}
