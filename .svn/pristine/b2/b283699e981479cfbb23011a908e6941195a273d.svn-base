package com.vsoftcorp.kls.business.util;

import java.math.BigDecimal;

import com.vsoftcorp.accounting.types.AccountingMoney;
import com.vsoftcorp.accounting.types.DebitOrCredit;
import com.vsoftcorp.finance.Currency;
import com.vsoftcorp.finance.Money;

/**
 * @author sponnam
 * 
 */
public class MoneyUtil {

	public static AccountingMoney getZeroAccountingMoney() {
		return AccountingMoney.valueOf(Currency.fromISOAlphabeticCode("INR"), new BigDecimal(0), DebitOrCredit.CREDIT);
	}

	public static AccountingMoney getAccountingMoney(double amount) {
		AccountingMoney accountingMoney = getZeroAccountingMoney();
		if (amount < 0) {
			accountingMoney = AccountingMoney.valueOf(Currency.fromISOAlphabeticCode("INR"), new BigDecimal(amount),
					DebitOrCredit.DEBIT);
		} else {
			accountingMoney = AccountingMoney.valueOf(Currency.fromISOAlphabeticCode("INR"), new BigDecimal(amount),
					DebitOrCredit.CREDIT);
		}

		return accountingMoney;

	}

	public static AccountingMoney getAccountingMoney(Money theMoney) {

		AccountingMoney accountingMoney = getZeroAccountingMoney();

		if (theMoney.isNegative()) {
			accountingMoney = AccountingMoney.valueOf(theMoney, DebitOrCredit.DEBIT);

		} else {
			accountingMoney = AccountingMoney.valueOf(theMoney, DebitOrCredit.CREDIT);
		}

		return accountingMoney;

	}

	public static Money getZeroMoney() {
		return Money.valueOf(Currency.fromISOAlphabeticCode("INR"), BigDecimal.valueOf(0));
	}

	public static Money getMoney(Double theValue) {
		Money theMoney = getZeroMoney();
		if (theValue != null) {
			theMoney = Money.valueOf(Currency.fromISOAlphabeticCode("INR"), BigDecimal.valueOf(theValue));
		}
		return theMoney;
	}

	public static AccountingMoney getAccountingMoney(BigDecimal bigDecimal) {
		AccountingMoney accountingMoney = getZeroAccountingMoney();
		BigDecimal absoluteValue = bigDecimal.abs();
		if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
			accountingMoney = AccountingMoney.valueOf(Currency.fromISOAlphabeticCode("INR"), absoluteValue,
					DebitOrCredit.DEBIT);
		} else {
			accountingMoney = AccountingMoney.valueOf(Currency.fromISOAlphabeticCode("INR"), absoluteValue,
					DebitOrCredit.CREDIT);
		}
		return accountingMoney;
	}

	public static AccountingMoney getAccountingMoney(BigDecimal bigDecimal, DebitOrCredit debitOrCreditType) {

		AccountingMoney accountingMoney = getZeroAccountingMoney();
		accountingMoney = AccountingMoney.valueOf(Currency.fromISOAlphabeticCode("INR"), bigDecimal, debitOrCreditType);
		return accountingMoney;
	}

	public static BigDecimal getAmountRoundedValue(BigDecimal input) {
		return input.setScale(2);
	}
}
