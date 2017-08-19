/**
 * 
 */
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

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.time.Date;

@TypeDefs({
	@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }),
})
/**
 * @author a1621
 *
 */
@Entity
@Table(name = "calendar_setup")
public class CalendarSetup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CalendarSetup(){
		
	}
	
	@Id
	@GeneratedValue(generator = "calendarSetupIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "calendarSetupIdSequence", sequenceName = "calendar_setup_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "pacs_id", length = 6)
	private Integer pacsId ;
	
	@Basic
	@Column(name = "branch_id", length = 4)
	private Integer branchId ;
	
	@Basic
	@Column(name = "weekly_off")
	private Integer weeklyOff;
	
	@Basic
	@Column(name = "last_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date lastDate ;

	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the pacsId
	 */
	public Integer getPacsId() {
		return pacsId;
	}

	/**
	 * @return the weeklyOff
	 */
	public Integer getWeeklyOff() {
		return weeklyOff;
	}

	/**
	 * @param weeklyOff the weeklyOff to set
	 */
	public void setWeeklyOff(Integer weeklyOff) {
		this.weeklyOff = weeklyOff;
	}

	/**
	 * @param pacsId the pacsId to set
	 */
	public void setPacsId(Integer pacsId) {
		this.pacsId = pacsId;
	}

	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the lastDate
	 */
	public Date getLastDate() {
		return lastDate;
	}

	/**
	 * @param lastDate the lastDate to set
	 */
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastDate == null) ? 0 : lastDate.hashCode());
		result = prime * result + ((pacsId == null) ? 0 : pacsId.hashCode());
		result = prime * result
				+ ((weeklyOff == null) ? 0 : weeklyOff.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalendarSetup other = (CalendarSetup) obj;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastDate == null) {
			if (other.lastDate != null)
				return false;
		} else if (!lastDate.equals(other.lastDate))
			return false;
		if (pacsId == null) {
			if (other.pacsId != null)
				return false;
		} else if (!pacsId.equals(other.pacsId))
			return false;
		if (weeklyOff == null) {
			if (other.weeklyOff != null)
				return false;
		} else if (!weeklyOff.equals(other.weeklyOff))
			return false;
		return true;
	}
	
	
}
