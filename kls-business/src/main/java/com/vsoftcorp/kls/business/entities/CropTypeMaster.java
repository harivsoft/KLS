package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CROP_TYPE_MAST")
public class CropTypeMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	public CropTypeMaster() {
	}

	@Id
	@Column(name = "CROP_TYPE_CODE", length = 5,nullable=false)
	private String cropTypeCode;
	@Basic
	@Column(name = "CROP_TYPE_NAME", length = 45,nullable=false)
	private String cropTypeName;

	public String getCropTypeCode() {
		return cropTypeCode;
	}

	public void setCropTypeCode(String cropTypeCode) {
		this.cropTypeCode = cropTypeCode;
	}

	public String getCropTypeName() {
		return cropTypeName;
	}

	public void setCropTypeName(String cropTypeName) {
		this.cropTypeName = cropTypeName;
	}

}