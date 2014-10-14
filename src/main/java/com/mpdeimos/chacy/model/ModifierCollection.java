package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.util.ListMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Collection of modifiers. Supports modification for single languages.
 */
public class ModifierCollection
{
	/** Prefix for modifiers that will remove this modifier. */
	private static final String REMOVE_MODIFIER_PREFIX = "-"; //$NON-NLS-1$

	/** The original modifiers from the Java class. */
	private final List<String> originalModifiers;

	/**
	 * List of custom modifiers for a language, including modifiers prefixed
	 * with {@value #REMOVE_MODIFIER_PREFIX}.
	 */
	private final ListMap<Language, String> languageModifiers = new ListMap<>();

	/**
	 * Map that registers rules for substituting Java visibility by a language
	 * specific visibility. The keys for this have to e created by
	 * {@link #getLanguageVisibilityKey(Language, EVisibility)}.
	 */
	private final Map<String, String> languageVisibility = new HashMap<>();

	/** The original visibility of the Java class. */
	private final EVisibility originalVisibility;

	/** Constructor. */
	public ModifierCollection(
			EVisibility visibilty,
			Collection<String> modifiers)
	{
		this.originalVisibility = visibilty;
		this.originalModifiers = new ArrayList<>(modifiers);
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
				if (modifier.startsWith(REMOVE_MODIFIER_PREFIX))
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

	/** Sets the visibility for the given language. */
	public void setVisibility(Language language, String visibility)
	{
		this.languageVisibility.put(getLanguageVisibilityKey(
				language,
				this.originalVisibility), visibility);
	}

	/** @return the key to be used for accessing {@link #languageVisibility}. */
	private static String getLanguageVisibilityKey(
			Language language,
			EVisibility visibility)
	{
		return language.name() + ":" + visibility.name(); //$NON-NLS-1$
	}

}
