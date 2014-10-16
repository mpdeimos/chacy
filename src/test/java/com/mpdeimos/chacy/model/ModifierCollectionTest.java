package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.Language;

import java.util.Arrays;
import java.util.EnumSet;

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
				EnumSet.of(EModifier.ABSTRACT, EModifier.FINAL));

		modifiers.addModifiers(Language.VALA, "-abstract");

		Assert.assertEquals(
				Arrays.asList("public", "abstract", "final"),
				modifiers.getModifiers(Language.CSHARP));
		Assert.assertEquals(
				Arrays.asList("public", "final"),
				modifiers.getModifiers(Language.VALA));
	}

	/**
	 * Tests replacing modifiers for a given language.
	 */
	@Test
	public void testReplaceModifiers()
	{
		ModifierCollection modifiers = new ModifierCollection(
				EVisibility.PUBLIC,
				EnumSet.of(EModifier.ABSTRACT, EModifier.FINAL));

		modifiers.replaceModifier(Language.VALA, EModifier.ABSTRACT, "foo");

		Assert.assertEquals(
				Arrays.asList("public", "abstract", "final"),
				modifiers.getModifiers(Language.CSHARP));
		Assert.assertEquals(
				Arrays.asList("public", "final", "foo"),
				modifiers.getModifiers(Language.VALA));
	}

	/**
	 * Tests adding multiple modifiers for a given language.
	 */
	@Test
	public void testAddingMultipleModifiers()
	{
		ModifierCollection modifiers = new ModifierCollection(
				EVisibility.PUBLIC,
				EnumSet.of(EModifier.ABSTRACT, EModifier.FINAL));

		modifiers.addModifiers(Language.CSHARP, "baz", "boo");

		Assert.assertEquals(
				Arrays.asList("public", "abstract", "final", "baz", "boo"),
				modifiers.getModifiers(Language.CSHARP));
		Assert.assertEquals(
				Arrays.asList("public", "abstract", "final"),
				modifiers.getModifiers(Language.VALA));
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
				EnumSet.of(EModifier.ABSTRACT, EModifier.FINAL));
		Assert.assertEquals(
				Arrays.asList("public", "abstract", "final"),
				modifiers.getModifiers(Language.CSHARP));
	}

	/**
	 * Tests modifying the visibility.
	 */
	@Test
	public void testModifyingVisibility()
	{
		ModifierCollection modifiers = new ModifierCollection(
				EVisibility.PUBLIC,
				EnumSet.noneOf(EModifier.class));

		modifiers.replaceVisibility(
				Language.CSHARP,
				EVisibility.PRIVATE,
				"asbtract");
		modifiers.replaceVisibility(Language.VALA, EVisibility.PUBLIC, "foo");
		Assert.assertEquals(
				Arrays.asList("public"),
				modifiers.getModifiers(Language.CSHARP));
		Assert.assertEquals(
				Arrays.asList("foo"), modifiers.getModifiers(Language.VALA));
	}

}
