package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.kls.valuetypes.SeasonStatus;
import com.vsoftcorp.time.Date;

/**
 * @author sponnam
 *
 */
@TypeDefs({
		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }),
		@TypeDef(name = "seasonStatus", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.SeasonStatus"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),

})
@Entity
@Table(name = "season")
public class Season implements Serializable {

	private static final long serialVersionUID = 1L;

	public Season() {
	}

	@Id
	@GeneratedValue(generator = "seasonIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seasonIdSequence", sequenceName = "season_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "name", length = 45)
	private String name;

	@Basic
	@Column(name = "drawal_start")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date drawalStartDate;

	@Basic
	@Column(name = "drawal_end")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date drawalEndDate;

	@Basic
	@Column(name = "overdue_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date overdueDate;

	@Basic
	@Column(name = "process_status", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "seasonStatus")
	private SeasonStatus processStatus;


	@Basic
	@Column(name = "recovery_period")
	private Integer recoveryPeriod;
	
	@Basic
	@Column(name = "due_date_in_months")
	private Integer dueDateInMonths;

	@Basic
	@Column(name = "due_date_method")
	private  String dueDateMethod;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDrawalStartDate() {
		return drawalStartDate;
	}

	public void setDrawalStartDate(Date drawalStartDate) {
		this.drawalStartDate = drawalStartDate;
	}

	public Date getDrawalEndDate() {
		return drawalEndDate;
	}

	public void setDrawalEndDate(Date drawalEndDate) {
		this.drawalEndDate = drawalEndDate;
	}

	public Date getOverdueDate() {
		return overdueDate;
	}

	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}

	public SeasonStatus getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(SeasonStatus processStatus) {
		this.processStatus = processStatus;
	}
	
	
	public Integer getRecoveryPeriod() {
		return recoveryPeriod;
	}

	public void setRecoveryPeriod(Integer recoveryPeriod) {
		this.recoveryPeriod = recoveryPeriod;
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
				+ ((drawalEndDate == null) ? 0 : drawalEndDate.hashCode());
		result = prime * result
				+ ((drawalStartDate == null) ? 0 : drawalStartDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((overdueDate == null) ? 0 : overdueDate.hashCode());
		result = prime * result
				+ ((processStatus == null) ? 0 : processStatus.hashCode());
		
		result = prime * result
				+ ((recoveryPeriod == null) ? 0 : recoveryPeriod.hashCode());
		
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
		Season other = (Season) obj;
		if (drawalEndDate == null) {
			if (other.drawalEndDate != null)
				return false;
		} else if (!drawalEndDate.equals(other.drawalEndDate))
			return false;
		if (drawalStartDate == null) {
			if (other.drawalStartDate != null)
				return false;
		} else if (!drawalStartDate.equals(other.drawalStartDate))
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
		if (overdueDate == null) {
			if (other.overdueDate != null)
				return false;
		} else if (!overdueDate.equals(other.overdueDate))
			return false;
		if (processStatus != other.processStatus)
			return false;
		
		if (recoveryPeriod == null) {
			if (other.recoveryPeriod != null)
				return false;
		} else if (!recoveryPeriod.equals(other.recoveryPeriod))
			return false;
		return true;
	}
	
}