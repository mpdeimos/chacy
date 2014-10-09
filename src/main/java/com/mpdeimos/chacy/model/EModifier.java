package com.mpdeimos.chacy.model;

import java.util.EnumSet;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * Defines modifiers (but visibility) of an element.
 */
public enum EModifier
{
	FINAL(Modifier.FINAL, "final"), //$NON-NLS-1$

	ABSTRACT(Modifier.ABSTRACT, "abstract"); //$NON-NLS-1$

	private final Modifier modifier;
	private final String name;

	/** Constructor. */
	private EModifier(Modifier modifier, String name)
	{
		this.modifier = modifier;
		this.name = name;
	}

	public static EnumSet<EModifier> fromModifiers(Set<Modifier> modifiers)
	{
		EnumSet<EModifier> result = EnumSet.noneOf(EModifier.class);

		for (EModifier modifier : values())
		{
			if (modifiers.contains(modifier.modifier))
			{
				result.add(modifier);
			}
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return this.name;
	}
}
