package com.mpdeimos.chacy.config;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Chacy.Value;
import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.util.CollectionMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** Holds a string value for a given language, e.g. to rename an element. */
public class LanguageValue
{
	/** Map of languages to values. */
	private final CollectionMap<Language, String> values;

	/** The default value, independent of a language. */
	private List<String> defaultValue;

	/** Constructor. */
	public LanguageValue()
	{
		this((List<String>) null);
	}

	/** Constructor. */
	public LanguageValue(List<String> defaultValue)
	{
		this.values = new CollectionMap<>();
		this.defaultValue = defaultValue;
	}

	/** Constructor. */
	public LanguageValue(LanguageValue renameRules)
	{
		this.values = new CollectionMap<>(renameRules.values);
		this.defaultValue = renameRules.defaultValue;
	}

	/**
	 * Sets the value for the given languages. If no language is specified, all
	 * languages that have no explicit value set will have the given value set.
	 */
	public void set(Language[] languages, String... values)
	{
		if (languages.length == 0)
		{
			this.defaultValue = new ArrayList<>(Arrays.asList(values));
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

	/**
	 * Returns an the values stored for the given language. If not present, the
	 * default value or <code>null</code>.
	 */
	public Collection<String> get(Language language)
	{
		Collection<String> value = this.values.get(language);
		if (value != null)
		{
			return value;
		}

		if (this.defaultValue == null)
		{
			return null;
		}

		return Collections.unmodifiableList(this.defaultValue);
	}
}
