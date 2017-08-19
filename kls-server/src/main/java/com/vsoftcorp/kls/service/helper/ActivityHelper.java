package com.vsoftcorp.kls.service.helper;

/**
 * @author a1565
 */

import java.math.BigDecimal;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Activity;
import com.vsoftcorp.kls.business.entities.Unit;
import com.vsoftcorp.kls.business.util.MoneyUtil;
import com.vsoftcorp.kls.data.ActivityData;

public class ActivityHelper {

	public static ActivityData getActivityData(Activity master) {

		ActivityData data = new ActivityData();
		data.setName(master.getName());
		data.setId(master.getId());
		if (master.getUnit() != null) {
			data.setUnitId(master.getUnit().getId().toString());
			data.setUnitName(master.getUnit().getName());
		}
		BigDecimal unitCost = master.getUnitCost();
		if (unitCost != null) {
			data.setUnitCost((MasterHelper.roundTo2DecimalPlaces(unitCost)).toString());
		}

		Money maxLimit = master.getMaximumLimit();
		if (maxLimit != null) {
			data.setMaxLimit((MasterHelper.roundTo2DecimalPlaces(maxLimit.getAmount())).toString());
		}
		return data;
	}

	public static Activity getActivity(ActivityData data) {

		Activity master = new Activity();
		if (data.getId() != null)
			master.setId(data.getId());
		master.setName(data.getName());
		if (data.getUnitId() != null) {
			Unit unit = new Unit();
			unit.setId(Integer.parseInt(data.getUnitId()));
			master.setUnit(unit);
		}
		String unitCost = data.getUnitCost();
		if (unitCost != null) {
			BigDecimal bd = new BigDecimal(unitCost);
			master.setUnitCost(bd);
		}
		String maxLimit = data.getMaxLimit();
		if (maxLimit != null) {
			BigDecimal bd = new BigDecimal(maxLimit);
			Money maxLimitMoney = MoneyUtil.getAccountingMoney(bd).getMoney();
			master.setMaximumLimit(maxLimitMoney);
		}
		return master;
	}

}
