package com.vsoftcorp.kls.subsidy.data;

public class InstituteMasterData {
	private Long id;
	private String instituteName;
	private Integer typeOfInstitute;
	private String geographicalArea;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public Integer getTypeOfInstitute() {
		return typeOfInstitute;
	}

	public void setTypeOfInstitute(Integer typeOfInstitute) {
		this.typeOfInstitute = typeOfInstitute;
	}

	public String getGeographicalArea() {
		return geographicalArea;
	}

	public void setGeographicalArea(String geographicalArea) {
		this.geographicalArea = geographicalArea;
	}

}
