package com.vsoftcorp.kls.report.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.transaction.AccountWiseConsistency;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.loan.IAccountDAO;
import com.vsoftcorp.kls.report.dao.IInconsistencyReportDAO;
import com.vsoftcorp.kls.report.factory.KLSReportDataAccessFactory;
import com.vsoftcorp.kls.report.service.data.InconsistencyConsistencyData;

public class InconsistencyReportHelper {

	public static List<InconsistencyConsistencyData> getInconsistencyReportDataList(List<AccountWiseConsistency> inconsistencyList) {
		List<InconsistencyConsistencyData> inconsistencyDataList = new ArrayList<InconsistencyConsistencyData>();
		InconsistencyConsistencyData data = null;
		try {
			
			IInconsistencyReportDAO inconsistencyDao =KLSReportDataAccessFactory.getInconsistencyReportDAO();
			for (AccountWiseConsistency ac : inconsistencyList) {

				BigDecimal difference;

				double loc = Math.round(ac.getlOCBalance().isDebit() ? -ac.getlOCBalance().getMoney().getAmount().doubleValue() : ac.getlOCBalance().getMoney().getAmount().doubleValue());
				double trans = Math.round(ac.getTransactionBalance().isDebit() ? -ac.getTransactionBalance().getMoney().getAmount().doubleValue() : ac.getTransactionBalance().getMoney().getAmount()
						.doubleValue());

				BigDecimal locBal = new BigDecimal(loc).setScale(2);
				BigDecimal transBal = new BigDecimal(trans).setScale(2);
				difference = ((locBal.setScale(2)).subtract(transBal.setScale(2))).setScale(2);

				Account account = new Account();
				IAccountDAO accountDao = KLSDataAccessFactory.getAccountDAO();
				account.setId(ac.getAccount().getId());
				account = accountDao.getAccount(account, false);

				data = new InconsistencyConsistencyData();
				if(account!=null)
				{
					data.setAccountNumber(account.getAccountNumber().toString());
					data.setBusinessDate(ac.getBusinessDate().toString());
					data.setLocId(ac.getLineOfCredit().getId().longValue());
					data.setLocBalance(locBal.setScale(2));
					data.setTransactionBalance(transBal.setScale(2));
					data.setDifference(difference.setScale(2));
					
					if(account.getAccountProperty()!=null)
					{
						String customer=inconsistencyDao.getCustomerName(account.getAccountProperty().getCustomerId());
						data.setMemberName(customer);
					}
				}
				
				inconsistencyDataList.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return inconsistencyDataList;
	}
}
