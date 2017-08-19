package com.vsoftcorp.kls.business.entity.subsidy;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.kls.business.entities.Scheme;
import com.vsoftcorp.kls.valuetypes.subsidy.EligibilityCriteria;
import com.vsoftcorp.kls.valuetypes.subsidy.TypeOfScheme;

@TypeDefs({
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }),
		@TypeDef(name = "typeOfScheme", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.subsidy.TypeOfScheme"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),
		@TypeDef(name = "eligibilityCriteria", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.subsidy.EligibilityCriteria"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") })
		})
/**
 * 
 * @author a1623
 *
 */
@Entity
@Table(name = "interest_subsidy")
public class InterestSubsidy {
	@Id
	@GeneratedValue(generator = "interestSubsidyIDSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "interestSubsidyIDSequence", sequenceName = "interest_subsidy_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "type_of_scheme", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "typeOfScheme")
	private TypeOfScheme typeOfScheme;

	@Basic
	@Column(name = "name_of_scheme")
	private String nameOfScheme;

	@ManyToOne(optional = false)
	@JoinColumn(name = "institute_master_id", referencedColumnName = "id")
	private InstituteMaster instituteMaster;

	@ManyToOne(optional = false)
	@JoinColumn(name = "scheme_id", referencedColumnName = "id")
	private Scheme scheme;

	@Basic
	@Column(name = "season_id")
	private Integer seasonId;

	@Column(name = "eligibility_criteria", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "eligibilityCriteria")
	private EligibilityCriteria eligibilityCriteria;

	@Basic
	@Column(name = "max_period_for_subsidy")
	private Integer maxPeriodForSubsidy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeOfScheme getTypeOfScheme() {
		return typeOfScheme;
	}

	public void setTypeOfScheme(TypeOfScheme typeOfScheme) {
		this.typeOfScheme = typeOfScheme;
	}

	public String getNameOfScheme() {
		return nameOfScheme;
	}

	public void setNameOfScheme(String nameOfScheme) {
		this.nameOfScheme = nameOfScheme;
	}

	public InstituteMaster getInstituteMaster() {
		return instituteMaster;
	}

	public void setInstituteMaster(InstituteMaster instituteMaster) {
		this.instituteMaster = instituteMaster;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public Integer getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}

	public EligibilityCriteria getEligibilityCriteria() {
		return eligibilityCriteria;
	}

	public void setEligibilityCriteria(EligibilityCriteria eligibilityCriteria) {
		this.eligibilityCriteria = eligibilityCriteria;
	}

	public Integer getMaxPeriodForSubsidy() {
		return maxPeriodForSubsidy;
	}

	public void setMaxPeriodForSubsidy(Integer maxPeriodForSubsidy) {
		this.maxPeriodForSubsidy = maxPeriodForSubsidy;
	}
	
}
