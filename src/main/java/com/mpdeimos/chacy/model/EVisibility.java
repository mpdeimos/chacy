package com.mpdeimos.chacy.model;

import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Defines the visibility of an element.
 */
public enum EVisibility
{
	/** public Java visibility. */
	PUBLIC("public"), //$NON-NLS-1$

	/** protected Java visibility. */
	PROTECTED("protected"), //$NON-NLS-1$

	/** package private visibility. */
	PACKAGE_PRIVATE(null),

	/** private visibility. */
	PRIVATE("private"); //$NON-NLS-1$

	/** The display name of the visibility. */
	private final String name;

	/** Constructor. */
	private EVisibility(String name)
	{
		this.name = name;
	}

	/** @return the {@link EVisibility} from the given Java Modifiers. */
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
