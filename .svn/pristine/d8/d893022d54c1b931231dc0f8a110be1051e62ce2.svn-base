package com.vsoftcorp.kls.business.entities;

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
@Table(name = "pacs_gl_mapping")
public class PacsGlMapping {

	private static final long serialVersionUID = 684500325025395560L;

	public PacsGlMapping() {
		super();
	}

	@Id
	@GeneratedValue(generator = "pacsglmappingIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "pacsglmappingIdSequence", sequenceName = "pacs_gl_mapping_id_seq", allocationSize = 1)
	@Column(name = "ID")
	private Integer id;

	@Basic
	@Column(name = "gl_code", length = 18)
	private String glCode;

	@Basic
	@Column(name = "int_receivable_gl", length = 18)
	private String intReceivableGL;

	@Basic
	@Column(name = "int_received_gl", length = 18)
	private String intReceivedGL;

	@Basic
	@Column(name = "penal_int_received_gl", length = 18)
	private String penalIntReceivedGL;

	@Basic
	@Column(name = "penal_int_receivable_gl", length = 18)
	private String penalintReceivableGL;

	@ManyToOne(optional = false)
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@Basic
	@Column(name = "processing_fee_gl", length = 18)
	private String processingFeeGl;

	@Basic
	@Column(name = "insurance_gl", length = 18)
	private String insuranceGl;

	@Basic
	@Column(name = "closing_charges_gl", length = 18)
	private String closingChargesGl;

	/**
	 * @return the closingChargesGl
	 */
	public String getClosingChargesGl() {
		return closingChargesGl;
	}

	/**
	 * @param closingChargesGl
	 *            the closingChargesGl to set
	 */
	public void setClosingChargesGl(String closingChargesGl) {
		this.closingChargesGl = closingChargesGl;
	}

	public Integer getId() {
		return id;
	}

	public String getGlCode() {
		return glCode;
	}

	public String getIntReceivableGL() {
		return intReceivableGL;
	}

	public String getIntReceivedGL() {
		return intReceivedGL;
	}

	public String getPenalIntReceivedGL() {
		return penalIntReceivedGL;
	}

	public String getPenalintReceivableGL() {
		return penalintReceivableGL;
	}

	public Pacs getPacs() {
		return pacs;
	}

	public Product getProduct() {
		return product;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public void setIntReceivableGL(String intReceivableGL) {
		this.intReceivableGL = intReceivableGL;
	}

	public void setIntReceivedGL(String intReceivedGL) {
		this.intReceivedGL = intReceivedGL;
	}

	public void setPenalIntReceivedGL(String penalIntReceivedGL) {
		this.penalIntReceivedGL = penalIntReceivedGL;
	}

	public void setPenalintReceivableGL(String penalintReceivableGL) {
		this.penalintReceivableGL = penalintReceivableGL;
	}

	public void setPacs(Pacs pacs) {
		this.pacs = pacs;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the processingFeeGl
	 */
	public String getProcessingFeeGl() {
		return processingFeeGl;
	}

	/**
	 * @param processingFeeGl
	 *            the processingFeeGl to set
	 */
	public void setProcessingFeeGl(String processingFeeGl) {
		this.processingFeeGl = processingFeeGl;
	}

	/**
	 * @return the insuranceGl
	 */
	public String getInsuranceGl() {
		return insuranceGl;
	}

	/**
	 * @param insuranceGl
	 *            the insuranceGl to set
	 */
	public void setInsuranceGl(String insuranceGl) {
		this.insuranceGl = insuranceGl;
	}
}
