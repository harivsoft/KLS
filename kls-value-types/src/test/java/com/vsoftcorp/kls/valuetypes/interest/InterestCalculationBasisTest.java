package com.vsoftcorp.kls.valuetypes.interest;

import org.junit.Assert;
import org.junit.Test;

public class InterestCalculationBasisTest {

	@Test
	public void testInterestCalculationBasis_given_type_return_Basis() {

		InterestCalculationBasis basis = InterestCalculationBasis
				.getType("D");
		Assert.assertEquals(InterestCalculationBasis.Daily, basis);
	}
}
