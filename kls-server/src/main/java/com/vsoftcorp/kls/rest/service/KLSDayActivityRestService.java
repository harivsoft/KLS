package com.vsoftcorp.kls.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.vsoftcorp.kls.business.entities.BankParameter;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.data.BankParameterData;
import com.vsoftcorp.kls.data.DayActivityData;
import com.vsoftcorp.kls.data.DayEndResponse;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;

import com.vsoftcorp.kls.service.IDayBeginService;
import com.vsoftcorp.kls.service.IOfflineDayEnd;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.service.transaction.IAccountWiseConsistencyService;
import com.vsoftcorp.kls.util.EntityManagerUtil;
import com.vsoftcorp.kls.valuetypes.BankProcessStatus;
import com.vsoftcorp.kls.valuetypes.calendar.EventType;
import com.vsoftcorp.time.Date;

@Path("/dayAction")
public class KLSDayActivityRestService {
	private static final Logger logger = Logger.getLogger(KLSDayActivityRestService.class);

	public KLSDayActivityRestService() {

	}

	private static final KLSDayActivityRestService INSTANCE = new KLSDayActivityRestService();

	public static KLSDayActivityRestService getInstance() {
		return INSTANCE;
	}

	@POST
	@Path("/dayActivity")
	@Consumes("application/json")
	@Produces("application/text")
	public String saveDayBegin(String dayActivityJsonString) {

		logger.info("Start : Calling Day Activity service in saveDayBegin() method.");
		String returnMsg = null;
		DayActivityData data = null;
		Gson gson = new Gson();
		data = gson.fromJson(dayActivityJsonString, DayActivityData.class);
		if (!data.getUserDetails().getIsBankUser()) {
			/* EntityManagerUtil.closeSession(); */
			/* return "User Not Authorized for Day Activity"; */
			returnMsg = "User Not Authorized for Day Activity";

		} else {
			Map businessDateData = KLSServiceFactory.getCalendarService().getNextBusinessDate(data.getBusinessDate(),
					0);

			logger.info(businessDateData.get("isValid"));

			if (businessDateData.get("isValid") == "false") {
				/* EntityManagerUtil.closeSession(); */
				/* return "Calendar / Next Business Date is not available"; */
				returnMsg = "Calendar / Next Business Date is not available";
			} else {
				try {
					IDayBeginService dayBeginService = KLSServiceFactory.getDayBeginService();

					DateUtil.getVSoftDateByString(data.getBusinessDate()).next();
					// As of now for holidays also day end should do
					returnMsg = dayBeginService.updateDayBegin(data, (String) businessDateData.get("type"),
							// DateUtil.getVSoftDateByString(businessDateData.get("nextBusinessDate").toString()));
							DateUtil.getVSoftDateByString(data.getBusinessDate()).next(), data.getGlDataList());

					if (returnMsg != "")
						return returnMsg + " GL does not exist in Pacs Suvikas";

					logger.info("End : Calling Day Activity service in saveDayBegin() method."
							+ data.getBankProcessStatus());
					if (data.getBankProcessStatus() == BankProcessStatus.DAY_BEGIN.getValue()) {
						/*
						 * EntityManagerUtil.closeSession(); return
						 * "Day Begin Started !";
						 */
						returnMsg = "Day Begin Started !";
					} else {
						/*
						 * EntityManagerUtil.closeSession(); return
						 * EventType.getType((String)
						 * businessDateData.get("type")) .getValue() +
						 * " Completed !";
						 */

						returnMsg = EventType.getType((String) businessDateData.get("type")).getValue()
								+ " Completed !";
					}
				} catch (Exception e) {
					/* EntityManagerUtil.closeSession(); */
					/* return e.getMessage(); */
					returnMsg = e.getMessage();
				}
			}

		}
		EntityManagerUtil.closeSession();
		return returnMsg;
	}

	@POST
	@Path("/offlineDayActivity")
	@Consumes("application/json")
	@Produces("application/text")
	public String offlineDayActivity(String dayActivityJsonString) {

		logger.info("Start : Calling Day Activity service in saveDayBegin() method.");
		String returnMsg = null;
		DayActivityData data = null;
		Gson gson = new Gson();
		data = gson.fromJson(dayActivityJsonString, DayActivityData.class);
		List<DayEndResponse> dayendResponse = new ArrayList<DayEndResponse>();
		if (!data.getUserDetails().getIsBankUser()) {
			/* EntityManagerUtil.closeSession(); */
			/* return "User Not Authorized for Day Activity"; */
			returnMsg = "User Not Authorized for Day Activity";

		} else {
			try {
				IOfflineDayEnd offlineDayEnd = KLSServiceFactory.getOfflineDayEnd();
				// As of now for holidays also day end should do
				dayendResponse = offlineDayEnd.doOfflineDayEnd(data, data.getGlDataList(),data.getUserDetails().getUserId());
				returnMsg = gson.toJson(dayendResponse);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		EntityManagerUtil.closeSession();
		return returnMsg;
	}

	@POST
	@Path("/inconsistency")
	@Consumes("application/json")
	@Produces("application/text")
	public String checkInconsistency(String jsonString) {

		logger.info("Start : Calling AccountWiseConsistencyService service in checkInconsistency() method.");
		BankParameterData data = null;
		try {
			IAccountWiseConsistencyService inconsistencyService = KLSServiceFactory.getAccountWiseConsistencyService();
			Gson gson = new Gson();
			data = gson.fromJson(jsonString, BankParameterData.class);
			BankParameter master = KLSDataAccessFactory.getBankParameterDAO().getAllBankParameters().get(0);
			// BankParameterHelper.getBankParameter(data);
			Date currentDate = master.getBusinessDate();
			// List<AccountWiseConsistency> inconsistencylist=new
			// ArrayList<AccountWiseConsistency>();
			// inconsistencylist=inconsistencyService.checkAccountWiseConsistency(currentDate);
			inconsistencyService.checkAccountWiseConsistency(currentDate);
			// int list=inconsistencylist.size();
			// String listSize=String.valueOf(list);

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : Calling AccountWiseConsistencyService service in checkInconsistency() method.");

		return "Inconsistency Check Completed !";

	}

}
