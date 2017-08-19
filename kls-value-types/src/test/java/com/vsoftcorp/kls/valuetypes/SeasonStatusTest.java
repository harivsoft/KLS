package com.vsoftcorp.kls.valuetypes;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sponnam
 * 
 */
public class SeasonStatusTest {

	@Test
	public void testSeasonStatus_given_type_return_SeasonStatus() {

		SeasonStatus status = SeasonStatus.getType("D");
		Assert.assertEquals(SeasonStatus.DRAWAL_STARTED, status);
	}

	@Test
	public void testSeasonStatus_given_stringValue_return_SeasonStatus() {

		SeasonStatus status = SeasonStatus.valueOf("NEW");
		Assert.assertEquals(SeasonStatus.NEW, status);
	}

}
