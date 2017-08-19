package com.vsoftcorp.kls.data;

public class SeasonMasterData {


	private String id;

	private String name;

	private Integer drawalStartDay;

	private Integer drawalStartMonth;

	private Integer drawalEndDuration;

	private Integer dueDuration;
	
	private Integer dueDateInMonths;
	
	private String dueDateMethod ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDrawalStartDay() {
		return drawalStartDay;
	}

	public void setDrawalStartDay(Integer drawalStartDay) {
		this.drawalStartDay = drawalStartDay;
	}

	public Integer getDrawalStartMonth() {
		return drawalStartMonth;
	}

	public void setDrawalStartMonth(Integer drawalStartMonth) {
		this.drawalStartMonth = drawalStartMonth;
	}

	public Integer getDrawalEndDuration() {
		return drawalEndDuration;
	}

	public void setDrawalEndDuration(Integer drawalEndDuration) {
		this.drawalEndDuration = drawalEndDuration;
	}

	public Integer getDueDuration() {
		return dueDuration;
	}

	public void setDueDuration(Integer dueDuration) {
		this.dueDuration = dueDuration;
	}
	public Integer getDueDateInMonths() {
		return dueDateInMonths;
	}

	public void setDueDateInMonths(Integer dueDateInMonths) {
		this.dueDateInMonths = dueDateInMonths;
	}

	public String getDueDateMethod() {
		return dueDateMethod;
	}

	public void setDueDateMethod(String dueDateMethod) {
		this.dueDateMethod = dueDateMethod;
	}
}
