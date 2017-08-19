package com.vsoftcorp.kls.valuetypes.transaction;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sponnam
 * 
 */
public class RecoveryOrderTest {

	@Test
	public void testRecoveryOrder_given_type_return_RecoveryOrder() {

		RecoveryOrder order = RecoveryOrder.CHARGES;
		Assert.assertEquals(RecoveryOrder.CHARGES, order);
	}

}
