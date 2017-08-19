package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;
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
@Table(name = "SCALE_OF_FINANCE")
public class ScaleOfFinance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8963451350511606867L;

	public ScaleOfFinance() {
	}

	@Id
	@GeneratedValue(generator = "scaleOfFinanceIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "scaleOfFinanceIdSequence", sequenceName = "scale_of_finance_id_seq", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;

	@Basic
	@Column(name = "AREA_TYPE", length = 1)
	private String areaType;

	@Basic
	@Column(name = "AREA_TYPE_ID")
	private Integer areaTypeId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "season_id", referencedColumnName = "ID")
	private Season season;

	@ManyToOne(optional = false)
	@JoinColumn(name = "crop_id", referencedColumnName = "ID")
	private Crop crop;

	@ManyToOne(optional = false)
	@JoinColumn(name = "land_type_id", referencedColumnName = "ID")
	private LandType landType;

	@Basic
	@Column(name = "max_loan_per_acre", precision =22, scale = 6)
	private BigDecimal loanAmtPerAcre;

	/**
	 * @return the loanAmtPerAcre
	 */
	public BigDecimal getLoanAmtPerAcre() {
		return loanAmtPerAcre;
	}

	/**
	 * @param loanAmtPerAcre
	 *            the loanAmtPerAcre to set
	 */
	public void setLoanAmtPerAcre(BigDecimal loanAmtPerAcre) {
		this.loanAmtPerAcre = loanAmtPerAcre;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the areaType
	 */
	public String getAreaType() {
		return areaType;
	}

	/**
	 * @param areaType
	 *            the areaType to set
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	/**
	 * @return the areaTypeId
	 */
	public Integer getAreaTypeId() {
		return areaTypeId;
	}

	/**
	 * @param areaTypeId
	 *            the areaTypeId to set
	 */
	public void setAreaTypeId(Integer areaTypeId) {
		this.areaTypeId = areaTypeId;
	}

	/**
	 * @return the season
	 */
	public Season getSeason() {
		return season;
	}

	/**
	 * @param season
	 *            the season to set
	 */
	public void setSeason(Season season) {
		this.season = season;
	}

	/**
	 * @return the crop
	 */
	public Crop getCrop() {
		return crop;
	}

	/**
	 * @param crop
	 *            the crop to set
	 */
	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	/**
	 * @return the landType
	 */
	public LandType getLandType() {
		return landType;
	}

	/**
	 * @param landType
	 *            the landType to set
	 */
	public void setLandType(LandType landType) {
		this.landType = landType;
	}

}
