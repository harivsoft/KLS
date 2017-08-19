package com.vsoftcorp.kls.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.SanctionedComponentDetail;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.data.SanctionedComponentData;
import com.vsoftcorp.kls.data.SanctionedComponentDetailData;

public class SanctionedComponentHelper {

	public static SanctionedComponentData getSanctionedComponentData(
			List<SanctionedComponentDetail> sanctionedComponentDetailList) {
		SanctionedComponentData data = new SanctionedComponentData();
		List<SanctionedComponentDetailData> sanctionedComponentDetailDataLst = new ArrayList<SanctionedComponentDetailData>();
		SanctionedComponentDetailData sanctionedComponentDetailData = null;
		BigDecimal totPercentage = BigDecimal.ZERO;
		for (SanctionedComponentDetail sanctionedComponentDetail : sanctionedComponentDetailList) {
			sanctionedComponentDetailData = new SanctionedComponentDetailData();

			data.setSeasonId(sanctionedComponentDetail.getSeason().getId()
					.toString());
			data.setSeasonName(sanctionedComponentDetail.getSeason().getName());
			totPercentage = totPercentage.add(sanctionedComponentDetail.getPercentageOfKind());
			sanctionedComponentDetailData.setId(Integer
					.toString(sanctionedComponentDetail.getId()));
			sanctionedComponentDetailData
					.setComponentDescription(sanctionedComponentDetail
							.getComponentDescription());
			sanctionedComponentDetailData
					.setPercentageOfKind(sanctionedComponentDetail
							.getPercentageOfKind().toString());
			sanctionedComponentDetailDataLst.add(sanctionedComponentDetailData);
		}
		data.setSanctionedComponentDetailDataLst(sanctionedComponentDetailDataLst);
		data.setTotPercentage(totPercentage.toString());
		return data;

	}

	public static List<SanctionedComponentDetail> getSanctionedComponentDetail(
			SanctionedComponentData data) {
		List<SanctionedComponentDetail> list = new ArrayList<SanctionedComponentDetail>();
		SanctionedComponentDetail sanctionedComponentDetail = null;
		List<SanctionedComponentDetailData> sanctionedComponentDetailDataList = data
				.getSanctionedComponentDetailDataLst();

		for (SanctionedComponentDetailData sanctionedComponentDetailData : sanctionedComponentDetailDataList) {
			sanctionedComponentDetail = new SanctionedComponentDetail();

			if (sanctionedComponentDetailData.getId() != null)
				sanctionedComponentDetail.setId(Integer
						.parseInt(sanctionedComponentDetailData.getId()));

			Season season = new Season();
			season.setId(Long.parseLong(data.getSeasonId()));
			sanctionedComponentDetail.setSeason(season);

			sanctionedComponentDetail.setPercentageOfKind(BigDecimal
					.valueOf(Double.parseDouble(sanctionedComponentDetailData
							.getPercentageOfKind())));
			sanctionedComponentDetail
					.setComponentDescription(sanctionedComponentDetailData
							.getComponentDescription());
			list.add(sanctionedComponentDetail);
		}
		return list;
	}
}
