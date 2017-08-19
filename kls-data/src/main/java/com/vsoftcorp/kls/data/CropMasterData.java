package com.vsoftcorp.kls.data;

import java.io.Serializable;

public class CropMasterData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 private static String EMPTY_STRING = "";

	private String id = EMPTY_STRING;

	private String name = EMPTY_STRING;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
