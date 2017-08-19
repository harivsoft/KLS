package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.EventTypeDefinition;
import com.vsoftcorp.kls.data.EventTypeData;

public class LoanRecoveryHelper {

	public static EventTypeData getEventTypeData(EventTypeDefinition master) {
		EventTypeData eventTypeData = new EventTypeData();
		eventTypeData.setId(master.getId());
		eventTypeData.setSequenceName(master.getSequenceName());
		eventTypeData.setRecoveryOrder(master.getSeqOrder());
		return eventTypeData;
	}

	public static EventTypeDefinition getRecoverySequence(EventTypeData data) {
		EventTypeDefinition master = new EventTypeDefinition();
		master.setSequenceName(data.getSequenceName());
		return master;
	}
}
