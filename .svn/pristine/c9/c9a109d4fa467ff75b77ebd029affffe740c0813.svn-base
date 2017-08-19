package com.vsoftcorp.kls.business.entity.subsidy;

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
@Table(name = "subsidy_scheme_gl_mapping")
public class SubsidySchemeGlMapping {

	@Id
	@GeneratedValue(generator = "subsidySchemeglMappingIDSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "subsidySchemeglMappingIDSequence", sequenceName = "subsidy_scheme_gl_mapping_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "pacs_id")
	private Integer pacsId;

	@Basic
	@Column(name = "subsidy_receievable_gl")
	private String subsidyReceievableGl;

	@ManyToOne(optional = false)
	@JoinColumn(name = "subsidy_scheme_id", referencedColumnName = "id")
	private InterestSubsidy subsidySchemeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPacsId() {
		return pacsId;
	}

	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	public String getSubsidyReceievableGl() {
		return subsidyReceievableGl;
	}

	public void setSubsidyReceievableGl(String subsidyReceievableGl) {
		this.subsidyReceievableGl = subsidyReceievableGl;
	}

	public InterestSubsidy getSubsidySchemeId() {
		return subsidySchemeId;
	}

	public void setSubsidySchemeId(InterestSubsidy subsidySchemeId) {
		this.subsidySchemeId = subsidySchemeId;
	}

	
}
