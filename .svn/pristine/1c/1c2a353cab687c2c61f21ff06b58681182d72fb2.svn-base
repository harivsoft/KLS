package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Village;
import com.vsoftcorp.kls.business.entity.loan.LoanRepaymentSchedule;
import com.vsoftcorp.kls.dataaccess.IPacsDAO;
import com.vsoftcorp.kls.dataaccess.IProductDAO;
import com.vsoftcorp.kls.dataaccess.IVillageDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRecoveryDAO;
import com.vsoftcorp.kls.dataaccess.loan.ILoanRepaymentScheduleDAO;
import com.vsoftcorp.kls.report.dao.IOverdueReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.OverdueListReportData;
import com.vsoftcorp.time.Date;

/*import com.vsoftcorp.time.Date;*/

public class OverdueReportHelper {

	public static List<OverdueListReportData> getOverdueListReport(List<Object[]> shareAccountList, Date instalmentDate) {
		List<OverdueListReportData> overdueDataList = new ArrayList<OverdueListReportData>();
		OverdueListReportData data = null;
		try {
			for (Object[] object : shareAccountList) {
				data = new OverdueListReportData();

				Integer pacsId = (Integer) object[0];

				data.setPacsId(pacsId);

				IPacsDAO pDao = KLSDataAccessFactory.getPacsDAO();
				Pacs pacs = pDao.getPacs(pacsId);
				data.setPacsName(pacs.getName());

				BigDecimal villageId = (BigDecimal) object[2];

				data.setVillageId(villageId.intValue());

				IVillageDAO vDao = KLSDataAccessFactory.getVillageDAO();

				Village v = new Village();
				v.setId(villageId.intValue());

				Village village = vDao.getVillage(v);
				data.setVillageName(village.getName());

				Integer productId = (Integer) object[4];
				data.setProductId(productId);

				IProductDAO prodDao = KLSDataAccessFactory.getProductMasterDAO();
				Product product = new Product();
				product.setId(productId);
				product = prodDao.getProduct(product, false);

				data.setProductName(product.getName());

				BigInteger customerId = (BigInteger) object[5];
				// PersonData customer =
				// RestClientUtil.getCustomerById(customerId.longValue());
				// if (customer != null)
				data.setMemberName((String) object[6]);
				if (object[7] != null)
					data.setPhoneNumber((String) object[7]);
				data.setAccountNumber((String) object[8]);
				data.setSanctionLimit((BigDecimal) object[9]);
				data.setLoanType((String) object[10]);
				data.setLoanTypeId((Integer) object[12]);
				BigInteger locId = (BigInteger) object[11];

				BigDecimal disbursemntAmt = null;

				IOverdueReportDAO oDao = KLSReportDataAccessFactory.getOverdueReportDAO();
				disbursemntAmt = oDao.getDisbursedAmountByLocId(Long.valueOf(locId.toString()));
				System.out.println("disbursemntAmt : " + disbursemntAmt);

				data.setDisbursedAmount(disbursemntAmt);
				data.setMemberNo(customerId.toString());
				// Date tranDate = (Date) object[7];

				// DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				// String text = df.format(tranDate);

				// data.setBusinessDate(text);

				ILoanRepaymentScheduleDAO lDao = KLSDataAccessFactory.getLoanRepaymentScheduleDAO();
				List<LoanRepaymentSchedule> list = lDao.getLoanRepaymentScheduleByLocId(Long.valueOf(locId.toString()));

				ILoanRecoveryDAO recDao = KLSDataAccessFactory.getLoanRecoveryDAO();
				Money recAmount = recDao.getLoanRecoveryByLocIdAndDate(Long.valueOf(locId.toString()), instalmentDate);
				int noOfInstalments = list.size();
				for (LoanRepaymentSchedule loanRepaymentSchedule : list) {
					if (recAmount != null) {
						if (recAmount.compareTo(Money.ZERO) == 1) {
							recAmount = recAmount.subtract(loanRepaymentSchedule.getInstallmentAmount());
							noOfInstalments = noOfInstalments - 1;
						}
					}
				}
				System.out.println("noOfInstalments==" + noOfInstalments);
				String loanType = (String) object[10];
				Object[] obj = null;
				if (loanType.equalsIgnoreCase("MT") || loanType.equalsIgnoreCase("LT")) {
					obj = oDao.getOutStandingValByLocIdAndBusinessDate(Long.valueOf(locId.toString()), instalmentDate);
					System.out.println("obj--" + obj);
					if (obj != null) {
						
						
						
						if (obj[2] != null){
							BigDecimal aa = (BigDecimal) obj[2];
							data.setPrincipalBalanceOutstanding(aa.abs());
						}
						if (obj[7] != null){
							BigDecimal bb = (BigDecimal) obj[7];
							data.setPrincipalOverdueAmount(bb.abs());
						}
						if (obj[3] != null){
							BigDecimal cc = (BigDecimal) obj[3];
							data.setInterestBalanceOutstanding(cc.abs());
						}
						if (obj[8] != null){
							BigDecimal aa = (BigDecimal) obj[8];
							data.setInterestOverdueAmount(aa.abs());
						}
						data.setNoOfInstallementsDue(noOfInstalments);
						if(obj[2]!=null || obj[7]!=null || obj[3]!=null || obj[8] != BigDecimal.ZERO)
						overdueDataList.add(data);
					}
				} else {
					obj = oDao.getOutStandingValByLocIdAndBusinessDateForST(Long.valueOf(locId.toString()),
							instalmentDate);
					if (obj != null) {
						if (obj[1] != null){
							BigDecimal aa = (BigDecimal) obj[1];
							data.setPrincipalBalanceOutstanding(aa.abs());
						}
						if (obj[2] != null){
							BigDecimal aa = (BigDecimal) obj[2];
							data.setInterestBalanceOutstanding(aa.abs());
						}

					}
					Long lId = oDao.getOverDueLoc(Long.valueOf(locId.toString()), instalmentDate);
					//System.out.println("lId--" + lId);
					if (lId != null) {
						if (obj[1] != null){
							BigDecimal aa = (BigDecimal) obj[1];
							data.setPrincipalOverdueAmount(aa.abs());
						}
						if (obj[2] != null){
							BigDecimal aa = (BigDecimal) obj[2];
							data.setInterestOverdueAmount(aa.abs());
						}
					} else {
						data.setPrincipalOverdueAmount(BigDecimal.ZERO);
						data.setInterestOverdueAmount(BigDecimal.ZERO);
					}

					if (!obj.equals(null) && lId != null){
						data.setNoOfInstallementsDue(noOfInstalments);
						overdueDataList.add(data);
					}
					
				}
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return overdueDataList;
	}
}
