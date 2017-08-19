package com.vsoftcorp.kls.business.entity.subsidy;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.kls.valuetypes.subsidy.GeographicalArea;
import com.vsoftcorp.kls.valuetypes.subsidy.TypeOfInstitute;


@TypeDefs({
	@TypeDef(name = "typeOfInstitute", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
			@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.subsidy.TypeOfInstitute"),
			@Parameter(name = "identifierMethod", value = "getValue"),
			@Parameter(name = "convertIdentifierMethod", value = "getType"),
			@Parameter(name = "valueOfMethod", value = "getType") }),
			@TypeDef(name = "geographicalArea", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.subsidy.GeographicalArea"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		})

@Entity
@Table(name = "institute_master")
public class InstituteMaster {
	
	
	@Id
	@GeneratedValue(generator = "instituteMasterIDSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "instituteMasterIDSequence", sequenceName = "institute_master_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;
	
	@Basic
	@Column(name = "institute_name")
	private String instituteName;
	
	@Column(name = "type_of_institute")
	@Enumerated(EnumType.STRING)
	@Type(type = "typeOfInstitute")
	private TypeOfInstitute typeOfInstitute;
	
	@Column(name = "geographical_area")
	@Enumerated(EnumType.STRING)
	@Type(type = "geographicalArea")
	private GeographicalArea geographicalArea;

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

	public TypeOfInstitute getTypeOfInstitute() {
		return typeOfInstitute;
	}

	public void setTypeOfInstitute(TypeOfInstitute typeOfInstitute) {
		this.typeOfInstitute = typeOfInstitute;
	}

	public GeographicalArea getGeographicalArea() {
		return geographicalArea;
	}

	public void setGeographicalArea(GeographicalArea geographicalArea) {
		this.geographicalArea = geographicalArea;
	}

}