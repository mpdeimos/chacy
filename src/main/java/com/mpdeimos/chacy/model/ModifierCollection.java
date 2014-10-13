package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.util.ListMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	/** Constructor. */
	public ModifierCollection(Collection<String> modifiers)
	{
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
		List<String> modifiers = new ArrayList<>(this.originalModifiers);

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
}
