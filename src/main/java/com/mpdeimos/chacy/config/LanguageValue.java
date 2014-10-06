package com.mpdeimos.chacy.config;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Chacy.Value;
import com.mpdeimos.chacy.Language;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** Holds a string value for a given language, e.g. to rename an element. */
public class LanguageValue
{
	/** Map of languages to values. */
	private final Map<Language, String> values = new HashMap<>();

	/**
	 * Sets the value for the given languages. If no language is specified, all
	 * languages that have no explicit value set will have the given value set.
	 */
	public void set(String name, Language... languages)
	{
		if (languages.length == 0)
		{
			for (Language language : Language.values())
			{
				this.values.putIfAbsent(language, name);
			}
			return;
		}

		for (Language language : languages)
		{
			this.values.put(language, name);
		}
	}

	/** Sets values from the given annotation. */
	public void set(Value[] values)
	{
		for (Chacy.Value rule : values)
		{
			this.set(rule.value(), rule.lang());
		}
	}

	/** Returns an optional of the value stored for the given language. */
	public Optional<String> get(Language language)
	{
		return Optional.ofNullable(this.values.get(language));
	}
}
