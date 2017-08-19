package com.vsoftcorp.kls.valuetypes;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sponnam
 * 
 */
public class PeriodTypeTest {

	@Test
	public void testPeriodType_given_StringValue_return_type() {

		PeriodType type = PeriodType.getType("D");
		Assert.assertEquals(PeriodType.Days, type);
	}

	@Test
	public void testPeriodType_given_value_return_type() {

		PeriodType type = PeriodType.valueOf("Years");
		Assert.assertEquals(PeriodType.Years, type);
	}

}
