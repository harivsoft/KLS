package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "document")
public class Document implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public Document() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "documentIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "documentIdSequence", sequenceName = "document_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", length = 45)
	private String name;

	@Column(name = "remarks", length = 150)
	private String remarks;

	@Column(name = "validation_rule", length = 64)
	private String validationRule;
	
	@Column(name = "is_document_no_required")
	private Boolean isDocNoRequired;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the validationRule
	 */
	public String getValidationRule() {
		return validationRule;
	}

	/**
	 * @param validationRule
	 *            the validationRule to set
	 */
	public void setValidationRule(String validationRule) {
		this.validationRule = validationRule;
	}

	public Boolean getIsDocNoRequired() {
		return isDocNoRequired;
	}

	public void setIsDocNoRequired(Boolean isDocNoRequired) {
		this.isDocNoRequired = isDocNoRequired;
	}

}
