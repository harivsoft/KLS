package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.SlabwiseInterestRate;
import com.vsoftcorp.kls.business.util.DateUtil;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.LoanApplicationStatusReportData;

public class LoanApplicationStatusReportHelper {
	private static final Logger logger = Logger.getLogger(LoanApplicationStatusReportHelper.class);

	public static List<LoanApplicationStatusReportData> getLoanAccountStatusData(List<Object[]> loanAccountList) {

		logger.info("Start: of LoanAccountStatusReportHelper");
		List<LoanApplicationStatusReportData> loanApplicationStatusReportDataList = new ArrayList<LoanApplicationStatusReportData>();
		LoanApplicationStatusReportData loanApplicationStatusReportData = null;
		try {
			for (Object[] object : loanAccountList) {
				loanApplicationStatusReportData = new LoanApplicationStatusReportData();

				loanApplicationStatusReportData.setMemberNumber((String) object[0]);
				loanApplicationStatusReportData.setAppId((BigInteger) object[1]);
				loanApplicationStatusReportData.setMemberName((String) object[2]);
				loanApplicationStatusReportData.setFatherName((String) object[3]);
				loanApplicationStatusReportData.setVillage((String) object[4]);
				loanApplicationStatusReportData.setContact((String) object[5]);
				Date dateOfApp = (Date) object[6];
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String appDate = df.format(dateOfApp);
				loanApplicationStatusReportData.setDateOfApp(appDate);
				loanApplicationStatusReportData.setLoanProduct((String) object[7]);
				loanApplicationStatusReportData.setLoanPurpose((String) object[8]);
				loanApplicationStatusReportData.setLoanSubPurpose((String) object[9]);
				loanApplicationStatusReportData.setLoanActivity((String) object[10]);
				if (object[11] != null) {
					loanApplicationStatusReportData.setTotalRequestedAmt((BigDecimal) object[11]);
				}
				if (object[12] != null) {
					loanApplicationStatusReportData.setTotalAmtAsPerUnit((BigDecimal) object[12]);
				}
				if (object[13] != null) {
					loanApplicationStatusReportData.setRecommondedAmt((BigDecimal) object[13]);
				}
				if (object[14] != null) {
					loanApplicationStatusReportData.setScrutinyAmt((BigDecimal) object[14]);
				}
				loanApplicationStatusReportData.setScrutinyRemarks((String) object[15]);
				if (object[16] != null) {
					loanApplicationStatusReportData.setInspectionAmt((BigDecimal) object[16]);
				}
				loanApplicationStatusReportData.setInspectionRemarks((String) object[17]);
				if (object[18] != null) {
					Date insDate = (Date) object[18];
					DateFormat d = new SimpleDateFormat("dd/MM/yyyy");
					String iDate = d.format(insDate);
					loanApplicationStatusReportData.setDateOfInspection(iDate);
				}
				if (object[19] != null) {
					loanApplicationStatusReportData.setTotalSactionAmt((BigDecimal) object[19]);
					Date sanDate = (Date) object[20];
					DateFormat d = new SimpleDateFormat("dd/MM/yyyy");
					String sDate = d.format(sanDate);
					loanApplicationStatusReportData.setDateOfSaction(sDate);
					loanApplicationStatusReportData.setInterestCatagory((Integer) object[22]);
					Money amt = Money.valueOf(Currency.getInstance("INR"), (BigDecimal) object[19]);
					SlabwiseInterestRate s = KLSDataAccessFactory.getLoanInterestCalculationDAO().getRateOfInterest(
							(Integer) object[22], DateUtil.getVSoftDateByString(sDate), amt);
					loanApplicationStatusReportData.setROI(s.getRoi());
					loanApplicationStatusReportData.setPenalROI(s.getPenalRoi());
				}
				if (object[21] != null) {
					loanApplicationStatusReportData.setLoanPeriod((Integer) object[21]);
				}
				loanApplicationStatusReportData.setLoanType((String) object[23]);
				loanApplicationStatusReportData.setLoanTypeId((Integer) object[24]);
				loanApplicationStatusReportDataList.add(loanApplicationStatusReportData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("End: of DeceasedReportHelper");
		return loanApplicationStatusReportDataList;
	}

}
