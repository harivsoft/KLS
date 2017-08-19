package com.vsoftcorp.kls.business.entities;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sanctioned_component_detail")
public class SanctionedComponentDetail {
	@Id
	@SequenceGenerator(name = "componentIdSeq", sequenceName = "component_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "componentIdSeq")
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "season_id", referencedColumnName = "ID")
	private Season season;
	
	@Basic
	@Column(name = "description", length = 45)
	private String componentDescription;

	@Basic
	@Column(name = "kind_percentage", precision = 5, scale = 2)
	private BigDecimal percentageOfKind;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public String getComponentDescription() {
		return componentDescription;
	}

	public void setComponentDescription(String componentDescription) {
		this.componentDescription = componentDescription;
	}

	public BigDecimal getPercentageOfKind() {
		return percentageOfKind;
	}

	public void setPercentageOfKind(BigDecimal percentageOfKind) {
		this.percentageOfKind = percentageOfKind;
	}

}
