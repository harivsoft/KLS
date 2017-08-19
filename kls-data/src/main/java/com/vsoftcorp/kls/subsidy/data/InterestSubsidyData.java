package com.vsoftcorp.kls.subsidy.data;

import java.util.List;

/**
 * 
 * @author a1623
 *
 */
public class InterestSubsidyData {
	private Long id;
	private String typeOfScheme;
	private String nameOfScheme;
	private Long instituteMasterId;
	private Integer schemeId;
	private Integer seasonId;
	private String eligibilityCriteria;
	private Integer maxPeriodForSubsidy;
	private List<SlabwisesubsidyInterestRateData> slabwiseRoiList;
	private List<SlabwisesubsidyInterestRateData> deleteList;
	private String instituteMasterName;
	private String seasonName;
	private String schemeName;
	private String typeOfSchemeName;
	
	public List<SlabwisesubsidyInterestRateData> getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(List<SlabwisesubsidyInterestRateData> deleteList) {
		this.deleteList = deleteList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTypeOfScheme() {
		return typeOfScheme;
	}
	public void setTypeOfScheme(String typeOfScheme) {
		this.typeOfScheme = typeOfScheme;
	}
	public String getNameOfScheme() {
		return nameOfScheme;
	}
	public void setNameOfScheme(String nameOfScheme) {
		this.nameOfScheme = nameOfScheme;
	}
	public Long getInstituteMasterId() {
		return instituteMasterId;
	}
	public void setInstituteMasterId(Long instituteMasterId) {
		this.instituteMasterId = instituteMasterId;
	}
	public Integer getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}
		
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	public String getEligibilityCriteria() {
		return eligibilityCriteria;
	}
	public void setEligibilityCriteria(String eligibilityCriteria) {
		this.eligibilityCriteria = eligibilityCriteria;
	}
	public Integer getMaxPeriodForSubsidy() {
		return maxPeriodForSubsidy;
	}
	public void setMaxPeriodForSubsidy(Integer maxPeriodForSubsidy) {
		this.maxPeriodForSubsidy = maxPeriodForSubsidy;
	}
	public List<SlabwisesubsidyInterestRateData> getSlabwiseRoiList() {
		return slabwiseRoiList;
	}
	public void setSlabwiseRoiList(
			List<SlabwisesubsidyInterestRateData> slabwiseRoiList) {
		this.slabwiseRoiList = slabwiseRoiList;
	}
	public String getInstituteMasterName() {
		return instituteMasterName;
	}
	public void setInstituteMasterName(String instituteMasterName) {
		this.instituteMasterName = instituteMasterName;
	}
	public String getSeasonName() {
		return seasonName;
	}
	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getTypeOfSchemeName() {
		return typeOfSchemeName;
	}
	public void setTypeOfSchemeName(String typeOfSchemeName) {
		this.typeOfSchemeName = typeOfSchemeName;
	}
	
	
}
