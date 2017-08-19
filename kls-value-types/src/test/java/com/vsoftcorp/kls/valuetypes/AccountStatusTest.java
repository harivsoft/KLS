package com.vsoftcorp.kls.valuetypes;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sponnam
 * 
 */
public class AccountStatusTest {

	@Test
	public void testAccountStatus_given_type_return_AccountStatus() {

		AccountStatus status = AccountStatus.getType("A");
		Assert.assertEquals(AccountStatus.Active, status);
	}

	@Test
	public void testAccountStatus_given_stringValue_return_AccountStatus() {

		AccountStatus status = AccountStatus.valueOf("DELETED");
		Assert.assertEquals(AccountStatus.DELETED, status);
	}

}
