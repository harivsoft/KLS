package com.vsoftcorp.kls.valuetypes;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sponnam
 * 
 */
public class LoanApplicationActionTest {

	@Test
	public void testLoanApplicationAction_given_type_return_LoanApplicationAction() {

		LoanApplicationAction action = LoanApplicationAction
				.getType("RECOMMEND");
		Assert.assertEquals(LoanApplicationAction.RECOMMEND, action);
	}

	@Test
	public void testLoanApplicationAction_given_stringValue_return_LoanApplicationAction() {

		LoanApplicationAction status = LoanApplicationAction.valueOf("INSPECT");
		Assert.assertEquals(LoanApplicationAction.INSPECT, status);
	}

}
