package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.config.LanguageSupport;
import com.mpdeimos.chacy.config.LanguageValue;

import java.util.Collection;

/** Defines a source code element that can be excluded or renamed. */
public class Element
{
	/** Map of type rename rules. */
	protected final LanguageValue name;

	/** Modifiers of the type. */
	protected final ModifierCollection modifiers;

	/** The languages this element supports. */
	private final LanguageSupport supportedLanguages = new LanguageSupport();

	/** Accessor for language values. */
	protected final LanguageValueAccessor accessor;

	/** Constructor. */
	public Element(
			LanguageValue name,
			ModifierCollection modifiers,
			LanguageValueAccessor accessor)
	{
		this.name = name;
		this.modifiers = modifiers;
		this.accessor = accessor;
	}

	/** @see #name */
	public String getName()
	{
		return this.accessor.access(this.name).iterator().next();
	}

	/** @see #supportedLanguages */
	public LanguageSupport getSupportedLanguages()
	{
		return this.supportedLanguages;
	}

	/** {@link LanguageValue} accessor delegate definition. */
	@FunctionalInterface
	public interface LanguageValueAccessor
	{
		/** Accesses a language value. */
		public Collection<String> access(LanguageValue langaugeValue);
	}
}
