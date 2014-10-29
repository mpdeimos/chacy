package com.mpdeimos.chacy.config;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.PredicateMatcher;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/** Tests {@link LanguageValue}. */
public class LanguageValueTest
{
	/** All languages. */
	private static final EnumSet<Language> ALL = EnumSet.allOf(Language.class);

	/** Just the C# language. */
	private static final EnumSet<Language> CSHARP = EnumSet.of(Language.CSHARP);

	/** All languages but C#. */
	private static final EnumSet<Language> NOT_CSHARP = EnumSet.complementOf(CSHARP);

	/** Runtime annotation that holds an array of language values. */
	@Retention(RetentionPolicy.RUNTIME)
	private @interface ValueHolder
	{
		/** The value array. */
		Chacy.Value[] value() default {};
	}

	/** Test class with annotation containing no values. */
	@ValueHolder
	public static class TestClassNoneSet
	{
		// empty
	}

	/** Tests that no values are stored if an empty values array is supplied. */
	@Test
	public void testNoneSet()
	{
		LanguageValue values = getValues(TestClassNoneSet.class);

		Assert.assertThat(
				ALL,
				CoreMatchers.everyItem(new PredicateMatcher<Language>("absent",
						language -> values.get(language) == null)));
	}

	/** Test class with annotation containing one value for all languages. */
	@ValueHolder(@Chacy.Value("foo"))
	public static class TestClassAllSet
	{
		// empty
	}

	/** Tests that all values are stored if no language array is supplied. */
	@Test
	public void testAllSet()
	{
		LanguageValue values = getValues(TestClassAllSet.class);

		assertAllValues(values, ALL, "foo");
	}

	/** Tests specifying a language value will override all. */
	@Test
	public void testOverrideAll()
	{
		LanguageValue values = new LanguageValue();
		values.set(new Language[] {}, "foo");
		values.set(new Language[] { Language.CSHARP }, "bar");

		assertAllValues(values, CSHARP, "bar");
		assertAllValues(values, NOT_CSHARP, "foo");

		// setting in reverse order will yield the same result
		values = new LanguageValue();
		values.set(new Language[] { Language.CSHARP }, "bar");
		values.set(new Language[] {}, "foo");

		assertAllValues(values, CSHARP, "bar");
		assertAllValues(values, NOT_CSHARP, "foo");
	}

	/** Tests specifying a default value. */
	@Test
	public void testDefaultValue()
	{
		LanguageValue values = new LanguageValue(Arrays.asList("foo"));
		values.set(new Language[] { Language.CSHARP }, "bar");

		assertAllValues(values, CSHARP, "bar");
		assertAllValues(values, NOT_CSHARP, "foo");
	}

	/**
	 * @return the values stored at a {@link ValueHolder} annotation for the
	 *         given class.
	 */
	private static LanguageValue getValues(Class<?> clazz)
	{
		LanguageValue values = new LanguageValue();
		ValueHolder annotation = clazz.getAnnotation(ValueHolder.class);
		values.set(annotation.value());
		return values;
	}

	/** Asserts that all values for the given language equal a given value. */
	private static void assertAllValues(
			LanguageValue values,
			EnumSet<Language> allOf,
			String value)
	{
		Assert.assertThat(
				allOf.stream().map(
						language -> values.get(language)).flatMap(
						list -> list.stream()).collect(
						Collectors.toList()),
				CoreMatchers.everyItem(CoreMatchers.is(value)));
	}

}
