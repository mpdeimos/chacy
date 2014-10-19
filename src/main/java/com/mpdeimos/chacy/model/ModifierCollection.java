package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Chacy.Value;
import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.util.CollectionMap;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Collection of modifiers. Supports modification for single languages.
 */
public class ModifierCollection
{
	/** The original modifiers from the Java class. */
	private final List<String> originalModifiers;

	/**
	 * List of custom modifiers for a language, including modifiers prefixed
	 * with {@value #REMOVE_MODIFIER_PREFIX}.
	 */
	private final CollectionMap<Language, String> languageModifiers;

	/**
	 * Map that registers rules for substituting Java visibility by a language
	 * specific visibility. The keys for this have to e created by
	 * {@link #getLanguageVisibilityKey(Language, EVisibility)}.
	 */
	private final Map<String, String> languageVisibility;

	/** The original visibility of the Java class. */
	private final EVisibility originalVisibility;

	/** Constructor. */
	public ModifierCollection(
			EVisibility visibilty,
			EnumSet<EModifier> modifiers)
	{
		this.originalVisibility = visibilty;
		this.originalModifiers = new ArrayList<>(modifiers.stream().map(
				EModifier::toString).collect(Collectors.toList()));
		this.languageVisibility = new HashMap<>();
		this.languageModifiers = new CollectionMap<>();
	}

	/** Copy constructor. */
	public ModifierCollection(ModifierCollection origin)
	{
		this.originalVisibility = origin.originalVisibility;
		this.originalModifiers = new ArrayList<>(origin.originalModifiers);
		this.languageVisibility = new HashMap<>(origin.languageVisibility);
		this.languageModifiers = new CollectionMap<Language, String>(
				origin.languageModifiers);
	}

	/**
	 * Adds modifiers for the given language. Modifiers can be prefixed with
	 * {@value #REMOVE_MODIFIER_PREFIX} and these modifiers will be removed.
	 */
	public void addModifiers(Language language, String... modifiers)
	{
		for (String modifier : modifiers)
		{
			this.languageModifiers.addToList(language, modifier);
		}
	}

	/** Adds modifiers from the given value annotation. */
	public void addModifiers(Value... values)
	{
		for (Value value : values)
		{
			Language[] languages = value.lang();
			if (languages.length == 0)
			{
				languages = Language.values();
			}

			for (Language language : languages)
			{
				addModifiers(language, value.value());
			}
		}
	}

	/**
	 * Replaces a modifier for the given language.
	 */
	public void replaceModifier(
			Language language,
			EModifier javaModifier,
			String languageModifier)
	{
		if (this.originalModifiers.contains(javaModifier.toString()))
		{
			this.addModifiers(
					language,
					Chacy.Const.REMOVE + javaModifier,
					languageModifier);
		}
	}

	/** @return The modifiers for the given language . */
	public List<String> getModifiers(Language language)
	{
		List<String> modifiers = new ArrayList<>();
		modifiers.add(this.getVisibility(language));
		modifiers.addAll(this.originalModifiers);

		List<String> languageModifiers = this.languageModifiers.get(language);
		if (languageModifiers != null)
		{
			for (String modifier : languageModifiers)
			{
				if (modifier.startsWith(Chacy.Const.REMOVE))
				{
					modifiers.remove(modifier.substring(1));
				}
				else if (!modifiers.contains(modifier))
				{
					modifiers.add(modifier);
				}
			}
		}

		return modifiers;
	}

	/** @return The visibility string for the given language. */
	private String getVisibility(Language language)
	{
		String visibility = this.languageVisibility.get(getLanguageVisibilityKey(
				language,
				this.originalVisibility));
		if (visibility != null)
		{
			return visibility;
		}
		return this.originalVisibility.toString();
	}

	/** Sets the visibility rule for the given language. */
	public void replaceVisibility(
			Language language,
			EVisibility javaVisibility,
			String languageVisibility)
	{
		this.languageVisibility.put(getLanguageVisibilityKey(
				language,
				javaVisibility), languageVisibility);
	}

	/** @return the key to be used for accessing {@link #languageVisibility}. */
	private static String getLanguageVisibilityKey(
			Language language,
			EVisibility visibility)
	{
		return language.name() + ":" + visibility.name();
	}
}
