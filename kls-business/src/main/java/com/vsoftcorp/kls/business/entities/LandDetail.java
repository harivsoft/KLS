package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

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

/**
 * This entity class is used to map the cust_land_detail table
 * @author a1605
 */
@Entity
@Table(name="CUST_LAND_DETAIL")
public class LandDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "landDetailIdSeq", sequenceName = "land_detail_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "landDetailIdSeq")

	@Column(name="ID")
	private Long id;
	
	@Basic
	@Column(name = "customer_id")
	private Long customerId;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="VILLAGE_ID",referencedColumnName="ID")
	private Village village;
	
	@Basic
	@Column(name="SUB_VILLAGE_name", length=45)
	private String subVillageCode;
	
	@Basic
	@Column(name="SURVEY_NO", length=20)
	private String surveyNo;
	
	@Basic
	@Column(name="BSR_NO", length=20)
	private String bsrNo;
	
	@Basic
	@Column(name="RSR_NO", length=20)
	private String rsrNo;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="LAND_TYPE_ID",referencedColumnName="ID")
	private LandType landType;
	
	@Basic
	@Column(name="AREA_OWNED")
	private Double areaOwned;
	
	@Basic
	@Column(name="AREA_CULTIVATED")
	private Double areaCultivated;
	
	@Basic
	@Column(name="IS_MORTGAZED", length=1)
	private String isMortgazed;
	
	@Basic
	@Column(name="MORTGAZED_REMARKS", length=45)
	private String mortgazedRemarks;
	
	@Basic
	@Column(name="IS_CHARGED", length=1)
	private String isCharged;
	
	@Basic
	@Column(name="CHARGED_REMARKS", length=45)
	private String chargedRemarks;
	
	@Basic
	@Column(name = "available_cul_land")
	private Double availableCulLand;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="TALUKA_ID",referencedColumnName="ID")
	private Taluka taluka;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getSubVillageCode() {
		return subVillageCode;
	}

	public void setSubVillageCode(String subVillageCode) {
		this.subVillageCode = subVillageCode;
	}

	public String getSurveyNo() {
		return surveyNo;
	}

	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}

	public String getBsrNo() {
		return bsrNo;
	}

	public void setBsrNo(String bsrNo) {
		this.bsrNo = bsrNo;
	}

	public String getRsrNo() {
		return rsrNo;
	}

	public void setRsrNo(String rsrNo) {
		this.rsrNo = rsrNo;
	}

	public Double getAreaOwned() {
		return areaOwned;
	}

	public void setAreaOwned(Double areaOwned) {
		this.areaOwned = areaOwned;
	}

	public Double getAreaCultivated() {
		return areaCultivated;
	}

	public void setAreaCultivated(Double areaCultivated) {
		this.areaCultivated = areaCultivated;
	}

	public String getIsMortgazed() {
		return isMortgazed;
	}

	public void setIsMortgazed(String isMortgazed) {
		this.isMortgazed = isMortgazed;
	}

	public String getMortgazedRemarks() {
		return mortgazedRemarks;
	}

	public void setMortgazedRemarks(String mortgazedRemarks) {
		this.mortgazedRemarks = mortgazedRemarks;
	}

	public String getIsCharged() {
		return isCharged;
	}

	public void setIsCharged(String isCharged) {
		this.isCharged = isCharged;
	}

	public String getChargedRemarks() {
		return chargedRemarks;
	}

	public void setChargedRemarks(String chargedRemarks) {
		this.chargedRemarks = chargedRemarks;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public LandType getLandType() {
		return landType;
	}

	public void setLandType(LandType landType) {
		this.landType = landType;
	}

	public Double getAvailableCulLand() {
		return availableCulLand;
	}

	public void setAvailableCulLand(Double availableCulLand) {
		this.availableCulLand = availableCulLand;
	}

	public Taluka getTaluka() {
		return taluka;
	}

	public void setTaluka(Taluka taluka) {
		this.taluka = taluka;
	}

}