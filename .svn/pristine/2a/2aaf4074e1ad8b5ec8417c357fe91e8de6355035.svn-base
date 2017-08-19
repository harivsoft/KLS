package com.vsoftcorp.kls.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.dataaccess.ILocClosureDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ILocClosureService;
import com.vsoftcorp.kls.valuetypes.LineOfCreditStatus;

public class LocClosureService implements ILocClosureService {
	private static final Logger logger = Logger
			.getLogger(LocClosureService.class);

	@Override
	public int updateLocClosure(String businessDate) {

		logger.info("Start : Calling LocCloserDAO in updateLocCloser() method.");
		LineOfCredit loc = new LineOfCredit();
		ILocClosureDAO dao = KLSDataAccessFactory.getLocClosureDAO();
		List<Long> locIds = dao
				.getAllLineOfCreditIdsForLocClosure(businessDate);
		try {
			if(locIds.size() > 0){
			for (int i = 0; i < locIds.size(); i++) {
				loc.setId(locIds.get(i));
				loc.setLineOfCreditStatus(LineOfCreditStatus.Closed);
				dao.updateLocClosure(loc);
			}
			}
		} catch (Exception excp) {
			excp.printStackTrace();
			logger.error("error while updating the LOC Closure");

		}
		logger.info("End : Calling LocCloserDAO in updateLocCloser() method.");
		return locIds.size();
	}

}
