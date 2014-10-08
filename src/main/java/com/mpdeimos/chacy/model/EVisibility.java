package com.mpdeimos.chacy.model;

import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Defines the visibility of an element.
 */
public enum EVisibility
{
	PUBLIC("public"),

	PROTECTED("protected"),

	PACKAGE_PRIVATE(null),

	PRIVATE("private");

	private final String name;

	/** Constructor. */
	private EVisibility(String name)
	{
		this.name = name;
	}

	public static EVisibility fromModifier(Set<Modifier> modifiers)
	{
		if (modifiers.contains(Modifier.PUBLIC))
		{
			return PUBLIC;
		}

		if (modifiers.contains(Modifier.PROTECTED))
		{
			return PROTECTED;
		}

		if (modifiers.contains(Modifier.PRIVATE))
		{
			return PRIVATE;
		}

		return PACKAGE_PRIVATE;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return this.name;
	}
}
