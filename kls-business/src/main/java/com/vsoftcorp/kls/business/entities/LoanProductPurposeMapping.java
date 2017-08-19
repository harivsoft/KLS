package com.vsoftcorp.kls.business.entities;

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

@Entity
@Table(name = "loan_product_purpose_mapping")
public class LoanProductPurposeMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	public LoanProductPurposeMapping() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "loanProductPurposeMappingIdSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "loanProductPurposeMappingIdSeq", sequenceName = "loan_product_purpose_mapping_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@ManyToOne(optional = false)
	@JoinColumn(name = "purpose_id", referencedColumnName = "id")
	private Purpose purpose;

	@ManyToOne(optional = true)
	@JoinColumn(name = "sub_purpose_id", referencedColumnName = "id")
	private SubPurpose subPurpose;

	@ManyToOne(optional = false)
	@JoinColumn(name = "activity_id", referencedColumnName = "id")
	private Activity activity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Purpose getPurpose() {
		return purpose;
	}

	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}

	public SubPurpose getSubPurpose() {
		return subPurpose;
	}

	public void setSubPurpose(SubPurpose subPurpose) {
		this.subPurpose = subPurpose;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}