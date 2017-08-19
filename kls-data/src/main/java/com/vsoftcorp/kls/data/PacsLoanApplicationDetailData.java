/**
 * 
 */
package com.vsoftcorp.kls.data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author a9152
 * 
 */
public class PacsLoanApplicationDetailData {

	private Long headerId;

	private List<LandApplicationDetail> landApplicationDetails;

	private List<DeleteLandApplicationDetail> deleteLandApplicationDetails;
	
	private String loanLimitPerFarmer;
	
	private UserLoginDetailsData loggedInUserDetails;

	private BigDecimal totalLoanedLand;
	
	/**
	 * @return the headerId
	 */
	public Long getHeaderId() {
		return headerId;
	}

	/**
	 * @param headerId
	 *            the headerId to set
	 */
	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}

	/**
	 * @return the landApplicationDetails
	 */
	public List<LandApplicationDetail> getLandApplicationDetails() {
		return landApplicationDetails;
	}

	/**
	 * @param landApplicationDetails
	 *            the landApplicationDetails to set
	 */
	public void setLandApplicationDetails(
			List<LandApplicationDetail> landApplicationDetails) {
		this.landApplicationDetails = landApplicationDetails;
	}

	/**
	 * @return the deleteLandApplicationDetails
	 */
	public List<DeleteLandApplicationDetail> getDeleteLandApplicationDetails() {
		return deleteLandApplicationDetails;
	}

	/**
	 * @param deleteLandApplicationDetails
	 *            the deleteLandApplicationDetails to set
	 */
	public void setDeleteLandApplicationDetails(
			List<DeleteLandApplicationDetail> deleteLandApplicationDetails) {
		this.deleteLandApplicationDetails = deleteLandApplicationDetails;
	}

	public String getLoanLimitPerFarmer() {
		return loanLimitPerFarmer;
	}

	public void setLoanLimitPerFarmer(String loanLimitPerFarmer) {
		this.loanLimitPerFarmer = loanLimitPerFarmer;
	}

	public UserLoginDetailsData getLoggedInUserDetails() {
		return loggedInUserDetails;
	}

	public void setLoggedInUserDetails(UserLoginDetailsData loggedInUserDetails) {
		this.loggedInUserDetails = loggedInUserDetails;
	}

	public BigDecimal getTotalLoanedLand() {
		return totalLoanedLand;
	}

	public void setTotalLoanedLand(BigDecimal totalLoanedLand) {
		this.totalLoanedLand = totalLoanedLand;
	}
	
	
}
