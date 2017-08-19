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

import com.vsoftcorp.kls.business.entities.Crop;
import com.vsoftcorp.kls.business.entities.Season;
/**
 * 
 * @author a1605
 * This is the entity class maps to "season_parameter" table
 *
 */
@Entity
@Table(name = "season_parameter")
public class SeasonParameter {
	
	@Id
	@GeneratedValue(generator = "seasonParameterId", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seasonParameterId", sequenceName = "season_parameter_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "season_id", referencedColumnName = "id")
	private Season season;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "crop_id", referencedColumnName = "id")
	private Crop crop;
	
	@Basic
	@Column(name = "insurance_gl",length=20)
	private String insuranceGL;
	
	@Basic
	@Column(name = "insurance_by_farmer", precision = 5, scale = 2)
	private BigDecimal insuranceByFarmer;
	
	@Basic
	@Column(name = "insurance_subsidy", precision = 5, scale = 2)
	private BigDecimal insuranceSubsidy;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;
	
	@Basic
	@Column(name="insurance_period")
	private Integer insurancePeriod;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the season
	 */
	public Season getSeason() {
		return season;
	}

	/**
	 * @return the crop
	 */
	public Crop getCrop() {
		return crop;
	}


	/**
	 * @return the insuranceByFarmer
	 */
	public BigDecimal getInsuranceByFarmer() {
		return insuranceByFarmer;
	}

	/**
	 * @return the insuranceSubsidy
	 */
	public BigDecimal getInsuranceSubsidy() {
		return insuranceSubsidy;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param season the season to set
	 */
	public void setSeason(Season season) {
		this.season = season;
	}

	/**
	 * @param crop the crop to set
	 */
	public void setCrop(Crop crop) {
		this.crop = crop;
	}


	/**
	 * @param insuranceByFarmer the insuranceByFarmer to set
	 */
	public void setInsuranceByFarmer(BigDecimal insuranceByFarmer) {
		this.insuranceByFarmer = insuranceByFarmer;
	}

	/**
	 * @param insuranceSubsidy the insuranceSubsidy to set
	 */
	public void setInsuranceSubsidy(BigDecimal insuranceSubsidy) {
		this.insuranceSubsidy = insuranceSubsidy;
	}

	/**
	 * @return the insuranceGL
	 */
	public String getInsuranceGL() {
		return insuranceGL;
	}

	/**
	 * @param insuranceGL the insuranceGL to set
	 */
	public void setInsuranceGL(String insuranceGL) {
		this.insuranceGL = insuranceGL;
	}

	/**
	 * @return the pacs
	 */
	public Pacs getPacs() {
		return pacs;
	}

	/**
	 * @param pacs the pacs to set
	 */
	public void setPacs(Pacs pacs) {
		this.pacs = pacs;
	}

	public Integer getInsurancePeriod() {
		return insurancePeriod;
	}

	public void setInsurancePeriod(Integer insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}

}
