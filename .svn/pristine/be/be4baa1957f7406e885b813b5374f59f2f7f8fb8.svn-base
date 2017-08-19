package com.vsoftcorp.kls.business.entity.account;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.kls.valuetypes.AccountStatus;
import com.vsoftcorp.time.Date;

/**
 * @author sponnam
 * 
 */

@TypeDefs({
		@TypeDef(name = "accountStatus", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
				@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.AccountStatus"),
				@Parameter(name = "identifierMethod", value = "getValue"),
				@Parameter(name = "convertIdentifierMethod", value = "getType"),
				@Parameter(name = "valueOfMethod", value = "getType") }),

		@TypeDef(name = "com.vsoftcorp.time.Date", typeClass = com.vsoftcorp.time.hibernate.types.InstantUserType.class, parameters = { @Parameter(name = "precision", value = "DAY") }) })
@Entity
@Table(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "accountIdSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "accountIdSeq", sequenceName = "account_id_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Long id;

	// branchid-4,pacsid-5,prdid-3,generatedno-6 = 18::: today product id be
	// nothing(all zeros)
	@Basic
	@Column(name = "account_number", length = 18)
	private String accountNumber;

	@Basic
	@Column(name = "status", length = 1)
	@Enumerated(EnumType.STRING)
	@Type(type = "accountStatus")
	private AccountStatus status;

	@Basic
	@Column(name = "open_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date openDate;

	@Basic
	@Column(name = "close_date")
	@Type(type = "com.vsoftcorp.time.Date")
	private Date closeDate;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_property_id", referencedColumnName = "id", unique = true, nullable = true, insertable = true, updatable = true)
	private AccountProperty accountProperty;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public AccountProperty getAccountProperty() {
		return accountProperty;
	}

	public void setAccountProperty(AccountProperty accountProperty) {
		this.accountProperty = accountProperty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((accountProperty == null) ? 0 : accountProperty.hashCode());
		result = prime * result + ((closeDate == null) ? 0 : closeDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((openDate == null) ? 0 : openDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Account other = (Account) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (accountProperty == null) {
			if (other.accountProperty != null)
				return false;
		} else if (!accountProperty.equals(other.accountProperty))
			return false;
		if (closeDate == null) {
			if (other.closeDate != null)
				return false;
		} else if (!closeDate.equals(other.closeDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (openDate == null) {
			if (other.openDate != null)
				return false;
		} else if (!openDate.equals(other.openDate))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

}
