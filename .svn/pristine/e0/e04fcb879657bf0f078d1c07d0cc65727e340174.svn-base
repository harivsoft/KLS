package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author sponnam
 * 
 */
@Entity
@Table(name = "season_definition")
public class SeasonDefinition implements Serializable {

	private static final long serialVersionUID = 7714638616142518064L;

	public SeasonDefinition() {
	}

	@Id
	@GeneratedValue(generator = "seasonDefinitionIdsequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seasonDefinitionIdsequence", sequenceName = "season_definition_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "NAME",length=45)
	private String name;

	@Basic
	@Column(name = "DRAWAL_START_DAY")
	private Integer drawalStartDay;

	@Basic
	@Column(name = "DRAWAL_START_MONTH")
	private Integer drawalStartMonth;
	
	@Basic
	@Column(name = "DRAWAL_END_DURATION")
	private Integer drawalEndDuration;
	
	@Basic
	@Column(name = "LOAN_OVERDUE_DURATION")
	private Integer loanOverdueDuration;
	
	
	@Basic
	@Column(name = "due_date_in_months")
	private Integer dueDateInMonths;
	
	

	@Basic
	@Column(name = "due_date_method")
	private  String dueDateMethod;
	
	
	
	
	
	
	
	
	
	
	
	/*@Basic
	@Column(name = "DRAWAL_END_DAY")
	private Integer drawalEndDay;

	@Basic
	@Column(name = "DRAWAL_END_MONTH")
	private Integer drawalEndMonth;

	@Basic
	@Column(name = "LOAN_OVERDUE_DAY")
	private Integer loanOverdueDay;

	@Basic
	@Column(name = "LOAN_OVERDUE_MONTH")
	private Integer loanOverdueMonth;*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String seasonName) {
		this.name = seasonName;
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

	public Integer getLoanOverdueDuration() {
		return loanOverdueDuration;
	}

	public void setLoanOverdueDuration(Integer loanOverdueDuration) {
		this.loanOverdueDuration = loanOverdueDuration;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((drawalEndDuration == null) ? 0 : drawalEndDuration.hashCode());
		
		result = prime * result
				+ ((drawalStartDay == null) ? 0 : drawalStartDay.hashCode());
		result = prime
				* result
				+ ((drawalStartMonth == null) ? 0 : drawalStartMonth.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeasonDefinition other = (SeasonDefinition) obj;
		
		if (drawalStartDay == null) {
			if (other.drawalStartDay != null)
				return false;
		} else if (!drawalStartDay.equals(other.drawalStartDay))
			return false;
		if (drawalStartMonth == null) {
			if (other.drawalStartMonth != null)
				return false;
		} else if (!drawalStartMonth.equals(other.drawalStartMonth))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
	
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
