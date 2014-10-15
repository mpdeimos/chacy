package com.mpdeimos.chacy.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link CollectionMap}.
 */
public class ListMapTest
{

	/** Tests addition of values. */
	@Test
	public void testAdd()
	{
		CollectionMap<String, String> map = new CollectionMap<>();
		map.addToList("foo", "bar");
		map.addToList("foo", "baz");
		map.addToList("bar", "gaz");

		Assert.assertEquals(2, map.size());
		Assert.assertThat(
				map.get("foo"),
				CoreMatchers.is(Arrays.asList("bar", "baz")));
		Assert.assertThat(
				map.get("bar"),
				CoreMatchers.is(Arrays.asList("gaz")));
	}

	/** Tests removal of values. */
	@Test
	public void testRemove()
	{
		CollectionMap<String, String> map = new CollectionMap<>();
		map.addToList("foo", "bar");
		map.addToList("foo", "baz");
		map.addToList("bar", "gaz");
		map.removeFromList("foo", "baz");

		Assert.assertEquals(2, map.size());
		Assert.assertThat(
				map.get("foo"),
				CoreMatchers.is(Arrays.asList("bar")));
		Assert.assertThat(
				map.get("bar"),
				CoreMatchers.is(Arrays.asList("gaz")));
	}

	/** Tests handling of empty list. */
	@Test
	public void testEmptyList()
	{
		CollectionMap<String, String> map = new CollectionMap<>();
		map.put("foo", new ArrayList<String>());

		Assert.assertEquals(1, map.size());
		Assert.assertThat(
				map.get("foo"),
				CoreMatchers.is(Arrays.asList()));

		map.addToList("foo", "bar");
		Assert.assertThat(
				map.get("foo"),
				CoreMatchers.is(Arrays.asList("bar")));
	}

	/** Tests copy constructor. */
	@Test
	public void testCopyConstructor()
	{
		CollectionMap<String, String> map = new CollectionMap<>();
		map.put("foo", Arrays.asList("foo", "bar"));

		CollectionMap<String, String> map2 = new CollectionMap<>(map);

		Assert.assertEquals(1, map2.size());
		Assert.assertThat(
				map2.get("foo"),
				CoreMatchers.is(Arrays.asList("foo", "bar")));

		// assert that the lists got copied as well and do not write through
		map.removeFromList("foo", "foo");
		Assert.assertThat(
				map.get("foo"),
				CoreMatchers.is(Arrays.asList("bar")));
		Assert.assertThat(
				map2.get("foo"),
				CoreMatchers.is(Arrays.asList("foo", "bar")));
	}

	/** Tests contains. */
	@Test
	public void testContains()
	{
		CollectionMap<String, String> map = new CollectionMap<>();
		map.put("foo", Arrays.asList("foo", "bar"));

		Assert.assertTrue(map.containsValue(Arrays.asList("foo", "bar")));
		Assert.assertFalse(map.containsValue(Arrays.asList("bar", "foo")));
		Assert.assertFalse(map.containsValue(Arrays.asList("foo", "bar", "baz")));
	}
}
