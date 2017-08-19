package com.vsoftcorp.kls.gl_interface.common;

import java.math.BigDecimal;

import junit.framework.TestCase;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.time.Date;
import com.vsoftcorp.time.DayOfMonth;
import com.vsoftcorp.time.MonthOfYear;
import com.vsoftcorp.time.YearOfEra;

public class CoreTransactionFileRecordTest extends TestCase {

	private CoreTransactionFileRecord coreTransactionFileRecord;
	public void setUp() {
		String record = new String("01000100010010001001730109000000       96540USD0000000550000+112720081127200812111Rental expenditure.");
		
		coreTransactionFileRecord = new CoreTransactionFileRecord(record, Date.now());
	}
	
	public void testGetInstitution() {
		assertEquals(new Integer(1), coreTransactionFileRecord.getInstitution());
	}
	
	public void testGetBranch() {
		assertEquals(new Integer(1), coreTransactionFileRecord.getBranch());
	}
	
	public void testGetProductCode() {
		assertEquals(new Integer(1), coreTransactionFileRecord.getProductCode());
	}
	
	public void testGetAccountNumber() {
		assertEquals("730109000000", coreTransactionFileRecord.getAccountNumber());
	}
	
	public void testGetTranCode() {
		assertEquals("       9654", coreTransactionFileRecord.getTranCode());
	}
	
	public void testGetDebitCredit() {
		assertEquals(DebitOrCredit.DEBIT, coreTransactionFileRecord.getDebitCredit());
	}
	
	public void testCurrency() {
		assertEquals("USD", coreTransactionFileRecord.getCurrency());
	}
	
	public void testGetAmount() {
		BigDecimal amount = new BigDecimal(5500.00);
		assertEquals(AccountingMoney.valueOf(Currency.USDollar, amount, DebitOrCredit.DEBIT), coreTransactionFileRecord.getAmount());
	}
	
	public void testEffectiveDate() {
		Date effectiveDate = new Date(YearOfEra.valueOf(2008), MonthOfYear.valueOf(11), DayOfMonth.valueOf(27));
		
		assertEquals(effectiveDate, coreTransactionFileRecord.getEffectiveDate());
	}

	public void testTransactionDate() {
		Date transactionDate = new Date(YearOfEra.valueOf(2008), MonthOfYear.valueOf(11), DayOfMonth.valueOf(27));
		
		assertEquals(transactionDate, coreTransactionFileRecord.getTransactionDate());
	}
	
	public void testGetStatus() {
		assertEquals("111", coreTransactionFileRecord.getStatus());
	}
	
	public void testDescription() {
		assertEquals("Rental expenditure.", coreTransactionFileRecord.getDescription());
	}
}
