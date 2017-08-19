package com.vsoftcorp.kls.valuetypes.transaction;

import org.junit.Assert;
import org.junit.Test;

import com.vsoftcorp.kls.valuetypes.transaction.ChannelType;

/**
 * @author sponnam
 * 
 */
public class ChannelTypeTest {

	@Test
	public void testChannelType_given_StringValue_return_type() {

		ChannelType type = ChannelType.getType("A");
		Assert.assertEquals(ChannelType.ATM, type);
	}

	@Test
	public void testChannelType_given_value_return_type() {

		ChannelType type = ChannelType.valueOf("POS");
		Assert.assertEquals(ChannelType.POS, type);
	}

}
