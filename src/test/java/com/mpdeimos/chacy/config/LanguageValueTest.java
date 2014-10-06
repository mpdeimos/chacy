package com.mpdeimos.chacy.config;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.PredicateMatcher;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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
				CoreMatchers.everyItem(new PredicateMatcher<Language>("absent", //$NON-NLS-1$
						language -> !values.get(language).isPresent())));
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

		assertAllValues(values, ALL, "foo"); //$NON-NLS-1$
	}

	/** Tests specifying a language value will overriding all. */
	@Test
	public void testOverrideAll()
	{
		LanguageValue values = new LanguageValue();
		values.set("foo"); //$NON-NLS-1$
		values.set("bar", Language.CSHARP); //$NON-NLS-1$

		assertAllValues(values, CSHARP, "bar"); //$NON-NLS-1$
		assertAllValues(values, NOT_CSHARP, "foo"); //$NON-NLS-1$

		// setting in reverse order will yield the same result
		values = new LanguageValue();
		values.set("bar", Language.CSHARP); //$NON-NLS-1$
		values.set("foo"); //$NON-NLS-1$

		assertAllValues(values, CSHARP, "bar"); //$NON-NLS-1$
		assertAllValues(values, NOT_CSHARP, "foo"); //$NON-NLS-1$
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
						language -> values.get(language).get()).collect(
						Collectors.toList()),
				CoreMatchers.everyItem(CoreMatchers.is(value)));
	}

}
