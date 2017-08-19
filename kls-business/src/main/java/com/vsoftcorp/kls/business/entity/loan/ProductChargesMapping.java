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

import com.vsoftcorp.kls.business.entities.Product;

@Entity
@Table(name = "product_charges_mapping")
public class ProductChargesMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3643865351097255146L;

	@Id
	@GeneratedValue(generator = "productChargesMappindIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "productChargesMappindIdSequence", sequenceName = "product_charges_mapping_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne(optional = true)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@ManyToOne(optional = true)
	@JoinColumn(name = "charges_master_id", referencedColumnName = "id")
	
	private ChargesMaster chargesMaster;
	@Column(name="bankpercentage")
    private double bankPercentage;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the chargesMaster
	 */
	public ChargesMaster getChargesMaster() {
		return chargesMaster;
	}

	/**
	 * @param chargesMaster
	 *            the chargesMaster to set
	 */
	public void setChargesMaster(ChargesMaster chargesMaster) {
		this.chargesMaster = chargesMaster;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getBankPercentage() {
		return bankPercentage;
	}

	public void setBankPercentage(double bankPercentage) {
		this.bankPercentage = bankPercentage;
	}

	

}
