package com.mpdeimos.chacy.config;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Chacy.Value;
import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.util.CollectionMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/** Holds a string value for a given language, e.g. to rename an element. */
public class LanguageValue
{
	/** Map of languages to values. */
	private final CollectionMap<Language, String> values;

	/** Constructor. */
	public LanguageValue()
	{
		this.values = new CollectionMap<>();
	}

	/** Constructor. */
	public LanguageValue(LanguageValue renameRules)
	{
		this.values = new CollectionMap<>(renameRules.values);
	}

	/**
	 * Sets the value for the given languages. If no language is specified, all
	 * languages that have no explicit value set will have the given value set.
	 */
	public void set(Language[] languages, String... values)
	{
		if (languages.length == 0)
		{
			for (Language language : Language.values())
			{
				this.values.putIfAbsent(language, Arrays.asList(values));
			}
			return;
		}

		for (Language language : languages)
		{
			this.values.put(language, Arrays.asList(values));
		}
	}

	/** Sets values from the given annotation. */
	public void set(Value... values)
	{
		for (Chacy.Value rule : values)
		{
			this.set(rule.lang(), rule.value());
		}
	}

	/** Returns an optional of the values stored for the given language. */
	public Optional<Collection<String>> get(Language language)
	{
		return Optional.ofNullable(this.values.get(language));
	}
}
