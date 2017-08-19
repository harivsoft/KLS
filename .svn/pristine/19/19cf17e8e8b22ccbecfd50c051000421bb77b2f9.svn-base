package com.vsoftcorp.kls.service.loan.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.PacsLoanApplicationDetail;
import com.vsoftcorp.kls.business.entities.PacsLoanApplicationHeader;
import com.vsoftcorp.kls.business.entities.Season;
import com.vsoftcorp.kls.data.LoanApplicationRenewalData;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationDetailDAO;
import com.vsoftcorp.kls.dataaccess.IPacsLoanApplicationHeaderDAO;
import com.vsoftcorp.kls.dataaccess.ISeasonDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILoanApplicationRenewalDAO;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.loan.ILoanApplicationRenewalService;
import com.vsoftcorp.kls.valuetypes.LoanApplicationState;

/**
 * @author a1605
 * 
 */
public class LoanApplicationRenewalService implements ILoanApplicationRenewalService {

	private static final Logger logger = Logger.getLogger(LoanApplicationRenewalService.class);

	@Override
	public String updateLoanApplicationRenewal(LoanApplicationRenewalData data, Integer pacId) {

		logger.info(" Start: inside updateLoanApplicationRenewal() ");
		ILoanApplicationRenewalDAO dao = KLSDataAccessFactory.getLoanApplicationRenewalDAO();
		IPacsLoanApplicationHeaderDAO headerDAO = KLSDataAccessFactory.getPacsLoanApplicationHeaderDAO();
		IPacsLoanApplicationDetailDAO detailDAO = KLSDataAccessFactory.getPacsLoanApplicationDetailDAO();
		StringBuilder response = new StringBuilder();
		try {

			PacsLoanApplicationHeader newHeader = null;
			List<PacsLoanApplicationHeader> oldHeaderList = dao.getLoanApplicationHeaderForRenewal(data.getOldFinancialYear(), pacId);
			ISeasonDAO seasonDAO = KLSDataAccessFactory.getSeasonDAO();
			List<Season> previousSeasonLst = seasonDAO.getAllSeasonsBySeasonYr(data.getOldFinancialYear());
			List<Season> newSeasonLst = seasonDAO.getAllSeasonsBySeasonYr(data.getNewFinancialYear());
			List<Long> headerIdList = new ArrayList<Long>();
			int count = 0;
			if (oldHeaderList.size() > 0) {
				if (previousSeasonLst.size()>0&&previousSeasonLst.size() <= newSeasonLst.size()) {
					for (Iterator<PacsLoanApplicationHeader> iterator = oldHeaderList.iterator(); iterator.hasNext();) {
						PacsLoanApplicationHeader pacsLoanApplicationHeader = (PacsLoanApplicationHeader) iterator.next();
						if (pacsLoanApplicationHeader.getIsRenewed() != null && pacsLoanApplicationHeader.getIsRenewed() != 1) {
							newHeader = new PacsLoanApplicationHeader();
							newHeader.setFinancialYear(data.getNewFinancialYear());
							newHeader.setApplicationDate(new Date(System.currentTimeMillis()));
							newHeader.setApplicationType(pacsLoanApplicationHeader.getApplicationType());
							//newHeader.setCrop(pacsLoanApplicationHeader.getCrop());
							newHeader.setPacs(pacsLoanApplicationHeader.getPacs());
							newHeader.setProcessStatus("A");
							newHeader.setScheme(pacsLoanApplicationHeader.getScheme());

							List<PacsLoanApplicationDetail> oldDetailList = dao.getLoanApplicationDetailForRenewal(pacsLoanApplicationHeader.getId());

							headerDAO.savePacsLoanApplicationHeader(newHeader);
							headerIdList.add(newHeader.getId());
							PacsLoanApplicationDetail newDetail = null;
							for (Iterator<PacsLoanApplicationDetail> iterator2 = oldDetailList.iterator(); iterator2.hasNext();) {
								PacsLoanApplicationDetail pacsLoanApplicationDetail = (PacsLoanApplicationDetail) iterator2.next();
								newDetail = new PacsLoanApplicationDetail();
								newDetail.setApplicationStatus(LoanApplicationState.ENTERED);
								newDetail.setHeaderId(newHeader);
								String oldSeasonName = pacsLoanApplicationDetail.getSeasonId().getName();
								String newSeasonName = oldSeasonName.substring(0, oldSeasonName.lastIndexOf("_")) + "_" + data.getNewFinancialYear();
								Season newSeason = dao.getSeasonByName(newSeasonName);

								newDetail.setCalculatedAmount(pacsLoanApplicationDetail.getCalculatedAmount());
								newDetail.setCropId(pacsLoanApplicationDetail.getCropId());
								newDetail.setCustomerId(pacsLoanApplicationDetail.getCustomerId());
								newDetail.setEnteredRemarks(pacsLoanApplicationDetail.getEnteredRemarks());
								newDetail.setLandArea(pacsLoanApplicationDetail.getLandArea());
								newDetail.setSeasonId(pacsLoanApplicationDetail.getSeasonId());
								newDetail.setRecommendedAmount(pacsLoanApplicationDetail.getRecommendedAmount());
								newDetail.setPriority(pacsLoanApplicationDetail.getPriority());
								newDetail.setLoanApplicationNo(pacsLoanApplicationDetail.getLoanApplicationNo());
								newDetail.setLandTypeId(pacsLoanApplicationDetail.getLandTypeId());
								newDetail.setRequiredAmount(pacsLoanApplicationDetail.getRequiredAmount());
								if (newSeason != null) {
									newDetail.setSeasonId(newSeason);
									detailDAO.savePacsLoanApplicationDetail(newDetail);
									count++;
								}
							}
							pacsLoanApplicationHeader.setIsRenewed(1);
							headerDAO.updatePacsLoanApplicationHeader(pacsLoanApplicationHeader);
						}
					}
					if (count == 0) {
						response.append("Loan applications are already renewed");
					} else {
						response.append("New Master Applications Created : ");
						response.append(headerIdList.toString());
						response.append("\n Number Of Former Applications Created : " + count);
					}
				} else
					response.append("Seasons are not created in financial year");
			} else
				response.append("Master applications not found in previous financial year");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" Error:While doing loan renewal) ");
			throw new KlsRuntimeException("Loan application renewal cannot be done");
		}
		logger.info(" End: inside updateLoanApplicationRenewal() ");
		return response.toString();
	}
}
