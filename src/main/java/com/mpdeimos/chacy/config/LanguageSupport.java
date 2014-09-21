package com.mpdeimos.chacy.config;

import com.mpdeimos.chacy.Language;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/** Defines included and excluded languages for an object. */
public class LanguageSupport
{
	/** Set of supported languages. */
	private final Set<Language> supported;

	/** Constructor. */
	public LanguageSupport()
	{
		this.supported = new HashSet<Language>(
				EnumSet.allOf(Language.class));
	}

	/** Constructor. */
	public LanguageSupport(Collection<Language> supportedLanguages)
	{
		this.supported = new HashSet<Language>(supportedLanguages);
	}

	/** @return <code>true</code> if the given language is supported. */
	public boolean isSupported(Language language)
	{
		return this.supported.contains(language);
	}

	/** @return An unmodifiable set of supported languages. */
	public Set<Language> getLanguages()
	{
		return Collections.unmodifiableSet(this.supported);
	}

	/**
	 * Sets the supported languages and replaces existing ones if at least one
	 * language is passed.
	 */
	public void setLanguages(Language... languages)
	{
		if (languages.length == 0)
		{
			return;
		}

		this.supported.clear();
		Collections.addAll(this.supported, languages);
	}

	/** Removes all the given languages. */
	public void removeLanguages(Language... languages)
	{
		this.supported.removeAll(Arrays.asList(languages));
	}
}
