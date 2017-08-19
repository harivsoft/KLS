package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.finance.Money;

/**
 * This entity class is used to map the customer table
 * 
 * @author a1605
 * 
 */
@TypeDefs({

@TypeDef(name = "money", typeClass = com.vsoftcorp.finance.hibernate.types.MoneyUserType.class, parameters = {
		@Parameter(name = "defaultISOCurrencyCode", value = "INR"),
		@Parameter(name = "isDebitNegative", value = "true") }) })
@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "customerIdSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "customerIdSequence", sequenceName = "customer_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	// branchid=4,pacsid-6,mbrSrlNo-6 = 16
	// TODO : add branch and pacsid if required
	@Column(name = "cust_id", length = 16)
	private String customerId;

	@Basic
	@Column(name = "member_srl_no", length = 6)
	private String memberSrlNo;

	@Basic
	@Column(name = "cbs_cust_id", length = 16)
	private String cbsCustId;

	@Basic
	@Column(name = "name", length = 45)
	private String name;

	@Basic
	@Column(name = "village_id")
	private Integer villageId;

	@Basic
	@Column(name = "member_type", length = 5)
	private String memberType;

	@Basic
	@Column(name = "share_balance", precision = 22, scale = 6)
	@Type(type = "money")
	private Money shareBalance;

	/*
	 * @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY) private
	 * Set<Account> accounts;
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMemberSrlNo() {
		return memberSrlNo;
	}

	public void setMemberSrlNo(String memberSrlNo) {
		this.memberSrlNo = memberSrlNo;
	}

	public String getCbsCustId() {
		return cbsCustId;
	}

	public void setCbsCustId(String cbsCustId) {
		this.cbsCustId = cbsCustId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public Money getShareBalance() {
		return shareBalance;
	}

	public void setShareBalance(Money shareBalance) {
		this.shareBalance = shareBalance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cbsCustId == null) ? 0 : cbsCustId.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((memberSrlNo == null) ? 0 : memberSrlNo.hashCode());
		result = prime * result + ((memberType == null) ? 0 : memberType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((villageId == null) ? 0 : villageId.hashCode());
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
		Customer other = (Customer) obj;
		if (cbsCustId == null) {
			if (other.cbsCustId != null)
				return false;
		} else if (!cbsCustId.equals(other.cbsCustId))
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
		if (memberSrlNo == null) {
			if (other.memberSrlNo != null)
				return false;
		} else if (!memberSrlNo.equals(other.memberSrlNo))
			return false;
		if (memberType == null) {
			if (other.memberType != null)
				return false;
		} else if (!memberType.equals(other.memberType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (villageId == null) {
			if (other.villageId != null)
				return false;
		} else if (!villageId.equals(other.villageId))
			return false;
		return true;
	}

}
