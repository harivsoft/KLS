package com.vsoftcorp.kls.business.entity.subsidy;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.vsoftcorp.kls.business.entity.account.LineOfCredit;

@Embeddable
public class SubsidyInterestAmountsId implements Serializable {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locId == null) ? 0 : locId.hashCode());
		result = prime * result
				+ ((subsidySchemeId == null) ? 0 : subsidySchemeId.hashCode());
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
		SubsidyInterestAmountsId other = (SubsidyInterestAmountsId) obj;
		if (locId == null) {
			if (other.locId != null)
				return false;
		} else if (!locId.equals(other.locId))
			return false;
		if (subsidySchemeId == null) {
			if (other.subsidySchemeId != null)
				return false;
		} else if (!subsidySchemeId.equals(other.subsidySchemeId))
			return false;
		return true;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "loc_id", referencedColumnName = "id")
	private LineOfCredit locId;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "subsidy_scheme_id", referencedColumnName = "id")
	private InterestSubsidy subsidySchemeId;

	public LineOfCredit getLocId() {
		return locId;
	}

	public void setLocId(LineOfCredit locId) {
		this.locId = locId;
	}

	public InterestSubsidy getSubsidySchemeId() {
		return subsidySchemeId;
	}

	public void setSubsidySchemeId(InterestSubsidy subsidySchemeId) {
		this.subsidySchemeId = subsidySchemeId;
	}

}
