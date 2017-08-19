package com.vosftcorp.kls.report.dao.dcb;

import java.math.BigDecimal;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.finance.Money;
import com.vsoftcorp.time.Date;

/**
 * 
 * @author a1605
 *
 */
public interface ISTMTLTDcbReportDAO {

	public Money getMTLTAgriPricipleReceivable(Integer pacsId, Date asOnDate,Integer branchId);

	public BigDecimal getMTLTAgriInterestReceivable(Integer pacsId, Date asOnDate,Integer branchId);

	public BigDecimal getMTLTAgriInterestPaid(Integer pacsId, Date asOnDate,Integer branchId);

	public BigDecimal getMTLTAgriPriciplePaid(Integer pacsId, Date asOnDate,Integer branchId);

	public AccountingMoney getSTAgriPricipleReceivable(Integer pacsId, Date asOnDate,Integer branchId);

	public BigDecimal getSTAgriInterestReceivable(Integer pacsId, Date asOnDate,Integer branchId);
	
	public AccountingMoney getSTAgriPrincipleCollectionTotal(Integer pacsId,Date asOnDate,Integer branchId);
	
	public AccountingMoney getSTAgriIntCollectionTotal(Integer pacsId,Date asOnDate,Integer branchId);

	public AccountingMoney getSTAgriPricipleReceivableTillDate(Integer pacsId, Date asOnDate,Integer branchId);

public 	Money getMTLTAgriPricipleReceivableTillDate(Integer pacsId, Date asOnDate,Integer branchId);
	
}
