package com.mpdeimos.chacy;

import org.junit.Assert;
import org.junit.Test;

public class ChacyTest
{
	@Test
	public void testHeartBeat()
	{
		Assert.assertEquals("Chacy", Chacy.heartBeat());
	}
}
