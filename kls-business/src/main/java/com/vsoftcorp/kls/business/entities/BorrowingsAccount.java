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

import com.vsoftcorp.kls.business.entity.account.LineOfCredit;

@Entity
@Table(name = "borrowings_account")
public class BorrowingsAccount implements Serializable {

	private static final long serialVersionUID = 6067921645190702550L;

	public BorrowingsAccount() {
	}

	@Id
	@SequenceGenerator(name = "borrowingsAccountIdSeq", sequenceName = "borrowings_account_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrowingsAccountIdSeq")
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "account_no", length = 20)
	private String accountNo;

	@Basic
	@Column(name = "bank_code", length = 20)
	private String bankCode;

	@ManyToOne(optional = false)
	@JoinColumn(name = "branch_id", referencedColumnName = "ID")
	private Branch branch;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@ManyToOne(optional = false)
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;

	@Basic
	@Column(name = "pacs_account_no", length = 20)
	private String pacsAccountNo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "line_of_credit_id", referencedColumnName = "id")
	private LineOfCredit lineOfCredit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	/**
	 * @return the pacsAccountNo
	 */
	public String getPacsAccountNo() {
		return pacsAccountNo;
	}

	/**
	 * @param pacsAccountNo
	 *            the pacsAccountNo to set
	 */
	public void setPacsAccountNo(String pacsAccountNo) {
		this.pacsAccountNo = pacsAccountNo;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the lineOfCredit
	 */
	public LineOfCredit getLineOfCredit() {
		return lineOfCredit;
	}

	/**
	 * @param lineOfCredit
	 *            the lineOfCredit to set
	 */
	public void setLineOfCredit(LineOfCredit lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}
}