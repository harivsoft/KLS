package com.vsoftcorp.kls.business.entity.loan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LoanApplicationActivityId implements Serializable {
	
	@Column(name="application_id")
	private Long applicationId;
	
	@Column(name="activity_id")
	private Long activityId;

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}


}
