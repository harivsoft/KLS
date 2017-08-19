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
@Table(name = "bank_pacs_gl_map")
public class BankPacsGl implements Serializable {

	private static final long serialVersionUID = 6067921645190702550L;

	public BankPacsGl() {
	}

	@Id
	@SequenceGenerator(name = "bankPacsGlIdSeq", sequenceName = "bank_pacs_gl_map_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankPacsGlIdSeq")
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "pos_device_no", length = 20)
	private String posDeviceNo;

	@Basic
	@Column(name = "account_no", length = 20)
	private String accountNo;

	@Basic
	@Column(name = "bank_code", length = 20)
	private String bankCode;

	//@ManyToOne(optional = false)
	@ManyToOne
	@JoinColumn(name = "branch_id", referencedColumnName = "ID")
	private Branch branch;

	//@ManyToOne(optional = false)
	@ManyToOne
	@JoinColumn(name = "pacs_id", referencedColumnName = "id")
	private Pacs pacs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPosDeviceNo() {
		return posDeviceNo;
	}

	public void setPosDeviceNo(String posDeviceNo) {
		this.posDeviceNo = posDeviceNo;
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
}