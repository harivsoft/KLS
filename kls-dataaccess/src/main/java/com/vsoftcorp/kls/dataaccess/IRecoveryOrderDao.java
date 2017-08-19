package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.EventType;
import com.vsoftcorp.kls.business.entities.EventTypeDefinition;

public interface IRecoveryOrderDao {
	public EventTypeDefinition saveRecoverySequence(EventTypeDefinition theRecoverySeq);

	public void saveEventType(EventType theRecoverySeq);

	public EventTypeDefinition getEventTypeDefinition(EventTypeDefinition theEventDef, boolean isCloseSession);
	
	public EventType getEventType(EventType theEventType, boolean isCloseSession);

	public List<EventTypeDefinition> getAllEventTypeDefinitions(boolean isCloseSession);

	public List<EventType> getAllEventTypeBasedOnEventDefinition(Integer eventDefId, boolean isCloseSession);

	public void updateRecoverySequence(EventTypeDefinition theMaster);

	public void updateEventType(EventType theMaster);
}
