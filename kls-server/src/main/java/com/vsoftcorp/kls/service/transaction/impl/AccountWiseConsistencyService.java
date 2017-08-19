package com.vsoftcorp.kls.service.transaction.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.kls.business.entity.account.Account;
import com.vsoftcorp.kls.business.entity.account.LineOfCredit;
import com.vsoftcorp.kls.business.entity.transaction.AccountWiseConsistency;
import com.vsoftcorp.kls.business.entity.transaction.CurrentTransaction;
import com.vsoftcorp.kls.business.entity.transaction.TransactionHistory;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.dataaccess.transaction.IAccountWiseConsistencyDAO;
import com.vsoftcorp.kls.service.transaction.IAccountWiseConsistencyService;
import com.vsoftcorp.kls.userexceptions.DataAccessException;
import com.vsoftcorp.time.Date;

public class AccountWiseConsistencyService implements IAccountWiseConsistencyService 
{
	private static final Logger logger = Logger.getLogger(AccountWiseConsistencyService.class);

	@Override
	public List<AccountWiseConsistency> checkAccountWiseConsistency(Date todayDate) 
	{
		logger.info(" Start : Calling AccountWiseConsistency dao in checkAccountWiseConsistency() method.");
		IAccountWiseConsistencyDAO dao = KLSDataAccessFactory
				.getAccountWiseConsistencyDAO();
		AccountWiseConsistency master = null;

		List<AccountWiseConsistency> inConsistencyList = new ArrayList<AccountWiseConsistency>();
			try {
				List<Object[]> totalTransactionBalance = dao.getAllTotalTransactionsAmount();
				List<Map> lineOfCreditBalance = dao.getAllLineOfCreditsAmount();
				
				for (Map locBalance : lineOfCreditBalance) 
				{
					AccountingMoney acMoneyOfLoc = (AccountingMoney) locBalance.get("lBalance");
					Long	masterCt;
					masterCt=(Long) locBalance.get("lineOfCreditId");
					List<TransactionHistory> currentList=dao.getTransactionBasedOnLid(masterCt);
				
					List<CurrentTransaction> historylist=dao.getCurrentTransactionBasedOnLid(masterCt);
					int list=0;
					list=currentList.size()+historylist.size();
					//Locbalance >0 
					if(!acMoneyOfLoc.isZero())
					{
							
						if(list!=0)
						{	
							for (Object[] ctBalance : totalTransactionBalance) 
							{
								BigInteger locId=(BigInteger)ctBalance[1];
								if (locBalance.get("lineOfCreditId").toString()
										.equals(locId.toString()))
								{
									BigDecimal ctAmount=(BigDecimal)ctBalance[2];
									Money moneyOfCt = Money.valueOf(
											Currency.getInstance("INR"),
											(String)ctAmount.toString());
									AccountingMoney acMoneyOfCt = AccountingMoney.valueOf(
											moneyOfCt, DebitOrCredit.CREDIT);
									//	logger.info("ct balance >0.."+acMoneyOfCt+"..Lid .."+locId.toString());
									if (acMoneyOfCt.compareTo(acMoneyOfLoc) != 0)
									{
										master = new AccountWiseConsistency();
										Account account = new Account();
										BigInteger accountId=(BigInteger)ctBalance[0];
										account.setId(accountId.longValue());
										master.setAccount(account);
										master.setBusinessDate(todayDate);
										master.setlOCBalance(acMoneyOfLoc);
										master.setTransactionBalance(acMoneyOfCt);
										master.setConsistencyStatus("N");
										LineOfCredit loc = new LineOfCredit();
										loc.setId(Long.parseLong((String)locId.toString()));
										master.setLineOfCredit(loc);
										inConsistencyList.add(master);
									}
								}
							}
						}
						else
						{
							master = new AccountWiseConsistency();
							Account account = new Account();
							account.setId(Long.parseLong((String) locBalance.get("accountId").toString()));
							master.setAccount(account);
							master.setBusinessDate(todayDate);
							master.setlOCBalance(acMoneyOfLoc);
							master.setTransactionBalance(AccountingMoney.ZERO);
							master.setConsistencyStatus("N");
							LineOfCredit loc = new LineOfCredit();
							loc.setId(Long.parseLong((String) locBalance.get(
										"lineOfCreditId").toString()));
							master.setLineOfCredit(loc);
							inConsistencyList.add(master);
						}
				}
				else
				{
					//logger.info("else balance==0.."+acMoneyOfLoc+"..Lid .."+locBalance.get("lineOfCreditId"));
						if(list!=0)
						{
							for (Object[] ctBalance : totalTransactionBalance)
							{
								BigInteger locId=(BigInteger)ctBalance[1];
	
								if (locBalance.get("lineOfCreditId").toString()
										.equals(locId.toString()))
								{
									BigDecimal ctAmount=(BigDecimal)ctBalance[2];
									Money moneyOfCt = Money.valueOf(
											Currency.getInstance("INR"),
											(String)ctAmount.toString());
									AccountingMoney acMoneyOfCt = AccountingMoney.valueOf(
											moneyOfCt, DebitOrCredit.CREDIT);
									if (!acMoneyOfCt.isZero())
									{
										master = new AccountWiseConsistency();
										Account account = new Account();
										BigInteger accountId=(BigInteger)ctBalance[0];
										account.setId(accountId.longValue());
										master.setAccount(account);
										master.setBusinessDate(todayDate);
										master.setlOCBalance(acMoneyOfLoc);
										master.setTransactionBalance(acMoneyOfCt);
										master.setConsistencyStatus("N");
										LineOfCredit loc = new LineOfCredit();
										loc.setId(Long.parseLong((String)locId.toString()));
										master.setLineOfCredit(loc);
										inConsistencyList.add(master);
									}
								}
							}
						}
					}
				}
	
				List<AccountWiseConsistency> dbInConsistencyList = dao.getAllInConsistency();
				
				for (AccountWiseConsistency inConsistency : inConsistencyList) 
				{
					Boolean flag = true;
					for (AccountWiseConsistency dbInConsistency : dbInConsistencyList) 
					{
						if (inConsistency.getLineOfCredit().getId() == dbInConsistency.getLineOfCredit().getId()) 
							flag = false;
					}
					if (flag)
					dao.saveInconsistency(inConsistency);
				}
				
				for (AccountWiseConsistency dbConsistency : dbInConsistencyList) 
				{
					Boolean flag = true;
					for (AccountWiseConsistency inConsistency : inConsistencyList) 
					{
						if (dbConsistency.getLineOfCredit().getId()==inConsistency.getLineOfCredit().getId())
							flag = false;
					}
					if (flag)
					dao.updateInconsistencyAsConsistency(dbConsistency);
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Error while checking account wise consistency.");
			throw new DataAccessException(
					"Error while checking account wise consistency.",
					e.getCause());
		}
		logger.info("Succefully Completed Checking AccountWiseConsistency in checkAccountWiseConsistency() method.");
		return inConsistencyList;
	}

}
	