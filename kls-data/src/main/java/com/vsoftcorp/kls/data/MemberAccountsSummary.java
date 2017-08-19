package com.vsoftcorp.kls.data;

import java.util.List;

import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;

public class MemberAccountsSummary {
	private STAccountsSummary stAccountsSummary;

	private List<MTAccountSummary> mtAccountsSummary;
	
	private PersonData personData;
	
	public STAccountsSummary getStAccountsSummary() {
		return stAccountsSummary;
	}

	public void setStAccountsSummary(STAccountsSummary stAccountsSummary) {
		this.stAccountsSummary = stAccountsSummary;
	}

	public List<MTAccountSummary> getLoanRecoveryData() {
		return mtAccountsSummary;
	}

	public void setLoanRecoveryData(List<MTAccountSummary> mtAccountsSummary) {
		this.mtAccountsSummary = mtAccountsSummary;
	}

	public PersonData getPersonData() {
		return personData;
	}

	public void setPersonData(PersonData personData) {
		this.personData = personData;
	}
	
	
	
}
