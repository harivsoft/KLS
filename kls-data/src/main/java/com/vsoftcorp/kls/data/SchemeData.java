package com.vsoftcorp.kls.data;

import java.io.Serializable;

//import com.vsoftcorp.kls.business.entities.Product;

public class SchemeData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Integer product_Id;
	private String productName;
//	private String insurancePercentage;

	public Integer getProductId() {
		return product_Id;
	}

	public void setProductId(Integer product_Id) {
		this.product_Id = product_Id;
	}

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
/*
	public String getInsurancePercentage() {
		return insurancePercentage;
	}

	public void setInsurancePercentage(String insurancePercentage) {
		this.insurancePercentage = insurancePercentage;
	}*/

}
