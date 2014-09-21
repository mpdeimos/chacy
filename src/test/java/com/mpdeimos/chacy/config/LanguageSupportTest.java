package com.mpdeimos.chacy.config;

import com.mpdeimos.chacy.Language;

import java.util.EnumSet;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/** Tests {@link LanguageSupport}. */
public class LanguageSupportTest
{
	/**
	 * Tests that the initial language support contains all languages.
	 */
	@Test
	public void testInitialAllLanguages()
	{
		LanguageSupport languageSupport = new LanguageSupport();
		assertContainsOnly(languageSupport, EnumSet.allOf(Language.class));
	}

	/**
	 * Tests {@link LanguageSupport#setLanguages(Language...)}
	 */
	@Test
	public void testSetLanguages()
	{
		LanguageSupport languageSupport = new LanguageSupport();

		// no param does nothing
		languageSupport.setLanguages();
		assertContainsOnly(languageSupport, EnumSet.allOf(Language.class));

		languageSupport.setLanguages(Language.CSHARP);
		assertContainsOnly(languageSupport, EnumSet.of(Language.CSHARP));

		// no param does nothing
		languageSupport.setLanguages();
		assertContainsOnly(languageSupport, EnumSet.of(Language.CSHARP));

		languageSupport.setLanguages(Language.VALA);
		assertContainsOnly(languageSupport, EnumSet.of(Language.VALA));

		languageSupport.setLanguages(Language.CSHARP, Language.VALA);
		assertContainsOnly(languageSupport, EnumSet.allOf(Language.class));
	}

	/** Tests {@link LanguageSupport#removeLanguages(Language...)} */
	public void testRemoveLanguages()
	{
		LanguageSupport languageSupport = new LanguageSupport();

		// no param does nothing
		languageSupport.removeLanguages();
		assertContainsOnly(languageSupport, EnumSet.allOf(Language.class));

		languageSupport.removeLanguages(Language.CSHARP);
		assertContainsOnly(languageSupport, EnumSet.of(Language.VALA));

		languageSupport.removeLanguages(Language.VALA);
		assertContainsOnly(languageSupport, EnumSet.noneOf(Language.class));

		languageSupport = new LanguageSupport();
		languageSupport.removeLanguages(Language.CSHARP, Language.VALA);
		assertContainsOnly(languageSupport, EnumSet.noneOf(Language.class));
	}

	/**
	 * Asserts that the language support object contains only the given
	 * languages.
	 */
	private void assertContainsOnly(
			LanguageSupport languageSupport,
			EnumSet<Language> language)
	{
		Language[] included = language.toArray(new Language[] {});
		Language[] excluded = EnumSet.complementOf(language).toArray(
				new Language[] {});

		Assert.assertThat(languageSupport.getLanguages(),
				CoreMatchers.hasItems(included));

		if (excluded.length > 0)
		{
			Assert.assertThat(languageSupport.getLanguages(),
					CoreMatchers.not(CoreMatchers.hasItems(excluded)));
		}
	}
}
