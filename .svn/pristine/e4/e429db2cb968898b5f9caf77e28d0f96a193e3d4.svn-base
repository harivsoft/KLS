package com.vsoftcorp.kls.business.entity.account;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vsoftcorp.kls.business.entities.Branch;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;

/**
 * @author sponnam TODO: pacs_id will be from the pacs selected and this will
 *         have the branch_id mapped
 */

@Entity
@Table(name = "account_property")
public class AccountProperty implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "accountPropertyIdSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "accountPropertyIdSeq", sequenceName = "account_property_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", referencedColumnName = "id")
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;

	// today this is of no use, but going forward it will be used
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@Basic
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "savings_account_number", unique = true)
	private String savingsAccountNumber;
	
	@Column(name = "ATM_loan_account_number")
	private String atmLoanAccountNumber;
	
	@Column(name = "kcc_card_number")
	private String cardNumber;
	
	@Column(name = "dummy_sb_account_number")
	private String dummySBAccountNumber;


	public String getDummySBAccountNumber() {
		return dummySBAccountNumber;
	}

	public void setDummySBAccountNumber(String dummySBAccountNumber) {
		this.dummySBAccountNumber = dummySBAccountNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getAtmLoanAccountNumber() {
		return atmLoanAccountNumber;
	}

	public void setAtmLoanAccountNumber(String atmLoanAccountNumber) {
		this.atmLoanAccountNumber = atmLoanAccountNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Pacs getPacs() {
		return pacs;
	}

	public void setPacs(Pacs pacs) {
		this.pacs = pacs;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the savingsAccountNumber
	 */
	public String getSavingsAccountNumber() {
		return savingsAccountNumber;
	}

	/**
	 * @param savingsAccountNumber
	 *            the savingsAccountNumber to set
	 */
	public void setSavingsAccountNumber(String savingsAccountNumber) {
		this.savingsAccountNumber = savingsAccountNumber;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pacs == null) ? 0 : pacs.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		AccountProperty other = (AccountProperty) obj;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pacs == null) {
			if (other.pacs != null)
				return false;
		} else if (!pacs.equals(other.pacs))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
}
