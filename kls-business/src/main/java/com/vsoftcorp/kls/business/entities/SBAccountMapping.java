package com.vsoftcorp.kls.business.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vsoftcorp.kls.valuetypes.SBAccountStatus;
import com.vsoftcorp.time.Date;
/*
 * skeleti
 */

@TypeDefs({
	@TypeDef(name = "sbAccountStatus", typeClass = com.vsoftcorp.common.hibernate.types.EnumUserType.class, parameters = {
			@Parameter(name = "enumClass", value = "com.vsoftcorp.kls.valuetypes.SBAccountStatus"),
			@Parameter(name = "identifierMethod", value = "getValue"),
			@Parameter(name = "convertIdentifierMethod", value = "getType"),
			@Parameter(name = "valueOfMethod", value = "getType") }),
		})


@Entity
@Table(name="sb_account_mappig_info")
public class SBAccountMapping {
	
	
	@Id
	@Column(name = "party_id")
	private Long partyId;
	
	@Basic
	@Column(name = "pacs_id")
	private Long pacsId;
	
	@Basic
	@Column(name = "business_date")
	private Date businessDate;
	
	@Enumerated(EnumType.STRING)
	@Type(type = "sbAccountStatus")
	@Column(name = "sb_status")
	private SBAccountStatus status;
	
	@Basic
	@Column(name = "savings_account_no")
	private String savingsAccountNo;
	
	@Basic
	@Column(name = "remarks")
	private String remarks;

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public Long getPacsId() {
		return pacsId;
	}

	public void setPacsId(Long pacsId) {
		this.pacsId = pacsId;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public SBAccountStatus getStatus() {
		return status;
	}

	public void setStatus(SBAccountStatus status) {
		this.status = status;
	}

	public String getSavingsAccountNo() {
		return savingsAccountNo;
	}

	public void setSavingsAccountNo(String savingsAccountNo) {
		this.savingsAccountNo = savingsAccountNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
