/**
 * 
 */
package com.vsoftcorp.kls.service.loan.impl;

import java.math.RoundingMode;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.business.entity.loan.TempLoanRepaymentSchedule;
import com.vsoftcorp.kls.data.LoanRepaymentEditTypeData;
import com.vsoftcorp.kls.data.LoanRepaymentScheduleData;
import com.vsoftcorp.kls.data.LoanRepaymentScheduleDataList;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILoanLineOfCreditDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.dataaccess.loan.ITempLoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.LoanRepaymentScheduleHelper;
import com.vsoftcorp.kls.service.helper.TempLoanRepaymentScheduleHelper;
import com.vsoftcorp.kls.service.loan.ILoanRepaymentScheduleService;
import com.vsoftcorp.kls.service.util.LoanServiceUtil;
import com.vsoftcorp.kls.valuetypes.loan.repayment.LoanRepaymentEditType;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentSchedule;
import com.vsoftcorp.kls.valuetypes.loanproduct.RepaymentType;
import com.vsoftcorp.time.Date;

/**
 * @author a9152
 * 
 */
public class LoanRepaymentScheduleService implements ILoanRepaymentScheduleService {

	private static final Logger logger = Logger.getLogger(LoanRepaymentScheduleService.class);

	@Override
	public void saveRepaymentSchedule(LoanRepaymentScheduleData loanRepaymentScheduleData) {

		logger.info("Start: Saving the loan repayment schedule in saveLoanRepaymentSchedule() method.");
		ILoanRepaymentScheduleDAO dao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
		List<LoanRepaymentSchedule> scheduleList = null;
		scheduleList = LoanRepaymentScheduleHelper.getLoanRepaymentScheduleList(loanRepaymentScheduleData);
		try {
			dao.saveLoanRepaymentScheduleList(scheduleList);
		} catch (Exception e) {
			logger.error("Error while saving the loan repayment schedule list to the database");
			throw new KlsRuntimeException("Error while saving the loan repayment schedule list to the database",
					e.getCause());
		}
		logger.info("End: Saving the loan repayment schedule in saveLoanRepaymentSchedule() method.");
	}

	/**
	 * 
	 */
	public void saveRepaymentSchedule(List<LoanRepaymentSchedule> scheduleList) {

		logger.info("Start: Saving the loan repayment schedule in saveLoanRepaymentSchedule() method.");
		ILoanRepaymentScheduleDAO dao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
		try {
			dao.saveLoanRepaymentScheduleList(scheduleList);
		} catch (Exception e) {
			logger.error("Error while saving the loan repayment schedule list to the database");
			throw new KlsRuntimeException("Error while saving the loan repayment schedule list to the database",
					e.getCause());
		}
		logger.info("End: Saving the loan repayment schedule in saveLoanRepaymentSchedule() method.");
	}

	/**
	 * 
	 * @param lineOfCreditId
	 * @return
	 */
	public LoanRepaymentScheduleDataList getRepaymentScheduleDataList(Long lineOfCreditId, Integer editType) {

		logger.info("Start: Fetching the loan repayment schedule list for a line of credit id in getLoanRepaymentScheduleDataList() method.");
		ILoanRepaymentScheduleDAO dao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
		ITempLoanRepaymentScheduleDAO tempDao = KLSDataAccessFactory.getTempLoanRepaymentScheduleDAO();
		List<LoanRepaymentScheduleData> scheduleDataList = null;
		LoanRepaymentScheduleDataList data = new LoanRepaymentScheduleDataList();
		LoanRepaymentEditTypeData editTypeData = null;
		List<LoanRepaymentSchedule> scheduleList = null;
		List<TempLoanRepaymentSchedule> tempScheduleList = null;
		logger.info(" lineOfCreditId : " + lineOfCreditId);
		try {
			tempScheduleList = tempDao.getTempLoanRepaymentScheduleByLocId(lineOfCreditId);
			if (tempScheduleList != null && !tempScheduleList.isEmpty()) {
				scheduleDataList = TempLoanRepaymentScheduleHelper
						.getTempLoanRepaymentScheduleDataList(tempScheduleList);
			} else {
				scheduleList = dao.getLoanRepaymentScheduleByLocId(lineOfCreditId);
				scheduleDataList = LoanRepaymentScheduleHelper.getLoanRepaymentScheduleDataList(scheduleList);
			}
			data.setLoanRepaymentScheduleData(scheduleDataList);
			editTypeData = getRepaymentEditType(scheduleList, tempScheduleList, editType);
			data.setRepaymentEditTypeData(editTypeData);
		} catch (Exception e) {
			logger.error("Error while fetching the loan repayment schedule list from the database");
			throw new KlsRuntimeException("Error while fetching the loan repayment schedule list from the database",
					e.getCause());
		}
		logger.info("End: Fetching the loan repayment schedule list for a line of credit id in getLoanRepaymentScheduleDataList() method.");
		return data;
	}

	/**
	 * 
	 * @param scheduleList
	 */
	private LoanRepaymentEditTypeData getRepaymentEditType(List<LoanRepaymentSchedule> scheduleList,
			List<TempLoanRepaymentSchedule> tempScheduleList, Integer editType) {

		logger.info("Start: Setting the loan repayment edit type data in getRepaymentEditType() method.");
		LoanRepaymentEditTypeData typeData = new LoanRepaymentEditTypeData();
		if (scheduleList != null && !scheduleList.isEmpty() && editType == null) {
			LoanRepaymentSchedule schedule = scheduleList.get(0);
			ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
			LoanLineOfCredit loanLoc = dao.getLoanLineOfCreditById(schedule.getLoanRepaymentScheduleId()
					.getLineOfCreditId());
			if (loanLoc != null) {
				Product product = loanLoc.getProduct();
				setRepaymentEditTypeData(product, typeData);
			}
		}
		if (tempScheduleList != null && !tempScheduleList.isEmpty() && editType == null) {
			TempLoanRepaymentSchedule tempSchedule = tempScheduleList.get(0);
			ILoanLineOfCreditDAO dao = KLSDataAccessFactory.getLoanLineOfCreditDAO();
			LoanLineOfCredit loanLoc = dao.getLoanLineOfCreditById(tempSchedule.getLoanRepaymentScheduleId()
					.getLineOfCreditId());
			if (loanLoc != null) {
				Product product = loanLoc.getProduct();
				setRepaymentEditTypeData(product, typeData);
			}
		}
		if (editType != null) {
			LoanRepaymentEditType editTypeEnum = LoanRepaymentEditType.getType(editType);
			typeData.setId(editTypeEnum.name());
			typeData.setValue(editTypeEnum.getValue());
		}
		logger.info("End: Setting the loan repayment edit type data in getRepaymentEditType() method.");
		return typeData;
	}

	/**
	 * 
	 * @param product
	 * @param typeData
	 */
	private void setRepaymentEditTypeData(Product product, LoanRepaymentEditTypeData typeData) {

		logger.info("Start: Setting the loan repayment edit type data in setRepaymentEditTypeData() method.");
		if (product != null) {
			RepaymentSchedule repaymentSchedule = product.getRepaymentSchedule();
			logger.info("repaymentSchedule : " + repaymentSchedule);
			RepaymentType repaymentType = product.getRepaymentType();
			logger.info("repaymentType : " + repaymentType);
			if (RepaymentSchedule.PRINCIPAL.equals(repaymentSchedule)) {
				typeData.setId(LoanRepaymentEditType.PRINCIPAL.toString());
				typeData.setValue(LoanRepaymentEditType.PRINCIPAL.getValue());
			} else if (RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
					&& RepaymentType.EQUATED_INSTALLMENTS.equals(repaymentType)) {
				typeData.setId(LoanRepaymentEditType.EQUATED.toString());
				typeData.setValue(LoanRepaymentEditType.EQUATED.getValue());
			} else if (RepaymentSchedule.PRINCIPAL_AND_INTEREST.equals(repaymentSchedule)
					&& RepaymentType.NON_EQUATED_INSTALLMENTS.equals(repaymentType)) {
				typeData.setId(LoanRepaymentEditType.NON_EQUATED.toString());
				typeData.setValue(LoanRepaymentEditType.NON_EQUATED.getValue());
			}
		}
		logger.info("End: Setting the loan repayment edit type data in setRepaymentEditTypeData() method.");
	}

	/**
	 * 
	 */
	public void updateRepaymentScheduleDataList(LoanRepaymentScheduleDataList scheduleDataList) {

		logger.info("Start: Updating the loan repayment schedule list in updateRepaymentScheduleDataList() method.");
		ILoanRepaymentScheduleDAO dao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
		ITempLoanRepaymentScheduleDAO tempDao = KLSDataAccessFactory.getTempLoanRepaymentScheduleDAO();
		List<LoanRepaymentSchedule> scheduleList = LoanRepaymentScheduleHelper
				.getLoanRepaymentScheduleList(scheduleDataList.getLoanRepaymentScheduleData());
		try {
			if (!scheduleList.isEmpty()) {
				LoanRepaymentSchedule schedule = scheduleList.get(0);
				Long loanLocId = schedule.getLoanRepaymentScheduleId().getLineOfCreditId();
				logger.info("loanLocId : " + loanLocId);
				tempDao.deleteTempLoanRepaymentScheduleByLocId(loanLocId);
				dao.updateLoanRepaymentScheduleList(scheduleList);
			}
		} catch (Exception e) {
			logger.error("Error while updating the loan repayment schedule list to the database");
			throw new KlsRuntimeException("Error while updating the loan repayment schedule list.", e.getCause());
		}
		logger.info("End: Updating the loan repayment schedule list in updateRepaymentScheduleDataList() method.");
	}

	@Override
	public LoanRepaymentScheduleData getRepaymentScheduleData(Long lineOfCreditId, Date businessDate) {
		logger.info("Start: Updating the loan repayment schedule list in updateRepaymentScheduleData() method.");
		LoanRepaymentScheduleData data = new LoanRepaymentScheduleData();
		try {
			LoanRepaymentSchedule loanRepaymentSchedule = KLSDataAccessFactory.getLoanRepaymentScheduleDAO().getLoanRepaymentSchedule(lineOfCreditId, businessDate);
			if(loanRepaymentSchedule != null) {
				data = LoanRepaymentScheduleHelper.getLoanRepaymentScheduleData(loanRepaymentSchedule);
			} else {
				data = null;
			}
		}catch(Exception e) {
			logger.error("Error while updating the loan repayment schedule to the database");
			throw new KlsRuntimeException("Error while updating the loan repayment schedule.", e.getCause());
		}
		return data;
	}
	
	@Override
	public List<LoanRepaymentScheduleData> getLoanRepaymentScheduleByLocIdAndBusinessDate(Long lineOfCreditId, Date businessDate) {
		List<LoanRepaymentScheduleData> scheduleListData = null;
		try {
			List<LoanRepaymentSchedule> scheduleList = KLSDataAccessFactory.getLoanRepaymentScheduleDAO().getLoanRepaymentScheduleByLocIdAndBusinessDateForDisburshment(lineOfCreditId, businessDate);
			scheduleListData = LoanRepaymentScheduleHelper.getLoanRepaymentScheduleDataList(scheduleList);
		}catch(Exception e) {
			logger.error("Error while updating the loan repayment schedule to the database");
			throw new KlsRuntimeException("Error while updating the loan repayment schedule.", e.getCause());
		}
		return scheduleListData;
	}
	
	@Override
	public String getNextInstallmentAmount(Long lineOfCreditId, Date businessDate) {
		String nextInstallmentAmount = null;
		try {
			List<LoanRepaymentSchedule> scheduleList = KLSDataAccessFactory.getLoanRepaymentScheduleDAO().getLoanRepaymentScheduleByLocIdAndBusinessDateForDisburshment(lineOfCreditId, businessDate);
			if(scheduleList.size() > 0) {
				nextInstallmentAmount = scheduleList.get(0).getInstallmentAmount().getAmount().setScale(2, RoundingMode.UP).toString();
			} else {
				nextInstallmentAmount = "0";
			}
		}catch(Exception e) {
			logger.error("Error while updating the loan repayment schedule to the database");
			throw new KlsRuntimeException("Error while updating the loan repayment schedule.", e.getCause());
		}
		return nextInstallmentAmount;
	}
	
}
