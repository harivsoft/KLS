package com.vsoftcorp.kls.business.entity.loan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vsoftcorp.time.Date;
/**
 * 
 * @author a1619
 *
 */

@Entity
@Table(name = "pacs_loan_app_document")
public class LoanApplicationDocument implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public LoanApplicationDocument() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator="idsequence",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name="idsequence",sequenceName="pacs_loan_app_document_id_seq",allocationSize=1)
	
	@Column(name="id")
	private Long id;
	
	@Column(name="application_id")
	private Long applicationId;
	
	@Column(name="document_number")
	private String documentNumber;
	
	@Column(name="valid_upto")
	private Date validUpto;
	
	@Column(name="document_id")
	private Long documentId;
	
	@Column(name="image_link")
	private String imageValue;
	
	@Column(name="REMARKS")
	private String remarks;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((applicationId == null) ? 0 : applicationId.hashCode());
		result = prime * result
				+ ((documentId == null) ? 0 : documentId.hashCode());
		result = prime * result
				+ ((documentNumber == null) ? 0 : documentNumber.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((imageValue == null) ? 0 : imageValue.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result
				+ ((validUpto == null) ? 0 : validUpto.hashCode());
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
		LoanApplicationDocument other = (LoanApplicationDocument) obj;
		if (applicationId == null) {
			if (other.applicationId != null)
				return false;
		} else if (!applicationId.equals(other.applicationId))
			return false;
		if (documentId == null) {
			if (other.documentId != null)
				return false;
		} else if (!documentId.equals(other.documentId))
			return false;
		if (documentNumber == null) {
			if (other.documentNumber != null)
				return false;
		} else if (!documentNumber.equals(other.documentNumber))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageValue == null) {
			if (other.imageValue != null)
				return false;
		} else if (!imageValue.equals(other.imageValue))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (validUpto == null) {
			if (other.validUpto != null)
				return false;
		} else if (!validUpto.equals(other.validUpto))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Date getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(Date validUpto) {
		this.validUpto = validUpto;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getImageValue() {
		return imageValue;
	}

	public void setImageValue(String imageValue) {
		this.imageValue = imageValue;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
