package com.vsoftcorp.kls.convertor;

import junit.framework.TestCase;

import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;

/**
 * @author sponnam
 *
 */
public class MoneyStringTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testMoneyToStringConversion() {
		Money money = Money.valueOf(Currency.getInstance("INR"), "1234.678");
		String alphabeticCode = money.getCurrency().getAlphabeticCode();
		assertEquals("INR", alphabeticCode);
		String amount = money.getAmount().toString();
		assertEquals("1234.68", amount);
	}

}
