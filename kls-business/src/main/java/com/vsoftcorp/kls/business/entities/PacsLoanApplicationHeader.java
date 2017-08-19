package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author a9153 This is the entity class with JPA mappings for
 *         pacs_loan_application_hdr
 */

@Entity
@Table(name = "pacs_loan_application_hdr")
public class PacsLoanApplicationHeader implements Serializable {

	private static final long serialVersionUID = -7561752101720603519L;

	public PacsLoanApplicationHeader() {
	}

	@Id
	@GeneratedValue(generator = "pacsLoanApplHdrIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "pacsLoanApplHdrIdSequence", sequenceName = "pacs_loan_appl_hdr_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "process_status", length = 1)
	private String processStatus;

	@Basic
	@Column(name = "application_type", length = 1)
	private String applicationType;

	@Basic
	@Column(name = "application_date")
	private Date applicationDate;

	@Basic
	@Column(name = "financial_year", length = 10)
	private String financialYear;

	@ManyToOne(optional = false)
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;

	/*@ManyToOne(optional = false)
	@JoinColumn(name = "crop_id", referencedColumnName = "id")
	private Crop crop;*/

	@ManyToOne(optional = false)
	@JoinColumn(name = "scheme_id", referencedColumnName = "id")
	private Scheme scheme;

	@Basic
	@Column(name = "is_renewed", length = 1)
	private Integer isRenewed = 0;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public Pacs getPacs() {
		return pacs;
	}

	public void setPacs(Pacs pacs) {
		this.pacs = pacs;
	}

	/*public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}*/

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public Integer getIsRenewed() {
		return isRenewed;
	}

	public void setIsRenewed(Integer isRenewed) {
		this.isRenewed = isRenewed;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}