package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

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

@Entity
@Table(name = "document_mapping")
public class LoanProductDocumentMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	public LoanProductDocumentMapping() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "loanProductDocMappingIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "loanProductDocMappingIdSequence", sequenceName = "loan_product_doc_mapping_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Long id;
	@Basic
	@Column(name = "loan_type", length = 2)
	private String loanType;
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;
	@ManyToOne(optional = false)
	@JoinColumn(name = "document_id", referencedColumnName = "id")
	private Document document;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
