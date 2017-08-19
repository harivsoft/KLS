package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.EventType;
import com.vsoftcorp.kls.business.entities.EventTypeDefinition;
import com.vsoftcorp.kls.data.EventTypeData;
import com.vsoftcorp.kls.dataaccess.IRecoveryOrderDao;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.IRecoveryOrderService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.LoanRecoveryHelper;

public class RecoveryOrderService implements IRecoveryOrderService {
	private static final Logger logger = Logger
			.getLogger(RecoveryOrderService.class);

	@Override
	public void saveRecoveryOrder(EventTypeData data) {

		logger.info("Start : Calling recovery sequence dao in saveRecoveryOrder() method.");
		IRecoveryOrderDao dao = KLSDataAccessFactory.getRecoveryOrderDAO();
		EventTypeDefinition master = LoanRecoveryHelper
				.getRecoverySequence(data);
		EventTypeDefinition maste = new EventTypeDefinition();
		EventTypeDefinition maste1 = new EventTypeDefinition();
		EventTypeDefinition dbMaster = null;
		EventType dbEventType = null;
		// List<EventTypeDefinition> EventTypeDefinitionLst = new ArrayList<>();
		try {
			String recSeq[] = data.getRecoveryOrder().split(",");
			for (int i = 0; i < recSeq.length; i++) {
				if (i == 0) {
					dbMaster = dao.getEventTypeDefinition(master, false);
					if (dbMaster == null) {
						maste = dao.saveRecoverySequence(master);
					}
				}
				EventType evntType = new EventType();
				maste1.setId(maste.getId());
				// EventTypeDefinitionLst.add(maste1);
				evntType.setEventDefId(maste1);
				evntType.setRecoveryOrderId(i + 1);
				evntType.setRecoverySequence(Integer.parseInt(recSeq[i]));
				dbEventType = dao.getEventType(evntType, false);
				if (dbEventType == null) {
					dao.saveEventType(evntType);
				}
			}
		} catch (Exception excp) {
			logger.error("Recovery Sequence data cannot be saved");
			throw new KlsRuntimeException(
					"Recovery Sequence data cannot be saved", excp);
		}
		if (dbMaster != null) {
			logger.error("Recovery Sequence data already exists");
			throw new KlsRuntimeException(
					"Recovery Sequence data already exists");
		}
		logger.info("End : Calling Recovery Sequence dao in saveRecoveryOrder() method.");
	}

	@Override
	public List<EventTypeData> getAllRecoverySequnces() {

		logger.info("Start : Calling recovery sequence dao in getAllRecoverySequnces() method.");
		// get the bank parameter dao.
		IRecoveryOrderDao dao = KLSDataAccessFactory.getRecoveryOrderDAO();
		List<EventTypeData> eventTypeDataList = new ArrayList<EventTypeData>();
		try {
			String seqOrder = null;
			List<EventTypeDefinition> eventTypeDefList = dao
					.getAllEventTypeDefinitions(false);
			List<EventType> seqList = new ArrayList<>();
			for (EventTypeDefinition data : eventTypeDefList) {
				seqList = dao.getAllEventTypeBasedOnEventDefinition(
						data.getId(), true);
				if (seqList.size() > 0) {
					for (int i = 0; i < seqList.size(); i++) {
						if (i == 0)
							seqOrder = Integer.toString(seqList.get(i)
									.getRecoverySequence());
						else
							seqOrder = seqOrder
									+ ','
									+ Integer.toString(seqList.get(i)
											.getRecoverySequence());
					}
				}
				data.setSeqOrder(seqOrder);
				eventTypeDataList.add(LoanRecoveryHelper
						.getEventTypeData((data)));
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the recovery sequence records");
			throw new KlsRuntimeException(
					"Error in retrieving all the recovery sequence records",
					excp.getCause());
		}
		logger.info("End : Calling recovery sequence dao in getAllRecoverySequnces() method.");
		return eventTypeDataList;
	}

	@Override
	public void updateRecoverySequence(EventTypeData theEventTypeData) {

		logger.info("Start: Inside method updateProduct()");
		try {
			IRecoveryOrderDao dao = KLSDataAccessFactory.getRecoveryOrderDAO();
			EventTypeDefinition master = LoanRecoveryHelper
					.getRecoverySequence(theEventTypeData);
			String recSeq[] = theEventTypeData.getRecoveryOrder().split(",");
			List<EventType> seqList = dao
					.getAllEventTypeBasedOnEventDefinition(
							theEventTypeData.getId(), true);
			if (seqList.size() > 0) {
				for (int i = 0; i < seqList.size(); i++) {
					if (i == 0) {
						master.setId(theEventTypeData.getId());
						dao.updateRecoverySequence(master);
					}
					EventType evntType = new EventType();

					evntType.setId(seqList.get(i).getId());
					evntType.setRecoverySequence(Integer.parseInt(recSeq[i]));
					dao.updateEventType(evntType);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Exception while updating Product: " + e.getMessage());
			throw new KlsRuntimeException("Exception while saving Product: ",
					e.getCause());
		}
		logger.info("End: Inside method updateProduct()");
	}

}
