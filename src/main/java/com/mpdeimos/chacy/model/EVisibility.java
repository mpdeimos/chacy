package com.mpdeimos.chacy.model;

import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Defines the visibility of an element.
 */
public enum EVisibility
{
	PUBLIC("public"), //$NON-NLS-1$

	PROTECTED("protected"), //$NON-NLS-1$

	PACKAGE_PRIVATE(null),

	PRIVATE("private"); //$NON-NLS-1$

	private final String name;

	/** Constructor. */
	private EVisibility(String name)
	{
		this.name = name;
	}

	public static EVisibility fromModifiers(Set<Modifier> modifiers)
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
