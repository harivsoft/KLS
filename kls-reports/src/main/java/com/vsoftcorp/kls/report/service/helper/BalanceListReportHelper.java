package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.report.dao.IBalanceListReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.BalanceListReportData;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class BalanceListReportHelper {

	public static List<BalanceListReportData> getBalanceListReportData(List<LineOfCredit> locList, Date asOnDate) {

		List<BalanceListReportData> dataList = new ArrayList<BalanceListReportData>();
		IBalanceListReportDAO dao = KLSReportDataAccessFactory.getBalanceListReportDAO();
		BalanceListReportData data = null;
		BigDecimal zero = BigDecimal.ZERO.setScale(2);
		for (LineOfCredit lineOfCredit : locList) {
			PersonData personData = RestClientUtil.getCustomerById(lineOfCredit.getCustomerId());
			String shareBalance = RestClientUtil.getShareBalanceByCustomerId(lineOfCredit.getCustomerId(), asOnDate.toString());
			Product product = lineOfCredit.getProduct();
			List<Object[]> objectList = dao.getBalanceListReportData(lineOfCredit.getId(), asOnDate);
			for (Object[] array : objectList) {
				data = new BalanceListReportData();
				data.setAccNo(lineOfCredit.getAccount().getAccountNumber());
				if (lineOfCredit.getLoanType().equals("C"))
					data.setCropOrPurposeName(lineOfCredit.getCrop().getName());
				else if (lineOfCredit instanceof LoanLineOfCredit) {
					LoanLineOfCredit loanLoc = (LoanLineOfCredit) lineOfCredit;
					data.setCropOrPurposeName(loanLoc.getPacsLoanApplication().getPurpose().getName());
				}
				data.setMemberName(personData.getName());
				data.setLocNo(lineOfCredit.getId());
				data.setMemberId(lineOfCredit.getCustomerId());
				data.setShareBal(shareBalance);
				data.setProductId(product.getId());
				data.setProductName(product.getName());
				BigDecimal principleBalance = (BigDecimal) array[0];
				BigDecimal intBalance = (BigDecimal) array[1];
				BigDecimal chargesBalance = (BigDecimal) array[2];
				data.setChargesBal(chargesBalance == null ? zero : chargesBalance.setScale(2));
				data.setPrincipleBal(principleBalance == null ? zero : principleBalance.setScale(2));
				data.setIntBal(intBalance == null ? zero : intBalance.setScale(2));
				dataList.add(data);
			}
		}
		return dataList;
	}
}
