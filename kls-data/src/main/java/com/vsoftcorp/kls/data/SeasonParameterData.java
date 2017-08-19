package com.vsoftcorp.kls.data;

/**
 * 
 * @author a1605
 *
 */
public class SeasonParameterData {
	
	private String id;
	
	private String seasonId;
	
	private String seasonName;
	
	private String cropId;
	
	private String cropName;
	
	private String insuranceGL;
	
	private String insuranceByFarmer;
	
	private String insuranceSubsidy;
	
	private LoggedInUserDetailsData userDetails;
	
	private String insuranceCutoffPeriod;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the seasonId
	 */
	public String getSeasonId() {
		return seasonId;
	}

	/**
	 * @return the seasonName
	 */
	public String getSeasonName() {
		return seasonName;
	}

	/**
	 * @return the cropId
	 */
	public String getCropId() {
		return cropId;
	}

	/**
	 * @return the cropName
	 */
	public String getCropName() {
		return cropName;
	}


	/**
	 * @return the insuranceByFarmer
	 */
	public String getInsuranceByFarmer() {
		return insuranceByFarmer;
	}

	/**
	 * @return the insuranceSubsidy
	 */
	public String getInsuranceSubsidy() {
		return insuranceSubsidy;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param seasonId the seasonId to set
	 */
	public void setSeasonId(String seasonId) {
		this.seasonId = seasonId;
	}

	/**
	 * @param seasonName the seasonName to set
	 */
	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	/**
	 * @param cropId the cropId to set
	 */
	public void setCropId(String cropId) {
		this.cropId = cropId;
	}

	/**
	 * @param cropName the cropName to set
	 */
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}


	/**
	 * @param insuranceByFarmer the insuranceByFarmer to set
	 */
	public void setInsuranceByFarmer(String insuranceByFarmer) {
		this.insuranceByFarmer = insuranceByFarmer;
	}

	/**
	 * @param insuranceSubsidy the insuranceSubsidy to set
	 */
	public void setInsuranceSubsidy(String insuranceSubsidy) {
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
	 * @return the userDetails
	 */
	public LoggedInUserDetailsData getUserDetails() {
		return userDetails;
	}

	/**
	 * @param userDetails the userDetails to set
	 */
	public void setUserDetails(LoggedInUserDetailsData userDetails) {
		this.userDetails = userDetails;
	}

	public String getInsuranceCutoffPeriod() {
		return insuranceCutoffPeriod;
	}

	public void setInsuranceCutoffPeriod(String insuranceCutoffPeriod) {
		this.insuranceCutoffPeriod = insuranceCutoffPeriod;
	}

		
}
