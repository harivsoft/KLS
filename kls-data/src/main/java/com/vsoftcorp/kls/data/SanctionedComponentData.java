package com.vsoftcorp.kls.data;

import java.util.List;

public class SanctionedComponentData {
	private String seasonId;

	private String seasonName;

	private String totPercentage;

	private List<SanctionedComponentDetailData> sanctionedComponentDetailDataLst;

	private List<DeleteSanctionedComponent> deleteSanctionedComponent;

	public String getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(String seasonId) {
		this.seasonId = seasonId;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getTotPercentage() {
		return totPercentage;
	}

	public void setTotPercentage(String totPercentage) {
		this.totPercentage = totPercentage;
	}

	public List<SanctionedComponentDetailData> getSanctionedComponentDetailDataLst() {
		return sanctionedComponentDetailDataLst;
	}

	public void setSanctionedComponentDetailDataLst(
			List<SanctionedComponentDetailData> sanctionedComponentDetailDataLst) {
		this.sanctionedComponentDetailDataLst = sanctionedComponentDetailDataLst;
	}

	public List<DeleteSanctionedComponent> getDeleteSanctionedComponent() {
		return deleteSanctionedComponent;
	}

	public void setDeleteSanctionedComponent(
			List<DeleteSanctionedComponent> deleteSanctionedComponent) {
		this.deleteSanctionedComponent = deleteSanctionedComponent;
	}

}
