package com.vsoftcorp.kls.valuetypes.transaction;

import org.junit.Assert;
import org.junit.Test;

import com.vsoftcorp.kls.valuetypes.transaction.TransactionType;

/**
 * @author sponnam
 * 
 */
public class TransactionTypeTest {

	@Test
	public void testTransactionType_given_StringValue_return_type() {

		TransactionType type = TransactionType.getType("W");
		Assert.assertEquals(TransactionType.Withdrawal, type);
	}

	@Test
	public void testTransactionType_given_value_return_type() {

		TransactionType type = TransactionType.valueOf("Deposit");
		Assert.assertEquals(TransactionType.Deposit, type);
	}

}
