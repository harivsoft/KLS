package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entities.Pacs;
import com.vsoftcorp.kls.business.entities.Product;
import com.vsoftcorp.kls.business.entities.Purpose;
import com.vsoftcorp.kls.business.entity.account.LoanLineOfCredit;
import com.vsoftcorp.kls.data.gateway.datahelpers.PersonData;
import com.vsoftcorp.kls.report.dao.IProductWiseCollectionReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.ProductWiseCollectionReportData;
import com.vsoftcorp.kls.service.util.RestClientUtil;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 * 
 */
public class ProductWiseCollectionReportHelper {

	private static BigDecimal zero = BigDecimal.ZERO.setScale(2);

	public static List<ProductWiseCollectionReportData> getProductWiseCollectionReportData(List<LoanLineOfCredit> locList, Date fromDate, Date toDate) {

		List<ProductWiseCollectionReportData> result = new ArrayList<ProductWiseCollectionReportData>();
		ProductWiseCollectionReportData data = null;

		IProductWiseCollectionReportDAO dao = KLSReportDataAccessFactory.getProductWiseCollectionReportDAO();

		for (LoanLineOfCredit loanLineOfCredit : locList) {
			data = new ProductWiseCollectionReportData();
			data.setLocId(loanLineOfCredit.getId());
			PersonData person = RestClientUtil.getCustomerById(loanLineOfCredit.getCustomerId());
			data.setMemberName(person.getName());

			Pacs pacs = loanLineOfCredit.getAccount().getAccountProperty().getPacs();
			data.setPacsId(pacs.getId());
			data.setPacsName(pacs.getName());

			Product product = loanLineOfCredit.getProduct();
			data.setProductId(product.getId());
			data.setProductName(product.getName());

			Purpose purpose = loanLineOfCredit.getPacsLoanApplication().getPurpose();
			data.setPurposeId(purpose.getId());
			data.setPurposeName(purpose.getName());

			data.setBranchId(pacs.getBranch().getId());
			data.setBranchName(pacs.getBranch().getName());

			data.setLoanOutstanding(loanLineOfCredit.getCurrentBalance().getMoney().getAmount().setScale(2));

			Money principle = Money.ZERO;
			Money interest = Money.ZERO;
			Money penalInterest = Money.ZERO;
			Money disburmentAmount = Money.ZERO;
			Object[] objects = dao.getProductWiseCollectionReportDataByLocId(loanLineOfCredit.getId(), fromDate, toDate);
			if (objects != null) {
				/*
				 * TransactionMode paymentMode = (TransactionMode) objects[0];
				 * if (paymentMode != null)
				 * data.setPaymentMode(paymentMode.getValue());
				 */
				principle = (Money) objects[0];
				interest = (Money) objects[1];
				penalInterest = (Money) objects[2];
				disburmentAmount = (Money) objects[3];
			}
			if (principle != null || interest != null || penalInterest != null || disburmentAmount != null) {
				data.setPrinciple(principle != null ? principle.getAmount().setScale(2) : zero);
				data.setInterest(interest != null ? interest.getAmount().setScale(2) : zero);
				data.setPenalInterest(penalInterest != null ? penalInterest.getAmount().setScale(2) : zero);
				if (disburmentAmount == null)
					disburmentAmount = Money.ZERO;
				data.setTotalDisbursed(disburmentAmount.getAmount().setScale(2));
				result.add(data);
			}
		}
		return result;
	}
}
