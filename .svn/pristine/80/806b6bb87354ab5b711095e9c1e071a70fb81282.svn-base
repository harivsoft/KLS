package com.vsoftcorp.kls.valuetypes;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sponnam
 * 
 */
public class BankProcessStatusTest {

	@Test
	public void testBankProcessStatus_given_type_return_BankProcessStatus() {

		BankProcessStatus status = BankProcessStatus.getType(1);
		Assert.assertEquals(BankProcessStatus.DAY_BEGIN, status);
	}

	@Test
	public void testBankProcessStatus_given_stringValue_return_BankProcessStatus() {

		BankProcessStatus status = BankProcessStatus
				.valueOf("DAY_BEGIN_IN_PROGRESS");
		Assert.assertEquals(BankProcessStatus.DAY_BEGIN_IN_PROGRESS, status);
	}

}
