package com.vsoftcorp.kls.business.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;

/**
 * @author sponnam
 * 
 */
public class MoneyUtilTest extends TestCase {

	private static final Logger logger = Logger.getLogger(MoneyUtilTest.class);

	private static Money PLUS_100 = Money.valueOf(Currency.getInstance("INR"),
			"100");

	private static AccountingMoney DEBIT_100 = AccountingMoney.valueOf(
			PLUS_100, DebitOrCredit.DEBIT);

	private static AccountingMoney CREDIT_100 = AccountingMoney.valueOf(
			PLUS_100, DebitOrCredit.CREDIT);

	@Test
	public void testGetScaledValue_02() {
		logger.info("debit amount"+DEBIT_100);
		Money money = Money.valueOf(Currency.fromISOAlphabeticCode("INR"),
				"1234.5678");
		assertEquals(
				Money.valueOf(Currency.fromISOAlphabeticCode("INR"), "1234.57"),
				money.getScaledValue(2, RoundingMode.UP));

		assertEquals("1234.57", money.getAmount().setScale(2).toString());
	}

	@Test
	public void testValueOfMoneyRoundedToDefault() {
		Money money = Money
				.valueOf(Currency.getInstance("INR"), "1.2345", true);
		assertEquals("1.23", money.getAmount().toString());
	}

	@Test
	public void testValueOfMoneyNotRounded() {
		Money money = Money.valueOf(Currency.getInstance("INR"), "1.2345",
				false);
		assertEquals("1.2345", money.getAmount().toString());
	}

	@Test
	public void testValueOfMoneyRoundedUsingScaling() {
		Money money = Money.valueOf(Currency.getInstance("INR"), "1.2345",
				false);
		assertEquals("1.235", money.getAmount().setScale(3, RoundingMode.UP)
				.toString());
	}

	@Test
	public void testSubtractMoney() {
		Money money1 = Money.valueOf(Currency.getInstance("INR"), "0.00");
		Money money2 = Money.valueOf(Currency.getInstance("INR"), "50.00");
		assertEquals(Money.valueOf(Currency.getInstance("INR"), "-50.00"),
				money1.subtract(money2));
	}

	@Test
	public void testGetAccountingMoneyGivenBigDecimalValue() {
		AccountingMoney b1 = MoneyUtil.getAccountingMoney(BigDecimal
				.valueOf(100.00));

		assertEquals(AccountingMoney.valueOf(
				Money.valueOf(Currency.getInstance("INR"), "100"),
				DebitOrCredit.CREDIT), b1);
	}

	@Test
	public void testGetMoneyGivenDoubleValue() {
		Money d1 = MoneyUtil.getMoney(Double.valueOf("100.00"));

		assertEquals(Money.valueOf(Currency.getInstance("INR"), "100.00"), d1);
	}

	@Test
	public void testAccountingMoneyGetValue() {

		assertEquals("1", CREDIT_100.getDebitOrCredit().getValue().toString());
	}

}
