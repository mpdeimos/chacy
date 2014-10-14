package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.Language;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/** Test for {@link ModifierCollection}. */
public class ModifierCollectionTest
{
	/**
	 * Tests removing modifiers for a given language.
	 */
	@Test
	public void testRemovingModifiers()
	{
		ModifierCollection modifiers = new ModifierCollection(
				EVisibility.PUBLIC,
				Arrays.asList("foo", "bar")); //$NON-NLS-1$//$NON-NLS-2$

		modifiers.addModifiers(Language.VALA, "-bar"); //$NON-NLS-1$

		Assert.assertEquals(
				Arrays.asList("public", "foo", "bar"), modifiers.getModifiers(Language.CSHARP)); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		Assert.assertEquals(
				Arrays.asList("public", "foo"), modifiers.getModifiers(Language.VALA)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Tests adding multiple modifiers for a given language.
	 */
	@Test
	public void testAddingMultipleModifiers()
	{
		ModifierCollection modifiers = new ModifierCollection(
				EVisibility.PUBLIC,
				Arrays.asList("foo", "bar")); //$NON-NLS-1$//$NON-NLS-2$

		modifiers.addModifiers(Language.CSHARP, "baz", "boo"); //$NON-NLS-1$ //$NON-NLS-2$

		Assert.assertEquals(
				Arrays.asList("public", "foo", "bar", "baz", "boo"), modifiers.getModifiers(Language.CSHARP)); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		Assert.assertEquals(
				Arrays.asList("public", "foo", "bar"), modifiers.getModifiers(Language.VALA)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Tests that getting modifiers for language no custom modifiers has been
	 * defined, yield the original modifiers.
	 */
	@Test
	public void testGettingForNonConfiguredLanguage()
	{
		ModifierCollection modifiers = new ModifierCollection(
				EVisibility.PUBLIC,
				Arrays.asList("foo", "bar")); //$NON-NLS-1$//$NON-NLS-2$
		Assert.assertEquals(
				Arrays.asList("public", "foo", "bar"), modifiers.getModifiers(Language.CSHARP)); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Tests modifying the visibility.
	 */
	@Test
	public void testModifyingVisibility()
	{
		ModifierCollection modifiers = new ModifierCollection(
				EVisibility.PUBLIC,
				Collections.emptyList());

		modifiers.setVisibility(Language.VALA, "foo"); //$NON-NLS-1$
		Assert.assertEquals(
				Arrays.asList("public"), modifiers.getModifiers(Language.CSHARP)); //$NON-NLS-1$
		Assert.assertEquals(
				Arrays.asList("foo"), modifiers.getModifiers(Language.VALA)); //$NON-NLS-1$
	}

}
