package com.mpdeimos.chacy.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link ListMap}.
 */
public class ListMapTest
{

	/** Tests addition of values. */
	@Test
	public void testAdd()
	{
		ListMap<String, String> map = new ListMap<>();
		map.add("foo", "bar"); //$NON-NLS-1$ //$NON-NLS-2$
		map.add("foo", "baz"); //$NON-NLS-1$ //$NON-NLS-2$
		map.add("bar", "gaz"); //$NON-NLS-1$ //$NON-NLS-2$

		Assert.assertEquals(2, map.size());
		Assert.assertThat(
				map.get("foo"), //$NON-NLS-1$
				CoreMatchers.is(Arrays.asList("bar", "baz"))); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertThat(
				map.get("bar"), //$NON-NLS-1$
				CoreMatchers.is(Arrays.asList("gaz"))); //$NON-NLS-1$
	}

	/** Tests handling of empty list. */
	@Test
	public void testEmptyList()
	{
		ListMap<String, String> map = new ListMap<>();
		map.put("foo", new ArrayList<String>()); //$NON-NLS-1$

		Assert.assertEquals(1, map.size());
		Assert.assertThat(
				map.get("foo"), //$NON-NLS-1$
				CoreMatchers.is(Arrays.asList()));

		map.add("foo", "bar"); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertThat(
				map.get("foo"), //$NON-NLS-1$
				CoreMatchers.is(Arrays.asList("bar"))); //$NON-NLS-1$
	}

}
