package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;


/**
 * 
 * @author a9153
 * This is the entity class with JPA mappings for General ledger
 */



@Entity
@Table(name = "pacs_gl")
public class PacsGl implements Serializable {

	private static final long serialVersionUID = -7561752101720603519L;

	public PacsGl() {
	}

	@Id
	@SequenceGenerator(name = "glSeq", sequenceName = "pacsgl_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "glSeq")
	@Column(name = "id")
	private Long id;
	
	
	@Basic
	@Column(name = "GL_CODE", length = 12)
	private String glCode;
	
	@Basic
	@Column(name = "NAME", nullable=false, length = 45)
	private String name;
	
	@Basic
	@Column(name = "gltype", nullable=false, length = 1)
	private String type;
	
	
	@Basic
	@Column(name="ENTERED_BY", length = 45)
	private String enteredBy;
	
	@Basic
	@Column(name="ENTERED_DATE")
	private Date enteredDate;
	
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private  Pacs pacs;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getGlCode() {
		return glCode;
	}


	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getEnteredBy() {
		return enteredBy;
	}


	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}


	public Date getEnteredDate() {
		return enteredDate;
	}


	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}


	public Pacs getPacs() {
		return pacs;
	}


	public void setPacs(Pacs pacs) {
		this.pacs = pacs;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((enteredBy == null) ? 0 : enteredBy.hashCode());
		result = prime * result
				+ ((enteredDate == null) ? 0 : enteredDate.hashCode());
		result = prime * result + ((glCode == null) ? 0 : glCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pacs == null) ? 0 : pacs.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		PacsGl other = (PacsGl) obj;
		if (enteredBy == null) {
			if (other.enteredBy != null)
				return false;
		} else if (!enteredBy.equals(other.enteredBy))
			return false;
		if (enteredDate == null) {
			if (other.enteredDate != null)
				return false;
		} else if (!enteredDate.equals(other.enteredDate))
			return false;
		if (glCode == null) {
			if (other.glCode != null)
				return false;
		} else if (!glCode.equals(other.glCode))
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
		if (pacs == null) {
			if (other.pacs != null)
				return false;
		} else if (!pacs.equals(other.pacs))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}